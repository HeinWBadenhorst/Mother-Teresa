/*
 * MTJtable.java
 *
 * Created on 17 May 2005, 10:51
 */

package motherteresa;

import javax.swing.*;
import java.util.*; 
import javax.swing.table.*;

/**
 *
 * @author  HWBadenhorst
 */
public class MTJTable extends JTable{
    
    /** Creates a new instance of MTJtable */
    public MTJTable() 
    {
        super();
    }
    
    public MTJTable(int numRows, int numColumns) 
    {
        super(numRows,numColumns);
    }

    public MTJTable(Object[][] rowData, Object[] columnNames) 
    {
        super(rowData,columnNames);
    }

    public MTJTable(TableModel dm) 
    {
        super(dm);
    }

    public MTJTable(TableModel dm, TableColumnModel cm) 
    {
        super(dm, cm);
    }

    public MTJTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) 
    {
        super(dm, cm,sm);
    }

    public MTJTable(Vector rowData, Vector columnNames) 
    {
        super(rowData, columnNames);
    }

    public boolean isCelleditable(int row, int col)
    {
        return false;
    }

}
