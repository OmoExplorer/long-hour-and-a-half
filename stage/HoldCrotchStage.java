package omo.stage;

import static omo.Bladder.*;
import static omo.NarrativeEngine.getCaughtByClassmates;
import omo.ui.GameFrame;

class HoldCrotchStage extends Stage
{
    HoldCrotchStage(Stage nextStage)
    {
        super(nextStage, new String[]
        {
            "You don't think anyone will see you doing it,",
            "so you take your hand and hold yourself down there.",
            "It feels a little better for now."
        });
    }

    @Override
    void operate(GameFrame ui)
    {
        rechargeSphPower(ui, 20);
        offsetTime(ui, 3);
        getCaughtByClassmates();
    }
}