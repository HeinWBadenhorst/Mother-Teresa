package motherteresa;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.print.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Dimension;
import javax.swing.border.Border;



public class TypeTableMVC extends JPanel implements Printable{
   
   // The initial width and height of the frame
  private static final int WIDTH = 800;
  private static final int HEIGHT = 400;
  private JScrollPane typePanel;
  public JScrollPane theScrollPane1;
  public static JButton updateButton;
  public static JButton addrowButton;
  public static JButton removeRowButton;
  public static JButton printTableButton;
  public DBAccess typeDBAccess;
  public String[][] dataArray;
  private Object[][] tmpDataArray;
  public String errorString = null;
 
  
  // the Model
  TypeTableDataModel tableModel;// = new TypeTableDataModel();
  
  // a View
  JTable tableView; // = new JTable(tableModel);   
  
  // Another View
  //Add other views here if needed;   
 
  
  // the Controller
  DataTable tableController;   
  
    
    private  class TCtoTMAdaptor implements ActionListener
    {
        TypeTableDataModel model;
        DataTable controller;
        DBAccess _dbAccess;
        
        public TCtoTMAdaptor(DataTable c, TypeTableDataModel m, DBAccess d) throws MTException
        {
            model = m;
            controller = c;
            _dbAccess = d;
            try 
            {

                int rowCount = controller.getRowCount();
                int columnCount = controller.getColumnCount();
                int i,j = 0;
                Object dataValue = null;
                for (i = 0; i < rowCount; i++)
                {
                   for (j = 0; j < columnCount; j++)
                   {
                       dataValue = (Object)controller.getDataAt(i,j);
                       model.setValueAt(dataValue,i,j);
                       
                   }
                }
            } 
            catch(Exception e)
            {
               throw new MTException(InfoManager.ADAPTER_ERROR, e.getMessage());
            }
         }
    
        public void actionPerformed(ActionEvent e) 
        {
            try
            {
                if (e.getSource() == updateButton)
                {
                    model.addData();
                } else
                if (e.getSource() == addrowButton)
                {
                    model.addRow();
                } else
                if (e.getSource() == removeRowButton)
                {
                    model.removeRow();
                } else
                if (e.getSource() == printTableButton)
                {
                    model.printTable();
                }
             }
             catch (MTException exc) 
             {
                  JOptionPane.showMessageDialog(typePanel,exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
             }

        }
    } 

    class TypeTableDataModel extends AbstractTableModel
    {
        String[] columnNames = (String[])typeDBAccess.dataTable.getColumnAliases();
        Object[][] data = dataArray;

        public int getColumnCount()
        {
            return columnNames.length;
        }
        
        public String getColumnName(int col)
        {
            return columnNames[col];
        }

        public int getRowCount()
        {
            return data.length;
        }
        
        public void removeRow()
        {
           tmpDataArray = new String[this.getRowCount()-1][this.getColumnCount()];
           for (int rowLoop = 0; rowLoop < this.getRowCount() - 1; rowLoop++)
           {
               for (int colLoop = 0; colLoop < this.getColumnCount(); colLoop++)
               {
                  tmpDataArray[rowLoop][colLoop] = this.getValueAt(rowLoop,colLoop);
               }
           }
           data = tmpDataArray;
           fireTableRowsDeleted(this.getRowCount(),this.getRowCount());
        }

        public void addRow()
        {
           tmpDataArray = new String[this.getRowCount()+1][this.getColumnCount()];
           for (int rowLoop = 0; rowLoop < this.getRowCount(); rowLoop++)
           {
               for (int colLoop = 0; colLoop < this.getColumnCount(); colLoop++)
               {
                  tmpDataArray[rowLoop][colLoop] = this.getValueAt(rowLoop,colLoop);
               }
           }
           data = tmpDataArray;
           fireTableRowsInserted(this.getRowCount(),this.getRowCount());
        }

        public void addData() throws MTException
        {
            fireTableDataChanged();
            try
            {
                for (int rowLoop = 0; rowLoop < this.getRowCount(); rowLoop++)
                {
                    for (int colLoop = 0; colLoop < this.getColumnCount(); colLoop++)
                    {
                        typeDBAccess.dataTable.addOrReplaceElement(rowLoop, colLoop, (String)this.getValueAt(rowLoop,colLoop));
                    }
                }
                int result = 0;
                result =  typeDBAccess.clearTableData();
                result = typeDBAccess.insertBulkData();
             }
             catch (MTException exc) 
             {
                throw exc;
             }
             catch (Exception e) 
             {
                throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
             }
        }
        
        public void printTable()
        {
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintable(TypeTableMVC.this);
            pj.printDialog();
            try
            {
                pj.print();
            } 
            catch (Exception PrintException)
            {
                //do nothing
            }
            
        }

        public Object getValueAt(int row, int column)
        {
            return data[row][column];
        }
        
        public void setValueAt(Object dataValue, int row, int column)
        {
            data[row][column] = dataValue;
            fireTableCellUpdated(row, column);
        }
        
        public boolean isCellEditable(int row, int column)
        {
            return true;//(column != 0);
        }
    }

    public TypeTableMVC(String tableName,String userName, char[] password) throws MTException 
    {
        
        /** Creates new form typePanel */
        initComponents(tableName, InfoManager.OS_VERSION, HEIGHT, WIDTH, userName, password);
        theScrollPane1 = typePanel;
    }

    public TypeTableMVC(String tableName, int height, int width,String userName, char[] password) throws MTException  
    {
        /** Creates new form typePanel */
        initComponents(tableName, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }
    
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        int fontHeight=g2.getFontMetrics().getHeight();
        int fontDesent=g2.getFontMetrics().getDescent();
        double pageHeight = pageFormat.getImageableHeight() - fontHeight;
        double pageWidth = pageFormat.getImageableWidth();
        double tableWidth = (double) tableView.getColumnModel().getTotalColumnWidth();
        double scale = 1;
        if (tableWidth >= pageWidth)
        {
           scale = pageWidth / tableWidth;
        }
        double headerHeightOnPage = tableView.getTableHeader().getHeight() * scale;
        double tableWidthOnPage = tableWidth * scale;
        double oneRowHeight = (tableView.getRowHeight() + tableView.getRowMargin()) * scale;
        int numRowsOnAPage = (int) ((pageHeight - headerHeightOnPage) / oneRowHeight);
        double pageHeightForTable = oneRowHeight*numRowsOnAPage;
        int totalNumPages = (int)Math.ceil(((double)tableView.getRowCount()) / numRowsOnAPage);
        if (pageIndex >= totalNumPages)
        {
            return NO_SUCH_PAGE;
        }
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2.drawString("Page: "+ (pageIndex + 1),(int)pageWidth / 2 - 35, (int) (pageHeight + fontHeight-fontDesent));
        g2.translate(0f,headerHeightOnPage);
        g2.translate(0f, - pageIndex * pageHeightForTable);
        //clipping code
        if (pageIndex + 1 == totalNumPages)
        {
            int lastRowPrinted = numRowsOnAPage * pageIndex;
            int numRowsLeft = tableView.getRowCount() - lastRowPrinted;
            g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage), (int) Math.ceil(oneRowHeight * numRowsLeft));
        } else
        {
            g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage), (int) Math.ceil(pageHeightForTable));
        }
        g2.scale(scale,scale);
        tableView.paint(g2);
        g2.scale(1/scale, 1/scale);
        g2.translate(0f,pageIndex * pageHeightForTable);
        g2.translate(0f,-headerHeightOnPage);
        g2.setClip(0,0, (int) Math.ceil(tableWidthOnPage), (int) Math.ceil(headerHeightOnPage));
        g2.scale(scale,scale);
        tableView.getTableHeader().paint(g2);
        return Printable.PAGE_EXISTS;
    }

    public void initComponents(String tableName, String _theOS, int _height, int _width, String userName, char[] password)
    {
        try
        {
            int result = 0;
            // Set Up DataTable by invoking DBAccess
            typeDBAccess = new DBAccess("localhost","medibase",tableName, userName, password, _theOS);
            int fieldNameIndex = 0;
            for (fieldNameIndex = 0; fieldNameIndex < typeDBAccess.dataTable.getColumnCount(); fieldNameIndex++)
            {
                typeDBAccess.addInputFieldList(typeDBAccess.dataTable.getColumnName(fieldNameIndex),"a");
            }
            result = typeDBAccess.selectData();
            dataArray = new String[typeDBAccess.dataTable.getRowCount()][typeDBAccess.dataTable.getColumnCount()];
            for (int rowLoop = 0; rowLoop < typeDBAccess.dataTable.getRowCount(); rowLoop++)
            {
                for (int colLoop = 0; colLoop < typeDBAccess.dataTable.getColumnCount(); colLoop++)
                {
                    dataArray[rowLoop][colLoop] = typeDBAccess.dataTable.getDataAt(rowLoop,colLoop);
                }
            }
            tableController = typeDBAccess.dataTable;
            tableModel = new TypeTableDataModel();
            tableView = new JTable(tableModel);
            // Hook the Controller up to the Model
            TCtoTMAdaptor CtoM = new TCtoTMAdaptor(tableController, tableModel, typeDBAccess);
            setSize(_width, _height);
        
            //initialize column widths here
            for (int loop=0; loop < typeDBAccess.dataTable.getColumnCount(); loop++)
            {
                tableView.sizeColumnsToFit(loop);
                //String strColSize = typeDBAccess.dataTable.getColumnLength(loop);
                //int intColSize = Integer.parseInt(strColSize);
                //set column width in table
            }
        
            typePanel = new JScrollPane(tableView);
            //tableView.setPreferredScrollableViewportSize(new Dimension(_width,_height));
            
            Border raisedBevel, loweredBevel, compoundBorder;
            raisedBevel = BorderFactory.createRaisedBevelBorder();
            loweredBevel = BorderFactory.createLoweredBevelBorder();
            compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1,0,5,5));
            buttonPanel.setBorder(compoundBorder);

            updateButton = new JButton("Update");
            addrowButton = new JButton("Add Row");
            removeRowButton = new JButton("Remove Row");
            printTableButton = new JButton("Print Table");

            buttonPanel.add(addrowButton);
            buttonPanel.add(removeRowButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(printTableButton);

            add(typePanel, BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.SOUTH);

            //add action listeners
            updateButton.addActionListener(CtoM);
            addrowButton.addActionListener(CtoM);
            removeRowButton.addActionListener(CtoM);
            printTableButton.addActionListener(CtoM);
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(typePanel,e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
     }
}
