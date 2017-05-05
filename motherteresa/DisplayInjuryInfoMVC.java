/*
 * DisplayInjuryInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayInjuryInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayInjuryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYINJURY", "SIMPLE",userName, password);
    }
    
}
