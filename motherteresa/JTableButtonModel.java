/*
 * JTableButtonModel.java
 *
 * Created on 13 May 2005, 08:38
 */

package motherteresa;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;//.AbstractTableModel;
import javax.swing.JButton;


/**
 *
 * @author  HWBadenhorst
 */
public class JTableButtonModel extends AbstractTableModel {

    public JButton tmpButton = null;
    public Object[][] __rows = null;
    public JTableButtonModel(JButton tmpButton)  
    {
        //super();
        this.tmpButton = tmpButton;
        setRows();
    }


    
    public void setRows()
    {
       Object[][]  __rowsTmp = {
      { "One", this.tmpButton},
      { "Two", new JButton("Button Two") },
      { "Three", new JButton("Button Three") },
      { "Four", new JButton("Button Four") }
    };
    
        __rows = __rowsTmp;
    }

  private String[] __columns = { "Numbers", "Buttons" };

  public String getColumnName(int column) { 
    return __columns[column];
  }

  public int getRowCount() {
    return __rows.length;
  }


  public int getColumnCount() {
    return __columns.length;
  }

  public Object getValueAt(int row, int column) { 
      return __rows[row][column];
  }

  public boolean isCellEditable(int row, int column) {
    return false;
  }

  public Class getColumnClass(int column) {
    return getValueAt(0, column).getClass();
  }
}
