package omo

class Scorer {
    private val finalMultipliers = mutableListOf<Int>()

    /**
     * Backing property for [score].
     * @suppress
     */
    private var _score = 0

    var score: Int
        get() {
            var score = _score
            finalMultipliers.forEach { score *= it }
            return score
        }
        set(value) {
            _score = value
        }

    operator fun plusAssign(amount: Int) {
        score += amount
    }

    operator fun minusAssign(amount: Int) {
        score -= amount
    }

    operator fun timesAssign(multiplier: Int) {
        score *= multiplier
    }

    operator fun divAssign(multiplier: Int) {
        score /= multiplier
    }

    fun addFinalMultiplier(multiplier: Int) {
        finalMultipliers.add(multiplier)
    }

    operator fun divAssign(multiplier: Double) {
        score = (score / multiplier).toInt()
    }
}