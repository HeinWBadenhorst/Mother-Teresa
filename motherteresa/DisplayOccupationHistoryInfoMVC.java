/*
 * DisplayOccupationHistoryInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayOccupationHistoryInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayOccupationHistoryInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYOCCHISTORY", "SIMPLE", userName, password);
    }
    
}
