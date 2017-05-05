/*
 * AddInjuryInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddInjuryInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddInjuryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDINJURY", "SIMPLE", userName, password);
    }
    
}
