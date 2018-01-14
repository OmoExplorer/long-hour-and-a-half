package longHourAndAHalf.ui

import longHourAndAHalf.*
import java.awt.Color
import java.awt.Dimension
import java.awt.EventQueue
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

@Suppress("ClassName")
/**
 * Frame for setting up the game. This is the first frame shown to a user.
 *
 * Code for this class was emitted by the frame generator, therefore it looks ugly. Don't try to understand it.
 * This class will be soon replaced by [MainMenuFrame].
 *
 * @author NetBeans Frame Generator, JavaBird
 */
class setupFramePre : JFrame() {
    var undiesColor = WearColor.RANDOM
    var lowerColor = WearColor.RANDOM
    // Variables declaration - do not modify//GEN-BEGIN:variables
    val basSlider = JSlider()
    val basSliderRadio = JRadioButton()
    private val bladAtStartLabel = JLabel()
    private val buttonGroup1 = ButtonGroup()
    private val buttonGroup2 = ButtonGroup()
    private val buttonGroup3 = ButtonGroup()
    private val difficultyPanel = JPanel()
    val femaleRadio = JRadioButton()
    val hardDiffRadio = JRadioButton()
    private val incontLabel = JLabel()
    val incontSlider = JSlider()
    private val jScrollPane1 = JScrollPane()
    private val jScrollPane2 = JScrollPane()
    private val jScrollPane3 = JScrollPane()
    private val jTextPane1 = JTextPane()
    private val loadGame = JButton()
    private val lowerColor_black1 = JPanel()
    private val lowerColor_blue1 = JPanel()
    private val lowerColor_darkBlue1 = JPanel()
    private val lowerColor_gray1 = JPanel()
    private val lowerColor_green1 = JPanel()
    private val lowerColor_orange1 = JPanel()
    private val lowerColor_pink1 = JPanel()
    private val lowerColor_purple1 = JPanel()
    private val lowerColor_random1 = JPanel()
    private val lowerColor_red1 = JPanel()
    private val lowerColor_yellow1 = JPanel()
    private val maleRadio = JRadioButton()
    val nameField = JTextField()
    private val nameLabel = JLabel()
    private val normalDiffRadio = JRadioButton()
    private val outerwearLabel = JLabel()
    val outerwearTree = JTree()
    private val randomBasSlider = JRadioButton()
    private val start = JButton()
    private val underwearLabel = JLabel()
    val underwearTree = JTree()
    private val undiesColor_black = JPanel()
    private val undiesColor_blue = JPanel()
    private val undiesColor_darkBlue = JPanel()
    private val undiesColor_gray = JPanel()
    private val undiesColor_green = JPanel()
    private val undiesColor_orange = JPanel()
    private val undiesColor_pink = JPanel()
    private val undiesColor_purple = JPanel()
    private val undiesColor_random = JPanel()
    private val undiesColor_red = JPanel()
    private val undiesColor_yellow = JPanel()
    private val wearEditorButton = JButton()

    init {
        initComponents()
    }

    private fun initComponents() {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false

        nameField.text = "Mrs. Nobody"
        nameField.toolTipText = "Your character's name"

        buttonGroup1.add(maleRadio)
        maleRadio.text = "Male"
        maleRadio.addActionListener { maleRadioActionPerformed() }

        buttonGroup1.add(femaleRadio)
        femaleRadio.isSelected = true
        femaleRadio.text = "Female"
        femaleRadio.addActionListener { femaleRadioActionPerformed() }

        difficultyPanel.border = BorderFactory.createTitledBorder("Difficulty")
        difficultyPanel.toolTipText = ""

        buttonGroup2.add(normalDiffRadio)
        normalDiffRadio.isSelected = true
        normalDiffRadio.text = "Normal"

        buttonGroup2.add(hardDiffRadio)
        hardDiffRadio.text = "Hardcore"
        hardDiffRadio.toolTipText = "<html>\n" +
                "Teacher never lets you pee<br>\n" +
                "Harder to hold pee<br>\n" +
                "You may get caught holding pee<br>\n" +
                "<i>Thirst</i> parameter\n</html>"

        val difficultyPanelLayout = GroupLayout(difficultyPanel)
        difficultyPanel.layout = difficultyPanelLayout
        difficultyPanelLayout.setHorizontalGroup(difficultyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(difficultyPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(normalDiffRadio, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                        .addGap(100, 100, 100)
                        .addComponent(hardDiffRadio)
                        .addContainerGap())
        )
        difficultyPanelLayout.setVerticalGroup(difficultyPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(difficultyPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(normalDiffRadio)
                        .addComponent(hardDiffRadio))
        )

        incontLabel.text = "Incontinence"
        incontLabel.toolTipText = "How incontinent are you?"

        incontSlider.minimum = 5
        incontSlider.toolTipText = "How incontinent are you?"
        incontSlider.value = 10

        bladAtStartLabel.text = "Bladder at start"
        bladAtStartLabel.toolTipText = "How much you have to pee on wake?"

        basSlider.toolTipText = "Defined value"
        basSlider.value = 0

        underwearLabel.text = "Underwear:"
        underwearLabel.toolTipText = "Select your underwear"

        outerwearLabel.text = "Outerwear:"
        outerwearLabel.toolTipText = "Select your outerwear"

        start.font = Font("sansserif", 1, 12) // NOI18N
        start.text = "Start"
        start.toolTipText = "Start new game"
        start.addActionListener {
            Launcher(this).runGame()
        }

        nameLabel.font = Font("sansserif", 1, 12) // NOI18N
        nameLabel.text = "Name"

        buttonGroup3.add(basSliderRadio)
        basSliderRadio.toolTipText = "Defined value"

        buttonGroup3.add(randomBasSlider)
        randomBasSlider.isSelected = true
        randomBasSlider.text = "Random"
        randomBasSlider.toolTipText = "Random value between 0% and 100%"


        var treeNode1 = DefaultMutableTreeNode("root")
        var treeNode2 = DefaultMutableTreeNode(UnderwearModel.getByName("Random underwear"))
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode("Female")
        var treeNode3 = DefaultMutableTreeNode("Panties")
        var treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Strings"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Tanga panties"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Regular panties"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Briefs"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode("Swimwear")
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("String bikini"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Regular bikini"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Swimsuit"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode("Diapers")
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Light diaper"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Normal diaper"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Heavy diaper"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode("Menstrual pads")
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Light pad"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Normal pad"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(UnderwearModel.getByName("Big pad"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode("Male")
        treeNode3 = DefaultMutableTreeNode(UnderwearModel.getByName("Pants"))
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode(UnderwearModel.getByName("Shorts-alike pants"))
        treeNode2.add(treeNode3)
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode("Special")
        treeNode3 = DefaultMutableTreeNode(UnderwearModel.getByName("Anti-gravity pants"))
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode(UnderwearModel.getByName("Super-absorbing diaper"))
        treeNode2.add(treeNode3)
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode(UnderwearModel.getByName("No underwear"))
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode(UnderwearModel.getByName("Custom underwear"))
        treeNode1.add(treeNode2)
        underwearTree.model = DefaultTreeModel(treeNode1)
        underwearTree.isRootVisible = false
        jScrollPane1.setViewportView(underwearTree)


        treeNode1 = DefaultMutableTreeNode("root")
        treeNode2 = DefaultMutableTreeNode(OuterwearModel.getByName("Random outerwear"))
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode("Female")
        treeNode3 = DefaultMutableTreeNode("Jeans")
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Long jeans"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Knee-length jeans"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Short jeans"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Very short jeans"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode("Trousers")
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Long trousers"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Knee-length trousers"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Short trousers"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Very short trousers"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode("Skirts")
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Long skirt"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Knee-length skirt"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Short skirt"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Mini skirt"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Micro skirt"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Long skirt and tights"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Knee-length skirt and tights"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Short skirt and tights"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Mini skirt and tights"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Micro skirt and tights"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode(OuterwearModel.getByName("Leggings"))
        treeNode2.add(treeNode3)
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode("Male")
        treeNode3 = DefaultMutableTreeNode("Jeans")
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Short male jeans"))
        treeNode3.add(treeNode4)
        treeNode4 = DefaultMutableTreeNode(OuterwearModel.getByName("Normal male jeans"))
        treeNode3.add(treeNode4)
        treeNode2.add(treeNode3)
        treeNode3 = DefaultMutableTreeNode(OuterwearModel.getByName("Male trousers"))
        treeNode2.add(treeNode3)
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode(OuterwearModel.getByName("No outerwear"))
        treeNode1.add(treeNode2)
        treeNode2 = DefaultMutableTreeNode(OuterwearModel.getByName("Custom outerwear"))
        treeNode1.add(treeNode2)
        outerwearTree.model = DefaultTreeModel(treeNode1)
        outerwearTree.isRootVisible = false
        jScrollPane2.setViewportView(outerwearTree)

        jTextPane1.isEditable = false
        jTextPane1.font = Font("Tahoma", 0, 12) // NOI18N
        jTextPane1.text = "This program is thanks to the wonderful help of the following users:\n" +
                "Anna May, FromRUSForum, and notwillnotcast of Omo.org, " +
                "and two others (who choose to not be credited for differing reasons —\n" +
                "                                            helping a ton, " +
                "along with a few others helping with my minor mistakes!\n\n" +
                "If your (user)name is not here and you want it here, " +
                "just shoot me a message and I'll add it post-haste!"
        jScrollPane3.setViewportView(jTextPane1)

        undiesColor_black.background = Color(0, 0, 0)
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255))
        undiesColor_black.toolTipText = "Black"
        undiesColor_black.preferredSize = Dimension(20, 20)
        undiesColor_black.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_blackMouseClicked()
            }
        })

        val undiesColor_blackLayout = GroupLayout(undiesColor_black)
        undiesColor_black.layout = undiesColor_blackLayout
        undiesColor_blackLayout.setHorizontalGroup(undiesColor_blackLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_blackLayout.setVerticalGroup(undiesColor_blackLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_gray.background = Color(153, 153, 153)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_gray.toolTipText = "Gray"
        undiesColor_gray.preferredSize = Dimension(20, 20)
        undiesColor_gray.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_grayMouseClicked()
            }
        })

        val undiesColor_grayLayout = GroupLayout(undiesColor_gray)
        undiesColor_gray.layout = undiesColor_grayLayout
        undiesColor_grayLayout.setHorizontalGroup(undiesColor_grayLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_grayLayout.setVerticalGroup(undiesColor_grayLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_red.background = Color(255, 0, 0)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_red.toolTipText = "Red"
        undiesColor_red.preferredSize = Dimension(20, 20)
        undiesColor_red.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_redMouseClicked()
            }
        })

        val undiesColor_redLayout = GroupLayout(undiesColor_red)
        undiesColor_red.layout = undiesColor_redLayout
        undiesColor_redLayout.setHorizontalGroup(undiesColor_redLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_redLayout.setVerticalGroup(undiesColor_redLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_orange.background = Color(255, 153, 0)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_orange.toolTipText = "Orange"
        undiesColor_orange.preferredSize = Dimension(20, 20)
        undiesColor_orange.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_orangeMouseClicked()
            }
        })

        val undiesColor_orangeLayout = GroupLayout(undiesColor_orange)
        undiesColor_orange.layout = undiesColor_orangeLayout
        undiesColor_orangeLayout.setHorizontalGroup(undiesColor_orangeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_orangeLayout.setVerticalGroup(undiesColor_orangeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_yellow.background = Color(255, 255, 0)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_yellow.toolTipText = "Yellow"
        undiesColor_yellow.preferredSize = Dimension(20, 20)
        undiesColor_yellow.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_yellowMouseClicked()
            }
        })

        val undiesColor_yellowLayout = GroupLayout(undiesColor_yellow)
        undiesColor_yellow.layout = undiesColor_yellowLayout
        undiesColor_yellowLayout.setHorizontalGroup(undiesColor_yellowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_yellowLayout.setVerticalGroup(undiesColor_yellowLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_green.background = Color(0, 255, 0)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_green.toolTipText = "Green"
        undiesColor_green.preferredSize = Dimension(20, 20)
        undiesColor_green.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_greenMouseClicked()
            }
        })

        val undiesColor_greenLayout = GroupLayout(undiesColor_green)
        undiesColor_green.layout = undiesColor_greenLayout
        undiesColor_greenLayout.setHorizontalGroup(undiesColor_greenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_greenLayout.setVerticalGroup(undiesColor_greenLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_blue.background = Color(0, 255, 204)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_blue.toolTipText = "Blue"
        undiesColor_blue.preferredSize = Dimension(20, 20)
        undiesColor_blue.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_blueMouseClicked()
            }
        })

        val undiesColor_blueLayout = GroupLayout(undiesColor_blue)
        undiesColor_blue.layout = undiesColor_blueLayout
        undiesColor_blueLayout.setHorizontalGroup(undiesColor_blueLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_blueLayout.setVerticalGroup(undiesColor_blueLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_darkBlue.background = Color(0, 0, 204)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_darkBlue.toolTipText = "Dark blue"
        undiesColor_darkBlue.preferredSize = Dimension(20, 20)
        undiesColor_darkBlue.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_darkBlueMouseClicked()
            }
        })

        val undiesColor_darkBlueLayout = GroupLayout(undiesColor_darkBlue)
        undiesColor_darkBlue.layout = undiesColor_darkBlueLayout
        undiesColor_darkBlueLayout.setHorizontalGroup(undiesColor_darkBlueLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_darkBlueLayout.setVerticalGroup(undiesColor_darkBlueLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_purple.background = Color(153, 0, 153)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_purple.toolTipText = "Purple"
        undiesColor_purple.preferredSize = Dimension(20, 20)
        undiesColor_purple.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_purpleMouseClicked()
            }
        })

        val undiesColor_purpleLayout = GroupLayout(undiesColor_purple)
        undiesColor_purple.layout = undiesColor_purpleLayout
        undiesColor_purpleLayout.setHorizontalGroup(undiesColor_purpleLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_purpleLayout.setVerticalGroup(undiesColor_purpleLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_pink.background = Color(255, 51, 255)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        undiesColor_pink.toolTipText = "Pink"
        undiesColor_pink.preferredSize = Dimension(20, 20)
        undiesColor_pink.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_pinkMouseClicked()
            }
        })

        val undiesColor_pinkLayout = GroupLayout(undiesColor_pink)
        undiesColor_pink.layout = undiesColor_pinkLayout
        undiesColor_pinkLayout.setHorizontalGroup(undiesColor_pinkLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_pinkLayout.setVerticalGroup(undiesColor_pinkLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_pink1.background = Color(255, 51, 255)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_pink1.toolTipText = "Pink"
        lowerColor_pink1.preferredSize = Dimension(20, 20)
        lowerColor_pink1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_pink1MouseClicked()
            }
        })

        val lowerColor_pink1Layout = GroupLayout(lowerColor_pink1)
        lowerColor_pink1.layout = lowerColor_pink1Layout
        lowerColor_pink1Layout.setHorizontalGroup(lowerColor_pink1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_pink1Layout.setVerticalGroup(lowerColor_pink1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_black1.background = Color(0, 0, 0)
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255))
        lowerColor_black1.toolTipText = "Black"
        lowerColor_black1.preferredSize = Dimension(20, 20)
        lowerColor_black1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_black1MouseClicked()
            }
        })

        val lowerColor_black1Layout = GroupLayout(lowerColor_black1)
        lowerColor_black1.layout = lowerColor_black1Layout
        lowerColor_black1Layout.setHorizontalGroup(lowerColor_black1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_black1Layout.setVerticalGroup(lowerColor_black1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_gray1.background = Color(153, 153, 153)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_gray1.toolTipText = "Gray"
        lowerColor_gray1.preferredSize = Dimension(20, 20)
        lowerColor_gray1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_gray1MouseClicked()
            }
        })

        val lowerColor_gray1Layout = GroupLayout(lowerColor_gray1)
        lowerColor_gray1.layout = lowerColor_gray1Layout
        lowerColor_gray1Layout.setHorizontalGroup(lowerColor_gray1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_gray1Layout.setVerticalGroup(lowerColor_gray1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_red1.background = Color(255, 0, 0)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_red1.toolTipText = "Red"
        lowerColor_red1.preferredSize = Dimension(20, 20)
        lowerColor_red1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_red1MouseClicked()
            }
        })

        val lowerColor_red1Layout = GroupLayout(lowerColor_red1)
        lowerColor_red1.layout = lowerColor_red1Layout
        lowerColor_red1Layout.setHorizontalGroup(lowerColor_red1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_red1Layout.setVerticalGroup(lowerColor_red1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_orange1.background = Color(255, 153, 0)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_orange1.toolTipText = "Orange"
        lowerColor_orange1.preferredSize = Dimension(20, 20)
        lowerColor_orange1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_orange1MouseClicked()
            }
        })

        val lowerColor_orange1Layout = GroupLayout(lowerColor_orange1)
        lowerColor_orange1.layout = lowerColor_orange1Layout
        lowerColor_orange1Layout.setHorizontalGroup(lowerColor_orange1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_orange1Layout.setVerticalGroup(lowerColor_orange1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_yellow1.background = Color(255, 255, 0)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_yellow1.toolTipText = "Yellow"
        lowerColor_yellow1.preferredSize = Dimension(20, 20)
        lowerColor_yellow1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_yellow1MouseClicked()
            }
        })

        val lowerColor_yellow1Layout = GroupLayout(lowerColor_yellow1)
        lowerColor_yellow1.layout = lowerColor_yellow1Layout
        lowerColor_yellow1Layout.setHorizontalGroup(lowerColor_yellow1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_yellow1Layout.setVerticalGroup(lowerColor_yellow1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_green1.background = Color(0, 255, 0)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_green1.toolTipText = "Green"
        lowerColor_green1.preferredSize = Dimension(20, 20)
        lowerColor_green1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_green1MouseClicked()
            }
        })

        val lowerColor_green1Layout = GroupLayout(lowerColor_green1)
        lowerColor_green1.layout = lowerColor_green1Layout
        lowerColor_green1Layout.setHorizontalGroup(lowerColor_green1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_green1Layout.setVerticalGroup(lowerColor_green1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_blue1.background = Color(0, 255, 204)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_blue1.toolTipText = "Blue"
        lowerColor_blue1.preferredSize = Dimension(20, 20)
        lowerColor_blue1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_blue1MouseClicked()
            }
        })

        val lowerColor_blue1Layout = GroupLayout(lowerColor_blue1)
        lowerColor_blue1.layout = lowerColor_blue1Layout
        lowerColor_blue1Layout.setHorizontalGroup(lowerColor_blue1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_blue1Layout.setVerticalGroup(lowerColor_blue1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_darkBlue1.background = Color(0, 0, 204)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_darkBlue1.toolTipText = "Dark blue"
        lowerColor_darkBlue1.preferredSize = Dimension(20, 20)
        lowerColor_darkBlue1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_darkBlue1MouseClicked()
            }
        })

        val lowerColor_darkBlue1Layout = GroupLayout(lowerColor_darkBlue1)
        lowerColor_darkBlue1.layout = lowerColor_darkBlue1Layout
        lowerColor_darkBlue1Layout.setHorizontalGroup(lowerColor_darkBlue1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_darkBlue1Layout.setVerticalGroup(lowerColor_darkBlue1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_purple1.background = Color(153, 0, 153)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0))
        lowerColor_purple1.toolTipText = "Purple"
        lowerColor_purple1.preferredSize = Dimension(20, 20)
        lowerColor_purple1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_purple1MouseClicked()
            }
        })

        val lowerColor_purple1Layout = GroupLayout(lowerColor_purple1)
        lowerColor_purple1.layout = lowerColor_purple1Layout
        lowerColor_purple1Layout.setHorizontalGroup(lowerColor_purple1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_purple1Layout.setVerticalGroup(lowerColor_purple1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 18, java.lang.Short.MAX_VALUE.toInt())
        )

        undiesColor_random.background = Color(204, 204, 204)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)
        undiesColor_random.toolTipText = "Random"
        undiesColor_random.preferredSize = Dimension(20, 20)
        undiesColor_random.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                undiesColor_randomMouseClicked()
            }
        })

        val undiesColor_randomLayout = GroupLayout(undiesColor_random)
        undiesColor_random.layout = undiesColor_randomLayout
        undiesColor_randomLayout.setHorizontalGroup(undiesColor_randomLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 14, java.lang.Short.MAX_VALUE.toInt())
        )
        undiesColor_randomLayout.setVerticalGroup(undiesColor_randomLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 14, java.lang.Short.MAX_VALUE.toInt())
        )

        lowerColor_random1.background = Color(204, 204, 204)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)
        lowerColor_random1.toolTipText = "Random"
        lowerColor_random1.preferredSize = Dimension(20, 20)
        lowerColor_random1.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(evt: MouseEvent?) {
                lowerColor_random1MouseClicked()
            }
        })

        val lowerColor_random1Layout = GroupLayout(lowerColor_random1)
        lowerColor_random1.layout = lowerColor_random1Layout
        lowerColor_random1Layout.setHorizontalGroup(lowerColor_random1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 14, java.lang.Short.MAX_VALUE.toInt())
        )
        lowerColor_random1Layout.setVerticalGroup(lowerColor_random1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGap(0, 14, java.lang.Short.MAX_VALUE.toInt())
        )

        wearEditorButton.text = "Wear editor"
        wearEditorButton.toolTipText = "Create your own wear and use it in game."
        wearEditorButton.addActionListener { wearEditorButtonActionPerformed() }

        loadGame.text = "Load game..."
        loadGame.toolTipText = "Load a paused game"
        loadGame.addActionListener { loadGameActionPerformed() }

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 782, java.lang.Short.MAX_VALUE.toInt())
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(nameField)
                                                                .addComponent(difficultyPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                                                .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(nameLabel)
                                                                        .addGap(0, 0, java.lang.Short.MAX_VALUE.toInt())))
                                                        .addGap(5, 5, 5))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(underwearLabel)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(6, 6, 6)
                                                                        .addComponent(undiesColor_black, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_gray, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_red, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_orange, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_yellow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_green, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_blue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_darkBlue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_purple, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(undiesColor_pink, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(undiesColor_random, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(basSliderRadio)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(basSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(randomBasSlider))
                                                .addComponent(jScrollPane2)
                                                .addComponent(incontSlider, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(femaleRadio)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                                        .addComponent(maleRadio))
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addComponent(outerwearLabel)
                                                                .addComponent(incontLabel)
                                                                .addComponent(bladAtStartLabel)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(lowerColor_black1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_gray1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_red1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_orange1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_yellow1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_green1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_blue1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_darkBlue1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_purple1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(lowerColor_pink1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(lowerColor_random1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(0, 0, java.lang.Short.MAX_VALUE.toInt()))))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(loadGame, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(start, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(wearEditorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())))
                        .addContainerGap())
        )
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(nameLabel)
                                        .addGap(8, 8, 8)
                                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(difficultyPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(underwearLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
                                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(femaleRadio)
                                                .addComponent(maleRadio))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bladAtStartLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(basSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(basSliderRadio)
                                                .addComponent(randomBasSlider))
                                        .addGap(8, 8, 8)
                                        .addComponent(incontLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(incontSlider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(outerwearLabel)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, java.lang.Short.MAX_VALUE.toInt())
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(undiesColor_black, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_gray, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_red, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_orange, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_green, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_blue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_darkBlue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_purple, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_pink, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_yellow, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_black1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_gray1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_red1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_orange1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_green1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_blue1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_darkBlue1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_purple1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_pink1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_yellow1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(undiesColor_random, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lowerColor_random1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(start, GroupLayout.DEFAULT_SIZE, 117, java.lang.Short.MAX_VALUE.toInt())
                                .addComponent(wearEditorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                .addComponent(loadGame, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()))
                        .addContainerGap())
        )

        pack()
    }

    private fun maleRadioActionPerformed() {//GEN-FIRST:event_maleRadioActionPerformed
        if (nameField.text == "Mrs. Nobody")
            nameField.text = "Mr. Nobody"
    }//GEN-LAST:event_maleRadioActionPerformed

    private fun femaleRadioActionPerformed() {//GEN-FIRST:event_femaleRadioActionPerformed
        if (nameField.text == "Mr. Nobody")
            nameField.text = "Mrs. Nobody"
    }//GEN-LAST:event_femaleRadioActionPerformed

    private fun undiesColor_blackMouseClicked()//GEN-FIRST:event_undiesColor_blackMouseClicked
    {//GEN-HEADEREND:event_undiesColor_blackMouseClicked
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 3)

        //Unchecking other colors
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.BLACK
    }//GEN-LAST:event_undiesColor_blackMouseClicked

    private fun undiesColor_grayMouseClicked()//GEN-FIRST:event_undiesColor_grayMouseClicked
    {//GEN-HEADEREND:event_undiesColor_grayMouseClicked
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.GRAY
    }//GEN-LAST:event_undiesColor_grayMouseClicked

    private fun undiesColor_redMouseClicked()//GEN-FIRST:event_undiesColor_redMouseClicked
    {//GEN-HEADEREND:event_undiesColor_redMouseClicked
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.RED
    }//GEN-LAST:event_undiesColor_redMouseClicked

    private fun undiesColor_orangeMouseClicked()//GEN-FIRST:event_undiesColor_orangeMouseClicked
    {//GEN-HEADEREND:event_undiesColor_orangeMouseClicked
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.ORANGE
    }//GEN-LAST:event_undiesColor_orangeMouseClicked

    private fun undiesColor_yellowMouseClicked()//GEN-FIRST:event_undiesColor_yellowMouseClicked
    {//GEN-HEADEREND:event_undiesColor_yellowMouseClicked
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.YELLOW
    }//GEN-LAST:event_undiesColor_yellowMouseClicked

    private fun undiesColor_greenMouseClicked()//GEN-FIRST:event_undiesColor_greenMouseClicked
    {//GEN-HEADEREND:event_undiesColor_greenMouseClicked
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.GREEN
    }//GEN-LAST:event_undiesColor_greenMouseClicked

    private fun undiesColor_blueMouseClicked()//GEN-FIRST:event_undiesColor_blueMouseClicked
    {//GEN-HEADEREND:event_undiesColor_blueMouseClicked
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.BLUE
    }//GEN-LAST:event_undiesColor_blueMouseClicked

    private fun undiesColor_darkBlueMouseClicked()//GEN-FIRST:event_undiesColor_darkBlueMouseClicked
    {//GEN-HEADEREND:event_undiesColor_darkBlueMouseClicked
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.DARK_BLUE
    }//GEN-LAST:event_undiesColor_darkBlueMouseClicked

    private fun undiesColor_purpleMouseClicked()//GEN-FIRST:event_undiesColor_purpleMouseClicked
    {//GEN-HEADEREND:event_undiesColor_purpleMouseClicked
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.PURPLE
    }//GEN-LAST:event_undiesColor_purpleMouseClicked

    private fun undiesColor_pinkMouseClicked()//GEN-FIRST:event_undiesColor_pinkMouseClicked
    {//GEN-HEADEREND:event_undiesColor_pinkMouseClicked
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.PINK
    }//GEN-LAST:event_undiesColor_pinkMouseClicked

    private fun lowerColor_pink1MouseClicked()//GEN-FIRST:event_lowerColor_pink1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_pink1MouseClicked
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.PINK
    }//GEN-LAST:event_lowerColor_pink1MouseClicked

    private fun lowerColor_black1MouseClicked()//GEN-FIRST:event_lowerColor_black1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_black1MouseClicked
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 3)

        //Unchecking other colors
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.BLACK
    }//GEN-LAST:event_lowerColor_black1MouseClicked

    private fun lowerColor_gray1MouseClicked()//GEN-FIRST:event_lowerColor_gray1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_gray1MouseClicked
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.GRAY
    }//GEN-LAST:event_lowerColor_gray1MouseClicked

    private fun lowerColor_red1MouseClicked()//GEN-FIRST:event_lowerColor_red1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_red1MouseClicked
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.RED
    }//GEN-LAST:event_lowerColor_red1MouseClicked

    private fun lowerColor_orange1MouseClicked()//GEN-FIRST:event_lowerColor_orange1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_orange1MouseClicked
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.ORANGE
    }//GEN-LAST:event_lowerColor_orange1MouseClicked

    private fun lowerColor_yellow1MouseClicked()//GEN-FIRST:event_lowerColor_yellow1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_yellow1MouseClicked
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.YELLOW
    }//GEN-LAST:event_lowerColor_yellow1MouseClicked

    private fun lowerColor_green1MouseClicked()//GEN-FIRST:event_lowerColor_green1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_green1MouseClicked
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.GREEN
    }//GEN-LAST:event_lowerColor_green1MouseClicked

    private fun lowerColor_blue1MouseClicked()//GEN-FIRST:event_lowerColor_blue1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_blue1MouseClicked
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.BLUE
    }//GEN-LAST:event_lowerColor_blue1MouseClicked

    private fun lowerColor_darkBlue1MouseClicked()//GEN-FIRST:event_lowerColor_darkBlue1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_darkBlue1MouseClicked
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.DARK_BLUE
    }//GEN-LAST:event_lowerColor_darkBlue1MouseClicked

    private fun lowerColor_purple1MouseClicked()//GEN-FIRST:event_lowerColor_purple1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_purple1MouseClicked
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.PURPLE
    }//GEN-LAST:event_lowerColor_purple1MouseClicked

    private fun undiesColor_randomMouseClicked()//GEN-FIRST:event_undiesColor_randomMouseClicked
    {//GEN-HEADEREND:event_undiesColor_randomMouseClicked
        undiesColor_random.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        undiesColor_black.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        undiesColor_red.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_orange.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_yellow.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_green.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_blue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_darkBlue.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_gray.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_pink.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        undiesColor_purple.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        undiesColor = WearColor.RANDOM
    }//GEN-LAST:event_undiesColor_randomMouseClicked

    private fun lowerColor_random1MouseClicked()//GEN-FIRST:event_lowerColor_random1MouseClicked
    {//GEN-HEADEREND:event_lowerColor_random1MouseClicked
        lowerColor_random1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 3)

        //Unchecking other colors
        lowerColor_black1.border = BorderFactory.createLineBorder(Color(255, 255, 255), 1)
        lowerColor_red1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_orange1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_yellow1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_green1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_blue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_darkBlue1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_gray1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_pink1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)
        lowerColor_purple1.border = BorderFactory.createLineBorder(Color(0, 0, 0), 1)

        lowerColor = WearColor.RANDOM
    }//GEN-LAST:event_lowerColor_random1MouseClicked

    private fun wearEditorButtonActionPerformed()//GEN-FIRST:event_wearEditorButtonActionPerformed
    {//GEN-HEADEREND:event_wearEditorButtonActionPerformed
        WearEditor().isVisible = true
    }//GEN-LAST:event_wearEditorButtonActionPerformed

    private fun loadGameActionPerformed() = Game.load()

    companion object {
        private val serialVersionUID = 1L

        /**
         * @param args the command line arguments
         */
        @JvmStatic
        fun main(args: Array<String>) {
            for (info in UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus" == info.name) {
                    UIManager.setLookAndFeel(info.className)
                    break
                }
            }

            EventQueue.invokeLater { setupFramePre().isVisible = true }
        }
    }
}
