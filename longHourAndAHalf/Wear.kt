package longHourAndAHalf

import java.io.Serializable
import java.util.*

/**
 * Describes an underwear of an outerwear of a character.
 *
 * @author JavaBird
 */
class Wear : Serializable {
    /**
     * The wear name (e. g. "Regular panties")
     */
    val name: String

    /**
     * The pressure of an wear.
     * 1 point of a pressure takes 1 point from the maximal bladder capacity.
     */
    val pressure: Double

    /**
     * The absorption of an wear.
     * 1 point of an absorption can store 1 point of a leaked pee.
     */
    val absorption: Double

    /**
     * The drying over time.
     * 1 point = -1 pee unit per 3 minutes
     */
    val dryingOverTime: Double

    /**
     * Whether or not certain wear equals "No under/outerwear".
     */
    val isMissing: Boolean

    /**
     * The insert characterName used in the game text (e. g. "panties")
     */
    private var insertName: String? = null

    /**
     * The wear assigned color.
     */
    var color: String? = null

    var type: WearType? = null

    /**
     * @param name           the wear name (e. g. "Regular panties")
     * @param insertName     the insert name used in the game text (e. g. "panties")
     * @param pressure       the pressure of an wear.
     *                          1 point of a pressure takes 1 point from the maximal bladder capacity.
     * @param absorption     the absorption of an wear.
     *                          1 point of an absorption can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.
     *                          1 point = -1 pee unit per 3 minutes
     */
    constructor(name: String, insertName: String, pressure: Double, absorption: Double, dryingOverTime: Double) {
        this.name = name
        this.insertName = insertName
        this.pressure = pressure
        this.absorption = absorption
        this.dryingOverTime = dryingOverTime
        isMissing = name == "No underwear" || name == "No outerwear"
    }

    /**
     * @param name           the wear characterName (e. g. "Regular panties")
     * @param insertName     the insert characterName used in the game text (e. g. "panties")
     * @param pressure       the pressure of an wear.
     *                          1 point of a pressure takes 1 point from the maximal bladder capacity.
     * @param absorption     the absorption of an wear.
     *                          1 point of an absorption can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.
     *                          1 point = -1 pee unit per 3 minutes
     * @param type           the wear type
     */
    constructor(name: String, insertName: String, pressure: Double, absorption: Double, dryingOverTime: Double, type: WearType) {
        this.name = name
        this.insertName = insertName
        this.pressure = pressure
        this.absorption = absorption
        this.dryingOverTime = dryingOverTime
        this.type = type
        isMissing = name == "No underwear" || name == "No outerwear"
    }

    /**
     * @param insertName the insert name (used in game text) to set
     */
    fun setInsertName(insertName: String) {
        this.insertName = insertName
    }

    /**
     * @return the insert name used in the game text (e. g. "panties")
     */
    fun insert(): String? {
        return insertName
    }

    enum class WearType {
        UNDERWEAR, OUTERWEAR, BOTH_SUITABLE
    }

    companion object {
        const val serialVersionUID = 1L

        /**
         * List of all underwear types.
         */
        var underwearList = arrayOf(
                //        Name      Insert characterName     Pressure, Absotption, Drying over time
                Wear("Random", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 0.0),
                Wear("No underwear", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 1.0),
                Wear("Strings", "panties", 1.0, 2.0, 1.0),
                Wear("Tanga panties", "panties", 1.5, 3.0, 1.0),
                Wear("Regular panties", "panties", 2.0, 4.0, 1.0),
                Wear("\"Boy shorts\" panties", "panties", 4.0, 7.0, 1.0),
                Wear("String bikini", "bikini panties", 1.0, 1.0, 2.0),
                Wear("Regular bikini", "bikini panties", 2.0, 2.0, 2.0),
                Wear("Swimsuit", "swimsuit", 4.0, 2.5, 2.5),
                Wear("Light diaper", "diaper", 9.0, 50.0, 0.0),
                Wear("Normal diaper", "diaper", 18.0, 100.0, 0.0),
                Wear("Heavy diaper", "diaper", 25.0, 175.0, 0.0),
                Wear("Light pad", "pad", 2.0, 16.0, 0.25),
                Wear("Normal pad", "pad", 3.0, 24.0, 0.25),
                Wear("Big pad", "pad", 4.0, 32.0, 0.25),
                Wear("Pants", "pants", 2.5, 5.0, 1.0),
                Wear("Shorts-alike pants", "pants", 3.75, 7.5, 1.0),
                Wear("Anti-gravity pants", "pants", 0.0, 4.0, 1.0),
                Wear("Super-absorbing diaper", "diaper", 18.0, 600.0, 0.0)
        )

        /**
         * List of all outerwear types.
         */
        var outerwearList = arrayOf(
                //        Name      Insert characterName     Pressure, Absotption, Drying over time
                Wear("Random", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 0.0),
                Wear("No outerwear", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 1.0),
                Wear("Long jeans", "jeans", 7.0, 12.0, 1.2),
                Wear("Knee-length jeans", "jeans", 6.0, 10.0, 1.2),
                Wear("Short jeans", "shorts", 5.0, 8.5, 1.2),
                Wear("Very short jeans", "shorts", 4.0, 7.0, 1.2),
                Wear("Long trousers", "trousers", 9.0, 15.75, 1.4),
                Wear("Knee-length trousers", "trousers", 8.0, 14.0, 1.4),
                Wear("Short trousers", "shorts", 7.0, 12.25, 1.4),
                Wear("Very short trousers", "shorts", 6.0, 10.5, 1.4),
                Wear("Long skirt", "skirt", 5.0, 6.0, 1.7),
                Wear("Knee-length skirt", "skirt", 4.0, 4.8, 1.7),
                Wear("Short skirt", "skirt", 3.0, 3.6, 1.7),
                Wear("Mini skirt", "skirt", 2.0, 2.4, 1.7),
                Wear("Micro skirt", "skirt", 1.0, 1.2, 1.7),
                Wear("Long skirt and tights", "skirt and tights", 6.0, 7.5, 1.6),
                Wear("Knee-length skirt and tights", "skirt and tights", 5.0, 8.75, 1.6),
                Wear("Short skirt and tights", "skirt and tights", 4.0, 7.0, 1.6),
                Wear("Mini skirt and tights", "skirt and tights", 3.0, 5.25, 1.6),
                Wear("Micro skirt and tights", "skirt and tights", 2.0, 3.5, 1.6),
                Wear("Leggings", "leggings", 10.0, 11.0, 1.8),
                Wear("Short male jeans", "jeans", 5.0, 8.5, 1.2),
                Wear("Normal male jeans", "jeans", 7.0, 12.0, 1.2),
                Wear("Male trousers", "trousers", 9.0, 15.75, 1.4)
        )

        /**
         * List of all colors that clothes may have.
         */
        var colorList = arrayOf("Black", "Gray", "Red", "Orange", "Yellow", "Green", "Blue", "Dark blue", "Purple", "Pink")

        /**
         * @param name name of a wear to return.
         * @param type type of a wear to search in.
         * @return wear object with corresponding name, or `null` if no wear found.
         * @throws `IllegalArgumentException` - if `type` is `BOTH_SUITABLE`.
         */
        fun getByName(name: String, type: Wear.WearType): Wear? {
            val list = when (type) {
                WearType.OUTERWEAR -> Wear.outerwearList
                WearType.UNDERWEAR -> Wear.underwearList
                WearType.BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE wear type isn't supported")
            }

            for (iWear in list) {
                if (iWear.name == name)
                    return iWear
            }

            return null
        }

        fun getRandom(type: WearType): Wear {
            val list = when (type) {
                WearType.UNDERWEAR -> Wear.underwearList
                WearType.OUTERWEAR -> Wear.outerwearList
                WearType.BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE argument isn't supported")
            }
            val random = Random()

            fun nextRandom() = list[random.nextInt(list.size)]

            var wearToReturn = nextRandom()

            while (wearToReturn.name == "Random") {
                wearToReturn = nextRandom()
            }

            return wearToReturn
        }
    }
}