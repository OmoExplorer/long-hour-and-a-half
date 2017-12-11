package longHourAndAHalf.ui

import longHourAndAHalf.*
import longHourAndAHalf.Action
import longHourAndAHalf.Game.Companion.load
import longHourAndAHalf.WearCombinationType.*
import java.awt.Color
import java.awt.Font
import java.awt.Rectangle
import javax.swing.*
import javax.swing.border.EmptyBorder

/**
 * Default gameplay user interface.
 *
 * @author JavaBird, thiswillnotcast, AnnaMay
 */
class StandardGameUI : JFrame("A Long Hour and a Half"), UI {
    /** Reference to the UI frame. */
    override val frame = this

    override var actionMustBeSelected = false

    override fun actionsChanged(actionGroupName: String, actions: List<Action>) {
        listChoice.setListData(actions.toTypedArray())
        listChoice.selectedIndex = 0
        showActionUI(actionGroupName)
    }

    override fun gameFinished() {
        btnNext.isVisible = false
    }

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
                (core.world.time - Lesson.classBeginningTime).rawMinutes.coerceAtLeast(0)
        )} of ${Lesson.classDuration.rawMinutes}"
        timeBar.value = (time - Lesson.classBeginningTime).rawMinutes
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
        lblLower.text = "Lower: ${outerwear.color} ${outerwear.name.toLowerCase()}"
    }

    companion object {
        /** Maximal lines of a text in [textLabel]. */
        private val MAX_LINES = 9

        private const val ACTION_BUTTONS_HEIGHT = 35
        private const val ACTION_BUTTONS_WIDTH = 89
        private const val ACTION_BUTTONS_TOP_BORDER = 510
    }


    private fun JButton.prepare(listener: () -> Unit, bounds: Rectangle, toolTipText: String?) {
        addActionListener({ listener() })
        this.bounds = bounds
        this.toolTipText = toolTipText
    }

    private fun addUtilButton(
            name: String,
            listener: () -> Unit,
            bounds: Rectangle,
            toolTipText: String? = null
    ): JButton {
        val button = JButton(name)

        button.prepare(listener, bounds, toolTipText)

        contentPane.add(button)
        return button
    }

    private fun createTahomaFont(size: Int) = Font("Tahoma", Font.PLAIN, size)

    private val contentPane = JPanel()
    private val textPanel = JPanel()
    val textLabel = JLabel()

    lateinit var btnNext: JButton

    private val lblName = JLabel()
    private val lblBladder = JLabel()
    private val bladderBar = JProgressBar()
    private val lblBelly = JLabel()
    private val lblEmbarrassment = JLabel()
    private val lblIncontinence = JLabel()
    private val lblSphPower = JLabel()
    private val sphincterBar = JProgressBar()
    private val lblMinutes = JLabel()
    private val timeBar = JProgressBar()
    private val lblThirst = JLabel()
    private val thirstBar = JProgressBar()
    private val lblDryness = JLabel()
    private val drynessBar = JProgressBar()
    private val lblUndies = JLabel()
    private val lblLower = JLabel()

    private val lblChoice = JLabel()
    private val listChoice = JList<Any>()
    private val listScroller = JScrollPane(listChoice)

    private val tahomaFont18 = createTahomaFont(18)
    private val tahomaFont15 = createTahomaFont(15)
    private val tahomaFont12 = createTahomaFont(12)

    fun init() {
        lblName.text = core.character.name
        lblBelly.text = "Bladder: ${core.character.bladder.fullness}%"
        lblBelly.text = "Belly: " + core.character.belly + "%"
        lblEmbarrassment.text = "Embarrassment: " + core.character.embarrassment
        lblIncontinence.text = "Incontinence: " + core.character.bladder.incontinence + "x"
        lblSphPower.text = "Pee holding ability: " + core.character.bladder.sphincterPower + "%"
        lblMinutes.text = "Minutes: ${(core.world.time - Lesson.classBeginningTime).rawMinutes} of 90"
        lblThirst.text = "Thirst: " + core.character.thirst + "%"
        lblDryness.text = "Clothes dryness: " + Math.round(core.character.dryness)

        addUtilButton(
                "Save",
                game::save,
                Rectangle(284, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        addUtilButton(
                "Load",
                Game.Companion::load,
                Rectangle(376, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        btnNext = addUtilButton(
                "Next",
                lambda@ {
                    /*core.handleNextClicked()*/
                    if (actionMustBeSelected) {
                        val action = listChoice.selectedValue as Action

                        if (!core.character.fatalLeakOccured) {
                            when (action) {
                                is CallbackAction -> action.callback()
                                is PlotRotateAction -> core.plot.nextStageID = action.nextStageID
                            }
                        }
                    }

                    core.handleNextClicked()
                },
                Rectangle(470, ACTION_BUTTONS_TOP_BORDER, 285, 35)
        )

        addUtilButton(
                "Reset",
                { core.reset(false); dispose() },
                Rectangle(10, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT),
                "Start the game over with the same parameters."
        )

        addUtilButton("New game",
                { core.reset(true); dispose() },
                Rectangle(102, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT),
                "Start the game over with the another parameters."
        )

        if (core.hardcore) {
            lblThirst.font = tahomaFont15
            lblThirst.setBounds(20, 480, 200, 32)
            lblThirst.toolTipText = "Character will automatically drink water at 30% of thirst."
            contentPane.add(lblThirst)

            thirstBar.setBounds(16, 482, 455, 25)
            thirstBar.maximum = Character.MAXIMAL_THIRST
            thirstBar.value = core.character.thirst
            thirstBar.toolTipText = "Character will automatically drink water at 30% of thirst."
            contentPane.add(thirstBar)
        }

        bladderBar.value = core.character.bladder.fullness.toInt()

        sphincterBar.maximum = core.character.bladder.maxSphincterPower
        sphincterBar.value = core.character.bladder.sphincterPower

        drynessBar.value = core.character.maximalDryness.toInt()
        drynessBar.maximum = core.character.maximalDryness.toInt()

        timeBar.value = (core.world.time - Lesson.classBeginningTime).rawMinutes

        //Coloring the name label according to the gender
        when (core.character.gender) {
            Gender.FEMALE -> {
                lblName.foreground = Color.MAGENTA
                if (core.character.name.isEmpty())
                    core.character.name = "Mrs. Nobody"
            }

            Gender.MALE -> {
                lblName.foreground = Color.CYAN
                if (core.character.name.isEmpty())
                    core.character.name = "Mr. Nobody"
            }
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

    /**
     * Sets the in-game text.
     *
     * @param text the in-game text to set.
     */
    private fun setText(text: Text) {
        var htmlText = "<html><center>"

        text.lines.forEach {
            htmlText += if (it.italic)
                "<i>\"${it.text}\"</i>"
            else
                it.text

            htmlText += "<br>"
        }

        htmlText += "</center></html>"
        textLabel.text = htmlText
    }

    override fun hideActionUI() {
        lblChoice.isVisible = false
        listScroller.isVisible = false
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

        addUtilButton(
                "Quit",
                { System.exit(0) },
                Rectangle(192, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        lblName.font = tahomaFont18
        lblName.setBounds(20, 170, 200, 32)
        contentPane.add(lblName)

        lblBladder.font = tahomaFont15
        lblBladder.setBounds(20, 210, 200, 32)
        lblBladder.toolTipText = "<html>Normal game:" +
                "<br>100% = need to hold, regular leaks" +
                "<br>130% = peeing(game over)" +
                "<br>" +
                "<br>" +
                "Hardcore:" +
                "<br>80% = need to hold, regular leaks" +
                "<br>100% = peeing(game over)</html>"
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
        bladderBar.toolTipText = "<html>Normal game:" +
                "<br>100% = need to hold, regular leaks" +
                "<br>130% = peeing(game over)" +
                "<br>" +
                "<br>Hardcore:" +
                "<br>80% = need to hold, regular leaks" +
                "<br>100% = peeing(game over)</html>"
        contentPane.add(bladderBar)

        sphincterBar.setBounds(16, 362, 455, 25)
        sphincterBar.isVisible = false
        sphincterBar.toolTipText = "<html>Ability to hold pee." +
                "<br>Drains faster on higher bladder fullness." +
                "<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thighs.</html>"
        contentPane.add(sphincterBar)

        drynessBar.setBounds(16, 392, 455, 25)
        drynessBar.minimum = Character.MINIMAL_DRYNESS
        drynessBar.isVisible = false
        drynessBar.toolTipText = "<html>Estimating dryness to absorb leaked pee.<br>" +
                "Refills by itself with the time.</html>"
        contentPane.add(drynessBar)

        timeBar.setBounds(16, 332, 455, 25)
        timeBar.maximum = 90
        timeBar.isVisible = false
        contentPane.add(timeBar)
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

    override fun forcedTextChange(text: Text) {
        setText(text)
    }

    override fun warnAboutLeaking(vararg warnText: String) {
        with(core.character) {
            forcedTextChange(
                    when (core.character.wearCombinationType) {
                        FULLY_CLOTHED, OUTERWEAR_ONLY -> Text(
                                "You see the wet spot expand on your ${lower.insert}!",
                                "You're about to pee! You must stop it!"
                        )

                        UNDERWEAR_ONLY -> Text("You see the wet spot expand on your ${undies.insert}!",
                                "You're about to pee! You must stop it!")

                        NAKED -> Text("You feel the leak running down your thighs...",
                                "You're about to pee! You must stop it!")
                    }
            )
        }
    }
}