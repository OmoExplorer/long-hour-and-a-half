package omo

class CharacterGameState(
        val character: Character,
        val bladderState: BladderGameState,
        val wearState: WearGameState
) {

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    var cornered: Boolean = false
    var embarrassment: Int = 0
    var belly: Double = 0.0
    var thirst: Int = 0

    companion object {
        const val MAXIMAL_THIRST = 30
    }
}