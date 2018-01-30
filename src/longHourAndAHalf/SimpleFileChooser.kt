package longHourAndAHalf

import java.awt.Component
import java.io.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * Wrapper for [JFileChooser] which provides easy object (de)serialization.
 * @param T the type of objects which this file chooser saves and loads.
 */
@Suppress("UNCHECKED_CAST")
open class SimpleFileChooser<T : Serializable>(
        private val fileDescription: String,
        fileExtensionLetter: String
) : JFileChooser() {
    init {
        fileFilter = FileNameExtensionFilter("A Long Hour and a Half $fileDescription", "lhh$fileExtensionLetter")
    }

    /** Shows a file opening dialog to user and returns an object from a selected file. */
    fun askForObject(parent: Component? = null): T? {
        do {
            val resultCode = showOpenDialog(parent)
            if (resultCode != APPROVE_OPTION) return null

            val objectInputStream = ObjectInputStream(FileInputStream(selectedFile))
            val obj = objectInputStream.readObject() as? T
            if (obj == null) {
                showError()
                continue
            } else
                return obj
        } while (true)
    }

    /** Shows a file saving dialog to user and writes the specified object to a selected file. */
    fun saveObject(obj: T, parent: Component? = null) {
        val resultCode = showSaveDialog(parent)
        if (resultCode != APPROVE_OPTION) return

        val objectOutputStream = ObjectOutputStream(FileOutputStream(selectedFile))
        objectOutputStream.writeObject(obj)
    }

    private fun showError(parent: Component? = null) {
        JOptionPane.showMessageDialog(
                parent,
                "Invalid ${fileDescription.toLowerCase()}",
                "Error",
                JOptionPane.ERROR_MESSAGE
        )
    }
}