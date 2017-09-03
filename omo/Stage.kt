package omo

//TODO text: String -> text: Stage.Text
class Stage(
        val text: Text,
        val operations: () -> Unit = {},
        nextStage: String? = null,
        val actions: List<Action>? = null,
        val duration: Int = 0
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
        val map = mapOf(
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
                        duration = 1,
                        nextStage = "LEAVE_HOME"
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
                        duration = 1,
                        nextStage = "GO_TO_CLASS"
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
                                        Text.Line("""Trying your best to make up lost time,
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
                                        Text.Line("Next time you run into class, ${game.character.name},", true),
                                        Text.Line("your teacher says,"),
                                        Text.Line("make sure you're wearing something less... revealing!"),
                                        Text.Line("A chuckle passes over the classroom, and you can't help but feel"),
                                        Text.Line("a tad bit embarrassed about your rush into class.")
                                ) else Text(
                                        Text.Line("Sit down, ${game.character.name}. You're running late.", true),
                                        Text.Line("your teacher says,"),
                                        Text.Line("And next time, don't make so much noise entering the classroom!"),
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
                        "ASK_ACTION"
                ),
                "ASK_ACTION" to Stage(
                        game.getBladderStatus(),
                        operations = {
                            if (game.random.nextInt(20) == 5) {
                                game.nextStage = Stage.map["CALLED_ON"]
                                game.handleNextClicked()
                            }
                        },
                        duration = 3
                ),
                "CALLED_ON" to Stage()
        )
    }
}