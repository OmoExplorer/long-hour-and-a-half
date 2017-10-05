package longHourAndAHalf

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class WearFileChooser : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Custom wear", "lhhwear")
    }
}