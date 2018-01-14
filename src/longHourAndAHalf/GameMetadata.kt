package longHourAndAHalf

import longHourAndAHalf.ui.StandardGameUI
import longHourAndAHalf.ui.UI

/**
 * Game metadata, such as:
 * * Title
 * * Description
 * * Version
 * * Author
 *
 *  Also contains the function which should return new UI instance.
 */
object GameMetadata {
    /** Game name. */
    const val NAME = "A Long Hour and a Half"
    /** Game description. */
    const val DESCRIPTION = "A game where one must make it through class with a rather full bladder. " +
            "It have choices that can hurt and help your ability to hold."
    /** Game version. */
    const val VERSION = "2.0-alpha"
    /** Game author. */
    const val AUTHOR = "Rosalie Elodie, JavaBird, Anna May, notwillnotcast"

    /**
     * Creates the [game UI][longHourAndAHalf.ui.UI].
     * Edit this function if you want to set your own UI.
     */
    fun createUI(): UI = StandardGameUI()
}