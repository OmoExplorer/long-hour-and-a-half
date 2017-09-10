package omo

import javax.swing.JOptionPane

open class Cheat(
        val name: String,
        open val use: (game: ALongHourAndAHalf) -> Unit
) {
    companion object {
        private val goToCorner = Cheat("Go to the corner") {
            it.character.gameState!!.cornered = !it.character.gameState!!.cornered
        }
        private val stayAfterClass = Cheat("Stay after class") {
            it.lesson.teacher.stay = !it.lesson.teacher.stay
        }
        private val peeInBottle = Cheat("Pee in a bottle") {
            it.nextStage = Stage.map[Stage.Companion.StageID.USE_BOTTLE] ?:
                    throw StageNotFoundException(Stage.Companion.StageID.USE_BOTTLE)
        }
        private val endLesson = Cheat("End class right now") {
            it.nextStage = Stage.map[Stage.Companion.StageID.CLASS_OVER] ?:
                    throw StageNotFoundException(Stage.Companion.StageID.CLASS_OVER)
        }
        private val calmTeacher = Cheat("Calm the teacher down") {
            it.lesson.teacher.timesPeeDenied = 0
        }
        private val raiseHand = Cheat("Raise your hand") {
            it.nextStage = Stage.map[Stage.Companion.StageID.CALLED_ON] ?:
                    throw StageNotFoundException(Stage.Companion.StageID.CALLED_ON)
        }
        private val drainPee = object : ToggleCheat("Make your pee disappear regularly", {}) {
            private var a = 0
            override val use: (game: ALongHourAndAHalf) -> Unit = { game ->
                if (a >= 5) {
                    game.character.bladder.gameState!!.fullness = 0.0
                    a = 0
                }
                else
                    a++
            }
        }
        private val toggleHardcore = Cheat("Toggle hardcore mode") { game ->
            game.hardcore = !game.hardcore
        }
        private val setFullness = Cheat("Set bladder fullness") { game ->
            val new = JOptionPane.showInputDialog(game.gameFrame, "Enter new bladder fullness:",
                    game.character.bladder.gameState!!.fullness).toDouble()
            game.character.bladder.gameState!!.fullness = new
        }

        val lesson = listOf(
                goToCorner,
                stayAfterClass,
                endLesson,
                peeInBottle
        )
        val global = listOf(
                drainPee,
                toggleHardcore,
                setFullness
        )
    }
}

open class ToggleCheat(
        name: String,
        use: (game: ALongHourAndAHalf) -> Unit
) : Cheat(name, use) {
    var on = false

    fun toggle() {
        on = !on
    }
}