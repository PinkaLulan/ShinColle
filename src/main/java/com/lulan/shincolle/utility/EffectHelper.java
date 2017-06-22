package com.lulan.shincolle.utility;

import java.util.HashMap;
import java.util.Map;

import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

/**
 * potion setting:
   1  speed:          move spd +15%/lv (multiplier)
   2  slow:           move spd -35%/lv (multiplier)
   3  haste:          attack spd +15%/lv (multiplier)
   4  mining fatigue: attack spd -35%/lv (multiplier)
   5  strength:       dmg +15%/lv (multiplier)
   6  instant heal:   heal (2% + 8)/lv
   7  instant dmg:    hurt (2% + 8)/lv
   8  jump:           range +20%/lv (multiplier)
   9  nausea:         miss +40%/lv (addition)
   10 regen:          heal (2% + 2)/lv every x ticks (potion 900/1800/450 ticks)
   11 resis:          reduce non-missile dmg 20%/lv (multiplier)
   12 fire resis:     reduce missile dmg 20%/lv (multiplier)
   13 water breath:   dodge +15%/lv (addition) ASM +25%/lv (multiplier)
   14 invis:          no special effect (clear invis target every 64 ticks)
   15 blind:          range -80% (only 1 level) (multiplier)
   16 night vision:   night battle effect -40%/lv (multiplier)
   17 hunger:         consumed grudge +100%/lv, no saturation limit (multiplier)
   18 weak:           dmg -35%/lv (multiplier)
   19 posion:         def -25%/lv (addition)
   20 wither:         hurt (2% + 2)/lv until dead
   21 heal boost:     max hp +35%/lv (multiplier)
   22 absorption:     def +20%/lv (addition)
   23 saturation:     grudge and morale +N/lv every x ticks
   24 glowing:        no effect
   25 levitation:     dodge +15%/lv (addition) AA +25%/lv (multiplier)
   26 luck:           cri/dhit/thit +15%/lv (addition)
   27 bad luck:       cri/dhit/thit -15%/lv (addition)
 *
 */
public class EffectHelper
{
	
	
	public EffectHelper() {}
	
	/**
	 * update potion effect on ship
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> void updateEffectBuffs(T host)
	{
		//add potion to buff map
		convertPotionToBuffMap(host);
		
		//add buff to attributes
		applyAttrsBuff(host);
	}
	
	/**
	 * convert potion effect to buff map
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> void convertPotionToBuffMap(T host)
	{
		HashMap<Byte, Byte> buffmap = new HashMap<Byte, Byte>();
		
		//potion effects to ship buffs
    	for (PotionEffect potioneffect : host.getActivePotionEffects())
        {
    		buffmap.put((byte)Potion.getIdFromPotion(potioneffect.getPotion()),
    					(byte)potioneffect.getAmplifier());
        }
    	
    	host.setBuffMap(buffmap);
	}
	
	/**
	 * apply some buff to ship's attributes
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> void applyAttrsBuff(T host)
	{
		HashMap<Byte, Byte> buffmap = host.getBuffMap();
		float[] rawstate = host.getStateFinal();
		float[] raweffect = host.getEffectEquip();
		float[] modstate = new float[rawstate.length];
		float[] modeffect = new float[raweffect.length];
		
		//check all buffs
		buffmap.forEach((id, amp) ->
		{
			//limit buff level to 1~5
			int lv = amp > 4 ? 5 : amp < 0 ? 1 : amp + 1;
			
			switch(id)
			{
			case 1:  //speed: mov +15%
				modstate[ID.MOV] += 0.15F * lv;
			break;
			case 2:  //slow: mov -35%
				modstate[ID.MOV] += -0.35F * lv;
			break;
			case 3:  //haste: aspd +15%
				modstate[ID.SPD] += 0.15F * lv;
			break;
			case 4:  //mining fatigue: aspd -35%
				modstate[ID.SPD] += -0.35F * lv;
			break;
			case 5:  //strength: dmg +15%
				modstate[ID.ATK] += 0.15F * lv;
			break;
			case 8:  //jump: range +20%
				modstate[ID.HIT] += 0.2F * lv;
			break;
			case 13: //water breath: dodge +15% (add) ASM +25%
				modeffect[ID.EquipEffect.DODGE] += 0.15F * lv;
				modeffect[ID.EquipEffect.ASM] += 0.25F * lv;
			break;
			case 15: //blind: range -80% (fixed level 1)
				modstate[ID.HIT] += -0.8F;
			break;
			case 18: //weak: dmg -35%
				modstate[ID.ATK] += -0.35F * lv;
			break;
			case 19: //poison: def -25%
				modstate[ID.DEF] += -0.25F * lv;
			break;
			case 21: //heal boost: hp +35%
				modstate[ID.HP] += 0.35F * lv;
			break;
			case 22: //absorb: def +20%
				modstate[ID.DEF] += 0.2F * lv;
			break;
			case 25: //levitation: dodge +15% (add) AA +25%
				modeffect[ID.EquipEffect.DODGE] += 0.15F * lv;
				modeffect[ID.EquipEffect.AA] += 0.25F * lv;
			break;
			case 26: //luck: cri/dhit/thit +15%
				modeffect[ID.EquipEffect.CRI] += 0.15F * lv;
				modeffect[ID.EquipEffect.DHIT] += 0.15F * lv;
				modeffect[ID.EquipEffect.THIT] += 0.15F * lv;
			break;
			case 27: //bad luck: cri/dhit/thit -15%
				modeffect[ID.EquipEffect.CRI] += -0.15F * lv;
				modeffect[ID.EquipEffect.DHIT] += -0.15F * lv;
				modeffect[ID.EquipEffect.THIT] += -0.15F * lv;
			break;
			}
		});  //end map foreach
		
		//apply multiplier buff
		//HP
		modstate[ID.HP] += 1F;
		rawstate[ID.HP] *= modstate[ID.HP];
		if (rawstate[ID.HP] < 4F) rawstate[ID.HP] = 4F;
		//ATK: only 1 multiplier
		modstate[ID.ATK] += 1F;
		rawstate[ID.ATK] *= modstate[ID.ATK];
		rawstate[ID.ATK_H] *= modstate[ID.ATK];
		rawstate[ID.ATK_AL] *= modstate[ID.ATK];
		rawstate[ID.ATK_AH] *= modstate[ID.ATK];
		if (rawstate[ID.ATK] < 1F) rawstate[ID.ATK] = 1F;
		if (rawstate[ID.ATK_H] < 1F) rawstate[ID.ATK_H] = 1F;
		if (rawstate[ID.ATK_AL] < 1F) rawstate[ID.ATK_AL] = 1F;
		if (rawstate[ID.ATK_AH] < 1F) rawstate[ID.ATK_AH] = 1F;
		//SPD
		modstate[ID.SPD] += 1F;
		rawstate[ID.SPD] *= modstate[ID.SPD];
		if (rawstate[ID.SPD] < 0.25F) rawstate[ID.SPD] = 0.25F;
		//MOV
		modstate[ID.MOV] += 1F;
		rawstate[ID.MOV] *= modstate[ID.MOV];
		if (rawstate[ID.MOV] < 0F) rawstate[ID.MOV] = 0F;
		//HIT
		modstate[ID.HIT] += 1F;
		rawstate[ID.HIT] *= modstate[ID.HIT];
		if (rawstate[ID.HIT] < 1F) rawstate[ID.HIT] = 1F;
		//AA
		modeffect[ID.EquipEffect.AA] += 1F;
		raweffect[ID.EquipEffect.AA] *= modeffect[ID.EquipEffect.AA];
		if (raweffect[ID.EquipEffect.AA] < 0F) raweffect[ID.EquipEffect.AA] = 0F;
		//ASM
		modeffect[ID.EquipEffect.ASM] += 1F;
		raweffect[ID.EquipEffect.ASM] *= modeffect[ID.EquipEffect.ASM];
		if (raweffect[ID.EquipEffect.ASM] < 0F) raweffect[ID.EquipEffect.ASM] = 0F;
		
		//apply addition buff
		//DEF
		rawstate[ID.DEF] += modstate[ID.DEF];
		if (rawstate[ID.DEF] < 0F) rawstate[ID.DEF] = 0F;
		//CRI
		raweffect[ID.EquipEffect.CRI] += modeffect[ID.EquipEffect.CRI];
		if (raweffect[ID.EquipEffect.CRI] < 0F) raweffect[ID.EquipEffect.CRI] = 0F;
		//DHIT
		raweffect[ID.EquipEffect.DHIT] += modeffect[ID.EquipEffect.DHIT];
		if (raweffect[ID.EquipEffect.DHIT] < 0F) raweffect[ID.EquipEffect.DHIT] = 0F;
		//THIT
		raweffect[ID.EquipEffect.THIT] += modeffect[ID.EquipEffect.THIT];
		if (raweffect[ID.EquipEffect.THIT] < 0F) raweffect[ID.EquipEffect.THIT] = 0F;
		//DODGE
		raweffect[ID.EquipEffect.DODGE] += modeffect[ID.EquipEffect.DODGE];
		if (raweffect[ID.EquipEffect.DODGE] < 0F) raweffect[ID.EquipEffect.DODGE] = 0F;
		
		//update new attrs to host
		host.setStateFinal(rawstate);
		host.setEffectEquip(raweffect);
		
	}
	
	/**
	 * apply ticking buff to ship: regen/wither...
	 * call this method every x ticks (default: 32)
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> void applyTicksBuff(T host)
	{
		//get buff map
		HashMap<Byte, Byte> buffmap = host.getBuffMap();
		
		//get host's 1% hp
		float hp1p = host.getMaxHealth() * 0.01F;
		if (hp1p < 1F) hp1p = 1F;
		
		//check all buffs
		/**
		 * NOTE: eclipse's content assist is bug in lambda + switch now
		 *       (heavily lag, cpu get to 100%)
		 */
		for (Map.Entry<Byte, Byte> entry : buffmap.entrySet())
		{
			int id = (int) entry.getKey();
			int amp = (int) entry.getValue();
			//limit buff level to 1~5
			int lv = amp > 4 ? 5 : amp < 0 ? 1 : amp + 1;
			
			switch(id)
			{
			case 10: //regen: heal 2% + 2 every ticking
				host.heal(hp1p * 2F + 2F);
			break;
			case 20: //wither: hurt 2% + 2 until dead
				host.attackEntityFrom(DamageSource.magic, hp1p * 2F + 2F);
			break;
			}
		}
	}
		
	
}