package longHourAndAHalf.ui

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * File chooser for opening and saving custom wear files.
 */
class WearFileChooser : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Custom wear", "lhhwear")
    }
}