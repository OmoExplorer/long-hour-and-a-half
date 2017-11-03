package longHourAndAHalf.ui

import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * File chooser for opening and saving games.
 */
class SaveFileChooser(selectedFile: File? = null) : JFileChooser() {
    init {
        selectedFile?.let { this.selectedFile = selectedFile }
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Save", "lhhsav")
    }
}