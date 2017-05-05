/*
 * DisplayAllPatientInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayAllPatientInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayAllPatientInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYALLPATIENT", "SIMPLE",userName,password);
    }
    
}
