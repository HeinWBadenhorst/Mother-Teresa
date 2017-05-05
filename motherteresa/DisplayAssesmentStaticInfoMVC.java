/*
 * DisplayAssesmentStaticInfoMVC.java
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
public class DisplayAssesmentStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayAssesmentStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYASSESMENT", "INFOBYXML",userName,password);
    }
    public DisplayAssesmentStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYASSESMENT",  JTabbedPane.TOP, "INFOBYXML",userName,password);
    }
      public DisplayAssesmentStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYASSESMENT",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }
    
    
    
}
