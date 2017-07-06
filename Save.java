package omo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static omo.Bladder.*;
import static omo.NarrativeEngine.*;
import omo.stage.Stage;
import static omo.stage.StageEngine.*;
import omo.ui.GameFrame;
import omo.ui.GameSaveFileChooser;

/**
 * All game values storage for saving into a file.
 *
 * @author JavaBird
 */
@SuppressWarnings("PackageVisibleField")
public class Save implements Serializable //"implements Serializable" is required for saving into a file
{
    private static final long serialVersionUID = 1L;

    /**
     * Reads the game values and applies them.
     *
     * @param ui {@link GameFrame} object to show the file selector dialog relative to.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void load(GameFrame ui)
    {
        GameSaveFileChooser fcGame = new GameSaveFileChooser();
        if (fcGame.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION)
        {
            File file = fcGame.getSelectedFile();
            try
            {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fin);
                Save save = (Save) ois.readObject();
                new GameCore(save, ui);
                ui.dispose();
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Creates new {@link Save} object, writes current game values into it and
     * saves an object into a file.
     *
     * @param ui {@link GameFrame} object to show the file selector dialog
     * relative to.
     * @see Save
     */
    public static void save(GameFrame ui)
    {
        GameSaveFileChooser fcGame = new GameSaveFileChooser();
        fcGame.setSelectedFile(new File(getName()));
        if (fcGame.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fcGame.getSelectedFile().getAbsolutePath() + ".lhhsav");
            //            PrintStream writer;
            FileOutputStream fout;
            ObjectOutputStream oos;
            try
            {
                Save save = new Save();
                save.name = getName();
                save.gender = NarrativeEngine.gender;
                save.hardcore = isHardcore(); //TODO
                save.incontinence = getIncontinence();
                save.bladder = getFulness();
                save.underwear = getUndies();
                save.outerwear = getLower();
                save.embarassment = getEmbarassment();
                save.dryness = getDryness();
                save.maxSphincterPower = getMaxSphincterPower();
                save.sphincterPower = getSphincterPower();
                save.time = getTime();
                save.stage = getCurrentStage();
                save.score = NarrativeEngine.getScore();
                save.scoreText = NarrativeEngine.getScoreText();
                save.timesPeeDenied = NarrativeEngine.getTimesPeeDenied();
                save.timesCaught = NarrativeEngine.timesCaught;
                save.classmatesAwareness = NarrativeEngine.classmatesAwareness;
                save.stay = NarrativeEngine.stay;
                save.cornered = NarrativeEngine.isCornered();
                save.boyName = NarrativeEngine.boyName;
                //                writer = new PrintStream(file);
                fout = new FileOutputStream(file);
                oos = new ObjectOutputStream(fout);
                oos.writeObject(save);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
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
        NarrativeEngine.boyName = this.boyName;
    }
}
