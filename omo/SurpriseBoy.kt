package omo

import java.util.*

fun <E> List<E>.random() = this[Random().nextInt(this.size)]

class SurpriseBoy {
    val name: String = names.random()
    var timesPeeDenied = 0

    companion object {
        val names = listOf("Mark", "Mike", "Jim", "Alex", "Ben", "Bill", "Dan")
    }
}