package omo.stage;

import java.util.ArrayList;
import omo.Bladder;
import static omo.Bladder.*;
import omo.NarrativeEngine;
import static omo.NarrativeEngine.*;
import static omo.stage.StageEngine.*;
import omo.ui.GameFrame;

public class StagePool
{
    public static Stage leaveBed;
    public static Stage surpriseAccident;
    public static Stage accident;
    public static Stage drink;
    public static Stage classOver;
    public static Stage caughtHoldingPee;

    Stage leaveHome;
    Stage arrivedToClass;
    Stage walkIn;
    Stage sitDown;
    HoldingStage schoolHolding;
    Stage askTeacherToPee;

    public static Stage surprise;
    public static Stage writeLines;
    private Stage peeingAfterClass;
    private Stage schoolRestroomLine;
    private Stage win;
    private Stage askedByTeacher;

    private Stage surpriseBeginning;
    private Stage surpriseBroughtToRestroom;
    private Stage surpriseOnWindowsill;
    private SelectionStage surpriseAction;
    private Stage surprisePersuade;
    private Stage surpriseHit;
    private final Stage surpriseWetPressure;
    private Stage wetting;
    private Stage humiliation;

    public StagePool()
    {
        //Leaving bed
        leaveBed = new Stage(leaveHome, (short) 3)
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

        //Leaving home
        leaveHome = new Stage(arrivedToClass, new String[]
        {
            "Just looking at the clock again in disbelief adds a redder tint to your cheeks.",
            "",
            "Paying much less attention to your daily routine, you quickly run down the stairs, get a small glass of orange juice and chug it.",
            "",
            "The cold drink brings a chill down your spine as you collect your things and rush out the door to school."
        }, (short) 10)
        {
            @Override
            public void operate(GameFrame ui)
            {
                offsetEmbarassment(ui, 3);
                offsetBelly(ui, 10);
            }
        };

        //Arrived to class
        arrivedToClass = new Stage(walkIn, (short) 1)
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

        //Walking in the class
        walkIn = new Stage((short) 1)
        {
            @Override
            public String[] getText()
            {
                //If lower clothes is a skirt
                if (isLowerRevealing())
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
                if (isLowerRevealing())
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
                StageEngine.rotatePlot(fulnessBetween((short) 0, (short) 60) ? sitDown : schoolHolding);
            }
        };

        //Sitting down
        sitDown = new Stage(schoolHolding, new String[]
        {
            "Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of",
            "your bladder filling as the liquids you drank earlier start to make their way down."
        }, (short) 1)
        {
            @Override
            public void operate()
            {
                score("Embarassment at start - " + getIncontinence() + " pts", '+', getEmbarassment());
            }
        };

        ArrayList<Action> customSchoolHoldingActions = new ArrayList<>();
        customSchoolHoldingActions.add(
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
        });

        //Holding in class
        schoolHolding = new HoldingStage(customSchoolHoldingActions, (short) 3)
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

            @Override
            void operate(GameFrame ui)
            {
                caughtByClassmates(ui);
                askedByTeacher(ui);
                checkClassOver(ui);
            }
        };

        //Asked the teacher to pee
        askTeacherToPee = new Stage()
        {
            private boolean success = NarrativeEngine.chance((byte) (40 / getTimesPeeDenied() + 1));
            private boolean punishmentType = RANDOM.nextBoolean();

            @Override
            public String[] getText()
            {
                if (success)
                {
                    return getWearDependentText(
                            new String[]
                            {
                                "You ask the teacher if you can go out to the restroom.",
                                "Yes, you may.",
                                "says the teacher.",
                                "You run to the restroom.",
                                "You enter it, pulled down your " + getLower().insert() + " and " + getUndies().insert() + ",",
                                "wearily flop down on the toilet and start peeing."
                            },
                            new String[]
                            {
                                "You ask the teacher if you can go out to the restroom.",
                                "Yes, you may.",
                                "says the teacher.",
                                "You run to the restroom.",
                                "You enter it, pulled down your " + getLower().insert() + ",",
                                "wearily flop down on the toilet and start peeing."
                            },
                            new String[]
                            {
                                "You ask the teacher if you can go out to the restroom.",
                                "Yes, you may.",
                                "says the teacher.",
                                "You run to the restroom.",
                                "You enter it, pulled down your " + getUndies().insert() + ",",
                                "wearily flop down on the toilet and start peeing."
                            },
                            new String[]
                            {
                                "You ask the teacher if you can go out to the restroom.",
                                "Yes, you may.",
                                "says the teacher.",
                                "You run to the restroom.",
                                "You enter it,",
                                "wearily flop down on the toilet and start peeing."
                            });
                }
                else
                {
                    switch (getTimesPeeDenied())
                    {
                        case 0:
                            return new String[]
                            {
                                "You ask the teacher if you can go out to the restroom.",
                                "No, you can't go out, the director prohibited it.",
                                "says the teacher."
                            };
                        case 1:
                            return new String[]
                            {
                                "You ask the teacher again if you can go out to the restroom.",
                                "No, you can't! I already told you that the director prohibited it!",
                                "says the teacher."
                            };
                        case 2:
                            return new String[]
                            {
                                "You ask the teacher once more if you can go out to the restroom.",
                                "No, you can't! Stop asking me or there will be consequences!",
                                "says the teacher."
                            };
                        case 3:
                            if (punishmentType)
                            {
                                return new String[]
                                {
                                    "Desperately, you ask the teacher if you can go out to the restroom.",
                                    "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,",
                                    "yells the teacher."
                                };
                            }
                            else
                            {
                                return new String[]
                                {
                                    "Desperately, you ask the teacher if you can go out to the restroom.",
                                    "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! YOU WILL WRITE LINES AFTER THE LESSON!!!,",
                                    "yells the teacher."
                                };
                            }
                    }
                    return null;
                }
            }

            @Override
            void operate()
            {
                success = NarrativeEngine.chance((byte) (40 / getTimesPeeDenied()));
                punishmentType = RANDOM.nextBoolean();
            }
        };

        //Asked by the teacher
        askedByTeacher = new Stage(schoolHolding, new String[]
        {
            NarrativeEngine.getName() + ", why don't you come up to the board and solve this problem?,",
            "suddenly asks you the teacher. Of course, you don't have a clue how to solve it.",
            "You make your way to the front of the room and act lost, knowing you'll be stuck",
            "up there for a while as the teacher explains it.",
            "Well, you can't dare to hold yourself now..."
        }, (short) 3)
        {
            @Override
            void operate()
            {
                score("Called on the lesson", '+', 5);
            }
        };

        //Caught by classmates
        caughtHoldingPee = new Stage(schoolHolding, (short) 1)
        {
            @Override
            public String[] getText()
            {
                switch (NarrativeEngine.timesCaught)
                {
                    case 0:
                        return new String[]
                        {
                            "It looks like a classmate has spotted that you've got to go badly.",
                            "Damn, he may spread that fact..."
                        };
                    case 1:
                        return new String[]
                        {
                            "You'he heard a suspicious whisper behind you.",
                            "Listening to the whisper, you've found out that they're saying that you need to go.",
                            "If I hold it until the lesson ends, I will beat them."
                        };
                    case 2:
                        if (isFemale())
                        {
                            setLinesAsDialogue(2);
                            return new String[]
                            {
                                "The most handsome boy in your class, " + NarrativeEngine.boyName + ", is whispering to you:",
                                "Hey there, don't wet yourself!",
                                "Oh no, he knows it..."
                            };
                        }
                        else
                        {
                            return new String[]
                            {
                                "The most nasty boy in your class, " + boyName + ", is calling you:",
                                "Hey there, don't wet yourself! Ahahahaa!",
                                "Shut up...",
                                "you think to yourself."
                            };
                        }
                    default:
                        return new String[]
                        {
                            "The chuckles are continiously passing over the classroom.",
                            "Everyone is watching you.",
                            "Oh god... this is so embarassing..."
                        };
                }
            }

            @Override
            void operate(GameFrame ui)
            {
                switch (timesCaught)
                {
                    case 0:
                        offsetEmbarassment(ui, 3);
                        score("Caught holding pee", '+', 3);
                        break;
                    case 1:
                        offsetEmbarassment(ui, 8);
                        score("Caught holding pee", '+', 8);
                        break;
                    case 2:
                        offsetEmbarassment(ui, 12);
                        score("Caught holding pee", '+', 12);
                        break;
                    default:
                        offsetEmbarassment(ui, 20);
                        score("Caught holding pee", '+', 20);
                }
                classmatesAwareness += 5;
                timesCaught++;
            }
        };

        //Class is over
        classOver = new Stage(new String[]
        {
            "Lesson is finally over, and you're running to the restroom as fast as you can."
        }, (short) 2)
        {
            @Override
            void operate()
            {
                if (RANDOM.nextBoolean())
                {
                    rotatePlot(peeingAfterClass);
                }
                else
                {
                    rotatePlot(schoolRestroomLine);
                }
                if (isHardcore() & chance((byte) 10))
                {
                    rotatePlot(surpriseBeginning);
                }
            }
        };

        //Peeing after the class
        peeingAfterClass = new Stage(win)
        {
            @Override
            public String[] getText()
            {
                return getWearDependentText(new String[]
                {
                    "Thank god, one cabin is free!",
                    "You enter it, pulled down your " + getLower().insert() + " and " + getUndies().insert() + ",",
                    "wearily flop down on the toilet and start peeing."
                }, new String[]
                {
                    "Thank god, one cabin is free!",
                    "You enter it, pulled down your " + getLower().insert() + ",",
                    "wearily flop down on the toilet and start peeing."
                }, new String[]
                {
                    "Thank god, one cabin is free!",
                    "You enter it, pulled down your " + getUndies().insert() + ",",
                    "wearily flop down on the toilet and start peeing."
                }, new String[]
                {
                    "Thank god, one cabin is free!",
                    "You enter it,",
                    "wearily flop down on the toilet and start peeing."
                });
            }

            @Override
            void operate(GameFrame ui)
            {
                Bladder.empty(ui);
            }
        };

        //Waiting for a free restroom cabin after the class
        schoolRestroomLine = new Stage(new String[]
        {
            "No, please... All cabins are occupied, and there's a line. You have to wait!"
        }, (short) 3)
        {
            @Override
            void operate()
            {
                rotatePlot(RANDOM.nextBoolean() ? schoolRestroomLine : peeingAfterClass);
            }
        };

        //Win
        win = new Stage(new String[]
        {
            "Congratulations! You won!"
        })
        {
            @Override
            void operate(GameFrame ui)
            {
                ui.gameOver();
            }
        };

        accident = new Stage(wetting, new String[]
        {
            "You can't help it.. No matter how much pressure you use, the leaks won't stop.",
            "Despite all this, you try your best, but suddenly you're forced to stop.",
            "You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere.",
            "But now... a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways."
        });

        wetting = new Stage(humiliation)
        {
            @Override
            public String[] getText()
            {
                if (!getLower().isMissing())
                {
                    if (!getUndies().isMissing())
                    {
                        return new String[]
                        {
                            "Before you can move an inch, pee quickly soaks through your " + getUndies().insert() + ",",
                            "floods your " + getUndies().insert() + ", and streaks down your legs.",
                            "A large puddle quickly forms, and you can't stop tears from falling down your cheeks."
                        };
                    }
                    else
                    {
                        return new String[]
                        {
                            "Before you can move an inch, pee quickly darkens your " + getLower().insert() + " and streaks down your legs.",
                            "A large puddle quickly forms, and you can't stop tears from falling down your cheeks."
                        };
                    }
                }
                else
                {
                    if (!getUndies().isMissing())
                    {
                        return new String[]
                        {
                            "Before you can move an inch, pee quickly soaks through your " + getUndies().insert() + ", and streaks down your legs.",
                            "A large puddle quickly forms, and you can't stop tears from falling down your cheeks."
                        };
                    }
                    else
                    {
                        if (!isCornered())
                        {
                            return new String[]
                            {
                                "The heavy pee jets are hitting the seat and loudly leaking out from your " + getUndies().insert() + ".",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks."
                            };
                        }
                        else
                        {
                            return new String[]
                            {
                                "The heavy pee jets are hitting the floor and loudly leaking out from your " + getUndies().insert() + ".",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks."
                            };
                        }
                    }
                }
            }
        };

        humiliation = new Stage(fail)
        {
            public String[] getText()
            {
                if (!stay)
                {
                    if (getLower().isMissing())
                    {
                        if (isFemale() && getUndies().isMissing())
                        {
                            setText("People around you are laughing loudly.", name + " peed herself! Ahaha!!!");
                        }
                        else
                        {
                            if (isMale() && undies.isMissing())
                            {
                                setText("People around you are laughing loudly.", name + " peed himself! Ahaha!!!");
                            }
                            else
                            {
                                setText("People around you are laughing loudly.", name + " wet h" + (isFemale() ? "er " : "is ") + undies.insert() + "! Ahaha!!");
                            }
                        }
                    }
                    else
                    {
                        if (isFemale())
                        {
                            setText("People around you are laughing loudly.", name + " peed her " + lower.insert() + "! Ahaha!!");
                        }
                        else
                        {
                            setText("People around you are laughing loudly.", " peed his " + lower.insert() + "! Ahaha!!");
                        }
                    }
                }
                else
                {
                    setText("Teacher is laughing loudly.", "Oh, you peed yourself? This is a great punishment.", "I hope you will no longer get in the way of the lesson.");
                }
            }
        } //***********SPECIAL HARDCORE EXTENSION***********
        surpriseBeginning = new Stage(surpriseBroughtToRestroom, new String[]
        {
            "The lesson is finally over, and you're running to the restroom as fast as you can.",
            "But... You see " + boyName + " staying in front of the restroom.",
            "Suddenly, he takes you, not letting you to escape."
        }, (short) 1)
        {
//            timesPeeDenied = 0;
//    specialHardcoreStage = true;
//    score("Got the \"surprise\" by " + boyName, '+', 70);
//    setText("The lesson is finally over, and you're running to the restroom as fast as you can.", "But... You see " + boyName + " staying in front of the restroom.", "Suddenly, he takes you, not letting you to escape.");
//    offsetEmbarassment(10, );
        };

        surpriseBroughtToRestroom = new Stage(surpriseOnWindowsill, new String[]
        {
            "What do you want from me?!",
            "He has brought you in the restroom and quickly put you on the windowsill.",
            boyName + " has locked the restroom door (seems he has stolen the key), then he puts his palm on your belly and says:",
            "I want you to wet yourself."
        }, (short) 1);

        surpriseOnWindowsill = new Stage(surpriseAction, new String[]
        {
            "No, please, don't do it, no...",
            "I want to see you wet...",
            "He slightly presses your belly again, you shook from the terrible pain",
            "in your bladder and subconsciously rubbed your crotch. You have to do something!"
        }, (short) 1);

        ArrayList<Action> surpriseActions = new ArrayList<>();
        surpriseActions.add(new Action("Persuade " + boyName + " to pee", surprisePersuade));
        surpriseActions.add(new Action("Hit him", surpriseHit));
        surpriseAction = new SelectionStage(surpriseActions, (short) 1,
                "No, please, don't do it, no...",
                "I want to see you wet...",
                "He slightly presses your belly again, you shook from the terrible pain",
                "in your bladder and subconsciously rubbed your crotch. You have to do something!"
        );

        surpriseHit = new Stage((short) 1)
        {
            private final boolean success = chance((byte) 20);

            @Override
            public String[] getText()
            {
                if (success)
                {
                    return getWearDependentText(new String[]
                    {
                        "You hit " + boyName + "'s groin.",
                        "Ouch!.. You, little bitch...",
                        "Then he left the restroom quickly.",
                        "You got off from the windowsill while holding your crotch,",
                        "opened the cabin door, entered it, pulled down your " + getLower().insert() + " and " + getUndies().insert() + ",",
                        "wearily flop down on the toilet and start peeing."
                    }, new String[]
                    {
                        "You hit " + boyName + "'s groin.",
                        "Ouch!.. You, little bitch...",
                        "Then he left the restroom quickly.",
                        "You got off from the windowsill while holding your crotch,",
                        "opened the cabin door, entered it, pulled down your " + getLower().insert() + ",",
                        "wearily flop down on the toilet and start peeing."
                    }, new String[]
                    {
                        "You hit " + boyName + "'s groin.",
                        "Ouch!.. You, little bitch...",
                        "Then he left the restroom quickly.",
                        "You got off from the windowsill while holding your crotch,",
                        "opened the cabin door, entered it, pulled down your " + getUndies().insert() + ",",
                        "wearily flop down on the toilet and start peeing."
                    }, new String[]
                    {
                        "You hit " + boyName + "'s groin.",
                        "Ouch!.. You, little bitch...",
                        "Then he left the restroom quickly.",
                        "You get off from the windowsill while holding your crotch,",
                        "open the cabin, enter it,",
                        "wearily flop down on the toilet and start peeing."
                    });
                }
                else
                {
                    return new String[]
                    {
                        "You hit " + boyName + "'s hand. Damn, you'd meant to hit his groin...",
                        "You're braver than I expected;",
                        "now let's check the strength of your bladder!",
                        boyName + " presses your bladder violently..."
                    };
                }
            }

            @Override
            void operate()
            {
                if (success)
                {
                    rotatePlot(win);
                }
                else
                {
                    rotatePlot(surpriseWetPressure);
                }
            }
        };

        surprisePersuade = new Stage((short) 1)
        {
            private boolean success = chance((byte) (15 + getTimesPeeDenied() + 1));

            public String[] getText()
            {
                if (success)
                {
                    return getWearDependentText(new String[]
                    {
                        "Ok, you may, but you'll let me watch you pee.",
                        "states " + boyName + ". You enter the cabin,",
                        "pull down your " + getLower().insert() + " and " + getUndies().insert() + ",",
                        "stand over the toilet and start peeing under " + boyName + "'s spectation."
                    }, new String[]
                    {
                        "Ok, you may, but you'll let me watch you pee.",
                        "states " + boyName + ". You enter the cabin,",
                        "pull down your " + getLower().insert() + ",",
                        "stand over the toilet and start peeing under " + boyName + "'s spectation."
                    }, new String[]
                    {
                        "Ok, you may, but you'll let me watch you pee.",
                        "states " + boyName + ". You enter the cabin,",
                        "pull down your " + getUndies().insert() + ",",
                        "stand over the toilet and start peeing under " + boyName + "'s spectation."
                    }, new String[]
                    {
                        "Ok, you may, but you'll let me watch you pee.",
                        "states " + boyName + ". You enter the cabin,",
                        "stand over the toilet and start peeing under " + boyName + "'s spectation."
                    });
                }
                else
                {
                    switch (getTimesPeeDenied())
                    {
                        case 0:
                            return new String[]
                            {
                                "You ask " + boyName + " if you can pee.",
                                "No, you can't pee in a cabin. I want you to wet yourself.,",
                                boyName + " says."
                            };
                        case 1:
                            return new String[]
                            {
                                "You ask " + boyName + " if you can pee again.",
                                "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.",
                                boyName + " demands."
                            };
                        case 2:
                            return new String[]
                            {
                                "You ask " + boyName + " if you can pee again desperately.",
                                "No, you can't pee in a cabin. You will wet yourself right now,",
                                boyName + " demands.",
                                "Then " + boyName + " pressed your bladder..."
                            };
                        default:
                            return null;
                    }
                }
            }

            @Override
            void operate()
            {
                if (success)
                {
                    rotatePlot(win);
                }
                else
                {
                    rotatePlot(surpriseWetPressure);
                }
            }
        };

        surpriseWetPressure = new Stage(fail)
        {
            @Override
            public String[] getText()
            {
                return getWearDependentText(new String[]
                {
                    "Ouch... The sudden pain flash passes through your bladder...",
                    "You try to hold the pee back, but you just can't.",
                    "You feel the warm pee stream",
                    "filling your " + getUndies().insert() + " and darkening your " + getLower().insert() + ".",
                    "You close your eyes and ease your sphincter off.",
                    "You feel the pee stream become much stronger."
                },
                        new String[]
                        {
                            "Ouch... The sudden pain flash passes through your bladder...",
                            "You try to hold the pee back, but you just can't.",
                            "You feel the warm pee stream",
                            "filling your " + getUndies().insert() + ".",
                            "You close your eyes and ease your sphincter off.",
                            "You feel the pee stream become much stronger."
                        },
                        new String[]
                        {
                            "Ouch... The sudden pain flash passes through your bladder...",
                            "You try to hold the pee back, but you just can't.",
                            "You feel the warm pee stream",
                            "filling your " + getLower().insert() + ".",
                            "You close your eyes and ease your sphincter off.",
                            "You feel the pee stream become much stronger."
                        }, new String[]
                        {
                            "Ouch... The sudden pain flash passes through your bladder...",
                            "You try to hold the pee back, but you just can't.",
                            "You feel the warm pee stream",
                            "running down your legs.",
                            "You close your eyes and ease your sphincter off.",
                            "You feel the pee stream become much stronger."
                        });
            }
        };
    }

    private void askedByTeacher(GameFrame ui)
    {
        if (chance((byte) 5))
        {
            StageEngine.rotatePlot(askedByTeacher);
            StageEngine.runNextStage(ui);
        }
    }

    public static void caughtByClassmates(GameFrame ui)
    {
        if (chance((byte) (15 + classmatesAwareness)) & isHardcore())
        {
            StageEngine.rotatePlot(caughtHoldingPee);
            StageEngine.runNextStage(ui);
        }
    }

    void checkClassOver(GameFrame ui)
    {
        if (getTime() >= 90)
        {
            rotatePlot(classOver);
            runNextStage(ui);
        }
    }
}
