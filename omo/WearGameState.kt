package omo

class WearGameState(
        var undies: Wear,
        var lower: Wear,
        val undiesColor: Wear.Color,
        val lowerColor: Wear.Color
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