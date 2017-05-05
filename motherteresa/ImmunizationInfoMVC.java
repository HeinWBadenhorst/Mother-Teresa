/*
 * ImmunizationInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class ImmunizationInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public ImmunizationInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITIMMUNIZATION", "SIMPLE", userName, password);
    }
    
}
