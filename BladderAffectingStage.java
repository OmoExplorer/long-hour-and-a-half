package omo;

class BladderAffectingStage extends Stage
{
    private short duration;

    BladderAffectingStage(Stage nextStage, short duration, String[] text)
    {
        super(nextStage, text);
        this.duration = duration;
    }

    BladderAffectingStage(short duration, String[] text)
    {
        super(text);
        this.duration = duration;
    }

    BladderAffectingStage(Stage nextStage, short duration)
    {
        super(nextStage);
        this.duration = duration;
    }

    @Override
    void operate(UI ui)
    {
        super.operate();
        Bladder.passTime(ui, duration);
    }

}