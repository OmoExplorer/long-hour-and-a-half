package omo.ui;

import omo.ALongHourAndAHalf;
import omo.Save;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@SuppressWarnings("CyclicClassDependency")
public class WelcomeFrame extends JFrame {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton exitButton;
    static final JFileChooser characterFC = new JFileChooser();
    static final JFileChooser saveFC = new JFileChooser();
    private JPanel cont;

    private WelcomeFrame() {
        $$$setupUI$$$();
        setContentPane(cont);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        JButton newGame = new JButton("New game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selection = JOptionPane.showOptionDialog(WelcomeFrame.this,
                        "Do you want to select an existing character or create a new one?",
                        "New game",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Existing", "New"},
                        "Existing");
                switch (selection) {
                    case JOptionPane.YES_OPTION:    //Existing
                        openExistingCharacter();
                        break;
                    case JOptionPane.NO_OPTION: //New
                        new CharacterCreator();
                }
            }
        });

        characterFC.setFileFilter(new FileNameExtensionFilter("A Long Hour and a Half Character", "lhhchr"));
        saveFC.setFileFilter(new FileNameExtensionFilter("A Long Hour and a Half Save", "lhhsav"));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        setNimbusSkin();
        EventQueue.invokeLater(() -> {
            new WelcomeFrame();
        });
    }

    private static void setNimbusSkin() {
        try {
//            throw new Exception();
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to set Nimbus UI skin.\n" +
                    "Update Java to fix this.", "", JOptionPane.WARNING_MESSAGE);
            System.err.println("Nimbus skin isn't available. Metal skin is used instead.");
        }
    }

    private void loadGame() {
        int result = saveFC.showOpenDialog(this);
        File file = saveFC.getSelectedFile();
        if (result == JFileChooser.APPROVE_OPTION && file != null) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                new ALongHourAndAHalf((Save) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Can't open the file.", "File error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openExistingCharacter() {
        int result = characterFC.showOpenDialog(this);
        File file = characterFC.getSelectedFile();
        if (result == JFileChooser.APPROVE_OPTION && file != null)
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                new CharacterSelect((omo.Character) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Can't open the file.", "File error", JOptionPane.ERROR_MESSAGE);
            }
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        cont = new JPanel();
        cont.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Welcome to A Long Hour and a Half");
        cont.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newGameButton = new JButton();
        newGameButton.setText("New game");
        cont.add(newGameButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadGameButton = new JButton();
        loadGameButton.setText("Load game");
        cont.add(loadGameButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setText("Exit");
        cont.add(exitButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return cont;
    }
}
