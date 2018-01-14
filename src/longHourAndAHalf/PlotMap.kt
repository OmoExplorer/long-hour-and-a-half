package longHourAndAHalf

import longHourAndAHalf.Gender.FEMALE
import longHourAndAHalf.Gender.MALE
import longHourAndAHalf.WearCombinationType.*
import java.util.*
import javax.swing.JOptionPane

/**
 * Contains the whole game plot.
 *
 * @property core A game core to operate with.
 */
class PlotMap {
    @Suppress("KDocMissingDocumentation")
    operator fun get(plotStageID: PlotStageID) = map[plotStageID]

    private val wait = action("Just wait", PlotStageID.ASK_ACTION)

    private fun getAskTeacherToPeeAction() = when (core.schoolDay.lesson.timesPeeDenied) {
        0 -> action("Ask the teacher to go pee", PlotStageID.ASK_TO_PEE)
        1 -> action("Ask the teacher to go pee again", PlotStageID.ASK_TO_PEE)
        2 -> action("Try to ask the teacher again", PlotStageID.ASK_TO_PEE)
        3 -> action("Take a chance and ask the teacher (RISKY)", PlotStageID.ASK_TO_PEE)
        else -> null
    }

    private fun getHoldCrotchAction() = if (!core.character.cornered) action(
            when (core.character.gender) {
                FEMALE -> "Press on your crotch"
                MALE -> "Squeeze your dick"
            }
    ) {
        ui.forcedTextChange(Text("You don't think anyone will see you doing it,",
                "so you take your hand and hold yourself down there.",
                "It feels a little better for now."))

        core.character.bladder.sphincterPower += 20
        core.world.time += 3

        getCaughtRandomly(15)
    } else null

    private fun getRubThighsAction() = action("Rub thighs") {
        ui.forcedTextChange(Text("You need to go, and it hurts, but you just",
                "can't bring yourself to risk getting caught with your hand between",
                "your legs. You rub your thighs hard but it doesn't really help."))

        core.character.bladder.sphincterPower += 2
        core.world.time += 3

        getCaughtRandomly(3)
    }

    private fun getGiveUpAction() = if (core.character.bladder.fullness > 100) action("Give up") {
        ui.forcedTextChange(Text("You're absolutely desperate to pee, and you think you'll",
                "end up peeing yourself anyway, so it's probably best to admit",
                "defeat and get rid of the painful ache in your bladder."))
        core.plot.nextStageID = PlotStageID.GIVE_UP
    } else null

    private fun getDrinkAction() = if (core.hardcore) action("Drink") {
        ui.forcedTextChange(Text("Feeling a tad bit thirsty,",
                "You decide to take a small sip of water from your bottle to get rid of it."))
        core.plot.nextStageID = PlotStageID.DRINK
    } else null

    private fun getCheatAction() = action("Cheat (you'll lose your score)") {
        ui.forcedTextChange(Text("You've got to go so bad!",
                "There must be something you can do, right?"))

        //Zeroing points
        core.cheatData.cheatsUsed = true
        core.plot.nextStageID = PlotStageID.ASK_CHEAT
    }

    private val map = mapOf(
            PlotStageID.STARTUP to { PlotStage(nextStageID = PlotStageID.WAKE_UP, duration = 0) },

            PlotStageID.WAKE_UP to {
                PlotStage(
                        Text(
                                "You wake up, yawning gently and rubbing away the resulting tears.",
                                "Mmmm, so tired...",
                                "Your voice is heavy with sleep as you turn to your alarm clock,",
                                "your eyes widening in turn."
                        ),
                        duration = 1,
                        nextStageID = PlotStageID.LEAVE_BED
                )
            },

            PlotStageID.LEAVE_BED to {
                PlotStage(
                        Text(
                                listOf(
                                        "Wh-what? Did I forget to set my alarm?!",
                                        "You cry, tumbling out of bed and feeling an instant jolt from your bladder."
                                ) + when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED -> listOf(
                                            "You hurriedly slip on some ${core.character.undies.insertName} " +
                                                    "and ${core.character.lower.insertName},",
                                            "not even worrying about what covers your chest."
                                    )
                                    OUTERWEAR_ONLY -> listOf(
                                            "You hurriedly slip on some ${core.character.lower.insertName}, " +
                                                    "quick to cover your crotch,",
                                            "not even worrying about what covers your chest."
                                    )
                                    UNDERWEAR_ONLY -> listOf(
                                            "You hurriedly slip on ${core.character.undies.insertName},",
                                            "not even worrying about what covers your chest and legs."
                                    )
                                    NAKED -> listOf("You are running downstairs fully naked.")
                                }
                        ),
                        nextStageID = PlotStageID.LEAVE_HOME,
                        duration = 1
                )
            },

            PlotStageID.LEAVE_HOME to {
                PlotStage(
                        Text(
                                "Just looking at the clock again " +
                                        "in disbelief adds a redder tint to your cheeks.",
                                "",
                                "Paying much less attention to your daily routine, you quickly run down the stairs, ",
                                "get a small glass of orange juice and chug it.",
                                "",
                                "The cold drink brings a chill down your spine " +
                                        "as you collect your things and rush out the door to school."
                        ),
                        {
                            core.character.embarrassment += 3
                            core.character.belly += 10.0
                        },
                        PlotStageID.GO_TO_CLASS,
                        duration = 1
                )
            },

            PlotStageID.GO_TO_CLASS to {
                PlotStage(
                        Text(
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED,
                                    OUTERWEAR_ONLY -> if (core.character.lower.insertName.contains("skirt"))
                                        listOf(
                                                "You rush into class, your ${core.character.lower.insertName}" +
                                                        " is blowing in the wind.",
                                                "Normally, you'd be worried your " +
                                                        "${core.character.undies.insertName} would be seen,",
                                                "but you can't worry about it right now.",
                                                "You make it to your seat without a minute to spare."
                                        )
                                    else
                                        listOf(
                                                "Trying your best to make up lost time, " +
                                                        "you rush into class and sit down to your seat " +
                                                        "without a minute to spare."
                                        )

                                    UNDERWEAR_ONLY -> listOf(
                                            "You rush into class;",
                                            "your classmates are looking at your ${core.character.undies.insertName}.",
                                            "You can't understand how you forgot to even put on any lower clothing,",
                                            "Under the gazes of the whole class,",
                                            "you make it to your seat without a minute to spare."
                                    )

                                    NAKED -> when (core.character.gender) {
                                        FEMALE ->
                                            listOf(
                                                    "You rush into class;",
                                                    "your classmates are looking at your pussy and boobs.",
                                                    "Guys are going mad and doing nothing except looking at you.",
                                                    "You can't understand how you dared to come to school naked.",
                                                    "Under the gazes of the whole class,",
                                                    "you make it to your seat without a minute to spare."
                                            )
                                        MALE ->
                                            listOf(
                                                    "You rush into class;",
                                                    "your classmates are looking at your penis.",
                                                    "Girls are really going mad " +
                                                            "and doing nothing except looking at you.",
                                                    "You can't understand how you dared to come to school naked.",
                                                    "Under the gazes of the whole class,",
                                                    "you make it to your seat without a minute to spare."
                                            )
                                    }
                                }
                        ),
                        {
                            core.character.embarrassment += 2
                        },
                        PlotStageID.WALK_IN
                )
            },

            PlotStageID.WALK_IN to {
                PlotStage(
                        Text(
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED,
                                    OUTERWEAR_ONLY ->
                                        if (core.character.lower.insertName == "skirt"
                                                || core.character.lower.insertName == "skirt and tights"
                                                || core.character.lower.insertName == "shorts")
                                            setOf(
                                                    TextLine("Next time you run into class," +
                                                            " ${core.character.name},", true),
                                                    TextLine("your teacher says,"),
                                                    TextLine("make sure you're wearing something less... " +
                                                            "revealing!"),
                                                    TextLine("A chuckle passes over the classroom, " +
                                                            "and you can't help but feel"),
                                                    TextLine("a tad bit embarrassed about your rush into class.")
                                            )
                                        else
                                            setOf(
                                                    TextLine("Sit down, ${core.character.name}. " +
                                                            "You're running late.",
                                                            true),
                                                    TextLine("your teacher says,"),
                                                    TextLine("And next time, don't make so much noise " +
                                                            "entering the classroom!"),
                                                    TextLine("A chuckle passes over the classroom, " +
                                                            "and you can't help but feel"),
                                                    TextLine("a tad bit embarrassed about your rush into class.")
                                            )
                                    UNDERWEAR_ONLY,
                                    NAKED -> setOf(
                                            TextLine("WHAT!? YOU CAME TO SCHOOL NAKED!?", true),
                                            TextLine("your teacher shouts in disbelief."),
                                            TextLine("A chuckle passes over the classroom, " +
                                                    "and you can't help but feel extremely embarrassed"),
                                            TextLine("about your rush into class, let alone your nudity")
                                    )
                                }
                        ),
                        {
                            core.character.embarrassment += when (core.character.wearCombinationType) {
                                FULLY_CLOTHED, OUTERWEAR_ONLY -> 5
                                UNDERWEAR_ONLY, NAKED -> 25
                            }
                        },
                        PlotStageID.SIT_DOWN
                )
            },

            PlotStageID.SIT_DOWN to {
                PlotStage(
                        Text(
                                "Subconsciously rubbing your thighs together, " +
                                        "you feel the uncomfortable feeling of",
                                "your bladder filling as the liquids you drank earlier start to make their way down."
                        ),
                        nextStageID = PlotStageID.ASK_ACTION,
                        scoreNomination = ScoreNomination(
                                "Embarrassment at start - ${core.character.bladder.incontinence} pts",
                                core.character.embarrassment,
                                ArithmeticAction.ADD
                        )
                )
            },

            PlotStageID.ASK_ACTION to {
                PlotStage(
                        core.character.bladder.conditionText,
                        actions = listOfNotNull(
                                wait,
                                getAskTeacherToPeeAction(),
                                getHoldCrotchAction(),
                                getRubThighsAction(),
                                getGiveUpAction(),
                                getDrinkAction(),
                                getCheatAction()
                        ),
                        nextStageID = PlotStageID.ASK_ACTION
                )
            },

            PlotStageID.ASK_TO_PEE to {
                PlotStage(
                        Text(
                                when (core.schoolDay.lesson.timesPeeDenied) {
                                    0 -> "You ask the teacher if you can go out to the restroom."
                                    1, 2 -> "You ask the teacher again if you can go out to the restroom."
                                    3 -> "Desperately, you ask the teacher if you can go out to the restroom."
                                    else -> throw IllegalStateException("character asked to pee after 3 fails")
                                }
                        ),
                        {
                            core.flags.peeingAllowed = chance(
                                    when (core.schoolDay.lesson.timesPeeDenied) {
                                        0 -> 40
                                        1 -> 10
                                        2 -> 30
                                        3 -> 7
                                        else -> throw IllegalStateException("character asked to pee after 3 fails")
                                    }
                            )

                            if (core.schoolDay.lesson.timesPeeDenied >= 3 && !core.flags.peeingAllowed)
                                core.flags.punishment = if (chance(50)) {
                                    core.character.cornered = true
                                    Punishment.SEND_TO_CORNER
                                } else {
                                    Punishment.WRITE_LINES
                                }
                        },
                        PlotStageID.ASK_TO_PEE_ANSWER, duration = 0
                )
            },

            PlotStageID.ASK_TO_PEE_ANSWER to {
                PlotStage(
                        if (core.flags.peeingAllowed)
                            Text(TextLine("Yes, you may.", true), TextLine("says the teacher."))
                        else
                            when (core.schoolDay.lesson.timesPeeDenied) {
                                0 -> Text(TextLine("No, you can't go out, the director prohibited it.",
                                        true),
                                        TextLine("says the teacher."))
                                1 -> Text(
                                        TextLine(
                                                "No, you can't! " +
                                                        "I already told you that the director prohibited it!",
                                                true
                                        ),
                                        TextLine("says the teacher.")
                                )
                                2 -> Text(
                                        TextLine(
                                                "No, you can't! Stop asking me or there will be consequences!",
                                                true
                                        ),
                                        TextLine("says the teacher.")
                                )
                                3 -> Text(
                                        TextLine(when (core.flags.punishment) {
                                            Punishment.SEND_TO_CORNER ->
                                                "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,"
                                            Punishment.WRITE_LINES ->
                                                "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! " +
                                                        "YOU WILL WRITE LINES AFTER THE LESSON!!!,"
                                        }),
                                        TextLine("yells the teacher.")
                                )
                                else -> throw IllegalStateException("character asked to pee after 3 fails")
                            },
                        { core.schoolDay.lesson.timesPeeDenied++ },
                        if (core.flags.peeingAllowed)
                            PlotStageID.PEE_ON_LESSON
                        else
                            PlotStageID.ASK_ACTION
                )
            },

            PlotStageID.PEE_ON_LESSON to {
                PlotStage(
                        when (core.character.wearCombinationType) {
                            FULLY_CLOTHED -> Text("You reach the restroom,",
                                    "enter it, pull down your ${core.character.lower.insertName} " +
                                            "and ${core.character.undies.insertName},",
                                    "wearily flop down on the toilet and start peeing.")
                            OUTERWEAR_ONLY -> Text("You reach the restroom,",
                                    "enter it, pull down your ${core.character.lower.insertName},",
                                    "wearily flop down on the toilet and start peeing.")
                            UNDERWEAR_ONLY -> Text("You reach the restroom,",
                                    "enter it, pull down your ${core.character.undies.insertName},",
                                    "wearily flop down on the toilet and start peeing.")
                            NAKED -> Text("You reach the restroom,",
                                    "wearily flop down on the toilet and start peeing.")
                        },
                        { core.character.bladder.fullness = 0.0 }, PlotStageID.ASK_ACTION
                )
            },

            PlotStageID.ASK_CHEAT to {
                PlotStage(
                        nextStageID = PlotStageID.ASK_ACTION,
                        actionGroupName = "Select a cheat",
                        actions = listOf(
                                action(if (core.character.cornered) "Go to a corner" else "Leave the corner")
                                {
                                    ui.forcedTextChange(Text(if (core.character.cornered)
                                        "You walk to the front corner of the classroom."
                                    else
                                        "You go back to your table."))
                                    core.character.cornered = !core.character.cornered
                                },
                                action(if (core.schoolDay.lesson.stay) "Stay after class" else "Don't stay after class")
                                {
                                    ui.forcedTextChange(Text(if (core.schoolDay.lesson.stay)
                                        "You decide to stay after class."
                                    else
                                        "You decide to skip staying after class."))
                                    core.schoolDay.lesson.stay = !core.schoolDay.lesson.stay
                                },
                                action("Pee in a bottle")
                                {
                                    ui.forcedTextChange(Text(
                                            "You see something out of the corner of your eye,",
                                            "just within your reach.",
                                            "Luckily for you, you happen to have brought an empty bottle to pee in.",
                                            "As quietly as you can, you put it in position and let go into it.",
                                            "You can't help but show a face of pure relief " +
                                                    "as your pee trickles down into it."))
                                },
                                action("Finish the lesson")
                                {
                                    ui.forcedTextChange(Text("A voice comes over the loudspeaker:",
                                            "All classes are now dismissed for no reason at all! Bye!",
                                            "Looks like your luck changed for the better."))
                                    core.world.time = Lesson.classEndingTime
                                    core.plot.nextStageID = PlotStageID.CLASS_OVER
                                },
                                action("Calm down the teacher")
                                {
                                    ui.forcedTextChange(
                                            Text("The teacher feels sorry for you. Try asking to pee.")
                                    )
                                    core.schoolDay.lesson.timesPeeDenied = 0
                                    core.schoolDay.lesson.stay = false
                                    core.character.cornered = false
                                },
                                action("Raise your hand")
                                {
                                    ui.forcedTextChange(Text("You decide to raise your hand."))
                                    core.plot.nextStageID = PlotStageID.CALLED_ON
                                },
                                action("Empty the bladder every 15 minutes")
                                {
                                    ui.forcedTextChange(Text("Suddenly, you feel like you're peeing...",
                                            "but you don't feel any wetness. It's not something you'd",
                                            "want to question, right?"))
                                    core.cheatData.drain = true
                                },
                                action("Change your incontinence")
                                {
                                    ui.forcedTextChange(
                                            Text("A friend in the desk next to you hands you a familiar",
                                                    "looking pill, and you take it.")
                                    )
                                    core.character.bladder.incontinence =
                                            JOptionPane.showInputDialog("How incontinent are you now?").toDouble()
                                    core.character.bladder.maxSphincterPower =
                                            (100 / core.character.bladder.incontinence).toInt()
                                    core.character.bladder.sphincterPower = core.character.bladder.maxSphincterPower
                                },
                                action("Change the bladder fullness")
                                {
                                    ui.forcedTextChange(
                                            Text("Suddenly you felt something going on in your bladder.")
                                    )
                                    core.character.bladder.incontinence =
                                            JOptionPane.showInputDialog("How your bladder is full now?").toDouble()
                                }
                        )
                )
            },

            PlotStageID.CALLED_ON to {
                PlotStage(
                        Text(
                                TextLine("${core.character.name}, " +
                                        "why don't you come up to the board and solve this problem?,"),
                                TextLine("says the teacher. Of course, you don't have a clue how to solve it."),
                                TextLine("You make your way to the front of the room and act lost, " +
                                        "knowing you'll be stuck"),
                                TextLine("up there for a while as the teacher explains it."),
                                TextLine("Well, you can't dare to hold yourself now...")
                        ),
                        nextStageID = PlotStageID.ASK_ACTION,
                        duration = 5,
                        scoreNomination = ScoreNomination("Called on the lesson", 5,
                                ArithmeticAction.ADD)
                )
            },

            PlotStageID.CLASS_OVER to {
                PlotStage(
                        Text("Lesson is finally over, and you're running to the restroom as fast as you can."),
                        {
                            core.flags.line = Random().nextBoolean()
                            if (core.flags.line) core.plot.nextStageID = PlotStageID.PEE_AFTER_LESSON_LINE
                        },
                        run
                        {
                            if (chance(10) && core.hardcore && core.character.gender == Gender.FEMALE)
                                PlotStageID.SURPRISE
                            if (core.schoolDay.lesson.stay) PlotStageID.AFTER_CLASS
                            PlotStageID.PEE_AFTER_LESSON
                        }, duration = 1
                )
            },

            PlotStageID.PEE_AFTER_LESSON to {
                PlotStage(
                        Text("Thank god, one cabin is free!",
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED -> "You enter it, pull down your ${core.character.lower.insertName} " +
                                            "and ${core.character.undies.insertName},"
                                    OUTERWEAR_ONLY -> "You enter it, pull down your ${core.character.lower.insertName},"
                                    UNDERWEAR_ONLY -> "You enter it, pull down your ${core.character.undies.insertName},"
                                    NAKED -> "you enter it,"
                                }, "wearily flop down on the toilet and start peeing."
                        ),
                        { core.character.bladder.fullness = 0.0 }, PlotStageID.END_GAME
                )
            },

            PlotStageID.PEE_AFTER_LESSON_LINE to {
                PlotStage(
                        Text("Oh no... ",
                                "All cabins are occupied, and there's a line. You have to wait!"),
                        nextStageID = if (chance(50)) PlotStageID.PEE_AFTER_LESSON_LINE
                        else PlotStageID.PEE_AFTER_LESSON
                )
            },

            PlotStageID.AFTER_CLASS to {
                PlotStage(
                        Text(
                                TextLine("Hey, ${core.character.name}, you wanted to escape? " +
                                        "You must stay after classes!", true),
                                TextLine("Please... let me go to the restroom... I can't hold it...", true),
                                TextLine("No, ${core.character.name}, you can't go to the restroom now! " +
                                        "This will be as punishment."),
                                TextLine("And don't think you can hold yourself either! I'm watching you...")
                        ),
                        nextStageID = if (core.world.time < Lesson.classEndingTime + Time(0, 30))
                            PlotStageID.AFTER_CLASS else PlotStageID.CLASS_OVER
                )
            },

            PlotStageID.ACCIDENT to {
                PlotStage(
                        Text("You can't help it.. No matter how much pressure you use, the leaks won't stop.",
                                "Despite all this, you try your best, but suddenly you're forced to stop.",
                                "You can't move, or you risk peeing yourself.",
                                "Heck, the moment you stood up you thought you " +
                                        "could barely move for risk of peeing everywhere.",
                                "But now.. a few seconds tick by as you try to will yourself to move, " +
                                        "but soon, the inevitable happens anyways."),
                        nextStageID = PlotStageID.WET, duration = 0
                )
            },

            PlotStageID.GIVE_UP to {
                PlotStage(
                        Text("You get tired of holding all the urine in your aching bladder,",
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED, UNDERWEAR_ONLY ->
                                        "and you decide to give up and pee in your ${core.character.undies.insertName}."
                                    OUTERWEAR_ONLY ->
                                        "and you decide to give up and pee in your ${core.character.lower.insertName}."
                                    NAKED -> "and you decide to give up and pee where you are."
                                }
                        ),
                        {
                            core.character.embarrassment += 80
                        }, PlotStageID.WET
                )
            },

            PlotStageID.WET to {
                PlotStage(
                        Text(
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED -> listOf("Before you can move an inch, " +
                                            "pee quickly soaks through your " +
                                            core.character.undies.insertName + ",",
                                            "floods your " + core.character.lower.insertName + ", " +
                                                    "and streaks down your legs.")

                                    OUTERWEAR_ONLY -> listOf("Before you can move an inch, pee quickly darkens your " +
                                            core.character.lower.insertName +
                                            " and streaks down your legs.")

                                    UNDERWEAR_ONLY -> listOf("Before you can move an inch, " +
                                            "pee quickly soaks through your " + core.character.undies.insertName +
                                            ", and streaks down your legs.")

                                    NAKED -> if (!core.character.cornered) {
                                        listOf("The heavy pee jets are hitting the seat " +
                                                "and loudly leaking out from your " +
                                                core.character.undies.insertName + ".",
                                                "A large puddle quickly forms, " +
                                                        "and you can't stop tears from falling down your cheeks.")
                                    } else {
                                        listOf("The heavy pee jets are hitting the floor " +
                                                "and loudly leaking out from your ${core.character.undies.insertName}.",
                                                "A large puddle quickly forms, " +
                                                        "and you can't stop tears from falling down your cheeks.")
                                    }
                                }
                        ),
                        nextStageID = PlotStageID.POST_WET, duration = 0
                )
            },

            PlotStageID.POST_WET to {
                PlotStage(
                        if (core.schoolDay.lesson.stay)
                            Text(
                                    TextLine("Teacher is laughing loudly."),
                                    TextLine("Oh, you peed yourself? This is a great punishment.", true),
                                    TextLine("I hope you will no longer get in the way of the lesson.", true)
                            )
                        else
                            Text("People around you are laughing loudly.",
                                    when (core.character.gender) {
                                        FEMALE -> "${core.character.name} peed herself! Ahaha!!!"
                                        MALE -> "${core.character.name} peed himself! Ahaha!!!"
                                    }
                            ),
                        nextStageID = PlotStageID.GAME_OVER, duration = 0
                )
            },

            PlotStageID.GAME_OVER to {
                PlotStage(
                        Text(
                                listOf(
                                        "No matter how hard you tried... " +
                                                "It doesn't seem to matter, even to think about it...",
                                        when (core.character.wearCombinationType) {
                                            FULLY_CLOTHED -> "Your ${core.character.lower.insertName} and " +
                                                    "${core.character.undies.insertName} are both clinging to your skin, " +
                                                    "a sign of your failure..."
                                            OUTERWEAR_ONLY -> "Your ${core.character.lower.insertName} " +
                                                    "clings to your skin, a sign of your failure..."
                                            UNDERWEAR_ONLY -> "Your ${core.character.undies.insertName} " +
                                                    "clings to your skin, a sign of your failure..."
                                            NAKED -> "Your legs are covered in your pee, a sign of your failure..."
                                        }
                                )
                        ), ui::gameFinished, PlotStageID.GAME_OVER, duration = 0
                )
            },

            PlotStageID.END_GAME to {
                PlotStage(
                        operations =
                        {
                            core.scorer.showScoreDialog()
                            ui.gameFinished()
                        }, nextStageID = PlotStageID.END_GAME, duration = 0
                )
            },

            PlotStageID.CAUGHT to {
                PlotStage(
                        when (core.schoolDay.timesCaught) {
                            0 -> Text("It looks like a classmate has spotted that you've got to go badly.",
                                    "Damn, he may spread that fact...")

                            1 -> Text(
                                    TextLine("You'he heard a suspicious whisper behind you."),
                                    TextLine("Listening to the whisper, " +
                                            "you've found out that they're saying that you need to go."),
                                    TextLine("If I hold it until the lesson ends, I will beat them.", true)
                            )

                            2 -> if (core.character.gender == Gender.FEMALE) Text(
                                    TextLine("The most handsome boy in your class, " +
                                            core.schoolDay.surpriseBoy.name + ", is calling you:"),
                                    TextLine("Hey there, don't wet yourself!", true),
                                    TextLine("Oh no, he knows it...")
                            ) else Text(
                                    TextLine("The most nasty boy in your class, " +
                                            core.schoolDay.surpriseBoy.name + ", is calling you:"),
                                    TextLine("Hey there, don't wet yourself! Ahahahaa!", true),
                                    TextLine("\"Shut up...\"", true),
                                    TextLine(", you think to yourself.")
                            )

                            else -> Text(
                                    TextLine("The chuckles are continuously passing over the classroom."),
                                    TextLine("Everyone is watching you."),
                                    TextLine("Oh god... this is so embarrassing...", true)
                            )
                        },
                        {
                            core.character.embarrassment += when (core.schoolDay.timesCaught) {
                                0 -> 3
                                1 -> 8
                                2 -> 12
                                else -> 20
                            }

                            core.schoolDay.classmatesAwareness += 5
                        },
                        PlotStageID.ASK_ACTION,
                        scoreNomination = ScoreNomination("Caught holding pee",
                                when (core.schoolDay.timesCaught) {
                                    0 -> 3
                                    1 -> 8
                                    2 -> 12
                                    else -> 20
                                }, ArithmeticAction.ADD)
                )
            },

            PlotStageID.SURPRISE to {
                PlotStage(
                        Text("What... You see ${core.schoolDay.surpriseBoy.name} " +
                                "staying in front of the restroom.",
                                "Suddenly, he takes you, not letting you to escape."),
                        {
                            core.plot.specialHardcoreStage = true
                            core.character.embarrassment += 10
                        }, PlotStageID.SURPRISE_2, scoreNomination = ScoreNomination(
                        "Got the \"surprise\" by ${core.schoolDay.surpriseBoy.name}",
                        70,
                        ArithmeticAction.ADD)
                )
            },

            PlotStageID.SURPRISE_2 to {
                PlotStage(
                        Text(
                                TextLine("What do you want from me?!", true),
                                TextLine("He has brought you in the restroom " +
                                        "and quickly put you on the windowsill."),
                                TextLine(core.schoolDay.surpriseBoy.name + " has locked the restroom door " +
                                        "(seems he has stolen the key), then he puts his palm on your belly and says:"),
                                TextLine("I want you to wet yourself.")
                        ),
                        { core.character.embarrassment += 10 }, PlotStageID.SURPRISE_DIALOGUE
                )
            },

            PlotStageID.SURPRISE_DIALOGUE to {
                PlotStage(
                        Text(
                                TextLine("No, please, don't do it, no...", true),
                                TextLine("I want to see you wet...", true),
                                TextLine("He slightly presses your belly again, you shook from the terrible pain"),
                                TextLine("in your bladder and subconsciously rubbed your crotch. " +
                                        "You have to do something!")
                        ),
                        { core.character.embarrassment += 10 }, PlotStageID.SURPRISE_IDLE,
                        "Don't let him to do it!",
                        listOf(
                                action(
                                        when (core.schoolDay.surpriseBoy.timesPeeDenied) {
                                            0 -> "Try to persuade him to let you pee"
                                            1 -> "Try to persuade him to let you pee again"
                                            2 -> "Take a chance and try to persuade him (RISKY)"
                                            else -> throw IllegalStateException("character asked to pee after 3 fails")
                                        },
                                        if (chance(
                                                when (core.schoolDay.surpriseBoy.timesPeeDenied) {
                                                    0 -> 10
                                                    1 -> 5
                                                    2 -> 2
                                                    else ->
                                                        throw IllegalStateException(
                                                                "character asked to pee after 3 fails"
                                                        )
                                                }
                                        )) PlotStageID.PERSUADE else PlotStageID.PERSUADE_FAILED
                                ),
                                action("Hit him")
                                {
                                    core.plot.nextStageID = if (chance(20))
                                        PlotStageID.HIT
                                    else
                                        PlotStageID.HIT_FAILED
                                },
                                action("Wet yourself", PlotStageID.SURPRISE_WET_VOLUNTARY)
                        ), 1
                )
            },

            PlotStageID.SURPRISE_IDLE to {
                PlotStage(
                        Text(
                                TextLine("You will wet yourself right now,", true),
                                TextLine(core.schoolDay.surpriseBoy.name + " demands."),
                                TextLine("Then ${core.schoolDay.surpriseBoy.name} presses your bladder...")
                        ), nextStageID = PlotStageID.SURPRISE_WET_PRESSURE
                )
            },

            PlotStageID.HIT to {
                PlotStage(
                        Text(
                                TextLine("You hit ${core.schoolDay.surpriseBoy.name}'s groin."),
                                TextLine("Ouch!.. You, little bitch...", true),
                                TextLine("Then he left the restroom quickly."),
                                TextLine("You got off from the windowsill while holding your crotch,"),
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED -> TextLine("opened the cabin door, entered it, " +
                                            "pulled down your ${core.character.lower.insertName} " +
                                            "and ${core.character.undies.insertName},")
                                    OUTERWEAR_ONLY -> TextLine("opened the cabin door, entered it, " +
                                            "pulled down your ${core.character.lower.insertName},")
                                    UNDERWEAR_ONLY -> TextLine("opened the cabin door, entered it, " +
                                            "pulled down your ${core.character.undies.insertName},")
                                    NAKED -> TextLine("opened the cabin door, entered it,")
                                },
                                TextLine("wearily flop down on the toilet and start peeing.")
                        ), nextStageID = PlotStageID.END_GAME
                )
            },

            PlotStageID.HIT_FAILED to {
                PlotStage(
                        Text(
                                TextLine("You hit ${core.schoolDay.surpriseBoy.name}'s hand. " +
                                        "Damn, you'd meant to hit his groin..."),
                                TextLine("You're braver than I expected;", true),
                                TextLine("now let's check the strength of your bladder!", true),
                                TextLine(core.schoolDay.surpriseBoy.name + " pressed your bladder violently...")
                        ), nextStageID = PlotStageID.SURPRISE_WET_PRESSURE
                )
            },

            PlotStageID.PERSUADE to {
                PlotStage(
                        Text(
                                setOf(
                                        TextLine("Ok, you may, but you'll let me watch you pee.", true),
                                        TextLine("states ${core.schoolDay.surpriseBoy.name}. You enter the cabin,"),
                                        when (core.character.wearCombinationType) {
                                            FULLY_CLOTHED -> TextLine("pull down your " +
                                                    core.character.lower.insertName +
                                                    " and ${core.character.undies.insertName},")
                                            OUTERWEAR_ONLY ->
                                                TextLine("pull down your ${core.character.lower.insertName},")
                                            UNDERWEAR_ONLY ->
                                                TextLine("pull down your ${core.character.undies.insertName},")
                                            NAKED -> null
                                        },
                                        TextLine("stand over the toilet and start peeing under " +
                                                "${core.schoolDay.surpriseBoy.name}'s watch.")
                                ).filterNotNull().toSet()
                        ),
                        { core.character.bladder.fullness = 0.0 }, PlotStageID.END_GAME,
                        scoreNomination = ScoreNomination(
                                "Persuaded ${core.schoolDay.surpriseBoy.name} to pee",
                                when (core.schoolDay.surpriseBoy.timesPeeDenied) {
                                    0 -> 40
                                    1 -> 60
                                    2 -> 80
                                    else -> throw IllegalStateException("character asked to pee after 3 fails")
                                },
                                ArithmeticAction.ADD
                        )
                )
            },

            PlotStageID.PERSUADE_FAILED to {
                PlotStage(
                        when (core.schoolDay.surpriseBoy.timesPeeDenied) {
                            0 -> Text(
                                    TextLine("No, you can't pee in a cabin. I want you to wet yourself.,"),
                                    TextLine(core.schoolDay.surpriseBoy.name + " says.")
                            )
                            1 -> Text(
                                    TextLine("No, you can't pee in a cabin. I want you to wet yourself. " +
                                            "You're doing it now."),
                                    TextLine(core.schoolDay.surpriseBoy.name + " demands.")
                            )
                            2 -> Text(
                                    TextLine("No, you can't pee in a cabin. You will wet yourself right now,"),
                                    TextLine(core.schoolDay.surpriseBoy.name + " demands."),
                                    TextLine("Then ${core.schoolDay.surpriseBoy.name} pressed your bladder...")
                            )
                            else -> throw IllegalStateException("character asked to pee after 3 fails")
                        },
                        {
                            core.schoolDay.surpriseBoy.timesPeeDenied++
                        },
                        if (core.schoolDay.surpriseBoy.timesPeeDenied > 2)
                            PlotStageID.SURPRISE_DIALOGUE
                        else
                            PlotStageID.SURPRISE_WET_PRESSURE
                )
            },

            PlotStageID.SURPRISE_WET_VOLUNTARY to {
                PlotStage(
                        Text(
                                TextLine("Alright, as you say.,", true),
                                TextLine("you say to ${core.schoolDay.surpriseBoy.name} with a defeated sigh."),
                                TextLine("Whatever, I really can't hold it anymore anyways...")
                        ), nextStageID = PlotStageID.SURPRISE_WET_VOLUNTARY2
                )
            },

            PlotStageID.SURPRISE_WET_VOLUNTARY2 to {
                PlotStage(
                        Text("You feel the warm pee stream",
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED -> "filling your ${core.character.undies.insertName} " +
                                            "and darkening your ${core.character.lower.insertName}."
                                    OUTERWEAR_ONLY -> "filling your ${core.character.lower.insertName}."
                                    UNDERWEAR_ONLY -> "filling your ${core.character.undies.insertName}."
                                    NAKED -> "running down your legs."
                                },
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger."),
                        { core.character.bladder.fullness = 0.0 }, PlotStageID.END_GAME
                )
            },

            PlotStageID.SURPRISE_WET_PRESSURE to {
                PlotStage(
                        Text("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                when (core.character.wearCombinationType) {
                                    FULLY_CLOTHED -> "filling your ${core.character.undies.insertName} " +
                                            "and darkening your ${core.character.lower.insertName}."
                                    OUTERWEAR_ONLY -> "filling your ${core.character.lower.insertName}."
                                    UNDERWEAR_ONLY -> "filling your ${core.character.undies.insertName}."
                                    NAKED -> "running down your legs."
                                },
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger."),
                        { core.character.bladder.fullness = 0.0 }, PlotStageID.END_GAME
                )
            },

            PlotStageID.DRINK to {
                PlotStage(
                        Text("You take your bottle with water,", "open it and take a small sip."),
                        {
                            core.character.belly += core.character.thirst.toDouble()
                            core.character.thirst = 0
                        }, PlotStageID.ASK_ACTION
                )
            }
    )

    private fun getCaughtRandomly(chance: Int) {
        core.plot.nextStageID = if (chance(chance + core.schoolDay.classmatesAwareness) && core.hardcore)
            PlotStageID.CAUGHT
        else
            PlotStageID.ASK_ACTION
    }
}