/*
 * BasicInfoMVC.java
 *
 * Created on February 3, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  administrator
 */
public class VitalInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoByDateMVC */
    public VitalInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITVITAL", "SIMPLE", userName, password);
    }
    
}
