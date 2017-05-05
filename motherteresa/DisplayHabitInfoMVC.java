/*
 * DisplayHabitInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayHabitInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayHabitInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYHABITS", "SIMPLE",userName,password);
    }
    
}
