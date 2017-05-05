/*
 * EditInfoByDateMVC.java
 *
 * Created on February 12, 2003, 2:43 PM
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
import java.io.*;
import javax.swing.event.*;
import java.util.*;
import java.beans.*;

public class EditInfoByDateMVC extends JPanel{
   
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
  private EditInfoByDateMVC EditInfoByDateMVCApp;
  public HashMap lookupTableDataCollection;
  public HashMap simpleTextCollection = new HashMap(10);
  private HashMap lookupTableData;
  private JButton openTextAreaButton;
  private JButton addFileButton;
  private JButton scanDocButton;
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
  private ArrayList fieldTextItems;
  private String docScanPath = "";
  private TwainScan twainScanner;
  private IniFile config = new IniFile();
  boolean iniFilePresent = false;
  private String defUserName = InfoManager.DEF_USER_NAME;
  private char[] defPassword = InfoManager.DEF_PASSWORD;

  
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
                       
                    ArrayList fieldValueList = null;
                    String[] fieldValueArray = null;
                    String[] fieldComboValueArray = null;
                    String comboValue = null;
                    JCheckBox checkBox = null;
                    Set keySet = controller.tablesTextList.keySet();
                    Iterator iter = keySet.iterator();
                    int lastCount = keySet.size() - 1;
                    DBAccess wrapDataInstance = null;
                    
                    while (iter.hasNext())
                    {
                       String tableName = (String)iter.next();
                       Set fieldSet = ((HashMap)controller.tablesTextList.get(tableName)).keySet();
                       HashMap fieldList = (HashMap)controller.tablesTextList.get(tableName);
                       //get deleteBoxes
                       HashMap deleteList = (HashMap)controller.tablesDeleteBoxes.get(tableName);
                       Iterator fieldIter = fieldSet.iterator();
                       HashMap dbAccessCollection = (HashMap)model.getdbAccessCollection();
                       HashMap wrapUpCollection = (HashMap)model.getdbWrapUpAccessCollection();
                       if (wrapUpCollection != null)
                       {    
                           wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)model.getPrimaryWrapUpTableName());
                       }
                       if ((wrapUpCollection != null) && (wrapDataInstance == null))
                       {
                           wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)model.getSecondaryWrapUpTableName());
                       }
                       DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)tableName);
                       while (fieldIter.hasNext())
                       {
                          String fieldName = (String)fieldIter.next();
                          String keyType = dataInstance.dataTable.getColumnKeyType(fieldName);
                          String fieldValue = "";
                          
                          if (keyType.equalsIgnoreCase("PRIMARY"))
                          {
                              fieldValueList = (ArrayList)fieldList.get((Object)fieldName);
                              fieldValueArray = new String[fieldValueList.size()];
                              for (int loop = 0; loop < fieldValueList.size(); loop++)
                              {    
                                  fieldValue = ((JTextField)fieldValueList.get(loop)).getText();
                                  //Code to strip off leading zeros
                                  int stripLoop = 0;
                                  for (stripLoop = 0; stripLoop < fieldValue.length(); stripLoop++)
                                  {
                                      if (fieldValue.substring(stripLoop, stripLoop+1).equalsIgnoreCase("0") == false)
                                      {
                                          break;
                                      }
                                  }
                                  fieldValue = fieldValue.substring(stripLoop);
                                  fieldValueArray[loop] = fieldValue;
                                  //***************************
                                  if (wrapDataInstance != null)
                                  {
                                      checkBox = (JCheckBox)deleteList.get((Object)fieldValue);
                                      String checkBoxValue = null;
                                      if (checkBox.isSelected())
                                      {
                                          checkBoxValue = "1";
                                      } else
                                          checkBoxValue = "0";
                                      String[] colValues = wrapDataInstance.dataTable.getColumn(fieldName);
                                      int rowPos = 0;
                                      for (int colLoop = 0; colLoop < colValues.length; colLoop++)
                                      {
                                          if (colValues[colLoop].equalsIgnoreCase(fieldValue))
                                          {
                                              rowPos = colLoop;
                                              break;
                                          }
                                      }
                                      int colPos = wrapDataInstance.dataTable.getColumnIndex("DELETED");
                                      wrapDataInstance.dataTable.addOrReplaceElement(rowPos,colPos,checkBoxValue);
                                  }
                                  
                              }
                              dataInstance.dataTable.replace(fieldName,fieldValueArray);
                              dataInstance.addWhereFieldList(fieldName,fieldValueArray);
                              
                             if (wrapUpCollection != null)
                              {
                                  Set wrapupKeySet = wrapUpCollection.keySet();
                                  Iterator wrapIter = wrapupKeySet.iterator();
                                  while (wrapIter.hasNext())
                                  {    
                                       String wrapTableName = (String)wrapIter.next();
                                       if (!(wrapTableName.equalsIgnoreCase("PATIENT_MEDICAL_HISTORY")))
                                       {
                                            wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)wrapTableName);
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
                              }
                          } else
                          if ((keyType.equalsIgnoreCase("TEXT_AREA_FIELD")) || (keyType.equalsIgnoreCase("DOCUMENT_AREA_FIELD")))
                          {
                              fieldValueList = (ArrayList)fieldList.get((Object)fieldName);
                              fieldValueArray = new String[fieldValueList.size()];
                              for (int loop = 0; loop < fieldValueList.size(); loop++)
                              {    
                                  fieldValue = ((JTextArea)fieldValueList.get(loop)).getText();
                                  fieldValueArray[loop] = fieldValue;
                              }
                              dataInstance.dataTable.replace(fieldName,fieldValueArray);
                          } else
                          if (keyType.equalsIgnoreCase("FOREIGN"))
                          {
                             lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                             
                              fieldValueList = (ArrayList)fieldList.get((Object)fieldName);
                              fieldComboValueArray = new String[fieldValueList.size()]; 
                              fieldValueArray = new String[fieldValueList.size()];
                              
                              for (int loop = 0; loop < fieldValueList.size(); loop++)
                              {    
                                  fieldValue = (String)((JComboBox)fieldValueList.get(loop)).getSelectedItem();
                                  fieldComboValueArray[loop] = fieldValue;
                              }
                              for (int loop = 0; loop < fieldComboValueArray.length; loop++)
                              {    
                                  comboValue = fieldComboValueArray[loop];
                                  Set lookupKeySet = lookupTableData.keySet();
                                  Iterator lookupIter = lookupKeySet.iterator();
                                  while (lookupIter.hasNext())
                                  {
                                      String theKey = (String)lookupIter.next();
                                      String theValue = (String)lookupTableData.get((Object)theKey);
                                      if (theValue.equalsIgnoreCase(comboValue))
                                      {
                                          fieldValueArray[loop] = theKey;
                                          break;
                                      }
                                  }
                              }
                              dataInstance.dataTable.replace(fieldName,fieldValueArray);
                          } else
                          if (keyType.equalsIgnoreCase("BOOLEAN_FIELD"))
                          {
                              fieldValueList = (ArrayList)fieldList.get((Object)fieldName);
                              fieldComboValueArray = new String[fieldValueList.size()]; 
                              fieldValueArray = new String[fieldValueList.size()];
                              
                              for (int loop = 0; loop < fieldValueList.size(); loop++)
                              {    
                                  fieldValue = (String)((JComboBox)fieldValueList.get(loop)).getSelectedItem();
                                  fieldComboValueArray[loop] = fieldValue;
                              }
                              for (int loop = 0; loop < fieldComboValueArray.length; loop++)
                              {    
                                  comboValue = fieldComboValueArray[loop];
                                  if (comboValue.equalsIgnoreCase("No"))
                                  {
                                    fieldValueArray[loop] = "0";  
                                  } else
                                  {
                                     fieldValueArray[loop] = "1"; 
                                  }
                              }
                              dataInstance.dataTable.replace(fieldName,fieldValueArray);
                          }  else
                          if (keyType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                          {
                              fieldValueList = (ArrayList)fieldList.get((Object)fieldName);
                              fieldComboValueArray = new String[fieldValueList.size()]; 
                              fieldValueArray = new String[fieldValueList.size()];
                              
                              for (int loop = 0; loop < fieldValueList.size(); loop++)
                              {    
                                  fieldValue = (String)((JComboBox)fieldValueList.get(loop)).getSelectedItem();
                                  fieldComboValueArray[loop] = fieldValue;
                              }
                              for (int loop = 0; loop < fieldComboValueArray.length; loop++)
                              {    
                                  comboValue = fieldComboValueArray[loop];
                                  if (comboValue.equalsIgnoreCase("To"))
                                  {
                                    fieldValueArray[loop] = "0";  
                                  } else
                                  {
                                     fieldValueArray[loop] = "1"; 
                                  }
                              }
                              dataInstance.dataTable.replace(fieldName,fieldValueArray);
                          } else
                          {
                              fieldValueList = (ArrayList)fieldList.get((Object)fieldName);
                              fieldValueArray = new String[fieldValueList.size()];
                              for (int loop = 0; loop < fieldValueList.size(); loop++)
                              {    
                                  fieldValue = ((JTextField)fieldValueList.get(loop)).getText();
                                  fieldValueArray[loop] = fieldValue;
                              }
                              dataInstance.dataTable.replace(fieldName,fieldValueArray);
                          }
                       }
                       dataInstance.updateMultiData();
                       //controller.dataUpdateButton.setEnabled(false);
                    }
                    HashMap wrapUpCollection = (HashMap)model.getdbWrapUpAccessCollection();
                    if (wrapUpCollection != null)
                    {
                       Set wrapupKeySet = wrapUpCollection.keySet();
                       Iterator wrapIter = wrapupKeySet.iterator();
                       while (wrapIter.hasNext())
                       {     
                            String wrapTableName = (String)wrapIter.next();
                            DBAccess dataInstance = (DBAccess)wrapUpCollection.get((Object)wrapTableName);
                            dataInstance.updateDataFromTable();
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
            super(modelName, InfoManager.OS_VERSION, userName, password);
        }

        public MTTableDataModel(String modelName, String keyValue, String userName, char[] password) throws MTException
        {
            super(modelName, keyValue, InfoManager.OS_VERSION, userName, password);
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
        public HashMap fieldTextList;
        public HashMap deleteBoxes;
        public HashMap tablesTextList;
        public HashMap tablesDeleteBoxes;
        
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
                tablesDeleteBoxes = new HashMap(1);
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    tabbedPane.addTab((String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop)), null, createPane(mtModel, theTableName, height, width));
                    tablesTextList.put((Object)theTableName,(Object)fieldTextList.clone());
                    tablesDeleteBoxes.put((Object)theTableName,(Object)deleteBoxes.clone());
                    
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
        
        public JTabbedPane createPane(MTTableDataModel _Model, String _TableName, int _height, int _width) throws MTException
        {
            int rowCount  =  0;
            int colCount = 0;
            int dateEntries = 0;
            int panelRows = 0;
            ArrayList tempList;
            HashMap dateList = null;
            ArrayList keyList = new ArrayList(10);
            JTabbedPane rightTabbedPane = null;
            Border raisedBevel, loweredBevel, compoundBorder;

            HashMap dbAccessCollection = (HashMap)_Model.getdbAccessCollection();
            lookupTableDataCollection = (HashMap)_Model.getLookupTableDataCollection();
            DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)_TableName);
            dateList = dataInstance.getDateFieldList();
            rightTabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
            rowCount = dataInstance.dataTable.getRowCount();
            colCount = dataInstance.dataTable.getColumnCount();
            fieldTextList = new HashMap(colCount);
            
            fieldTextItems = new ArrayList(dateList.size());

            if (colCount < 21)
            {
               panelRows = 21; 
            } else
            {
                panelRows = colCount;
            }
            deleteBoxes = new HashMap(dateList.size());    
            for(dateEntries = 0; dateEntries < dateList.size(); dateEntries++)
            {
            
            raisedBevel = BorderFactory.createRaisedBevelBorder();
            loweredBevel = BorderFactory.createLoweredBevelBorder();
            compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
            
            JPanel dataPanel = new JPanel();
            JPanel labelPanel = new JPanel();
            JPanel fieldPanel = new JPanel();
            JPanel southPanel = new JPanel();
            JPanel buttonPanel = new JPanel();
            JPanel headingPanel = new JPanel();
            dataPanel.setLayout(new BorderLayout());
            labelPanel.setLayout(new GridLayout(panelRows, 0));
            fieldPanel.setLayout(new GridLayout(panelRows, 0));
            buttonPanel.setLayout(new GridLayout(1,0,5,5));
            buttonPanel.setBorder(compoundBorder);
            southPanel.setLayout(new BorderLayout());
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
            MaskFormatter formatter;
            JFormattedTextField ftf;
            String selectedValue = "";
            String keyFieldValue = null;
            String dateTabField = "";
            String keyVal = "";
            
            try
            {
              for (int fieldIndex = 0; fieldIndex < colCount; fieldIndex++)
              {
                String theFieldLabel = theColumnAliases[fieldIndex] + ":";
                String theFieldName = theColumnNames[fieldIndex];
                String theFieldType = theColumnTypes[fieldIndex];
                String theFieldValue = dataInstance.dataTable.getDataAt(dateEntries, fieldIndex);
                int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                boolean theFieldLock = theColumnLocks[fieldIndex];
                boolean theFieldDisplay = theColumnDisplays[fieldIndex];
                JLabel fieldLabel = new JLabel(theFieldLabel);
                fieldLabel.setPreferredSize(new Dimension(200,20));
                if (theFieldType.equalsIgnoreCase("PRIMARY"))
                {
                    keyFieldValue = theFieldValue;
                    //add primary key to keylist
                    keyList.add((Object)theFieldValue); 
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
                    }
                    if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                    {
                        fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                        fieldTextItems.add((Object)fieldText);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                    } else
                    {
                        fieldTextItems = new  ArrayList();
                        fieldTextItems.add((Object)fieldText);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
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
                         if (theFieldValue.equalsIgnoreCase(theKey))
                         {
                             selectedValue = theValue;
                         }
                         comboBoxData[comboBoxDataIndex] = theValue;
                         comboBoxDataIndex++;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    //cb.add(new JButton("A.Z"));
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    cb.setSelectedItem(selectedValue);
                    cb.addActionListener(new CBActionListener());
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                } else
                if (theFieldType.equalsIgnoreCase("BOOLEAN_FIELD"))
                {
                    String[] comboBoxData = new String[2];
                    for (int comboBoxDataIndex = 0; comboBoxDataIndex < 2; comboBoxDataIndex++) 
                    {
                        String theValue = "";
                        if (comboBoxDataIndex == 0)
                        {
                            theValue = "No";
                        } else
                        {
                            theValue = "Yes";
                        }
                        if (theFieldValue.equalsIgnoreCase("0"))
                            selectedValue = "No"; 
                        else
                            selectedValue = "Yes";
                        comboBoxData[comboBoxDataIndex] = theValue;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    cb.setSelectedItem(selectedValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                } else
                if (theFieldType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                {
                    String[] comboBoxData = new String[2];
                    for (int comboBoxDataIndex = 0; comboBoxDataIndex < 2; comboBoxDataIndex++) 
                    {
                        String theValue = "";
                        if (comboBoxDataIndex == 0)
                        {
                            theValue = "To";
                        } else
                        {
                            theValue = "By";
                        }
                        if (theFieldValue.equalsIgnoreCase("0"))
                            selectedValue = "To"; 
                        else
                            selectedValue = "By";
                        comboBoxData[comboBoxDataIndex] = theValue;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    cb.setSelectedItem(selectedValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(cb);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)cb);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                } else
                if (theFieldType.equalsIgnoreCase("PHONE_FIELD"))
                {
                    formatter = new  MaskFormatter("(###) ###-####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("CELL_FIELD"))
                {
                    formatter = new  MaskFormatter("(###) ###-####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("AREA_FIELD"))
                {
                    formatter = new  MaskFormatter("####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("ID_FIELD"))
                {
                    formatter = new  MaskFormatter("#############");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                {
                    formatter = new  MaskFormatter("##/##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
                {
                    ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                    openTextAreaButton = new JButton("Push to open",openIcon);
                    openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex] + "," + String.valueOf(dateEntries));
                    openTextAreaButton.setPreferredSize(new Dimension(200,20));
                    openTextAreaButton.addMouseListener(new TheMouseListener());
                    if (theFieldDisplay == true)
                    {
                        textArea = new JTextArea(80,25);
                        textArea.setText(theFieldValue);
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(openTextAreaButton);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)textArea);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)textArea);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                {
                    ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                    openTextAreaButton = new JButton("Push to open",openIcon);
                    //addFileButton = new JButton("Add");
                    openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex] + "," + String.valueOf(dateEntries));
                    openTextAreaButton.setPreferredSize(new Dimension(200,20));
                    openTextAreaButton.addMouseListener(new TheMouseListener());
                    //openTextAreaButton.add(addFileButton);
                    if (theFieldDisplay == true)
                    {
                        textArea = new JTextArea(80,25);
                        textArea.setText(theFieldValue);
                        
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(openTextAreaButton);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)textArea);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)textArea);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                } else
                if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                {
                    formatter = new  MaskFormatter("U U U");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                } else
                if (theFieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                {
                    formatter = new  MaskFormatter("####-##-## ##:##:##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("DATE_FIELD"))
                {
                    formatter = new  MaskFormatter("####-##-##");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
                    ftf.setValue(theFieldValue);
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
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
                        if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                        {
                            fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        } else
                        {
                            fieldTextItems = new  ArrayList();
                            fieldTextItems.add((Object)ftf);
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                        }
                    }
                } //end else
              } //end for

              //NEW CODE to add delete functionality
              JLabel deleteRecordLabel = new JLabel(" Delete record? ");
              JCheckBox deleteBox = new JCheckBox();
              
              //labelPanel.add(deleteRecordLabel);
              buttonPanel.add(deleteRecordLabel);
              buttonPanel.add(deleteBox);
              keyVal = (String)keyList.get(dateEntries);
              deleteBoxes.put((Object)keyVal, (Object)deleteBox); 

              
            } //end try
            catch (Exception ex) 
            {
                throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
            }
            southPanel.add(buttonPanel, BorderLayout.EAST);
            dataPanel.add(labelPanel, BorderLayout.WEST);
            dataPanel.add(fieldPanel, BorderLayout.EAST);
            dataPanel.add(southPanel, BorderLayout.SOUTH);
            if ((dateList != null)  && (dateList.containsKey((Object)keyFieldValue)))
            {
                dateTabField = (String)dateList.get((Object)keyFieldValue);
            } else
            {
               dateTabField = "Null"; 
            }
            rightTabbedPane.addTab(dateTabField,null,dataPanel,"Capture Date");
            } //end of datelist entries
            
            return rightTabbedPane;
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
                   String fieldIndex = "";
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
                        if (cellReferenceIndex == 1)
                        {
                            fieldName = theToken;
                        } else
                        {
                            fieldIndex = theToken;
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
                   HashMap fieldMap = (HashMap)(theDataView.tablesTextList.get((Object)tableName));
                   ArrayList fieldArray = (ArrayList)fieldMap.get((Object)fieldName);
                   textArea = (JTextArea)fieldArray.get(Integer.parseInt(fieldIndex));
                   areaScrollPane = new JScrollPane(textArea);
                   areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                   areaScrollPane.setPreferredSize(new Dimension(300,300));
                   textAreaFrame.getContentPane().add(areaScrollPane,BorderLayout.CENTER);
                   if ((frameHeading.indexOf("DOCS") > 0) || (frameHeading.indexOf("REFERENCE") > 0))
                   {
                       addFileButton = new JButton("Add File");
                       scanDocButton = new JButton("Scan Document");
                       JPanel buttonPane = new JPanel();
                       buttonPane.add(addFileButton);
                       if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
                       {
                          buttonPane.add(scanDocButton);
                       }
                       addFileButton.addActionListener(new ActionListener()
                       {
                           public void actionPerformed(ActionEvent e)
                           {
                               JFileChooser fileChooser = new JFileChooser(docScanPath);
                               FileExtensionFilter filter = new FileExtensionFilter();
                               filter.add("gif");
                               filter.add("jpg");
                               filter.setDescription("GIF & JPG Images");
                               fileChooser.setFileFilter(filter);
                               int returnOption = fileChooser.showOpenDialog(theDataView);
                               if (returnOption == JFileChooser.APPROVE_OPTION)
                               {
                                   String fileName = fileChooser.getSelectedFile().getName();
                                   textArea.append("\n" + fileName);
                               }
                               textArea.repaint();
                           }
                       });
                        
                       if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
                       {
                           scanDocButton.addActionListener(new ActionListener()
                           {
                               public void actionPerformed(ActionEvent e)
                               {
                                   String fileIndex = "";
                                   iniFilePresent = config.readIni(docScanPath + "docscan.ini");
                                   if (iniFilePresent)
                                   {
                                       JOptionPane optionPane = new JOptionPane();
                                       int result = optionPane.showConfirmDialog(theDataView,"Put document in scanner and press\nOK when ready to scan else press Cancel.","Scanner Confirmation.",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                                       if (result == JOptionPane.OK_OPTION)
                                       {
                                           config.setHeader("DOCSCANS");
                                           fileIndex = config.getItem("ScanFileIndex");
                                           int intFileIndex = Integer.parseInt(fileIndex);
                                           intFileIndex++;
                                           fileIndex = String.valueOf(intFileIndex);
                                           twainScanner = new TwainScan();
                                           config.changeItem("ScanFileIndex", fileIndex);
                                           config.writeIni(docScanPath + "docscan.ini");
                                           twainScanner.scan();
                                           twainScanner.save(docScanPath, "DocScan" + fileIndex + ".jpg");
                                           textArea.append("\n" + "DocScan" + fileIndex + ".jpg");
                                       }
                                       
                                   } else
                                   {
                                       JOptionPane optionPane = new JOptionPane();
                                       optionPane.showMessageDialog(theDataView,"Required docscan.ini file is missing!");
                                   }
                               }
                           });
                       }
                       textAreaFrame.getContentPane().add(buttonPane,BorderLayout.SOUTH);
                   }
                   add(textAreaFrame);
                   textAreaFrame.moveToFront();
                   textAreaFrame.requestFocus();
                   textFrame = true;
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
                  theDataModel = new MTTableDataModel(editModelName, selectedKeyValue, defUserName, defPassword);
                  theDataView = new DataView(editViewType, editTabPlacement, theDataModel, editHeight, editWidth);
                  removeAll();
                  add(theDataView);
                  CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
                  EditInfoByDateMVCApp.updateUI();
               }
            }
            catch (MTException exc)
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            
         }
   }
    

   class CBActionListener implements ActionListener
   {
       public void actionPerformed(ActionEvent e) 
       {
           boolean keyFound = false;
           String selectedKey = null;
           String theCollectionKey = null;
           int arrayIndex = 0;
           
           String selectedItem = (String)((JComboBox)e.getSource()).getSelectedItem();
           JComboBox sourceComboBox = (JComboBox)e.getSource(); 
           //find lookup key for this description
           Set lookupCollectionKeySet = lookupTableDataCollection.keySet();
           Iterator lookupCollectionIter = lookupCollectionKeySet.iterator();
           while (lookupCollectionIter.hasNext())
           {
               theCollectionKey = (String)lookupCollectionIter.next();
               HashMap lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theCollectionKey);
               Set lookupKeySet = lookupTableData.keySet();
               Iterator lookupIter = lookupKeySet.iterator();
               while (lookupIter.hasNext())
               {
                   String theKey = (String)lookupIter.next();
                   String theValue = (String)lookupTableData.get((Object)theKey);
                   if (theValue.equalsIgnoreCase(selectedItem))
                   {
                       selectedKey = theKey;
                       keyFound = true;
                       break;
                   }
               }
               if (keyFound == true) break;
           }
           //find location and index of combobox in dataview
           boolean fieldFound = false;
           String tableName = null;
           HashMap fieldList = null;
           Set tableKeySet = theDataView.tablesTextList.keySet();
           Iterator tableIter = tableKeySet.iterator();
           while (tableIter.hasNext())
           {
               tableName = (String)tableIter.next();
               fieldList = (HashMap)theDataView.tablesTextList.get(tableName);
               ArrayList fieldArrayList = null;
               fieldArrayList = (ArrayList)fieldList.get((Object)theCollectionKey);
               if (fieldArrayList != null)
               {
                   for (int arrayLoop = 0; arrayLoop < fieldArrayList.size(); arrayLoop++)
                   {
                      JComboBox cbBox = (JComboBox)fieldArrayList.get(arrayLoop);
                      if (cbBox.equals(sourceComboBox))
                      {
                          arrayIndex = arrayLoop;
                          fieldFound = true;
                          break;
                      }
                       
                   }
                   if (fieldFound == true) break;
               }
           }
           //check which fields are crosslinked and update them
           boolean crossKeyFound = false;
           String selectedValue = null;
           HashMap crossLinkCollection = (HashMap)theDataModel.getLookupTableCrossLinks();
           ArrayList crossLinkList = (ArrayList)crossLinkCollection.get(theCollectionKey);
           for (int loop = 0; loop < crossLinkList.size(); loop++)
           {
               String crossLinkField = (String)crossLinkList.get(loop);
               HashMap crossLookupTableData = (HashMap)lookupTableDataCollection.get((Object)crossLinkField);
               
               Set crossLookupTableKeySet = crossLookupTableData.keySet();
               Iterator crossLookupIter = crossLookupTableKeySet.iterator();
               while (crossLookupIter.hasNext())
               {
                   String theKey = (String)crossLookupIter.next();
                   String theValue = (String)crossLookupTableData.get((Object)theKey);
                   if (theKey.equalsIgnoreCase(selectedKey))
                   {
                       selectedValue = theValue;
                       ArrayList fieldsArray = (ArrayList)fieldList.get((Object)crossLinkField);
                       JComboBox theCB = (JComboBox)fieldsArray.get(arrayIndex);
                       theCB.setSelectedItem(selectedValue);
                       theCB.transferFocus();
                       crossKeyFound = true;
                       break;
                   }
               }//end while
           }//end for loop < crossLinkList.size()
       }// end actionperformed
   }// end class.
   
    public EditInfoByDateMVC(String selectionModelName, String modelName, String viewType, String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        EditInfoByDateMVCApp = this;
        defUserName = userName;
        defPassword = password;
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, HEIGHT, WIDTH, userName, password);
        theScrollPane1 = typePanel;
    }

    public EditInfoByDateMVC(String selectionModelName, String modelName, String viewType, int height, int width, String userName, char[] password) 
    {
        EditInfoByDateMVCApp = this;
        defUserName = userName;
        defPassword = password;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }

    public EditInfoByDateMVC(String selectionModelName, String modelName, String viewType, int tabPlacement, int height, int width, String userName, char[] password) 
    {
        EditInfoByDateMVCApp = this;
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
               docScanPath = InfoManager.WINDOWS_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("UNIX"))
            {
               imagePath = InfoManager.UNIX_IMAGE_PATH;
               docScanPath = InfoManager.UNIX_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINXP"))
            {
               imagePath = InfoManager.WINXP_IMAGE_PATH;
               docScanPath = InfoManager.WINXP_DOCUMENT_PATH;
            }
            // Set Up DataModel by instantiating MTBusinessModel
            editModelName  = modelName;
            editViewType = viewType;
            editTabPlacement = tabPlacement;
            editHeight = _height;
            editWidth = _width;
            selectionDataModel = new  MTTableDataModel(selectionModelName, userName, password);
            theDataSelectView = new DataSelectView(viewType, tabPlacement, selectionDataModel, _height, _width);
            add(theDataSelectView);
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }
   
}
