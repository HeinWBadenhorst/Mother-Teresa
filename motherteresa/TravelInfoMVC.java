/*
 * TravelInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class TravelInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public TravelInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITTRAVEL", "SIMPLE", userName, password);
    }
    
}
