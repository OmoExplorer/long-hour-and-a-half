package longHourAndAHalf

/**
 * @property lower Character's lower body clothing.
 */
class Character(
        var name: String,
        var gender: ALongHourAndAHalf.Gender,
        var incontinence: Double,
        var bladderFullness: Double,
        var undies: Wear,
        var lower: Wear,
        var undiesColor: String,
        var lowerColor: String
) {
    constructor(
            name: String,
            gender: ALongHourAndAHalf.Gender,
            incontinence: Double,
            bladderFullness: Double,
            undies: String,
            lower: String,
            undiesColor: String,
            lowerColor: String
    ) : this(
            name,
            gender,
            incontinence,
            bladderFullness,
            Wear.getByName(undies, Wear.WearType.UNDERWEAR)!!,
            Wear.getByName(lower, Wear.WearType.OUTERWEAR)!!,
            undiesColor,
            lowerColor
    )

    var game: ALongHourAndAHalf? = null

    var bladder = 0.0
    var belly = 0.0
    var maxBladder: Int = 130

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
     * Defines the sphincter weakening speed.
     */
    var incon = 1.0

    /**
     * Maximal time without squirming and leaking.
     */
    var maxSphincterPower = 0

    /**
     * Current sphincter power. The higher bladder level, the faster power
     * consumption.
     */
    var sphincterPower = 0

    /**
     * Amount of pee that clothes can store.
     */
    var dryness = 0.0

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    var cornered = false
}