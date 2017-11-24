/*
 * Contains basic utility functions.
 */
package longHourAndAHalf

import java.util.*

private val random = Random()

/**
 * @return random item from this array.
 */
fun <T> Array<T>.randomItem(): T {
    val randomIndex = random.nextInt(this.size)
    return this[randomIndex]
}

/**
 * @return random item from this list.
 */
fun <T> List<T>.randomItem(): T {
    val randomIndex = random.nextInt(this.size)
    return this[randomIndex]
}

/**
 * @return `true` with a specified chance, `false` otherwise.
 * @param probability chance to return `true` in percents.
 */
fun chance(probability: Int) = chance(probability.toDouble())

/**
 * @return `true` with a specified chance, `false` otherwise.
 * @param probability chance to return `true` in percents.
 */
fun chance(probability: Double) = random.nextInt(100) < probability

/**
 * Clamps the given value to be strictly between the min and max values.
 *
 * @return the clamped value.
 */
fun clamp(min: Double, value: Double, max: Double): Double {
    if (value < min) return min
    return if (value > max) max else value
}