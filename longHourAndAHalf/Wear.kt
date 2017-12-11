package longHourAndAHalf

import java.io.Serializable
import java.util.*

/**
 * @property name Wear name (for example, "Skirt").
 */
abstract class AbstractWear(var name: String) : Serializable {
    override fun toString() = name
}

/**
 * Wear stubs that aren't intended to be used in game.
 *
 * @param name Wear name (for example, "Skirt").
 * @property instead Lambda that returns [Wear] object which should be used instead.
 */
class MaintenanceWear(name: String, val instead: () -> Wear) : AbstractWear(name)

/**
 * Underwear or outerwear of a character.
 *
 * @param name Wear name (for example, "Skirt").
 * @property insert Name used in a game name (for example "panties")
 * @property pressure Pressure of this wear.
 * 1 point of a pressure takes 1 point from a maximal bladder capacity.
 * @property absorption Absorption of this wear.
 * 1 point of an absorption can store 1 point of a leaked pee.
 * @property dryingOverTime Drying over time.
 * 1 point = -1 pee percent per 3 minutes
 * @property type Whether this is an underwear or an outerwear.
 */
open class Wear(
        name: String,
        var insert: String = name.toLowerCase(),
        val pressure: Double,
        val absorption: Double,
        val dryingOverTime: Double,
        var type: WearType? = null,
        var colorable: Boolean = true
) : AbstractWear(name) {
    /**
     * Whether this wear equals "No under/outerwear".
     */
    val isMissing: Boolean = name == "No outerwear" || name == "No underwear"

    /**
     * The wear assigned color.
     */
    lateinit var color: WearColor

    companion object {
        /**
         * @param name name of a wear to return.
         * @return wear with the corresponding name, or `null` if no wear found.
         */
        fun getByName(name: String): AbstractWear? {
            //Merging underwear an outerwear lists
            val list = Wardrobe.underwear + Wardrobe.outerwear

            //Looping through the merged list
            for (it in list)
                if (it.name == name) return it

            return null
        }

        /**
         * @return random wear of a specified type.
         */
        fun getRandom(type: WearType): Wear {   //TODO: Gender conforming random
            val list = when (type) {
                WearType.UNDERWEAR -> Wardrobe.underwear
                WearType.OUTERWEAR -> Wardrobe.outerwear
                WearType.BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE argument isn't supported")
            }
            val random = Random()

            fun nextRandom() = list[random.nextInt(list.size)]

            var wearToReturn: AbstractWear

            do {
                wearToReturn = nextRandom()
            } while (wearToReturn !is Wear)

            return wearToReturn
        }
    }
}

class Skirt(
        name: String,
        insert: String,
        pressure: Double,
        absorption: Double,
        dryingOverTime: Double
) : Wear(name, insert, pressure, absorption, dryingOverTime, WearType.OUTERWEAR) {
    var pantyhoses = false
}