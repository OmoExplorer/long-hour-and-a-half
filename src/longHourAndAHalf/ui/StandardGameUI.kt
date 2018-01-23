package longHourAndAHalf.ui

import longHourAndAHalf.*
import longHourAndAHalf.Action
import java.awt.Color
import java.awt.Font
import java.awt.Rectangle
import javax.swing.*
import javax.swing.border.EmptyBorder
import kotlin.system.exitProcess

/**
 * Default gameplay user interface.
 *
 * @author JavaBird, thiswillnotcast, AnnaMay
 */
class StandardGameUI : JFrame("A Long Hour and a Half"), UI {
    override var actionsPresent = false

    override fun setup() {
        setLabelTexts()
        addButtons()
        if (core.hardcore) enableHardcoreUI()
        setBarsValues()
        setNameLabelColor()
    }

    private fun setNameLabelColor() {
        lblName.foreground = when (core.character.gender) {
            Gender.FEMALE -> Color.MAGENTA
            Gender.MALE -> Color.CYAN
        }
    }

    private fun setBarsValues() {
        with(core.character) {
            bladderBar.value = bladder.fullness.toInt()

            sphincterBar.maximum = bladder.maxSphincterPower
            sphincterBar.value = bladder.sphincterPower

            drynessBar.value = maximalDryness.toInt()
            drynessBar.maximum = maximalDryness.toInt()
        }

        timeBar.value = (core.world.time - Lesson.classBeginningTime).rawMinutes
    }

    private fun enableHardcoreUI() {
        lblHardcore.isVisible = true

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

    private fun addButtons() {
        addButton(
                "Save",
                game::save,
                Rectangle(284, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        addButton(
                "Load",
                Game.Companion::load,
                Rectangle(376, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        btnNext = addButton(
                "Next",
                {
                    if (!core.character.fatalLeakOccurred && actionsPresent) handleSelectedAction()
                    core.plot.advanceToNextStage()
                },
                Rectangle(470, ACTION_BUTTONS_TOP_BORDER, 285, 35)
        )

        addButton(
                "Reset",
                {
                    core.reset(false)
                    dispose()
                },
                Rectangle(10, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT),
                "Start the game over with the same parameters."
        )

        addButton("New game",
                {
                    core.reset(true)
                    dispose()
                },
                Rectangle(102, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT),
                "Start the game over with the another parameters."
        )
    }

    private fun handleSelectedAction() {
        val action = listChoice.selectedValue as Action
        when (action) {
            is CallbackAction -> action.callback()
            is PlotRotateAction -> core.plot.nextStageID = action.nextStageID
        }
    }

    private fun setLabelTexts() {
        lblName.text = core.character.name
        lblBladder.text = "Bladder: ${core.character.bladder.fullness}%"
        lblBelly.text = "Water in tummy: ${core.character.bladder.waterInTummy}%"
        lblEmbarrassment.text = "Embarrassment: " + core.character.embarrassment
        lblIncontinence.text = "Incontinence: ${core.character.bladder.sphincter.incontinence}x"
        lblSphPower.text = "Pee holding ability: ${core.character.bladder.sphincter.power}%"
        lblMinutes.text = "Minutes: ${(core.world.time - Lesson.classBeginningTime).rawMinutes} of 90"
        lblThirst.text = "Thirst: ${core.character.thirst}%"
        lblDryness.text = "Clothes dryness: " + round(core.character.dryness)
    }

    /** Reference to the UI frame. */
    override val frame = this

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

    override fun bellyWaterLevelChanged(bellyWaterLevel: Int) {
        lblBelly.text = "Belly: ${round(bellyWaterLevel)}%"
    }

    override fun incontinenceMultiplierChanged(incontinenceMultiplier: Double) {
        lblIncontinence.text = "Incontinence: ${incontinenceMultiplier}x"
    }

    override fun timeChanged(time: Time) {
        lblMinutes.text = "Minutes: " +
                "${(core.world.time - Lesson.classBeginningTime).rawMinutes.coerceAtLeast(0)}" +
                " of ${Lesson.classDuration.rawMinutes}"
        timeBar.value = (time - Lesson.classBeginningTime).rawMinutes
    }

    override fun sphincterStrengthChanged(sphincterStrength: Int) {
        lblSphPower.text = "Pee holding ability: $sphincterStrength%"
        sphincterBar.value = sphincterStrength
    }

    override fun wearDrynessChanged(wearDryness: Double) {
        lblDryness.text = "Clothes dryness: " + round(wearDryness)
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

    override fun leakOccurred() {}

    override fun slideChanged() {
        lblLeak.isVisible = core.character.bladder.sphincterPower < 0
    }

    override fun bladderFullnessChanged(fullness: Double) {
        lblBladder.text = "Bladder: $fullness ml"
        bladderBar.value = fullness.toInt()
        lblBladder.foreground = if (fullness > core.character.bladder.leakingFullnessLevel)
            java.awt.Color.RED
        else
            java.awt.Color.BLACK
    }

    override fun forcedTextChange(text: Text) {
        setText(text)
    }

    @Suppress("KDocMissingDocumentation")
    private companion object {
        const val ACTION_BUTTONS_HEIGHT = 35
        const val ACTION_BUTTONS_WIDTH = 89
        const val ACTION_BUTTONS_TOP_BORDER = 510
    }

    private inline fun JButton.prepare(crossinline listener: () -> Unit, bounds: Rectangle, toolTipText: String?) {
        addActionListener({ listener() })
        this.bounds = bounds
        this.toolTipText = toolTipText
    }

    private fun addButton(
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
    private val textLabel = JLabel()
    private lateinit var btnNext: JButton
    private val lblName = JLabel()
    private val lblHardcore = JLabel("HARDCORE")
    private val lblBladder = JLabel()
    private val lblLeak = JLabel("LEAK")
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

        addButton(
                "Quit",
                { exitProcess(0) },
                Rectangle(192, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        )

        lblName.font = tahomaFont18
        lblName.setBounds(20, 170, 200, 32)
        contentPane.add(lblName)

        lblHardcore.font = tahomaFont18
        lblHardcore.setBounds(220, 170, 100, 32)
        lblHardcore.isVisible = false
        contentPane.add(lblHardcore)

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

        lblLeak.font = tahomaFont15
        lblLeak.foreground = Color.RED
        lblLeak.setBounds(20, 230, 50, 32)
        lblLeak.toolTipText = "You're leaking!!!"
        lblLeak.isVisible = false
        contentPane.add(lblLeak)

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
        contentPane.add(lblMinutes)

        lblSphPower.font = tahomaFont15
        lblSphPower.setBounds(20, 360, 200, 32)
        lblSphPower.toolTipText = "<html>Ability to hold pee." +
                "<br>Drains faster on higher bladder fullness." +
                "<br>Leaking when 0%." +
                "<br>Refill it by holding crotch and rubbing thighs.</html>"
        contentPane.add(lblSphPower)

        lblDryness.font = tahomaFont15
        lblDryness.setBounds(20, 390, 200, 32)
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
        sphincterBar.toolTipText = "<html>Ability to hold pee." +
                "<br>Drains faster on higher bladder fullness." +
                "<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thighs.</html>"
        contentPane.add(sphincterBar)

        drynessBar.setBounds(16, 392, 455, 25)
        drynessBar.minimum = Character.MINIMAL_DRYNESS
        drynessBar.toolTipText = "<html>Estimating dryness to absorb leaked pee.<br>" +
                "Refills by itself with the time.</html>"
        contentPane.add(drynessBar)

        timeBar.setBounds(16, 332, 455, 25)
        timeBar.maximum = 90
        contentPane.add(timeBar)
    }

    fun finishSetup() {
        lblUndies.text = "Undies: " + core.character.undies.color + " " + core.character.undies.name.toLowerCase()
        lblLower.text = "Lower: " + core.character.lower.color + " " + core.character.lower.name.toLowerCase()
    }
}