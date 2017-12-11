package longHourAndAHalf

/**
 * Multi-line text with italic text support.
 * Used in plot stages.
 *
 * @sample PlotMap.map
 *
 * @see PlotStage
 */
class Text {
    /**
     * Creates a text from the string lines. All lines will be not italic.
     */
    constructor(vararg lines: String) {
        val textLineList = mutableListOf<TextLine>()

        lines.forEach {
            textLineList.add(TextLine(it, false))
        }

        this.lines = textLineList
    }

    /**
     * Creates a text from the [TextLine]s.
     * Provides the ability to make lines italic:
     *
     * `Text(TextLine("Sample", `**`true`**`))   //passing true to make the line italic`
     *
     * **Output:**
     *
     * *Sample*
     */
    constructor(vararg lines: TextLine) {
        this.lines = lines.toList()
    }

    /**
     * Creates a text from the list of strings. All lines will be not italic.
     * Handy when the block of the text needs to be dependent from the game state:
     *
     * `Text(`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;`listOf("Static") + if (...)`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;
     * &nbsp;&nbsp;&nbsp;&nbsp;`listOf("Dynamic, part 1, branch 1", "Dynamic, part 2, branch 1")`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;`else`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;
     * &nbsp;&nbsp;&nbsp;&nbsp;`listOf("Dynamic, part 1, branch 2", "Dynamic, part 2, branch 2")`
     *
     * `)`
     */
    constructor(lines: List<String>) {
        val textLineList = mutableListOf<TextLine>()

        lines.forEach {
            textLineList.add(TextLine(it, false))
        }

        this.lines = textLineList
    }

    /**
     * Creates a text from the set of [TextLine]s. Provides the ability to make lines italic.
     * Handy when the block of the text needs to be dependent from the game state:
     *
     * `Text(`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;`setOf(TextLine("Static")) + if (...)`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;
     * &nbsp;&nbsp;&nbsp;&nbsp;`setOf(TextLine("Dynamic, part 1, branch 1", "Dynamic, part 2, branch 1"))`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;`else`
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;
     * &nbsp;&nbsp;&nbsp;&nbsp;`setOf(TextLine("Dynamic, part 1, branch 2", "Dynamic, part 2, branch 2"))`
     *
     * `)`
     */
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
        val empty = Text(TextLine.empty)
    }
}

@Suppress("KDocMissingDocumentation")
/**
 * Line of a [Text].
 *
 * @property text Text of this line.
 * @property italic Whether this line should be italic.
 *
 * **Example:**
 *
 * Normal line
 *
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