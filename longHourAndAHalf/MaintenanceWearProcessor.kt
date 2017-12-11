package longHourAndAHalf

import longHourAndAHalf.ui.WearFileChooser
import longHourAndAHalf.ui.setupFramePre
import java.io.FileInputStream
import java.io.ObjectInputStream
import javax.swing.JFileChooser
import javax.swing.JOptionPane

object MaintenanceWearProcessor {
    //TODO: Refactor
    fun openCustomWear(requiredType: WearType): Wear? {
        fun abort(message: String) {
            JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)
            setupFramePre.main(arrayOf())
        }

        val fcWear = WearFileChooser()
        fcWear.dialogTitle = when (requiredType) {
            WearType.UNDERWEAR -> "Open an underwear file"
            WearType.OUTERWEAR -> "Open an outerwear file"
            WearType.BOTH_SUITABLE -> "Open a wear"
        }

        val openedWear: Wear
        if (fcWear.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            val file = fcWear.selectedFile
            try {
                val fin = FileInputStream(file)
                val ois = ObjectInputStream(fin)
                openedWear = ois.readObject() as Wear
                if (openedWear.type == requiredType) {
                    abort("Wrong wear requiredType.")
                    return null
                }
            } catch (e: Exception) {
                abort("File error.")
                return null
            }
        } else {
            setupFramePre()
            return null
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
                processedWear.insert = if (core.character.gender == Gender.FEMALE)
                    "crotch"
                else
                    "penis"
        }

        return processedWear
    }

    fun processWear(abstractWear: AbstractWear, color: WearColor): Wear {
        val wear = processAbstractWear(abstractWear)
        processColor(wear, color)
        return wear
    }

    private fun processAbstractWear(abstractWear: AbstractWear): Wear {
        return when (abstractWear) {
            is MaintenanceWear -> abstractWear.instead()
            is Wear -> abstractWear
            else -> throw IllegalArgumentException("wear is not valid")
        }
    }

    private fun processColor(wear: Wear, color: WearColor) {
        wear.color = when {
            !wear.colorable -> WearColor.NONE
            color == WearColor.RANDOM -> WearColor.random()
            else -> color
        }
    }
}