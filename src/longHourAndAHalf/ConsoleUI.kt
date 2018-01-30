package longHourAndAHalf

/**
 * Command line game interface.
 * @author JavaBird
 *
 * @property gameplay Gameplay object to get the game data from.
 */
class ConsoleUI(override val gameplay: Gameplay) : UI {
    override fun doTurn(slideText: SlideText, actions: List<Action>): Action {
        printTurnData(slideText, actions)
        return askAction(actions)
    }

    private fun printTurnData(slideText: SlideText, actions: List<Action>) {
        with(gameplay.character) {
            println("Character: $name")
            println("Gender: $gender")
            println()
            println("Urine in bladder: ${bladder.urine} ml")
            println("Pee holding power: ${bladder.sphincterPowerPercent}")
            println("Incontinence: ${bladder.incontinence}x")
            println("Underwear: ${underwear.color} $underwear")
            println("Outerwear: ${outerwear.color} $outerwear")
            println("Underwear capacity: ${underwear.capacity}")
            println("Outerwear capacity: ${outerwear.capacity}")
            printSeparator()
            println(slideText)
            printSeparator()
            printActions(actions)
        }
    }

    private fun printActions(actions: List<Action>) {
        for (i in actions.indices)
            println("${i + 1}: ${actions[i]}")
    }

    private fun printSeparator() {
        repeat(50, { print('_') })
        println()
    }

    private fun askAction(actions: List<Action>): Action {
        print("> ")
        var actionNumber: Int?
        do
            actionNumber = readLine()?.toInt()
        while (actionNumber == null || actionNumber !in 1..actions.size)
        printSeparator()
        return actions[actionNumber - 1]
    }
}
