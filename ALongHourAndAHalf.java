/*
*ALongHourAndAHalf Vers. 1.3
*
*Dev: Rosalie Elodie, JavaBird
*
*Version History:
*0.1: Default game mechanics shown, non interactable. No playability, no customization. Not all game mechanics even implemented, purely a showcase program.
*0.2: MASSIVE REWRITE! (Thanks to Anna May! This is definitely the format I want!)
*1.0 Added interactivity, improved code, added hardcore mode(this isn't working now) and... cheats!
*1.1 Reintegrated the two versions
*1.2 New hardcore features:
*       Classmates can be aware that you've to go
*       Less bladder capacity
*    Improved bladder: it's more realistic now
*    Balanced pee holding methods
*    New game options frame
*    Even more clothes
*    Cleaned and documented code a bit
*1.3 Bug fixes
*    Interface improvements
*    Game text refining
*
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

package omo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import static omo.ALongHourAndAHalf.GameStage.*;
import static omo.ALongHourAndAHalf.Gender.*;
import static omo.ALongHourAndAHalf.generator;
import static omo.Wear.WearType.*;

/**
 * Describes an underwear of an outerwear of a character.
 *
 * @author JavaBird
 */
class Wear
{
    /**
     * List of all colors that clothes may have.
     */
    static String[] colorList =
    {
        "Black", "Gray", "Red", "Orange", "Yellow", "Green", "Blue", "Dark blue", "Purple", "Pink"
    };

    /**
     * The wear name (e. g. "Regular panties")
     */
    private final String name;

    /**
     * The insert name used in the game text (e. g. "panties")
     */
    private String insertName;

    /**
     * The pressure of an wear.<br>1 point of a pressure takes 1 point from the
     * maximal bladder capacity.
     */
    private final float pressure;

    /**
     * The absorption of an wear.<br>1 point of an absorption can store 1 point
     * of a leaked pee.
     */
    private final float absorption;

    /**
     * The drying over time.<br>1 point = -1 pee unit per 3 minutes
     */
    private final float dryingOverTime;

    /**
     * The wear assigned color.
     */
    private String color;

    /**
     * Whether or not certain wear equals "No under/outerwear".
     */
    private final boolean missing;
    private WearType type;

    /**
     * @param name the wear name (e. g. "Regular panties")
     * @param insertName the insert name used in the game text (e. g. "panties")
     * @param pressure the pressure of an wear.<br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption the absorption of an wear.<br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     * 3 minutes
     */
    Wear(String name, String insertName, float pressure, float absorption, float dryingOverTime)
    {
        this.name = name;
        this.insertName = insertName;
        this.pressure = pressure;
        this.absorption = absorption;
        this.dryingOverTime = dryingOverTime;
        missing = name.equals("No underwear") || name.equals("No outerwear");
    }

    /**
     * @param name the wear name (e. g. "Regular panties")
     * @param insertName	the insert name used in the game text (e. g. "panties")
     * @param pressure	the pressure of an wear.<br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption	the absorption of an wear.<br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     * 3 minutes
     * @param type the wear type
     */
    Wear(String name, String insertName, float pressure, float absorption, float dryingOverTime, WearType type)
    {
        this.name = name;
        this.insertName = insertName;
        this.pressure = pressure;
        this.absorption = absorption;
        this.dryingOverTime = dryingOverTime;
        this.type = type;
        missing = name.equals("No underwear") || name.equals("No outerwear");
    }

    /**
     * @param insertName the insert name (used in game text) to set
     */
    public void setInsertName(String insertName)
    {
        this.insertName = insertName;
    }

    /**
     * @return the wear name (e. g. "Regular panties")
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the pressure of an wear
     */
    public float getPressure()
    {
        return pressure;
    }

    /**
     * @return the absorption of an wear
     */
    public float getAbsorption()
    {
        return absorption;
    }

    /**
     * @return the insert name used in the game text (e. g. "panties")
     */
    public String insert()
    {
        return insertName;
    }

    /**
     * @return the drying over time.<br>1 = -1 pee unit per 3 minutes
     */
    public float getDryingOverTime()
    {
        return dryingOverTime;
    }

    /**
     * @return the color
     */
    public String getColor()
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color)
    {
        this.color = color;
    }

    /**
     * @return whether or not certain wear equals "No under/outerwear".
     */
    boolean isMissing()
    {
        return missing;
    }

    /**
     * @return the type
     */
    public WearType getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(WearType type)
    {
        this.type = type;
    }
    
    public enum WearType
    {
        UNDERWEAR, OUTERWEAR, BOTH_SUITABLE
    }
}

@SuppressWarnings("serial")
public class ALongHourAndAHalf extends JFrame
{

    //Maximal lines of a text
    private final static int MAX_LINES = 9;

    //Random stuff generator
    public static Random generator = new Random();

    //Parameters used for a game reset
    static String nameParam;
    static Gender gndrParam;
    static boolean diffParam;
    static float incParam;
    static int bladderParam;
    static String underParam;
    static String outerParam;
    static String underColorParam;
    static String outerColorParam;

    /**
     * The dryness game over minimal threshold.
     */
    private static final int MINIMAL_DRYNESS = 0;

    /**
     * Resets the game and values, optionally letting player to select new
     * parameters.
     *
     * @param newValues
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private static void reset(boolean newValues)
    {
        if (newValues)
        {
            new setupFramePre().setVisible(true);
        } else
        {
            new ALongHourAndAHalf(nameParam, gndrParam, diffParam, incParam, bladderParam, underParam, outerParam, underColorParam, outerColorParam);
        }
    }

    //Game frame variables declaration
    private final JPanel contentPane;
    private final JPanel textPanel;

    private final JButton btnNext;
    private final JButton btnReset;
    private final JButton btnQuit;

    private final JLabel textLabel;
    private final JLabel lblBelly;
    private final JLabel lblBladder;
    private final JLabel lblChoice;
    private final JLabel lblEmbarassment;
    private final JLabel lblIncon;
    private final JLabel lblMinutes;
    private final JLabel lblName;
    private final JButton btnNewGame;
    private final JLabel lblUndies;
    private final JLabel lblLower;
    private final JLabel lblSphPower;
    private final JLabel lblDryness;
    private final Color lblDefaultColor;
    private final JList<Object> listChoice;

    private final JScrollPane listScroller;

    private final JProgressBar bladderBar;
    private final JProgressBar sphincterBar;
    private final JProgressBar drynessBar;
    private final JProgressBar timeBar;

    /**
     * List of all underwear types.
     */
    Wear[] underwearList
            =
            {
                //        Name      Insert name     Pressure, Absotption, Drying over time
                new Wear("Random", "THIS IS A BUG", 0, 0, 0),
                new Wear("No underwear", "THIS IS A BUG", 0, 0, 1),
                new Wear("Strings", "panties", 1, 2, 1),
                new Wear("Tanga panties", "panties", 1.5F, 3, 1),
                new Wear("Regular panties", "panties", 2, 4, 1),
                new Wear("\"Boy shorts\" panties", "panties", 4, 7, 1),
                new Wear("String bikini", "bikini panties", 1, 1, 2),
                new Wear("Regular bikini", "bikini panties", 2, 2, 2),
                new Wear("Swimsuit", "swimsuit", 4, 2.5F, 2.5F),
                new Wear("Light diaper", "diaper", 4.5F, 10, 0),
                new Wear("Normal diaper", "diaper", 9, 20, 0),
                new Wear("Heavy diaper", "diaper", 18, 30, 0),
                new Wear("Light pad", "pad", 1, 8, 0.25F),
                new Wear("Normal pad", "pad", 1.5F, 12, 0.25F),
                new Wear("Big pad", "pad", 2, 16, 0.25F),
                new Wear("Pants", "pants", 2.5F, 5, 1),
                new Wear("Shorts-alike pants", "pants", 3.75F, 7.5F, 1),
                new Wear("Anti-gravity pants", "pants", 0, 4, 1),
                new Wear("Super-absorbing diaper", "diaper", 18, 200, 0)
            };

    /**
     * List of all outerwear types.
     */
    Wear[] outerwearList
            =
            {
                //        Name      Insert name     Pressure, Absotption, Drying over time
                new Wear("Random", "THIS IS A BUG", 0, 0, 0),
                new Wear("No outerwear", "THIS IS A BUG", 0, 0, 1),
                new Wear("Long jeans", "jeans", 7, 12, 1.2F),
                new Wear("Knee-length jeans", "jeans", 6, 10, 1.2F),
                new Wear("Short jeans", "shorts", 5, 8.5F, 1.2F),
                new Wear("Very short jeans", "shorts", 4, 7, 1.2F),
                new Wear("Long trousers", "trousers", 9, 15.75F, 1.4F),
                new Wear("Knee-length trousers", "trousers", 8, 14, 1.4F),
                new Wear("Short trousers", "shorts", 7, 12.25F, 1.4F),
                new Wear("Very short trousers", "shorts", 6, 10.5F, 1.4F),
                new Wear("Long skirt", "skirt", 5, 6, 1.7F),
                new Wear("Knee-length skirt", "skirt", 4, 4.8F, 1.7F),
                new Wear("Short skirt", "skirt", 3, 3.6F, 1.7F),
                new Wear("Mini skirt", "skirt", 2, 2.4F, 1.7F),
                new Wear("Micro skirt", "skirt", 1, 1.2F, 1.7F),
                new Wear("Long skirt and tights", "skirt and tights", 6, 7.5F, 1.6F),
                new Wear("Knee-length skirt and tights", "skirt and tights", 5, 8.75F, 1.6F),
                new Wear("Short skirt and tights", "skirt and tights", 4, 7, 1.6F),
                new Wear("Mini skirt and tights", "skirt and tights", 3, 5.25F, 1.6F),
                new Wear("Micro skirt and tights", "skirt and tights", 2, 3.5F, 1.6F),
                new Wear("Leggings", "leggings", 10, 11, 1.8F),
                new Wear("Short male jeans", "jeans", 5, 8.5F, 1.2F),
                new Wear("Normal male jeans", "jeans", 7, 12, 1.2F),
                new Wear("Male trousers", "trousers", 9, 15.75F, 1.4F)
            };

    /**
     * List of all cheats.
     */
    String[] cheatList
            =
            {
                "Go to the corner", "Stay after class", "Pee in a bottle", "End class right now",
                "Calm the teacher down", "Raise your hand", "Make your pee disappear regularly",
                "Set your incontinence level", "Toggle hardcore mode", "Set bladder fulness"
            };

    /**
     * List of all boy names for special hardcore scene.
     */
    String names[]
            =
            {
                "Mark",
                "Mike",
                "Jim",
                "Alex",
                "Ben",
                "Bill",
                "Dan"
            };

    /**
     * Special hardcore scene boy name.
     */
    String boyName = names[generator.nextInt(names.length)];

    /**
     * Actions list.
     */
    ArrayList<String> actionList = new ArrayList<>();

    /**
     * A stage after the current stage.
     */
    private GameStage nextStage;

    /**
     * Character's name.
     */
    public String name;

    /**
     * Character's lower body clothing.
     */
    public Wear lower;

    /**
     * Character's undies.
     */
    public Wear undies;

    /**
     * Current character gender.
     */
    public Gender gender;

    /**
     * Text to be displayed after the game which shows how many {@link score}
     * did you get.
     */
    public String scoreText;

    /**
     * Current bladdder fulness.
     */
    public float bladder = 5.0F;

    /**
     * Maximal bladder fulness.
     */
    public float maxBladder = 130;

    /**
     * Makes the wetting chance higher after reaching 100% of the bladder
     * fulness.
     */
    public byte embarassment = 0;

    /**
     * Amount of a water in a belly.
     */
    public double belly = 5.0;

    /**
     * Before 1.1:<br>
     * simply multiplies a bladder increasement.<br>
     * <br>
     * 1.1 and after:<br>
     * defines the sphincter weakening speed.
     */
    public float incon = 1.0F;

    /**
     * Maximal time without squirming and leaking.
     */
    public double maxSphincterPower;

    /**
     * Current sphincter power. The higher bladder level, the faster power
     * consumption.
     */
    public double sphincterPower;

    /**
     * Amount of pee that clothes can store.
     */
    public double dryness;

    /**
     * The class time.
     */
    public byte min = 0;

    /**
     * Times teacher denied character to go out.
     */
    public byte timesPeeDenied = 0;

    /**
     * A number that shows a game difficulty - the higher score, the harder was
     * the game. Specific actions (for example, peeing in a restroom during a
     * lesson) reduce score points. Using the cheats will zero the score points.
     */
    public int score = 0;

    /**
     * Number of times player got caught holding pee.
     */
    public byte timesCaught = 0;

    /**
     * Amount of embarassment raising every time character caught holding pee.
     */
    public int classmatesAwareness = 0;

    /**
     * Whether or not charecter has to stay 30 minutes after class.
     */
    public boolean stay = false;

    /**
     * Whether or not character currently stands in the corner and unable to
     * hold crotch.
     */
    public boolean cornered = false;

    /**
     * Whether or not pee drain cheat enabled: pee mysteriously vanishes every
     * 15 minutes.
     */
    public boolean drain = false;

    /**
     * Whether or not hardcore mode enabled: teacher never lets you pee, it's
     * harder to hold pee, you may get caught holding pee
     */
    public boolean hardcore = false;

    /**
     * An array that contains boolean values that define <i>dialogue lines</i>.
     * Dialogue lines, unlike normal lines, are <i>italic</i>.
     */
    private boolean[] dialogueLines = new boolean[MAX_LINES];

    /**
     * Whether or not player has used cheats.
     */
    public boolean cheatsUsed = false;
    private final JFileChooser fc;

    /**
     * Launch the application.
     *
     * @param name the name of a character
     * @param gndr the gender of a character
     * @param diff the game difficulty - Normal or Hardcore
     * @param bladder the bladder fullness at start
     * @param under the underwear
     * @param outer the outerwear
     * @param inc the incontinence
     * @param undiesColor the underwear color
     * @param lowerColor the outerwear color
     */
    public ALongHourAndAHalf(String name, Gender gndr, boolean diff, float inc, int bladder, String under, String outer, String undiesColor, String lowerColor)
    {
        //Saving parameters for the reset
        nameParam = name;
        gndrParam = gndr;
        incParam = inc;
        bladderParam = bladder;

        //Assigning constructor parameters to values
        this.name = name;
        gender = gndr;
        hardcore = diff;
        incon = inc;
        this.bladder = bladder;
        maxSphincterPower = 100 / incon;
        sphincterPower = maxSphincterPower;

        //Making bladder smaller in the hardcore mode, adding hardcore label
        if (hardcore)
        {
            maxBladder = 100;
            name += " [Hardcore]";
        }

        //Assigning the boy's name
        boyName = names[generator.nextInt(names.length)];

        //Setting up custom wear file chooser
        this.fc = new JFileChooser();
        fc.setFileFilter(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                String extension = "";
                int i = pathname.getName().lastIndexOf('.');
                if (i > 0)
                {
                    extension = pathname.getName().substring(i + 1);
                }
                return extension.equals("lhhwear");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Custom wear";
            }
        });

        if (under.equals("Custom"))
        {
            fc.setDialogTitle("Open an underwear file");
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    undies = (Wear) ois.readObject();
                    if (undies.getType() == OUTERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an underwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        setupFramePre.main(new String[0]);
                    }
                } catch (IOException | ClassNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    setupFramePre.main(new String[0]);
                }
            }
        }
        
        //If random undies were chosen...
        if (under.equals("Random"))
        {
            undies = underwearList[generator.nextInt(underwearList.length)];
            while (undies.getName().equals("Random"))
            //...selecting random undies from the undies array.
            {
                undies = underwearList[generator.nextInt(underwearList.length)];
            }
            //If random undies weren't chosen...
        } else
        {
            //We look for the selected undies in the array
            for (Wear iWear : underwearList)
            {
                //By comparing all possible undies' names with the selected undies string
                if (iWear.getName().equals(under))
                {
                    //If the selected undies were found, assigning current compared undies to the character's undies
                    undies = iWear;
                    break;
                }
            }
        }
        //If the selected undies weren't found
        if (undies == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect underwear selected. Setting random instead.", "Incorrect underwear", JOptionPane.WARNING_MESSAGE);
            undies = underwearList[generator.nextInt(underwearList.length)];
        }

        //Assigning color
        if (!undies.isMissing())
        {
            if (!undiesColor.equals("Random"))
            {
                undies.setColor(undiesColor);
            } else
            {
                undies.setColor(Wear.colorList[generator.nextInt(Wear.colorList.length)]);
            }
        } else
        {
            undies.setColor("");
        }

        if (outer.equals("Custom"))
        {
            fc.setDialogTitle("Open an outerwear file");
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    lower = (Wear) ois.readObject();
                    if (lower.getType() == UNDERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "This isn't an outerwear.", "Wrong wear type", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        setupFramePre.main(new String[0]);
                    }
                } catch (IOException | ClassNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    setupFramePre.main(new String[0]);
                }
            }
        }

        //Same with the lower clothes
        if (outer.equals("Random"))
        {
            lower = outerwearList[generator.nextInt(outerwearList.length)];
            while (lower.getName().equals("Random"))
            {
                lower = outerwearList[generator.nextInt(outerwearList.length)];
            }
        } else
        {
            for (Wear iWear : outerwearList)
            {
                if (iWear.getName().equals(outer))
                {
                    lower = iWear;
                    break;
                }
            }
        }
        if (lower == null)
        {
            JOptionPane.showMessageDialog(null, "Incorrect outerwear selected. Setting random instead.", "Incorrect outerwear", JOptionPane.WARNING_MESSAGE);
            lower = outerwearList[generator.nextInt(outerwearList.length)];
        }

        //Assigning color
        if (!lower.isMissing())
        {
            if (!lowerColor.equals("Random"))
            {
                lower.setColor(lowerColor);
            } else
            {
                lower.setColor(Wear.colorList[generator.nextInt(Wear.colorList.length)]);
            }
        } else
        {
            lower.setColor("");
        }

        //Calculating dryness and maximal bladder capacity values
        dryness = lower.getAbsorption() + undies.getAbsorption();
        maxBladder -= lower.getPressure() + undies.getPressure();

        //Setting "No undrwear" insert name depending on character's gender
        //May be gone soon
        if (isMale())
        {
            underwearList[1].setInsertName("penis");
        } else
        {
            underwearList[1].setInsertName("crotch");
        }

        //Finishing saving parameters for game reset
        outerParam = lower.getName();
        underParam = undies.getName();
        underColorParam = undies.getColor();
        outerColorParam = lower.getColor();

        //Game window setup
        setResizable(false);
        setTitle("A Long Hour and a Half");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 540);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Text panel setup
        textPanel = new JPanel();
        textPanel.setBounds(10, 11, 614, 150);
        contentPane.add(textPanel);
        textPanel.setLayout(null);
        textLabel = new JLabel("");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setBounds(0, 0, 614, 150);
        textPanel.add(textLabel);

        //"Next" button setup
        btnNext = new JButton("Next");
//        btnNext.setToolTipText("Hold down for the time warp");
        btnNext.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                handleNextClicked();
            }
        });

        btnNext.setBounds((textPanel.getWidth() / 2) + 25, 460, 280, 43);
        contentPane.add(btnNext);

        //"Quit" button setup
        btnQuit = new JButton("Quit");
        btnQuit.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                System.exit(0);
            }
        });
        btnQuit.setBounds((textPanel.getWidth() / 2) - 120, 480, 89, 23);
        contentPane.add(btnQuit);

        //"Reset" button setup
        btnReset = new JButton("Reset");
        btnReset.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                ALongHourAndAHalf.reset(false);
                dispose();
            }
        });
        btnReset.setBounds((textPanel.getWidth() / 2) - 300, 480, 89, 23);
        contentPane.add(btnReset);

        //"New game" button setup
        btnNewGame = new JButton("New game");
        btnNewGame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                ALongHourAndAHalf.reset(true);
                dispose();
            }
        });
        btnNewGame.setBounds((textPanel.getWidth() / 2) - 210, 480, 89, 23);
        contentPane.add(btnNewGame);

        //Name label setup
        lblName = new JLabel(name);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblName.setBounds(20, 170, 200, 32);
        contentPane.add(lblName);

        //Bladder label setup
        lblBladder = new JLabel("Bladder: " + Math.round(this.bladder) + "%");
        lblBladder.setToolTipText("<html>Normal game:<br>100% = need to hold<br>130% = peeing(game over)<br><br>Hardcore:<br>80% = need to hold<br>100% = peeing(game over)</html>");
        lblBladder.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBladder.setBounds(20, 210, 200, 32);
        contentPane.add(lblBladder);
        lblDefaultColor = lblBladder.getForeground();

        //Embarassment label setup
        lblEmbarassment = new JLabel("Embarassment: " + embarassment);
        lblEmbarassment.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmbarassment.setBounds(20, 240, 200, 32);
        contentPane.add(lblEmbarassment);

        //Belly label setup
        lblBelly = new JLabel("Belly: " + Math.round(belly) + "%");
        lblBelly.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBelly.setBounds(20, 270, 200, 32);
        contentPane.add(lblBelly);

        //Incontinence label setup
        lblIncon = new JLabel("Incontinence: " + inc + "x");
        lblIncon.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblIncon.setBounds(20, 300, 200, 32);
        contentPane.add(lblIncon);

        //Time label setup
        lblMinutes = new JLabel("Minutes: " + min + " of 90");
        lblMinutes.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMinutes.setBounds(20, 330, 200, 32);
        lblMinutes.setVisible(false);
        contentPane.add(lblMinutes);

        //Sphincter power label setup
        lblSphPower = new JLabel("Pee holding ability: " + Math.round(sphincterPower) + "%");
        lblSphPower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSphPower.setBounds(20, 360, 200, 32);
        lblSphPower.setVisible(false);
        contentPane.add(lblSphPower);

        //Clothing dryness label setup
        lblDryness = new JLabel("Clothes dryness: " + Math.round(dryness));
        lblDryness.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblDryness.setBounds(20, 390, 200, 32);
        lblDryness.setVisible(false);
        contentPane.add(lblDryness);

        //Undies label setup
        lblUndies = new JLabel("Undies: " + undies.getColor() + " " + undies.getName().toLowerCase());
        lblUndies.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblUndies.setBounds(20, 420, 400, 32);
        contentPane.add(lblUndies);

        //Lower label setup
        lblLower = new JLabel("Lower: " + lower.getColor() + " " + lower.getName().toLowerCase());
        lblLower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblLower.setBounds(20, 450, 400, 32);
        contentPane.add(lblLower);

        //Choice label ("What to do?") setup
        lblChoice = new JLabel();
        lblChoice.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblChoice.setBounds(320, 162, 280, 32);
        lblChoice.setVisible(false);
        contentPane.add(lblChoice);

        //Action list setup
        listChoice = new JList<>();
        listChoice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listChoice.setLayoutOrientation(JList.VERTICAL);

        listScroller = new JScrollPane(listChoice);
        listScroller.setBounds(320, 194, 300, 300);
        listScroller.setVisible(false);
        contentPane.add(listScroller);

        //Bladder bar setup
        bladderBar = new JProgressBar();
        bladderBar.setBounds(16, 212, 300, 25);
        bladderBar.setMaximum(130);
        bladderBar.setValue(bladder);
        contentPane.add(bladderBar);

        //Sphincter bar setup
        sphincterBar = new JProgressBar();
        sphincterBar.setBounds(16, 362, 300, 25);
        sphincterBar.setMaximum((int) Math.round(maxSphincterPower));
        sphincterBar.setValue((int) Math.round(sphincterPower));
        sphincterBar.setVisible(false);
        contentPane.add(sphincterBar);

        //Dryness bar setup
        drynessBar = new JProgressBar();
        drynessBar.setBounds(16, 392, 300, 25);
        drynessBar.setMaximum((int) dryness);
        drynessBar.setValue((int) dryness);
        drynessBar.setMinimum(MINIMAL_DRYNESS);
        drynessBar.setVisible(false);
        contentPane.add(drynessBar);

        //Time bar setup
        timeBar = new JProgressBar();
        timeBar.setBounds(16, 332, 300, 25);
        timeBar.setMaximum(90);
        timeBar.setValue(min);
        timeBar.setVisible(false);
        contentPane.add(timeBar);

        //Displaying the frame
        setVisible(true);

        //Starting the game
        nextStage = LEAVE_BED;
        handleNextClicked();
    }

    /**
     * @return TRUE - if character's gender is female<br>FALSE - if character's
     * gender is male
     */
    public boolean isFemale()
    {
        return gender == FEMALE;
    }

    /**
     * @return TRUE - if character's gender is male<br>FALSE - if character's
     * gender is female
     */
    public boolean isMale()
    {
        return gender == MALE;
    }

    private void handleNextClicked()
    {
        switch (nextStage)
        {
            case LEAVE_BED:
                //Coloring the name label according to the gender
                if (isFemale())
                {
                    lblName.setForeground(Color.MAGENTA);
                } else
                {
                    lblName.setForeground(Color.CYAN);
                }

                //Assigning the blank name if player didn't selected the name
                if (name.isEmpty())
                {
                    if (isFemale())
                    {
                        name = "Mrs. Nobody";
                    } else
                    {
                        name = "Mr. Nobody";
                    }
                }

                //Scoring bladder at start
                score("Bladder at start - " + bladder + "%", '+', Math.round(bladder));

                //Scoring incontinence
                score("Incontinence - " + Math.round(incon) + "x", '*', Math.round(incon));

                /*
                    Hardcore, it will be harder to hold pee:
                    Teacher will never let character to go pee
                    Character's bladder will have less capacity
                    Character may get caught holding pee
                 */
                if (hardcore)
                {
                    score("Hardcore", '*', 2F);
                }

                //Making line 1 italic
                setLinesAsDialogue(1);
                if (!lower.isMissing())
                {
                    if (!undies.isMissing())
                    //Both lower clothes and undies
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some " + undies.insert() + " and " + lower.insert() + ",",
                                "not even worrying about what covers your chest.");
                    } else
                    //Lower clothes only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on some " + lower.insert() + ", quick to cover your " + undies.insert() + ",",
                                "not even worrying about what covers your chest.");
                    }
                } else
                    if (!undies.isMissing())
                    //Undies only
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You hurriedly slip on " + undies.insert() + ",",
                                "not even worrying about what covers your chest and legs.");
                    } else
                    //No clothes at all
                    {
                        setText("Wh-what? Did I forget to set my alarm?!",
                                "You cry, tumbling out of bed and feeling an instant jolt from your bladder.",
                                "You are running downstairs fully naked.");
                    }
                //Adding 0.5 of liquid to bladder
                offsetBladder(.5);

                //Setting the next stage to "Leaving home"
                nextStage = LEAVE_HOME;
                break;

            case LEAVE_HOME:
                setText("Just looking at the clock again in disbelief adds a redder tint to your cheeks.",
                        "",
                        "Paying much less attention to your daily routine, you quickly run down the stairs, get a small glass of orange juice and chug it.",
                        "",
                        "The cold drink brings a chill down your spine as you collect your things and rush out the door to school.");

                //MANUAL BLADDER OFFSET
                offsetBladder(.5);
                offsetEmbarassment(3);
                offsetBelly(10);
                bladderBar.setValue(Math.round(bladder));

                nextStage = GO_TO_CLASS;
                break;

            case GO_TO_CLASS:
                //Displaying all values
                lblMinutes.setVisible(true);
                lblSphPower.setVisible(true);
                lblDryness.setVisible(true);
                sphincterBar.setVisible(true);
                drynessBar.setVisible(true);
                timeBar.setVisible(true);

                if (!lower.isMissing())
                {
                    //Skirt blowing in the wind
                    if (lower.insert().equals("skirt"))
                    {
                        setText("You rush into class, your " + lower.insert() + " blowing in the wind.",
                                "",
                                "Normally, you'd be worried your " + undies.insert() + " would be seen, but you can't worry about it right now.",
                                "You make it to your seat without a minute to spare.");
                    } else
                    {
                        //Nothing is blowing in wind
                        setText("Trying your best to make up lost time, you rush into class and sit down to your seat without a minute to spare.");
                    }
                } else
                    if (!undies.isMissing())
                    {
                        setText("You rush into class; your classmates are looking at your " + undies.insert() + ".",
                                "You can't understand how you forgot to even put on any lower clothing,",
                                "and you know that your " + undies.insert() + " have definitely been seen.",
                                "You make it to your seat without a minute to spare.");
                    } else
                        if (isFemale())
                        {
                            setText("You rush into class; your classmates are looking at your pussy and boobs.",
                                    "Guys are going mad and doing nothing except looking at you.",
                                    "You can't understand how you dared to come to school naked.",
                                    "You make it to your seat without a minute to spare.");
                        } else
                        {
                            setText("You rush into class; your classmates are looking at your penis.",
                                    "Girls are really going mad and doing nothing except looking at you.",
                                    "You can't understand how you dared to come to school naked.",
                                    "You make it to your seat without a minute to spare.");
                        }

                offsetEmbarassment(2);
                nextStage = WALK_IN;
                break;

            case WALK_IN:
                //If lower clothes is a skirt
                if (lower.insert().equals("skirt") || lower.insert().equals("skirt and tights") || lower.insert().equals("skirt and tights"))
                {
                    setLinesAsDialogue(1, 3);
                    setText("Next time you run into class, " + name + ",",
                            "your teacher says,",
                            "make sure you're wearing something less... revealing!",
                            "A chuckle passes over the classroom, and you can't help but feel a",
                            "tad bit embarrassed about your rush into class.");
                    offsetEmbarassment(5);
                } else //No outerwear
                    if (lower.isMissing())
                    {
                        setLinesAsDialogue(1);
                        setText("WHAT!? YOU CAME TO SCHOOL NAKED!?",
                                "your teacher shouts in disbelief.",
                                "",
                                "A chuckle passes over the classroom, and you can't help but feel extremely embarrassed",
                                "about your rush into class, let alone your nudity");
                        offsetEmbarassment(25);
                    } else
                    {
                        setLinesAsDialogue(1, 3);
                        setText("Sit down, " + name + ". You're running late.",
                                "your teacher says,",
                                "And next time, don't make so much noise entering the classroom!",
                                "A chuckle passes over the classroom, and you can't help but feel a tad bit embarrassed",
                                "about your rush into class.");
                    }
                nextStage = SIT_DOWN;
                break;

            case SIT_DOWN:
                setText("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of",
                        "your bladder filling as the liquids you drank earlier start to make their way down.");
                passTime();
                nextStage = ASK_ACTION;
                score("Embarassment at start - " + incon + " pts", '+', embarassment);
                break;

            case ASK_ACTION:
//                do
//                {
                //Called by teacher if unlucky
                if (generator.nextInt(20) == 5)
                {
                    setText("Suddenly, you hear the teacher call your name.");
                    nextStage = CALLED_ON;
                    break;
                }

                //Bladder: 0-20
                if (bladder <= 20)
                {
                    setText("Feeling bored about the day, and not really caring about the class too much,",
                            "you look to the clock, watching the minutes tick by.");
                }
                //Bladder: 20-40
                if (bladder > 20 && bladder <= 40)
                {
                    setText("Having to pee a little bit,",
                            "you look to the clock, watching the minutes tick by and wishing the lesson to get over faster.");
                }
                //Bladder: 40-60
                if (bladder > 40 && bladder <= 60)
                {
                    setText("Clearly having to pee,",
                            "you impatiently wait for the lesson end.");
                }
                //Bladder: 60-80
                if (bladder > 60 && bladder <= 80)
                {
                    setLinesAsDialogue(2);
                    setText("You feel the rather strong pressure in your bladder, and you're starting to get even more desperate.",
                            "Maybe I should ask teacher to go to the restroom? It hurts a bit...");
                }
                //Bladder: 80-100
                if (bladder > 80 && bladder <= 100)
                {
                    setLinesAsDialogue(1, 3);
                    setText("Keeping all that urine inside will become impossible very soon.",
                            "You feel the terrible pain and pressure in your bladder, and you can almost definitely say you haven't needed to pee this badly in your life.",
                            "Ouch, it hurts a lot... I must do something about it now, or else...");
                }
                //Bladder: 100-130
                if (bladder > 100 && bladder <= 130)
                {
                    setLinesAsDialogue(1, 3);
                    if (isFemale())
                    {
                        setText("This is really bad...",
                                "You know that you can't keep it any longer and you may wet yourself in any moment and oh,",
                                "You can clearly see your bladder as it bulging.",
                                "Ahhh... I cant hold it anymore!!!",
                                "Even holding your crotch doesn't seems to help you to keep it in.");
                    } else
                    {
                        setText("This is really bad...",
                                "You know that you can't keep it any longer and you may wet yourself in any moment and oh,",
                                "You can clearly see your bladder as it bulging.",
                                "Ahhh... I cant hold it anymore!!!",
                                "Even squeezing your penis doesn't seems to help you to keep it in.");
                    }
                }
                lblChoice.setText("What now?");
                listScroller.setVisible(true);
                lblChoice.setVisible(true);

                actionList.clear();

                //Adding action choices
                switch (timesPeeDenied)
                {
                    case 0:
                        actionList.add("Ask the teacher to go pee");
                        break;
                    case 1:
                        actionList.add("Ask the teacher to go pee again");
                        break;
                    case 2:
                        actionList.add("Try to ask the teacher again");
                        break;
                    case 3:
                        actionList.add("Take a chance and ask the teacher (RISKY)");
                        break;
                    default:
                        actionList.add("[Unavailable]");
                }

                if (!cornered)
                {
                    if (isFemale())
                    {
                        actionList.add("Press on your crotch");
                    } else
                    {
                        actionList.add("Squeeze your penis");
                    }
                } else
                {
                    actionList.add("[Unavailable]");
                }

                actionList.add("Rub thighs");

                if (bladder >= 100)
                {
                    actionList.add("Give up and pee yourself");
                } else
                {
                    actionList.add("[Unavailable]");
                }

                actionList.add("Just wait");
                actionList.add("Cheat (will reset your score)");

                //Loading the choice array into the action selector
                listChoice.setListData(actionList.toArray());
                nextStage = CHOSE_ACTION;
                passTime();
                //Don't go further if player selected no or unavailable action
//                }while(listChoice.isSelectionEmpty()||listChoice.getSelectedValue().equals("[Unavailable]"));
                break;

            case CHOSE_ACTION:
                System.out.println();
                nextStage = ASK_ACTION;
                if (listChoice.isSelectionEmpty() || listChoice.getSelectedValue().equals("[Unavailable]"))
                {
//                    setText("You spent a few minutes doing nothing.");
                    break;
                }

                //Hiding the action selector
                lblChoice.setVisible(false);
                listScroller.setVisible(false);
                btnNext.setText("Next");

                //Doing action job
                switch (listChoice.getSelectedIndex())
                {

                    //Ask the teacher to go pee
                    case 0:
                        nextStage = ASK_TO_PEE;
                        setLinesAsDialogue(2, 3);
                        setText("You think to yourself:",
                                "I don't think I can hold it until class ends!",
                                "I don't have a choice, I have to ask the teacher...");
                        break;

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
                    case 1:
                        setText("You don't think anyone will see you doing it,",
                                "so you take your hand and hold yourself down there.",
                                "It feels a little better for now.");

                        rechargeSphPower(20);
                        offsetTime(3);

                        //Chance to be caught by classmates in hardcore mode
                        if (generator.nextInt(100) <= 15 + classmatesAwareness & hardcore)
                        {
                            nextStage = CAUGHT;
                        } else
                        {
                            nextStage = ASK_ACTION;
                        }
                        break;

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
                    case 2:
                        setText("You need to go, and it hurts, but you just",
                                "can't bring yourself to risk getting caught with your hand between",
                                "your legs. You rub your thighs hard but it doesn't really help.");

                        rechargeSphPower(2);
                        offsetTime(3);

                        //Chance to be caught by classmates in hardcore mode
                        if (generator.nextInt(100) <= 3 + classmatesAwareness & hardcore)
                        {
                            nextStage = CAUGHT;
                        } else
                        {
                            nextStage = ASK_ACTION;
                        }
                        break;

                    //Give up
                    case 3:
                        setText("You're absolutely desperate to pee, and you think you'll",
                                "end up peeing yourself anyway, so it's probably best to admit",
                                "defeat and get rid of the painful ache in your bladder.");
                        nextStage = GIVE_UP;
                        break;

                    /*
                     * Wait
                     * =========================
                     * 3 + 2 + n minutes
                     * +(2.5n) bladder
                     * Detection chance: 1
                     * Future effectiveness: 2.4(1), 0.4(2), 0.47(30)
                     */
                    case 4:

                        //Asking player how much to wait
                        int time;
                        try
                        {
                            time = Integer.parseInt(JOptionPane.showInputDialog("How much to wait?"));
                            if (time < 1 || time > 125)
                            {
                                throw new NumberFormatException();
                            }
                        } //Ignoring invalid output
                        catch (NumberFormatException | NullPointerException e)
                        {
                            nextStage = ASK_ACTION;
                            break;
                        }

                        passTime((byte) time);

                        //Chance to be caught by classmates in hardcore mode
                        if (generator.nextInt(100) <= 1 + classmatesAwareness & hardcore)
                        {
                            nextStage = CAUGHT;

                        } else
                        {
                            nextStage = ASK_ACTION;
                        }
                        break;

                    //Cheat
                    case 5:
                        setText("You've got to go so bad!",
                                "There must be something you can do, right?");

                        //Zeroing points
                        cheatsUsed = true;
                        nextStage = ASK_CHEAT;
                        break;
                    
                    case -1:
                        setText("Bugs.");
                }
                break;

            case ASK_TO_PEE:
                switch (timesPeeDenied)
                {
                    case 0:
                        //Success
                        if (generator.nextInt(100) <= 40 & !hardcore)
                        {
                            setText("Yes, you may.",
                                    "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
//                            score *= 0.2;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -80% of points");
                            score("Restroom usage during the lesson", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                            //Fail
                        } else
                        {
                            setText("You ask the teacher if you can go out to the restroom.",
                                    "No, you can't go out, the director prohibited it.",
                                    "says the teacher.");
                            timesPeeDenied++;
                        }
                        break;

                    case 1:
                        if (generator.nextInt(100) <= 10 & !hardcore)
                        {
                            setText("You ask the teacher again if you can go out to the restroom.",
                                    "Yes, you may.,",
                                    "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
//                            score *= 0.22;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -78% of points");
                            score("Restroom usage during the lesson", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        } else
                        {
                            setText("You ask the teacher again if you can go out to the restroom.",
                                    "No, you can't! I already told you that the director prohibited it!",
                                    "says the teacher.");
                            timesPeeDenied++;
                        }
                        break;

                    case 2:
                        if (generator.nextInt(100) <= 30 & !hardcore)
                        {
                            setText("You ask the teacher if you can go out to the restroom.",
                                    "OK, you may. You are so annoying, " + name + "!",
                                    "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
//                            score *= 0.23;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -77% of points");
                            score("Restroom usage during the lesson", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        } else
                        {
                            setText("You ask the teacher once more if you can go out to the restroom.",
                                    "No, you can't! Stop asking me or there will be consequences!",
                                    "says the teacher.");
                            timesPeeDenied++;
                        }
                        break;

                    case 3:
                        if (generator.nextInt(100) <= 7 & !hardcore)
                        {
                            setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                    "OK, you may. You are so annoying, " + name + "!",
                                    "says the teacher. You run to the restroom. Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
//                            score *= 0.3;
//                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -70% of points");
                            score("Restroom usage during the lesson", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        } else
                            if (generator.nextBoolean())
                            {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,",
                                        "yells the teacher.");
                                cornered = true;
//                            score += 1.3 * (90 - min / 3);
//                            scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +" + 1.3 * (90 - min / 3) + " score");
                                score("Stayed on corner " + (90 - min) + " minutes", '+', 1.3F * (90 - min / 3));
                                offsetEmbarassment(5);
                            } else
                            {
                                setText("Desperately, you ask the teacher if you can go out to the restroom.",
                                        "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! YOU WILL WRITE LINES AFTER THE LESSON!!!,",
                                        "yells the teacher.");
                                offsetEmbarassment(5);
                                stay = true;
                                timeBar.setMaximum(120);
//                            scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
//                            score *= 1.6;
                                score("Wrote lines after the lesson", '*', 1.6F);
                            }
                        timesPeeDenied++;

                        break;
                }
                nextStage = ASK_ACTION;
                break;

            case ASK_CHEAT:
                do
                {
                    lblChoice.setText("Select a cheat:");
                    listChoice.setListData(cheatList);
                    lblChoice.setVisible(true);
                    listScroller.setVisible(true);
                    nextStage = CHOSE_CHEAT;
                } while (listChoice.isSelectionEmpty());
                break;

            case CHOSE_CHEAT:
                lblChoice.setText("");
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                switch (listChoice.getSelectedIndex())
                {
                    case 0:
                        setText("You walk to the front corner of the classroom.");
                        cornered = true;
                        nextStage = ASK_ACTION;
                        break;

                    case 1:
                        setText("You decide to stay after class.");
                        stay = true;
                        timeBar.setMaximum(120);
                        nextStage = ASK_ACTION;
                        break;

                    case 2:
                        setText("You see something out of the corner of your eye,",
                                "just within your reach.");
                        nextStage = USE_BOTTLE;
                        break;

                    case 3:
                        setLinesAsDialogue(2);
                        setText("A voice comes over the loudspeaker:",
                                "All classes are now dismissed for no reason at all! Bye!",
                                "Looks like your luck changed for the better.");
                        min = 89;
                        nextStage = CLASS_OVER;
                        break;

                    case 4:
                        setText("The teacher feels sorry for you. Try asking to pee.");
                        timesPeeDenied = 0;
                        stay = false;
                        timeBar.setMaximum(90);
                        cornered = false;
                        nextStage = ASK_ACTION;
                        break;

                    case 5:
                        setText("You decide to raise your hand.");
                        nextStage = CALLED_ON;
                        break;

                    case 6:
                        setText("Suddenly, you feel like you're peeing...",
                                "but you don't feel any wetness. It's not something you'd",
                                "want to question, right?");
                        drain = true;
                        nextStage = ASK_ACTION;
                        break;

                    case 7:
                        setText("A friend in the desk next to you hands you a familiar",
                                "looking pill, and you take it.");
                        incon = Float.parseFloat(JOptionPane.showInputDialog("How incontinent are you now?"));
                        lblIncon.setText("Incontinence: " + incon + "x");
                        nextStage = ASK_ACTION;
                        break;

                    case 8:
                        setText("The teacher suddenly looks like they've had enough",
                                "of people having to pee.");
                        hardcore = !hardcore;
                        nextStage = ASK_ACTION;
                        break;

                    case 9:
                        setText("Suddenly you felt something going on in your bladder.");
                        incon = Float.parseFloat(JOptionPane.showInputDialog("How your bladder is full now?"));
                        lblIncon.setText("Bladder: " + bladder + "%");
                        nextStage = ASK_ACTION;
                        break;
                }
                break;

            case USE_BOTTLE:
                emptyBladder();
                setLinesAsDialogue(3);
                setText("Luckily for you, you happen to have brought an empty bottle to pee in.",
                        "As quietly as you can, you put it in position and let go into it.",
                        "Ahhhhh...",
                        "You can't help but show a face of pure relief as your pee trickles down into it.");
                nextStage = ASK_ACTION;
                break;

            case CALLED_ON:
                lblChoice.setVisible(false);
                listScroller.setVisible(false);
                btnNext.setText("Next");

                setLinesAsDialogue(1);
                setText("" + name + ", why don't you come up to the board and solve this problem?,",
                        "says the teacher. Of course, you don't have a clue how to solve it.",
                        "You make your way to the front of the room and act lost, knowing you'll be stuck",
                        "up there for a while as the teacher explains it.",
                        "Well, you can't dare to hold yourself now...");

                score("Called on the lesson", '+', 5);
                nextStage = ASK_ACTION;
                passTime((byte) 5);
                break;

            case CLASS_OVER:
                //Special hardcore scene trigger
                if (generator.nextInt(100) <= 10 && hardcore & isFemale())
                {
                    nextStage = SURPRISE;
                    break;
                }
                if (stay)
                {
                    nextStage = AFTER_CLASS;
                    break;
                }

                if (generator.nextBoolean())
                {
                    setText("Lesson is finally over, and you're running to the restroom as fast as you can.",
                            "No, please... All cabins are occupied, and there's a line. You have to wait!");

                    score("Waited for a free cabin in the restroom", '+', 3);
                    passTime();
                    break;
                } else
                {
                    if (!lower.isMissing())
                    {
                        if (!undies.isMissing())
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        }
                    } else
                        if (!undies.isMissing())
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it,",
                                    "wearily flop down on the toilet and start peeing.");
                        }
                    nextStage = END_GAME;
                }
                break;

            case AFTER_CLASS:
                if (min >= 120)
                {
                    stay = false;
                    nextStage = CLASS_OVER;
                    break;
                }

                setLinesAsDialogue(1, 2, 3, 4);
                setText("Hey, " + name + ", you wanted to escape? You must stay after classes!",
                        "Please... let me go to the restroom... I can't hold it...",
                        "No, " + name + ", you can't go to the restroom now! This will be as punishment.",
                        "And don't think you can hold yourself either! I'm watching you...");

                passTime();
                break;

            case ACCIDENT:
                listScroller.setVisible(false);
                lblChoice.setVisible(false);
                setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.",
                        "Despite all this, you try your best, but suddenly you're forced to stop.",
                        "You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere.",
                        "But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.");
                nextStage = WET;
                break;

            case GIVE_UP:
                offsetEmbarassment(80);
                if (!lower.isMissing())
                {
                    if (!undies.isMissing())
                    {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + undies.insert() + ".");
                    } else
                    {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decided to pee in your " + lower.insert() + ".");
                    }
                } else
                    if (!undies.isMissing())
                    {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + undies.insert() + ".");
                    } else
                    {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee where you are.");
                    }
                nextStage = WET;
                break;

            case WET:
                emptyBladder();
                embarassment = 100;
                if (!lower.isMissing())
                {
                    if (!undies.isMissing())
                    {
                        setText("Before you can move an inch, pee quickly soaks through your " + undies.insert() + ",",
                                "floods your " + lower.insert() + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                    } else
                    {
                        setText("Before you can move an inch, pee quickly darkens your " + lower.insert() + " and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                    }
                } else
                    if (!undies.isMissing())
                    {
                        setText("Before you can move an inch, pee quickly soaks through your " + undies.insert() + ", and streaks down your legs.",
                                "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                    } else
                        if (!cornered)
                        {
                            setText("The heavy pee jets are hitting the seat and loudly leaking out from your " + undies.insert() + ".",
                                    "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                        } else
                        {
                            setText("The heavy pee jets are hitting the floor and loudly leaking out from your " + undies.insert() + ".",
                                    "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
                        }
                nextStage = POST_WET;
                break;

            case POST_WET:
                setLinesAsDialogue(2);
                if (!stay)
                {
                    if (lower.isMissing())
                    {
                        if (isFemale() && undies.isMissing())
                        {
                            setText("People around you are laughing loudly.",
                                    name + " peed herself! Ahaha!!!");
                        } else
                            if (isMale() && undies.isMissing())
                            {
                                setText("People around you are laughing loudly.",
                                        name + " peed himself! Ahaha!!!");
                            } else
                            {
                                setText("People around you are laughing loudly.",
                                        name + " wet h" + (isFemale() ? "er " : "is ") + undies.insert() + "! Ahaha!!");
                            }
                    } else
                        if (isFemale())
                        {
                            setText("People around you are laughing loudly.",
                                    name + " peed her " + lower.insert() + "! Ahaha!!");
                        } else
                        {
                            setText("People around you are laughing loudly.",
                                    " peed his " + lower.insert() + "! Ahaha!!");
                        }
                } else
                {
                    setText("Teacher is laughing loudly.",
                            "Oh, you peed yourself? This is a great punishment.",
                            "I hope you will no longer get in the way of the lesson.");
                }
                nextStage = GAME_OVER;
                break;

            case GAME_OVER:
                if (lower.isMissing())
                {
                    if (undies.isMissing())
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
                    } else
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + undies.insert() + " are clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
                    }
                } else
                    if (undies.isMissing())
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + lower.insert() + " is clinging to your skin, a sign of your failure...",//TODO: Add "is/are" depending on lower clothes type
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
                    } else
                    {
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + lower.insert() + " and " + undies.insert() + " are both clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
                    }
                btnNext.setVisible(false);
                break;

            case END_GAME:
                if (cheatsUsed)
                {
                    score = 0;
                    scoreText = "\nYou've used the cheats, so you've got no score.";
                }
                String scoreText2 = "Your score: " + score + "\n" + scoreText;

                JOptionPane.showMessageDialog(this, scoreText2);
                btnNext.setVisible(false);
                break;

            case CAUGHT:
                switch (timesCaught)
                {
                    case 0:
                        setText("It looks like a classmate has spotted that you've got to go badly.",
                                "Damn, he may spread that fact...");
                        offsetEmbarassment(3);
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 3);
                        timesCaught++;
                        break;

                    case 1:
                        setLinesAsDialogue(3);
                        setText("You'he heard a suspicious whisper behind you.",
                                "Listening to the whisper, you've found out that they're saying that you need to go.",
                                "If I hold it until the lesson ends, I will beat them.");
                        offsetEmbarassment(8);
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 8);
                        timesCaught++;
                        break;

                    case 2:
                        if (isFemale())
                        {
                            setLinesAsDialogue(2);
                            setText("The most handsome boy in your class, " + boyName + ", is calling you:",
                                    "Hey there, don't wet yourself!",
                                    "Oh no, he knows it...");
                        } else
                        {
                            setLinesAsDialogue(2, 3);
                            setText("The most nasty boy in your class, " + boyName + ", is calling you:",
                                    "Hey there, don't wet yourself! Ahahahaa!",
                                    "\"Shut up...\"",
                                    ", you think to yourself.");
                        }
                        offsetEmbarassment(12);
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 12);
                        timesCaught++;
                        break;

                    default:
                        setText("The chuckles are continiously passing over the classroom.",
                                "Everyone is watching you.",
                                "Oh god... this is so embarassing...");
                        offsetEmbarassment(20);
                        classmatesAwareness += 5;
                        score("Caught holding pee", '+', 20);
                }
                nextStage = ASK_ACTION;
                break;

            //The special hardcore scene
            /*
             * "Surprise" is an additional scene after the lesson where player is being caught by her classmate. He wants her to wet herself.
             * Triggering conditions: female, hardcore
             * Triggering chance: 10%
             */
            case SURPRISE:

                //Resetting timesPeeDenied to use for that boy
                timesPeeDenied = 0;
                score("Got the \"surprise\" by " + boyName, '+', 70);
                setText("The lesson is finally over, and you're running to the restroom as fast as you can.",
                        "But... You see " + boyName + " staying in front of the restroom.",
                        "Suddenly, he takes you, not letting you to escape.");
                offsetEmbarassment(10);
                if (testWet())
                {
                    nextStage = SURPRISE_ACCIDENT;
                    break;
                }
                nextStage = SURPRISE_2;
                break;

            case SURPRISE_2:
                setLinesAsDialogue(1);
                setText("What do you want from me?!",
                        "He has brought you in the restroom and quickly put you on the windowsill.",
                        boyName + " has locked the restroom door (seems he has stolen the key), then he puts his palm on your belly and says:",
                        "I want you to wet yourself.");
                offsetEmbarassment(10);
                if (testWet())
                {
                    nextStage = SURPRISE_ACCIDENT;
                    break;
                }
                nextStage = SURPRISE_DIALOGUE;
                break;

            case SURPRISE_DIALOGUE:
                setLinesAsDialogue(1);
                setText("No, please, don't do it, no...",
                        "I want to see you wet...",
                        "He slightly presses your belly again, you shook from the terrible pain",
                        "in your bladder and subconsciously rubbed your crotch. You have to do something!");
                offsetEmbarassment(10);
                if (testWet())
                {
                    nextStage = SURPRISE_ACCIDENT;
                    break;
                }
                lblChoice.setText("Don't let him to do it!");
                actionList.clear();

                actionList.add("Hit him");
                switch (timesPeeDenied)
                {
                    case 0:
                        actionList.add("Try to persuade him to let you pee");
                        break;
                    case 1:
                        actionList.add("Try to persuade him to let you pee again");
                        break;
                    case 2:
                        actionList.add("Take a chance and try to persuade him (RISKY)");
                        break;
                }
                actionList.add("Pee yourself");

                listChoice.setListData(actionList.toArray());
                lblChoice.setVisible(true);
                listScroller.setVisible(true);
                nextStage = SURPRISE_CHOSE;
                break;

            case SURPRISE_CHOSE:
                nextStage = SURPRISE_DIALOGUE;
                if (listChoice.isSelectionEmpty())
                {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName + " demands.",
                            "Then " + boyName + " presses your bladder...");
                    nextStage = SURPRISE_WET_PRESSURE;
                }
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

//                actionNum = listChoice.getSelectedIndex();
                if (listChoice.getSelectedValue().equals("[Unavailable]"))
                {
                    //No idling
                    setText("You will wet yourself right now,",
                            boyName + " demands.",
                            "Then " + boyName + " presses your bladder...");
                    nextStage = SURPRISE_WET_PRESSURE;
                }

                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                listChoice.clearSelection();

                switch (listChoice.getSelectedIndex())
                {
                    case 0:
                        nextStage = HIT;
                        break;
                    case 1:
                        nextStage = PERSUADE;
                        break;
                    case 2:
                        nextStage = SURPRISE_WET_VOLUNTARY;
                }
                break;

            case HIT:
                if (generator.nextInt(100) <= 20)
                {
                    setLinesAsDialogue(2);
                    nextStage = GameStage.END_GAME;
                    score("Successful hit on " + boyName + "'s groin", '+', 40F);
                    if (!lower.isMissing())
                    {
                        if (!undies.isMissing())
                        {
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it, pulled down your " + lower.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        }
                    } else
                        if (!undies.isMissing())
                        {
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it, pulled down your " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        } else
                        {
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it,",
                                    "wearily flop down on the toilet and start peeing.");
                        }
                } else
                {
                    nextStage = GameStage.SURPRISE_WET_PRESSURE;
                    setLinesAsDialogue(2, 3);
                    setText("You hit " + boyName + "'s hand. Damn, you'd meant to hit his groin...",
                            "You're braver than I expected;",
                            "now let's check the strength of your bladder!",
                            boyName + " pressed your bladder violently...");
                }
                break;

            case PERSUADE:
                switch (timesPeeDenied)
                {
                    case 0:
                        if (generator.nextInt(100) <= 10)
                        {
                            setLinesAsDialogue(1);
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + lower.insert() + " and " + undies.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + lower.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            } else
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + undies.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            score("Persuaded " + boyName + " to pee", '+', 40);
                            emptyBladder();
                            nextStage = END_GAME;
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee.",
                                    "No, you can't pee in a cabin. I want you to wet yourself.,",
                                    boyName + " says.");
                            timesPeeDenied++;
                            nextStage = SURPRISE_DIALOGUE;
                        }
                        break;

                    case 1:
                        if (generator.nextInt(100) <= 5)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + lower.insert() + " and " + undies.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + lower.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            } else
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + undies.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            score("Persuaded " + boyName + " to pee", '+', 60);
                            emptyBladder();
                            nextStage = END_GAME;
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee again.",
                                    "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.",
                                    boyName + " demands.");
                            timesPeeDenied++;
                            nextStage = SURPRISE_DIALOGUE;
                        }
                        break;

                    case 2:
                        if (generator.nextInt(100) <= 2)
                        {
                            if (!lower.isMissing())
                            {
                                if (!undies.isMissing())
                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + lower.insert() + " and " + undies.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else

                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + lower.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }
                            } else
                                if (!undies.isMissing())

                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "pull down your " + undies.insert() + ",",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                } else

                                {
                                    setText("Ok, you may, but you'll let me watch you pee.",
                                            "states " + boyName + ". You enter the cabin,",
                                            "stand over the toilet and start peeing under " + boyName + "'s spectation.");
                                }

                            score("Persuaded " + boyName + " to pee", '+', 80);
                            emptyBladder();
                            nextStage = END_GAME;
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee again desperately.",
                                    "No, you can't pee in a cabin. You will wet yourself right now,",
                                    boyName + " demands.",
                                    "Then " + boyName + " pressed your bladder...");
                            nextStage = SURPRISE_WET_PRESSURE;
                        }
                        break;
                }
                break;

            case SURPRISE_WET_VOLUNTARY:
                setLinesAsDialogue(1, 3);
                setText("Alright, as you say.,",
                        "you say to " + boyName + " with a defeated sigh.",
                        "Whatever, I really can't hold it anymore anyways...");
                emptyBladder();
                nextStage = SURPRISE_WET_VOLUNTARY2;
                break;

            case SURPRISE_WET_VOLUNTARY2:
                if (!undies.isMissing())
                {
                    if (!lower.isMissing())
                    {
                        setText("You feel the warm pee stream",
                                "filling your " + undies.insert() + " and darkening your " + lower.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("You feel the warm pee stream",
                                "filling your " + undies.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    }
                } else
                    if (!lower.isMissing())
                    {
                        setText("You feel the warm pee stream",
                                "filling your " + lower.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("You feel the warm pee stream",
                                "running down your legs.",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    }
                emptyBladder();
                nextStage = END_GAME;
                break;

            case SURPRISE_WET_PRESSURE:
                if (!undies.isMissing())
                {
                    if (!lower.isMissing())
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + undies.insert() + " and darkening your " + lower.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + undies.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    }
                } else
                    if (!lower.isMissing())
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "filling your " + lower.insert() + ".",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    } else
                    {
                        setText("Ouch... The sudden pain flash passes through your bladder...",
                                "You try to hold the pee back, but you just can't.",
                                "You feel the warm pee stream",
                                "running down your legs.",
                                "You close your eyes and ease your sphincter off.",
                                "You feel the pee stream become much stronger.");
                    }
                emptyBladder();
                nextStage = END_GAME;
                break;

            default:
                setText("Error parsing button. Next text is unavailable, text #" + nextStage);
                break;
            //case template
            //      case 4:
            //   setText("");
            //   nextStage = ;
            //   break;
        }
    }

    /**
     * Increments the time by 3 minutes and all time-related parameters.
     */
    public void passTime()
    {
        passTime((byte) 3);
    }

    /**
     * Increments the time by # minutes and all time-related parameters.
     *
     * @param time #
     */
    public void passTime(byte time)
    {
        offsetTime(time);
        offsetBladder(time * 1.5);
        offsetBelly(-time * 1.5);

        if (min >= 88)
        {
            setText("You hear the bell finally ring.");
            nextStage = CLASS_OVER;
        }

        if (testWet())
        {
            nextStage = ACCIDENT;
        }

        //Decrementing sphincter power for every 3 minutes
        for (int i = 0; i < time; i++)
        {
            decaySphPower();
            if (belly != 0)
            {
                if (belly > 3)
                {
                    offsetBladder(2);
                } else
                {
                    offsetBladder(belly);
                    emptyBelly();
                }
            }
        }

        //Updating labels
        lblSphPower.setText("Pee holding ability: " + Math.round(sphincterPower) + "%");
        sphincterBar.setValue((int) Math.round(sphincterPower));
        lblDryness.setText("Clothes dryness: " + Math.round(dryness));
//        drynessBar.setValue((int)Math.round(sphincterPower));
    }

    /**
     * Checks the wetting conditions.
     *
     * @return true, if it's time to wet; false otherwise
     */
    public boolean testWet()
    {
        //If bladder is filled more than 130 points in the normal mode and 100 points in the hardcore mode, forcing wetting
        if (bladder >= maxBladder & !hardcore)
        {
            return true;
        } else //If bladder is filled more than 100 points in the normal mode and 50 points in the hardcore mode, character has a chance to wet
            if ((bladder > maxBladder - 30 & !hardcore) | (bladder > maxBladder - 20 & hardcore))
            {
                if (!hardcore)
                {
                    byte wetChance = (byte) (3 * (bladder - 100));
                    if (generator.nextInt(100) < wetChance)
                    {
                        return true;
                    }
                } else
                {
                    byte wetChance = (byte) (5 * (bladder - 80));
                    if (generator.nextInt(100) < wetChance)
                    {
                        return true;
                    }
                }
            }
        return false;
    }

    /**
     * Empties the bladder.
     */
    public void emptyBladder()
    {
        bladder = 0;
        lblBladder.setText("Bladder: " + (int) bladder + "%");
        bladderBar.setValue(0);
    }

    /**
     * Offsets bladder fulness by a specified amount.
     *
     * @param amount the amount to offset bladder fulness
     */
    public void offsetBladder(double amount)
    {
        bladder += amount/* * incon*/;//Incontinence does another job after 1.1
        lblBladder.setText("Bladder: " + Math.round(bladder) + "%");
        bladderBar.setValue(Math.round(bladder));
        if ((bladder > 100 && !hardcore) || (bladder > 80 && hardcore))
        {
            lblBladder.setForeground(Color.RED);
        } else
        {
            lblBladder.setForeground(lblDefaultColor);
        }

    }

    /**
     * Empties the belly.
     */
    public void emptyBelly()
    {
        offsetBelly(-belly);
    }

    public void offsetBelly(double amount)
    {
        belly += amount;
        if (belly < 0)
        {
            belly = 0;
        }
        lblBelly.setText("Belly: " + Math.round(belly) + "%");
    }

    public void offsetEmbarassment(int amount)
    {
        embarassment += amount;
        if (embarassment < 0)
        {
            embarassment = 0;
        }
        lblEmbarassment.setVisible(true);
        lblEmbarassment.setText("Embarassment: " + embarassment + " pts");
    }

    public void offsetTime(int amount)
    {
        min += amount;
        lblMinutes.setText("Minutes: " + min + " of " + (stay ? "120" : "90"));
        timeBar.setValue(min);
        if (drain & (min % 15) == 0)
        {
            emptyBladder();
        }
    }

    /**
     * Decreases the sphincter power.
     */
    public void decaySphPower()
    {
        //Clothes drying over time
        if (dryness < lower.getAbsorption() + undies.getAbsorption())
        {
            dryness += lower.getDryingOverTime() + undies.getDryingOverTime();
        }

        if (dryness > lower.getAbsorption() + undies.getAbsorption())
        {
            dryness = lower.getAbsorption() + undies.getAbsorption();
        }

        sphincterPower -= bladder / 30;//TODO: Balance this
        if (sphincterPower < 0)
        {
            dryness -= 5; //Decreasing dryness
            bladder -= 2.5; //Decreasing bladder level
            sphincterPower = 0;
            if (dryness > MINIMAL_DRYNESS)
            {
                //Naked
                if (lower.isMissing() && undies.isMissing())
                {
                    setText("You feel the leak running down your thighs...",
                            "You're about to pee! You must stop it!");
                } else //Outerwear
                    if (!lower.isMissing())
                    {
                        setText("You see the wet spot expand on your " + lower.insert() + "!",
                                "You're about to pee! You must stop it!");
                    } else //Underwear
                        if (!undies.isMissing())
                        {
                            setText("You see the wet spot expand on your " + undies.insert() + "!",
                                    "You're about to pee! You must stop it!");
                        }
            }

            if (dryness < MINIMAL_DRYNESS)
            {
                if (lower.isMissing() && undies.isMissing())
                {
                    if (cornered)
                    {
                        setText("You see a puddle forming on the floor beneath you, you're peeing!",
                                "It's too much...");
                        nextStage = ACCIDENT;
                        handleNextClicked();
                    } else
                    {
                        setText("Feeling the pee hit the chair and soon fall over the sides,",
                                "you see a puddle forming under your chair, you're peeing!",
                                "It's too much...");
                        nextStage = ACCIDENT;
                        handleNextClicked();
                    }
                } else
                    if (!lower.isMissing())
                    {
                        setText("You see the wet spot expanding on your " + lower.insert() + "!",
                                "It's too much...");
                        nextStage = ACCIDENT;
                        handleNextClicked();
                    } else
                        if (!undies.isMissing())
                        {
                            setText("You see the wet spot expanding on your " + undies.insert() + "!",
                                    "It's too much...");
                            nextStage = ACCIDENT;
                            handleNextClicked();
                        }
            }
        }
        lblBladder.setText("Bladder: " + (int) bladder + "%");
        bladderBar.setValue(Math.round(bladder));
        lblDryness.setText("Clothes dryness: " + Math.round(dryness));
        drynessBar.setValue((int) dryness);
    }

    /**
     * Replenishes the sphincter power.
     *
     * @param amount the sphincter recharge amount
     */
    public void rechargeSphPower(int amount)
    {
        sphincterPower += amount;
        if (sphincterPower > maxSphincterPower)
        {
            sphincterPower = maxSphincterPower;
        }
    }

    private void setLinesAsDialogue(int... lines)
    {
        for (int i : lines)
        {
            dialogueLines[i - 1] = true;
        }
    }

    /**
     * Sets the in-game text.
     *
     * @param lines the in-game text to set
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    private void setText(String... lines)
    {
        if (lines.length > MAX_LINES)
        {
            System.err.println("You can't have more than " + MAX_LINES + " lines at a time!");
            return;
        }
        if (lines.length <= 0)
        {
            textLabel.setText("");
            return;
        }

        String toSend = "<html><center>";

        for (int i = 0; i < lines.length; i++)
        {
            if (dialogueLines[i])
            {
                toSend += "<i>\"" + lines[i] + "\"</i>";
            } else
            {
                toSend += lines[i];
            }
            toSend += "<br>";

        }
        toSend += "</center></html>";
        textLabel.setText(toSend);
        this.dialogueLines = new boolean[MAX_LINES];
    }

    /**
     * Adds/substracts the player score
     *
     * @param message the reason to manipulate score
     * @param mode add or substract
     * @param points points to add/substract
     */
    public void score(String message, char mode, int points)
    {
        switch (mode)
        {
            case '+':
                score += points;
                scoreText = scoreText + "\n" + message + ": +" + points + " points";
                break;
            case '-':
                score -= points;
                scoreText = scoreText + "\n" + message + ": -" + points + " points";
                break;
            case '*':
                score *= points;
                scoreText = scoreText + "\n" + message + ": +" + points * 100 + "% of points";
                break;
            case '/':
                score /= points;
                scoreText = scoreText + "\n" + message + ": -" + 100 / points + "% of points";
                break;
            default:
                System.err.println("score() method used incorrectly, message: \"" + message + "\"");
        }
    }

    /**
     * Multiplies/divides the player score
     *
     * @param message the reason to manipulate score
     * @param mode multiply or divide
     * @param points points to multiply/divide
     */
    public void score(String message, char mode, float points)
    {
        switch (mode)
        {
            case '+':
                score += points;
                scoreText = scoreText + "\n" + message + ": +" + points + " points";
                break;
            case '-':
                score -= points;
                scoreText = scoreText + "\n" + message + ": -" + points + " points";
                break;
            case '*':
                score *= points;
                scoreText = scoreText + "\n" + message + ": +" + points * 100 + "% of points";
                break;
            case '/':
                score /= points;
                scoreText = scoreText + "\n" + message + ": -" + 100 / points + "% of points";
                break;
            default:
                System.err.println("score() method used incorrectly, message: \"" + message + "\"");
        }
    }

    enum GameStage
    {
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
        SURPRISE_WET_PRESSURE
    }

    enum Gender
    {
        MALE, FEMALE
    }
}