/*
 * AllergyInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AllergyInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public AllergyInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITALLERGY", "SIMPLE",userName, password);
    }
    
}
