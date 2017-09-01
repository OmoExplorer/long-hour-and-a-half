package omo

class GameStartParameters(game: ALongHourAndAHalf) {
    val character: Character = game.character
    val hardcore: Boolean = game.hardcore

    fun load(game: ALongHourAndAHalf) {
        game.character = character
        game.hardcore = hardcore
    }
}