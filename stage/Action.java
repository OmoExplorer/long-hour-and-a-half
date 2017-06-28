package omo.stage;

import omo.stage.Stage;

/**
 * Action variant used in selection stages.
 *
 * @author JavaBird
 */
public class Action
{
	/**
	 * Action name, for example "Hold crotch".
	 */
    private String name;
    
    /**
     * Stage which plays when an action was selected.
     */
    private Stage actionStage;

    /**
     * Constructor for actions which have a constant assigned stage.
     *
     * @param name the action name
     * @param actionStage the assigned stage
     */
    Action(String name, Stage actionStage)
    {
        this.name = name;
        this.actionStage = actionStage;
    }

    /**
     * Constructor for actions which have different assigned stages depending on context or current values.
     *
     * @param name the action name
     */
    Action(String name)
    {
        this.name = name;
    }

    public Action()
    {
        
    }

    Action(Stage actionStage)
    {
        this.actionStage = actionStage;
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
}