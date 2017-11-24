package longHourAndAHalf

import longHourAndAHalf.ui.WearFileChooser
import longHourAndAHalf.ui.setupFramePre
import java.io.FileInputStream
import java.io.ObjectInputStream
import javax.swing.JFileChooser
import javax.swing.JOptionPane

/**
 *
 */
object MaintenanceWearProcessor {
    fun openCustomWear(type: WearType): Wear? {
        fun abort(message: String) {
            JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)
            setupFramePre.main(arrayOf(""))
        }

        val fcWear = WearFileChooser()
        fcWear.dialogTitle = when (type) {
            WearType.UNDERWEAR -> "Open an underwear file"
            WearType.OUTERWEAR -> "Open an outerwear file"
            WearType.BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE wear type isn't supported")
        }

        var openedWear: Wear? = null
        if (fcWear.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            val file = fcWear.selectedFile
            try {
                val fin = FileInputStream(file)
                val ois = ObjectInputStream(fin)
                openedWear = ois.readObject() as Wear
                if (openedWear.type == type) {
                    abort("Wrong wear type.")
                    return null
                }
            } catch (e: Exception) {
                abort("File error.")
                return null
            }
        }

        return openedWear
    }

    /**
     * Converts custom and random [MaintenanceWear] objects to [Wear] objects that can be used in game.
     *
     * @see Wardrobe
     */
    fun setupWear(wear: Wear, type: WearType): Wear? {
        val processedWear = when (wear.name) {
            "Custom" -> MaintenanceWearProcessor.openCustomWear(type)
            "Random" -> Wear.getRandom(type)
            else -> wear
        }

        processedWear?.let {
            if (processedWear.name == "No underwear" || processedWear.name == "No outerwear")
                processedWear.insert = if (CoreHolder.core.character.gender == Gender.FEMALE)
                    "crotch"
                else
                    "penis"
        }

        return processedWear
    }
}