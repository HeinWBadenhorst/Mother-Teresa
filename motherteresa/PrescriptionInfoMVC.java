/*
 * PrescriptionInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class PrescriptionInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public PrescriptionInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITPRESCRIPTION", "SIMPLE", userName,  password);
    }
    
}
