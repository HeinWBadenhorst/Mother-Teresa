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
public class DisplayComplaintStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayComplaintStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYCOMPLAINT", "INFOBYXML",userName,password);
    }
    public DisplayComplaintStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYCOMPLAINT",  JTabbedPane.TOP, "INFOBYXML",userName,password);
    }
    public DisplayComplaintStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYCOMPLAINT",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }
    
}
