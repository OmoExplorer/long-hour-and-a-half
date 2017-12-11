package longHourAndAHalf

import java.io.Serializable

class Plot : Serializable {
    /**
     * The current stage's ID.
     *
     * @see PlotMap
     */
    var nextStageID = PlotStageID.STARTUP

//    var nextStageID = Plot

    /**
     * Whether special hardcore stage is active now.
     */
    var specialHardcoreStage = false

    private val plotMap = PlotMap()

    /**
     * Moves the plot to a next stage.
     */
    fun advanceToNextStage() {
        class StageNotFoundException(id: PlotStageID) : Exception("stage with ID ${id.name} couldn't be found")

        val nextStageLambda = plotMap[nextStageID] ?: throw StageNotFoundException(nextStageID)
        val nextStage = nextStageLambda()
        nextStageID = nextStage.nextStageID

        unpackStage(nextStage)
    }

    private fun unpackStage(stage: PlotStage) {
        ui.forcedTextChange(stage.text)
        stage.operations()
        core.world.time += stage.duration
        stage.scoreNomination?.let { core.scorer.countOut(stage.scoreNomination) }

        if (!stage.actions.isEmpty())
            ui.actionsChanged(stage.actionGroupName, stage.actions)
        else
            ui.hideActionUI()
    }
}