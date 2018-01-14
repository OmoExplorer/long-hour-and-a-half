package longHourAndAHalf

import longHourAndAHalf.ui.setupFramePre
import java.util.*
import javax.swing.tree.DefaultMutableTreeNode

/**
 * Creates a new game by creating [Core] and setting it up.
 *
 * @property setupFrame Frame to get the game settings from.
 */
class Launcher(private val setupFrame: setupFramePre) {
    /**
     * Random number generator.
     */
    private val random = Random()

    /**
     * Runs the game with selected parameters.
     */
    fun runGame() {
        val name = retrieveCharacterNameFromSetupUI()
        val gender = retrieveGenderFromSetupUI()
        val bladderFullnessAtStart = retrieveBladderFullnessFromSetupUI()
        val hardcore = retrieveIsHardcoreFromSetupUI()
        val underwearToAssign = getWearFromSelectedTreeNode(WearType.UNDERWEAR) ?: return
        val outerwearToAssign = getWearFromSelectedTreeNode(WearType.OUTERWEAR) ?: return
        val incontinence = getSelectedIncontinence()

        val parameters = GameParameters(
                name,
                gender,
                bladderFullnessAtStart,
                incontinence,
                underwearToAssign,
                outerwearToAssign,
                setupFrame.undiesColor,
                setupFrame.lowerColor,
                hardcore
        )

        GameInitializer.createGame(parameters)

        setupFrame.dispose()
    }

    private fun retrieveIsHardcoreFromSetupUI() = setupFrame.hardDiffRadio.isSelected

    private fun retrieveCharacterNameFromSetupUI() = setupFrame.nameField.text!!

    /**
     * Retrieves a bladder fullness level from [setupFrame].
     */
    private fun retrieveBladderFullnessFromSetupUI(): Int {
        val userSelectedBladderFullnessRadioButton = setupFrame.basSliderRadio
        val userSelectedBladderFullnessSlider = setupFrame.basSlider

        val userSelectsFullness = userSelectedBladderFullnessRadioButton.isSelected

        return if (userSelectsFullness) {
            val userSelectedBladderFullness = userSelectedBladderFullnessSlider.value
            userSelectedBladderFullness
        } else {
            val randomBladderFullness = random.nextInt(Bladder.RANDOM_FULLNESS_CAP)
            randomBladderFullness
        }
    }

    /**
     * Retrieves the selected gender from [setupFrame].
     */
    private fun retrieveGenderFromSetupUI(): Gender {
        val femaleRadio = setupFrame.femaleRadio

        return if (femaleRadio.isSelected)
            Gender.FEMALE
        else
            Gender.MALE
    }

    private fun getWearFromSelectedTreeNode(type: WearType): AbstractWearModel? {
        val tree = when (type) {
            WearType.UNDERWEAR -> setupFrame.underwearTree
            WearType.OUTERWEAR -> setupFrame.outerwearTree
        }

        val node = tree.lastSelectedPathComponent

        return if (node == null)
            when (type) {
                WearType.UNDERWEAR -> UnderwearModel.getRandom()
                WearType.OUTERWEAR -> OuterwearModel.getRandom()
            }
        else
            (node as DefaultMutableTreeNode).userObject as AbstractWearModel
    }

    private fun getSelectedIncontinence(): Double {
        var incontinence = (setupFrame.incontSlider.value / 10).toDouble()
        if (incontinence < 1) incontinence += 0.5
        return incontinence
    }
}