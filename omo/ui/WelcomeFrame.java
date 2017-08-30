package omo.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame
{
    private WelcomeFrame()
    {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel gameName = new JLabel("A Long Hour and a Half");
        gameName.setFont(new Font("Segoe UI Light", Font.PLAIN, 32));
        add(gameName);

        JButton newGame = new JButton("New game");
        newGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showNameAndGenderDialog();
            }
        });
    }

    public static void main(String[] args)
    {

    }

    private void showNameAndGenderDialog()
    {

    }
}
