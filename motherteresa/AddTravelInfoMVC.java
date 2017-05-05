/*
 * AddTravelInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddTravelInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddTravelInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDTRAVEL", "SIMPLE",userName,password);
    }
    
}
