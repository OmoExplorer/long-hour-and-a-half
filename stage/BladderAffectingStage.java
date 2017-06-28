package omo.stage;

import static omo.Bladder.*;
import omo.ui.GameFrame;

/**
 * Stage which affects time and bladder values.
 *
 * @author JavaBird
 */
public class BladderAffectingStage extends Stage
{
    /**
	 * Scene duration in in-game minutes.
	 */
    private final short duration;

    BladderAffectingStage(Stage nextStage, short duration, String[] text)
    {
        super(nextStage, text);
        this.duration = duration;
    }

    BladderAffectingStage(short duration, String[] text)
    {
        super(text);
        this.duration = duration;
    }

    BladderAffectingStage(Stage nextStage, short duration)
    {
        super(nextStage);
        this.duration = duration;
    }

    public BladderAffectingStage(short duration)
    {
        super();
        this.duration = duration;
    }

    @Override
    public void operate(GameFrame ui)
    {
        super.operate();
        passTime(ui, duration);
    }
}