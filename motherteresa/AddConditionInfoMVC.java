/*
 * AddConditionInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddConditionInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddConditionInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDCONDITION", "SIMPLE", userName, password);
    }
    
}
