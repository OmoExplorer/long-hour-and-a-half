package longHourAndAHalf

import java.io.Serializable

class Plot(@Transient val core: Core) : Serializable {
    /**
     * The current stage's ID.
     *
     * @see PlotStageID, PlotMap
     */
    var nextStageID = PlotStageID.STARTUP

//    var nextStageID = Plot

    /**
     * Whether special hardcore stage is active now.
     */
    var specialHardcoreStage = false

    private val plotMap = PlotMap(core)

    /**
     * Moves the plot to a next stage.
     */
    fun advanceToNextStage() {
        class StageNotFoundException(id: PlotStageID) : Exception("stage with ID ${id.name} couldn't be found")

        val nextStage = plotMap[nextStageID] ?: throw StageNotFoundException(nextStageID)
        nextStageID = nextStage.nextStageID!!

        unpackStage(nextStage)
    }

    private fun unpackStage(stage: PlotStage) {
        core.ui.forcedTextChange(stage.text)
        stage.operations()
        core.world.time += stage.duration
        stage.scoreNomination?.let { core.scorer.countOut(stage.scoreNomination) }

        if (!stage.actions.isEmpty()) {
            core.ui.actionsChanged(stage.actionGroupName, stage.actions)
            core.ui.actionMustBeSelected = true
        } else {
            core.ui.hideActionUI()
            core.ui.actionMustBeSelected = false
        }
    }

    init {
        advanceToNextStage()
    }
}