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
 * 2.0    Total rewrite
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

import longHourAndAHalf.ALongHourAndAHalf.GameStage.*
import longHourAndAHalf.WearType.*
import longHourAndAHalf.ui.SaveFileChooser
import longHourAndAHalf.ui.StandardGameUI
import longHourAndAHalf.ui.WearFileChooser
import longHourAndAHalf.ui.setupFramePre
import java.awt.Color
import java.io.*
import java.util.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane

private val random = Random()

/**
 * @return random item from this array
 */
fun <T> Array<T>.randomItem() = this[random.nextInt(this.size)]

/**
 * @return random item from this list
 */
fun <T> List<T>.randomItem() = this[random.nextInt(this.size)]

@Suppress("KDocMissingDocumentation")
class ALongHourAndAHalf {

    /**
     * Game character.
     */
    var character: Character

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
    var ui: StandardGameUI

    /**
     * The lesson time.
     */
    var time: Int

    /**
     * Times teacher denied character to go out.
     */
    var timesPeeDenied: Int

    /**
     * Number of times character got caught holding pee from classmates.
     */
    var timesCaught: Int

    /**
     * Amount of embarrassment raising every time character caught holding pee.
     */
    var classmatesAwareness: Int

    /**
     * Whether character has to stay additional 30 minutes after class.
     */
    var stay: Boolean

    /**
     * Whether pee drain cheat enabled: pee mysteriously vanishes every 15 minutes.
     */
    var drain: Boolean

    /**
     * Whether player has used cheats.
     */
    var cheatsUsed: Boolean

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

    /**
     * List of all boy names for special hardcore scene.
     */
    private val names = listOf("Mark", "Mike", "Jim", "Alex", "Ben", "Bill", "Dan")

    /**
     * Special hardcore scene boy's name.
     */
    val boyName: String

    /**
     * Actions list.
     */
    private val actionList = mutableListOf<String>()

    /**
     * A stage after the current stage.
     */
    var nextStage: GameStage

    /**
     * An array that contains boolean values that define *dialogue lines*.
     * Dialogue lines, unlike normal lines, are *italic*.
     */
    private var dialogueLines = BooleanArray(MAX_LINES)

    /**
     * Whether special hardcore stage is active now.
     */
    var specialHardcoreStage = false

    private fun openCustomWear(type: WearType): Wear? {
        var openedWear: Wear? = null

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
        this.character = character
        this.character.game = this
        this.hardcore = hardcore

        nextStage = LEAVE_BED
        time = 0
        timesPeeDenied = 0
        scorer = Scorer()
        timesCaught = 0
        classmatesAwareness = 0
        stay = false
        drain = false
        cheatsUsed = false
        boyName = names.randomItem()

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

        this.character.undies = setupWear(character.undies, UNDERWEAR) ?: onIncorrectWearSelected(UNDERWEAR)
        this.character.lower = setupWear(character.lower, OUTERWEAR) ?: onIncorrectWearSelected(OUTERWEAR)

        this.character.undies.setupColor(this.character.undies.color)
        this.character.lower.setupColor(this.character.lower.color)

        ui.finishSetup()

        //Scoring bladder at start
        scorer.nominate("Bladder at start - ${this.character.bladder}%", this.character.bladder,
                ArithmeticAction.ADD)

        //Scoring incontinence
        scorer.nominate(
                "Incontinence - ${this.character.incontinence}x", this.character.incontinence,
                ArithmeticAction.MULTIPLY, true
        )

        if (this.hardcore) {
            character.maxBladder = 100
            ui.hardcoreModeToggled(true)
            scorer.nominate("Hardcore", 2, ArithmeticAction.MULTIPLY)
        }

        ui.drynessBar.maximum = this.character.maximalDryness.toInt()
        ui.drynessBar.value = this.character.dryness.toInt()

        ui.showBladderAndTime()

        handleNextClicked()

        postConstructor()
    }

    constructor(save: Save) {
        character = save.character
        hardcore = save.hardcore
        time = save.time
        nextStage = save.stage
        scorer = save.scorer
        timesPeeDenied = save.timesPeeDenied
        timesCaught = save.timesCaught
        classmatesAwareness = save.classmatesAwareness
        stay = save.stay
        drain = save.drain
        cheatsUsed = save.cheatsUsed
        boyName = save.boyName

        ui = StandardGameUI(this)
        ui.finishSetup()

        ui.drynessBar.maximum = this.character.maximalDryness.toInt()
        ui.drynessBar.value = this.character.dryness.toInt()

        ui.showBladderAndTime()

//        handleNextClicked()

        postConstructor()
    }

    private fun postConstructor() {
        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore) {
            character.maxBladder = 100
            ui.hardcoreModeToggled(true)
        }

        gameStartSave = Save(this)   //Saving game for a reset

        ui.isVisible = true     //Displaying the frame
    }

    fun handleNextClicked() {
        updateUI()
        when (nextStage) {
            LEAVE_BED -> {
                //Making line 1 italic
                setLinesAsDialogue(1)
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing)
                    //Both lower clothes and undies
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some " +
                                        "${character.undies.insert} and ${character.lower.insert},",
                                "not even worrying about what covers your chest.")
                    } else
                    //Lower clothes only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some ${character.lower.insert}, " +
                                        "quick to cover your ${character.undies.insert},",
                                "not even worrying about what covers your chest.")
                    }
                } else {
                    if (!character.undies.isMissing)
                    //Undies only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on ${character.undies.insert},",
                                "not even worrying about what covers your chest and legs.")
                    } else
                    //No clothes at all
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You are running downstairs fully naked.")
                    }
                }
                passTime(1)

                //Setting the next stage to "Leaving home"
                nextStage = LEAVE_HOME
            }

            LEAVE_HOME -> {
                setText("Just looking at the clock again in disbelief adds a redder tint to your cheeks.",
                        "",
                        "Paying much less attention to your daily routine, you quickly run down the stairs, " +
                                "get a small glass of orange juice and chug it.",
                        "",
                        "The cold drink brings a chill down your spine " +
                                "as you collect your things and rush out the door to school.")

                passTime(1)
                offsetEmbarrassment(3)
                offsetBelly(10.0)

                nextStage = GO_TO_CLASS
            }

            GO_TO_CLASS -> {
                //Displaying all values
                ui.showBladderAndTime()

                if (!character.lower.isMissing) {
                    //Skirt blowing in the wind
                    if (character.lower.insert == "skirt") {
                        setText("You rush into class, your ${character.lower.insert} blowing in the wind.",
                                "",
                                "Normally, you'd be worried your ${character.undies.insert} would be seen, " +
                                        "but you can't worry about it right now.",
                                "You make it to your seat without a minute to spare.")
                    } else {
                        //Nothing is blowing in wind
                        setText("Trying your best to make up lost time, " +
                                "you rush into class and sit down to your seat without a minute to spare.")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        setText("You rush into class; " +
                                "your classmates are looking at your ${character.undies.insert}.",
                                "You can't understand how you forgot to even put on any lower clothing,",
                                "and you know that your ${character.undies.insert} have definitely been seen.",
                                "You make it to your seat without a minute to spare.")
                    } else {
                        if (character.gender == Gender.FEMALE) {
                            setText("You rush into class; your classmates are looking at your pussy and boobs.",
                                    "Guys are going mad and doing nothing except looking at you.",
                                    "You can't understand how you dared to come to school naked.",
                                    "You make it to your seat without a minute to spare.")
                        } else {
                            setText("You rush into class; your classmates are looking at your penis.",
                                    "Girls are really going mad and doing nothing except looking at you.",
                                    "You can't understand how you dared to come to school naked.",
                                    "You make it to your seat without a minute to spare.")
                        }
                    }
                }

                offsetEmbarrassment(2)
                nextStage = WALK_IN
            }

            WALK_IN -> {
                //If lower clothes is a skirt
                if (character.lower.insert == "skirt" || character.lower.insert == "skirt and tights"
                        || character.lower.insert == "skirt and tights") {
                    setLinesAsDialogue(1, 3)
                    setText("Next time you run into class, ${character.name},",
                            "your teacher says,",
                            "make sure you're wearing something less... revealing!",
                            "A chuckle passes over the classroom, and you can't help but feel a",
                            "tad bit embarrassed about your rush into class.")
                    offsetEmbarrassment(5)
                } else
                //No outerwear
                {
                    if (character.lower.isMissing) {
                        setLinesAsDialogue(1)
                        setText("WHAT!? YOU CAME TO SCHOOL NAKED!?",
                                "your teacher shouts in disbelief.",
                                "",
                                "A chuckle passes over the classroom, " +
                                        "and you can't help but feel extremely embarrassed",
                                "about your rush into class, let alone your nudity")
                        offsetEmbarrassment(25)
                    } else {
                        setLinesAsDialogue(1, 3)
                        setText("Sit down, ${character.name}. You're running late.",
                                "your teacher says,",
                                "And next time, don't make so much noise entering the classroom!",
                                "A chuckle passes over the classroom, " +
                                        "and you can't help but feel a tad bit embarrassed",
                                "about your rush into class.")
                    }
                }
                nextStage = SIT_DOWN
            }

            SIT_DOWN -> {
                setText("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of",
                        "your bladder filling as the liquids you drank earlier start to make their way down.")
                passTime()
                nextStage = ASK_ACTION
                scorer.nominate(
                        "Embarrassment at start - ${character.incontinence} pts",
                        character.embarrassment,
                        ArithmeticAction.ADD
                )
            }

            ASK_ACTION -> {
                //Called by teacher if unlucky
                actionList.clear()
                if (generator.nextInt(20) == 5) {
                    setText("Suddenly, you hear the teacher call your name.")
                    nextStage = CALLED_ON
                    return
                }

                //Bladder: 0-20
                if (character.bladder <= 20) {
                    setText("Feeling bored about the day, and not really caring about the class too much,",
                            "you look to the clock, watching the minutes tick by.")
                }
                //Bladder: 20-40
                if (character.bladder > 20 && character.bladder <= 40) {
                    setText("Having to pee a little bit,",
                            "you look to the clock, " +
                                    "watching the minutes tick by and wishing the lesson to get over faster.")
                }
                //Bladder: 40-60
                if (character.bladder > 40 && character.bladder <= 60) {
                    setText("Clearly having to pee,",
                            "you impatiently wait for the lesson end.")
                }
                //Bladder: 60-80
                if (character.bladder > 60 && character.bladder <= 80) {
                    setLinesAsDialogue(2)
                    setText("You feel the rather strong pressure in your bladder, " +
                            "and you're starting to get even more desperate.",
                            "Maybe I should ask teacher to go to the restroom? It hurts a bit...")
                }
                //Bladder: 80-100
                if (character.bladder > 80 && character.bladder <= 100) {
                    setLinesAsDialogue(1, 3)
                    setText("Keeping all that urine inside will become impossible very soon.",
                            "You feel the terrible pain and pressure in your bladder, " +
                                    "and you can almost definitely say ",
                            "you haven't needed to pee this badly in your life.",
                            "Ouch, it hurts a lot... I must do something about it now, or else...")
                }
                //Bladder: 100-130
                if (character.bladder > 100 && character.bladder <= 130) {
                    setLinesAsDialogue(1, 3)
                    if (character.gender == Gender.FEMALE) {
                        setText("This is really bad...",
                                "You know that you can't keep it any longer and " +
                                        "you may wet yourself in any moment and oh,",
                                "You can clearly see your bladder as it bulging.",
                                "Ahhh... I cant hold it anymore!!!",
                                "Even holding your crotch doesn't seems to help you to keep it in.")
                    } else {
                        setText("This is really bad...",
                                "You know that you can't keep it any longer and " +
                                        "you may wet yourself in any moment and oh,",
                                "You can clearly see your bladder as it bulging.",
                                "Ahhh... I cant hold it anymore!!!",
                                "Even squeezing your penis doesn't seems to help you to keep it in.")
                    }
                }

                showActionUI("What now?")

                //Adding action choices
                when (timesPeeDenied) {
                    0 -> actionList.add("Ask the teacher to go pee")
                    1 -> actionList.add("Ask the teacher to go pee again")
                    2 -> actionList.add("Try to ask the teacher again")
                    3 -> actionList.add("Take a chance and ask the teacher (RISKY)")
                    else -> actionList.add("[Unavailable]")
                }

                if (!character.cornered) {
                    if (character.gender == Gender.FEMALE) {
                        actionList.add("Press on your crotch")
                    } else {
                        actionList.add("Squeeze your penis")
                    }
                } else {
                    actionList.add("[Unavailable]")
                }

                actionList.add("Rub thighs")

                if (character.bladder >= 100) {
                    actionList.add("Give up and pee yourself")
                } else {
                    actionList.add("[Unavailable]")
                }
                if (hardcore) {
                    actionList.add("Drink water")
                } else {
                    actionList.add("[Unavailable]")
                }
                actionList.add("Just wait")
                actionList.add("Cheat (will reset your score)")

                //Loading the choice array into the action selector
                ui.listChoice.setListData(actionList.toTypedArray())
                nextStage = CHOSE_ACTION
                passTime()
            }

            CHOSE_ACTION -> {
                nextStage = ASK_ACTION
                if (ui.listChoice.isSelectionEmpty || ui.listChoice.selectedValue == "[Unavailable]") {
                    handleNextClicked()
                    return
                }

                //Hiding the action selector and doing action job
                when (hideActionUI()) {
                //Ask the teacher to go pee
                    0 -> {
                        nextStage = ASK_TO_PEE
                        setLinesAsDialogue(2, 3)
                        setText("You think to yourself:",
                                "I don't think I can hold it until class ends!",
                                "I don't have a choice, I have to ask the teacher...")
                    }

                /*
                 * Press on crotch/squeeze penis
                 * 3 minutes
                 * -2 bladder
                 * Detection chance: 15
                 * Effectiveness: 0.4
                 * =========================
                 * 3 minutes
                 * +20 sph. power
                 * Detection chance: 15
                 * Future effectiveness: 4
                 */
                    1 -> {
                        setText("You don't think anyone will see you doing it,",
                                "so you take your hand and hold yourself down there.",
                                "It feels a little better for now.")

                        rechargeSphPower(20)
                        offsetTime(3)

                        //Chance to be caught by classmates in hardcore mode
                        nextStage = if ((generator.nextInt(100) <= 15 + classmatesAwareness) and hardcore) {
                            CAUGHT
                        } else {
                            ASK_ACTION
                        }
                    }

                /*
                     * Rub thighs
                     * 3 + 3 = 6 minutes
                     * -0.2 bladder
                     * Detection chance: 3
                     * Effectiveness: 6
                     * =========================
                     * 3 + 3 = 6 minutes
                     * +2 sph. power
                     * Detection chance: 3
                     * Future effectiveness: 4
                     */
                    2 -> {
                        setText("You need to go, and it hurts, but you just",
                                "can't bring yourself to risk getting caught with your hand between",
                                "your legs. You rub your thighs hard but it doesn't really help.")

                        rechargeSphPower(2)
                        offsetTime(3)

                        //Chance to be caught by classmates in hardcore mode
                        nextStage = if ((generator.nextInt(100) <= 3 + classmatesAwareness) and hardcore) {
                            CAUGHT
                        } else {
                            ASK_ACTION
                        }
                    }

                //Give up
                    3 -> {
                        setText("You're absolutely desperate to pee, and you think you'll",
                                "end up peeing yourself anyway, so it's probably best to admit",
                                "defeat and get rid of the painful ache in your bladder.")
                        nextStage = GIVE_UP
                    }

                //Drink water
                    4 -> {
                        setText("Feeling a tad bit thirsty,",
                                "You decide to take a small sip of water from your bottle to get rid of it.")
                        nextStage = DRINK
                    }

                /*
                     * Wait
                     * =========================
                     * 3 + 2 + n minutes
                     * +(2.5n) bladder
                     * Detection chance: 1
                     * Future effectiveness: 2.4(1), 0.4(2), 0.47(30)
                     */
                    5 -> {
                        //Asking player how much to wait
                        val timeOffset: Int
                        try {
                            timeOffset = java.lang.Integer.parseInt(JOptionPane.showInputDialog("How much to wait?"))
                            if (time < 1 || time > 125) {
                                throw NumberFormatException()
                            }
                            passTime(timeOffset)
                        } //Ignoring invalid output
                        catch (e: Exception) {
                            nextStage = ASK_ACTION
                            return
                        }

                        //Chance to be caught by classmates in hardcore mode
                        nextStage = if ((generator.nextInt(100) <= 1 + classmatesAwareness) and hardcore) {
                            CAUGHT
                        } else {
                            ASK_ACTION
                        }
                    }

                //Cheat
                    6 -> {
                        setText("You've got to go so bad!",
                                "There must be something you can do, right?")

                        //Zeroing points
                        cheatsUsed = true
                        nextStage = ASK_CHEAT
                    }

                    else -> setText("Bugs.")
                }
            }

            ASK_TO_PEE -> {
                when (timesPeeDenied) {
                    0 ->
                        //Success
                        if ((generator.nextInt(100) <= 40) and !hardcore) {
                            if (!character.lower.isMissing) {
                                if (!character.undies.isMissing) {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your ${character.lower.insert} " +
                                                    "and ${character.undies.insert},",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your ${character.lower.insert},",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!character.undies.isMissing) {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your ${character.undies.insert},",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it,",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            }
                            //score *= 0.2;
                            //scoreText = scoreText.concat("\nRestroom usage during the lesson: -80% of points");
                            scorer.nominate("Restroom usage during the lesson", 80,
                                    ArithmeticAction.TAKE_PERCENT)
                            emptyBladder()
                            nextStage = ASK_ACTION
                            //Fail
                        } else {
                            setText("You ask the teacher if you can go out to the restroom.",
                                    "No, you can't go out, the director prohibited it.",
                                    "says the teacher.")
                            timesPeeDenied++
                        }

                    1 -> if ((generator.nextInt(100) <= 10) and !hardcore) {
                        if (!character.lower.isMissing) {
                            if (!character.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your ${character.lower.insert} " +
                                                "and ${character.undies.insert},",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your ${character.lower.insert},",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!character.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your ${character.undies.insert},",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it,",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        }
                        //score *= 0.22;
                        //scoreText = scoreText.concat("\nRestroom usage during the lesson: -70% of points");
                        scorer.nominate("Restroom usage during the lesson", 70, ArithmeticAction.TAKE_PERCENT)
                        emptyBladder()
                        nextStage = ASK_ACTION
                    } else {
                        setText("You ask the teacher again if you can go out to the restroom.",
                                "No, you can't! I already told you that the director prohibited it!",
                                "says the teacher.")
                        timesPeeDenied++
                    }

                    2 -> if ((generator.nextInt(100) <= 30) and !hardcore) {
                        if (!character.lower.isMissing) {
                            if (!character.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.lower.insert + " and " +
                                                character.undies.insert + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.lower.insert + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!character.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.undies.insert + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it,",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        }
                        //score *= 0.23;
                        //scoreText = scoreText.concat("\nRestroom usage during the lesson: -60% of points");
                        scorer.nominate("Restroom usage during the lesson", 60, ArithmeticAction.TAKE_PERCENT)
                        emptyBladder()
                        nextStage = ASK_ACTION
                    } else {
                        setText("You ask the teacher once more if you can go out to the restroom.",
                                "No, you can't! Stop asking me or there will be consequences!",
                                "says the teacher.")
                        timesPeeDenied++
                    }

                    3 -> {
                        if ((generator.nextInt(100) <= 7) and !hardcore) {
                            if (!character.lower.isMissing) {
                                if (!character.undies.isMissing) {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.lower.insert + " and " +
                                                    character.undies.insert + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.lower.insert + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!character.undies.isMissing) {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.undies.insert + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it,",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            }
                            //score *= 0.3;
                            //scoreText = scoreText.concat("\nRestroom usage during the lesson: -50% of points");
                            scorer.nominate("Restroom usage during the lesson", 50, ArithmeticAction.TAKE_PERCENT)
                            emptyBladder()
                            nextStage = ASK_ACTION
                        } else {
                            if (generator.nextBoolean()) {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,",
                                        "yells the teacher.")
                                character.cornered = true
                                //score += 1.3 * (90 - min / 3);
                                //scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +"
                                //+ 1.3 * (90 - min / 3) + " score");
                                scorer.nominate(
                                        "Stayed on corner ${90 - time} minutes",
                                        1.3 * (90 - time / 3), ArithmeticAction.ADD
                                )
                                offsetEmbarrassment(5)
                            } else {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! YOU WILL WRITE LINES AFTER THE LESSON!!!,",
                                        "yells the teacher.")
                                offsetEmbarrassment(5)
                                stay = true
                                ui.timeBar.maximum = 120
                                //scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
                                //score *= 1.6;
                                scorer.nominate("Wrote lines after the lesson", 60, ArithmeticAction.ADD_PERCENT)
                            }
                        }
                        timesPeeDenied++
                    }
                }
                nextStage = ASK_ACTION
            }

            ASK_CHEAT -> {
                ui.listChoice.setListData(cheatList.toTypedArray())
                showActionUI("Select a cheat:")
                nextStage = CHOSE_CHEAT
            }

            CHOSE_CHEAT -> {
                if (ui.listChoice.isSelectionEmpty) {
                    nextStage = ASK_CHEAT
                    return
                }
                when (hideActionUI()) {
                    0 -> {
                        setText("You walk to the front corner of the classroom.")
                        character.cornered = true
                        nextStage = ASK_ACTION
                    }

                    1 -> {
                        setText("You decide to stay after class.")
                        stay = true
                        ui.timeBar.maximum = 120
                        nextStage = ASK_ACTION
                    }

                    2 -> {
                        setText("You see something out of the corner of your eye,",
                                "just within your reach.")
                        nextStage = USE_BOTTLE
                    }

                    3 -> {
                        setLinesAsDialogue(2)
                        setText("A voice comes over the loudspeaker:",
                                "All classes are now dismissed for no reason at all! Bye!",
                                "Looks like your luck changed for the better.")
                        time = 89
                        nextStage = CLASS_OVER
                    }

                    4 -> {
                        setText("The teacher feels sorry for you. Try asking to pee.")
                        timesPeeDenied = 0
                        stay = false
                        ui.timeBar.maximum = 90
                        character.cornered = false
                        nextStage = ASK_ACTION
                    }

                    5 -> {
                        setText("You decide to raise your hand.")
                        nextStage = CALLED_ON
                    }

                    6 -> {
                        setText("Suddenly, you feel like you're peeing...",
                                "but you don't feel any wetness. It's not something you'd",
                                "want to question, right?")
                        drain = true
                        nextStage = ASK_ACTION
                    }

                    7 -> {
                        setText("A friend in the desk next to you hands you a familiar",
                                "looking pill, and you take it.")
                        character.incontinence = JOptionPane.showInputDialog("How incontinent are you now?")
                                .toDouble()
                        character.maxSphincterPower = (100 / character.incontinence).toInt()
                        character.sphincterPower = character.maxSphincterPower
                        nextStage = ASK_ACTION
                    }

                    8 -> {
                        setText("The teacher suddenly looks like they've had enough",
                                "of people having to pee.")
                        hardcore = !hardcore
                        nextStage = ASK_ACTION
                    }

                    9 -> {
                        setText("Suddenly you felt something going on in your bladder.")
                        character.incontinence = JOptionPane.showInputDialog("How your bladder is full now?")
                                .toDouble()
                        nextStage = ASK_ACTION
                    }
                }
            }

            USE_BOTTLE -> {
                emptyBladder()
                setLinesAsDialogue(3)
                setText("Luckily for you, you happen to have brought an empty bottle to pee in.",
                        "As quietly as you can, you put it in position and let go into it.",
                        "Ahhhhh...",
                        "You can't help but show a face of pure relief as your pee trickles down into it.")
                nextStage = ASK_ACTION
            }

            CALLED_ON -> {
                setLinesAsDialogue(1)
                setText(character.name + ", why don't you come up to the board and solve this problem?,",
                        "says the teacher. Of course, you don't have a clue how to solve it.",
                        "You make your way to the front of the room and act lost, knowing you'll be stuck",
                        "up there for a while as the teacher explains it.",
                        "Well, you can't dare to hold yourself now...")
                passTime(5)
                scorer.nominate("Called on the lesson", 5, ArithmeticAction.ADD)
                nextStage = ASK_ACTION
            }

            CLASS_OVER -> {
                //Special hardcore scene trigger
                if (generator.nextInt(100) <= 10 && hardcore && character.gender == Gender.FEMALE) {
                    nextStage = SURPRISE
                    return
                }
                if (stay) {
                    nextStage = AFTER_CLASS
                    return
                }

                if (generator.nextBoolean()) {
                    setText("Lesson is finally over, and you're running to the restroom as fast as you can.",
                            "No, please... All cabins are occupied, and there's a line. You have to wait!")

                    scorer.nominate("Waited for a free cabin in the restroom", 3, ArithmeticAction.ADD)
                    passTime()
                    return
                } else {
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        } else {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.lower.insert + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.undies.insert + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        } else {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it,",
                                    "wearily flop down on the toilet and start peeing.")
                        }
                    }
                    nextStage = END_GAME
                }
            }

            AFTER_CLASS -> {
                if (time >= 120) {
                    stay = false
                    nextStage = CLASS_OVER
                    return
                }

                setLinesAsDialogue(1, 2, 3, 4)
                setText("Hey, ${character.name}, you wanted to escape? You must stay after classes!",
                        "Please... let me go to the restroom... I can't hold it...",
                        "No, ${character.name}, you can't go to the restroom now! This will be as punishment.",
                        "And don't think you can hold yourself either! I'm watching you...")

                passTime()
            }

            ACCIDENT -> {
                hideActionUI()
                setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.",
                        "Despite all this, you try your best, but suddenly you're forced to stop.",
                        "You can't move, or you risk peeing yourself. " +
                                "Heck, the moment you stood up you thought you " +
                                "could barely move for risk of peeing everywhere.",
                        "But now.. a few seconds tick by as you try to will yourself to move, " +
                                "but soon, the inevitable happens anyways.")
                nextStage = WET
            }

            GIVE_UP -> {
                offsetEmbarrassment(80)
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing) {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + character.undies.insert + ".")
                    } else {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decided to pee in your " + character.lower.insert + ".")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + character.undies.insert + ".")
                    } else {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee where you are.")
                    }
                }
                nextStage = WET
            }

            WET -> {
                emptyBladder()
                character.embarrassment = 100
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing) {
                        setText("Before you can move an inch, pee quickly soaks through your " +
                                character.undies.insert + ",",
                                "floods your " + character.lower.insert + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        setText("Before you can move an inch, pee quickly darkens your " + character.lower.insert +
                                " and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        setText("Before you can move an inch, pee quickly soaks through your " +
                                character.undies.insert + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        if (!character.cornered) {
                            setText("The heavy pee jets are hitting the seat and loudly leaking out from your " +
                                    character.undies.insert + ".",
                                    "A large puddle quickly forms, " +
                                            "and you can't stop tears from falling down your cheeks.")
                        } else {
                            setText("The heavy pee jets are hitting the floor and loudly leaking out from your " +
                                    character.undies.insert + ".",
                                    "A large puddle quickly forms, " +
                                            "and you can't stop tears from falling down your cheeks.")
                        }
                    }
                }
                nextStage = POST_WET
            }

            POST_WET -> {
                setLinesAsDialogue(2)
                if (!stay) {
                    if (character.lower.isMissing) {
                        if (character.gender == Gender.FEMALE && character.undies.isMissing) {
                            setText("People around you are laughing loudly.",
                                    character.name + " peed herself! Ahaha!!!")
                        } else {
                            if (character.gender == Gender.MALE && character.undies.isMissing) {
                                setText("People around you are laughing loudly.",
                                        character.name + " peed himself! Ahaha!!!")
                            } else {
                                setText("People around you are laughing loudly.",
                                        character.name + " wet h" +
                                                (if (character.gender == Gender.FEMALE) "er " else "is ") +
                                                character.undies.insert + "! Ahaha!!")
                            }
                        }
                    } else {
                        if (character.gender == Gender.FEMALE) {
                            setText("People around you are laughing loudly.",
                                    character.name + " peed her " + character.lower.insert + "! Ahaha!!")
                        } else {
                            setText("People around you are laughing loudly.",
                                    " peed his " + character.lower.insert + "! Ahaha!!")
                        }
                    }
                } else {
                    setText("Teacher is laughing loudly.",
                            "Oh, you peed yourself? This is a great punishment.",
                            "I hope you will no longer get in the way of the lesson.")
                }
                nextStage = GAME_OVER
            }

            GAME_OVER -> {
                if (character.lower.isMissing) {
                    if (character.undies.isMissing) {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.undies.insert +
                                        " are clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    }
                } else {
                    if (character.undies.isMissing) {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.lower.insert +
                                        " is clinging to your skin, a sign of your failure...",
                                //TODO: Add "is/are" depending on lower clothes type
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
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
                when (timesCaught) {
                    0 -> {
                        setText("It looks like a classmate has spotted that you've got to go badly.",
                                "Damn, he may spread that fact...")
                        offsetEmbarrassment(3)
                        classmatesAwareness += 5
                        scorer.nominate("Caught holding pee", 3, ArithmeticAction.ADD)
                        timesCaught++
                    }

                    1 -> {
                        setLinesAsDialogue(3)
                        setText("You'he heard a suspicious whisper behind you.",
                                "Listening to the whisper, you've found out that they're saying that you need to go.",
                                "If I hold it until the lesson ends, I will beat them.")
                        offsetEmbarrassment(8)
                        classmatesAwareness += 5
                        scorer.nominate("Caught holding pee", 8, ArithmeticAction.ADD)
                        timesCaught++
                    }

                    2 -> {
                        if (character.gender == Gender.FEMALE) {
                            setLinesAsDialogue(2)
                            setText("The most handsome boy in your class, $boyName, is calling you:",
                                    "Hey there, don't wet yourself!",
                                    "Oh no, he knows it...")
                        } else {
                            setLinesAsDialogue(2, 3)
                            setText("The most nasty boy in your class, $boyName, is calling you:",
                                    "Hey there, don't wet yourself! Ahahahaa!",
                                    "\"Shut up...\"",
                                    ", you think to yourself.")
                        }
                        offsetEmbarrassment(12)
                        classmatesAwareness += 5
                        scorer.nominate("Caught holding pee", 12, ArithmeticAction.ADD)
                        timesCaught++
                    }

                    else -> {
                        setText("The chuckles are continuously passing over the classroom.",
                                "Everyone is watching you.",
                                "Oh god... this is so embarrassing...")
                        offsetEmbarrassment(20)
                        classmatesAwareness += 5
                        scorer.nominate("Caught holding pee", 20, ArithmeticAction.ADD)
                        timesCaught++
                    }
                }
                nextStage = ASK_ACTION
            }

        //The special hardcore scene
        /*
         * "Surprise" is an additional scene after the lesson where player is being caught by her classmate.
         * He wants her to wet herself.
         * Triggering conditions: female, hardcore
         * Triggering chance: 10%
         */
            SURPRISE -> {

                //Resetting timesPeeDenied to use for that boy
                timesPeeDenied = 0

                specialHardcoreStage = true

                scorer.nominate("Got the \"surprise\" by $boyName", 70, ArithmeticAction.ADD)
                setText("The lesson is finally over, and you're running to the restroom as fast as you can.",
                        "But... You see $boyName staying in front of the restroom.",
                        "Suddenly, he takes you, not letting you to escape.")
                offsetEmbarrassment(10)
                nextStage = SURPRISE_2
            }

            SURPRISE_2 -> {
                setLinesAsDialogue(1)
                setText("What do you want from me?!",
                        "He has brought you in the restroom and quickly put you on the windowsill.",
                        boyName + " has locked the restroom door (seems he has stolen the key), " +
                                "then he puts his palm on your belly and says:",
                        "I want you to wet yourself.")
                offsetEmbarrassment(10)
                nextStage = SURPRISE_DIALOGUE
            }

            SURPRISE_DIALOGUE -> {
                setLinesAsDialogue(1)
                setText("No, please, don't do it, no...",
                        "I want to see you wet...",
                        "He slightly presses your belly again, you shook from the terrible pain",
                        "in your bladder and subconsciously rubbed your crotch. You have to do something!")
                offsetEmbarrassment(10)

                actionList.add("Hit him")
                when (timesPeeDenied) {
                    0 -> actionList.add("Try to persuade him to let you pee")
                    1 -> actionList.add("Try to persuade him to let you pee again")
                    2 -> actionList.add("Take a chance and try to persuade him (RISKY)")
                }
                actionList.add("Pee yourself")

                ui.listChoice.setListData(actionList.toTypedArray())
                showActionUI("Don't let him to do it!")
                nextStage = SURPRISE_CHOSE
            }

            SURPRISE_CHOSE -> {
                if (ui.listChoice.isSelectionEmpty) {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName + " demands.",
                            "Then $boyName presses your bladder...")
                    nextStage = SURPRISE_WET_PRESSURE
                }

                //                actionNum = listChoice.getSelectedIndex();
                if (ui.listChoice.selectedValue == "[Unavailable]") {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName + " demands.",
                            "Then $boyName presses your bladder...")
                    nextStage = SURPRISE_WET_PRESSURE
                }

                when (hideActionUI()) {
                    0 -> nextStage = HIT
                    1 -> nextStage = PERSUADE
                    2 -> nextStage = SURPRISE_WET_VOLUNTARY
                }
            }

            HIT -> if (generator.nextInt(100) <= 20) {
                setLinesAsDialogue(2)
                nextStage = GameStage.END_GAME
                scorer.nominate("Successful hit on $boyName's groin", 40, ArithmeticAction.ADD)
                if (!character.lower.isMissing) {
                    if (!character.undies.isMissing) {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + character.lower.insert +
                                        " and " + character.undies.insert + ",",
                                "wearily flop down on the toilet and start peeing.")
                    } else {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " +
                                        character.lower.insert + ",",
                                "wearily flop down on the toilet and start peeing.")
                    }
                } else {
                    if (!character.undies.isMissing) {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " +
                                        character.undies.insert + ",",
                                "wearily flop down on the toilet and start peeing.")
                    } else {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it,",
                                "wearily flop down on the toilet and start peeing.")
                    }
                }
            } else {
                nextStage = GameStage.SURPRISE_WET_PRESSURE
                setLinesAsDialogue(2, 3)
                setText("You hit $boyName's hand. Damn, you'd meant to hit his groin...",
                        "You're braver than I expected;",
                        "now let's check the strength of your bladder!",
                        boyName + " pressed your bladder violently...")
            }

            PERSUADE -> when (timesPeeDenied) {
                0 -> if (generator.nextInt(100) <= 10) {
                    setLinesAsDialogue(1)
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.lower.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.undies.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        }
                    }
                    scorer.nominate("Persuaded $boyName to pee", 40, ArithmeticAction.ADD)
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee.",
                            "No, you can't pee in a cabin. I want you to wet yourself.,",
                            boyName + " says.")
                    timesPeeDenied++
                    nextStage = SURPRISE_DIALOGUE
                }

                1 -> if (generator.nextInt(100) <= 5) {
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.lower.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.undies.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        }
                    }
                    scorer.nominate("Persuaded $boyName to pee", 60, ArithmeticAction.ADD)
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee again.",
                            "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.",
                            boyName + " demands.")
                    timesPeeDenied++
                    nextStage = SURPRISE_DIALOGUE
                }

                2 -> if (generator.nextInt(100) <= 2) {
                    if (!character.lower.isMissing) {
                        if (!character.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.lower.insert + " and " +
                                            character.undies.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.lower.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        }
                    } else {
                        if (!character.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.undies.insert + ",",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing under $boyName's watch.")
                        }
                    }

                    scorer.nominate("Persuaded $boyName to pee", 80, ArithmeticAction.ADD)
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee again desperately.",
                            "No, you can't pee in a cabin. You will wet yourself right now,",
                            boyName + " demands.",
                            "Then $boyName pressed your bladder...")
                    nextStage = SURPRISE_WET_PRESSURE
                }
            }

            SURPRISE_WET_VOLUNTARY -> {
                setLinesAsDialogue(1, 3)
                setText("Alright, as you say.,",
                        "you say to $boyName with a defeated sigh.",
                        "Whatever, I really can't hold it anymore anyways...")
                emptyBladder()
                nextStage = SURPRISE_WET_VOLUNTARY2
            }

            SURPRISE_WET_VOLUNTARY2 -> {
                if (!character.undies.isMissing) {
                    if (!character.lower.isMissing) {
                        setText("You feel the warm pee stream",
                                "filling your " + character.undies.insert + " and darkening your " +
                                        character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("You feel the warm pee stream",
                                "filling your " + character.undies.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!character.lower.isMissing) {
                        setText("You feel the warm pee stream",
                                "filling your " + character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("You feel the warm pee stream",
                                "running down your legs.",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                }
                emptyBladder()
                nextStage = END_GAME
            }

            SURPRISE_WET_PRESSURE -> {
                if (!character.undies.isMissing) {
                    if (!character.lower.isMissing) {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.undies.insert + " and darkening your " +
                                        character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.undies.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!character.lower.isMissing) {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.lower.insert + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "running down your legs.",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                }
                emptyBladder()
                nextStage = END_GAME
            }

            DRINK -> {
                setText("You take your bottle with water,",
                        "open it and take a small sip of water.")
                offsetBelly(character.thirst.toDouble())
                character.thirst = 0
                nextStage = ASK_ACTION
            }

            else -> setText("Error parsing button. Next text is unavailable, text #" + nextStage)
        }//Don't go further if player selected no or unavailable action
        //                }while(listChoice.isSelectionEmpty()||listChoice.getSelectedValue().equals("[Unavailable]"));
        //                } while (listChoice.isSelectionEmpty());
        //case template
        //      case 4:
        //   setText("");
        //   nextStage = ;
        //   break;
    }

    /**
     * Increments the time by specified amount of minutes and all time-related parameters.
     *
     * @param time amount of time increase.
     */
    private fun passTime(time: Int = 3) {
        offsetTime(time)
        offsetBladder(time * 1.5)
        offsetBelly(-time * 1.5)

        if (this.time >= 88) {
            setText("You hear the bell finally ring.")
            nextStage = CLASS_OVER
        }

        testWet()

        //Decrementing sphincter power for every 3 minutes
        for (i in 0 until time) {
            decaySphPower()
            if (character.belly != 0.0) {
                if (character.belly > 3) {
                    offsetBladder(2.0)
                } else {
                    offsetBladder(character.belly)
                    emptyBelly()
                }
            }
        }
        if (hardcore) {
            character.thirst += 2
            if (character.thirst > Character.MAXIMAL_THIRST) {
                nextStage = DRINK
            }
        }
        //Updating labels
        updateUI()
    }

    //TODO: Refactor
    /**
     * Checks the wetting conditions, and if they are met, wetting.
     */
    private fun testWet() {
        //If bladder is filled more than 130 points in the normal mode
        //and 100 points in the hardcore mode, forcing wetting
        if ((character.bladder >= character.maxBladder) and !hardcore) {
            character.sphincterSpasm()
        } else
        //If bladder is filled more than 100 points in the normal mode and 50 points in the hardcore mode,
        //character has a chance to wet
        {
            if (
            (character.bladder > character.maxBladder - 30)
                    && !hardcore
                    || (
                    (character.bladder > character.maxBladder - 20) && hardcore
                    )
                    ) {
                val wetChance = if (hardcore)
                    5 * (character.bladder - 80)
                else
                    3 * (character.bladder - 100) + character.embarrassment

                if (generator.nextInt(100) < wetChance) {
                    character.sphincterSpasm()
                }
            }
        }
    }

    /**
     * Empties the bladder.
     */
    private fun emptyBladder() {
        character.bladder = 0.0
        ui.lblBladder.text = "Bladder: " + character.bladder + "%"
        updateUI()
    }

    /**
     * Offsets bladder fullness by a specified amount.
     *
     * @param amount the amount to offset bladder fullness
     */
    private fun offsetBladder(amount: Double) {
        character.bladder += amount/* * incon*///Incontinence does another job after 1.1
        if (character.bladder > 100 && !hardcore || character.bladder > 80 && hardcore) {
            ui.lblBladder.foreground = Color.RED
        } else {
            ui.lblBladder.foreground = Color.BLACK
        }
        updateUI()
    }

    /**
     * Empties the belly.
     */
    private fun emptyBelly() {
        offsetBelly(-character.belly)
    }

    private fun offsetBelly(amount: Double) {
        character.belly += amount
        if (character.belly < 0) {
            character.belly = 0.0
        }
        updateUI()
    }

    private fun offsetEmbarrassment(amount: Int) {
        character.embarrassment += amount
        if (character.embarrassment < 0) {
            character.embarrassment = 0
        }
        updateUI()
    }

    private fun offsetTime(amount: Int) {
        time += amount
        if (drain and (time % 15 == 0)) {
            emptyBladder()
        }
        //Clothes drying over time
        if (character.dryness < character.maximalDryness) {
            character.dryness += character.lower.dryingOverTime + character.undies.dryingOverTime * (amount / 3)
        }

        if (character.dryness > character.maximalDryness) {
            character.dryness = character.maximalDryness
        }
        updateUI()
    }

    /**
     * Decreases the sphincter power.
     */
    private fun decaySphPower() {
        character.sphincterPower -= (character.bladder / 30).toInt()
        if (character.sphincterPower < 0) {
            character.dryness -= 5f //Decreasing dryness
            character.bladder -= 2.5 //Decreasing bladder level
            character.sphincterPower = 0
            if (character.dryness > MINIMAL_DRYNESS) {
                //Naked
                if (character.lower.isMissing && character.undies.isMissing) {
                    setText("You feel the leak running down your thighs...",
                            "You're about to pee! You must stop it!")
                } else
                //Outerwear
                {
                    if (!character.lower.isMissing) {
                        setText("You see the wet spot expand on your " + character.lower.insert + "!",
                                "You're about to pee! You must stop it!")
                    } else
                    //Underwear
                    {
                        if (!character.undies.isMissing) {
                            setText("You see the wet spot expand on your " + character.undies.insert + "!",
                                    "You're about to pee! You must stop it!")
                        }
                    }
                }
            }

            if (character.dryness < MINIMAL_DRYNESS) {
                if (character.lower.isMissing && character.undies.isMissing) {
                    if (character.cornered) {
                        setText("You see a puddle forming on the floor beneath you, you're peeing!",
                                "It's too much...")
                        nextStage = ACCIDENT
                        handleNextClicked()
                    } else {
                        setText("Feeling the pee hit the chair and soon fall over the sides,",
                                "you see a puddle forming under your chair, you're peeing!",
                                "It's too much...")
                        nextStage = ACCIDENT
                        handleNextClicked()
                    }
                } else {
                    if (!character.lower.isMissing) {
                        setText("You see the wet spot expanding on your " + character.lower.insert + "!",
                                "It's too much...")
                        nextStage = ACCIDENT
                        handleNextClicked()
                    } else {
                        if (!character.undies.isMissing) {
                            setText("You see the wet spot expanding on your " + character.undies.insert + "!",
                                    "It's too much...")
                            nextStage = ACCIDENT
                            handleNextClicked()
                        }
                    }
                }
            }
        }
        updateUI()
    }

    /**
     * Replenishes the sphincter power.
     *
     * @param amount the sphincter recharge amount
     */
    private fun rechargeSphPower(amount: Int) {
        character.sphincterPower += amount
        if (character.sphincterPower > character.maxSphincterPower) {
            character.sphincterPower = character.maxSphincterPower
        }
        updateUI()
    }

    private fun setLinesAsDialogue(vararg lines: Int) {
        for (i in lines) {
            dialogueLines[i - 1] = true
        }
    }

    /**
     * Sets the in-game text.
     *
     * @param lines the in-game text to set
     */
    private fun setText(vararg lines: String) {
        if (lines.size > MAX_LINES) {
            System.err.println("You can't have more than $MAX_LINES lines at a time!")
            return
        }
        if (lines.isEmpty()) {
            ui.textLabel.text = ""
            return
        }

        var toSend = "<html><center>"

        for (i in lines.indices) {
            toSend += if (dialogueLines[i]) {
                "<i>\"" + lines[i] + "\"</i>"
            } else {
                lines[i]
            }
            toSend += "<br>"

        }
        toSend += "</center></html>"
        ui.textLabel.text = toSend
        this.dialogueLines = BooleanArray(MAX_LINES)
    }

    private fun updateUI() {
        ui.lblName.text = character.name
        ui.lblBladder.text = "Bladder: " + character.bladder + "%"
        ui.lblEmbarrassment.text = "Embarrassment: " + character.embarrassment
        ui.lblBelly.text = "Belly: " + Math.round(character.belly) + "%"
        ui.lblIncontinence.text = "Incontinence: " + character.incontinence + "x"
        ui.lblMinutes.text = "Minutes: $time of 90"
        ui.lblSphPower.text = "Pee holding ability: " + character.sphincterPower + "%"
        ui.lblDryness.text = "Clothes dryness: " + Math.round(character.dryness)
        ui.lblUndies.text = "Undies: " + character.undies.color + " " + character.undies.name.toLowerCase()
        ui.lblLower.text = "Lower: " + character.lower.color + " " + character.lower.name.toLowerCase()
        ui.bladderBar.value = character.bladder.toInt()
        ui.sphincterBar.value = character.sphincterPower
        ui.drynessBar.value = character.dryness.toInt()
        ui.timeBar.value = time
        ui.lblThirst.text = "Thirst: " + character.thirst + "%"
        ui.thirstBar.value = character.thirst
    }

    private fun hideActionUI(): Int {
        val choice = ui.listChoice.selectedIndex
        actionList.clear()
        ui.lblChoice.isVisible = false
        ui.listScroller.isVisible = false
        return choice
    }

    private fun showActionUI(actionGroupName: String) {
        ui.lblChoice.isVisible = true
        ui.lblChoice.text = actionGroupName
        ui.listScroller.isVisible = true
    }

    internal fun save() {
        val fcGame = SaveFileChooser()
        fcGame.selectedFile = File(character.name)
        if (fcGame.showSaveDialog(ui) == JFileChooser.APPROVE_OPTION) {
            val file = File(fcGame.selectedFile.absolutePath + ".lhhsav")
            //            PrintStream writer;
            val fileOutputStream: FileOutputStream
            val objectOutputStream: ObjectOutputStream
            try {
                val save = Save(this)
                //                writer = new PrintStream(file);
                fileOutputStream = FileOutputStream(file)
                objectOutputStream = ObjectOutputStream(fileOutputStream)
                objectOutputStream.writeObject(save)
            } catch (e: IOException) {
                e.printStackTrace()
                JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
    }

    internal fun load() {
        val fcGame = SaveFileChooser()
        if (fcGame.showOpenDialog(ui) == JFileChooser.APPROVE_OPTION) {
            val file = fcGame.selectedFile
            try {
                val fin = FileInputStream(file)
                val ois = ObjectInputStream(fin)
                val save = ois.readObject() as Save
                ALongHourAndAHalf(save)
                ui.dispose()
            } catch (e: Exception) {
                e.printStackTrace()
                JOptionPane.showMessageDialog(ui, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
    }

    enum class GameStage {
        LEAVE_BED,
        LEAVE_HOME,
        GO_TO_CLASS,
        WALK_IN,
        SIT_DOWN,
        ASK_ACTION,
        CHOSE_ACTION,
        ASK_TO_PEE,
        CALLED_ON,
        CAUGHT,
        USE_BOTTLE,
        ASK_CHEAT,
        CHOSE_CHEAT,
        CLASS_OVER,
        AFTER_CLASS,
        ACCIDENT,
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
        DRINK
    }

    companion object {
        //Maximal lines of a text
        private val MAX_LINES = 9

        /**
         * The dryness game over minimal threshold.
         */
        val MINIMAL_DRYNESS = 0

        //Random stuff generator
        var generator = Random()

        private var gameStartSave: Save? = null

        /**
         * Resets the game and values, optionally letting player to select new parameters.
         *
         * @param newValues whether to suggest user to select new game parameters
         */
        fun reset(newValues: Boolean) {
            if (newValues) {
                setupFramePre().isVisible = true
            } else {
                ALongHourAndAHalf(gameStartSave!!)
            }
        }
    }
}
