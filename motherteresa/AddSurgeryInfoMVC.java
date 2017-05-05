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
public class AddSurgeryInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddSurgeryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDSURGERY", "SIMPLE", userName, password);
    }
    
}
