/*
 * AddPoisoningInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddPoisoningInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddPoisoningInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDPOISONING", "SIMPLE", userName, password);
    }
    
}
