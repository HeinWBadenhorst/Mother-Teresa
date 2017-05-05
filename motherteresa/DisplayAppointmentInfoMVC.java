/*
 * DisplayAppointmentInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayAppointmentInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DisplayAppointmentInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYAPPOINTMENT", "SIMPLE",userName,password);
    }
    
}
