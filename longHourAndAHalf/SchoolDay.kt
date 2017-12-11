package longHourAndAHalf

import java.io.Serializable

/**
 * Data about a school day. Holds random number (from 1 to 3) of random lessons and classmates data.
 */
class SchoolDay : Serializable {
    /**
     * Day's only lesson. Will be replaced with a list of lessons in 2.0.
     */
    val lesson = Lesson()

    /**
     * State of boy NPC that takes place in different scenes on hardcore difficulty.
     */
    val surpriseBoy = SurpriseBoy()

    /**
     * Number of times character got caught holding pee from classmates.
     */
    var timesCaught = 0

    /**
     * Amount of embarrassment raising every time character caught holding pee.
     */
    var classmatesAwareness = 0

    companion object {
        /**
         * [Time] when the game begins.
         */
        val gameBeginningTime = Time(8, 52)
    }
}