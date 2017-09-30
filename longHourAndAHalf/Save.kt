/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longHourAndAHalf

import java.io.Serializable

/**
 * @author JavaBird
 */
internal class Save : Serializable {

    var name: String? = null //Already has been given to the constructor
    var hardcore: Boolean = false //Already has been given to the constructor
    var gender: ALongHourAndAHalf.Gender? = null //Already has been given to the constructor
    var bladder = 0.0 //Already has been given to the constructor
    var incontinence: Double = 0.0 //Already has been given to the constructor
    var belly: Int = 0 //Already has been given to the constructor
    var embarassment: Int = 0
    var dryness: Double = 0.0
    var maxSphincterPower: Int = 0
    var sphincterPower: Int = 0
    var underwear: Wear? = null
    var outerwear: Wear? = null
    var time: Int = 0
    var stage: ALongHourAndAHalf.GameStage? = null
    var score: Int = 0
    var scoreText: String? = null
    var timesPeeDenied: Int = 0
    var timesCaught: Int = 0
    var classmatesAwareness: Int = 0
    var stay: Boolean = false
    var cornered: Boolean = false
    var drain: Boolean = false
    var cheatsUsed: Boolean = false
    var boyName: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }

    var character: Character? = null
}
