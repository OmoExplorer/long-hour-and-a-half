package longHourAndAHalf

/**
 * Multi-line text with italic text support.
 */
open class Text {
    constructor(vararg lines: String) {
        val textLineList = mutableListOf<TextLine>()

        lines.forEach {
            textLineList.add(TextLine(it, false))
        }

        this.lines = textLineList
    }

    constructor(vararg lines: TextLine) {
        this.lines = lines.toList()
    }

    constructor(lines: List<String>) {
        val textLineList = mutableListOf<TextLine>()

        lines.forEach {
            textLineList.add(TextLine(it, false))
        }

        this.lines = textLineList
    }

    constructor(lines: Set<TextLine>) {
        this.lines = lines.toList()
    }

    /**
     * [Lines][TextLine] of this text.
     */
    val lines: List<TextLine>

    companion object {
        /**
         * Empty text.
         */
        val empty = Text(TextLine(""))
    }
}

/**
 * Line of a [text][Text].
 * @property text Text of this line.
 * @property italic Whether this line should be italic.
 * Example:
 * Normal line,
 * *Italic line*
 */
class TextLine(
        val text: String,
        val italic: Boolean = false
) {
    companion object {
        /**
         * Empty line.
         */
        val empty = TextLine("")
    }
}