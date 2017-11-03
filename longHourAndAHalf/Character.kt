package longHourAndAHalf

import longHourAndAHalf.WearCombinationType.*
import java.awt.Color
import java.io.Serializable
import java.util.*

@Suppress("IfThenToSafeAccess")
/**
 * Game character.
 *
 * @property name Character's name.
 * @property gender Character's gender.
 * @property fullness Bladder fullness in percents.
 * @property incontinence Defines the sphincter weakening speed.
 */
class Character(
        name: String,
        var gender: Gender,
        fullness: Double = Random().nextInt(50).toDouble(),
        incontinence: Double,
        undies: Wear,
        lower: Wear
) : Serializable {
    /**
     * Game instance to operate with.
     */
    @Transient lateinit var core: Core

    var name = name
        set(value) {
            field = value
            core.ui.characterNameChanged(name)
        }

    var fullness = fullness
        set(value) {
            field = value
            core.ui.bladderFullnessChanged(fullness)
            core.ui.lblBladder.foreground = if (fullness > 100 && !core.hardcore || fullness > 80 && core.hardcore)
                Color.RED
            else
                Color.BLACK
        }

    var incontinence = incontinence
        set(value) {
            field = value
            core.ui.incontinenceMultiplierChanged(incontinence)
        }

    /**
     * Character's undies.
     */
    var undies = undies
        set(value) {
            field = value
            onWearChanged()
        }

    /**
     * Character's lower body clothing.
     */
    var lower = lower
        set(value) {
            field = value
            onWearChanged()
        }

    private fun onWearChanged() {
        updateDryness()
        updateWearCombination()
    }

    class WTFError : Error("This error would never be thrown. If it was, something is very wrong.")

    fun updateWearCombination() = when {
        undies.isMissing && lower.isMissing -> NAKED
        undies.isMissing && !lower.isMissing -> UNDERWEAR_ONLY
        !undies.isMissing && lower.isMissing -> OUTERWEAR_ONLY
        !undies.isMissing && !lower.isMissing -> FULLY_CLOTHED
        else -> throw WTFError()
    }

    var wearCombinationType = updateWearCombination()

    /**
     * Amount of water in belly.
     */
    var belly = 0.0
        set(value) {
            field = Math.max(value, 0.0)
            core.ui.bellyWaterLevelChanged(belly)
        }

    private fun updateDryness() {
        maximalDryness = calculateMaximalDryness()
        dryness = maximalDryness
        core.ui.drynessBar.value = dryness.toInt()
    }

    /**
     * Maximal [bladder fullness][fullness].
     */
    var maxBladder = 0

    /**
     * [Bladder fullness][fullness] where leaks happen.
     */
    var criticalBladderFullnessLevel = 0

    /**
     * Makes the wetting chance higher after reaching 100% of the fullness fullness.
     */
    var embarrassment = 0
        set(value) {
            field = Math.max(value, 0)
            core.ui.embarrassmentChanged(embarrassment)
        }

    /**
     * Amount of the character thirstiness.
     * Used only in hardcore mode.
     */
    var thirst = 0
        set(value) {
            if (!core.hardcore) return
            field = value
            core.ui.thirstChanged(thirst)
        }

    /**
     * Maximal time without squirming and leaking.
     */
    var maxSphincterPower = (100 / incontinence).toInt()

    /**
     * Current sphincter power. The higher fullness level, the faster power
     * consumption.
     */
    var sphincterPower = maxSphincterPower
        set(value) {
            field = Math.min(value, maxSphincterPower)
            core.ui.sphincterStrengthChanged(sphincterPower)
        }

    private fun calculateMaximalDryness() = lower.absorption + undies.absorption

    /**
     * Maximal dryness of both clothes.
     */
    var maximalDryness = calculateMaximalDryness()

    /**
     * Summary amount of pee that clothes can store.
     */
    var dryness = maximalDryness
        set(value) {
            field = Math.min(value, maximalDryness)
            core.ui.wearDrynessChanged(dryness)
        }

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    var cornered = false

    fun finishSetup(core: Core) {
        this.core = core

        maxBladder = (
                if (core.hardcore)
                    130
                else
                    100
                ) - (this.lower.pressure + this.undies.pressure).toInt()

        criticalBladderFullnessLevel = (
                if (core.hardcore)
                    100
                else
                    50
                ) - (this.lower.pressure + this.undies.pressure).toInt()
    }

    /**
     * Sets sphincter power to 0 and checks if wear is too wet.
     * Directs the core to accident ending if so.
     */
    fun sphincterSpasm() {
        sphincterPower = 0

        if (dryness > MINIMAL_DRYNESS) return

        core.plot.nextStage = if (core.plot.specialHardcoreStage) {
            GameStage.SURPRISE_ACCIDENT
        } else {
            GameStage.ACCIDENT
        }
    }

    fun applyDrainCheat() {
        if (!(core.cheatData.drain && (core.world.time.minutes % 15 == 0))) return
        fullness = 0.0
    }

    fun dryClothes(timeOffset: Int) {
        dryness += (lower.dryingOverTime + undies.dryingOverTime) * (timeOffset / 3)
    }

    fun timeEffect(timeOffset: Int) {
        with(core) {
            if (world.time >= SchoolDay.classEndingTime) {
                ui.setText("You hear the bell finally ring.")
                plot.nextStage = GameStage.CLASS_OVER
            }

            testWet()
            makeUrineFromWater(timeOffset)

            //Decrementing sphincter power for every 3 minutes
            for (i in 0..timeOffset) {
                decaySphPower()
            }
            if (hardcore) {
                thirst += 2
                if (thirst > MAXIMAL_THIRST) {
                    plot.nextStage = GameStage.DRINK
                }
            }
        }
    }

    private fun makeUrineFromWater(timeOffset: Int) {
        fullness += timeOffset * 1.5
        belly -= timeOffset * 1.5

        if (belly == 0.0) return

        //If there is more than 3 water units, make additional 2 urine units
        if (belly > 3)
            fullness += 2.0
        else {
            fullness += belly
            belly = 0.0
        }
    }

    private fun calculateLeakingChance() = with(core) {
        (if (hardcore)
            5
        else
            3) * (fullness - maxBladder) + embarrassment
    }

    /**
     * If bladder fullness is past [maximal limit][maxBladder], makes character guranteely leak.
     * Or if bladder fullness is past [critical value][criticalBladderFullnessLevel],
     * makes character leak with a chance.
     */
    fun testWet() {
        with(core) {
            if (fullness >= maxBladder ||
                    character.fullness > character.criticalBladderFullnessLevel && chance(calculateLeakingChance()))
                sphincterSpasm()
        }
    }

    private fun warnUserAboutLeaking() {
        //Naked
        if (lower.isMissing && undies.isMissing) {
            core.ui.setText("You feel the leak running down your thighs...",
                    "You're about to pee! You must stop it!")
        } else
        //Outerwear
        {
            if (!lower.isMissing) {
                core.ui.setText("You see the wet spot expand on your ${lower.insert}!",
                        "You're about to pee! You must stop it!")
            } else
            //Underwear
            {
                if (!undies.isMissing) {
                    core.ui.setText("You see the wet spot expand on your ${undies.insert}!",
                            "You're about to pee! You must stop it!")
                }
            }
        }
    }

    private fun leakingTooMuchSoGameOver() {
        when (wearCombinationType) {
            NAKED -> if (cornered)
                core.ui.setText("You see a puddle forming on the floor beneath you, you're peeing!", "It's too much...")
            else
                core.ui.setText("Feeling the pee hit the chair and soon fall over the sides,",
                        "you see a puddle forming under your chair, you're peeing!", "It's too much...")

            OUTERWEAR_ONLY, FULLY_CLOTHED ->
                core.ui.setText("You see the wet spot expanding on your ${lower.insert}!", "It's too much...")

            UNDERWEAR_ONLY ->
                core.ui.setText("You see the wet spot expanding on your ${undies.insert}!", "It's too much...")
        }

        core.plot.nextStage = GameStage.ACCIDENT
        core.handleNextClicked()
    }

    /**
     * Decreases the sphincter power.
     */
    fun decaySphPower() {
        sphincterPower -= (fullness / 30).toInt()

        if (sphincterPower >= 0) return

        dryness -= 5 //Decreasing dryness
        fullness -= 2.5 //Decreasing fullness level
        sphincterPower = 0

        if (dryness > MINIMAL_DRYNESS)
            warnUserAboutLeaking()

        if (dryness < MINIMAL_DRYNESS)
            leakingTooMuchSoGameOver()
    }

    companion object {
        /**
         * Maximal thirst level limit.
         * When [thirst] is higher than this value, character will automatically drink water.
         */
        @Suppress("KDocUnresolvedReference")
        const val MAXIMAL_THIRST = 30

        /**
         * The dryness minimal threshold.
         * Game ends if [dryness] goes below this value.
         */
        val MINIMAL_DRYNESS = 0
    }
}