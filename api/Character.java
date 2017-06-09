/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api.Scenes.WettingScene;
import api.clothes.Underwear;
import api.clothes.Outerwear;

/**
 *
 * @author Jonisan
 */
public class Character
{

    /**
     * @return the randomIncontinenceFrom
     */
    public short getRandomIncontinenceFrom()
    {
        return randomIncontinenceFrom;
    }

    /**
     * @param randomIncontinenceFrom the randomIncontinenceFrom to set
     */
    public void setRandomIncontinenceFrom(short randomIncontinenceFrom)
    {
        this.randomIncontinenceFrom = randomIncontinenceFrom;
    }

    /**
     * @return the randomIncontinenceTo
     */
    public short getRandomIncontinenceTo()
    {
        return randomIncontinenceTo;
    }

    /**
     * @param randomIncontinenceTo the randomIncontinenceTo to set
     */
    public void setRandomIncontinenceTo(short randomIncontinenceTo)
    {
        this.randomIncontinenceTo = randomIncontinenceTo;
    }

    /**
     * @param definedIncontinenceFrom the definedIncontinenceFrom to set
     */
    public void setDefinedIncontinenceFrom(short definedIncontinenceFrom)
    {
        this.definedIncontinenceFrom = definedIncontinenceFrom;
    }

    /**
     * @param definedIncontinenceTo the definedIncontinenceTo to set
     */
    public void setDefinedIncontinenceTo(short definedIncontinenceTo)
    {
        this.definedIncontinenceTo = definedIncontinenceTo;
    }

    /**
     * @param definedBladderFrom the definedBladderFrom to set
     */
    public void setDefinedBladderFrom(short definedBladderFrom)
    {
        this.definedBladderFrom = definedBladderFrom;
    }

    /**
     * @param definedBladderTo the definedBladderTo to set
     */
    public void setDefinedBladderTo(short definedBladderTo)
    {
        this.definedBladderTo = definedBladderTo;
    }

    /**
     * @param randomBladderFrom the randomBladderFrom to set
     */
    public void setRandomBladderFrom(short randomBladderFrom)
    {
        this.randomBladderFrom = randomBladderFrom;
    }

    /**
     * @param randomBladderTo the randomBladderTo to set
     */
    public void setRandomBladderTo(short randomBladderTo)
    {
        this.randomBladderTo = randomBladderTo;
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
    private WettingScene wettingScene;
    private boolean allowParametersSelection;
    private boolean canDefineBladder;
    private short randomBladderFrom;
    private short randomBladderTo;
    private short definedBladderFrom;
    private short definedBladderTo;
    private boolean allowRandomBladder;
    private boolean canDefineIncontinence;
    private short randomIncontinenceFrom;
    private short randomIncontinenceTo;
    private short definedIncontinenceFrom;
    private short definedIncontinenceTo;
    private boolean allowRandomIncontinence;
    private boolean forceName;

    /**
     * @param wettingScene the character's wetting scene to set
     */
    public void setWettingScene(WettingScene wettingScene)
    {
        this.wettingScene = wettingScene;
    }

    /**
     * @param allowParametersSelection the allowParametersSelection to set
     */
    public void allowParametersSelection(boolean allowParametersSelection)
    {
        this.allowParametersSelection = allowParametersSelection;
    }

    /**
     * @param canDefineBladder whether to allow player to define bladder fulness
     */
    public void canDefineBladder(boolean canDefineBladder)
    {
        this.canDefineBladder = canDefineBladder;
    }

    /**
     * @param allowRandomBladder the allowRandomBladder to set
     */
    public void allowRandomBladder(boolean allowRandomBladder)
    {
        this.allowRandomBladder = allowRandomBladder;
    }

    /**
     * @param canDefineIncontinence the canDefineIncontinence to set
     */
    public void canDefineIncontinence(boolean canDefineIncontinence)
    {
        this.canDefineIncontinence = canDefineIncontinence;
    }

    /**
     * @param allowRandomIncontinence the allowRandomIncontinence to set
     */
    public void allowRandomIncontinence(boolean allowRandomIncontinence)
    {
        this.allowRandomIncontinence = allowRandomIncontinence;
    }

    /**
     * @param forcedName
     */
    public void forceName(String forcedName)
    {
        forceName = true;
        name = forcedName;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }
}
