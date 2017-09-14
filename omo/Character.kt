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
    fun setupGameState(
            initialBladderFullness: Double,
            undies: Wear,
            lower: Wear,
            undiesColor: String,
            lowerColor: String
    ) {
        TODO()
//        undies.gameState = undies.GameState(undiesColor)
//        lower.gameState = lower.GameState(lowerColor)
//        bladder.gameState = bladder.GameState(initialBladderFullness)
//        gameState = GameState(undies, lower)
    }
}