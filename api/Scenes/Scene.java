/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.Scenes;

import api.Action;
import api.Operation;
import java.util.ArrayList;

/**
 *
 * @author Jonisan
 */
public abstract class Scene
{

    /**
     * @return the sceneTitle
     */
    public String getSceneTitle()
    {
        return sceneTitle;
    }

    /**
     * @param sceneTitle the sceneTitle to set
     */
    public void setSceneTitle(String sceneTitle)
    {
        this.sceneTitle = sceneTitle;
    }

    /**
     * @return the sceneText
     */
    public String getSceneText()
    {
        return sceneText;
    }

    /**
     * @param sceneText the sceneText to set
     */
    public void setSceneText(String sceneText)
    {
        this.sceneText = sceneText;
    }

    /**
     * @return the watchedCharacters
     */
    public ArrayList<Character> getWatchedCharacters()
    {
        return watchedCharacters;
    }

    /**
     * @param watchedCharacters the watchedCharacters to set
     */
    public void setWatchedCharacters(ArrayList<Character> watchedCharacters)
    {
        this.watchedCharacters = watchedCharacters;
    }

    /**
     * @return the actionList
     */
    public ArrayList<Action> getActionList()
    {
        return actionList;
    }

    /**
     * @param actionList the actionList to set
     */
    public void setActionList(ArrayList<Action> actionList)
    {
        this.actionList = actionList;
    }

    /**
     * @return the operationList
     */
    public ArrayList<Operation> getOperationList()
    {
        return operationList;
    }

    /**
     * @param operationList the operationList to set
     */
    public void setOperationList(ArrayList<Operation> operationList)
    {
        this.operationList = operationList;
    }

    /**
     * @return the nextScene
     */
    public Scene getNextScene()
    {
        return nextScene;
    }

    /**
     * @param nextScene the nextScene to set
     */
    public void setNextScene(Scene nextScene)
    {
        this.nextScene = nextScene;
    }
    private String sceneTitle;
    private String sceneText;
    private ArrayList<Character> watchedCharacters;
    private ArrayList<Action> actionList;
    private ArrayList<Operation> operationList;
    private Scene nextScene;
}
