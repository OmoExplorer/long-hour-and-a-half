package omo.stage;

import omo.ui.GameFrame;
import static omo.ui.GameFrame.MAX_LINES;

public class Stage
{
    private String[] text = new String[MAX_LINES];
    private Stage nextStage;

    Stage(Stage nextStage, String[] text)
    {
        this.nextStage = nextStage;
        this.text = text;
    }

    Stage(String[] text)
    {
        this.text = text;
    }

    Stage(Stage nextStage)
    {
        this.nextStage = nextStage;
    }

    void operate()
    {

    }

    void operate(GameFrame ui)
    {

    }

    void run(GameFrame ui)
    {
        ui.setText(getText());
        operate();
    }

    /**
     * @return the stage text
     */
    public String[] getText()
    {
        return text;
    }

    /**
     * @param text the stage text to set
     */
    public void setText(String... text)
    {
        this.text = text;
    }
}