package longHourAndAHalf

/**
 * Describes the basic arithmetic actions.
 * Used in [Scorer] and [ScoreNomination] for describing how to process a modifier.
 *
 * @property sign Symbolic representation of this arithmetic action.
 * Accessible through the [toString] method.
 */
@Suppress("KDocMissingDocumentation")
enum class ArithmeticAction(private val sign: String) {
    ADD("+"),
    TAKE("-"),
    MULTIPLY("*"),
    DIVIDE("/"),

    /** Add percents from a certain value. */
    ADD_PERCENT("+"),

    /** Take percents from a certain value. */
    TAKE_PERCENT("-");

    /** Returns an arithmetic action [sign]. */
    override fun toString() = sign
}