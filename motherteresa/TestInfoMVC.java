/*
 * TestInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class TestInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public TestInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITTEST", "SIMPLE", userName, password);
    }
    
}
