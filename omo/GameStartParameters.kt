package omo

class GameStartParameters(game: ALongHourAndAHalf) {
    val state = game.state

    fun load(game: ALongHourAndAHalf) {
        game.state = state
    }
}