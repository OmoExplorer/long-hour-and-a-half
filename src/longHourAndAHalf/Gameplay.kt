package longHourAndAHalf

/**
 * Contains all gameplay data.
 *
 * An Gameplay object is created in [startGame] and then passed to all game components' constructors.
 *
 * @author JavaBird
 *
 * @property character Active character.
 * @property difficulty Game difficulty level.
 */
class Gameplay(val character: Character, val difficulty: Difficulty) {
    val plot = GameSettings.createPlot(this)
}