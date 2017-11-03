package longHourAndAHalf.ui;

import longHourAndAHalf.Character;
import longHourAndAHalf.Core;
import longHourAndAHalf.Gender;
import longHourAndAHalf.Wear;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.Random;

@SuppressWarnings({"CastToConcreteClass", "AnonymousInnerClassWithTooManyMethods"})
public class SetupFrame {
    private static final int RANDOM_FULLNESS_BOUND = 50;

    private JTextField nameTextField;
    private JRadioButton femaleGenderRadioButton;
    private JRadioButton hardDifficultyRadioButton;
    private JRadioButton randomFullnessRadioButton;
    private JSlider fullnessSlider;
    private JSlider incontinenceSlider;
    private JTree underwearTree;
    private JTree outerwearTree;
    private JButton startButton;

    SetupFrame() {
        //TODO
        //noinspection OverlyComplexAnonymousInnerClass
        //noinspection ClassWithTooManyFields
        underwearTree.setModel(new TreeModel() {
            @Override
            public Object getRoot() {
                return null;
            }

            @Override
            public Object getChild(Object parent, int index) {
                return null;
            }

            @Override
            public int getChildCount(Object parent) {
                return 0;
            }

            @Override
            public boolean isLeaf(Object node) {
                return false;
            }

            @Override
            public void valueForPathChanged(TreePath path, Object newValue) {

            }

            @Override
            public int getIndexOfChild(Object parent, Object child) {
                return 0;
            }

            @Override
            public void addTreeModelListener(TreeModelListener l) {

            }

            @Override
            public void removeTreeModelListener(TreeModelListener l) {

            }
        });

        //noinspection OverlyLongLambda
        startButton.addActionListener(e -> {
            Random random = new Random();

            Gender gender = femaleGenderRadioButton.isSelected() ? Gender.FEMALE : Gender.MALE;
            double fullness = randomFullnessRadioButton.isSelected() ?
                    random.nextInt(RANDOM_FULLNESS_BOUND) : fullnessSlider.getValue();

            boolean hardDifficulty = hardDifficultyRadioButton.isSelected();

            //noinspection ResultOfObjectAllocationIgnored,CastToConcreteClass,CastToConcreteClass
            new Core(
                    new Character(
                            nameTextField.getText(),
                            gender,
                            fullness,
                            incontinenceSlider.getValue(),
                            (Wear) underwearTree.getLastSelectedPathComponent(),
                            (Wear) outerwearTree.getLastSelectedPathComponent()
                    ),
                    hardDifficulty
            );
        });
    }

}
