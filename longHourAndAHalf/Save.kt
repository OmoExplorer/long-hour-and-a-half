package longHourAndAHalf

import java.io.Serializable

class Save(game: Core) : Serializable {
    val character = game.character
    val hardcore = game.hardcore
    val world = game.world
    val plot = game.plot
    val scorer = game.scorer
    val schoolDay = game.schoolDay
    val cheatData = game.cheatData
}