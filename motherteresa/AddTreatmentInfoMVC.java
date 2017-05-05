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
public class AddTreatmentInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddTreatmentInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDTREATMENT", "SIMPLE", userName, password);
    }
    
}
