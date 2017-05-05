/*
 * DisplaySymptomInfoMVC.java
 *
 * Created on February 12, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DisplaySymptomInfoMVC extends DisplayInfoByKeyMVC
{
    
    /** Creates a new instance of DisplayInfoByKeyMVC */
    public DisplaySymptomInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "DISPLAYSYMPTOM", "SIMPLE",userName, password);
    }
    
}
