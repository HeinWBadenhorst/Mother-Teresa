/*
 * DisplayTravelInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayTravelInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayTravelInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYTRAVEL", "SIMPLE",userName, password);
    }
    
}
