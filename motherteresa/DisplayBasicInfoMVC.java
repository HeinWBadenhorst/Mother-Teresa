/*
 * DisplayBasicInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayBasicInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayBasicInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYBASIC", "SIMPLE", userName, password);
    }
    
}
