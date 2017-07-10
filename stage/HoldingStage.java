package omo.stage;

import java.awt.EventQueue;
import java.util.ArrayList;
import static omo.GameCore.STAGE_POOL;

class HoldingStage extends SelectionStage
{
    HoldingStage(ArrayList<Action> actions, short duration, String... text)
    {
        super(actions, duration, text);
        addHoldingActions();
    }

    HoldingStage(ArrayList<Action> actions, short duration)
    {
        super(actions, duration);
        addHoldingActions();
    }

    private void addHoldingActions()
    {
        EventQueue.invokeLater(() ->
        {
            addAction(new Action("Hold crotch", STAGE_POOL.holdCrotch));
            addAction(new Action("Rub thigs", STAGE_POOL.rubThigs));
            addAction(new Action("Give up", STAGE_POOL.giveUp));
        });

        //TODO: More holding actions
    }
}
