package omo

import java.io.Serializable

fun clamp(number: Int, lowerBound: Int, upperBound: Int): Int {
    var clamped = number
    clamped = Math.min(clamped, upperBound)
    clamped = Math.max(clamped, lowerBound)

    return clamped
}

fun clamp(number: Double, lowerBound: Double, upperBound: Double): Double {
    var clamped = number
    clamped = Math.min(clamped, upperBound)
    clamped = Math.max(clamped, lowerBound)

    return clamped
}

class GameState(
        val game: ALongHourAndAHalf,
        val characterState: CharacterGameState,
        hardcore: Boolean,
        character: Character,
        bladderState: CharacterGameState.BladderGameState,
        wearState: CharacterGameState.WearGameState,
        bladder: Bladder,
        fullness: Double,
        undies: Wear,
        lower: Wear,
        undiesColor: Color,
        lowerColor: Color
) : Serializable {
    var lesson = Lesson(hardcore)
    var cheatsUsed = false
    var specialHardcoreStage = false

    companion object {
        const val MAXIMAL_THIRST = 30

        /**
         * The dryness game over minimal threshold.
         */
        const val MINIMAL_DRYNESS = 0
    }
}