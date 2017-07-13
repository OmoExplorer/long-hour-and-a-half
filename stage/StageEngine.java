package omo.stage;

import static omo.GameCore.*;
import omo.ui.GameFrame;

/**
 * Handles scenes.
 * @author JavaBird
 */
public class StageEngine
{
    private static Stage previousStage;
    private static Stage currentStage;
    private static Stage firstStage;
    private static Stage nextStage;
    
    /**
     * Sets a specified stage as a story beginning point.
     * Resets current story flow, so <i>don't call it when story is already running</i>.
     * 
     * @param firstStage the story beginning stage to set
     */
    public static void setFirstStage(Stage firstStage)
    {
        previousStage = null;
        StageEngine.firstStage = firstStage;
//        Workaround
//        currentStage = firstStage;
        currentStage = STAGE_POOL.leaveBed;
        
//        Workaround
//        nextStage = firstStage.getNextStage();
        nextStage = STAGE_POOL.leaveHome;
    }
    
    /**
     * @return the current stage
     */
    public static Stage getCurrentStage()
    {
        return currentStage;
    }
    
    /**
     * Immediately runs a next stage.
     * @param ui 
     */
    public static void runNextStage(GameFrame ui)
    {
        nextStage.run(ui);
        previousStage = currentStage;
        currentStage = nextStage;
        nextStage = nextStage.getNextStage();
    }
    
    public static void runPreviousStage(GameFrame ui)
    {
        getPreviousStage().run(ui);
        nextStage = currentStage;
        currentStage = getPreviousStage();
        previousStage = null;
    }
    
    /**
     * Changes the next stage.
     * Doesn't advances to a next stage.
     * @param nextStage next stage to set
     */
    public static void rotatePlot(Stage nextStage)
    {
        StageEngine.setNextStage(nextStage);
    }

    /**
     * @return the nextStage
     */
    public static Stage getNextStage()
    {
        return nextStage;
    }

    /**
     * @param aNextStage the nextStage to set
     */
    public static void setNextStage(Stage aNextStage)
    {
        nextStage = aNextStage;
    }

    /**
     * @return the previousStage
     */
    public static Stage getPreviousStage()
    {
        return previousStage;
    }
}
