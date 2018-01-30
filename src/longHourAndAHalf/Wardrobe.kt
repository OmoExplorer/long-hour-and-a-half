package longHourAndAHalf

/**
 * Contains all built-in wear models.
 * @see WearModel
 */
object Wardrobe {
    /** List of all built-in underwear models. */
    val underwears = listOf(
            WearCategoryNode(
                    "Underwear",

                    WearCategoryNode(
                            "Female",

                            WearCategoryNode(
                                    "Regular",

                                    UnderwearModel("Strings", "panties", 1.0, 2.0, 1.0),
                                    UnderwearModel("Tanga panties", "panties", 1.5, 3.0, 1.0),
                                    UnderwearModel("Regular panties", "panties", 2.0, 4.0, 1.0),
                                    UnderwearModel("Briefs", "panties", 4.0, 7.0, 1.0)
                            ),
                            WearCategoryNode(
                                    "Swimwear",

                                    UnderwearModel("String bikini", "bikini panties", 1.0, 1.0, 2.0),
                                    UnderwearModel("Regular bikini", "bikini panties", 2.0, 2.0, 2.0),
                                    UnderwearModel("Swimsuit", "swimsuit", 4.0, 2.5, 2.5)
                            ),
                            WearCategoryNode(
                                    "Diapers",

                                    UnderwearModel("Light diaper", "diaper", 9.0, 50.0, 0.0),
                                    UnderwearModel("Normal diaper", "diaper", 18.0, 100.0, 0.0),
                                    UnderwearModel("Heavy diaper", "diaper", 25.0, 175.0, 0.0)
                            ),
                            WearCategoryNode(
                                    "Menstrual pads",

                                    UnderwearModel("Light pad", "pad", 2.0, 16.0, 0.25),
                                    UnderwearModel("Normal pad", "pad", 3.0, 24.0, 0.25),
                                    UnderwearModel("Big pad", "pad", 4.0, 32.0, 0.25)
                            )
                    ),
                    WearCategoryNode(
                            "Male",

                            UnderwearModel("Pants", "pants", 2.5, 5.0, 1.0),
                            UnderwearModel("Shorts-alike pants", "pants", 3.75, 7.5, 1.0)
                    ),
                    WearCategoryNode(
                            "Special",

                            UnderwearModel("No underwear", "", 0.0, 0.0, 1.0, false),
                            UnderwearModel("Anti-gravity pants", "pants", 0.0, 4.0, 1.0),
                            UnderwearModel("Super-absorbing diaper", "diaper", 18.0, 600.0, 0.0)
                    )
            )
    )

    /** List of all built-in outerwear models. */

    val outerwears = listOf(
            WearCategoryNode(
                    "Outerwear",

                    WearCategoryNode(
                            "Female",

                            WearCategoryNode(
                                    "Jeans",

                                    OuterwearModel("Long jeans", "jeans", 7.0, 12.0, 1.2),
                                    OuterwearModel("Knee-length jeans", "jeans", 6.0, 10.0, 1.2),
                                    OuterwearModel("Short jeans", "shorts", 5.0, 8.5, 1.2),
                                    OuterwearModel("Very short jeans", "shorts", 4.0, 7.0, 1.2)
                            ),
                            WearCategoryNode(
                                    "Trousers",

                                    OuterwearModel("Long trousers", "trousers", 9.0, 15.75, 1.4),
                                    OuterwearModel("Knee-length trousers", "trousers", 8.0, 14.0, 1.4),
                                    OuterwearModel("Short trousers", "shorts", 7.0, 12.25, 1.4),
                                    OuterwearModel("Very short trousers", "shorts", 6.0, 10.5, 1.4)
                            ),
                            WearCategoryNode(
                                    "Skirts",

                                    OuterwearModel("Long skirt", "skirt", 5.0, 6.0, 1.7),
                                    OuterwearModel("Knee-length skirt", "skirt", 4.0, 4.8, 1.7),
                                    OuterwearModel("Short skirt", "skirt", 3.0, 3.6, 1.7),
                                    OuterwearModel("Mini skirt", "skirt", 2.0, 2.4, 1.7),
                                    OuterwearModel("Micro skirt", "skirt", 1.0, 1.2, 1.7)
                            ),
                            OuterwearModel("Leggings", "leggings", 10.0, 11.0, 1.8),
                            WearCategoryNode(
                                    ""
                            )
                    ),
                    WearCategoryNode(
                            "Male",

                            OuterwearModel("Short male jeans", "jeans", 5.0, 8.5, 1.2),
                            OuterwearModel("Normal male jeans", "jeans", 7.0, 12.0, 1.2),
                            OuterwearModel("Male trousers", "trousers", 9.0, 15.75, 1.4)
                    ),
                    OuterwearModel("No outerwear", "", 0.0, 0.0, 1.0)
            )
    )
//            //        Name      Insert Name     Pressure, Absorption, Drying
////            MaintenanceWear("Random outerwear") {
////                Wear.getRandom(WearType.OUTERWEAR)
////            },
////            MaintenanceWear("Custom outerwear") {
////                MaintenanceWearProcessor.openCustomWear(WearType.OUTERWEAR)!!
////            },
//            ,
//
//            ,
//            ,
//            OuterwearModel("Long skirt and tights", "skirt and tights", 6.0, 7.5, 1.6),
//            OuterwearModel("Knee-length skirt and tights", "skirt and tights", 5.0, 8.75, 1.6),
//            OuterwearModel("Short skirt and tights", "skirt and tights", 4.0, 7.0, 1.6),
//            OuterwearModel("Mini skirt and tights", "skirt and tights", 3.0, 5.25, 1.6),
//            OuterwearModel("Micro skirt and tights", "skirt and tights", 2.0, 3.5, 1.6),
//            ,
//
//    )
}