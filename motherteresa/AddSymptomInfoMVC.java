/*
 * AddSymptomInfoMVC.java
 *
 * Created on February 17, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddSymptomInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddSymptomInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDSYMPTOM", "SIMPLE", userName, password);
    }
    
}
