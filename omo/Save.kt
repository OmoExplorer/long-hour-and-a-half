package omo

import omo.ALongHourAndAHalf.GameStage
import java.io.Serializable

/**
 *
 * @author JavaBird
 */
class Save(game: ALongHourAndAHalf) : Serializable {
    val character: Character = game.character
    val hardcore: Boolean = game.hardcore
    val time: Int = game.time
    val stage: GameStage = game.nextStage
    val scorer: Scorer = game.scorer
    val timesPeeDenied: Int = game.timesPeeDenied
    val timesCaught: Int = game.timesCaught
    val classmatesAwareness: Int = game.classmatesAwareness
    var stay: Boolean = game.stay
    var cornered: Boolean = game.cornered
    var drain: Boolean = game.drain
    var cheatsUsed: Boolean = game.cheatsUsed
    var boyName: String = game.boyName

    fun restore(game: ALongHourAndAHalf) {
        game.character = character
        game.hardcore = hardcore
        game.time = time
        game.nextStage = stage
        game.scorer = scorer
        game.timesPeeDenied = timesPeeDenied
        game.timesCaught = timesCaught
        game.classmatesAwareness = classmatesAwareness
        game.stay = stay
        game.cornered = cornered
        game.drain = drain
        game.cheatsUsed = cheatsUsed
        game.boyName = boyName
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
