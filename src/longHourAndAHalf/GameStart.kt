/*
 * Top-level functions for starting the game.
 *
 * startGame() - starts a new game.
 * loadGame() - loads a game.
 */
package longHourAndAHalf

/** Constructs a gameplay and starts the game. */
fun startGame(
        characterProfile: CharacterProfile,
        difficulty: Difficulty,
        underwearModel: UnderwearModel,
        outerwearModel: OuterwearModel,
        underwearColor: WearColor,
        outerwearColor: WearColor
) {
    val character = Character(
            characterProfile,
            Underwear(underwearModel, underwearColor),
            Outerwear(outerwearModel, outerwearColor)
    )
    val gameplay = Gameplay(character, difficulty)
}

/** Loads the game from a save file. */
fun loadGame() {
    TODO()
}