package com.lulan.shincolle.entity;

/**
 * entity with floating AI
 */
public interface IFloatingEntity
{
    
    /** depth value for floating AI (distance Y below fluid surface) */
    double getEntityDepth();
    void setEntityDepth(double par1);
    
    /** the min depth when floating */
    double getEntityFloatingDepth();
    void setEntityFloatingDepth(double par1);
    
}