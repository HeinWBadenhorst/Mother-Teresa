/*
 * AddFamilyInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddFamilyInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddFamilyInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDFAMILY", "SIMPLE",userName, password);
    }
    
}
