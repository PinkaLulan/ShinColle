package com.lulan.shincolle.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.capability.CapaInventoryExtend;
import com.lulan.shincolle.capability.CapaShipSavedValues;
import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.entity.transport.EntityTransportWa;
import com.lulan.shincolle.handler.AttackHandler;
import com.lulan.shincolle.handler.BuffHandler;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.GuardHandler;
import com.lulan.shincolle.handler.IAIShip;
import com.lulan.shincolle.handler.IInventoryShip;
import com.lulan.shincolle.handler.IMoveShip;
import com.lulan.shincolle.handler.IPacketShip;
import com.lulan.shincolle.handler.IRenderEntity;
import com.lulan.shincolle.handler.IStateShip;
import com.lulan.shincolle.handler.RenderHandler;
import com.lulan.shincolle.handler.ShipInventoryHandler;
import com.lulan.shincolle.handler.ShipMoveHandler;
import com.lulan.shincolle.handler.ShipStateHandler;
import com.lulan.shincolle.handler.TargetHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.server.CacheDataShip;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.InteractHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TaskHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.items.CapabilityItemHandler;

/**SHIP DATA <br>
 * Explanation in crafting/ShipCalc.class
 */
public abstract class BasicEntityShip extends EntityTameable
implements IInventoryShip, IStateShip, IMoveShip, IRenderEntity, IPacketShip,
           IAIShip,
           
           IShipCannonAttack, //TODO to IAttackEntity
           IShipFloating,
           IShipCustomTexture,
           IShipMorph,
           IShipGuardian  //TODO to IGuardEntity
{

    //override attribute
    public static final IAttribute MAX_HP = (new RangedAttribute((IAttribute)null, "generic.maxHealth", 4D, 0D, 30000D)).setDescription("Max Health").setShouldWatch(true);
    
    /** inventory: items, handheld item, equips, ... */
    protected ShipInventoryHandler shipItems;
    /** states: flags, minor values, ... */
    protected ShipStateHandler shipStates;
    /** move: path finding, move, jump, ... */
    protected ShipMoveHandler shipMove;
    /** target: ai, attack, guard, owner target */
    protected TargetHandler shipTarget;
    /** attack: all attack methods */
    protected AttackHandler shipAttack;
    /** guard: type, target, position, ... */
    protected GuardHandler shipGuard;
    /** buff: buff, debuff, attack effect, ... */
    protected BuffHandler shipBuff;
    /** render params handler */
    protected RenderHandler shipRender;
    
    /** stop onUpdate, onLivingUpdate flag */
    protected static boolean stopAI = false;
    protected boolean stopAIindivdual = false;
    /** initial flag */
    private boolean initWait, initPost;        //init flag
    private boolean isUpdated;                 //updated ship id/owner id tag
    private int updateTime = 16;               //update check interval
    /** body part height: values are between: top, head, neck, chest, belly, underbelly, leg */
    protected byte[] bodyHeightStand;
    protected byte[] bodyHeightSit;
    /** ModelPos: posX, posY, posZ, scale (in ship inventory) */
    protected float[] modelPosInGUI;
    /** hand held item rotate angle */
    protected float[] rotateAngle;
    /** ship prev pos X ticks ago */
    protected double shipPrevX;
    protected double shipPrevY;
    protected double shipPrevZ;
    
    /** chunk loader */
    private Ticket chunkTicket;
    private Set<ChunkPos> chunks;
    
    /** inter-mod */
    protected boolean isMorph;  //is a morph entity, for Metamorph mod
    protected EntityPlayer morphHost;
    
    
    public BasicEntityShip(World world)
    {
        super(world);
        
        //init parent value
        this.ignoreFrustumCheck = true;  //即使不在視線內一樣render
        this.maxHurtResistantTime = 2;   //受傷無敵時間降為2 ticks
        this.isImmuneToFire = true;    //set ship immune to lava
        this.stepHeight = 1F;
        
        //init ship value
        this.bodyHeightStand = new byte[] {92, 78, 73, 58, 47, 37};
        this.bodyHeightSit = new byte[] {64, 49, 44, 29, 23, 12};
        this.modelPosInGUI = new float[] {0F, 0F, 0F, 50F};
        this.resetMissileData();
        this.shipPrevX = posX;              //ship X position at X ticks ago
        this.shipPrevY = posY;
        this.shipPrevZ = posZ;
        this.rotateAngle = new float[3];    //model rotate angle (right hand)
        this.initWait = false;              //init after 128 ticks
        this.initPost = false;              //init after nbt loaded
        this.isUpdated = false;
        this.chunkTicket = null;
        this.chunks = null;
        this.stopAI = false;
        this.stopAIindivdual = false;
        this.isMorph = false;
        
        //choice random sensitive body part
        randomSensitiveBody();
    }
    
    /** stop AI at base level */
    public boolean getStopAI()
    {
        return this.stopAI;
    }
    
    public void setStopAI(boolean flag)
    {
        this.stopAI = flag;
    }
    
    /** stop AI at object level */
    public boolean getStopAIindivdual()
    {
        return this.stopAIindivdual;
    }
    
    public void setStopAIindivdual(boolean flag)
    {
        this.stopAIindivdual = flag;
    }
    
    /** stop AI at class level */
    public abstract boolean getStopAIclass();
    public abstract void setStopAIclass(boolean flag);
    
    /** init data at the end of constructor */
    protected void initPre()
    {
        this.shipNavigator = new ShipPathNavigate(this);
        this.shipMoveHelper = new ShipMoveHelper(this, 60F);
    }
    
    /** init data after nbt tags loaded */
    protected void initPost()
    {
        this.initPost = true;
        
        //TODO
    }
    
    /**
     * init data only:
     *   1. after ticking X ticks &
     *   2. initWait = false &
     *   3. initPost = true
     */
    protected void initWait()
    {
        if (!this.initWait && this.initPost && this.ticksExisted > 60)
        {
            this.initWait = true;
            
            //TODO
        }
    }
    
    @Override
    public EntityAgeable createChild(EntityAgeable entity)
    {
        return null;
    }
    
    @Override
    public boolean isEntityInvulnerable(DamageSource source)
    {
        return StateTimer[ID.T.ImmuneTime] > 0;
    }
    
    @Override
    protected boolean canDespawn()
    {
        return false;
    }
    
    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }
    
    @Override
    public boolean isBurning()
    {    //display fire effect
        return this.getStateEmotion(ID.S.HPState) == ID.HPState.HEAVY;
    }
    
    @Override
    public float getEyeHeight()
    {
        return this.height * 0.8F;
    }
    
    //check world time is 0~23 hour, -1 for fail
    private int getWorldHourTime()
    {
        long time = this.world.provider.getWorldTime();
        int checkTime = (int) (time % 1000L);
        
        if (checkTime == 0)
        {
            return (int) (time / 1000L) % 24;
        }
        
        return -1;
    }
    
    //音量大小
    @Override
    protected float getSoundVolume()
    {
        return ConfigHandler.volumeShip;
    }
    
    @Override
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F;
    }
    
    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return getCustomSound(0, this);
    }
    
    @Override
    @Nullable
    protected SoundEvent getHurtSound()
    {
        return getCustomSound(2, this);
    }
    
    @Override
    @Nullable
    protected SoundEvent getDeathSound()
    {
        return getCustomSound(3, this);
    }
    
    /**
     * Plays living's sound at its position
     */
    @Override
    public void playLivingSound()
    {
        //30% play sound
        if (this.getStateFlag(ID.F.NoFuel) || this.rand.nextInt(10) > 3) return;
        
        //get sound event
        SoundEvent sound = null;
        
        //married ship
        if (this.getStateFlag(ID.F.IsMarried))
        {
            if (rand.nextInt(5) == 0)
            {
                sound = getCustomSound(4, this);
            }
            else
            {
                sound = getAmbientSound();
            }
        }
        //normal ship
        else
        {
            sound = getAmbientSound();
        }
        
        //play sound
        if (sound != null)
        {
            this.playSound(sound, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    //play hurt sound with sound cooldown
    @Override
    protected void playHurtSound(DamageSource source)
    {
        if (this.StateTimer[ID.T.SoundTime] <= 0)
        {
            this.StateTimer[ID.T.SoundTime] = 20 + this.getRNG().nextInt(30);
            super.playHurtSound(source);
        }
    }
    
    /**
     * FIX: mounts and rider be pushed while saving and loading from disk
     */
    @Override
    public boolean writeToNBTOptional(NBTTagCompound nbt)
    {
        //is guarding and riding, check dist to guard pos
        if (this.isRiding() && this.dimension == this.getGuardedPos(3) &&
            this.getGuardedPos(1) > 0)
        {
            float dx = (float) (this.posX - this.getGuardedPos(0));
            float dy = (float) (this.posY - this.getGuardedPos(1));
            float dz = (float) (this.posZ - this.getGuardedPos(2));
            float dist = dx*dx+dy*dy+dz*dz;
            
            if (dist > 256F)
            {
                this.dismountRidingEntity();
                this.setPosition(this.getGuardedPos(0)+0.5D, this.getGuardedPos(1)+0.5D, this.getGuardedPos(2)+0.5D);
            
                if (!this.world.isRemote) this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_PosRot, true);
            }
        }
        
        //is riding player, dismount while saving/loading
        if (this.getRidingEntity() instanceof EntityPlayer)
        {
            this.dismountRidingEntity();
            this.sendSyncPacketRiders();
        }
        
        return super.writeToNBTOptional(nbt);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        //load ship attributes
        CapaShipSavedValues.loadNBTData(nbt, this);
        
        //load ship inventory
        if (nbt.hasKey(CapaInventoryExtend.InvName))
        {
            itemHandler.deserializeNBT((NBTTagCompound) nbt.getTag(CapaInventoryExtend.InvName));
        }
        
        //change rider position on chunk loading to fix "Wrong Location!" bug
        if (this.world == null || (!this.world.isRemote && this.ticksExisted <= 0))
        {
            if (nbt.hasKey("Passengers", Constants.NBT.TAG_LIST))
            {
                NBTTagList list = nbt.getTagList("Passengers", Constants.NBT.TAG_COMPOUND);
                
                for (int i = 0; i < list.tagCount(); ++i)
                {
                    NBTTagCompound rider = list.getCompoundTagAt(i);
                    NBTTagList pos = rider.getTagList("Pos", 6);
                    pos.set(0, new NBTTagDouble(this.posX));
                    pos.set(2, new NBTTagDouble(this.posZ));
                }
            }
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        //load ship attributes
        CapaShipSavedValues.saveNBTData(nbt, this);
        
        //load ship inventory
        nbt.setTag(CapaInventoryExtend.InvName, itemHandler.serializeNBT());
        
        return nbt;
    }
    
    /**
     * calc ship attributes<br>
     * <br>
     * parms:<br>
     *   flag: bits: EDCBA, all = 31<br>
     *         A(1):  recalc raw attrs<br>
     *         B(2):  recalc equips<br>
     *         C(4):  recalc morale buff<br>
     *         D(8):  recalc potion buff<br>
     *         E(16): recalc formation buff<br>
     *         <br>
     *   sync: send sync packet to client<br>
     */
    public static void calcShipAttributes(BasicEntityShip ship, int flag, boolean sync)
    {
        //null check
        if (this.shipAttrs == null) this.shipAttrs = new AttrsAdv(this.getShipClass());
        
        //server side
        if (!this.world.isRemote)
        {
            //check morph
            if (this.isMorph)
            {
                //recalc raw attrs
                if ((flag & 1) == 1)
                {
                    BuffHelper.updateAttrsRaw(this.shipAttrs, this.getShipClass(), this.getLevel());
                    this.calcShipAttributesAddRaw();
                    this.setUpdateFlag(ID.FlagUpdate.AttrsRaw, true);
                    this.setUpdateFlag(ID.FlagUpdate.AttrsBonus, true);
                }
                //recalc equips
                if ((flag & 2) == 2)
                {
                    EquipCalc.updateAttrsEquipOfMorph(this);
                    this.calcShipAttributesAddEquip();
                    this.setUpdateFlag(ID.FlagUpdate.AttrsEquip, true);
                }
                //recalc potion buff
                if ((flag & 8) == 8)
                {
                    BuffHelper.updateBuffPotion(this);
                    this.setUpdateFlag(ID.FlagUpdate.AttrsPotion, true);
                }
                
                  //apply all buff to raw attrs
                BuffHelper.applyBuffOnAttrs(this);
                this.calcShipAttributesAdd();
                this.setUpdateFlag(ID.FlagUpdate.AttrsBuffed, true);
            }
            else
            {
                //recalc raw attrs
                if ((flag & 1) == 1)
                {
                    BuffHelper.updateAttrsRaw(this.shipAttrs, this.getShipClass(), this.getLevel());
                    this.calcShipAttributesAddRaw();
                    this.setUpdateFlag(ID.FlagUpdate.AttrsRaw, true);
                }
                //recalc equips
                if ((flag & 2) == 2)
                {
                    EquipCalc.updateAttrsEquip(this);
                    this.calcShipAttributesAddEquip();
                    this.setUpdateFlag(ID.FlagUpdate.AttrsEquip, true);
                }
                //recalc morale buff
                if ((flag & 4) == 4)
                {
                    BuffHelper.updateBuffMorale(this.shipAttrs, this.getMorale());
                    this.setUpdateFlag(ID.FlagUpdate.AttrsMorale, true);
                }
                //recalc potion buff
                if ((flag & 8) == 8)
                {
                    BuffHelper.updateBuffPotion(this);
                    this.setUpdateFlag(ID.FlagUpdate.AttrsPotion, true);
                }
                //recalc formation buff
                if ((flag & 16) == 16)
                {
                    BuffHelper.updateBuffFormation(this);
                    this.setUpdateFlag(ID.FlagUpdate.AttrsFormation, true);
                }
                  //apply all buff to raw attrs
                BuffHelper.applyBuffOnAttrs(this);
                this.calcShipAttributesAdd();
                this.setUpdateFlag(ID.FlagUpdate.AttrsBuffed, true);
            }
            
//            float[] raw = this.shipAttrs.getAttrsRaw();  //TODO debug
//            float[] eq = this.shipAttrs.getAttrsEquip();
//            float[] mor = this.shipAttrs.getAttrsMorale();
//            float[] pot = this.shipAttrs.getAttrsPotion();
//            float[] form = this.shipAttrs.getAttrsFormation();
//            float[] buf = this.shipAttrs.getAttrsBuffed();
//            int idd = ID.Attrs.ATK_L;
//            LogHelper.debug("BBBBBB "+this.world.isRemote+" "+raw[idd]
//                    +" "+eq[idd]+" "+mor[idd]+" "+pot[idd]
//                    +" "+form[idd]+" "+buf[idd]);
            
            //send sync packets
            if (sync)
            {
                this.sendSyncPacketMinor();
                this.sendSyncPacketAttrs();
            }
        }
        
        //set attrs to entity
        /**
         * DO NOT SET ATTACK DAMAGE to non-EntityMob!!!!!
         */
        getEntityAttribute(MAX_HP).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.HP));
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.MOV));
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.KB));
        
        //apply attrs to player
        if (this.morphHost != null)
        {
            this.morphHost.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D + this.shipAttrs.getAttrsBuffed(ID.Attrs.HP) * ConfigHandler.morphHPRatio);
        }
    }
    
    /** calc addition attributes */
    public void calcShipAttributesAdd() {}
    public void calcShipAttributesAddRaw() {}
    public void calcShipAttributesAddEquip() {}
    
    /** reset attack effect map */
    public void calcShipAttributesAddEffect()
    {
        this.AttackEffectMap = new HashMap<Integer, int[]>();
    }
    
    


 
    
    /** get model position in GUI */
    public float[] getModelPos()
    {
        return modelPosInGUI;
    }
    
    /** grudge consumption when IDLE */
    public int getGrudgeConsumption()
    {
        return getStateMinor(ID.M.GrudgeCon);
    }
    
    public int getAmmoConsumption()
    {
        return getStateMinor(ID.M.AmmoCon);
    }
    
    public int getFoodSaturation()
    {
        return getStateMinor(ID.M.Food);
    }
    
    public int getFoodSaturationMax()
    {
        return getStateMinor(ID.M.FoodMax);
    }
    
    public CapaInventoryExtend getCapaShipInventory()
    {
        return this.itemHandler;
    }
    

    
    @Override
    public IAttributeInstance getEntityAttribute(IAttribute attribute)
    {
        if (attribute == SharedMonsterAttributes.MAX_HEALTH)
        {
            this.getAttributeMap().getAttributeInstance(MAX_HP); 
        }
        
        return this.getAttributeMap().getAttributeInstance(attribute);
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        this.getAttributeMap().registerAttribute(MAX_HP);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
    }
    
    //prevent player use name tag
    @Override
    public void setCustomNameTag(String str) {}
    
    //custom name tag method
    public void setNameTag(String str)
    {
        super.setCustomNameTag(str);
    }
    
    public void setEntitySit(boolean sit)
    {
        this.setSitting(sit);
        
        //若設定為坐下, 則清空路徑跟攻擊目標
        if (sit)
        {
            this.isJumping = false;
            this.getShipNavigate().clearPathEntity();
            this.getNavigator().clearPathEntity();
            this.setAttackTarget(null);
            this.setEntityTarget(null);
        }
    }
    
    public void setRiderAndMountSit()
    {
        //set mount sitting
        if (this.getRidingEntity() instanceof BasicEntityShip)
        {
            BasicEntityShip mountShip = (BasicEntityShip) this.getRidingEntity();
            
            mountShip.setEntitySit(this.isSitting());
            
            //special mount: set all rider sit
            if (mountShip.getRidingState() > 0)
            {
                List<Entity> rider = mountShip.getPassengers();
                
                for (Entity r : rider)
                {
                    if (r instanceof BasicEntityShip)
                    {
                        ((BasicEntityShip) r).setEntitySit(this.isSitting());
                    }
                }
            }
        }
        
        //set all rider sitting
        List<Entity> rider = this.getPassengers();
        
        for (Entity r : rider)
        {
            if (r instanceof BasicEntityShip)
            {
                ((BasicEntityShip) r).setEntitySit(this.isSitting());
            }
        }
    }
    
    /**
     * 1.9.4:
     * EnumActionResult:
     *   PASS:本方法的動作成功, 並且繼續給其他interact方法判定
     *   FAIL:本方法的動作失敗, 並且禁止其他interact
     *   SUCCESS:本方法的動作成功, 並且禁止其他interact
     */
    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand)
    {
        //禁用副手, 死亡時不反應, morph不反應
        if (hand == EnumHand.OFF_HAND || !this.isEntityAlive() || this.isMorph) return EnumActionResult.FAIL;

        //server side
        if (!this.world.isRemote)
        {
            //use item
            if (stack != null)
            {
                //use name tag, owner only
                if (stack.getItem() == Items.NAME_TAG && TeamHelper.checkSameOwner(player, this))
                {
                    //若該name tag有取名過, 則將名字貼到entity上
                    if (stack.hasDisplayName())
                    {
                        this.setNameTag(stack.getDisplayName());
                        return EnumActionResult.SUCCESS;
                    } 
                }
                //use repair bucket
                else if (stack.getItem() == ModItems.BucketRepair)
                {
                    if (InteractHelper.interactBucket(this, player, stack)) return EnumActionResult.SUCCESS;
                }
                //use owner paper, owner only
                else if (stack.getItem() == ModItems.OwnerPaper && TeamHelper.checkSameOwner(this, player) && player.isSneaking())
                {
                    if (InteractHelper.interactOwnerPaper(this, player, stack)) return EnumActionResult.SUCCESS;
                }
                //use modernization kit
                else if (stack.getItem() == ModItems.ModernKit)
                {
                    if (InteractHelper.interactModernKit(this, player, stack)) return EnumActionResult.SUCCESS;
                }
                //use pointer item (caress head mode server side)
                else if (stack.getItem() == ModItems.PointerItem && stack.getMetadata() > 2 && !player.isSneaking())
                {
                    InteractHelper.interactPointer(this, player, stack);
                    return EnumActionResult.SUCCESS;
                }
                //use kaitai hammer, OWNER and OP only
                else if (stack.getItem() == ModItems.KaitaiHammer && player.isSneaking() &&
                         (TeamHelper.checkSameOwner(this, player) || EntityHelper.checkOP(player)))
                {
                    InteractHelper.interactKaitaiHammer(this, player, stack);
                    return EnumActionResult.SUCCESS;
                }
                //use wedding ring
                else if (stack.getItem() == ModItems.MarriageRing && !this.getStateFlag(ID.F.IsMarried) && 
                         player.isSneaking() && TeamHelper.checkSameOwner(this, player))
                {
                    InteractHelper.interactWeddingRing(this, player, stack);
                    return EnumActionResult.SUCCESS;
                }
                //use book
                else if (stack.getItem() == ModItems.TrainingBook)
                {
                    if (this.getLevel() < 150)
                    {
                        int lv = this.getLevel() + 5 + this.rand.nextInt(6);
                        int lvcap = this.getStateFlag(ID.F.IsMarried) ? 150 : 100;
                        if (lv > lvcap) lv = lvcap;
                        
                        this.setShipLevel(lv, true);
                        
                        //level up sound
                        this.playSound(ModSounds.SHIP_LEVEL, 0.75F, 1F);
                        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), 0.75F, 1F);
                        
                        //item--
                        if (player != null && !player.capabilities.isCreativeMode)
                        {
                            --stack.stackSize;
                        }
                    }
                    
                    return EnumActionResult.SUCCESS;
                }
                //use lead: clear path
                else if (stack.getItem() == Items.LEAD)
                {
                    this.getShipNavigate().clearPathEntity();
                    return EnumActionResult.SUCCESS;
                }
                //feed
                else if (InteractHelper.interactFeed(this, player, stack))
                {
                    return EnumActionResult.SUCCESS;
                }
            }//end item != null
            
            //owner right click
            if (TeamHelper.checkSameOwner(this, player))
            {
                //sneak: open GUI
                if (player.isSneaking())
                {
                    FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.SHIPINVENTORY, this.world, this.getEntityId(), 0, 0);
                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    //current item = pointer
                    if (EntityHelper.getPointerInUse(player) != null)
                    {
                        //call sitting method by PointerItem class, not here
                    }
                    else
                    {
                        sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
                        this.setEntitySit(!this.isSitting());
                        this.setRiderAndMountSit();
                    }
                    
                    return EnumActionResult.SUCCESS;
                }
            }
        }//end server side
        //client side
        else
        {
            if (stack != null)
            {
                //use pointer item (caress head mode CLIENT side)
                if (stack.getItem() == ModItems.PointerItem && stack.getMetadata() > 2 && !player.isSneaking())
                {
                    this.setHitHeight(CalcHelper.getEntityHitHeightByClientPlayer(this));
                    this.setHitAngle(CalcHelper.getEntityHitSideByClientPlayer(this));
                    this.checkCaressed();
                    
                    //send caress position to server (this method is called every 4 ticks)
                    CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.HitHeight, this.getEntityId(), this.getHitHeight(), this.getHitAngle()));
                }
            }
        }//end client side
        
        return EnumActionResult.PASS;
    }
    
    @Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        if (! player.world.isRemote)
        {
            return TeamHelper.checkSameOwner(this, player);
        }
        return true;    //client端只回傳true
    }
    
    /**
     * 修改移動方法, 使其water跟lava中移動時像是flying entity
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(float strafe, float vertical, float forward)
    {
        super.travel(strafe, vertical, forward);
        
        //TODO old method:
//        public void moveEntityWithHeading(float strafe, float forward)
//        {
//            EntityHelper.moveEntityWithHeading(this, strafe, forward);
//        }
    }

    /** update entity 
     *  在此用onUpdate跟onLivingUpdate區分server跟client update
     *  for shincolle:
     *  onUpdate = client update only
     *  onLivingUpdate = server update only
     */
    @Override
    public void onUpdate()
    {
        /** BOTH SIDE */
        if (this.stopAI || this.stopAIindivdual) return;
        
        //check morph world (FIX: morph ship's world is different between server and client after change dim)
        if (this.morphHost != null)
        {
            this.world = this.morphHost.world;
            this.dimension = this.morphHost.dimension;
        }
        
        super.onUpdate();
        
        //get depth if in fluid block
        EntityHelper.checkDepth(this);
        
        //update arm
        updateArmSwingProgress();
        
        //update client timer
        updateBothSideTimer();
        
        //client side
        if (this.world.isRemote)
        {
            //有移動時, 產生水花特效
            if (this.getShipDepth() > 0D && !isRiding())
            {
                //(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
                double motX = this.posX - this.prevPosX;
                double motZ = this.posZ - this.prevPosZ;
                double parH = this.posY - (int)this.posY;
                double limit = 0.25D;
                
                if (motX > limit) motX = limit;
                else if (motX < -limit) motX = -limit;
                
                if (motZ > limit) motZ = limit;
                else if (motZ < -limit) motZ = -limit;
                
                
                if (motX != 0 || motZ != 0)
                {
                    ParticleHelper.spawnAttackParticleAt(this.posX + motX*3D, this.posY + 0.4D, this.posZ + motZ*3D, 
                            -motX, this.width, -motZ, (byte)47);
                }
            }
            
            //request model display sync after construction
            if (this.ticksExisted == 2)
            {
                CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.getEntityId(), this.world.provider.getDimension()));
            }
            
            //faster body rotateYaw update (vanilla = 12~20 ticks?)
            updateClientBodyRotate();
            
            //update searchlight, CLIENT SIDE ONLY, NO block light value sync!
            if (this.ticksExisted % ConfigHandler.searchlightCD == 0)
            {
                if (ConfigHandler.canSearchlight && this.isEntityAlive())
                {
                    updateSearchlight();
                }
            }
            
            //check every 8 ticks
            if (this.ticksExisted % 8 == 0)
            {
                //check every 16 ticks
                if (this.ticksExisted % 16 == 0)
                {
                    //generate HP state effect, use parm:lookX to send width size
                    switch (getStateEmotion(ID.S.HPState))
                    {
                    case ID.HPState.MINOR:
                        ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
                                this.width, 0.05D, 0D, (byte)4);
                        break;
                    case ID.HPState.MODERATE:
                        ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
                                this.width, 0.05D, 0D, (byte)5);
                        break;
                    case ID.HPState.HEAVY:
                        ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
                                this.width, 0.05D, 0D, (byte)7);
                        break;
                    default:
                        break;
                    }
                    
                    //check every 32 ticks
                    if (this.ticksExisted % 32 == 0)
                    {
                        if (!this.isMorph)
                        {
                            //show guard position
                            if (!this.getStateFlag(ID.F.CanFollow))
                            {
                                //set guard entity
                                if (this.getStateMinor(ID.M.GuardID) > 0)
                                {
                                    Entity getEnt = EntityHelper.getEntityByID(this.getStateMinor(ID.M.GuardID), 0, true);
                                    this.setGuardedEntity(getEnt);
                                }
                                else
                                {
                                    //reset guard entity
                                    this.setGuardedEntity(null);
                                }
                            }//end show pointer target effect
                            
                            //display circle particle, 只有owner才會接收到該ship同步的EID, 非owner讀取到的EID <= 0
                            //get owner entity
                            EntityPlayer player = null;
                            if (this.getStateMinor(ID.M.PlayerEID) > 0)
                            {
                                player = EntityHelper.getEntityPlayerByID(getStateMinor(ID.M.PlayerEID), 0, true);
                            }
                            
                            //show circle particle on ship and guard target
                            if (player != null && player.dimension == this.getGuardedPos(3))
                            {
                                ItemStack item = EntityHelper.getPointerInUse((EntityPlayer) player);
                                
                                if (item != null && item.getItemDamage() < 3 || ConfigHandler.alwaysShowTeamParticle)
                                {
                                    //show friendly particle
                                    ParticleHelper.spawnAttackParticleAtEntity(this, 0.3D, 7D, 0D, (byte)2);
                                    
                                    //show guard particle
                                    //標記在entity上
                                    if (this.getGuardedEntity() != null)
                                    {
                                        ParticleHelper.spawnAttackParticleAtEntity(this.getGuardedEntity(), 0.3D, 6D, 0D, (byte)2);
                                        ParticleHelper.spawnAttackParticleAtEntity(this, this.getGuardedEntity(), 0D, 0D, 0D, (byte)3, false);
                                    }
                                    //標記在block上
                                    else if (this.getGuardedPos(1) >= 0)
                                    {
                                        ParticleHelper.spawnAttackParticleAt(this.getGuardedPos(0)+0.5D, this.getGuardedPos(1), this.getGuardedPos(2)+0.5D, 0.3D, 6D, 0D, (byte)25);
                                        ParticleHelper.spawnAttackParticleAtEntity(this, this.getGuardedPos(0)+0.5D, this.getGuardedPos(1)+0.2D, this.getGuardedPos(2)+0.5D, (byte)8);
                                    }
                                }
                            }
                            else
                            {
                                this.setStateMinor(ID.M.PlayerEID, -1);
                            }
                        }//end is not morph
                    }//end every 32 ticks
                }//end every 16 ticks
            }//end  every 8 ticks
        }//end client side
    }

    /**
     * update living entity
     * 此方法在onUpdate中途呼叫
     */
    @Override
    public void onLivingUpdate()
    {
        if (this.ticksExisted == 5)
        {
            //reseed random
            this.rand.setSeed((this.getShipUID() << 4) + System.currentTimeMillis());
            
            //reset flag
            this.initAI = false;
            this.initWaitAI = false;
        }
        
        /** server side */
        if ((!world.isRemote))
        {
            if (!this.isMorph)
            {
                //update movement, NOTE: 1.9.4: must done before vanilla MoveHelper updating in super.onLivingUpdate()
                EntityHelper.updateShipNavigator(this);
                
                //update target
                TargetHelper.updateTarget(this);
                
                //update/init id
                updateShipCacheData(false);
            }
            
            super.onLivingUpdate();
            
            //timer ticking
            updateServerTimer();
            
            //pump liquid, transport ship has built-in pump equip
            if (this.StateFlag[ID.F.AutoPump] && !this.isMorph &&
                this.isEntityAlive() && !this.isSitting() && !this.getStateFlag(ID.F.NoFuel))
                TaskHelper.onUpdatePumping(this);
            
            //check every 8 ticks
            if ((ticksExisted & 7) == 0)
            {
                //init riding rotation
                if (this.ticksExisted == 32 && this.getPassengers().size() > 0)
                {
                    sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
                }
                
                if (!this.isMorph)
                {
                    //update formation buff (fast update)
                    if (this.getUpdateFlag(ID.FlagUpdate.FormationBuff))
                    {
                        this.calcShipAttributes(16, true);
                    }
                    
                    //reset AI and sync once
                    if (!this.initAI && ticksExisted > 10)
                    {
                        setStateFlag(ID.F.CanDrop, true);
                        clearAITasks();
                        clearAITargetTasks();        //reset AI for get owner after loading NBT data
                        setAIList();
                        setAITargetList();
                        decrGrudgeNum(0);            //check grudge
                        updateChunkLoader();
                        
                        this.initAI = true;
                    }
                    
                    //update task
                    TaskHelper.onUpdateTask(this);
                }
                
                //check every 16 ticks
                if ((ticksExisted & 15) == 0)
                {
                    if (this.isEntityAlive())
                    {
                        if (!this.isMorph)
                        {
                            //waypoint move
                            if (!(this.getRidingEntity() instanceof BasicEntityMount))
                            {
                                if (EntityHelper.updateWaypointMove(this))
                                {
                                    sendSyncPacket(S2CEntitySync.PID.SyncShip_Guard, true);
                                }
                            }
                            
                            //cancel mounts
                            if (this.hasShipMounts())
                            {
                                if (!this.canSummonMounts())
                                {
                                          //cancel riding
                                          if (this.isRiding() && this.getRidingEntity() instanceof BasicEntityMount)
                                          {
                                              this.dismountRidingEntity();
                                          }
                                      }
                            }
                        }
                        
                        //update potion buff
                        BuffHelper.convertPotionToBuffMap(this);
                        this.calcShipAttributes(8, true);
                    }

                    //check every 32 ticks
                    if ((ticksExisted & 31) == 0)
                    {
                        if (this.isEntityAlive() && !this.isMorph)
                        {
                            //apply potion effects
                            BuffHelper.applyBuffOnTicks(this);
                            
                            //use bucket automatically
                            if ((getMaxHealth() - getHealth()) > (getMaxHealth() * 0.1F + 5F))
                            {
                                if (decrSupplies(7))
                                {
                                    this.heal(this.getMaxHealth() * 0.08F + 15F);
                                
                                    //airplane++
                                    if (this instanceof BasicEntityShipCV)
                                    {
                                        BasicEntityShipCV ship = (BasicEntityShipCV) this;
                                        ship.setNumAircraftLight(ship.getNumAircraftLight() + 1);
                                        ship.setNumAircraftHeavy(ship.getNumAircraftHeavy() + 1);
                                    }
                                }
                            }
                        }
                        
                        //check every 64 ticks
                        if ((ticksExisted & 63) == 0)
                        {
                            //update random emotion
                            updateEmotionState();
                            
                            //sync riding state to client
                            if (this.isRiding() && !this.isMorph)
                            {
                                this.sendSyncPacketRiders();
                            }
                            
                            //check every 128 ticks
                            if ((ticksExisted & 127) == 0)
                            {
                                //update state (slow update)
                                this.calcShipAttributes(31, true);
                                
                                if (!this.isMorph)
                                {
                                    //check chunk loader
                                    this.updateChunkLoader();
                                    
                                    //delayed init, waiting for player entity loaded
                                    if (!this.initWaitAI && ticksExisted >= 128)
                                    {
                                        //request formation buff update
                                        setUpdateFlag(ID.FlagUpdate.FormationBuff, true);
                                        sendSyncPacketAll();
                                        
                                        this.initWaitAI = true;
                                    }
                                    
                                    //check owner online
                                    if (this.getPlayerUID() > 0)
                                    {
                                        //get owner
                                        EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
                                        
                                        //owner exists (online and same world)
                                        if (player != null)
                                        {
                                            //update owner entity id (could be changed when owner change dimension or dead)
                                            this.setStateMinor(ID.M.PlayerEID, player.getEntityId());
                                            //sync guard position
                                            this.sendSyncPacket(S2CEntitySync.PID.SyncShip_Guard, false);
                                        }
                                    }
                                    
                                    if (this.isEntityAlive())
                                    {
                                        //use combat ration automatically
                                        if (EntityHelper.getMoraleLevel(this.getMorale()) >= getStateMinor(ID.M.UseCombatRation) && getFoodSaturation() < getFoodSaturationMax())
                                        {
                                            useCombatRation();
                                        }
                                        
                                        //update mount
                                        updateMountSummon();
                                        
                                        //update consume item
                                        updateConsumeItem();
                                        
                                        //update morale value
                                        if (!getStateFlag(ID.F.NoFuel)) updateMorale();
                                    }
                                }//end is not morph
                                
                                //check every 256 ticks
                                if ((this.ticksExisted & 255) == 0)
                                {
                                    if (!this.isMorph)
                                    {
                                        if (this.isEntityAlive())
                                        {
                                            //show idle emotes
                                            if (!getStateFlag(ID.F.NoFuel)) applyEmotesReaction(4);
                                            
                                            //HP auto regen
                                            if (this.getHealth() < this.getMaxHealth())
                                            {
                                                this.heal(this.getMaxHealth() * 0.03F + 1F);
                                            }
                                            
                                            //update ship cache data
                                            updateShipCacheDataWithoutNewID();
                                        }
                                        
                                        //update name string
                                        EntityHelper.updateNameTag(this);
                                    }
                                    
                                    //food saturation--
                                    int f = this.getFoodSaturation();
                                    if (f > 0)
                                    {
                                        this.setFoodSaturation(--f);
                                    }
                                }//end every 256 ticks
                            }//end every 128 ticks
                        }//end every 64 ticks
                    }//end every 32 ticks
                }//end every 16 ticks
            }//end every 8 ticks
            
            //play timekeeping sound
            if (!this.isMorph && ConfigHandler.timeKeeping && this.getStateFlag(ID.F.TimeKeeper) && this.isEntityAlive())
            {
                int checkHour = getWorldHourTime();
                if (checkHour >= 0) playTimeSound(checkHour);
            }//end timekeeping
        }//end server side
        /** client side */
        else
        {
            super.onLivingUpdate();
            
            //update client side timer
            updateClientTimer();
            
            //request sync packet
            if (this.ticksExisted == 40 || this.ticksExisted == 120)
            {
                this.sendSyncRequest(1);    //request unit name sync
            }
            
            //every 32 ticks
            if ((this.ticksExisted & 31) == 0)
            {
                //show unit name and uid on head
                EntityHelper.showNameTag(this);
                
                if ((this.ticksExisted & 63) == 0)
                {
                    //request sync model state every X ticks
                    this.sendSyncRequest(0);    //request emotion state sync
                    
                    //every 128 ticks
                    if ((this.ticksExisted & 127) == 0)
                    {
                        //update potion buff (client side for GUI display)
                        this.sendSyncRequest(2);    //request buff map sync
                    }//end 128 ticks
                }//end 64 ticks
            }//end 32 ticks
        }//end client side
        
        /** both side */
        //every 32 ticks
        if ((this.ticksExisted & 15) == 0)
        {
            //TODO debug
//            float[] raw = this.shipAttrs.getAttrsRaw();
//            float[] eq = this.shipAttrs.getAttrsEquip();
//            float[] mor = this.shipAttrs.getAttrsMorale();
//            float[] pot = this.shipAttrs.getAttrsPotion();
//            float[] form = this.shipAttrs.getAttrsFormation();
//            float[] buf = this.shipAttrs.getAttrsBuffed();
//            int idd = ID.Attrs.ATK_L;
//            LogHelper.debug("AAAAAA "+this.world.isRemote+" "+raw[idd]
//                    +" "+eq[idd]+" "+mor[idd]+" "+pot[idd]
//                    +" "+form[idd]+" "+buf[idd]);
            
            if ((this.ticksExisted & 127) == 0)
            {
                this.setAir(300);
            }//end 128 ticks
        }//end 32 ticks
    }
    
    @Override
    public boolean canPassengerSteer()
    {
        return false;
    }
    
    

    //melee attack method, no ammo cost, no attack speed, damage = 12.5% atk
    @Override
    public boolean attackEntityAsMob(Entity target)
    {
        //TODO AttackHandler
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float atk)
    {
      //TODO AttackHandler
    }
    
    //true if host has mounts
    public boolean hasShipMounts()
    {
        return false;
    }
    
    //true if host can summon mounts
    public boolean canSummonMounts()
    {
        return (this.getStateEmotion(ID.S.State) & 1) == 1 && !this.getStateFlag(ID.F.NoFuel);
    }
    
    public BasicEntityMount summonMountEntity()
    {
        return null;
    }
    
    @Override
    public double getMountedYOffset()
    {
        return this.height;
    }
    
    @Override
    public Entity getHostEntity()
    {
        if (this.getPlayerUID() > 0)
        {
            if (this.world.isRemote)
            {
                return this.getOwner();
            }
            else
            {
                return EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
            }
        }
        else
        {
            return this.getOwner();
        }
    }
    
    //update ship id
    public void updateShipCacheData(boolean forceUpdate)
    {
        //register or update ship id and owner id
        if (!this.isUpdated && ticksExisted % updateTime == 0 || forceUpdate)
        {
            LogHelper.debug("DEBUG: update ship: initial SID, PID  cd: "+updateTime+" force: "+forceUpdate);
            
            //update owner uid
            if (this.getPlayerUID() <= 0)
            {
                ServerProxy.updateShipOwnerID(this);
            }
            
            //update ship uid
            ServerProxy.updateShipID(this);
            
            //update success
            if (getPlayerUID() > 0 && getShipUID() > 0)
            {
                this.sendSyncPacketAll();
                this.isUpdated = true;
            }
            
            //prolong update time
            if (updateTime > 4096)
            {
                updateTime = 4096;
            }
            else
            {
                updateTime *= 2;
            }
        }//end update id
    }
    
    //update ship id
    public void updateShipCacheDataWithoutNewID()
    {
        //update ship cache
        if (!this.world.isRemote)
        {
            int uid = this.getShipUID();
            
            if (uid > 0)
            {
                //ship dupe bug checking
                BasicEntityShip ship = ServerProxy.checkShipIsDupe(this, uid);
                
                if (this.equals(ship))
                {
                    CacheDataShip sdata = new CacheDataShip(this.getEntityId(), this.world.provider.getDimension(),
                            this.getShipClass(), this.isDead, this.posX, this.posY, this.posZ,
                            this.writeToNBT(new NBTTagCompound()));
                    
                    ServerProxy.setShipWorldData(uid, sdata);
                }
                else
                {
                    this.setDead();
                }
            }
        }
    }
    
    //update emotion, SERVER SIDE ONLY!
    protected void updateEmotionState()
    {
        float hpState = 1F;
          
        if (this.morphHost != null)
        {
            hpState = this.morphHost.getHealth() / this.morphHost.getMaxHealth();
        }
        else
        {
            hpState = this.getHealth() / this.getMaxHealth();
        }
        
        //check hp state
        if (hpState > 0.75F)
        {    //normal
            this.setStateEmotion(ID.S.HPState, ID.HPState.NORMAL, false);
        }
        else if (hpState > 0.5F)
        {    //minor damage
            this.setStateEmotion(ID.S.HPState, ID.HPState.MINOR, false);
        }
        else if (hpState > 0.25F)
        {    //moderate damage
            this.setStateEmotion(ID.S.HPState, ID.HPState.MODERATE, false);               
        }
        else
        {    //heavy damage
            this.setStateEmotion(ID.S.HPState, ID.HPState.HEAVY, false);
        }
        
        //roll emtion: hungry > T_T > bored
        if (getStateFlag(ID.F.NoFuel))
        {
            if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.HUNGRY)
            {
                this.setStateEmotion(ID.S.Emotion, ID.Emotion.HUNGRY, false);
            }
        }
        else
        {
            if (hpState < 0.35F)
            {
                if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.T_T)
                {
                    this.setStateEmotion(ID.S.Emotion, ID.Emotion.T_T, false);
                }            
            }
            else
            {
                //random Emotion
                switch (this.getStateEmotion(ID.S.Emotion))
                {
                case ID.Emotion.NORMAL:        //if normal, 33% to bored
                    if (this.getRNG().nextInt(3) == 0)
                        this.setStateEmotion(ID.S.Emotion, ID.Emotion.BORED, false);
                break;
                default:                    //other, 25% return normal
                    if (this.getRNG().nextInt(4) == 0)
                    {
                        this.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
                    }
                break;
                }
                
                //random Emotion4
                switch (this.getStateEmotion(ID.S.Emotion4))
                {
                case ID.Emotion.NORMAL:        //if normal, 33% to bored
                    if (this.getRNG().nextInt(3) == 0)
                        this.setStateEmotion(ID.S.Emotion4, ID.Emotion.BORED, false);
                break;
                default:                    //other, 33% return normal
                    if (this.getRNG().nextInt(3) == 0)
                        this.setStateEmotion(ID.S.Emotion4, ID.Emotion.NORMAL, false);
                break;
                }
            }
        }
        
        //send sync packet
        if (!this.world.isRemote)
        {
            this.sendSyncPacketEmotion();
        }
    }
      
    //update hp state
    protected void updateMountSummon()
    {
        if (this.hasShipMounts())
        {
            //summon mount if emotion state >= equip00
            if (this.canSummonMounts() && !this.getStateFlag(ID.F.NoFuel))
            {
                if (!this.isRiding())
                {
                    //summon mount entity
                    BasicEntityMount mount = this.summonMountEntity();
                    mount.initAttrs(this);
                    this.world.spawnEntity(mount);
                      
                    //clear rider
                    for (Entity p : this.getPassengers())
                    {
                        p.dismountRidingEntity();
                    }
                      
                    //set riding entity
                    this.startRiding(mount, true);
                    
                    //sync rider
                    mount.sendSyncPacket(0);
                }
            }
        }
    }
      
    //update consume item
    protected void updateConsumeItem()
      {
        //get ammo if no ammo
        if (!this.hasAmmoLight()) { this.decrAmmoNum(0, 0); }
        if (!this.hasAmmoHeavy()) { this.decrAmmoNum(1, 0); }
        
        //calc move distance
        double distX = posX - shipPrevX;
        double distY = posY - shipPrevY;
        double distZ = posZ - shipPrevZ;
        
        //calc total consumption
        int valueConsume = (int) MathHelper.sqrt(distX*distX + distY*distY + distZ*distZ);
        if (shipPrevY <= 0D) valueConsume = 0;  //do not decrGrudge if ShipPrev not inited
        
        //morale-- per 2 blocks
        int m = (int)((float)valueConsume * 0.5F);
        if(m < 5) m = 5;
        if(m > 50) m = 50;
        this.addMorale(-m);
        
        //moving grudge cost per block
        valueConsume *= ConfigHandler.consumeGrudgeAction[ID.ShipConsume.Move];
        
        //get exp if transport
        if(this instanceof EntityTransportWa && this.ticksExisted > 200)
        {
            //gain exp when moving
            int moveExp = (int) (valueConsume * 0.2F);
            addShipExp(moveExp);
        }
        
        //add idle grudge cost
        valueConsume += this.getGrudgeConsumption();
        
        //eat grudge
        decrGrudgeNum(valueConsume);
        
        //update pos
        shipPrevX = posX;
        shipPrevY = posY;
        shipPrevZ = posZ;
    }
      
    /** morale value
     *  5101 - X     Exciting
     *  3901 - 5100  Happy
     *  2101 - 3900  Normal
     *  901  - 2100  Tired
     *  0    - 900   Exhausted
     *  
     *  action:
     *  
     *  caress head:
     *    +X per 3 ticks
     *    
     *  feed:
     *    +X per item
     *    
     *  idle:
     *    -X if > happy
     *    +X if < normal * 150%
     *    
     *  attack:
     *    -X per hit
     *    +X per kill
     *    
     *  damaged:
     *    -X per hit
     *    
     *  move:
     *    -X per Y blocks
     *  
     */
    protected void updateMorale()
    {
        int m = this.getMorale();
          
        //out of combat
        if (EntityHelper.checkShipOutOfCombat(this))
        {
            if (m < ID.Morale.L_Normal + 1000)
            {
                //take 5 min from 0 to 2100
                this.setStateMinor(ID.M.Morale, m + 40);
            }
            else if (m > ID.Morale.L_Happy)
            {
                this.setStateMinor(ID.M.Morale, m - 10);
            }
        }
        //in combat
        else
        {
            if (m < ID.Morale.L_Tired)
            {
                this.setStateMinor(ID.M.Morale, m - 11);
            }
            else if (m < ID.Morale.L_Normal)
            {
                this.setStateMinor(ID.M.Morale, m - 7);
            }
            else if (m < ID.Morale.L_Happy)
            {
                this.setStateMinor(ID.M.Morale, m - 5);
            }
            else if (m >= ID.Morale.L_Happy)
            {
                this.setStateMinor(ID.M.Morale, m - 11);
            }
        } 
    }
    
    protected void updateFuelState(boolean nofuel)
    {
        if (nofuel)
        {
            setMorale(0);
            clearAITasks();
            clearAITargetTasks();
            sendSyncPacketAllMisc();
            
            //設定mount的AI
            if (this.getRidingEntity() instanceof BasicEntityMount)
            {
                ((BasicEntityMount) this.getRidingEntity()).clearAITasks();
            }
            
            //show no fuel emotes
            if (this.getEmotesTick() <= 0)
            {
                this.setEmotesTick(20);
                switch (this.rand.nextInt(7))
                {
                case 1:
                    applyParticleEmotion(0);  //drop
                    break;
                case 2:
                    applyParticleEmotion(32);  //hmm
                    break;
                case 3:
                    applyParticleEmotion(2);  //panic
                    break;
                case 4:
                    applyParticleEmotion(12);  //omg
                    break;
                case 5:
                    applyParticleEmotion(5);  //...
                    break;
                case 6:
                    applyParticleEmotion(20);  //orz
                    break;
                default:
                    applyParticleEmotion(10);  //dizzy
                    break;
                }
            }
        }
        else
        {
            clearAITasks();
            clearAITargetTasks();
            setAIList();
            setAITargetList();
            sendSyncPacketAllMisc();
            
            //設定mount的AI
            if (this.getRidingEntity() instanceof BasicEntityMount)
            {
                ((BasicEntityMount) this.getRidingEntity()).clearAITasks();
                ((BasicEntityMount) this.getRidingEntity()).setAIList();
            }
            
            //show recovery emotes
            if (this.getEmotesTick() <= 0)
            {
                this.setEmotesTick(40);
                switch (this.rand.nextInt(5))
                {
                case 1:
                    applyParticleEmotion(31);  //shy
                    break;
                case 2:
                    applyParticleEmotion(32);  //hmm
                    break;
                case 3:
                    applyParticleEmotion(7);  //note
                    break;
                default:
                    applyParticleEmotion(1);  //love
                    break;
                }
            }
        }
    }
      
    /** update searchlight effect */
    protected void updateSearchlight()
    {
        if (this.getStateMinor(ID.M.LevelSearchlight) > 0)
        {
            BlockPos pos = new BlockPos(this);
            int light = this.world.getLight(pos, true);
    
            //place new light block
            if (light < 11)
            {
                BlockHelper.placeLightBlock(this.world, pos);
            }
            //search light block, renew lifespan
            else
            {
                BlockHelper.updateNearbyLightBlock(this.world, pos);
            }
        }
    }
      
    /** update client timer */
    protected void updateClientTimer()
    {
        //attack motion timer
        if (this.StateTimer[ID.T.AttackTime] > 0) this.StateTimer[ID.T.AttackTime]--;
          
        //caress reaction time
        if (this.StateTimer[ID.T.Emotion3Time] > 0)
        {
            this.StateTimer[ID.T.Emotion3Time]--;
            
            if (this.StateTimer[ID.T.Emotion3Time] == 0)
            {
                this.setStateEmotion(ID.S.Emotion3, 0, false);
            }
        }
    }
      
    /** update server timer */
    protected void updateServerTimer()
    {
        //immune timer
        if (this.StateTimer[ID.T.ImmuneTime] > 0) this.StateTimer[ID.T.ImmuneTime]--;
          
        //crane change state delay
        if (this.StateTimer[ID.T.CrandDelay] > 0) this.StateTimer[ID.T.CrandDelay]--;
          
        //craning timer
        if (this.getStateMinor(ID.M.CraneState) > 1) this.StateTimer[ID.T.CraneTime]++;
          
        //hurt sound delay
        if (this.StateTimer[ID.T.SoundTime] > 0) this.StateTimer[ID.T.SoundTime]--;
    
        //emotes delay
        if (this.StateTimer[ID.T.EmoteDelay] > 0) this.StateTimer[ID.T.EmoteDelay]--;
    }
      
    /** update both side timer */
    protected void updateBothSideTimer()
    {
        //mount skill cd
        if (this.StateTimer[ID.T.MountSkillCD1] > 0) this.StateTimer[ID.T.MountSkillCD1]--;
        if (this.StateTimer[ID.T.MountSkillCD2] > 0) this.StateTimer[ID.T.MountSkillCD2]--;
        if (this.StateTimer[ID.T.MountSkillCD3] > 0) this.StateTimer[ID.T.MountSkillCD3]--;
        if (this.StateTimer[ID.T.MountSkillCD4] > 0) this.StateTimer[ID.T.MountSkillCD4]--;
        if (this.StateTimer[ID.T.MountSkillCD5] > 0) this.StateTimer[ID.T.MountSkillCD5]--;
    }
      
    /** update rotate */
    protected void updateClientBodyRotate()
    {
        if (!this.isRiding())
        {
            if (this.morphHost != null)
            {
                this.rotationYaw = this.morphHost.rotationYaw;
            }
            else
            {
                if (MathHelper.abs((float) (posX - prevPosX)) > 0.1F || MathHelper.abs((float) (posZ - prevPosZ)) > 0.1F)
                {
                    float[] degree = CalcHelper.getLookDegree(posX - prevPosX, posY - prevPosY, posZ - prevPosZ, true);
                    this.rotationYaw = degree[0];
                }
            }
        }
    }
    
    /** release ticket when dead */
    @Override
    public void setDead()
    {
        //clear chunk loader
        this.clearChunkLoader();
        super.setDead();
        
        //update ship cache
        this.updateShipCacheDataWithoutNewID();
    }
    
    /** release chunk loader ticket */
    public void clearChunkLoader()
    {
        //unforce chunk
        if (this.chunks != null)
        {
            for(ChunkPos p : this.chunks)
            {
                ForgeChunkManager.unforceChunk(this.chunkTicket, p);
            }
        }
        
        //release ticket
        if (this.chunkTicket != null)
        {
            ForgeChunkManager.releaseTicket(this.chunkTicket);
        }
        
        this.chunks = null;
        this.chunkTicket = null;
    }
      
    /** chunk loader ticking */
    public void updateChunkLoader()
    {
        if (!this.world.isRemote)
        {
            //set ticket
            setChunkLoader();
            //apply ticket
            applyChunkLoader();
        }
    }
      
    /** request chunk loader ticket
     * 
     *  player must be ONLINE
     */
    private void setChunkLoader()
    {
        //if equip chunk loader
        if (this.getStateMinor(ID.M.LevelChunkLoader) > 0)
        {
            EntityPlayer player = null;
            int uid = this.getPlayerUID();
            
            //check owner exists
            if (uid > 0)
            {
                player = EntityHelper.getEntityPlayerByUID(uid);
                
                //player is online and no ticket
                if (player != null && this.chunkTicket == null)
                {
                    //get ticket
                    this.chunkTicket = ForgeChunkManager.requestPlayerTicket(ShinColle.instance, player.getName(), world, ForgeChunkManager.Type.ENTITY);
                    
                    if (this.chunkTicket != null)
                    {
                        this.chunkTicket.bindEntity(this);
                    }
                    else
                    {
                        LogHelper.debug("DEBUG: Ship get chunk loader ticket fail.");
                        clearChunkLoader();
                    }
                }
            }//end check player
        }//end can chunk loader
        else
        {
            clearChunkLoader();
        }
    }
      
    /** force chunk load */
    private void applyChunkLoader()
    {
        if (this.chunkTicket != null)
        {
            HashSet<ChunkPos> unloadChunks = new HashSet<ChunkPos>();
            HashSet<ChunkPos> loadChunks = null;
            HashSet<ChunkPos> tempChunks = new HashSet<ChunkPos>();
            
            //get chunk x,z
            int cx = MathHelper.floor(this.posX) >> 4;
            int cz = MathHelper.floor(this.posZ) >> 4;
            
            //get new chunk
            loadChunks = BlockHelper.getChunksWithinRange(world, cx, cz, ConfigHandler.chunkloaderMode);
            
            //get unload chunk
            if (this.chunks != null)
            {
                unloadChunks.addAll(this.chunks);    //copy old chunks
            }
            unloadChunks.removeAll(loadChunks);        //remove all load chunks
            
            //get load chunk
            tempChunks.addAll(loadChunks);            //copy new chunks
            if (this.chunks != null)
            {
                loadChunks.removeAll(this.chunks);    //remove all old chunks
            }
            
            //set old chunk
            this.chunks = tempChunks;
            
              //unforce unload chunk
            for(ChunkPos p : unloadChunks)
            {
                ForgeChunkManager.unforceChunk(this.chunkTicket, p);
            }
            
            //force load chunk
            for(ChunkPos p : loadChunks)
            {
                ForgeChunkManager.forceChunk(this.chunkTicket, p);
            }
            
            LogHelper.debug("DEBUG : ship chunk loader: "+this.chunks+" "+this);
        }
    }
    
    //不跟aircraft, mount碰撞
    @Override
    protected void collideWithEntity(Entity target)
    {
        if (target instanceof BasicEntityAirplane)
        {
            return;
        }
        
        //TODO passenger need test
//          for (Entity p : this.getPassengers())
//          {
//              if (target.equals(p)) return;
//          }
        
        target.applyEntityCollision(this);
    }
    
    @Override
    public int getPortalCooldown()
    {
        return 40;
    }
    
    //固定swing max tick為6, 無視藥水效果
    @Override
    protected void updateArmSwingProgress()
    {
        int swingMaxTick = 6;
        if (this.isSwingInProgress)
        {
            ++this.swingProgressInt;
            
            if (this.swingProgressInt >= swingMaxTick)
            {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        }
        else
        {
            this.swingProgressInt = 0;
        }

        this.swingProgress = (float)this.swingProgressInt / (float)swingMaxTick;
    }
    
    @Override
    public boolean shouldDismountInWater(Entity rider)
    {
        return false;
    }
    
    @Override
    public double getShipFloatingDepth()
    {
        return 0.32D;
    }

    @Override
    public void setShipFloatingDepth(double par1) {}
    
    //apply heal effect
    @Override
    public void heal(float healAmount)
    {
        //server side
        if (!this.world.isRemote)
        {
            //apply heal particle, server side only
              TargetPoint tp = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
              CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 23, 0D, 0.1D, 0D), tp);
        
              //potion modify heal value (splash and cloud potion only)
              healAmount = BuffHelper.applyBuffOnHeal(this, healAmount);
        }
        
        super.heal(healAmount * this.shipAttrs.getAttrsBuffed(ID.Attrs.HPRES));
    }
    
    //death animation
    @Override
    protected void onDeathUpdate()
    {
        ++this.deathTime;
        
        //spawn smoke
        if (this.world.isRemote)
        {
            if ((this.ticksExisted & 3) == 0)
            {
                int maxpar = (int)((3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 1.8F);
                double range = this.width * 1.2D;
                for (int i = 0; i < maxpar; i++)
                ParticleHelper.spawnAttackParticleAt(posX-range+this.rand.nextDouble()*range*2D, posY+0.1D+this.rand.nextDouble()*0.3D, posZ-range+this.rand.nextDouble()*range*2D, 1.5D, 0D, 0D, (byte)43);
            }
        }
        
        if (this.deathTime == ConfigHandler.deathMaxTick)
        {
            //spawn ship egg
            if (!this.world.isRemote && this.getStateFlag(ID.F.CanDrop))
            {
                //set flag to false to prevent multiple drop from unknown bug
                this.setStateFlag(ID.F.CanDrop, false);
                
                //save ship attributes to ship spawn egg
                ItemStack egg = new ItemStack(ModItems.ShipSpawnEgg, 1, this.getShipClass()+2);
                egg.setTagCompound(EntityHelper.saveShipDataToNBT(this, true));
                
                //spawn entity item
                BasicEntityItem entityItem2 = new BasicEntityItem(this.world, this.posX, this.posY+0.5D, this.posZ, egg);
                this.world.spawnEntity(entityItem2);
            }
            
            //spawn XP orb
            if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))
            {
                int i = this.getExperiencePoints(this.attackingPlayer);
                i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                }
            }

            //set dead
            this.setDead();

            //spawn smoke particle
            for (int k = 0; k < 20; ++k)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1, new int[0]);
            }
        }
        //clear bug entity
        else if (this.deathTime > ConfigHandler.deathMaxTick && !this.isDead)
        {
            this.setDead();
        }
    }
    
    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        return 0;
    }
    
    @Override
    @Nullable
    public ItemStack getHeldItemMainhand()
    {
        //show morph on player's hand
        if (this.morphHost != null)
        {
            return this.morphHost.getHeldItemMainhand();
        }
        //show item in ship's inventory
        else
        {
            if (this.itemHandler != null) return this.itemHandler.getStackInSlot(22);
            return null;
        }
    }

    @Override
    @Nullable
    public ItemStack getHeldItemOffhand()
    {
        //show morph on player's hand
        if (this.morphHost != null)
        {
            return this.morphHost.getHeldItemOffhand();
        }
        //show item in ship's inventory
        else
        {
            if (this.itemHandler != null) return this.itemHandler.getStackInSlot(23);
            return null;
        }
    }
    
    public byte[] getBodyHeightStand()
    {
        return this.bodyHeightStand;
    }
    
    public byte[] getBodyHeightSit()
    {
        return this.bodyHeightSit;
    }
    
    /**
     * FIX: bug that ship be deleted while teleport (dismount)
     */
    @Override
    public void dismountEntity(Entity mount)
    {
        this.setPositionAndUpdate(this.posX, this.posY + 1D, this.posZ);
    }
    
    @Override
    public boolean isGlowing()
    {
        //for client side, if player is owner, show glowing effect when invisible
        if (this.world.isRemote)
        {
            if (this.isInvisible() &&
                TeamHelper.checkSameOwner(this, ClientProxy.getClientPlayer()))
            {
                return true;
            }
        }
        
        return super.isGlowing();
    }
    
    @Override
    public boolean isMorph()
    {
        return this.isMorph;
    }

    @Override
    public void setIsMorph(boolean par1)
    {
        this.isMorph = par1;
    }

    @Override
    public EntityPlayer getMorphHost()
    {
        return this.morphHost;
    }

    @Override
    public void setMorphHost(EntityPlayer player)
    {
        this.morphHost = player;
    }
    
    /**
     * IItemHandler getter:
     *   from null: get item slots
     *   from UP/DOWN: get hand slots
     *   from NEWS: get equip slots
     */
    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable net.minecraft.util.EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            if (facing == null) return (T) this.invItem;
            else if (facing.getAxis().isVertical()) return (T) this.invHand;
            else if (facing.getAxis().isHorizontal()) return (T) this.invEquip;
        }
        return super.getCapability(capability, facing);
    }
    
    @Override
    public ShipStateHandler getStateHandler()
    {
        if (this.shipStates == null) this.shipStates = new ShipStatesHandler(this);
        return this.shipStates;
    }
    
    
}