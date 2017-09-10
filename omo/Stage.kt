package omo

import java.util.*
import javax.swing.JOptionPane

class Stage(
        val text: Text? = null,
        val operations: () -> Unit = {},
        nextStage: StageID? = null,
        val actionGroupName: String? = null,
        val actions: List<Action?>? = null,
        val duration: Int = 0, val score: Int = 0,
        val cheatLists: List<List<Cheat>>? = null,
        val nextStagePriority: NextStagePriority = NextStagePriority.ACTION
) {
    val nextStage = Stage.map[nextStage]

    class Text(vararg lines: Line) {
        class Line(
                val line: String,
                val italic: Boolean = false
        ) {
            companion object {
                val empty = Line("")
            }
        }

        val lines = lines

        companion object {
            val empty = Text(Line(""))
        }
    }

    enum class NextStagePriority {
        ACTION,
        DEFINED
    }

    companion object {
        enum class StageID {
            LEAVE_BED,
            LEAVE_HOME,
            GO_TO_CLASS,
            WALK_IN,
            SIT_DOWN,
            ASK_ACTION,
            ASK_TO_PEE,
            ASK_TO_PEE_THOUGHTS,
            HOLD_CROTCH,
            RUB_THIGHS,
            CALLED_ON,
            CAUGHT,
            USE_BOTTLE,
            CLASS_OVER,
            SCHOOL_RESTROOM_LINE,
            PEE_SCHOOL_RESTROOM,
            AFTER_CLASS,
            ACCIDENT,
            GIVE_UP_THOUGHTS,
            GIVE_UP,
            WET,
            POST_WET,
            GAME_OVER,
            END_GAME,
            SURPRISE,
            SURPRISE_2,
            SURPRISE_ACCIDENT,
            SURPRISE_DIALOGUE,
            SURPRISE_CHOSE,
            HIT,
            PERSUADE,
            SURPRISE_WET_VOLUNTARY,
            SURPRISE_WET_VOLUNTARY2,
            SURPRISE_WET_PRESSURE,
            DRINK,
            DRINK_WATER_THOUGHTS,


        }

        lateinit var game: ALongHourAndAHalf
        private var restroomAllowed: Boolean = isRestroomAllowed()
        private var restroomAllowed_surprise: Boolean = isRestroomAllowed_surprise()
        private val hitSuccessful = Random().nextInt(100) <= 20
        val map: Map<StageID, Stage> = mapOf(
                StageID.LEAVE_BED to Stage(
                        Text(
                                Text.Line("Wh-what? Did I forget to set my alarm?!", true),
                                Text.Line("You cry, " +
                                        "tumbling out of bed and feeling an instant jolt from your bladder."),
                                //TODO: What if bladder isn't so full to give a jolt?
                                Text.Line(when (game.character.gameState!!.wearMode) {
                                    Wear.Mode.BOTH -> "You hurriedly slip on some " +
                                            "${game.character.gameState!!.undies.insert()} and " +
                                            "${game.character.gameState!!.lower.insert()},"
                                    Wear.Mode.LOWER -> "You hurriedly slip on some" +
                                            "${game.character.gameState!!.lower.insert()},"
                                    Wear.Mode.UNDIES -> "You hurriedly slip on ${game.character.gameState!!.undies.insert()},"
                                    Wear.Mode.NONE -> ""
                                })
                        ),
                        nextStage = StageID.LEAVE_HOME,
                        duration = 1
                ),
                StageID.LEAVE_HOME to Stage(
                        Text(
                                Text.Line("Just looking at the clock again in disbelief " +
                                        "adds a redder tint to your cheeks."),
                                Text.Line("Paying much less attention to your daily routine, " +
                                        "you quickly run down the stairs, " +
                                        "get a small glass of orange juice and chug it."),
                                Text.Line("The cold drink brings a chill down your spine " +
                                        "as you collect your things and rush out the door to school.")
                        ),
                        nextStage = StageID.GO_TO_CLASS,
                        duration = 1
                ),
                StageID.GO_TO_CLASS to Stage(
                        when (game.character.gameState!!.wearMode) {
                            Wear.Mode.BOTH,
                            Wear.Mode.LOWER ->
                                //Nothing is blowing in wind
                                if (game.character.gameState!!.lower.insert() == "skirt")
                                    Text(
                                            Text.Line("You rush into class, your " +
                                                    "${game.character.gameState!!.lower.insert()} blowing in the wind."),
                                            Text.Line("Normally, you'd be worried your " +
                                                    "${game.character.gameState!!.undies.insert()} would be seen, " +
                                                    "but you can't worry about it right now."),
                                            Text.Line("You make it to your seat without a minute to spare.")
                                    )
                                else Text(
                                        Text.Line("Trying your best to make up lost time, " +
                                                "you rush into class and sit down to your seat without a minute to spare.")
                                )
                            Wear.Mode.UNDIES -> Text(
                                    Text.Line("You rush into class; " +
                                            "your classmates are looking at your " +
                                            "${game.character.gameState!!.undies.insert()}."),
                                    Text.Line("You can't understand how you forgot " +
                                            "to even put on any lower clothing,"),
                                    Text.Line("and you know that your " +
                                            "${game.character.gameState!!.undies.insert()} have definitely been seen."),
                                    Text.Line("You make it to your seat without a minute to spare.")
                            )
                            Wear.Mode.NONE ->
                                if (game.character.gender == Gender.FEMALE) {
                                    Text(
                                            Text.Line("You rush into class; your classmates are looking at your pussy and boobs."),
                                            Text.Line("Guys are going mad and doing nothing except looking at you."),
                                            Text.Line("You can't understand how you dared to come to school naked."),
                                            Text.Line("You make it to your seat without a minute to spare.")
                                    )
                                } else {
                                    Text(
                                            Text.Line("You rush into class; your classmates are looking at your penis."),
                                            Text.Line("Girls are really going mad and doing nothing except looking at you."),
                                            Text.Line("You can't understand how you dared to come to school naked."),
                                            Text.Line("You make it to your seat without a minute to spare.")
                                    )
                                }
                        },
                        operations = { game.character.gameState!!.embarrassment += 2 },
                        nextStage = StageID.WALK_IN
                ),
                StageID.WALK_IN to Stage(
                        when (game.character.gameState!!.wearMode) {
                            Wear.Mode.BOTH,
                            Wear.Mode.LOWER ->
                                if (game.character.gameState!!.lower.insert() == "skirt"
                                        || game.character.gameState!!.lower.insert() == "skirt and tights"
                                        || game.character.gameState!!.lower.insert() == "shorts") Text(
                                        Text.Line("Next lesson.time you run into class, ${game.character.name},", true),
                                        Text.Line("your teacher says,"),
                                        Text.Line("make sure you're wearing something less... revealing!"),
                                        Text.Line("A chuckle passes over the classroom, and you can't help but feel"),
                                        Text.Line("a tad bit embarrassed about your rush into class.")
                                ) else Text(
                                        Text.Line("Sit down, ${game.character.name}. You're running late.", true),
                                        Text.Line("your teacher says,"),
                                        Text.Line("And next lesson.time, don't make so much noise entering the classroom!"),
                                        Text.Line("A chuckle passes over the classroom, and you can't help but feel"),
                                        Text.Line("a tad bit embarrassed about your rush into class.")
                                )
                            Wear.Mode.UNDIES,
                            Wear.Mode.NONE -> Text(
                                    Text.Line("WHAT!? YOU CAME TO SCHOOL NAKED!?", true),
                                    Text.Line("your teacher shouts in disbelief."),
                                    Text.Line("A chuckle passes over the classroom, and you can't help but feel extremely embarrassed"),
                                    Text.Line("about your rush into class, let alone your nudity")
                            )
                        },
                        operations = {
                            when (game.character.gameState!!.wearMode) {
                                Wear.Mode.BOTH,
                                Wear.Mode.LOWER -> if (game.character.gameState!!.lower.insert() == "skirt"
                                        || game.character.gameState!!.lower.insert() == "skirt and tights"
                                        || game.character.gameState!!.lower.insert() == "shorts") game.character.gameState!!.embarrassment += 5
                                Wear.Mode.UNDIES,
                                Wear.Mode.NONE -> game.character.gameState!!.embarrassment += 25
                            }
                        },
                        nextStage = StageID.SIT_DOWN
                ),
                StageID.SIT_DOWN to Stage(
                        Text(
                                Text.Line("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of"),
                                Text.Line("your bladder filling as the liquids you drank earlier start to make their way down.")
                        ),
                        {
                            game.scorer += game.character.gameState!!.embarrassment.toInt()
                        },
                        StageID.ASK_ACTION, null
                ),
                StageID.ASK_ACTION to Stage(
                        game.getBladderStatus(),
                        nextStage = when {
                            game.lesson.time >= 90 -> StageID.CLASS_OVER
                            game.random.nextInt(20) == 5 -> StageID.CALLED_ON
                            else -> null
                        },
                        actionGroupName = "What now?",
                        actions = listOf<Action?>(
                                when (game.lesson.teacher.timesPeeDenied) {
                                    0 -> Action("Ask the teacher to go pee", StageID.ASK_TO_PEE_THOUGHTS)
                                    1 -> Action("Ask the teacher to go pee again", StageID.ASK_TO_PEE_THOUGHTS)
                                    2 -> Action("Try to ask the teacher again", StageID.ASK_TO_PEE_THOUGHTS)
                                    3 -> Action("Take a chance and ask the teacher (RISKY)", StageID.ASK_TO_PEE_THOUGHTS)
                                    else -> null
                                },
                                if (!game.character.gameState!!.cornered) {
                                    if (game.character.gender == Gender.FEMALE) {
                                        Action("Press on your crotch", StageID.HOLD_CROTCH)
                                    } else {
                                        Action("Squeeze your penis", StageID.HOLD_CROTCH)
                                    }
                                } else null,

                                Action("Rub thighs", StageID.RUB_THIGHS),
                                if ((game.character.bladder.gameState as Bladder.GameState).fullness >= 100)
                                    Action("Give up and pee yourself", StageID.GIVE_UP_THOUGHTS)
                                else null,

                                if (game.hardcore)
                                    Action("Drink water", StageID.DRINK_WATER_THOUGHTS)
                                else
                                    null,

                                Action("Just wait", Stage(
                                        operations = {
                                            val timeOffset: Int
                                            try {
                                                timeOffset = JOptionPane.showInputDialog("How much to wait?").toInt()
                                                if (game.lesson.time < 1 || game.lesson.time > 125) {
                                                    throw NumberFormatException()
                                                }
                                                game.passTime(timeOffset)
                                            } //Ignoring invalid output
                                            catch (e: Exception) {
                                            }
                                        },
                                        nextStage = if ((Random().nextInt(100) <= 1 + game.lesson.classmates.holdingAwareness) && game.hardcore)
                                            StageID.CAUGHT
                                        else
                                            StageID.ASK_ACTION,
                                        duration = 3
                                ))
                        ),
                        duration = 3,
                        cheatLists = listOf(Cheat.global, Cheat.lesson),
                        nextStagePriority = NextStagePriority.DEFINED
                ),
                StageID.CALLED_ON to Stage(
                        Text(
                                Text.Line("${game.character.name}, why don't you come up to the board and solve this problem?,", true),
                                Text.Line("says the teacher. Of course, you don't have a clue how to solve it."),
                                Text.Line("You make your way to the front of the room and act lost, knowing you'll be stuck"),
                                Text.Line("up there for a while as the teacher explains it."),
                                Text.Line("Well, you can't dare to hold yourself now...")
                        ),
                        nextStage = StageID.ASK_ACTION,
                        duration = 5, score = 5
                ),
                StageID.ASK_TO_PEE_THOUGHTS to Stage(
                        Text(
                                Text.Line("You think to yourself:"),
                                Text.Line("I don't think I can hold it until class ends!", true),
                                Text.Line("I don't have a choice, I have to ask the teacher...", true)
                        ),
                        nextStage = StageID.ASK_TO_PEE
                ),
                StageID.ASK_TO_PEE to Stage(
                        Text(
                                Text.Line(
                                        when (game.lesson.teacher.timesPeeDenied) {
                                            0 -> "You ask the teacher if you can go out to the restroom."
                                            1, 2 -> "You ask the teacher again if you can go out to the restroom."
                                            3 -> "Desperately, you ask the teacher if you can go out to the restroom."
                                            else -> throw IllegalStateException("Character asked to pee after 3 failed attempts")
                                        }
                                ),
                                Text.Line(
                                        if (isRestroomAllowed() && !game.hardcore)
                                            "Yes, you may."
                                        else
                                            when (game.lesson.teacher.timesPeeDenied) {
                                                0 -> "No, you can't go out, the director prohibited it."
                                                1 -> "No, you can't! I already told you that the director prohibited it!"
                                                2 -> "No, you can't! Stop asking me or there will be consequences!"
                                                3 -> "NO NO NO! YOU'LL BE PUNISHED!!"
                                                else -> throw IllegalStateException("Character asked to pee after 3 failed attempts")
                                            }
                                        , true),
                                Text.Line("says the teacher.")
                        ),
                        {
                            if (restroomAllowed) {
                                when (game.lesson.teacher.timesPeeDenied) {
                                    0 -> game.scorer /= 5
                                    1 -> game.scorer /= 3
                                    2 -> game.scorer /= 2
                                    3 -> game.scorer /= 1.5
                                }
                            } else
                                game.lesson.teacher.timesPeeDenied++
                            restroomAllowed = isRestroomAllowed()
                        },
                        if (restroomAllowed)
                            StageID.PEE_SCHOOL_RESTROOM
                        else
                            StageID.ASK_ACTION
                ),
                StageID.HOLD_CROTCH to Stage(
                        Text(
                                Text.Line("You don't think anyone will see you doing it,"),
                                Text.Line("so you take your hand and hold yourself down there."),
                                Text.Line("It feels a little better for now.")
                        ),
                        {
                            game.rechargeSphPower(20)
                        },
                        if ((Random().nextInt(100) <= 15 + game.lesson.classmates.holdingAwareness) && game.hardcore) {
                            StageID.CAUGHT
                        } else {
                            StageID.ASK_ACTION
                        },
                        duration = 3
                ),
                StageID.RUB_THIGHS to Stage(
                        Text(
                                Text.Line("You need to go, and it hurts, but you just"),
                                Text.Line("can't bring yourself to risk getting caught with your hand between"),
                                Text.Line("your legs. You rub your thighs hard but it doesn't really help.")
                        ),
                        {
                            game.rechargeSphPower(2)
                        },
                        if ((Random().nextInt(100) <= 3 + game.lesson.classmates.holdingAwareness) && game.hardcore) {
                            StageID.CAUGHT
                        } else {
                            StageID.ASK_ACTION
                        },
                        duration = 3
                ),
                StageID.GIVE_UP_THOUGHTS to Stage(
                        Text(
                                Text.Line("You're absolutely desperate to pee, and you think you'll"),
                                Text.Line("end up peeing yourself anyway, so it's probably best to admit"),
                                Text.Line("defeat and get rid of the painful ache in your bladder.")
                        ),
                        nextStage = StageID.GIVE_UP
                ),
                StageID.GIVE_UP to Stage(
                        Text(
                                Text.Line("You get tired of holding all the urine in your aching bladder,"),
                                Text.Line(when (game.character.gameState!!.wearMode) {
                                    Wear.Mode.BOTH,
                                    Wear.Mode.UNDIES -> "and you decide to give up and pee in your ${game.character.gameState!!.undies.insert()}."
                                    Wear.Mode.LOWER -> "and you decide to give up and pee in your ${game.character.gameState!!.lower.insert()}."
                                    Wear.Mode.NONE -> "and you decide to give up and pee where you are."
                                })
                        ),
                        {
                            game.character.gameState!!.embarrassment += 80
                        },
                        StageID.WET
                ),
                StageID.ACCIDENT to Stage(
                        Text(
                                Text.Line("You can't help it.. No matter how much pressure you use, the leaks won't stop."),
                                Text.Line("Despite all this, you try your best, but suddenly you're forced to stop."),
                                Text.Line("You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere."),
                                Text.Line("But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.")
                        ),
                        nextStage = StageID.WET
                ),
                StageID.WET to Stage(
                        when (game.character.gameState!!.wearMode) {
                            Wear.Mode.BOTH -> Text(
                                    Text.Line("Before you can move an inch, pee quickly soaks through your " + game.character.gameState!!.undies.insert() + ","),
                                    Text.Line("floods your " + game.character.gameState!!.lower.insert() + ", and streaks down your legs."),
                                    Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                            Wear.Mode.LOWER -> Text(
                                    Text.Line("Before you can move an inch, pee quickly darkens your " + game.character.gameState!!.lower.insert() + " and streaks down your legs."),
                                    Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                            Wear.Mode.UNDIES -> Text(
                                    Text.Line("Before you can move an inch, pee quickly soaks through your " + game.character.gameState!!.undies.insert() + ", and streaks down your legs."),
                                    Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                            Wear.Mode.NONE -> if (game.character.gameState!!.cornered)
                                Text(
                                        Text.Line("The heavy pee jets are hitting the floor and loudly leaking out from your " + game.character.gameState!!.undies.insert() + "."),
                                        Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                                )
                            else
                                Text(
                                        Text.Line("The heavy pee jets are hitting the seat and loudly leaking out from your " + game.character.gameState!!.undies.insert() + "."),
                                        Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                                )
                        },
                        {
                            game.character.bladder.gameState!!.fullness = 0.0
                            game.character.gameState!!.embarrassment += 100
                        },
                        StageID.POST_WET
                ),
                StageID.POST_WET to Stage(
                        if (game.lesson.teacher.stay)
                            Text(
                                    Text.Line("Teacher is laughing loudly."),
                                    Text.Line("Oh, you peed yourself? This is a great punishment."),
                                    Text.Line("I hope you will no longer get in the way of the lesson.")
                            )
                        else
                            Text(
                                    Text.Line("People around you are laughing loudly."),
                                    Text.Line(if (game.character.gender == Gender.FEMALE)
                                        "${game.character.name} peed herself! Ahaha!"
                                    else
                                        "${game.character.name} peed himself! Ahaha!")
                            ),
                        nextStage = StageID.GAME_OVER
                ),
                StageID.GAME_OVER to Stage(
                        when (game.character.gameState!!.wearMode) {
                            Wear.Mode.BOTH -> Text(
                                    Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                    Text.Line("Your ${game.character.gameState!!.lower.insert()} and ${game.character.gameState!!.undies.insert()} are both clinging to your skin, a sign of your failure..."),
                                    Text.Line("...unless, of course, you meant for this to happen?"),
                                    Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                    Text.Line("Game over!")
                            )
                            Wear.Mode.LOWER -> Text(
                                    Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                    Text.Line("Your ${game.character.gameState!!.lower.insert()} is clinging to your skin, a sign of your failure..."), //TODO: Add "is/are" depending on character.gameState.lower clothes type
                                    Text.Line("...unless, of course, you meant for this to happen?"),
                                    Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                    Text.Line("Game over!")
                            )
                            Wear.Mode.UNDIES -> Text(
                                    Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                    Text.Line("Your ${game.character.gameState!!.undies.insert()} are clinging to your skin, a sign of your failure..."),
                                    Text.Line("...unless, of course, you meant for this to happen?"),
                                    Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                    Text.Line("Game over!")
                            )
                            Wear.Mode.NONE -> Text(
                                    Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                    Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                    Text.Line("Game over!")
                            )
                        }
                ),
                StageID.END_GAME to Stage(
                        Text(Text.Line.empty),
                        {
                            val scoreText = "Your score: ${game.scorer.score}"

                            JOptionPane.showMessageDialog(game.gameFrame, scoreText)
                            game.gameFrame.btnNext.isVisible = false
                        }
                ),
                StageID.CAUGHT to Stage(
                        when (game.lesson.classmates.timesCaught) {
                            0 -> Text(
                                    Text.Line("It looks like a classmate has spotted that you've got to go badly."),
                                    Text.Line("Damn, he may spread that fact...")
                            )

                            1 -> Text(
                                    Text.Line("You'he heard a suspicious whisper behind you."),
                                    Text.Line("Listening to the whisper, you've found out that they're saying that you need to go."),
                                    Text.Line("If I hold it until the lesson ends, I will beat them.", true)
                            )

                            2 -> if (game.character.gender == Gender.FEMALE) Text(
                                    Text.Line("The most handsome boy in your class, $game.boyName, is calling you:"),
                                    Text.Line("Hey there, don't wet yourself!", true),
                                    Text.Line("Oh no, he knows it...")
                            ) else Text(
                                    Text.Line("The most nasty boy in your class, $game.boyName, is calling you:"),
                                    Text.Line("Hey there, don't wet yourself! Ahahaha!", true),
                                    Text.Line("\"Shut up...\"", true),
                                    Text.Line(", you think to yourself.")
                            )

                            else -> Text(
                                    Text.Line("The chuckles are continuously passing over the classroom."),
                                    Text.Line("Everyone is watching you."),
                                    Text.Line("Oh god... this is so embarrassing...")
                            )
                        },
                        {
                            game.character.gameState!!.embarrassment += when (game.lesson.classmates.timesCaught) {
                                0 -> 3
                                1 -> 8
                                2 -> 12
                                else -> 20
                            }
                            game.lesson.classmates.holdingAwareness += 5
                            game.lesson.classmates.timesCaught++
                        },
                        StageID.ASK_ACTION,
                        score = when (game.lesson.classmates.timesCaught) {
                            0 -> 3
                            1 -> 8
                            2 -> 12
                            3 -> 20
                            else -> 20
                        }
                ),
                StageID.DRINK_WATER_THOUGHTS to Stage(
                        Text(
                                Text.Line("Feeling a tad bit thirsty,"),
                                Text.Line("You decide to take a small sip of water from your bottle to get rid of it.")
                        ),
                        nextStage = StageID.DRINK
                ),
                StageID.DRINK to Stage(
                        Text(
                                Text.Line("You take your bottle with water,"),
                                Text.Line("open it and take a small sip.")
                        ),
                        {
                            game.offsetBelly(game.character.gameState!!.thirst.toDouble())
                            game.character.gameState!!.thirst = 0
                        },
                        StageID.ASK_ACTION
                ),
                StageID.USE_BOTTLE to Stage(
                        Text(
                                Text.Line("Luckily for you, you happen to have brought an empty bottle to pee in."),
                                Text.Line("As quietly as you can, you put it in position and let go into it."),
                                Text.Line("Ahhhhh...", true),
                                Text.Line("You can't help but show a face of pure relief as your pee trickles down into it.")
                        ),
                        {
                            game.character.bladder.gameState!!.fullness = 0.0
                        },
                        StageID.ASK_ACTION
                ),
                StageID.CLASS_OVER to Stage(
                        Text(
                                Text.Line("You hear the bell finally ring."),
                                Text.Line("Lesson is finally over, and you're running to the restroom as fast as you can.")
                        ),
                        nextStage = when {
                            game.lesson.teacher.stay -> StageID.AFTER_CLASS
                            Random().nextInt(100) <= 10 && game.lesson.hard && game.character.gender == Gender.FEMALE ->
                                StageID.SURPRISE
                            else -> if (Random().nextBoolean()) StageID.SCHOOL_RESTROOM_LINE else StageID.PEE_SCHOOL_RESTROOM
                        }
                ),
                StageID.SCHOOL_RESTROOM_LINE to Stage(
                        Text(Text.Line("No, please... All cabins are occupied, and there's a line. You have to wait!")),
                        nextStage = if (Random().nextBoolean()) StageID.SCHOOL_RESTROOM_LINE else StageID.PEE_SCHOOL_RESTROOM,
                        score = 3
                ),
                StageID.PEE_SCHOOL_RESTROOM to Stage(
                        Text(
                                Text.Line("Thank god, one cabin is free!"),
                                Text.Line(when (game.character.gameState!!.wearMode) {
                                    Wear.Mode.BOTH ->
                                        "You enter it, pull down your ${game.character.gameState!!.lower.insert()} " +
                                                "and ${game.character.gameState!!.undies.insert()},"
                                    Wear.Mode.LOWER ->
                                        "You enter it, pull down your ${game.character.gameState!!.lower.insert()},"
                                    Wear.Mode.UNDIES ->
                                        "You enter it, pull down your ${game.character.gameState!!.undies.insert()},"
                                    Wear.Mode.NONE ->
                                        "You enter it,"
                                }),
                                Text.Line("wearily flop down on the toilet and start peeing.")
                        ),
                        nextStage = StageID.END_GAME
                ),
                StageID.AFTER_CLASS to Stage(
                        Text(
                                Text.Line("Hey, ${game.character.name}, you wanted to escape? You must stay after classes!", true),
                                Text.Line("Please... let me go to the restroom... I can't hold it...", true),
                                Text.Line("No, ${game.character.name}, you can't go to the restroom now! This will be as punishment.", true),
                                Text.Line("And don't think you can hold yourself either! I'm watching you...", true)
                        ),
                        nextStage = if (game.lesson.time < 120)
                            StageID.AFTER_CLASS
                        else
                            StageID.PEE_SCHOOL_RESTROOM,
                        duration = 3
                ),
                StageID.SURPRISE to Stage(
                        Text(
                                Text.Line("But... You see ${game.lesson.surpriseBoy.name} staying in front of the restroom."),
                                Text.Line("Suddenly, he takes you, not letting you to escape.")
                        ),
                        {
                            game.specialHardcoreStage = true
                            game.character.gameState!!.embarrassment += 10
                        },
                        StageID.SURPRISE_2,
                        score = 70
                ),
                StageID.SURPRISE_2 to Stage(
                        Text(
                                Text.Line("What do you want from me?!", true),
                                Text.Line("He has brought you in the restroom and quickly put you on the windowsill."),
                                Text.Line("${game.lesson.surpriseBoy.name} has locked the restroom door (seems he has stolen the key), then he puts his palm on your belly and says:"),
                                Text.Line("I want you to wet yourself.", true)
                        ),
                        {
                            game.character.gameState!!.embarrassment += 10
                        },
                        StageID.SURPRISE_DIALOGUE
                ),
                StageID.SURPRISE_DIALOGUE to Stage(
                        Text(
                                Text.Line("No, please, don't do it, no...", true),
                                Text.Line("I want to see you wet...", true),
                                Text.Line("He slightly presses your belly again, you shook from the terrible pain"),
                                Text.Line("in your bladder and subconsciously rubbed your crotch. You have to do something!")
                        ),
                        {
                            game.character.gameState!!.embarrassment += 10
                        },
                        StageID.SURPRISE_WET_PRESSURE,
                        "Don't let him to do it!",
                        listOf(
                                Action("Hit him", StageID.HIT),
                                when (game.lesson.surpriseBoy.timesPeeDenied) {
                                    0 -> Action("Try to persuade him to let you pee", StageID.PERSUADE)
                                    1 -> Action("Try to persuade him to let you pee again", StageID.PERSUADE)
                                    2 -> Action("Take a chance and try to persuade him (RISKY)", StageID.PERSUADE)
                                    else -> null
                                },
                                Action("Pee yourself", StageID.SURPRISE_WET_VOLUNTARY)
                        )
                ),
                StageID.HIT to Stage(
                        if (hitSuccessful)
                            Text(
                                    Text.Line("You hit ${game.lesson.surpriseBoy.name}'s groin."),
                                    Text.Line("Ouch!.. You, little bitch...", true),
                                    Text.Line("Then he left the restroom quickly."),
                                    Text.Line("You got off from the windowsill while holding your crotch,"),
                                    Text.Line(when (game.character.gameState!!.wearMode) {
                                        Wear.Mode.BOTH -> "opened the cabin door, entered it, pulled down your " +
                                                "${game.character.gameState!!.lower.insert()} and ${game.character.gameState!!.undies.insert()},"
                                        Wear.Mode.LOWER -> "opened the cabin door, entered it, pulled down your " + game.character.gameState!!.lower.insert() + ","
                                        Wear.Mode.UNDIES -> "opened the cabin door, entered it, pulled down your " + game.character.gameState!!.undies.insert() + ","
                                        Wear.Mode.NONE -> "opened the cabin door, entered it,"
                                    }),
                                    Text.Line("wearily flop down on the toilet and start peeing.")
                            )
                        else
                            Text(
                                    Text.Line("You hit ${game.lesson.surpriseBoy.name}'s hand. Damn, you'd meant to hit his groin..."),
                                    Text.Line("You're braver than I expected;"),
                                    Text.Line("now let's check the strength of your bladder!"),
                                    Text.Line("${game.lesson.surpriseBoy.name} pressed your bladder violently...")
                            ),
                        nextStage = if (hitSuccessful)
                            StageID.END_GAME
                        else
                            StageID.SURPRISE_WET_PRESSURE,
                        score = if (hitSuccessful) 40 else 0
                ),
                StageID.PERSUADE to Stage(
                        if (restroomAllowed_surprise)
                            Text(
                                    Text.Line("Ok, you may, but you'll let me watch you pee.", true),
                                    Text.Line("states ${game.lesson.surpriseBoy.name}. You enter the cabin,"),
                                    when (game.character.gameState!!.wearMode) {
                                        Wear.Mode.BOTH -> Text.Line("pull down your " + game.character.gameState!!.lower.insert() + " and " + game.character.gameState!!.undies.insert() + ",")
                                        Wear.Mode.LOWER -> Text.Line("pull down your " + game.character.gameState!!.lower.insert() + ",")
                                        Wear.Mode.UNDIES -> Text.Line("pull down your " + game.character.gameState!!.undies.insert() + ",")
                                        else -> Text.Line("")   //TODO
                                    },
                                    Text.Line("stand over the toilet and start peeing under ${game.lesson.surpriseBoy.name}'s spectation.")
                            )
                        else
                            when (game.lesson.surpriseBoy.timesPeeDenied) {
                                0 -> Text(Text.Line("You ask ${game.lesson.surpriseBoy.name} if you can pee."),
                                        Text.Line("No, you can't pee in a cabin. I want you to wet yourself.,"),
                                        Text.Line("${game.lesson.surpriseBoy.name} says.")
                                )
                                1 -> Text(Text.Line("You ask ${game.lesson.surpriseBoy.name} if you can pee again."),
                                        Text.Line("No, you can't pee in a cabin. I want you to wet yourself. You're doing it now."),
                                        Text.Line("${game.lesson.surpriseBoy.name} demands.")
                                )
                                2 -> Text(Text.Line("You ask if you can pee again desperately."),
                                        Text.Line("No, you can't pee in a cabin. You will wet yourself right now,"),
                                        Text.Line("${game.lesson.surpriseBoy.name} demands.")
                                )
                                else -> throw IllegalStateException("Character asked to pee after 2 failed attempts")
                            },
                        {
                            if (restroomAllowed_surprise)
                                game.character.bladder.gameState!!.fullness = 0.0
                            else
                                game.lesson.surpriseBoy.timesPeeDenied++
                        },
                        if (restroomAllowed_surprise)
                            StageID.END_GAME
                        else
                            if (game.lesson.surpriseBoy.timesPeeDenied < 1)
                                StageID.SURPRISE_WET_PRESSURE
                            else
                                StageID.SURPRISE_DIALOGUE,
                        score = if (restroomAllowed_surprise)
                            when (game.lesson.surpriseBoy.timesPeeDenied) {
                                0 -> 40
                                1 -> 60
                                2 -> 80
                                else -> throw IllegalStateException("Character asked to pee after 2 failed attempts")
                            } else 0
                ),
                StageID.SURPRISE_WET_VOLUNTARY to Stage(
                        Text(
                                Text.Line("Alright, as you say.,", true),
                                Text.Line("you say to ${game.lesson.surpriseBoy.name} with a defeated sigh."),
                                Text.Line("Whatever, I really can't hold it anymore anyways...", true)
                        ),
                        nextStage = StageID.SURPRISE_WET_VOLUNTARY2
                ),
                StageID.SURPRISE_WET_VOLUNTARY2 to Stage(
                        Text(
                                Text.Line("You feel the warm pee stream"),
                                Text.Line(when (game.character.gameState!!.wearMode) {
                                    Wear.Mode.BOTH -> "filling your ${game.character.gameState!!.undies.insert()} " +
                                            "and darkening your ${game.character.gameState!!.lower.insert()}."
                                    Wear.Mode.LOWER -> "filling your ${game.character.gameState!!.lower.insert()}."
                                    Wear.Mode.UNDIES -> "filling your ${game.character.gameState!!.undies.insert()}."
                                    Wear.Mode.NONE -> "running down your legs."
                                })
                        ),
                        {
                            game.character.bladder.gameState!!.fullness = 0.0
                        },
                        StageID.END_GAME
                ),
                StageID.SURPRISE_WET_PRESSURE to Stage(
                        Text(
                                Text.Line("${game.lesson.surpriseBoy.name} pressed your bladder violently..."),
                                Text.Line("Ouch... The sudden pain flash passes through your bladder..."),
                                Text.Line("You try to hold the pee back, but you just can't."),
                                Text.Line("You feel the warm pee stream"),
                                Text.Line(when (game.character.gameState!!.wearMode) {
                                    Wear.Mode.BOTH -> "filling your ${game.character.gameState!!.undies.insert()} " +
                                            "and darkening your ${game.character.gameState!!.lower.insert()}."
                                    Wear.Mode.LOWER -> "filling your ${game.character.gameState!!.lower.insert()}."
                                    Wear.Mode.UNDIES -> "filling your ${game.character.gameState!!.undies.insert()}."
                                    Wear.Mode.NONE -> "running down your legs."
                                }),
                                Text.Line("You close your eyes and ease your sphincter off"),
                                Text.Line("and feel the pee stream become much stronger.")
                        ),
                        {
                            game.character.bladder.gameState!!.fullness = 0.0
                        },
                        StageID.END_GAME
                )
        )

        private fun isRestroomAllowed() = Random().nextInt(100) <= when (game.lesson.teacher.timesPeeDenied) {
            0 -> 40
            1 -> 10
            2 -> 25
            3 -> 7
            else -> throw IllegalStateException("Character asked to pee after 3 failed attempts")
        }

        private fun isRestroomAllowed_surprise() = Random().nextInt(100) <= when (game.lesson.surpriseBoy.timesPeeDenied) {
            0 -> 10
            1 -> 5
            2 -> 2
            else -> throw IllegalStateException("Character asked to pee after 2 failed attempts")
        }
    }
}