package longHourAndAHalf

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
    var name = name
        set(value) {
            field = value
            game.ui.characterNameChanged(name)
        }

    var fullness = fullness
        set(value) {
            field = value
            game.ui.bladderFullnessChanged(fullness)
            game.ui.lblBladder.foreground = if (fullness > 100 && !game.hardcore || fullness > 80 && game.hardcore)
                Color.RED
            else
                Color.BLACK
        }

    var incontinence = incontinence
        set(value) {
            field = value
            game.ui.incontinenceMultiplierChanged(incontinence)
        }

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
        set(value) {
            field = Math.max(value, 0.0)
            game.ui.bellyWaterLevelChanged(belly)
        }

    private fun updateDryness() {
        maximalDryness = calculateMaximalDryness()
        dryness = maximalDryness
        game.ui.drynessBar.value = dryness.toInt()
    }

    /**
     * Maximal [bladder fullness][fullness].
     */
    var maxBladder = 130 - (this.lower.pressure + this.undies.pressure).toInt()

    /**
     * Makes the wetting chance higher after reaching 100% of the fullness fullness.
     */
    var embarrassment = 0
        set(value) {
            field = Math.max(value, 0)
            game.ui.embarrassmentChanged(embarrassment)
        }

    /**
     * Amount of the character thirstiness.
     * Used only in hardcore mode.
     */
    var thirst = 0
        set(value) {
            field = value
            game.ui.thirstChanged(thirst)
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
            game.ui.sphincterStrengthChanged(sphincterPower)
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
            field = value
            game.ui.wearDrynessChanged(dryness)
        }

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    var cornered = false

    /**
     * Sets sphincter power to 0 and checks if wear is too wet.
     * Directs the core to accident ending if so.
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