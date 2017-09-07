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
            var lower: Wear,
            var embarrassment: Int = 0,
            var belly: Double = 0.0,
            var thirst: Int = 0,
            /**
             * Whether or not character currently stands in the corner and unable to
             * hold crotch.
             */
            var cornered: Boolean = false
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
    }

    var gameState: GameState = GameState(Wear("Error", "error"), Wear("Error", "error"))

    fun setupGameState(initialBladderFullness: Double, undies: Wear, lower: Wear) {
        this.bladder.gameState = Bladder.GameState(initialBladderFullness, this.bladder.maximalSphincterStrength)
        gameState = GameState(undies, lower)
    }
}