package longHourAndAHalf

abstract class Action(val text: String) {
    override fun toString() = text
}

class CallbackAction(text: String, val callback: () -> Unit) : Action(text)

class PlotRotateAction(text: String, val nextStageID: PlotStageID) : Action(text)

fun action(text: String, callback: () -> Unit) = CallbackAction(text, callback)
fun action(text: String, nextStageID: PlotStageID) = PlotRotateAction(text, nextStageID)