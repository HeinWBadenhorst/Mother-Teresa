/*
 * SharedListSelectionHandler.java
 *
 * Created on 28 June 2005, 08:47
 */

package motherteresa;
import javax.swing.event.*;
import javax.swing.*; 

/**
 *
 * @author  HWBadenhorst
 */
public class SharedListSelectionHandler implements ListSelectionListener
{
    String listItem = null;
    
    
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
        return;
       JList theList = (JList)e.getSource();
        if (theList.isSelectionEmpty()) 
        {
            return;
        } else 
        {
             int index = theList.getSelectedIndex();
             listItem = (String)theList.getSelectedValue();
        }
    }
/*   
     ListSelectionModel lsm = (ListSelectionModel)e.getSource();
     int firstIndex = e.getFirstIndex();
     int lastIndex = e.getLastIndex();
     boolean isAdjusting = e.getValueIsAdjusting();
     output.append("Event for indexes "
                  + firstIndex + " - " + lastIndex
                  + "; isAdjusting is " + isAdjusting
                  + "; selected indexes:");
     if (lsm.isSelectionEmpty()) {
         output.append(" <none>");
     } else {
        // Find out which indexes are selected.
        int minIndex = lsm.getMinSelectionIndex();
        int maxIndex = lsm.getMaxSelectionIndex();
        for (int i = minIndex; i <= maxIndex; i++) {
            if (lsm.isSelectedIndex(i)) {
               output.append(" " + i);
            }
        }
     }
        output.append(newline);
    }
*/
}
