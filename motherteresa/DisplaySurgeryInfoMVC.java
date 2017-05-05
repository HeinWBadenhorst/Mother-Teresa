/*
 * DisplaySurgeryInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplaySurgeryInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplaySurgeryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYSURGERY", "SIMPLE", userName, password);
    }
    
}
