package longHourAndAHalf

import java.io.Serializable

/**
 * Contains a lesson data. It's individual for each lesson of a [school day][SchoolDay].
 */
class Lesson : Serializable {
    /**
     * Times teacher denied character to go out.
     */
    var timesPeeDenied = 0

    /**
     * Whether character has to stay additional 30 minutes after class.
     */
    var stay = false
}