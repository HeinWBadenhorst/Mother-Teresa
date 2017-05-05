/*
 * AddTestInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddTestInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddTestInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDTEST", "SIMPLE",userName,password);
    }
    
}
