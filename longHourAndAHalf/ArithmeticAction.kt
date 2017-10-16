package longHourAndAHalf

/**
 * Describes the basic arithmetic actions.
 */
@Suppress("KDocMissingDocumentation")
enum class ArithmeticAction(val sign: String) {
    ADD("+"),
    TAKE("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    ADD_PERCENT("+"),
    TAKE_PERCENT("-");

    override fun toString() = sign
}