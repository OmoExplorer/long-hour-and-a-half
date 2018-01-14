package longHourAndAHalf

/**
 * Type of wear combination.
 */
enum class WearCombinationType {
    /** Fully clothed (both outerwear and underwear are on). */
    FULLY_CLOTHED,

    /** Outerwear without underwear. */
    OUTERWEAR_ONLY,

    /** Underwear only. */
    UNDERWEAR_ONLY,

    /** Completely naked. */
    NAKED
}