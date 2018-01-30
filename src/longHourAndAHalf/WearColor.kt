package longHourAndAHalf

import java.awt.Color

/** Colors that [Wear] can have. */
@Suppress("KDocMissingDocumentation")
enum class WearColor(val awtColor: Color) {
    RANDOM(Color.DARK_GRAY),
    BLACK(Color.BLACK),
    GRAY(Color.GRAY),
    RED(Color.RED),
    ORANGE(Color.ORANGE),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.CYAN),
    DARK_BLUE(Color.BLUE) {
        override fun toString() = "dark blue"
    },
    PURPLE(Color.MAGENTA),
    PINK(Color.PINK);

    override fun toString() = name.toLowerCase()

    companion object {
        fun random() = values().filterNot { it == RANDOM }.randomItem()
    }
}