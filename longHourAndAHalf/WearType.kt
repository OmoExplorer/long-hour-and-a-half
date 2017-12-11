package longHourAndAHalf

@Suppress("KDocMissingDocumentation")

/**
 * Type of wear: underwear or outerwear.
 */
enum class WearType {
    UNDERWEAR, OUTERWEAR,

    /**
     * Can be considered as both underwear or outerwear.
     */
    BOTH_SUITABLE
}