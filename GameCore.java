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

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import static omo.Bladder.*;
import static omo.Gender.*;
import static omo.NarrativeEngine.*;
import omo.NarrativeEngine.GameStage;
import static omo.NarrativeEngine.GameStage.*;
import static omo.UI.*;
import static omo.Wear.WearType.*;

class Action
{
    private String name;
    private Stage actionStage;

    Action(String name, Stage actionStage)
    {
        this.name = name;
        this.actionStage = actionStage;
    }

    Action(String name)
    {
        this.name = name;
    }
}

//TODO
class HoldCrotchStage extends Stage
{
    HoldCrotchStage(Stage nextStage)
    {
        super(nextStage, new String[]
        {
            "You don't think anyone will see you doing it,",
            "so you take your hand and hold yourself down there.",
            "It feels a little better for now."
        });
    }

    @Override
    void operate(UI ui)
    {
        Bladder.rechargeSphPower(ui, 20);
        Bladder.offsetTime(ui, 3);
        NarrativeEngine.getCaughtByClassmates();
    }
}

class RubThigsStage extends Stage
{
    RubThigsStage(Stage nextStage)
    {
        super(nextStage, new String[]
        {
            "You need to go, and it hurts, but you just",
            "can't bring yourself to risk getting caught with your hand between",
            "your legs. You rub your thighs hard but it doesn't really help."
        });
    }

    @Override
    void operate(UI ui)
    {
        Bladder.rechargeSphPower(ui, 2);
        Bladder.offsetTime(ui, 3);
        NarrativeEngine.getCaughtByClassmates();
    }
}

class CheatStage extends SelectionStage
{
    CheatStage(ArrayList<omo.Action> actions, short duration, String... text)
    {
        super(actions, duration, text);
        addAction(new Action("Pee in a bottle"));
        addAction(new Action("Go to extended game"));
        //TODO: Add more cheats
    }
}

/**
 * Describes an underwear of an outerwear of a character.
 *
 * @author JavaBird
 */
class Wear implements Serializable
{

    /**
     * List of all colors that clothes may have.
     */
    static final String[] COLOR_LIST =
    {
        "Black", "Gray", "Red", "Orange", "Yellow", "Green", "Blue", "Dark blue", "Purple", "Pink"
    };
    private static final long serialVersionUID = 1L;

    /**
     * The wear name (e. g. "Regular panties")
     */
    private String name;

    /**
     * The insert name used in the game text (e. g. "panties")
     */
    private String insertName;

    /**
     * The pressure of an wear.<br>1 point of a pressure takes 1 point from the
     * maximal bladder capacity.
     */
    private float pressure;

    /**
     * The absorption of an wear.<br>1 point of an absorption can store 1 point
     * of a leaked pee.
     */
    private float absorption;

    /**
     * The drying over time.<br>1 point = -1 pee unit per 3 minutes
     */
    private float dryingOverTime;

    /**
     * The wear assigned color.
     */
    private String color;

    /**
     * Whether or not certain wear equals "No under/outerwear".
     */
    private boolean missing;

    /**
     * Underwear or outerwear?
     */
    private WearType type;

    private void setFieldValuesInConstructor(String name, String insertName, float pressure, float absorption, float dryingOverTime)
    {
        this.name = name;
        this.insertName = insertName;
        this.pressure = pressure;
        this.absorption = absorption;
        this.dryingOverTime = dryingOverTime;
        missing = name.equals("No underwear") || name.equals("No outerwear");
    }

    /**
     * @param name the wear name (e. g. "Regular panties")
     * @param insertName the insert name used in the game text (e. g. "panties")
     * @param pressure the pressure of an wear.<br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption the absorption of an wear.<br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     * 3 minutes
     */
    Wear(String name, String insertName, float pressure, float absorption, float dryingOverTime)
    {
        setFieldValuesInConstructor(name, insertName, pressure, absorption, dryingOverTime);
    }

    /**
     * @param name the wear name (e. g. "Regular panties")
     * @param insertName	the insert name used in the game text (e. g. "panties")
     * @param pressure	the pressure of an wear.<br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption	the absorption of an wear.<br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     * 3 minutes
     * @param type the wear type
     */
    Wear(String name, String insertName, float pressure, float absorption, float dryingOverTime, WearType type)
    {
        setFieldValuesInConstructor(name, insertName, pressure, absorption, dryingOverTime);
        this.type = type;
    }

    /**
     * @param insertName the insert name (used in game text) to set
     */
    public void setInsertName(String insertName)
    {
        this.insertName = insertName;
    }

    /**
     * @return the wear name (e. g. "Regular panties")
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the pressure of an wear
     */
    public float getPressure()
    {
        return pressure;
    }

    /**
     * @return the absorption of an wear
     */
    public float getAbsorption()
    {
        return absorption;
    }

    /**
     * @return the insert name used in the game text (e. g. "panties")
     */
    public String insert()
    {
        return insertName;
    }

    /**
     * @return the drying over time.<br>1 = -1 pee unit per 3 minutes
     */
    public float getDryingOverTime()
    {
        return dryingOverTime;
    }

    /**
     * @return the color
     */
    public String getColor()
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color)
    {
        this.color = color;
    }

    /**
     * @return whether or not certain wear equals "No under/outerwear".
     */
    boolean isMissing()
    {
        return missing;
    }

    /**
     * @return the type
     */
    public WearType getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(WearType type)
    {
        this.type = type;
    }

    public enum WearType
    {
        UNDERWEAR, OUTERWEAR, BOTH_SUITABLE
    }
}

class Bladder
{

    /**
     * The dryness game over minimal threshold.
     */
    static final int MINIMAL_DRYNESS = 0;

    /**
     * Maximal time without squirming and leaking.
     */
    static short maxSphincterPower;

    /**
     * List of all underwear types.
     */
    static Wear[] underwearList =
    {
        //        Name      Insert name     Pressure, Absotption, Drying over time
        new Wear("Random", NarrativeEngine.showError((byte) 0) + "</i></b>", 0, 0, 0),
        new Wear("No underwear", showError((byte) 0), 0, 0, 1),
        new Wear("Strings", "panties", 1, 2, 1),
        new Wear("Tanga panties", "panties", 1.5F, 3, 1),
        new Wear("Regular panties", "panties", 2, 4, 1),
        new Wear("\"Boy shorts\" panties", "panties", 4, 7, 1),
        new Wear("String bikini", "bikini panties", 1, 1, 2),
        new Wear("Regular bikini", "bikini panties", 2, 2, 2),
        new Wear("Swimsuit", "swimsuit", 4, 2.5F, 2.5F),
        new Wear("Light diaper", "diaper", 9, 50, 0),
        new Wear("Normal diaper", "diaper", 18, 100, 0),
        new Wear("Heavy diaper", "diaper", 25, 175, 0),
        new Wear("Light pad", "pad", 2, 16, 0.25F),
        new Wear("Normal pad", "pad", 3, 24, 0.25F),
        new Wear("Big pad", "pad", 4, 32, 0.25F),
        new Wear("Pants", "pants", 2.5F, 5, 1),
        new Wear("Shorts-alike pants", "pants", 3.75F, 7.5F, 1),
        new Wear("Anti-gravity pants", "pants", 0, 4, 1),
        new Wear("Super-absorbing diaper", "diaper", 18, 600, 0)
    };

    /**
     * Amount of pee that clothes can store.
     */
    static float dryness;

    /**
     * Maximal bladder fulness.
     */
    static short maxBladder = 130;

    /**
     * Current sphincter power. The higher bladder level, the faster power
     * consumption.
     */
    static short sphincterPower;

    /**
     * The class time.
     */
    static byte time = 0;

    /**
     * Current bladdder fulness.
     */
    static short fulness;

    /**
     * Amount of a water in a belly.
     */
    static double belly;

    /**
     * Character's undies.
     */
    static Wear undies;

    final static float MAXIMAL_THIRST = 30;

    /**
     * Character's lower body clothing.
     */
    static Wear lower;

    /**
     * Amount of the character thirstiness. Used only in hardcore mode.
     */
    static float thirst = 0;

    /**
     * Makes the wetting chance higher after reaching 100% of the bladder
     * fulness.
     */
    static short embarassment;

    /**
     * Before 1.1:<br>
     * simply multiplies a bladder increasement.<br>
     * <br>
     * 1.1 and after:<br>
     * defines the sphincter weakening speed.
     */
    static float incontinence;

    /**
     *
     * @param amount the value of amount
     * @param the value of
     */
    static void offsetTime(UI ui, int amount)
    {
        time += amount;
        if (NarrativeEngine.drain & (time % 15) == 0)
        {
            emptyBladder(ui);
        }
        //Clothes drying over time
        if (dryness < lower.getAbsorption() + undies.getAbsorption())
        {
            dryness += lower.getDryingOverTime() + undies.getDryingOverTime() * (amount / 3);
        }
        if (dryness > lower.getAbsorption() + undies.getAbsorption())
        {
            dryness = lower.getAbsorption() + undies.getAbsorption();
        }
        ui.update();
    }

    /**
     * Offsets bladder fulness by a specified amount.
     *
     * @param amount the amount to offset bladder fulness
     */
    static void offsetBladder(UI ui, double amount)
    {
        fulness += amount; //Incontinence does another job after 1.1
        if ((fulness > 100 && !NarrativeEngine.hardcore) || (fulness > 80 && hardcore))
        {
            ui.lblBladder.setForeground(Color.RED);
        }
        else
        {
            ui.lblBladder.setForeground(UI.lblDefaultColor);
        }
        ui.update();
    }

    /**
     *
     * @param the value of
     * @return the boolean
     */
    private static boolean isCriticalBladder()
    {
        return (fulness > maxBladder - 30 & !hardcore) | (fulness > maxBladder - 20 & hardcore);
    }

    /**
     * Replenishes the sphincter power.
     *
     * @param amount the sphincter recharge amount
     */
    static void rechargeSphPower(UI ui, int amount)
    {
        sphincterPower += amount;
        if (sphincterPower > maxSphincterPower)
        {
            sphincterPower = maxSphincterPower;
        }
        ui.update();
    }

    /**
     * Checks the wetting conditions, and if they are met, wetting
     */
    static void testWet()
    {
        //If bladder is filled more than 130 points in the normal mode and 100 points in the hardcore mode, forcing wetting
        if (fulness >= maxBladder & !hardcore)
        {
            sphincterPower = 0;
            if (dryness < MINIMAL_DRYNESS)
            {
                if (NarrativeEngine.specialHardcoreStage)
                {
                    NarrativeEngine.setNextStage(NarrativeEngine.GameStage.SURPRISE_ACCIDENT);
                }
                else
                {
                    NarrativeEngine.setNextStage(NarrativeEngine.GameStage.ACCIDENT);
                }
            }
        }
        else //If bladder is filled more than 100 points in the normal mode and 50 points in the hardcore mode, character has a chance to wet
        {
            if (isCriticalBladder())
            {
                wetIfUnlucky(!hardcore ? ((short) (5 * (fulness - 80))) : ((short) (3 * (fulness - 100) + embarassment)));
            }
        }
    }

    /**
     * Empties the belly.
     *
     */
    static void emptyBelly(UI ui)
    {
        offsetBelly(ui, -belly);
    }

    /**
     *
     * @param wetChance the value of wetChance
     * @param the value of
     */
    private static void wetIfUnlucky(short wetChance)
    {
        if (NarrativeEngine.chance((byte) wetChance))
        {
            sphincterPower = 0;
            if (dryness < MINIMAL_DRYNESS)
            {
                if (specialHardcoreStage)
                {
                    setNextStage(NarrativeEngine.GameStage.SURPRISE_ACCIDENT);
                }
                else
                {
                    setNextStage(NarrativeEngine.GameStage.ACCIDENT);
                }
            }
        }
    }

    /**
     * Increments the time by 3 minutes and all time-related parameters.
     *
     */
    static void passTime(UI ui)
    {
        passTime(ui, (byte) 3);
    }

    /**
     * Increments the time by # minutes and all time-related parameters.
     *
     * @param time #
     */
    static void passTime(UI ui, short time)
    {
        offsetTime(ui, time);
        offsetBladder(ui, time * 1.5);
        offsetBelly(ui, -time * 1.5);
        if (Bladder.time >= 88)
        {
            ui.setText("You hear the bell finally ring.");
            setNextStage(NarrativeEngine.GameStage.CLASS_OVER);
        }
        testWet();
        //Decrementing sphincter power for every 3 minutes
        for (int i = 0; i < time; i++)
        {
            decaySphPower(ui);
            if (belly != 0)
            {
                if (belly > 3)
                {
                    offsetBladder(ui, 2);
                }
                else
                {
                    offsetBladder(ui, belly);
                    emptyBelly(ui);
                }
            }
        }
        if (hardcore)
        {
            thirst += 2;
            if (Bladder.thirst > Bladder.MAXIMAL_THIRST)
            {
                setNextStage(NarrativeEngine.GameStage.DRINK);
            }
        }
        //Updating labels
        ui.update();
    }

    /**
     *
     * @param amount the value of amount
     * @param the value of
     */
    static void offsetBelly(UI ui, double amount)
    {
        belly += amount;
        if (belly < 0)
        {
            belly = 0;
        }
        ui.update();
    }

    /**
     *
     * @param amount the value of amount
     * @param the value of
     */
    public void offsetEmbarassment(UI ui, int amount)
    {
        embarassment += amount;
        if (embarassment < 0)
        {
            embarassment = 0;
        }
        ui.update();
    }

    //TODO: Refactor
    /**
     * Decreases the sphincter power.
     */
    /**
     *
     * @param the value of
     */
    static void decaySphPower(UI ui)
    {
        sphincterPower -= fulness / 30;
        if (sphincterPower < 0)
        {
            dryness -= 5; //Decreasing dryness
            fulness -= 2.5; //Decreasing bladder level
            sphincterPower = 0;
            if (dryness > MINIMAL_DRYNESS)
            {
                //Naked
                if (lower.isMissing() && undies.isMissing())
                {
                    ui.setText("You feel the leak running down your thighs...", "You're about to pee! You must stop it!");
                }
                else //Outerwear
                {
                    if (!lower.isMissing())
                    {
                        ui.setText("You see the wet spot expand on your " + lower.insert() + "!", "You're about to pee! You must stop it!");
                    }
                    else //Underwear
                    {
                        if (!undies.isMissing())
                        {
                            ui.setText("You see the wet spot expand on your " + undies.insert() + "!", "You're about to pee! You must stop it!");
                        }
                    }
                }
            }
            if (dryness < MINIMAL_DRYNESS)
            {
                if (lower.isMissing() && undies.isMissing())
                {
                    if (cornered)
                    {
                        ui.setText("You see a puddle forming on the floor beneath you, you're peeing!", "It's too much...");
                        setNextStage(ACCIDENT);
                        ui.handleNextClicked();
                    }
                    else
                    {
                        ui.setText("Feeling the pee hit the chair and soon fall over the sides,", "you see a puddle forming under your chair, you're peeing!", "It's too much...");
                        setNextStage(ACCIDENT);
                        ui.handleNextClicked();
                    }
                }
                else
                {
                    if (!lower.isMissing())
                    {
                        ui.setText("You see the wet spot expanding on your " + lower.insert() + "!", "It's too much...");
                        setNextStage(ACCIDENT);
                        ui.handleNextClicked();
                    }
                    else
                    {
                        if (!undies.isMissing())
                        {
                            ui.setText("You see the wet spot expanding on your " + undies.insert() + "!", "It's too much...");
                            setNextStage(ACCIDENT);
                            ui.handleNextClicked();
                        }
                    }
                }
            }
        }
        ui.update();
    }

    /**
     * Empties the bladder.
     *
     */
    static void emptyBladder(UI ui)
    {
        fulness = 0;
        ui.update();
    }

    static void calculateCaps(UI ui)
    {
        //Calculating dryness and maximal bladder capacity values
        //TODO: Move to Bladder
        dryness = lower.getAbsorption() + undies.getAbsorption();
        maxBladder -= lower.getPressure() + undies.getPressure();
        ui.drynessBar.setMaximum((int) dryness);
    }
}

class NarrativeEngine
{

    static GameStage getNextStage()
    {
        return nextStage;
    }

    class StagePool
    {
        Stage leaveHome;
        Stage leaveBed = new BladderAffectingStage(leaveHome, (short) 3)
        {
            @Override
            public String[] getText()
            {
                setLinesAsDialogue(1);
                return getWearDependentText(new String[]
                {
                    "Wh-what? Did I forget to set my alarm?!",
                    "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                    "You hurriedly slip on some " + undies.insert() + " and " + lower.insert() + ",",
                    "not even worrying about what covers your chest."
                }, new String[]
                {
                    "Wh-what? Did I forget to set my alarm?!",
                    "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                    "You hurriedly slip on some " + lower.insert() + ", quick to cover your " + undies.insert() + ",",
                    "not even worrying about what covers your chest."
                }, new String[]
                {
                    "Wh-what? Did I forget to set my alarm?!",
                    "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                    "You hurriedly slip on " + undies.insert() + ",",
                    "not even worrying about what covers your chest and legs."
                }, new String[]
                {
                    "Wh-what? Did I forget to set my alarm?!",
                    "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                    "You are running downstairs fully naked."
                });
            }
        };
    }
    //Random stuff generator
    public static final Random RANDOM = new Random();

    static boolean chance(byte chance)
    {
        return RANDOM.nextInt(100) <= chance;
    }

    /**
     * List of all outerwear types.
     */
    final static Wear[] OUTERWEAR_LIST =
    {
        //        Name      Insert name     Pressure, Absotption, Drying over time
        new Wear("Random", showError((byte) 0), 0, 0, 0),
        new Wear("No outerwear", showError((byte) 0), 0, 0, 1),
        new Wear("Long jeans", "jeans", 7, 12, 1.2F),
        new Wear("Knee-length jeans", "jeans", 6, 10, 1.2F),
        new Wear("Short jeans", "shorts", 5, 8.5F, 1.2F),
        new Wear("Very short jeans", "shorts", 4, 7, 1.2F),
        new Wear("Long trousers", "trousers", 9, 15.75F, 1.4F),
        new Wear("Knee-length trousers", "trousers", 8, 14, 1.4F),
        new Wear("Short trousers", "shorts", 7, 12.25F, 1.4F),
        new Wear("Very short trousers", "shorts", 6, 10.5F, 1.4F),
        new Wear("Long skirt", "skirt", 5, 6, 1.7F),
        new Wear("Knee-length skirt", "skirt", 4, 4.8F, 1.7F),
        new Wear("Short skirt", "skirt", 3, 3.6F, 1.7F),
        new Wear("Mini skirt", "skirt", 2, 2.4F, 1.7F),
        new Wear("Micro skirt", "skirt", 1, 1.2F, 1.7F),
        new Wear("Long skirt and tights", "skirt and tights", 6, 7.5F, 1.6F),
        new Wear("Knee-length skirt and tights", "skirt and tights", 5, 8.75F, 1.6F),
        new Wear("Short skirt and tights", "skirt and tights", 4, 7, 1.6F),
        new Wear("Mini skirt and tights", "skirt and tights", 3, 5.25F, 1.6F),
        new Wear("Micro skirt and tights", "skirt and tights", 2, 3.5F, 1.6F),
        new Wear("Leggings", "leggings", 10, 11, 1.8F),
        new Wear("Short male jeans", "jeans", 5, 8.5F, 1.2F),
        new Wear("Normal male jeans", "jeans", 7, 12, 1.2F),
        new Wear("Male trousers", "trousers", 9, 15.75F, 1.4F)
    };

    /**
     * Times teacher denied character to go out.
     */
    static byte timesPeeDenied = 0;

    /**
     * Whether or not hardcore mode enabled: teacher never lets you pee, it's
     * harder to hold pee, you may get caught holding pee
     */
    static boolean hardcore = false;

    /**
     * An array that contains boolean values that define <i>dialogue lines</i>.
     * Dialogue lines, unlike normal lines, are <i>italic</i>.
     */
    static boolean[] dialogueLines = new boolean[UI.MAX_LINES];

    /**
     * Actions list.
     */
    static ArrayList<String> actionList = new ArrayList<>();

    /**
     * Amount of embarassment raising every time character caught holding pee.
     */
    static short classmatesAwareness = 0;

    /**
     * Current character gender.
     */
    static Gender gender;

    //TODO: Refactor
    /**
     * List of all cheats.
     */
    final String[] CHEAT_LIST =
    {
        "Go to the corner", "Stay after class", "Pee in a bottle", "End class right now",
        "Calm the teacher down", "Raise your hand", "Make your pee disappear regularly",
        "Set your incontinence level", "Toggle hardcore mode", "Set bladder fulness"
    };

    /**
     * A stage after the current stage.
     */
    private static GameStage nextStage;

    static boolean specialHardcoreStage = false;

    /**
     * List of all boy names for special hardcore scene.
     */
    static final String[] BOY_NAMES =
    {
        "Mark",
        "Mike",
        "Jim",
        "Alex",
        "Ben",
        "Bill",
        "Dan"
    };

    private static final String[] ERRORS =
    {
        "LACK OF WEAR HANDLING",
        "NOT OVERRIDDEN"
    };

    /**
     * Whether or not pee drain cheat enabled: pee mysteriously vanishes every
     * 15 minutes.
     */
    public static boolean drain = false;

    /**
     * Special hardcore scene boy name.
     */
    static String boyName = BOY_NAMES[RANDOM.nextInt(BOY_NAMES.length)];

    /**
     * Character's name.
     */
    static String name;

    /**
     * Whether or not charecter has to stay 30 minutes after class.
     */
    static boolean stay = false;

    /**
     * Whether or not player has used cheats.
     */
    static boolean cheatsUsed = false;

    /**
     * Number of times player got caught holding pee.
     */
    static byte timesCaught = 0;

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    static boolean cornered = false;

    private final String[] askToPeeSuccessText =
    {
        "You ask the teacher if you can go out to the restroom.",
        "Yes, you may.",
        "says the teacher. You run to the restroom,",
        showError((byte) 1),
        "wearily flop down on the toilet and start peeing."
    };

    /**
     * Text to be displayed after the game which shows how many {@link score}
     * did you get.
     */
    static String scoreText = "";

    /**
     * A number that shows a game difficulty - the higher score, the harder was
     * the game. Specific actions (for example, peeing in a restroom during a
     * lesson) reduce score points. Using the cheats will zero the score points.
     */
    static int score = 0;

    /**
     *
     * @return the boolean
     */
    private boolean linesAreTooLong()
    {
        if (time >= 120)
        {
            stay = false;
            setNextStage(CLASS_OVER);
            return true;
        }
        return false;
    }

    /**
     *
     * @param lines the value of lines
     */
    private void setLinesAsDialogue(int... lines)
    {
        for (int i : lines)
        {
            dialogueLines[i - 1] = true;
        }
    }

    /**
     *
     * @param the value of
     * @return the boolean
     */
    private boolean triggerClsasOverScene()
    {
        //Special hardcore scene trigger
        if (RANDOM.nextInt(100) <= 10 && hardcore & isFemale())
        {
            setNextStage(SURPRISE);
            return true;
        }
        if (stay)
        {
            setNextStage(AFTER_CLASS);
            return true;
        }
        return false;
    }

    /**
     *
     * @param nextStage the value of nextStage
     */
    static void setNextStage(GameStage nextStage)
    {
        NarrativeEngine.nextStage = nextStage;
    }

    /**
     *
     */
    static void getCaughtByClassmates()
    {
        //Chance to be caught by classmates in hardcore mode
        if (RANDOM.nextInt(100) <= 15 + classmatesAwareness & hardcore)
        {
            setNextStage(CAUGHT);
        }
        else
        {
            setNextStage(ASK_ACTION);
        }
    }

    /**
     *
     * @param the value of
     */
    private void displayDesperationStatus(UI ui)
    {
        //Bladder: 0-20
        if (fulness <= 20)
        {
            ui.setText("Feeling bored about the day, and not really caring about the class too much,", "you look to the clock, watching the minutes tick by.");
        }
        //Bladder: 20-40
        if (fulness > 20 && fulness <= 40)
        {
            ui.setText("Having to pee a little bit,", "you look to the clock, watching the minutes tick by and wishing the lesson to get over faster.");
        }
        //Bladder: 40-60
        if (fulness > 40 && fulness <= 60)
        {
            ui.setText("Clearly having to pee,", "you impatiently wait for the lesson end.");
        }
        //Bladder: 60-80
        if (fulness > 60 && fulness <= 80)
        {
            setLinesAsDialogue(2);
            ui.setText("You feel the rather strong pressure in your bladder, and you're starting to get even more desperate.", "Maybe I should ask teacher to go to the restroom? It hurts a bit...");
        }
        //Bladder: 80-100
        if (fulness > 80 && fulness <= 100)
        {
            setLinesAsDialogue(1, 3);
            ui.setText("Keeping all that urine inside will become impossible very soon.", "You feel the terrible pain and pressure in your bladder, and you can almost definitely say you haven't needed to pee this badly in your life.", "Ouch, it hurts a lot... I must do something about it now, or else...");
        }
        //Bladder: 100-130
        if (fulness > 100 && fulness <= 130)
        {
            setLinesAsDialogue(1, 3);
            if (isFemale())
            {
                ui.setText("This is really bad...", "You know that you can't keep it any longer and you may wet yourself in any moment and oh,", "You can clearly see your bladder as it bulging.", "Ahhh... I cant hold it anymore!!!", "Even holding your crotch doesn't seems to help you to keep it in.");
            }
            else
            {
                ui.setText("This is really bad...", "You know that you can't keep it any longer and you may wet yourself in any moment and oh,", "You can clearly see your bladder as it bulging.", "Ahhh... I cant hold it anymore!!!", "Even squeezing your penis doesn't seems to help you to keep it in.");
            }
        }
    }

    /**
     *
     * @param the value of
     */
    private void offerHoldingChoices()
    {
        //Adding action choices
        switch (timesPeeDenied)
        {
            case 0:
                actionList.add("Ask the teacher to go pee");
                break;
            case 1:
                actionList.add("Ask the teacher to go pee again");
                break;
            case 2:
                actionList.add("Try to ask the teacher again");
                break;
            case 3:
                actionList.add("Take a chance and ask the teacher (RISKY)");
                break;
            default:
                actionList.add("[Unavailable]");
        }
        if (!cornered)
        {
            if (isFemale())
            {
                actionList.add("Press on your crotch");
            }
            else
            {
                actionList.add("Squeeze your penis");
            }
        }
        else
        {
            actionList.add("[Unavailable]");
        }
        actionList.add("Rub thighs");
        if (fulness >= 100)
        {
            actionList.add("Give up and pee yourself");
        }
        else
        {
            actionList.add("[Unavailable]");
        }
        if (hardcore)
        {
            actionList.add("Drink water");
        }
        else
        {
            actionList.add("[Unavailable]");
        }
        actionList.add("Just wait");
        actionList.add("Cheat (will reset your score)");
    }

    /**
     *
     * @param errorIndex the value of errorIndex
     */
    static String showError(byte errorIndex)
    {
        return "<b><i>" + ERRORS[errorIndex] + "@" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "</i></b>";
    }

    /**
     *
     * @return the boolean
     */
    private boolean gotCalledByTeacher(UI ui)
    {
        //Called by teacher if unlucky
        if (RANDOM.nextInt(20) == 5)
        {
            ui.setText("Suddenly, you hear the teacher call your name.");
            setNextStage(CALLED_ON);
            return true;
        }
        return false;
    }

    /**
     *
     * @param femaleText the value of femaleText
     * @param maleText the value of maleText
     * @param the value of
     */
    String[] getGenderDependentText(String[] femaleText, String[] maleText)
    {
        if (isFemale())
        {
            return femaleText;
        }
        else
        {
            return maleText;
        }
    }

    /**
     *
     * @param bothWear the value of bothWear
     * @param lowerOnly the value of lowerOnly
     * @param undiesOnly the value of undiesOnly
     * @param noWear the value of noWear
     */
    String[] getWearDependentText(String[] bothWear, String[] lowerOnly, String[] undiesOnly, String[] noWear)
    {
        if (lower.isMissing())
        {
            if (undies.isMissing())
            {
                return bothWear;
            }
            else
            {
                return lowerOnly;
            }
        }
        else
        {
            if (undies.isMissing())
            {
                return undiesOnly;
            }
            else
            {
                return noWear;
            }
        }
    }

    /**
     * Operates the player score.
     *
     * @param message the reason to manipulate score
     * @param mode add, substract, divide or multiply
     * @param points amount of points to operate
     */
    public static void score(String message, char mode, float points)
    {
        switch (mode)
        {
            case '+':
                score += points;
                scoreText += "\n" + message + ": +" + points + " points";
                break;
            case '-':
                score -= points;
                scoreText += "\n" + message + ": -" + points + " points";
                break;
            case '*':
                score *= points;
                scoreText += "\n" + message + ": +" + points * 100 + "% of points";
                break;
            case '/':
                score /= points;
                scoreText += "\n" + message + ": -" + 100 / points + "% of points";
                break;
            default:
                System.err.println("score() method used incorrectly, message: \"" + message + "\"");
        }
    }

    /**
     * @return TRUE - if character's gender is female<br>FALSE - if character's
     * gender is male
     */
    static boolean isFemale()
    {
        return gender == FEMALE;
    }

    /**
     * @return TRUE - if character's gender is male<br>FALSE - if character's
     * gender is female
     */
    static boolean isMale()
    {
        return gender == MALE;
    }

    enum GameStage
    {
        LEAVE_BED,
        LEAVE_HOME,
        GO_TO_CLASS,
        WALK_IN,
        SIT_DOWN,
        ASK_ACTION,
        CHOSE_ACTION,
        ASK_TO_PEE,
        CALLED_ON,
        CAUGHT,
        USE_BOTTLE,
        ASK_CHEAT,
        CHOSE_CHEAT,
        CLASS_OVER,
        AFTER_CLASS,
        ACCIDENT,
        GIVE_UP,
        WET,
        POST_WET,
        GAME_OVER,
        END_GAME,
        SURPRISE,
        SURPRISE_2,
        SURPRISE_ACCIDENT,
        SURPRISE_DIALOGUE,
        SURPRISE_CHOSE,
        HIT,
        PERSUADE,
        SURPRISE_WET_VOLUNTARY,
        SURPRISE_WET_VOLUNTARY2,
        SURPRISE_WET_PRESSURE,
        DRINK
    }
}

enum Gender
{
    MALE, FEMALE
}

class UI extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final int ACTION_BUTTONS_HEIGHT = 35;
    private static final int ACTION_BUTTONS_WIDTH = 89;
    private static final int ACTION_BUTTONS_TOP_BORDER = 510;
    JLabel lblLower;
    JLabel lblThirst;
    JLabel lblMinutes;
    JLabel lblName;
    JProgressBar sphincterBar;
    private JScrollPane listScroller;
    private JButton btnNewGame;
    static Color lblDefaultColor;
    //Maximal lines of a text
    static final int MAX_LINES = 9;

    /**
     * Sets the in-game text.
     *
     * @param lines the in-game text to set
     */
    @SuppressWarnings(value = "UseOfSystemOutOrSystemErr")
    void setText(String... lines)
    {
        if (lines.length > UI.MAX_LINES)
        {
            System.err.println("You can't have more than " + UI.MAX_LINES + " lines at a time!");
            return;
        }
        if (lines.length <= 0)
        {
            this.textLabel.setText("");
            return;
        }
        String toSend = "<html><center>";
        for (int i = 0; i < lines.length; i++)
        {
            if (NarrativeEngine.dialogueLines[i])
            {
                toSend += "<i>\"" + lines[i] + "\"</i>";
            }
            else
            {
                toSend += lines[i];
            }
            toSend += "<br>";
        }
        toSend += "</center></html>";
        textLabel.setText(toSend);
        NarrativeEngine.dialogueLines = new boolean[UI.MAX_LINES];
    }
    JLabel lblSphPower;

    void handleNextClicked()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private JList<Object> listChoice;
    private JButton btnLoad;
    private JLabel lblIncon;
    private JLabel lblUndies;
    JFileChooser fcWear;
    private JLabel lblEmbarassment;
    JLabel lblDryness;
    private JProgressBar thirstBar;
    private JLabel lblChoice;
    JFileChooser fcGame;
    private JPanel textPanel;
    private JButton btnQuit;
    private JLabel lblBelly;
    private JProgressBar bladderBar;
    JProgressBar drynessBar;
    private JButton btnReset;
    JLabel textLabel;
    JProgressBar timeBar;
    //Game frame variables declaration
    private JPanel contentPane;
    private JButton btnNext;
    JLabel lblBladder;
    private JButton btnSave;

    /**
     *
     * @param the value of
     * @throws HeadlessException
     */
    private void showScore() throws HeadlessException
    {
        if (cheatsUsed)
        {
            score = 0;
            scoreText = "\nYou've used the cheats, so you've got no score.";
        }
        String scoreText2 = "Your score: " + score + "\n" + scoreText;
        JOptionPane.showMessageDialog(null, scoreText2);
    }

    /**
     *
     * @return the boolean
     */
    private boolean inappropriateSelection()
    {
        return listChoice.isSelectionEmpty() || listChoice.getSelectedValue().equals("[Unavailable]");
    }

    /**
     *
     * @param actionGroupName the value of actionGroupName
     * @param the value of
     */
    void showActionUI(String actionGroupName)
    {
        listChoice.setListData(NarrativeEngine.actionList.toArray());
        lblChoice.setVisible(true);
        lblChoice.setText(actionGroupName);
        listScroller.setVisible(true);
    }

    /**
     *
     * @throws NumberFormatException
     * @throws HeadlessException
     */
    private void askNewBladderLevel() throws NumberFormatException, HeadlessException
    {
        short newBladder = Short.parseShort(JOptionPane.showInputDialog("How your bladder is full now?"));
        if (newBladder > 0 || newBladder < 150)
        {
            fulness = newBladder;
        }
    }

    //TODO: Refactor
    //This method is monstrously huge
    //Introduce Stage class
    /*
    static void handleNextClicked()
    {
        update();
        switch (nextStage)
        {
            case LEAVE_BED:
                //Making line 1 italic
                setLinesAsDialogue(1);
                if (!lower.isMissing())
                {
                    if (!undies.isMissing()) //Both lower clothes and undies
                    {
                        setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You hurriedly slip on some " + undies.insert() + " and " + lower.insert() + ",", "not even worrying about what covers your chest.");
                    } else //Lower clothes only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You hurriedly slip on some " + lower.insert() + ", quick to cover your " + undies.insert() + ",", "not even worrying about what covers your chest.");
                    }
                } else
                {
                    if (!undies.isMissing()) //Undies only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You hurriedly slip on " + undies.insert() + ",", "not even worrying about what covers your chest and legs.");
                    } else //No clothes at all
                    {
                        setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You are running downstairs fully naked.");
                    }
                }
                passTime((byte) 1, );
                //Setting the next stage to "Leaving home"
                setNextStage(LEAVE_HOME);
                break;
            case LEAVE_HOME:
                setText("Just looking at the clock again in disbelief adds a redder tint to your cheeks.", "", "Paying much less attention to your daily routine, you quickly run down the stairs, get a small glass of orange juice and chug it.", "", "The cold drink brings a chill down your spine as you collect your things and rush out the door to school.");
                passTime((byte) 1, );
                offsetEmbarassment(3, );
                offsetBelly(10, );
                setNextStage(GO_TO_CLASS);
                break;
            case GO_TO_CLASS:
                //Displaying all values
                lblMinutes.setVisible(true);
                lblSphPower.setVisible(true);
                lblDryness.setVisible(true);
                sphincterBar.setVisible(true);
                drynessBar.setVisible(true);
                timeBar.setVisible(true);
                if (!lower.isMissing())
                {
                    //Skirt blowing in the wind
                    if (lower.insert().equals("skirt"))
                    {
                        setText("You rush into class, your " + lower.insert() + " blowing in the wind.", "", "Normally, you'd be worried your " + undies.insert() + " would be seen, but you can't worry about it right now.", "You make it to your seat without a minute to spare.");
                    } else
                    {
                        //Nothing is blowing in wind
                        setText("Trying your best to make up lost time, you rush into class and sit down to your seat without a minute to spare.");
                    }
                } else
                {
                    if (!undies.isMissing())
                    {
                        setText("You rush into class; your classmates are looking at your " + undies.insert() + ".", "You can't understand how you forgot to even put on any lower clothing,", "and you know that your " + undies.insert() + " have definitely been seen.", "You make it to your seat without a minute to spare.");
                    } else
                    {
                        if (isFemale())
                        {
                            setText("You rush into class; your classmates are looking at your pussy and boobs.", "Guys are going mad and doing nothing except looking at you.", "You can't understand how you dared to come to school naked.", "You make it to your seat without a minute to spare.");
                        } else
                        {
                            setText("You rush into class; your classmates are looking at your penis.", "Girls are really going mad and doing nothing except looking at you.", "You can't understand how you dared to come to school naked.", "You make it to your seat without a minute to spare.");
                        }
                    }
                }
                offsetEmbarassment(2, );
                setNextStage(WALK_IN);
                break;
            case WALK_IN:
                //If lower clothes is a skirt
                if (lower.insert().equals("skirt") || lower.insert().equals("skirt and tights") || lower.insert().equals("skirt and tights"))
                {
                    setLinesAsDialogue(1, 3);
                    setText("Next time you run into class, " + name + ",", "your teacher says,", "make sure you're wearing something less... revealing!", "A chuckle passes over the classroom, and you can't help but feel a", "tad bit embarrassed about your rush into class.");
                    offsetEmbarassment(5, );
                } else //No outerwear
                {
                    if (lower.isMissing())
                    {
                        setLinesAsDialogue(1);
                        setText("WHAT!? YOU CAME TO SCHOOL NAKED!?", "your teacher shouts in disbelief.", "", "A chuckle passes over the classroom, and you can't help but feel extremely embarrassed", "about your rush into class, let alone your nudity");
                        offsetEmbarassment(25, );
                    } else
                    {
                        setLinesAsDialogue(1, 3);
                        setText("Sit down, " + name + ". You're running late.", "your teacher says,", "And next time, don't make so much noise entering the classroom!", "A chuckle passes over the classroom, and you can't help but feel a tad bit embarrassed", "about your rush into class.");
                    }
                }
                setNextStage(SIT_DOWN);
                break;
            case SIT_DOWN:
                setText("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of", "your bladder filling as the liquids you drank earlier start to make their way down.");
                passTime();
                setNextStage(ASK_ACTION);
                score("Embarassment at start - " + incontinence + " pts", '+', embarassment);
                break;
            case ASK_ACTION:
                if (gotCalledByTeacher())
                {
                    break;
                }
                displayDesperationStatus();
                offerHoldingChoices();
                showActionUI("What now?");
                //Loading the choice array into the action selector
                setNextStage(CHOSE_ACTION);
                passTime();
                //Don't go further if player selected no or unavailable action
//                }while(listChoice.isSelectionEmpty()||listChoice.getSelectedValue().equals("[Unavailable]"));
                break;
            case CHOSE_ACTION:
                if (inappropriateSelection())
                {
                    setNextStage(ASK_ACTION);
                    break;
                }
                //Hiding the action selector and doing action job
                switch (hideActionUI())
                {
                    //Ask the teacher to go pee
                    case 0:
                        setNextStage(ASK_TO_PEE);
                        setLinesAsDialogue(2, 3);
                        setText("You think to yourself:", "I don't think I can hold it until class ends!", "I don't have a choice, I have to ask the teacher...");
                        break;
                    
//                 * Press on crotch/squeeze penis
//                 * 3 minutes
//                 * -2 bladder
//                 * Detection chance: 15
//                 * Effectiveness: 0.4
//                 * =========================
//                 * 3 minutes
//                 * +20 sph. power
//                 * Detection chance: 15
//                 * Future effectiveness: 4
    
                    case 1:
                        setText("You don't think anyone will see you doing it,", "so you take your hand and hold yourself down there.", "It feels a little better for now.");
                        rechargeSphPower(20, );
                        offsetTime(3, );
                        getCaughtByClassmates();
                        break;
                    
//                 * Rub thighs
//                 * 3 + 3 = 6 minutes
//                 * -0.2 bladder
//                 * Detection chance: 3
//                 * Effectiveness: 6
//                 * =========================
//                 * 3 + 3 = 6 minutes
//                 * +2 sph. power
//                 * Detection chance: 3
//                 * Future effectiveness: 4
//                     
                    case 2:
                        setText("You need to go, and it hurts, but you just", "can't bring yourself to risk getting caught with your hand between", "your legs. You rub your thighs hard but it doesn't really help.");
                        rechargeSphPower(2, );
                        offsetTime(3, );
                        //Chance to be caught by classmates in hardcore mode
                        getCaughtByClassmates();
                        break;
                    //Give up
                    case 3:
                        setText("You're absolutely desperate to pee, and you think you'll", "end up peeing yourself anyway, so it's probably best to admit", "defeat and get rid of the painful ache in your bladder.");
                        setNextStage(GIVE_UP);
                        break;
                    //Drink water
                    case 4:
                        setText("Feeling a tad bit thirsty,", "You decide to take a small sip of water from your bottle to get rid of it.");
                        setNextStage(DRINK);
                        break;
                    
//                 * Wait
//                 * =========================
//                 * 3 + 2 + n minutes
//                 * +(2.5n) bladder
//                 * Detection chance: 1
//                 * Future effectiveness: 2.4(1), 0.4(2), 0.47(30)
                     
                    case 5:
                        byte timeOffset;
                        //Asking player how much to wait
                        try
                        {
                            timeOffset = askPlayerHowMuchToWait();
                        } //Ignoring invalid output
                        catch (NumberFormatException | NullPointerException e)
                        {
                            setNextStage(ASK_ACTION);
                            break;
                        }
                        passTime(timeOffset, );
                        //Chance to be caught by classmates in hardcore mode
                        getCaughtByClassmates();
                        break;
                    //Cheat
                    case 6:
                        setText("You've got to go so bad!", "There must be something you can do, right?");
                        //Zeroing points
                        cheatsUsed = true;
                        setNextStage(ASK_CHEAT);
                        break;
                    case -1:
                    default:
                        setText("Bugs.");
                }
                break;
            //TODO: Refactor
            case ASK_TO_PEE:
                switch (timesPeeDenied)
                {
                    case 0:
                        //Success
                        if (NarrativeEngine.chance((byte) 40) & !hardcore)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                }
                            } else
                            {
                                if (!undies.isMissing())
                                {
                                    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
                                }
                            }
//                            score *= 0.2;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -80% of points");
                            score("Restroom usage during the lesson", '/', 5);
                            emptyBladder();
                            setNextStage(ASK_ACTION);
                            //Fail
                        } else
                        {
                            setText("You ask the teacher if you can go out to the restroom.", "No, you can't go out, the director prohibited it.", "says the teacher.");
                            timesPeeDenied++;
                        }
                        break;
                    case 1:
                        if (NarrativeEngine.RANDOM.nextInt(100) <= 10 & !hardcore)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                }
                            } else
                            {
                                if (!undies.isMissing())
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
                                }
                            }
//                            score *= 0.22;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -70% of points");
                            score("Restroom usage during the lesson", '/', 3.3F);
                            emptyBladder();
                            setNextStage(ASK_ACTION);
                        } else
                        {
                            setText("You ask the teacher again if you can go out to the restroom.", "No, you can't! I already told you that the director prohibited it!", "says the teacher.");
                            timesPeeDenied++;
                        }
                        break;
                    case 2:
                        if (NarrativeEngine.RANDOM.nextInt(100) <= 30 & !hardcore)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                }
                            } else
                            {
                                if (!undies.isMissing())
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
                                }
                            }
//                            score *= 0.23;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -60% of points");
                            score("Restroom usage during the lesson", '/', 2.5F);
                            emptyBladder();
                            setNextStage(ASK_ACTION);
                        } else
                        {
                            setText("You ask the teacher once more if you can go out to the restroom.", "No, you can't! Stop asking me or there will be consequences!", "says the teacher.");
                            timesPeeDenied++;
                        }
                        break;
                    case 3:
                        if (NarrativeEngine.RANDOM.nextInt(100) <= 7 & !hardcore)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                }
                            } else
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                                } else
                                {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
                                }
                            }
//                            score *= 0.3;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -50% of points");
                            score("Restroom usage during the lesson", '/', 2F);
                            emptyBladder();
                            setNextStage(ASK_ACTION);
                        } else
                        {
                            if (NarrativeEngine.RANDOM.nextBoolean())
                            {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.", "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,", "yells the teacher.");
                                cornered = true;
//                            score += 1.3 * (90 - min / 3);
//                            scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +" + 1.3 * (90 - min / 3) + " score");
                                score("Stayed on corner " + (90 - time) + " minutes", '+', 1.3F * (90 - time / 3));
                                offsetEmbarassment(5, );
                            } else
                            {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.", "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! YOU WILL WRITE LINES AFTER THE LESSON!!!,", "yells the teacher.");
                                offsetEmbarassment(5, );
                                stay = true;
                                timeBar.setMaximum(120);
//                            scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
//                            score *= 1.6;
                                score("Wrote lines after the lesson", '*', 1.6F);
                            }
                        }
                        timesPeeDenied++;
                        break;
                }
                setNextStage(ASK_ACTION);
                break;
            case ASK_CHEAT:
                listChoice.setListData(cheatList);
                showActionUI("Select a cheat:");
                setNextStage(CHOSE_CHEAT);
                break;
            case CHOSE_CHEAT:
                if (inappropriateSelection())
                {
                    setNextStage(ASK_CHEAT);
                    break;
                }
                switch (hideActionUI())
                {
                    case 0:
                        setText("You walk to the front corner of the classroom.");
                        cornered = true;
                        setNextStage(ASK_ACTION);
                        break;
                    case 1:
                        setText("You decide to stay after class.");
                        stay = true;
                        timeBar.setMaximum(120);
                        setNextStage(ASK_ACTION);
                        break;
                    case 2:
                        setText("You see something out of the corner of your eye,", "just within your reach.");
                        setNextStage(USE_BOTTLE);
                        break;
                    case 3:
                        setLinesAsDialogue(2);
                        setText("A voice comes over the loudspeaker:", "All classes are now dismissed for no reason at all! Bye!", "Looks like your luck changed for the better.");
                        time = 89;
                        setNextStage(CLASS_OVER);
                        break;
                    case 4:
                        setText("The teacher feels sorry for you. Try asking to pee.");
                        timesPeeDenied = 0;
                        stay = false;
                        timeBar.setMaximum(90);
                        cornered = false;
                        setNextStage(ASK_ACTION);
                        break;
                    case 5:
                        setText("You decide to raise your hand.");
                        setNextStage(CALLED_ON);
                        break;
                    case 6:
                        setText("Suddenly, you feel like you're peeing...", "but you don't feel any wetness. It's not something you'd", "want to question, right?");
                        drain = true;
                        setNextStage(ASK_ACTION);
                        break;
                    case 7:
                        setText("A friend in the desk next to you hands you a familiar", "looking pill, and you take it.");
                        askNewIncontinenceLevel();
                        setNextStage(ASK_ACTION);
                        break;
                    case 8:
                        setText("The teacher suddenly looks like they've had enough", "of people having to pee.");
                        hardcore = !hardcore;
                        setNextStage(ASK_ACTION);
                        break;
                    case 9:
                        setText("Suddenly you felt something going on in your bladder.");
                        askNewBladderLevel();
                        setNextStage(ASK_ACTION);
                        break;
                }
                break;
            case USE_BOTTLE:
                setLinesAsDialogue(3);
                setText("Luckily for you, you happen to have brought an empty bottle to pee in.", "As quietly as you can, you put it in position and let go into it.", "Ahhhhh...", "You can't help but show a face of pure relief as your pee trickles down into it.");
                emptyBladder();
                setNextStage(ASK_ACTION);
                break;
            case CALLED_ON:
                setLinesAsDialogue(1);
                setText("" + name + ", why don't you come up to the board and solve this problem?,", "says the teacher. Of course, you don't have a clue how to solve it.", "You make your way to the front of the room and act lost, knowing you'll be stuck", "up there for a while as the teacher explains it.", "Well, you can't dare to hold yourself now...");
                passTime((byte) 5, );
                score("Called on the lesson", '+', 5);
                setNextStage(ASK_ACTION);
                break;
            case CLASS_OVER:
                if (triggerClsasOverScene())
                {
                    break;
                }
                if (NarrativeEngine.RANDOM.nextBoolean())
                {
                    setText("Lesson is finally over, and you're running to the restroom as fast as you can.", "No, please... All cabins are occupied, and there's a line. You have to wait!");
                    score("Waited for a free cabin in the restroom", '+', 3);
                    passTime();
                    break;
                } else
                {
                    //TODO: Refactor
                    if (!lower.isMissing())
                    {
                        if (!undies.isMissing())
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
                        }
                    } else
                    {
                        if (!undies.isMissing())
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
                        }
                    }
                    setNextStage(END_GAME);
                }
                break;
            case AFTER_CLASS:
                if (linesAreTooLong())
                {
                    break;
                }
                setLinesAsDialogue(1, 2, 3, 4);
                setText("Hey, " + name + ", you wanted to escape? You must stay after classes!", "Please... let me go to the restroom... I can't hold it...", "No, " + name + ", you can't go to the restroom now! This will be as punishment.", "And don't think you can hold yourself either! I'm watching you...");
                passTime();
                break;
            case ACCIDENT:
                hideActionUI();
                setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.", "Despite all this, you try your best, but suddenly you're forced to stop.", "You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere.", "But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.");
                setNextStage(WET);
                break;
            case GIVE_UP:
                //TODO: Refactor
                if (!lower.isMissing())
                {
                    if (!undies.isMissing())
                    {
                        setText("You get tired of holding all the urine in your aching bladder,", "and you decide to give up and pee in your " + undies.insert() + ".");
                    } else
                    {
                        setText("You get tired of holding all the urine in your aching bladder,", "and you decided to pee in your " + lower.insert() + ".");
                    }
                } else
                {
                    if (!undies.isMissing())
                    {
                        setText("You get tired of holding all the urine in your aching bladder,", "and you decide to give up and pee in your " + undies.insert() + ".");
                    } else
                    {
                        setText("You get tired of holding all the urine in your aching bladder,", "and you decide to give up and pee where you are.");
                    }
                }
                offsetEmbarassment(80, );
                setNextStage(WET);
                break;
            case WET:
                if (!lower.isMissing())
                {
                    if (!undies.isMissing())
                    {
                        setText("Before you can move an inch, pee quickly soaks through your " + undies.insert() + ",", "floods your " + lower.insert() + ", and streaks down your legs.", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                    } else
                    {
                        setText("Before you can move an inch, pee quickly darkens your " + lower.insert() + " and streaks down your legs.", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                    }
                } else
                {
                    if (!undies.isMissing())
                    {
                        setText("Before you can move an inch, pee quickly soaks through your " + undies.insert() + ", and streaks down your legs.", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                    } else
                    {
                        if (!cornered)
                        {
                            setText("The heavy pee jets are hitting the seat and loudly leaking out from your " + undies.insert() + ".", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                        } else
                        {
                            setText("The heavy pee jets are hitting the floor and loudly leaking out from your " + undies.insert() + ".", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                        }
                    }
                }
                emptyBladder();
                embarassment = 100;
                setNextStage(POST_WET);
                break;
            case POST_WET:
                setLinesAsDialogue(2);
                if (!stay)
                {
                    if (lower.isMissing())
                    {
                        if (isFemale() && undies.isMissing())
                        {
                            setText("People around you are laughing loudly.", name + " peed herself! Ahaha!!!");
                        } else
                        {
                            if (isMale() && undies.isMissing())
                            {
                                setText("People around you are laughing loudly.", name + " peed himself! Ahaha!!!");
                            } else
                            {
                                setText("People around you are laughing loudly.", name + " wet h" + (isFemale() ? "er " : "is ") + undies.insert() + "! Ahaha!!");
                            }
                        }
                    } else
                    {
                        if (isFemale())
                        {
                            setText("People around you are laughing loudly.", name + " peed her " + lower.insert() + "! Ahaha!!");
                        } else
                        {
                            setText("People around you are laughing loudly.", " peed his " + lower.insert() + "! Ahaha!!");
                        }
                    }
                } else
                {
                    setText("Teacher is laughing loudly.", "Oh, you peed yourself? This is a great punishment.", "I hope you will no longer get in the way of the lesson.");
                }
                setNextStage(GAME_OVER);
                break;
            case GAME_OVER:
                if (lower.isMissing())
                {
                    if (undies.isMissing())
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
                    } else
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "Your " + undies.insert() + " are clinging to your skin, a sign of your failure...", "...unless, of course, you meant for this to happen?", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
                    }
                } else
                {
                    if (undies.isMissing())
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "Your " + lower.insert() + " is clinging to your skin, a sign of your failure...", //TODO: Add "is/are" depending on lower clothes type
                                "...unless, of course, you meant for this to happen?", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
                    } else
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "Your " + lower.insert() + " and " + undies.insert() + " are both clinging to your skin, a sign of your failure...", "...unless, of course, you meant for this to happen?", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
                    }
                }
                gameOver();
                break;
            case END_GAME:
                showScore();
                gameOver();
                break;
            case CAUGHT:
                switch (timesCaught)
                {
                    case 0:
                        setText("It looks like a classmate has spotted that you've got to go badly.", "Damn, he may spread that fact...");
                        offsetEmbarassment(3, );
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 3);
                        timesCaught++;
                        break;
                    case 1:
                        setLinesAsDialogue(3);
                        setText("You'he heard a suspicious whisper behind you.", "Listening to the whisper, you've found out that they're saying that you need to go.", "If I hold it until the lesson ends, I will beat them.");
                        offsetEmbarassment(8, );
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 8);
                        timesCaught++;
                        break;
                    case 2:
                        if (isFemale())
                        {
                            setLinesAsDialogue(2);
                            setText("The most handsome boy in your class, " + boyName + ", is calling you:", "Hey there, don't wet yourself!", "Oh no, he knows it...");
                        } else
                        {
                            setLinesAsDialogue(2, 3);
                            setText("The most nasty boy in your class, " + boyName + ", is calling you:", "Hey there, don't wet yourself! Ahahahaa!", "\"Shut up...\"", ", you think to yourself.");
                        }
                        offsetEmbarassment(12, );
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 12);
                        timesCaught++;
                        break;
                    default:
                        setText("The chuckles are continiously passing over the classroom.", "Everyone is watching you.", "Oh god... this is so embarassing...");
                        offsetEmbarassment(20, );
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 20);
                        timesCaught++;
                }
                setNextStage(ASK_ACTION);
                break;
            //The special hardcore scene
            
//         * "Surprise" is an additional scene after the lesson where player is being caught by her classmate. He wants her to wet herself.
//         * Triggering conditions: female, hardcore
//         * Triggering chance: 10%
             
            case SURPRISE:
                //Resetting timesPeeDenied to use for that boy
                timesPeeDenied = 0;
                specialHardcoreStage = true;
                score("Got the \"surprise\" by " + boyName, '+', 70);
                setText("The lesson is finally over, and you're running to the restroom as fast as you can.", "But... You see " + boyName + " staying in front of the restroom.", "Suddenly, he takes you, not letting you to escape.");
                offsetEmbarassment(10, );
                setNextStage(SURPRISE_2);
                break;
            case SURPRISE_2:
                setLinesAsDialogue(1);
                setText("What do you want from me?!", "He has brought you in the restroom and quickly put you on the windowsill.", boyName + " has locked the restroom door (seems he has stolen the key), then he puts his palm on your belly and says:", "I want you to wet yourself.");
                offsetEmbarassment(10, );
                setNextStage(SURPRISE_DIALOGUE);
                break;
            case SURPRISE_DIALOGUE:
                setLinesAsDialogue(1);
                setText("No, please, don't do it, no...", "I want to see you wet...", "He slightly presses your belly again, you shook from the terrible pain", "in your bladder and subconsciously rubbed your crotch. You have to do something!");
                offsetEmbarassment(10, );
                actionList.add("Hit him");
                switch (timesPeeDenied)
                {
                    case 0:
                        actionList.add("Try to persuade him to let you pee");
                        break;
                    case 1:
                        actionList.add("Try to persuade him to let you pee again");
                        break;
                    case 2:
                        actionList.add("Take a chance and try to persuade him (RISKY)");
                        break;
                }
                actionList.add("Pee yourself");
                listChoice.setListData(actionList.toArray());
                showActionUI("Don't let him to do it!");
                setNextStage(SURPRISE_CHOSE);
                break;
            case SURPRISE_CHOSE:
                if (listChoice.isSelectionEmpty())
                {
                    //No idling
                    setText("You will wet yourself right now,", boyName + " demands.", "Then " + boyName + " presses your bladder...");
                    setNextStage(SURPRISE_WET_PRESSURE);
                }
//                actionNum = listChoice.getSelectedIndex();
                if (listChoice.getSelectedValue().equals("[Unavailable]"))
                {
                    //No idling
                    setText("You will wet yourself right now,", boyName + " demands.", "Then " + boyName + " presses your bladder...");
                    setNextStage(SURPRISE_WET_PRESSURE);
                }
                switch (hideActionUI())
                {
                    case 0:
                        setNextStage(HIT);
                        break;
                    case 1:
                        setNextStage(PERSUADE);
                        break;
                    case 2:
                        setNextStage(SURPRISE_WET_VOLUNTARY);
                }
                break;
            case HIT:
                if (NarrativeEngine.RANDOM.nextInt(100) <= 20)
                {
                    setLinesAsDialogue(2);
                    setNextStage(END_GAME);
                    score("Successful hit on " + boyName + "'s groin", '+', 40F);
                    if (!lower.isMissing())
                    {
                        if (!undies.isMissing())
                        {
                            setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
                        }
                    } else
                    {
                        if (!undies.isMissing())
                        {
                            setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it,", "wearily flop down on the toilet and start peeing.");
                        }
                    }
                } else
                {
                    setNextStage(SURPRISE_WET_PRESSURE);
                    setLinesAsDialogue(2, 3);
                    setText("You hit " + boyName + "'s hand. Damn, you'd meant to hit his groin...", "You're braver than I expected;", "now let's check the strength of your bladder!", boyName + " pressed your bladder violently...");
                }
                break;
            case PERSUADE:
                switch (timesPeeDenied)
                {
                    case 0:
                        if (NarrativeEngine.RANDOM.nextInt(100) <= 10)
                        {
                            setLinesAsDialogue(1);
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + " and " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            } else
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            }
                            score("Persuaded " + boyName + " to pee", '+', 40);
                            emptyBladder();
                            setNextStage(END_GAME);
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee.", "No, you can't pee in a cabin. I want you to wet yourself.,", boyName + " says.");
                            timesPeeDenied++;
                            setNextStage(SURPRISE_DIALOGUE);
                        }
                        break;
                    case 1:
                        if (NarrativeEngine.RANDOM.nextInt(100) <= 5)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + " and " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            } else
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            }
                            score("Persuaded " + boyName + " to pee", '+', 60);
                            emptyBladder();
                            setNextStage(END_GAME);
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee again.", "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.", boyName + " demands.");
                            timesPeeDenied++;
                            setNextStage(SURPRISE_DIALOGUE);
                        }
                        break;
                    case 2:
                        if (NarrativeEngine.RANDOM.nextInt(100) <= 2)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + " and " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            } else
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            }
                            score("Persuaded " + boyName + " to pee", '+', 80);
                            emptyBladder();
                            setNextStage(END_GAME);
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee again desperately.", "No, you can't pee in a cabin. You will wet yourself right now,", boyName + " demands.", "Then " + boyName + " pressed your bladder...");
                            setNextStage(SURPRISE_WET_PRESSURE);
                        }
                        break;
                }
                break;
            case SURPRISE_WET_VOLUNTARY:
                setLinesAsDialogue(1, 3);
                setText("Alright, as you say.,", "you say to " + boyName + " with a defeated sigh.", "Whatever, I really can't hold it anymore anyways...");
                emptyBladder();
                setNextStage(SURPRISE_WET_VOLUNTARY2);
                break;
            case SURPRISE_WET_VOLUNTARY2:
                if (!undies.isMissing())
                {
                    if (!lower.isMissing())
                    {
                        setText("You feel the warm pee stream", "filling your " + undies.insert() + " and darkening your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("You feel the warm pee stream", "filling your " + undies.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    }
                } else
                {
                    if (!lower.isMissing())
                    {
                        setText("You feel the warm pee stream", "filling your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("You feel the warm pee stream", "running down your legs.", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    }
                }
                emptyBladder();
                setNextStage(END_GAME);
                break;
            case SURPRISE_WET_PRESSURE:
                if (!undies.isMissing())
                {
                    if (!lower.isMissing())
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "filling your " + undies.insert() + " and darkening your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "filling your " + undies.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    }
                } else
                {
                    if (!lower.isMissing())
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "filling your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "running down your legs.", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
                    }
                }
                emptyBladder();
                setNextStage(END_GAME);
                break;
            case DRINK:
                setText("You take your bottle with water,", "open it and take a small sip of water.");
                offsetBelly(thirst, );
                thirst = 0;
                setNextStage(ASK_ACTION);
                break;
            default:
                setText("Error parsing button. Next text is unavailable, text #" + nextStage);
                break;
            //case template
            //      case 4:
            //   setText("");
            //   setNextStage(;
            //   break; 
        }
    }
     */
    /**
     *
     * @param the value of
     */
    void update()
    {
        try
        {
            lblName.setText(name);
            lblBladder.setText("Bladder: " + Math.round(fulness) + "%");
            lblEmbarassment.setText("Embarassment: " + embarassment);
            lblBelly.setText("Belly: " + Math.round(belly) + "%");
            lblIncon.setText("Incontinence: " + incontinence + "x");
            lblMinutes.setText("Minutes: " + time + " of 90");
            lblSphPower.setText("Pee holding ability: " + Math.round(sphincterPower) + "%");
            lblDryness.setText("Clothes dryness: " + Math.round(dryness));
            lblUndies.setText("Undies: " + undies.getColor() + " " + undies.getName().toLowerCase());
            lblLower.setText("Lower: " + lower.getColor() + " " + lower.getName().toLowerCase());
            bladderBar.setValue(fulness);
            sphincterBar.setValue(Math.round(sphincterPower));
            drynessBar.setValue((int) dryness);
            timeBar.setValue(time);
            lblThirst.setText("Thirst: " + Math.round(thirst) + "%");
            thirstBar.setValue((int) thirst);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void gameOver()
    {
        btnNext.setVisible(false);
    }

    /**
     *
     * @param the value of
     * @return the byte
     */
    byte hideActionUI()
    {
        byte choice = (byte) listChoice.getSelectedIndex();
        actionList.clear();
        lblChoice.setVisible(false);
        listScroller.setVisible(false);
        return choice;
    }

    /**
     *
     * @return the byte
     * @throws NumberFormatException
     */
    byte askPlayerHowMuchToWait() throws NumberFormatException
    {
        byte timeOffset;
        timeOffset = Byte.parseByte(JOptionPane.showInputDialog("How much to wait?"));
        if (time < 1 || time > 125)
        {
            throw new NumberFormatException();
        }
        return timeOffset;
    }

    /**
     *
     * @param the value of
     * @throws HeadlessException
     * @throws NumberFormatException
     */
    private void askNewIncontinenceLevel() throws HeadlessException, NumberFormatException
    {
        float newIncontinence = Float.parseFloat(JOptionPane.showInputDialog("How incontinent are you now?"));
        if (newIncontinence >= 0.1)
        {
            incontinence = newIncontinence;
            maxSphincterPower = (short) (100 / incontinence);
            sphincterPower = maxSphincterPower;
        }
    }

    void setup()
    {
        //Game window setup
        setResizable(true);
        setTitle("A Long Hour and a Half");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 770, 594);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //Text panel setup
        textPanel = new JPanel();
        textPanel.setBounds(10, 11, 740, 150);
        contentPane.add(textPanel);
        textPanel.setLayout(null);
        textLabel = new JLabel("");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setBounds(0, 0, 740, 150);
        textPanel.add(textLabel);
        //"Next" button setup
        btnNext = new JButton("Next");
        //        btnNext.setToolTipText("Hold down for the time warp");
        btnNext.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                //TODO: Handle "Next" button clicks
            }
        });
        btnNext.setBounds(470, UI.ACTION_BUTTONS_TOP_BORDER, 285, 35);
        contentPane.add(btnNext);
        //"Quit" button setup
        btnQuit = new JButton("Quit");
        btnQuit.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                System.exit(0);
            }
        });
        btnQuit.setBounds(192, UI.ACTION_BUTTONS_TOP_BORDER, UI.ACTION_BUTTONS_WIDTH, UI.ACTION_BUTTONS_HEIGHT);
        contentPane.add(btnQuit);
        //"Save" button setup
        btnSave = new JButton("Save");
        btnSave.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.save(UI.this);
            }
        });
        btnSave.setBounds(284, UI.ACTION_BUTTONS_TOP_BORDER, UI.ACTION_BUTTONS_WIDTH, UI.ACTION_BUTTONS_HEIGHT);
        contentPane.add(btnSave);
        //"Load" button setup
        btnLoad = new JButton("Load");
        btnLoad.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.load(UI.this);
            }
        });
        btnLoad.setBounds(376, UI.ACTION_BUTTONS_TOP_BORDER, UI.ACTION_BUTTONS_WIDTH, UI.ACTION_BUTTONS_HEIGHT);
        contentPane.add(btnLoad);
        //"Reset" button setup
        btnReset = new JButton("Reset");
        btnReset.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.reset(false);
                dispose();
            }
        });
        btnReset.setBounds(10, UI.ACTION_BUTTONS_TOP_BORDER, UI.ACTION_BUTTONS_WIDTH, UI.ACTION_BUTTONS_HEIGHT);
        btnReset.setToolTipText("Start the game over with the same parameters.");
        contentPane.add(btnReset);
        //"New game" button setup
        btnNewGame = new JButton("New game");
        btnNewGame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.reset(true);
                dispose();
            }
        });
        btnNewGame.setBounds(102, UI.ACTION_BUTTONS_TOP_BORDER, UI.ACTION_BUTTONS_WIDTH, UI.ACTION_BUTTONS_HEIGHT);
        btnNewGame.setToolTipText("Start the game over with the another parameters.");
        contentPane.add(btnNewGame);
        //Name label setup
        lblName = new JLabel(NarrativeEngine.name);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblName.setBounds(20, 170, 200, 32);
        contentPane.add(lblName);
        //Bladder label setup
        lblBladder = new JLabel("Bladder: " + Math.round(Bladder.fulness) + "%");
        lblBladder.setToolTipText("<html>Normal game:<br>100% = need to hold, regular leaks<br>130% = peeing(game over)<br><br>Hardcore:<br>80% = need to hold, regular leaks<br>100% = peeing(game over)</html>");
        lblBladder.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBladder.setBounds(20, 210, 200, 32);
        contentPane.add(lblBladder);
        lblDefaultColor = lblBladder.getForeground();
        //Embarassment label setup
        lblEmbarassment = new JLabel("Embarassment: " + embarassment);
        lblEmbarassment.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmbarassment.setBounds(20, 240, 200, 32);
        lblEmbarassment.setToolTipText("Makes leaks more frequent");
        contentPane.add(lblEmbarassment);
        //Belly label setup
        lblBelly = new JLabel("Belly: " + Math.round(belly) + "%");
        lblBelly.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBelly.setBounds(20, 270, 200, 32);
        lblBelly.setToolTipText("<html>The water in your belly.<br>Any amount of water speeds the bladder filling up.</html>");
        contentPane.add(lblBelly);
        //Thirst label setup
        lblThirst = new JLabel("Thirst: " + Math.round(thirst) + "%");
        lblThirst.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblThirst.setBounds(20, 480, 200, 32);
        lblThirst.setToolTipText("Character will automatically drink water at 30% of thirst.");
        if (hardcore)
        {
            contentPane.add(lblThirst);
        }
        //Thirst bar setup
        thirstBar = new JProgressBar();
        thirstBar.setBounds(16, 482, 455, 25);
        thirstBar.setMaximum((int) Bladder.MAXIMAL_THIRST);
        thirstBar.setValue((int) thirst);
        thirstBar.setToolTipText("Character will automatically drink water at 30% of thirst.");
        if (hardcore)
        {
            contentPane.add(thirstBar);
        }
        //Incontinence label setup
        lblIncon = new JLabel("Incontinence: " + incontinence + "x");
        lblIncon.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblIncon.setBounds(20, 300, 200, 32);
        lblIncon.setToolTipText("Makes your bladder weaker");
        contentPane.add(lblIncon);
        //Time label setup
        lblMinutes = new JLabel("Minutes: " + time + " of 90");
        lblMinutes.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMinutes.setBounds(20, 330, 200, 32);
        lblMinutes.setVisible(false);
        contentPane.add(lblMinutes);
        //Sphincter power label setup
        lblSphPower = new JLabel("Pee holding ability: " + Math.round(sphincterPower) + "%");
        lblSphPower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSphPower.setBounds(20, 360, 200, 32);
        lblSphPower.setVisible(false);
        lblSphPower.setToolTipText("<html>Ability to hold pee.<br>Drains faster on higher bladder fulnesses.<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thigs.</html>");
        contentPane.add(lblSphPower);
        //Clothing dryness label setup
        lblDryness = new JLabel("Clothes dryness: " + Math.round(dryness));
        lblDryness.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblDryness.setBounds(20, 390, 200, 32);
        lblDryness.setVisible(false);
        lblDryness.setToolTipText("<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>");
        contentPane.add(lblDryness);
        //Choice label ("What to do?") setup
        lblChoice = new JLabel();
        lblChoice.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblChoice.setBounds(480, 162, 280, 32);
        lblChoice.setVisible(false);
        contentPane.add(lblChoice);
        //Action list setup
        listChoice = new JList<>();
        listChoice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listChoice.setLayoutOrientation(JList.VERTICAL);
        listScroller = new JScrollPane(listChoice);
        listScroller.setBounds(472, 194, 280, 318);
        listScroller.setVisible(false);
        contentPane.add(listScroller);
        //Bladder bar setup
        bladderBar = new JProgressBar();
        bladderBar.setBounds(16, 212, 455, 25);
        bladderBar.setMaximum(130);
        bladderBar.setValue(Bladder.fulness);
        bladderBar.setToolTipText("<html>Normal game:<br>100% = need to hold, regular leaks<br>130% = peeing(game over)<br><br>Hardcore:<br>80% = need to hold, regular leaks<br>100% = peeing(game over)</html>");
        contentPane.add(bladderBar);
        //Sphincter bar setup
        sphincterBar = new JProgressBar();
        sphincterBar.setBounds(16, 362, 455, 25);
        sphincterBar.setMaximum(Math.round(maxSphincterPower));
        sphincterBar.setValue(Math.round(sphincterPower));
        sphincterBar.setVisible(false);
        sphincterBar.setToolTipText("<html>Ability to hold pee.<br>Drains faster on higher bladder fulnesses.<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thigs.</html>");
        contentPane.add(sphincterBar);
        //Dryness bar setup
        drynessBar = new JProgressBar();
        drynessBar.setBounds(16, 392, 455, 25);
        drynessBar.setValue((int) dryness);
        drynessBar.setMinimum(Bladder.MINIMAL_DRYNESS);
        drynessBar.setVisible(false);
        drynessBar.setToolTipText("<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>");
        contentPane.add(drynessBar);
        //Time bar setup
        timeBar = new JProgressBar();
        timeBar.setBounds(16, 332, 455, 25);
        timeBar.setMaximum(90);
        timeBar.setValue(time);
        timeBar.setVisible(false);
        contentPane.add(timeBar);
        //Coloring the name label according to the gender
        if (NarrativeEngine.isFemale())
        {
            lblName.setForeground(Color.MAGENTA);
        }
        else
        {
            lblName.setForeground(Color.CYAN);
        }
        //Assigning the blank name if player didn't selected the name
        if (name.isEmpty())
        {
            if (isFemale())
            {
                name = "Mrs. Nobody";
            }
            else
            {
                name = "Mr. Nobody";
            }
        }
    }

    void setupWearLabels()
    {
        //Undies label setup
        lblUndies = new JLabel("Undies: " + undies.getColor() + " " + undies.getName().toLowerCase());
        lblUndies.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblUndies.setBounds(20, 420, 400, 32);
        contentPane.add(lblUndies);
        //Lower label setup
        lblLower = new JLabel("Lower: " + lower.getColor() + " " + lower.getName().toLowerCase());
        lblLower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblLower.setBounds(20, 450, 400, 32);
        contentPane.add(lblLower);
    }

    void displayAllValues()
    {
        //Displaying all values
        lblMinutes.setVisible(true);
        lblSphPower.setVisible(true);
        lblDryness.setVisible(true);
        sphincterBar.setVisible(true);
        drynessBar.setVisible(true);
        timeBar.setVisible(true);
        handleNextClicked();
    }
}

class ResetParametersStorage
{
    private static final long serialVersionUID = 1;

    static String underColorParam;
    static String outerColorParam;
    static String outerParam;
    //Parameters used for a game reset
    static String nameParam;
    static short bladderParam;
    static Gender gndrParam;
    static String underParam;
    static boolean diffParam;
    static float incParam;
}

@SuppressWarnings("serial")
public class GameCore
{

    /**
     * Resets the game and values, optionally letting player to select new
     * parameters.
     *
     * @param newValues
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    static void reset(boolean newValues)
    {
        if (newValues)
        {
            new setupFramePre().setVisible(true);
        }
        else
        {
            new GameCore(ResetParametersStorage.nameParam, ResetParametersStorage.gndrParam, ResetParametersStorage.diffParam, ResetParametersStorage.incParam, ResetParametersStorage.bladderParam, ResetParametersStorage.underParam, ResetParametersStorage.outerParam, ResetParametersStorage.underColorParam, ResetParametersStorage.outerColorParam, new UI());
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
    void preConstructor(String name, Gender gndr, boolean diff, float inc, short bladder, UI ui)
    {
        stashParametersForReset();

        assignFieldValuesFromParameters(name, gndr, diff, inc, bladder);

        setupFileChoosers(ui);

        //Setting "No underwear" insert name depending on character's gender
        //May be gone soon
        if (isMale())
        {
            underwearList[1].setInsertName("penis");
        }
        else
        {
            underwearList[1].setInsertName("crotch");
        }

        ui.setup();

        //Scoring bladder at start
        NarrativeEngine.score("Bladder at start - " + bladder + "%", '+', Math.round(bladder));

        //Scoring incontinence
        score("Incontinence - " + Math.round(incontinence) + "x", '*', Math.round(incontinence));

        /*
                    Hardcore, it will be harder to hold pee:
                    Teacher will never let character to go pee
                    Character's bladder will have less capacity
                    Character may get caught holding pee
         */
        if (hardcore)
        {
            score("Hardcore", '*', 2F);
        }
    }

    private void setupFileChoosers(UI ui)
    {
        //Setting up custom wear file chooser
        ui.fcWear = new JFileChooser();
        ui.fcWear.setFileFilter(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                String extension = "";
                int i = pathname.getName().lastIndexOf('.');
                if (i > 0)
                {
                    extension = pathname.getName().substring(i + 1);
                }
                return extension.equals("lhhwear");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Custom wear";
            }
        });

        ui.fcGame = new JFileChooser();
        ui.fcGame.setFileFilter(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                String extension = "";
                int i = pathname.getName().lastIndexOf('.');
                if (i > 0)
                {
                    extension = pathname.getName().substring(i + 1);
                }
                return extension.equals("lhhsav");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Save game";
            }
        });
    }

    private void assignFieldValuesFromParameters(String name1, Gender gndr, boolean diff, float inc, short bladder)
    {
        //Assigning constructor parameters to values
        NarrativeEngine.name = name1;
        gender = gndr;
        hardcore = diff;
        incontinence = inc;
        fulness = bladder;
        maxSphincterPower = (short) Math.round(100 / incontinence);
        sphincterPower = maxSphincterPower;
        //Assigning the boy's name
        boyName = BOY_NAMES[NarrativeEngine.RANDOM.nextInt(BOY_NAMES.length)];
    }

    GameCore(String name, Gender gndr, boolean diff, float inc, short bladder, String under, String outer, String undiesColor, String lowerColor, UI ui)
    {
        preConstructor(name, gndr, diff, inc, bladder, ui);

        if (under.equals("Custom"))
        {
            ui.fcWear.setDialogTitle("Open an underwear file");
            if (ui.fcWear.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = ui.fcWear.getSelectedFile();
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    undies = (Wear) ois.readObject();
                    if (undies.getType() == OUTERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an underwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        ui.dispose();
                        setupFramePre.main(new String[0]);
                    }
                }
                catch (IOException | ClassNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
                    ui.dispose();
                    setupFramePre.main(new String[0]);
                }
            }
        }

        if (under.equals("Random"))
        {
            undies = underwearList[NarrativeEngine.RANDOM.nextInt(underwearList.length)];
            while (undies.getName().equals("Random"))
            //...selecting random undies from the undies array.
            {
                undies = underwearList[NarrativeEngine.RANDOM.nextInt(underwearList.length)];
            }
            //If random undies weren't chosen...
        }
        else
        {
            //We look for the selected undies in the array
            for (Wear iWear : underwearList)
            {
                //By comparing all possible undies' names with the selected undies string
                if (iWear.getName().equals(under))
                {
                    //If the selected undies were found, assigning current compared undies to the character's undies
                    undies = iWear;
                    break;
                }
            }
        }
        //If the selected undies weren't found
        if (undies == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect underwear selected. Setting random instead.", "Incorrect underwear", JOptionPane.WARNING_MESSAGE);
            undies = underwearList[NarrativeEngine.RANDOM.nextInt(underwearList.length)];
        }

        //Assigning color
        if (!undies.isMissing())
        {
            if (!undiesColor.equals("Random"))
            {
                undies.setColor(undiesColor);
            }
            else
            {
                undies.setColor(Wear.COLOR_LIST[NarrativeEngine.RANDOM.nextInt(Wear.COLOR_LIST.length)]);
            }
        }
        else
        {
            undies.setColor("");
        }

        if (outer.equals("Custom"))
        {
            ui.fcWear.setDialogTitle("Open an outerwear file");
            if (ui.fcWear.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = ui.fcWear.getSelectedFile();
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    lower = (Wear) ois.readObject();
                    if (lower.getType() == UNDERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an outerwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        ui.dispose();
                        setupFramePre.main(new String[0]);
                    }
                }
                catch (IOException | ClassNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
                    ui.dispose();
                    setupFramePre.main(new String[0]);
                }
            }
        }

        //Same with the lower clothes
        if (outer.equals("Random"))
        {
            lower = NarrativeEngine.OUTERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(OUTERWEAR_LIST.length)];
            while (lower.getName().equals("Random"))
            {
                lower = NarrativeEngine.OUTERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(OUTERWEAR_LIST.length)];
            }
        }
        else
        {
            for (Wear iWear : NarrativeEngine.OUTERWEAR_LIST)
            {
                if (iWear.getName().equals(outer))
                {
                    lower = iWear;
                    break;
                }
            }
        }
        if (lower == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect outerwear selected. Setting random instead.", "Incorrect outerwear", JOptionPane.WARNING_MESSAGE);
            lower = NarrativeEngine.OUTERWEAR_LIST[NarrativeEngine.RANDOM.nextInt(OUTERWEAR_LIST.length)];
        }

        //Assigning color
        if (!lower.isMissing())
        {
            if (!lowerColor.equals("Random"))
            {
                lower.setColor(lowerColor);
            }
            else
            {
                lower.setColor(Wear.COLOR_LIST[NarrativeEngine.RANDOM.nextInt(Wear.COLOR_LIST.length)]);
            }
        }
        else
        {
            lower.setColor("");
        }

        //Displaying all values
        ui.lblMinutes.setVisible(true);
        ui.lblSphPower.setVisible(true);
        ui.lblDryness.setVisible(true);
        ui.sphincterBar.setVisible(true);
        ui.drynessBar.setVisible(true);
        ui.timeBar.setVisible(true);

        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore)
        {
            maxBladder = 100;
            ui.lblName.setText(ui.lblName.getText() + " [Hardcore]");
        }
        //Starting the game
        setNextStage(LEAVE_BED);

        postConstructor(ui);
    }

    GameCore(Save save, UI ui)
    {
        preConstructor(save.name, save.gender, save.hardcore, save.incontinence, save.bladder, ui);
        undies = save.underwear;
        lower = save.outerwear;
        embarassment = save.embarassment;
        dryness = save.dryness;
        maxSphincterPower = save.maxSphincterPower;
        sphincterPower = save.sphincterPower;
        time = save.time;
        setNextStage(save.stage);
        score = save.score;
        scoreText = save.scoreText;
        timesPeeDenied = save.timesPeeDenied;
        timesCaught = save.timesCaught;
        classmatesAwareness = save.classmatesAwareness;
        stay = save.stay;
        cornered = save.cornered;
        drain = save.drain;
        cheatsUsed = save.cheatsUsed;
        boyName = save.boyName;

        ui.displayAllValues();
        postConstructor(ui);
    }

    void postConstructor(UI ui)
    {
        Bladder.calculateCaps(ui);

        stashParametersForReset();

        ui.setupWearLabels();

        initHardcoreMode(ui);

        ui.handleNextClicked();

        //Displaying the frame
        ui.setVisible(true);
    }

    private void initHardcoreMode(UI ui)
    {
        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore)
        {
            Bladder.maxBladder = 100;
            ui.lblName.setName(ui.lblName.getName() + " [Hardcore]");
        }
    }

    private void stashParametersForReset()
    {
        //TODO
        ResetParametersStorage.nameParam = NarrativeEngine.name;
        ResetParametersStorage.gndrParam = NarrativeEngine.gender;
        ResetParametersStorage.incParam = Bladder.incontinence;
        ResetParametersStorage.bladderParam = Bladder.fulness;
        ResetParametersStorage.outerParam = lower.getName();
        ResetParametersStorage.underParam = undies.getName();
        ResetParametersStorage.underColorParam = undies.getColor();
        ResetParametersStorage.outerColorParam = lower.getColor();
    }

    static void save(UI ui)
    {
        ui.fcGame.setSelectedFile(new File(name));
        if (ui.fcGame.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(ui.fcGame.getSelectedFile().getAbsolutePath() + ".lhhsav");
//            PrintStream writer;
            FileOutputStream fout;
            ObjectOutputStream oos;
            try
            {
                Save save = new Save();
                save.name = NarrativeEngine.name;
                save.gender = NarrativeEngine.gender;
                save.hardcore = NarrativeEngine.hardcore; //TODO
                save.incontinence = Bladder.incontinence;
                save.bladder = Bladder.fulness;
                save.underwear = Bladder.undies;
                save.outerwear = Bladder.lower;
                save.embarassment = Bladder.embarassment;
                save.dryness = Bladder.dryness;
                save.maxSphincterPower = Bladder.maxSphincterPower;
                save.sphincterPower = Bladder.sphincterPower;
                save.time = Bladder.time;
                save.stage = NarrativeEngine.getNextStage();
                save.score = NarrativeEngine.score;
                save.scoreText = NarrativeEngine.scoreText;
                save.timesPeeDenied = NarrativeEngine.timesPeeDenied;
                save.timesCaught = NarrativeEngine.timesCaught;
                save.classmatesAwareness = NarrativeEngine.classmatesAwareness;
                save.stay = NarrativeEngine.stay;
                save.cornered = NarrativeEngine.cornered;
                save.drain = NarrativeEngine.drain;
                save.cheatsUsed = NarrativeEngine.cheatsUsed;
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

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    static void load(UI ui)
    {
        if (ui.fcGame.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION)
        {
            File file = ui.fcGame.getSelectedFile();
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
}
