package longHourAndAHalf

import java.io.Serializable

/**
 * Stores game world data.
 */
class World(@Transient var core: Core) : Serializable {
    /**
     * World's current time.
     */
    var time: Time = SchoolDay.gameBeginningTime
        set(value) {
            val oldValue = field
            field = value

            val offset = time.rawMinutes - oldValue.rawMinutes

            with(core.schoolDay.lesson) {
                if (shouldFinish()) {
                    finish()
                }
            }
            with(core.character) {
                bladder.applyDrainCheat()
                dryClothes(offset)
            }

            timeEffect(offset)
            core.ui.timeChanged(time)
        }

    /**
     * Runs all time-related events.
     */
    private fun timeEffect(timeOffset: Int) {
        with(core.character) {
            if (bladder.shouldLeak()) bladder.sphincterSpasm()
            bladder.makeUrineFromWater(timeOffset)
            bladder.decaySphincterPower(timeOffset)
            if (core.hardcore) {
                thirst += 2
                if (thirst > Character.MAXIMAL_THIRST) {
                    core.plot.nextStage = GameStage.DRINK
                }
            }
        }
    }
}