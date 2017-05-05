/*
 * DisplayTestInfoMVC.java
 *
 * Created on March 19, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayTestInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayTestInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYTESTS", "SIMPLE",userName, password);
    }
    
}
