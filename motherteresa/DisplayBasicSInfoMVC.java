/*
 * DisplayBasicInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayBasicSInfoMVC extends DisplayStaticInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayBasicSInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYBASIC", "INFO", userName, password);
    }
    
}
