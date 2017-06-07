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
class ALongHourAndAHalfSave implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String name;
    private boolean hardcore;
    private Gender gender;
    private float bladder;
    private float incontinence;
    private float belly;
    private byte embarassment;
    private byte dryness;
    private byte maxSphincterPower;
    private byte sphincterPower;
    private Wear underwear;
    private Wear outerwear;
    private byte time;
    private GameStage stage;
    private short score;
    private String scoreText;
    private byte timesPeeDenied;
    private byte timesCaught;
    private short classmatesAwareness;
    private boolean stay;
}
