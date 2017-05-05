/*
 * ConditionInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class ConditionInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public ConditionInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITCONDITION", "SIMPLE",userName, password);
    }
    
}
