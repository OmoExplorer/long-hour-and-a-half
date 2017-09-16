package omo

import java.io.Serializable

enum class Gender : Serializable {
    FEMALE, MALE
}

data class Character(
        var name: String,
        var gender: Gender,
        var bladder: Bladder,
        var level: Int,
        var xp: Int
) : Serializable