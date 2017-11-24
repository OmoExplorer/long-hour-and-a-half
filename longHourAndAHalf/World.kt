package longHourAndAHalf

import java.io.Serializable

/**
 * Stores game world data.
 */
class World : Serializable {
    /**
     * World's current time.
     */
    var time: Time = SchoolDay.gameBeginningTime
        set(value) {
            val oldValue = field
            field = value

            val offset = time.rawMinutes - oldValue.rawMinutes

            with(CoreHolder.core.schoolDay.lesson) {
                if (shouldFinish()) {
                    finish()
                }
            }
            with(CoreHolder.core.character) {
                bladder.applyDrainCheat()
                dryClothes(offset)
            }

            timeEffect(offset)
            CoreHolder.core.ui.timeChanged(time)
        }

    /**
     * Runs all time-related events.
     */
    private fun timeEffect(timeOffset: Int) {
        with(CoreHolder.core.character) {
            if (bladder.shouldLeak()) bladder.sphincterSpasm()
            bladder.makeUrineFromWater(timeOffset)
            bladder.decaySphincterPower(timeOffset)
            if (CoreHolder.core.hardcore) {
                thirst += 2
                if (thirst > Character.MAXIMAL_THIRST) {
                    CoreHolder.core.plot.nextStageID = PlotStageID.DRINK
                }
            }
        }
    }
}