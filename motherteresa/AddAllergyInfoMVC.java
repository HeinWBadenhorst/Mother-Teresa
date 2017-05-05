/*
 * AddAllergyInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddAllergyInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddAllergyInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDALLERGY", "SIMPLE", userName, password);
    }
    
}
