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
*Male and Female (only affects gender pronouns (yes, that means crossdressing's allowed!))
*Random bladder amount upon awaking (Or preset)
*Choice of clothing (or, if in a rush, random choice of clothing (will be "gender conforming" clothing)
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
*   --------------------|-------------------------------------------|---------------------------------------------------------------------
*   Rosalie Elodie      |REDev987532(https://github.com/REDev987532) |Justice (https://www.omorashi.org/profile/25796-justice/)
*   JavaBird            |javabird25 (https://github.com/javabird25)  |FromRUSForum (https://www.omorashi.org/profile/89693-fromrusforum/)
*   Anna May            |AnnahMay (https://github.com/AnnahMay)      |Anna May (https://www.omorashi.org/profile/10087-anna-may/)
*   notwillnotcast      |?                                           |notwillnotcast (https://www.omorashi.org/profile/14935-notwillnotcast/)
*
*FINAL NOTE: While this is created by Rosalie Dev, she allows it to be posted
*freely, so long as she's creditted. She also states that this program is
*ABSOLUTELY FREE, not to mention she hopes you enjoy ^_^
*
*
*DEV NOTES: Look for bugs, there is always a bunch of them
 */
package longHourAndAHalf

import longHourAndAHalf.ALongHourAndAHalf.GameStage.*
import longHourAndAHalf.ALongHourAndAHalf.Gender.FEMALE
import longHourAndAHalf.ALongHourAndAHalf.Gender.MALE
import longHourAndAHalf.Wear.WearType.OUTERWEAR
import longHourAndAHalf.Wear.WearType.UNDERWEAR
import java.awt.Color
import java.awt.Font
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.*
import java.util.*
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.filechooser.FileFilter

/**
 * Describes an underwear of an outerwear of a character.
 *
 * @author JavaBird
 */
class Wear : Serializable {
    /**
     * The wear characterName (e. g. "Regular panties")
     */
    /**
     * @return the wear characterName (e. g. "Regular panties")
     */
    val name: String
    /**
     * The pressure of an wear.<br></br>1 point of a pressure takes 1 point from the
     * maximal bladder capacity.
     */
    /**
     * @return the pressure of an wear
     */
    val pressure: Double
    /**
     * The absorption of an wear.<br></br>1 point of an absorption can store 1 point
     * of a leaked pee.
     */
    /**
     * @return the absorption of an wear
     */
    val absorption: Double
    /**
     * The drying over time.<br></br>1 point = -1 pee unit per 3 minutes
     */
    /**
     * @return the drying over time.<br></br>1 = -1 pee unit per 3 minutes
     */
    val dryingOverTime: Double
    /**
     * Whether or not certain wear equals "No under/outerwear".
     */
    /**
     * @return whether or not certain wear equals "No under/outerwear".
     */
    val isMissing: Boolean
    /**
     * The insert characterName used in the game text (e. g. "panties")
     */
    private var insertName: String? = null
    /**
     * The wear assigned color.
     */
    /**
     * @return the color
     */
    /**
     * @param color the color to set
     */
    var color: String? = null
    /**
     * @return the type
     */
    /**
     * @param type the type to set
     */
    var type: WearType? = null

    /**
     * @param name           the wear characterName (e. g. "Regular panties")
     * @param insertName     the insert characterName used in the game text (e. g. "panties")
     * @param pressure       the pressure of an wear.<br></br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption     the absorption of an wear.<br></br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br></br>1 point = -1 pee unit per
     * 3 minutes
     */
    constructor(name: String, insertName: String, pressure: Double, absorption: Double, dryingOverTime: Double) {
        this.name = name
        this.insertName = insertName
        this.pressure = pressure
        this.absorption = absorption
        this.dryingOverTime = dryingOverTime
        isMissing = name == "No underwear" || name == "No outerwear"
    }

    /**
     * @param name           the wear characterName (e. g. "Regular panties")
     * @param insertName     the insert characterName used in the game text (e. g. "panties")
     * @param pressure       the pressure of an wear.<br></br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption     the absorption of an wear.<br></br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br></br>1 point = -1 pee unit per
     * 3 minutes
     * @param type           the wear type
     */
    constructor(name: String, insertName: String, pressure: Double, absorption: Double, dryingOverTime: Double, type: WearType) {
        this.name = name
        this.insertName = insertName
        this.pressure = pressure
        this.absorption = absorption
        this.dryingOverTime = dryingOverTime
        this.type = type
        isMissing = name == "No underwear" || name == "No outerwear"
    }

    /**
     * @param insertName the insert characterName (used in game text) to set
     */
    fun setInsertName(insertName: String) {
        this.insertName = insertName
    }

    /**
     * @return the insert characterName used in the game text (e. g. "panties")
     */
    fun insert(): String? {
        return insertName
    }

    enum class WearType {
        UNDERWEAR, OUTERWEAR, BOTH_SUITABLE
    }

    companion object {

        private const val serialVersionUID = 1L
        /**
         * List of all colors that clothes may have.
         */
        var colorList = arrayOf("Black", "Gray", "Red", "Orange", "Yellow", "Green", "Blue", "Dark blue", "Purple", "Pink")
    }
}

class ALongHourAndAHalf : JFrame {
    private val MAXIMAL_THIRST = 30
    /**
     * Character's characterName.
     */
    var characterName: String? = null
    /**
     * Character's lower body clothing.
     */
    var lower: Wear? = null
    /**
     * Character's undies.
     */
    var undies: Wear? = null
    /**
     * Current character gender.
     */
    var gender: Gender? = null
    /**
     * Text to be displayed after the game which shows how many [score]
     * did you get.
     */
    var scoreText: String? = ""
    /**
     * Current bladdder fulness.
     */
    var bladder = 5.0
    /**
     * Maximal bladder fulness.
     */
    var maxBladder: Int = 130
    /**
     * Makes the wetting chance higher after reaching 100% of the bladder
     * fulness.
     */
    var embarassment: Int = 0
    /**
     * Amount of a water in a belly.
     */
    var belly = 5.0
    /**
     * Amount of the character thirstiness.
     * Used only in hardcore mode.
     */
    var thirst = 0
    /**
     * Before 1.1:<br></br>
     * simply multiplies a bladder increasement.<br></br>
     * <br></br>
     * 1.1 and after:<br></br>
     * defines the sphincter weakening speed.
     */
    var incon = 1.0
    /**
     * Maximal time without squirming and leaking.
     */
    var maxSphincterPower: Int = 0
    /**
     * Current sphincter power. The higher bladder level, the faster power
     * consumption.
     */
    var sphincterPower: Int = 0
    /**
     * Amount of pee that clothes can store.
     */
    var dryness: Double = 0.0
    /**
     * The class time.
     */
    var time: Int = 0
    /**
     * Times teacher denied character to go out.
     */
    var timesPeeDenied: Int = 0
    /**
     * A number that shows a game difficulty - the higher score, the harder was
     * the game. Specific actions (for example, peeing in a restroom during a
     * lesson) reduce score points. Using the cheats will zero the score points.
     */
    var score = 0
    /**
     * Number of times player got caught holding pee.
     */
    var timesCaught = 0
    /**
     * Amount of embarassment raising every time character caught holding pee.
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
     * Whether or not pee drain cheat enabled: pee mysteriously vanishes every
     * 15 minutes.
     */
    var drain = false
    /**
     * Whether or not hardcore mode enabled: teacher never lets you pee, it's
     * harder to hold pee, you may get caught holding pee
     */
    var hardcore = false
    /**
     * Whether or not player has used cheats.
     */
    var cheatsUsed = false
    /**
     * List of all underwear types.
     */
    internal var underwearList = arrayOf(
            //        Name      Insert characterName     Pressure, Absotption, Drying over time
            Wear("Random", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 0.0),
            Wear("No underwear", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 1.0),
            Wear("Strings", "panties", 1.0, 2.0, 1.0),
            Wear("Tanga panties", "panties", 1.5, 3.0, 1.0),
            Wear("Regular panties", "panties", 2.0, 4.0, 1.0),
            Wear("\"Boy shorts\" panties", "panties", 4.0, 7.0, 1.0),
            Wear("String bikini", "bikini panties", 1.0, 1.0, 2.0),
            Wear("Regular bikini", "bikini panties", 2.0, 2.0, 2.0),
            Wear("Swimsuit", "swimsuit", 4.0, 2.5, 2.5),
            Wear("Light diaper", "diaper", 9.0, 50.0, 0.0),
            Wear("Normal diaper", "diaper", 18.0, 100.0, 0.0),
            Wear("Heavy diaper", "diaper", 25.0, 175.0, 0.0),
            Wear("Light pad", "pad", 2.0, 16.0, 0.25),
            Wear("Normal pad", "pad", 3.0, 24.0, 0.25),
            Wear("Big pad", "pad", 4.0, 32.0, 0.25),
            Wear("Pants", "pants", 2.5, 5.0, 1.0),
            Wear("Shorts-alike pants", "pants", 3.75, 7.5, 1.0),
            Wear("Anti-gravity pants", "pants", 0.0, 4.0, 1.0),
            Wear("Super-absorbing diaper", "diaper", 18.0, 600.0, 0.0)
    )
    /**
     * List of all outerwear types.
     */
    internal var outerwearList = arrayOf(
            //        Name      Insert characterName     Pressure, Absotption, Drying over time
            Wear("Random", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 0.0),
            Wear("No outerwear", "<b><i>LACK OF WEAR HANDLING$" + Thread.currentThread().stackTrace[0].lineNumber + "</i></b>", 0.0, 0.0, 1.0),
            Wear("Long jeans", "jeans", 7.0, 12.0, 1.2),
            Wear("Knee-length jeans", "jeans", 6.0, 10.0, 1.2),
            Wear("Short jeans", "shorts", 5.0, 8.5, 1.2),
            Wear("Very short jeans", "shorts", 4.0, 7.0, 1.2),
            Wear("Long trousers", "trousers", 9.0, 15.75, 1.4),
            Wear("Knee-length trousers", "trousers", 8.0, 14.0, 1.4),
            Wear("Short trousers", "shorts", 7.0, 12.25, 1.4),
            Wear("Very short trousers", "shorts", 6.0, 10.5, 1.4),
            Wear("Long skirt", "skirt", 5.0, 6.0, 1.7),
            Wear("Knee-length skirt", "skirt", 4.0, 4.8, 1.7),
            Wear("Short skirt", "skirt", 3.0, 3.6, 1.7),
            Wear("Mini skirt", "skirt", 2.0, 2.4, 1.7),
            Wear("Micro skirt", "skirt", 1.0, 1.2, 1.7),
            Wear("Long skirt and tights", "skirt and tights", 6.0, 7.5, 1.6),
            Wear("Knee-length skirt and tights", "skirt and tights", 5.0, 8.75, 1.6),
            Wear("Short skirt and tights", "skirt and tights", 4.0, 7.0, 1.6),
            Wear("Mini skirt and tights", "skirt and tights", 3.0, 5.25, 1.6),
            Wear("Micro skirt and tights", "skirt and tights", 2.0, 3.5, 1.6),
            Wear("Leggings", "leggings", 10.0, 11.0, 1.8),
            Wear("Short male jeans", "jeans", 5.0, 8.5, 1.2),
            Wear("Normal male jeans", "jeans", 7.0, 12.0, 1.2),
            Wear("Male trousers", "trousers", 9.0, 15.75, 1.4)
    )
    /**
     * List of all cheats.
     */
    internal var cheatList = arrayOf(
            "Go to the corner",
            "Stay after class",
            "Pee in a bottle",
            "End class right now",
            "Calm the teacher down",
            "Raise your hand",
            "Make your pee disappear regularly",
            "Set your incontinence level",
            "Toggle hardcore mode",
            "Set bladder fulness"
    )
    /**
     * List of all boy names for special hardcore scene.
     */
    internal var names = arrayOf("Mark", "Mike", "Jim", "Alex", "Ben", "Bill", "Dan")
    /**
     * Special hardcore scene boy characterName.
     */
    internal var boyName: String? = names[generator.nextInt(names.size)]
    /**
     * Actions list.
     */
    internal var actionList = ArrayList<String>()
    //Game frame variables declaration
    private var contentPane: JPanel? = null
    private var textPanel: JPanel? = null
    private var btnNext: JButton? = null
    private var btnReset: JButton? = null
    private var btnQuit: JButton? = null
    private var textLabel: JLabel? = null
    private var lblBelly: JLabel? = null
    private var lblBladder: JLabel? = null
    private var lblChoice: JLabel? = null
    private var lblEmbarassment: JLabel? = null
    private var lblIncon: JLabel? = null
    private var lblMinutes: JLabel? = null
    private var lblName: JLabel? = null
    private var btnNewGame: JButton? = null
    private var lblUndies: JLabel? = null
    private var lblLower: JLabel? = null
    private var lblSphPower: JLabel? = null
    private var lblDryness: JLabel? = null
    private var lblDefaultColor: Color? = null
    private var listChoice: JList<Any>? = null
    private var listScroller: JScrollPane? = null
    private var bladderBar: JProgressBar? = null
    private var sphincterBar: JProgressBar? = null
    private var drynessBar: JProgressBar? = null
    private var timeBar: JProgressBar? = null
    /**
     * A stage after the current stage.
     */
    private var nextStage: GameStage? = null
    /**
     * An array that contains boolean values that define *dialogue lines*.
     * Dialogue lines, unlike normal lines, are *italic*.
     */
    private var dialogueLines = BooleanArray(MAX_LINES)
    private var specialHardcoreStage = false
    private var fcWear: JFileChooser? = null
    private var fcGame: JFileChooser? = null
    private var btnSave: JButton? = null
    private var btnLoad: JButton? = null
    private var lblThirst: JLabel? = null
    private var thirstBar: JProgressBar? = null

    /**
     * @return TRUE - if character's gender is female<br></br>FALSE - if character's
     * gender is male
     */
    val isFemale: Boolean
        get() = gender == FEMALE

    /**
     * @return TRUE - if character's gender is male<br></br>FALSE - if character's
     * gender is female
     */
    val isMale: Boolean
        get() = gender == MALE

    internal constructor(name: String?, gndr: Gender?, diff: Boolean, inc: Double, bladder: Double, under: String?, outer: String?, undiesColor: String?, lowerColor: String?) {
        preConstructor(name, gndr, diff, inc, bladder)

        if (under == "Custom") {
            fcWear!!.dialogTitle = "Open an underwear file"
            if (fcWear!!.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                val file = fcWear!!.selectedFile
                try {
                    val fin = FileInputStream(file)
                    val ois = ObjectInputStream(fin)
                    undies = ois.readObject() as Wear
                    if (undies!!.type == OUTERWEAR) {
                        JOptionPane.showMessageDialog(null, "This isn't an underwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE)
                        dispose()
                        setupFramePre.main(arrayOf(""))
                    }
                } catch (e: IOException) {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
                    dispose()
                    setupFramePre.main(arrayOf(""))
                } catch (e: ClassNotFoundException) {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
                    dispose()
                    setupFramePre.main(arrayOf(""))
                }

            }
        }

        if (under == "Random") {
            undies = underwearList[generator.nextInt(underwearList.size)]
            while (undies!!.name == "Random")
            //...selecting random undies from the undies array.
            {
                undies = underwearList[generator.nextInt(underwearList.size)]
            }
            //If random undies weren't chosen...
        } else {
            //We look for the selected undies in the array
            for (iWear in underwearList) {
                //By comparing all possible undies' names with the selected undies string
                if (iWear.name == under) {
                    //If the selected undies were found, assigning current compared undies to the character's undies
                    undies = iWear
                    break
                }
            }
        }
        //If the selected undies weren't found
        if (undies == null) {
            JOptionPane.showMessageDialog(null, "Incorrect underwear selected. Setting random instead.", "Incorrect underwear", JOptionPane.WARNING_MESSAGE)
            undies = underwearList[generator.nextInt(underwearList.size)]
        }

        //Assigning color
        if (!undies!!.isMissing) {
            if (undiesColor != "Random") {
                undies!!.color = undiesColor
            } else {
                undies!!.color = Wear.colorList[generator.nextInt(Wear.colorList.size)]
            }
        } else {
            undies!!.color = ""
        }

        if (outer == "Custom") {
            fcWear!!.dialogTitle = "Open an outerwear file"
            if (fcWear!!.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                val file = fcWear!!.selectedFile
                try {
                    val fin = FileInputStream(file)
                    val ois = ObjectInputStream(fin)
                    lower = ois.readObject() as Wear
                    if (lower!!.type == UNDERWEAR) {
                        JOptionPane.showMessageDialog(null, "This isn't an outerwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE)
                        dispose()
                        setupFramePre.main(arrayOf(""))
                    }
                } catch (e: IOException) {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
                    dispose()
                    setupFramePre.main(arrayOf(""))
                } catch (e: ClassNotFoundException) {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
                    dispose()
                    setupFramePre.main(arrayOf(""))
                }

            }
        }

        //Same with the lower clothes
        if (outer == "Random") {
            lower = outerwearList[generator.nextInt(outerwearList.size)]
            while (lower!!.name == "Random") {
                lower = outerwearList[generator.nextInt(outerwearList.size)]
            }
        } else {
            for (iWear in outerwearList) {
                if (iWear.name == outer) {
                    lower = iWear
                    break
                }
            }
        }
        if (lower == null) {
            JOptionPane.showMessageDialog(null, "Incorrect outerwear selected. Setting random instead.", "Incorrect outerwear", JOptionPane.WARNING_MESSAGE)
            lower = outerwearList[generator.nextInt(outerwearList.size)]
        }

        //Assigning color
        if (!lower!!.isMissing) {
            if (lowerColor != "Random") {
                lower!!.color = lowerColor
            } else {
                lower!!.color = Wear.colorList[generator.nextInt(Wear.colorList.size)]
            }
        } else {
            lower!!.color = ""
        }

        //Displaying all values
        lblMinutes!!.isVisible = true
        lblSphPower!!.isVisible = true
        lblDryness!!.isVisible = true
        sphincterBar!!.isVisible = true
        drynessBar!!.isVisible = true
        timeBar!!.isVisible = true

        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore) {
            maxBladder = 100
            lblName!!.text = lblName!!.text + " [Hardcore]"
        }
        //Starting the game
        nextStage = LEAVE_BED

        postConstructor()
    }

    internal constructor(save: Save) {
        preConstructor(save.name, save.gender, save.hardcore, save.incontinence, save.bladder)
        undies = save.underwear
        lower = save.outerwear
        embarassment = save.embarassment
        dryness = save.dryness
        maxSphincterPower = save.maxSphincterPower
        sphincterPower = save.sphincterPower
        time = save.time
        nextStage = save.stage
        score = save.score
        scoreText = save.scoreText
        timesPeeDenied = save.timesPeeDenied
        timesCaught = save.timesCaught
        classmatesAwareness = save.classmatesAwareness
        stay = save.stay
        cornered = save.cornered
        drain = save.drain
        cheatsUsed = save.cheatsUsed
        boyName = save.boyName

        //Displaying all values
        lblMinutes!!.isVisible = true
        lblSphPower!!.isVisible = true
        lblDryness!!.isVisible = true
        sphincterBar!!.isVisible = true
        drynessBar!!.isVisible = true
        timeBar!!.isVisible = true
        handleNextClicked()
        postConstructor()
    }

    /**
     * Launch the application.
     *
     * @param name        the characterName of a character
     * @param gndr        the gender of a character
     * @param diff        the game difficulty - Normal or Hardcore
     * @param bladder     the bladder fullness at start
     * @param under       the underwear
     * @param outer       the outerwear
     * @param inc         the incontinence
     * @param undiesColor the underwear color
     * @param lowerColor  the outerwear color
     */
    internal fun preConstructor(name: String?, gndr: Gender?, diff: Boolean, inc: Double, bladder: Double) {
        //Saving parameters for the reset
        nameParam = name
        gndrParam = gndr
        incParam = inc
        bladderParam = bladder

        //Assigning constructor parameters to values
        this.characterName = name
        gender = gndr
        hardcore = diff
        incon = inc
        this.bladder = bladder
        maxSphincterPower = (100 / incon).toInt()
        sphincterPower = maxSphincterPower

        //Assigning the boy's characterName
        boyName = names[generator.nextInt(names.size)]

        //Setting up custom wear file chooser
        fcWear = JFileChooser()
        fcWear!!.fileFilter = object : FileFilter() {
            override fun accept(pathname: File): Boolean {
                var extension = ""
                val i = pathname.name.lastIndexOf('.')
                if (i > 0) {
                    extension = pathname.name.substring(i + 1)
                }
                return extension == "lhhwear"
            }

            override fun getDescription(): String {
                return "A Long Hour and a Half Custom wear"
            }
        }

        fcGame = JFileChooser()
        fcGame!!.fileFilter = object : FileFilter() {
            override fun accept(pathname: File): Boolean {
                var extension = ""
                val i = pathname.name.lastIndexOf('.')
                if (i > 0) {
                    extension = pathname.name.substring(i + 1)
                }
                return extension == "lhhsav"
            }

            override fun getDescription(): String {
                return "A Long Hour and a Half Save game"
            }
        }

        //Setting "No undrwear" insert characterName depending on character's gender
        //May be gone soon
        if (isMale) {
            underwearList[1].setInsertName("penis")
        } else {
            underwearList[1].setInsertName("crotch")
        }

        //Game window setup
        isResizable = true
        title = "A Long Hour and a Half"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setBounds(100, 100, 770, 594)
        setLocationRelativeTo(null)

        contentPane = JPanel()
        contentPane!!.background = Color.LIGHT_GRAY
        contentPane!!.border = EmptyBorder(5, 5, 5, 5)
        setContentPane(contentPane)
        contentPane!!.layout = null

        //Text panel setup
        textPanel = JPanel()
        textPanel!!.setBounds(10, 11, 740, 150)
        contentPane!!.add(textPanel)
        textPanel!!.layout = null
        textLabel = JLabel("")
        textLabel!!.horizontalAlignment = SwingConstants.CENTER
        textLabel!!.setBounds(0, 0, 740, 150)
        textPanel!!.add(textLabel)

        //"Next" button setup
        btnNext = JButton("Next")
        //        btnNext.setToolTipText("Hold down for the time warp");
        btnNext!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(arg0: MouseEvent?) {
                handleNextClicked()
            }
        })

        btnNext!!.setBounds(470, ACTION_BUTTONS_TOP_BORDER, 285, 35)
        contentPane!!.add(btnNext)

        //"Quit" button setup
        btnQuit = JButton("Quit")
        btnQuit!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(arg0: MouseEvent?) {
                System.exit(0)
            }
        })
        btnQuit!!.setBounds(192, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        contentPane!!.add(btnQuit)

        //"Save" button setup
        btnSave = JButton("Save")
        btnSave!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(arg0: MouseEvent?) {
                save()
            }
        })
        btnSave!!.setBounds(284, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        contentPane!!.add(btnSave)

        //"Load" button setup
        btnLoad = JButton("Load")
        btnLoad!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(arg0: MouseEvent?) {
                load()
            }
        })
        btnLoad!!.setBounds(376, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        contentPane!!.add(btnLoad)

        //"Reset" button setup
        btnReset = JButton("Reset")
        btnReset!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(arg0: MouseEvent?) {
                ALongHourAndAHalf.reset(false)
                dispose()
            }
        })
        btnReset!!.setBounds(10, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        btnReset!!.toolTipText = "Start the game over with the same parameters."
        contentPane!!.add(btnReset)

        //"New game" button setup
        btnNewGame = JButton("New game")
        btnNewGame!!.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(arg0: MouseEvent?) {
                ALongHourAndAHalf.reset(true)
                dispose()
            }
        })
        btnNewGame!!.setBounds(102, ACTION_BUTTONS_TOP_BORDER, ACTION_BUTTONS_WIDTH, ACTION_BUTTONS_HEIGHT)
        btnNewGame!!.toolTipText = "Start the game over with the another parameters."
        contentPane!!.add(btnNewGame)

        //Name label setup
        lblName = JLabel(name)
        lblName!!.font = Font("Tahoma", Font.PLAIN, 18)
        lblName!!.setBounds(20, 170, 200, 32)
        contentPane!!.add(lblName)

        //Bladder label setup
        lblBladder = JLabel("Bladder: " + Math.round(this.bladder.toFloat()) + "%")
        lblBladder!!.toolTipText = "<html>Normal game:<br>100% = need to hold, regular leaks<br>130% = peeing(game over)<br><br>Hardcore:<br>80% = need to hold, regular leaks<br>100% = peeing(game over)</html>"
        lblBladder!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblBladder!!.setBounds(20, 210, 200, 32)
        contentPane!!.add(lblBladder)
        lblDefaultColor = lblBladder!!.foreground

        //Embarassment label setup
        lblEmbarassment = JLabel("Embarassment: " + embarassment)
        lblEmbarassment!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblEmbarassment!!.setBounds(20, 240, 200, 32)
        lblEmbarassment!!.toolTipText = "Makes leaks more frequent"
        contentPane!!.add(lblEmbarassment)

        //Belly label setup
        lblBelly = JLabel("Belly: " + Math.round(belly) + "%")
        lblBelly!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblBelly!!.setBounds(20, 270, 200, 32)
        lblBelly!!.toolTipText = "<html>The water in your belly.<br>Any amount of water speeds the bladder filling up.</html>"
        contentPane!!.add(lblBelly)

        //Thirst label setup
        lblThirst = JLabel("Thirst: " + thirst + "%")
        lblThirst!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblThirst!!.setBounds(20, 480, 200, 32)
        lblThirst!!.toolTipText = "Character will automatically drink water at 30% of thirst."
        if (hardcore) {
            contentPane!!.add(lblThirst)
        }

        //Thirst bar setup
        thirstBar = JProgressBar()
        thirstBar!!.setBounds(16, 482, 455, 25)
        thirstBar!!.maximum = MAXIMAL_THIRST.toInt()
        thirstBar!!.value = thirst.toInt()
        thirstBar!!.toolTipText = "Character will automatically drink water at 30% of thirst."
        if (hardcore) {
            contentPane!!.add(thirstBar)
        }

        //Incontinence label setup
        lblIncon = JLabel("Incontinence: " + incon + "x")
        lblIncon!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblIncon!!.setBounds(20, 300, 200, 32)
        lblIncon!!.toolTipText = "Makes your bladder weaker"
        contentPane!!.add(lblIncon)

        //Time label setup
        lblMinutes = JLabel("Minutes: $time of 90")
        lblMinutes!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblMinutes!!.setBounds(20, 330, 200, 32)
        lblMinutes!!.isVisible = false
        contentPane!!.add(lblMinutes)

        //Sphincter power label setup
        lblSphPower = JLabel("Pee holding ability: " + Math.round(sphincterPower.toFloat()) + "%")
        lblSphPower!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblSphPower!!.setBounds(20, 360, 200, 32)
        lblSphPower!!.isVisible = false
        lblSphPower!!.toolTipText = "<html>Ability to hold pee.<br>Drains faster on higher bladder fulnesses.<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thigs.</html>"
        contentPane!!.add(lblSphPower)

        //Clothing dryness label setup
        lblDryness = JLabel("Clothes dryness: " + Math.round(dryness))
        lblDryness!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblDryness!!.setBounds(20, 390, 200, 32)
        lblDryness!!.isVisible = false
        lblDryness!!.toolTipText = "<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>"
        contentPane!!.add(lblDryness)

        //Choice label ("What to do?") setup
        lblChoice = JLabel()
        lblChoice!!.font = Font("Tahoma", Font.BOLD, 12)
        lblChoice!!.setBounds(480, 162, 280, 32)
        lblChoice!!.isVisible = false
        contentPane!!.add(lblChoice)

        //Action list setup
        listChoice = JList()
        listChoice!!.selectionMode = ListSelectionModel.SINGLE_SELECTION
        listChoice!!.layoutOrientation = JList.VERTICAL

        listScroller = JScrollPane(listChoice)
        listScroller!!.setBounds(472, 194, 280, 318)
        listScroller!!.isVisible = false
        contentPane!!.add(listScroller)

        //Bladder bar setup
        bladderBar = JProgressBar()
        bladderBar!!.setBounds(16, 212, 455, 25)
        bladderBar!!.maximum = 130
        bladderBar!!.value = this.bladder.toInt()
        bladderBar!!.toolTipText = "<html>Normal game:<br>100% = need to hold, regular leaks<br>130% = peeing(game over)<br><br>Hardcore:<br>80% = need to hold, regular leaks<br>100% = peeing(game over)</html>"
        contentPane!!.add(bladderBar)

        //Sphincter bar setup
        sphincterBar = JProgressBar()
        sphincterBar!!.setBounds(16, 362, 455, 25)
        sphincterBar!!.maximum = maxSphincterPower
        sphincterBar!!.value = sphincterPower
        sphincterBar!!.isVisible = false
        sphincterBar!!.toolTipText = "<html>Ability to hold pee.<br>Drains faster on higher bladder fulnesses.<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thigs.</html>"
        contentPane!!.add(sphincterBar)

        //Dryness bar setup
        drynessBar = JProgressBar()
        drynessBar!!.setBounds(16, 392, 455, 25)
        drynessBar!!.value = dryness.toInt()
        drynessBar!!.minimum = MINIMAL_DRYNESS
        drynessBar!!.isVisible = false
        drynessBar!!.toolTipText = "<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>"
        contentPane!!.add(drynessBar)

        //Time bar setup
        timeBar = JProgressBar()
        timeBar!!.setBounds(16, 332, 455, 25)
        timeBar!!.maximum = 90
        timeBar!!.value = time
        timeBar!!.isVisible = false
        contentPane!!.add(timeBar)

        //Coloring the characterName label according to the gender
        if (isFemale) {
            lblName!!.foreground = Color.MAGENTA
        } else {
            lblName!!.foreground = Color.CYAN
        }

        //Assigning the blank characterName if player didn't selected the characterName
        if (name!!.isEmpty()) {
            if (isFemale) {
                this.characterName = "Mrs. Nobody"
            } else {
                this.characterName = "Mr. Nobody"
            }
        }

        //Scoring bladder at start
        score("Bladder at start - $bladder%", '+', bladder)

        //Scoring incontinence
        score("Incontinence - " + Math.round(incon) + "x", '*', incon)

        /*
                    Hardcore, it will be harder to hold pee:
                    Teacher will never let character to go pee
                    Character's bladder will have less capacity
                    Character may get caught holding pee
         */
        if (hardcore) {
            score("Hardcore", '*', 2)
        }
    }

    internal fun postConstructor() {
        //Calculating dryness and maximal bladder capacity values
        dryness = lower!!.absorption + undies!!.absorption
        maxBladder -= (lower!!.pressure + undies!!.pressure).toInt()

        drynessBar!!.maximum = dryness.toInt()

        //Finishing saving parameters for game reset
        outerParam = lower!!.name
        underParam = undies!!.name
        underColorParam = undies!!.color
        outerColorParam = lower!!.color

        //Undies label setup
        lblUndies = JLabel("Undies: " + undies!!.color + " " + undies!!.name.toLowerCase())
        lblUndies!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblUndies!!.setBounds(20, 420, 400, 32)
        contentPane!!.add(lblUndies)

        //Lower label setup
        lblLower = JLabel("Lower: " + lower!!.color + " " + lower!!.name.toLowerCase())
        lblLower!!.font = Font("Tahoma", Font.PLAIN, 15)
        lblLower!!.setBounds(20, 450, 400, 32)
        contentPane!!.add(lblLower)

        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore) {
            maxBladder = 100
            lblName!!.name = lblName!!.name + " [Hardcore]"
        }

        handleNextClicked()

        //Displaying the frame
        isVisible = true
    }

    private fun handleNextClicked() {
        updateUI()
        when (nextStage) {
            LEAVE_BED -> {
                //Making line 1 italic
                setLinesAsDialogue(1)
                if (!lower!!.isMissing) {
                    if (!undies!!.isMissing)
                    //Both lower clothes and undies
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some " + undies!!.insert() + " and " + lower!!.insert() + ",",
                                "not even worrying about what covers your chest.")
                    } else
                    //Lower clothes only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some " + lower!!.insert() + ", quick to cover your " + undies!!.insert() + ",",
                                "not even worrying about what covers your chest.")
                    }
                } else {
                    if (!undies!!.isMissing)
                    //Undies only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on " + undies!!.insert() + ",",
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
                lblMinutes!!.isVisible = true
                lblSphPower!!.isVisible = true
                lblDryness!!.isVisible = true
                sphincterBar!!.isVisible = true
                drynessBar!!.isVisible = true
                timeBar!!.isVisible = true

                if (!lower!!.isMissing) {
                    //Skirt blowing in the wind
                    if (lower!!.insert() == "skirt") {
                        setText("You rush into class, your " + lower!!.insert() + " blowing in the wind.",
                                "",
                                "Normally, you'd be worried your " + undies!!.insert() + " would be seen, but you can't worry about it right now.",
                                "You make it to your seat without a minute to spare.")
                    } else {
                        //Nothing is blowing in wind
                        setText("Trying your best to make up lost time, you rush into class and sit down to your seat without a minute to spare.")
                    }
                } else {
                    if (!undies!!.isMissing) {
                        setText("You rush into class; your classmates are looking at your " + undies!!.insert() + ".",
                                "You can't understand how you forgot to even put on any lower clothing,",
                                "and you know that your " + undies!!.insert() + " have definitely been seen.",
                                "You make it to your seat without a minute to spare.")
                    } else {
                        if (isFemale) {
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
                //If lower clothes is a skirt
                if (lower!!.insert() == "skirt" || lower!!.insert() == "skirt and tights" || lower!!.insert() == "skirt and tights") {
                    setLinesAsDialogue(1, 3)
                    setText("Next time you run into class, $characterName,",
                            "your teacher says,",
                            "make sure you're wearing something less... revealing!",
                            "A chuckle passes over the classroom, and you can't help but feel a",
                            "tad bit embarrassed about your rush into class.")
                    offsetEmbarassment(5)
                } else
                //No outerwear
                {
                    if (lower!!.isMissing) {
                        setLinesAsDialogue(1)
                        setText("WHAT!? YOU CAME TO SCHOOL NAKED!?",
                                "your teacher shouts in disbelief.",
                                "",
                                "A chuckle passes over the classroom, and you can't help but feel extremely embarrassed",
                                "about your rush into class, let alone your nudity")
                        offsetEmbarassment(25)
                    } else {
                        setLinesAsDialogue(1, 3)
                        setText("Sit down, $characterName. You're running late.",
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
                score("Embarassment at start - $incon pts", '+', embarassment.toInt())
            }

            ASK_ACTION -> {
                //                do
                //                {
                //Called by teacher if unlucky
                actionList.clear()
                if (generator.nextInt(20) == 5) {
                    setText("Suddenly, you hear the teacher call your characterName.")
                    nextStage = CALLED_ON
                    return
                }

                //Bladder: 0-20
                if (bladder <= 20) {
                    setText("Feeling bored about the day, and not really caring about the class too much,",
                            "you look to the clock, watching the minutes tick by.")
                }
                //Bladder: 20-40
                if (bladder > 20 && bladder <= 40) {
                    setText("Having to pee a little bit,",
                            "you look to the clock, watching the minutes tick by and wishing the lesson to get over faster.")
                }
                //Bladder: 40-60
                if (bladder > 40 && bladder <= 60) {
                    setText("Clearly having to pee,",
                            "you impatiently wait for the lesson end.")
                }
                //Bladder: 60-80
                if (bladder > 60 && bladder <= 80) {
                    setLinesAsDialogue(2)
                    setText("You feel the rather strong pressure in your bladder, and you're starting to get even more desperate.",
                            "Maybe I should ask teacher to go to the restroom? It hurts a bit...")
                }
                //Bladder: 80-100
                if (bladder > 80 && bladder <= 100) {
                    setLinesAsDialogue(1, 3)
                    setText("Keeping all that urine inside will become impossible very soon.",
                            "You feel the terrible pain and pressure in your bladder, and you can almost definitely say you haven't needed to pee this badly in your life.",
                            "Ouch, it hurts a lot... I must do something about it now, or else...")
                }
                //Bladder: 100-130
                if (bladder > 100 && bladder <= 130) {
                    setLinesAsDialogue(1, 3)
                    if (isFemale) {
                        setText("This is really bad...",
                                "You know that you can't keep it any longer and you may wet yourself in any moment and oh,",
                                "You can clearly see your bladder as it bulging.",
                                "Ahhh... I cant hold it anymore!!!",
                                "Even holding your crotch doesn't seems to help you to keep it in.")
                    } else {
                        setText("This is really bad...",
                                "You know that you can't keep it any longer and you may wet yourself in any moment and oh,",
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

                if (!cornered) {
                    if (isFemale) {
                        actionList.add("Press on your crotch")
                    } else {
                        actionList.add("Squeeze your penis")
                    }
                } else {
                    actionList.add("[Unavailable]")
                }

                actionList.add("Rub thighs")

                if (bladder >= 100) {
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
                listChoice!!.setListData(actionList.toTypedArray())
                nextStage = CHOSE_ACTION
                passTime()
            }

            CHOSE_ACTION -> {
                nextStage = ASK_ACTION
                if (listChoice!!.isSelectionEmpty || listChoice!!.selectedValue == "[Unavailable]") {
                    //                    setText("You spent a few minutes doing nothing.");
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
                        if ((generator.nextInt(100) <= 15 + classmatesAwareness) and hardcore) {
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
                        if ((generator.nextInt(100) <= 3 + classmatesAwareness) and hardcore) {
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
                            timeOffset = java.lang.Integer.parseInt(JOptionPane.showInputDialog("How much to wait?"))
                            if (time < 1 || time > 125) {
                                throw NumberFormatException()
                            }
                            passTime(timeOffset)
                        } //Ignoring invalid output
                        catch (e: NumberFormatException) {
                            nextStage = ASK_ACTION
                            return
                        } catch (e: NullPointerException) {
                            nextStage = ASK_ACTION
                            return
                        }

                        //Chance to be caught by classmates in hardcore mode
                        if ((generator.nextInt(100) <= 1 + classmatesAwareness) and hardcore) {
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

                    -1 -> setText("Bugs.")
                    else -> setText("Bugs.")
                }
            }

            ASK_TO_PEE -> {
                when (timesPeeDenied) {
                    0 ->
                        //Success
                        if ((generator.nextInt(100) <= 40) and !hardcore) {
                            if (!lower!!.isMissing) {
                                if (!undies!!.isMissing) {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + lower!!.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!undies!!.isMissing) {
                                    setText("You ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + undies!!.insert() + ",",
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
                            score("Restroom usage during the lesson", '/', 5)
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
                        if (!lower!!.isMissing) {
                            if (!undies!!.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + lower!!.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!undies!!.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + undies!!.insert() + ",",
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
                        score("Restroom usage during the lesson", '/', 3.3)
                        emptyBladder()
                        nextStage = ASK_ACTION
                    } else {
                        setText("You ask the teacher again if you can go out to the restroom.",
                                "No, you can't! I already told you that the director prohibited it!",
                                "says the teacher.")
                        timesPeeDenied++
                    }

                    2 -> if ((generator.nextInt(100) <= 30) and !hardcore) {
                        if (!lower!!.isMissing) {
                            if (!undies!!.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            } else {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + lower!!.insert() + ",",
                                        "wearily flop down on the toilet and start peeing.")
                            }
                        } else {
                            if (!undies!!.isMissing) {
                                setText("You ask the teacher again if you can go out to the restroom.",
                                        "Yes, you may.",
                                        "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                        "You enter it, pulled down your " + undies!!.insert() + ",",
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
                        score("Restroom usage during the lesson", '/', 2.5)
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
                            if (!lower!!.isMissing) {
                                if (!undies!!.isMissing) {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                } else {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + lower!!.insert() + ",",
                                            "wearily flop down on the toilet and start peeing.")
                                }
                            } else {
                                if (!undies!!.isMissing) {
                                    setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                            "Yes, you may.",
                                            "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                            "You enter it, pulled down your " + undies!!.insert() + ",",
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
                            score("Restroom usage during the lesson", '/', 2)
                            emptyBladder()
                            nextStage = ASK_ACTION
                        } else {
                            if (generator.nextBoolean()) {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,",
                                        "yells the teacher.")
                                cornered = true
                                //                            score += 1.3 * (90 - min / 3);
                                //                            scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +" + 1.3 * (90 - min / 3) + " score");
                                score("Stayed on corner " + (90 - time) + " minutes", '+', 1.3 * (90 - time / 3))
                                offsetEmbarassment(5)
                            } else {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! YOU WILL WRITE LINES AFTER THE LESSON!!!,",
                                        "yells the teacher.")
                                offsetEmbarassment(5)
                                stay = true
                                timeBar!!.maximum = 120
                                //                            scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
                                //                            score *= 1.6;
                                score("Wrote lines after the lesson", '*', 1.6)
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
                listChoice!!.setListData(cheatList)
                showActionUI("Select a cheat:")
                nextStage = CHOSE_CHEAT
            }

            CHOSE_CHEAT -> {
                if (listChoice!!.isSelectionEmpty) {
                    nextStage = ASK_CHEAT
                    return
                }
                when (hideActionUI()) {
                    0 -> {
                        setText("You walk to the front corner of the classroom.")
                        cornered = true
                        nextStage = ASK_ACTION
                    }

                    1 -> {
                        setText("You decide to stay after class.")
                        stay = true
                        timeBar!!.maximum = 120
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
                        timeBar!!.maximum = 90
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
                        incon = java.lang.Double.parseDouble(JOptionPane.showInputDialog("How incontinent are you now?"))
                        maxSphincterPower = (100 / incon).toInt()
                        sphincterPower = maxSphincterPower
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
                        incon = java.lang.Double.parseDouble(JOptionPane.showInputDialog("How your bladder is full now?"))
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
                setText("" + characterName + ", why don't you come up to the board and solve this problem?,",
                        "says the teacher. Of course, you don't have a clue how to solve it.",
                        "You make your way to the front of the room and act lost, knowing you'll be stuck",
                        "up there for a while as the teacher explains it.",
                        "Well, you can't dare to hold yourself now...")
                passTime(5)
                score("Called on the lesson", '+', 5)
                nextStage = ASK_ACTION
            }

            CLASS_OVER -> {
                //Special hardcore scene trigger
                if (generator.nextInt(100) <= 10 && hardcore and isFemale) {
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

                    score("Waited for a free cabin in the restroom", '+', 3)
                    passTime()
                    return
                } else {
                    if (!lower!!.isMissing) {
                        if (!undies!!.isMissing) {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        } else {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower!!.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.")
                        }
                    } else {
                        if (!undies!!.isMissing) {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + undies!!.insert() + ",",
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
                setText("Hey, $characterName, you wanted to escape? You must stay after classes!",
                        "Please... let me go to the restroom... I can't hold it...",
                        "No, $characterName, you can't go to the restroom now! This will be as punishment.",
                        "And don't think you can hold yourself either! I'm watching you...")

                passTime()
            }

            ACCIDENT -> {
                listScroller!!.isVisible = false
                lblChoice!!.isVisible = false
                setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.",
                        "Despite all this, you try your best, but suddenly you're forced to stop.",
                        "You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere.",
                        "But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.")
                nextStage = WET
            }

            GIVE_UP -> {
                offsetEmbarassment(80)
                if (!lower!!.isMissing) {
                    if (!undies!!.isMissing) {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + undies!!.insert() + ".")
                    } else {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decided to pee in your " + lower!!.insert() + ".")
                    }
                } else {
                    if (!undies!!.isMissing) {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + undies!!.insert() + ".")
                    } else {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee where you are.")
                    }
                }
                nextStage = WET
            }

            WET -> {
                emptyBladder()
                embarassment = 100
                if (!lower!!.isMissing) {
                    if (!undies!!.isMissing) {
                        setText("Before you can move an inch, pee quickly soaks through your " + undies!!.insert() + ",",
                                "floods your " + lower!!.insert() + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        setText("Before you can move an inch, pee quickly darkens your " + lower!!.insert() + " and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    }
                } else {
                    if (!undies!!.isMissing) {
                        setText("Before you can move an inch, pee quickly soaks through your " + undies!!.insert() + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                    } else {
                        if (!cornered) {
                            setText("The heavy pee jets are hitting the seat and loudly leaking out from your " + undies!!.insert() + ".",
                                    "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                        } else {
                            setText("The heavy pee jets are hitting the floor and loudly leaking out from your " + undies!!.insert() + ".",
                                    "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.")
                        }
                    }
                }
                nextStage = POST_WET
            }

            POST_WET -> {
                setLinesAsDialogue(2)
                if (!stay) {
                    if (lower!!.isMissing) {
                        if (isFemale && undies!!.isMissing) {
                            setText("People around you are laughing loudly.",
                                    characterName!! + " peed herself! Ahaha!!!")
                        } else {
                            if (isMale && undies!!.isMissing) {
                                setText("People around you are laughing loudly.",
                                        characterName!! + " peed himself! Ahaha!!!")
                            } else {
                                setText("People around you are laughing loudly.",
                                        characterName + " wet h" + (if (isFemale) "er " else "is ") + undies!!.insert() + "! Ahaha!!")
                            }
                        }
                    } else {
                        if (isFemale) {
                            setText("People around you are laughing loudly.",
                                    characterName + " peed her " + lower!!.insert() + "! Ahaha!!")
                        } else {
                            setText("People around you are laughing loudly.",
                                    " peed his " + lower!!.insert() + "! Ahaha!!")
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
                if (lower!!.isMissing) {
                    if (undies!!.isMissing) {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + undies!!.insert() + " are clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    }
                } else {
                    if (undies!!.isMissing) {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + lower!!.insert() + " is clinging to your skin, a sign of your failure...", //TODO: Add "is/are" depending on lower clothes type
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    } else {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + lower!!.insert() + " and " + undies!!.insert() + " are both clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!")
                    }
                }
                btnNext!!.isVisible = false
            }

            END_GAME -> {
                if (cheatsUsed) {
                    score = 0
                    scoreText = "\nYou've used the cheats, so you've got no score."
                }
                val scoreText2 = "Your score: " + score + "\n" + scoreText

                JOptionPane.showMessageDialog(this, scoreText2)
                btnNext!!.isVisible = false
            }

            CAUGHT -> {
                when (timesCaught) {
                    0 -> {
                        setText("It looks like a classmate has spotted that you've got to go badly.",
                                "Damn, he may spread that fact...")
                        offsetEmbarassment(3)
                        classmatesAwareness += 5
                        score("Caught holding pee", '+', 3)
                        timesCaught++
                    }

                    1 -> {
                        setLinesAsDialogue(3)
                        setText("You'he heard a suspicious whisper behind you.",
                                "Listening to the whisper, you've found out that they're saying that you need to go.",
                                "If I hold it until the lesson ends, I will beat them.")
                        offsetEmbarassment(8)
                        classmatesAwareness += 5
                        score("Caught holding pee", '+', 8)
                        timesCaught++
                    }

                    2 -> {
                        if (isFemale) {
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
                        score("Caught holding pee", '+', 12)
                        timesCaught++
                    }

                    else -> {
                        setText("The chuckles are continiously passing over the classroom.",
                                "Everyone is watching you.",
                                "Oh god... this is so embarassing...")
                        offsetEmbarassment(20)
                        classmatesAwareness += 5
                        score("Caught holding pee", '+', 20)
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

                score("Got the \"surprise\" by " + boyName!!, '+', 70)
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
                        boyName!! + " has locked the restroom door (seems he has stolen the key), then he puts his palm on your belly and says:",
                        "I want you to wet yourself.")
                offsetEmbarassment(10)
                nextStage = SURPRISE_DIALOGUE
            }

            SURPRISE_DIALOGUE -> {
                setLinesAsDialogue(1)
                setText("No, please, don't do it, no...",
                        "I want to see you wet...",
                        "He slightly presses your belly again, you shook from the terrible pain",
                        "in your bladder and subconsciously rubbed your crotch. You have to do something!")
                offsetEmbarassment(10)

                actionList.add("Hit him")
                when (timesPeeDenied) {
                    0 -> actionList.add("Try to persuade him to let you pee")
                    1 -> actionList.add("Try to persuade him to let you pee again")
                    2 -> actionList.add("Take a chance and try to persuade him (RISKY)")
                }
                actionList.add("Pee yourself")

                listChoice!!.setListData(actionList.toTypedArray())
                showActionUI("Don't let him to do it!")
                nextStage = SURPRISE_CHOSE
            }

            SURPRISE_CHOSE -> {
                if (listChoice!!.isSelectionEmpty) {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName!! + " demands.",
                            "Then $boyName presses your bladder...")
                    nextStage = SURPRISE_WET_PRESSURE
                }

                //                actionNum = listChoice.getSelectedIndex();
                if (listChoice!!.selectedValue == "[Unavailable]") {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName!! + " demands.",
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
                score("Successful hit on $boyName's groin", '+', 40)
                if (!lower!!.isMissing) {
                    if (!undies!!.isMissing) {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                "wearily flop down on the toilet and start peeing.")
                    } else {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + lower!!.insert() + ",",
                                "wearily flop down on the toilet and start peeing.")
                    }
                } else {
                    if (!undies!!.isMissing) {
                        setText("You hit $boyName's groin.",
                                "Ouch!.. You, little bitch...",
                                "Then he left the restroom quickly.",
                                "You got off from the windowsill while holding your crotch,",
                                "opened the cabin door, entered it, pulled down your " + undies!!.insert() + ",",
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
                        boyName!! + " pressed your bladder violently...")
            }

            PERSUADE -> when (timesPeeDenied) {
                0 -> if (generator.nextInt(100) <= 10) {
                    setLinesAsDialogue(1)
                    if (!lower!!.isMissing) {
                        if (!undies!!.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + lower!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        }
                    } else {
                        if (!undies!!.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + undies!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        }
                    }
                    score("Persuaded $boyName to pee", '+', 40)
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee.",
                            "No, you can't pee in a cabin. I want you to wet yourself.,",
                            boyName!! + " says.")
                    timesPeeDenied++
                    nextStage = SURPRISE_DIALOGUE
                }

                1 -> if (generator.nextInt(100) <= 5) {
                    if (!lower!!.isMissing) {
                        if (!undies!!.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + lower!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        }
                    } else {
                        if (!undies!!.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + undies!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        }
                    }
                    score("Persuaded $boyName to pee", '+', 60)
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee again.",
                            "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.",
                            boyName!! + " demands.")
                    timesPeeDenied++
                    nextStage = SURPRISE_DIALOGUE
                }

                2 -> if (generator.nextInt(100) <= 2) {
                    if (!lower!!.isMissing) {
                        if (!undies!!.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + lower!!.insert() + " and " + undies!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + lower!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        }
                    } else {
                        if (!undies!!.isMissing) {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "pull down your " + undies!!.insert() + ",",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        } else {
                            setText("Ok, you may, but you'll let me watch you pee.",
                                    "states $boyName. You enter the cabin,",
                                    "stand over the toilet and start peeing under $boyName's spectation.")
                        }
                    }

                    score("Persuaded $boyName to pee", '+', 80)
                    emptyBladder()
                    nextStage = END_GAME
                } else {
                    setText("You ask $boyName if you can pee again desperately.",
                            "No, you can't pee in a cabin. You will wet yourself right now,",
                            boyName!! + " demands.",
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
                if (!undies!!.isMissing) {
                    if (!lower!!.isMissing) {
                        setText("You feel the warm pee stream",
                                "filling your " + undies!!.insert() + " and darkening your " + lower!!.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("You feel the warm pee stream",
                                "filling your " + undies!!.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!lower!!.isMissing) {
                        setText("You feel the warm pee stream",
                                "filling your " + lower!!.insert() + ".",
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
                if (!undies!!.isMissing) {
                    if (!lower!!.isMissing) {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + undies!!.insert() + " and darkening your " + lower!!.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    } else {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + undies!!.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.")
                    }
                } else {
                    if (!lower!!.isMissing) {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + lower!!.insert() + ".",
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
                offsetBelly(thirst.toDouble())
                thirst = 0
                nextStage = ASK_ACTION
            }

            else -> setText("Error parsing button. Next text is unavailable, text #" + nextStage!!)
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
     * Increments the time by # minutes and all time-related parameters.
     *
     * @param time #
     */
    @JvmOverloads
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
        for (i in 0 until time) {
            decaySphPower()
            if (belly != 0.0) {
                if (belly > 3) {
                    offsetBladder(2.0)
                } else {
                    offsetBladder(belly)
                    emptyBelly()
                }
            }
        }
        if (hardcore) {
            thirst += 2
            if (thirst > MAXIMAL_THIRST) {
                nextStage = DRINK
            }
        }
        //Updating labels
        updateUI()
    }

    /**
     * Checks the wetting conditions, and if they are met, wetting TODO in v1.4:
     * add diapers and pads support
     */
    fun testWet() {
        //If bladder is filled more than 130 points in the normal mode and 100 points in the hardcore mode, forcing wetting
        if ((bladder >= maxBladder) and !hardcore) {
            sphincterPower = 0
            if (dryness < MINIMAL_DRYNESS) {
                if (specialHardcoreStage) {
                    nextStage = SURPRISE_ACCIDENT
                } else {
                    nextStage = ACCIDENT
                }
            }
        } else
        //If bladder is filled more than 100 points in the normal mode and 50 points in the hardcore mode, character has a chance to wet
        {
            if ((bladder > maxBladder - 30) and !hardcore or ((bladder > maxBladder - 20) and hardcore)) {
                if (!hardcore) {
                    val wetChance = (3 * (bladder - 100) + embarassment)
                    if (generator.nextInt(100) < wetChance) {
                        sphincterPower = 0
                        if (dryness < MINIMAL_DRYNESS) {
                            if (specialHardcoreStage) {
                                nextStage = SURPRISE_ACCIDENT
                            } else {
                                nextStage = ACCIDENT
                            }
                        }
                    }
                } else {
                    val wetChance = (5 * (bladder - 80))
                    if (generator.nextInt(100) < wetChance) {
                        sphincterPower = 0
                        if (dryness < MINIMAL_DRYNESS) {
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
        bladder = 0.0
        lblBladder!!.text = "Bladder: " + bladder + "%"
        updateUI()
    }

    /**
     * Offsets bladder fulness by a specified amount.
     *
     * @param amount the amount to offset bladder fulness
     */
    fun offsetBladder(amount: Double) {
        bladder += amount/* * incon*///Incontinence does another job after 1.1
        if (bladder > 100 && !hardcore || bladder > 80 && hardcore) {
            lblBladder!!.foreground = Color.RED
        } else {
            lblBladder!!.foreground = lblDefaultColor
        }
        updateUI()
    }

    /**
     * Empties the belly.
     */
    fun emptyBelly() {
        offsetBelly(-belly)
    }

    fun offsetBelly(amount: Double) {
        belly += amount
        if (belly < 0) {
            belly = 0.0
        }
        updateUI()
    }

    fun offsetEmbarassment(amount: Int) {
        embarassment += amount
        if (embarassment < 0) {
            embarassment = 0
        }
        updateUI()
    }

    fun offsetTime(amount: Int) {
        time += amount
        if (drain and (time % 15 == 0)) {
            emptyBladder()
        }
        //Clothes drying over time
        if (dryness < lower!!.absorption + undies!!.absorption) {
            dryness += lower!!.dryingOverTime + undies!!.dryingOverTime * (amount / 3)
        }

        if (dryness > lower!!.absorption + undies!!.absorption) {
            dryness = lower!!.absorption + undies!!.absorption
        }
        updateUI()
    }

    /**
     * Decreases the sphincter power.
     */
    fun decaySphPower() {
        sphincterPower -= (bladder / 30).toInt()
        if (sphincterPower < 0) {
            dryness -= 5f //Decreasing dryness
            bladder -= 2.5 //Decreasing bladder level
            sphincterPower = 0
            if (dryness > MINIMAL_DRYNESS) {
                //Naked
                if (lower!!.isMissing && undies!!.isMissing) {
                    setText("You feel the leak running down your thighs...",
                            "You're about to pee! You must stop it!")
                } else
                //Outerwear
                {
                    if (!lower!!.isMissing) {
                        setText("You see the wet spot expand on your " + lower!!.insert() + "!",
                                "You're about to pee! You must stop it!")
                    } else
                    //Underwear
                    {
                        if (!undies!!.isMissing) {
                            setText("You see the wet spot expand on your " + undies!!.insert() + "!",
                                    "You're about to pee! You must stop it!")
                        }
                    }
                }
            }

            if (dryness < MINIMAL_DRYNESS) {
                if (lower!!.isMissing && undies!!.isMissing) {
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
                    if (!lower!!.isMissing) {
                        setText("You see the wet spot expanding on your " + lower!!.insert() + "!",
                                "It's too much...")
                        nextStage = ACCIDENT
                        handleNextClicked()
                    } else {
                        if (!undies!!.isMissing) {
                            setText("You see the wet spot expanding on your " + undies!!.insert() + "!",
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
        sphincterPower += amount
        if (sphincterPower > maxSphincterPower) {
            sphincterPower = maxSphincterPower
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
            textLabel!!.text = ""
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
        textLabel!!.text = toSend
        this.dialogueLines = BooleanArray(MAX_LINES)
    }

    /**
     * Operates the player score.
     *
     * @param message the reason to manipulate score
     * @param mode    add, substract, divide or multiply
     * @param points  amount of points to operate
     */
    fun score(message: String, mode: Char, points: Int) {
        when (mode) {
            '+' -> {
                score += points
                scoreText += "\n$message: +$points points"
            }
            '-' -> {
                score -= points
                scoreText += "\n$message: -$points points"
            }
            '*' -> {
                score *= points
                scoreText += "\n" + message + ": +" + points * 100 + "% of points"
            }
            '/' -> {
                score /= points
                scoreText += "\n" + message + ": -" + 100 / points + "% of points"
            }
            else -> System.err.println("score() method used incorrectly, message: \"" + message + "\"")
        }
    }

    /**
     * Operates the player score.
     *
     * @param message the reason to manipulate score
     * @param mode    add, substract, divide or multiply
     * @param points  amount of points to operate
     */
    fun score(message: String, mode: Char, points: Double) {
        when (mode) {
            '+' -> {
                score += points.toInt()
                scoreText += "\n$message: +$points points"
            }
            '-' -> {
                score -= points.toInt()
                scoreText += "\n$message: -$points points"
            }
            '*' -> {
                score *= points.toInt()
                scoreText += "\n" + message + ": +" + points * 100 + "% of points"
            }
            '/' -> {
                score /= points.toInt()
                scoreText += "\n" + message + ": -" + 100 / points + "% of points"
            }
            else -> System.err.println("score() method used incorrectly, message: \"" + message + "\"")
        }
    }

    internal fun updateUI() {
        try {
            lblName!!.text = characterName
            lblBladder!!.text = "Bladder: " + this.bladder + "%"
            lblEmbarassment!!.text = "Embarassment: " + embarassment
            lblBelly!!.text = "Belly: " + Math.round(belly) + "%"
            lblIncon!!.text = "Incontinence: " + incon + "x"
            lblMinutes!!.text = "Minutes: $time of 90"
            lblSphPower!!.text = "Pee holding ability: " + sphincterPower + "%"
            lblDryness!!.text = "Clothes dryness: " + Math.round(dryness)
            lblUndies!!.text = "Undies: " + undies!!.color + " " + undies!!.name.toLowerCase()
            lblLower!!.text = "Lower: " + lower!!.color + " " + lower!!.name.toLowerCase()
            bladderBar!!.value = bladder.toInt()
            sphincterBar!!.value = sphincterPower
            drynessBar!!.value = dryness.toInt()
            timeBar!!.value = time
            lblThirst!!.text = "Thirst: " + thirst + "%"
            thirstBar!!.value = thirst
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal fun hideActionUI(): Int {
        val choice = listChoice!!.selectedIndex
        actionList.clear()
        lblChoice!!.isVisible = false
        listScroller!!.isVisible = false
        return choice
    }

    internal fun showActionUI(actionGroupName: String) {
        lblChoice!!.isVisible = true
        lblChoice!!.text = actionGroupName
        listScroller!!.isVisible = true
    }

    internal fun save() {
        fcGame!!.selectedFile = File(characterName!!)
        if (fcGame!!.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            val file = File(fcGame!!.selectedFile.absolutePath + ".lhhsav")
            //            PrintStream writer;
            val fout: FileOutputStream
            val oos: ObjectOutputStream
            try {
                val save = Save()
                save.name = characterName
                save.gender = gender
                save.hardcore = hardcore
                save.incontinence = incon
                save.bladder = bladder
                save.underwear = undies
                save.outerwear = lower
                save.embarassment = embarassment
                save.dryness = dryness
                save.maxSphincterPower = maxSphincterPower
                save.sphincterPower = sphincterPower
                save.time = time
                save.stage = nextStage
                save.score = score
                save.scoreText = scoreText
                save.timesPeeDenied = timesPeeDenied
                save.timesCaught = timesCaught
                save.classmatesAwareness = classmatesAwareness
                save.stay = stay
                save.cornered = cornered
                save.drain = drain
                save.cheatsUsed = cheatsUsed
                save.boyName = boyName

                //                writer = new PrintStream(file);
                fout = FileOutputStream(file)
                oos = ObjectOutputStream(fout)
                oos.writeObject(save)
            } catch (e: IOException) {
                e.printStackTrace()
                JOptionPane.showMessageDialog(this, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
            }

        }
    }

    internal fun load() {
        if (fcGame!!.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            val file = fcGame!!.selectedFile
            try {
                val fin = FileInputStream(file)
                val ois = ObjectInputStream(fin)
                val save = ois.readObject() as Save
                ALongHourAndAHalf(save)
                dispose()
            } catch (e: IOException) {
                e.printStackTrace()
                JOptionPane.showMessageDialog(this, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
                JOptionPane.showMessageDialog(this, "File error.", "Error", JOptionPane.ERROR_MESSAGE)
            }

        }
    }

    internal enum class GameStage {
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

    enum class Gender {
        MALE, FEMALE
    }

    companion object {

        //Maximal lines of a text
        private val MAX_LINES = 9
        /**
         * The dryness game over minimal threshold.
         */
        private val MINIMAL_DRYNESS = 0
        private val ACTION_BUTTONS_HEIGHT = 35
        private val ACTION_BUTTONS_WIDTH = 89
        private val ACTION_BUTTONS_TOP_BORDER = 510
        //Random stuff generator
        var generator = Random()
        //Parameters used for a game reset
        internal var nameParam: String? = null
        internal var gndrParam: Gender? = null
        internal var diffParam: Boolean = false
        internal var incParam: Double = 0.0
        internal var bladderParam = 0.0
        internal var underParam: String? = null
        internal var outerParam: String? = null
        internal var underColorParam: String? = null
        internal var outerColorParam: String? = null

        /**
         * Resets the game and values, optionally letting player to select new
         * parameters.
         *
         * @param newValues
         */
        private fun reset(newValues: Boolean) {
            if (newValues) {
                setupFramePre().isVisible = true
            } else {
                ALongHourAndAHalf(nameParam, gndrParam, diffParam, incParam, bladderParam, underParam, outerParam, underColorParam, outerColorParam)
            }
        }
    }
}
/**
 * Increments the time by 3 minutes and all time-related parameters.
 */
