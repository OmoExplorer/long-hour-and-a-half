package longHourAndAHalf.ui

import longHourAndAHalf.Action
import longHourAndAHalf.Text
import longHourAndAHalf.Time
import longHourAndAHalf.Wear
import javax.swing.JFrame

/**
 * Used for updating the user interface. UI class would implement this interface in order to listen to the game events.
 */
interface UI {
    val frame: JFrame
    var actionMustBeSelected: Boolean

    fun characterNameChanged(name: String)
    fun hideBladderAndTime()
    fun showBladderAndTime()
    fun hardcoreModeToggled(on: Boolean)    //TODO: Remove
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
    fun forcedTextChange(text: Text)
    fun warnAboutLeaking(vararg warnText: String)
    fun gameFinished()
    fun actionsChanged(actionGroupName: String, actions: List<Action>)
    fun hideActionUI()
}