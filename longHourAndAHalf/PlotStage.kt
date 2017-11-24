package longHourAndAHalf

/**
 * Slide of the game.
 */
data class PlotStage(
        val text: Text = Text.empty,
        val operations: () -> Unit = {},
        val nextStageID: PlotStageID,
        val actionGroupName: String = "",
        val actions: List<Action> = listOf(),
        val duration: Int = Core.TURN_DURATION,
        val scoreNomination: ScoreNomination? = null//,
//        val cheatLists: List<List<Cheat>> = listOf(Cheat.global),
//        val nextStagePriority: NextStagePriority = NextStagePriority.ACTION
)