package omo

import omo.Wear.Color
import java.io.Serializable

class GameState(
        val characterState: CharacterGameState,
        var hardcore: Boolean
) : Serializable {
    class CharacterGameState(
            val character: Character,
            val bladderState: BladderGameState,
            val wearState: WearGameState
    ) {
        class BladderGameState(
                val bladder: Bladder,
                var fullness: Double
        ) {
            var sphincterStrength = bladder.maximalSphincterStrength
        }

        class WearGameState(
                var undies: Wear,
                var lower: Wear,
                val undiesColor: Color,
                val lowerColor: Color
        ) {
            var dryness = undies.absorption + lower.absorption
            var wearMode = when {
                undies.isMissing && lower.isMissing -> Wear.Mode.NONE
                undies.isMissing && !lower.isMissing -> Wear.Mode.LOWER
                !undies.isMissing && lower.isMissing -> Wear.Mode.UNDIES
                !undies.isMissing && !lower.isMissing -> Wear.Mode.BOTH
                else -> throw IllegalStateException("""Can't set wear mode
                                                    |    undies.isMissing = ${undies.isMissing}
                                                    |    lower.isMissing = ${lower.isMissing}""".trimMargin())
            }
        }

        /**
         * Whether or not character currently stands in the corner and unable to
         * hold crotch.
         */
        var cornered: Boolean = false
        var embarrassment: Int = 0
        var belly: Double = 0.0
        var thirst: Int = 0

        companion object {
            const val MAXIMAL_THIRST = 30
        }
    }

    var lesson = Lesson(hardcore)
    var cheatsUsed = false
    var specialHardcoreStage = false
}