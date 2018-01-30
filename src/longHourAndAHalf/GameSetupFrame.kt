package longHourAndAHalf

import longHourAndAHalf.Difficulty.*
import longHourAndAHalf.characterProfileEditor.CharacterProfileFileChooser
import javax.swing.*

/**
 * Frame for setting up the game parameters, such as used character or game difficulty.
 *
 * Frame is used for collecting following information:
 * - [Character] instance which will be used in the game;
 * - [Game difficulty level][Difficulty];
 * - [Wear] and its [color][WearColor] for the character.
 *
 * @author JavaBird
 */
class GameSetupFrame : JFrame("Game setup") {
    private var pickedCharacter: CharacterProfile? = null
        set(value) {
            field = value
            nameLabel.text = pickedCharacter?.name ?: "No character"
        }

    private val nameLabel = JLabel("No character")

    private val easyRadioButton = JRadioButton("Easy")
    private val mediumRadioButton = JRadioButton("Medium")
    private val hardRadioButton = JRadioButton("Hard")

    private val underwearTree = JTree(WearModelTreeModel(Wardrobe.underwears))
    private val outerwearTree = JTree(WearModelTreeModel(Wardrobe.outerwears))

    private val underwearColorPicker = ColorPicker()
    private val outerwearColorPicker = ColorPicker()

    init {
        contentPane.layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        add(nameLabel)

        add(JButton("Pick...").apply {
            addActionListener {
                pickedCharacter = CharacterProfileFileChooser().askForObject(this) ?: return@addActionListener
            }
        })

        add(JLabel("Difficulty"))
        add(easyRadioButton)
        add(mediumRadioButton)
        add(hardRadioButton)

        add(JButton("Start").apply {
            addActionListener {
                val character = pickedCharacter
                if (character == null) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Pick a character.",
                            "Can't start the game",
                            JOptionPane.ERROR_MESSAGE
                    )
                    return@addActionListener
                }
                val difficulty = when {
                    easyRadioButton.isSelected -> EASY
                    mediumRadioButton.isSelected -> MEDIUM
                    hardRadioButton.isSelected -> HARD
                    else -> return@addActionListener
                }

                startGame(
                        character,
                        difficulty,
                        underwearTree.selectionPath.lastPathComponent as UnderwearModel,
                        outerwearTree.selectionPath.lastPathComponent as OuterwearModel,
                        underwearColorPicker.selectedColor,
                        outerwearColorPicker.selectedColor
                )
            }
        })

        display()
    }
}