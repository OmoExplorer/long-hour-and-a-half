package longHourAndAHalf

import java.io.Serializable

/**
 * Transfers data between [plot stages][PlotStage].
 */
class PlotFlags : Serializable {
    var peeingAllowed = false
    lateinit var punishment: Punishment
    var line = false
    var hitSuccessful = false
}