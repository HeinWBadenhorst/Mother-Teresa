/*
 * SpecialistReferralInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class SpecialistReferralInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public SpecialistReferralInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITSPECIALISTREF", "SIMPLE",userName,password);
    }
    
}
