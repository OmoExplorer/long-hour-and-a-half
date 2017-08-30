package omo

import omo.ALongHourAndAHalf.GameStage
import omo.ALongHourAndAHalf.Gender
import java.io.Serializable

/**
 *
 * @author JavaBird
 */
class Save : Serializable {
    internal var name: String? = null //Already has been given to the constructor
    internal var hardcore: Boolean = false //Already has been given to the constructor
    internal var gender: Gender? = null //Already has been given to the constructor
    internal var bladder: Int = 0 //Already has been given to the constructor
    internal var incontinence: Double = 0.0 //Already has been given to the constructor
    internal var belly: Int = 0 //Already has been given to the constructor
    internal var embarassment: Int = 0
    internal var dryness: Double = 0.0
    internal var maxSphincterPower: Int = 0
    internal var sphincterPower: Int = 0
    internal var underwear: Wear? = null
    internal var outerwear: Wear? = null
    internal var time: Int = 0
    internal var stage: GameStage? = null
    internal var score: Int = 0
    internal var scoreText: String? = null
    internal var timesPeeDenied: Int = 0
    internal var timesCaught: Int = 0
    internal var classmatesAwareness: Int = 0
    internal var stay: Boolean = false
    internal var cornered: Boolean = false
    internal var drain: Boolean = false
    internal var cheatsUsed: Boolean = false
    internal var boyName: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
