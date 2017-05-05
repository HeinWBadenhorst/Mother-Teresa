/*
 * PoisoningInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class PoisoningInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public PoisoningInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITPOISONING", "SIMPLE", userName,password);
    }
    
}
