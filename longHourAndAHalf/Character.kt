package longHourAndAHalf

import longHourAndAHalf.WearCombinationType.*
import java.io.Serializable
import java.util.*

@Suppress("IfThenToSafeAccess")
/**
 * Game character.
 *
 * @property gender Character's gender.
 */
class Character(
        name: String,
        var gender: Gender,
        private val fullness: Double = Random().nextInt(50).toDouble(),
        private val incontinence: Double,
        undies: Wear,
        lower: Wear
) : Serializable {
    /**
     * Game instance to operate with.
     */
    @Transient lateinit var core: Core

    fun finishSetup(core: Core) {
        this.core = core
        bladder = Bladder(fullness, incontinence, core)
    }

    /**
     * Character's name.
     */
    var name = name
        set(value) {
            field = value
            core.ui.characterNameChanged(name)
        }

    /**
     * Character's bladder.
     */
    lateinit var bladder: Bladder

    /**
     * Character's underwear.
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

    /**
     * Local error type. Should be never thrown.
     */
    class WTFError : Error("This error would never be thrown. If it was, something is very wrong.")

    private fun updateWearCombination() = when {
        undies.isMissing && lower.isMissing -> NAKED
        undies.isMissing && !lower.isMissing -> UNDERWEAR_ONLY
        !undies.isMissing && lower.isMissing -> OUTERWEAR_ONLY
        !undies.isMissing && !lower.isMissing -> FULLY_CLOTHED
        else -> throw WTFError()
    }

    /**
     * Type of character's wear combination.
     */
    var wearCombinationType = updateWearCombination()

    /**
     * Amount of water in character's belly.
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

    /**
     * Increases [dryness] by a certain value.
     */
    fun dryClothes(timeOffset: Int) {
        dryness += (lower.dryingOverTime + undies.dryingOverTime) * (timeOffset / 3)
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