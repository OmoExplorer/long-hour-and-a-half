/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omo;

import java.io.Serializable;
import omo.ALongHourAndAHalf.GameStage;
import omo.ALongHourAndAHalf.Gender;

/**
 *
 * @author JavaBird
 */
class Save implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    String name;
    boolean hardcore;
    Gender gender;
    float bladder;
    float incontinence;
    float belly;
    byte embarassment;
    byte dryness;
    byte maxSphincterPower;
    byte sphincterPower;
    Wear underwear;
    Wear outerwear;
    byte time;
    GameStage stage;
    short score;
    String scoreText;
    byte timesPeeDenied;
    byte timesCaught;
    short classmatesAwareness;
    boolean stay;
    boolean cornered;
    boolean drain;
    boolean cheatsUsed;
    String boyName;
}
