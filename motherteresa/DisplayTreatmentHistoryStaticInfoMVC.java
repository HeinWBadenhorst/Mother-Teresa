/*
 * DisplayTreatmentHistoryStaticInfoMVC.java
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
public class DisplayTreatmentHistoryStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayTreatmentHistoryStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYTREATMENT", "INFOBYXML", userName, password);
    }
    public DisplayTreatmentHistoryStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYTREATMENT",  JTabbedPane.TOP, "INFOBYXML", userName, password);
    }
    public DisplayTreatmentHistoryStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYTREATMENT",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }
    
}
