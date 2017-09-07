package omo

class Scorer {
    private val finalMultipliers = mutableListOf<Int>()

    var score = 0
        get() {
            var score = this.score
            finalMultipliers.forEach { score *= it }
            return score
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