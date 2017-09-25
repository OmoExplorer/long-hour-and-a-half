package omo

class BladderGameState(
        val bladder: Bladder,
        var fullness: Double
) {
    var sphincterStrength = bladder.maximalSphincterStrength
}