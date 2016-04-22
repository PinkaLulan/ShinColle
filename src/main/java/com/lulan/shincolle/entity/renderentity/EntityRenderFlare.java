package com.lulan.shincolle.entity.renderentity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.lulan.shincolle.utility.LogHelper;

/** light entity for moving light source
 *  
 *  (in testing)
 */
public class EntityRenderFlare extends BasicRenderEntity {
	
	public int life;   //flare life
	public int light;  //flare light level
	
	
	public EntityRenderFlare(World world) {
		super(world);
		this.setSize(0.8F, 0.8F);
		this.life = 2;
		this.light = 14;
	}
	
	@Override
	public void onUpdate() {
		//server side
		if(!this.worldObj.isRemote) {
			//check life
			if(this.ticksExisted > this.life) {
				this.setDead();
			}
		}//end server side
		//client side
		else {
			if(this.ticksExisted == 1) {
				float light = this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), (int)this.posY, MathHelper.floor_double(this.posZ));
	  			
				if(this.posY >= 1D && light < 12F) {
					int px = MathHelper.floor_double(this.posX);
					int py = (int) this.posY;
					int pz = MathHelper.floor_double(this.posZ);
					
					for(int i = -1; i <= 1; i++) {
						for(int k = -1; k <= 1; k++) {
							Block b = this.worldObj.getBlock(px+i, py, pz+k);
							int meta = this.worldObj.getBlockMetadata(px+i, py, pz+k);
							
							this.worldObj.setLightValue(EnumSkyBlock.Block, px+i, py, pz+k, this.light);
//							this.worldObj.setLightValue(EnumSkyBlock.Sky, px+i, py, pz+k, this.light);
//							this.worldObj.notifyBlockChange(px+i, py, pz+k, b);
//							this.worldObj.markBlockForUpdate(px+i, py, pz+k);
//							this.worldObj.markBlocksDirtyVertical(px+i, py, pz+k-2, pz+k+3);
							this.worldObj.updateLightByType(EnumSkyBlock.Sky, px+i, py, pz+k);
						}
					}
					
//					this.worldObj.markBlockRangeForRenderUpdate(px-2, py-2, pz-2, px+2, py+2, pz+2);
				}
			}
		}//end client side
	}
	
	@Override
	public void setDead() {

        super.setDead();
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {	
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {	
	}
	

}

