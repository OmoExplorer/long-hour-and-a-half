package omo.stage;

import java.util.ArrayList;

class HoldingStage extends SelectionStage
{
    HoldingStage(ArrayList<Action> actions, short duration, String... text)
    {
        super(actions, duration, text);
        addAction(new Action("Hold crotch", new HoldCrotchStage(new HoldingStage(actions, duration, text))));
        addAction(new Action("Rub thigs", new RubThigsStage(new HoldingStage(actions, duration, text))));
        //TODO: More holding actions
    }
}