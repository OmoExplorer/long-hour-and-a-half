package longHourAndAHalf

/** Creates and sets up the game. */
object GameInitializer {

    /** Start the game creation process with the given [parameters]. Called in [Launcher.runGame]. */
    fun createGame(parameters: GameParameters) {
        val bladder = Bladder(parameters.bladderFullnessAtStart.toDouble(), parameters.incontinence)
        val character = Character(
                parameters.name,
                parameters.gender,
                bladder,
                setupWear(parameters.underwearModelToAssign, parameters.underwearColor) as Underwear,
                setupWear(parameters.outerwearModelToAssign, parameters.outerwearColor) as Outerwear
        )

        val core = Core(character, parameters.hardcore, parameters)
        val ui = GameMetadata.createUI()
        game = Game(core, ui)

        core.plot.advanceToNextStage()
        core.scorer.countOutInitialScore()
//        ui.setup(character.name,)
        ui.setup()
//        if (core.hardcore) ui.hardcoreModeToggled(true)
        ui.underwearChanged(character.undies)
        ui.outerwearChanged(character.lower)
        ui.frame.isVisible = true     //Displaying the frame
    }

    /** Returns the random bladder fullness. */
    fun generateRandomBladderFullness() = random.nextInt(Bladder.RANDOM_FULLNESS_CAP)
}