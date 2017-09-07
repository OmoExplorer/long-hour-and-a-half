package omo

class Lesson(var hard: Boolean = false) {
    object Teacher {
        /**
         * Times teacher denied character to go out.
         */
        var timesPeeDenied = 0

        /**
         * Whether or not character has to stay 30 minutes after class.
         */
        var stay = false
    }

    object Classmates {
        /**
         * Amount embarrassment raising every time character caught holding pee.
         */
        var holdingAwareness = 0

        /**
         * Number of times player got caught holding pee.
         */
        var timesCaught = 0
    }

    /**
     * The lesson time.
     */
    var time = 0


}