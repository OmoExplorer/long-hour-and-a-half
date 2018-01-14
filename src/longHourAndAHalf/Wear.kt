package longHourAndAHalf

///**
// * Underwear or outerwear of a character.
// *
// * @property color The wear assigned color.
// */
//open class OldWear(val model: WearModel, val color: WearColor) : AbstractWear(model.name) {
//    companion object {
//        /**
//         * @param name name of a wear to return.
//         * @return wear with the corresponding name, or `null` if no wear found.
//         */
//        fun getByName(name: String): AbstractWear? {
//            //Merging underwear an outerwear lists
//            val list = Wardrobe.underwear + Wardrobe.outerwear
//
//            //Looping through the merged list
//            for (it in list)
//                if (it.name == name) return it
//
//            return null
//        }
//
//        /**
//         * @return random wear of a specified type.
//         */
//        fun getRandom(type: WearType): Wear {   //TODO: Gender conforming random
//            val list = when (type) {
//                WearType.UNDERWEAR -> Wardrobe.underwear
//                WearType.OUTERWEAR -> Wardrobe.outerwear
//            }
//            val random = Random()
//
//            fun nextRandom() = list[random.nextInt(list.size)]
//
//            var wearToReturn: AbstractWear
//
//            do {
//                wearToReturn = nextRandom()
//            } while (wearToReturn !is Wear)
//
//            return wearToReturn
//        }
//    }
//}

/** Piece of character's wear. */
abstract class Wear(model: WearModel, val color: WearColor) {
    val name = model.name
    val insertName = model.insertName
    val pressure = model.pressure
    val maxAbsorption = model.absorption
    var absorption = maxAbsorption
    val dryingOverTime = model.dryingOverTime
    val isMissing = model.isMissing
}

/** Underwear. */
class Underwear(model: UnderwearModel, wearColor: WearColor) : Wear(model, wearColor) {
    companion object {
        /**
         * Returns random underwear.
         */
        //TODO: Gender conforming random
        fun getRandom(color: WearColor) = Underwear(UnderwearModel.getRandom(), color)
    }
}

/** Outerwear. */
open class Outerwear(model: OuterwearModel, wearColor: WearColor) : Wear(model, wearColor) {
    companion object {
        /**
         * Returns random outerwear.
         *///TODO: Gender conforming random
        fun getRandom(color: WearColor) = Outerwear(OuterwearModel.getRandom(), color)
    }
}

/** Skirt. */
class Skirt(model: OuterwearModel, color: WearColor) : Outerwear(model, color) {
    var pantyhoses = false
}