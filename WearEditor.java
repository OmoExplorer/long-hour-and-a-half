/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import static omo.Wear.WearType.*;

/**
 *
 * @author JavaBird
 */
public class WearEditor extends javax.swing.JFrame
{

    private static final long serialVersionUID = 1L;

    private JFileChooser fc;
    private Scanner fs;
//    PrintStream writer;
    private Wear wear;

    /**
     * Creates new form WearEditor
     */
    public WearEditor()
    {
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
                return extension.equals("lhhwear");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Custom wear";
            }
        });

        initComponents();
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

        nameLabel = new JLabel();
        nameField = new JTextField();
        typeLabel = new JLabel();
        typeComboBox = new JComboBox<>();
        pressureLabel = new JLabel();
        pressureSpinner = new JSpinner();
        absorptionLabe = new JLabel();
        absorptionSpinner = new JSpinner();
        dotLabel = new JLabel();
        dotSpinner = new JSpinner();
        saveButton = new JButton();
        openButton = new JButton();
        insertNameLabel = new JLabel();
        insertNameField = new JTextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wear editor");

        nameLabel.setText("Wear name");
        nameLabel.setToolTipText("Your wear name (e. g. \"Beautiful skirt\")");
        nameLabel.setName("nameLabel"); // NOI18N

        nameField.setToolTipText("Your wear name (e. g. \"Beautiful skirt\")");
        nameField.setName("nameField"); // NOI18N

        typeLabel.setText("Wear type");
        typeLabel.setToolTipText("Type of your wear: undies or lower.");
        typeLabel.setName("typeLabel"); // NOI18N

        typeComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Underwear", "Outerwear", "Both are suitable" }));
        typeComboBox.setSelectedIndex(2);
        typeComboBox.setName("typeComboBox"); // NOI18N

        pressureLabel.setText("Pressure");
        pressureLabel.setToolTipText("<html>\nDecreases the maximal bladder capacity.<br>\n<b>1 point = -1% of max. bladder capacity.</b>\n</html>");
        pressureLabel.setName("pressureLabel"); // NOI18N

        pressureSpinner.setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(300.0f), Float.valueOf(1.0f)));
        pressureSpinner.setToolTipText("<html> Decreases the maximal bladder capacity.<br> <b>1 point = -1% of max. bladder capacity.</b> </html>");
        pressureSpinner.setName("pressureSpinner"); // NOI18N

        absorptionLabe.setText("Absorption");
        absorptionLabe.setToolTipText("<html>\nAbsorbs the leaked pee.<br>\n<b>1 point = 0.5% of pee.</b>\n</html>\n");
        absorptionLabe.setName("absorptionLabe"); // NOI18N

        absorptionSpinner.setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(300.0f), Float.valueOf(1.0f)));
        absorptionSpinner.setToolTipText("<html> Absorbs the leaked pee.<br> <b>1 point = 0.5% of pee.</b> </html> ");
        absorptionSpinner.setName("absorptionSpinner"); // NOI18N

        dotLabel.setText("Drying over time");
        dotLabel.setToolTipText("<html>\nSpeed of wear drying.<br>\n<b>1 point = -1% of absorbed pee per 3 minutes.</b>\n</html>");
        dotLabel.setName("dotLabel"); // NOI18N

        dotSpinner.setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(1.0f)));
        dotSpinner.setToolTipText("<html> Speed of wear drying.<br> <b>1 point = -1% of absorbed pee per 3 minutes.</b> </html>");
        dotSpinner.setName("dotSpinner"); // NOI18N

        saveButton.setText("Save...");
        saveButton.setName("saveButton"); // NOI18N
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                saveButtonActionPerformed(evt);
            }
        });

        openButton.setText("Open...");
        openButton.setName("openButton"); // NOI18N
        openButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                openButtonActionPerformed(evt);
            }
        });

        insertNameLabel.setText("Wear insert name");
        insertNameLabel.setToolTipText("Your wear name which is inserted in the game text (e. g. \"skirt\")");
        insertNameLabel.setName("insertNameLabel"); // NOI18N

        insertNameField.setToolTipText("Your wear name which is inserted in the game text (e. g. \"skirt\")");
        insertNameField.setName("insertNameField"); // NOI18N

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertNameLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(insertNameField)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(typeLabel)
                        .addGap(18, 18, 18)
                        .addComponent(typeComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pressureLabel)
                        .addGap(18, 18, 18)
                        .addComponent(pressureSpinner, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(absorptionLabe)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(absorptionSpinner, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dotLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dotSpinner, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertNameLabel)
                    .addComponent(insertNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(pressureLabel)
                    .addComponent(pressureSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(absorptionLabe)
                    .addComponent(absorptionSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(dotLabel)
                    .addComponent(dotSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(openButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(ActionEvent evt)//GEN-FIRST:event_saveButtonActionPerformed
    {//GEN-HEADEREND:event_saveButtonActionPerformed
        fc.setSelectedFile(new File(nameField.getText()));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fc.getSelectedFile().getAbsolutePath() + ".lhhwear");
            FileOutputStream fout;
            ObjectOutputStream oos;
            try
            {
                wear = new Wear(nameField.getText(), insertNameField.getText(), (float) pressureSpinner.getValue(), (float) absorptionSpinner.getValue(), (float) dotSpinner.getValue());
                switch (typeComboBox.getSelectedIndex())
                {
                    case 0:
                        wear.setType(UNDERWEAR);
                        break;
                    case 1:
                        wear.setType(OUTERWEAR);
                        break;
                    case 2:
                        wear.setType(BOTH_SUITABLE);
                        break;
                }

//                writer = new PrintStream(file);
                fout = new FileOutputStream(file);
                oos = new ObjectOutputStream(fout);
                oos.writeObject(wear);
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(this, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void openButtonActionPerformed(ActionEvent evt)//GEN-FIRST:event_openButtonActionPerformed
    {//GEN-HEADEREND:event_openButtonActionPerformed
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            try
            {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fin);
                wear = (Wear) ois.readObject();
            } catch (IOException | ClassNotFoundException e)
            {
                JOptionPane.showMessageDialog(this, "File error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            nameField.setText(wear.getName());
            insertNameField.setText(wear.insert());
            pressureSpinner.setValue(wear.getPressure());
            absorptionSpinner.setValue(wear.getAbsorption());
            dotSpinner.setValue(wear.getDryingOverTime());

            switch (wear.getType())
            {
                case UNDERWEAR:
                    typeComboBox.setSelectedIndex(0);
                    break;
                case OUTERWEAR:
                    typeComboBox.setSelectedIndex(1);
                    break;
                case BOTH_SUITABLE:
                    typeComboBox.setSelectedIndex(2);
                    break;
            }
        }
    }//GEN-LAST:event_openButtonActionPerformed

    public static void main(String[] args)
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException | InstantiationException ex)
        {
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() ->
        {
            new WearEditor().setVisible(true);
        });
    }

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
