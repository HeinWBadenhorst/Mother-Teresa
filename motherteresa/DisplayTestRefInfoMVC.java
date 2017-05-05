/*
 * DisplayTestRefInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayTestRefInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayTestRefInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYTESTREF", "SIMPLE",userName,password);
    }
    
}
