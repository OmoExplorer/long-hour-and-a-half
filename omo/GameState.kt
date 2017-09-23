package omo

import omo.Wear.Color
import java.io.Serializable

fun clamp(number: Int, lowerBound: Int, upperBound: Int): Int {
    var clamped = number
    clamped = Math.min(clamped, upperBound)
    clamped = Math.max(clamped, lowerBound)

    return clamped
}

fun clamp(number: Double, lowerBound: Double, upperBound: Double): Double {
    var clamped = number
    clamped = Math.min(clamped, upperBound)
    clamped = Math.max(clamped, lowerBound)

    return clamped
}

class GameState(
        val game: ALongHourAndAHalf,
        val characterState: CharacterGameState,
        hardcore: Boolean,
        character: Character,
        bladderState: CharacterGameState.BladderGameState,
        wearState: CharacterGameState.WearGameState,
        bladder: Bladder,
        fullness: Double,
        undies: Wear,
        lower: Wear,
        undiesColor: Color,
        lowerColor: Color
) : Serializable {
    private val _character = character
    private val _bladderState = bladderState
    private val _wearState = wearState
    private val _bladder = bladder
    private val _fullness = fullness
    private var _undies: Wear = undies
    private var _lower: Wear = lower
    private val _undiesColor: Color = undiesColor
    private val _lowerColor: Color = lowerColor

    inner class CharacterGameState {
        val character = _character
        val bladderState = _bladderState
        val wearState = _wearState

        inner class BladderGameState {
            val bladder = _bladder

            fun decaySphincterStrength() {
                sphincterStrength -= fullness / 30
            }

            var sphincterStrength = bladder.maximalSphincterStrength
                set(value) {
                    field = clamp(value, 0.0, bladder.maximalFullness)
                    if (field < 0) {
                        leak()
                    }

                    game.gameFrame.lblSphPower.text = "Sphincter power: $field"
                }

            var fullness = _fullness
                set(value) {
                    field = clamp(value, 0.0, 150.0)
                    game.gameFrame.lblBladder.text = "Bladder: $field%"
                    game.gameFrame.lblBladder.foreground = if (field > bladder.criticalFullness)
                        java.awt.Color.BLACK
                    else
                        java.awt.Color.RED
                }

            /**
             * Checks the wetting conditions, and if they are met, wetting
             */
            fun testWet() {
                if (fullness >= bladder.maximalFullness ||
                        (fullness >= bladder.criticalFullness && omo.chance(calculateWettingChance())))
                    sphincterSpasm()
            }

            private fun calculateWettingChance() = (if (hardcore) 3 else 5) *
                    (fullness - 100) + embarrassment

            fun leak() {
                wearState.dryness -= 5
                bladderState.fullness -= 2.5

                game.overrideStage(
                        if (wearState.dryness > GameState.MINIMAL_DRYNESS) {
                            StageID.LEAK
                        } else {
                            StageID.FATAL_LEAK
                        }
                )
            }

            fun sphincterSpasm() {
                bladderState.sphincterStrength = 0.0
                if (wearState.dryness < GameState.MINIMAL_DRYNESS)
                    game.overrideStage(
                            if (specialHardcoreStage)
                                StageID.SURPRISE_ACCIDENT
                            else
                                StageID.ACCIDENT
                    )
            }
        }

        inner class WearGameState {
            var undies = _undies
            var lower = _lower
            val undiesColor = _undiesColor
            val lowerColor = _lowerColor

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

        /**
         * Whether or not character currently stands in the corner and unable to
         * hold crotch.
         */
        var cornered = false
        var embarrassment = 0
            set(value) {
                field = Math.max(value, 0)
                game.gameFrame.lblEmbarrassment.text = "Embarrassment: $field"
            }
        var belly = 0.0
            set(value) {
                field = Math.max(value, 0.0)
                game.gameFrame.lblBelly.text = "Belly: $field"
            }
        var thirst = 0
            set(value) {
                field = Math.max(value, 0)
                game.gameFrame.lblBelly.text = "Thirst: $field"
            }
    }

    inner class Lesson {
        inner class Teacher {
            /**
             * Times teacher denied character to go out.
             */
            var timesPeeDenied: Int = 0

            /**
             * Whether or not character has to stay 30 minutes after class.
             */
            var stay: Boolean = false
        }

        val teacher = Teacher()

        inner class Classmates {
            /**
             * Amount embarrassment raising every time character caught holding pee.
             */
            var holdingAwareness = 0

            /**
             * Number of times player got caught holding pee.
             */
            var timesCaught = 0
        }

        val classmates = Classmates()

        val surpriseBoy = SurpriseBoy()

        /**
         * The lesson time.
         */
        var time = 0
            set(value) {
                val difference = value - field
                field = value

                with(characterState.wearState) {
                    val maximalAbsorption = lower.absorption + undies.absorption
                    val summaryDryingOverTime = lower.dryingOverTime + undies.dryingOverTime
                    //Don't let dryness to go above the limit
                    dryness = Math.min(dryness + summaryDryingOverTime * (difference / 3), maximalAbsorption)
                }

                game.gameFrame.lblMinutes.text = "Time: $value of 90"
            }
    }

    var hardcore = hardcore
        set(value) {
            field = value
            //TODO
        }

    var lesson = Lesson()
    var cheatsUsed = false
    var specialHardcoreStage = false

    companion object {
        const val MAXIMAL_THIRST = 30

        /**
         * The dryness game over minimal threshold.
         */
        const val MINIMAL_DRYNESS = 0
    }
}