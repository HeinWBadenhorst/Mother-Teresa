/*
 * AddTreatmentInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddVitalSignInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddVitalSignInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDVITALS", "SIMPLE", userName, password);
    }
    
}
