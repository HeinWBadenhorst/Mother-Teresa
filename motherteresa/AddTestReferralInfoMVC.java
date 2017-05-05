/*
 * AddTestReferralInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddTestReferralInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddTestReferralInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDTESTREF", "SIMPLE",userName,password);
    }
    
}
