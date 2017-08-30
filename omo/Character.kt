package omo

import java.util.*

enum class Gender {
    FEMALE, MALE
}

class Character(
        var name: String,
        var gender: Gender,
        val bladder: Bladder,
        var level: Int,
        var xp: Int
) {
    lateinit var undies: Wear
    lateinit var lower: Wear

    fun setupForSchool(initialBladderFullness: Double = Random().nextInt(75).toDouble(), undies: Wear, lower: Wear) {
        this.bladder.fullness = initialBladderFullness
        this.undies = undies
        this.lower = lower
    }
}