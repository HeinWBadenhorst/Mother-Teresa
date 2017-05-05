/*
 * DisplayTreatmentInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayTreatmentInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayTreatmentInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYTREATMENT", "SIMPLE", userName, password);
    }
    
}
