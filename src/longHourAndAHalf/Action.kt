package longHourAndAHalf

/**
 * Choice for user on certain slides.
 * @author JavaBird
 *
 * @property name Action's name.
 * @property targetSlide Destination slide. Plot changes to this slide when this action is selected.
 */
class Action(val name: String, val targetSlide: SlideID) {
    override fun toString() = name
}