/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omo;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import static javax.swing.SwingUtilities.invokeLater;
import javax.swing.UIManager.LookAndFeelInfo;
import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.setLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A story editor main window.
 * @author JavaBird
 */
public class StoryEditor extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem newItem;
    private final JMenuItem openItem;
    private final JMenuItem saveItem;
    private final JMenuItem saveAsItem;
    private final JMenuItem quitItem;
    private final JTree storyTree;
    private final DefaultMutableTreeNode rootNode;
    private DefaultMutableTreeNode scenesNode;
    private final DefaultMutableTreeNode noScenesNode;
    
    public StoryEditor()
    {
        setTitle("Story editor");
        setBounds(100,100,992, 623);
        setPreferredSize(new Dimension(992, 623));
        setMinimumSize(new Dimension(12,28));
        
        this.menuBar = new JMenuBar();
        
        this.fileMenu = new JMenu("File");
        this.fileMenu.setMnemonic(KeyEvent.VK_F);
        this.menuBar.add(fileMenu);
        
        this.newItem = new JMenuItem("New story");
        this.newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        this.newItem.getAccessibleContext().setAccessibleDescription("Create a new story");
        this.newItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        this.fileMenu.add(newItem);
        
        this.openItem = new JMenuItem("Open story...");
        this.openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        this.openItem.getAccessibleContext().setAccessibleDescription("Open an existing story");
        this.openItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        this.fileMenu.add(openItem);
        
        this.fileMenu.addSeparator();
        
        this.saveItem = new JMenuItem("Save story");
        this.saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        this.saveItem.getAccessibleContext().setAccessibleDescription("Save currently opened story");
        this.saveItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        this.fileMenu.add(saveItem);
        
        this.saveAsItem = new JMenuItem("Save story as...");
        this.saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        this.saveAsItem.getAccessibleContext().setAccessibleDescription("Save currently opened story as another file");
        this.saveAsItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        this.fileMenu.add(saveAsItem);
        
        this.quitItem = new JMenuItem("Quit");
        this.quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        this.quitItem.getAccessibleContext().setAccessibleDescription("Close the story editor");
        this.quitItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        this.fileMenu.add(quitItem);
        
        setJMenuBar(menuBar);
        
        rootNode = new DefaultMutableTreeNode("root");
        this.storyTree = new JTree(rootNode);
        this.storyTree.setRootVisible(false);
        
        this.rootNode.add(new DefaultMutableTreeNode("Story parameters"));
        
        scenesNode = new DefaultMutableTreeNode("Scenes");
        this.rootNode.add(scenesNode);
        
        noScenesNode = new DefaultMutableTreeNode("<no scenes>", false);
        this.scenesNode.add(noScenesNode);
        
        
        try
        {
            for (LookAndFeelInfo info : getInstalledLookAndFeels())
                if (info.getName().equals("Nimbus"))
                {
                    setLookAndFeel(info.getClassName());
                    break;
                }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            System.err.println("Error setting Nimbus");
        }
        invokeLater(() ->
        {
            setVisible(true);
        });
    }
}
