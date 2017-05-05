/*
 * AddImmunizationInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddImmunizationInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddImmunizationInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDIMMUNIZATION", "SIMPLE",userName, password);
    }
    
}
