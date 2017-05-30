/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import api.Scenes.Scene;
import java.util.ArrayList;

/**
 * 
 * @author JavaBird
 */
public class Story
{
    private ArrayList<Scene> scenesList;
    private String storyName;
    private ArrayList<Character> charactersList;
    private String title;
    private String description;
    private String author;
    private long creationTimestamp;
    private long editTimestamp;
}
