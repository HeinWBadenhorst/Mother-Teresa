/*
 * DiagnosesInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DiagnosesInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public DiagnosesInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITDIAGNOSES", "SIMPLE", userName, password);
    }
    
}
