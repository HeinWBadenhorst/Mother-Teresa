/*
 * TestReferralInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class TestReferralInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public TestReferralInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITTESTREF", "SIMPLE", userName, password);
    }
    
}
