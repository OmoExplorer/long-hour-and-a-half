package longHourAndAHalf

/**
 * Option of character action.
 *
 * Actions are offered to a user in interface
 * by calling the [longHourAndAHalf.ui.UI.actionsChanged] function
 * with an action group name and a list of actions as arguments.
 *
 * Action group name is a hint for the user that describes the group of actions, for example "What to do?".
 *
 * This class is abstract and inherited by [CallbackAction]
 * and [PlotRotateAction].
 *
 * @property name Name of this action, for example "Hold crotch".
 *
 * @see PlotStageID
 * @see PlotStage
 * @see Plot
 * @see PlotMap
 */
abstract class Action internal constructor(@Suppress("KDocMissingDocumentation") val name: String) {
    /** Returns a [name] of this action. */
    override fun toString() = name
}

/**
 * Action which does something when being selected.
 *
 * When this action is selected, the [callback] is being executed in
 * a [Plot.advanceToNextStage] call.
 *
 * *This class has the internal constructor, so modders can't create its instances explicitly.
 * Use the [action] function instead to create an action.*
 *
 * @param name name of this action, for example "Hold crotch".
 * @property callback Callback to execute when this action is selected.
 *
 * @see PlotStageID
 * @see PlotStage
 * @see Plot
 * @see PlotMap
 */
class CallbackAction internal constructor(
        name: String,
        @Suppress("KDocMissingDocumentation") val callback: () -> Unit
) : Action(name)

/**
 * Action which changes the plot stage when being selected.
 *
 * When this action is selected,
 * the plot stage assigned to [nextStageID]
 * is being set as the current stage in a [Plot.advanceToNextStage] call.
 *
 * *This class has the internal constructor, so modders can't create its instances explicitly.
 * Use the [action] function instead to create an action.*
 *
 * @param name Name of this action, for example "Hold crotch".
 * @property nextStageID ID of the stage which will be set as current when this action is selected.
 *
 * @see PlotStageID
 * @see PlotStage
 * @see Plot
 * @see PlotMap
 */
class PlotRotateAction internal constructor(
        name: String,
        @Suppress("KDocMissingDocumentation") val nextStageID: PlotStageID
) : Action(name)

/**
 * Factory function that creates different action types depending on passed parameters.
 *
 * @param text name of new action, for example "Hold crotch".
 * @return newly created [Action] subclass instance.
 */
fun action(text: String, callback: () -> Unit) = CallbackAction(text, callback)

/**
 * Factory function that creates different action types depending on passed parameters.
 *
 * @param text name of new action, for example "Hold crotch".
 * @return newly created [Action] subclass instance.
 */
fun action(text: String, nextStageID: PlotStageID) = PlotRotateAction(text, nextStageID)