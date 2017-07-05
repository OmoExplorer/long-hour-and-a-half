package omo;

import java.io.Serializable;
import static omo.Bladder.*;
import static omo.NarrativeEngine.*;
import omo.stage.Stage;
import static omo.stage.StageEngine.*;

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
    String boyName;

    void restoreValues()
    {
        setUndies(this.underwear);
        setLower(this.outerwear);
        setEmbarassment(this.embarassment);
        setDryness(this.dryness);
        setMaxSphincterPower(this.maxSphincterPower);
        setSphincterPower(this.sphincterPower);
        setTime(this.time);
        rotatePlot(this.stage);
        setScore(this.score);
        setScoreText(this.scoreText);
        setTimesPeeDenied(this.timesPeeDenied);
        NarrativeEngine.timesCaught = this.timesCaught;
        NarrativeEngine.classmatesAwareness = this.classmatesAwareness;
        NarrativeEngine.stay = this.stay;
        setCornered(this.cornered);
        setDrain(this.drain);
        NarrativeEngine.boyName = this.boyName;
    }
}
