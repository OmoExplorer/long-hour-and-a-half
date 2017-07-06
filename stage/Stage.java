package omo.stage;

import static omo.Bladder.passTime;
import omo.ui.GameFrame;
import static omo.ui.GameFrame.MAX_LINES;

/**
 * The basic game slide.
 */
public class Stage
{
    private String[] text = new String[MAX_LINES];
    private final short duration;
    private Stage nextStage;

    Stage(Stage nextStage, String[] text, short duration)
    {
        this.nextStage = nextStage;
        this.text = text;
        this.duration = duration;
    }

    Stage(String[] text, short duration)
    {
        this.text = text;
        this.duration = duration;
    }

    Stage(Stage nextStage, short duration)
    {
        this.nextStage = nextStage;
        this.duration = duration;
    }

    public Stage()
    {
        this.duration = 0;
    }
    
    public Stage(short duration)
    {
        this.duration = duration;
    }
    
    Stage(Stage nextStage, String[] text)
    {
        this.nextStage = nextStage;
        this.text = text;
        this.duration = 0;
    }

    Stage(String[] text)
    {
        this.text = text;
        this.duration = 0;
    }

    Stage(Stage nextStage)
    {
        this.nextStage = nextStage;
        this.duration = 0;
    }

    void operate()
    {
        
    }
    
    void operate(GameFrame ui)
    {
        
    }
    
    void run(GameFrame ui)
    {
        if(duration != 0)
        {
            passTime(ui, duration);
        }
        ui.setText(getText());
        operate(ui);
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

    /**
     * @return the nextStage
     */
    public Stage getNextStage()
    {
        return nextStage;
    }

    /**
     * @param nextStage the nextStage to set
     */
    public void setNextStage(Stage nextStage)
    {
        this.nextStage = nextStage;
    }
}