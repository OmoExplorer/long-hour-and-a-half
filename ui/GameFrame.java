package omo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import omo.Bladder;
import static omo.Bladder.*;
import omo.GameCore;
import omo.NarrativeEngine;
import static omo.NarrativeEngine.*;

public class GameFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final int ACTION_BUTTONS_HEIGHT = 35;
    private static final int ACTION_BUTTONS_WIDTH = 89;
    private static final int ACTION_BUTTONS_TOP_BORDER = 510;
    public static Color lblDefaultColor;
    
    //Maximal lines of a text
    public static final int MAX_LINES = 9;
    private JLabel lblLower;
    private JLabel lblThirst;
    private JLabel lblMinutes;
    public JLabel lblName;
    private JProgressBar sphincterBar;
    private JScrollPane listScroller;
    private JButton btnNewGame;
    JLabel lblSphPower;

    private JList<Object> listChoice;
    private JButton btnLoad;
    private JLabel lblIncon;
    private JLabel lblUndies;
    private JLabel lblEmbarassment;
    JLabel lblDryness;
    private JProgressBar thirstBar;
    private JLabel lblChoice;
    private JPanel textPanel;
    private JButton btnQuit;
    private JLabel lblBelly;
    private JProgressBar bladderBar;
    public JProgressBar drynessBar;
    private JButton btnReset;
    JLabel textLabel;
    JProgressBar timeBar;
    //Game frame variables declaration
    private JPanel contentPane;
    private JButton btnNext;
    public JLabel lblBladder;
    private JButton btnSave;
    
    /**
     * Sets the in-game text.
     *
     * @param lines the in-game text to set
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public void setText(String... lines)
    {
        if (lines.length > GameFrame.MAX_LINES)
        {
            System.err.println("You can't have more than " + GameFrame.MAX_LINES + " lines at a time!");
            return;
        }
        if (lines.length <= 0)
        {
            this.textLabel.setText("");
            return;
        }
        String toSend = "<html><center>";
        for (int i = 0; i < lines.length; i++)
        {
            if (NarrativeEngine.getDialogueLines()[i])
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
        NarrativeEngine.setDialogueLines(new boolean[GameFrame.MAX_LINES]);
    }
    
    public void handleNextClicked()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void showScore()
    {
        String scoreText2 = "Your score: " + getScore() + "\n" + getScoreText();
        JOptionPane.showMessageDialog(null, scoreText2);
    }

    /**
     *
     * @return the boolean
     */
    private boolean inappropriateSelection()
    {
        return listChoice.isSelectionEmpty() || listChoice.getSelectedValue().equals(ACTION_UNAVAILABLE);
    }

    /**
     *
     * @param actionGroupName the value of actionGroupName
     * @param the value of
     */
    void showActionUI(String actionGroupName)
    {
        listChoice.setListData(getActionList().toArray());
        lblChoice.setVisible(true);
        lblChoice.setText(actionGroupName);
        listScroller.setVisible(true);
    }

    /**
     *
     * @throws NumberFormatException
     * @throws HeadlessException
     */
    private void askNewBladderLevel() throws NumberFormatException
    {
        short newBladder = Short.parseShort(JOptionPane.showInputDialog("How your bladder is full now?"));
        if (newBladder > 0 || newBladder < 150)
        {
            setFulness(newBladder);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Old handleNextClicked()">
    //TODO: Refactor
    //This method is monstrously huge
    //Introduce Stage class
    /*
    static void handleNextClicked()
    {
    update();
    switch (nextStage)
    {
    case LEAVE_BED:
    //Making line 1 italic
    setLinesAsDialogue(1);
    if (!lower.isMissing())
    {
    if (!undies.isMissing()) //Both lower clothes and undies
    {
    setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You hurriedly slip on some " + undies.insert() + " and " + lower.insert() + ",", "not even worrying about what covers your chest.");
    } else //Lower clothes only
    {
    setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You hurriedly slip on some " + lower.insert() + ", quick to cover your " + undies.insert() + ",", "not even worrying about what covers your chest.");
    }
    } else
    {
    if (!undies.isMissing()) //Undies only
    {
    setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You hurriedly slip on " + undies.insert() + ",", "not even worrying about what covers your chest and legs.");
    } else //No clothes at all
    {
    setText("Wh-what? Did I forget to set my alarm?!", "You cry, tumbling out of bed and feeling an instant jolt from your bladder.", "You are running downstairs fully naked.");
    }
    }
    passTime((byte) 1, );
    //Setting the next stage to "Leaving home"
    setNextStage(LEAVE_HOME);
    break;
    case LEAVE_HOME:
    setText("Just looking at the clock again in disbelief adds a redder tint to your cheeks.", "", "Paying much less attention to your daily routine, you quickly run down the stairs, get a small glass of orange juice and chug it.", "", "The cold drink brings a chill down your spine as you collect your things and rush out the door to school.");
    passTime((byte) 1, );
    offsetEmbarassment(3, );
    offsetBelly(10, );
    setNextStage(GO_TO_CLASS);
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
    setText("You rush into class, your " + lower.insert() + " blowing in the wind.", "", "Normally, you'd be worried your " + undies.insert() + " would be seen, but you can't worry about it right now.", "You make it to your seat without a minute to spare.");
    } else
    {
    //Nothing is blowing in wind
    setText("Trying your best to make up lost time, you rush into class and sit down to your seat without a minute to spare.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("You rush into class; your classmates are looking at your " + undies.insert() + ".", "You can't understand how you forgot to even put on any lower clothing,", "and you know that your " + undies.insert() + " have definitely been seen.", "You make it to your seat without a minute to spare.");
    } else
    {
    if (isFemale())
    {
    setText("You rush into class; your classmates are looking at your pussy and boobs.", "Guys are going mad and doing nothing except looking at you.", "You can't understand how you dared to come to school naked.", "You make it to your seat without a minute to spare.");
    } else
    {
    setText("You rush into class; your classmates are looking at your penis.", "Girls are really going mad and doing nothing except looking at you.", "You can't understand how you dared to come to school naked.", "You make it to your seat without a minute to spare.");
    }
    }
    }
    offsetEmbarassment(2, );
    setNextStage(WALK_IN);
    break;
    case WALK_IN:
    //If lower clothes is a skirt
    if (lower.insert().equals("skirt") || lower.insert().equals("skirt and tights") || lower.insert().equals("skirt and tights"))
    {
    setLinesAsDialogue(1, 3);
    setText("Next time you run into class, " + name + ",", "your teacher says,", "make sure you're wearing something less... revealing!", "A chuckle passes over the classroom, and you can't help but feel a", "tad bit embarrassed about your rush into class.");
    offsetEmbarassment(5, );
    } else //No outerwear
    {
    if (lower.isMissing())
    {
    setLinesAsDialogue(1);
    setText("WHAT!? YOU CAME TO SCHOOL NAKED!?", "your teacher shouts in disbelief.", "", "A chuckle passes over the classroom, and you can't help but feel extremely embarrassed", "about your rush into class, let alone your nudity");
    offsetEmbarassment(25, );
    } else
    {
    setLinesAsDialogue(1, 3);
    setText("Sit down, " + name + ". You're running late.", "your teacher says,", "And next time, don't make so much noise entering the classroom!", "A chuckle passes over the classroom, and you can't help but feel a tad bit embarrassed", "about your rush into class.");
    }
    }
    setNextStage(SIT_DOWN);
    break;
    case SIT_DOWN:
    setText("Subconsciously rubbing your thighs together, you feel the uncomfortable feeling of", "your bladder filling as the liquids you drank earlier start to make their way down.");
    passTime();
    setNextStage(ASK_ACTION);
    score("Embarassment at start - " + incontinence + " pts", '+', embarassment);
    break;
    case ASK_ACTION:
    if (gotCalledByTeacher())
    {
    break;
    }
    displayDesperationStatus();
    offerHoldingChoices();
    showActionUI("What now?");
    //Loading the choice array into the action selector
    setNextStage(CHOSE_ACTION);
    passTime();
    //Don't go further if player selected no or unavailable action
    //                }while(listChoice.isSelectionEmpty()||listChoice.getSelectedValue().equals("[Unavailable]"));
    break;
    case CHOSE_ACTION:
    if (inappropriateSelection())
    {
    setNextStage(ASK_ACTION);
    break;
    }
    //Hiding the action selector and doing action job
    switch (hideActionUI())
    {
    //Ask the teacher to go pee
    case 0:
    setNextStage(ASK_TO_PEE);
    setLinesAsDialogue(2, 3);
    setText("You think to yourself:", "I don't think I can hold it until class ends!", "I don't have a choice, I have to ask the teacher...");
    break;
    
    //                 * Press on crotch/squeeze penis
    //                 * 3 minutes
    //                 * -2 bladder
    //                 * Detection chance: 15
    //                 * Effectiveness: 0.4
    //                 * =========================
    //                 * 3 minutes
    //                 * +20 sph. power
    //                 * Detection chance: 15
    //                 * Future effectiveness: 4
    
    case 1:
    setText("You don't think anyone will see you doing it,", "so you take your hand and hold yourself down there.", "It feels a little better for now.");
    rechargeSphPower(20, );
    offsetTime(3, );
    getCaughtByClassmates();
    break;
    
    //                 * Rub thighs
    //                 * 3 + 3 = 6 minutes
    //                 * -0.2 bladder
    //                 * Detection chance: 3
    //                 * Effectiveness: 6
    //                 * =========================
    //                 * 3 + 3 = 6 minutes
    //                 * +2 sph. power
    //                 * Detection chance: 3
    //                 * Future effectiveness: 4
    //
    case 2:
    setText("You need to go, and it hurts, but you just", "can't bring yourself to risk getting caught with your hand between", "your legs. You rub your thighs hard but it doesn't really help.");
    rechargeSphPower(2, );
    offsetTime(3, );
    //Chance to be caught by classmates in hardcore mode
    getCaughtByClassmates();
    break;
    //Give up
    case 3:
    setText("You're absolutely desperate to pee, and you think you'll", "end up peeing yourself anyway, so it's probably best to admit", "defeat and get rid of the painful ache in your bladder.");
    setNextStage(GIVE_UP);
    break;
    //Drink water
    case 4:
    setText("Feeling a tad bit thirsty,", "You decide to take a small sip of water from your bottle to get rid of it.");
    setNextStage(DRINK);
    break;
    
    //                 * Wait
    //                 * =========================
    //                 * 3 + 2 + n minutes
    //                 * +(2.5n) bladder
    //                 * Detection chance: 1
    //                 * Future effectiveness: 2.4(1), 0.4(2), 0.47(30)
    
    case 5:
    byte timeOffset;
    //Asking player how much to wait
    try
    {
    timeOffset = askPlayerHowMuchToWait();
    } //Ignoring invalid output
    catch (NumberFormatException | NullPointerException e)
    {
    setNextStage(ASK_ACTION);
    break;
    }
    passTime(timeOffset, );
    //Chance to be caught by classmates in hardcore mode
    getCaughtByClassmates();
    break;
    //Cheat
    case 6:
    setText("You've got to go so bad!", "There must be something you can do, right?");
    //Zeroing points
    cheatsUsed = true;
    setNextStage(ASK_CHEAT);
    break;
    case -1:
    default:
    setText("Bugs.");
    }
    break;
    //TODO: Refactor
    case ASK_TO_PEE:
    switch (timesPeeDenied)
    {
    case 0:
    //Success
    if (NarrativeEngine.chance((byte) 40) & !hardcore)
    {
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
    }
    }
    //                            score *= 0.2;
    //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -80% of points");
    score("Restroom usage during the lesson", '/', 5);
    emptyBladder();
    setNextStage(ASK_ACTION);
    //Fail
    } else
    {
    setText("You ask the teacher if you can go out to the restroom.", "No, you can't go out, the director prohibited it.", "says the teacher.");
    timesPeeDenied++;
    }
    break;
    case 1:
    if (NarrativeEngine.RANDOM.nextInt(100) <= 10 & !hardcore)
    {
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
    }
    }
    //                            score *= 0.22;
    //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -70% of points");
    score("Restroom usage during the lesson", '/', 3.3F);
    emptyBladder();
    setNextStage(ASK_ACTION);
    } else
    {
    setText("You ask the teacher again if you can go out to the restroom.", "No, you can't! I already told you that the director prohibited it!", "says the teacher.");
    timesPeeDenied++;
    }
    break;
    case 2:
    if (NarrativeEngine.RANDOM.nextInt(100) <= 30 & !hardcore)
    {
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You ask the teacher again if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
    }
    }
    //                            score *= 0.23;
    //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -60% of points");
    score("Restroom usage during the lesson", '/', 2.5F);
    emptyBladder();
    setNextStage(ASK_ACTION);
    } else
    {
    setText("You ask the teacher once more if you can go out to the restroom.", "No, you can't! Stop asking me or there will be consequences!", "says the teacher.");
    timesPeeDenied++;
    }
    break;
    case 3:
    if (NarrativeEngine.RANDOM.nextInt(100) <= 7 & !hardcore)
    {
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("Desperately, you ask the teacher if you can go out to the restroom.", "Yes, you may.", "says the teacher. You run to the restroom. Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
    }
    }
    //                            score *= 0.3;
    //                            scoreText = scoreText.concat("\nRestroom usage during the lesson: -50% of points");
    score("Restroom usage during the lesson", '/', 2F);
    emptyBladder();
    setNextStage(ASK_ACTION);
    } else
    {
    if (NarrativeEngine.RANDOM.nextBoolean())
    {
    setText("Desperately, you ask the teacher if you can go out to the restroom.", "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! STAY IN THAT CORNER!!!,", "yells the teacher.");
    cornered = true;
    //                            score += 1.3 * (90 - min / 3);
    //                            scoreText = scoreText.concat("\nStayed on corner " + (90 - min) + " minutes: +" + 1.3 * (90 - min / 3) + " score");
    score("Stayed on corner " + (90 - time) + " minutes", '+', 1.3F * (90 - time / 3));
    offsetEmbarassment(5, );
    } else
    {
    setText("Desperately, you ask the teacher if you can go out to the restroom.", "NO!! NO!! NO!!! YOU CAN'T GO OUT!!! YOU WILL WRITE LINES AFTER THE LESSON!!!,", "yells the teacher.");
    offsetEmbarassment(5, );
    stay = true;
    timeBar.setMaximum(120);
    //                            scoreText = scoreText.concat("\nWrote lines after the lesson: +60% score");
    //                            score *= 1.6;
    score("Wrote lines after the lesson", '*', 1.6F);
    }
    }
    timesPeeDenied++;
    break;
    }
    setNextStage(ASK_ACTION);
    break;
    case ASK_CHEAT:
    listChoice.setListData(cheatList);
    showActionUI("Select a cheat:");
    setNextStage(CHOSE_CHEAT);
    break;
    case CHOSE_CHEAT:
    if (inappropriateSelection())
    {
    setNextStage(ASK_CHEAT);
    break;
    }
    switch (hideActionUI())
    {
    case 0:
    setText("You walk to the front corner of the classroom.");
    cornered = true;
    setNextStage(ASK_ACTION);
    break;
    case 1:
    setText("You decide to stay after class.");
    stay = true;
    timeBar.setMaximum(120);
    setNextStage(ASK_ACTION);
    break;
    case 2:
    setText("You see something out of the corner of your eye,", "just within your reach.");
    setNextStage(USE_BOTTLE);
    break;
    case 3:
    setLinesAsDialogue(2);
    setText("A voice comes over the loudspeaker:", "All classes are now dismissed for no reason at all! Bye!", "Looks like your luck changed for the better.");
    time = 89;
    setNextStage(CLASS_OVER);
    break;
    case 4:
    setText("The teacher feels sorry for you. Try asking to pee.");
    timesPeeDenied = 0;
    stay = false;
    timeBar.setMaximum(90);
    cornered = false;
    setNextStage(ASK_ACTION);
    break;
    case 5:
    setText("You decide to raise your hand.");
    setNextStage(CALLED_ON);
    break;
    case 6:
    setText("Suddenly, you feel like you're peeing...", "but you don't feel any wetness. It's not something you'd", "want to question, right?");
    drain = true;
    setNextStage(ASK_ACTION);
    break;
    case 7:
    setText("A friend in the desk next to you hands you a familiar", "looking pill, and you take it.");
    askNewIncontinenceLevel();
    setNextStage(ASK_ACTION);
    break;
    case 8:
    setText("The teacher suddenly looks like they've had enough", "of people having to pee.");
    hardcore = !hardcore;
    setNextStage(ASK_ACTION);
    break;
    case 9:
    setText("Suddenly you felt something going on in your bladder.");
    askNewBladderLevel();
    setNextStage(ASK_ACTION);
    break;
    }
    break;
    case USE_BOTTLE:
    setLinesAsDialogue(3);
    setText("Luckily for you, you happen to have brought an empty bottle to pee in.", "As quietly as you can, you put it in position and let go into it.", "Ahhhhh...", "You can't help but show a face of pure relief as your pee trickles down into it.");
    emptyBladder();
    setNextStage(ASK_ACTION);
    break;
    case CALLED_ON:
    setLinesAsDialogue(1);
    setText("" + name + ", why don't you come up to the board and solve this problem?,", "says the teacher. Of course, you don't have a clue how to solve it.", "You make your way to the front of the room and act lost, knowing you'll be stuck", "up there for a while as the teacher explains it.", "Well, you can't dare to hold yourself now...");
    passTime((byte) 5, );
    score("Called on the lesson", '+', 5);
    setNextStage(ASK_ACTION);
    break;
    case CLASS_OVER:
    if (triggerClsasOverScene())
    {
    break;
    }
    if (NarrativeEngine.RANDOM.nextBoolean())
    {
    setText("Lesson is finally over, and you're running to the restroom as fast as you can.", "No, please... All cabins are occupied, and there's a line. You have to wait!");
    score("Waited for a free cabin in the restroom", '+', 3);
    passTime();
    break;
    } else
    {
    //TODO: Refactor
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("Lesson is over, and you're running to the restroom as fast as you can.", "Thank god, one cabin is free!", "You enter it,", "wearily flop down on the toilet and start peeing.");
    }
    }
    setNextStage(END_GAME);
    }
    break;
    case AFTER_CLASS:
    if (linesAreTooLong())
    {
    break;
    }
    setLinesAsDialogue(1, 2, 3, 4);
    setText("Hey, " + name + ", you wanted to escape? You must stay after classes!", "Please... let me go to the restroom... I can't hold it...", "No, " + name + ", you can't go to the restroom now! This will be as punishment.", "And don't think you can hold yourself either! I'm watching you...");
    passTime();
    break;
    case ACCIDENT:
    hideActionUI();
    setText("You can't help it.. No matter how much pressure you use, the leaks won't stop.", "Despite all this, you try your best, but suddenly you're forced to stop.", "You can't move, or you risk peeing yourself. Heck, the moment you stood up you thought you could barely move for risk of peeing everywhere.", "But now.. a few seconds tick by as you try to will yourself to move, but soon, the inevitable happens anyways.");
    setNextStage(WET);
    break;
    case GIVE_UP:
    //TODO: Refactor
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("You get tired of holding all the urine in your aching bladder,", "and you decide to give up and pee in your " + undies.insert() + ".");
    } else
    {
    setText("You get tired of holding all the urine in your aching bladder,", "and you decided to pee in your " + lower.insert() + ".");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("You get tired of holding all the urine in your aching bladder,", "and you decide to give up and pee in your " + undies.insert() + ".");
    } else
    {
    setText("You get tired of holding all the urine in your aching bladder,", "and you decide to give up and pee where you are.");
    }
    }
    offsetEmbarassment(80, );
    setNextStage(WET);
    break;
    case WET:
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("Before you can move an inch, pee quickly soaks through your " + undies.insert() + ",", "floods your " + lower.insert() + ", and streaks down your legs.", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
    } else
    {
    setText("Before you can move an inch, pee quickly darkens your " + lower.insert() + " and streaks down your legs.", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("Before you can move an inch, pee quickly soaks through your " + undies.insert() + ", and streaks down your legs.", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
    } else
    {
    if (!cornered)
    {
    setText("The heavy pee jets are hitting the seat and loudly leaking out from your " + undies.insert() + ".", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
    } else
    {
    setText("The heavy pee jets are hitting the floor and loudly leaking out from your " + undies.insert() + ".", "A large puddle quickly forms, and you can't stop tears from falling down your cheeks.");
    }
    }
    }
    emptyBladder();
    embarassment = 100;
    setNextStage(POST_WET);
    break;
    case POST_WET:
    setLinesAsDialogue(2);
    if (!stay)
    {
    if (lower.isMissing())
    {
    if (isFemale() && undies.isMissing())
    {
    setText("People around you are laughing loudly.", name + " peed herself! Ahaha!!!");
    } else
    {
    if (isMale() && undies.isMissing())
    {
    setText("People around you are laughing loudly.", name + " peed himself! Ahaha!!!");
    } else
    {
    setText("People around you are laughing loudly.", name + " wet h" + (isFemale() ? "er " : "is ") + undies.insert() + "! Ahaha!!");
    }
    }
    } else
    {
    if (isFemale())
    {
    setText("People around you are laughing loudly.", name + " peed her " + lower.insert() + "! Ahaha!!");
    } else
    {
    setText("People around you are laughing loudly.", " peed his " + lower.insert() + "! Ahaha!!");
    }
    }
    } else
    {
    setText("Teacher is laughing loudly.", "Oh, you peed yourself? This is a great punishment.", "I hope you will no longer get in the way of the lesson.");
    }
    setNextStage(GAME_OVER);
    break;
    case GAME_OVER:
    if (lower.isMissing())
    {
    if (undies.isMissing())
    {
    setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
    } else
    {
    setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "Your " + undies.insert() + " are clinging to your skin, a sign of your failure...", "...unless, of course, you meant for this to happen?", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
    }
    } else
    {
    if (undies.isMissing())
    {
    setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "Your " + lower.insert() + " is clinging to your skin, a sign of your failure...", //TODO: Add "is/are" depending on lower clothes type
    "...unless, of course, you meant for this to happen?", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
    } else
    {
    setText("No matter how hard you tried... It doesn't seem to matter, even to think about it...", "Your " + lower.insert() + " and " + undies.insert() + " are both clinging to your skin, a sign of your failure...", "...unless, of course, you meant for this to happen?", "No, nobody would be as sadistic as that, especially to themselves...", "Game over!");
    }
    }
    gameOver();
    break;
    case END_GAME:
    showScore();
    gameOver();
    break;
    case CAUGHT:
    switch (timesCaught)
    {
    case 0:
    setText("It looks like a classmate has spotted that you've got to go badly.", "Damn, he may spread that fact...");
    offsetEmbarassment(3, );
    classmatesAwareness += 5;
    score("Caught holding pee", '+', 3);
    timesCaught++;
    break;
    case 1:
    setLinesAsDialogue(3);
    setText("You'he heard a suspicious whisper behind you.", "Listening to the whisper, you've found out that they're saying that you need to go.", "If I hold it until the lesson ends, I will beat them.");
    offsetEmbarassment(8, );
    classmatesAwareness += 5;
    score("Caught holding pee", '+', 8);
    timesCaught++;
    break;
    case 2:
    if (isFemale())
    {
    setLinesAsDialogue(2);
    setText("The most handsome boy in your class, " + boyName + ", is calling you:", "Hey there, don't wet yourself!", "Oh no, he knows it...");
    } else
    {
    setLinesAsDialogue(2, 3);
    setText("The most nasty boy in your class, " + boyName + ", is calling you:", "Hey there, don't wet yourself! Ahahahaa!", "\"Shut up...\"", ", you think to yourself.");
    }
    offsetEmbarassment(12, );
    classmatesAwareness += 5;
    score("Caught holding pee", '+', 12);
    timesCaught++;
    break;
    default:
    setText("The chuckles are continiously passing over the classroom.", "Everyone is watching you.", "Oh god... this is so embarassing...");
    offsetEmbarassment(20, );
    classmatesAwareness += 5;
    score("Caught holding pee", '+', 20);
    timesCaught++;
    }
    setNextStage(ASK_ACTION);
    break;
    //The special hardcore scene
    
    //         * "Surprise" is an additional scene after the lesson where player is being caught by her classmate. He wants her to wet herself.
    //         * Triggering conditions: female, hardcore
    //         * Triggering chance: 10%
    
    case SURPRISE:
    //Resetting timesPeeDenied to use for that boy
    timesPeeDenied = 0;
    specialHardcoreStage = true;
    score("Got the \"surprise\" by " + boyName, '+', 70);
    setText("The lesson is finally over, and you're running to the restroom as fast as you can.", "But... You see " + boyName + " staying in front of the restroom.", "Suddenly, he takes you, not letting you to escape.");
    offsetEmbarassment(10, );
    setNextStage(SURPRISE_2);
    break;
    case SURPRISE_2:
    setLinesAsDialogue(1);
    setText("What do you want from me?!", "He has brought you in the restroom and quickly put you on the windowsill.", boyName + " has locked the restroom door (seems he has stolen the key), then he puts his palm on your belly and says:", "I want you to wet yourself.");
    offsetEmbarassment(10, );
    setNextStage(SURPRISE_DIALOGUE);
    break;
    case SURPRISE_DIALOGUE:
    setLinesAsDialogue(1);
    setText("No, please, don't do it, no...", "I want to see you wet...", "He slightly presses your belly again, you shook from the terrible pain", "in your bladder and subconsciously rubbed your crotch. You have to do something!");
    offsetEmbarassment(10, );
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
    showActionUI("Don't let him to do it!");
    setNextStage(SURPRISE_CHOSE);
    break;
    case SURPRISE_CHOSE:
    if (listChoice.isSelectionEmpty())
    {
    //No idling
    setText("You will wet yourself right now,", boyName + " demands.", "Then " + boyName + " presses your bladder...");
    setNextStage(SURPRISE_WET_PRESSURE);
    }
    //                actionNum = listChoice.getSelectedIndex();
    if (listChoice.getSelectedValue().equals("[Unavailable]"))
    {
    //No idling
    setText("You will wet yourself right now,", boyName + " demands.", "Then " + boyName + " presses your bladder...");
    setNextStage(SURPRISE_WET_PRESSURE);
    }
    switch (hideActionUI())
    {
    case 0:
    setNextStage(HIT);
    break;
    case 1:
    setNextStage(PERSUADE);
    break;
    case 2:
    setNextStage(SURPRISE_WET_VOLUNTARY);
    }
    break;
    case HIT:
    if (NarrativeEngine.RANDOM.nextInt(100) <= 20)
    {
    setLinesAsDialogue(2);
    setNextStage(END_GAME);
    score("Successful hit on " + boyName + "'s groin", '+', 40F);
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it, pulled down your " + lower.insert() + " and " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it, pulled down your " + lower.insert() + ",", "wearily flop down on the toilet and start peeing.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it, pulled down your " + undies.insert() + ",", "wearily flop down on the toilet and start peeing.");
    } else
    {
    setText("You hit " + boyName + "'s groin.", "Ouch!.. You, little bitch...", "Then he left the restroom quickly.", "You got off from the windowsill while holding your crotch,", "opened the cabin door, entered it,", "wearily flop down on the toilet and start peeing.");
    }
    }
    } else
    {
    setNextStage(SURPRISE_WET_PRESSURE);
    setLinesAsDialogue(2, 3);
    setText("You hit " + boyName + "'s hand. Damn, you'd meant to hit his groin...", "You're braver than I expected;", "now let's check the strength of your bladder!", boyName + " pressed your bladder violently...");
    }
    break;
    case PERSUADE:
    switch (timesPeeDenied)
    {
    case 0:
    if (NarrativeEngine.RANDOM.nextInt(100) <= 10)
    {
    setLinesAsDialogue(1);
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + " and " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    } else
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    } else
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    }
    }
    score("Persuaded " + boyName + " to pee", '+', 40);
    emptyBladder();
    setNextStage(END_GAME);
    } else
    {
    setText("You ask " + boyName + " if you can pee.", "No, you can't pee in a cabin. I want you to wet yourself.,", boyName + " says.");
    timesPeeDenied++;
    setNextStage(SURPRISE_DIALOGUE);
    }
    break;
    case 1:
    if (NarrativeEngine.RANDOM.nextInt(100) <= 5)
    {
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + " and " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    } else
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    } else
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    }
    }
    score("Persuaded " + boyName + " to pee", '+', 60);
    emptyBladder();
    setNextStage(END_GAME);
    } else
    {
    setText("You ask " + boyName + " if you can pee again.", "No, you can't pee in a cabin. I want you to wet yourself. You're doing it now.", boyName + " demands.");
    timesPeeDenied++;
    setNextStage(SURPRISE_DIALOGUE);
    }
    break;
    case 2:
    if (NarrativeEngine.RANDOM.nextInt(100) <= 2)
    {
    if (!lower.isMissing())
    {
    if (!undies.isMissing())
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + " and " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    } else
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + lower.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    }
    } else
    {
    if (!undies.isMissing())
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "pull down your " + undies.insert() + ",", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    } else
    {
    setText("Ok, you may, but you'll let me watch you pee.", "states " + boyName + ". You enter the cabin,", "stand over the toilet and start peeing under " + boyName + "'s spectation.");
    }
    }
    score("Persuaded " + boyName + " to pee", '+', 80);
    emptyBladder();
    setNextStage(END_GAME);
    } else
    {
    setText("You ask " + boyName + " if you can pee again desperately.", "No, you can't pee in a cabin. You will wet yourself right now,", boyName + " demands.", "Then " + boyName + " pressed your bladder...");
    setNextStage(SURPRISE_WET_PRESSURE);
    }
    break;
    }
    break;
    case SURPRISE_WET_VOLUNTARY:
    setLinesAsDialogue(1, 3);
    setText("Alright, as you say.,", "you say to " + boyName + " with a defeated sigh.", "Whatever, I really can't hold it anymore anyways...");
    emptyBladder();
    setNextStage(SURPRISE_WET_VOLUNTARY2);
    break;
    case SURPRISE_WET_VOLUNTARY2:
    if (!undies.isMissing())
    {
    if (!lower.isMissing())
    {
    setText("You feel the warm pee stream", "filling your " + undies.insert() + " and darkening your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    } else
    {
    setText("You feel the warm pee stream", "filling your " + undies.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    }
    } else
    {
    if (!lower.isMissing())
    {
    setText("You feel the warm pee stream", "filling your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    } else
    {
    setText("You feel the warm pee stream", "running down your legs.", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    }
    }
    emptyBladder();
    setNextStage(END_GAME);
    break;
    case SURPRISE_WET_PRESSURE:
    if (!undies.isMissing())
    {
    if (!lower.isMissing())
    {
    setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "filling your " + undies.insert() + " and darkening your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    } else
    {
    setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "filling your " + undies.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    }
    } else
    {
    if (!lower.isMissing())
    {
    setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "filling your " + lower.insert() + ".", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    } else
    {
    setText("Ouch... The sudden pain flash passes through your bladder...", "You try to hold the pee back, but you just can't.", "You feel the warm pee stream", "running down your legs.", "You close your eyes and ease your sphincter off.", "You feel the pee stream become much stronger.");
    }
    }
    emptyBladder();
    setNextStage(END_GAME);
    break;
    case DRINK:
    setText("You take your bottle with water,", "open it and take a small sip of water.");
    offsetBelly(thirst, );
    thirst = 0;
    setNextStage(ASK_ACTION);
    break;
    default:
    setText("Error parsing button. Next text is unavailable, text #" + nextStage);
    break;
    //case template
    //      case 4:
    //   setText("");
    //   setNextStage(;
    //   break;
    }
    }
    */
//</editor-fold>
    
    public void update()
    {
        try
        {
            lblName.setText(getName());
            lblBladder.setText("Bladder: " + Math.round(getFulness()) + "%");
            lblEmbarassment.setText("Embarassment: " + getEmbarassment());
            lblBelly.setText("Belly: " + Math.round(getBelly()) + "%");
            lblIncon.setText("Incontinence: " + getIncontinence() + "x");
            lblMinutes.setText("Minutes: " + getTime() + " of 90");
            lblSphPower.setText("Pee holding ability: " + Math.round(getSphincterPower()) + "%");
            lblDryness.setText("Clothes dryness: " + Math.round(getDryness()));
            lblUndies.setText("Undies: " + getUndies().getColor() + " " + getUndies().getName().toLowerCase());
            lblLower.setText("Lower: " + getLower().getColor() + " " + getLower().getName().toLowerCase());
            bladderBar.setValue(getFulness());
            sphincterBar.setValue(Math.round(getSphincterPower()));
            drynessBar.setValue((int) getDryness());
            timeBar.setValue(getTime());
            lblThirst.setText("Thirst: " + Math.round(getThirst()) + "%");
            thirstBar.setValue((int) getThirst());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void gameOver()
    {
        btnNext.setVisible(false);
    }

    byte hideActionUI()
    {
        byte choice = (byte) listChoice.getSelectedIndex();
        getActionList().clear();
        lblChoice.setVisible(false);
        listScroller.setVisible(false);
        return choice;
    }

    byte askPlayerHowMuchToWait() throws NumberFormatException
    {
        byte timeOffset;
        timeOffset = Byte.parseByte(JOptionPane.showInputDialog("How much to wait?"));
        if (getTime() < 1 || getTime() > 125)
        {
            throw new NumberFormatException();
        }
        return timeOffset;
    }

    private void askNewIncontinenceLevel() throws NumberFormatException
    {
        float newIncontinence = Float.parseFloat(JOptionPane.showInputDialog("How incontinent are you now?"));
        if (newIncontinence >= 0.1)
        {
            setIncontinence(newIncontinence);
            setMaxSphincterPower((short) (100 / getIncontinence()));
            setSphincterPower(getMaxSphincterPower());
        }
    }

    public void setup()
    {
        //Game window setup
        setResizable(true);
        setTitle("A Long Hour and a Half");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 770, 594);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.LIGHT_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //Text panel setup
        textPanel = new JPanel();
        textPanel.setBounds(10, 11, 740, 150);
        contentPane.add(textPanel);
        textPanel.setLayout(null);
        textLabel = new JLabel("");
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setBounds(0, 0, 740, 150);
        textPanel.add(textLabel);
        //"Next" button setup
        btnNext = new JButton("Next");
        //        btnNext.setToolTipText("Hold down for the time warp");
        btnNext.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                //TODO: Handle "Next" button clicks
            }
        });
        btnNext.setBounds(470, GameFrame.ACTION_BUTTONS_TOP_BORDER, 285, 35);
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
        btnQuit.setBounds(192, GameFrame.ACTION_BUTTONS_TOP_BORDER, GameFrame.ACTION_BUTTONS_WIDTH, GameFrame.ACTION_BUTTONS_HEIGHT);
        contentPane.add(btnQuit);
        //"Save" button setup
        btnSave = new JButton("Save");
        btnSave.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.save(GameFrame.this);
            }
        });
        btnSave.setBounds(284, GameFrame.ACTION_BUTTONS_TOP_BORDER, GameFrame.ACTION_BUTTONS_WIDTH, GameFrame.ACTION_BUTTONS_HEIGHT);
        contentPane.add(btnSave);
        //"Load" button setup
        btnLoad = new JButton("Load");
        btnLoad.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.load(GameFrame.this);
            }
        });
        btnLoad.setBounds(376, GameFrame.ACTION_BUTTONS_TOP_BORDER, GameFrame.ACTION_BUTTONS_WIDTH, GameFrame.ACTION_BUTTONS_HEIGHT);
        contentPane.add(btnLoad);
        //"Reset" button setup
        btnReset = new JButton("Reset");
        btnReset.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.reset(false, GameFrame.this);
                dispose();
            }
        });
        btnReset.setBounds(10, GameFrame.ACTION_BUTTONS_TOP_BORDER, GameFrame.ACTION_BUTTONS_WIDTH, GameFrame.ACTION_BUTTONS_HEIGHT);
        btnReset.setToolTipText("Start the game over with the same parameters.");
        contentPane.add(btnReset);
        //"New game" button setup
        btnNewGame = new JButton("New game");
        btnNewGame.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent arg0)
            {
                GameCore.reset(true, GameFrame.this);
                dispose();
            }
        });
        btnNewGame.setBounds(102, GameFrame.ACTION_BUTTONS_TOP_BORDER, GameFrame.ACTION_BUTTONS_WIDTH, GameFrame.ACTION_BUTTONS_HEIGHT);
        btnNewGame.setToolTipText("Start the game over with the another parameters.");
        contentPane.add(btnNewGame);
        //Name label setup
        lblName = new JLabel(NarrativeEngine.getName());
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblName.setBounds(20, 170, 200, 32);
        contentPane.add(lblName);
        //Bladder label setup
        lblBladder = new JLabel("Bladder: " + Math.round(Bladder.getFulness()) + "%");
        lblBladder.setToolTipText("<html>Normal game:<br>100% = need to hold, regular leaks<br>130% = peeing(game over)<br><br>Hardcore:<br>80% = need to hold, regular leaks<br>100% = peeing(game over)</html>");
        lblBladder.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBladder.setBounds(20, 210, 200, 32);
        contentPane.add(lblBladder);
        lblDefaultColor = lblBladder.getForeground();
        //Embarassment label setup
        lblEmbarassment = new JLabel("Embarassment: " + getEmbarassment());
        lblEmbarassment.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEmbarassment.setBounds(20, 240, 200, 32);
        lblEmbarassment.setToolTipText("Makes leaks more frequent");
        contentPane.add(lblEmbarassment);
        //Belly label setup
        lblBelly = new JLabel("Belly: " + Math.round(getBelly()) + "%");
        lblBelly.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblBelly.setBounds(20, 270, 200, 32);
        lblBelly.setToolTipText("<html>The water in your belly.<br>Any amount of water speeds the bladder filling up.</html>");
        contentPane.add(lblBelly);
        //Thirst label setup
        lblThirst = new JLabel("Thirst: " + Math.round(getThirst()) + "%");
        lblThirst.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblThirst.setBounds(20, 480, 200, 32);
        lblThirst.setToolTipText("Character will automatically drink water at 30% of thirst.");
        if (isHardcore())
        {
            contentPane.add(lblThirst);
        }
        //Thirst bar setup
        thirstBar = new JProgressBar();
        thirstBar.setBounds(16, 482, 455, 25);
        thirstBar.setMaximum((int) MAXIMAL_THIRST);
        thirstBar.setValue((int) getThirst());
        thirstBar.setToolTipText("Character will automatically drink water at 30% of thirst.");
        if (isHardcore())
        {
            contentPane.add(thirstBar);
        }
        //Incontinence label setup
        lblIncon = new JLabel("Incontinence: " + getIncontinence() + "x");
        lblIncon.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblIncon.setBounds(20, 300, 200, 32);
        lblIncon.setToolTipText("Makes your bladder weaker");
        contentPane.add(lblIncon);
        //Time label setup
        lblMinutes = new JLabel("Minutes: " + getTime() + " of 90");
        lblMinutes.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblMinutes.setBounds(20, 330, 200, 32);
        lblMinutes.setVisible(false);
        contentPane.add(lblMinutes);
        //Sphincter power label setup
        lblSphPower = new JLabel("Pee holding ability: " + Math.round(getSphincterPower()) + "%");
        lblSphPower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblSphPower.setBounds(20, 360, 200, 32);
        lblSphPower.setVisible(false);
        lblSphPower.setToolTipText("<html>Ability to hold pee.<br>Drains faster on higher bladder fulnesses.<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thigs.</html>");
        contentPane.add(lblSphPower);
        //Clothing dryness label setup
        lblDryness = new JLabel("Clothes dryness: " + Math.round(getDryness()));
        lblDryness.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblDryness.setBounds(20, 390, 200, 32);
        lblDryness.setVisible(false);
        lblDryness.setToolTipText("<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>");
        contentPane.add(lblDryness);
        //Choice label ("What to do?") setup
        lblChoice = new JLabel();
        lblChoice.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblChoice.setBounds(480, 162, 280, 32);
        lblChoice.setVisible(false);
        contentPane.add(lblChoice);
        //Action list setup
        listChoice = new JList<>();
        listChoice.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listChoice.setLayoutOrientation(JList.VERTICAL);
        listScroller = new JScrollPane(listChoice);
        listScroller.setBounds(472, 194, 280, 318);
        listScroller.setVisible(false);
        contentPane.add(listScroller);
        //Bladder bar setup
        bladderBar = new JProgressBar();
        bladderBar.setBounds(16, 212, 455, 25);
        bladderBar.setMaximum(130);
        bladderBar.setValue(Bladder.getFulness());
        bladderBar.setToolTipText("<html>Normal game:<br>100% = need to hold, regular leaks<br>130% = peeing(game over)<br><br>Hardcore:<br>80% = need to hold, regular leaks<br>100% = peeing(game over)</html>");
        contentPane.add(bladderBar);
        //Sphincter bar setup
        sphincterBar = new JProgressBar();
        sphincterBar.setBounds(16, 362, 455, 25);
        sphincterBar.setMaximum(Math.round(getMaxSphincterPower()));
        sphincterBar.setValue(Math.round(getSphincterPower()));
        sphincterBar.setVisible(false);
        sphincterBar.setToolTipText("<html>Ability to hold pee.<br>Drains faster on higher bladder fulnesses.<br>Leaking when 0%.<br>Refill it by holding crotch and rubbing thigs.</html>");
        contentPane.add(sphincterBar);
        //Dryness bar setup
        drynessBar = new JProgressBar();
        drynessBar.setBounds(16, 392, 455, 25);
        drynessBar.setValue((int) getDryness());
        drynessBar.setMinimum(MINIMAL_DRYNESS);
        drynessBar.setVisible(false);
        drynessBar.setToolTipText("<html>Estimating dryness to absorb leaked pee.<br>Refills by itself with the time.</html>");
        contentPane.add(drynessBar);
        //Time bar setup
        timeBar = new JProgressBar();
        timeBar.setBounds(16, 332, 455, 25);
        timeBar.setMaximum(90);
        timeBar.setValue(getTime());
        timeBar.setVisible(false);
        contentPane.add(timeBar);
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
        if (getName().isEmpty())
        {
            if (isFemale())
            {
                setName("Mrs. Nobody");
            }
            else
            {
                setName("Mr. Nobody");
            }
        }
    }

    public void setupWearLabels()
    {
        //Undies label setup
        lblUndies = new JLabel("Undies: " + getUndies().getColor() + " " + getUndies().getName().toLowerCase());
        lblUndies.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblUndies.setBounds(20, 420, 400, 32);
        contentPane.add(lblUndies);
        //Lower label setup
        lblLower = new JLabel("Lower: " + getLower().getColor() + " " + getLower().getName().toLowerCase());
        lblLower.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblLower.setBounds(20, 450, 400, 32);
        contentPane.add(lblLower);
    }

    public void displayAllValues()
    {
        //Displaying all values
        lblMinutes.setVisible(true);
        lblSphPower.setVisible(true);
        lblDryness.setVisible(true);
        sphincterBar.setVisible(true);
        drynessBar.setVisible(true);
        timeBar.setVisible(true);
        handleNextClicked();
    }
}