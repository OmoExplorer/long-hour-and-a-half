package longHourAndAHalf

/**
 * Game settings.
 * Used to create the game in [GameInitializer].
 */
data class GameParameters(
        val name: String,
        val gender: Gender,
        val bladderFullnessAtStart: Int,
        val incontinence: Double,
        val underwearModelToAssign: AbstractWearModel,
        val outerwearModelToAssign: AbstractWearModel,
        val underwearColor: WearColor,
        val outerwearColor: WearColor,
        val hardcore: Boolean
)