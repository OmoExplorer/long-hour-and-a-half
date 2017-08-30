package omo.ui;

import omo.Character;
import omo.GameCore;
import omo.Gender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterSelect extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel nameLabel;
    private JLabel genderLabel;
    private JLabel levelLabel;
    private JProgressBar progressBar;
    private JLabel xpLabel;

    public CharacterSelect(Character character) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        nameLabel.setText(character.getName());
        if (character.getGender() == Gender.FEMALE) {
            genderLabel.setText("🚺 Female");
        }else{
            genderLabel.setText("🚹 Male");
        }
        levelLabel.setText(Integer.toString(character.getLevel()));
        //TODO: progressBar
        xpLabel.setText(Integer.toString(character.getXp()));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(character);
            }

        });
    }

    private void onOK(Character character) {
        new GameCore(character);
        dispose();
    }
}
