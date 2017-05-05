/*
 * DisplaySystemicStaticInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

import javax.swing.*;
import java.util.*;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplaySystemicStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplaySystemicStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYSYSTEMIC", "INFOBYXML",userName, password);
    }
    public DisplaySystemicStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYSYSTEMIC",  JTabbedPane.TOP, "INFOBYXML",userName,password);
    }
    public DisplaySystemicStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYSYSTEMIC",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }

    
}
