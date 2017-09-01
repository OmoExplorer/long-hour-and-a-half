package omo

import java.io.Serializable

enum class Gender : Serializable {
    FEMALE, MALE
}

class Character(
        var name: String,
        var gender: Gender,
        var bladder: Bladder,
        var level: Int,
        var xp: Int
) : Serializable {
    class GameState(
            var undies: Wear,
            var lower: Wear
    )

    var gameState: GameState = GameState(Wear("Error", "error"), Wear("Error", "error"))

    fun setupGameState(initialBladderFullness: Double, undies: Wear, lower: Wear) {
        this.bladder.gameState = Bladder.GameState(initialBladderFullness)
        gameState = GameState(undies, lower)
    }
}