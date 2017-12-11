package longHourAndAHalf

@Suppress("KDocMissingDocumentation")
/**
 * Colors that [Wear] can have.
 */
enum class WearColor {
    /**
     * Null color. Applied when the target wear is missing.
     */
    NONE {
        override fun toString() = ""
    },

    /**
     * Special color. Replaced by `Wear.replaceRandomColor` declared in [Launcher].
     */
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

    companion object {
        fun random(): WearColor {
            var randomColor: WearColor

            do
                randomColor = values().randomItem()
            while (randomColor == NONE || randomColor == RANDOM)

            return randomColor
        }
    }
}