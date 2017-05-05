/*
 * TitleTypeMVC.java
 *
 * Created on January 8, 2003, 3:11 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class TitleTypeMVC extends TypeTableMVC
{
    
    /** Creates a new instance of TitleTypeMVC */
    public TitleTypeMVC(String tableName,String userName, char[] password) throws MTException
    {
        super(tableName, userName, password);
    }
    
}
