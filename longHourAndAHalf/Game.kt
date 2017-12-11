package longHourAndAHalf

import longHourAndAHalf.ui.SaveFileChooser
import longHourAndAHalf.ui.UI

@Suppress("KDocMissingDocumentation")
/**
 * Running game.
 *
 * Consists of [Core] and [longHourAndAHalf.ui.UI] instances: [core] and [ui].
 */
class Game(val core: Core, val ui: UI) {
    /** Saves this game to a file. */
    fun save() = SaveFileManager.writeFile(SaveFileChooser(), core, "lhhsav")

    companion object {
        /** Loads a game from a file. */
        fun load() {
            val core = SaveFileManager.openFile<Core>(SaveFileChooser()) ?: return
//            val ui = GameMetadata.UI_CLASS.createInstance()
            //Workaround
            val ui = GameMetadata.createUI()
            longHourAndAHalf.game = Game(core, ui)
        }
    }
}