/*
 * A Long Hour and a Half v2.0
 *
 * Version history:
 * 0.1    Default game mechanics shown. No interaction, playability or customization.
 *        Not all game mechanics even implemented, purely a showcase program.
 *
 * 0.2    MASSIVE REWRITE! (Thanks to Anna May! This is definitely the format I want!)
 *
 * 1.0    Added interactivity, improved code, added hardcore mode(this isn't working now) and... cheats!
 *
 * 1.1    Reintegrated the two versions
 *
 * 1.2    New hardcore features:
 *            Classmates can be aware that you've to go
 *            Less bladder capacity
 *        Improved bladder: it's more realistic now
 *        Balanced pee holding methods
 *        New game options frame
 *        Even more clothes
 *        Cleaned and documented code a bit
 *
 * 1.3    Bug fixes
 *        Interface improvements
 *        Game text refining
 *
 * 1.3.1  Bug fixes
 *
 * 1.4    Character can drink during the class
 *        Saving/loading games
 *        Bug fixes
 *
 * 1.4.1  Bug fixes
 *
 * 2.0    Total rewrite. Splitting the game into separate modules for better understandability.
 *
 *
 * A Long Hour and a Half is a narrative game where
 * one must make it through class with a rather full bladder.
 * Players have choices that can hurt and help character's ability to hold.
 *
 * If you have any questions, bugs or suggestions,
 * create an issue or a pull request on GitHub:
 * https://github.com/javabird25/long-hour-and-a-half/
 *
 * Developers' usernames table:
 * Code documentation | GitHub                              | Omorashi.org
 * -------------------|-------------------------------------|----------------------------------------------------------
 * Rosalie Elodie     | REDev987532(github.com/REDev987532) | Justice (omorashi.org/profile/25796-justice/)
 * JavaBird           | javabird25 (github.com/javabird25)  | FromRUSForum (omorashi.org/profile/89693-fromrusforum/)
 * Anna May           | AnnahMay (github.com/AnnahMay)      | Anna May (omorashi.org/profile/10087-anna-may/)
 * notwillnotcast     | ?                                   | thisonestays (omorashi.org/profile/14935-notwillnotcast/)
 *
 * While this is created by Rosalie Dev, she allows it to be posted
 * freely, so long as she's credited. She also states that this program is
 * ABSOLUTELY FREE, not to mention she hopes you enjoy ^_^
 */

package longHourAndAHalf

import longHourAndAHalf.WearType.*
import longHourAndAHalf.ui.SaveFileChooser
import longHourAndAHalf.ui.StandardGameUI
import longHourAndAHalf.ui.WearFileChooser
import longHourAndAHalf.ui.setupFramePre
import java.io.*
import java.util.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane

private val random = Random()

/**
 * @return random item from this array
 */
fun <T> Array<T>.randomItem(): T {
    val randomIndex = random.nextInt(this.size)
    return this[randomIndex]
}

/**
 * @return random item from this list
 */
fun <T> List<T>.randomItem(): T {
    val randomIndex = random.nextInt(this.size)
    return this[randomIndex]
}

/**
 * @return `true` with a specified chance, `false` otherwise.
 * @param probability chance to return `true` in percents.
 */
fun chance(probability: Int) = chance(probability.toDouble())

/**
 * @return `true` with a specified chance, `false` otherwise.
 * @param probability chance to return `true` in percents.
 */
fun chance(probability: Double) = random.nextInt(100) < probability

/**
 * Simple utility function which clamps the given value to be strictly
 * between the min and max values.
 */
fun clamp(min: Double, value: Double, max: Double): Double {
    if (value < min) return min
    return if (value > max) max else value
}

@Suppress("KDocMissingDocumentation")
/**
 * Game core which is used for communication between modules.
 */
class Core {
    /**
     * Game character.
     */
    val character: Character

    /**
     * Score counter.
     */
    val scorer: Scorer

    /**
     * Whether hardcore mode is enabled.
     * Hardcore mode features:
     * - Teacher never lets you out
     * - It's harder to hold pee
     * - You may get caught holding pee
     */
    var hardcore: Boolean

    /**
     * Game interface frame.
     */
    val ui: StandardGameUI

    /**
     * Virtual game world data.
     */
    val world: World

    /**
     * Data about a school day. Holds random number (from 1 to 3) of random lessons and classmates data.
     */
    val schoolDay: SchoolDay

    /**
     * Data about cheats.
     */
    val cheatData: CheatData

    val flags: PlotFlags

    val plot: Plot

    /**
     * List of cheats.
     */
    private val cheatList = listOf(
            "Go to the corner",
            "Stay after class",
            "Pee in a bottle",
            "End class right now",
            "Calm the teacher down",
            "Raise your hand",
            "Make your pee disappear regularly",
            "Set your incontinence level",
            "Toggle hardcore mode",
            "Set bladder fullness"
    )

    private fun openCustomWear(type: WearType): Wear? {
        fun abort(message: String) {
            JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)
            ui.dispose()
            setupFramePre.main(arrayOf(""))
        }

        val fcWear = WearFileChooser()
        fcWear.dialogTitle = when (type) {
            UNDERWEAR -> "Open an underwear file"
            OUTERWEAR -> "Open an outerwear file"
            BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE wear type isn't supported")
        }

        var openedWear: Wear? = null
        if (fcWear.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            val file = fcWear.selectedFile
            try {
                val fin = FileInputStream(file)
                val ois = ObjectInputStream(fin)
                openedWear = ois.readObject() as Wear
                if (openedWear.type == type) {
                    abort("Wrong wear type.")
                }
            } catch (e: Exception) {
                abort("File error.")
            }
        }

        return openedWear
    }

    private fun setupWear(wear: Wear, type: WearType): Wear? {
        val processedWear = when (wear.name) {
            "Custom" -> openCustomWear(type)
            "Random" -> Wear.getRandom(type)
            else -> wear
        }

        processedWear?.let {
            if (processedWear.name == "No underwear" || processedWear.name == "No outerwear")
                processedWear.insert = if (character.gender == Gender.FEMALE)
                    "crotch"
                else
                    "penis"
        }

        return processedWear
    }

    constructor(character: Character, hardcore: Boolean) {
        this.hardcore = hardcore
        this.character = character
        this.character.finishSetup(this)
        this.character.bladder.finishSetup(this)
        world = World(this)
        world.core = this
        cheatData = CheatData()
        schoolDay = SchoolDay(this)
        flags = PlotFlags()
        scorer = Scorer()

        fun onIncorrectWearSelected(type: WearType): Wear {
            JOptionPane.showMessageDialog(
                    null,
                    "Incorrect " + when (type) {
                        OUTERWEAR -> "outerwear"
                        UNDERWEAR -> "underwear"
                        BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE argument isn't supported")
                    } + " selected. Setting random instead.",
                    "Incorrect underwear",
                    JOptionPane.WARNING_MESSAGE
            )
            return Wear.getRandom(UNDERWEAR)
        }

        fun Wear.setupColor(color: WearColor) {
            this.color = if (!isMissing) {
                if (color != WearColor.RANDOM)
                    color
                else
                    WearColor.values().randomItem()
            } else WearColor.NONE
        }

        ui = StandardGameUI(this)

        plot = Plot(this)

        this.character.undies = setupWear(character.undies, UNDERWEAR) ?: onIncorrectWearSelected(UNDERWEAR)
        this.character.lower = setupWear(character.lower, OUTERWEAR) ?: onIncorrectWearSelected(OUTERWEAR)

        this.character.undies.setupColor(this.character.undies.color)
        this.character.lower.setupColor(this.character.lower.color)

        ui.finishSetup()

        //Scoring fullness at start
        scorer.countOut("Bladder at start - ${this.character.bladder.fullness}%", this.character.bladder.fullness,
                ArithmeticAction.ADD)

        //Scoring incontinence
        scorer.countOut(
                "Incontinence - ${this.character.bladder.incontinence}x", this.character.bladder.incontinence,
                ArithmeticAction.MULTIPLY, true
        )

        if (this.hardcore) {
            character.bladder.maxBladder = 100
            ui.hardcoreModeToggled(true)
            scorer.countOut("Hardcore", 2, ArithmeticAction.MULTIPLY)
        }

        ui.drynessBar.maximum = this.character.maximalDryness.toInt()
        ui.drynessBar.value = this.character.dryness.toInt()

        ui.showBladderAndTime()

        handleNextClicked()

        postConstructor()
    }

    constructor(save: Save) {
        hardcore = save.hardcore
        character = save.character
        character.finishSetup(this)
        character.bladder.finishSetup(this)
        world = save.world
        world.core = this
        flags = save.flags
        plot = save.plot
        scorer = save.scorer
        schoolDay = save.schoolDay
        cheatData = save.cheatData

        ui = StandardGameUI(this)
        ui.finishSetup()

        ui.drynessBar.maximum = this.character.maximalDryness.toInt()
        ui.drynessBar.value = this.character.dryness.toInt()

        ui.showBladderAndTime()

        ui.forcedTextChange(Text("Loading complete. Click \"Next\" to continue the game."))

//        handleNextClicked()

        postConstructor()
    }

    private fun postConstructor() {
        //Making fullness smaller in the hardcore mode, adding hardcore label
        if (hardcore) ui.hardcoreModeToggled(true)

        gameStartSave = Save(this)   //Saving game for a reset

        ui.isVisible = true     //Displaying the frame
    }

    fun handleNextClicked() {/*
        when (plot.nextStageID) {
            LEAVE_BED -> {
                //Making line 1 italic
                ui.setLinesAsDialogue(1)
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing)
                    //Both lower clothes and undies
                    {
                        ui.setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your fullness.",
                                "You hurriedly slip on some " +
                                        "${character.undies.insert} and ${character.lower.insert},",
                                "not even worrying about what covers your chest.")
                    } else
                    //Lower clothes only
                    {
                        ui.setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your fullness.",
                                "You hurriedly slip on some ${character.lower.insert}, " +
                                        "quick to cover your ${character.undies.insert},",
                                "not even worrying about what covers your chest.")
                    }
                } else {
                    if (!character.undies.isMissing)
                    //Undies only
                    {
                        ui.setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your fullness.",
                                "You hurriedly slip on ${character.undies.insert},",
                                "not even worrying about what covers your chest and legs.")
                    } else
                    //No clothes at all
                    {
                        ui.setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your fullness.",
                                "You are running downstairs fully naked.")
                    }
                }
                world.time += 1

                //Setting the next stage to "Leaving home"
                plot.nextStageID = LEAVE_HOME
            }

            LEAVE_HOME -> {
                ui.setText("Just looking at the clock again in disbelief adds a redder tint to your cheeks.",
                        "",
                        "Paying much less attention to your daily routine, you quickly run down the stairs, " +
                                "get a small glass of orange juice and chug it.",
                        "",
                        "The cold drink brings a chill down your spine " +
                                "as you collect your things and rush out the door to school.")

                world.time += 1
                character.embarrassment += 3
                character.belly += 10.0

                plot.nextStageID = GO_TO_CLASS
            }

            GO_TO_CLASS -> {
                //Displaying all values
                ui.showBladderAndTime()

                if (!character.lower.isMissing) {
                    //Skirt blowing in the wind
                    if (character.lower.insert == "skirt") {
                        ui.setText("You rush into class, your ${character.lower.insert} blowing in the wind.",
                                "",
                                "Normally, you'd be worried your ${character.undies.insert} would be seen, " +
                                        "but you can't worry about it right now.",
                                "You make it to your seat without a minute to spare.")
                    } else {
                        //Nothing is blowing in wind
                        ui.setText("Trying your best to make up lost time, " +
                                "you rush into class and sit down to your seat without a minute to spare.")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        ui.setText("You rush into class; " +
                                "your classmates are looking at your ${character.undies.insert}.",
                                "You can't understand how you forgot to even put on any lower clothing,",
                                "and you know that your ${character.undies.insert} have definitely been seen.",
                                "You make it to your seat without a minute to spare.")
                    } else {
                        if (character.gender == Gender.FEMALE) {
                            ui.setText("You rush into class; your classmates are looking at your pussy and boobs.",
                                    "Guys are going mad and doing nothing except looking at you.",
                                    "You can't understand how you dared to come to school naked.",
                                    "You make it to your seat without a minute to spare.")
                        } else {
                            ui.setText("You rush into class; your classmates are looking at your penis.",
                                    "Girls are really going mad and doing nothing except looking at you.",
                                    "You can't understand how you dared to come to school naked.",
                                    "You make it to your seat without a minute to spare.")
                        }
                    }
                }

                character.embarrassment += 2
                plot.nextStageID = WALK_IN
            }

            WALK_IN -> {
                //If lower clothes is a skirt
                if (character.lower.insert == "skirt" || character.lower.insert == "skirt and tights"
                        || character.lower.insert == "skirt and tights") {
                    ui.setLinesAsDialogue(1, 3)
                    ui.setText("Next time you run into class, ${character.name},",
                            "your teacher says,",
                            "make sure you're wearing something less... revealing!",
                            "A chuckle passes over the classroom, and you can't help but feel a",
                            "tad bit embarrassed about your rush into class.")
                    character.embarrassment += 5
                } else
                //No outerwear
                {
                    if (character.lower.isMissing) {
                        ui.setLinesAsDialogue(1)
                        ui.setText("WHAT!? YOU CAME TO SCHOOL NAKED!?",
                                "your teacher shouts in disbelief.",
                                "",
                                "A chuckle passes over the classroom, " +
                                        "and you can't help but feel extremely embarrassed",
                                "about your rush into class, let alone your nudity")
                        character.embarrassment += 25
                    } else {
                        ui.setLinesAsDialogue(1, 3)
                        ui.setText("Sit down, ${character.name}. You're running late.",
                                "your teacher says,",
                                "And next time, don't make so much noise entering the classroom!",
                                "A chuckle passes over the classroom, " +
                                        "and you can't help but feel a tad bit embarrassed",
                                "about your rush into class.")
                    }
                }
                plot.nextStageID = SIT_DOWN
            }

            SIT_DOWN -> {
                ui.setText("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of",
                        "your fullness filling as the liquids you drank earlier start to make their way down.")
                world.time += 3
                plot.nextStageID = ASK_ACTION
                scorer.countOut(
                        "Embarrassment at start - ${character.bladder.incontinence} pts",
                        character.embarrassment,
                        ArithmeticAction.ADD
                )
            }

            ASK_ACTION -> {
                //Called by teacher if unlucky
                plot.actionList.clear()
                if (random.nextInt(20) == 5) {
                    ui.setText("Suddenly, you hear the teacher call your name.")
                    plot.nextStageID = CALLED_ON
                    return
                }

                //Bladder: 0-20
                if (character.bladder.fullness <= 20) {
                    ui.setText("Feeling bored about the day, and not really caring about the class too much,",
                            "you look to the clock, watching the minutes tick by.")
                }
                //Bladder: 20-40
                if (character.bladder.fullness > 20 && character.bladder.fullness <= 40) {
                    ui.setText("Having to pee a little bit,",
                            "you look to the clock, " +
                                    "watching the minutes tick by and wishing the lesson to get over faster.")
                }
                //Bladder: 40-60
                if (character.bladder.fullness > 40 && character.bladder.fullness <= 60) {
                    ui.setText("Clearly having to pee,",
                            "you impatiently wait for the lesson end.")
                }
                //Bladder: 60-80
                if (character.bladder.fullness > 60 && character.bladder.fullness <= 80) {
                    ui.setLinesAsDialogue(2)
                    ui.setText("You feel the rather strong pressure in your fullness, " +
                            "and you're starting to get even more desperate.",
                            "Maybe I should ask teacher to go to the restroom? It hurts a bit...")
                }
                //Bladder: 80-100
                if (character.bladder.fullness > 80 && character.bladder.fullness <= 100) {
                    ui.setLinesAsDialogue(1, 4)
                    ui.setText("Keeping all that urine inside will become impossible very soon.",
                            "You feel the terrible pain and pressure in your fullness, " +
                                    "and you can almost definitely say ",
                            "you haven't needed to pee this badly in your life.",
                            "Ouch, it hurts a lot... I must do something about it now, or else...")
                }
                //Bladder: 100-130
                if (character.bladder.fullness > 100 && character.bladder.fullness <= 130) {
                    ui.setLinesAsDialogue(1, 3)
                    if (character.gender == Gender.FEMALE) {
                        ui.setText("This is really bad...",
                                "You know that you can't keep it any longer and " +
                                        "you may wet yourself in any moment and oh,",
                                "You can clearly see your fullness as it bulging.",
                                "Ahhh... I cant hold it anymore!!!",
                                "Even holding your crotch doesn't seems to help you to keep it in.")
                    } else {
                        ui.setText("This is really bad...",
                                "You know that you can't keep it any longer and " +
                                        "you may wet yourself in any moment and oh,",
                                "You can clearly see your fullness as it bulging.",
                                "Ahhh... I cant hold it anymore!!!",
                                "Even squeezing your penis doesn't seems to help you to keep it in.")
                    }
                }

                ui.showActionUI("What now?")

                //Adding action choices
                when (schoolDay.lesson.timesPeeDenied) {
                    0 -> plot.actionList.add("Ask the teacher to go pee")
                    1 -> plot.actionList.add("Ask the teacher to go pee again")
                    2 -> plot.actionList.add("Try to ask the teacher again")
                    3 -> plot.actionList.add("Take a chance and ask the teacher (RISKY)")
                    else -> plot.actionList.add("[Unavailable]")
                }

                if (!character.cornered) {
                    if (character.gender == Gender.FEMALE) {
                        plot.actionList.add("Press on your crotch")
                    } else {
                        plot.actionList.add("Squeeze your penis")
                    }
                } else {
                    plot.actionList.add("[Unavailable]")
                }

                plot.actionList.add("Rub thighs")

                if (character.bladder.fullness >= 100) {
                    plot.actionList.add("Give up and pee yourself")
                } else {
                    plot.actionList.add("[Unavailable]")
                }
                if (hardcore) {
                    plot.actionList.add("Drink water")
                } else {
                    plot.actionList.add("[Unavailable]")
                }
                plot.actionList.add("Just wait")
                plot.actionList.add("Cheat (will reset your score)")

                //Loading the choice array into the action selector
                ui.listChoice.setListData(plot.actionList.toTypedArray())
                plot.nextStageID = CHOSE_ACTION
                world.time += 3
            }

            CHOSE_ACTION -> {
                plot.nextStageID = ASK_ACTION
                if (ui.listChoice.isSelectionEmpty || ui.listChoice.selectedValue == "[Unavailable]") {
                    handleNextClicked()
                    return
                }

                //Hiding the action selector and doing action job
                when (ui.hideActionUI()) {
                //Ask the teacher to go pee
                    0 -> {
                        plot.nextStageID = ASK_TO_PEE
                        ui.setLinesAsDialogue(2, 3)
                        ui.setText("You think to yourself:",
                                "I don't think I can hold it until class ends!",
                                "I don't have a choice, I have to ask the teacher...")
                    }

                /*
                 * Press on crotch/squeeze penis
                 * 3 minutes
                 * -2 fullness
                 * Detection chance: 15
                 * Effectiveness: 0.4
                 * =========================
                 * 3 minutes
                 * +20 sph. power
                 * Detection chance: 15
                 * Future effectiveness: 4
                 */
                    1 -> {
                        ui.setText("You don't think anyone will see you doing it,",
                                "so you take your hand and hold yourself down there.",
                                "It feels a little better for now.")

                        character.bladder.sphincterPower += 20
                        world.time += 3

                        //Chance to be caught by classmates in hardcore mode
                        plot.nextStageID = if ((random.nextInt(100) <= 15 + schoolDay.classmatesAwareness) and hardcore) {
                            CAUGHT
                        } else {
                            ASK_ACTION
                        }
                    }

                /*
                     * Rub thighs
                     * 3 + 3 = 6 minutes
                     * -0.2 fullness
                     * Detection chance: 3
                     * Effectiveness: 6
                     * =========================
                     * 3 + 3 = 6 minutes
                     * +2 sph. power
                     * Detection chance: 3
                     * Future effectiveness: 4
                     */
                    2 -> {
                        ui.setText("You need to go, and it hurts, but you just",
                                "can't bring yourself to risk getting caught with your hand between",
                                "your legs. You rub your thighs hard but it doesn't really help.")

                        character.bladder.sphincterPower += 2
                        world.time += 3

                        //Chance to be caught by classmates in hardcore mode
                        plot.nextStageID = if ((random.nextInt(100) <= 3 + schoolDay.classmatesAwareness) and hardcore) {
                            CAUGHT
                        } else {
                            ASK_ACTION
                        }
                    }

                //Give up
                    3 -> {
                        ui.setText("You're absolutely desperate to pee, and you think you'll",
                                "end up peeing yourself anyway, so it's probably best to admit",
                                "defeat and get rid of the painful ache in your fullness.")
                        plot.nextStageID = GIVE_UP
                    }

                //Drink water
                    4 -> {
                        ui.setText("Feeling a tad bit thirsty,",
                                "You decide to take a small sip of water from your bottle to get rid of it.")
                        plot.nextStageID = DRINK
                    }

                /*
                     * Wait
                     * =========================
                     * 3 + 2 + n minutes
                     * +(2.5n) fullness
                     * Detection chance: 1
                     * Future effectiveness: 2.4(1), 0.4(2), 0.47(30)
                     */
                    5 -> {
                        //Asking player how much to wait
                        val timeOffset: Int
                        try {
                            timeOffset = java.lang.Integer.parseInt(JOptionPane.showInputDialog("How much to wait?"))
                            if (timeOffset < 1 || timeOffset > 125) {
                                throw NumberFormatException()
                            }
                            world.time += timeOffset
                        } //Ignoring invalid output
                        catch (e: Exception) {
                            plot.nextStageID = ASK_ACTION
                            return
                        }

                        //Chance to be caught by classmates in hardcore mode
                        plot.nextStageID = if ((random.nextInt(100) <= 1 + schoolDay.classmatesAwareness) and hardcore) {
                            CAUGHT
                        } else {
                            ASK_ACTION
                        }
                    }

                //Cheat
                    6 -> {
                        ui.setText("You've got to go so bad!",
                                "There must be something you can do, right?")

                        //Zeroing points
                        cheatData.cheatsUsed = true
                        plot.nextStageID = ASK_CHEAT
                    }

                    else -> ui.setText("Bugs.")
                }
            }

            ASK_TO_PEE -> {
                when (schoolDay.lesson.timesPeeDenied) {
                    0 ->
                        //Success
                        if ((random.nextInt(100) <= 40) and !hardcore) {
                            if (!character.lower.isMissing) {
                                if (!character.undies.isMissing) {
                                    ui.setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your ${character.lower.insert} " +
                                                    "and ${character.undies.insert},",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    ui.setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your ${character.lower.insert},",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!character.undies.isMissing) {
                                    ui.setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your ${character.undies.insert},",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    ui.setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it,",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            }
                            //score *= 0.2;
                            //scoreText = scoreText.concat("\nRestroom usage during the lesson: -80% of points");
                            scorer.countOut("Restroom usage during the lesson", 80,
                                    ArithmeticAction.TAKE_PERCENT)
                            character.bladder.fullness = 0.0
                            plot.nextStageID = ASK_ACTION
                            //Fail
                        } else {
                            ui.setLinesAsDialogue(2)
                            ui.setText("You ask the teacher if you can go out to the restroom.",
                                    "No, you can't go out, the director prohibited it.",
                                    "says the teacher.")
                            schoolDay.lesson.timesPeeDenied++
                        }

                    1 -> if ((random.nextInt(100) <= 10) and !hardcore) {
                        if (!character.lower.isMissing) {
                            if (!character.undies.isMissing) {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your ${character.lower.insert} " +
                                                "and ${character.undies.insert},",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your ${character.lower.insert},",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!character.undies.isMissing) {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your ${character.undies.insert},",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it,",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        }
                        //score *= 0.22;
                        //scoreText = scoreText.concat("\nRestroom usage during the lesson: -70% of points");
                        scorer.countOut("Restroom usage during the lesson", 70, ArithmeticAction.TAKE_PERCENT)
                        character.bladder.fullness = 0.0
                        plot.nextStageID = ASK_ACTION
                    } else {
                        ui.setText("You ask the teacher again if you can go out to the restroom.",
                                "No, you can't! I already told you that the director prohibited it!",
                                "says the teacher.")
                        schoolDay.lesson.timesPeeDenied++
                    }

                    2 -> if ((random.nextInt(100) <= 30) and !hardcore) {
                        if (!character.lower.isMissing) {
                            if (!character.undies.isMissing) {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.lower.insert + " and " +
                                                character.undies.insert + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.lower.insert + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!character.undies.isMissing) {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.undies.insert + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                ui.setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it,",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        }
                        //score *= 0.23;
                        //scoreText = scoreText.concat("\nRestroom usage during the lesson: -60% of points");
                        scorer.countOut("Restroom usage during the lesson", 60, ArithmeticAction.TAKE_PERCENT)
                        character.bladder.fullness = 0.0
                        plot.nextStageID = ASK_ACTION
                    } else {
                        ui.setText("You ask the teacher once more if you can go out to the restroom.",
                                "No, you can't! Stop asking me or there will be consequences!",
                                "says the teacher.")
                        schoolDay.lesson.timesPeeDenied++
                    }

                    3 -> {
                        if ((random.nextInt(100) <= 7) and !hardcore) {
                            if (!character.lower.isMissing) {
                                if (!character.undies.isMissing) {
                                    ui.setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.lower.insert + " and " +
                                                    character.undies.insert + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    ui.setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.lower.insert + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!character.undies.isMissing) {
                                    ui.setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.undies.insert + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    ui.setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it,",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            }
                            //score *= 0.3;
                            //scoreText = scoreText.concat("\nRestroom usage during the lesson: -50% of points");
                            scorer.countOut("Restroom usage during the lesson", 50, ArithmeticAction.TAKE_PERCENT)
                            character.bladder.fullness = 0.0
                            plot.nextStageID = ASK_ACTION
                        } else {
                            if (random.nextBoolean()) {
                                ui.setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,",
                                        "yells the teacher.")
                                character.cornered = true
                                //score += 1.3 * (90 - min / 3);
                                //scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +"
                                //+ 1.3 * (90 - min / 3) + " score");
                                scorer.countOut(
                                        "Stayed on corner ${(Lesson.classEndingTime - world.time).rawMinutes} minutes",
                                        1.3 * ((Lesson.classEndingTime - world.time).rawMinutes / 3), ArithmeticAction.ADD
                                )
                                character.embarrassment += 5
                            } else {
                                ui.setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! YOU WILL WRITE LINES AFTER THE LESSON!!!,",
                                        "yells the teacher.")
                                character.embarrassment += 5
                                schoolDay.lesson.stay = true
                                ui.timeBar.maximum = 120
                                //scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
                                //score *= 1.6;
                                scorer.countOut("Wrote lines after the lesson", 60, ArithmeticAction.ADD_PERCENT)
                            }
                        }
                        schoolDay.lesson.timesPeeDenied++
                    }
                }
                plot.nextStageID = ASK_ACTION
            }

            ASK_CHEAT -> {
                ui.listChoice.setListData(cheatList.toTypedArray())
                ui.showActionUI("Select a cheat:")
                plot.nextStageID = CHOSE_CHEAT
            }

            CHOSE_CHEAT -> {
                if (ui.listChoice.isSelectionEmpty) {
                    plot.nextStageID = ASK_CHEAT
                    return
                }
                when (ui.hideActionUI()) {
                    0 -> {
                        ui.setText("You walk to the front corner of the classroom.")
                        character.cornered = true
                        plot.nextStageID = ASK_ACTION
                    }

                    1 -> {
                        ui.setText("You decide to stay after class.")
                        schoolDay.lesson.stay = true
                        ui.timeBar.maximum = 120
                        plot.nextStageID = ASK_ACTION
                    }

                    2 -> {
                        ui.setText("You see something out of the corner of your eye,",
                                "just within your reach.")
                        plot.nextStageID = USE_BOTTLE
                    }

                    3 -> {
                        ui.setLinesAsDialogue(2)
                        ui.setText("A voice comes over the loudspeaker:",
                                "All classes are now dismissed for no reason at all! Bye!",
                                "Looks like your luck changed for the better.")
                        world.time = Lesson.classEndingTime
                        plot.nextStageID = CLASS_OVER
                    }

                    4 -> {
                        ui.setText("The teacher feels sorry for you. Try asking to pee.")
                        schoolDay.lesson.timesPeeDenied = 0
                        schoolDay.lesson.stay = false
                        ui.timeBar.maximum = 90
                        character.cornered = false
                        plot.nextStageID = ASK_ACTION
                    }

                    5 -> {
                        ui.setText("You decide to raise your hand.")
                        plot.nextStageID = CALLED_ON
                    }

                    6 -> {
                        ui.setText("Suddenly, you feel like you're peeing...",
                                "but you don't feel any wetness. It's not something you'd",
                                "want to question, right?")
                        cheatData.drain = true
                        plot.nextStageID = ASK_ACTION
                    }

                    7 -> {
                        ui.setText("A friend in the desk next to you hands you a familiar",
                                "looking pill, and you take it.")
                        character.bladder.incontinence = JOptionPane.showInputDialog("How incontinent are you now?")
                                .toDouble()
                        character.bladder.maxSphincterPower = (100 / character.bladder.incontinence).toInt()
                        character.bladder.sphincterPower = character.bladder.maxSphincterPower
                        plot.nextStageID = ASK_ACTION
                    }

                    8 -> {
                        ui.setText("The teacher suddenly looks like they've had enough",
                                "of people having to pee.")
                        hardcore = !hardcore
                        plot.nextStageID = ASK_ACTION
                    }

                    9 -> {
                        ui.setText("Suddenly you felt something going on in your fullness.")
                        character.bladder.incontinence = JOptionPane.showInputDialog("How your fullness is full now?")
                                .toDouble()
                        plot.nextStageID = ASK_ACTION
                    }
                }
            }

            USE_BOTTLE -> {
                character.bladder.fullness = 0.0
                ui.setLinesAsDialogue(3)
                ui.setText("Luckily for you, you happen to have brought an empty bottle to pee in.",
                        "As quietly as you can, you put it in position and let go into it.",
                        "Ahhhhh...",
                        "You can't help but show a face of pure relief as your pee trickles down into it.")
                plot.nextStageID = ASK_ACTION
            }

            CALLED_ON -> {
                ui.setLinesAsDialogue(1)
                ui.setText(character.name + ", why don't you come up to the board and solve this problem?,",
                        "says the teacher. Of course, you don't have a clue how to solve it.",
                        "You make your way to the front of the room and act lost, knowing you'll be stuck",
                        "up there for a while as the teacher explains it.",
                        "Well, you can't dare to hold yourself now...")
                world.time += 5
                scorer.countOut("Called on the lesson", 5, ArithmeticAction.ADD)
                plot.nextStageID = ASK_ACTION
            }

            CLASS_OVER -> {
                //Special hardcore scene trigger
                if (random.nextInt(100) <= 10 && hardcore && character.gender == Gender.FEMALE) {
                    plot.nextStageID = SURPRISE
                    return
                }
                if (schoolDay.lesson.stay) {
                    plot.nextStageID = AFTER_CLASS
                    return
                }

                if (random.nextBoolean()) {
                    ui.setText("Lesson is finally over, and you're running to the restroom as fast as you can.",
                            "No, please... All cabins are occupied, and there's a line. You have to wait!")

                    scorer.countOut("Waited for a free cabin in the restroom", 3, ArithmeticAction.ADD)
                    world.time += 3
                    return
                } else {
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            ui.setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        } else {
                            ui.setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.lower.insert + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            ui.setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.undies.insert + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        } else {
                            ui.setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it,",
                                    "wearily flop down on the toilet and start peeing.")
                        }
                    }
                    plot.nextStageID = END_GAME
                }
            }

            AFTER_CLASS -> {
                if (world.time >= Lesson.classEndingTime) {
                    schoolDay.lesson.stay = false
                    plot.nextStageID = CLASS_OVER
                    return
                }

                ui.setLinesAsDialogue(1, 2, 3, 4)
                ui.setText("Hey, ${character.name}, you wanted to escape? You must stay after classes!",
                        "Please... let me go to the restroom... I can't hold it...",
                        "No, ${character.name}, you can't go to the restroom now! This will be as punishment.",
                        "And don't think you can hold yourself either! I'm watching you...")

                world.time += 3
            }

            ACCIDENT -> {
                ui.hideActionUI()
                ui.setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.",
                        "Despite all this, you try your best, but suddenly you're forced to stop.",
                        "You can't move, or you risk peeing yourself. " +
                                "Heck, the moment you stood up you thought you " +
                                "could barely move for risk of peeing everywhere.",
                        "But now.. a few seconds tick by as you try to will yourself to move, " +
                                "but soon, the inevitable happens anyways.")
                plot.nextStageID = WET
            }

            GIVE_UP -> {
                character.embarrassment += 80
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing) {
                        ui.setText("You get tired of holding all the urine in your aching fullness,",
                                "and you decide to give up and pee in your " + character.undies.insert + ".")
                    } else {
                        ui.setText("You get tired of holding all the urine in your aching fullness,",
                                "and you decided to pee in your " + character.lower.insert + ".")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        ui.setText("You get tired of holding all the urine in your aching fullness,",
                                "and you decide to give up and pee in your " + character.undies.insert + ".")
                    } else {
                        ui.setText("You get tired of holding all the urine in your aching fullness,",
                                "and you decide to give up and pee where you are.")
                    }
                }
                plot.nextStageID = WET
            }

            WET -> {
                character.bladder.fullness = 0.0
                character.embarrassment = 100
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing) {
                        ui.setText("Before you can move an inch, pee quickly soaks through your " +
                                character.undies.insert + ",",
                                "floods your " + character.lower.insert + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        ui.setText("Before you can move an inch, pee quickly darkens your " + character.lower.insert +
                                " and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        ui.setText("Before you can move an inch, pee quickly soaks through your " +
                                character.undies.insert + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        if (!character.cornered) {
                            ui.setText("The heavy pee jets are hitting the seat and loudly leaking out from your " +
                                    character.undies.insert + ".",
                                    "A large puddle quickly forms, " +
                                            "and you can't stop tears from falling down your cheeks.")
                        } else {
                            ui.setText("The heavy pee jets are hitting the floor and loudly leaking out from your " +
                                    character.undies.insert + ".",
                                    "A large puddle quickly forms, " +
                                            "and you can't stop tears from falling down your cheeks.")
                        }
                    }
                }
                plot.nextStageID = POST_WET
            }

            POST_WET -> {
                ui.setLinesAsDialogue(2)
                if (!schoolDay.lesson.stay) {
                    if (character.lower.isMissing) {
                        if (character.gender == Gender.FEMALE && character.undies.isMissing) {
                            ui.setText("People around you are laughing loudly.",
                                    character.name + " peed herself! Ahaha!!!")
                        } else {
                            if (character.gender == Gender.MALE && character.undies.isMissing) {
                                ui.setText("People around you are laughing loudly.",
                                        character.name + " peed himself! Ahaha!!!")
                            } else {
                                ui.setText("People around you are laughing loudly.",
                                        character.name + " wet h" +
                                                (if (character.gender == Gender.FEMALE) "er " else "is ") +
                                                character.undies.insert + "! Ahaha!!")
                            }
                        }
                    } else {
                        if (character.gender == Gender.FEMALE) {
                            ui.setText("People around you are laughing loudly.",
                                    character.name + " peed her " + character.lower.insert + "! Ahaha!!")
                        } else {
                            ui.setText("People around you are laughing loudly.",
                                    " peed his " + character.lower.insert + "! Ahaha!!")
                        }
                    }
                } else {
                    ui.setText("Teacher is laughing loudly.",
                            "Oh, you peed yourself? This is a great punishment.",
                            "I hope you will no longer get in the way of the lesson.")
                }
                plot.nextStageID = GAME_OVER
            }

            GAME_OVER -> {
                if (character.lower.isMissing) {
                    if (character.undies.isMissing) {
                        ui.setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        ui.setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.undies.insert +
                                        " are clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    }
                } else {
                    if (character.undies.isMissing) {
                        ui.setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.lower.insert +
                                        " is clinging to your skin, a sign of your failure...",
                                //TODO: Add "is/are" depending on lower clothes type
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        ui.setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.lower.insert + " and " + character.undies.insert +
                                        " are both clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    }
                }
                ui.btnNext.isVisible = false
            }

            END_GAME -> {
                scorer.showScoreDialog()
                ui.btnNext.isVisible = false
            }

            CAUGHT -> {
                when (schoolDay.timesCaught) {
                    0 -> {
                        ui.setText("It looks like a classmate has spotted that you've got to go badly.",
                                "Damn, he may spread that fact...")
                        character.embarrassment += 3
                        schoolDay.classmatesAwareness += 5
                        scorer.countOut("Caught holding pee", 3, ArithmeticAction.ADD)
                        schoolDay.timesCaught++
                    }

                    1 -> {
                        ui.setLinesAsDialogue(3)
                        ui.setText("You'he heard a suspicious whisper behind you.",
                                "Listening to the whisper, you've found out that they're saying that you need to go.",
                                "If I hold it until the lesson ends, I will beat them.")
                        character.embarrassment += 8
                        schoolDay.classmatesAwareness += 5
                        scorer.countOut("Caught holding pee", 8, ArithmeticAction.ADD)
                        schoolDay.timesCaught++
                    }

                    2 -> {
                        if (character.gender == Gender.FEMALE) {
                            ui.setLinesAsDialogue(2)
                            ui.setText("The most handsome boy in your class, ${schoolDay.surpriseBoy.name}, is calling you:",
                                    "Hey there, don't wet yourself!",
                                    "Oh no, he knows it...")
                        } else {
                            ui.setLinesAsDialogue(2, 3)
                            ui.setText("The most nasty boy in your class, ${schoolDay.surpriseBoy.name}, is calling you:",
                                    "Hey there, don't wet yourself! Ahahahaa!",
                                    "\"Shut up...\"",
                                    ", you think to yourself.")
                        }
                        character.embarrassment += 12
                        schoolDay.classmatesAwareness += 5
                        scorer.countOut("Caught holding pee", 12, ArithmeticAction.ADD)
                        schoolDay.timesCaught++
                    }

                    else -> {
                        ui.setText("The chuckles are continuously passing over the classroom.",
                                "Everyone is watching you.",
                                "Oh god... this is so embarrassing...")
                        character.embarrassment += 20
                        schoolDay.classmatesAwareness += 5
                        scorer.countOut("Caught holding pee", 20, ArithmeticAction.ADD)
                        schoolDay.timesCaught++
                    }
                }
                plot.nextStageID = ASK_ACTION
            }

        //The special hardcore scene
        /*
         * "Surprise" is an additional scene after the lesson where player is being caught by her classmate.
         * He wants her to wet herself.
         * Triggering conditions: female, hardcore
         * Triggering chance: 10%
         */
            SURPRISE -> {
                plot.specialHardcoreStage = true

                scorer.countOut("Got the \"surprise\" by ${schoolDay.surpriseBoy.name}", 70, ArithmeticAction.ADD)
                ui.setText("The lesson is finally over, and you're running to the restroom as fast as you can.",
                        "But... You see ${schoolDay.surpriseBoy.name} staying in front of the restroom.",
                        "Suddenly, he takes you, not letting you to escape.")
                character.embarrassment += 10
                plot.nextStageID = SURPRISE_2
            }

            SURPRISE_2 -> {
                ui.setLinesAsDialogue(1)
                ui.setText("What do you want from me?!",
                        "He has brought you in the restroom and quickly put you on the windowsill.",
                        schoolDay.surpriseBoy.name + " has locked the restroom door (seems he has stolen the key), " +
                                "then he puts his palm on your belly and says:",
                        "I want you to wet yourself.")
                character.embarrassment += 10
                plot.nextStageID = SURPRISE_DIALOGUE
            }

            SURPRISE_DIALOGUE -> {
                ui.setLinesAsDialogue(1)
                ui.setText("No, please, don't do it, no...",
                        "I want to see you wet...",
                        "He slightly presses your belly again, you shook from the terrible pain",
                        "in your fullness and subconsciously rubbed your crotch. You have to do something!")
                character.embarrassment += 10

                plot.actionList.add("Hit him")
                when (schoolDay.lesson.timesPeeDenied) {
                    0 -> plot.actionList.add("Try to persuade him to let you pee")
                    1 -> plot.actionList.add("Try to persuade him to let you pee again")
                    2 -> plot.actionList.add("Take a chance and try to persuade him (RISKY)")
                }
                plot.actionList.add("Pee yourself")

                ui.listChoice.setListData(plot.actionList.toTypedArray())
                ui.showActionUI("Don't let him to do it!")
                plot.nextStageID = SURPRISE_CHOSE
            }

            SURPRISE_CHOSE -> {
                if (ui.listChoice.isSelectionEmpty) {
                    //No idling
                    ui.setText("You will wet yourself right now,",
                            schoolDay.surpriseBoy.name + " demands.",
                            "Then ${schoolDay.surpriseBoy.name} presses your fullness...")
                    plot.nextStageID = SURPRISE_WET_PRESSURE
                }

                //                actionNum = listChoice.getSelectedIndex();
                if (ui.listChoice.selectedValue == "[Unavailable]") {
                    //No idling
                    ui.setText("You will wet yourself right now,",
                            schoolDay.surpriseBoy.name + " demands.",
                            "Then ${schoolDay.surpriseBoy.name} presses your fullness...")
                    plot.nextStageID = SURPRISE_WET_PRESSURE
                }

                when (ui.hideActionUI()) {
                    0 -> plot.nextStageID = HIT
                    1 -> plot.nextStageID = PERSUADE
                    2 -> plot.nextStageID = SURPRISE_WET_VOLUNTARY
                }
            }

            HIT -> if (random.nextInt(100) <= 20) {
                ui.setLinesAsDialogue(2)
                plot.nextStageID = PlotStageID.END_GAME
                scorer.countOut("Successful hit on ${schoolDay.surpriseBoy.name}'s groin", 40, ArithmeticAction.ADD)
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing) {
                        ui.setText("You hit ${schoolDay.surpriseBoy.name}'s groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + character.lower.insert +
                                        " and " + character.undies.insert + ",",
                                "wearily flop down on the toilet and start peeing.")
                    } else {
                        ui.setText("You hit ${schoolDay.surpriseBoy.name}'s groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " +
                                        character.lower.insert + ",",
                                "wearily flop down on the toilet and start peeing.")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        ui.setText("You hit ${schoolDay.surpriseBoy.name}'s groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " +
                                        character.undies.insert + ",",
                                "wearily flop down on the toilet and start peeing.")
                    } else {
                        ui.setText("You hit ${schoolDay.surpriseBoy.name}'s groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it,",
                                "wearily flop down on the toilet and start peeing.")
                    }
                }
            } else {
                plot.nextStageID = PlotStageID.SURPRISE_WET_PRESSURE
                ui.setLinesAsDialogue(2, 3)
                ui.setText("You hit ${schoolDay.surpriseBoy.name}'s hand. Damn, you'd meant to hit his groin...",
                        "You're braver than I expected;",
                        "now let's check the strength of your fullness!",
                        schoolDay.surpriseBoy.name + " pressed your fullness violently...")
            }

            PERSUADE -> when (schoolDay.surpriseBoy.timesPeeDenied) {
                0 -> if (random.nextInt(100) <= 10) {
                    ui.setLinesAsDialogue(1)
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        } else {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.lower.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.undies.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        } else {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        }
                    }
                    scorer.countOut("Persuaded ${schoolDay.surpriseBoy.name} to pee", 40, ArithmeticAction.ADD)
                    character.bladder.fullness = 0.0
                    plot.nextStageID = END_GAME
                } else {
                    ui.setText("You ask ${schoolDay.surpriseBoy.name} if you can pee.",
                            "No, you can't pee in a cabin. I want you to wet yourself.,",
                            schoolDay.surpriseBoy.name + " says.")
                    schoolDay.surpriseBoy.timesPeeDenied++
                    plot.nextStageID = SURPRISE_DIALOGUE
                }

                1 -> if (random.nextInt(100) <= 5) {
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        } else {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.lower.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.undies.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        } else {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        }
                    }
                    scorer.countOut("Persuaded ${schoolDay.surpriseBoy.name} to pee", 60, ArithmeticAction.ADD)
                    character.bladder.fullness = 0.0
                    plot.nextStageID = END_GAME
                } else {
                    ui.setText("You ask ${schoolDay.surpriseBoy.name} if you can pee again.",
                            "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.",
                            schoolDay.surpriseBoy.name + " demands.")
                    schoolDay.surpriseBoy.timesPeeDenied++
                    plot.nextStageID = SURPRISE_DIALOGUE
                }

                2 -> if (random.nextInt(100) <= 2) {
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        } else {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.lower.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "pull down your " + character.undies.insert + ",",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        } else {
                            ui.setText("Ok, you may, but you'll let me watch you pee.",
                                    "states ${schoolDay.surpriseBoy.name}. You enter the cabin,",
                                    "stand over the toilet and start peeing under ${schoolDay.surpriseBoy.name}'s watch.")
                        }
                    }

                    scorer.countOut("Persuaded ${schoolDay.surpriseBoy.name} to pee", 80, ArithmeticAction.ADD)
                    character.bladder.fullness = 0.0
                    plot.nextStageID = END_GAME
                } else {
                    ui.setText("You ask ${schoolDay.surpriseBoy.name} if you can pee again desperately.",
                            "No, you can't pee in a cabin. You will wet yourself right now,",
                            schoolDay.surpriseBoy.name + " demands.",
                            "Then ${schoolDay.surpriseBoy.name} pressed your fullness...")
                    plot.nextStageID = SURPRISE_WET_PRESSURE
                }
            }

            SURPRISE_WET_VOLUNTARY -> {
                ui.setLinesAsDialogue(1, 3)
                ui.setText("Alright, as you say.,",
                        "you say to ${schoolDay.surpriseBoy.name} with a defeated sigh.",
                        "Whatever, I really can't hold it anymore anyways...")
                character.bladder.fullness = 0.0
                plot.nextStageID = SURPRISE_WET_VOLUNTARY2
            }

            SURPRISE_WET_VOLUNTARY2 -> {
                if (!character.undies.isMissing) {
                    if (!character.lower.isMissing) {
                        ui.setText("You feel the warm pee stream",
                                "filling your " + character.undies.insert + " and darkening your " +
                                        character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        ui.setText("You feel the warm pee stream",
                                "filling your " + character.undies.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!character.lower.isMissing) {
                        ui.setText("You feel the warm pee stream",
                                "filling your " + character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        ui.setText("You feel the warm pee stream",
                                "running down your legs.",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                }
                character.bladder.fullness = 0.0
                plot.nextStageID = END_GAME
            }

            SURPRISE_WET_PRESSURE -> {
                if (!character.undies.isMissing) {
                    if (!character.lower.isMissing) {
                        ui.setText("Ouch... The sudden pain flash passes through your fullness...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.undies.insert + " and darkening your " +
                                        character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        ui.setText("Ouch... The sudden pain flash passes through your fullness...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.undies.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!character.lower.isMissing) {
                        ui.setText("Ouch... The sudden pain flash passes through your fullness...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        ui.setText("Ouch... The sudden pain flash passes through your fullness...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "running down your legs.",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                }
                character.bladder.fullness = 0.0
                plot.nextStageID = END_GAME
            }

            DRINK -> {
                ui.setText("You take your bottle with water,",
                        "open it and take a small sip of water.")
                character.belly += character.thirst.toDouble()
                character.thirst = 0
                plot.nextStageID = ASK_ACTION
            }

            else -> ui.setText("Error parsing button. Next text is unavailable, text #" + plot.nextStageID)
        */
        plot.advanceToNextStage()
    }

    fun writeSaveFile() {
        val fcGame = SaveFileChooser(File(character.name))

        if (fcGame.showSaveDialog(ui) != JFileChooser.APPROVE_OPTION) return

        val file = File(fcGame.selectedFile.absolutePath + ".lhhsav")
        try {
            val save = Save(this)
            val fileOutputStream = FileOutputStream(file)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)

            objectOutputStream.writeObject(save)
        } catch (e: IOException) {
            e.printStackTrace()
            JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    fun openSaveFile() {
        val fcGame = SaveFileChooser()

        if (fcGame.showOpenDialog(ui) != JFileChooser.APPROVE_OPTION) return

        val file = fcGame.selectedFile!!
        try {
            val fin = FileInputStream(file)
            val ois = ObjectInputStream(fin)
            val save = ois.readObject() as Save
            Core(save)
            ui.dispose()
        } catch (e: Exception) {
            e.printStackTrace()
            JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
        }
    }

    /**
     * Resets the game and values, optionally letting player to select new parameters.
     *
     * @param newValues whether to suggest user to select new game parameters.
     */
    fun reset(newValues: Boolean) {
        if (newValues)
            setupFramePre().isVisible = true
        else
        //Creating new core
            Core(gameStartSave)
    }

    companion object {
        private val random = Random()
        private lateinit var gameStartSave: Save

        /**
         * Default turn duration in in-game minutes.
         */
        const val TURN_DURATION = 3
    }
}