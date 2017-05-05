/*
* AddDiagnosesInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddDiagnosesInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddDiagnosesInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDDIAGNOSES", "SIMPLE",userName,password);
    }
    
}
