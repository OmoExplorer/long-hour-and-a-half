package longHourAndAHalf.wearModelEditor

import longHourAndAHalf.OuterwearModel
import longHourAndAHalf.SimpleFileChooser
import longHourAndAHalf.UnderwearModel

/**
 * Chooser for underwear model files (extension: `.lhhu`).
 * @author JavaBird
 * @see javax.swing.JFileChooser
 */
class UnderwearModelFileChooser : SimpleFileChooser<UnderwearModel>("underwear", "u")

/**
 * Chooser for underwear model files (extension: `.lhho`).
 * @author JavaBird
 * @see javax.swing.JFileChooser
 */
class OuterwearModelFileChooser : SimpleFileChooser<OuterwearModel>("outerwear", "o")
