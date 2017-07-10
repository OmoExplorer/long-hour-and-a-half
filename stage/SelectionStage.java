package omo.stage;

import java.util.ArrayList;
import omo.ui.GameFrame;

public class SelectionStage extends Stage
{
    private final ArrayList<Action> actions;

    /**
     * Contstructor for stages with static text.
     * 
     * @param actions list of provided choices
     * @param duration stage duration
     * @param text static in-game text
     */
    SelectionStage(ArrayList<Action> actions, short duration, String... text)
    {
        super(text, duration);
        this.actions = actions;
    }
    
    /**
     * Contstructor for stages with dynamic text.
     * Text has to be set this way:<br>
     * <br>
     * <code>
     * SelectionStage stage = new SelectionStage(actions, duration)<br>
     * {<br>
     *      public String[] getText()<br>
     *      {<br>
     *          String[] text;<br>
     *          //text defining code<br>
     *          return text;<br>
     *      }<br>
     * }
     * </code>
     * 
     * @param actions list of provided choices
     * @param duration stage duration
     */
    SelectionStage(ArrayList<Action> actions, short duration)
    {
        super(duration);
        this.actions = actions;
    }
    
    void addAction(Action action)
    {
        actions.add(action);
    }
    
    void runAction(GameFrame ui)
    {
        Action selection = (Action)ui.listChoice.getSelectedValue();
        StageEngine.rotatePlot(selection.getActionStage());
    }
    
    @Override
    void run(GameFrame ui)
    {
        super.run(ui);
        runAction(ui);
    }
}