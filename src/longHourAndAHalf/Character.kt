package longHourAndAHalf

import longHourAndAHalf.WearCombinationType.*
import java.io.Serializable

@Suppress("KDocMissingDocumentation")
/**
 * Game character.
 *
 * @property name Character's name.
 * @property gender Character's gender.
 * @property bladder Character's bladder.
 */
class Character(
        val name: String,
        var gender: Gender,
        val bladder: Bladder,
        undies: Underwear,
        lower: Outerwear
) : Serializable {

    /** Character's underwear. */
    var undies = undies
        set(value) {
            field = value
            onWearChanged()
        }

    /** Character's lower body clothing. */
    var lower = lower
        set(value) {
            field = value
            onWearChanged()
        }

    private fun onWearChanged() {
        updateDryness()
        updateWearCombination()
    }

    private fun updateWearCombination() = when {
        undies.isMissing && lower.isMissing -> NAKED
        undies.isMissing && !lower.isMissing -> UNDERWEAR_ONLY
        !undies.isMissing && lower.isMissing -> OUTERWEAR_ONLY
        !undies.isMissing && !lower.isMissing -> FULLY_CLOTHED
        else -> throw IllegalStateException()
    }

    /** Type of character's wear combination. */
    var wearCombinationType = updateWearCombination()

    private fun updateDryness() {
        maximalDryness = calculateMaximalDryness()
        dryness = maximalDryness
        ui.wearDrynessChanged(dryness)
    }

    /** Makes the wetting chance higher after reaching 100% of the bladder fullness. */
    var embarrassment = 0
        set(value) {
            field = value.coerceAtLeast(0)
            ui.embarrassmentChanged(embarrassment)
        }

    /**
     * Amount of the character thirstiness.
     * Used only in hardcore mode.
     */
    var thirst = 0
        set(value) {
            if (!core.hardcore) return
            field = value
            ui.thirstChanged(thirst)
        }

    var fatalLeakOccurred = false

    private fun calculateMaximalDryness() = lower.absorption + undies.absorption

    /**
     * Maximal dryness of both clothes.
     * @see dryness
     */
    var maximalDryness = calculateMaximalDryness()

    /** Summary amount of pee that clothes can store. */
    var dryness = maximalDryness
        set(value) {
            field = value.coerceAtMost(maximalDryness)
            ui.wearDrynessChanged(dryness)
            checkForLeakingGameOver()
        }

    private fun checkForLeakingGameOver() {
        if (dryness < 0) runLeakingGameOver()
    }

    private fun runLeakingGameOver() {
        game.ui.hideActionUI()
        setLeakingGameOverText()
        core.plot.nextStageID = PlotStageID.ACCIDENT
//        core.plot.advanceToNextStage()
    }

    private fun setLeakingGameOverText() {
        when (core.character.wearCombinationType) {
            NAKED -> if (core.character.cornered)
                ui.forcedTextChange(Text("You see a puddle forming on the floor beneath you, " +
                        "you're peeing!", "It's too much..."))
            else
                ui.forcedTextChange(Text("Feeling the pee hit the chair and soon fall over the sides,",
                        "you see a puddle forming under your chair, you're peeing!", "It's too much..."))

            OUTERWEAR_ONLY, FULLY_CLOTHED ->
                ui.forcedTextChange(
                        Text("You see the wet spot expanding on your ${core.character.lower.insertName}!",
                                "It's too much...")
                )

            UNDERWEAR_ONLY ->
                ui.forcedTextChange(
                        Text("You see the wet spot expanding on your ${core.character.undies.insertName}!",
                                "It's too much...")
                )
        }
    }

    /** Whether or not character currently stands in the corner and unable to hold crotch. */
    var cornered = false

    /** Increases [dryness] by a certain value. */
    fun dryClothes(timeOffset: Int) {
        dryness += (lower.dryingOverTime + undies.dryingOverTime) * (timeOffset / 3)
    }

    fun forceToDrink() {
        core.plot.nextStageID = PlotStageID.DRINK
    }

    /** Makes the character to leak. */
    fun leak() {
        val volume = calculateLeakVolume()
        bladder.fullness -= volume
        dryness -= volume
    }

    private fun calculateLeakVolume(): Int {
        val leakVolumeMultiplier = random.nextGaussian() + 0.75
        val leakBaseValue = (bladder.sphincter.maximalPower - bladder.sphincter.power) * bladder.sphincter.incontinence
        return (leakBaseValue * leakVolumeMultiplier).toInt()
    }

    companion object {
        /**
         * Maximal thirst level limit.
         * When [thirst][Character.thirst] is higher than this value, character will automatically drink water.
         */
        const val MAXIMAL_THIRST = 30

        /** The dryness minimal threshold. Game ends if [dryness] goes below this value. */
        const val MINIMAL_DRYNESS = 0
    }
}