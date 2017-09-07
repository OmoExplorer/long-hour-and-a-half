package omo

import java.util.*
import javax.swing.JOptionPane

class Stage(
        val text: Text? = null,
        val operations: () -> Unit = {},
        nextStage: String? = null,
        val actionGroupName: String? = null,
        val actions: List<Action?>? = null,
        val duration: Int = 0, val score: Int = 0
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
    }

    companion object {
        lateinit var game: ALongHourAndAHalf
        private var restroomAllowed: Boolean = isRestroomAllowed()
        val map: Map<String, Stage> = mapOf(
                "LEAVE_BED" to Stage(
                        Text(
                                Text.Line("Wh-what? Did I forget to set my alarm?!", true),
                                Text.Line("""You cry,
                                |tumbling out of bed and feeling an instant jolt from your bladder.""".trimMargin()),
                                //TODO: What if bladder isn't so full to give a jolt?
                                Text.Line(when (game.character.gameState.wearMode) {
                                    Wear.Mode.BOTH -> """You hurriedly slip on some
                                    |${game.character.gameState.undies.insert()} and
                                    |${game.character.gameState.lower.insert()},""".trimMargin()
                                    Wear.Mode.LOWER -> """You hurriedly slip on some
                                    |${game.character.gameState.lower.insert()},""".trimMargin()
                                    Wear.Mode.UNDIES -> "You hurriedly slip on ${game.character.gameState.undies.insert()},"
                                    Wear.Mode.NONE -> ""
                                })
                        ),
                        nextStage = "LEAVE_HOME",
                        duration = 1
                ),
                "LEAVE_HOME" to Stage(
                        Text(
                                Text.Line("""Just looking at the clock again in disbelief
                                    |adds a redder tint to your cheeks.""".trimMargin()),
                                Text.Line("""Paying much less attention to your daily routine,
                                    |you quickly run down the stairs,
                                    |get a small glass of orange juice and chug it.""".trimMargin()),
                                Text.Line("""The cold drink brings a chill down your spine
                                    |as you collect your things and rush out the door to school.""".trimMargin())
                        ),
                        nextStage = "GO_TO_CLASS",
                        duration = 1
                ),
                "GO_TO_CLASS" to Stage(
                        when (game.character.gameState.wearMode) {
                            Wear.Mode.BOTH,
                            Wear.Mode.LOWER ->
                                //Nothing is blowing in wind
                                if (game.character.gameState.lower.insert() == "skirt")
                                    Text(
                                            Text.Line("""You rush into class, your
                                                        |${game.character.gameState.lower.insert()} blowing in the wind."""
                                                    .trimMargin()),
                                            Text.Line("""Normally, you'd be worried your
                                                        |${game.character.gameState.undies.insert()} would be seen,
                                                        |but you can't worry about it right now.""".trimMargin()),
                                            Text.Line("You make it to your seat without a minute to spare.")
                                    )
                                else Text(
                                        Text.Line("""Trying your best to make up lost `lesson.time`,
                                                    |you rush into class and sit down to your seat without a minute to spare."""
                                                .trimMargin())
                                )
                            Wear.Mode.UNDIES -> Text(
                                    Text.Line("""You rush into class;
                                                |your classmates are looking at your
                                                |${game.character.gameState.undies.insert()}.""".trimMargin()),
                                    Text.Line("""You can't understand how you forgot
                                                |to even put on any lower clothing,""".trimMargin()),
                                    Text.Line("""and you know that your
                                                |${game.character.gameState.undies.insert()} have definitely been seen."""
                                            .trimMargin()),
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
                        operations = { game.character.gameState.embarrassment += 2 },
                        nextStage = "WALK_IN"
                ),
                "WALK_IN" to Stage(
                        when (game.character.gameState.wearMode) {
                            Wear.Mode.BOTH,
                            Wear.Mode.LOWER ->
                                if (game.character.gameState.lower.insert() == "skirt"
                                        || game.character.gameState.lower.insert() == "skirt and tights"
                                        || game.character.gameState.lower.insert() == "shorts") Text(
                                        Text.Line("Next `lesson.time` you run into class, ${game.character.name},", true),
                                        Text.Line("your teacher says,"),
                                        Text.Line("make sure you're wearing something less... revealing!"),
                                        Text.Line("A chuckle passes over the classroom, and you can't help but feel"),
                                        Text.Line("a tad bit embarrassed about your rush into class.")
                                ) else Text(
                                        Text.Line("Sit down, ${game.character.name}. You're running late.", true),
                                        Text.Line("your teacher says,"),
                                        Text.Line("And next `lesson.time`, don't make so much noise entering the classroom!"),
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
                            when (game.character.gameState.wearMode) {
                                Wear.Mode.BOTH,
                                Wear.Mode.LOWER -> if (game.character.gameState.lower.insert() == "skirt"
                                        || game.character.gameState.lower.insert() == "skirt and tights"
                                        || game.character.gameState.lower.insert() == "shorts") game.character.gameState.embarrassment += 5
                                Wear.Mode.UNDIES,
                                Wear.Mode.NONE -> game.character.gameState.embarrassment += 25
                            }
                        },
                        nextStage = "SIT_DOWN"
                ),
                "SIT_DOWN" to Stage(
                        Text(
                                Text.Line("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of"),
                                Text.Line("your bladder filling as the liquids you drank earlier start to make their way down.")
                        ),
                        {
                            game.scorer += game.character.gameState.embarrassment.toInt()
                        },
                        "ASK_ACTION", null
                ),
                "ASK_ACTION" to Stage(
                        game.getBladderStatus(),
                        nextStage = if (game.random.nextInt(20) == 5) "CALLED_ON" else null,
                        actionGroupName = "What now?",
                        actions = listOf<Action?>(
                                when (game.timesPeeDenied) {
                                    0 -> Action("Ask the teacher to go pee", "ASK_TO_PEE_THOUGHTS")
                                    1 -> Action("Ask the teacher to go pee again", "ASK_TO_PEE_THOUGHTS")
                                    2 -> Action("Try to ask the teacher again", "ASK_TO_PEE_THOUGHTS")
                                    3 -> Action("Take a chance and ask the teacher (RISKY)", "ASK_TO_PEE_THOUGHTS")
                                    else -> null
                                },
                                if (!game.`character.gameState.cornered`) {
                                    if (game.character.gender == Gender.FEMALE) {
                                        Action("Press on your crotch", "HOLD_CROTCH")
                                    } else {
                                        Action("Squeeze your penis", "HOLD_CROTCH")
                                    }
                                } else null,

                                Action("Rub thighs", "RUB_THIGHS"),
                                if ((game.character.bladder.gameState as Bladder.GameState).fullness >= 100)
                                    Action("Give up and pee yourself", "GIVE_UP_THOUGHTS")
                                else null,

                                if (game.hardcore)
                                    Action("Drink water", "DRINK_WATER")
                                else
                                    null,

                                Action("Just wait", Stage(nextStage = "ASK_ACTION", duration = 3)),
                                Action("Cheat (will reset your score)", "ASK_CHEAT")
                        ),
                        duration = 3
                ),
                "CALLED_ON" to Stage(
                        Text(
                                Text.Line("${game.character.name}, why don't you come up to the board and solve this problem?,", true),
                                Text.Line("says the teacher. Of course, you don't have a clue how to solve it."),
                                Text.Line("You make your way to the front of the room and act lost, knowing you'll be stuck"),
                                Text.Line("up there for a while as the teacher explains it."),
                                Text.Line("Well, you can't dare to hold yourself now...")
                        ),
                        nextStage = "ASK_ACTION",
                        duration = 5, score = 5
                ),
                "ASK_TO_PEE_THOUGHTS" to Stage(
                        Text(
                                Text.Line("You think to yourself:"),
                                Text.Line("I don't think I can hold it until class ends!", true),
                                Text.Line("I don't have a choice, I have to ask the teacher...", true)
                        ),
                        nextStage = "ASK_TO_PEE"
                ),
                "ASK_TO_PEE" to Stage(
                        Text(
                                Text.Line(
                                        when (game.timesPeeDenied) {
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
                                            when (game.timesPeeDenied) {
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
                                when (game.timesPeeDenied) {
                                    0 -> game.scorer /= 5
                                    1 -> game.scorer /= 3
                                    2 -> game.scorer /= 2
                                    3 -> game.scorer /= 1.5
                                }
                            } else
                                game.timesPeeDenied++
                            restroomAllowed = isRestroomAllowed()
                        },
                        if (restroomAllowed)
                            "PEE_SCHOOL_RESTROOM"
                        else
                            "ASK_ACTION"
                ),
                "HOLD_CROTCH" to Stage(
                        Text(
                                Text.Line("You don't think anyone will see you doing it,"),
                                Text.Line("so you take your hand and hold yourself down there."),
                                Text.Line("It feels a little better for now.")
                        ),
                        {
                            game.rechargeSphPower(20)
                        },
                        if ((Random().nextInt(100) <= 15 + game.`lesson.Classmates.holdingAwareness`) && game.hardcore) {
                            "CAUGHT"
                        } else {
                            "ASK_ACTION"
                        },
                        duration = 3
                ),
                "RUB_THIGHS" to Stage(
                        Text(
                                Text.Line("You need to go, and it hurts, but you just"),
                                Text.Line("can't bring yourself to risk getting caught with your hand between"),
                                Text.Line("your legs. You rub your thighs hard but it doesn't really help.")
                        ),
                        {
                            game.rechargeSphPower(2)
                        },
                        if ((Random().nextInt(100) <= 3 + game.`lesson.Classmates.holdingAwareness`) && game.hardcore) {
                            "CAUGHT"
                        } else {
                            "ASK_ACTION"
                        },
                        duration = 3
                ),
                "GIVE_UP_THOUGHTS" to Stage(
                        Text(
                                Text.Line("You're absolutely desperate to pee, and you think you'll"),
                                Text.Line("end up peeing yourself anyway, so it's probably best to admit"),
                                Text.Line("defeat and get rid of the painful ache in your bladder.")
                        ),
                        nextStage = "GIVE_UP"
                ),
                "GIVE_UP" to Stage(
                        Text(
                                Text.Line("You get tired of holding all the urine in your aching bladder,"),
                                Text.Line(when (game.character.gameState.wearMode) {
                                    Wear.Mode.BOTH,
                                    Wear.Mode.UNDIES -> "and you decide to give up and pee in your ${game.character.gameState.undies.insert()}."
                                    Wear.Mode.LOWER -> "and you decide to give up and pee in your ${game.character.gameState.lower.insert()}."
                                    Wear.Mode.NONE -> "and you decide to give up and pee where you are."
                                })
                        ),
                        {
                            game.offsetEmbarassment(80)
                        },
                        "WET"
                ),
                "ACCIDENT" to Stage(
                        Text(
                                Text.Line("You can't help it.. No matter how much pressure you use, the leaks won't stop."),
                                Text.Line("Despite all this, you try your best, but suddenly you're forced to stop."),
                                Text.Line("You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere."),
                                Text.Line("But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.")
                        ),
                        nextStage = "WET"
                ),
                "WET" to Stage(
                        when (game.character.gameState.wearMode) {
                            Wear.Mode.BOTH -> Text(
                                    Text.Line("Before you can move an inch, pee quickly soaks through your " + game.character.gameState.undies.insert() + ","),
                                    Text.Line("floods your " + game.character.gameState.lower.insert() + ", and streaks down your legs."),
                                    Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                            Wear.Mode.LOWER -> Text(
                                    Text.Line("Before you can move an inch, pee quickly darkens your " + game.character.gameState.lower.insert() + " and streaks down your legs."),
                                    Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                            Wear.Mode.UNDIES -> Text(
                                    Text.Line("Before you can move an inch, pee quickly soaks through your " + game.character.gameState.undies.insert() + ", and streaks down your legs."),
                                    Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                            )
                            Wear.Mode.NONE -> if (game.`character.gameState.cornered`)
                                Text(
                                        Text.Line("The heavy pee jets are hitting the floor and loudly leaking out from your " + game.character.gameState.undies.insert() + "."),
                                        Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                                )
                            else
                                Text(
                                        Text.Line("The heavy pee jets are hitting the seat and loudly leaking out from your " + game.character.gameState.undies.insert() + "."),
                                        Text.Line("A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                                )
                        },
                        {
                            game.emptyBladder()
                            game.character.gameState.embarrassment += 100
                        },
                        "POST_WET"
                ),
                "POST_WET" to Stage(
                        if (game.`lesson.teacher.stay`)
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
                        nextStage = "GAME_OVER"
                ),
                "GAME_OVER" to Stage(
                        when (game.character.gameState.wearMode) {
                            Wear.Mode.BOTH -> Text(
                                    Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                    Text.Line("Your ${game.character.gameState.lower.insert()} and ${game.character.gameState.undies.insert()} are both clinging to your skin, a sign of your failure..."),
                                    Text.Line("...unless, of course, you meant for this to happen?"),
                                    Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                    Text.Line("Game over!")
                            )
                            Wear.Mode.LOWER -> Text(
                                    Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                    Text.Line("Your ${game.character.gameState.lower.insert()} is clinging to your skin, a sign of your failure..."), //TODO: Add "is/are" depending on character.gameState.lower clothes type
                                    Text.Line("...unless, of course, you meant for this to happen?"),
                                    Text.Line("No, nobody would be as sadistic as that, especially to themselves..."),
                                    Text.Line("Game over!")
                            )
                            Wear.Mode.UNDIES -> Text(
                                    Text.Line("No matter how hard you tried... It doesn't seem to matter, even to think about it..."),
                                    Text.Line("Your ${game.character.gameState.undies.insert()} are clinging to your skin, a sign of your failure..."),
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
                "END_GAME" to Stage(
                        Text(Text.Line.empty),
                        {
                            val scoreText = "Your score: ${game.scorer.score}"

                            JOptionPane.showMessageDialog(game.gameFrame, scoreText)
                            game.gameFrame.btnNext.isVisible = false
                        }
                ),
                "CAUGHT" to Stage(
                        when (game.timesCaught) {
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
                            game.offsetEmbarassment(when (game.timesCaught) {
                                0 -> 3
                                1 -> 8
                                2 -> 12
                                else -> 20
                            })
                            game.`lesson.Classmates.holdingAwareness` += 5
                            game.timesCaught++
                        },
                        "ASK_ACTION",
                        score = when (game.timesCaught) {
                            0 -> 3
                            1 -> 8
                            2 -> 12
                            3 -> 20
                            else -> 20
                        }
                )
        )

        private fun isRestroomAllowed() = Random().nextInt(100) <= when (game.timesPeeDenied) {
            0 -> 40
            1 -> 10
            2 -> 25
            3 -> 7
            else -> throw IllegalStateException("Character asked to pee after 3 failed attempts")
        }
    }
}