/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.storyEditorPanels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Jonisan
 */
public class ManifestEditPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private final JLabel nameLabel;
    private final JTextField nameField;
    private final JTextArea descriptionField;
    private final JLabel descriptionLabel;
    private final JLabel authorLabel;
    private final JTextField authorField;
    
    public ManifestEditPanel()
    {
        setBounds(0,20,792,623);
        
        nameLabel = new JLabel("Story title");
        nameLabel.setBounds(10,10,20,30);
//        nameLabel.setVisible(true);
        add(nameLabel);
        
        nameField = new JTextField();
        nameField.setBounds(30, 10, 752, 30);
        add(nameField);
        
        descriptionLabel = new JLabel("Story description");
        descriptionLabel.setBounds(10,40,20,30);
        add(descriptionLabel);
        
        descriptionField = new JTextArea();
        descriptionField.setBounds(30, 40, 752, 100);
        add(descriptionField);
        
        authorLabel = new JLabel("Author");
        authorLabel.setBounds(10,150,20,20);
        add(authorLabel);
        
        authorField = new JTextField();
        authorField.setBounds(30, 150, 752, 30);
        add(authorField);
    }
}
