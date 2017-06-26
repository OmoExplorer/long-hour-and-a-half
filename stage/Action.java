package omo.stage;

import omo.stage.Stage;

public class Action
{
    private String name;
    private Stage actionStage;

    Action(String name, Stage actionStage)
    {
        this.name = name;
        this.actionStage = actionStage;
    }

    Action(String name)
    {
        this.name = name;
    }
}