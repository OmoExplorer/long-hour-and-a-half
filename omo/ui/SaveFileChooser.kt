package omo.ui

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class SaveFileChooser : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half Save", "lhhsav")
    }
}