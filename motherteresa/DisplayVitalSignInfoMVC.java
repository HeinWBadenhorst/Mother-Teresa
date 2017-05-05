/*
 * DisplayVitalSignInfoMVC.java
 *
 * Created on March 18, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayVitalSignInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayVitalSignInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYVITALSIGNS", "SIMPLE",userName,password);
    }
    
}
