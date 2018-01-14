package longHourAndAHalf.ui

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/** File chooser for opening and saving custom underwear or outerwear files. */
class WearFileChooser : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Custom wear", "lhhuwear", "lhhowear")
    }
}

/** File chooser for opening and saving custom underwear files. */
class UnderwearFileChooser : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Custom underwear", "lhhuwear")
    }
}

/** File chooser for opening and saving custom outerwear files. */
class OuterwearFileChooser : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Custom outerwear", "lhhowear")
    }
}