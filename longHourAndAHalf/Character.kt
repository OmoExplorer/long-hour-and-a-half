package longHourAndAHalf

import java.io.Serializable

/**
 * @property incontinence Defines the sphincter weakening speed.
 */
class Character(
        var name: String,
        var gender: Gender,
        var incontinence: Double,
        var bladderFullness: Double,
        undies: Wear,
        lower: Wear,
        var undiesColor: String, var lowerColor: String
) : Serializable {
    constructor(
            name: String,
            gender: Gender,
            incontinence: Double,
            bladderFullness: Double,
            undies: String,
            lower: String,
            undiesColor: String, lowerColor: String
    ) : this(
            name,
            gender,
            incontinence,
            bladderFullness,
            Wear.getByName(undies, Wear.WearType.UNDERWEAR)!!,
            Wear.getByName(lower, Wear.WearType.OUTERWEAR)!!,
            undiesColor, lowerColor
    )

    @Transient lateinit var ui: StandardGameUI

    var bladder = 0.0
    var belly = 0.0
    var maxBladder = 130 - (lower.pressure + undies.pressure).toInt()

    private fun updateDryness() {
        maximalDryness = calculateMaximalDryness()
        dryness = maximalDryness
        ui.drynessBar.value = dryness.toInt()
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
     * Makes the wetting chance higher after reaching 100% of the bladder
     * fullness.
     */
    var embarrassment: Int = 0

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

    var maximalDryness = calculateMaximalDryness()

    /**
     * Amount of pee that clothes can store.
     */
    var dryness = maximalDryness

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    var cornered = false
}