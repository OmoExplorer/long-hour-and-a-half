package omo

import java.io.Serializable

class Bladder(var incontinence: Double) : Serializable {
    class GameState(
            var fullness: Double,
            var sphincterStrength: Double
    )

    var gameState: GameState? = null

    var maximalSphincterStrength = 100 / incontinence
    var sphincterStrength = maximalSphincterStrength
    var maximalFullness = 130.0
}