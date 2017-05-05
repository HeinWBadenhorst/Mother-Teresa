/*
 * DisplayFamilyHistoryInfoMVC.java
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
public class DisplayFamilyHistoryStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayFamilyHistoryStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYFAMILYHISTORY", "INFOBYXML", userName, password);
    }
    public DisplayFamilyHistoryStaticInfoMVC(String keyValue, String userName, char[] password)
    {
        super(keyValue, "DISPLAYFAMILYHISTORY",  JTabbedPane.TOP, "INFOBYXML", userName, password);
    }
    public DisplayFamilyHistoryStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYFAMILYHISTORY",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }

    
}
