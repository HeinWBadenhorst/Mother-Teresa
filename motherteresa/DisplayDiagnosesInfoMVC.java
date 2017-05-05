/*
 * DisplayDiagnosesInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayDiagnosesInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplayDiagnosesInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYDIAGNOSES", "SIMPLE", userName, password);
    }
    
}
