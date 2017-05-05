/*
 * AddPrescriptionInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddPrescriptionInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddPrescriptionInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDPRESCRIPTION", "SIMPLE",userName,password);
    }
    
}
