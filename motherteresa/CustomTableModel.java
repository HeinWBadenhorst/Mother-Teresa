package motherteresa;
import javax.swing.table.*;
import java.util.*; 
import javax.swing.JButton;
import java.lang.reflect.Array;

public class CustomTableModel extends AbstractTableModel
{

  private Object[] columnNames = null;
  private Object[][] columnValues = null;
  private ArrayList noteContentList = null;
  int buttCol = -1;

  public CustomTableModel(Object[][]cellData, Object[] tableHeadingData)
   {
      super();
      columnNames = tableHeadingData;
      columnValues = cellData;
   }
              
  public CustomTableModel(Object[][]cellData, Object[] tableHeadingData, int buttonColumn)
   {
      super();
      int len = 0;
      columnNames = tableHeadingData;
      columnValues = new Object[cellData.length][6];

      for (int loop = 0; loop < cellData.length; loop++)
      {
         len = cellData[loop].length;
         Object[] tmpArray = new Object[len];
         for (int loop1 = 0; loop1 < len; loop1++)
         {
             if (loop1 == buttonColumn)
             {
                tmpArray[loop1] = new JButton((String)cellData[loop][loop1]);  
             } else
             {
                tmpArray[loop1] = cellData[loop][loop1];  
             }
         }
         columnValues[loop] = tmpArray;
         //noteContentList.add(columnValues[loop][buttCol]);
         //JButton tmpButton = new JButton("View");
         //columnValues[loop+1][buttCol] = (Object)tmpButton;
         //Array.set(columnValues[loop+1],buttCol,(Object)tmpButton);
      }

      //buttCol = buttonColumn;
      //if (buttCol != -1)
      //{
      //    noteContentList = new ArrayList(columnValues.length);
      //    for (int loop = 0; loop < columnValues.length; loop++)
      //    {
      //        noteContentList.add(columnValues[loop][buttCol]);
      //        JButton tmpButton = new JButton("View");
      //        columnValues[loop+1][buttCol] = (Object)tmpButton;
      //        //Array.set(columnValues[loop+1],buttCol,(Object)tmpButton);
      //    }
      //}
   }

  public int getColumnCount() {
       return columnNames.length;
   }   
                   
   public int getRowCount() {
       return columnValues.length;
   }   
                   
   public Object getValueAt(int rowIndex, int columnIndex) {
       return columnValues[rowIndex][columnIndex];
   }
  
   public String getColumnName(int col)
   {
      return (String)columnNames[col];
   }

   public boolean isCelleditable(int row, int col)
   {
       return false;
   }
  
   
   public Class getColumnClass(int c)
   {
       return getValueAt(0, c).getClass();
   }

}