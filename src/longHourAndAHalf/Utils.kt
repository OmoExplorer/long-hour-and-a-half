package longHourAndAHalf

import java.util.*
import javax.swing.JFrame

/** Packs this frame and makes it visible. */
fun JFrame.display() {
    pack()
    isVisible = true
}

/** Random number generator for the whole game. */
val random = Random()

/** Returns a random element from this iterable. */
fun <T> Iterable<T>.randomItem(): T {
    val randomIndex = random.nextInt(this.count())
    return this.elementAt(randomIndex)
}

/** Returns a random element from this array. */
fun <T> Array<T>.randomItem(): T {
    val randomIndex = random.nextInt(this.size)
    return this[randomIndex]
}