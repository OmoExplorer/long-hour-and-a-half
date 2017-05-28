/*
*ALongHourAndAHalf Vers. 1.2
*Date of Version: 5/27/2017
*Time of Version: 22:08
*
*Dev: Rosalie Elodie
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
*
*
*If you have any questions, or want to shove code in her face,
*be sure to send them to Rosalie at her following email address:
*rosalieelodiedev@gmail.com
*(without caps)
*RosalieElodieDev@gmail.com
*(with caps)
*
*She also goes by the terrible username "Justice" on Omo.org
*(omorashi.org)
*and you're free to contact her there!
*(If you already have an account there, it's much more preferable ^^; )
*
*FINAL NOTE: While this is created by Rosalie Dev, she allows it to be posted
*freely, so long as she's creditted. She also states that this program is
*ABSOLUTELY FREE, not to mention she hopes you enjoy ^_^
*
*
*DEV NOTES: Look for bugs, there is always a bunch of them
*Bugs:
*
 */
//name used for copying/pasting purposes (to keep from typing over and over again)
//ALongHourAndAHalf
package omo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import static omo.ALongHourAndAHalf.GameStage.*;
import static omo.ALongHourAndAHalf.Gender.*;
import static omo.ALongHourAndAHalf.generator;

/**
 * Describes an underwear of an outerwear of a character.
 *
 * @author JavaBird
 */
class Wear
{
    /**
     * Отмечено как устаревшее из-за проблем с падежами в русском языке. Вместо этого метка одежды красится в цвет, соответствующий цвету одежды.
     * @deprecated
     */
    @Deprecated
    static String[] colorList = 
    {
        "Black", "Gray", "Red", "Orange", "Yellow", "Green", "Blue", "Dark blue", "Purple", "Pink"
    };

    private final String name;
    private String insertName;
    private final float pressure;
    private final float absorption;
    private final float dryingOverTime;
    private String color;

    /**
     *
     * @param name the wear name (e. g. "Regular panties")
     * @param insertName
     * @param pressure the pressure of an wear.<br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption the absorption of an wear.<br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     * 3 minutes
     * @param type
     */
    Wear(String name, String insertName, float pressure, float absorption, float dryingOverTime)
    {
        this.name = name;
        this.insertName = insertName;
        this.pressure = pressure;
        this.absorption = absorption;
        this.dryingOverTime = dryingOverTime;
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
     * @return the insert name used in the game (e. g. "panties")
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
     * @return the color (enum element)
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
}

//TODO: Разобраться с падежами
@SuppressWarnings("serial")
public class ALongHourAndAHalf extends JFrame
{

    //Maximal lines of a text
    private final static int MAX_LINES = 9;

    public static Random generator = new Random();
    static String nameParam;
    static Gender gndrParam;
    static boolean diffParam;
    static float incParam;
    static int bladderParam;
    static String underParam;
    static String outerParam;
    static String underColorParam;
    static String outerColorParam;

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private static void reset(boolean newValues)
    {
        if(newValues)
            new setupFramePre().setVisible(true);
        else
            new ALongHourAndAHalf(nameParam, gndrParam, diffParam, incParam, bladderParam, underParam, outerParam, underColorParam, outerColorParam);
    }

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

    private final JList<Object> listChoice;

    private final JScrollPane listScroller;

    Wear[] underwearList
            =
            {
                new Wear("Случайно", "ЭТО ОШИБКА", 0, 0, 0),
                new Wear("Без нижней одежды", "ЭТО ОШИБКА", 0, 0, 1),
                new Wear("Стринги", "стринги", 1, 2, 1),
                new Wear("Трусики \"Танга\"", "трусики", 1.5F, 3, 1),
                new Wear("Обычные трусики", "трусики", 2, 4, 1),
                new Wear("Трусики с закрытыми бёдрами", "трусики", 4, 7, 1),
                new Wear("Бикини-стринги", "трусики бикини", 1, 1, 2),
                new Wear("Обычные бикини", "трусики бикини", 2, 2, 2),
                new Wear("Купальник", "купальник", 4, 2.5F, 2.5F),
                new Wear("Лёгкий подгузник", "подгузник", 4.5F, 10, 0),
                new Wear("Средний подгузник", "подгузник", 9, 20, 0),
                new Wear("Большой подгузник", "подгузник", 18, 30, 0),
                new Wear("Лёгкая прокладка", "прокладка", 1, 8, 0.25F),
                new Wear("Обычная прокладка", "прокладка", 1.5F, 12, 0.25F),
                new Wear("Большая прокладка", "прокладка", 2, 16, 0.25F),
                new Wear("Трусы", "трусы", 2.5F, 5, 1),
                new Wear("Трусы с закрытыми бёдрами", "трусы", 3.75F, 7.5F, 1),
                new Wear("Анти-гравитационные трусы", "трусы", 0, 4, 1),
                new Wear("Гипервпитывающий подгузник", "подгузник", 18, 200, 0)
            };

    Wear[] outerwearList
            =
            {
                new Wear("Случайно", "ЭТО ОШИБКА", 0, 0, 0),
                new Wear("Без верхней одежды", "ЭТО ОШИБКА", 0, 0, 1),
                new Wear("Обычные джинсы", "джинсы", 7, 12, 1.2F),
                new Wear("Джинсы по колено", "джинсы", 6, 10, 1.2F),
                new Wear("Джинсовые шорты", "шорты", 5, 8.5F, 1.2F),
                new Wear("Джинсовые мини-шорты", "мини-шорты", 4, 7, 1.2F),
                new Wear("Обычные брюки", "брюки", 9, 15.75F, 1.4F),
                new Wear("Брюки по колено", "брюки", 8, 14, 1.4F),
                new Wear("Шорты", "шорты", 7, 12.25F, 1.4F),
                new Wear("Мини-шорты", "мини-шорты", 6, 10.5F, 1.4F),
                new Wear("Длинная юбка", "юбка", 5, 6, 1.7F),
                new Wear("Обычная юбка", "юбка", 4, 4.8F, 1.7F),
                new Wear("Короткая юбка", "юбка", 3, 3.6F, 1.7F),
                new Wear("Мини-юбка", "мини-юбка", 2, 2.4F, 1.7F),
                new Wear("Микро-юбка", "микро-юбка", 1, 1.2F, 1.7F),
                new Wear("Длинная юбка с колготками", "юбка с колготками", 6, 7.5F, 1.6F),
                new Wear("Обычная юбка с колготками", "юбка с колготками", 5, 8.75F, 1.6F),
                new Wear("Короткая юбка с колготками", "юбка с колготками", 4, 7, 1.6F),
                new Wear("Мини-юбка с колготками", "мини-юбка с колготками", 3, 5.25F, 1.6F),
                new Wear("Микро-юбка с колготками", "микро-юбка с колготками", 2, 3.5F, 1.6F),
                new Wear("Леггинсы", "леггинсы", 10, 11, 1.8F),
                new Wear("Мужские джинсовые шорты", "шорты", 5, 8.5F, 1.2F),
                new Wear("Мужские джинсы", "джинсы", 7, 12, 1.2F),
                new Wear("Мужские брюки", "брюки", 9, 15.75F, 1.4F)
            };

    String[] cheatList
            =
            {
                "Встать в угол", "Остаться после урока", "Пописать в бутылку", "Завершить урок",
                "Успокоить учителя", "Поднять руку", "Периодически опустошать мочевой пузырь",
                "Установить уровень инконтиненции", "Включить/выключить сложный режим", "Установить заполненность мочевого пузыря"
            };

    String names[]
            =
            {
                "Данил",
                "Алёша",
                "Олег",
                "Андрей",
                "Дима",
                "Матвей"
            };
    String boyName = names[generator.nextInt(names.length)];

    ArrayList<String> actionList = new ArrayList<>();
    private GameStage nextStage;

    public String name; //your name
    public Wear lower; // lower body clothing
    public Wear undies; // usually referenced with leaks
    
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
    public int embarassment = 0;
    
    /**
     * Amount of a water in a belly.
     */
    public double belly = 5.0;
    
    /**
     * Before 2.1:<br>
     * simply multiplies a bladder increasement.<br>
     * <br>
     * 2.1 and after:<br>
     * defines the sphincter weakening speed.
     */
    public float incon = 1.0F;
    public double maxSphincterPower; //How many minutes you can wait without squirming?
    public double sphincterPower; //Current sphincter power. The higher bladder level, the faster power consumption.
    public double dryness; //How much pee your clothes can store now?
    public byte min = 0;
    public byte timesPeeDenied = 0;
    
    /**
     * A number that shows a game session difficulty - the higher score, the
     * harder was the game. Specific actions (for example, peeing in a restroom
     * during a lesson) reduce score points. Using the cheats will zero the
     * score points.
     */
    public int score = 0;
    
    /**
     * Number of times player got caught holding pee.
     */
    public byte timesCaught = 0;
    public int classmatesAwareness = 0; //How many classmates know that you've to go?

    public boolean stay = false; // Stay after class? (30 minutes)
    public boolean cornered = false; // Stand in the corner? (can't hold crotch)
    public boolean drain = false; // Pee mysteriously vanishes every 15 minutes?
    public boolean hardcore = false; // Hardcore mode: teacher never lets you pee, it's harder to hold pee, you may get caught holding pee

    /**
     * An array that contains boolean values that define <i>dialogue lines</i>.
     * Dialogue lines, unlike normal lines, are <i>italic</i>.
     */
    private boolean[] dialogueLines = new boolean[MAX_LINES];
    public boolean cheatsUsed = false;//Did player use cheats?
    
    private final JButton btnNewGame;
    private final JLabel lblUndies;
    private final JLabel lblLower;
    private final JLabel lblSphPower;
    private final JLabel lblDryness;
    
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
        bladderParam=bladder;
        
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
            name += " [Сложный режим]";
        }

        //Assigning the boy's name
        boyName = names[generator.nextInt(names.length)];

        //If random undies were chosen...
        if (under.equals("Случайно"))
        {
            undies = underwearList[generator.nextInt(underwearList.length)];
            while(undies.getName().equals("Случайно"))
            //...selecting random undies from the undies array.
                undies = underwearList[generator.nextInt(underwearList.length)];
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
            JOptionPane.showMessageDialog(null, "Выбрана некорректная нижняя одежда. Будет выбрана случайная.", "Некорректная нижняя одежда", JOptionPane.WARNING_MESSAGE);
            undies = underwearList[generator.nextInt(underwearList.length)];
        }

        //Assigning color
        if(!undies.getName().equals("Без нижней одежды"))
            if(!undiesColor.equals("Случайно"))
                undies.setColor(undiesColor);
            else
                undies.setColor(Wear.colorList[generator.nextInt(Wear.colorList.length)]);
        else
            undies.setColor("");
        
        //Same with the lower clothes
        if (outer.equals("Случайно"))
        {
            lower = outerwearList[generator.nextInt(outerwearList.length)];
            while(lower.getName().equals("Случайно"))
                lower = outerwearList[generator.nextInt(outerwearList.length)];
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
            JOptionPane.showMessageDialog(null, "Выбрана некорректная верхняя одежда. Будет выбрана случайная.", "Некорректная верхняя одежда", JOptionPane.WARNING_MESSAGE);
            lower = outerwearList[generator.nextInt(outerwearList.length)];
        }
        
        //Assigning color
        if(!lower.getName().equals("Без верхней одежды"))
            if(!lowerColor.equals("Случайно"))
                lower.setColor(lowerColor);
            else
                lower.setColor(Wear.colorList[generator.nextInt(Wear.colorList.length)]);
        else
            lower.setColor("");

        //Calculating dryness and maximal bladder capacity values
        dryness = lower.getAbsorption() + undies.getAbsorption();
        maxBladder -= lower.getPressure() + undies.getPressure();
        
        if (isMale())
        {
            underwearList[1].setInsertName("член");
        } else
        {
            underwearList[1].setInsertName("промежность");
        }
        
        outerParam = lower.getName();
        underParam = undies.getName();
        underColorParam = undies.getColor();
        outerColorParam = lower.getColor();
        
        //Game window setup
        setResizable(false);
        setTitle("Долгие полтора часа");
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
        btnNext = new JButton("Дальше");
//        btnNext.setToolTipText("Hold down for the time warp");
        btnNext.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                handleNextClicked();
            }
        });
        
        btnNext.setBounds((textPanel.getWidth() / 2) + 50, 480, 89, 23);
        contentPane.add(btnNext);

        //"Quit" button setup
        btnQuit = new JButton("Выход");
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
        btnReset = new JButton("Сброс");
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
        btnNewGame = new JButton("Новая игра");
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
        lblBladder = new JLabel("Мочевой пузырь: " + bladder + "%");
        lblBladder.setToolTipText("<html>Обычный режим:<br>100% = сложно терпеть<br>130% = максимум<br><br>Сложный режим:<br>80% = сложно терпеть<br>100% = максимум</html>");
        lblBladder.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBladder.setBounds(20, 210, 200, 32);
        contentPane.add(lblBladder);

        //Embarassment label setup
        lblEmbarassment = new JLabel("Смущение: " + embarassment);
        lblEmbarassment.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmbarassment.setBounds(20, 240, 200, 32);
        contentPane.add(lblEmbarassment);

        //Belly label setup
        lblBelly = new JLabel("Вода в животе: " + belly + "%");
        lblBelly.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBelly.setBounds(20, 270, 200, 32);
        contentPane.add(lblBelly);

        //Incontinence label setup
        lblIncon = new JLabel("Инконтиненция: " + inc + "x");
        lblIncon.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblIncon.setBounds(20, 300, 200, 32);
        contentPane.add(lblIncon);

        //Time label setup
        lblMinutes = new JLabel("Время: " + min + " минут(ы) из 90");
        lblMinutes.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMinutes.setBounds(20, 330, 200, 32);
        lblMinutes.setVisible(false);
        contentPane.add(lblMinutes);
        
        //Sphincter power label setup
        lblSphPower = new JLabel("Способность терпеть: " + sphincterPower + "%");
        lblSphPower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSphPower.setBounds(20, 360, 200, 32);
        lblSphPower.setVisible(false);
        contentPane.add(lblSphPower);
        
        //Clothing dryness label setup
        lblDryness = new JLabel("Сухость одежды: " + dryness);
        lblBladder.setToolTipText("-20 = конец игры");
        lblDryness.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblDryness.setBounds(20, 390, 200, 32);
        lblDryness.setVisible(false);
        contentPane.add(lblDryness);
        
        //Undies label setup
        lblUndies = new JLabel("Нижняя одежда: " + undies.getColor() + " " + undies.getName().toLowerCase());
        lblUndies.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblUndies.setBounds(20, 420, 400, 32);
        contentPane.add(lblUndies);
        
        //Lower label setup
        lblLower = new JLabel("Верхняя одежда: " + lower.getColor() + " " + lower.getName().toLowerCase());
        lblLower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblLower.setBounds(20, 450, 400, 32);
        contentPane.add(lblLower);

        //Choice label ("What to do?") setup
        lblChoice = new JLabel();
        lblChoice.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblChoice.setBounds(320, 192, 280, 32);
        lblChoice.setVisible(false);
        contentPane.add(lblChoice);

        //Action list setup
        listChoice = new JList<>();
        listChoice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listChoice.setLayoutOrientation(JList.VERTICAL);

        listScroller = new JScrollPane(listChoice);
        listScroller.setBounds(320, 216, 280, 160);
        listScroller.setVisible(false);
        contentPane.add(listScroller);

//        pack();
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

    //Telling the compiler to ignore missing break at the end of some cases
    @SuppressWarnings("fallthrough")
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
                        name = "Миссис Никто";
                    } else
                    {
                        name = "Мистер Никто";
                    }
                }

                score("Заполненность мочевого пузыря в начале игры - " + bladder + "%", '+', Math.round(bladder));

                //Incontinence rounding
                BigDecimal bd = new BigDecimal(incon);
                bd = bd.setScale(1, RoundingMode.HALF_UP);
                incon = bd.floatValue();

                score("Инконтиненция - " + incon + "x", '*', incon);

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

                setLinesAsDialogue(1);
                if (!lower.getName().equals("Без верхней одежды"))
                    if (!undies.getName().equals("Без нижней одежды"))
                        //Both lower clothes and undies
                        if(isFemale())
                            setText("Ч-что? Я-я забыла завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + undies.insert()/*винительный падеж*/ + " и " + lower.insert() + ",",
                                    "даже не заботясь о том, что надеть на тело.");
                        else
                            setText("Ч-что? Я-я забыл завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + undies.insert()/*винительный падеж*/ + " и " + lower.insert() + ",",
                                    "даже не заботясь о том, что надеть на тело.");
                    else
                        //Lower clothes only
                        if(isFemale())
                            setText("Ч-что? Я-я забыла завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + lower.insert() + ",",
                                    "даже не заботясь о том, что надеть на тело.");
                        else
                            setText("Ч-что? Я-я забыл завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + lower.insert() + ",",
                                    "даже не заботясь о том, что надеть на тело.");
                else
                    if (!undies.getName().equals("Без нижней одежды"))
                        //Undies only
                        if(isFemale())
                            setText("Ч-что? Я-я забыла завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + lower.insert() + ", каким-то образом забыв надеть верхнюю одежду,",
                                    "даже не заботясь о том, что надеть на тело.");
                        else
                            setText("Ч-что? Я-я забыл завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + lower.insert() + ", каким-то образом забыв надеть верхнюю одежду,",
                                    "даже не заботясь о том, что надеть на тело.");
                    else
                        //No clothes at all
                        if(isFemale())
                            setText("Ч-что? Я-я забыла завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться,",
                                    "И решаешь пойти в школу... Полностью голышом.");
                        else
                            setText("Ч-что? Я-я забыл завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться,",
                                    "И решаешь пойти в школу... Полностью голышом.");
                offsetBladder(.5);
                nextStage = LEAVE_HOME;
                break;

            case LEAVE_HOME:
                if(isFemale())
                    setText("Взглянув на часы, ты никак не можешь понять, почему же ты забыла завести вчера будильник.",
                            "",
                            "Не обращая внимания на ежедневную суету, ты залпом выпиваешь стакан сока и выбегаешь на улицу.");
                else
                    setText("Взглянув на часы, ты никак не можешь понять, почему же ты забыл завести вчера будильник.",
                            "",
                            "Не обращая внимания на ежедневную суету, ты залпом выпиваешь стакан сока и выбегаешь на улицу.");
                offsetBladder(.5);
                offsetEmbarassment(3);
                offsetBelly(10);
                nextStage = GO_TO_CLASS;
                break;

            case GO_TO_CLASS:
                lblMinutes.setVisible(true);
                lblSphPower.setVisible(true);
                lblDryness.setVisible(true);

                if (!lower.getName().equals("Без верхней одежды"))
                    if (lower.insert().endsWith("юбка"))
                        setText("Ты вбегаешь в класс так быстро, что твоя " + lower.insert() + " вздувается ветром.",
                                "",
                                "Если бы не второпях, ты бы пожалела о том, что кто-то смог бы увидеть твои " + undies.insert() + "под юбкой.",
                                "Не привлекая особого внимания, садишься на своё место.");
                    else
                        setText("Быстро вбегая в класс и",
                                "не привлекая особого внимания, ты садишься на своё место.");
                else
                    if (!undies.getName().equals("Без нижней одежды"))
                        setText("Ты вбегаешь в класс, и твои одноклассники сразу же с крайним удивлением обратили внимание на твои " + undies.insert() + ".",
                                "Ты садишься на место под взгядами всего класса.");
                    else
                        if (isFemale())
                        {
                            setLinesAsDialogue(3,4);
                            setText("Ты вбегаешь в класс, и твои одноклассники, особенно мальчики, сразу же с крайним удивлением обратили внимание на твои женские прелести.",
                                    "По классу слышен смущенный говор твоих однокласскиков.",
                                    "Чего?",
                                    "С ума сойти!!",
                                    "Ты садишься на место под взгядами всего класса.");
                        }
                        else
                            setLinesAsDialogue(3,4);
                            setText("Ты вбегаешь в класс, и твои одноклассники, особенно девочки, сразу же с крайним удивлением обратили внимание на твои мужские прелести.",
                                    "По классу слышен смущенный говор твоих однокласскиков.",
                                    "Чего?",
                                    "С ума сойти!!",
                                    "Ты садишься на место под взгядами всего класса.");

                offsetEmbarassment(2);
                nextStage = WALK_IN;
                break;

            case WALK_IN:
                if (lower.insert().endsWith("юбка") || lower.insert().equals("юбка с колготками") || lower.insert().equals("мини-шорты") || lower.insert().equals("микро-шолты"))
                {
                    setLinesAsDialogue(1, 3);
                    setText("В следующий раз, " + name + ",",
                            "говорит учитель,",
                            "надевай что-нибудь менее... вызывающее!");
                    offsetEmbarassment(5);
                } else
                    if (lower.getName().equals("Без верхней одежды"))
                    {
                        setLinesAsDialogue(1);
                        if(isFemale())
                            setText("ЧЕГО!? ТЫ ПРИШЛА В ШКОЛУ ГОЛОЙ!?",
                                    "не может поверить своим глазам учитель.",
                                    "Как и твои одноклассники.");
                        else
                            setText("ЧЕГО!? ТЫ ПРИШЁЛ В ШКОЛУ ГОЛЫМ!?",
                                    "не может поверить своим глазам учитель.",
                                    "Как и твои одноклассники.");
                        offsetEmbarassment(25);
                    } else
                    {
                        setLinesAsDialogue(1, 3);
                        setText("Садись на место, " + name + ". Ты опоздала.",
                                "говорит учитель,",
                                "И больше не вламывайся в класс!");
                    }
                nextStage = SIT_DOWN;
                break;

            case SIT_DOWN:
                setText("Невольно сжимая ноги, ты чуствуешь этот дискомфорт",
                        "создаваемый жидкостью, идущей в мочевой пузырь.");
                passTime();
                nextStage = ASK_ACTION;
                score("Смущение в начале игры - " + incon + " очк.", '+', embarassment);
                break;

            case ASK_ACTION:
                if (generator.nextInt(20) == 5)
                {
                    setText("Внезапно учитель окликает тебя.");
                    nextStage = CALLED_ON;
                    break;
                }

                //Bladder: 0-20
                if (bladder<=20)
                    setText("Совершенно не вдаваясь в урок,",
                            "ты сидишь и скучаешь, поглядывая на часы.");
                //Bladder: 20-40
                if (bladder>20 && bladder<=40)
                    setText("Чуствуя слабые позывы пописать,",
                            "ты смотришь на часы, про себя умоляя время идти быстрее.");
                //Bladder: 40-60
                if (bladder>40 && bladder<=60)
                    setText("Отчётливо желая посетить уборную,",
                            "ты нетерпеливо ждёшь конца урока, закинув ногу на ногу, чтобы ослабить нужду.");
                //Bladder: 60-80
                if (bladder>60 && bladder<=80)
                {
                    setLinesAsDialogue(3);
                    setText("Ты чуствуешь, как твой мочевой пузырь буквально надувается как воздушный шарик.",
                            "Ты уже очень сильно хжелаешь пописать.",
                            "Может быть попросить учителя выйти? Терпеть уже очень неприятно...");
                }
                //Bladder: 80-100
                if (bladder>80 && bladder<=100)
                {
                    setLinesAsDialogue(3);
                    setText("Держать в себе всю мочу очень скоро станет невозможным.",
                            "Чувствуя тупую боль в мочевом пузыре, ты хочешь писать как никогда раньше.",
                            "Ай!.. Нужно срочно что-то делать, иначе...");
                }
                //Bladder: 100-130
                if (bladder>100 && bladder<=130)
                {
                    setLinesAsDialogue(1,3);
                    if(isFemale())
                        setText("Это какой-то кошмар... пусть уж это будет сном...",
                                "Ты не знаешь, сможешь ли ты продержаться до конца урока и о ужас,",
                                "Мочевой пузырь выпирает уже выше пупка.",
                                "Ааааа... Я не могу больше терпеть!!!",
                                "Даже палец, перекрывающий уретру, уже не помогает облегчать твои страдания.");
                    else
                        setText("Это какой-то кошмар... пусть уж это будет сном...",
                                "Ты не знаешь, сможешь ли ты продержаться до конца урока и о ужас,",
                                "Мочевой пузырь выпирает уже выше пупка.",
                                "Ааааа... Я не могу больше терпеть!!!",
                                "Даже эрекция уже не помогает облегчать твои страдания.");
                }
                lblChoice.setText("Что дальше?");
                listScroller.setVisible(true);
                lblChoice.setVisible(true);

                actionList.clear();
                switch (timesPeeDenied)
                {
                    case 0:
                        actionList.add("Попросить учителя выйти");
                        break;
                    case 1:
                        actionList.add("Попросить учителя выйти ещё раз");
                        break;
                    case 2:
                        actionList.add("Попросить учителя выйти ещё раз");
                        break;
                    case 3:
                        actionList.add("Рискнуть и попроситься ещё раз (опасно)");
                        break;
                    default:
                        actionList.add("[Недоступно]");
                }

                if (!cornered)
                {
                    if (isFemale())
                    {
                        actionList.add("Надавить на промежность");
                    } else
                    {
                        actionList.add("Сжать член");
                    }
                } else
                {
                    actionList.add("[Недоступно]");
                }

                actionList.add("Сжать ноги");

                if (bladder >= 100)
                {
                    actionList.add("Сдаться и описаться");
                } else
                {
                    actionList.add("[Недоступно]");
                }

                actionList.add("Просто ждать");
                actionList.add("Использовать читы (аннулирует очки)");

                listChoice.setListData(actionList.toArray());
                nextStage = CHOSE_ACTION;
                passTime();
                break;

            case CHOSE_ACTION:
                nextStage = ASK_ACTION;
                if (listChoice.isSelectionEmpty())
                {
                    break;
                }
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                String actionName = (String) listChoice.getSelectedValue();
                if (actionName.equals("[Недоступно]"))
                {
                    listChoice.clearSelection();
                    if(isFemale())
                        setText("Ты прождала пару минут, ничего не делая.");
                    else
                        setText("Ты прождал пару минут, ничего не делая.");
                    break;
                }

                int actionNum = listChoice.getSelectedIndex();

                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                listChoice.clearSelection();

                switch (actionNum)
                {
                    //Default effectiveness: 12

                    //Ask the teacher to go pee
                    case 0:
                        nextStage = ASK_TO_PEE;
                        setLinesAsDialogue(2, 3);
                        setText("Ты размышляешь:",
                                "Я не думаю, что я вытерплю до конца урока.",
                                "Нужно попробовать попроситься в туалет.");
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
                        setText("Надеясь, что это никто не заметит",
                                "ты сжимаешь свою ладонб между ног.",
                                "Стало немного легче.");

                        rechargeSphPower(20);
                        offsetTime(3);

                        if (generator.nextInt(100) <= 15 + classmatesAwareness & hardcore)
                            nextStage = CAUGHT;
                        else
                            nextStage = ASK_ACTION;
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
                        setText("Хоть ты и хочешь писать,",
                                "ты не можешь себе позволить, чтобы кто-нибудь увидел тебя",
                                "с рукой между ног. Ты поджимаешь ноги, но это не сильно помогает.");

                        rechargeSphPower(2);
                        offsetTime(3);

                        if (generator.nextInt(100) <= 3 + classmatesAwareness & hardcore)
                            nextStage = CAUGHT;
                        else
                            nextStage = ASK_ACTION;
                        break;

                    //Give up
                    case 3:
                        setText("Тебе очень сильно нужно пописать,",
                                "но предполагая, что ты всё равно описаешься, ты решаешь сдаться",
                                "и избавиться от этой ужасной боли в мочевом пузыре.");
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
                        int time;
                        try
                        {
                            time = Integer.parseInt(JOptionPane.showInputDialog("Сколько ждать?"));
                            if (time < 1)
                            {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException | NullPointerException e)
                        {
                            nextStage = ASK_ACTION;
                            break;
                        }

                        

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
                        setText("Приспичило довольно сильно.",
                                "Но ведь всегда есть выход, не правда ли?");
                        cheatsUsed = true;
                        nextStage = ASK_CHEAT;
                        break;

                    default:
                        if(isFemale())
                            setText("Ты просидела несколько минут, ничего не делая.");
                        else
                            setText("Ты просидел несколько минут, ничего не делая.");
                        nextStage = ASK_ACTION;
                        break;
                }
                break;

            case ASK_TO_PEE:
                switch (timesPeeDenied)
                {
                    case 0:
                        if (generator.nextInt(100) <= 40 & !hardcore)
                        {
                            setLinesAsDialogue(1,2);
                            if(!lower.getName().equals("Без верхней одежды"))
                                if(!undies.getName().equals("Без нижней одежды"))
                                    setText("Можно выйти?",
                                            "Да, конечно,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.insert() + " и " + undies.insert(),
                                            "и начинаешь писать.");
                                else
                                    setText("Можно выйти?",
                                            "Да, конечно,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.insert(),
                                            "и начинаешь писать.");
                            else
                                if(!undies.getName().equals("Без нижней одежды"))
                                    setText("Можно выйти?",
                                            "Да, конечно,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + undies.insert(),
                                            "и начинаешь писать.");
                                else
                                    setText("Можно выйти?",
                                            "Да, конечно,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты заходишь в кабинку",
                                            "и начинаешь писать.");
//                            score *= 0.2;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -80% of points");
                            score("Использование туалета во время урока", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        } else
                        {
                            setLinesAsDialogue(1);
                            setText("Можно выйти?",
                                    "Нет, директор запретил ученикам выходить во время урока.",
                                    "ответил учитель.");
                            timesPeeDenied++;
                        }
                        break;

                    case 1:
                        if (generator.nextInt(100) <= 10 & !hardcore)
                        {
                            setLinesAsDialogue(1,2);
                            if(!lower.getName().equals("Без верхней одежды"))
                                if(!undies.getName().equals("Без нижней одежды"))
                                    setText("Можно выйти?",
                                            "Ладно, выйди",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.insert() + " и " + undies.insert(),
                                            "и начинаешь писать.");
                                else
                                    setText("Можно выйти?",
                                            "Ладно, выйди,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.insert(),
                                            "и начинаешь писать.");
                            else
                                if(!undies.getName().equals("Без нижней одежды"))
                                    setText("Можно выйти?",
                                            "Ладно, выйди,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + undies.insert(),
                                            "и начинаешь писать.");
                                else
                                    setText("Можно выйти?",
                                            "Ладно, выйди,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты заходишь в кабинку",
                                            "и начинаешь писать.");
//                            score *= 0.22;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -78% of points");
                            score("", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        } else
                        {
                            if(isFemale())
                                setText("Можно выйти?",
                                        "Нет! "+ name +", чего ты такая непонятливая? Было сказано, директор не разрешает!",
                                        "отказал учитель.");
                            else
                                setText("Можно выйти?",
                                        "Нет! "+ name +", чего ты такой непонятливый? Было сказано, директор не разрешает!",
                                        "отказал учитель.");
                            timesPeeDenied++;
                        }
                        break;

                    case 2:
                        if (generator.nextInt(100) <= 30 & !hardcore)
                        {
                            setLinesAsDialogue(1,2);
                            if(!lower.getName().equals("Без верхней одежды"))
                                if(!undies.getName().equals("Без нижней одежды"))
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert() + " и " + undies.insert(),
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert() + " и " + undies.insert(),
                                                "и начинаешь писать.");
                                else
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert(),
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert(),
                                                "и начинаешь писать.");
                            else
                                if(!undies.getName().equals("Без нижней одежды"))
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + undies.insert(),
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + undies.insert(),
                                                "и начинаешь писать.");
                                else
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты заходишь в кабинку",
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты заходишь в кабинку",
                                                "и начинаешь писать.");
//                            score *= 0.23;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -77% of points");
                            score("Использование туалета во время урока", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        } else
                        {
                            setLinesAsDialogue(1,2,3);
                            setText("Можно выйти, пожалуйста?",
                                    "Нет, "+name+"! Кому сказано!",
                                    "Прекрати проситься, или я тебя накажу!");
                            timesPeeDenied++;
                        }
                        break;

                    case 3:
                        if (generator.nextInt(100) <= 7 & !hardcore)
                        {
                            setLinesAsDialogue(1,2);
                            if(!lower.getName().equals("Без верхней одежды"))
                                if(!undies.getName().equals("Без нижней одежды"))
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert() + " и " + undies.insert(),
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert() + " и " + undies.insert(),
                                                "и начинаешь писать.");
                                else
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert(),
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.insert(),
                                                "и начинаешь писать.");
                            else
                                if(!undies.getName().equals("Без нижней одежды"))
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + undies.insert(),
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + undies.insert(),
                                                "и начинаешь писать.");
                                else
                                    if(isFemale())
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такая надоедливая, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты заходишь в кабинку",
                                                "и начинаешь писать.");
                                    else
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такой надоедливый, "+name+"!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты заходишь в кабинку",
                                                "и начинаешь писать.");
//                            score *= 0.3;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -70% of points");
                            score("Использование туалета во время урока", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        } else
                            if (generator.nextBoolean())
                            {
                                setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                        "НЕТ, НЕТ, НЕТ!!! НЕЛЬЗЯ!!! В УГОЛ!!",
                                        "разозлился учитель.");
                                cornered = true;
//                            score += 1.3 * (90 - min / 3);
//                            scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +" + 1.3 * (90 - min / 3) + " score");
                                score("Stayed on corner " + (90 - min) + " minutes", '+', 1.3F * (90 - min / 3));
                                offsetEmbarassment(5);
                            } else
                            {
                                setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                        "НЕТ, НЕТ, НЕТ!!! НЕЛЬЗЯ!!! ОСТАЁШЬСЯ ПОСЛЕ УРОКОВ!!",
                                        "разозлился учитель.");
                                offsetEmbarassment(5);
                                stay = true;
//                            scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
//                            score *= 1.6;
                                if(isFemale())
                                    score("Осталась после урока", '*', 1.6F);
                                else
                                    score("Остался после урока", '*', 1.6F);
                            }
                        timesPeeDenied++;

                        break;
                }
                nextStage = ASK_ACTION;
                break;

            case ASK_CHEAT:
                lblChoice.setText("Выберите чит:");
                listChoice.setListData(cheatList);
                lblChoice.setVisible(true);
                listScroller.setVisible(true);
                nextStage = CHOSE_CHEAT;
                break;

            case CHOSE_CHEAT:
                if (listChoice.isSelectionEmpty())
                {
                    break;
                }

                lblChoice.setText("");
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                switch (listChoice.getSelectedIndex())
                {
                    case 0:
                        setText("Ты проходишь в угол класса.");
                        cornered = true;
                        nextStage = ASK_ACTION;
                        break;

                    case 1:
                        setText("Ты решаешь остаться после уроков.");
                        stay = true;
                        nextStage = ASK_ACTION;
                        break;

                    case 2:
                        setText("Ты заметил что-то рядом с тобой.");
                        nextStage = USE_BOTTLE;
                        break;

                    case 3:
                        setLinesAsDialogue(2);
                        setText("Из громкоговорителей раздался голос:",
                                "Все уроки отменены без причины!");
                        min = 89;
                        break;

                    case 4:
                        setText("Учитель, похоже, пожалел тебя. Попробуй попроситься выйти.");
                        timesPeeDenied = 0;
                        stay = false;
                        cornered = false;
                        nextStage = ASK_ACTION;
                        break;

                    case 5:
                        setText("Ты поднял руку.");
                        nextStage = CALLED_ON;
                        break;

                    case 6:
                        setText("Внезапно, ты чуствуешь, что ты писаешь...",
                                "но не чувствуешь никакой влаги.",
                                "Что это может быть?");
                        drain = true;
                        nextStage = ASK_ACTION;
                        break;

                    case 7:
                        setText("Сосед по парте дал тебе какую-то таблетку,",
                                "и ты её проглотил.");
                        incon = Float.parseFloat(JOptionPane.showInputDialog("Введите уровень инконтиненции:"));
                        lblIncon.setText("Инконтиненция: " + incon + "x");
                        nextStage = ASK_ACTION;
                        break;

                    case 8:
                        setText("Кажется, учителю",
                                "надоели люди, просящиеся в туалет.");
                        hardcore = !hardcore;
                        nextStage = ASK_ACTION;
                        break;
                        
                    case 9:
                        setText("В твоём мочевом пузыре что-то происходит.");
                        incon = Float.parseFloat(JOptionPane.showInputDialog("Введите заполненность мочевого пузыря:"));
                        lblIncon.setText("Мочевой пузырь: " + bladder + "%");
                        nextStage = ASK_ACTION;
                        break;
                }
                break;

            case USE_BOTTLE:
                emptyBladder();
                setLinesAsDialogue(3);
                setText("Этим чем-то оказалась бутылка.",
                        "Как можно тише, ты начинаешь писать в бутыку",
                        "Аааааах...",
                        "Ты испытываешь истинное наслаждение от опустошения мочевого пузыря");
                nextStage = ASK_ACTION;
                break;

            case CALLED_ON:
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                setLinesAsDialogue(1);
                setText(name + ", почему бы тебе не выйти к доске и не ответить,",
                        "спросил учитель. Естественно, ты не знаешь, как ответить.",
                        "Ты проходишь к доске и пробуешь вспомнить хоть что-нибудь, и знаешь, что застренешь здесь",
                        "на несколько минут, пока учитель не растолкует материал.",
                        "Что ж, у доски терпеть сложнее...");
                
//                score += 5;
//                scoreText = scoreText.concat("\nCalled on the lesson: +5 points");
                if(isFemale())
                    score("Вызвана на уроке", '+', 5);
                else
                    score("Вызван на уроке", '+', 5);
                nextStage = ASK_ACTION;
                passTime((byte) 5);
                break;

            case CLASS_OVER:
                if (generator.nextInt(100) <= 5 && hardcore & isFemale())
                {
                    nextStage = SURPRISE;
                }
                if (stay)
                {
                    nextStage = AFTER_CLASS;
                    break;
                }

                //setLinesAsDialogue(6);
                if (generator.nextBoolean())
                {
                    setText("Lesson is finally over, and you're running to the restroom as fast as you can.",
                            "No, please... All cabins are occupied, and there's a line. You have to wait!");
                    
//                    score += 5;
//                    scoreText = scoreText.concat("\nWaited for a free cabin in the restroom: +5 score");
                    score("Waited for a free cabin in the restroom", '+', 5);
                    passTime((byte) 5);
                    break;
                } else
                {
                    if (!lower.getName().equals("Без верхней одежды"))
                        if (!undies.getName().equals("Без нижней одежды"))
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        else
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                    else
                        if (!undies.getName().equals("Без нижней одежды"))
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        else
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it,",
                                    "wearily flop down on the toilet and start peeing.");
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
                        "No, " + name + ", you can't go to the restroom now! It will be as punishment.",
                        "And don't think you can hold yourself either! I'm watching you...");

                if (belly >= 3)
                {
                    offsetBladder(3);
                    offsetBelly(-3);
                    decaySphPower();
                } else
                {
                    offsetBladder(belly + 1);
                    decaySphPower();
                    emptyBelly();
                }

                passTime();

                if (testWet())
                {
                    nextStage = ACCIDENT;
                    break;
                }

                offsetTime(3);
                break;

            case ACCIDENT:
//                listChoice.setVisible(false);
                listScroller.setVisible(false);
                lblChoice.setVisible(false);
                //setLinesAsDialogue(6);
                setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.",
                        "Despite all this, you try your best, but suddenly you're forced to stop.",
                        "You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere.",
                        "But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.");
                nextStage = WET;
                break;

            case GIVE_UP:
                offsetEmbarassment(80);
                if (!lower.getName().equals("Без верхней одежды"))
                {
                    if (!undies.getName().equals("Без нижней одежды"))
                    {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decide to give up and pee in your " + undies.insert() + ".");
                    } else
                    {
                        setText("You get tired of holding all the urine in your aching bladder,",
                                "and you decided to pee in your " + lower.insert() + ".");
                    }
                } else
                    if (!undies.getName().equals("Без нижней одежды"))
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
                embarassment=100;
                if (!lower.getName().equals("Без верхней одежды"))
                {
                    if (!undies.getName().equals("Без нижней одежды"))
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
                    if (!undies.getName().equals("Без нижней одежды"))
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
                    if (lower.getName().equals("Без верхней одежды"))
                    {
                        if (isFemale() && undies.getName().equals("Без нижней одежды"))
                        {
                            setText("People around you are laughing loudly.",
                                    name + " peed herself! Ahaha!!!");
                        } else
                            if (isMale() && undies.getName().equals("Без нижней одежды"))
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
                            "Oh, you peed yourself? This is as punishment.",
                            "I hope you will no longer get in the way of the lesson.");
                }
                nextStage = GAME_OVER;
                break;

            case GAME_OVER:
                if (lower.getName().equals("Без верхней одежды"))
                    if (undies.getName().equals("Без нижней одежды"))
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
                    else
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + undies.insert() + " are clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
                else
                    if (undies.getName().equals("Без нижней одежды"))
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + lower.insert() + " clinging to your skin, a sign of your failure...",//TODO: Add "is/are" depending on lower clothes type
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
                    else
                        setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...",
                                "Your " + lower.insert() + " and " + undies.insert() + " are both clinging to your skin, a sign of your failure...",
                                "...unless, of course, you meant for this to happen?",
                                "No, nobody would be as sadistic as that, especially to themselves...",
                                "Game over!");
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
                System.exit(0);
                break;

            case CAUGHT:
                switch (timesCaught)
                {
                    case 0:
                        setText("It looks like a classmate has spotted that you've got to go badly.",
                                "Damn, he may spread that fact...");
                        offsetEmbarassment(3);
                        classmatesAwareness += 5;
//                        score += 3;
//                        scoreText = scoreText.concat("\nCaught holding pee: +3 points");
                        score("Caught holding pee", '+', 3);
                        timesCaught++;
                        break;
                    case 1:
                        setLinesAsDialogue(3);
                        setText("You'he heard a suspicious whisper behind you.",
                                "Listening to whisper, you've found out that they're talking that you need to go.",
                                "If I hold it until the lesson end, I will beat them.");
                        offsetEmbarassment(8);
                        classmatesAwareness += 5;
//                        score += 8;
//                        scoreText = scoreText.concat("\nCaught holding pee: +8 points");
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
//                        score += 12;
//                        scoreText = scoreText.concat("\nCaught holding pee: +12 points");
                        score("Caught holding pee", '+', 12);
                        timesCaught++;
                        break;
                    default:
                        setText("The chuckles are continiously passing over the classroom.",
                                "Everyone are watching over you.",
                                "Oh god... So embarassed...");
                        offsetEmbarassment(20);
                        classmatesAwareness += 5;
//                        score += 20;
//                        scoreText = scoreText.concat("\nCaught holding pee: +20 points");
                        score("Caught holding pee", '+', 20);
                }
                nextStage = ASK_ACTION;
                break;

            /*
             * "Surprise" is an additional scene after the lesson where player is being caught by her classmate. He wants her to wet herself.
             * Triggering conditions: female, hardcore
             * Triggering chance: 5%
             */
            case SURPRISE:
//                score += 70;
//                scoreText = scoreText.concat("\nGot the \"surprise\" by " + boyName + ": +70 points");
                score("Got the \"surprise\" by " + boyName, '+', 70);
                setText("Lesson is finally over, and you're running to the restroom as fast as you can.",
                        "But... You see " + boyName + " staying in front of the restroom.",
                        "Suddenly, he takes you, don't letting you to escape.");
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
                        "He has brought you on his hands in the restroom and put you on the windowsill.",
                        boyName + " has locked the restroom door (seems he has stolen the key), then he put his palm on your belly and said:",
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
                        "He slightly pressed your belly, you shook from the terrible pain",
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
                    setText("You will wet yourself right now,",
                            boyName + " says.",
                            "Then " + boyName + " pressed your bladder...");
                    nextStage = SURPRISE_WET_PRESSURE;
                }
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                offsetTime(1);
                offsetBladder(1.5);
                offsetBelly(-1.5);
                decaySphPower();

                actionName = (String) listChoice.getSelectedValue();
                if (actionName.equals("[Недоступно]"))
                {
                    setText("You will wet yourself right now,",
                            boyName + " says.",
                            "Then " + boyName + " pressed your bladder...");
                    nextStage = SURPRISE_WET_PRESSURE;
                }

                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                listChoice.clearSelection();

                switch (actionName)
                {
                    case "Hit him":
                        nextStage = HIT;
                        break;
                    case "Try to persuade him to let you pee":
                    case "Try to persuade him to let you pee again":
                    case "Take a chance and try to persuade him (RISKY)":
                        nextStage = PERSUADE;
                        break;
                    case "Pee yourself":
                        nextStage = SURPRISE_WET_VOLUNTARY;
                }

            case HIT:
                if (generator.nextInt(100) <= 10)
                {
                    setLinesAsDialogue(2);
                    nextStage = GameStage.END_GAME;
//                    score += 40;
//                    scoreText = scoreText.concat("\nSuccessful hit on " + boyName + "'s groin: +40 points");
                    score("Successful hit on " + boyName + "'s groin", '+', 40F);
                    if (!lower.getName().equals("Без верхней одежды"))
                        if (!undies.getName().equals("Без нижней одежды"))
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it, pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        else
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it, pulled down your " + lower.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                    else
                        if (!undies.getName().equals("Без нижней одежды"))
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it, pulled down your " + undies.insert() + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        else
                            setText("You hit " + boyName + "'s groin.",
                                    "Ouch!.. You, little bitch...",
                                    "Then he left the restroom quickly.",
                                    "You got off from the windowsill while holding your crotch,",
                                    "opened the cabin door, entered it,",
                                    "wearily flop down on the toilet and start peeing.");
                    /*
                    Gender-dependent text block template

                    if(!lower.equals("no lower"))
                        if(!undies.equals("промежность")|!undies.equals("член"))
                            Undies: yes
                            Lower: yes
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower + " and " + undies + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        else
                            Undies: no
                            Lower: yes
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + lower + ",",
                                    "wearily flop down on the toilet and start peeing.");
                    else
                        if(!undies.equals("промежность")|!undies.equals("член"))
                            Undies: yes
                            Lower: no
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it, pulled down your " + undies + ",",
                                    "wearily flop down on the toilet and start peeing.");
                        else
                            Undies: no
                            Lower: no
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Thank god, one cabin is free!",
                                    "You enter it,",
                                    "wearily flop down on the toilet and start peeing.");
                     */
                } else
                {
                    nextStage = GameStage.SURPRISE_WET_PRESSURE;
                    setLinesAsDialogue(2, 3);
                    setText("You hit " + boyName + "'s hand. Damn, you'd to hit his groin...",
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
                            if (!lower.getName().equals("Без верхней одежды"))
                                if (!undies.getName().equals("Без нижней одежды"))
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                else
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + lower.insert() + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                            else
                                if (!undies.getName().equals("Без нижней одежды"))
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + undies + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                else
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
//                            score += 40;
//                            scoreText = scoreText.concat("\nPersuaded " + boyName + " to pee: +40 points");
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
                            if (!lower.getName().equals("Без верхней одежды"))
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                } else
                                {
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + lower.insert() + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                }
                            } else
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + undies + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                } else
                                {
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                }
//                            score += 60;
//                            scoreText = scoreText.concat("\nPersuaded " + boyName + " to pee: +60 points");
                            score("Persuaded " + boyName + " to pee", '+', 60);
                            emptyBladder();
                            nextStage = END_GAME;
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee again.",
                                    "No, you can't pee in a cabin. I want you to wet yourself. Let's do it.,",
                                    boyName + " says.");
                            timesPeeDenied++;
                            nextStage = SURPRISE_DIALOGUE;
                        }
                        break;

                    case 2:
                        if (generator.nextInt(100) <= 2)
                        {
                            if (!lower.getName().equals("Без верхней одежды"))
                                if (!undies.getName().equals("Без нижней одежды"))
                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + lower.insert() + " and " + undies.insert() + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                else

                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + lower.insert() + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");

                            else
                                if (!undies.getName().equals("Без нижней одежды"))

                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "pulled down your " + undies.insert() + ",",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");
                                else

                                    setText("Ok, you may, but let me watch how do you pee.",
                                            "says " + boyName + ". You enter the cabin,",
                                            "stood over the toilet and start peeing under the " + boyName + " spectation.");

//                            score += 80;
//                            scoreText = scoreText.concat("\nPersuaded " + boyName + " to pee: +80 points");
                            score("Persuaded " + boyName + " to pee", '+', 80);
                            emptyBladder();
                            nextStage = END_GAME;
                        } else
                        {
                            setText("You ask " + boyName + " if you can pee again desperately.",
                                    "No, you can't pee in a cabin. You will wet yourself right now,",
                                    boyName + " says.",
                                    "Then " + boyName + " pressed your bladder...");
                            nextStage = SURPRISE_WET_PRESSURE;
                        }
                        break;
                }
                break;

            case SURPRISE_WET_VOLUNTARY:
                setLinesAsDialogue(1, 3);
                setText("Alright, as you say.,",
                        "you say to " + boyName,
                        "Whatever I can't hold it anymore.");
                emptyBladder();
                nextStage = SURPRISE_WET_VOLUNTARY2;
                break;

            case SURPRISE_WET_VOLUNTARY2:
                setText("You're feeling the warm, scalding pee stream",
                        "filling your " + undies.insert() + " and wetting your " + lower.insert() + ".",
                        "You're closing your eyes and easing your sphincter off.",
                        "You feel as the pee stream becaming much stronger.");
                emptyBladder();
                nextStage = END_GAME;
                break;

            case SURPRISE_WET_PRESSURE:
                setText("Ouch... The sudden pain flash is passing through your bladder...",
                        "You're trying to hold the pee back, but you can't.",
                        "You're feeling the warm, scalding pee stream",
                        "filling your " + undies.insert() + " and wetting your " + lower.insert() + ".",
                        "You're closing your eyes and easing your sphincter off.",
                        "You feel as the pee stream becaming much stronger.");
                emptyBladder();
                nextStage = END_GAME;
                break;

            case WHERE_TO_GO:
                setLinesAsDialogue(4);
                setText("Lesson is over, and you're running to the restroom as fast as you can.",
                       "Trying to get into the restroom,",
                       "You find with fear that restroom is locked for some reason.",
                       "Damnit... Seems that I have to pee somewhere else...");
                lblChoice.setText("Where to go?");
                listScroller.setVisible(true);
                lblChoice.setVisible(true);

                actionList.clear();
                actionList.add("Mall (5 minutes)");
                actionList.add("Bus stop (1 minute)");
                actionList.add("Directly to home (15 minutes)");
                
                if (!listChoice.isSelectionEmpty())
                {
                    nextStage = GameStage.WHERE_TO_GO_CHOSE;
                    break;
                }
            break;
               
            case WHERE_TO_GO_CHOSE:
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

//                actionName = (String) listChoice.getSelectedValue();
//                if (actionName.equals("[Недоступно]"))
//                {
//                    listChoice.clearSelection();
//                    setText("You've spent a few minutes doing nothing.");
//                    break;
//                }

                actionNum = listChoice.getSelectedIndex();

                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                listChoice.clearSelection();

                switch (actionNum)
                {
                    case 0:
                        nextStage = GOING_TO_MALL;
                        break;
                    case 1:
                        nextStage = GOING_TO_BUS_STOP;
                        break;
                    case 2:
                        nextStage = GOING_TO_HOME;
                        break;
                }
                
            default:
                setText("Error parsing button. Next text is unavailable, text #" + nextStage);
                break;
            //case copy (delete if not in use)
            //      case 4:
            //   setText("");
            //   nextStage = 5;
            //   break;
        }
    }

    public void passTime()
    {
        offsetTime(3);
        offsetBladder(4.5);
        offsetBelly(-4.5);
        if (belly != 0)
            if (belly > 3)
                offsetBladder(2);
            else
            {
                offsetBladder(belly);
                emptyBelly();
            }

        if (min >= 88)
        {
            setText("You hear the bell finally ring.");
            nextStage = CLASS_OVER;
        }
        if (testWet())
            nextStage = ACCIDENT;
        
        for (int i = 0; i < 3; i++)
            decaySphPower();
        
        //Sphincter power rounding
//        BigDecimal bd = new BigDecimal(sphincterPower);
//        bd = bd.setScale(1, RoundingMode.HALF_UP);
//        sphincterPower = bd.floatValue();
        lblSphPower.setText("Pee holding ability: " + Math.round(sphincterPower) + "%");
        //Dryness rounding
//        bd = new BigDecimal(dryness);
//        bd = bd.setScale(1, RoundingMode.HALF_UP);
//        dryness = bd.floatValue();
        lblDryness.setText("Clothes dryness: " + Math.round(dryness));
    }

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
            nextStage = ACCIDENT;
        
        for (int i = 0; i < time; i++)
        {
            decaySphPower();
            if (belly != 0)
                if (belly > 3)
                    offsetBladder(2);
                else
                {
                    offsetBladder(belly);
                    emptyBelly();
                }
        }
        
        //Sphincter power rounding
//        BigDecimal bd = new BigDecimal(sphincterPower);
//        bd = bd.setScale(1, RoundingMode.HALF_UP);
//        sphincterPower = bd.floatValue();
        lblSphPower.setText("Pee holding ability: " + Math.round(sphincterPower) + "%");
        //Dryness rounding
//        bd = new BigDecimal(dryness);
//        bd = bd.setScale(1, RoundingMode.HALF_UP);
//        dryness = bd.floatValue();
        lblDryness.setText("Clothes dryness: " + Math.round(dryness));
    }

    public boolean testWet()
    {
        //If bladder is filled more than 130 points in the normal mode and 100 points in the hardcore mode, forcing wetting
        if (bladder >= maxBladder & !hardcore)
            return true;
        else
            //If bladder is filled more than 100 points in the normal mode and 50 points in the hardcore mode
            if ((bladder > maxBladder - 30 & !hardcore) | (bladder > maxBladder - 20 & hardcore))
            {
//                double wetChance = bladder + (hardcore ? 30 : 10) + embarassment;
//                double wetLimit = generator.nextInt(80);
//                if (wetChance > wetLimit)
//                {
//                    return true;
//                }
                if(!hardcore)
                {
                    byte wetChance = (byte)(3 * (bladder - 100));
                    if(generator.nextInt(100)<wetChance)
                        return true;
                }
                else
                {
                    byte wetChance = (byte)(5 * (bladder - 80));
                    if(generator.nextInt(100)<wetChance)
                        return true;
                }
            }
        return false;
    }


    public void emptyBladder()
    {
        bladder = 0;
        lblBladder.setText("Bladder: " + (int) bladder + "%");
    }

    public void offsetBladder(double amount)
    {
        bladder += amount/* * incon*/;
        lblBladder.setText("Bladder: " + (int) bladder + "%");
    }

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
        lblBelly.setText("Belly: " + belly + "%");
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

        if (drain & (min % 15) == 0)
        {
            emptyBladder();
        }
    }

    public void decaySphPower()
    {
        //Clothes drying over time
        if (dryness < lower.getAbsorption() + undies.getAbsorption())
            dryness += lower.getDryingOverTime() + undies.getDryingOverTime();

        if (dryness > lower.getAbsorption() + undies.getAbsorption())
            dryness = lower.getAbsorption() + undies.getAbsorption();

        sphincterPower -= bladder / 30;//TODO: Balance this
        if (sphincterPower < 0)
        {
            dryness += sphincterPower; //Decreasing dryness
            bladder += sphincterPower; //Decreasing bladder level
            sphincterPower = 0;
            if (dryness > 0)
            {
                //Naked
                if (lower.getName().equals("Без верхней одежды") && undies.getName().equals("Без нижней одежды"))
                    setText("You feel as the urine is passing outside...",
                            "You're about to pee! You must stop it!");
                else
                    //Outerwear
                    if (!lower.getName().equals("Без верхней одежды"))
                        setText("You see the wet spot on your " + lower.insert() + "!",
                                "You're about to pee! You must stop it!");
                    else
                        //Underwear
                        if (!undies.getName().equals("Без нижней одежды"))
                            setText("You see the wet spot on your " + undies.insert() + "!",
                                    "You're about to pee! You must stop it!");
            }

            if (dryness < -20)
            {
                if (lower.getName().equals("Без верхней одежды") && undies.getName().equals("Без нижней одежды"))
                    if (cornered)
                    {
                        setText("You see the puddle on the floor under you! You're peeing!",
                                "It's too big...");
                        nextStage = ACCIDENT;
                        handleNextClicked();
                    } else
                    {
                        setText("You see the puddle on your chair! You're peeing!",
                                "It's too big...");
                        nextStage = ACCIDENT;
                        handleNextClicked();
                    }
                else
                    if (!lower.getName().equals("Без верхней одежды"))
                    {
                        setText("You see the wet spot on your " + lower.insert() + "!",
                                "It's too big...");
                        nextStage = ACCIDENT;
                        handleNextClicked();
                    } else
                        if (!undies.getName().equals("Без нижней одежды"))
                        {
                            setText("You see the wet spot on your " + undies.insert() + "!",
                                    "It's too big...");
                            nextStage = ACCIDENT;
                            handleNextClicked();
                        }
            }
        }
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
            default:
                System.err.println("score() method used incorrectly, message: \"" + message + "\"");
        }
    }

    public void score(String message, char mode, float points)
    {
        switch (mode)
        {
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
        LEAVE_BED, //Home
        LEAVE_HOME, //Home
        GO_TO_CLASS, //Home -> school
        WALK_IN, //School
        SIT_DOWN, //School
        ASK_ACTION, //School, waiting for player to select an action
        CHOSE_ACTION, //TODO: Rework
        ASK_TO_PEE, //School, asking a teacher to pee
        CALLED_ON, //School, teacher asked character a question
        CAUGHT, //School, classmates have spotted that character has to pee (hardcore only)
        USE_BOTTLE, //School, cheat, peeing in a bottle
        ASK_CHEAT, //School, waiting for player to select a cheat 
        CHOSE_CHEAT, //TODO: Rework
        CLASS_OVER, //School, class over
        AFTER_CLASS, //School, writing lines if character has failed to ask teacher to go to the restroom for three times
        ACCIDENT, //School, leaking
        ACCIDENT_STREET, //Street, leaking
        ACCIDENT_BUS, //Bus, leaking
        ACCIDENT_HOME, //Home, leaking
        GIVE_UP,
        WET, //School, wetting
        WET_STREET, //Street, wetting
        WET_BUS, //Bus, wetting
        WET_HOME, //Home, wetting
        POST_WET, //School, being made fun of
        POST_BUS, //Bus, passangers around can't understand how did character pee in a bus
        POST_STREET, //Street, 
        GAME_OVER,
        SURPRISE, //Hidden gameplay after passing through the class in the hardcore mode
        SURPRISE_2, 
        SURPRISE_ACCIDENT,
        SURPRISE_DIALOGUE,
        SURPRISE_CHOSE,
        HIT,
        PERSUADE,
        SURPRISE_WET_VOLUNTARY,
        SURPRISE_WET_VOLUNTARY2,
        SURPRISE_WET_PRESSURE, //End of the hidden gameplay stages 
        WHERE_TO_GO, //Asking the player where to go after the lessons if all restrooms are locked
        GOING_TO_MALL, //Street, going to the mall to find a restroom (5 minutes)
        GOING_TO_BUS_STOP, //Street, going to the bus stop (1 minute, then waiting for a bus for 3 - 20 minutes)
        GOING_TO_HOME, //Street, going to home on foot (15 minutes)
        HOME, //Made it to home
        HOME_LOST_KEYS, //Forgot keys at school (chance 12%)
        BACK_TO_SCHOOL, //Going back to school on foot (15 minutes)
        BACK_TO_SCHOOL_BUS_STOP, //Waiting for a bus to school (3 - 15 minutes)
        IN_BUS, //In a bus (3 minutes, 5% chance to get stuck in traffic (5 - 25 minutes)
        END_GAME, //Good ending, showing score
        WHERE_TO_GO_CHOSE
    }

    enum Gender
    {
        MALE, FEMALE
    }
}