package longHourAndAHalf.ui

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * File chooser for opening and saving games.
 */
class SaveFileChooser : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Save", "lhhsav")
    }
}