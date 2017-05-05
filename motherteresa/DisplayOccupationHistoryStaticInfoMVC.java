/*
 * DisplayOccupationHistoryStaticInfoMVC.java
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
public class DisplayOccupationHistoryStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayOccupationHistoryStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYOCCUPATIONHISTORY", "INFOBYXML",userName, password);
    }
    public DisplayOccupationHistoryStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYOCCUPATIONHISTORY",  JTabbedPane.TOP, "INFOBYXML",userName, password);
    }
    public DisplayOccupationHistoryStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYOCCUPATIONHISTORY",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }


    
}
