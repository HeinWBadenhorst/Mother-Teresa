/*
 * AppointmentInfoMVC.java
 *
 * Created on February 3, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  administrator
 */
public class AppointmentInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoByDateMVC */
    public AppointmentInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITAPPOINTMENT", "SIMPLE",userName, password);
    }
    
}
