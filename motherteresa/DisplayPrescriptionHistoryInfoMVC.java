/*
 * DisplayPrescriptionHistoryInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayPrescriptionHistoryInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayPrescriptionHistoryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYPRESCRIPTION", "SIMPLE", userName, password);
    }
    
}
