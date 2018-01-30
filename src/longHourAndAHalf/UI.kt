package longHourAndAHalf

/**
 * The thing for implementation by all game interfaces.
 *
 * If you are creating your own UI, your UI class should implement this interface.
 *
 * @author JavaBird
 */
interface UI {
    /** Gameplay instance. Use it for getting the game data to display to a player. */
    val gameplay: Gameplay

    /**
     * Function that called every time the turn occurred, slide is changed
     * and the game waits for an action to be selected by a player.
     *
     * The new slide text and the list of available actions are passed as a parameter.
     *
     * The function returns a selected action.
     */
    fun doTurn(slideText: SlideText, actions: List<Action>): Action
}