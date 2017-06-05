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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
class Wear implements Serializable
{

    static String[] colorList =
    {
        "Чёрный", "Серый", "Красный", "Оранжевый", "Жёлтый", "Зелёный", "Синий", "Тёмно-синий", "Фиолетовый", "Розовый"
    };
    private static final long serialVersionUID = 1L;

    private final String name;
    private String insertName;
    private final float pressure;
    private final float absorption;
    private final float dryingOverTime;
    private String color;
    byte число;//Единственное или множественное
    String названиеВВинительномПадеже;
    private WearType type;
	
    /**
     *
     * @param name           the wear name (e. g. "Regular panties")
     * @param insertName
     * @param pressure       the pressure of an wear.<br>1 point of a pressure
     *                       takes 1 point from the maximal bladder capacity.
     * @param absorption     the absorption of an wear.<br>1 point of an
     *                       absorption can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     *                       3 minutes
     * @param type
     */
    Wear(String name, String insertName, String винительныйПадеж, float pressure, float absorption, float dryingOverTime, byte число)
    {
        this.name = name;
        this.insertName = insertName;
        this.названиеВВинительномПадеже = винительныйПадеж;
        this.pressure = pressure;
        this.absorption = absorption;
        this.dryingOverTime = dryingOverTime;
        this.число = число;
    }
    
    /**
     *
     * @param name           the wear name (e. g. "Regular panties")
     * @param insertName
     * @param pressure       the pressure of an wear.<br>1 point of a pressure
     *                       takes 1 point from the maximal bladder capacity.
     * @param absorption     the absorption of an wear.<br>1 point of an
     *                       absorption can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     *                       3 minutes
     * @param type
     */
    Wear(String name, String insertName, String винительныйПадеж, float pressure, float absorption, float dryingOverTime, WearType type, byte число)
    {
        this.name = name;
        this.insertName = insertName;
        this.названиеВВинительномПадеже = винительныйПадеж;
        this.pressure = pressure;
        this.absorption = absorption;
        this.dryingOverTime = dryingOverTime;
        this.число = число;
        this.type = type;
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
        if (число == 1)
        {
            this.color = color;
        }
        else
        {
            try
            {
                this.color = color.subSequence(0, color.length() - 1) + "е";
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
            }
        }
        if(this.name.contains("юбка"))
           try
            {
                this.color = color.subSequence(0, color.length() - 2) + ((!color.equals("Синяя")&&!color.equals("Тёмно-синяя")) ? "яя" : "ая");
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
            } 
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
        UNDERWEAR,OUTERWEAR,BOTH_SUITABLE
    }
}

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
        if (newValues)
        {
            new setupFramePre().setVisible(true);
        }
        else
        {
            new ALongHourAndAHalf(nameParam, gndrParam, diffParam, incParam, bladderParam, underParam, outerParam, underColorParam, outerColorParam);
        }
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
                new Wear("Случайно", "ЭТО ОШИБКА", "ЭТО ОШИБКА", 0, 0, 0, (byte)1),
                new Wear("Без нижней одежды", "ЭТО ОШИБКА", "ЭТО ОШИБКА", 0, 0, 1, (byte)1),
                new Wear("Стринги", "стринги", "стринги", 1, 2, 1, (byte)2),
                new Wear("Трусики \"Танга\"", "трусики", "трусики", 1.5F, 3, 1, (byte)2),
                new Wear("Обычные трусики", "трусики", "трусики", 2, 4, 1, (byte)2),
                new Wear("Трусики с закрытыми бёдрами", "трусики", "трусики", 4, 7, 1, (byte)2),
                new Wear("Бикини-стринги", "трусики бикини", "трусики бикини", 1, 1, 2, (byte)2),
                new Wear("Обычные бикини", "трусики бикини", "трусики бикини", 2, 2, 2, (byte)2),
                new Wear("Купальник", "купальник", "купальник", 4, 2.5F, 2.5F, (byte)1),
                new Wear("Лёгкий подгузник", "подгузник", "подгузник", 4.5F, 10, 0, (byte)1),
                new Wear("Средний подгузник", "подгузник", "подгузник", 9, 20, 0, (byte)1),
                new Wear("Большой подгузник", "подгузник", "подгузник", 18, 30, 0, (byte)1),
                new Wear("Лёгкая прокладка", "прокладка", "прокладку", 1, 8, 0.25F, (byte)1),
                new Wear("Обычная прокладка", "прокладка", "прокладку", 1.5F, 12, 0.25F, (byte)1),
                new Wear("Большая прокладка", "прокладка", "прокладку", 2, 16, 0.25F, (byte)1),
                new Wear("Трусы", "трусы", "трусы", 2.5F, 5, 1, (byte)2),
                new Wear("Трусы с закрытыми бёдрами", "трусы", "трусы", 3.75F, 7.5F, 1, (byte)2),
                new Wear("Анти-гравитационные трусы", "трусы", "трусы", 0, 4, 1, (byte)2),
                new Wear("Гипервпитывающий подгузник", "подгузник", "трусы", 18, 200, 0, (byte)1)
            };

    Wear[] outerwearList
            =
            {
                new Wear("Случайно", "ЭТО ОШИБКА", "ЭТО ОШИБКА", 0, 0, 0, (byte)1),
                new Wear("Без верхней одежды", "ЭТО ОШИБКА", "ЭТО ОШИБКА", 0, 0, 1, (byte)1),
                new Wear("Обычные джинсы", "джинсы", "джинсы", 7, 12, 1.2F, (byte)2),
                new Wear("Джинсы по колено", "джинсы", "джинсы", 6, 10, 1.2F, (byte)2),
                new Wear("Джинсовые шорты", "шорты", "шорты", 5, 8.5F, 1.2F, (byte)2),
                new Wear("Джинсовые мини-шорты", "мини-шорты", "мини-шорты", 4, 7, 1.2F, (byte)2),
                new Wear("Обычные брюки", "брюки", "брюки", 9, 15.75F, 1.4F, (byte)2),
                new Wear("Брюки по колено", "брюки", "брюки", 8, 14, 1.4F, (byte)2),
                new Wear("Шорты", "шорты", "шорты", 7, 12.25F, 1.4F, (byte)2),
                new Wear("Мини-шорты", "мини-шорты", "мини-шорты", 6, 10.5F, 1.4F, (byte)2),
                new Wear("Длинная юбка", "юбка", "юбку", 5, 6, 1.7F, (byte)1),
                new Wear("Обычная юбка", "юбка", "юбку", 4, 4.8F, 1.7F, (byte)1),
                new Wear("Короткая юбка", "юбка", "юбку", 3, 3.6F, 1.7F, (byte)1),
                new Wear("Мини-юбка", "мини-юбка", "мини-юбку", 2, 2.4F, 1.7F, (byte)1),
                new Wear("Микро-юбка", "микро-юбка", "микро-юбку", 1, 1.2F, 1.7F, (byte)1),
                new Wear("Длинная юбка с колготками", "юбка с колготками", "юбку с колготками", 6, 7.5F, 1.6F, (byte)1),
                new Wear("Обычная юбка с колготками", "юбка с колготками", "юбку с колготками", 5, 8.75F, 1.6F, (byte)1),
                new Wear("Короткая юбка с колготками", "юбка с колготками", "юбку с колготками", 4, 7, 1.6F, (byte)1),
                new Wear("Мини-юбка с колготками", "мини-юбка с колготками", "мини-юбку с колготками", 3, 5.25F, 1.6F, (byte)1),
                new Wear("Микро-юбка с колготками", "микро-юбка с колготками", "микро-юбку с колготками", 2, 3.5F, 1.6F, (byte)1),
                new Wear("Леггинсы", "леггинсы", "леггинсы", 10, 11, 1.8F, (byte)2),
                new Wear("Мужские джинсовые шорты", "шорты", "шорты", 5, 8.5F, 1.2F, (byte)2),
                new Wear("Мужские джинсы", "джинсы", "джинсы", 7, 12, 1.2F, (byte)2),
                new Wear("Мужские брюки", "брюки", "брюки", 9, 15.75F, 1.4F, (byte)2)
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
                "Артём",
                "Олег",
                "Андрей",
                "Дима",
                "Матвей"
            };
    
    String namesРодительный[]
            =
            {
                "Данила",
                "Артёма",
                "Олега",
                "Андрея",
                "Димы",
                "Матвея"
            };
    
    String namesВинительный[]
            =
            {
                "Данила",
                "Артёма",
                "Олега",
                "Андрея",
                "Диму",
                "Матвея"
            };
    
    String namesДательный[]
            =
            {
                "Данилу",
                "Артёму",
                "Олегу",
                "Андрею",
                "Диме",
                "Матвею"
            };
    
    byte index = (byte)generator.nextInt(names.length);
    
    String boyName = names[index];
    String boyNameРодительный = namesРодительный[index];
    String boyNameВинительный = namesВинительный[index];
    String boyNameДательный = namesДательный[index];

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
    public String scoreText = "";

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
    private final JProgressBar sphincterBar;
    private final JProgressBar drynessBar;
    private final JProgressBar timeBar;

    JFileChooser fc;
    private final JProgressBar bladderBar;

    /**
     * Launch the application.
     *
     * @param name        the name of a character
     * @param gndr        the gender of a character
     * @param diff        the game difficulty - Normal or Hardcore
     * @param bladder     the bladder fullness at start
     * @param under       the underwear
     * @param outer       the outerwear
     * @param inc         the incontinence
     * @param undiesColor the underwear color
     * @param lowerColor  the outerwear color
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
            name += " [Сложный режим]";
        }

        //Assigning the boy's name
        boyName = names[generator.nextInt(names.length)];

        //Setting up custom wear file chooser
        this.fc = new JFileChooser();
//        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
                return extension.equals("lhhruswear");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Custom wear";
            }
        });

        if (under.equals("Пользовательская"))
        {
            fc.setDialogTitle("Открыть файл нижней одежды");
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    undies = (Wear) ois.readObject();
                    if(undies.getType()==OUTERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "Это не нижняя одежда.", "Неверный вид одежды", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        setupFramePre.main(new String[0]); 
                    }
                } catch (IOException | ClassNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "Ошибка при работе с файлом.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    setupFramePre.main(new String[0]);
                }
            }
        }

        //If random undies were chosen...
        if (under.equals("Случайно"))
        {
            undies = underwearList[generator.nextInt(underwearList.length)];
            while (undies.getName().equals("Случайно"))
            //...selecting random undies from the undies array.
            {
                undies = underwearList[generator.nextInt(underwearList.length)];
            }
            //If random undies weren't chosen...
        }
        else
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
        if (!undies.getName().equals("Без нижней одежды"))
        {
            if (!undiesColor.equals("Случайно"))
            {
                undies.setColor(undiesColor);
            }
            else
            {
                undies.setColor(Wear.colorList[generator.nextInt(Wear.colorList.length)]);
            }
        }
        else
        {
            undies.setColor("");
        }
        if (outer.equals("Пользовательская"))
        {
            fc.setDialogTitle("Открыть файл верхней одежды");
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
                try
                {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    lower = (Wear) ois.readObject();
                    if(lower.getType()==UNDERWEAR)
                    {
                        JOptionPane.showMessageDialog(null, "Это не верхняя одежда.", "Неверный вид одежды", JOptionPane.ERROR_MESSAGE);
                        dispose();
                        setupFramePre.main(new String[0]); 
                    }
                } catch (IOException | ClassNotFoundException e)
                {
                    JOptionPane.showMessageDialog(null, "Ошибка при работе с файлом.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    setupFramePre.main(new String[0]);
                }
            }
        }

        //Same with the lower clothes
        if (outer.equals("Случайно"))
        {
            lower = outerwearList[generator.nextInt(outerwearList.length)];
            while (lower.getName().equals("Случайно"))
            {
                lower = outerwearList[generator.nextInt(outerwearList.length)];
            }
        }
        else
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
        if (!lower.getName().equals("Без верхней одежды"))
        {
            if (!lowerColor.equals("Случайно"))
            {
                lower.setColor(lowerColor);
            }
            else
            {
                lower.setColor(Wear.colorList[generator.nextInt(Wear.colorList.length)]);
            }
        }
        else
        {
            lower.setColor("");
        }

        //Calculating dryness and maximal bladder capacity values
        dryness = lower.getAbsorption() + undies.getAbsorption();
        maxBladder -= lower.getPressure() + undies.getPressure();

        if (isMale())
        {
            underwearList[1].setInsertName("член");
        }
        else
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
        contentPane= new JPanel();
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

        btnNext.setBounds((textPanel.getWidth() / 2) + 25, 460, 280, 43);
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
        btnQuit.setBounds((textPanel.getWidth() / 2) - 110, 480, 89, 23);
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
        btnNewGame.setBounds((textPanel.getWidth() / 2) - 210, 480, 100, 23);
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
        lblEmbarassment = new JLabel("Смущение: " + Math.round(embarassment));
        lblEmbarassment.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmbarassment.setBounds(20, 240, 200, 32);
        contentPane.add(lblEmbarassment);

        //Belly label setup
        lblBelly = new JLabel("Вода в животе: " + Math.round(belly) + "%");
        lblBelly.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBelly.setBounds(20, 270, 200, 32);
        contentPane.add(lblBelly);

        //Incontinence label setup
        lblIncon = new JLabel("Инконтиненция: " + inc + "x");
        lblIncon.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblIncon.setBounds(20, 300, 200, 32);
        contentPane.add(lblIncon);

        //Time label setup
        lblMinutes = new JLabel("Время: " + min + " минут(ы) из " + (stay ? "120" : "90"));
        lblMinutes.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMinutes.setBounds(20, 330, 200, 32);
        lblMinutes.setVisible(false);
        contentPane.add(lblMinutes);

        //Sphincter power label setup
        lblSphPower = new JLabel("Способность терпеть: " + Math.round(sphincterPower) + "%");
        lblSphPower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSphPower.setBounds(20, 360, 250, 32);
        lblSphPower.setVisible(false);
        contentPane.add(lblSphPower);

        //Clothing dryness label setup
        lblDryness = new JLabel("Сухость одежды: " + Math.round(dryness));
        lblDryness.setToolTipText("0 = конец игры");
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
        add(bladderBar);

        //Sphincter bar setup
        sphincterBar = new JProgressBar();
        sphincterBar.setBounds(16, 362, 300, 25);
        sphincterBar.setMaximum((int)Math.round(maxSphincterPower));
        sphincterBar.setValue((int)Math.round(sphincterPower));
        sphincterBar.setVisible(false);
        add(sphincterBar);
        
        //Dryness bar setup
        drynessBar = new JProgressBar();
        drynessBar.setBounds(16, 392, 300, 25);
        drynessBar.setMaximum((int)dryness);
        drynessBar.setValue((int)dryness);
        drynessBar.setMinimum(0);
        drynessBar.setVisible(false);
        add(drynessBar);
        
        //Time bar setup
        timeBar = new JProgressBar();
        timeBar.setBounds(16, 332, 300, 25);
        timeBar.setMaximum(90);
        timeBar.setValue(min);
        timeBar.setVisible(false);
        add(timeBar);
        
//        pack();
        setVisible(true);

        //Starting the game
        nextStage = LEAVE_BED;
        handleNextClicked();
    }

    /**
     * @return TRUE - if character's gender is female<br>FALSE - if character's
     *         gender is male
     */
    public boolean isFemale()
    {
        return gender == FEMALE;
    }

    /**
     * @return TRUE - if character's gender is male<br>FALSE - if character's
     *         gender is female
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
                }
                else
                {
                    lblName.setForeground(Color.CYAN);
                }

                //Assigning the blank name if player didn't selected the name
                if (name.isEmpty())
                {
                    if (isFemale())
                    {
                        name = "Миссис Никто";
                    }
                    else
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
                    score("Сложный режим", '*', 2F);
                }

                setLinesAsDialogue(1);
                if (!lower.getName().equals("Без верхней одежды"))
                {
                    if (!undies.getName().equals("Без нижней одежды"))
                    //Both lower clothes and undies
                    {
                        if (isFemale())
                        {
                            setText("Ч-что? Я-я забыла завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + undies.названиеВВинительномПадеже + " и " + lower.названиеВВинительномПадеже + ",",
                                    "даже не заботясь о том, что надеть на тело.");
                        }
                        else
                        {
                            setText("Ч-что? Я-я забыл завести будильник?!",
                                    "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                    "Второпясь надеваешь " + undies.названиеВВинительномПадеже + " и " + lower.названиеВВинительномПадеже + ",",
                                    "даже не заботясь о том, что надеть на тело.");
                        }
                    }
                    else //Lower clothes only
                    if (isFemale())
                    {
                        setText("Ч-что? Я-я забыла завести будильник?!",
                                "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                "Второпясь надеваешь " + lower.названиеВВинительномПадеже + ",",
                                "даже не заботясь о том, что надеть на тело.");
                    }
                    else
                    {
                        setText("Ч-что? Я-я забыл завести будильник?!",
                                "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                "Второпясь надеваешь " + lower.названиеВВинительномПадеже + ",",
                                "даже не заботясь о том, что надеть на тело.");
                    }
                }
                else if (!undies.getName().equals("Без нижней одежды"))
                //Undies only
                {
                    if (isFemale())
                    {
                        setText("Ч-что? Я-я забыла завести будильник?!",
                                "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                "Второпясь надеваешь " + lower.названиеВВинительномПадеже + ", каким-то образом забыв надеть верхнюю одежду,",
                                "даже не заботясь о том, что надеть на тело.");
                    }
                    else
                    {
                        setText("Ч-что? Я-я забыл завести будильник?!",
                                "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться.",
                                "Второпясь надеваешь " + lower.названиеВВинительномПадеже + ", каким-то образом забыв надеть верхнюю одежду,",
                                "даже не заботясь о том, что надеть на тело.");
                    }
                }
                else //No clothes at all
                if (isFemale())
                {
                    setText("Ч-что? Я-я забыла завести будильник?!",
                            "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться,",
                            "И решаешь пойти в школу... Полностью голышом.");
                }
                else
                {
                    setText("Ч-что? Я-я забыл завести будильник?!",
                            "С крайне плохим настроением ты выпрыгиваешь из постели и сразу же начинаешь собираться,",
                            "И решаешь пойти в школу... Полностью голышом.");
                }
                offsetBladder(.5);
                nextStage = LEAVE_HOME;
                break;

            case LEAVE_HOME:
                if (isFemale())
                {
                    setText("Взглянув на часы, ты никак не можешь понять, почему же ты забыла завести вчера будильник.",
                            "",
                            "Не обращая внимания на ежедневную суету, ты залпом выпиваешь стакан сока и выбегаешь на улицу.");
                }
                else
                {
                    setText("Взглянув на часы, ты никак не можешь понять, почему же ты забыл завести вчера будильник.",
                            "",
                            "Не обращая внимания на ежедневную суету, ты залпом выпиваешь стакан сока и выбегаешь на улицу.");
                }
                offsetBladder(.5);
                offsetEmbarassment(3);
                offsetBelly(10);
                nextStage = GO_TO_CLASS;
                break;

            case GO_TO_CLASS:
                lblMinutes.setVisible(true);
                lblSphPower.setVisible(true);
                lblDryness.setVisible(true);
                sphincterBar.setVisible(true);
                drynessBar.setVisible(true);
                timeBar.setVisible(true);

                if (!lower.getName().equals("Без верхней одежды"))
                {
                    if (lower.insert().contains("юбка"))
                    {
                        if(undies.число == 1)
                            setText("Ты вбегаешь в класс так быстро, что твоя " + lower.insert() + " вздувается ветром.",
                                    "",
                                    "Если бы не второпях, ты бы пожалела о том, что кто-то смог бы увидеть твой " + undies.названиеВВинительномПадеже + "под юбкой.",
                                    "Не привлекая особого внимания, садишься на своё место.");
                        else
                            setText("Ты вбегаешь в класс так быстро, что твоя " + lower.insert() + " вздувается ветром.",
                                    "",
                                    "Если бы не второпях, ты бы пожалела о том, что кто-то смог бы увидеть твои " + undies.названиеВВинительномПадеже + "под юбкой.",
                                    "Не привлекая особого внимания, садишься на своё место.");
                    }
                    else
                    {
                        setText("Быстро вбегая в класс и",
                                "не привлекая особого внимания, ты садишься на своё место.");
                    }
                }
                else if (!undies.getName().equals("Без нижней одежды"))
                {
                    if(undies.число == 1)
                        setText("Ты вбегаешь в класс, и твои одноклассники сразу же с крайним удивлением обратили внимание на твой " + undies.названиеВВинительномПадеже + ".",
                                "Ты садишься на место под взгядами всего класса.");
                    else
                        setText("Ты вбегаешь в класс, и твои одноклассники сразу же с крайним удивлением обратили внимание на твои " + undies.названиеВВинительномПадеже + ".",
                                "Ты садишься на место под взгядами всего класса.");
                }
                else if (isFemale())
                {
                    setLinesAsDialogue(3, 4);
                    setText("Ты вбегаешь в класс, и твои одноклассники, особенно мальчики, сразу же с крайним удивлением обратили внимание на твои женские прелести.",
                            "По классу слышен смущенный говор твоих однокласскиков.",
                            "Чего?",
                            "С ума сойти!!",
                            "Ты садишься на место под взгядами всего класса.");
                }
                else
                {
                    setLinesAsDialogue(3, 4);
                    setText("Ты вбегаешь в класс, и твои одноклассники, особенно девочки, сразу же с крайним удивлением обратили внимание на твои мужские прелести.",
                        "По классу слышен смущенный говор твоих однокласскиков.",
                        "Чего?",
                        "С ума сойти!!",
                        "Ты садишься на место под взгядами всего класса.");
                }
                

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
                }
                else if (lower.getName().equals("Без верхней одежды"))
                {
                    setLinesAsDialogue(1);
                    if (isFemale())
                    {
                        setText("ЧЕГО!? ТЫ ПРИШЛА В ШКОЛУ ГОЛОЙ!?",
                                "не может поверить своим глазам учитель.",
                                "Как и твои одноклассники.");
                    }
                    else
                    {
                        setText("ЧЕГО!? ТЫ ПРИШЁЛ В ШКОЛУ ГОЛЫМ!?",
                                "не может поверить своим глазам учитель.",
                                "Как и твои одноклассники.");
                    }
                    offsetEmbarassment(25);
                }
                else
                {
                    setLinesAsDialogue(1, 3);
                    setText("Садись на место, " + name + ". Ты опоздала.",
                            "говорит учитель,",
                            "И больше не вламывайся в класс!");
                }
                nextStage = SIT_DOWN;
                break;

            case SIT_DOWN:
                setText("Невольно сжимая ноги, ты чуствуешь этот дискомфорт,",
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
                if (bladder <= 20)
                {
                    setText("Совершенно не вдаваясь в урок,",
                            "ты сидишь и скучаешь, поглядывая на часы.");
                }
                //Bladder: 20-40
                if (bladder > 20 && bladder <= 40)
                {
                    setText("Чуствуя слабые позывы пописать,",
                            "ты смотришь на часы, про себя умоляя время идти быстрее.");
                }
                //Bladder: 40-60
                if (bladder > 40 && bladder <= 60)
                {
                    setText("Отчётливо желая посетить уборную,",
                            "ты нетерпеливо ждёшь конца урока, закинув ногу на ногу, чтобы ослабить нужду.");
                }
                //Bladder: 60-80
                if (bladder > 60 && bladder <= 80)
                {
                    setLinesAsDialogue(3);
                    setText("Ты чуствуешь, как твой мочевой пузырь буквально надувается как воздушный шарик.",
                            "Ты уже очень сильно желаешь пописать.",
                            "Может быть попросить учителя выйти? Терпеть уже очень неприятно...");
                }
                //Bladder: 80-100
                if (bladder > 80 && bladder <= 100)
                {
                    setLinesAsDialogue(3);
                    setText("Держать в себе всю мочу очень скоро станет невозможным.",
                            "Чувствуя тупую боль в мочевом пузыре, ты хочешь писать как никогда раньше.",
                            "Ай!.. Нужно срочно что-то делать, иначе...");
                }
                //Bladder: 100-130
                if (bladder > 100 && bladder <= 130)
                {
                    setLinesAsDialogue(1, 3);
                    if (isFemale())
                    {
                        setText("Это какой-то кошмар... пусть уж это будет сном...",
                                "Ты не знаешь, сможешь ли ты продержаться до конца урока и о ужас,",
                                "Мочевой пузырь выпирает уже выше пупка.",
                                "Ааааа... Я не могу больше терпеть!!!",
                                "Даже палец, перекрывающий уретру, уже не помогает облегчать твои страдания.");
                    }
                    else
                    {
                        setText("Это какой-то кошмар... пусть уж это будет сном...",
                                "Ты не знаешь, сможешь ли ты продержаться до конца урока и о ужас,",
                                "Мочевой пузырь выпирает уже выше пупка.",
                                "Ааааа... Я не могу больше терпеть!!!",
                                "Даже эрекция уже не помогает облегчать твои страдания.");
                    }
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
                    }
                    else
                    {
                        actionList.add("Сжать член");
                    }
                }
                else
                {
                    actionList.add("[Недоступно]");
                }

                actionList.add("Сжать ноги");

                if (bladder >= 100)
                {
                    actionList.add("Сдаться и описаться");
                }
                else
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
                    if (isFemale())
                    {
                        setText("Ты прождала пару минут, ничего не делая.");
                    }
                    else
                    {
                        setText("Ты прождал пару минут, ничего не делая.");
                    }
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
                                "ты сжимаешь свою ладонь между ног.",
                                "Стало немного легче.");

                        rechargeSphPower(20);
                        offsetTime(3);

                        if (generator.nextInt(100) <= 15 + classmatesAwareness & hardcore)
                        {
                            nextStage = CAUGHT;
                        }
                        else
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
                        setText("Хоть ты и хочешь писать,",
                                "ты не можешь себе позволить, чтобы кто-нибудь увидел тебя",
                                "с рукой между ног. Ты поджимаешь ноги, но это не сильно помогает.");

                        rechargeSphPower(2);
                        offsetTime(3);

                        if (generator.nextInt(100) <= 3 + classmatesAwareness & hardcore)
                        {
                            nextStage = CAUGHT;
                        }
                        else
                        {
                            nextStage = ASK_ACTION;
                        }
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
                        }
                        catch (NumberFormatException | NullPointerException e)
                        {
                            nextStage = ASK_ACTION;
                            break;
                        }

                        passTime((byte)time);
                        
                        if (generator.nextInt(100) <= 1 + classmatesAwareness & hardcore)
                        {
                            nextStage = CAUGHT;

                        }
                        else
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
                        if (isFemale())
                        {
                            setText("Ты просидела несколько минут, ничего не делая.");
                        }
                        else
                        {
                            setText("Ты просидел несколько минут, ничего не делая.");
                        }
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
                            setLinesAsDialogue(1, 2);
                            if (!lower.getName().equals("Без верхней одежды"))
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    setText("Можно выйти?",
                                            "Да, конечно,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                                else
                                {
                                    setText("Можно выйти?",
                                            "Да, конечно,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                            }
                            else if (!undies.getName().equals("Без нижней одежды"))
                            {
                                setText("Можно выйти?",
                                        "Да, конечно,",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты стягиваешь " + undies.названиеВВинительномПадеже,
                                        "и начинаешь писать.");
                            }
                            else
                            {
                                setText("Можно выйти?",
                                        "Да, конечно,",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты заходишь в кабинку",
                                        "и начинаешь писать.");
                            }
//                            score *= 0.2;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -80% of points");
                            score("Использование туалета во время урока", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        }
                        else
                        {
                            setLinesAsDialogue(1,2);
                            setText("Можно выйти?",
                                    "Нет, директор запретил ученикам выходить во время урока.",
                                    "ответил учитель.");
                            timesPeeDenied++;
                        }
                        break;

                    case 1:
                        if (generator.nextInt(100) <= 10 & !hardcore)
                        {
                            setLinesAsDialogue(1, 2);
                            if (!lower.getName().equals("Без верхней одежды"))
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    setText("Можно выйти?",
                                            "Ладно, выйди",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                                else
                                {
                                    setText("Можно выйти?",
                                            "Ладно, выйди,",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                            }
                            else if (!undies.getName().equals("Без нижней одежды"))
                            {
                                setText("Можно выйти?",
                                        "Ладно, выйди,",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты стягиваешь " + undies.названиеВВинительномПадеже,
                                        "и начинаешь писать.");
                            }
                            else
                            {
                                setText("Можно выйти?",
                                        "Ладно, выйди,",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты заходишь в кабинку",
                                        "и начинаешь писать.");
                            }
//                            score *= 0.22;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -78% of points");
                            score("", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        }
                        else
                        {
                            setLinesAsDialogue(1,2);
                            if (isFemale())
                            {
                                setText("Можно выйти?",
                                        "Нет! " + name + ", чего ты такая непонятливая? Было сказано, директор не разрешает!",
                                        "отказал учитель.");
                            }
                            else
                            {
                                setText("Можно выйти?",
                                        "Нет! " + name + ", чего ты такой непонятливый? Было сказано, директор не разрешает!",
                                        "отказал учитель.");
                            }
                            timesPeeDenied++;
                        }
                        break;

                    case 2:
                        setLinesAsDialogue(1, 2);
                        if (generator.nextInt(100) <= 30 & !hardcore)
                        {
                            if (!lower.getName().equals("Без верхней одежды"))
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    if (isFemale())
                                    {
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такая надоедливая, " + name + "!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже,
                                                "и начинаешь писать.");
                                    }
                                    else
                                    {
                                        setText("Можно выйти, пожалуйста?",
                                                "Хорошо, хорошо. Ты такой надоедливый, " + name + "!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже,
                                                "и начинаешь писать.");
                                    }
                                }
                                else if (isFemale())
                                {
                                    setText("Можно выйти, пожалуйста?",
                                            "Хорошо, хорошо. Ты такая надоедливая, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                                else
                                {
                                    setText("Можно выйти, пожалуйста?",
                                            "Хорошо, хорошо. Ты такой надоедливый, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                            }
                            else if (!undies.getName().equals("Без нижней одежды"))
                            {
                                if (isFemale())
                                {
                                    setText("Можно выйти, пожалуйста?",
                                            "Хорошо, хорошо. Ты такая надоедливая, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + undies.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                                else
                                {
                                    setText("Можно выйти, пожалуйста?",
                                            "Хорошо, хорошо. Ты такой надоедливый, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + undies.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                            }
                            else if (isFemale())
                            {
                                setText("Можно выйти, пожалуйста?",
                                        "Хорошо, хорошо. Ты такая надоедливая, " + name + "!",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты заходишь в кабинку",
                                        "и начинаешь писать.");
                            }
                            else
                            {
                                setText("Можно выйти, пожалуйста?",
                                        "Хорошо, хорошо. Ты такой надоедливый, " + name + "!",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты заходишь в кабинку",
                                        "и начинаешь писать.");
                            }
//                            score *= 0.23;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -77% of points");
                            score("Использование туалета во время урока", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        }
                        else
                        {
                            setLinesAsDialogue(1, 2, 3);
                            setText("Можно выйти, пожалуйста?",
                                    "Нет, " + name + "! Кому сказано!",
                                    "Прекрати проситься, или я тебя накажу!");
                            timesPeeDenied++;
                        }
                        break;

                    case 3:
                        if (generator.nextInt(100) <= 7 & !hardcore)
                        {
                            setLinesAsDialogue(1, 2);
                            if (!lower.getName().equals("Без верхней одежды"))
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    if (isFemale())
                                    {
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такая надоедливая, " + name + "!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже,
                                                "и начинаешь писать.");
                                    }
                                    else
                                    {
                                        setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                                "Ладно! Иди уже! Ты такой надоедливый, " + name + "!",
                                                "разрешил учитель. Забежав в туалет,",
                                                "Ты стягиваешь " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже,
                                                "и начинаешь писать.");
                                    }
                                }
                                else if (isFemale())
                                {
                                    setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                            "Ладно! Иди уже! Ты такая надоедливая, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                                else
                                {
                                    setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                            "Ладно! Иди уже! Ты такой надоедливый, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + lower.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                            }
                            else if (!undies.getName().equals("Без нижней одежды"))
                            {
                                if (isFemale())
                                {
                                    setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                            "Ладно! Иди уже! Ты такая надоедливая, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + undies.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                                else
                                {
                                    setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                            "Ладно! Иди уже! Ты такой надоедливый, " + name + "!",
                                            "разрешил учитель. Забежав в туалет,",
                                            "Ты стягиваешь " + undies.названиеВВинительномПадеже,
                                            "и начинаешь писать.");
                                }
                            }
                            else if (isFemale())
                            {
                                setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                        "Ладно! Иди уже! Ты такая надоедливая, " + name + "!",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты заходишь в кабинку",
                                        "и начинаешь писать.");
                            }
                            else
                            {
                                setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                        "Ладно! Иди уже! Ты такой надоедливый, " + name + "!",
                                        "разрешил учитель. Забежав в туалет,",
                                        "Ты заходишь в кабинку",
                                        "и начинаешь писать.");
                            }
//                            score *= 0.3;
//                            scoreText = scoreText.concat("\nИспользование туалета во время урока: -70% of points");
                            score("Использование туалета во время урока", '/', 1.25F);
                            emptyBladder();
                            nextStage = ASK_ACTION;
                        }
                        else if (generator.nextBoolean())
                        {
                            setLinesAsDialogue(1,2);
                            setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                    "НЕТ, НЕТ, НЕТ!!! НЕЛЬЗЯ!!! В УГОЛ!!",
                                    "разозлился учитель.");
                            cornered = true;
//                            score += 1.3 * (90 - min / 3);
//                            scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +" + 1.3 * (90 - min / 3) + " score");
                            if(isFemale())
                                score("Простояла в угле " + (90 - min) + " минут", '+', 1.3F * (90 - min / 3));
                            else
                                score("Простоял в угле " + (90 - min) + " минут", '+', 1.3F * (90 - min / 3));
                            offsetEmbarassment(5);
                        }
                        else
                        {
                            setLinesAsDialogue(1,2);
                            setText("Можно выйти, пожалуйста? Мне очень нужно!! Прошу!",
                                    "НЕТ, НЕТ, НЕТ!!! НЕЛЬЗЯ!!! ОСТАЁШЬСЯ ПОСЛЕ УРОКОВ!!",
                                    "разозлился учитель.");
                            offsetEmbarassment(5);
                            stay = true;
//                            scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
//                            score *= 1.6;
                            if (isFemale())
                            {
                                score("Осталась после урока", '*', 1.6F);
                            }
                            else
                            {
                                score("Остался после урока", '*', 1.6F);
                            }
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
                        timeBar.setMaximum(120);
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
                        nextStage = ASK_ACTION;
                        break;

                    case 4:
                        setText("Учитель, похоже, пожалел тебя. Попробуй попроситься выйти.");
                        timesPeeDenied = 0;
                        stay = false;
                        cornered = false;
                        timeBar.setMaximum(90);
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
                        if(isFemale())
                            setText("Сосед по парте дал тебе какую-то таблетку,",
                                    "и ты её проглотила.");
                        else
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
                        "Ты испытываешь истинное наслаждение от опустошения мочевого пузыря.");
                nextStage = ASK_ACTION;
                break;

            case CALLED_ON:
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                setLinesAsDialogue(1);
                setText(name + ", почему бы тебе не выйти к доске и не ответить?,",
                        "спросил учитель. Естественно, ты не знаешь, что говорить.",
                        "Ты проходишь к доске и пробуешь вспомнить хоть что-нибудь, и знаешь, что застрянешь здесь",
                        "на несколько минут, пока учитель не растолкует материал.",
                        "Что ж, у доски терпеть сложнее...");

//                score += 5;
//                scoreText = scoreText.concat("\nCalled on the lesson: +5 points");
                if (isFemale())
                {
                    score("Вызвана на уроке", '+', 5);
                }
                else
                {
                    score("Вызван на уроке", '+', 5);
                }
                nextStage = ASK_ACTION;
                passTime((byte) 5);
                break;

            case CLASS_OVER:
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

                //setLinesAsDialogue(6);
                if (generator.nextBoolean())
                {
                    setText("Наконец то урок закончился, и ты бежишь в туалет со всех ног.",
                            "Нет, пожалуйста... Все кабинки заняты, к тому же тут ещё и очередь. Придётся ждать!");

//                    score += 5;
//                    scoreText = scoreText.concat("\nWaited for a free cabin in the restroom: +5 score");
                    score("Ожидание в очереди в туалет", '+', 5);
                    passTime((byte) 5);
                    break;
                }
                else
                {
                    if (!lower.getName().equals("Без верхней одежды"))
                    {
                        if (!undies.getName().equals("Без нижней одежды"))
                        {
                            setText("Наконец то урок закончился, и ты бежишь в туалет со всех ног.",
                                    "Слава богу, одна кабинка свободна!",
                                    "Ты заходишь в неё, стягиваешь " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже + ",",
                                    "и начинаешь писать.");
                        }
                        else
                        {
                            setText("Наконец то урок закончился, и ты бежишь в туалет со всех ног.",
                                    "Слава богу, одна кабинка свободна!",
                                    "Ты заходишь в неё, стягиваешь " + lower.названиеВВинительномПадеже + ",",
                                    "и начинаешь писать.");
                        }
                    }
                    else if (!undies.getName().equals("Без нижней одежды"))
                    {
                        setText("Наконец то урок закончился, и ты бежишь в туалет со всех ног.",
                                "Слава богу, одна кабинка свободна!",
                                "Ты заходишь в неё, стягиваешь " + undies.названиеВВинительномПадеже + ",",
                                "и начинаешь писать.");
                    }
                    else
                    {
                        setText("Наконец то урок закончился, и ты бежишь в туалет со всех ног.",
                                "Слава богу, одна кабинка свободна!",
                                "You enter it,",
                                "и начинаешь писать.");
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
                if (isFemale())
                {
                    setText("Подожди ка, " + name + ", хотела сбежать? Ты оставлена после уроков!",
                            "Пожалуйста... дайте мне сходить в туалет... Я не могу...",
                            "Нет, " + name + ", потерпишь! Это будет как наказание.");
                }
                else
                {
                    setText("Подожди ка, " + name + ", хотел сбежать? Ты оставлен после уроков!",
                            "Пожалуйста... дайте мне сходить в туалет... Я не могу...",
                            "Нет, " + name + ", потерпишь! Это будет как наказание.");
                }

                timeBar.setMaximum(120);
                
                if (belly >= 3)
                {
                    offsetBladder(3);
                    offsetBelly(-3);
                    decaySphPower();
                }
                else
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
                setText("Ничего не помогает... Вне зависимости от приложенный усилий, остановить мочу не получилось.",
                        "Ты не можешь двигаться, иначе описаешься.",
                        "Паника сразу же охватила тебя.");
                nextStage = WET;
                break;

            case GIVE_UP:
                offsetEmbarassment(80);
                if (isFemale())
                {
                    setText("Тебе надоело держать всю эту мочу внутри,",
                            "и ты решила описаться прямо здесь.");
                }
                else
                {
                    setText("Тебе надоело держать всю эту мочу внутри,",
                            "и ты решил описаться прямо здесь.");
                }
                nextStage = WET;
                break;

            case WET:
                emptyBladder();
                embarassment = 100;
                if (!lower.getName().equals("Без верхней одежды"))
                {
                    if (!undies.getName().equals("Без нижней одежды"))
                    {
                        if(!lower.getName().contains("юбка"))
                            setText("Моча быстро просачивается сквозь твои " + undies.названиеВВинительномПадеже + ",",
                                    "заливает " + lower.названиеВВинительномПадеже + ", и стекает вниз по ногам.",
                                    "Под тобой быстро образуется большая лужа, и ты не можешь прекратить не писаться, не плакать.");
                        else
                            setText("Моча быстро просачивается сквозь твою " + undies.названиеВВинительномПадеже + ",",
                                    "заливает " + lower.названиеВВинительномПадеже + ", и стекает вниз по ногам.",
                                    "Под тобой быстро образуется большая лужа, и ты не можешь прекратить не писаться, не плакать.");
                    }
                    else
                    {
                        if(!lower.getName().contains("юбка"))
                            setText("Моча быстро просачивается сквозь твои " + lower.названиеВВинительномПадеже,
                                    "и стекает вниз по ногам.",
                                    "Под тобой быстро образуется большая лужа, и ты не можешь прекратить не писаться, не плакать.");
                        else
                            setText("Моча быстро просачивается сквозь твою " + lower.названиеВВинительномПадеже,
                                    "и стекает вниз по ногам.",
                                    "Под тобой быстро образуется большая лужа, и ты не можешь прекратить не писаться, не плакать.");
                    }
                }
                else if (!undies.getName().equals("Без нижней одежды"))
                {
                    setText("Моча быстро просачивается сквозь твои " + undies.названиеВВинительномПадеже,
                            "и стекает вниз по ногам.",
                            "Под тобой быстро образуется большая лужа, и ты не можешь прекратить не писаться, не плакать.");
                }
                else
                {
                    setText("Моча быстро образует под тобой большую лужу, и ты не можешь прекратить не писаться, не плакать.");
                }
                nextStage = POST_WET;
                break;

            case POST_WET:

                if (!stay)
                {
                    setLinesAsDialogue(2);
                    if (lower.getName().equals("Без верхней одежды"))
                    {
                        if (isFemale())
                        {
                            setText("Твои одноклассники громко засмеялись.",
                                    name + " описалась! Ахахаха!!!");
                        }
                        else
                        {
                            setText("Твои одноклассники громко засмеялись.",
                                    name + " описался! Ахахаха!!!");
                        }
                    }
                }
                else
                {
                    setLinesAsDialogue(2, 3);
                    if (isFemale())
                    {
                        setText("Учитель громко засмеялся.",
                                "О, ты описалась? Это тоже в качестве наказания.",
                                "В следующий раз не будешь прерывать урок.");
                    }
                    else
                    {
                        setText("Учитель громко засмеялся.",
                                "О, ты описался? Это тоже в качестве наказания.",
                                "В следующий раз не будешь прерывать урок.");
                    }
                }
                nextStage = GAME_OVER;
                break;

            case GAME_OVER:
                if (lower.getName().equals("Без верхней одежды"))
                {
                    if (undies.getName().equals("Без нижней одежды"))
                    {
                        if (isFemale())
                        {
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно...",
                                    "Игра окончена!");
                        }
                        else
                        {
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно...",
                                    "Игра окончена!");
                        }
                    }
                    else if (isFemale())
                    {
                        if(undies.число == 1)
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твой " + undies.insert() + " прилипает к коже, как знак этого провала...",
                                    "Игра окончена!");
                        else
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твои " + undies.insert() + " прилипает к коже, как знак этого провала...",
                                    "Игра окончена!");
                    }
                    else
                    {
                        if(undies.число == 1)
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твой " + undies.insert() + " прилипает к коже, как знак этого провала...",
                                    "Игра окончена!");
                        else
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твои " + undies.insert() + " прилипают к коже, как знак этого провала...",
                                    "Игра окончена!");
                    }
                }
                else if (undies.getName().equals("Без нижней одежды"))
                {
                    if (isFemale())
                    {
                        if(lower.число == 1)
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твой " + lower.insert() + " прилипает к коже, как знак этого провала...",
                                    "Игра окончена!");
                        else
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твои " + lower.insert() + " прилипают к коже, как знак этого провала...",
                                    "Игра окончена!");
                        if(lower.getName().contains("юбка"))
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твоя " + lower.insert() + " прилипает к коже, как знак этого провала...",
                                    "Игра окончена!");
                    }
                    else
                    {
                        if(lower.число == 1)
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твой " + lower.insert() + " прилипает к коже, как знак этого провала...",
                                    "Игра окончена!");
                        else
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твои " + lower.insert() + " прилипают к коже, как знак этого провала...",
                                    "Игра окончена!");
                        if(lower.getName().contains("юбка"))
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твоя " + lower.insert() + " прилипает к коже, как знак этого провала...",
                                    "Игра окончена!");
                    }
                }
                else if (isFemale())
                {
                    if(undies.число == 1)
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твой " + undies.insert() + " и "+ lower.insert() + " прилипают к коже, как знак этого провала...",
                                    "Игра окончена!");
                        else
                            setText("Не важно, как ты старалась вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твои " + undies.insert() + " и "+ lower.insert() + " прилипают к коже, как знак этого провала...",
                                    "Игра окончена!");
                }
                else
                {
                    if(undies.число == 1)
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твой " + undies.insert() + " и "+ lower.insert() + " прилипают к коже, как знак этого провала...",
                                    "Игра окончена!");
                        else
                            setText("Не важно, как ты старался вытерпеть... Теперь то уже не важно...",
                                    "Уже ничто не важно... Твои " + undies.insert() + " и "+ lower.insert() + " прилипают к коже, как знак этого провала...",
                                    "Игра окончена!");
                }
                btnNext.setVisible(false);
                break;

            case END_GAME:
                if (cheatsUsed)
                {
                    score = 0;
                    scoreText = "\nИз-за использования читов очки обнулились.";
                }
                String scoreText2 = "Очки: " + score + "\n" + scoreText;

                JOptionPane.showMessageDialog(this, scoreText2);
                System.exit(0);
                break;

            case CAUGHT:
                switch (timesCaught)
                {
                    case 0:
                        setText("Похоже, один одноклассник заметил, что тебе сильно нужно в туалет.",
                                "Чёрт, он может рассказать другим...");
                        offsetEmbarassment(3);
                        classmatesAwareness += 5;
//                        score += 3;
//                        scoreText = scoreText.concat("\nCaught holding pee: +3 points");
                        score("Одноклассники заметили, что тебе нужно в туалет", '+', 3);
                        timesCaught++;
                        break;
                    case 1:
                        setLinesAsDialogue(3);
                        if (isFemale())
                        {
                            setText("Ты услышала подозрительный шёпот с парты позади.",
                                    "Прислушавшись, ты поняла, что они обсуждают твоё терпение.",
                                    "Если я дотерплю, я их изобью на перемене.");
                        }
                        else
                        {
                            setText("Ты услышал подозрительный шёпот с парты позади.",
                                    "Прислушавшись, ты понял, что они обсуждают твоё терпение.",
                                    "Если я дотерплю, я их изобью на перемене.");
                        }
                        offsetEmbarassment(8);
                        classmatesAwareness += 5;
//                        score += 8;
//                        scoreText = scoreText.concat("\nCaught holding pee: +8 points");
                        score("Одноклассники заметили, что тебе нужно в туалет", '+', 8);
                        timesCaught++;
                        break;
                    case 2:
                        if (isFemale())
                        {
                            setLinesAsDialogue(2);
                            setText("Самый красивый парень в классе, " + boyName + ", говорит тебе:",
                                    "Эй, ты тут нам не описайся!",
                                    "О чёрт, только не он...");
                        }
                        else
                        {
                            setLinesAsDialogue(2, 3);
                            setText("Самый уродливый мальчик в классе, " + boyName + ", говорит тебе:",
                                    "Эй, ты тут нам не описайся! Хахаха!",
                                    "\"Заткнись...\"",
                                    ", думаешь ты про себя.");
                        }
                        offsetEmbarassment(12);
                        classmatesAwareness += 5;
//                        score += 12;
//                        scoreText = scoreText.concat("\nCaught holding pee: +12 points");
                        score("Одноклассники заметили, что тебе нужно в туалет", '+', 12);
                        timesCaught++;
                        break;
                    default:
                        setText("По классу периодически издаются смешки и...",
                                "все смотрят на тебя.",
                                "О нет... Теперь все знают...");
                        offsetEmbarassment(20);
                        classmatesAwareness += 5;
//                        score += 20;
//                        scoreText = scoreText.concat("\nCaught holding pee: +20 points");
                        score("Одноклассники заметили, что тебе нужно в туалет", '+', 20);
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
                score("\"сюрприз\" от " + boyNameРодительный, '+', 70);
                setText("Наконец то урок закончился, и ты бежишь в туалет со всех ног.",
                        "Но... Ты видишь " + boyNameВинительный + ", стоящего около туалета.",
                        "Внезапно он хватает тебя, не давая сбежать.");
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
                setText("Что тебе нужно?!",
                        "Он перенёс тебя на руках и положил на подоконник.",
                        boyName + " запер дверь (похоже, он украл ключи), затем положил ладонь на твой живот и сказал:",
                        "Я хочу, чтобы ты описалась.");
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
                setText("Нет, пожалуйста, не надо, нет...",
                        "Я хочу видеть, как ты писаешься...",
                        "Он легонько нажал на живот и ты содрогнулась от вспышки боли",
                        "в мочевом пузыре и невольно схватилась за промежность. Нужно что-то делать!");
                offsetEmbarassment(10);
                if (testWet())
                {
                    nextStage = SURPRISE_ACCIDENT;
                    break;
                }
                lblChoice.setText("Не дай ему этого сделать!");
                actionList.clear();

                actionList.add("Ударить его");
                switch (timesPeeDenied)
                {
                    case 0:
                        actionList.add("Попробовать уговорить его пописать");
                        break;
                    case 1:
                        actionList.add("Попробовать уговорить его пописать ещё раз");
                        break;
                    case 2:
                        actionList.add("Попробовать уговорить его пописать ещё раз (опасно)");
                        break;
                }
                actionList.add("Описаться");

                listChoice.setListData(actionList.toArray());
                lblChoice.setVisible(true);
                listScroller.setVisible(true);
                nextStage = SURPRISE_CHOSE;
                break;

            case SURPRISE_CHOSE:
                nextStage = SURPRISE_DIALOGUE;
                if (listChoice.isSelectionEmpty())
                {
                    setText("Ты пописаешь здесь и сейчас...,",
                            "сказал " + boyName + ".",
                            "Затем он нажал прямо на мочевой пузырь...");
                    nextStage = SURPRISE_WET_PRESSURE;
                }
                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                offsetTime(1);
                offsetBladder(1.5);
                offsetBelly(-1.5);
                decaySphPower();

                actionName = (String) listChoice.getSelectedValue();
                actionNum = listChoice.getSelectedIndex();
                if (actionName.equals("[Недоступно]"))
                {
                    setText("Ты пописаешь здесь и сейчас...,",
                            "сказал " + boyName + ".",
                            "Затем он нажал прямо на мочевой пузырь...");
                    nextStage = SURPRISE_WET_PRESSURE;
                }

                lblChoice.setVisible(false);
                listScroller.setVisible(false);

                listChoice.clearSelection();

                switch (actionNum)
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
                if (generator.nextInt(100) <= 10)
                {
                    setLinesAsDialogue(2);
                    nextStage = GameStage.END_GAME;
//                    score += 40;
//                    scoreText = scoreText.concat("\nSuccessful hit on " + boyName + "'s groin: +40 points");
                    score("Успешный удар в пах " + boyNameРодительный, '+', 40F);
                    if (!lower.getName().equals("Без верхней одежды"))
                    {
                        if (!undies.getName().equals("Без нижней одежды"))
                        {
                            setText("Ты ударила " + boyNameВинительный + " в пах.",
                                    "Ай! Ты, маленькая сучка...",
                                    "Затем он быстро убрался из туалета.",
                                    "Ты слезла с подоконника, держа палец в своей щёлке,",
                                    "вошла в кабинку, стянула " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже,
                                    "и начала писать.");
                        }
                        else
                        {
                            setText("Ты ударила " + boyNameВинительный + " в пах.",
                                    "Ай! Ты, маленькая сучка...",
                                    "Затем он быстро убрался из туалета.",
                                    "Ты слезла с подоконника, держа палец в своей щёлке,",
                                    "вошла в кабинку, стянула " + lower.названиеВВинительномПадеже,
                                    "и начала писать.");
                        }
                    }
                    else if (!undies.getName().equals("Без нижней одежды"))
                    {
                        setText("Ты ударила " + boyNameВинительный + " в пах.",
                                "Ай! Ты, маленькая сучка...",
                                "Затем он быстро убрался из туалета.",
                                "Ты слезла с подоконника, держа палец в своей щёлке,",
                                "вошла в кабинку, стянула " + undies.названиеВВинительномПадеже,
                                "и начала писать.");
                    }
                    else
                    {
                        setText("Ты ударила " + boyNameВинительный + " в пах.",
                                "Ай! Ты, маленькая сучка...",
                                "Затем он быстро убрался из туалета.",
                                "Ты слезла с подоконника, держа палец в своей щёлке,",
                                "вошла в кабинку и начала писать.");
                    }
                    /*
                    Gender-dependent text block template
                    if(!lower.equals("no lower"))
                        if(!undies.equals("промежность")|!undies.equals("член"))
                            Undies: yes
                            Lower: yes
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Слава богу, одна кабинка свободна!",
                                    "Ты заходишь в неё, стягиваешь " + lower + " and " + undies + ",",
                                    "и начинаешь писать.");
                        else
                            Undies: no
                            Lower: yes
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Слава богу, одна кабинка свободна!",
                                    "Ты заходишь в неё, стягиваешь " + lower + ",",
                                    "и начинаешь писать.");
                    else
                        if(!undies.equals("промежность")|!undies.equals("член"))
                            Undies: yes
                            Lower: no
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Слава богу, одна кабинка свободна!",
                                    "Ты заходишь в неё, стягиваешь " + undies + ",",
                                    "и начинаешь писать.");
                        else
                            Undies: no
                            Lower: no
                            setText("Lesson is over, and you're running to the restroom as fast as you can.",
                                    "Слава богу, одна кабинка свободна!",
                                    "You enter it,",
                                    "и начинаешь писать.");
                     */
                }
                else
                {
                    nextStage = GameStage.SURPRISE_WET_PRESSURE;
                    setLinesAsDialogue(2, 3);
                    setText("Ты ударила руку " + boyNameРодительный + "Чёрт, надо было бить в пах...",
                            "Ты смелее, чем я думал;",
                            "а теперь давай проверим твой мочевой пузырь!",
                            boyName + " надавил на мочевой пузырь со всех сил...");
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
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    setText("Хорошо, пописай, но дай мне посмотреть.",
                                            "разрешил " + boyName + ". Ты вошла в кабинку,",
                                            "стянула " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже + ",",
                                            "и начала писать под наблюдением " + boyNameРодительный + ".");
                                }
                                else
                                {
                                    setText("Хорошо, пописай, но дай мне посмотреть.",
                                            "разрешил " + boyName + ". Ты вошла в кабинку,",
                                            "стянула " + lower.названиеВВинительномПадеже,
                                            "и начала писать под наблюдением " + boyNameРодительный + ".");
                                }
                            }
                            else if (!undies.getName().equals("Без нижней одежды"))
                            {
                                setText("Хорошо, пописай, но дай мне посмотреть.",
                                        "разрешил " + boyName + ". Ты вошла в кабинку,",
                                        "стянула " + undies.названиеВВинительномПадеже,
                                        "и начала писать под наблюдением " + boyNameРодительный + ".");
                            }
                            else
                            {
                                setText("Хорошо, пописай, но дай мне посмотреть.",
                                        "разрешил " + boyName + ". Ты вошла в кабинку",
                                        "и начала писать под наблюдением " + boyNameРодительный + ".");
                            }
//                            score += 40;
//                            scoreText = scoreText.concat("\nPersuaded " + boyName + " to pee: +40 points");
                            score("Уговорила " + boyNameВинительный + " пописать", '+', 40);
                            emptyBladder();
                            nextStage = END_GAME;
                        }
                        else
                        {
                            setText("Ты спросила " + boyNameВинительный + ", можешь ли ты пописать.",
                                    "Нет, не можешь. Я хочу, чтобы ты пописала здесь.",
                                    "отказал " + boyName);
                            timesPeeDenied++;
                            nextStage = SURPRISE_DIALOGUE;
                        }
                        break;

                    case 1:
                        if (generator.nextInt(100) <= 5)
                        {
                            setLinesAsDialogue(1);
                            if (!lower.getName().equals("Без верхней одежды"))
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    setText("Хорошо, пописай, но дай мне посмотреть.",
                                            "разрешил " + boyName + ". Ты вошла в кабинку,",
                                            "стянула " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже + ",",
                                            "и начала писать под наблюдением " + boyNameРодительный + ".");
                                }
                                else
                                {
                                    setText("Хорошо, пописай, но дай мне посмотреть.",
                                            "разрешил " + boyName + ". Ты вошла в кабинку,",
                                            "стянула " + lower.названиеВВинительномПадеже,
                                            "и начала писать под наблюдением " + boyNameРодительный + ".");
                                }
                            }
                            else if (!undies.getName().equals("Без нижней одежды"))
                            {
                                setText("Хорошо, пописай, но дай мне посмотреть.",
                                        "разрешил " + boyName + ". Ты вошла в кабинку,",
                                        "стянула " + undies.названиеВВинительномПадеже,
                                        "и начала писать под наблюдением " + boyNameРодительный + ".");
                            }
                            else
                            {
                                setText("Хорошо, пописай, но дай мне посмотреть.",
                                        "разрешил " + boyName + ". Ты вошла в кабинку",
                                        "и начала писать под наблюдением " + boyNameРодительный + ".");
                            }
//                            score += 60;
//                            scoreText = scoreText.concat("\nPersuaded " + boyName + " to pee: +60 points");
                            score("Уговорила " + boyNameВинительный + " пописать", '+', 60);
                            emptyBladder();
                            nextStage = END_GAME;
                        }
                        else
                        {
                            setText("Ты спросила " + boyNameВинительный + ", можешь ли ты пописать, опять.",
                                    "Нет, не можешь. Давай ты описаешься.",
                                    "отказал " + boyName);
                            timesPeeDenied++;
                            nextStage = SURPRISE_DIALOGUE;
                        }
                        break;

                    case 2:
                        if (generator.nextInt(100) <= 2)
                        {
                            setLinesAsDialogue(1);
                            if (!lower.getName().equals("Без верхней одежды"))
                            {
                                if (!undies.getName().equals("Без нижней одежды"))
                                {
                                    setText("Хорошо, пописай, но дай мне посмотреть.",
                                            "разрешил " + boyName + ". Ты вошла в кабинку,",
                                            "стянула " + lower.названиеВВинительномПадеже + " и " + undies.названиеВВинительномПадеже + ",",
                                            "и начала писать под наблюдением " + boyNameРодительный + ".");
                                }
                                else
                                {
                                    setText("Хорошо, пописай, но дай мне посмотреть.",
                                            "разрешил " + boyName + ". Ты вошла в кабинку,",
                                            "стянула " + lower.названиеВВинительномПадеже,
                                            "и начала писать под наблюдением " + boyNameРодительный + ".");
                                }
                            }
                            else if (!undies.getName().equals("Без нижней одежды"))
                            {
                                setText("Хорошо, пописай, но дай мне посмотреть.",
                                        "разрешил " + boyName + ". Ты вошла в кабинку,",
                                        "стянула " + undies.названиеВВинительномПадеже,
                                        "и начала писать под наблюдением " + boyNameРодительный + ".");
                            }
                            else
                            {
                                setText("Хорошо, пописай, но дай мне посмотреть.",
                                        "разрешил " + boyName + ". Ты вошла в кабинку",
                                        "и начала писать под наблюдением " + boyNameРодительный + ".");
                            }

//                            score += 80;
//                            scoreText = scoreText.concat("\nPersuaded " + boyName + " to pee: +80 points");
                            score("Уговорила " + boyName + " пописать", '+', 80);
                            emptyBladder();
                            nextStage = END_GAME;
                        }
                        else
                        {
                            setText("В нетерпении ты умоляешь " + boyNameВинительный + " разрешить тебе пописать.",
                                    "Нет. Зачем тебе это, если ты сделаешь это прямо сейчас?",
                                    "сказал " + boyName,
                                    "Затем он нажал прямо на мочевой пузырь...");
                            nextStage = SURPRISE_WET_PRESSURE;
                        }
                        break;
                }
                break;

            case SURPRISE_WET_VOLUNTARY:
                setLinesAsDialogue(1, 3);
                setText("Ладно, как скажешь.",
                        "ты говоришь " + boyNameДательный,
                        "Всё равно я больше не могу терпеть.");
                emptyBladder();
                nextStage = SURPRISE_WET_VOLUNTARY2;
                break;

            case SURPRISE_WET_VOLUNTARY2:
                if (!lower.getName().equals("Без верхней одежды"))
                {
                    if (!undies.getName().equals("Без нижней одежды"))
                    {
                        setText("Ты почуствовала тёплую, обжигающую струю мочи,",
                                "заливающую твои " + undies.названиеВВинительномПадеже + " и " + lower.названиеВВинительномПадеже + ".",
                                "Ты закрываешь свои глаза и полностью расслабляешься,",
                                "и чувствуешь, как струя стала намного сильнее.");
                    }
                    else
                    {
                        if(lower.число == 1)
                            setText("Ты почуствовала тёплую, обжигающую струю мочи,",
                                    "заливающую твой " + lower.insert() + ".",
                                    "Ты закрываешь свои глаза и полностью расслабляешься,",
                                    "и чувствуешь, как струя стала намного сильнее.");
                        else
                            setText("Ты почуствовала тёплую, обжигающую струю мочи,",
                                    "заливающую твои " + lower.insert() + ".",
                                    "Ты закрываешь свои глаза и полностью расслабляешься,",
                                    "и чувствуешь, как струя стала намного сильнее.");
                        if(lower.getName().contains("юбка"))
                            setText("Ты почуствовала тёплую, обжигающую струю мочи,",
                                    "заливающую твою " + lower.insert() + ".",
                                    "Ты закрываешь свои глаза и полностью расслабляешься,",
                                    "и чувствуешь, как струя стала намного сильнее.");
                    }
                }
                else if (!undies.getName().equals("Без нижней одежды"))
                {
                    if(undies.число == 1)
                        setText("Ты почуствовала тёплую, обжигающую струю мочи,",
                                "заливающую твой " + undies.insert() + ".",
                                "Ты закрываешь свои глаза и полностью расслабляешься,",
                                "и чувствуешь, как струя стала намного сильнее.");
                    else
                        setText("Ты почуствовала тёплую, обжигающую струю мочи,",
                                "заливающую твои " + undies.insert() + ".",
                                "Ты закрываешь свои глаза и полностью расслабляешься,",
                                "и чувствуешь, как струя стала намного сильнее.");
                }
                else
                {
                    setText("Ты почуствовала тёплую, обжигающую струю мочи,",
                            "обливающую твои ноги.",
                            "Ты закрываешь свои глаза и полностью расслабляешься,",
                            "и чувствуешь, как струя стала намного сильнее.");
                }
                emptyBladder();
                nextStage = END_GAME;
                break;

            case SURPRISE_WET_PRESSURE:
//                setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
//                        "Ты пытаешься вытерпеть, но физически не можешь.",
//                        "Ты почуствовала тёплую, обжигающую струю мочи,",
//                        "заливающую твои " + undies.insert() + " и " + lower.insert() + ".",
//                        "Ты закрываешь свои глаза и полностью расслабляешься.",
//                        "Ты чувствуешь, как струя стала намного сильнее.");
                
                if (!lower.getName().equals("Без верхней одежды"))
                {
                    if (!undies.getName().equals("Без нижней одежды"))
                    {
                        setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
                                "Ты пытаешься вытерпеть, но физически не можешь.",
                                "Ты почуствовала тёплую, обжигающую струю мочи,",
                                "заливающую твои " + undies.названиеВВинительномПадеже + " и " + lower.названиеВВинительномПадеже + ".",
                                "Ты закрываешь свои глаза и полностью расслабляешься,",
                                "и чувствуешь, как струя стала намного сильнее.");
                    }
                    else
                    {
                        if(lower.число == 1)
                            setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
                                    "Ты пытаешься вытерпеть, но физически не можешь.",
                                    "Ты почуствовала тёплую, обжигающую струю мочи,",
                                    "заливающую твой " + lower.названиеВВинительномПадеже + ".",
                                    "Ты закрываешь свои глаза и полностью расслабляешься,",
                                    "и чувствуешь, как струя стала намного сильнее.");
                        else
                            setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
                                    "Ты пытаешься вытерпеть, но физически не можешь.",
                                    "Ты почуствовала тёплую, обжигающую струю мочи,",
                                    "заливающую твои " + lower.названиеВВинительномПадеже + ".",
                                    "Ты закрываешь свои глаза и полностью расслабляешься,",
                                    "и чувствуешь, как струя стала намного сильнее.");
                        
                        if(lower.getName().contains("юбка"))
                            setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
                                    "Ты пытаешься вытерпеть, но физически не можешь.",
                                    "Ты почуствовала тёплую, обжигающую струю мочи,",
                                    "заливающую твою " + lower.названиеВВинительномПадеже + ".",
                                    "Ты закрываешь свои глаза и полностью расслабляешься,",
                                    "и чувствуешь, как струя стала намного сильнее.");
                    }
                }
                else if (!undies.getName().equals("Без нижней одежды"))
                {
                    if(undies.число == 1)
                        setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
                                "Ты пытаешься вытерпеть, но физически не можешь.",
                                "Ты почуствовала тёплую, обжигающую струю мочи,",
                                "заливающую твой " + undies.названиеВВинительномПадеже + ".",
                                "Ты закрываешь свои глаза и полностью расслабляешься,",
                                "и чувствуешь, как струя стала намного сильнее.");
                    else
                        setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
                                "Ты пытаешься вытерпеть, но физически не можешь.",
                                "Ты почуствовала тёплую, обжигающую струю мочи,",
                                "заливающую твои " + undies.названиеВВинительномПадеже + ".",
                                "Ты закрываешь свои глаза и полностью расслабляешься,",
                                "и чувствуешь, как струя стала намного сильнее.");
                }
                else
                {
                    setText("Ай... Внезапная нестерпимая вспышка ужасной боли пронзила мочевой пузырь...",
                            "Ты пытаешься вытерпеть, но физически не можешь.",
                            "Ты почуствовала тёплую, обжигающую струю мочи,",
                            "обливающую твои ноги.",
                            "Ты закрываешь свои глаза и полностью расслабляешься,",
                            "и чувствуешь, как струя стала намного сильнее.");
                }
                emptyBladder();
                nextStage = END_GAME;
                break;

            default:
                setText("Ошибка. Следующий слайд: #" + nextStage);
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
        passTime((byte) 3);
    }

    public void passTime(byte time)
    {
        offsetTime(time);
        offsetBladder(time * 1.5);
        offsetBelly(-time * 1.5);

        if (min >= 88)
        {
            if (isFemale())
            {
                setText("Ты услышала долгожданный звонок.");
            }
            else
            {
                setText("Ты услышал долгожданный звонок.");
            }
            nextStage = CLASS_OVER;
        }

        if (testWet())
        {
            nextStage = ACCIDENT;
        }

        for (int i = 0; i < time; i++)
        {
            decaySphPower();
            if (belly != 0)
            {
                if (belly > 3)
                {
                    offsetBladder(2);
                }
                else
                {
                    offsetBladder(belly);
                    emptyBelly();
                }
            }
        }

        lblSphPower.setText("Способность терпеть: " + Math.round(sphincterPower) + "%");
        sphincterBar.setValue((int)Math.round(sphincterPower));
        lblDryness.setText("Сухость одежды: " + Math.round(dryness));
        drynessBar.setValue((int)Math.round(dryness));
    }

    
    public boolean testWet()
    {
        //If bladder is filled more than 130 points in the normal mode and 100 points in the hardcore mode, forcing wetting
        if (bladder >= maxBladder & !hardcore)
            return true;
        else //If bladder is filled more than 100 points in the normal mode and 50 points in the hardcore mode
            if ((bladder > maxBladder - 30 & !hardcore) | (bladder > maxBladder - 20 & hardcore))
            {
//                double wetChance = bladder + (hardcore ? 30 : 10) + embarassment;
//                double wetLimit = generator.nextInt(80);
//                if (wetChance > wetLimit)
//                {
//                    return true;
//                }
                if (!hardcore)
                {
                    byte wetChance = (byte) (3 * (bladder - 100));
                    if (generator.nextInt(100) < wetChance)
                        return true;
                } else
                {
                    byte wetChance = (byte) (5 * (bladder - 80));
                    if (generator.nextInt(100) < wetChance)
                        return true;
                }
            }
        return false;
    }

    public void emptyBladder()
    {
        bladder = 0;
        lblBladder.setText("Мочевой пузырь: " + (int) bladder + "%");
    }

    public void offsetBladder(double amount)
    {
        bladder += amount/* * incon*/;
        lblBladder.setText("Мочевой пузырь: " + (int) bladder + "%");
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
        lblBelly.setText("Вода в животе: " + Math.round(belly) + "%");
    }

    public void offsetEmbarassment(int amount)
    {
        embarassment += amount;
        if (embarassment < 0)
        {
            embarassment = 0;
        }
        lblEmbarassment.setVisible(true);
        lblEmbarassment.setText("Смущение: " + Math.round(embarassment));
    }

    public void offsetTime(int amount)
    {
        min += amount;
        timeBar.setValue(min);
        lblMinutes.setText("Время: " + min + " минут(ы) из " + (stay ? "120" : "90"));

        if (drain & (min % 15) == 0)
        {
            emptyBladder();
        }
    }

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
            if (dryness > 0)
            {
                //Naked
                if (lower.getName().equals("Без верхней одежды") && undies.getName().equals("Без нижней одежды"))
                {
                    setText("Ты чувствуешь, как моча начинает выходить наружу...",
                            "Ты начинаешь писаться! Нужно остановить это!");
                }
                else //Outerwear
                if (!lower.getName().equals("Без верхней одежды"))
                {
                    setText("Ты смотришь на " + lower.названиеВВинительномПадеже + " и видишь влажное пятнышко!",
                            "Ты начинаешь писаться! Нужно остановить это!");
                }
                else //Underwear
                if (!undies.getName().equals("Без нижней одежды"))
                {
                    setText("Ты смотришь на " + undies.названиеВВинительномПадеже + " и видишь влажное пятнышко!",
                            "Ты начинаешь писаться! Нужно остановить это!");
                }
            }

            if (dryness < 0)
            {
                if (lower.getName().equals("Без верхней одежды") && undies.getName().equals("Без нижней одежды"))
                {
                    setText("Ты видишь лужу на полу под тобой!",
                            "Ты писаешься...");
                    nextStage = ACCIDENT;
                    handleNextClicked();
                }
                else if (!lower.getName().equals("Без верхней одежды"))
                {
                        setText("Ты смотришь на " + lower.названиеВВинительномПадеже + " и видишь влажное пятнышко!",
                                "Оно слишком большое! Ты писаешься...");
                    nextStage = ACCIDENT;
                    handleNextClicked();
                }
                else if (!undies.getName().equals("Без нижней одежды"))
                {
                        setText("Ты смотришь на " + undies.названиеВВинительномПадеже + " и видишь влажное пятнышко!",
                                "Оно слишком большое! Ты писаешься...");
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
            System.err.println("Невозможно установить больше чем " + MAX_LINES + " строк текста!");
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
            }
            else
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
                scoreText = scoreText + "\n" + message + ": +" + points + " очков";
                break;
            case '-':
                score -= points;
                scoreText = scoreText + "\n" + message + ": -" + points + " очков";
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
                System.err.println("Метод score() использован неправильно, сообщение: \"" + message + "\"");
        }
    }

    public void score(String message, char mode, float points)
    {
        switch (mode)
        {
            case '+':
                score += points;
                scoreText = scoreText + "\n" + message + ": +" + points + " очков";
                break;
            case '-':
                score -= points;
                scoreText = scoreText + "\n" + message + ": -" + points + " очков";
                break;
            case '*':
                score *= points;
                scoreText = scoreText + "\n" + message + ": +" + points * 100 + "% очков";
                break;
            case '/':
                score /= points;
                scoreText = scoreText + "\n" + message + ": -" + 100 / points + "% окчов";
                break;
            default:
                System.err.println("Метод score() использован неправильно, сообщение: \"" + message + "\"");
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
        SURPRISE_WET_PRESSURE,
        END_GAME //Good ending, showing score
    }

    enum Gender
    {
        MALE, FEMALE
    }
}
