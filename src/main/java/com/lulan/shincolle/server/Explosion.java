package com.lulan.shincolle.server;

import com.lulan.shincolle.handler.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by lyt on 2017/6/15.
 */

public class Explosion {
    /**
     * @param world  爆炸发生的世界
     * @param e      引发爆炸的实体，是船本身或者是船的炮弹
     * @param posX   爆炸位置X
     * @param posY   爆炸位置Y
     * @param posZ   爆炸位置Z
     * @param attack 初始攻击力
     * @param missed 是否为近失弹
     * @param limit  爆炸威力上限，某些船可能有系数相乘，基于config的给出
     */
    public static void ShipCannonExplosion(World world, Entity e, double posX, double posY, double posZ, double attack, boolean missed, float limit) {
        float power = ConfigHandler.ExplosionEnabled ? (float) (attack * ConfigHandler.PowerCoeff) : 0;
        if (power > 0) {
            power = power > limit ? limit : power;
            if (missed) {
                //近失弹
                //威力下降25%
                power = power * 0.75f;
                //坐标随即偏离
                posX += (-3 + 6 * world.rand.nextFloat());
                posZ += (-3 + 6 * world.rand.nextFloat());
            }
            if (!ConfigHandler.BlacklistedDims.contains(world.provider.dimensionId)) {
                //如果当前的世界允许爆炸
                //爆炸
                world.newExplosion(e, posX, posY, posZ, power, true, true);
            }
        }
    }
}
