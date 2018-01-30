package longHourAndAHalf.characterProfileEditor

import longHourAndAHalf.CharacterProfile
import longHourAndAHalf.SimpleFileChooser

/**
 * Chooser for character profile files (extension: `.lhhc`).
 * @author JavaBird
 * @see javax.swing.JFileChooser
 */
class CharacterProfileFileChooser : SimpleFileChooser<CharacterProfile>("Character profile", "c")