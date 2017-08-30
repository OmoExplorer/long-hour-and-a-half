package omo.ui

import omo.ALongHourAndAHalf
import omo.Gender
import java.awt.Color
import java.awt.Font
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.border.EmptyBorder

class GameFrame(game: ALongHourAndAHalf) : JFrame() {
    //Game frame variables declaration
    val contentPane = JPanel()
    val textPanel = JPanel()

    val btnNext = JButton("Next")
    val btnReset = JButton("Reset")
    val btnQuit = JButton("Quit")
    val btnSave = JButton("Save")
    val btnLoad = JButton("Load")

    val textLabel = JLabel()
    val lblBelly = JLabel("Belly: ${Math.round(game.belly)} %")
    val lblBladder = JLabel("Bladder: ${Math.round(game.bladder)} %")
    val lblChoice = JLabel()
    val lblEmbarassment = JLabel("Embarrassment: ${game.embarassment}")
    val lblIncon = JLabel("Incontinence: ${game.incon}x")
    val lblMinutes = JLabel("Time: ${game.time}")
    val lblName = JLabel(game.characterName)
    val btnNewGame = JButton("New game")
    val lblUndies = JLabel("Undies: ${game.undies!!.color} ${game.undies!!.name.toLowerCase()}")
    val lblLower = JLabel("Lower: ${game.lower!!.color} ${game.lower!!.name.toLowerCase()}")
    val lblSphPower = JLabel("Sphincter power: ${game.sphincterPower}")
    val lblDryness = JLabel("Clothes dryness: ${game.dryness}")
    val lblThirst = JLabel("Thirst: ${Math.round(game.thirst)}%")
    val listChoice = JList<Any>()

    val listScroller = JScrollPane()

    val bladderBar = JProgressBar()
    val sphincterBar = JProgressBar()
    val drynessBar = JProgressBar()
    val timeBar = JProgressBar()
    val thirstBar = JProgressBar()

    init {
        title = "A Long Hour and a Half"
        isResizable = true
        setBounds(100, 100, 770, 594)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        contentPane.background = Color.LIGHT_GRAY
        contentPane.border = EmptyBorder(5, 5, 5, 5)
        contentPane.layout = null
        setContentPane(contentPane)

        //Text panel setup
        textPanel.setBounds(10, 11, 740, 150)
        textPanel.layout = null
        contentPane.add(textPanel)

        textLabel.setBounds(0, 0, 740, 150)
        textLabel.horizontalAlignment = SwingConstants.CENTER
        textPanel.add(textLabel)

        //"Next" button setup
        btnNext.addActionListener(ActionListener {
            game.handleNextClicked()
        })
        btnNext.setBounds(470, ALongHourAndAHalf.ACTION_BUTTONS_TOP_BORDER, 285, 35)
        contentPane.add(btnNext)

        //"Quit" button setup
        btnQuit.addActionListener(ActionListener {
            System.exit(0)
        })
        btnQuit.setBounds(
                192,
                ALongHourAndAHalf.ACTION_BUTTONS_TOP_BORDER,
                ALongHourAndAHalf.ACTION_BUTTONS_WIDTH,
                ALongHourAndAHalf.ACTION_BUTTONS_HEIGHT
        )
        contentPane.add(btnQuit)

        //"Save" button setup
        btnSave.addActionListener(ActionListener {
            game.save()
        })
        btnSave.setBounds(
                284,
                ALongHourAndAHalf.ACTION_BUTTONS_TOP_BORDER,
                ALongHourAndAHalf.ACTION_BUTTONS_WIDTH,
                ALongHourAndAHalf.ACTION_BUTTONS_HEIGHT
        )
        contentPane.add(btnSave)

        //"Load" button setup
        btnLoad.addActionListener(ActionListener {
            game.load()
        })
        btnLoad.setBounds(
                376,
                ALongHourAndAHalf.ACTION_BUTTONS_TOP_BORDER,
                ALongHourAndAHalf.ACTION_BUTTONS_WIDTH,
                ALongHourAndAHalf.ACTION_BUTTONS_HEIGHT
        )
        contentPane.add(btnLoad)

        //"Reset" button setup
        btnReset.addActionListener(ActionListener {
            ALongHourAndAHalf.reset(false)
            dispose()
        })
        btnReset.setBounds(
                10,
                ALongHourAndAHalf.ACTION_BUTTONS_TOP_BORDER,
                ALongHourAndAHalf.ACTION_BUTTONS_WIDTH,
                ALongHourAndAHalf.ACTION_BUTTONS_HEIGHT
        )
        btnReset.toolTipText = "Start the game over with the same parameters."
        contentPane.add(btnReset)

        //"New game" button setup
        btnNewGame.addActionListener(ActionListener {
            ALongHourAndAHalf.reset(true)
            dispose()
        })
        btnNewGame.setBounds(
                102,
                ALongHourAndAHalf.ACTION_BUTTONS_TOP_BORDER,
                ALongHourAndAHalf.ACTION_BUTTONS_WIDTH,
                ALongHourAndAHalf.ACTION_BUTTONS_HEIGHT
        )
        btnNewGame.toolTipText = "Start the game over with the another parameters."
        contentPane.add(btnNewGame)

        //Name label setup
        lblName.setBounds(20, 170, 200, 32)
        lblName.font = Font("Tahoma", Font.PLAIN, 18)
        contentPane.add(lblName)

        //Bladder label setup
        lblBladder.setBounds(20, 210, 200, 32)
        lblBladder.toolTipText = """<html>Normal game:
            |<br>100% = need to hold, regular leaks
            |<br>130% = peeing(game over)
            |<br>
            |<br>Hardcore:
            |<br>80% = need to hold, regular leaks
            |<br>100% = peeing(game over)</html>""".trimMargin()
        lblBladder.font = Font("Tahoma", Font.PLAIN, 15)
        contentPane.add(lblBladder)

        //Embarrassment label setup
        lblEmbarassment.setBounds(20, 240, 200, 32)
        lblEmbarassment.toolTipText = "Makes leaks more frequent"
        lblEmbarassment.font = Font("Tahoma", Font.PLAIN, 15)
        contentPane.add(lblEmbarassment)

        //Belly label setup
        lblBelly.setBounds(20, 270, 200, 32)
        lblBelly.toolTipText = """<html>The water in your belly.
            |<br>Any amount of water speeds the bladder filling up.</html>""".trimMargin()
        lblBelly.font = Font("Tahoma", Font.PLAIN, 15)
        contentPane.add(lblBelly)

        //Thirst label setup
        if (game.hardcore) {
            lblThirst.setBounds(20, 480, 200, 32)
            lblThirst.toolTipText = "Character will automatically drink water at 30% of thirst."
            lblThirst.font = Font("Tahoma", Font.PLAIN, 15)
            contentPane.add(lblThirst)

            //Thirst bar setup
            thirstBar.setBounds(16, 482, 455, 25)
            thirstBar.maximum = game.MAXIMAL_THIRST
            thirstBar.value = game.thirst.toInt()
            thirstBar.toolTipText = "Character will automatically drink water at 30% of thirst."
            contentPane.add(thirstBar)
        }

        //Incontinence label setup
        lblIncon.setBounds(20, 300, 200, 32)
        lblIncon.toolTipText = "Makes your bladder weaker"
        lblIncon.font = Font("Tahoma", Font.PLAIN, 15)
        contentPane.add(lblIncon)

        //Time label setup
        lblMinutes.setBounds(20, 330, 200, 32)
        lblMinutes.font = Font("Tahoma", Font.PLAIN, 15)
        lblMinutes.isVisible = false
        contentPane.add(lblMinutes)

        //Sphincter power label setup
        lblSphPower.setBounds(20, 360, 200, 32)
        lblSphPower.toolTipText = """<html>Ability to hold pee.<br>Drains faster on higher bladder fulnesses.
            |<br>Leaking when 0%.
            |<br>Refill it by holding crotch and rubbing thighs.</html>""".trimMargin()
        lblSphPower.font = Font("Tahoma", Font.PLAIN, 15)
        lblSphPower.isVisible = false
        contentPane.add(lblSphPower)

        //Clothing dryness label setup
        lblDryness.setBounds(20, 390, 200, 32)
        lblDryness.toolTipText = """<html>Estimating dryness to absorb leaked pee.
            |<br>Refills by itself with the time.</html>""".trimMargin()
        lblDryness.font = Font("Tahoma", Font.PLAIN, 15)
        lblDryness.isVisible = false
        contentPane.add(lblDryness)

        //Choice label ("What to do?") setup
        lblChoice.setBounds(480, 162, 280, 32)
        lblChoice.font = Font("Tahoma", Font.BOLD, 12)
        lblChoice.isVisible = false
        contentPane.add(lblChoice)

        //Action list setup
        listChoice.selectionMode = ListSelectionModel.SINGLE_SELECTION
        listChoice.layoutOrientation = JList.VERTICAL

        listScroller.setBounds(472, 194, 280, 318)
        listScroller.isVisible = false
        contentPane.add(listScroller)

        //Bladder bar setup
        bladderBar.setBounds(16, 212, 455, 25)
        bladderBar.maximum = 130
        bladderBar.value = game.bladder.toInt()
        bladderBar.toolTipText = """<html>Normal game:
            |<br>100% = need to hold, regular leaks
            |<br>130% = peeing(game over)
            |<br>
            |<br>Hardcore:
            |<br>80% = need to hold, regular leak
            |s<br>100% = peeing(game over)</html>""".trimMargin()
        contentPane.add(bladderBar)

        //Sphincter bar setup
        sphincterBar.setBounds(16, 362, 455, 25)
        sphincterBar.maximum = game.maxSphincterPower
        sphincterBar.value = game.sphincterPower
        sphincterBar.isVisible = false
        sphincterBar.toolTipText = """<html>Ability to hold pee.
            |<br>Drains faster on higher bladder fullness.
            |<br>Leaking when 0%.
            |<br>Refill it by holding crotch and rubbing thighs.</html>""".trimMargin()
        contentPane.add(sphincterBar)

        //Dryness bar setup
        drynessBar.setBounds(16, 392, 455, 25)
        drynessBar.value = game.dryness.toInt()
        drynessBar.minimum = ALongHourAndAHalf.MINIMAL_DRYNESS
        drynessBar.isVisible = false
        drynessBar.toolTipText =
                "<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>"
        contentPane.add(drynessBar)

        //Time bar setup
        timeBar.setBounds(16, 332, 455, 25)
        timeBar.maximum = 90
        timeBar.value = game.time
        timeBar.isVisible = false
        contentPane.add(timeBar)

        //Coloring the characterName label according to the gender
        if (game.gender == Gender.FEMALE) {
            lblName.foreground = Color.MAGENTA
        } else {
            lblName.foreground = Color.CYAN
        }

        //Assigning the blank characterName if player didn't selected the characterName
        if (game.characterName.isEmpty()) {
            if (game.gender == Gender.FEMALE) {
                this.name = "Mrs. Nobody"
            } else {
                this.name = "Mr. Nobody"
            }
        }
    }
}