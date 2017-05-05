/*
 * AddTreatmentInfoMVC.java
 *
 * Created on February 13, 2003, 1:41 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class AddDrugInfoMVC extends AddInfoMVC
{
    
    /** Creates a new instance of AddInfoMVC */
    public AddDrugInfoMVC(String userName, char[] password)
    {
        super("SELECTPATIENT", "ADDHABIT", "SIMPLE",userName,password);
    }
    
}
