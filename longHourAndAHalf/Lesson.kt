package longHourAndAHalf

import java.io.Serializable

/**
 * Contains a lesson data. It's individual for each lesson of a [school day][SchoolDay].
 */
class Lesson(@Transient var core: Core) : Serializable {
    /**
     * Times teacher denied character to go out.
     */
    var timesPeeDenied = 0

    /**
     * Whether character has to stay additional 30 minutes after class.
     */
    var stay = false

    /**
     * @return `true` if the lesson should finish, `false` otherwise.
     */
    fun shouldFinish() = core.world.time >= classEndingTime

    fun finish() {
        core.ui.forcedTextChange("You hear the bell finally ring.")
        core.plot.nextStage = GameStage.CLASS_OVER
    }

    //TODO: Companion object will be removed
    companion object {
        /**
         * [Time] when the lesson begins.
         */
        val classBeginningTime = Time(9, 0)
        val classDuration = Time(1, 30)
        val classEndingTime = classBeginningTime + classDuration
    }
}