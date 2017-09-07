package omo

class StageNotFoundException(stage: String) : Exception("Can't find stage $stage")

class Action(
        val name: String,
        val stage: Stage
) {
    constructor(name: String, stage: String) : this(name, Stage.map[stage] ?: throw StageNotFoundException(stage))
}