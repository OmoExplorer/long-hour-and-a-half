package longHourAndAHalf

/** Reference to [Game.core] of [game]. */
val core
    get() = game.core

/** Reference to [Game.ui] of [game]. */
val ui
    get() = game.ui

/**
 * [Game] instance.
 *
 * Being set by [GameInitializer.createGame].
 */
lateinit var game: Game