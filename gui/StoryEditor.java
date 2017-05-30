/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.storyEditorPanels.ManifestEditPanel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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
    private final JTree toolTree;
    private final DefaultMutableTreeNode rootNode;
    private final DefaultMutableTreeNode scenesNode;
    private final DefaultMutableTreeNode newSetupSceneNode;
    private final DefaultMutableTreeNode setupScenesNode;
    private final DefaultMutableTreeNode activeScenesNode;
    private final DefaultMutableTreeNode newActiveSceneNode;
    private final DefaultMutableTreeNode wettingScenesNode;
    private final DefaultMutableTreeNode newWettingSceneNode;
    private final DefaultMutableTreeNode charactersNode;
    private final DefaultMutableTreeNode newCharacterNode;
    private final DefaultMutableTreeNode actionsNode;
    private final DefaultMutableTreeNode newActionNode;
    private final DefaultMutableTreeNode operationsNode;
    private final DefaultMutableTreeNode newOperationNode;
    private final DefaultMutableTreeNode newVariableNode;
    private final DefaultMutableTreeNode variablesNode;
    private final DefaultMutableTreeNode customWearNode;
    private final DefaultMutableTreeNode newCustomWearNode;
    private final JPanel manifestEditPanel;
    
    public StoryEditor()
    {
        setTitle("Story editor");
        setBounds(100,100,992, 623);
        setResizable(false);
//        setPreferredSize(new Dimension(992, 623));
//        setMinimumSize(new Dimension(12,28));
        
        menuBar = new JMenuBar();
        
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        
        newItem = new JMenuItem("New story");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newItem.getAccessibleContext().setAccessibleDescription("Create a new story");
        newItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        fileMenu.add(newItem);
        
        openItem = new JMenuItem("Open story...");
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openItem.getAccessibleContext().setAccessibleDescription("Open an existing story");
        openItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        fileMenu.add(openItem);
        
        fileMenu.addSeparator();
        
        saveItem = new JMenuItem("Save story");
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveItem.getAccessibleContext().setAccessibleDescription("Save currently opened story");
        saveItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        fileMenu.add(saveItem);
        
        saveAsItem = new JMenuItem("Save story as...");
        saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        saveAsItem.getAccessibleContext().setAccessibleDescription("Save currently opened story as another file");
        saveAsItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        fileMenu.add(saveAsItem);
        
        quitItem = new JMenuItem("Quit");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        quitItem.getAccessibleContext().setAccessibleDescription("Close the story editor");
        quitItem.addActionListener((ActionEvent e) ->
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });
        fileMenu.add(quitItem);
        
        setJMenuBar(menuBar);
        
        rootNode = new DefaultMutableTreeNode("root");
        toolTree = new JTree(rootNode);
        toolTree.setRootVisible(false);
        toolTree.setBounds(0,20,200,623);
        
        rootNode.add(new DefaultMutableTreeNode("Story manifest"));
        
        scenesNode = new DefaultMutableTreeNode("Scenes");
        rootNode.add(scenesNode);
        
        setupScenesNode = new DefaultMutableTreeNode("Setup");
        scenesNode.add(setupScenesNode);
        newSetupSceneNode = new DefaultMutableTreeNode("<New scene>", false);
        setupScenesNode.add(newSetupSceneNode);
        
        activeScenesNode = new DefaultMutableTreeNode("Active");
        scenesNode.add(activeScenesNode);
        newActiveSceneNode = new DefaultMutableTreeNode("<New scene>", false);
        activeScenesNode.add(newActiveSceneNode);
        
        wettingScenesNode = new DefaultMutableTreeNode("Wetting");
        scenesNode.add(wettingScenesNode);
        newWettingSceneNode = new DefaultMutableTreeNode("<New scene>", false);
        wettingScenesNode.add(newWettingSceneNode);
        
        charactersNode = new DefaultMutableTreeNode("Characters");
        charactersNode.add(rootNode);
        newCharacterNode = new DefaultMutableTreeNode("<New character>", false);
        charactersNode.add(newCharacterNode);
        
        actionsNode = new DefaultMutableTreeNode("Actions", true);
        actionsNode.add(rootNode);
        newActionNode = new DefaultMutableTreeNode("<New action>");
        actionsNode.add(newActionNode);
        
        operationsNode = new DefaultMutableTreeNode("Operations", true);
        operationsNode.add(rootNode);
        newOperationNode = new DefaultMutableTreeNode("<New operation>", false);
        operationsNode.add(newOperationNode);
        
        variablesNode = new DefaultMutableTreeNode("Variables", true);
        variablesNode.add(rootNode);
        newVariableNode = new DefaultMutableTreeNode("<New variable>", false);
        variablesNode.add(newVariableNode);
        
        customWearNode = new DefaultMutableTreeNode("Custom wear", true);
        customWearNode.add(rootNode);
        newCustomWearNode = new DefaultMutableTreeNode("<New custom wear>", false);
        customWearNode.add(newCustomWearNode);
        
        add(toolTree);
        toolTree.setBounds(0,20,200,623);
        toolTree.setVisible(true);
        
        //(0,20,792,623)
        manifestEditPanel = new ManifestEditPanel();
        add(manifestEditPanel);
        
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
