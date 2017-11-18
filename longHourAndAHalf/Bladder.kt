package longHourAndAHalf

import java.io.Serializable
import java.util.*

/**
 * Simulates an urinary bladder.
 */
class Bladder(
        fullness: Double = Random().nextInt(50).toDouble(),
        incontinence: Double,
        @Transient var core: Core
) : Serializable {
    /**
     * Amount of urine held in this bladder in percents.
     */
    var fullness = fullness
        set(value) {
            field = clamp(0.0, value, 150.0)
            with(core) {
                ui.bladderFullnessChanged(fullness)
                ui.lblBladder.foreground = if (fullness > 100 && !hardcore || fullness > 80 && hardcore)
                    java.awt.Color.RED
                else
                    java.awt.Color.BLACK
            }
        }

    /**
     * Bladder's incontinence.
     * The higher incontinence, the more intense [sphincter power][sphincterPower] drain.
     */
    var incontinence = incontinence
        set(value) {
            field = value
            core.ui.incontinenceMultiplierChanged(incontinence)
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

    /**
     * Decreases the sphincter power.
     */
    fun decaySphincterPower(time: Int) {
        for (i in 0..(time / 3)) {
            sphincterPower -= (fullness / 30).toInt()

            if (sphincterPower >= 0) return

            core.character.dryness -= 5 //Decreasing dryness
            fullness -= 2.5 //Decreasing fullness level
            sphincterPower = 0

            if (core.character.dryness > Character.MINIMAL_DRYNESS)
                core.ui.warnAboutLeaking()

            if (core.character.dryness < Character.MINIMAL_DRYNESS)
                leakingTooMuchSoGameOver()
        }
    }

    fun makeUrineFromWater(timeOffset: Int) {
        fullness += timeOffset * 1.5
        with(core.character) {
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
    }

    private fun calculateLeakingChance() = with(core) {
        (if (hardcore)
            5
        else
            3) * (fullness - maxBladder) + character.embarrassment
    }

    /**
     * If bladder fullness is past [maximal limit][maxBladder], returns `true`.
     * Or if bladder fullness is past [critical value][criticalBladderFullnessLevel],
     * returns `true` with a chance.
     */
    fun shouldLeak() = fullness >= maxBladder ||
            fullness > criticalBladderFullnessLevel && chance(calculateLeakingChance())

    /**
     * Sets sphincter power to 0 and checks if wear is too wet.
     * Directs the game to accident ending if so.
     */
    fun sphincterSpasm() {
        sphincterPower = 0

        if (core.character.dryness > Character.MINIMAL_DRYNESS) return

        core.plot.nextStageID = if (core.plot.specialHardcoreStage) {
            PlotStageID.SURPRISE_ACCIDENT
        } else {
            PlotStageID.ACCIDENT
        }
    }

    /**
     * Empties this bladder each 15 in-game minutes.
     */
    fun applyDrainCheat() {
        if (!(core.cheatData.drain && (core.world.time.minutes % 15 == 0))) return
        fullness = 0.0
    }

    /**
     * Finishes the character instance setup.
     * Must be called as soon as [game core][Core] becomes available.
     */
    fun finishSetup(core: Core) {
        this.core = core

        maxBladder = (
                if (core.hardcore)
                    130
                else
                    100
                ) - (core.character.lower.pressure + core.character.undies.pressure).toInt()

        criticalBladderFullnessLevel = (
                if (core.hardcore)
                    100
                else
                    50
                ) - (core.character.lower.pressure + core.character.undies.pressure).toInt()
    }

    private fun leakingTooMuchSoGameOver() {
        when (core.character.wearCombinationType) {
            WearCombinationType.NAKED -> if (core.character.cornered)
                core.ui.forcedTextChange(Text("You see a puddle forming on the floor beneath you, " +
                        "you're peeing!", "It's too much..."))
            else
                core.ui.forcedTextChange(Text("Feeling the pee hit the chair and soon fall over the sides,",
                        "you see a puddle forming under your chair, you're peeing!", "It's too much..."))

            WearCombinationType.OUTERWEAR_ONLY, WearCombinationType.FULLY_CLOTHED ->
                core.ui.forcedTextChange(
                        Text("You see the wet spot expanding on your ${core.character.lower.insert}!",
                                "It's too much...")
                )

            WearCombinationType.UNDERWEAR_ONLY ->
                core.ui.forcedTextChange(
                        Text("You see the wet spot expanding on your ${core.character.undies.insert}!",
                                "It's too much...")
                )
        }

        core.plot.nextStageID = PlotStageID.ACCIDENT
        core.handleNextClicked()
    }

    fun getThoughts() = when (fullness) {
        in 0..20 -> Text(
                "Feeling bored about the day, and not really caring about the class too much,",
                "you look to the clock, watching the minutes tick by."
        )
        in 20..40 -> Text(
                "Having to pee a little bit, you look to the clock, ",
                "watching the minutes tick by and wishing the lesson to get over faster."
        )
        in 40..60 -> Text("Clearly having to pee, you impatiently wait for the lesson end.")
        in 60..80 -> Text(
                TextLine(
                        "You feel the rather strong pressure in your bladder, " +
                                "and you're starting to get even more desperate."
                ),
                TextLine("Maybe I should ask teacher to go to the restroom? It hurts a bit...", true)
        )
        in 80..100 -> Text(
                TextLine("Keeping all that urine inside will become impossible very soon.", true),
                TextLine("You feel the terrible pain and pressure in your bladder"),
                TextLine("and you can almost definitely say you haven't needed to pee this badly in your life."),
                TextLine("Ouch, it hurts a lot... I must do something about it now, or else...", true)
        )
        in 100..Int.MAX_VALUE -> Text(
                TextLine("This is really bad...", true),
                TextLine("You know that you can't keep it any longer and you may wet yourself in any moment."),
                TextLine("Oh! You can clearly see your bladder as it bulging."),
                TextLine("Ahhh... I cant hold it anymore!!!", true),
                TextLine("Even holding your crotch doesn't seems to help you to keep it in.")
        )
        else -> throw IllegalStateException(
                "Bladder fullness is outside of legal bounds\n" +
                        "Bounds: 0..${Int.MAX_VALUE}\n" +
                        "Value: $fullness"
        )
    }
}