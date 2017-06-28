package omo.stage;

import java.util.ArrayList;

/**
 * Stage with cheats selection.
 */
class CheatStage extends SelectionStage
{
    CheatStage(ArrayList<omo.stage.Action> actions, short duration, String... text)
    {
        super(actions, duration, text);
        addAction(new Action("Pee in a bottle"));
        addAction(new Action("Go to extended game"));
        //TODO: Add more cheats
    }
}