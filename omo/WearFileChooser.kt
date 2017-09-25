package omo

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class WearFileChooser : JFileChooser() {
    val fileFilter = FileNameExtensionFilter("A Long Hour and a Half Wear", "lhhwear")
}