package omo;

import static omo.Bladder.*;

/**
 * Class for storing game user parameters for reset.
 *
 * @author JavaBird
 */
class ResetParametersStorage
{
    private static final long serialVersionUID = 1;
    
    static String nameParam;
    static short bladderParam;
    static Gender gndrParam;
    static boolean diffParam;
    static float incParam;
    static String underParam;
    static String outerParam;
    static String underColorParam;
    static String outerColorParam;

    static void stashParametersForReset()
    {
        //TODO
        ResetParametersStorage.nameParam = NarrativeEngine.getCharacterName();
        ResetParametersStorage.gndrParam = NarrativeEngine.getGender();
        ResetParametersStorage.incParam = Bladder.getIncontinence();
        ResetParametersStorage.bladderParam = Bladder.getFulness();
        ResetParametersStorage.outerParam = getLower().getName();
        ResetParametersStorage.underParam = getUndies().getName();
        ResetParametersStorage.underColorParam = getUndies().getColor();
        ResetParametersStorage.outerColorParam = getLower().getColor();
    }
}