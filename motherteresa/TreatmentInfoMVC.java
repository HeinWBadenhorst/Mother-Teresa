/*
 * TreatmentInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class TreatmentInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public TreatmentInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITTREATMENT", "SIMPLE",userName, password);
    }
    
}
