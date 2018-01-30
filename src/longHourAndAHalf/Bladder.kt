package longHourAndAHalf

/**
 * Simulates an urinary bladder.
 *
 * **Usage in the A Long Hour and a Half:**
 *
 * Bladder is created in [startGame] and passed as the constructor argument to [Character].
 *
 * @property urine Amount of urine in this bladder in milliliters.
 * @property incontinence Incontinence multiplier for this bladder.
 */
class Bladder(var urine: Int, val incontinence: Double) {
    /**
     * Maximal sphincter power.
     * It is inversely proportional to the [incontinence] level.
     */
    val maximalSphincterPower = (100 / incontinence).toInt()

    /** Current sphincter power. */
    var sphincterPower = maximalSphincterPower

    /** [Sphincter power][sphincterPower] in percents from the [maximal level][maximalSphincterPower]. */
    val sphincterPowerPercent
        get() = (sphincterPower.toDouble() / maximalSphincterPower * 100).toInt()

    companion object {
        /**
         * Maximal [urine] amount that a bladder can have.
         */
        const val MAXIMAL_CAPACITY = 1500

        /**
         * [Urine][urine] amount when leaks begin to occur.
         */
        const val LEAKING_LEVEL = 1000
    }
}