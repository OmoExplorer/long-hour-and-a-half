/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.Scenes;

/**
 * @author JavaBird
 */
public class ActiveScene extends Scene
{
    private int sceneTime;

    /**
     * @return the scene time
     */
    public int getSceneTime()
    {
        return sceneTime;
    }

    /**
     * @param sceneTime the new scene time
     */
    public void setSceneTime(int sceneTime)
    {
        this.sceneTime = sceneTime;
    }
}
