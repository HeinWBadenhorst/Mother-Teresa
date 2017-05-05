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
public class DisplayMedicalHistoryStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayMedicalHistoryStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYMEDHISTORY", "INFOBYXML",userName, password);
    }
    public DisplayMedicalHistoryStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYMEDHISTORY",  JTabbedPane.TOP, "INFOBYXML", userName, password);
    }
    public DisplayMedicalHistoryStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYMEDHISTORY",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }

    
}
