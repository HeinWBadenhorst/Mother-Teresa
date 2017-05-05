/*
 * NewPatientMVC.java
 *
 * Created on December 10, 2002, 1:02 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class NewPatientMVC extends MTTableMVC
{
    private String theKey = null;
    private boolean updatedFlag = false;
    
    /** Creates a new instance of NewPatientMVC */
    public NewPatientMVC(String userName, char[] password)
    {
        super("NEWPATIENT", "SIMPLE",userName,password);
        theKey = super.getKeyValue();
    }
    
    public String getKeyValue()
    {
        return theKey;
    }

    public boolean getUpdatedState()
    {
        updatedFlag = super.getUpdatedFlag();
        return updatedFlag;
    }
    
}
