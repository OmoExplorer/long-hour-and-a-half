package omo.stage;

import omo.Bladder;
import omo.ui.GameFrame;

public class BladderAffectingStage extends Stage
{
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

    @Override
    void operate(GameFrame ui)
    {
        super.operate();
        Bladder.passTime(ui, duration);
    }

}