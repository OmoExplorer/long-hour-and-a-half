package longHourAndAHalf

/** Simulates the bladder sphincter. */
class Sphincter(incontinence: Double) {
    /** Incontinence multiplier. */
    var incontinence = incontinence
        set(value) {
            field = value.coerceIn(1.0, 10.0)
            ui.incontinenceMultiplierChanged(incontinence)
        }

    /** Maximal sphincter duration. */
    val maximalPower = (100 / incontinence).toInt()

    /** Sphincter duration. */
    var power = maximalPower
        set(value) {
            field = value.coerceIn(0, maximalPower)
            ui.sphincterStrengthChanged(power)
            if (power == 0)
                core.character.leak()
        }

    /** Decreases the [power] for specified amount of [minutes]. */
    fun decreasePower(minutes: Int) {
        power -= core.character.bladder.fullness / POWER_DECREASE_FROM_BLADDER_FULLNESS_PROPORTION
    }

    val leakingChance: Int
        get() = with(core.character.bladder) {
            if (fullness < leakingFullnessLevel) return 0

            val leakingZone = maximalFullnessLevel - leakingFullnessLevel
            val percentsForOneMl = 100 / leakingZone
            val fullnessSpanInLeakingZone = fullness - leakingFullnessLevel

            fullnessSpanInLeakingZone * percentsForOneMl
        }

    fun leakRandomly() {
        if (chance(leakingChance)) core.character.leak()
    }

    companion object {
        private const val POWER_DECREASE_FROM_BLADDER_FULLNESS_PROPORTION = 15000
    }
}