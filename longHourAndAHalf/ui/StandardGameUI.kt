package longHourAndAHalf.ui

import longHourAndAHalf.*
import java.awt.Color
import java.awt.Font
import java.awt.Rectangle
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.border.EmptyBorder

class StandardGameUI(override val core: Core) : JFrame("A Long Hour and a Half"), UIGameEventHandler {
    override fun embarrassmentChanged(embarrassment: Int) {
        lblEmbarrassment.text = "Embarrassment: $embarrassment"
    }

    override fun bellyWaterLevelChanged(bellyWaterLevel: Double) {
        lblBelly.text = "Belly: ${Math.round(bellyWaterLevel)}%"
    }

    override fun incontinenceMultiplierChanged(incontinenceMultiplier: Double) {
        lblIncontinence.text = "Incontinence: ${incontinenceMultiplier}x"
    }

    override fun timeChanged(time: Time) {
        lblMinutes.text = "Minutes: ${Math.max(0,
                (core.world.time - SchoolDay.classBeginningTime).rawMinutes
        )} of ${SchoolDay.classDuration.rawMinutes}"
        timeBar.value = (time - SchoolDay.classBeginningTime).rawMinutes
    }

    override fun characterNameChanged(name: String) {
        lblName.text = name
    }

    override fun sphincterStrengthChanged(sphincterStrength: Int) {
        lblSphPower.text = "Pee holding ability: $sphincterStrength%"
        sphincterBar.value = sphincterStrength
    }

    override fun wearDrynessChanged(wearDryness: Double) {
        lblDryness.text = "Clothes dryness: ${Math.round(wearDryness)}"
        drynessBar.value = wearDryness.toInt()
    }

    override fun thirstChanged(thirst: Int) {
        lblThirst.text = "Thirst: $thirst%"
        thirstBar.value = thirst
    }

    override fun underwearChanged(underwear: Wear) {
        lblUndies.text = "Undies: ${underwear.color} ${underwear.name.toLowerCase()}"
    }

    override fun outerwearChanged(outerwear: Wear) {
        lblUndies.text = "Lower: $outerwear.color} ${outerwear.name.toLowerCase()}"
    }

    companion object {
        /**Maximal lines of a text.*/
        private val MAX_LINES = 9

        private const val ACTION_BUTTONS_HEIGHT = 35
        private const val ACTION_BUTTONS_WIDTH = 89
        private const val ACTION_BUTTONS_TOP_BORDER = 510
    }

    private fun addUtilButton(name: String, listener: () -> Unit, bounds: Rectangle, toolTipText: String? = null): JButton {
        val button = JButton(name)
        with(button) {
            addActionListener(ActionListener {
                listener()
            })
            setBounds(bounds)
            this.toolTipText = toolTipText
        }
        contentPane.add(button)
        return button
    }

    private fun createTahomaFont(size: Int) = Font("Tahoma", Font.PLAIN, size)

    val contentPane = JPanel()
    val textPanel = JPanel()
    val textLabel = JLabel()

    val btnNext: JButton

    val lblName = JLabel(core.character.name)
    val lblBladder = JLabel("Bladder: " + core.character.fullness + "%")
    val bladderBar = JProgressBar()
    val lblBelly = JLabel("Belly: " + core.character.belly + "%")
    val lblEmbarrassment = JLabel("Embarrassment: " + core.character.embarrassment)
    val lblIncontinence = JLabel("Incontinence: " + core.character.incontinence + "x")
    val lblSphPower = JLabel("Pee holding ability: " + core.character.sphincterPower + "%")
    val sphincterBar = JProgressBar()
    val lblMinutes = JLabel("Minutes: ${(core.world.time - SchoolDay.classBeginningTime).rawMinutes} of 90")
    val timeBar = JProgressBar()
    val lblThirst = JLabel("Thirst: " + core.character.thirst + "%")
    val thirstBar = JProgressBar()
    val lblDryness = JLabel("Clothes dryness: " + Math.round(core.character.dryness))
    val drynessBar = JProgressBar()
    val lblUndies = JLabel()
    val lblLower = JLabel()

    val lblChoice = JLabel()
    val listChoice = JList<Any>()
    val listScroller = JScrollPane(listChoice)

    private val tahomaFont18 = createTahomaFont(18)
    private val tahomaFont15 = createTahomaFont(15)
    private val tahomaFont12 = createTahomaFont(12)

    /**
     * An array that contains boolean values that define *dialogue lines*.
     * Dialogue lines, unlike normal lines, are *italic*.
     */
    var dialogueLines = BooleanArray(MAX_LINES)

    /**
     * Sets the in-core text.
     *
     * @param lines the in-core text to set
     */
    fun setText(vararg lines: String) {
        if (lines.size > MAX_LINES) {
            System.err.println("You can't have more than $MAX_LINES lines at a time!")
            return
        }
        if (lines.isEmpty()) {
            textLabel.text = ""
            return
        }

        var toSend = "<html><center>"

        for (i in lines.indices) {
            toSend += if (dialogueLines[i]) {
                "<i>\"" + lines[i] + "\"</i>"
            } else {
                lines[i]
            }
            toSend += "<br>"

        }
        toSend += "</center></html>"
        textLabel.text = toSend
        dialogueLines = BooleanArray(MAX_LINES)
    }

    fun setLinesAsDialogue(vararg lines: Int) {
        for (i in lines) {
            dialogueLines[i - 1] = true
        }
    }

    fun hideActionUI(): Int {
        val choice = listChoice.selectedIndex
        core.plot.actionList.clear()
        lblChoice.isVisible = false
        listScroller.isVisible = false
        return choice
    }

    fun showActionUI(actionGroupName: String) {
        lblChoice.isVisible = true
        lblChoice.text = actionGroupName
        listScroller.isVisible = true
    }

    init {
        isResizable = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setBounds(100, 100, 770, 594)

        contentPane.background = Color.LIGHT_GRAY
        contentPane.border = EmptyBorder(5, 5, 5, 5)
        setContentPane(contentPane)
        contentPane.layout = null

        textPanel.setBounds(10, 11, 740, 150)
        contentPane.add(textPanel)
        textPanel.layout = null

        textLabel.horizontalAlignment = SwingConstants.CENTER
        textLabel.setBounds(0, 0, 740, 150)
        textPanel.add(textLabel)

        btnNext = addUtilButton(
                "Next",
                { core.handleNextClicked() },
                Rectangle(470, ACTION_BUTTONS_TOP_BORDER, 285, 35)
        )

        addUtilButton(
                "Quit",
                { System.exit(0) },
                Rectangle(192, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        addUtilButton(
                "Save",
                { core.writeSaveFile() },
                Rectangle(284, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        addUtilButton(
                "Load",
                { core.openSaveFile() },
                Rectangle(376, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        addUtilButton(
                "Reset",
                { core.reset(false); dispose() },
                Rectangle(10, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT),
                "Start the core over with the same parameters."
        )

        addUtilButton("New core",
                { core.reset(true); dispose() },
                Rectangle(102, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT),
                "Start the core over with the another parameters."
        )

        lblName.font = tahomaFont18
        lblName.setBounds(20, 170, 200, 32)
        contentPane.add(lblName)

        lblBladder.font = tahomaFont15
        lblBladder.setBounds(20, 210, 200, 32)
        lblBladder.toolTipText = "<html>Normal core:" +
                "<br>100% = need to hold, regular leaks" +
                "<br>130% = peeing(core over)" +
                "<br>" +
                "<br>" +
                "Hardcore:" +
                "<br>80% = need to hold, regular leaks" +
                "<br>100% = peeing(core over)</html>"
        contentPane.add(lblBladder)

        lblEmbarrassment.font = tahomaFont15
        lblEmbarrassment.setBounds(20, 240, 200, 32)
        lblEmbarrassment.toolTipText = "Makes leaks more frequent"
        contentPane.add(lblEmbarrassment)

        lblBelly.font = tahomaFont15
        lblBelly.setBounds(20, 270, 200, 32)
        lblBelly.toolTipText = "<html>The water in your belly." +
                "<br>Any amount of water speeds the bladder filling up.</html>"
        contentPane.add(lblBelly)

        if (core.hardcore) {
            lblThirst.font = tahomaFont15
            lblThirst.setBounds(20, 480, 200, 32)
            lblThirst.toolTipText = "Character will automatically drink water at 30% of thirst."
            contentPane.add(lblThirst)
        }

        if (core.hardcore) {
            thirstBar.setBounds(16, 482, 455, 25)
            thirstBar.maximum = Character.MAXIMAL_THIRST.toInt()
            thirstBar.value = core.character.thirst.toInt()
            thirstBar.toolTipText = "Character will automatically drink water at 30% of thirst."
            contentPane.add(thirstBar)
        }

        lblIncontinence.font = tahomaFont15
        lblIncontinence.setBounds(20, 300, 200, 32)
        lblIncontinence.toolTipText = "Makes your bladder weaker"
        contentPane.add(lblIncontinence)

        lblMinutes.font = tahomaFont15
        lblMinutes.setBounds(20, 330, 200, 32)
        lblMinutes.isVisible = false
        contentPane.add(lblMinutes)

        lblSphPower.font = tahomaFont15
        lblSphPower.setBounds(20, 360, 200, 32)
        lblSphPower.isVisible = false
        lblSphPower.toolTipText = "<html>Ability to hold pee." +
                "<br>Drains faster on higher bladder fullness." +
                "<br>Leaking when 0%." +
                "<br>Refill it by holding crotch and rubbing thighs.</html>"
        contentPane.add(lblSphPower)

        lblDryness.font = tahomaFont15
        lblDryness.setBounds(20, 390, 200, 32)
        lblDryness.isVisible = false
        lblDryness.toolTipText = "<html>Estimating dryness to absorb leaked pee." +
                "<br>Refills by itself with the time.</html>"
        contentPane.add(lblDryness)

        lblUndies.font = tahomaFont15
        lblUndies.setBounds(20, 420, 400, 32)
        contentPane.add(lblUndies)

        lblLower.font = tahomaFont15
        lblLower.setBounds(20, 450, 400, 32)
        contentPane.add(lblLower)

        lblChoice.font = tahomaFont12
        lblChoice.setBounds(480, 162, 280, 32)
        lblChoice.isVisible = false
        contentPane.add(lblChoice)

        listChoice.selectionMode = ListSelectionModel.SINGLE_SELECTION
        listChoice.layoutOrientation = JList.VERTICAL

        listScroller.setBounds(472, 194, 280, 318)
        listScroller.isVisible = false
        contentPane.add(listScroller)

        bladderBar.setBounds(16, 212, 455, 25)
        bladderBar.maximum = 130
        bladderBar.value = core.character.fullness.toInt()
        bladderBar.toolTipText = "<html>Normal core:" +
                "<br>100% = need to hold, regular leaks" +
                "<br>130% = peeing(core over)" +
                "<br>" +
                "<br>Hardcore:" +
                "<br>80% = need to hold, regular leaks" +
                "<br>100% = peeing(core over)</html>"
        contentPane.add(bladderBar)

        sphincterBar.setBounds(16, 362, 455, 25)
        sphincterBar.maximum = core.character.maxSphincterPower
        sphincterBar.value = core.character.sphincterPower
        sphincterBar.isVisible = false
        sphincterBar.toolTipText = "<html>Ability to hold pee." +
                "<br>Drains faster on higher bladder fullness." +
                "<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thighs.</html>"
        contentPane.add(sphincterBar)

        drynessBar.setBounds(16, 392, 455, 25)
        drynessBar.value = core.character.maximalDryness.toInt()
        drynessBar.minimum = Character.MINIMAL_DRYNESS
        drynessBar.maximum = core.character.maximalDryness.toInt()
        drynessBar.isVisible = false
        drynessBar.toolTipText = "<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>"
        contentPane.add(drynessBar)

        timeBar.setBounds(16, 332, 455, 25)
        timeBar.maximum = 90
        timeBar.value = (core.world.time - SchoolDay.classBeginningTime).rawMinutes
        timeBar.isVisible = false
        contentPane.add(timeBar)

        //Coloring the name label according to the gender
        if (core.character.gender == Gender.FEMALE) {
            lblName.foreground = Color.MAGENTA
        } else {
            lblName.foreground = Color.CYAN
        }

        //Assigning the blank name if player didn't selected it
        if (core.character.name.isEmpty()) {
            if (core.character.gender == Gender.FEMALE) {
                core.character.name = "Mrs. Nobody"
            } else {
                core.character.name = "Mr. Nobody"
            }
        }
    }

    override fun hideBladderAndTime() {
        lblMinutes.isVisible = false
        lblSphPower.isVisible = false
        lblDryness.isVisible = false
        sphincterBar.isVisible = false
        drynessBar.isVisible = false
        timeBar.isVisible = false
    }

    override fun showBladderAndTime() {
        lblMinutes.isVisible = true
        lblSphPower.isVisible = true
        lblDryness.isVisible = true
        sphincterBar.isVisible = true
        drynessBar.isVisible = true
        timeBar.isVisible = true
    }

    override fun hardcoreModeToggled(on: Boolean) {
        if (on)
            lblName.text = core.character.name + " [Hardcore]"
        else
            lblName.text = core.character.name
    }

    fun finishSetup() {
        lblUndies.text = "Undies: " + core.character.undies.color + " " + core.character.undies.name.toLowerCase()
        lblLower.text = "Lower: " + core.character.lower.color + " " + core.character.lower.name.toLowerCase()
    }

    override fun bladderFullnessChanged(fullness: Double) {
        lblBladder.text = "Bladder: $fullness%"
        bladderBar.value = fullness.toInt()
    }
}