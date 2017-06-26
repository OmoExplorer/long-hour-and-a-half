package omo.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import omo.GameCore;

/**
 *
 * @author Jonisan
 */
public class GameSaveFileChooser extends JFileChooser
{
    private static final long serialVersionUID = 1L;
    public GameSaveFileChooser()
    {
        this.setFileFilter(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return GameCore.isExtensionEquals(pathname, "lhhsave");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Save game";
            }
        });
    }
}
