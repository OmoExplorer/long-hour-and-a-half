package omo

import java.io.Serializable

class Bladder(var incontinence: Double) : Serializable {
    inner class GameState(
            var fullness: Double
    ) {
        var sphincterStrength = maximalSphincterStrength
    }

    var gameState: GameState? = null

    var maximalSphincterStrength = 100 / incontinence

    var maximalFullness = 130.0
}