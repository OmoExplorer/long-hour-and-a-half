package longHourAndAHalf

import java.io.Serializable

class Save(
        game: ALongHourAndAHalf
) : Serializable {
    val character = game.character!!
    val hardcore = game.hardcore
    var time = game.time
    var stage = game.nextStage!!
    var score = game.score
    var scoreText = game.scoreText!!
    var timesPeeDenied = game.timesPeeDenied
    var timesCaught = game.timesCaught
    var classmatesAwareness = game.classmatesAwareness
    var stay: Boolean = game.stay
    var drain = game.drain
    var cheatsUsed = game.cheatsUsed
    var boyName = game.boyName
}