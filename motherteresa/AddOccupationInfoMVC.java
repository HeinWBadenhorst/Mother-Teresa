/*
 * AddOccupationInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddOccupationInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddOccupationInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDOCCUPATION", "SIMPLE",userName,password);
    }
    
}
