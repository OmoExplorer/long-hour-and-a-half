package omo

import com.sun.javafx.util.Utils.clamp

class BladderGameState(
        val bladder: Bladder,
        fullness: Double
) {
    lateinit var game: ALongHourAndAHalf

    var fullness = fullness
        set(value) {
            field = clamp(value, 0.0, 150.0)
            game.gameFrame.lblBladder.text = "Bladder: $field%"
            game.gameFrame.lblBladder.foreground = if (field > bladder.criticalFullness)
                java.awt.Color.BLACK
            else
                java.awt.Color.RED
        }

    var sphincterStrength = bladder.maximalSphincterStrength
        set(value) {
            field = clamp(value, 0.0, bladder.maximalFullness)
            if (field < 0) {
                leak(game)
            }

            game.gameFrame.lblSphPower.text = "Sphincter power: $field"
        }

    fun leak(game: ALongHourAndAHalf) {
        with(game.state.characterState) {
            wearState.dryness -= 5
            bladderState.fullness -= 2.5
            bladderState.sphincterStrength = 0.0
        }

        game.overrideStage(
                if (game.state.characterState.wearState.dryness > WearGameState.MINIMAL_DRYNESS) {
                    StageID.LEAK
                } else {
                    StageID.FATAL_LEAK
                }
        )
    }

    fun sphincterSpasm(game: ALongHourAndAHalf) {
        with(game.state.characterState) {
            bladderState.sphincterStrength = 0.0
            if (wearState.dryness < WearGameState.MINIMAL_DRYNESS)
                game.overrideStage(
                        if (game.state.specialHardcoreStage)
                            StageID.SURPRISE_ACCIDENT
                        else
                            StageID.ACCIDENT
                )
        }
    }

    fun testLeak(game: ALongHourAndAHalf) {
        if (fullness > bladder.criticalFullness && chance(calculateWettingChance(game.state.hardcore, game.state.characterState.embarrassment))) {
            sphincterSpasm(game)
        }
    }

    private fun calculateWettingChance(hardcore: Boolean, embarrassment: Int) = (if (hardcore) 3 else 5) *
            (fullness - 100) + embarrassment
}