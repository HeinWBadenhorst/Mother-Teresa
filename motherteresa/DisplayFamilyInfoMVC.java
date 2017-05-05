/*
 * DisplayFamilyInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayFamilyInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayFamilyInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYFAMILY", "SIMPLE",userName,password);
    }
    
}
