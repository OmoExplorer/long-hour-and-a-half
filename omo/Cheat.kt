package omo

import javax.swing.JOptionPane

//TODO: Make accessible
open class Cheat(
        val name: String,
        open val use: (game: ALongHourAndAHalf) -> Unit
) {
    companion object {
        private val goToCorner = Cheat("Go to the corner") {
            it.state.characterState.cornered = !it.state.characterState.cornered
        }
        private val stayAfterClass = Cheat("Stay after class") {
            it.state.lesson.teacher.stay = !it.state.lesson.teacher.stay
        }
        private val peeInBottle = Cheat("Pee in a bottle") {
            it.currentStage = it.stageMap[StageID.USE_BOTTLE] ?:
                    throw StageNotFoundException(StageID.USE_BOTTLE)
        }
        private val endLesson = Cheat("End class right now") {
            it.currentStage = it.stageMap[StageID.CLASS_OVER] ?:
                    throw StageNotFoundException(StageID.CLASS_OVER)
        }
        private val calmTeacher = Cheat("Calm the teacher down") {
            it.state.lesson.teacher.timesPeeDenied = 0
        }
        private val raiseHand = Cheat("Raise your hand") {
            it.currentStage = it.stageMap[StageID.CALLED_ON] ?:
                    throw StageNotFoundException(StageID.CALLED_ON)
        }
        private val drainPee = object : ToggleCheat("Make your pee disappear regularly", {}) {
            private var a = 0
            override val use: (game: ALongHourAndAHalf) -> Unit = {
                if (a >= 5) {
                    it.state.characterState.bladderState.fullness = 0.0
                    a = 0
                } else
                    a++
            }
        }
        private val toggleHardcore = Cheat("Toggle hardcore mode") {
            it.state.hardcore = !it.state.hardcore
        }
        private val setFullness = Cheat("Set bladder fullness") {
            val new = JOptionPane.showInputDialog(it.gameFrame, "Enter new bladder fullness:",
                    it.state.characterState.bladderState.fullness).toDouble()
            it.state.characterState.bladderState.fullness = new
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