/*
 * DisplayAllergyHistoryStaticInfoMVC.java
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
public class DisplayAllergyHistoryStaticInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayAllergyHistoryStaticInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYALLERGY", "INFOBYXML",userName,password);
    }
    public DisplayAllergyHistoryStaticInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYALLERGY",  JTabbedPane.TOP, "INFOBYXML",userName,password);
    }
      public DisplayAllergyHistoryStaticInfoMVC(String keyValue,ArrayList dateSelectionArray,String userName, char[] password)
    {
        super(keyValue, "DISPLAYALLERGY",  dateSelectionArray, JTabbedPane.TOP, "BYXMLDATED",userName,password);
    }
 
}
