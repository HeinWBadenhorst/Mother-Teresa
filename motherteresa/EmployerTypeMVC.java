/*
 * LanguageMVC.java
 *
 * Created on December 3, 2002, 11:55 AM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class EmployerTypeMVC extends MTTableMVC
{
    /** Creates a new instance of LanguageMVC */
    public EmployerTypeMVC(String userName, char[] password) throws MTException
    {
        super("NEWEMPLOYER", "SIMPLE",userName, password);
    }
    
}
