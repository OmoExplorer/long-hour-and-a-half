package omo;

import java.util.ArrayList;
import java.util.Random;
import static omo.Bladder.*;
import static omo.GameCore.STAGE_POOL;
import static omo.Gender.*;
import omo.stage.StageEngine;
import omo.ui.GameFrame;
import static omo.ui.GameFrame.MAX_LINES;

//TODO: Break down
//TODO: Remove old stage engine functinality
@SuppressWarnings("PackageVisibleField")
public class NarrativeEngine
{

    //Random stuff generator
    public static final Random RANDOM = new Random();

    /**
     * Times teacher denied character to go out.
     */
    private static byte timesPeeDenied = 0;

    /**
     * Whether or not hardcore mode enabled: teacher never lets you pee, it's
     * harder to hold pee, you may get caught holding pee
     */
    private static boolean hardcore = false;

    /**
     * An array that contains boolean values that define <i>dialogue lines</i>.
     * Dialogue lines, unlike normal lines, are <i>italic</i>.
     */
    private static boolean[] dialogueLines = new boolean[MAX_LINES];

    /**
     * Actions list.
     */
    private static ArrayList<String> actionList = new ArrayList<>();

    /**
     * Amount of embarassment raising every time character caught holding pee.
     */
    public static short classmatesAwareness = 0;

    /**
     * Current character gender.
     */
    private static Gender gender;

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
     * Special hardcore scene boy name.
     */
    public static String boyName = BOY_NAMES[RANDOM.nextInt(BOY_NAMES.length)];

    /**
     * Character's name.
     */
    private static String characterName;

    /**
     * Whether or not charecter has to stay 30 minutes after class.
     */
    public static boolean stay = false;

    /**
     * Number of times player got caught holding pee.
     */
    public static byte timesCaught = 0;

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    private static boolean cornered = false;

    /**
     * Text to be displayed after the game which shows how many {@link score}
     * did you get.
     */
    private static String scoreText = "";

    /**
     * A number that shows a game difficulty - the higher score, the harder was
     * the game. Specific actions (for example, peeing in a restroom during a
     * lesson) reduce score points.
     */
    private static int score = 0;

    public static final String ACTION_UNAVAILABLE = "[Unavailable]";

    /**
     * @return the timesPeeDenied
     */
    public static byte getTimesPeeDenied()
    {
        return timesPeeDenied;
    }

    /**
     * @param aTimesPeeDenied the timesPeeDenied to set
     */
    public static void setTimesPeeDenied(byte aTimesPeeDenied)
    {
        timesPeeDenied = aTimesPeeDenied;
    }

    public static String[] getBladderDependingText(String[] empty, String[] firstUrge, String[] continuousUrges, String[] full, String[] bursting, String[] critical)
    {
        if (fulnessBetween((short) 0, (short) 20))
        {
            return empty;
        }
        if (fulnessBetween((short) 20, (short) 40))
        {
            return firstUrge;
        }
        if (fulnessBetween((short) 40, (short) 60))
        {
            return continuousUrges;
        }
        if (fulnessBetween((short) 60, (short) 80))
        {
            return full;
        }
        if (fulnessBetween((short) 80, (short) 100))
        {
            return bursting;
        }
        if (fulnessBetween((short) 100, (short) 130))
        {
            return critical;
        }
        return new String[0];
    }

    public static boolean chance(byte chance)
    {
        return RANDOM.nextInt(100) <= chance;
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
                setScore((int) (getScore() + points));
                setScoreText(getScoreText() + "\n" + message + ": +" + points + " points");
                break;
            case '-':
                setScore((int) (getScore() - points));
                setScoreText(getScoreText() + "\n" + message + ": -" + points + " points");
                break;
            case '*':
                setScore((int) (getScore() * points));
                setScoreText(getScoreText() + "\n" + message + ": +" + points * 100 + "% of points");
                break;
            case '/':
                setScore((int) (getScore() / points));
                setScoreText(getScoreText() + "\n" + message + ": -" + 100 / points + "% of points");
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
        return getGender() == FEMALE;
    }

    /**
     * @return TRUE - if character's gender is male<br>FALSE - if character's
     * gender is female
     */
    public static boolean isMale()
    {
        return getGender() == MALE;
    }

    public static boolean isLowerRevealing()
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
            getDialogueLines()[i - 1] = true;
        }
    }

    /**
     * Returns in-game text depending on wear.
     *
     * @param bothWear the value of bothWear
     * @param lowerOnly the value of lowerOnly
     * @param undiesOnly the value of undiesOnly
     * @param noWear the value of noWear
     *
     * @return in-game text depending on wear
     */
    public static String[] getWearDependentText(String[] bothWear, String[] lowerOnly, String[] undiesOnly, String[] noWear)
    {
        if (getLower().isMissing())
        {
            if (getUndies().isMissing())
            {
                return bothWear;
            } else
            {
                return lowerOnly;
            }
        } else
        {
            if (getUndies().isMissing())
            {
                return undiesOnly;
            } else
            {
                return noWear;
            }
        }
    }

    public static void scoreBeginning(short bladder)
    {
        //Scoring bladder at start
        score("Bladder at start - " + bladder + "%", '+', Math.round(bladder));
        //Scoring incontinence
        score("Incontinence - " + Math.round(getIncontinence()) + "x", '*', Math.round(getIncontinence()));
    }
    //    public enum GameStage
//    {
//        LEAVE_BED,
//        LEAVE_HOME,
//        GO_TO_CLASS,
//        WALK_IN,
//        SIT_DOWN,
//        ASK_ACTION,
//        CHOSE_ACTION,
//        ASK_TO_PEE,
//        CALLED_ON,
//        CAUGHT,
//        USE_BOTTLE,
//        ASK_CHEAT,
//        CHOSE_CHEAT,
//        CLASS_OVER,
//        AFTER_CLASS,
//        ACCIDENT,
//        GIVE_UP,
//        WET,
//        POST_WET,
//        GAME_OVER,
//        END_GAME,
//        SURPRISE,
//        SURPRISE_2,
//        SURPRISE_ACCIDENT,
//        SURPRISE_DIALOGUE,
//        SURPRISE_CHOSE,
//        HIT,
//        PERSUADE,
//        SURPRISE_WET_VOLUNTARY,
//        SURPRISE_WET_VOLUNTARY2,
//        SURPRISE_WET_PRESSURE,
//        DRINK
//    }

    /**
     * @return the cornered
     */
    public static boolean isCornered()
    {
        return cornered;
    }

    /**
     * @param aCornered the cornered to set
     */
    public static void setCornered(boolean cornered)
    {
        NarrativeEngine.cornered = cornered;
    }

    /**
     * @return the hardcore
     */
    public static boolean isHardcore()
    {
        return hardcore;
    }

    /**
     * @param aHardcore the hardcore to set
     */
    public static void setHardcore(boolean hardcore)
    {
        NarrativeEngine.hardcore = hardcore;
    }

    /**
     * @return the dialogueLines
     */
    public static boolean[] getDialogueLines()
    {
        return dialogueLines;
    }

    /**
     * @param aDialogueLines the dialogueLines to set
     */
    public static void setDialogueLines(boolean[] dialogueLines)
    {
        NarrativeEngine.dialogueLines = dialogueLines;
    }

    /**
     * @return the actionList
     */
    public static ArrayList<String> getActionList()
    {
        return actionList;
    }

    /**
     * @param actionList the action list to set
     */
    public static void setActionList(ArrayList<String> actionList)
    {
        NarrativeEngine.actionList = actionList;
    }

    /**
     * @return the name
     */
    public static String getCharacterName()
    {
        return characterName;
    }

    /**
     * @param characterName the name to set
     */
    public static void setCharacterName(String characterName)
    {
        NarrativeEngine.characterName = characterName;
    }

    /**
     * @return the scoreText
     */
    public static String getScoreText()
    {
        return scoreText;
    }

    /**
     * @param aScoreText the scoreText to set
     */
    public static void setScoreText(String scoreText)
    {
        NarrativeEngine.scoreText = scoreText;
    }

    /**
     * @return the score
     */
    public static int getScore()
    {
        return score;
    }

    /**
     * @param aScore the score to set
     */
    public static void setScore(int score)
    {
        NarrativeEngine.score = score;
    }

    //    private final String[] askToPeeSuccessText =
//    {
//        "You ask the teacher if you can go out to the restroom.",
//        "Yes, you may.",
//        "says the teacher. You run to the restroom,",
//        showError((byte) 1),
//        "wearily flop down on the toilet and start peeing."
//    };

    /**
     *
     * @return the boolean
     */
    private boolean linesAreTooLong()
    {
        if (getTime() >= 120)
        {
            stay = false;
            StageEngine.rotatePlot(STAGE_POOL.classOver);
            return true;
        }
        return false;
    }

    /**
     *
     * @param the value of
     *
     * @return the boolean
     */
    private void triggerClassOverScene()
    {
        //Special hardcore scene trigger
        if (RANDOM.nextInt(100) <= 10 && isHardcore() & isFemale())
        {
            StageEngine.rotatePlot(STAGE_POOL.surprise);
        }
        if (stay)
        {
            StageEngine.rotatePlot(STAGE_POOL.writeLines);
        }
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
            } else
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
        switch (getTimesPeeDenied())
        {
            case 0:
                getActionList().add("Ask the teacher to go pee");
                break;
            case 1:
                getActionList().add("Ask the teacher to go pee again");
                break;
            case 2:
                getActionList().add("Try to ask the teacher again");
                break;
            case 3:
                getActionList().add("Take a chance and ask the teacher (RISKY)");
                break;
            default:
                getActionList().add(ACTION_UNAVAILABLE);
        }
        if (!isCornered())
        {
            if (isFemale())
            {
                getActionList().add("Press on your crotch");
            } else
            {
                getActionList().add("Squeeze your penis");
            }
        } else
        {
            getActionList().add(ACTION_UNAVAILABLE);
        }
        getActionList().add("Rub thighs");
        if (getFulness() >= 100)
        {
            getActionList().add("Give up and pee yourself");
        } else
        {
            getActionList().add(ACTION_UNAVAILABLE);
        }
        if (isHardcore())
        {
            getActionList().add("Drink water");
        } else
        {
            getActionList().add(ACTION_UNAVAILABLE);
        }
        getActionList().add("Just wait");
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
        } else
        {
            return maleText;
        }
    }

    /**
     * @return the gender
     */
    public static Gender getGender()
    {
        return gender;
    }

    /**
     * @param aGender the gender to set
     */
    public static void setGender(Gender aGender)
    {
        gender = aGender;
    }

}
