/*
 * LanguageMVC.java
 *
 * Created on December 3, 2002, 11:55 AM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class MedicalAidTypeMVC extends MTTableMVC
{
    
    /** Creates a new instance of LanguageMVC */
    public MedicalAidTypeMVC(String userName, char[] password) throws MTException
    {
        super("NEWMEDICALAID", "SIMPLE",userName, password);
    }
    
}
