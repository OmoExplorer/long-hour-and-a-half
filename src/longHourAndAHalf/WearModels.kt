@file:Suppress("KDocMissingDocumentation")

package longHourAndAHalf

/**
 * Models of wear.
 *
 * @property name Wear name (for example, "Skirt").
 */
abstract class AbstractWearModel(val name: String) {
    override fun toString() = name
}

/**
 * Underwear model stubs that aren't intended to be used in game.
 *
 * @param name Wear name (for example, "Random").
 * @property instead Lambda that returns [UnderwearModel] object which should be used instead.
 */
class MaintenanceUnderwearModel(name: String, val instead: () -> UnderwearModel) : AbstractWearModel(name)

/**
 * Outerwear model stubs that aren't intended to be used in game.
 *
 * @param name Wear name (for example, "Random").
 * @property instead Lambda that returns [OuterwearModel] object which should be used instead.
 */
class MaintenanceOuterwearModel(name: String, val instead: () -> OuterwearModel) : AbstractWearModel(name)

/**
 * Model of wear.
 *
 * @param name wear name (for example, "Pants").
 *
 * @property insertName Name used in a game name (for example, "panties").
 *
 * @property pressure Pressure of this wear.
 *
 * 1 point of a pressure takes 1 point from a maximal bladder capacity.
 *
 * @property absorption Absorption of this wear.
 *
 * 1 point of an absorption can store 1 point of a leaked pee.
 *
 * @property dryingOverTime Drying-over-time rate.
 *
 * 1 point = -1 pee percent per 3 minutes.
 *
 * @property colorable Whether this wear can be colored.
 */
abstract class WearModel(
        name: String,
        val insertName: String = name.toLowerCase(),
        val pressure: Double,
        val absorption: Double,
        val dryingOverTime: Double,
        val colorable: Boolean = true
) : AbstractWearModel(name) {
    /** Whether this wear is "No underwear". */
    abstract val isMissing: Boolean
}

/**
 * Model of underwear.
 *
 * @param name wear name (for example, "Pants").
 *
 * @property insertName Name used in a game name (for example, "panties").
 *
 * @property pressure Pressure of this wear.
 *
 * 1 point of a pressure takes 1 point from a maximal bladder capacity.
 *
 * @property absorption Absorption of this wear.
 *
 * 1 point of an absorption can store 1 point of a leaked pee.
 *
 * @property dryingOverTime Drying-over-time rate.
 *
 * 1 point = -1 pee percent per 3 minutes.
 *
 * @property colorable Whether this wear can be colored.
 */
class UnderwearModel(
        name: String,
        insert: String = name.toLowerCase(),
        pressure: Double,
        absorption: Double,
        dryingOverTime: Double,
        colorable: Boolean = true
) : WearModel(name, insert, pressure, absorption, dryingOverTime, colorable) {
    /** Whether this wear is "No underwear". */
    override val isMissing: Boolean = name == "No underwear"

    companion object {
        /** Returns random underwear. */
        fun getRandom(): UnderwearModel {   //TODO: Gender conforming random
            var model: AbstractWearModel
            do model = Wardrobe.underwearModels.randomItem() while (model !is UnderwearModel)
            return model
        }

        //TODO: Gender conforming random
        /** Returns an underwear model by the specified [name]. */
        fun getByName(name: String): AbstractWearModel = Wardrobe.underwearModels.first { it.name == name }
    }
}

/**
 * Model of outerwear.
 *
 * @constructor
 * @param name wear name (for example, "Jeans").
 *
 * @property insertName Name used in a game name (for example, "jeans").
 *
 * @property pressure Pressure of this wear.
 *
 * 1 point of a pressure takes 1 point from a maximal bladder capacity.
 *
 * @property absorption Absorption of this wear.
 *
 * 1 point of an absorption can store 1 point of a leaked pee.
 *
 * @property dryingOverTime Drying-over-time rate.
 *
 * 1 point = -1 pee percent per 3 minutes.
 *
 * @property colorable Whether this wear can be colored.
 */
class OuterwearModel(
        name: String,
        insert: String = name.toLowerCase(),
        pressure: Double,
        absorption: Double,
        dryingOverTime: Double,
        colorable: Boolean = true
) : WearModel(name, insert, pressure, absorption, dryingOverTime, colorable) {
    /**  Whether this wear is "No outerwear". */
    override val isMissing: Boolean = name == "No outerwear"

    companion object {
        /** Returns random outerwear. */
        fun getRandom(): OuterwearModel {   //TODO: Gender conforming random
            var model: AbstractWearModel
            do model = Wardrobe.outerwearModels.randomItem() while (model !is OuterwearModel)
            return model
        }

        //TODO: Gender conforming random
        /** Returns an outerwear model by the specified [name]. */
        fun getByName(name: String): AbstractWearModel = Wardrobe.outerwearModels.first { it.name == name }
    }
}