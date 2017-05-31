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
        setBounds(0,10,792,633);
        
        nameLabel = new JLabel("Story title");
        nameLabel.setBounds(0,0,20,20);
//        nameLabel.setVisible(true);
        add(nameLabel);
        
        nameField = new JTextField();
        nameField.setBounds(25, 0, 757, 30);
        add(nameField);
        
        descriptionLabel = new JLabel("Story description");
        descriptionLabel.setBounds(5,10,20,20);
        add(descriptionLabel);
        
        descriptionField = new JTextArea();
        descriptionField.setBounds(30, 35, 757, 100);
        add(descriptionField);
        
        authorLabel = new JLabel("Author");
        authorLabel.setBounds(5,150,20,20);
        add(authorLabel);
        
        authorField = new JTextField();
        authorField.setBounds(30, 150, 757, 30);
        add(authorField);
    }
}
