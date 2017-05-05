/*
 * DisplayInjuryInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplayDiseaseDiagInfoMVC extends DiagnosticAidMVC
{
    
    /** Creates a new instance of DisplayDiseaseDiagInfoMVC */
    public DisplayDiseaseDiagInfoMVC(String keyValue,String userName, char[] password)
    {
        super(keyValue, "DISPLAYDIAGBYSYMPTOM",1,"SIMPLE",userName, password);
    }
    
}
