package longHourAndAHalf

import longHourAndAHalf.ui.setupFramePre
import java.util.*
import javax.swing.JRadioButton
import javax.swing.JSlider
import javax.swing.JTextField
import javax.swing.JTree
import javax.swing.tree.DefaultMutableTreeNode

/**
 * Creates a new game by creating [Core] and setting it up.
 */
object Launcher {
    private val random = Random()

    /**
     * Runs the game with selected parameters.
     */
    fun runGame(
            setupFrame: setupFramePre,
            nameField: JTextField,
            femaleRadio: JRadioButton,
            userSelectedBladderFullnessRadio: JRadioButton,
            userSelectedBladderFullnessSlider: JSlider,
            incontinenceSlider: JSlider,
            hardcoreDifficultyRadio: JRadioButton,
            underwearTree: JTree,
            outerwearTree: JTree,
            underwearColor: WearColor,
            outerwearColor: WearColor
    ) {
        val name = nameField.text!!

        val gender = getGender(femaleRadio)

        val bladderFullnessAtStart =
                getBladderFullness(userSelectedBladderFullnessRadio, userSelectedBladderFullnessSlider)

        val hardcore = hardcoreDifficultyRadio.isSelected

        var underwearToAssign = getWearFromSelectedTreeNode(underwearTree.lastSelectedPathComponent,
                WearType.UNDERWEAR) ?: return
        var outerwearToAssign = getWearFromSelectedTreeNode(outerwearTree.lastSelectedPathComponent,
                WearType.OUTERWEAR) ?: return

        val underwearAndOuterwear = handleMaintenanceWear(underwearToAssign, outerwearToAssign)
        outerwearToAssign = underwearAndOuterwear.first
        underwearToAssign = underwearAndOuterwear.second

        handleSpecialWearColors(underwearToAssign, outerwearToAssign, underwearColor, outerwearColor)

        assertIncorrectWear(underwearToAssign, outerwearToAssign)

        val incontinence = getSelectedIncontinence(incontinenceSlider)

        launchCore(
                gender,
                bladderFullnessAtStart,
                incontinence,
                underwearToAssign,
                outerwearToAssign,
                hardcore,
                nameField
        )

        setupFrame.dispose()
    }

    private fun getBladderFullness(
            userSelectedBladderFullnessRadio: JRadioButton,
            userSelectedBladderFullnessSlider: JSlider
    ): Int {
        return if (userSelectedBladderFullnessRadio.isSelected) {
            val userSelectedBladderFullness = userSelectedBladderFullnessSlider.value
            userSelectedBladderFullness
        } else {
            val randomBladderFullness = random.nextInt(Bladder.RANDOM_FULLNESS_CAP)
            randomBladderFullness
        }
    }

    private fun getGender(femaleRadio: JRadioButton): Gender {
        return if (femaleRadio.isSelected)
            Gender.FEMALE
        else
            Gender.MALE
    }

    private fun launchCore(
            gender: Gender,
            bladderFullnessAtStart: Int,
            incontinence: Double,
            underwearToAssign: AbstractWear,
            outerwearToAssign: AbstractWear,
            hardcore: Boolean,
            nameField: JTextField
    ) {
        Core(
                Character(
                        nameField.text,
                        gender,
                        bladderFullnessAtStart.toDouble(),
                        incontinence,
                        underwearToAssign as Wear,
                        outerwearToAssign as Wear
                ),
                hardcore
        )
    }

    private fun getWearFromSelectedTreeNode(node: Any?, type: WearType) = if (node == null)
        Wear.getRandom(type)
    else
        (node as DefaultMutableTreeNode).userObject as? AbstractWear

    private fun Wear.setConcreteColorBySpecialColor(color: WearColor) {
        var colorToAssign = color
        while (colorToAssign == WearColor.RANDOM)
            colorToAssign = WearColor.values().randomItem()
        this.color = colorToAssign
    }

    private fun getSelectedIncontinence(incontinenceSlider: JSlider): Double {
        var incont = (incontinenceSlider.value / 10).toDouble()
        if (incont < 1)
            incont += 0.5
        return incont
    }

    private fun handleSpecialWearColors(
            underwearToAssign: AbstractWear,
            outerwearToAssign: AbstractWear,
            undiesColor: WearColor,
            lowerColor: WearColor
    ) {
        (underwearToAssign as Wear).setConcreteColorBySpecialColor(undiesColor)
        (outerwearToAssign as Wear).setConcreteColorBySpecialColor(lowerColor)
    }

    private fun assertIncorrectWear(underwearToAssign: AbstractWear, outerwearToAssign: AbstractWear) {
        assert(underwearToAssign is Wear)
        assert(outerwearToAssign is Wear)
    }

    private fun handleMaintenanceWear(
            underwearToAssignParameter: AbstractWear,
            outerwearToAssignParameter: AbstractWear
    ): Pair<AbstractWear, AbstractWear> {
        var underwearToAssign1 = underwearToAssignParameter
        var outerwearToAssign1 = outerwearToAssignParameter
        if (underwearToAssign1 is MaintenanceWear)
            underwearToAssign1 = underwearToAssign1.instead()
        if (outerwearToAssign1 is MaintenanceWear)
            outerwearToAssign1 = outerwearToAssign1.instead()
        return outerwearToAssign1 to underwearToAssign1
    }
}