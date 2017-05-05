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
public class DisplayBasicStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayBasicStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYBASIC", "INFO",userName,password);
    }
    public DisplayBasicStaticInfoMVC(String keyValue, String userName, char[] password)
    {
        super(keyValue, "DISPLAYBASIC",  JTabbedPane.TOP, "INFOBYXML",userName,password);
    }
    public String getSelectedKeyValue()
    {
      return super.selectedKeyValue;
    }
    public ArrayList getDateList()
    {
      return super.dateListArray;
    }
    
}
