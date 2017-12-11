package longHourAndAHalf

import longHourAndAHalf.ArithmeticAction.*
import java.awt.Component
import java.io.Serializable
import javax.swing.JOptionPane

/**
 * Counts a game score.
 * Score is a number that shows a game difficulty - the higher score, the harder was the game.
 * Specific actions (for example, peeing in a restroom during a lesson) reduce score points.
 * Using the cheats will zero the score points.
 */
class Scorer : Serializable {
    private val nominations = mutableListOf<ScoreNomination>()
    private val ordinalNominations: List<ScoreNomination>
        get() = nominations.filterNot { it.final }
    private val finalNominations: List<ScoreNomination>
        get() = nominations.filter { it.final }

    /**
     * Final score.
     */
    private val finalScore: Int
        get() {
            fun List<ScoreNomination>.apply(beginningScore: Double): Double {
                var score = beginningScore

                this.forEach {
                    when (it.arithmeticAction) {
                        ADD -> score += it.score
                        TAKE -> score -= it.score
                        MULTIPLY -> score *= it.score
                        DIVIDE -> score /= it.score
                        ADD_PERCENT -> score *= 1 + it.score / 100
                        TAKE_PERCENT -> score *= 1 - it.score / 100
                    }
                }

                return score
            }

            var score = ordinalNominations.apply(0.0)
            score = finalNominations.apply(score)

            return score.toInt()
        }

    /**
     * Adds a nomination.
     *
     * @param comment a nomination comment.
     * @param score a nomination score/multiplier/divider.
     * @param arithmeticAction an arithmetic action to perform with score.
     * @param final whether to apply the nomination at the end.
     */
    fun countOut(comment: String, score: Double, arithmeticAction: ArithmeticAction, final: Boolean = false) =
            nominations.add(ScoreNomination(comment, score, arithmeticAction, final))

    /**
     * Adds a nomination.
     *
     * @param comment a nomination comment.
     * @param score a nomination score/multiplier/divider.
     * @param arithmeticAction an arithmetic action to perform with score.
     * @param final whether to apply the nomination at the end.
     */
    fun countOut(comment: String, score: Int, arithmeticAction: ArithmeticAction, final: Boolean = false) =
            nominations.add(ScoreNomination(comment, score.toDouble(), arithmeticAction, final))

    /**
     * Adds a nomination.
     *
     * @param scoreNomination a nomination to add.
     */
    fun countOut(scoreNomination: ScoreNomination) = nominations.add(scoreNomination)

    /**
     * Text stating all score nominations.
     */
    private val scoreText: String
        get() {
            var text = "Score: $finalScore\n"

            ordinalNominations.forEach {
                text += it.toString()
            }

            return text
        }

    /**
     * Shows a dialog frame that displays all score nominations.
     * @param parentComponent component to align a dialog frame with.
     */
    fun showScoreDialog(parentComponent: Component? = null) {
        JOptionPane.showMessageDialog(parentComponent, scoreText)
    }

    fun countOutInitialScore() {
        countOut("Bladder at start - ${core.character.bladder.fullness}%", core.character.bladder.fullness,
                ArithmeticAction.ADD)

        //Scoring incontinence
        countOut("Incontinence - ${core.character.bladder.incontinence}x", core.character.bladder.incontinence,
                ArithmeticAction.MULTIPLY, true)

        if (core.hardcore) countOut("Hardcore", 2, ArithmeticAction.MULTIPLY)
    }
}