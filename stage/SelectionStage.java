package omo.stage;

public class SelectionStage extends BladderAffectingStage
{
    private Action[] actions;

    void addAction(Action action)
    {
        actions.add(action);
    }

    SelectionStage(omo.stage.Action[] actions, short duration, String... text)
    {
        super(duration, text);
        this.actions = actions;
        addAction(new Action("Cheat (will reset your score)", new CheatStage(actions, duration, text)));
    }
    SelectionStage(omo.stage.Action[] actions, short duration)
    {
        super(duration);
        this.actions = actions;
        addAction(new Action("Cheat (will reset your score)", new CheatStage(actions, duration)));
    }
}