package omo;

import java.io.Serializable;
import omo.stage.Stage;

/**
 * All game values storage for saving into a file.
 *
 * @author JavaBird
 */
@SuppressWarnings("PackageVisibleField")
public class Save implements Serializable //"implements Serializable" is required for saving into a file
{
    private static final long serialVersionUID = 1L;
    
    String name; //Already has been given to the constructor
    boolean hardcore; //Already has been given to the constructor
    Gender gender; //Already has been given to the constructor
    short bladder; //Already has been given to the constructor
    float incontinence; //Already has been given to the constructor
    short belly; //Already has been given to the constructor
    short embarassment;
    float dryness;
    short maxSphincterPower;
    short sphincterPower;
    Wear underwear;
    Wear outerwear;
    byte time;
    Stage stage;
    int score;
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
