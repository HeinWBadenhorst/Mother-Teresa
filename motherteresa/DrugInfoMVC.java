/*
 * DrugInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DrugInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DrugInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITHABIT", "SIMPLE",userName, password);
    }
    
}
