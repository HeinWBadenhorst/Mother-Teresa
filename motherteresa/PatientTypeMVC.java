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
public class PatientTypeMVC extends TypeTableMVC
{
    
    /** Creates a new instance of LanguageMVC */
    public PatientTypeMVC(String tableName,String userName, char[] password) throws MTException
    {
        super(tableName, userName, password);
    }
    
}
