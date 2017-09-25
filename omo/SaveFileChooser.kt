package omo

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class SaveFileChooser : JFileChooser() {
    val fileFilter = FileNameExtensionFilter("A Long Hour and a Half Save", "lhhsav")
}