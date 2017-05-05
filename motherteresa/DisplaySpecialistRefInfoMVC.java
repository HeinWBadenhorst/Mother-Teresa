/*
 * DisplaySpecialistRefInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplaySpecialistRefInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplaySpecialistRefInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYSPECIALISTREF", "SIMPLE", userName, password);
    }
    
}
