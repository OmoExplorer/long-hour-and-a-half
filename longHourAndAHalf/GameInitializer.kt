package longHourAndAHalf

import longHourAndAHalf.ui.StandardGameUI

/**
 * Creates and sets up the game.
 */
object GameInitializer {
    /**
     * Start the game creation process. Called in [Launcher.runGame].
     *
     * @param parameters the game parameters.
     */
    fun createGame(parameters: GameParameters) {
        val bladder = Bladder(parameters.bladderFullnessAtStart.toDouble(), parameters.incontinence)
        val character = Character(
                parameters.name,
                parameters.gender,
                bladder,
                MaintenanceWearProcessor.processWear(parameters.underwearToAssign, parameters.underwearColor),
                MaintenanceWearProcessor.processWear(parameters.outerwearToAssign, parameters.outerwearColor)
        )

        val core = Core(character, parameters.hardcore, parameters)
        val ui = GameMetadata.createUI()
        game = Game(core, ui)

        game.core.plot.advanceToNextStage()
        game.core.scorer.countOutInitialScore()
        game.ui.showBladderAndTime()
        game.core.handleNextClicked()
        if (game.core.hardcore) game.ui.hardcoreModeToggled(true)
        (game.ui as StandardGameUI).init()  //TODO: Workaround
        game.ui.underwearChanged(character.undies)
        game.ui.outerwearChanged(character.lower)
        game.ui.frame.isVisible = true     //Displaying the frame
    }
}