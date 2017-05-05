/*
 * SymptomInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class SymptomInfoMVC extends EditInfoByDateMVC
{
    
    /** Creates a new instance of EditInfoMVC */
    public SymptomInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "EDITSYMPTOM", "SIMPLE", userName, password);
    }
    
}
