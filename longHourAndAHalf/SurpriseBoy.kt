package longHourAndAHalf

import java.io.Serializable

/**
 * State of boy NPC that takes place in different scenes on hardcore difficulty.
 */
class SurpriseBoy : Serializable {
    val name: String = availableNames.randomItem()

    var timesPeeDenied = 0

    companion object {
        private val availableNames = listOf("Mark", "Mike", "Jim", "Alex", "Ben", "Bill", "Dan")
    }
}