/*
*ALongHourAndAHalf Vers. 1.4.1
*
*Dev: Rosalie Elodie, JavaBird
*
*Version History:
*0.1    Default game mechanics shown, non interactable. No playability, no customization. Not all game mechanics even implemented, purely a showcase program.
*0.2    MASSIVE REWRITE! (Thanks to Anna May! This is definitely the format I want!)
*1.0    Added interactivity, improved code, added hardcore mode(this isn't working now) and... cheats!
*1.1    Reintegrated the two versions
*1.2    New hardcore features:
*           Classmates can be aware that you've to go
*           Less bladder capacity
*       Improved bladder: it's more realistic now
*       Balanced pee holding methods
*       New game options frame
*       Even more clothes
*       Cleaned and documented code a bit
*1.3    Bug fixes
*       Interface improvements
*       Game text refining
*1.3.1  Bug fixes
*1.4    Character can drink during the class
*       Saving/loading games
*       Bug fixes
*1.4.1  Bug fixes
*
*A Long Hour and a Half (ALongHourAndAHalf) is a game where
*one must make it through class with a rather full bladder.
*This game will be more of a narrative game, being extremely text based,
*but it will have choices that can hurt and help your ability to hold.
*Some randomization elements are going to be in the game, but until completion,
*it's unknown how many.
*
*Many options are already planned for full release, such as:
*Name (friends and teacher may say it. Also heard in mutterings if an accident occurs)
*Male and Female (only affects gender pronouns (yes, that means crossdressing's allowed!))
*Random bladder amount upon awaking (Or preset)
*Choice of clothing (or, if in a rush, random choice of clothing (will be "gender conforming" clothing)
*Ability to add positives (relative to holding capabilities)
*Ability to add negatives (relative to holding capabilities)
*Called upon in class if unlucky (every 15 minutes)
*Incontinence (continence?) level selectable (multiplier basis. Maybe just presets, but having ability to choose may also be nice).
*
*
*Other options, which may be added in later or not, are these:
*Extended game ("Can [name] get through an entire school day AND make it home?") (probably will be in the next update)
*Better Dialog (lines made by someone that's not me >_< )
*Story editor (players can create their own stories and play them)
*Wear editor (players can create their own wear types and use it in A Long hour and a Half and custom stories
*Save/load game states
*Character presets
*
*
*If you have any questions, bugs or suggestions, 
*create an issue or a pull request on GitHub:
*https://github.com/javabird25/long-hour-and-a-half/
*
*Developers' usernames table
*   Code documentation  |GitHub                                      |Omorashi.org
*   --------------------|-------------------------------------------|---------------------------------------------------------------------
*   Rosalie Elodie      |REDev987532(https://github.com/REDev987532) |Justice (https://www.omorashi.org/profile/25796-justice/)
*   JavaBird            |javabird25 (https://github.com/javabird25)  |FromRUSForum (https://www.omorashi.org/profile/89693-fromrusforum/)
*   Anna May            |AnnahMay (https://github.com/AnnahMay)      |Anna May (https://www.omorashi.org/profile/10087-anna-may/)
*   notwillnotcast      |?                                           |notwillnotcast (https://www.omorashi.org/profile/14935-notwillnotcast/)
*   
*FINAL NOTE: While this is created by Rosalie Dev, she allows it to be posted
*freely, so long as she's creditted. She also states that this program is
*ABSOLUTELY FREE, not to mention she hopes you enjoy ^_^
*
*
*DEV NOTES: Look for bugs, there is always a bunch of them
 */
package omo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import static omo.Bladder.*;
import static omo.NarrativeEngine.*;
import static omo.NarrativeEngine.GameStage.*;
import omo.Wear.WearType;
import static omo.Wear.WearType.*;
import omo.ui.GameFrame;
import omo.ui.GameSaveFileChooser;
import omo.ui.WearFileChooser;
import omo.ui.setupFramePre;

/**
 * Provides the basic game functions.
 * @author JavaBird
 */
@SuppressWarnings("serial")
public class GameCore
{
    /**
	 * JFileChooser object for picking wear files.
	 */
    private static WearFileChooser fcWear;
    
    /**
	 * JFileChooser object for picking save files.
	 */
    private static GameSaveFileChooser fcGame;
    
    /**
     * Resets the game and values, optionally letting player to select new
     * parameters.
     *
     * @param newValues
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void reset(boolean newValues)
    {
        if (newValues)
        {
            new setupFramePre().setVisible(true);
        } else
        {
            new GameCore(ResetParametersStorage.nameParam, ResetParametersStorage.gndrParam, ResetParametersStorage.diffParam, ResetParametersStorage.incParam, ResetParametersStorage.bladderParam, ResetParametersStorage.underParam, ResetParametersStorage.outerParam, ResetParametersStorage.underColorParam, ResetParametersStorage.outerColorParam, new GameFrame());
        }
    }

    /**
     * Creates new {@link Save} object, writes current game values into it and saves an object into a file.
     * @param ui {@link GameFrame} object to show the file selector dialog relative to.
     * @see Save
     */
    public static void save(GameFrame ui)
    {
        fcGame.setSelectedFile(new File(name));
        if (fcGame.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fcGame.getSelectedFile().getAbsolutePath() + ".lhhsav");
//            PrintStream writer;
            FileOutputStream fout;
            ObjectOutputStream oos;
            try
            {
                Save save = new Save();
                save.name = NarrativeEngine.name;
                save.gender = NarrativeEngine.gender;
                save.hardcore = NarrativeEngine.hardcore; //TODO
                save.incontinence = Bladder.getIncontinence();
                save.bladder = Bladder.getFulness();
                save.underwear = Bladder.getUndies();
                save.outerwear = Bladder.getLower();
                save.embarassment = Bladder.getEmbarassment();
                save.dryness = Bladder.getDryness();
                save.maxSphincterPower = Bladder.getMaxSphincterPower();
                save.sphincterPower = Bladder.getSphincterPower();
                save.time = Bladder.getTime();
                save.stage = NarrativeEngine.getNextStage();
                save.score = NarrativeEngine.score;
                save.scoreText = NarrativeEngine.scoreText;
                save.timesPeeDenied = NarrativeEngine.getTimesPeeDenied();
                save.timesCaught = NarrativeEngine.timesCaught;
                save.classmatesAwareness = NarrativeEngine.classmatesAwareness;
                save.stay = NarrativeEngine.stay;
                save.cornered = NarrativeEngine.isCornered();
                save.drain = NarrativeEngine.drain;
                save.cheatsUsed = NarrativeEngine.cheatsUsed;
                save.boyName = NarrativeEngine.boyName;

//                writer = new PrintStream(file);
                fout = new FileOutputStream(file);
                oos = new ObjectOutputStream(fout);
                oos.writeObject(save);
            } catch (IOException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /**
     * Opens a Save} object, reads game values and applies them.
     *
     * @param ui GameFrame} object to show the file selector dialog relative to.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void load(GameFrame ui)
    {
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
            } catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
                showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Compares given file's extension with a given one.
     *
     * @param pathname File name which extension to compare
     * @param compareExtension extension to compare
     * @return <code>true</code> if extensions are equal, <code>false</code> otherwise
     */
    public static boolean isExtensionEquals(File pathname, String compareExtension)
    {
        String fileExtension = "";
        int i = pathname.getName().lastIndexOf('.');
        if (i > 0)
        {
            fileExtension = pathname.getName().substring(i + 1);
        }
        return fileExtension.equals(compareExtension);
    }

    /**
     * New game constructor.
     *
     * @param name character's name
     * @param gndr character's gender
     * @param diff hardcore mode
     * @param inc character's incontinence
     * @param bladder character's start bladder fulness
     * @param under character's undies
     * @param outer character's lower wear
     * @param undiesColor character undies' color
     * @param lowerColor character lower wear's color
     * @param ui GameFrame} object to assign values to.
     */
    public GameCore(String name, Gender gndr, boolean diff, float inc, short bladder, String under, String outer, String undiesColor, String lowerColor, GameFrame ui)
    {
        preConstructor(name, gndr, diff, inc, bladder, ui);

        if (under.equals("Custom"))
        {
            openWearFile(ui, UNDERWEAR);
        }

        if (under.equals("Random"))
        {
            setUndies(UNDERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(UNDERWEAR_LIST.length)]);
            while (getUndies().getName().equals("Random"))
            //...selecting random undies from the undies array.
            {
                setUndies(UNDERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(UNDERWEAR_LIST.length)]);
            }
            //If random undies weren't chosen...
        } else
        {
            //We look for the selected undies in the array
            for (Wear iWear : UNDERWEAR_LIST)
            {
                //By comparing all possible undies' names with the selected undies string
                if (iWear.getName().equals(under))
                {
                    //If the selected undies were found, assigning current compared undies to the character's undies
                    setUndies(iWear);
                    break;
                }
            }
        }
        //If the selected undies weren't found
        if (getUndies() == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect underwear selected. Setting random instead.", "Incorrect underwear", JOptionPane.WARNING_MESSAGE);
            setUndies(UNDERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(UNDERWEAR_LIST.length)]);
        }

        //Assigning color
        if (!getUndies().isMissing())
        {
            if (!undiesColor.equals("Random"))
            {
                getUndies().setColor(undiesColor);
            } else
            {
                getUndies().setColor(Wear.COLOR_LIST[NarrativeEngine.RANDOM.nextInt(Wear.COLOR_LIST.length)]);
            }
        } else
        {
            getUndies().setColor("");
        }

        if (outer.equals("Custom"))
        {
            openWearFile(ui, OUTERWEAR);
        }

        //Same with the lower clothes
        if (outer.equals("Random"))
        {
            setLower(Bladder.OUTERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(Bladder.OUTERWEAR_LIST.length)]);
            while (getLower().getName().equals("Random"))
            {
                setLower(Bladder.OUTERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(Bladder.OUTERWEAR_LIST.length)]);
            }
        } else
        {
            for (Wear iWear : Bladder.OUTERWEAR_LIST)
            {
                if (iWear.getName().equals(outer))
                {
                    setLower(iWear);
                    break;
                }
            }
        }
        if (getLower() == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect outerwear selected. Setting random instead.", "Incorrect outerwear", JOptionPane.WARNING_MESSAGE);
            setLower(Bladder.OUTERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(Bladder.OUTERWEAR_LIST.length)]);
        }

        //Assigning color
        if (!getLower().isMissing())
        {
            if (!lowerColor.equals("Random"))
            {
                getLower().setColor(lowerColor);
            } else
            {
                getLower().setColor(Wear.COLOR_LIST[NarrativeEngine.RANDOM.nextInt(Wear.COLOR_LIST.length)]);
            }
        } else
        {
            getLower().setColor("");
        }

        //Displaying all values
        ui.displayAllValues();

        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore)
        {
            setMaxFulness((short) 100);
            ui.lblName.setText(ui.lblName.getText() + " [Hardcore]");
        }

        //Starting the game
        setNextStage(LEAVE_BED);

        postConstructor(ui);
    }
    
    /**
     * Saved game restore constructor.
     *
     * @param save Save} object to retrieve values from
     * @param ui GameFrame} object to assign values to
     */
    public GameCore(Save save, GameFrame ui)
    {
        preConstructor(save.name, save.gender, save.hardcore, save.incontinence, save.bladder, ui);
        setUndies(save.underwear);
        setLower(save.outerwear);
        setEmbarassment(save.embarassment);
        setDryness(save.dryness);
        setMaxSphincterPower(save.maxSphincterPower);
        setSphincterPower(save.sphincterPower);
        setTime(save.time);
        setNextStage(save.stage);
        score = save.score;
        scoreText = save.scoreText;
        setTimesPeeDenied(save.timesPeeDenied);
        timesCaught = save.timesCaught;
        classmatesAwareness = save.classmatesAwareness;
        stay = save.stay;
        setCornered(save.cornered);
        drain = save.drain;
        cheatsUsed = save.cheatsUsed;
        boyName = save.boyName;
        
        ui.displayAllValues();
        postConstructor(ui);
    }

    /**
     * Shows file chooser dialog, reads an {@link Wear} object from a selected file, checks if its type is matching the given one and assigns it to {@link lower}.
     *
     * @param ui {@code GameFrame} object
     */
    private void openWearFile(GameFrame ui, WearType type)
    {
        if(type == UNDERWEAR)
        {
            fcWear.setDialogTitle("Open an underwear file");
        } else
        {
            fcWear.setDialogTitle("Open an outerwear file");
        }
        if (fcWear.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File file = fcWear.getSelectedFile();
            try
            {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fin);
                if(type == UNDERWEAR)
                {
                    setUndies((Wear) ois.readObject());
                    if (getLower().getType() == OUTERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an underwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        ui.dispose();
                        setupFramePre.main(new String[0]);
                    }
                }
                else
                {
                    setLower((Wear) ois.readObject());
                    if (getLower().getType() == UNDERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an outerwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        ui.dispose();
                        setupFramePre.main(new String[0]);
                    }
                }
            } catch (IOException | ClassNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
                ui.dispose();
                setupFramePre.main(new String[0]);
            }
        }
    }

    //TODO: Refactor
    /**
     * Launch the application.
     *
     * @param name the name of a character
     * @param gndr the gender of a character
     * @param diff the game difficulty - Normal or Hardcore
     * @param bladder the bladder fullness at start
     * @param under the underwear
     * @param outer the outerwear
     * @param inc the incontinence
     * @param undiesColor the underwear color
     * @param lowerColor the outerwear color
     */
    void preConstructor(String name, Gender gndr, boolean diff, float inc, short bladder, GameFrame ui)
    {
        stashParametersForReset();

        assignFieldValuesFromParameters(name, gndr, diff, inc, bladder);

        setupFileChoosers(ui);

        initCrotchName();

        ui.setup();

        scoreBeginning(bladder);
    }

    private void setupFileChoosers(GameFrame ui)
    {
        fcWear = new WearFileChooser();
        fcGame = new GameSaveFileChooser();
    }

    private void assignFieldValuesFromParameters(String name1, Gender gndr, boolean diff, float inc, short bladder)
    {
        //Assigning constructor parameters to values
        NarrativeEngine.name = name1;
        gender = gndr;
        hardcore = diff;
        setIncontinence(inc);
        setFulness(bladder);
        setMaxSphincterPower((short) Math.round(100 / getIncontinence()));
        setSphincterPower(getMaxSphincterPower());
        //Assigning the boy's name
        boyName = BOY_NAMES[NarrativeEngine.RANDOM.nextInt(BOY_NAMES.length)];
    }

    void postConstructor(GameFrame ui)
    {
        calculateCaps(ui);

        stashParametersForReset();

        ui.setupWearLabels();

        initHardcoreMode(ui);

        ui.handleNextClicked();

        //Displaying the frame
        ui.setVisible(true);
    }

    private void initHardcoreMode(GameFrame ui)
    {
        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore)
        {
            Bladder.setMaxFulness((short) 100);
            ui.lblName.setName(ui.lblName.getName() + " [Hardcore]");
        }
        if (hardcore)
        {
            score("Hardcore", '*', 2F);
        }
    }

    private void stashParametersForReset()
    {
        //TODO
        ResetParametersStorage.nameParam = NarrativeEngine.name;
        ResetParametersStorage.gndrParam = NarrativeEngine.gender;
        ResetParametersStorage.incParam = Bladder.getIncontinence();
        ResetParametersStorage.bladderParam = Bladder.getFulness();
        ResetParametersStorage.outerParam = getLower().getName();
        ResetParametersStorage.underParam = getUndies().getName();
        ResetParametersStorage.underColorParam = getUndies().getColor();
        ResetParametersStorage.outerColorParam = getLower().getColor();
    }

}
