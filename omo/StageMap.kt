package omo

import java.util.*
import javax.swing.JOptionPane

class StageMap(game: ALongHourAndAHalf) {
    private val state = game.state

    private val random = Random()

    private val restroomAllowed
        get() = random.nextInt(100) <= when (state.lesson.teacher.timesPeeDenied) {
            0 -> 40
            1 -> 10
            2 -> 25
            3 -> 7
            else -> throw IllegalStateException("Character asked to pee after 3 failed attempts")
        }
    private val restroomAllowed_surprise
        get() = random.nextInt(100) <= when (state.lesson.surpriseBoy.timesPeeDenied) {
            0 -> 10
            1 -> 5
            2 -> 2
            else -> throw IllegalStateException("Character asked to pee after 2 failed attempts")
        }
    private val hitSuccessful = random.nextInt(100) <= 20

    private val map: Map<StageID, Stage> = mapOf(
            StageID.LEAVE_BED to Stage(
                    Stage.Text(
                            Stage.Text.Line("Wh-what? Did I forget to set my alarm?!", true),
                            Stage.Text.Line("You cry, " +
                                    "tumbling out of bed and feeling an instant jolt from your bladder."),
                            //TODO: What if bladder isn't so full to give a jolt?
                            Stage.Text.Line(when (state.characterState.wearState.wearMode) {
                                Wear.Mode.BOTH -> "You hurriedly slip on some " +
                                        "${state.characterState.wearState.undies.insert()} and " +
                                        "${state.characterState.wearState.lower.insert()},"
                                Wear.Mode.LOWER -> "You hurriedly slip on some" +
                                        "${state.characterState.wearState.lower.insert()},"
                                Wear.Mode.UNDIES -> "You hurriedly slip on ${state.characterState.wearState.undies.insert()},"
                                Wear.Mode.NONE -> ""
                            })
                    ),
                    nextStage = StageID.LEAVE_HOME,
                    duration = 1
            ),
            StageID.LEAVE_HOME to Stage(
                    Stage.Text(
                            Stage.Text.Line("Just looking at the clock again in disbelief " +
                                    "adds a redder tint to your cheeks."),
                            Stage.Text.Line("Paying much less attention to your daily routine, " +
                                    "you quickly run down the stairs, " +
                                    "get a small glass of orange juice and chug it."),
                            Stage.Text.Line("The cold drink brings a chill down your spine " +
                                    "as you collect your things and rush out the door to school.")
                    ),
                    nextStage = StageID.GO_TO_CLASS,
                    duration = 1
            ),
            StageID.GO_TO_CLASS to Stage(
                    when (state.characterState.wearState.wearMode) {
                        Wear.Mode.BOTH,
                        Wear.Mode.LOWER ->
                            //Nothing is blowing in wind
                            if (state.characterState.wearState.lower.insert() == "skirt")
                                Stage.Text(
                                        Stage.Text.Line("You rush into class, your " +
                                                "${state.characterState.wearState.lower.insert()} blowing in the wind."),
                                        Stage.Text.Line("Normally, you'd be worried your " +
                                                "${state.characterState.wearState.undies.insert()} would be seen, " +
                                                "but you can't worry about it right now."),
                                        Stage.Text.Line("You make it to your seat without a minute to spare.")
                                )
                            else Stage.Text(
                                    Stage.Text.Line("Trying your best to make up lost time, " +
                                            "you rush into class and sit down to your seat without a minute to spare.")
                            )
                        Wear.Mode.UNDIES -> Stage.Text(
                                Stage.Text.Line("You rush into class; " +
                                        "your classmates are looking at your " +
                                        "${state.characterState.wearState.undies.insert()}."),
                                Stage.Text.Line("You can't understand how you forgot " +
                                        "to even put on any lower clothing,"),
                                Stage.Text.Line("and you know that your " +
                                        "${state.characterState.wearState.undies.insert()} have definitely been seen."),
                                Stage.Text.Line("You make it to your seat without a minute to spare.")
                        )
                        Wear.Mode.NONE ->
                            if (state.characterState.character.gender == Gender.FEMALE) {
                                Stage.Text(
                                        Stage.Text.Line("You rush into class; your classmates are looking at your pussy and boobs."),
                                        Stage.Text.Line("Guys are going mad and doing nothing except looking at you."),
                                        Stage.Text.Line("You can't understand how you dared to come to school naked."),
                                        Stage.Text.Line("You make it to your seat without a minute to spare.")
                                )
                            } else {
                                Stage.Text(
                                        Stage.Text.Line("You rush into class; your classmates are looking at your penis."),
                                        Stage.Text.Line("Girls are really going mad and doing nothing except looking at you."),
                                        Stage.Text.Line("You can't understand how you dared to come to school naked."),
                                        Stage.Text.Line("You make it to your seat without a minute to spare.")
                                )
                            }
                    },
                    operations = { state.characterState.embarrassment += 2 },
                    nextStage = StageID.WALK_IN
            ),
            StageID.WALK_IN to Stage(
                    when (state.characterState.wearState.wearMode) {
                        Wear.Mode.BOTH,
                        Wear.Mode.LOWER ->
                            if (state.characterState.wearState.lower.insert() == "skirt"
                                    || state.characterState.wearState.lower.insert() == "skirt and tights"
                                    || state.characterState.wearState.lower.insert() == "shorts") Stage.Text(
                                    Stage.Text.Line("Next time you run into class, ${state.characterState.character.name},", true),
                                    Stage.Text.Line("your teacher says,"),
                                    Stage.Text.Line("make sure you're wearing something less... revealing!"),
                                    Stage.Text.Line("A chuckle passes over the classroom, and you can't help but feel"),
                                    Stage.Text.Line("a tad bit embarrassed about your rush into class.")
                            ) else Stage.Text(
                                    Stage.Text.Line("Sit down, ${state.characterState.character.name}. You're running late.", true),
                                    Stage.Text.Line("your teacher says,"),
                                    Stage.Text.Line("And next time, don't make so much noise entering the classroom!"),
                                    Stage.Text.Line("A chuckle passes over the classroom, and you can't help but feel"),
                                    Stage.Text.Line("a tad bit embarrassed about your rush into class.")
                            )
                        Wear.Mode.UNDIES,
                        Wear.Mode.NONE -> Stage.Text(
                                Stage.Text.Line("WHAT!? YOU CAME TO SCHOOL NAKED!?", true),
                                Stage.Text.Line("your teacher shouts in disbelief."),
                                Stage.Text.Line("A chuckle passes over the classroom, and you can't help but feel extremely embarrassed"),
                                Stage.Text.Line("about your rush into class, let alone your nudity")
                        )
                    },
                    operations = {
                        when (state.characterState.wearState.wearMode) {
                            Wear.Mode.BOTH,
                            Wear.Mode.LOWER -> if (state.characterState.wearState.lower.insert() == "skirt"
                                    || state.characterState.wearState.lower.insert() == "skirt and tights"
                                    || state.characterState.wearState.lower.insert() == "shorts") state.characterState.embarrassment += 5
                            Wear.Mode.UNDIES,
                            Wear.Mode.NONE -> state.characterState.embarrassment += 25
                        }
                    },
                    nextStage = StageID.SIT_DOWN
            ),
            StageID.SIT_DOWN to Stage(
                    Stage.Text(
                            Stage.Text.Line("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of"),
                            Stage.Text.Line("your bladder filling as the liquids you drank earlier start to make their way down.")
                    ),
                    {
                        game.scorer += state.characterState.embarrassment.toInt()
                    },
                    StageID.ASK_ACTION
            ),
            StageID.ASK_ACTION to Stage(
                    game.getBladderStatus(),
                    nextStage = when {
                        state.lesson.time >= 90 -> StageID.CLASS_OVER
                        random.nextInt(20) == 5 -> StageID.CALLED_ON
                        else -> null
                    },
                    actionGroupName = "What now?",
                    actions = listOf<Action?>(
                            when (state.lesson.teacher.timesPeeDenied) {
                                0 -> Action("Ask the teacher to go pee", StageID.ASK_TO_PEE_THOUGHTS)
                                1 -> Action("Ask the teacher to go pee again", StageID.ASK_TO_PEE_THOUGHTS)
                                2 -> Action("Try to ask the teacher again", StageID.ASK_TO_PEE_THOUGHTS)
                                3 -> Action("Take a chance and ask the teacher (RISKY)", StageID.ASK_TO_PEE_THOUGHTS)
                                else -> null
                            },
                            if (!state.characterState.cornered) {
                                if (state.characterState.character.gender == Gender.FEMALE) {
                                    Action("Press on your crotch", StageID.HOLD_CROTCH)
                                } else {
                                    Action("Squeeze your penis", StageID.HOLD_CROTCH)
                                }
                            } else null,

                            Action("Rub thighs", StageID.RUB_THIGHS),
                            if (state.characterState.bladderState.fullness >= 100)
                                Action("Give up and pee yourself", StageID.GIVE_UP_THOUGHTS)
                            else null,

                            if (state.hardcore)
                                Action("Drink water", StageID.DRINK_WATER_THOUGHTS)
                            else
                                null,

                            Action("Just wait", StageID.ASK_WAIT_TIME)
                    ).filterNotNull(),
                    duration = 3,
                    cheatLists = listOf(Cheat.global, Cheat.lesson),
                    nextStagePriority = Stage.NextStagePriority.DEFINED
            ),
            StageID.ASK_WAIT_TIME to Stage(
                    operations = {
                        val timeOffset: Int
                        try {
                            timeOffset = JOptionPane.showInputDialog("How much to wait?").toInt()
                            if (state.lesson.time < 1 || state.lesson.time > 125) {
                                throw NumberFormatException()
                            }
                            game.passTime(timeOffset)
                        } //Ignoring invalid output
                        catch (e: Exception) {
                        }
                    },
                    nextStage = if ((random.nextInt(100) <= 1 + state.lesson.classmates.holdingAwareness) && state.hardcore)
                        StageID.CAUGHT
                    else
                        StageID.ASK_ACTION
            ),
            StageID.CALLED_ON to Stage(
                    Stage.Text(
                            Stage.Text.Line("${state.characterState.character.name}, why don't you come up to the board and solve this problem?,", true),
                            Stage.Text.Line("says the teacher. Of course, you don't have a clue how to solve it."),
                            Stage.Text.Line("You make your way to the front of the room and act lost, knowing you'll be stuck"),
                            Stage.Text.Line("up there for a while as the teacher explains it."),
                            Stage.Text.Line("Well, you can't dare to hold yourself now...")
                    ),
                    nextStage = StageID.ASK_ACTION,
                    duration = 5, score = 5
            ),
            StageID.ASK_TO_PEE_THOUGHTS to Stage(
                    Stage.Text(
                            Stage.Text.Line("You think to yourself:"),
                            Stage.Text.Line("I don't think I can hold it until class ends!", true),
                            Stage.Text.Line("I don't have a choice, I have to ask the teacher...", true)
                    ),
                    nextStage = StageID.ASK_TO_PEE
            ),
            StageID.ASK_TO_PEE to Stage(
                    Stage.Text(
                            Stage.Text.Line(
                                    when (state.lesson.teacher.timesPeeDenied) {
                                        0 -> "You ask the teacher if you can go out to the restroom."
                                        1, 2 -> "You ask the teacher again if you can go out to the restroom."
                                        3 -> "Desperately, you ask the teacher if you can go out to the restroom."
                                        else -> throw IllegalStateException("Character asked to pee after 3 failed attempts")
                                    }
                            ),
                            Stage.Text.Line(
                                    if (restroomAllowed && !state.hardcore)
                                        "Yes, you may."
                                    else
                                        when (state.lesson.teacher.timesPeeDenied) {
                                            0 -> "No, you can't go out, the director prohibited it."
                                            1 -> "No, you can't! I already told you that the director prohibited it!"
                                            2 -> "No, you can't! Stop asking me or there will be consequences!"
                                            3 -> "NO NO NO! YOU'LL BE PUNISHED!!"
                                            else -> throw IllegalStateException("Character asked to pee after 3 failed attempts")
                                        }
                                    , true),
                            Stage.Text.Line("says the teacher.")
                    ),
                    {
                        if (restroomAllowed) {
                            when (state.lesson.teacher.timesPeeDenied) {
                                0 -> game.scorer /= 5
                                1 -> game.scorer /= 3
                                2 -> game.scorer /= 2
                                3 -> game.scorer /= 1.5
                            }
                        } else
                            state.lesson.teacher.timesPeeDenied++
                    },
                    if (restroomAllowed)
                        StageID.PEE_SCHOOL_RESTROOM
                    else
                        StageID.ASK_ACTION
            ),
            StageID.HOLD_CROTCH to Stage(
                    Stage.Text(
                            Stage.Text.Line("You don't think anyone will see you doing it,"),
                            Stage.Text.Line("so you take your hand and hold yourself down there."),
                            Stage.Text.Line("It feels a little better for now.")
                    ),
                    {
                        game.rechargeSphPower(20)
                    },
                    if ((random.nextInt(100) <= 15 + state.lesson.classmates.holdingAwareness) && state.hardcore) {
                        StageID.CAUGHT
                    } else {
                        StageID.ASK_ACTION
                    },
                    duration = 3
            ),
            StageID.RUB_THIGHS to Stage(
                    Stage.Text(
                            Stage.Text.Line("You need to go, and it hurts, but you just"),
                            Stage.Text.Line("can't bring yourself to risk getting caught with your hand between"),
                            Stage.Text.Line("your legs. You rub your thighs hard but it doesn't really help.")
                    ),
                    {
                        game.rechargeSphPower(2)
                    },
                    if ((random.nextInt(100) <= 3 + state.lesson.classmates.holdingAwareness) && state.hardcore) {
                        StageID.CAUGHT
                    } else {
                        StageID.ASK_ACTION
                    },
                    duration = 3
            ),
            StageID.GIVE_UP_THOUGHTS to Stage(
                    Stage.Text(
                            Stage.Text.Line("You're absolutely desperate to pee, and you think you'll"),
                            Stage.Text.Line("end up peeing yourself anyway, so it's probably best to admit"),
                            Stage.Text.Line("defeat and get rid of the painful ache in your bladder.")
                    ),
                    nextStage = StageID.GIVE_UP
            ),
            StageID.GIVE_UP to Stage(
                    Stage.Text(
                            Stage.Text.Line("You get tired of holding all the urine in your aching bladder,"),
                            Stage.Text.Line(when (state.characterState.wearState.wearMode) {
                                Wear.Mode.BOTH,
                                Wear.Mode.UNDIES -> "and you decide to give up and pee in your ${state.characterState.wearState.undies.insert()}."
                                Wear.Mode.LOWER -> "and you decide to give up and pee in your ${state.characterState.wearState.lower.insert()}."
                                Wear.Mode.NONE -> "and you decide to give up and pee where you are."
                            })
                    ),
                    {
                        state.characterState.embarrassment += 80
                    },
                    StageID.WET
            ),
            StageID.ACCIDENT to Stage(
                    Stage.Text(
                            Stage.Text.Line("You can't help it.. No matter how much pressure you use, the leaks won't stop."),
                            Stage.Text.Line("Despite all this, you try your best, but suddenly you're forced to stop."),
                            Stage.Text.Line("You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere."),
                            Stage.Text.Line("But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.")
                    ),
                    nextStage = StageID.WET
            ),
            StageID.WET to Stage(
                    when (state.characterState.wearState.wearMode) {
                        Wear.Mode.BOTH -> Stage.Text(
                                Stage.Text.Line("Before you can move an inch, pee quickly soaks through your " + state.characterState.wearState.undies.insert() + ","),
                                Stage.Text.Line("floods your " + state.characterState.wearState.lower.insert() + ", and streaks down your legs."),
                                Stage.Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                        )
                        Wear.Mode.LOWER -> Stage.Text(
                                Stage.Text.Line("Before you can move an inch, pee quickly darkens your " + state.characterState.wearState.lower.insert() + " and streaks down your legs."),
                                Stage.Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                        )
                        Wear.Mode.UNDIES -> Stage.Text(
                                Stage.Text.Line("Before you can move an inch, pee quickly soaks through your " + state.characterState.wearState.undies.insert() + ", and streaks down your legs."),
                                Stage.Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                        )
                        Wear.Mode.NONE -> if (state.characterState.cornered)
                            Stage.Text(
                                    Stage.Text.Line("The heavy pee jets are hitting the floor and loudly leaking out from your " + state.characterState.wearState.undies.insert() + "."),
                                    Stage.Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                        else
                            Stage.Text(
                                    Stage.Text.Line("The heavy pee jets are hitting the seat and loudly leaking out from your " + state.characterState.wearState.undies.insert() + "."),
                                    Stage.Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                    },
                    {
                        state.characterState.bladderState.fullness = 0.0
                        state.characterState.embarrassment += 100
                    },
                    StageID.POST_WET
            ),
            StageID.POST_WET to Stage(
                    if (state.lesson.teacher.stay)
                        Stage.Text(
                                Stage.Text.Line("Teacher is laughing loudly."),
                                Stage.Text.Line("Oh, you peed yourself? This is a great punishment."),
                                Stage.Text.Line("I hope you will no longer get in the way of the lesson.")
                        )
                    else
                        Stage.Text(
                                Stage.Text.Line("People around you are laughing loudly."),
                                Stage.Text.Line(if (state.characterState.character.gender == Gender.FEMALE)
                                    "${state.characterState.character.name} peed herself! Ahaha!"
                                else
                                    "${state.characterState.character.name} peed himself! Ahaha!")
                        ),
                    nextStage = StageID.GAME_OVER
            ),
            StageID.GAME_OVER to Stage(
                    when (state.characterState.wearState.wearMode) {
                        Wear.Mode.BOTH -> Stage.Text(
                                Stage.Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                Stage.Text.Line("Your ${state.characterState.wearState.lower.insert()} and ${state.characterState.wearState.undies.insert()} are both clinging to your skin, a sign of your failure..."),
                                Stage.Text.Line("...unless, of course, you meant for this to happen?"),
                                Stage.Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                Stage.Text.Line("Game over!")
                        )
                        Wear.Mode.LOWER -> Stage.Text(
                                Stage.Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                Stage.Text.Line("Your ${state.characterState.wearState.lower.insert()} is clinging to your skin, a sign of your failure..."), //TODO: Add "is/are" depending on lower clothes type
                                Stage.Text.Line("...unless, of course, you meant for this to happen?"),
                                Stage.Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                Stage.Text.Line("Game over!")
                        )
                        Wear.Mode.UNDIES -> Stage.Text(
                                Stage.Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                Stage.Text.Line("Your ${state.characterState.wearState.undies.insert()} are clinging to your skin, a sign of your failure..."),
                                Stage.Text.Line("...unless, of course, you meant for this to happen?"),
                                Stage.Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                Stage.Text.Line("Game over!")
                        )
                        Wear.Mode.NONE -> Stage.Text(
                                Stage.Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                Stage.Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                Stage.Text.Line("Game over!")
                        )
                    }
            ),
            StageID.END_GAME to Stage(
                    Stage.Text(Stage.Text.Line.empty),
                    {
                        val scoreText = "Your score: ${game.scorer.score}"

                        JOptionPane.showMessageDialog(game.gameFrame, scoreText)
                        game.gameFrame.btnNext.isVisible = false
                    }
            ),
            StageID.CAUGHT to Stage(
                    when (state.lesson.classmates.timesCaught) {
                        0 -> Stage.Text(
                                Stage.Text.Line("It looks like a classmate has spotted that you've got to go badly."),
                                Stage.Text.Line("Damn, he may spread that fact...")
                        )

                        1 -> Stage.Text(
                                Stage.Text.Line("You'he heard a suspicious whisper behind you."),
                                Stage.Text.Line("Listening to the whisper, you've found out that they're saying that you need to go."),
                                Stage.Text.Line("If I hold it until the lesson ends, I will beat them.", true)
                        )

                        2 -> if (state.characterState.character.gender == Gender.FEMALE) Stage.Text(
                                Stage.Text.Line("The most handsome boy in your class, $state.boyName, is calling you:"),
                                Stage.Text.Line("Hey there, don't wet yourself!", true),
                                Stage.Text.Line("Oh no, he knows it...")
                        ) else Stage.Text(
                                Stage.Text.Line("The most nasty boy in your class, $state.boyName, is calling you:"),
                                Stage.Text.Line("Hey there, don't wet yourself! Ahahaha!", true),
                                Stage.Text.Line("\"Shut up...\"", true),
                                Stage.Text.Line(", you think to yourself.")
                        )

                        else -> Stage.Text(
                                Stage.Text.Line("The chuckles are continuously passing over the classroom."),
                                Stage.Text.Line("Everyone is watching you."),
                                Stage.Text.Line("Oh god... this is so embarrassing...")
                        )
                    },
                    {
                        state.characterState.embarrassment += when (state.lesson.classmates.timesCaught) {
                            0 -> 3
                            1 -> 8
                            2 -> 12
                            else -> 20
                        }
                        state.lesson.classmates.holdingAwareness += 5
                        state.lesson.classmates.timesCaught++
                    },
                    StageID.ASK_ACTION,
                    score = when (state.lesson.classmates.timesCaught) {
                        0 -> 3
                        1 -> 8
                        2 -> 12
                        3 -> 20
                        else -> 20
                    }
            ),
            StageID.DRINK_WATER_THOUGHTS to Stage(
                    Stage.Text(
                            Stage.Text.Line("Feeling a tad bit thirsty,"),
                            Stage.Text.Line("You decide to take a small sip of water from your bottle to get rid of it.")
                    ),
                    nextStage = StageID.DRINK
            ),
            StageID.DRINK to Stage(
                    Stage.Text(
                            Stage.Text.Line("You take your bottle with water,"),
                            Stage.Text.Line("open it and take a small sip.")
                    ),
                    {
                        game.offsetBelly(state.characterState.thirst.toDouble())
                        state.characterState.thirst = 0
                    },
                    StageID.ASK_ACTION
            ),
            StageID.USE_BOTTLE to Stage(
                    Stage.Text(
                            Stage.Text.Line("Luckily for you, you happen to have brought an empty bottle to pee in."),
                            Stage.Text.Line("As quietly as you can, you put it in position and let go into it."),
                            Stage.Text.Line("Ahhhhh...", true),
                            Stage.Text.Line("You can't help but show a face of pure relief as your pee trickles down into it.")
                    ),
                    {
                        state.characterState.bladderState.fullness = 0.0
                    },
                    StageID.ASK_ACTION
            ),
            StageID.CLASS_OVER to Stage(
                    Stage.Text(
                            Stage.Text.Line("You hear the bell finally ring."),
                            Stage.Text.Line("Lesson is finally over, and you're running to the restroom as fast as you can.")
                    ),
                    nextStage = when {
                        state.lesson.teacher.stay -> StageID.AFTER_CLASS
                        random.nextInt(100) <= 10 && state.lesson.hard && state.characterState.character.gender == Gender.FEMALE ->
                            StageID.SURPRISE
                        else -> if (random.nextBoolean()) StageID.SCHOOL_RESTROOM_LINE else StageID.PEE_SCHOOL_RESTROOM
                    }
            ),
            StageID.SCHOOL_RESTROOM_LINE to Stage(
                    Stage.Text(Stage.Text.Line("No, please... All cabins are occupied, and there's a line. You have to wait!")),
                    nextStage = if (random.nextBoolean()) StageID.SCHOOL_RESTROOM_LINE else StageID.PEE_SCHOOL_RESTROOM,
                    score = 3
            ),
            StageID.PEE_SCHOOL_RESTROOM to Stage(
                    Stage.Text(
                            Stage.Text.Line("Thank god, one cabin is free!"),
                            Stage.Text.Line(when (state.characterState.wearState.wearMode) {
                                Wear.Mode.BOTH ->
                                    "You enter it, pull down your ${state.characterState.wearState.lower.insert()} " +
                                            "and ${state.characterState.wearState.undies.insert()},"
                                Wear.Mode.LOWER ->
                                    "You enter it, pull down your ${state.characterState.wearState.lower.insert()},"
                                Wear.Mode.UNDIES ->
                                    "You enter it, pull down your ${state.characterState.wearState.undies.insert()},"
                                Wear.Mode.NONE ->
                                    "You enter it,"
                            }),
                            Stage.Text.Line("wearily flop down on the toilet and start peeing.")
                    ),
                    nextStage = StageID.END_GAME
            ),
            StageID.AFTER_CLASS to Stage(
                    Stage.Text(
                            Stage.Text.Line("Hey, ${state.characterState.character.name}, you wanted to escape? You must stay after classes!", true),
                            Stage.Text.Line("Please... let me go to the restroom... I can't hold it...", true),
                            Stage.Text.Line("No, ${state.characterState.character.name}, you can't go to the restroom now! This will be as punishment.", true),
                            Stage.Text.Line("And don't think you can hold yourself either! I'm watching you...", true)
                    ),
                    nextStage = if (state.lesson.time < 120)
                        StageID.AFTER_CLASS
                    else
                        StageID.PEE_SCHOOL_RESTROOM,
                    duration = 3
            ),
            StageID.SURPRISE to Stage(
                    Stage.Text(
                            Stage.Text.Line("But... You see ${state.lesson.surpriseBoy.name} staying in front of the restroom."),
                            Stage.Text.Line("Suddenly, he takes you, not letting you to escape.")
                    ),
                    {
                        state.specialHardcoreStage = true
                        state.characterState.embarrassment += 10
                    },
                    StageID.SURPRISE_2,
                    score = 70
            ),
            StageID.SURPRISE_2 to Stage(
                    Stage.Text(
                            Stage.Text.Line("What do you want from me?!", true),
                            Stage.Text.Line("He has brought you in the restroom and quickly put you on the windowsill."),
                            Stage.Text.Line("${state.lesson.surpriseBoy.name} has locked the restroom door (seems he has stolen the key), then he puts his palm on your belly and says:"),
                            Stage.Text.Line("I want you to wet yourself.", true)
                    ),
                    {
                        state.characterState.embarrassment += 10
                    },
                    StageID.SURPRISE_DIALOGUE
            ),
            StageID.SURPRISE_DIALOGUE to Stage(
                    Stage.Text(
                            Stage.Text.Line("No, please, don't do it, no...", true),
                            Stage.Text.Line("I want to see you wet...", true),
                            Stage.Text.Line("He slightly presses your belly again, you shook from the terrible pain"),
                            Stage.Text.Line("in your bladder and subconsciously rubbed your crotch. You have to do something!")
                    ),
                    {
                        state.characterState.embarrassment += 10
                    },
                    StageID.SURPRISE_WET_PRESSURE,
                    "Don't let him to do it!",
                    listOf(
                            Action("Hit him", StageID.HIT!!),
                            when (state.lesson.surpriseBoy.timesPeeDenied) {
                                0 -> Action("Try to persuade him to let you pee", StageID.PERSUADE!!)
                                1 -> Action("Try to persuade him to let you pee again", StageID.PERSUADE!!)
                                2 -> Action("Take a chance and try to persuade him (RISKY)", StageID.PERSUADE!!)
                                else -> null
                            },
                            Action("Pee yourself", StageID.SURPRISE_WET_VOLUNTARY!!)
                    ).filterNotNull()
            ),
            StageID.HIT to Stage(
                    if (hitSuccessful)
                        Stage.Text(
                                Stage.Text.Line("You hit ${state.lesson.surpriseBoy.name}'s groin."),
                                Stage.Text.Line("Ouch!.. You, little bitch...", true),
                                Stage.Text.Line("Then he left the restroom quickly."),
                                Stage.Text.Line("You got off from the windowsill while holding your crotch,"),
                                Stage.Text.Line(when (state.characterState.wearState.wearMode) {
                                    Wear.Mode.BOTH -> "opened the cabin door, entered it, pulled down your " +
                                            "${state.characterState.wearState.lower.insert()} and ${state.characterState.wearState.undies.insert()},"
                                    Wear.Mode.LOWER -> "opened the cabin door, entered it, pulled down your " + state.characterState.wearState.lower.insert() + ","
                                    Wear.Mode.UNDIES -> "opened the cabin door, entered it, pulled down your " + state.characterState.wearState.undies.insert() + ","
                                    Wear.Mode.NONE -> "opened the cabin door, entered it,"
                                }),
                                Stage.Text.Line("wearily flop down on the toilet and start peeing.")
                        )
                    else
                        Stage.Text(
                                Stage.Text.Line("You hit ${state.lesson.surpriseBoy.name}'s hand. Damn, you'd meant to hit his groin..."),
                                Stage.Text.Line("You're braver than I expected;"),
                                Stage.Text.Line("now let's check the strength of your bladder!"),
                                Stage.Text.Line("${state.lesson.surpriseBoy.name} pressed your bladder violently...")
                        ),
                    nextStage = if (hitSuccessful)
                        StageID.END_GAME
                    else
                        StageID.SURPRISE_WET_PRESSURE,
                    score = if (hitSuccessful) 40 else 0
            ),
            StageID.PERSUADE to Stage(
                    if (restroomAllowed_surprise)
                        Stage.Text(
                                Stage.Text.Line("Ok, you may, but you'll let me watch you pee.", true),
                                Stage.Text.Line("states ${state.lesson.surpriseBoy.name}. You enter the cabin,"),
                                when (state.characterState.wearState.wearMode) {
                                    Wear.Mode.BOTH -> Stage.Text.Line("pull down your " + state.characterState.wearState.lower.insert() + " and " + state.characterState.wearState.undies.insert() + ",")
                                    Wear.Mode.LOWER -> Stage.Text.Line("pull down your " + state.characterState.wearState.lower.insert() + ",")
                                    Wear.Mode.UNDIES -> Stage.Text.Line("pull down your " + state.characterState.wearState.undies.insert() + ",")
                                    else -> Stage.Text.Line("")   //TODO
                                },
                                Stage.Text.Line("stand over the toilet and start peeing under ${state.lesson.surpriseBoy.name}'s spectation.")
                        )
                    else
                        when (state.lesson.surpriseBoy.timesPeeDenied) {
                            0 -> Stage.Text(Stage.Text.Line("You ask ${state.lesson.surpriseBoy.name} if you can pee."),
                                    Stage.Text.Line("No, you can't pee in a cabin. I want you to wet yourself.,"),
                                    Stage.Text.Line("${state.lesson.surpriseBoy.name} says.")
                            )
                            1 -> Stage.Text(Stage.Text.Line("You ask ${state.lesson.surpriseBoy.name} if you can pee again."),
                                    Stage.Text.Line("No, you can't pee in a cabin. I want you to wet yourself. You're doing it now."),
                                    Stage.Text.Line("${state.lesson.surpriseBoy.name} demands.")
                            )
                            2 -> Stage.Text(Stage.Text.Line("You ask if you can pee again desperately."),
                                    Stage.Text.Line("No, you can't pee in a cabin. You will wet yourself right now,"),
                                    Stage.Text.Line("${state.lesson.surpriseBoy.name} demands.")
                            )
                            else -> throw IllegalStateException("Character asked to pee after 2 failed attempts")
                        },
                    {
                        if (restroomAllowed_surprise)
                            state.characterState.bladderState.fullness = 0.0
                        else
                            state.lesson.surpriseBoy.timesPeeDenied++
                    },
                    when {
                        restroomAllowed_surprise -> StageID.END_GAME
                        state.lesson.surpriseBoy.timesPeeDenied < 1 -> StageID.SURPRISE_WET_PRESSURE
                        else -> StageID.SURPRISE_DIALOGUE
                    },
                    score = if (restroomAllowed_surprise)
                        when (state.lesson.surpriseBoy.timesPeeDenied) {
                            0 -> 40
                            1 -> 60
                            2 -> 80
                            else -> throw IllegalStateException("Character asked to pee after 2 failed attempts")
                        } else 0
            ),
            StageID.SURPRISE_WET_VOLUNTARY to Stage(
                    Stage.Text(
                            Stage.Text.Line("Alright, as you say.,", true),
                            Stage.Text.Line("you say to ${state.lesson.surpriseBoy.name} with a defeated sigh."),
                            Stage.Text.Line("Whatever, I really can't hold it anymore anyways...", true)
                    ),
                    nextStage = StageID.SURPRISE_WET_VOLUNTARY2
            ),
            StageID.SURPRISE_WET_VOLUNTARY2 to Stage(
                    Stage.Text(
                            Stage.Text.Line("You feel the warm pee stream"),
                            Stage.Text.Line(when (state.characterState.wearState.wearMode) {
                                Wear.Mode.BOTH -> "filling your ${state.characterState.wearState.undies.insert()} " +
                                        "and darkening your ${state.characterState.wearState.lower.insert()}."
                                Wear.Mode.LOWER -> "filling your ${state.characterState.wearState.lower.insert()}."
                                Wear.Mode.UNDIES -> "filling your ${state.characterState.wearState.undies.insert()}."
                                Wear.Mode.NONE -> "running down your legs."
                            })
                    ),
                    {
                        state.characterState.bladderState.fullness = 0.0
                    },
                    StageID.END_GAME
            ),
            StageID.SURPRISE_WET_PRESSURE to Stage(
                    Stage.Text(
                            Stage.Text.Line("${state.lesson.surpriseBoy.name} pressed your bladder violently..."),
                            Stage.Text.Line("Ouch... The sudden pain flash passes through your bladder..."),
                            Stage.Text.Line("You try to hold the pee back, but you just can't."),
                            Stage.Text.Line("You feel the warm pee stream"),
                            Stage.Text.Line(when (state.characterState.wearState.wearMode) {
                                Wear.Mode.BOTH -> "filling your ${state.characterState.wearState.undies.insert()} " +
                                        "and darkening your ${state.characterState.wearState.lower.insert()}."
                                Wear.Mode.LOWER -> "filling your ${state.characterState.wearState.lower.insert()}."
                                Wear.Mode.UNDIES -> "filling your ${state.characterState.wearState.undies.insert()}."
                                Wear.Mode.NONE -> "running down your legs."
                            }),
                            Stage.Text.Line("You close your eyes and ease your sphincter off"),
                            Stage.Text.Line("and feel the pee stream become much stronger.")
                    ),
                    {
                        state.characterState.bladderState.fullness = 0.0
                    },
                    StageID.END_GAME
            ),
            StageID.NULL to Stage()
    )

    operator fun get(key: StageID) = this.map[key]
}