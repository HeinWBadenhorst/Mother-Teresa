/*
 * DisplayBasicInfoMVC.java
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
public class DisplaySurgicalHistoryStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplaySurgicalHistoryStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYSURGHISTORY", "INFOBYXML", userName, password);
    }
    public DisplaySurgicalHistoryStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYSURGHISTORY",  JTabbedPane.TOP, "INFOBYXML", userName, password);
    }
    public DisplaySurgicalHistoryStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYSURGHISTORY",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }
    
}
