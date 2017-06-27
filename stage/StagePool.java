package omo.stage;

import static omo.Bladder.*;
import omo.NarrativeEngine;
import static omo.NarrativeEngine.*;
import omo.ui.GameFrame;

public class StagePool
{

    BladderAffectingStage leaveBed;
    BladderAffectingStage leaveHome;
    BladderAffectingStage arrivedToClass;
    BladderAffectingStage walkIn;
    BladderAffectingStage sitDown;
    HoldingStage schoolHolding;

    public StagePool()
    {
        leaveBed = new BladderAffectingStage(leaveHome, (short) 3)
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

        leaveHome = new BladderAffectingStage(arrivedToClass, (short) 10, new String[]
        {
            "Just looking at the clock again in disbelief adds a redder tint to your cheeks.",
            "",
            "Paying much less attention to your daily routine, you quickly run down the stairs, get a small glass of orange juice and chug it.",
            "",
            "The cold drink brings a chill down your spine as you collect your things and rush out the door to school."
        })
        {
            @Override
            public void operate(GameFrame ui)
            {
                offsetEmbarassment(ui, 3);
                offsetBelly(ui, 10);
            }
        };

        arrivedToClass = new BladderAffectingStage(walkIn, (short) 1)
        {
            @Override
            public String[] getText()
            {
                if (!getLower().isMissing())
                {
                    //Skirt blowing in the wind
                    if (getLower().insert().equals("skirt"))
                    {
                        return new String[]
                        {
                            "You rush into class, your " + getLower().insert() + " blowing in the wind.",
                            "",
                            "Normally, you'd be worried your " + getUndies().insert() + " would be seen, but you can't worry about it right now.",
                            "You make it to your seat without a minute to spare."
                        };
                    } else
                    {
                        //Nothing is blowing in wind
                        return new String[]
                        {
                            "Trying your best to make up lost time, you rush into class and sit down to your seat without a minute to spare."
                        };
                    }
                } else
                {
                    if (!getUndies().isMissing())
                    {
                        return new String[]
                        {
                            "You rush into class; your classmates are looking at your " + getUndies().insert() + ".",
                            "You can't understand how you forgot to even put on any lower clothing,",
                            "and you know that your " + getUndies().insert() + " have definitely been seen.",
                            "You make it to your seat without a minute to spare."
                        };
                    } else
                    {
                        if (isFemale())
                        {
                            return new String[]
                            {
                                "You rush into class; your classmates are looking at your pussy and boobs.",
                                "Guys are going mad and doing nothing except looking at you.",
                                "You can't understand how you dared to come to school naked.",
                                "You make it to your seat without a minute to spare."
                            };
                        } else
                        {
                            return new String[]
                            {
                                "You rush into class; your classmates are looking at your penis.",
                                "Girls are really going mad and doing nothing except looking at you.",
                                "You can't understand how you dared to come to school naked.",
                                "You make it to your seat without a minute to spare."
                            };
                        }
                    }
                }
            }

            @Override
            public void operate(GameFrame ui)
            {
                offsetEmbarassment(ui, 2);
            }
        };

        walkIn = new BladderAffectingStage(sitDown,(short) 1)
        {
            public String[] getText()
            {
                //If lower clothes is a skirt
                if (revealingLower())
                {
                    setLinesAsDialogue(1, 3);
                    return new String[]
                    {
                        "Next time you run into class, " + name + ",", "your teacher says,",
                        "make sure you're wearing something less... revealing!",
                        "A chuckle passes over the classroom, and you can't help but feel a",
                        "tad bit embarrassed about your rush into class."
                    };
                } else //No outerwear
                {
                    if (getLower().isMissing())
                    {
                        setLinesAsDialogue(1);
                        return new String[]
                        {
                            "WHAT!? YOU CAME TO SCHOOL NAKED!?",
                            "your teacher shouts in disbelief.",
                            "",
                            "A chuckle passes over the classroom, and you can't help but feel extremely embarrassed",
                            "about your rush into class, let alone your nudity."
                        };
                        offsetEmbarassment(ui, 25);
                    } else
                    {
                        setLinesAsDialogue(1, 3);
                        return new String[]
                        {
                            "Sit down, " + name + ". You're running late.",
                            "your teacher says,",
                            "And next time, don't make so much noise entering the classroom!",
                            "A chuckle passes over the classroom, and you can't help but feel a tad bit embarrassed",
                            "about your rush into class."
                        };
                    }
                }
                return new String[0];
            }
            
            public void operate(GameFrame ui)
            {
                if(revealingLower())
                {
                    offsetEmbarassment(ui, 5);
                }
                else
                {
                    if (getLower().isMissing())
                    {
                        offsetEmbarassment(ui, 25);
                    }
                }
            }
        };
        
        sitDown = new BladderAffectingStage(schoolHolding, 1)
        {
            public String[] getText()
            {
                return getBladderDependingText(
            }
        };
    }
}
