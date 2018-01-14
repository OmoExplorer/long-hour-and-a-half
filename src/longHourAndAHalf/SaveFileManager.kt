package longHourAndAHalf

import java.io.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane

object SaveFileManager {
    fun writeFile(fileChooser: JFileChooser, obj: Any, extension: String) {
        if (fileChooser.showSaveDialog(ui.frame) != JFileChooser.APPROVE_OPTION) return
        val saveFile = File(fileChooser.selectedFile.absolutePath + ".$extension")

        try {
            val fileOutputStream = FileOutputStream(saveFile)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)

            objectOutputStream.writeObject(obj)
        } catch (e: IOException) {
            e.printStackTrace()
            JOptionPane.showMessageDialog(ui.frame, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    fun <T> openFile(fileChooser: JFileChooser): T? {
        if (fileChooser.showOpenDialog(ui.frame) != JFileChooser.APPROVE_OPTION) return null
        val saveFile = fileChooser.selectedFile!!

        try {
            val fin = FileInputStream(saveFile)
            val ois = ObjectInputStream(fin)
            @Suppress("UNCHECKED_CAST")
            return ois.readObject() as T
        } catch (e: Exception) {
            e.printStackTrace()
            JOptionPane.showMessageDialog(ui.frame, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
        }

        return null
    }
}