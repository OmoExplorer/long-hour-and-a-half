package longHourAndAHalf.ui

import longHourAndAHalf.Core
import longHourAndAHalf.Time
import longHourAndAHalf.Wear

interface UIGameEventHandler {
    val core: Core

    fun characterNameChanged(name: String)
    fun hideBladderAndTime()
    fun showBladderAndTime()
    fun hardcoreModeToggled(on: Boolean)
    fun bladderFullnessChanged(fullness: Double)
    fun embarrassmentChanged(embarrassment: Int)
    fun bellyWaterLevelChanged(bellyWaterLevel: Double)
    fun incontinenceMultiplierChanged(incontinenceMultiplier: Double)
    fun timeChanged(time: Time)
    fun sphincterStrengthChanged(sphincterStrength: Int)
    fun wearDrynessChanged(wearDryness: Double)
    fun thirstChanged(thirst: Int)
    fun underwearChanged(underwear: Wear)
    fun outerwearChanged(outerwear: Wear)
    fun forcedTextChange(vararg text: String)
    fun warnAboutLeaking(vararg warnText: String)
}