/*
 * DisplayPoisoningInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayPoisoningInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayPoisoningInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYPOISONING", "SIMPLE",userName, password);
    }
    
}
