/*
* AddAppointmentInfoMVC.java
 *
 * Created on April 24, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddAppointmentInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddAppointmentInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDAPPOINTMENT", "SIMPLE", userName, password);
    }
    
}
