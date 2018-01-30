package longHourAndAHalf

import java.io.Serializable

abstract class WearModel(
        val name: String,
        val inGameName: String = name.toLowerCase(),
        val pressure: Double,
        val absorption: Double,
        val drying: Double,
        val colorable: Boolean = true
) : Serializable

class UnderwearModel(
        name: String,
        inGameName: String = name.toLowerCase(),
        pressure: Double,
        absorption: Double,
        drying: Double,
        colorable: Boolean = true
) : WearModel(name, inGameName, pressure, absorption, drying, colorable)

class OuterwearModel(
        name: String,
        inGameName: String = name.toLowerCase(),
        pressure: Double,
        absorption: Double,
        drying: Double,
        colorable: Boolean = true
) : WearModel(name, inGameName, pressure, absorption, drying, colorable)