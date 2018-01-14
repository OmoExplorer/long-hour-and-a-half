package longHourAndAHalf

import java.io.Serializable

/**
 * Data about cheats.
 */
class CheatData : Serializable {
    /**
     * Whether player has used cheats.
     */
    var cheatsUsed = false

    /**
     * Whether pee drain cheat enabled: pee mysteriously vanishes every 15 minutes.
     */
    var drain = false
}