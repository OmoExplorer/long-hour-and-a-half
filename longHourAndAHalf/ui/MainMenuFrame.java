package longHourAndAHalf.ui;

import longHourAndAHalf.ALongHourAndAHalf;
import longHourAndAHalf.Save;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

@SuppressWarnings("ResultOfObjectAllocationIgnored")
public final class MainMenuFrame {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton openWearEditorButton;

    @SuppressWarnings("WeakerAccess")
    MainMenuFrame() {
        newGameButton.addActionListener(e -> new SetupFrame());
        loadGameButton.addActionListener(e -> loadGame());
        openWearEditorButton.addActionListener(e -> new WearEditor());
    }

    private static void loadGame() {
        SaveFileChooser saveFileChooser = new SaveFileChooser();
        saveFileChooser.showOpenDialog(null);
        File saveFile = saveFileChooser.getSelectedFile();

        if (saveFile == null) return;
        try {
            FileInputStream fileInputStream = new FileInputStream(saveFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Save save = (Save) objectInputStream.readObject();

            new ALongHourAndAHalf(save);
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(null,
                    "Can't open the save.",
                    "",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MainMenuFrame();
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
}
