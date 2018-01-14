package longHourAndAHalf

import longHourAndAHalf.ui.UnderwearFileChooser
import java.io.FileInputStream
import java.io.ObjectInputStream
import javax.swing.JFileChooser

fun setupWear(model: AbstractWearModel, color: WearColor): Wear {
    val normalColor = when (color) {
        WearColor.RANDOM -> WearColor.random()
        else -> color
    }

    return when (model) {
        is MaintenanceUnderwearModel -> Underwear(model.instead(), normalColor)
        is MaintenanceOuterwearModel -> Outerwear(model.instead(), normalColor)
        is UnderwearModel -> Underwear(model, normalColor)
        is OuterwearModel -> Outerwear(model, normalColor)
        else -> {
            class UnknownWearModelSubtypeException : Exception("")
            throw UnknownWearModelSubtypeException()
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : WearModel> openCustomWearModel(): T? {
    val underwearChooser = UnderwearFileChooser()
    val result = underwearChooser.showOpenDialog(null)
    if (result != JFileChooser.APPROVE_OPTION) return null

    val underwearFile = underwearChooser.selectedFile
    val objectInputStream = ObjectInputStream(FileInputStream(underwearFile))
    return objectInputStream.readObject() as T
}