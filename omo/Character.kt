package omo

import omo.ui.GameFrame
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
    inner class GameState(
            var gameFrame: GameFrame,
            var undies: Wear,
            var lower: Wear
    ) {
        var wearMode = when {
            undies.isMissing && lower.isMissing -> Wear.Mode.NONE
            undies.isMissing && !lower.isMissing -> Wear.Mode.LOWER
            !undies.isMissing && lower.isMissing -> Wear.Mode.UNDIES
            !undies.isMissing && !lower.isMissing -> Wear.Mode.BOTH
            else -> throw IllegalStateException("""Can't set wear mode
                                                    |    undies.isMissing = ${undies.isMissing}
                                                    |    lower.isMissing = ${lower.isMissing}""".trimMargin())
        }
        var dryness = undies.absorption + lower.absorption
        /**
         * Whether or not character currently stands in the corner and unable to
         * hold crotch.
         */
        var cornered: Boolean = false
        var embarrassment: Int = 0
        var belly: Double = 0.0
        var thirst: Int = 0
    }

    var gameState: GameState? = null
        private set

    fun setupGameState(
            gameFrame: GameFrame,
            initialBladderFullness: Double,
            undies: Wear,
            lower: Wear,
            undiesColor: String,
            lowerColor: String
    ) {
        undies.gameState = undies.GameState(undiesColor)
        lower.gameState = lower.GameState(lowerColor)
        bladder.gameState = bladder.GameState(initialBladderFullness)
        gameState = GameState(gameFrame, undies, lower)
    }
}