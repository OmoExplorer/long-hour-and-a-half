package omo

class Stage(
        val text: Text = Text.empty,
        val operations: () -> Unit = {},
        val nextStage: StageID? = StageID.END_GAME,
        val actionGroupName: String = "",
        val actions: List<Action> = listOf<Action>(),
        val duration: Int = 0,
        val score: Int = 0,
        val cheatLists: List<List<Cheat>> = listOf(Cheat.global),
        val nextStagePriority: NextStagePriority = NextStagePriority.ACTION
) {
    class Text(vararg lines: Line) {
        class Line(
                val line: String,
                val italic: Boolean = false
        ) {
            companion object {
                val empty = Line("")
            }
        }

        val lines = lines

        companion object {
            val empty = Text(Line(""))
        }
    }

    enum class NextStagePriority {
        ACTION,
        DEFINED
    }
}