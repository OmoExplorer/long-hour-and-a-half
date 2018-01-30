package longHourAndAHalf

import longHourAndAHalf.characterProfileEditor.CharacterProfileEditor
import longHourAndAHalf.wearModelEditor.WearModelEditor
import java.awt.Font
import javax.swing.*
import kotlin.system.exitProcess

class MainMenuFrame : JFrame(GameMetadata.NAME) {
    init {
        contentPane.layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        add(JLabel(GameMetadata.NAME).apply { font = Font("Tahoma", Font.PLAIN, 20) })
        add(JButton("New game").apply { addActionListener { GameSetupFrame() } })
        add(JButton("Load game").apply { addActionListener { loadGame() } })
        add(JButton("Character editor").apply { addActionListener { CharacterProfileEditor() } })
        add(JButton("Wear model editor").apply { addActionListener { WearModelEditor() } })
        add(JButton("About the game").apply {
            addActionListener {
                JOptionPane.showMessageDialog(
                        this@MainMenuFrame,
                        GameMetadata.DESCRIPTION,
                        "About ${GameMetadata.NAME}",
                        JOptionPane.PLAIN_MESSAGE
                )
            }
        })
        add(JButton("Quit").apply { addActionListener { exitProcess(0) } })

        display()
    }
}