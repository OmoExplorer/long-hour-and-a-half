package longHourAndAHalf

object SlideIterator : Iterator<Slide> {
    /** Returns `true` if the iteration has more slides. */
    override fun hasNext(): Boolean {
        TODO("not implemented")
    }

    /** Returns the next slide in the iteration. */
    override fun next(): Slide {
        TODO("not implemented")
    }
}

abstract class Plot(private val gameplay: Gameplay, vararg val slides: () -> Slide) : Iterable<Slide> {
    /** Returns an iterator over the slides of this plot. */
    override fun iterator() = SlideIterator
}