/*
 * AddSpecialistReferralInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddSpecialistReferralInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddSpecialistReferralInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDSPECIALISTREF", "SIMPLE", userName, password);
    }
    
}
