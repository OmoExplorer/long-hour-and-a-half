package omo;

class Stage
{
    private String[] text = new String[UI.MAX_LINES];
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

    void operate(UI ui)
    {

    }

    void run(UI ui)
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