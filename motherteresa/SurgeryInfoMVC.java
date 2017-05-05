/*
 * SuregeryInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class SurgeryInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public SurgeryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITSURGERY", "SIMPLE",userName, password);
    }
    
}
