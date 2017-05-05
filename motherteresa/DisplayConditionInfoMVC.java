/*
 * DisplayConditionInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayConditionInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayConditionInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYCONDITION", "SIMPLE", userName, password);
    }
    
}
