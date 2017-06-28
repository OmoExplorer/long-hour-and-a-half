package omo.stage;

import java.util.ArrayList;

public class SelectionStage extends BladderAffectingStage
{
    private ArrayList<Action> actions;

    void addAction(Action action)
    {
        actions.add(action);
    }

    SelectionStage(ArrayList<omo.stage.Action> actions, short duration, String... text)
    {
        super(duration, text);
        this.actions = actions;
        addAction(new Action("Cheat (will reset your score)", new CheatStage(actions, duration, text)));
    }
    SelectionStage(ArrayList<omo.stage.Action> actions, short duration)
    {
        super(duration);
        this.actions = actions;
        addAction(new Action("Cheat (will reset your score)", new CheatStage(actions, duration)));
    }
}