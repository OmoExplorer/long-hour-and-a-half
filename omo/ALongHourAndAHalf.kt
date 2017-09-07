/*
*ALongHourAndAHalf Vers. 1.3
*
*Dev: Rosalie Elodie, JavaBird
*
*Version History:
*0.1    Default game mechanics shown, non interactable. No playability, no customization. Not all game mechanics even implemented, purely a showcase program.
*0.2    MASSIVE REWRITE! (Thanks to Anna May! This is definitely the format I want!)
*1.0    Added interactivity, improved code, added hardcore mode(this isn't working now) and... cheats!
*1.1    Reintegrated the two versions
*1.2    New hardcore features:
*           Classmates can be aware that you've to go
*           Less bladder capacity
*       Improved bladder: it's more realistic now
*       Balanced pee holding methods
*       New game options frame
*       Even more clothes
*       Cleaned and documented code a bit
*1.3    Bug fixes
*       Interface improvements
*       Game text refining
*1.3.1  Bug fixes
*1.4    Character can drink during the class
*       Saving/loading games
*       Bug fixes
*1.4.1  Bug fixes
*
*A Long Hour and a Half (ALongHourAndAHalf) is a game where
*one must make it through class with a rather full bladder.
*This game will be more of a narrative game, being extremely text based,
*but it will have choices that can hurt and help your ability to hold.
*Some randomization elements are going to be in the game, but until completion,
*it's unknown how many.
*
*Many options are already planned for full release, such as:
*Name (friends and teacher may say it. Also heard in mutterings if an accident occurs)
*Male and Female (only affects character.gender pronouns (yes, that means crossdressing's allowed!))
*Random bladder amount upon awaking (Or preset)
*Choice of clothing (or, if in a rush, random choice of clothing (will be "character.gender conforming" clothing)
*Ability to add positives (relative to holding capabilities)
*Ability to add negatives (relative to holding capabilities)
*Called upon in class if unlucky (every 15 minutes)
*Incontinence (continence?) level selectable (multiplier basis. Maybe just presets, but having ability to choose may also be nice).
*
*
*Other options, which may be added in later or not, are these:
*Extended game ("Can [name] get through an entire school day AND make it home?") (probably will be in the next update)
*Better Dialog (lines made by someone that's not me >_< )
*Story editor (players can create their own stories and play them)
*Wear editor (players can create their own wear types and use it in A Long hour and a Half and custom stories
*Save/load game states
*Character presets
*
*
*If you have any questions, bugs or suggestions,
*create an issue or a pull request on GitHub:
*https://github.com/javabird25/long-hour-and-a-half/
*
*Developers' usernames table
*   Code documentation  |GitHub                                      |Omorashi.org
*   --------------------|--------------------------------------------|---------------------------------------------------------------------
*   Rosalie Elodie      |REDev987532 (https://github.com/REDev987532)|Justice (https://www.omorashi.org/profile/25796-justice/)
*   JavaBird            |javabird25 (https://github.com/javabird25)  |FromRUSForum (https://www.omorashi.org/profile/89693-fromrusforum/)
*   Anna May            |AnnahMay (https://github.com/AnnahMay)      |Anna May (https://www.omorashi.org/profile/10087-anna-may/)
*   notwillnotcast      |?                                           |thisonestays (https://www.omorashi.org/profile/14935-thisonestays/)
*
*FINAL NOTE: While this is created by Rosalie Dev, she allows it to be posted
*freely, so long as she's creditted. She also states that this program is
*ABSOLUTELY FREE, not to mention she hopes you enjoy ^_^
*
*
*DEV NOTES: Look for bugs, there is always a bunch of them
 */
package omo

import omo.Wear.WearType.*
import omo.ui.GameFrame
import omo.ui.SchoolSetup
import java.awt.Color
import java.io.*
import java.util.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

@Suppress("UNCHECKED_CAST")
fun <T> File.userObject() = ObjectInputStream(FileInputStream(this)).readObject() as T

/**
 * Main game class.
 *
 * @author Rosaile Elodie, JavaBird, Anna May, notwillnotcast
 */
class ALongHourAndAHalf {
    constructor(save: Save) {
        save.restore(this)
        gameFrame.isVisible = true
    }

    lateinit var character: Character

    var hardcore: Boolean = false

    val random = Random()

    /**
     * List of all boy names for special hardcore scene.
     */
    internal val names = listOf("Mark", "Mike", "Jim", "Alex", "Ben", "Bill", "Dan")

    /**
     * Special hardcore scene boy name.
     */
    internal var boyName = names[random.nextInt(names.size)]

    /**
     * Actions list.
     * TODO: Remove in 2.0
     */
    internal var actionList = mutableListOf<String>()

    /**
     * A stage after the current stage.
     */
    var nextStage: Stage = Stage.map["LEAVE_BED"]

    /**
     * Text to be displayed after the game which shows how many score
     * did you get.
     * TODO: Remove in 2.0
     */
    var scoreText = ""

    /**
     * Maximal time without squirming and leaking.
     */
    //var character.bladder.gameState.fullness.maximalSphincterStrength = 100 / character.bladder.gameState.fullness.incontinence

    /**
     * The class time.
     */
    var time = 0

    /**
     * Times teacher denied character to go out.
     */
    var timesPeeDenied = 0

    /**
     * Number of times player got caught holding pee.
     */
    var timesCaught = 0

    /**
     * Amount of character.gameState.embarrassment raising every time character caught holding pee.
     */
    var classmatesAwareness = 0

    /**
     * Whether or not charecter has to stay 30 minutes after class.
     */
    var stay = false

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    var cornered = false

    /**
     * Whether or not player has used cheats.
     */
    var cheatsUsed = false
    var specialHardcoreStage = false

    val MAXIMAL_THIRST = 30

    val parametersStorage = GameStartParameters(this)

    var scorer = Scorer()

    private val fileChoosers = object {
        val wear = JFileChooser().also {
            it.fileFilter = FileNameExtensionFilter("A Long Hour and a Half Wear", "lhhwear")
        }
        val save = JFileChooser().also {
            it.fileFilter = FileNameExtensionFilter("A Long Hour and a Half Save", "lhhsav")
        }
    }

    val gameFrame: GameFrame = GameFrame(this)

    constructor(character: Character, hardcore: Boolean) {
        this.character = character
        this.hardcore = hardcore


        handleSpecialWear(UNDERWEAR)
        handleSpecialWear(OUTERWEAR)


        //Scoring bladder fullness at start
        scorer += character.bladder.gameState!!.fullness.toInt()

        //Scoring incontinence
        scorer += character.bladder.incontinence.toInt()

        if (hardcore) {
            scorer.addFinalMultiplier(2)
        }

        displayAllGameStateValues()

        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore) {
            character.bladder.maximalFullness = 100.0
            gameFrame.lblName.text = gameFrame.lblName.text + " [Hardcore]"
        }

        //Starting the game
        nextStage = Stage.map["LEAVE_BED"]
        handleNextClicked()
        gameFrame.isVisible = true
    }

    private fun handleSpecialWear(type: Wear.WearType) {
        val state = character.gameState
        var targetWear = when (type) {
            UNDERWEAR -> state.undies
            OUTERWEAR -> state.lower
            BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE argument isn't supported")
        }

        when (targetWear.name) {
            "No underwear" -> {
                targetWear.insertName = if (character.gender == Gender.FEMALE) {
                    "crotch"
                } else {
                    "penis"
                }
            }
            "Random" -> {    //TODO: Gender-conforming random
                targetWear = randomWear(type)
            }
            "Custom" -> {
                fileChoosers.wear.showOpenDialog(gameFrame)
                try {
                    val wear: Wear = fileChoosers.wear.selectedFile.userObject()
                    if (wear.type == OUTERWEAR) {   //TODO: Move validation to SchoolSetup
                        JOptionPane.showMessageDialog(gameFrame, "Incorrect wear type.", null, JOptionPane.ERROR_MESSAGE)
                        gameFrame.dispose()
                        SchoolSetup(character)
                    } else {
                        character.gameState.undies = wear
                    }
                } catch (e: Exception) {
                    JOptionPane.showMessageDialog(gameFrame, "File error.\nRandom wear will be selected.", null, JOptionPane.ERROR_MESSAGE)
                    character.gameState.undies = randomWear(UNDERWEAR)
                }
            }
        }

        when (type) {
            UNDERWEAR -> character.gameState.undies = targetWear
            OUTERWEAR -> character.gameState.lower = targetWear
            BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE argument isn't supported")
        }
    }

    private fun randomWear(type: Wear.WearType): Wear {
        when (type) {
            UNDERWEAR -> return Wear.underwearList[Random().nextInt(Wear.underwearList.size - 3) + 3]   //First 3 wear list indices are special
            OUTERWEAR -> return Wear.outerwearList[Random().nextInt(Wear.outerwearList.size - 3) + 3]
            BOTH_SUITABLE -> throw IllegalArgumentException("BOTH_SUITABLE argument isn't supported")
        }
    }

    fun handleNextClicked() {
        updateUI()
        when (nextStage) {
            LEAVE_BED -> {
                //Making line 1 italic
                setLinesAsDialogue(1)
                if (!character.gameState.lower.isMissing) {
                    if (!character.gameState.undies.isMissing)
                    //Both character.gameState.lower clothes and character.gameState.undies
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some " + character.gameState.undies.insert() + " and " + character.gameState.lower.insert() + ",",
                                "not even worrying about what covers your chest.")
                    } else
                    //character.gameState.lower clothes only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some " + character.gameState.lower.insert() + ", quick to cover your " + character.gameState.undies.insert() + ",",
                                "not even worrying about what covers your chest.")
                    }
                } else {
                    if (!character.gameState.undies.isMissing)
                    //character.gameState.undies only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on " + character.gameState.undies.insert() + ",",
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
                        "Paying much less attention to your daily routine, you quickly run down the stairs, get a small glass of orange juice and chug it.",
                        "",
                        "The cold drink brings a chill down your spine as you collect your things and rush out the door to school.")

                passTime(1)
                offsetEmbarassment(3)
                offsetBelly(10.0)

                nextStage = GO_TO_CLASS
            }

            GO_TO_CLASS -> {
                //Displaying all values
                displayAllGameStateValues()

                if (!character.gameState.lower.isMissing) {
                    //Skirt blowing in the wind
                    if (character.gameState.lower.insert() == "skirt") {
                        setText("You rush into class, your " + character.gameState.lower.insert() + " blowing in the wind.",
                                "",
                                "Normally, you'd be worried your " + character.gameState.undies.insert() + " would be seen, but you can't worry about it right now.",
                                "You make it to your seat without a minute to spare.")
                    } else {
                        //Nothing is blowing in wind
                        setText("Trying your best to make up lost time, you rush into class and sit down to your seat without a minute to spare.")
                    }
                } else {
                    if (!character.gameState.undies.isMissing) {
                        setText("You rush into class; your classmates are looking at your " + character.gameState.undies.insert() + ".",
                                "You can't understand how you forgot to even put on any character.gameState.lower clothing,",
                                "and you know that your " + character.gameState.undies.insert() + " have definitely been seen.",
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

                offsetEmbarassment(2)
                nextStage = WALK_IN
            }

            WALK_IN -> {
                //If character.gameState.lower clothes is a skirt
                if (character.gameState.lower.insert() == "skirt" || character.gameState.lower.insert() == "skirt and tights" || character.gameState.lower.insert() == "skirt and tights") {
                    setLinesAsDialogue(1, 3)
                    setText("Next time you run into class, ${character.name},",
                            "your teacher says,",
                            "make sure you're wearing something less... revealing!",
                            "A chuckle passes over the classroom, and you can't help but feel a",
                            "tad bit embarrassed about your rush into class.")
                    offsetEmbarassment(5)
                } else
                //No outerwear
                {
                    if (character.gameState.lower.isMissing) {
                        setLinesAsDialogue(1)
                        setText("WHAT!? YOU CAME TO SCHOOL NAKED!?",
                                "your teacher shouts in disbelief.",
                                "",
                                "A chuckle passes over the classroom, and you can't help but feel extremely embarrassed",
                                "about your rush into class, let alone your nudity")
                        offsetEmbarassment(25)
                    } else {
                        setLinesAsDialogue(1, 3)
                        setText("Sit down, ${character.name}. You're running late.",
                                "your teacher says,",
                                "And next time, don't make so much noise entering the classroom!",
                                "A chuckle passes over the classroom, and you can't help but feel a tad bit embarrassed",
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
                scorer += character.gameState.embarrassment.toInt()
            }

            ASK_ACTION -> {
                //                do
                //                {
                //Called by teacher if unlucky
                actionList.clear()
                if (random.nextInt(20) == 5) {
                    setText("Suddenly, you hear the teacher call your name.")
                    nextStage = CALLED_ON
                    return
                }

                getBladderStatus()

                gameFrame.showActionUI("What now?")

                //Adding action choices
                when (timesPeeDenied) {
                    0 -> actionList.add("Ask the teacher to go pee")
                    1 -> actionList.add("Ask the teacher to go pee again")
                    2 -> actionList.add("Try to ask the teacher again")
                    3 -> actionList.add("Take a chance and ask the teacher (RISKY)")
                    else -> actionList.add("[Unavailable]")
                }

                if (!cornered) {
                    if (character.gender == Gender.FEMALE) {
                        actionList.add("Press on your crotch")
                    } else {
                        actionList.add("Squeeze your penis")
                    }
                } else {
                    actionList.add("[Unavailable]")
                }

                actionList.add("Rub thighs")

                if ((character.bladder.gameState as Bladder.GameState).fullness >= 100) {
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
                gameFrame.listChoice.setListData(actionList.toTypedArray())
                nextStage = CHOSE_ACTION
                passTime()
            }

            CHOSE_ACTION -> {
                nextStage = ASK_ACTION
                if (gameFrame.listChoice.isSelectionEmpty || gameFrame.listChoice.selectedValue == "[Unavailable]") {
                    //                    setText("You spent a few minutes doing nothing.");
                    handleNextClicked()
                    return
                }

                //Hiding the action selector and doing action job
                when (gameFrame.hideActionUI()) {
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
                        if ((random.nextInt(100) <= 15 + classmatesAwareness) and hardcore) {
                            nextStage = CAUGHT
                        } else {
                            nextStage = ASK_ACTION
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
                        if ((random.nextInt(100) <= 3 + classmatesAwareness) and hardcore) {
                            nextStage = CAUGHT
                        } else {
                            nextStage = ASK_ACTION
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
                            timeOffset = JOptionPane.showInputDialog("How much to wait?").toInt()
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
                        if ((random.nextInt(100) <= 1 + classmatesAwareness) and hardcore) {
                            nextStage = CAUGHT

                        } else {
                            nextStage = ASK_ACTION
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
                        if ((random.nextInt(100) <= 40) and !hardcore) {
                            if (!character.gameState.lower.isMissing) {
                                if (!character.gameState.undies.isMissing) {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.gameState.lower.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!character.gameState.undies.isMissing) {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.gameState.undies.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it,",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            }
                            //                            score *= 0.2;
                            //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -80% of points");
                            scorer /= 5
                            emptyBladder()
                            nextStage = ASK_ACTION
                            //Fail
                        } else {
                            setText("You ask the teacher if you can go out to the restroom.",
                                    "No, you can't go out, the director prohibited it.",
                                    "says the teacher.")
                            timesPeeDenied++
                        }

                    1 -> if ((random.nextInt(100) <= 10) and !hardcore) {
                        if (!character.gameState.lower.isMissing) {
                            if (!character.gameState.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.gameState.lower.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!character.gameState.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.gameState.undies.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it,",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        }
                        //                            score *= 0.22;
                        //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -70% of points");
                        scorer /= 3
                        emptyBladder()
                        nextStage = ASK_ACTION
                    } else {
                        setText("You ask the teacher again if you can go out to the restroom.",
                                "No, you can't! I already told you that the director prohibited it!",
                                "says the teacher.")
                        timesPeeDenied++
                    }

                    2 -> if ((random.nextInt(100) <= 30) and !hardcore) {
                        if (!character.gameState.lower.isMissing) {
                            if (!character.gameState.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.gameState.lower.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!character.gameState.undies.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + character.gameState.undies.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it,",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        }
                        //                            score *= 0.23;
                        //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -60% of points");
                        scorer /= 2
                        emptyBladder()
                        nextStage = ASK_ACTION
                    } else {
                        setText("You ask the teacher once more if you can go out to the restroom.",
                                "No, you can't! Stop asking me or there will be consequences!",
                                "says the teacher.")
                        timesPeeDenied++
                    }

                    3 -> {
                        if ((random.nextInt(100) <= 7) and !hardcore) {
                            if (!character.gameState.lower.isMissing) {
                                if (!character.gameState.undies.isMissing) {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.gameState.lower.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!character.gameState.undies.isMissing) {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + character.gameState.undies.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it,",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            }
                            //                            score *= 0.3;
                            //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -50% of points");
                            scorer /= 2
                            emptyBladder()
                            nextStage = ASK_ACTION
                        } else {
                            if (random.nextBoolean()) {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO NO NO! YOU CAN'T GO OUT! STAY IN THAT CORNER!,",
                                        "yells the teacher.")
                                cornered = true
                                //                            score += 1.3 * (90 - min / 3);
                                //                            scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +" + 1.3 * (90 - min / 3) + " score");
                                scorer += (1.3 * (90 - time / 3)).toInt()
                                offsetEmbarassment(5)
                            } else {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO NO NO! YOU CAN'T GO OUT! YOU WILL WRITE LINES AFTER THE LESSON!,",
                                        "yells the teacher.")
                                offsetEmbarassment(5)
                                stay = true
                                gameFrame.timeBar.maximum = 120
                                //                            scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
                                //                            score *= 1.6;
                                scorer *= 2
                            }
                        }
                        timesPeeDenied++
                    }
                }
                nextStage = ASK_ACTION
            }

            ASK_CHEAT -> {
                //                do
                //                {
                gameFrame.listChoice.setListData(cheatList.toTypedArray())
                gameFrame.showActionUI("Select a cheat:")
                nextStage = CHOSE_CHEAT
            }

            CHOSE_CHEAT -> {
                if (gameFrame.listChoice.isSelectionEmpty) {
                    nextStage = ASK_CHEAT
                    return
                }
                when (gameFrame.hideActionUI()) {
                    0 -> {
                        setText("You walk to the front corner of the classroom.")
                        cornered = true
                        nextStage = ASK_ACTION
                    }

                    1 -> {
                        setText("You decide to stay after class.")
                        stay = true
                        gameFrame.timeBar.maximum = 120
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
                        gameFrame.timeBar.maximum = 90
                        cornered = false
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
                        character.bladder.incontinence = JOptionPane.showInputDialog("How incontinent are you now?").toDouble()
                        character.bladder.maximalSphincterStrength = 100 / character.bladder.incontinence
                        character.bladder.gameState.sphincterStrength = character.bladder.maximalSphincterStrength
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
                        character.bladder.incontinence = JOptionPane.showInputDialog("How your bladder is full now?").toDouble()
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
                setText("${character.name}, why don't you come up to the board and solve this problem?,",
                        "says the teacher. Of course, you don't have a clue how to solve it.",
                        "You make your way to the front of the room and act lost, knowing you'll be stuck",
                        "up there for a while as the teacher explains it.",
                        "Well, you can't dare to hold yourself now...")
                passTime(5)
                scorer += 5
                nextStage = ASK_ACTION
            }

            CLASS_OVER -> {
                //Special hardcore scene trigger
                if (random.nextInt(100) <= 10 && hardcore && character.gender == Gender.FEMALE) {
                    nextStage = SURPRISE
                    return
                }
                if (stay) {
                    nextStage = AFTER_CLASS
                    return
                }

                if (random.nextBoolean()) {
                    setText("Lesson is finally over, and you're running to the restroom as fast as you can.",
                            "No, please... All cabins are occupied, and there's a line. You have to wait!")

                    scorer += 3
                    passTime()
                    return
                } else {
                    if (!character.gameState.lower.isMissing) {
                        if (!character.gameState.undies.isMissing) {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        } else {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.gameState.lower.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        }
                    } else {
                        if (!character.gameState.undies.isMissing) {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + character.gameState.undies.insert() + ",",
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
                gameFrame.listScroller.isVisible = false
                gameFrame.lblChoice.isVisible = false
                setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.",
                        "Despite all this, you try your best, but suddenly you're forced to stop.",
                        "You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere.",
                        "But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.")
                nextStage = WET
            }

            GIVE_UP -> {
                offsetEmbarassment(80)
                if (!character.gameState.lower.isMissing) {
                    if (!character.gameState.undies.isMissing) {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + character.gameState.undies.insert() + ".")
                    } else {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decided to pee in your " + character.gameState.lower.insert() + ".")
                    }
                } else {
                    if (!character.gameState.undies.isMissing) {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + character.gameState.undies.insert() + ".")
                    } else {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee where you are.")
                    }
                }
                nextStage = WET
            }

            WET -> {
                emptyBladder()
                character.gameState.embarrassment = 100
                if (!character.gameState.lower.isMissing) {
                    if (!character.gameState.undies.isMissing) {
                        setText("Before you can move an inch, pee quickly soaks through your " + character.gameState.undies.insert() + ",",
                                "floods your " + character.gameState.lower.insert() + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        setText("Before you can move an inch, pee quickly darkens your " + character.gameState.lower.insert() + " and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    }
                } else {
                    if (!character.gameState.undies.isMissing) {
                        setText("Before you can move an inch, pee quickly soaks through your " + character.gameState.undies.insert() + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        if (!cornered) {
                            setText("The heavy pee jets are hitting the seat and loudly leaking out from your " + character.gameState.undies.insert() + ".",
                                    "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                        } else {
                            setText("The heavy pee jets are hitting the floor and loudly leaking out from your " + character.gameState.undies.insert() + ".",
                                    "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                        }
                    }
                }
                nextStage = POST_WET
            }

            POST_WET -> {
                setLinesAsDialogue(2)
                if (!stay) {
                    if (character.gameState.lower.isMissing) {
                        if (character.gender == Gender.FEMALE && character.gameState.undies.isMissing) {
                            setText("People around you are laughing loudly.",
                                    character.name + " peed herself! Ahaha!")
                        } else {
                            if (character.gender == Gender.MALE && character.gameState.undies.isMissing) {
                                setText("People around you are laughing loudly.",
                                        character.name + " peed himself! Ahaha!")
                            } else {
                                setText("People around you are laughing loudly.",
                                        character.name + " wet h" + (if (character.gender == Gender.FEMALE) "er " else "is ") + character.gameState.undies.insert() + "! Ahaha")
                            }
                        }
                    } else {
                        if (character.gender == Gender.FEMALE) {
                            setText("People around you are laughing loudly.",
                                    character.name + " peed her " + character.gameState.lower.insert() + "! Ahaha")
                        } else {
                            setText("People around you are laughing loudly.",
                                    " peed his " + character.gameState.lower.insert() + "! Ahaha")
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
                if (character.gameState.lower.isMissing) {
                    if (character.gameState.undies.isMissing) {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.gameState.undies.insert() + " are clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    }
                } else {
                    if (character.gameState.undies.isMissing) {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.gameState.lower.insert() + " is clinging to your skin, a sign of your failure...", //TODO: Add "is/are" depending on character.gameState.lower clothes type
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + " are both clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    }
                }
                gameFrame.btnNext.isVisible = false
            }

            END_GAME -> {
                if (cheatsUsed) {
                    score = 0
                    scoreText = "\nYou've used the cheats, so you've got no score."
                }
                val scoreText2 = "Your score: " + score + "\n" + scoreText

                JOptionPane.showMessageDialog(gameFrame, scoreText2)
                gameFrame.btnNext.isVisible = false
            }

            CAUGHT -> {
                when (timesCaught) {
                    0 -> {
                        setText("It looks like a classmate has spotted that you've got to go badly.",
                                "Damn, he may spread that fact...")
                        offsetEmbarassment(3)
                        classmatesAwareness += 5
                        scorer += 3
                        timesCaught++
                    }

                    1 -> {
                        setLinesAsDialogue(3)
                        setText("You'he heard a suspicious whisper behind you.",
                                "Listening to the whisper, you've found out that they're saying that you need to go.",
                                "If I hold it until the lesson ends, I will beat them.")
                        offsetEmbarassment(8)
                        classmatesAwareness += 5
                        scorer += 8
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
                        offsetEmbarassment(12)
                        classmatesAwareness += 5
                        scorer += 12
                        timesCaught++
                    }

                    else -> {
                        setText("The chuckles are continiously passing over the classroom.",
                                "Everyone is watching you.",
                                "Oh god... this is so embarassing...")
                        offsetEmbarassment(20)
                        classmatesAwareness += 5
                        scorer += 20
                        timesCaught++
                    }
                }
                nextStage = ASK_ACTION
            }

        //The special hardcore scene
        /*
             * "Surprise" is an additional scene after the lesson where player is being caught by her classmate. He wants her to wet herself.
             * Triggering conditions: female, hardcore
             * Triggering chance: 10%
             */
            SURPRISE -> {

                //Resetting timesPeeDenied to use for that boy
                timesPeeDenied = 0

                specialHardcoreStage = true

                scorer += 70
                setText("The lesson is finally over, and you're running to the restroom as fast as you can.",
                        "But... You see $boyName staying in front of the restroom.",
                        "Suddenly, he takes you, not letting you to escape.")
                offsetEmbarassment(10)
                nextStage = SURPRISE_2
            }

            SURPRISE_2 -> {
                setLinesAsDialogue(1)
                setText("What do you want from me?!",
                        "He has brought you in the restroom and quickly put you on the windowsill.",
                        boyName + " has locked the restroom door (seems he has stolen the key), then he puts his palm on your character.gameState.belly and says:",
                        "I want you to wet yourself.")
                offsetEmbarassment(10)
                nextStage = SURPRISE_DIALOGUE
            }

            SURPRISE_DIALOGUE -> {
                setLinesAsDialogue(1)
                setText("No, please, don't do it, no...",
                        "I want to see you wet...",
                        "He slightly presses your character.gameState.belly again, you shook from the terrible pain",
                        "in your bladder and subconsciously rubbed your crotch. You have to do something!")
                offsetEmbarassment(10)

                actionList.add("Hit him")
                when (timesPeeDenied) {
                    0 -> actionList.add("Try to persuade him to let you pee")
                    1 -> actionList.add("Try to persuade him to let you pee again")
                    2 -> actionList.add("Take a chance and try to persuade him (RISKY)")
                }
                actionList.add("Pee yourself")

                gameFrame.listChoice.setListData(actionList.toTypedArray())
                gameFrame.showActionUI("Don't let him to do it!")
                nextStage = SURPRISE_CHOSE
            }

            SURPRISE_CHOSE -> {
                if (gameFrame.listChoice.isSelectionEmpty) {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName + " demands.",
                            "Then $boyName presses your bladder...")
                    nextStage = SURPRISE_WET_PRESSURE
                }

                //                actionNum = listChoice.getSelectedIndex();
                if (gameFrame.listChoice.selectedValue == "[Unavailable]") {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName + " demands.",
                            "Then $boyName presses your bladder...")
                    nextStage = SURPRISE_WET_PRESSURE
                }

                when (gameFrame.hideActionUI()) {
                    0 -> nextStage = HIT
                    1 -> nextStage = PERSUADE
                    2 -> nextStage = SURPRISE_WET_VOLUNTARY
                }
            }

            HIT -> if (random.nextInt(100) <= 20) {
                setLinesAsDialogue(2)
                nextStage = GameStage.END_GAME
                scorer += 40
                if (!character.gameState.lower.isMissing) {
                    if (!character.gameState.undies.isMissing) {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                "wearily flop down on the toilet and start peeing.")
                    } else {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + character.gameState.lower.insert() + ",",
                                "wearily flop down on the toilet and start peeing.")
                    }
                } else {
                    if (!character.gameState.undies.isMissing) {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + character.gameState.undies.insert() + ",",
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
                0 -> if (random.nextInt(100) <= 10) {
                    setLinesAsDialogue(1)
                    if (!character.gameState.lower.isMissing) {
                        if (!character.gameState.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.lower.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        }
                    } else {
                        if (!character.gameState.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.undies.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        }
                    }
                    scorer += 40
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee.",
                            "No, you can't pee in a cabin. I want you to wet yourself.,",
                            boyName + " says.")
                    timesPeeDenied++
                    nextStage = SURPRISE_DIALOGUE
                }

                1 -> if (random.nextInt(100) <= 5) {
                    if (!character.gameState.lower.isMissing) {
                        if (!character.gameState.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.lower.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        }
                    } else {
                        if (!character.gameState.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.undies.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        }
                    }
                    scorer += 60
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee again.",
                            "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.",
                            boyName + " demands.")
                    timesPeeDenied++
                    nextStage = SURPRISE_DIALOGUE
                }

                2 -> if (random.nextInt(100) <= 2) {
                    if (!character.gameState.lower.isMissing) {
                        if (!character.gameState.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.lower.insert() + " and " + character.gameState.undies.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.lower.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        }
                    } else {
                        if (!character.gameState.undies.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + character.gameState.undies.insert() + ",",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing character.gameState.undies $boyName's spectation.")
                        }
                    }

                    scorer += 80
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
                if (!character.gameState.undies.isMissing) {
                    if (!character.gameState.lower.isMissing) {
                        setText("You feel the warm pee stream",
                                "filling your " + character.gameState.undies.insert() + " and darkening your " + character.gameState.lower.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("You feel the warm pee stream",
                                "filling your " + character.gameState.undies.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!character.gameState.lower.isMissing) {
                        setText("You feel the warm pee stream",
                                "filling your " + character.gameState.lower.insert() + ".",
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
                if (!character.gameState.undies.isMissing) {
                    if (!character.gameState.lower.isMissing) {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.gameState.undies.insert() + " and darkening your " + character.gameState.lower.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.gameState.undies.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!character.gameState.lower.isMissing) {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + character.gameState.lower.insert() + ".",
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
                offsetBelly(character.gameState.thirst.toDouble())
                character.gameState.thirst = 0.0
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

    fun getBladderStatus(): Stage.Text = when (character.bladder.gameState!!.fullness) {
        in 0..20 -> Stage.Text(
                Stage.Text.Line("Feeling bored about the day, and not really caring about the class too much,"),
                Stage.Text.Line("you look to the clock, watching the minutes tick by.")
        )
        in 21..40 -> Stage.Text(
                Stage.Text.Line("Having to pee a little bit,"),
                Stage.Text.Line("you look to the clock, watching the minutes tick by and wishing the lesson to get over faster.")
        )
        in 41..60 -> Stage.Text(
                Stage.Text.Line("Clearly having to pee,"),
                Stage.Text.Line("you impatiently wait for the lesson end.")
        )
        in 61..80 -> Stage.Text(
                Stage.Text.Line("You feel the rather strong pressure in your bladder, and you're starting to get even more desperate."),
                Stage.Text.Line("Maybe I should ask teacher to go to the restroom? It hurts a bit...", true)
        )
        in 81..100 -> Stage.Text(
                Stage.Text.Line("Keeping all that urine inside will become impossible very soon.", true),
                Stage.Text.Line("You feel the terrible pain and pressure in your bladder, and you can almost definitely say you haven't needed to pee this badly in your life."),
                Stage.Text.Line("Ouch, it hurts a lot... I must do something about it now, or else...")
        )
        in 101..Int.MAX_VALUE ->
            Stage.Text(
                    Stage.Text.Line("This is really bad...", true),
                    Stage.Text.Line("You know that you can't keep it any longer and you may wet yourself in any moment and oh,"),
                    Stage.Text.Line("You can clearly see your bladder as it bulging."),
                    Stage.Text.Line("Ahhh... I cant hold it anymore!", true),
                    Stage.Text.Line(if (character.gender == Gender.FEMALE)
                        "Even holding your crotch doesn't seems to help you to keep it in."
                    else
                        "Even squeezing your penis doesn't seems to help you to keep it in.")
            )
        else -> throw IllegalStateException("""Bladder fullness isn't in the legal range (0..${Int.MAX_VALUE}:
                                            |${character.bladder.gameState!!.fullness}""".trimMargin())
    }

    private fun displayAllGameStateValues() {
        gameFrame.lblMinutes.isVisible = true
        gameFrame.lblSphPower.isVisible = true
        gameFrame.lblDryness.isVisible = true
        gameFrame.sphincterBar.isVisible = true
        gameFrame.drynessBar.isVisible = true
        gameFrame.timeBar.isVisible = true
    }

    /**
     * Increments the time by # minutes and all time-related parameters.
     *
     * @param time #
     */
    fun passTime(time: Int = 3) {
        offsetTime(time)
        offsetBladder(time * 1.5)
        offsetBelly(-time * 1.5)

        if (this.time >= 88) {
            setText("You hear the bell finally ring.")
            nextStage = CLASS_OVER
        }

        testWet()

        //Decrementing sphincter power for every 3 minutes
        for (i in 0..time - 1) {
            decaySphPower()
            if (character.gameState.belly != 0.0) {
                if (character.gameState.belly > 3) {
                    offsetBladder(2.0)
                } else {
                    offsetBladder(character.gameState.belly)
                    emptyBelly()
                }
            }
        }
        if (hardcore) {
            character.gameState.thirst += 2f
            if (character.gameState.thirst > MAXIMAL_THIRST) {
                nextStage = DRINK
            }
        }
        //Updating labels
        updateUI()
    }

    /**
     * Checks the wetting conditions, and if they are met, wetting
     */
    fun testWet() {
        //If bladder is filled more than 130 points in the normal mode and 100 points in the hardcore mode, forcing wetting
        if ((character.bladder.gameState!!.fullness >= character.bladder.maximalFullness) && !hardcore) {
            character.bladder.gameState.sphincterStrength = 0.0
            if (character.gameState.dryness < MINIMAL_DRYNESS) {
                if (specialHardcoreStage) {
                    nextStage = SURPRISE_ACCIDENT
                } else {
                    nextStage = ACCIDENT
                }
            }
        } else
        //If bladder is filled more than 100 points in the normal mode and 50 points in the hardcore mode, character has a chance to wet
        {
            if ((character.bladder.gameState!!.fullness > character.bladder.maximalFullness - 30) && !hardcore
                    || ((character.bladder.gameState!!.fullness > character.bladder.maximalFullness - 20) && hardcore)) {
                if (!hardcore) {
                    val wetChance = (3 * (character.bladder.gameState!!.fullness - 100) + character.gameState.embarrassment)
                    if (random.nextInt(100) < wetChance) {
                        character.bladder.gameState.sphincterStrength = 0.0
                        if (character.gameState.dryness < MINIMAL_DRYNESS) {
                            if (specialHardcoreStage) {
                                nextStage = SURPRISE_ACCIDENT
                            } else {
                                nextStage = ACCIDENT
                            }
                        }
                    }
                } else {
                    val wetChance = (5 * (character.bladder.gameState!!.fullness - 80))
                    if (random.nextInt(100) < wetChance) {
                        character.bladder.gameState.sphincterStrength = 0.0
                        if (character.gameState.dryness < MINIMAL_DRYNESS) {
                            if (specialHardcoreStage) {
                                nextStage = SURPRISE_ACCIDENT
                            } else {
                                nextStage = ACCIDENT
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Empties the bladder.
     */
    fun emptyBladder() {
        character.bladder.gameState!!.fullness = 0.0
        updateUI()
    }

    /**
     * Offsets bladder fulness by a specified amount.
     *
     * @param amount the amount to offset bladder fulness
     */
    fun offsetBladder(amount: Double) {
        character.bladder.gameState!!.fullness += amount
        if (character.bladder.gameState!!.fullness > 100 && !hardcore
                || character.bladder.gameState!!.fullness > 80 && hardcore) {
            gameFrame.lblBladder.foreground = Color.RED
        } else {
            gameFrame.lblBladder.foreground = Color.BLACK
        }
        updateUI()
    }

    /**
     * Empties the character.gameState.belly.
     */
    fun emptyBelly() {
        offsetBelly(-character.gameState.belly)
    }

    fun offsetBelly(amount: Double) {
        character.gameState.belly += amount
        if (character.gameState.belly < 0) {
            character.gameState.belly = 0.0
        }
        updateUI()
    }

    fun offsetEmbarassment(amount: Int) {
        character.gameState.embarrassment += amount.toShort()
        if (character.gameState.embarrassment < 0) {
            character.gameState.embarrassment = 0
        }
        updateUI()
    }

    fun offsetTime(amount: Int) {
        time += amount.toByte()
        if (drain and (time % 15 == 0)) {
            emptyBladder()
        }
        //Clothes drying over time
        if (character.gameState.dryness < character.gameState.lower.absorption + character.gameState.undies.absorption) {
            character.gameState.dryness += character.gameState.lower.dryingOverTime + character.gameState.undies.dryingOverTime * (amount / 3)
        }

        if (character.gameState.dryness > character.gameState.lower.absorption + character.gameState.undies.absorption) {
            character.gameState.dryness = character.gameState.lower.absorption + character.gameState.undies.absorption
        }
        updateUI()
    }

    /**
     * Decreases the sphincter power.
     */
    fun decaySphPower() {
        character.bladder.gameState.sphincterStrength -= (character.bladder.gameState!!.fullness / 30).toShort()
        if (character.bladder.gameState.sphincterStrength < 0) {
            character.gameState.dryness -= 5f //Decreasing character.gameState.dryness
            character.bladder.gameState!!.fullness -= 2.5 //Decreasing bladder level
            character.bladder.gameState.sphincterStrength = 0.0
            if (character.gameState.dryness > MINIMAL_DRYNESS) {
                //Naked
                if (character.gameState.lower.isMissing && character.gameState.undies.isMissing) {
                    setText("You feel the leak running down your thighs...",
                            "You're about to pee! You must stop it!")
                } else
                //Outerwear
                {
                    if (!character.gameState.lower.isMissing) {
                        setText("You see the wet spot expand on your " + character.gameState.lower.insert() + "!",
                                "You're about to pee! You must stop it!")
                    } else
                    //Underwear
                    {
                        if (!character.gameState.undies.isMissing) {
                            setText("You see the wet spot expand on your " + character.gameState.undies.insert() + "!",
                                    "You're about to pee! You must stop it!")
                        }
                    }
                }
            }

            if (character.gameState.dryness < MINIMAL_DRYNESS) {
                if (character.gameState.lower.isMissing && character.gameState.undies.isMissing) {
                    if (cornered) {
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
                    if (!character.gameState.lower.isMissing) {
                        setText("You see the wet spot expanding on your " + character.gameState.lower.insert() + "!",
                                "It's too much...")
                        nextStage = ACCIDENT
                        handleNextClicked()
                    } else {
                        if (!character.gameState.undies.isMissing) {
                            setText("You see the wet spot expanding on your " + character.gameState.undies.insert() + "!",
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
    fun rechargeSphPower(amount: Int) {
        character.bladder.gameState.sphincterStrength += amount.toShort()
        if (character.bladder.gameState.sphincterStrength > character.bladder.maximalSphincterStrength) {
            character.bladder.gameState.sphincterStrength = character.bladder.maximalSphincterStrength
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
        if (lines.size <= 0) {
            gameFrame.textLabel.text = ""
            return
        }

        var toSend = "<html><center>"

        for (i in lines.indices) {
            if (dialogueLines[i]) {
                toSend += "<i>\"" + lines[i] + "\"</i>"
            } else {
                toSend += lines[i]
            }
            toSend += "<br>"

        }
        toSend += "</center></html>"
        gameFrame.textLabel.text = toSend
        this.dialogueLines = BooleanArray(MAX_LINES)
    }

    internal fun updateUI() {
        TODO("Move functionality to field setters")
        /*
        gameFrame.lblName.text = character.name
        gameFrame.lblBladder.text = "bladder: " + Math.round(this.character.bladder.gameState.fullness) + "%"
        gameFrame.lbjEmbarrassment.text = "Embarassment: " + character.gameState.embarrassment
        gameFrame.lblBelly.text = "Belly: " + Math.round(character.gameState.belly) + "%"
        gameFrame.lblIncontinence.text = "Incontinence: " + character.bladder.gameState.fullness.incontinence + "x"
        gameFrame.lblMinutes.text = "Minutes: $time of 90"
        gameFrame.lblSphPower.text = "Pee holding ability: " + Math.round(character.bladder.gameState.sphincterStrength) + "%"
        gameFrame.lblDryness.text = "Clothes character.gameState.dryness: " + Math.round(character.gameState.dryness)
        gameFrame.lblUndies.text = "Undies: " + character.gameState.undies.gameState.color + " " + character.gameState.undies.name.toLowerCase()
        gameFrame.lblLower.text = "Lower: " + character.gameState.lower.gameState.color + " " + character.gameState.lower.name.toLowerCase()
        gameFrame.bladderBar.value = character.bladder.gameState.fullness.toInt()
        gameFrame.sphincterBar.value = Math.round(character.bladder.gameState.sphincterStrength).toInt()
        gameFrame.drynessBar.value = character.gameState.dryness.toInt()
        gameFrame.timeBar.value = time.toInt()
        gameFrame.lblThirst.text = "Thirst: " + Math.round(character.gameState.thirst) + "%"
        gameFrame.thirstBar.value = character.gameState.thirst.toInt()
        */
    }

    internal fun save() {
        gameFrame.fcGame.selectedFile = File(character.name)
        if (gameFrame.fcGame.showSaveDialog(gameFrame) == JFileChooser.APPROVE_OPTION) {
            try {
                val file = File(gameFrame.fcGame.selectedFile.absolutePath + ".lhhsav")
                val fos = FileOutputStream(file)
                val oos = ObjectOutputStream(fos)
                oos.writeObject(Save(this))
            } catch (e: IOException) {
                e.printStackTrace()
                JOptionPane.showMessageDialog(gameFrame, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
            }
        }
    }

    /**
     * Resets the game and values, optionally letting player to select new
     * parameters.
     *
     * @param newValues
     */
    fun reset(newValues: Boolean) {
        if (newValues) {
            SchoolSetup(character)
        } else {
            gameFrame.dispose()
            ALongHourAndAHalf(parametersStorage.character, parametersStorage.hardcore)
        }
    }

    companion object {
        //Maximal lines of a text
        private const val MAX_LINES = 9

        //Random stuff random


        /**
         * The character.gameState.dryness game over minimal threshold.
         */
        const val MINIMAL_DRYNESS = 0

        const val ACTION_BUTTONS_HEIGHT = 35
        const val ACTION_BUTTONS_WIDTH = 89
        const val ACTION_BUTTONS_TOP_BORDER = 510
    }
}
/**
 * Increments the time by 3 minutes and all time-related parameters.
 */
