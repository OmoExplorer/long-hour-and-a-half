package longHourAndAHalf

import longHourAndAHalf.ui.SaveFileChooser
import java.io.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane

object SaveFileManager {
    fun writeSaveFile() {
        val fcGame = SaveFileChooser(File(CoreHolder.core.character.name))
        val ui = CoreHolder.core.ui
        val saveFile = File(fcGame.selectedFile.absolutePath + ".lhhsav")

        if (fcGame.showSaveDialog(ui) != JFileChooser.APPROVE_OPTION) return

        try {
            val save = Save(CoreHolder.core)
            val fileOutputStream = FileOutputStream(saveFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)

            objectOutputStream.writeObject(save)
        } catch (e: IOException) {
            e.printStackTrace()
            JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    fun openSaveFile() {
        val fileChooser = SaveFileChooser()
        val ui = CoreHolder.core.ui
        val saveFile = fileChooser.selectedFile!!

        if (fileChooser.showOpenDialog(ui) != JFileChooser.APPROVE_OPTION) return

        try {
            val fin = FileInputStream(saveFile)
            val ois = ObjectInputStream(fin)
            val save = ois.readObject() as Save
            Core(save)
            ui.dispose()
        } catch (e: Exception) {
            e.printStackTrace()
            JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }
}