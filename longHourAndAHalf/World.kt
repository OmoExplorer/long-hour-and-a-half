package longHourAndAHalf

import kotlin.properties.Delegates

class World(val game: ALongHourAndAHalf) {
    var time: Time by Delegates.observable(ALongHourAndAHalf.gameBeginningTime) { _, oldValue, newValue ->
        game.passTime(newValue.rawMinutes - oldValue.rawMinutes)
        game.ui.timeChanged(time)
    }

    private fun offsetTime(amount: Int) {
        if (game.drain and (time.minutes % 15 == 0)) {
            game.character.fullness = 0.0
        }
        //Clothes drying over time
        if (game.character.dryness < game.character.maximalDryness) {
            game.character.dryness += Math.min(
                    game.character.lower.dryingOverTime + game.character.undies.dryingOverTime * (amount / 3),
                    game.character.maximalDryness
            )
        }
    }
}