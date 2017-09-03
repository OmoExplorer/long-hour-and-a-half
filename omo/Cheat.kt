package omo

import javax.swing.JOptionPane

open class Cheat(
        val name: String,
        open val use: (game: ALongHourAndAHalf) -> Unit
) {
    companion object {
        private val goToCorner = Cheat("Go to the corner") {
            TODO("Refactor Lesson class before")
        }
        private val stayAfterClass = Cheat("Stay after class") {
            TODO("Refactor Lesson class before")
        }
        private val endLesson = Cheat("End class right now") {
            TODO("Refactor Stage class before")
        }
        private val calmTeacher = Cheat("Calm the teacher down") {
            TODO("Refactor Lesson class before")
        }
        private val raiseHand = Cheat("Raise your hand") {
            TODO("Refactor Stage class before")
        }
        private val drainPee = object : ToggleCheat("Make your pee disappear regularly", {}) {
            private var a = 0
            override val use: (game: ALongHourAndAHalf) -> Unit = { game ->
                if (a >= 5)
                    game.character.bladder.gameState!!.fullness = 0.0
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
                endLesson
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