package longHourAndAHalf

import javax.swing.JFrame

/**
 * New game interface powered by Swing library.
 * @author JavaBird
 */
class NewUI(override val gameplay: Gameplay) : JFrame(GameMetadata.NAME), UI {
    /**
     * Function that called every time the turn occurred, slide is changed
     * and the game waits for an action to be selected by a player.
     *
     * The new slide text and the list of available actions are passed as a parameter.
     *
     * The function returns a selected action.
     */
    override fun doTurn(slideText: SlideText, actions: List<Action>): Action {
        TODO("not implemented")
    }
}