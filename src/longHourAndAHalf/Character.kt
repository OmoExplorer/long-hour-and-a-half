package longHourAndAHalf

@Suppress("KDocUnresolvedReference")
/**
 * Main character that possessed by a player.
 *
 * **Usage:**
 *
 * Character is created in [startGame], then passed to the [Gameplay] constructor as a parameter.
 *
 * @constructor Creates a new character.
 *
 * [name], [gender] and [bladder] properties' values are copied from the
 * [profile] that passed as the constructor parameter.
 *
 * @author JavaBird
 *
 * @property underwear This character's underwear.
 * @property outerwear This character's outerwear.
 */
class Character(profile: CharacterProfile, val underwear: Underwear, val outerwear: Outerwear) {
    /**
     * Character's name.
     *
     * Name is used by NPCs when they talk to character.
     */
    val name = profile.name
    /**
     * Character's gender.
     *
     * Gender affects several game text fragments.
     */
    val gender = profile.gender

    /** Character's bladder. */
    val bladder = Bladder(0, profile.incontinence)
}