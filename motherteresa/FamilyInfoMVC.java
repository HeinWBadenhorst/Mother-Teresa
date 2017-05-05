/*
 * FamilyInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class FamilyInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public FamilyInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITFAMILY", "SIMPLE", userName, password);
    }
    
}
