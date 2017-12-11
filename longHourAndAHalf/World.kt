package longHourAndAHalf

import java.io.Serializable

/**
 * Stores game world data, such as current game [time].
 *
 * @see Time
 */
class World : Serializable {
    /**
     * World's current time.
     *
     * When being changed, checks if the lesson should finish; if so, finishes it. Also, runs [timeEffect].
     */
    var time: Time = SchoolDay.gameBeginningTime
        set(value) {
            val oldValue = field
            field = value

            val offset = time.rawMinutes - oldValue.rawMinutes

            if (offset != 0) timeEffect(offset)
            ui.timeChanged(time)
        }

    /**
     * Runs all time-related events when the [time] changes, such as:
     * - Checks if the lesson should finish with [Lesson.shouldFinish], if so, finishes it with [Lesson.finish];
     * - Applies the drain cheat ([Bladder.applyDrainCheat]);
     * - Dries character's clothes ([Character.dryClothes]);
     * - Makes the character leak (checking with [Bladder.shouldLeak], making a leak by [Bladder.leak]);
     * - Generates urine ([Bladder.makeUrine]);
     * - Depletes the sphincter power ([Bladder.decaySphincterPower]);
     * - On hardcore, increasing the [thirst][Character.thirst] level.
     *
     * @see Bladder
     * @see Lesson
     */
    private fun timeEffect(timeOffset: Int) {
        with(core.schoolDay.lesson) {
            if (shouldFinish()) {
                finish()
            }
        }

        with(core.character) {
            bladder.applyDrainCheat()
            dryClothes(timeOffset)

            if (!fatalLeakOccured && bladder.shouldLeak()) bladder.leak(timeOffset)
            bladder.makeUrine(timeOffset)
            bladder.decaySphincterPower(timeOffset)
            if (core.hardcore) {
                thirst += 2
                if (thirst > Character.MAXIMAL_THIRST) {
                    core.plot.nextStageID = PlotStageID.DRINK
                }
            }
        }
    }
}