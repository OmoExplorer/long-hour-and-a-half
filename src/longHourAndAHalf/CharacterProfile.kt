package longHourAndAHalf

import java.io.Serializable

/**
 * [Character]'s profile.
 * Profiles are stored in files
 * (that's why this class implements [Serializable])
 * and used across the games.
 * Profiles can be created and edited in the
 * [Character Profile Editor][longHourAndAHalf.characterProfileEditor.CharacterProfileEditor].
 *
 * **Usage:**
 *
 * Before the game, the player is asked for a profile for the game character to use,
 * in [Game Setup][GameSetupFrame] dialog.
 * Player has to select a profile file.
 * File is selected with [longHourAndAHalf.characterProfileEditor.CharacterProfileFileChooser].
 * Then the profile is read from the file
 * and passed as a constructor argument to [Character] in [startGame].
 *
 * @author JavaBird
 *
 * @property name Character's name.
 *
 * Name is set by a player or random one is selected if they did not set it.
 *
 * @property gender Character's gender.
 *
 * Gender affects several game text fragments.
 *
 * @property incontinence Character's incontinence multiplier.
 *
 * During the game, it can be retrieved from [Bladder.incontinence].
 *
 * Incontinence multiplier affects the character bladder's
 * [maximal sphincter power][Bladder.maximalSphincterPower].
 */
class CharacterProfile(
        val name: String,
        val gender: Gender,
        val incontinence: Double
) : Serializable