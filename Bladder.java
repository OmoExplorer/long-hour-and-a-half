package omo;

import java.awt.Color;
import static omo.NarrativeEngine.*;
import static omo.stage.StageEngine.*;
import omo.ui.GameFrame;
import static omo.ui.GameFrame.*;
import static omo.GameCore.STAGE_POOL;

/**
 * Static class which provides bladder simulation functionality and stores the
 * bladder data.
 *
 * @author JavaBird
 */
public class Bladder
{
    /**
     * Current bladder fulness.
     */
    private static short fulness;

    /**
     * Dynamic maximal bladder fulness. Being decreased by a wear pressure.
     */
    private static short maxFulness = 130;

    /**
     * Maximal bladder fulness in normal mode.
     */
    public static final int MAX_FULNESS_NORMAL_MODE = 130;

    /**
     * Maximal bladder fulness in hardcore mode.
     */
    public static final int MAX_FULNESS_HARDCORE = 100;

    /**
     * Current sphincter power.
     */
    private static short sphincterPower;

    /**
     * Maximal time without squirming and leaking.
     */
    private static short maxSphincterPower;

    /**
     * Current amount of a water in a belly.
     */
    private static double belly;

    /**
     * Current amount of pee that clothes can store.
     */
    private static float dryness;

    /**
     * The dryness minimal threshold. If dryness reaches this value, game is
     * over.
     */
    public static final int MINIMAL_DRYNESS = 0;

    /**
     * Maximal dryness limit.
     */
    private static float maxDryness;

    /**
     * The class time in minutes.
     */
    private static byte time = 0;

    /**
     * Amount of the character thirstiness. Used only in hardcore mode.
     */
    private static float thirst = 0;

    /**
     * Maximal allowed thirst level. Character will drink water if thirst is
     * past this value.
     */
    public final static float MAXIMAL_THIRST = 30;

    /**
     * Makes the wetting chance higher after reaching 100% of the bladder
     * fulness.
     */
    private static short embarassment;

    /**
     * Before 1.1:<br>
     * simply multiplies a bladder increasement speed.<br>
     * <br>
     * 1.1 and after:<br>
     * defines the sphincter weakening speed.
     */
    private static float incontinence;

    /**
     * Character's undies.
     */
    private static Wear undies;

    /**
     * List of all underwear types.
     */
    static final Wear[] UNDERWEAR_LIST =
    {
        //        Name      Insert name     Pressure, Absotption, Drying over time
        new Wear("Random", showError((byte) 0), 0, 0, 0),
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
     * Character's lower body clothing.
     */
    private static Wear lower;

    /**
     * List of all outerwear types.
     */
    static final Wear[] OUTERWEAR_LIST =
    { //        Name      Insert name     Pressure, Absotption, Drying over time
        new Wear("Random", NarrativeEngine.showError((byte) 0), 0, 0, 0),
        new Wear("No outerwear", NarrativeEngine.showError((byte) 0), 0, 0, 1),
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
     * Offsets the time by a specified amount.
     *
     * @param ui {@link GameFrame} object to update values
     * @param amount the offset value. May be negative to decrease time
     */
    public static void offsetTime(GameFrame ui, int amount)
    {
        setTime((byte) (getTime() + amount));
        dryClothesOverTime(amount);
        ui.update();
    }

    /**
     * Restores clothes' dryness over the time.
     *
     * @param amount time amount
     */
    private static void dryClothesOverTime(int amount)
    {
        //Clothes drying over time
        if (getDryness() < maxDryness)
        {
            setDryness(getDryness() + getLower().getDryingOverTime() + getUndies().getDryingOverTime() * (amount / 3));
        }
        if (getDryness() > maxDryness)
        {
            setDryness(maxDryness);
        }
    }

    /**
     * Offsets bladder fulness by a specified amount.
     *
     * @param ui {@link GameFrame} object to update values
     * @param amount the bladder fulness offset amount
     */
    static void offsetFulness(GameFrame ui, double amount)
    {
        setFulness((short) (getFulness() + amount)); //Incontinence does another job after 1.1
        changeLabelColor(ui);
        ui.update();
    }

    /**
     * Colors bladder label in red if fulness is past the critical value.
     *
     * @param ui {@code GameFrame} object to update values
     */
    private static void changeLabelColor(GameFrame ui)
    {
        if ((getFulness() > 100 && !isHardcore()) || (getFulness() > 80 && isHardcore()))
        {
            ui.lblBladder.setForeground(Color.RED);
        }
        else
        {
            ui.lblBladder.setForeground(lblDefaultColor);
        }
    }

    /**
     * Checks if bladder fulness is higher than critical value (when leaks are
     * beginning).
     *
     * @return {@code true} if bladder fulness is past the critical value,
     * {@code false} otherwise
     */
    private static boolean isCriticalFulness()
    {
        return (getFulness() > getMaxFulness() - 30 & !isHardcore()) | (getFulness() > getMaxFulness() - 20 & isHardcore());
    }

    /**
     * Replenishes the sphincter power.
     *
     * @param ui {@code GameFrame} object to update values
     * @param amount the sphincter recharge amount
     */
    public static void rechargeSphPower(GameFrame ui, int amount)
    {
        setSphincterPower((short) (getSphincterPower() + amount));
        if (getSphincterPower() > getMaxSphincterPower())
        {
            setSphincterPower(getMaxSphincterPower());
        }
        ui.update();
    }

    /**
     * Empties the belly.
     *
     * @param ui {@link GameFrame} object to update values
     */
    static void emptyBelly(GameFrame ui)
    {
        offsetBelly(ui, -getBelly());
    }

    /**
     * Sets sphicter power to 0 with a specific chance if bladder is filled more
     * than critical value.
     */
    private static void randomlyLeakOnFullBladder()
    {
        if (isCriticalFulness())
        {
            if (chance(calculateLeakingChance()))
            {
                setSphincterPower((short) 0);
                if (getDryness() < MINIMAL_DRYNESS)
                {
                    if (specialHardcoreStage)
                    {
                        rotatePlot(STAGE_POOL.surpriseAccident);
                    }
                    else
                    {
                        rotatePlot(STAGE_POOL.accident);
                    }
                }
            }
        }
    }

    private static byte calculateLeakingChance()
    {
        return !isHardcore() ? ((byte) (5 * (getFulness() - 80))) : ((byte) (3 * (getFulness() - 100) + getEmbarassment()));
    }

    /**
     * Increments the time by 3 minutes and increments all time-related
     * parameters.
     *
     * @param ui {@link GameFrame} object to update values
     */
    static void passTime(GameFrame ui)
    {
        passTime(ui, (byte) 3);
    }

    /**
     * Increments the time by specified amount and increments all time-related
     * parameters.
     *
     * @param time increasement amount
     * @param ui {@link GameFrame} object to update values
     */
    public static void passTime(GameFrame ui, short time)
    {
        offsetTime(ui, time);
        offsetFulness(ui, time * 1.5);
        offsetBelly(ui, -time * 1.5);
        cycleBladder(ui, time);
        //Updating labels
        ui.update();
    }

    private static void cycleBladder(GameFrame ui, short time)
    {
        for (int i = 0; i < time; i+=3)
        {
            decaySphPower(ui);
            if (getBelly() != 0)
            {
                if (getBelly() > 3)
                {
                    offsetFulness(ui, 2);
                }
                else
                {
                    offsetFulness(ui, getBelly());
                    emptyBelly(ui);
                }
            }
            offsetThirst((byte) 2);
            randomlyLeakOnFullBladder();
            checkClassOver(ui);
        }
    }

    private static void offsetThirst(byte amount)
    {
        if (isHardcore())
        {
            setThirst(getThirst() + amount);
            if (Bladder.getThirst() > Bladder.MAXIMAL_THIRST)
            {
                rotatePlot(STAGE_POOL.drink);
            }
        }
    }

    private static void checkClassOver(GameFrame ui)
    {
        if (Bladder.getTime() >= 90)
        {
            ui.setText("You hear the bell finally ring.");
            rotatePlot(STAGE_POOL.classOver);
        }
    }

    /**
     * Offsets a belly water amount by specified value.
     *
     * @param amount increasement amount
     * @param ui {@link GameFrame} object to update values
     */
    public static void offsetBelly(GameFrame ui, double amount)
    {
        setBelly(getBelly() + amount);
        if (getBelly() < 0)
        {
            setBelly(0);
        }
        ui.update();
    }

    /**
     * Decreases the sphincter power.
     *
     * @param ui {@code GameFrame} object to update values
     */
    static void decaySphPower(GameFrame ui)
    {
        setSphincterPower((short) (getSphincterPower() - getFulness() / 30));
        if (getSphincterPower() < 0)
        {
            setDryness(getDryness() - 5); //Decreasing dryness
            setFulness((short) (getFulness() - 2.5)); //Decreasing bladder level
            setSphincterPower((short) 0);
            leakingAlert(ui);
        }
        ui.update();
    }

    private static void leakingAlert(GameFrame ui)
    {
        if (getDryness() > MINIMAL_DRYNESS)
        {
            //Naked
            if (getLower().isMissing() && getUndies().isMissing())
            {
                ui.setText("You feel the leak running down your thighs...", "You're about to pee! You must stop it!");
            }
            else //Outerwear
            {
                if (!lower.isMissing())
                {
                    ui.setText("You see the wet spot expand on your " + getLower().insert() + "!", "You're about to pee! You must stop it!");
                }
                else //Underwear
                {
                    if (!undies.isMissing())
                    {
                        ui.setText("You see the wet spot expand on your " + getUndies().insert() + "!", "You're about to pee! You must stop it!");
                    }
                }
            }
        }
        if (getDryness() < MINIMAL_DRYNESS)
        {
            if (getLower().isMissing() && getUndies().isMissing())
            {
                if (isCornered())
                {
                    ui.setText("You see a puddle forming on the floor beneath you, you're peeing!", "It's too much...");
                }
                else
                {
                    ui.setText("Feeling the pee hit the chair and soon fall over the sides,", "you see a puddle forming under your chair, you're peeing!", "It's too much...");
                }
            }
            else
            {
                if (!lower.isMissing())
                {
                    ui.setText("You see the wet spot expanding on your " + getLower().insert() + "!", "It's too much...");
                }
                else
                {
                    if (!undies.isMissing())
                    {
                        ui.setText("You see the wet spot expanding on your " + getUndies().insert() + "!", "It's too much...");
                    }
                }
            }
            rotatePlot(STAGE_POOL.accident);
            ui.handleNextClicked();
        }
    }

    /**
     * Empties the bladder.
     * @param ui
     */
    public static void empty(GameFrame ui)
    {
        setFulness((short) 0);
        ui.update();
    }

    static void calculateCaps(GameFrame ui)
    {
        //Calculating dryness and maximal bladder capacity values
        maxDryness = getLower().getAbsorption() + getUndies().getAbsorption();
        maxFulness = (short) (getMaxFulness() - getLower().getPressure() + getUndies().getPressure());
        ui.drynessBar.setMaximum((int) getDryness());
    }

    /**
     * @return the maxSphincterPower
     */
    public static short getMaxSphincterPower()
    {
        return maxSphincterPower;
    }

    /**
     * @param aMaxSphincterPower the maxSphincterPower to set
     */
    public static void setMaxSphincterPower(short aMaxSphincterPower)
    {
        maxSphincterPower = aMaxSphincterPower;
    }

    /**
     * @return the dryness
     */
    public static float getDryness()
    {
        return dryness;
    }

    /**
     * @param aDryness the dryness to set
     */
    public static void setDryness(float aDryness)
    {
        dryness = aDryness;
    }

    /**
     * @return the sphincterPower
     */
    public static short getSphincterPower()
    {
        return sphincterPower;
    }

    /**
     * @param aSphincterPower the sphincterPower to set
     */
    public static void setSphincterPower(short aSphincterPower)
    {
        sphincterPower = aSphincterPower;
    }

    /**
     * @return the time
     */
    public static byte getTime()
    {
        return time;
    }

    /**
     * @param aTime the time to set
     */
    public static void setTime(byte aTime)
    {
        time = aTime;
    }

    /**
     * @return the fulness
     */
    public static short getFulness()
    {
        return fulness;
    }

    /**
     * @param aFulness the fulness to set
     */
    public static void setFulness(short aFulness)
    {
        fulness = aFulness;
    }

    /**
     * @return the belly
     */
    public static double getBelly()
    {
        return belly;
    }

    /**
     * @param aBelly the belly to set
     */
    public static void setBelly(double aBelly)
    {
        belly = aBelly;
    }

    /**
     * @return the undies
     */
    public static Wear getUndies()
    {
        return undies;
    }

    /**
     * @param aUndies the undies to set
     */
    public static void setUndies(Wear aUndies)
    {
        undies = aUndies;
    }

    /**
     * @return the lower
     */
    public static Wear getLower()
    {
        return lower;
    }

    /**
     * @param aLower the lower to set
     */
    public static void setLower(Wear aLower)
    {
        lower = aLower;
    }

    /**
     * @return the thirst
     */
    public static float getThirst()
    {
        return thirst;
    }

    /**
     * @param aThirst the thirst to set
     */
    public static void setThirst(float aThirst)
    {
        thirst = aThirst;
    }

    /**
     * @return the embarassment
     */
    public static short getEmbarassment()
    {
        return embarassment;
    }

    /**
     * @param aEmbarassment the embarassment to set
     */
    public static void setEmbarassment(short aEmbarassment)
    {
        embarassment = aEmbarassment;
    }

    /**
     * @return the incontinence
     */
    public static float getIncontinence()
    {
        return incontinence;
    }

    /**
     * @param aIncontinence the incontinence to set
     */
    public static void setIncontinence(float aIncontinence)
    {
        incontinence = aIncontinence;
    }

    /**
     * @return the maxBladder
     */
    public static short getMaxFulness()
    {
        return maxFulness;
    }

    /**
     * @param maxFulness the maxBladder to set
     */
    public static void setMaxFulness(short maxFulness)
    {
        Bladder.maxFulness = maxFulness;
    }

    /**
     *
     * @param ui the GameFrame object to update the interface
     * @param amount the value of amount
     */
    public static void offsetEmbarassment(GameFrame ui, int amount)
    {
        setEmbarassment((short) (getEmbarassment() + amount));
        if (getEmbarassment() < 0)
        {
            setEmbarassment((short) 0);
        }
        ui.update();
    }

    public static boolean fulnessBetween(short min, short max)
    {
        return Bladder.getFulness() > min && Bladder.getFulness() < max;
    }

    //TODO
    public static void initCrotchName()
    {
        //Setting "No underwear" insert name depending on character's gender
        //May be gone soon
        if (NarrativeEngine.isMale())
        {
            UNDERWEAR_LIST[1].setInsertName("penis");
        }
        else
        {
            UNDERWEAR_LIST[1].setInsertName("crotch");
        }
    }
}
