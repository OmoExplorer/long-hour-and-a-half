package longHourAndAHalf

private typealias UWM = UnderwearModel
private typealias OWM = OuterwearModel

/** Contains all built-in wear models. */
object Wardrobe {

    /** List of all built-in underwear models. */
    val underwearModels = listOf(
            // Name, Insert Name, Pressure, Absorption, Drying over time
            MaintenanceUnderwearModel("Random underwear") { UnderwearModel.getRandom() },
            MaintenanceUnderwearModel("Custom underwear") { openCustomWearModel()!! },
            UWM("No underwear", "", 0.0, 0.0, 1.0),
            UWM("Strings", "panties", 1.0, 2.0, 1.0),
            UWM("Tanga panties", "panties", 1.5, 3.0, 1.0),
            UWM("Regular panties", "panties", 2.0, 4.0, 1.0),
            UWM("Briefs", "panties", 4.0, 7.0, 1.0),
            UWM("String bikini", "bikini panties", 1.0, 1.0, 2.0),
            UWM("Regular bikini", "bikini panties", 2.0, 2.0, 2.0),
            UWM("Swimsuit", "swimsuit", 4.0, 2.5, 2.5),
            UWM("Light diaper", "diaper", 9.0, 50.0, 0.0),
            UWM("Normal diaper", "diaper", 18.0, 100.0, 0.0),
            UWM("Heavy diaper", "diaper", 25.0, 175.0, 0.0),
            UWM("Light pad", "pad", 2.0, 16.0, 0.25),
            UWM("Normal pad", "pad", 3.0, 24.0, 0.25),
            UWM("Big pad", "pad", 4.0, 32.0, 0.25),
            UWM("Pants", "pants", 2.5, 5.0, 1.0),
            UWM("Shorts-alike pants", "pants", 3.75, 7.5, 1.0),
            UWM("Anti-gravity pants", "pants", 0.0, 4.0, 1.0),
            UWM("Super-absorbing diaper", "diaper", 18.0, 600.0, 0.0)
    )

    /** List of all built-in outerwear models. */
    val outerwearModels = listOf(
            // Name, Insert Name, Pressure, Absorption, Drying over time
            MaintenanceOuterwearModel("Random outerwear") { OuterwearModel.getRandom() },
            MaintenanceOuterwearModel("Custom outerwear") { openCustomWearModel()!! },
            OWM("No outerwear", "", 0.0, 0.0, 1.0),
            OWM("Long jeans", "jeans", 7.0, 12.0, 1.2),
            OWM("Knee-length jeans", "jeans", 6.0, 10.0, 1.2),
            OWM("Short jeans", "shorts", 5.0, 8.5, 1.2),
            OWM("Very short jeans", "shorts", 4.0, 7.0, 1.2),
            OWM("Long trousers", "trousers", 9.0, 15.75, 1.4),
            OWM("Knee-length trousers", "trousers", 8.0, 14.0, 1.4),
            OWM("Short trousers", "shorts", 7.0, 12.25, 1.4),
            OWM("Very short trousers", "shorts", 6.0, 10.5, 1.4),
            OWM("Long skirt", "skirt", 5.0, 6.0, 1.7),
            OWM("Knee-length skirt", "skirt", 4.0, 4.8, 1.7),
            OWM("Short skirt", "skirt", 3.0, 3.6, 1.7),
            OWM("Mini skirt", "skirt", 2.0, 2.4, 1.7),
            OWM("Micro skirt", "skirt", 1.0, 1.2, 1.7),
            OWM("Long skirt and tights", "skirt and tights", 6.0, 7.5, 1.6),
            OWM("Knee-length skirt and tights", "skirt and tights", 5.0, 8.75, 1.6),
            OWM("Short skirt and tights", "skirt and tights", 4.0, 7.0, 1.6),
            OWM("Mini skirt and tights", "skirt and tights", 3.0, 5.25, 1.6),
            OWM("Micro skirt and tights", "skirt and tights", 2.0, 3.5, 1.6),
            OWM("Leggings", "leggings", 10.0, 11.0, 1.8),
            OWM("Short male jeans", "jeans", 5.0, 8.5, 1.2),
            OWM("Normal male jeans", "jeans", 7.0, 12.0, 1.2),
            OWM("Male trousers", "trousers", 9.0, 15.75, 1.4)
    )
}