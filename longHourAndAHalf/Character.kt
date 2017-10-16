package longHourAndAHalf

import java.io.Serializable
import java.util.*

@Suppress("IfThenToSafeAccess")
/**
 * Game character.
 *
 * @property name Character's name.
 * @property gender Character's gender.
 * @property bladder Bladder fullness in percents.
 * @property incontinence Defines the sphincter weakening speed.
 */
class Character(
        var name: String,
        var gender: Gender,
        var bladder: Double = Random().nextInt(50).toDouble(),
        var incontinence: Double,
        undies: Wear,
        lower: Wear
) : Serializable {
    /**
     * Character's undies.
     */
    var undies = undies
        set(value) {
            field = value

            updateDryness()
        }

    /**
     * Character's lower body clothing.
     */
    var lower = lower
        set(value) {
            field = value

            updateDryness()
        }

    /**
     * Game instance to operate with.
     */
    @Transient lateinit var game: ALongHourAndAHalf

    /**
     * Amount of water in belly.
     */
    var belly = 0.0

    private fun updateDryness() {
        maximalDryness = calculateMaximalDryness()
        dryness = maximalDryness
        game.ui.drynessBar.value = dryness.toInt()
    }

    /**
     * Maximal [bladder fullness][bladder].
     */
    var maxBladder = 130 - (this.lower.pressure + this.undies.pressure).toInt()

    /**
     * Makes the wetting chance higher after reaching 100% of the bladder fullness.
     */
    var embarrassment = 0

    /**
     * Amount of the character thirstiness.
     * Used only in hardcore mode.
     */
    var thirst = 0

    /**
     * Maximal time without squirming and leaking.
     */
    var maxSphincterPower = (100 / incontinence).toInt()

    /**
     * Current sphincter power. The higher bladder level, the faster power
     * consumption.
     */
    var sphincterPower = maxSphincterPower

    private fun calculateMaximalDryness() = lower.absorption + undies.absorption

    /**
     * Maximal dryness of both clothes.
     */
    var maximalDryness = calculateMaximalDryness()

    /**
     * Summary amount of pee that clothes can store.
     */
    var dryness = maximalDryness

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    var cornered = false

    /**
     * Sets sphincter power to 0 and checks if wear is too wet.
     * Directs the game to accident ending if so.
     */
    fun sphincterSpasm() {
        game.character.sphincterPower = 0

        if (game.character.dryness > ALongHourAndAHalf.MINIMAL_DRYNESS) return

        game.nextStage = if (game.specialHardcoreStage) {
            ALongHourAndAHalf.GameStage.SURPRISE_ACCIDENT
        } else {
            ALongHourAndAHalf.GameStage.ACCIDENT
        }
    }

    companion object {

        /**
         * Maximal thirst level limit.
         * When [thirst] is higher than this value, character will automatically drink water.
         */
        @Suppress("KDocUnresolvedReference")
        const val MAXIMAL_THIRST = 30
    }
}