package omo;

import java.util.ArrayList;
import java.util.Random;
import static omo.Bladder.*;
import static omo.Gender.*;
import static omo.NarrativeEngine.GameStage.*;
import omo.ui.GameFrame;
import static omo.ui.GameFrame.MAX_LINES;

@SuppressWarnings("PackageVisibleField")
public class NarrativeEngine
{    
    //Random stuff generator
    public static final Random RANDOM = new Random();

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
    public static boolean hardcore = false;

    /**
     * An array that contains boolean values that define <i>dialogue lines</i>.
     * Dialogue lines, unlike normal lines, are <i>italic</i>.
     */
    public static boolean[] dialogueLines = new boolean[MAX_LINES];

    /**
     * Actions list.
     */
    public static ArrayList<String> actionList = new ArrayList<>();

    /**
     * Amount of embarassment raising every time character caught holding pee.
     */
    static short classmatesAwareness = 0;

    /**
     * Current character gender.
     */
    static Gender gender;

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
    public static String name;

    /**
     * Whether or not charecter has to stay 30 minutes after class.
     */
    static boolean stay = false;

    /**
     * Whether or not player has used cheats.
     */
    public static boolean cheatsUsed = false;

    /**
     * Number of times player got caught holding pee.
     */
    static byte timesCaught = 0;

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    static boolean cornered = false;

    /**
     * Text to be displayed after the game which shows how many {@link score}
     * did you get.
     */
    public static String scoreText = "";

    /**
     * A number that shows a game difficulty - the higher score, the harder was
     * the game. Specific actions (for example, peeing in a restroom during a
     * lesson) reduce score points. Using the cheats will zero the score points.
     */
    public static int score = 0;
    public static String[] getBladderDependingText(String[] empty, String[] firstUrge, String[] continuousUrges, String[] full, String[] bursting, String[] critical)
    {
        if(fulnessBetween((short)0, (short)20))
        {
            return empty;
        }
        if(fulnessBetween((short)20, (short)40))
        {
            return firstUrge;
        }
        if(fulnessBetween((short)40, (short)60))
        {
            return continuousUrges;
        }
        if(fulnessBetween((short)60, (short)80))
        {
            return full;
        }
        if(fulnessBetween((short)80, (short)100))
        {
            return bursting;
        }
        if(fulnessBetween((short)100, (short)130))
        {
            return critical;
        }
        return new String[0];
    }
    static GameStage getNextStage()
    {
        return nextStage;
    }
    static boolean chance(byte chance)
    {
        return RANDOM.nextInt(100) <= chance;
    }
    
    /**
     *
     * @param nextStage the value of nextStage
     */
    public static void setNextStage(GameStage nextStage)
    {
        NarrativeEngine.nextStage = nextStage;
    }
    /**
     *
     */
    public static void getCaughtByClassmates()
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
     * @param errorIndex the value of errorIndex
     */
    static String showError(byte errorIndex)
    {
        return "<b><i>" + ERRORS[errorIndex] + "@" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "</i></b>";
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
    public static boolean isFemale()
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

    public static boolean revealingLower()
    {
        return getLower().insert().equals("skirt") || getLower().insert().equals("skirt and tights") || getLower().insert().equals("skirt and tights");
    }
    /**
     *
     * @param lines the value of lines
     */
    public static void setLinesAsDialogue(int... lines)
    {
        for (int i : lines)
        {
            dialogueLines[i - 1] = true;
        }
    }
    /**
     * Returns in-game text depending on wear.
     *
     * @param bothWear the value of bothWear
     * @param lowerOnly the value of lowerOnly
     * @param undiesOnly the value of undiesOnly
     * @param noWear the value of noWear
     * @return in-game text depending on wear
     */
    public static String[] getWearDependentText(String[] bothWear, String[] lowerOnly, String[] undiesOnly, String[] noWear)
    {
        if (getLower().isMissing())
        {
            if (getUndies().isMissing())
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
            if (getUndies().isMissing())
            {
                return undiesOnly;
            }
            else
            {
                return noWear;
            }
        }
    }
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
    private final String[] askToPeeSuccessText =
    {
        "You ask the teacher if you can go out to the restroom.",
        "Yes, you may.",
        "says the teacher. You run to the restroom,",
        showError((byte) 1),
        "wearily flop down on the toilet and start peeing."
    };

    /**
     *
     * @return the boolean
     */
    private boolean linesAreTooLong()
    {
        if (getTime() >= 120)
        {
            stay = false;
            setNextStage(CLASS_OVER);
            return true;
        }
        return false;
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
     * @param the value of
     */
    private void displayDesperationStatus(GameFrame ui)
    {
        //Bladder: 0-20
        if (getFulness() <= 20)
        {
            ui.setText("Feeling bored about the day, and not really caring about the class too much,", "you look to the clock, watching the minutes tick by.");
        }
        //Bladder: 20-40
        if (getFulness() > 20 && getFulness() <= 40)
        {
            ui.setText("Having to pee a little bit,", "you look to the clock, watching the minutes tick by and wishing the lesson to get over faster.");
        }
        //Bladder: 40-60
        if (getFulness() > 40 && getFulness() <= 60)
        {
            ui.setText("Clearly having to pee,", "you impatiently wait for the lesson end.");
        }
        //Bladder: 60-80
        if (getFulness() > 60 && getFulness() <= 80)
        {
            setLinesAsDialogue(2);
            ui.setText("You feel the rather strong pressure in your bladder, and you're starting to get even more desperate.", "Maybe I should ask teacher to go to the restroom? It hurts a bit...");
        }
        //Bladder: 80-100
        if (getFulness() > 80 && getFulness() <= 100)
        {
            setLinesAsDialogue(1, 3);
            ui.setText("Keeping all that urine inside will become impossible very soon.", "You feel the terrible pain and pressure in your bladder, and you can almost definitely say you haven't needed to pee this badly in your life.", "Ouch, it hurts a lot... I must do something about it now, or else...");
        }
        //Bladder: 100-130
        if (getFulness() > 100 && getFulness() <= 130)
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
        if (getFulness() >= 100)
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
     * @return the boolean
     */
    private boolean gotCalledByTeacher(GameFrame ui)
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


    @Deprecated
    public enum GameStage
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