package omo;

import java.util.ArrayList;

class SelectionStage extends BladderAffectingStage
{
    private ArrayList<Action> actions;

    void addAction(Action action)
    {
        actions.add(action);
    }

    SelectionStage(ArrayList<omo.Action> actions, short duration, String... text)
    {
        super(duration, text);
        this.actions = actions;
        addAction(new Action("Cheat (will reset your score)", new CheatStage(actions, duration, text)));
    }
}