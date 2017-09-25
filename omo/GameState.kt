package omo

import java.io.Serializable

class GameState(
        val characterState: CharacterGameState,
        var hardcore: Boolean
) : Serializable {
    var lesson = Lesson(hardcore)
    var cheatsUsed = false
    var specialHardcoreStage = false
}