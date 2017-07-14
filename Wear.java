package omo;

import java.io.Serializable;

/**
 * Describes an underwear of an outerwear of a character.
 *
 * @author JavaBird
 */
public class Wear implements Serializable //"implements Serializable" is required for saving into a file
{
    /**
     * List of all colors that clothes may have.
     */
    static final String[] COLOR_LIST =
    {
        "Black", "Gray", "Red", "Orange", "Yellow", "Green", "Blue", "Dark blue", "Purple", "Pink"
    };
    
    private static final long serialVersionUID = 1L;
    static final String CUSTOM_WEAR = "Custom";
    static final String RANDOM_WEAR = "Random";

    /**
     * The wear name (e. g. "Regular panties")
     */
    private String name;

    /**
     * The insert name used in the game text (e. g. "panties")
     */
    private String insertName;

    /**
     * The pressure of an wear.<br>1 point of a pressure takes 1 point from the
     * maximal bladder capacity.
     */
    private float pressure;

    /**
     * The absorption of an wear.<br>1 point of an absorption can store 1 point
     * of a leaked pee.
     */
    private float absorption;

    /**
     * The drying over time.<br>1 point = -1 pee unit per 3 minutes
     */
    private float dryingOverTime;

    /**
     * The wear assigned color.
     */
    private String color;

    /**
     * Whether or not certain wear equals "No under/outerwear".
     */
    private boolean missing;

    /**
     * Underwear or outerwear?
     */
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
        setFieldValuesInConstructor(name, insertName, pressure, absorption, dryingOverTime);
    }

    /**
     * @param name the wear name (e. g. "Regular panties")
     * @param insertName the insert name used in the game text (e. g. "panties")
     * @param pressure the pressure of an wear.<br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption the absorption of an wear.<br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     * 3 minutes
     * @param type the wear type
     */
    public Wear(String name, String insertName, float pressure, float absorption, float dryingOverTime, WearType type)
    {
        setFieldValuesInConstructor(name, insertName, pressure, absorption, dryingOverTime);
        this.type = type;
    }
    
    /**
     * Assign wear parameters.
     *
     * @param name the wear name (e. g. "Regular panties")
     * @param insertName the insert name used in the game text (e. g. "panties")
     * @param pressure the pressure of an wear.<br>1 point of a pressure takes 1
     * point from the maximal bladder capacity.
     * @param absorption the absorption of an wear.<br>1 point of an absorption
     * can store 1 point of a leaked pee.
     * @param dryingOverTime the drying over time.<br>1 point = -1 pee unit per
     * 3 minutes
     */
    private void setFieldValuesInConstructor(String name, String insertName, float pressure, float absorption, float dryingOverTime)
    {
        this.name = name;
        this.insertName = insertName;
        this.pressure = pressure;
        this.absorption = absorption;
        this.dryingOverTime = dryingOverTime;
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
    public boolean isMissing()
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

    /**
     * The wear types list.
     */
    public enum WearType
    {
    	/**
         * Wear can be considered only as underwear.
         */
        UNDERWEAR,
        /**
         * Wear can be considered only as outerwear.
         */
        OUTERWEAR,
        /**
         * Wear can be considered as either underwear or outerwear.
         */
        BOTH_SUITABLE
    }
}