package longHourAndAHalf.characterProfileEditor

import longHourAndAHalf.CharacterProfile
import longHourAndAHalf.Gender.FEMALE
import longHourAndAHalf.Gender.MALE
import longHourAndAHalf.display
import javax.swing.*

/**
 * Frame that allows user to create and edit [character profiles][CharacterProfile].
 * @author JavaBird
 */
class CharacterProfileEditor : JFrame("Character editor") {
    // Character name text field
    private val nameField = JTextField("Name")

    // Gender switches
    private val femaleRadio = JRadioButton("Female")
    private val maleRadio = JRadioButton("Male")
//    private val genderGroup = ButtonGroup().apply {
//        add(femaleRadio)
//        add(maleRadio)
//    }

    // Incontinence multiplier slider
    private val incontinenceSlider = JSlider(1, 5)

    init {
        contentPane.layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)
        addComponentsToFrame()
        display()
    }

    private fun addComponentsToFrame() {
        add(nameField)
        addGenderSwitchesToFrame()
        add(incontinenceSlider)

        addControlButtonsToFrame()
    }

    private fun addControlButtonsToFrame() {
        addOpenButtonToFrame()
        addSaveButtonToFrame()
    }

    private fun addSaveButtonToFrame() {
        add(JButton("Save...").apply {
            addActionListener {
                val name = nameField.text
                val gender = if (femaleRadio.isSelected) FEMALE else MALE
                val incontinence = incontinenceSlider.value

                val profile = CharacterProfile(name, gender, incontinence.toDouble())
                CharacterProfileFileChooser().saveObject(profile, this)
            }
        })
    }

    private fun addOpenButtonToFrame() {
        add(JButton("Open...").apply {
            addActionListener {
                val profile = CharacterProfileFileChooser().askForObject(this) ?: return@addActionListener

                nameField.text = profile.name
                when (profile.gender) {
                    FEMALE -> femaleRadio.isSelected = true
                    MALE -> maleRadio.isSelected = true
                }
                incontinenceSlider.value = profile.incontinence.toInt()
            }
        })
    }

    private fun addGenderSwitchesToFrame() {
        add(femaleRadio)
        add(maleRadio)
    }
}