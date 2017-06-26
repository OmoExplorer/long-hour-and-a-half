package omo.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import omo.GameCore;

/**
 *
 * @author Jonisan
 */
public class WearFileChooser extends JFileChooser
{
    private static final long serialVersionUID = 1L;
    public WearFileChooser()
    {
        this.setFileFilter(new FileFilter()
        {
            @Override
            public boolean accept(File pathname)
            {
                return GameCore.isExtensionEquals(pathname, "lhhwear");
            }

            @Override
            public String getDescription()
            {
                return "A Long Hour and a Half Custom wear";
            }
        });
    }
}
