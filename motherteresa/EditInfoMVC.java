/*
 * MTTableMVC.java
 *
 * Created on December 9, 2002, 2:43 PM
 * Author: Hein Badenhorst
 */
package motherteresa;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JScrollPane.*;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.MaskFormatter;
import javax.swing.table.*;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import java.util.Set;
import java.util.Iterator;
import java.text.Format;


import javax.swing.event.*;
import java.util.*; 

public class EditInfoMVC extends JPanel{
   
   // The initial width and height of the frame
  public static int WIDTH = 800;
  public static int HEIGHT = 400;
  private JScrollPane typePanel;
  public JScrollPane theScrollPane1;
  public static JButton updateButton;
  public static JButton addrowButton;
  public static JButton removeRowButton;
  public DBAccess typeDBAccess;
  public String[][] dataArray;
  private Object[][] tmpDataArray;
  private static final String SIMPLE_VIEW = "SIMPLE";
  private static final String OTHER_VIEW = "OTHER";
  private static final int tabsTop = JTabbedPane.TOP;
  private static final int tabsBottom = JTabbedPane.BOTTOM;
  private static final int tabsLeft = JTabbedPane.LEFT;
  private static final int tabsRight = JTabbedPane.RIGHT;
  private EditInfoMVC EditInfoMVCApp;
  public HashMap lookupTableDataCollection;
  public HashMap simpleTextCollection = new HashMap(10);
  private HashMap lookupTableData;
  private JButton openTextAreaButton;
  private Dimension textAreaSize = new Dimension(0,0);
  private boolean textFrame = false;
  private JScrollPane areaScrollPane;
  private JTextArea textArea;
  private JLayeredPane layeredPane;
  private JTextField textField;
  public  String fieldName = "";
  private String imagePath = "";
  private int selectedRow = 0;
  public String selectedKeyValue = "";
  public JTable selectTable;
  private String editModelName = "";
  private String editViewType = "";
  private int editTabPlacement = 0;
  private int editHeight = 0;
  private int editWidth = 0;
  private String keyDescription = "";
  private String defUserName = null;
  private char[] defPassword = null;


  
  // the Models
  MTTableDataModel theDataModel;
  MTTableDataModel selectionDataModel;

  //View and controller combined
  DataView theDataView; //   
  DataSelectView theDataSelectView; //   
  
  // Another View
  //Add other views here if needed;   
  
  // the Controller
  // combined into view
    
    private  class CtoMAdaptor implements ActionListener 
    {
        MTTableDataModel model;
        DataView controller;
        
        public CtoMAdaptor(DataView c, MTTableDataModel m) throws MTException
        {
            model = m;
            controller = c;
            try 
            {
                controller.dataUpdateButton.addActionListener(this);
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
                if (e.getSource() == controller.dataUpdateButton)
                {
                       
                    
                    
                    Set keySet = controller.tablesTextList.keySet();
                    Iterator iter = keySet.iterator();
                    int lastCount = keySet.size() - 1;
                    while (iter.hasNext())
                    {
                       String tableName = (String)iter.next();
                       Set fieldSet = ((HashMap)controller.tablesTextList.get(tableName)).keySet();
                       HashMap fieldList = (HashMap)controller.tablesTextList.get(tableName);
                       Iterator fieldIter = fieldSet.iterator();
                       HashMap dbAccessCollection = (HashMap)model.getdbAccessCollection();
                       DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)tableName);
                       while (fieldIter.hasNext())
                       {
                          String fieldName = (String)fieldIter.next();
                          String keyType = dataInstance.dataTable.getColumnKeyType(fieldName);
                          String fieldValue = "";
                          
                          if (keyType.equalsIgnoreCase("PRIMARY"))
                          {
                              fieldValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                              int loop = 0;
                              //Code to strip off leading zeros
                              for (loop = 0; loop < fieldValue.length(); loop++)
                              {
                                  if (fieldValue.substring(loop, loop+1).equalsIgnoreCase("0") == false)
                                  {
                                      break;
                                  }
                              }
                              fieldValue = fieldValue.substring(loop);
                              //dataInstance.addWhereFieldList(fieldName, fieldValue); 
                              HashMap wrapUpCollection = (HashMap)model.getdbWrapUpAccessCollection();
                              if (wrapUpCollection != null)
                              {
                                  Set wrapupKeySet = wrapUpCollection.keySet();
                                  Iterator wrapIter = wrapupKeySet.iterator();
                                  while (wrapIter.hasNext())
                                  {    
                                       String wrapTableName = (String)wrapIter.next();
                                       DBAccess wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)wrapTableName);
                                       String[] columnSet = wrapDataInstance.dataTable.getColumnNames();
                                       for (int loop1 = 0; loop1 < columnSet.length; loop1++)
                                       {
                                           if (columnSet[loop1].equalsIgnoreCase(fieldName))
                                           {
                                              wrapDataInstance.dataTable.addElement(fieldName, fieldValue);
                                              break;
                                           }
                                       }
                                  }
                              }
                          } else
                          if ((keyType.equalsIgnoreCase("TEXT_AREA_FIELD")) || (keyType.equalsIgnoreCase("DOCUMENT_AREA_FIELD")))
                          {
                              fieldValue =  ((JTextArea)fieldList.get((Object)fieldName)).getText();
                          } else
                          if (keyType.equalsIgnoreCase("FOREIGN"))
                          {
                             lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                             String comboValue = (String)((JComboBox)fieldList.get((Object)fieldName)).getSelectedItem();
                             Set lookupKeySet = lookupTableData.keySet();
                             Iterator lookupIter = lookupKeySet.iterator();
                             while (lookupIter.hasNext())
                             {
                                String theKey = (String)lookupIter.next();
                                String theValue = (String)lookupTableData.get((Object)theKey);
                                if (theValue.equalsIgnoreCase(comboValue))
                                {
                                    fieldValue =  theKey;
                                    break;
                                }
                             }  
                          } else
                          {
                              fieldValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                          }
                          dataInstance.dataTable.addElement(fieldName, fieldValue);
                       }
                       dataInstance.updateBulkData();
                       controller.dataUpdateButton.setEnabled(false);
                    }
                    HashMap wrapUpCollection = (HashMap)model.getdbWrapUpAccessCollection();
                    if (wrapUpCollection != null)
                    {
                       Set wrapupKeySet = wrapUpCollection.keySet();
                       Iterator wrapIter = wrapupKeySet.iterator();
                       while (wrapIter.hasNext())
                       {     
                            String wrapTableName = (String)wrapIter.next();
                            DBAccess wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)wrapTableName);
                            wrapDataInstance.insertBulkData();
                       }
                    }
                    //model.addData();
                } else
                if (e.getSource() == addrowButton)
                {
                    //model.addRow();
                } else
                if (e.getSource() == removeRowButton)
                {
                    //model.removeRow();
                }
                 else
                if (e.getSource() == openTextAreaButton)
                {
                    //model.removeRow();
                }
                    
            }
            catch (MTException exc) 
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception ex) 
            {
               JOptionPane.showMessageDialog(theDataView, ex.getMessage(), "Unspecified Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } 

    class MTTableDataModel extends MTBusinessModel 
    {
        public MTTableDataModel(String modelName, String userName, char[] password) throws MTException
        {
            super(modelName, InfoManager.OS_VERSION,userName, password);
        }

        public MTTableDataModel(String modelName, String keyValue, String userName, char[] password) throws MTException
        {
            super(modelName, keyValue, InfoManager.OS_VERSION,userName, password);
        }
        
        public void updateData()
        {
             /*try
             {
                
             } catch (Exception exc) 
             {
                errorString = exc.getMessage();
             } */
        }           
    }

     public class DataSelectView extends JPanel
    {
        private JTabbedPane tabbedPane;
        public JButton dataUpdateButton;
        private HashMap fieldTextList;
        public HashMap tablesTextList;
        public DataSelectView(String viewType, int tabPlacement, MTTableDataModel _theModel, int _height, int  _width) throws MTException
        {
            if(viewType.equalsIgnoreCase(SIMPLE_VIEW))
            {
                try
                {
                    initSimpleDataSelectView(tabPlacement, _theModel,  _height, _width);
                }
                catch (MTException exc)
                {
                    throw exc;
                }
            } else
            if(viewType.equalsIgnoreCase(OTHER_VIEW))
            {
                //try
                //{
                    //initOtherDataView(tabPlacement, _theModel, _height, _width);
                //}
                //catch (MTException ex)
                //{
                    //throw ex;
                //}
            }
        }
        
        public void initSimpleDataSelectView(int _tabPlacement, MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                Border raisedBevel, loweredBevel, compoundBorder;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

                tabbedPane = new JTabbedPane(_tabPlacement);
                JPanel controlPane = new JPanel(); 
                String theTableName = null;
                String theGroupLabel = null;
                theGroupLabel = mtModel.getGroupName();
                setLayout(new BorderLayout());
                JLabel mainHeaderLabel = new JLabel(theGroupLabel);
                mainHeaderLabel.setBorder(BorderFactory.createEtchedBorder());
                this.add(mainHeaderLabel,  BorderLayout.NORTH);
                tablesTextList = new HashMap(mtModel.getTableCount());
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    tabbedPane.addTab((String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop)), null, createSelectPane(mtModel, theTableName, height, width));
                    tablesTextList.put((Object)theTableName,(Object)fieldTextList.clone()); 
                }
                this.add(tabbedPane,  BorderLayout.CENTER);
            }
            catch (MTException e)
            {
                throw new MTException(e.getExceptionType(), e.getErrorMessage());
            }
            catch (Exception e)
            {
                throw new MTException(InfoManager.VIEW_BUILDER_ERROR, e.getMessage());
            }
            
        }
        
        public JPanel createSelectPane(MTTableDataModel _Model, String _TableName, int _height, int _width) throws MTException
        {
            int rowCount  =  0;
            int colCount = 0;
            int colIndex = 0;
            String[] theKeyValues = null;
            String[] theDescValues = null;
            JLabel label1;
            JLabel label2;
            Border raisedBevel, loweredBevel, compoundBorder;
            HashMap dbAccessCollection = (HashMap)_Model.getdbAccessCollection();
            //lookupTableDataCollection = (HashMap)_Model.getLookupTableDataCollection();
            DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)_TableName);
            raisedBevel = BorderFactory.createRaisedBevelBorder();
            loweredBevel = BorderFactory.createLoweredBevelBorder();
            compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
            
            JPanel dataPanel = new JPanel();
            JPanel labelPanel = new JPanel();
            JPanel fieldPanel = new JPanel();
            JPanel southPanel = new JPanel();
            JPanel buttonPanel = new JPanel();
            JPanel headingPanel = new JPanel();
            rowCount = dataInstance.dataTable.getColumnCount();
            if (rowCount < 21)
            {
               rowCount = 21; 
            }
            dataPanel.setLayout(new BorderLayout());
            labelPanel.setLayout(new GridLayout(rowCount, 0));
            fieldPanel.setLayout(new GridLayout(rowCount, 0));
            buttonPanel.setLayout(new GridLayout(1,0,5,5));
            buttonPanel.setBorder(compoundBorder);
            southPanel.setLayout(new BorderLayout());
            dataPanel.setBorder(BorderFactory.createEtchedBorder());
            String[] ColumnNameSelection = dataInstance.getInputFieldList();
            
            String[] theColumnNames = dataInstance.dataTable.getColumnNames();
            String[] theColumnAliases = dataInstance.dataTable.getColumnAliases();
            String[] theColumnTypes = dataInstance.dataTable.getColumnKeyTypes();
            boolean[] theColumnLocks = dataInstance.dataTable.getColumnLocks();
            boolean[] theColumnDisplays = dataInstance.dataTable.getColumnDisplays();
            String thePanelHeading = (String)_Model.getTableAliases().get((Object)_TableName);
            JLabel panelHeadingLabel = new JLabel(thePanelHeading);
            panelHeadingLabel.setForeground(new Color(255,255,255));
            panelHeadingLabel.setPreferredSize(new Dimension(200,20));
            panelHeadingLabel.setFont(new Font("Serif",Font.BOLD,14));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setBackground(new Color(0,50,150));
            headingPanel.add(panelHeadingLabel);
            dataPanel.add(headingPanel, BorderLayout.NORTH);
            colCount = ColumnNameSelection.length;
            
            fieldTextList = new HashMap(colCount);
            MaskFormatter formatter;
            JFormattedTextField ftf;
            try
            {
              for (int fieldIndex = 0; fieldIndex < colCount; fieldIndex++)
              { 
                colIndex = 0;  
                for (int loop = 0; loop < theColumnNames.length; loop++)
                {
                    if (ColumnNameSelection[fieldIndex].equalsIgnoreCase(theColumnNames[loop]))
                    {
                        colIndex = loop;
                        break;
                    }
                }
                String theFieldLabel = theColumnAliases[colIndex] + ":";
                String theFieldName = theColumnNames[colIndex];
                String theFieldType = theColumnTypes[colIndex];
                String[] theFieldValues = dataInstance.dataTable.getColumn(colIndex);
                if (theDescValues == null)
                {
                    theDescValues = new String[theFieldValues.length];
                }
                String theFieldValue = "";
                int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                boolean theFieldLock = theColumnLocks[colIndex];
                boolean theFieldDisplay = theColumnDisplays[colIndex];
                JLabel fieldLabel = new JLabel(theFieldLabel);
                fieldLabel.setPreferredSize(new Dimension(200,20));
                if (theFieldType.equalsIgnoreCase("PRIMARY"))
                {
                    theKeyValues = new String[theFieldValues.length];
                    for (int loop1 = 0; loop1 < theFieldValues.length; loop1++)
                    {
                        String zeroPadding = "";
                        theFieldValue = theFieldValues[loop1];
                        for (int loop = 0; loop < fieldLength - theFieldValue.length(); loop++)
                        {
                            zeroPadding = zeroPadding + "0";
                        }
                        theFieldValue = zeroPadding + theFieldValue;
                        theKeyValues[loop1] = theFieldValue;
                    }
                } else
                if (theFieldType.equalsIgnoreCase("FOREIGN"))
                {
                    lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theFieldName);
                    Set keySet = lookupTableData.keySet();
                    Iterator iter = keySet.iterator();
                    String[] comboBoxData = new String[lookupTableData.size()];
                    int comboBoxDataIndex = 0;
                    while (iter.hasNext())
                    {
                         String theKey = (String)iter.next();
                         String theValue = (String)lookupTableData.get((Object)theKey);
                         comboBoxData[comboBoxDataIndex] = theValue;
                         comboBoxDataIndex++;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                    }
                } else
                {
                    for (int loop1 = 0; loop1 < theFieldValues.length; loop1++)
                    {
                       if (theDescValues[loop1] != null)
                       {
                          theDescValues[loop1] = theDescValues[loop1] + " " +  theFieldValues[loop1];
                       } else
                       {
                          theDescValues[loop1] = theFieldValues[loop1];
                       }
                    }
                } //end else
              } //end for
              
              String[][] tableData = new String[theKeyValues.length][2];
              String[] tableHeading = new String[2];
              ArrayList headingList = _Model.getSelectTableHeadings();
              tableHeading[0] = (String)headingList.get(0);
              tableHeading[1] = (String)headingList.get(1);
              
              for (int loop = 0; loop < theKeyValues.length; loop++)
              {
                 tableData[loop][0] = theKeyValues[loop];
                 tableData[loop][1] = theDescValues[loop];
              }

              selectTable = new JTable(tableData,tableHeading);
              JScrollPane scrollPane =  new JScrollPane(selectTable);
              selectTable.setPreferredScrollableViewportSize(new Dimension(400,600));
              selectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
              dataPanel.add(scrollPane, BorderLayout.WEST);
              ListSelectionModel rowSM = selectTable.getSelectionModel();
              rowSM.addListSelectionListener(new tableListener());              
            } //end try
            catch (Exception ex) 
            {
                throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
            }
            return dataPanel;
        }
        public JTabbedPane getTabbedPane()
        {
            return tabbedPane;
        }
    }
     

    public class DataView extends JPanel
    {
        private JTabbedPane tabbedPane;
        public JButton dataUpdateButton;
        private HashMap fieldTextList;
        public HashMap tablesTextList;
        public DataView(String viewType, int tabPlacement, MTTableDataModel _theModel, int _height, int  _width) throws MTException
        {
            if(viewType.equalsIgnoreCase(SIMPLE_VIEW))
            {
                try
                {
                    initSimpleDataView(tabPlacement, _theModel, _height, _width);
                }
                catch (MTException exc)
                {
                    throw exc;
                }
            } else
            if(viewType.equalsIgnoreCase(OTHER_VIEW))
            {
                //try
                //{
                    //initOtherDataView(tabPlacement, _theModel, _height, _width);
                //}
                //catch (MTException ex)
                //{
                    //throw ex;
                //}
            }
        }
        
        public void initSimpleDataView(int _tabPlacement, MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                Border raisedBevel, loweredBevel, compoundBorder;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);

                tabbedPane = new JTabbedPane(_tabPlacement);
                JPanel controlPane = new JPanel(); 
                String theTableName = null;
                String theGroupLabel = null;
                theGroupLabel = mtModel.getGroupName();
                setLayout(new BorderLayout());
                JLabel mainHeaderLabel = new JLabel(theGroupLabel);
                mainHeaderLabel.setBorder(BorderFactory.createEtchedBorder());
                this.add(mainHeaderLabel,  BorderLayout.NORTH);
                tablesTextList = new HashMap(mtModel.getTableCount());
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    tabbedPane.addTab((String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop)), null, createPane(mtModel, theTableName, height, width));
                    tablesTextList.put((Object)theTableName,(Object)fieldTextList.clone()); 
                }
                this.add(tabbedPane,  BorderLayout.CENTER);

                dataUpdateButton = new JButton("Update");
                controlPane.setLayout(new BorderLayout());
                controlPane.setBorder(compoundBorder);
                controlPane.add(dataUpdateButton, BorderLayout.CENTER);
                this.add(controlPane,  BorderLayout.SOUTH);
            }
            catch (MTException e)
            {
                throw new MTException(e.getExceptionType(), e.getErrorMessage());
            }
            catch (Exception e)
            {
                throw new MTException(InfoManager.VIEW_BUILDER_ERROR, e.getMessage());
            }
            
        }
        
        public JPanel createPane(MTTableDataModel _Model, String _TableName, int _height, int _width) throws MTException
        {
            int rowCount  =  0;
            int colCount = 0;
            Border raisedBevel, loweredBevel, compoundBorder;

            HashMap dbAccessCollection = (HashMap)_Model.getdbAccessCollection();
            lookupTableDataCollection = (HashMap)_Model.getLookupTableDataCollection();
            DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)_TableName);
            raisedBevel = BorderFactory.createRaisedBevelBorder();
            loweredBevel = BorderFactory.createLoweredBevelBorder();
            compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
            
            JPanel dataPanel = new JPanel();
            JPanel labelPanel = new JPanel();
            JPanel fieldPanel = new JPanel();
            JPanel southPanel = new JPanel();
            JPanel buttonPanel = new JPanel();
            JPanel headingPanel = new JPanel();
            
            rowCount = dataInstance.dataTable.getColumnCount();
            if (rowCount < 21)
            {
               rowCount = 21; 
            }
            dataPanel.setLayout(new BorderLayout());
            labelPanel.setLayout(new GridLayout(rowCount, 0));
            fieldPanel.setLayout(new GridLayout(rowCount, 0));
            buttonPanel.setLayout(new GridLayout(1,0,5,5));
            buttonPanel.setBorder(compoundBorder);
            southPanel.setLayout(new BorderLayout());
            //JButton clearButton = new JButton("Clear");
            //JButton searchButton = new JButton("Search");
            //buttonPanel.add(clearButton);
            //buttonPanel.add(searchButton);
            dataPanel.setBorder(BorderFactory.createEtchedBorder());
            String[] theColumnNames = dataInstance.dataTable.getColumnNames();
            String[] theColumnAliases = dataInstance.dataTable.getColumnAliases();
            String[] theColumnTypes = dataInstance.dataTable.getColumnKeyTypes();
            boolean[] theColumnLocks = dataInstance.dataTable.getColumnLocks();
            boolean[] theColumnDisplays = dataInstance.dataTable.getColumnDisplays();
            String thePanelHeading = (String)_Model.getTableAliases().get((Object)_TableName);
            thePanelHeading = thePanelHeading + ": " + keyDescription;
            JLabel panelHeadingLabel = new JLabel(thePanelHeading);
            panelHeadingLabel.setForeground(new Color(255,255,255));
            panelHeadingLabel.setPreferredSize(new Dimension(200,20));
            panelHeadingLabel.setFont(new Font("Serif",Font.BOLD,14));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setBackground(new Color(0,50,150));
            headingPanel.add(panelHeadingLabel);
            dataPanel.add(headingPanel, BorderLayout.NORTH);
            colCount = dataInstance.dataTable.getColumnCount();
            fieldTextList = new HashMap(colCount);
            MaskFormatter formatter;
            JFormattedTextField ftf;
            String selectedValue = "";
            try
            {
              for (int fieldIndex = 0; fieldIndex < colCount; fieldIndex++)
              {
                String theFieldLabel = theColumnAliases[fieldIndex] + ":";
                String theFieldName = theColumnNames[fieldIndex];
                String theFieldType = theColumnTypes[fieldIndex];
                String theFieldValue = dataInstance.dataTable.getDataAt(0, fieldIndex);
                int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                boolean theFieldLock = theColumnLocks[fieldIndex];
                boolean theFieldDisplay = theColumnDisplays[fieldIndex];
                JLabel fieldLabel = new JLabel(theFieldLabel);
                fieldLabel.setPreferredSize(new Dimension(200,20));
                if (theFieldType.equalsIgnoreCase("PRIMARY"))
                {
                    String zeroPadding = "";
                    for (int loop = 0; loop < fieldLength - theFieldValue.length(); loop++)
                    {
                        zeroPadding = zeroPadding + "0";
                    }
                    theFieldValue = zeroPadding + theFieldValue;
                    JTextField fieldText = new JTextField(theFieldValue,20);
                    if (theFieldLock)
                    {
                        fieldText.setEnabled(false);
                    } else
                    {
                        fieldText.setEnabled(true);
                    }
                    fieldText.setPreferredSize(new Dimension(200,20));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(fieldText);
                        //fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldText);
                    }
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldText);
                } else
                if (theFieldType.equalsIgnoreCase("FOREIGN"))
                {
                    lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theFieldName);
                    Set keySet = lookupTableData.keySet();
                    Iterator iter = keySet.iterator();
                    String[] comboBoxData = new String[lookupTableData.size()];
                    int comboBoxDataIndex = 0;
                    while (iter.hasNext())
                    {
                         String theKey = (String)iter.next();
                         String theValue = (String)lookupTableData.get((Object)theKey);
                         if (theFieldValue.equalsIgnoreCase(theKey))
                         {
                             selectedValue = theValue;
                         }
                         comboBoxData[comboBoxDataIndex] = theValue;
                         comboBoxDataIndex++;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    cb.setSelectedItem(selectedValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                    }
                } else
                if (theFieldType.equalsIgnoreCase("PHONE_FIELD"))
                {
                    formatter = new  MaskFormatter("(###) ###-####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("CELL_FIELD"))
                {
                    formatter = new  MaskFormatter("(###) ###-####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("AREA_FIELD"))
                {
                    formatter = new  MaskFormatter("####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("ID_FIELD"))
                {
                    formatter = new  MaskFormatter("#############");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                {
                    formatter = new  MaskFormatter("##/##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if ((theFieldType.equalsIgnoreCase("TEXT_AREA_FIELD")) || (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD")))
                {
                    //textField = new JTextField((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                    //textField.addMouseListener(new TheMouseListener());
                    //textField.setBackground(new Color(200,200,200));
                    //textField.setForeground(new Color(200,200,200));
                    ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                    openTextAreaButton = new JButton("Push to open",openIcon);
                    openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                    openTextAreaButton.setPreferredSize(new Dimension(200,20));
                    openTextAreaButton.addMouseListener(new TheMouseListener());
                    //openTextAreaButton.addActionListener(this);
                    if (theFieldDisplay == true)
                    {
                        textArea = new JTextArea(80,25);
                        textArea.setText(theFieldValue);
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(openTextAreaButton);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)textArea);
                    }
                } else
                if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                {
                    formatter = new  MaskFormatter("U U U");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                } else
                if (theFieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                {
                    formatter = new  MaskFormatter("####-##-## ##:##:##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("DATE_FIELD"))
                {
                    formatter = new  MaskFormatter("####-##-##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                } else
                {
                    String mask = "";
                    for (int loop = 0; loop < fieldLength; loop++)
                    {
                        mask = mask + "*";
                    }
                    formatter = new  MaskFormatter(mask);
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldLock)
                    {
                        ftf.setEnabled(false);
                    } else
                    {
                        ftf.setEnabled(true);
                    }
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                } //end else
              } //end for
            } //end try
            catch (Exception ex) 
            {
                throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
            }
            southPanel.add(buttonPanel, BorderLayout.EAST);
            dataPanel.add(labelPanel, BorderLayout.WEST);
            dataPanel.add(fieldPanel, BorderLayout.EAST);
            dataPanel.add(southPanel, BorderLayout.SOUTH);
            
            return dataPanel;
        }
        public JTabbedPane getTabbedPane()
        {
            return tabbedPane;
        }
    }
    
    class TheMouseListener extends MouseInputAdapter implements InternalFrameListener 
    {
        public void mouseReleased(MouseEvent mEvent)
        {
           if ( (SwingUtilities.isLeftMouseButton(mEvent)) && (textFrame == false))
           {
                   String cellReference = "";
                   String tableName = "";
                   String fieldName = "";
                   String theToken = "";
                   cellReference = ((JButton)mEvent.getComponent()).getToolTipText();
                   //((JButton)mEvent.getComponent()).setVisible(false);
                   
                   StringTokenizer fieldTokens = new StringTokenizer(cellReference,",");	
                   int tokenCount = fieldTokens.countTokens();
                   for (int cellReferenceIndex = 0; cellReferenceIndex <  tokenCount; cellReferenceIndex++)
                   {
                        theToken = (String)fieldTokens.nextToken();
                        if (cellReferenceIndex == 0)
                        {
                            tableName = theToken;
                        } else
                        {
                            fieldName = theToken;
                        }
                   }
                   String frameHeading = fieldName.replace('_',' ');
                   JInternalFrame textAreaFrame = new JInternalFrame(frameHeading,
                   true, //resizable
                   true, //closable
                   true, //maximizable
                   true); //iconifiable
                   textAreaFrame.setSize(new Dimension(300,300));
                   textAreaFrame.setLocation(200, 200);
                   textAreaFrame.setVisible(true);
       	           textAreaFrame.addInternalFrameListener(this);
                   textArea = (JTextArea)((HashMap)theDataView.tablesTextList.get((Object)tableName)).get((Object)fieldName);
                   //textArea.disable();//.setEditable(false);
                   areaScrollPane = new JScrollPane(textArea);
                   areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                   areaScrollPane.setPreferredSize(new Dimension(300,300));
                   textAreaFrame.getContentPane().add(areaScrollPane);
                   add(textAreaFrame);
                   textAreaFrame.moveToFront();
                   textAreaFrame.requestFocus();
                   textFrame = true;
                  // textArea.setFocus();
           }
        }
       
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameClosed(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameClosing(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
           textFrame = false;
        }
        
        public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameIconified(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameOpened(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
    }

    
     class tableListener implements ListSelectionListener 
     {
         public void valueChanged(ListSelectionEvent e) 
         {
            try
            {
              if (e.getValueIsAdjusting()) return;
              ListSelectionModel lsm = (ListSelectionModel)e.getSource();
              if (lsm.isSelectionEmpty())
              {
              } else
              {
                  selectedRow = lsm.getMinSelectionIndex();
                  selectedKeyValue =  (String)selectTable.getValueAt(selectedRow,0);
                  keyDescription =  (String)selectTable.getValueAt(selectedRow,1);

                  theDataSelectView.removeAll();//show(false);
                  theDataModel = new MTTableDataModel(editModelName, selectedKeyValue,defUserName,defPassword);
                  theDataView = new DataView(editViewType, editTabPlacement, theDataModel, editHeight, editWidth);
                  removeAll();
                  add(theDataView);
                  CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
                  EditInfoMVCApp.updateUI();
               }
            }
            catch (MTException exc)
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            
         }
   }
   
    public EditInfoMVC(String selectionModelName, String modelName, String viewType, String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        EditInfoMVCApp = this;
        defUserName = userName;
        defPassword = password;
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName, password);
        theScrollPane1 = typePanel;
    }

    public EditInfoMVC(String selectionModelName, String modelName, String viewType, int height, int width, String userName, char[] password) 
    {
        EditInfoMVCApp = this;
        defUserName = userName;
        defPassword = password;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, height, width,userName, password);
        theScrollPane1 = typePanel;
    }

    public EditInfoMVC(String selectionModelName, String modelName, String viewType, int tabPlacement, int height, int width, String userName, char[] password) 
    {
        EditInfoMVCApp = this;
        defUserName = userName;
        defPassword = password;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }

    public void initComponents(String selectionModelName, String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
    {
        try
        {
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
            {
               imagePath = InfoManager.WINDOWS_IMAGE_PATH;
               //docScanPath = InfoManager.WINDOWS_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("UNIX"))
            {
               imagePath = InfoManager.UNIX_IMAGE_PATH;
               //docScanPath = InfoManager.UNIX_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINXP"))
            {
               imagePath = InfoManager.WINXP_IMAGE_PATH;
               //docScanPath = InfoManager.WINXP_DOCUMENT_PATH;
            }
            // Set Up DataModel by instantiating MTBusinessModel
            editModelName  = modelName;
            editViewType = viewType;
            editTabPlacement = tabPlacement;
            editHeight = _height;
            editWidth = _width;
            selectionDataModel = new  MTTableDataModel(selectionModelName,userName,password);
            theDataSelectView = new DataSelectView(viewType, tabPlacement, selectionDataModel, _height, _width);
            add(theDataSelectView);
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }
   
}
