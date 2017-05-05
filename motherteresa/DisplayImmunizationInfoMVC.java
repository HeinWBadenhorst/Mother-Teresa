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
public class DisplayImmunizationInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayImmunizationInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYIMMUNIZATION", "SIMPLE", userName, password);
    }
    
}
