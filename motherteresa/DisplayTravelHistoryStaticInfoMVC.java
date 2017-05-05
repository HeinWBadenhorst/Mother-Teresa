/*
 * DisplayTravelHistoryStaticInfoMVC.java
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
public class DisplayTravelHistoryStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayTravelHistoryStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYTRAVELHISTORY", "INFOBYXML",userName,password);
    }
    public DisplayTravelHistoryStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYTRAVELHISTORY",  JTabbedPane.TOP, "INFOBYXML",userName,password);
    }
    public DisplayTravelHistoryStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYTRAVELHISTORY",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }

    
}
