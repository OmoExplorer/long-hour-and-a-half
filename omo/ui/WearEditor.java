/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omo.ui;

import omo.Wear;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import static omo.Wear.WearType.*;

/**
 *
 * @author JavaBird
 */
class WearEditor extends javax.swing.JFrame
{

    private static final long serialVersionUID = 1L;

    private final JFileChooser fc;
    Scanner fs;
//    PrintStream writer;
private Wear wear;

    /**
     * Creates new form WearEditor
     */
    private WearEditor()
    {
        fc = new JFileChooser();
//        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.fc.setFileFilter(new FileFilter()
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
                return extension.equals("lhhwear");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Custom wear";
            }
        });

        this.initComponents();
    }

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, `lesson.teacher.stay` with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException | InstantiationException ex) {
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() ->
        {
            new WearEditor().setVisible(true);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        this.nameLabel = new JLabel();
        this.nameField = new JTextField();
        this.typeLabel = new JLabel();
        this.typeComboBox = new JComboBox<>();
        this.pressureLabel = new JLabel();
        this.pressureSpinner = new JSpinner();
        this.absorptionLabe = new JLabel();
        this.absorptionSpinner = new JSpinner();
        this.dotLabel = new JLabel();
        this.dotSpinner = new JSpinner();
        this.saveButton = new JButton();
        this.openButton = new JButton();
        this.insertNameLabel = new JLabel();
        this.insertNameField = new JTextField();

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Wear editor");

        this.nameLabel.setText("Wear characterName");
        this.nameLabel.setToolTipText("Your wear characterName (e. g. \"Beautiful skirt\")");
        this.nameLabel.setName("nameLabel"); // NOI18N

        this.nameField.setToolTipText("Your wear characterName (e. g. \"Beautiful skirt\")");
        this.nameField.setName("nameField"); // NOI18N

        this.typeLabel.setText("Wear type");
        this.typeLabel.setToolTipText("Type of your wear: undies or lower.");
        this.typeLabel.setName("typeLabel"); // NOI18N

        this.typeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Underwear", "Outerwear", "Both are suitable"}));
        this.typeComboBox.setSelectedIndex(2);
        this.typeComboBox.setName("typeComboBox"); // NOI18N

        this.pressureLabel.setText("Pressure");
        this.pressureLabel.setToolTipText("<html>\nDecreases the maximal bladder capacity.<br>\n<b>1 point = -1% of max. bladder capacity.</b>\n</html>");
        this.pressureLabel.setName("pressureLabel"); // NOI18N

        this.pressureSpinner.setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(300.0f), Float.valueOf(1.0f)));
        this.pressureSpinner.setToolTipText("<html> Decreases the maximal bladder capacity.<br> <b>1 point = -1% of max. bladder capacity.</b> </html>");
        this.pressureSpinner.setName("pressureSpinner"); // NOI18N

        this.absorptionLabe.setText("Absorption");
        this.absorptionLabe.setToolTipText("<html>\nAbsorbs the leaked pee.<br>\n<b>1 point = 0.5% of pee.</b>\n</html>\n");
        this.absorptionLabe.setName("absorptionLabe"); // NOI18N

        this.absorptionSpinner.setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(300.0f), Float.valueOf(1.0f)));
        this.absorptionSpinner.setToolTipText("<html> Absorbs the leaked pee.<br> <b>1 point = 0.5% of pee.</b> </html> ");
        this.absorptionSpinner.setName("absorptionSpinner"); // NOI18N

        this.dotLabel.setText("Drying over `lesson.time`");
        this.dotLabel.setToolTipText("<html>\nSpeed of wear drying.<br>\n<b>1 point = -1% of absorbed pee per 3 minutes.</b>\n</html>");
        this.dotLabel.setName("dotLabel"); // NOI18N

        this.dotSpinner.setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(1.0f)));
        this.dotSpinner.setToolTipText("<html> Speed of wear drying.<br> <b>1 point = -1% of absorbed pee per 3 minutes.</b> </html>");
        this.dotSpinner.setName("dotSpinner"); // NOI18N

        this.saveButton.setText("Save...");
        this.saveButton.setName("saveButton"); // NOI18N
        this.saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                WearEditor.this.saveButtonActionPerformed(evt);
            }
        });

        this.openButton.setText("Open...");
        this.openButton.setName("openButton"); // NOI18N
        this.openButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                WearEditor.this.openButtonActionPerformed(evt);
            }
        });

        this.insertNameLabel.setText("Wear insert characterName");
        this.insertNameLabel.setToolTipText("Your wear characterName which is inserted in the game text (e. g. \"skirt\")");
        this.insertNameLabel.setName("insertNameLabel"); // NOI18N

        this.insertNameField.setToolTipText("Your wear characterName which is inserted in the game text (e. g. \"skirt\")");
        this.insertNameField.setName("insertNameField"); // NOI18N

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(this.nameLabel)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(nameField, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(insertNameLabel)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(insertNameField)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(typeLabel)
                        .addGap(18, 18, 18)
                            .addComponent(typeComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(pressureLabel)
                        .addGap(18, 18, 18)
                                    .addComponent(pressureSpinner, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(absorptionLabe)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(absorptionSpinner, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(dotLabel)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(dotSpinner, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(openButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(nameLabel)
                            .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(insertNameLabel)
                            .addComponent(insertNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(typeLabel)
                            .addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(pressureLabel)
                            .addComponent(pressureSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(absorptionLabe)
                            .addComponent(absorptionSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(dotLabel)
                            .addComponent(dotSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(this.saveButton)
                            .addComponent(this.openButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        this.pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(ActionEvent evt)//GEN-FIRST:event_saveButtonActionPerformed
    {//GEN-HEADEREND:event_saveButtonActionPerformed
        this.fc.setSelectedFile(new File(this.nameField.getText()));
        if (this.fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(this.fc.getSelectedFile().getAbsolutePath() + ".lhhwear");
            FileOutputStream fout;
            ObjectOutputStream oos;
            try
            {
                this.wear = new Wear(this.nameField.getText(), this.insertNameField.getText(), (float) this.pressureSpinner.getValue(), (float) this.absorptionSpinner.getValue(), (float) this.dotSpinner.getValue(), null);
                switch (this.typeComboBox.getSelectedIndex())
                {
                    case 0:
                        this.wear.setType(UNDERWEAR);
                        break;
                    case 1:
                        this.wear.setType(OUTERWEAR);
                        break;
                    case 2:
                        this.wear.setType(BOTH_SUITABLE);
                        break;
                }

//                writer = new PrintStream(file);
                fout = new FileOutputStream(file);
                oos = new ObjectOutputStream(fout);
                oos.writeObject(this.wear);
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(this, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void openButtonActionPerformed(ActionEvent evt)//GEN-FIRST:event_openButtonActionPerformed
    {//GEN-HEADEREND:event_openButtonActionPerformed
        if (this.fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File file = this.fc.getSelectedFile();
            try
            {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fin);
                this.wear = (Wear) ois.readObject();
            } catch (IOException | ClassNotFoundException e)
            {
                JOptionPane.showMessageDialog(this, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            nameField.setText(wear.getName());
            insertNameField.setText(wear.getInsert());
            pressureSpinner.setValue(wear.getPressure());
            absorptionSpinner.setValue(wear.getAbsorption());
            dotSpinner.setValue(wear.getDryingOverTime());

            switch (wear.getType())
            {
                case UNDERWEAR:
                    this.typeComboBox.setSelectedIndex(0);
                    break;
                case OUTERWEAR:
                    this.typeComboBox.setSelectedIndex(1);
                    break;
                case BOTH_SUITABLE:
                    this.typeComboBox.setSelectedIndex(2);
                    break;
            }
        }
    }//GEN-LAST:event_openButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel absorptionLabe;
    private JSpinner absorptionSpinner;
    private JLabel dotLabel;
    private JSpinner dotSpinner;
    private JTextField insertNameField;
    private JLabel insertNameLabel;
    private JTextField nameField;
    private JLabel nameLabel;
    private JButton openButton;
    private JLabel pressureLabel;
    private JSpinner pressureSpinner;
    private JButton saveButton;
    private JComboBox<String> typeComboBox;
    private JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
