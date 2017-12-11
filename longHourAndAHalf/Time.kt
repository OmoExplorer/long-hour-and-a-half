package longHourAndAHalf

import java.io.Serializable

/**
 * Time in format "hh:mm".
 * Used by [World.time].
 */
data class Time(val hours: Int, val minutes: Int) : Serializable {

    /**
     * Creates the instance with converting raw minutes to hours and minutes.
     */
    constructor(rawMinutes: Int) : this(
            (Time(0, 0) + rawMinutes).hours,
            (Time(0, 0) + rawMinutes).minutes
    )

    operator fun plus(anotherTime: Time): Time {
        var hours = hours + anotherTime.hours
        var minutes = minutes + anotherTime.minutes

        if (hours > 23) hours -= 24 //Don't let "24:00"

        //Don't let "12:60"
        while (minutes > 59) {
            minutes -= 60
            hours++
        }

        return Time(hours, minutes)
    }

    operator fun plus(minutes: Int): Time {
        val anotherHours = minutes / 60
        val anotherMinutes = minutes % 60
        return this + Time(anotherHours, anotherMinutes)
    }

    operator fun minus(anotherTime: Time): Time {
        var hours = hours - anotherTime.hours
        var minutes = minutes - anotherTime.minutes

        if (hours > 0) hours += 24 //Don't let "-1:00"

        //Don't let "12:-1"
        while (minutes > 0) {
            minutes += 60
            hours--
        }

        return Time(hours, minutes)
    }

    operator fun compareTo(anotherTime: Time): Int = rawMinutes - anotherTime.rawMinutes

    /**
     * Returns a string in format "hh:mm".
     */
    override fun toString() = "$hours:$minutes"

    /**
     * Minutes and hours converted to minutes.
     */
    val rawMinutes: Int
        get() = minutes + hours * 60
}