package omo.stage;

import static omo.Bladder.*;
import static omo.NarrativeEngine.*;

/**
 *
 * @author JavaBird
 */
public class GiveUpStage extends Stage
{
    @Override
    public String[] getText()
    {
        return getWearDependentText(new String[]
        {
            "You get tired of holding all the urine in your aching bladder,",
            "and you decide to give up and pee in your " + getUndies().insert() + "."
        },
        new String[]
        {
            "You get tired of holding all the urine in your aching bladder,",
            "and you decided to pee in your " + getLower().insert() + "."
        },
        new String[]
        {
            "You get tired of holding all the urine in your aching bladder,",
            "and you decide to give up and pee in your " + getUndies().insert() + "."
        },
        new String[]
        {
            "You get tired of holding all the urine in your aching bladder,",
            "and you decide to give up and pee where you are."
        });
    }
    
    @Override
    void operate()
    {
        rottePlot(StagePool.wetting);
    }
}