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
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.*;
import static omo.Bladder.*;
import static omo.NarrativeEngine.*;
import static omo.ResetParametersStorage.*;
import omo.Wear.WearType;
import static omo.Wear.WearType.*;
import static omo.stage.StageEngine.*;
import omo.stage.StagePool;
import omo.ui.GameFrame;
import omo.ui.GameSaveFileChooser;
import omo.ui.SetupFrame;
import omo.ui.WearFileChooser;

/**
 * Provides the basic game functions.
 *
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
    
    public final static StagePool STAGE_POOL = StagePool.getInstance();
    
    /**
     * Resets the game and values, optionally letting player to select new
     * parameters.
     *
     * @param newValues
     * @param ui {@link GameFrame} object to update the interface
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void reset(boolean newValues, GameFrame ui)
    {
        if (newValues)
        {
            new SetupFrame().setVisible(true);
            ui.dispose();
        }
        else
        {
            new GameCore(ResetParametersStorage.nameParam, ResetParametersStorage.gndrParam, ResetParametersStorage.diffParam, ResetParametersStorage.incParam, ResetParametersStorage.bladderParam, ResetParametersStorage.underParam, ResetParametersStorage.outerParam, ResetParametersStorage.underColorParam, ResetParametersStorage.outerColorParam, new GameFrame());
        }
    }

    /**
     * Compares given file's extension with a given one.
     *
     * @param pathname File name which extension to compare
     * @param compareExtension extension to compare
     * @return <code>true</code> if extensions are equal, <code>false</code>
     * otherwise
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

        if (under.equals(Wear.CUSTOM_WEAR))
        {
            openWearFile(ui, UNDERWEAR);
        }

        if (under.equals(Wear.RANDOM_WEAR))
        {
            setRandomWear(UNDERWEAR);
        }
        else
        {
            assignSelectedWear(under, UNDERWEAR);
        }
        //If the selected undies weren't found
        if (getUndies() == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect underwear selected. Setting random instead.", "Incorrect underwear", JOptionPane.WARNING_MESSAGE);
            setRandomWear(UNDERWEAR);
        }

        assignWearColor(undiesColor, UNDERWEAR);

        if (outer.equals(Wear.CUSTOM_WEAR))
        {
            openWearFile(ui, OUTERWEAR);
        }

        //Same with the lower clothes
        if (outer.equals(Wear.RANDOM_WEAR))
        {
            setRandomWear(OUTERWEAR);
        }
        else
        {
            assignSelectedWear(outer, OUTERWEAR);
        }
        if (getLower() == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect outerwear selected. Setting random instead.", "Incorrect outerwear", JOptionPane.WARNING_MESSAGE);
            setRandomWear(OUTERWEAR);
        }

        assignWearColor(lowerColor, OUTERWEAR);

        //Displaying all values
        ui.displayAllValues();

        initHardcoreMode(ui);
        
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
        save.restoreValues();
        ui.displayAllValues();
        postConstructor(ui);
    }

    private void assignWearColor(String wearColor, WearType type)
    {
        if (type == UNDERWEAR)
        {
            //Assigning color
            if (!getUndies().isMissing())
            {
                if (!wearColor.equals(Wear.RANDOM_WEAR))
                {
                    getUndies().setColor(wearColor);
                }
                else
                {
                    getUndies().setColor(Wear.COLOR_LIST[NarrativeEngine.RANDOM.nextInt(Wear.COLOR_LIST.length)]);
                }
            }
            else
            {
                getUndies().setColor("");
            }
        }
        if (type == OUTERWEAR)
        {
            //Assigning color
            if (!getLower().isMissing())
            {
                if (!wearColor.equals(Wear.RANDOM_WEAR))
                {
                    getLower().setColor(wearColor);
                }
                else
                {
                    getLower().setColor(Wear.COLOR_LIST[NarrativeEngine.RANDOM.nextInt(Wear.COLOR_LIST.length)]);
                }
            }
            else
            {
                getLower().setColor("");
            }
        }
    }

    private void assignSelectedWear(String wear, WearType type)
    {
        if (type == UNDERWEAR)
        {
            //We look for the selected undies in the array
            for (Wear iWear : UNDERWEAR_LIST)
            {
                //By comparing all possible undies' names with the selected undies string
                if (iWear.getName().equals(wear))
                {
                    //If the selected undies were found, assigning current compared undies to the character's undies
                    setUndies(iWear);
                    break;
                }
            }
        }
        if (type == OUTERWEAR)
        {
            //We look for the selected undies in the array
            for (Wear iWear : OUTERWEAR_LIST)
            {
                //By comparing all possible undies' names with the selected undies string
                if (iWear.getName().equals(wear))
                {
                    //If the selected undies were found, assigning current compared undies to the character's undies
                    setLower(iWear);
                    break;
                }
            }
        }
    }

    private void setRandomWear(WearType type)
    {
        if (type == UNDERWEAR)
        {
            do
            {
                setUndies(UNDERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(UNDERWEAR_LIST.length)]);
            }
            while (getUndies().getName().equals(Wear.RANDOM_WEAR));
        }
        if (type == OUTERWEAR)
        {
            do
            {
                setLower(OUTERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(OUTERWEAR_LIST.length)]);
            }
            while (getLower().getName().equals(Wear.RANDOM_WEAR));
        }
    }

    /**
     * Shows file chooser dialog, reads an {@link Wear} object from a selected
     * file, checks if its type is matching the given one and assigns it to
     * {@link lower}.
     *
     * @param ui {@code GameFrame} object
     */
    private void openWearFile(GameFrame ui, WearType type)
    {
        if (type == UNDERWEAR)
        {
            fcWear.setDialogTitle("Open an underwear file");
        }
        else
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
                if (type == UNDERWEAR)
                {
                    setUndies((Wear) ois.readObject());
                    if (getLower().getType() == OUTERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an underwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        ui.dispose();
                        SetupFrame.main(new String[0]);
                    }
                }
                else
                {
                    setLower((Wear) ois.readObject());
                    if (getLower().getType() == UNDERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an outerwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        ui.dispose();
                        SetupFrame.main(new String[0]);
                    }
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
                ui.dispose();
                SetupFrame.main(new String[0]);
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
        assignFieldValuesFromParameters(name, gndr, diff, inc, bladder);

        setupFileChoosers(ui);

        initCrotchName();

        ui.setup();

        scoreBeginning(bladder);
//        StageEngine.setNextStage(leaveBed);
    }

    private void setupFileChoosers(GameFrame ui)
    {
        fcWear = new WearFileChooser();
        fcGame = new GameSaveFileChooser();
    }

    private void assignFieldValuesFromParameters(String name1, Gender gndr, boolean diff, float inc, short bladder)
    {
        //Assigning constructor parameters to values
        NarrativeEngine.setName(name1);
        gender = gndr;
        setHardcore(diff);
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
        
        setFirstStage(STAGE_POOL.leaveBed);
        
        ui.handleNextClicked();

        ui.setVisible(true);
    }

    private void initHardcoreMode(GameFrame ui)
    {
        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (isHardcore())
        {
            Bladder.setMaxFulness((short) 100);
            ui.lblName.setName(ui.lblName.getName() + " [Hardcore]");
        }
        if (isHardcore())
        {
            score("Hardcore", '*', 2F);
        }
    }
}
