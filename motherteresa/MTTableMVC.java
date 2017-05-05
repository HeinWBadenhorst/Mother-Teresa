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

public class MTTableMVC extends JPanel{
   
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
  private MTTableMVC MTTableMVCApp;
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
  private ArrayList fieldTextItems;
  private String docScanPath = "";
  private TwainScan twainScanner;
  private IniFile config = new IniFile();
  boolean iniFilePresent = false;
  private JButton addFileButton;
  private JButton scanDocButton;
  private String keyValue = null;
  private boolean updatedFlag = false;


  
  // the Model
  MTTableDataModel theDataModel;
  
  //View and controller combined
  DataView theDataView; //   
  
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
            ArrayList fieldValueList = null;
            String[] fieldValueArray = null;
            String[] fieldComboValueArray = null;
            String comboValue = null;
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
                                              wrapDataInstance.dataTable.addElement("DELETED", "0");
                                              break;
                                           }
                                       }
                                  }
                              }
                              
                          } else
                          if (keyType.equalsIgnoreCase("BOOLEAN_FIELD"))
                          {
                              String boolVal = "";
                              comboValue = (String)((JComboBox)fieldList.get((Object)fieldName)).getSelectedItem();
                              if (comboValue.equalsIgnoreCase("No"))
                              {
                                 boolVal = "0";
                              } else
                              {
                                 boolVal = "1";
                              }
                              fieldValue = boolVal;   
                          }  else
                          if (keyType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                          {
                              String boolVal = "";
                              comboValue = (String)((JComboBox)fieldList.get((Object)fieldName)).getSelectedItem();
                              if (comboValue.equalsIgnoreCase("To"))
                              {
                                 boolVal = "0";
                              } else
                              {
                                 boolVal = "1";
                              }
                              fieldValue = boolVal;   
                          } else
                          if ((keyType.equalsIgnoreCase("TEXT_AREA_FIELD")) || (keyType.equalsIgnoreCase("DOCUMENT_AREA_FIELD")))
                          {
                              fieldValue =  ((JTextArea)fieldList.get((Object)fieldName)).getText();
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
                                          //fieldValueArray[loop] = theKey;
                                          fieldValue = theKey;
                                          break;
                                      }
                                  }
                              }
                             /* lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                             comboValue = (String)((JComboBox)fieldList.get((Object)fieldName)).getSelectedItem();
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
                             } */ 
                          } else
                          {
                              fieldValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                          }
                          dataInstance.dataTable.addElement(fieldName, fieldValue);
                       }
                       dataInstance.insertBulkData();
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
                    updatedFlag = true;
                    
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
            super(modelName, InfoManager.OS_VERSION,userName,password);
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
            JButton clearButton = new JButton("Clear");
            JButton searchButton = new JButton("Search");
            buttonPanel.add(clearButton);
            buttonPanel.add(searchButton);
            dataPanel.setBorder(BorderFactory.createEtchedBorder());
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
                    keyValue = theFieldValue;
                    JTextField fieldText = new JTextField(theFieldValue,20);
                    if (theFieldLock)
                    {
                        fieldText.setEnabled(false);
                    } else
                    {
                        fieldText.setEnabled(true);;
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
                         comboBoxData[comboBoxDataIndex] = theValue;
                         comboBoxDataIndex++;
                    }  
                    JComboBox cb = new JComboBox(comboBoxData);  
                    //cb.add(new JButton("A.Z"));
                    cb.setPreferredSize(new Dimension(200,20));
                    cb.setBackground(new Color(255,255,255));
                    //cb.setSelectedItem(selectedValue);
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
                   /* while (iter.hasNext())
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
                    }*/
                }  else
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
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
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
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                    }
                } else
                if (theFieldType.equalsIgnoreCase("PHONE_FIELD"))
                {
                    formatter = new  MaskFormatter("(###) ###-####");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
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
                    //cb.setBackground(new Color(255,255,255));
                    if (theFieldDisplay == true)
                    {
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(ftf);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
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
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(openTextAreaButton);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)textArea);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                {
                    ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                    openTextAreaButton = new JButton("Push to open",openIcon);
                    //addFileButton = new JButton("Add");
                    openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                    openTextAreaButton.setPreferredSize(new Dimension(200,20));
                    openTextAreaButton.addMouseListener(new TheMouseListener());
                    //openTextAreaButton.add(addFileButton);
                    if (theFieldDisplay == true)
                    {
                        textArea = new JTextArea(80,25);
                        textArea.setText(theFieldValue);
                        labelPanel.add(fieldLabel);
                        fieldPanel.add(openTextAreaButton);
                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)textArea);
                    }
                }  else
                if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                {
                    formatter = new  MaskFormatter("U U U");
                    ftf = new JFormattedTextField(formatter);  
                    ftf.setPreferredSize(new Dimension(200,20));
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
                   //ArrayList fieldArray = (ArrayList)fieldMap.get((Object)fieldName);
                   //textArea = (JTextArea)fieldArray.get(Integer.parseInt(fieldIndex));
                   textArea = (JTextArea)fieldMap.get((Object)fieldName);
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

/*    class TheMouseListener extends MouseInputAdapter implements InternalFrameListener 
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
*/
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
           if (crossLinkCollection != null)
           {
               ArrayList crossLinkList = (ArrayList)crossLinkCollection.get(theCollectionKey);
               for (int loop = 0; loop < crossLinkList.size(); loop++) {
                   String crossLinkField = (String)crossLinkList.get(loop);
                   HashMap crossLookupTableData = (HashMap)lookupTableDataCollection.get((Object)crossLinkField);
                   
                   Set crossLookupTableKeySet = crossLookupTableData.keySet();
                   Iterator crossLookupIter = crossLookupTableKeySet.iterator();
                   while (crossLookupIter.hasNext()) {
                       String theKey = (String)crossLookupIter.next();
                       String theValue = (String)crossLookupTableData.get((Object)theKey);
                       if (theKey.equalsIgnoreCase(selectedKey)) {
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
           } //end if crossLinkCollection != null
       }// end actionperformed
   }// end class.

    public MTTableMVC(String modelName, String viewType, String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        MTTableMVCApp = this;
        initComponents(modelName, viewType, tabsTop, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }

    public MTTableMVC(String modelName, String viewType, int height, int width, String userName, char[] password) 
    {
        MTTableMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(modelName, viewType, tabsTop, InfoManager.OS_VERSION, height, width,userName,password);
        theScrollPane1 = typePanel;
    }

    public MTTableMVC(String modelName, String viewType, int tabPlacement, int height, int width, String userName, char[] password) 
    {
        MTTableMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(modelName, viewType, tabPlacement, InfoManager.OS_VERSION, height, width,userName,password);
        theScrollPane1 = typePanel;
    }

    public void initComponents(String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
    {
        try
        {
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
            {
               imagePath = InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\";
               docScanPath = InfoManager.WINDOWS_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("UNIX"))
            {
               imagePath = "/usr/src/Mother Teresa/images/";
               docScanPath = InfoManager.UNIX_DOCUMENT_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINXP"))
            {
               imagePath = InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\";
               docScanPath = InfoManager.WINXP_DOCUMENT_PATH;
            }
            // Set Up DataModel by instantiating MTBusinessModel
            theDataModel = new MTTableDataModel(modelName,userName,password);
            theDataView = new DataView(viewType, tabPlacement, theDataModel, _height, _width);
            add(theDataView);
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }
    
    public String getKeyValue()
    {
        return keyValue;
    }
    
    public boolean getUpdatedFlag()
    {
        return updatedFlag;
    }
   
}
