/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api.clothes.Underwear;
import api.clothes.Outerwear;

/**
 *
 * @author Jonisan
 */
public class Character
{
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the bladder
     */
    public float getBladder()
    {
        return bladder;
    }

    /**
     * @param bladder the bladder to set
     */
    public void setBladder(float bladder)
    {
        this.bladder = bladder;
    }

    /**
     * @return the affectedByTime
     */
    public boolean isAffectedByTime()
    {
        return affectedByTime;
    }

    /**
     * @param affectedByTime the affectedByTime to set
     */
    public void setAffectedByTime(boolean affectedByTime)
    {
        this.affectedByTime = affectedByTime;
    }

    /**
     * @return the incontinence
     */
    public float getIncontinence()
    {
        return incontinence;
    }

    /**
     * @param incontinence the incontinence to set
     */
    public void setIncontinence(float incontinence)
    {
        this.incontinence = incontinence;
    }

    /**
     * @return the bellyWater
     */
    public float getBellyWater()
    {
        return bellyWater;
    }

    /**
     * @param bellyWater the bellyWater to set
     */
    public void setBellyWater(float bellyWater)
    {
        this.bellyWater = bellyWater;
    }

    /**
     * @return the sphincterPower
     */
    public float getSphincterPower()
    {
        return sphincterPower;
    }

    /**
     * @param sphincterPower the sphincterPower to set
     */
    public void setSphincterPower(float sphincterPower)
    {
        this.sphincterPower = sphincterPower;
    }

    /**
     * @return the maxSphincterPower
     */
    public float getMaxSphincterPower()
    {
        return maxSphincterPower;
    }

    /**
     * @param maxSphincterPower the maxSphincterPower to set
     */
    public void setMaxSphincterPower(float maxSphincterPower)
    {
        this.maxSphincterPower = maxSphincterPower;
    }

    /**
     * @return the underwear
     */
    public Underwear getUnderwear()
    {
        return underwear;
    }

    /**
     * @param underwear the underwear to set
     */
    public void setUnderwear(Underwear underwear)
    {
        this.underwear = underwear;
    }

    /**
     * @return the outerwear
     */
    public Outerwear getOuterwear()
    {
        return outerwear;
    }

    /**
     * @param outerwear the outerwear to set
     */
    public void setOuterwear(Outerwear outerwear)
    {
        this.outerwear = outerwear;
    }
    
    private String name;
    private float bladder;
    private boolean affectedByTime;
    private float incontinence;
    private float bellyWater;
    private float sphincterPower;
    private float maxSphincterPower;
    private Underwear underwear;
    private Outerwear outerwear;
}
