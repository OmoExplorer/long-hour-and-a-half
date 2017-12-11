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

        core.plot.advanceToNextStage()
        core.scorer.countOutInitialScore()
//        ui.setup(character.name,)
        ui.setup()
        core.handleNextClicked()
//        if (core.hardcore) ui.hardcoreModeToggled(true)
        (ui as StandardGameUI).init()  //TODO: Workaround
        ui.underwearChanged(character.undies)
        ui.outerwearChanged(character.lower)
        ui.frame.isVisible = true     //Displaying the frame
    }
}