package omo

import java.io.Serializable

/**
 *
 * @author JavaBird
 */
class Save(game: ALongHourAndAHalf) : Serializable {
    val gameState = game.state
    val scorer = game.scorer
    val stage: Stage = game.nextStage

    companion object {
        private const val serialVersionUID = 1L
    }
}
