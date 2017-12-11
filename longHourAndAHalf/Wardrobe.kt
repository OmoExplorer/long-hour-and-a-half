package longHourAndAHalf

/**
 * Contains all built-in wear models.
 */
object Wardrobe {
    /**
     * List of all built-in underwear models.
     */
    val underwear = listOf(
            //        Name      Insert Name     Pressure, Absorption, Drying over time
            MaintenanceWear("Random underwear") {
                Wear.getRandom(WearType.UNDERWEAR)
            },
            MaintenanceWear("Custom underwear") {
                MaintenanceWearProcessor.openCustomWear(WearType.UNDERWEAR)!!
            },
            Wear("No underwear", "", 0.0, 0.0, 1.0),
            Wear("Strings", "panties", 1.0, 2.0, 1.0),
            Wear("Tanga panties", "panties", 1.5, 3.0, 1.0),
            Wear("Regular panties", "panties", 2.0, 4.0, 1.0),
            Wear("Briefs", "panties", 4.0, 7.0, 1.0),
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
     * List of all built-in outerwear models.
     */
    val outerwear = listOf(
            //        Name      Insert Name     Pressure, Absorption, Drying over time
            MaintenanceWear("Random outerwear") {
                Wear.getRandom(WearType.OUTERWEAR)
            },
            MaintenanceWear("Custom outerwear") {
                MaintenanceWearProcessor.openCustomWear(WearType.OUTERWEAR)!!
            },
            Wear("No outerwear", "", 0.0, 0.0, 1.0),
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
}