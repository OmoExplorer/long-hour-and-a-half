package omo

class StageNotFoundException(stage: Stage.Companion.StageID) : Exception("Can't find stage ${stage.name}")

class Action(
        val name: String,
        val stage: Stage
) {
    constructor(name: String, stage: Stage.Companion.StageID) : this(name, Stage.map[stage] ?: throw StageNotFoundException(stage))
}