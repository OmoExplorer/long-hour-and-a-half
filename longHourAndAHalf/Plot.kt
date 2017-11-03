package longHourAndAHalf

import java.io.Serializable

class Plot : Serializable {
    /**
     * A stage after the current stage.
     */
    var nextStage = GameStage.LEAVE_BED

    /**
     * Actions list.
     */
    val actionList = mutableListOf<String>()

    /**
     * Whether special hardcore stage is active now.
     */
    var specialHardcoreStage = false
}