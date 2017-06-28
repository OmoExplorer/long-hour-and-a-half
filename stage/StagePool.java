package omo.stage;

import java.util.ArrayList;
import static omo.Bladder.*;
import static omo.NarrativeEngine.*;
import omo.ui.GameFrame;

public class StagePool
{

    public static BladderAffectingStage leaveBed;
    public static Stage surpriseAccident;
    public static Stage accident;
    public static Stage drink;
    public static Stage classOver;
    public static Stage caughtHoldingPee;
    public static Stage calledOn;
    
    BladderAffectingStage leaveHome;
    BladderAffectingStage arrivedToClass;
    BladderAffectingStage walkIn;
    BladderAffectingStage sitDown;
    HoldingStage schoolHolding;
    Stage askTeacherToPee;

    public static BladderAffectingStage surprise;
    public static BladderAffectingStage writeLines;
    
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
                    }
                    else
                    {
                        //Nothing is blowing in wind
                        return new String[]
                        {
                            "Trying your best to make up lost time, you rush into class and sit down to your seat without a minute to spare."
                        };
                    }
                }
                else
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
                    }
                    else
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
                        }
                        else
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

        walkIn = new BladderAffectingStage((short) 1)
        {
            @Override
            public String[] getText()
            {
                //If lower clothes is a skirt
                if (revealingLower())
                {
                    setLinesAsDialogue(1, 3);
                    return new String[]
                    {
                        "Next time you run into class, " + getName() + ",", "your teacher says,",
                        "make sure you're wearing something less... revealing!",
                        "A chuckle passes over the classroom, and you can't help but feel a",
                        "tad bit embarrassed about your rush into class."
                    };
                }
                else //No outerwear
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
                    }
                    else
                    {
                        setLinesAsDialogue(1, 3);
                        return new String[]
                        {
                            "Sit down, " + getName() + ". You're running late.",
                            "your teacher says,",
                            "And next time, don't make so much noise entering the classroom!",
                            "A chuckle passes over the classroom, and you can't help but feel a tad bit embarrassed",
                            "about your rush into class."
                        };
                    }
                }
            }

            @Override
            public void operate(GameFrame ui)
            {
                if (revealingLower())
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
                setNextStage(fulnessBetween((short) 0, (short) 60) ? sitDown : schoolHolding);
            }
        };

        sitDown = new BladderAffectingStage(schoolHolding, (short) 1, new String[]
        {
            "Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of",
            "your bladder filling as the liquids you drank earlier start to make their way down."
        })
        {
            @Override
            public void operate()
            {
                score("Embarassment at start - " + getIncontinence() + " pts", '+', getEmbarassment());
            }
        };

        ArrayList<Action> customSchoolHoldingActions = new ArrayList<>();
        customSchoolHoldingActions.add
        (
            new Action(askTeacherToPee)
            {
                @Override
                public String getName()
                {
                    switch (getTimesPeeDenied())
                    {
                        case 0:
                            return "Ask teacher to go pee";
                        case 1:
                            return "Ask the teacher to go pee again";
                        case 2:
                            return "Try to ask the teacher again";
                        case 3:
                            return "Take a chance and ask the teacher (RISKY)";
                        default:
                            return ACTION_UNAVAILABLE;
                    }
                }
            }
        );
        
        schoolHolding = new HoldingStage(customSchoolHoldingActions, (short) 2)
        {
            @Override
            public String[] getText()
            {
                return getBladderDependingText(
                        new String[]
                        {
                            "Feeling bored about the day, and not really caring about the class too much,",
                            "you look to the clock, watching the minutes tick by."
                        },
                        new String[]
                        {
                            "Having to pee a little bit,",
                            "you look to the clock, watching the minutes tick by and wishing the lesson to get over faster."
                        },
                        new String[]
                        {
                            "Clearly having to pee,",
                            "you impatiently wait for the lesson end."
                        },
                        new String[]
                        {
                            "You feel the rather strong pressure in your bladder, and you're starting to get even more desperate.",
                            "Maybe I should ask teacher to go to the restroom? It hurts a bit..."
                        },
                        new String[]
                        {
                            "Keeping all that urine inside will become impossible very soon.",
                            "You feel the terrible pain and pressure in your bladder, and you can almost definitely say you haven't needed to pee this badly in your life.",
                            "Ouch, it hurts a lot... I must do something about it now, or else..."
                        },
                        new String[]
                        {
                            "This is really bad...",
                            "You know that you can't keep it any longer and you may wet yourself in any moment and oh,",
                            "You can clearly see your bladder as it bulging.",
                            "Ahhh... I cant hold it anymore!!!",
                            "Even holding your crotch doesn't seems to help you to keep it in."
                        });
            }
        };
    }
}
