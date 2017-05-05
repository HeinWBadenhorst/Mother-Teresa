/*
 * MTException.java
 *
 * Created on December 19, 2002, 1:14 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class MTException extends Exception
{
    int exceptionType = 0;
    String errorMessage = "";
    String messageHeading = "";
    
    /** Creates a new instance of MTException */
    public MTException(int exceptionType, String errorMessage)
    {
        this.exceptionType = exceptionType;
        this.errorMessage = errorMessage;
        switch(exceptionType)
        {
            case InfoManager.MVC_ERROR:
            {
               this.errorMessage = "[" + errorMessage + "]";  
               this.messageHeading = "MVC Error";
               break;
            }
            case InfoManager.DBACCESS_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "DB Access Error"; 
                break;
            }
            case InfoManager.ADAPTER_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Adapter Error"; 
                break;
            }
            case InfoManager.INDEX_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Indexing Error"; 
                break;
            }
            case InfoManager.NULL_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Null Variable Error"; 
                break;
            }
            case InfoManager.CLASS_CAST_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Class Cast Error"; 
                break;
            }
            case InfoManager.VIEW_BUILDER_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "View Builder Error"; 
                break;
            }
            case InfoManager.LOG_WRITE_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Log Write Error"; 
                break;
            }
            case InfoManager.PANE_CREATION_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Pane Creation Error"; 
                break;
            }
            case InfoManager.BUSINESS_MODEL_ERROR:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Business Model Error"; 
                break;
            }
            default:
            {
                this.errorMessage = "[" + errorMessage + "]";
                this.messageHeading = "Unspecified Error"; 
                break;
            }
        }
    }
    public int getExceptionType()
    {
       return this.exceptionType;
    }
    
    public String getErrorMessage()
    {
       return this.errorMessage;
    }

    public String getMessageHeading()
    {
       return this.messageHeading;
    }
}
