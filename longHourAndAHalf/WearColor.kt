package longHourAndAHalf

@Suppress("KDocMissingDocumentation")
/**
 * Enumeration of wear colors.
 */
enum class WearColor {
    NONE {
        override fun toString() = ""
    },
    RANDOM,
    BLACK,
    GRAY,
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    DARK_BLUE {
        override fun toString() = "dark blue"
    },
    PURPLE,
    PINK;

    override fun toString() = name.toLowerCase()
}