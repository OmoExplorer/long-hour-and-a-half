package omo;

import java.awt.Color;
import static omo.NarrativeEngine.*;
import static omo.NarrativeEngine.GameStage.ACCIDENT;
import omo.ui.GameFrame;

/**
 * Static class which provides bladder simulation functionality and stores the
 * bladder data.
 *
 * @author JavaBird
 */
public class Bladder
{
    /**
     * The dryness game over minimal threshold.
     */
    public static final int MINIMAL_DRYNESS = 0;

    /**
     * Maximal time without squirming and leaking.
     */
    private static short maxSphincterPower;

    /**
     * List of all underwear types.
     */
    static Wear[] underwearList =
    {
        //        Name      Insert name     Pressure, Absotption, Drying over time
        new Wear("Random", NarrativeEngine.showError((byte) 0), 0, 0, 0),
        new Wear("No underwear", NarrativeEngine.showError((byte) 0), 0, 0, 1),
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
     * Maximal amount of pee that clothes can store.
     */
    private static float dryness;

    /**
     * Maximal bladder fulness.
     */
    private static short maxBladder = 130;

    /**
     * Current sphincter power.
     */
    private static short sphincterPower;

    /**
     * The class time in minutes.
     */
    private static byte time = 0;

    /**
     * Current bladdder fulness.
     */
    private static short fulness;

    /**
     * Amount of a water in a belly.
     */
    private static double belly;

    /**
     * Character's undies.
     */
    private static Wear undies;

    public final static float MAXIMAL_THIRST = 30;

    /**
     * Character's lower body clothing.
     */
    private static Wear lower;

    /**
     * Amount of the character thirstiness. Used only in hardcore mode.
     */
    private static float thirst = 0;

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
     * Offsets the time by a specified amount.
     *
     * @param ui {@link GameFrame} object to update values
     * @param amount the offset value. May be negative to decrease time
     */
    public static void offsetTime(GameFrame ui, int amount)
    {
        setTime((byte) (getTime() + amount));
        drain(ui);
        dryClothesOverTime(amount);
        ui.update();
    }

    /**
     * Restores clothes' dryness over time.
     *
     * @param amount time amount
     */
    private static void dryClothesOverTime(int amount)
    {
        //Clothes drying over time
        if (getDryness() < getLower().getAbsorption() + getUndies().getAbsorption()) //TODO: replace with a variable
        {
            setDryness(getDryness() + getLower().getDryingOverTime() + getUndies().getDryingOverTime() * (amount / 3));
        }
        if (getDryness() > getLower().getAbsorption() + getUndies().getAbsorption())
        {
            setDryness(getLower().getAbsorption() + getUndies().getAbsorption());
        }
    }

    /**
     * Empties a bladder every 15 in-game minutes if corresponding cheat is
     * enabled.
     *
     * @param ui {@code GameFrame} object to update values
     */
    private static void drain(GameFrame ui)
    {
        if (NarrativeEngine.drain & (getTime() % 15) == 0)
        {
            emptyBladder(ui);
        }
    }

    /**
     * Offsets bladder fulness by a specified amount.
     *
     * @param ui {@link GameFrame} object to update values
     * @param amount the bladder fulness offset amount
     */
    static void offsetBladder(GameFrame ui, double amount)
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
        if ((getFulness() > 100 && !NarrativeEngine.hardcore) || (getFulness() > 80 && hardcore))
        {
            ui.lblBladder.setForeground(Color.RED);
        }
        else
        {
            ui.lblBladder.setForeground(GameFrame.lblDefaultColor);
        }
    }

    /**
     * Checks if bladder fulness is higher than critical value (when leaks are
     * beginning).
     *
     * @return {@link true} if bladder fulness is past the critical value,
     * {@link false} otherwise
     */
    private static boolean isCriticalBladder()
    {
        return (getFulness() > getMaxBladder() - 30 & !hardcore) | (getFulness() > getMaxBladder() - 20 & hardcore);
    }

    /**
     * Replenishes the sphincter power.
     *
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
     * Checks the wetting conditions, and if they are met, wetting
     */
    static void testWet()
    {
        //If bladder is filled more than 130 points in the normal mode and 100 points in the hardcore mode, setting sphincter power to 0
        if (getFulness() >= getMaxBladder() & !hardcore)
        {
            setSphincterPower((short) 0);
            if (getDryness() < MINIMAL_DRYNESS)
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
                wetIfUnlucky(!hardcore ? ((short) (5 * (getFulness() - 80))) : ((short) (3 * (getFulness() - 100) + getEmbarassment())));
            }
        }
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

    //TODO: Refactor
    /**
     * Sets sphicter power to 0 with a specific chance if bladder is filled more
     * than critical value.
     */
    private static void wetIfUnlucky(short wetChance)
    {
        if (NarrativeEngine.chance((byte) wetChance))
        {
            setSphincterPower((short) 0);
            if (getDryness() < MINIMAL_DRYNESS)
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
        offsetBladder(ui, time * 1.5);
        offsetBelly(ui, -time * 1.5);
        if (Bladder.getTime() >= 88)
        {
            ui.setText("You hear the bell finally ring.");
            setNextStage(NarrativeEngine.GameStage.CLASS_OVER);
        }
        testWet();
        //Decrementing sphincter power for every 3 minutes
        for (int i = 0; i < time; i++)
        {
            decaySphPower(ui);
            if (getBelly() != 0)
            {
                if (getBelly() > 3)
                {
                    offsetBladder(ui, 2);
                }
                else
                {
                    offsetBladder(ui, getBelly());
                    emptyBelly(ui);
                }
            }
        }
        if (hardcore)
        {
            setThirst(getThirst() + 2);
            if (Bladder.getThirst() > Bladder.MAXIMAL_THIRST)
            {
                setNextStage(NarrativeEngine.GameStage.DRINK);
            }
        }
        //Updating labels
        ui.update();
    }

    /**
     * Offsets a belly water amount by specified value.
     *
     * @param amount increasement amount
     * @param ui {@link GameFrame} object to update values
     */
    static void offsetBelly(GameFrame ui, double amount)
    {
        setBelly(getBelly() + amount);
        if (getBelly() < 0)
        {
            setBelly(0);
        }
        ui.update();
    }

    //TODO: Refactor
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
            peeingAlert(ui);
        }
        ui.update();
    }

    private static void peeingAlert(GameFrame ui)
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
                    ui.setText("You see the wet spot expanding on your " + getLower().insert() + "!", "It's too much...");
                    setNextStage(ACCIDENT);
                    ui.handleNextClicked();
                }
                else
                {
                    if (!undies.isMissing())
                    {
                        ui.setText("You see the wet spot expanding on your " + getUndies().insert() + "!", "It's too much...");
                        setNextStage(ACCIDENT);
                        ui.handleNextClicked();
                    }
                }
            }
        }
    }

    /**
     * Empties the bladder.
     *
     */
    static void emptyBladder(GameFrame ui)
    {
        setFulness((short) 0);
        ui.update();
    }

    static void calculateCaps(GameFrame ui)
    {
        //Calculating dryness and maximal bladder capacity values
        //TODO: Move to Bladder
        setDryness(getLower().getAbsorption() + getUndies().getAbsorption());
        setMaxBladder((short) (getMaxBladder() - getLower().getPressure() + getUndies().getPressure()));
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
    public static short getMaxBladder()
    {
        return maxBladder;
    }

    /**
     * @param aMaxBladder the maxBladder to set
     */
    public static void setMaxBladder(short aMaxBladder)
    {
        maxBladder = aMaxBladder;
    }
    /**
     *
     * @param ui the GameFrame object to update the interface
     * @param amount the value of amount
     */
    public void offsetEmbarassment(GameFrame ui, int amount)
    {
        setEmbarassment((short) (getEmbarassment() + amount));
        if (getEmbarassment() < 0)
        {
            setEmbarassment((short) 0);
        }
        ui.update();
    }
}
