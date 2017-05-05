/*
 * MTCustomTableModel.java
 *
 * Created on 17 May 2005, 07:59
 */

package motherteresa;

import javax.swing.table.*;
import java.util.*; 
//import javax.swing.JButton;
//import java.lang.reflect.Array;

/**
 *
 * @author  HWBadenhorst
 */
public class MTCustomTableModel  extends DefaultTableModel{
    
  
     //Creates a new instance of MTCustomTableModel 
    public MTCustomTableModel() 
    {
        super();
    }
    
    public MTCustomTableModel(int rowCount, int colCount) 
    {
        super(rowCount,colCount);
    }

    public MTCustomTableModel(Object[][] data, Object[] columnNames) 
    {
        super(data,columnNames);
    }

    public MTCustomTableModel(Object[] columnNames, int rowCount) 
    {
        super(columnNames,rowCount);
    }

    public MTCustomTableModel(Vector columnNames, int rowCount) 
    {
        super(columnNames,rowCount);
    }

    public MTCustomTableModel(Vector data, Vector columnNames) 
    {
        super(data, columnNames);
    }

    //overriding isCelleditable to always return false as we do not want to edit the table
    public boolean isCelleditable(int row, int col)
    {
        return false;
    }

}
