package longHourAndAHalf

abstract class Wear(model: WearModel, color: WearColor? = null) {
    val name = model.name
    val inGameName = model.inGameName
    val pressure = model.pressure
    val absorption = model.absorption
    val drying = model.drying

    val color = if (model.colorable) color else null
    var capacity = absorption

    override fun toString() = name
}

class Underwear(model: WearModel, color: WearColor?) : Wear(model, color)
class Outerwear(model: WearModel, color: WearColor?) : Wear(model, color)