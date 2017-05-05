/*
 * InjuryInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class InjuryInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public InjuryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITINJURY", "SIMPLE", userName, password);
    }
    
}
