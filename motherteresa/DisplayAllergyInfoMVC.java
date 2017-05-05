/*
 * DisplayAllergyInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayAllergyInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayAllergyInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYALLERGY", "SIMPLE", userName, password);
    }
    
}
