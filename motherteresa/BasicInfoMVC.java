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
public class BasicInfoMVC extends EditInfoMVC
{
    
    /** Creates a new instance of BasicInfoMVC */
    public BasicInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITBASIC", "SIMPLE", userName, password);
    }
    
}
