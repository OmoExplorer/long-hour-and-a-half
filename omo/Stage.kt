package omo

class Stage(
        val text: Text? = null,
        val operations: () -> Unit = {},
        val nextStage: StageID? = null,
        val actionGroupName: String? = null,
        val actions: List<Action?>? = null,
        val duration: Int = 0, val score: Int = 0,
        val cheatLists: List<List<Cheat>>? = null,
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