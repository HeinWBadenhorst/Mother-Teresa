/*
 * DisplayExaminationStaticInfoMVC.java
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
public class DisplayExaminationStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayExaminationStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYEXAMINATION", "INFOBYXML", userName, password);
    }
    public DisplayExaminationStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYEXAMINATION",  JTabbedPane.TOP, "INFOBYXML", userName, password);
    }
    public DisplayExaminationStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYEXAMINATION",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }
  
}
