/*
 * OccupationInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class OccupationInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public OccupationInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITOCCUPATION", "SIMPLE", userName,  password);
    }
    
}
