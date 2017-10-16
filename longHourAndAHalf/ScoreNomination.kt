package longHourAndAHalf

import longHourAndAHalf.ArithmeticAction.ADD_PERCENT
import longHourAndAHalf.ArithmeticAction.TAKE_PERCENT

/**
 * [Scorer] entry.
 * @sample `ScoreNomination("Wrote lines after the lesson", 1.6, ArithmeticAction.MULTIPLY, false)`
 */
data class ScoreNomination(
        private val comment: String = "",
        val score: Double,
        val arithmeticAction: ArithmeticAction,
        val final: Boolean = false
) {
    override fun toString() = "$comment: $arithmeticAction $score\n" + when (arithmeticAction) {
        ADD_PERCENT, TAKE_PERCENT -> "%"
        else -> ""
    }
}