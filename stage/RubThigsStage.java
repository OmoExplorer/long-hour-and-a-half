package omo.stage;

import static omo.Bladder.*;
import omo.ui.GameFrame;

class RubThigsStage extends Stage
{
    RubThigsStage(Stage nextStage)
    {
        super(nextStage, new String[]
        {
            "You need to go, and it hurts, but you just",
            "can't bring yourself to risk getting caught with your hand between",
            "your legs. You rub your thighs hard but it doesn't really help."
        });
    }

    @Override
    void operate(GameFrame ui)
    {
        rechargeSphPower(ui, 2);
        offsetTime(ui, 3);
    }
}