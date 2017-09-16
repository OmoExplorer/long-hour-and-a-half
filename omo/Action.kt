package omo

class StageNotFoundException(stage: StageID) : Exception("Can't find stage ${stage.name}")

data class Action(
        val name: String,
        val stage: StageID
) {
    override fun toString() = name
}