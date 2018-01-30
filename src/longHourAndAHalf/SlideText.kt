package longHourAndAHalf

import java.lang.StringBuilder

/**
 * Text which [lines][TextLine] can be italic (like *this*).
 *
 * **Usage in A Long Hour and a Half:**
 *
 * Used in the game slides.
 *
 * @author JavaBird
 *
 * @property lines [TextLine]s of this text.
 */
class SlideText(vararg val lines: TextLine) {
    override fun toString() = StringBuilder().let { builder ->
        lines.forEach { line ->
            builder.append(line)
        }
        builder.toString()
    }
}

/**
 * Line of a text which can be italic (like *this*).
 *
 * Used in the [SlideText] class.
 *
 * @author JavaBird
 *
 * @property text Line's text.
 * @property italic Whether the text is italic.
 */
class TextLine(val text: String, val italic: Boolean = false) {
    override fun toString() = text
}