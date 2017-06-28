package omo.stage;

import omo.ui.GameFrame;

/**
 * Handles scenes.
 * @author JavaBird
 */
public class StageEngine
{
    private static Stage currentStage;
    private static Stage firstStage;
    private static Stage nextStage;
    
    /**
     * Sets a specified satge as a story beginning point.
     * Resets current story flow, so <i>don't call it when story is already running</i>.
     * 
     * @param firstStage the story beginning stage to set
     */
    public static void setFirstStage(Stage firstStage)
    {
        StageEngine.firstStage = firstStage;
        currentStage = firstStage;
        nextStage = firstStage.getNextStage();
    }
    
    /**
     * @return the current stage
     */
    public static Stage getCurrentStage()
    {
        return currentStage;
    }
    
    public static void runNextStage(GameFrame ui)
    {
        nextStage.run(ui);
    }
    
    public static void rotatePlot(Stage nextStage)
    {
        StageEngine.nextStage = nextStage;
    }

}
