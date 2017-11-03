package longHourAndAHalf

import java.io.Serializable

class World(@Transient var core: Core) : Serializable {
    /**
     * Virtual world current time.
     */
    var time: Time = SchoolDay.gameBeginningTime
        set(value) {
            val oldValue = field
            field = value

            val offset = time.rawMinutes - oldValue.rawMinutes
            with(core.character) {
                applyDrainCheat()
                dryClothes(offset)
                timeEffect(offset)
            }
            core.ui.timeChanged(time)
        }
}