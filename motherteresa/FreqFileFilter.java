/*
 * FreqFileFilter.java
 *
 * Created on 22 June 2005, 02:49
 */

package motherteresa;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 *
 * @author  HWBadenhorst
 */
public class FreqFileFilter extends FileFilter
{
    
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
	if (extension != null) {
            if (extension.equals("rts")) {
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }
    
    // The description of this filter
    public String getDescription() {
        return "Just *.rts Files";
    }    
}
