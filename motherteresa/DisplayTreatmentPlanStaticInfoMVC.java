/*
 * DisplayTreatmentPlanStaticInfoMVC.java
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
public class DisplayTreatmentPlanStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayTreatmentPlanStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYTREATMENTPLAN", "INFOBYXML",userName,password);
    }
    public DisplayTreatmentPlanStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYTREATMENTPLAN",  JTabbedPane.TOP, "INFOBYXML",userName,password);
    }
    public DisplayTreatmentPlanStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYTREATMENTPLAN",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }
    
}
