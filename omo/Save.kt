package omo

import java.io.Serializable

/**
 *
 * @author JavaBird
 */
class Save(game: ALongHourAndAHalf) : Serializable {
//    val character: Character = game.character
//    val hardcore: Boolean = game.hardcore
//    val time: Int = game.lesson.time
//    val stage: GameStage = game.nextStage
//    val scorer: Scorer = game.scorer
//    val timesPeeDenied: Int = game.timesPeeDenied
//    val timesCaught: Int = game.timesCaught
//    val classmatesAwareness: Int = game.lesson.Classmates.holdingAwareness
//    var stay: Boolean = game.lesson.teacher.stay
//    var cornered: Boolean = game.character.gameState.cornered
//    var drain: Boolean = game.drain
//    var cheatsUsed: Boolean = game.cheatsUsed
//    var boyName: String = game.boyName

    fun restore(game: ALongHourAndAHalf) {
        TODO()
//        game.character = character
//        game.hardcore = hardcore
//        game.lesson.time = time
//        game.nextStage = stage
//        game.scorer = scorer
//        game.timesPeeDenied = timesPeeDenied
//        game.timesCaught = timesCaught
//        game.lesson.Classmates.holdingAwareness = classmatesAwareness
//        game.lesson.teacher.stay = stay
//        game.character.gameState.cornered = cornered
//        game.drain = drain
//        game.cheatsUsed = cheatsUsed
//        game.boyName = boyName
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
