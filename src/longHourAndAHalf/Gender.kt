package longHourAndAHalf

enum class Gender {
    FEMALE {
        override fun toString() = "Female"
    },
    MALE {
        override fun toString() = "Male"
    }
}