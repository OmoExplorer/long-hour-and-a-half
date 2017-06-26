package omo.stage;

import static omo.Bladder.getLower;
import static omo.Bladder.getUndies;
import static omo.NarrativeEngine.*;

public class StagePool
{
    Stage leaveHome;
    Stage leaveBed = new BladderAffectingStage(leaveHome, (short) 3)
    {
        @Override
        public String[] getText()
        {
            setLinesAsDialogue(1);
            return getWearDependentText(new String[]
            {
                "Wh-what? Did I forget to set my alarm?!",
                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                "You hurriedly slip on some " + getUndies().insert() + " and " + getLower().insert() + ",",
                "not even worrying about what covers your chest."
            }, new String[]
            {
                "Wh-what? Did I forget to set my alarm?!",
                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                "You hurriedly slip on some " + getLower().insert() + ", quick to cover your " + getUndies().insert() + ",",
                "not even worrying about what covers your chest."
            }, new String[]
            {
                "Wh-what? Did I forget to set my alarm?!",
                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                "You hurriedly slip on " + getUndies().insert() + ",",
                "not even worrying about what covers your chest and legs."
            }, new String[]
            {
                "Wh-what? Did I forget to set my alarm?!",
                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                "You are running downstairs fully naked."
            });
        }
    };
}
