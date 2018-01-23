package longHourAndAHalf.ui

import longHourAndAHalf.Action
import longHourAndAHalf.Text
import longHourAndAHalf.Time
import longHourAndAHalf.Wear
import javax.swing.JFrame

/** Used for updating a user interface. An UI class would implement this interface in order to listen to game events. */
interface UI {
    /** Reference to the UI frame. */
    val frame: JFrame

    /** Whether actions are present and any from them should be selected. */
    var actionsPresent: Boolean

    //Listener functions. Being run by the game.
    /** Called by the engine when the bladder fullness has been changed. */
    fun bladderFullnessChanged(fullness: Int)

    /** Called by the engine when the character embarrassment has been changed. */
    fun embarrassmentChanged(embarrassment: Int)

    /** Called by the engine when the belly water level has been changed. */
    fun bellyWaterLevelChanged(bellyWaterLevel: Int)

    /** Called by the engine when the incontinence has been changed. */
    fun incontinenceMultiplierChanged(incontinenceMultiplier: Double)

    /** Called by the engine when the time has been changed. */
    fun timeChanged(time: Time)

    /** Called by the engine when the sphincter strength has been changed. */
    fun sphincterStrengthChanged(sphincterStrength: Int)

    /** Called by the engine when the wear dryness has been changed. */
    fun wearDrynessChanged(wearDryness: Double)

    /** Called by the engine when the thirst has been changed. */
    fun thirstChanged(thirst: Int)

    /** Called by the engine when the underwear has been changed. */
    fun underwearChanged(underwear: Wear)

    /** Called by the engine when the outerwear has been changed. */
    fun outerwearChanged(outerwear: Wear)

    /** Called by the engine when the text was forced to change. */
    fun forcedTextChange(text: Text)

    /** Called by the engine when a leak has occurred. */
    fun leakOccurred()

    /** Called by the engine when the game has finished. */
    fun gameFinished()

    /** Called by the engine when the actions have been changed. */
    fun actionsChanged(actionGroupName: String, actions: List<Action>)

    /** Called by the engine when the action UI part should hide. */
    fun hideActionUI()

    /** Called by the engine when the setup has finished. */
    fun setup()

    /** Called by the engine when the slide has been changed. Use this function for custom callbacks. */
    fun slideChanged() {}
}