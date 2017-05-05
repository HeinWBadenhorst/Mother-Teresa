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

public class AddInfoMVC extends JPanel
{
    
    // The initial width and height of the frame
    public static int WIDTH = 700;
    public static int HEIGHT = 480;//480;
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
    private static final int  FRAME_WIDTH = 450;
    private static final int FRAME_HEIGHT = 480;//480;
    private static final int tabsTop = JTabbedPane.TOP;
    private static final int tabsBottom = JTabbedPane.BOTTOM;
    private static final int tabsLeft = JTabbedPane.LEFT;
    private static final int tabsRight = JTabbedPane.RIGHT;
    private AddInfoMVC AddInfoMVCApp;
    public HashMap lookupTableDataCollection;
    public HashMap xMLTableCollection;
    public HashMap autoSelectFieldCollection;
    public HashMap simpleTextCollection = new HashMap(10);
    private HashMap lookupTableData;
    private HashMap groupLookupData;
    private HashMap groupData;
    private HashMap groupDataSubset;
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
    private String theTableName = null;
    
    private ArrayList fieldTextItems;
    private String docScanPath = "";
    private TwainScan twainScanner;
    private IniFile config = new IniFile();
    boolean iniFilePresent = false;
    private JButton addFileButton;
    private JButton scanDocButton;
    public  HashMap  groupLookupTableCollection;
    public  HashMap  groupedTableDataCollection;
    public  HashMap  dependanceTableDataCollection;
    public HashMap dependanceItemTypeCollection;
    public HashMap comboBoxInactiveStateCollection;
    public HashMap comboBoxActiveStateCollection;
    public HashMap dependantItemActivityMap;
    public  HashMap  comboBoxDefaultCollection;
    public  HashMap  updateSynopsisCollection;
    public  HashMap  synonymTableInfoCollection;
    public  HashMap  fieldFilterListCollection = null;
    public HashMap comboLabelCollection = null;
    public HashMap comboItemCollection = null;
    public HashMap textFieldLabelCollection = null;
    public HashMap otherButtonLabelCollection = null;
    private String[] theColumnNames = null;
    private String[] theColumnAliases = null;
    private String[] theColumnTypes = null;
    private boolean[] theColumnLocks = null;
    private boolean[] theColumnDisplays = null;
    private boolean[] theLabelDisplays = null;
    private boolean firstKeyPressPass = true;
    private boolean groupFirstKeyPressPass = true;
    private ArrayList tempCBItemList = new ArrayList(5);
    private ArrayList groupTempCBItemList = new ArrayList(5);
    
    private StringBuffer keyPressBuffer = new StringBuffer("");
    private StringBuffer groupKeyPressBuffer = new StringBuffer("");
    private int keyPressBufferLength = 0;
    private int groupKeyPressBufferLength = 0;
    private String autoSelectFieldType = null;
    private MTUtils theMTUtils = null;
    private boolean firstItem = true;
    private JPanel synopsisPanel;
    private JLabel synopsisLabel;
    private Border compoundBorder;
    private Border raisedBevel, loweredBevel;
    private String defUserName = InfoManager.DEF_USER_NAME;
    private char[] defPassword = InfoManager.DEF_PASSWORD;
    private HashMap captureList = new HashMap(5);
    public JFormattedTextField finalFtf;
    private HashMap otherButtonList;
    private String theTabIconName = null;
    private MTUtils utilsInstance = new MTUtils();
    private String theUsername = null;
    private JTabbedPane tabbedPane;
    private JComboBox synonymSourceComboBox;
    private JInternalFrame synonymListFrame;
    private XMLNavigator xmlNavigator = null;
    
    
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
        JPanel capData = null;
        HashMap capList = null; 
        
        public CtoMAdaptor(DataView c, MTTableDataModel m) throws MTException
        {
            model = m;
            controller = c;
            try
            {
                controller.dataUpdateButton.addActionListener(this);
                //controller.dataUpdateEndButton.addActionListener(this);
            }
            catch(Exception e)
            {
                throw new MTException(InfoManager.ADAPTER_ERROR, e.getMessage());
            }
            
        }
        
        public CtoMAdaptor(DataView c, MTTableDataModel m, JPanel capturedDataPanel, HashMap captureList) throws MTException
        {
            model = m;
            controller = c;
            capData = capturedDataPanel;
            capList = captureList;
            try
            {
                controller.dataUpdateButton.addActionListener(this);
                //controller.dataUpdateEndButton.addActionListener(this);
            }
            catch(Exception e)
            {
                throw new MTException(InfoManager.ADAPTER_ERROR, e.getMessage());
            }
            
        }

        public boolean updateSynopsisList(String listValue)
        {
            String tempStr = null;
            if (capData == null)
            {
            if (firstItem == true)
            {
                synopsisPanel = new JPanel();
                JPanel headingPanel = new JPanel();
                if (finalFtf != null)
                {
                    tempStr = finalFtf.getText().trim();
                } else
                {
                    tempStr = listValue;
                }
                if ((tempStr != null) && (tempStr.length() > 0))
                {
                    captureList.put((Object)tempStr,(Object)"dummy");
                    synopsisLabel = new JLabel(tempStr);
                } else
                {
                    if ((listValue == null) || (listValue.length() == 0))
                    {
                        JOptionPane.showMessageDialog(theDataView,"Please select an item from the list first."  , "Error: No Item Selected ", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    captureList.put((Object)listValue,(Object)"dummy");
                    synopsisLabel = new JLabel(listValue);
                }
                JLabel headingLabel = new  JLabel("Captured:");
                headingPanel.add(headingLabel);
                headingPanel.setBackground(new Color(110,110,200));
                headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
                synopsisPanel.setLayout(new BoxLayout(synopsisPanel, BoxLayout.Y_AXIS));
                synopsisPanel.setBorder(compoundBorder);
                synopsisPanel.add(headingPanel);
                synopsisPanel.add(synopsisLabel);
                add(synopsisPanel,BorderLayout.WEST);
                firstItem = false;
                return true;
            } else
            {
                
                if (finalFtf != null)
                {
                    tempStr = finalFtf.getText().trim();
                } else
                {
                    tempStr = listValue;
                }
                if ((tempStr != null) && (tempStr.length() > 0))
                {

                    if (captureList.containsKey(tempStr))
                    {
                        JOptionPane.showMessageDialog(theDataView,"[" + tempStr + "] has already been captured!"  , "Error: Double Capture ", JOptionPane.ERROR_MESSAGE);
                        return false;
                    } else
                    {
                        captureList.put((Object)tempStr,(Object)"dummy");
                        JLabel synopsisAddLabel = new JLabel(tempStr);
                        synopsisPanel.add(synopsisAddLabel);
                        return true;
                    }
                } else
                {
                    if (captureList.containsKey(listValue))
                    {
                        JOptionPane.showMessageDialog(theDataView,"[" + listValue + "] has already been captured!"  , "Error: Double Capture ", JOptionPane.ERROR_MESSAGE);
                        return false;
                    } else
                    {
                        captureList.put((Object)listValue,(Object)"dummy");
                        JLabel synopsisAddLabel = new JLabel(listValue);
                        synopsisPanel.add(synopsisAddLabel);
                        return true;
                    }
                }
            } 
        } else
        {
                //now write update item to the common captureDataPanel
                  if ((finalFtf != null) && (finalFtf.getText().trim().length() > 0))
                  {
                      tempStr = finalFtf.getText().trim();
                  } else
                  {
                      tempStr = listValue.trim();
                  }
                       String curTableName = (String)model.getTableNames().get(0); 
            if ((capList != null) && (capList.containsKey(tempStr)) && (curTableName.equalsIgnoreCase("PATIENT_ALLERGIES") != true) )     
            {
                   JOptionPane.showMessageDialog(theDataView,"[" + tempStr + "] has already been captured!"  , "Error: Double Capture ", JOptionPane.ERROR_MESSAGE);
                   return false;
                
            } else
            {    
                 capList.put((Object)tempStr,(Object) "dummy");
                 JTextField synopsisAddText = new JTextField(tempStr);
                 synopsisAddText.setEditable(false);
                 synopsisAddText.setMaximumSize(new Dimension(190, 20));
                 synopsisAddText.setBackground(new Color(0,0,255));
                 synopsisAddText.setForeground(new Color(255,255,0));
                 JScrollPane scrollPane = new JScrollPane(synopsisAddText);
                 scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
                 scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                 scrollPane.setMaximumSize(new Dimension(190, 40));
                 //synopsisAddText.setPreferredSize(new Dimension(200, 25));
                 //synopsisAddText.setSize(new Dimension(200, 25));
                 capData.add(scrollPane);
                 //capData.repaint();
                 capData.updateUI();
                 return true;
            }
            
        }
        //return true;
        }
        public void actionPerformed(ActionEvent e)
        {
            ArrayList fieldValueList = null;
            String[] fieldValueArray = null;
            String[] fieldComboValueArray = null;
            String comboValue = null;
            boolean okToUpdate = true;
            String tableName = "";
            String keyFieldName = "";
            String categoryFieldName = "";
            String categoryFieldValue = "";
            String updateFieldName = "";
            String updateFieldValue = "";
            boolean updateDataFlag = false;
            String newGroupKey = "";
            String updateTableName = "";
            String updateTableFieldName = "";
            
            try
            {
                if (e.getSource() == controller.dataUpdateButton)
                {
                    okToUpdate = true;
                    Set keySet = controller.tablesTextList.keySet();
                    Iterator iter = keySet.iterator();
                    int lastCount = keySet.size() - 1;
                    //@@String test = parent.imagePath;
                    String systemicUpdateItem = model.getSystemicSymptom();
                    if (systemicUpdateItem != null)
                    {
                       okToUpdate = updateSynopsisList(systemicUpdateItem);
                    }
                    String familyType = model.getFamilyType();
                    String examinationType = model.getExaminationType();
                    
                    String occupationType = model.getOccupationType();

                    while (iter.hasNext())
                    {
                        tableName = (String)iter.next();
                        updateTableName = model.getDataSourceTable();
                        updateTableFieldName = model.getDataSourceField();
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
                                if (e.getSource() == controller.dataUpdateButton)
                                {
                                    //Increment the keyfield to be ready for next update
                                    JTextField tempTextField = (JTextField)fieldList.get((Object)fieldName);
                                    String tempString = null;
                                    tempString = String.valueOf(Integer.parseInt(fieldValue) + 1);
                                    tempTextField.setText(tempString);
                                }
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
                                                wrapDataInstance.dataTable.addElement("DELETED", "0");
                                                break;
                                            }
                                        }
                                    }
                                }
                            }  else
                                if ((keyType.equalsIgnoreCase("TEXT_AREA_FIELD")) || (keyType.equalsIgnoreCase("DOCUMENT_AREA_FIELD")))
                                {
                                    fieldValue =  ((JTextArea)fieldList.get((Object)fieldName)).getText();
                                    //clear text area box after update
                                    ((JTextArea)fieldList.get((Object)fieldName)).selectAll();
                                    ((JTextArea)fieldList.get((Object)fieldName)).cut();
                                } else
                                    if ((keyType.equalsIgnoreCase("SPININTFIELD")) || (keyType.equalsIgnoreCase("SPINDOSAGE")) || (keyType.equalsIgnoreCase("SPINBREATH")) ||  (keyType.equalsIgnoreCase("SPINTEMP")) || (keyType.equalsIgnoreCase("SPINPULSE")) || (keyType.equalsIgnoreCase("SPINBPS")) || (keyType.equalsIgnoreCase("SPINBPD")) || (keyType.equalsIgnoreCase("BOOLEAN_GROUPDEPENDANT")))
                                    {
                                        JSpinner theSpinner =  ((JSpinner)fieldList.get((Object)fieldName));
                                        JComponent editor = theSpinner.getEditor();
                                        if (editor instanceof JSpinner.DefaultEditor)
                                        {
                                            JFormattedTextField tmpFTF = ((JSpinner.DefaultEditor)editor).getTextField();
                                            fieldValue = tmpFTF.getText();
                                        }
                                    } else
                                    if (keyType.equalsIgnoreCase("XMLTREE"))
                                    {
                                        fieldValue = xmlNavigator.getCurrentSelection();
                                    } else
                                        if ((keyType.equalsIgnoreCase("BOOLEAN_FIELD")) || (keyType.equalsIgnoreCase("BOOLEAN_GROUP")))
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
                                                if (keyType.equalsIgnoreCase("FOREIGN") || keyType.equalsIgnoreCase("GROUP"))
                                                {
                                                    lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                                                    if (keyType.equalsIgnoreCase("GROUP"))
                                                    {
                                                        updateFieldName = fieldName;
                                                        //keyFieldName = fieldName;

                                                    }
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
                                                        //remove this if there is update problems
                                                        if (comboValue == null)
                                                        {
                                                            okToUpdate = false;
                                                            break;
                                                        }
                                                        Set lookupKeySet = lookupTableData.keySet();
                                                        Iterator lookupIter = lookupKeySet.iterator();
                                                        while (lookupIter.hasNext())
                                                        {
                                                            String theKey = (String)lookupIter.next();
                                                            String theValue = (String)lookupTableData.get((Object)theKey);
                                                            if (theValue.equalsIgnoreCase(comboValue))
                                                            {
                                                                //fieldValueArray[loop] = theKey;
                                                                //this code is to show a temp list of items written to the database
                                                                if ((updateSynopsisCollection != null) && (keyType.equalsIgnoreCase("GROUP") == true))
                                                                {
                                                                    if (updateSynopsisCollection.containsKey((Object)fieldName) == true)
                                                                    {
                                                                        if (familyType != null)
                                                                        {
                                                                           okToUpdate =  updateSynopsisList(familyType + theValue);
                                                                        } else
                                                                        if (occupationType != null)
                                                                        {
                                                                           okToUpdate =  updateSynopsisList(occupationType + theValue);
                                                                        } else
                                                                        {
                                                                            okToUpdate = updateSynopsisList(theValue);
                                                                        }
                                                                        updateUI();
                                                                    }
                                                                }
                                                                fieldValue = theKey;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    
                                                    //dataInstance.dataTable.replace(fieldName,fieldValueArray);
                                                    
/*                           lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
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
                             }*/
                                                }  else
                                                    if (keyType.equalsIgnoreCase("GROUPKEY"))
                                                    {
                                                        lookupTableData = (HashMap)groupLookupTableCollection.get((Object)fieldName);
                                                        categoryFieldName  = fieldName;
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
                                                                    categoryFieldValue  = theKey;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    } else
                                                        if (keyType.equalsIgnoreCase("SPINMONTHYEAR"))
                                                        {
                                                            
                                                            JSpinner theSpinner =  ((JSpinner)fieldList.get((Object)fieldName));
                                                            JComponent editor = theSpinner.getEditor();
                                                            if (editor instanceof JSpinner.DateEditor)
                                                            {
                                                                JFormattedTextField tmpFTF = ((JSpinner.DateEditor)editor).getTextField();
                                                                String tempValue = tmpFTF.getText();
                                                                fieldValue =  tempValue.substring(3,5) + "-" + tempValue.substring(0,2) + "-01";
                                                                //int X = 1;
                                                            }
                                                        } else
                                                            if (keyType.equalsIgnoreCase("SPINLITMONTHYEAR"))
                                                            {
                                                                
                                                                JSpinner theSpinner =  ((JSpinner)fieldList.get((Object)fieldName));
                                                                JComponent editor = theSpinner.getEditor();
                                                                if (editor instanceof JSpinner.DateEditor)
                                                                {
                                                                    JFormattedTextField tmpFTF = ((JSpinner.DateEditor)editor).getTextField();
                                                                    String tempValue = tmpFTF.getText();
                                                                    StringTokenizer dateTokens = new StringTokenizer(tempValue," ");
                                                                    String monthItem = (String)dateTokens.nextToken();
                                                                    String yearItem = (String)dateTokens.nextToken();
                                                                    String shortYear = yearItem.substring(2,4);
                                                                    String numMonthItem = "01";
                                                                    if (monthItem.equalsIgnoreCase("January") == true)
                                                                    {
                                                                        numMonthItem = "01";
                                                                    } else
                                                                        if (monthItem.equalsIgnoreCase("January") == true)
                                                                        {
                                                                            numMonthItem = "01";
                                                                        } else
                                                                            if (monthItem.equalsIgnoreCase("January") == true)
                                                                            {
                                                                                numMonthItem = "01";
                                                                            } else
                                                                                if (monthItem.equalsIgnoreCase("February") == true)
                                                                                {
                                                                                    numMonthItem = "02";
                                                                                } else
                                                                                    if (monthItem.equalsIgnoreCase("March") == true)
                                                                                    {
                                                                                        numMonthItem = "03";
                                                                                    } else
                                                                                        if (monthItem.equalsIgnoreCase("April") == true)
                                                                                        {
                                                                                            numMonthItem = "04";
                                                                                        } else
                                                                                            if (monthItem.equalsIgnoreCase("May") == true)
                                                                                            {
                                                                                                numMonthItem = "05";
                                                                                            } else
                                                                                                if (monthItem.equalsIgnoreCase("June") == true)
                                                                                                {
                                                                                                    numMonthItem = "06";
                                                                                                } else
                                                                                                    if (monthItem.equalsIgnoreCase("July") == true)
                                                                                                    {
                                                                                                        numMonthItem = "07";
                                                                                                    } else
                                                                                                        if (monthItem.equalsIgnoreCase("August") == true)
                                                                                                        {
                                                                                                            numMonthItem = "08";
                                                                                                        } else
                                                                                                            if (monthItem.equalsIgnoreCase("September") == true)
                                                                                                            {
                                                                                                                numMonthItem = "09";
                                                                                                            } else
                                                                                                                if (monthItem.equalsIgnoreCase("October") == true)
                                                                                                                {
                                                                                                                    numMonthItem = "10";
                                                                                                                }  else
                                                                                                                    if (monthItem.equalsIgnoreCase("November") == true)
                                                                                                                    {
                                                                                                                        numMonthItem = "11";
                                                                                                                    }  else
                                                                                                                        if (monthItem.equalsIgnoreCase("December") == true)
                                                                                                                        {
                                                                                                                            numMonthItem = "12";
                                                                                                                        }
                                                                    
                                                                    fieldValue =  shortYear + "-" + numMonthItem + "-01";
                                                                    //int X = 1;
                                                                }
                                                            }  else
                                                                if (keyType.equalsIgnoreCase("SPIN_DATE_FIELD"))
                                                                {
                                                                    
                                                                    JSpinner theSpinner =  ((JSpinner)fieldList.get((Object)fieldName));
                                                                    JComponent editor = theSpinner.getEditor();
                                                                    if (editor instanceof JSpinner.DateEditor)
                                                                    {
                                                                        JFormattedTextField tmpFTF = ((JSpinner.DateEditor)editor).getTextField();
                                                                        //String tempValue = tmpFTF.getText();
                                                                        fieldValue = tmpFTF.getText();
                                                                        //fieldValue = tempValue.substring(3,5) + "-" + tempValue.substring(0,2) + "-03";
                                                                        //int X = 1;
                                                                    }
                                                                }   else
                                                                    if (keyType.equalsIgnoreCase("SPINYEARFIELD"))
                                                                    {
                                                                        
                                                                        JSpinner theSpinner =  ((JSpinner)fieldList.get((Object)fieldName));
                                                                        JComponent editor = theSpinner.getEditor();
                                                                        if (editor instanceof JSpinner.DateEditor)
                                                                        {
                                                                            JFormattedTextField tmpFTF = ((JSpinner.DateEditor)editor).getTextField();
                                                                            String tempValue = tmpFTF.getText();
                                                                            //fieldValue = tmpFTF.getText();
                                                                            fieldValue = tempValue.substring(0,4) + "-01-01";
                                                                            //int X = 1;
                                                                        }
                                                                    } else
                                                                        
                                                                        if (keyType.equalsIgnoreCase("SHORT_DATE"))
                                                                        {
                                                                            String tempValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                                                                            fieldValue = tempValue.substring(3,5) + "-" + tempValue.substring(0,2) + "-03";
                                                                        } else
                                                                        if (keyType.equalsIgnoreCase("GROUPDEPENDANT"))
                                                                        {
                                                                            fieldValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                                                                            fieldValue = fieldValue.trim();
                                                                            if ((fieldValue != null) && (fieldValue.length() > 0))
                                                                            {
                                                                                updateFieldValue = fieldValue;
                                                                                updateDataFlag = true;
                                                                            
                                                                            } else
                                                                            {
                                                                                fieldValue =  "-";
                                                                                updateDataFlag = false;
                                                                            }
                                                                        } else
                                                                        {
                                                                            fieldValue =  ((JTextField)fieldList.get((Object)fieldName)).getText();
                                                                            fieldValue = fieldValue.trim();
                                                                            
                                                                            //----------
                                                                if (updateSynopsisCollection != null)
                                                                {
                                                                    if (updateSynopsisCollection.containsKey((Object)fieldName) == true)
                                                                    {
                                                                        if (occupationType != null)
                                                                        {
                                                                           okToUpdate =  updateSynopsisList(occupationType + fieldValue);
                                                                        } else
                                                                        {
                                                                            okToUpdate = updateSynopsisList(fieldValue);
                                                                        }
                                                                        updateUI();
                                                                    }
                                                                }
                                                                            
                                                                            
                                                                            
                                                                            
                                                                        }
                            dataInstance.dataTable.addElement(fieldName, fieldValue);
                        }
                        if (okToUpdate == true)
                        {
                            if (updateDataFlag == true)
                            {
                                HashMap updateFieldChange = model.getUpdateFieldNameChange();
                                if (updateFieldChange != null)
                                {
                                    if (updateFieldChange.containsKey(categoryFieldName) == true)
                                    {
                                       categoryFieldName =  (String)updateFieldChange.get((Object)categoryFieldName);
                                    }
                                }
                                
                                dataInstance.addItemToUpdateFields(updateTableName, updateFieldName, updateTableFieldName, updateFieldValue,categoryFieldName,categoryFieldValue);
                                // check this later to obfuscate
                                
                                if (InfoManager.ENCRYPTED_DATABASE == true)
                                {
                                    byte[] dbKey =   utilsInstance.composeKey();
                                    byte[] checkKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                                    boolean dummyFlag = true;
                                    for (int k = 0; k < dbKey.length; k++) {
                                        if (dbKey[k] !=  checkKey[k]) {
                                            dummyFlag = false;
                                            break;
                                        }
                                    }
                                    if (dummyFlag == false) {

                                        int userNameSize = theUsername.length();
                                        int amtNames = (int)Math.ceil((double)36 /(double)userNameSize);
                                        String concatName = "";
                                        for (int cLoop = 0; cLoop < amtNames; cLoop++) {
                                            concatName = concatName + theUsername;
                                        }
                                        String strMashKey = concatName.substring(0,36);
                                        byte[] mashKey =   strMashKey.getBytes();
                                        for (int x = 0; x < mashKey.length; x++) {
                                            dbKey[x] = (byte)((int)dbKey[x] ^ (int)mashKey[x]);
                                        }
                                        newGroupKey = dataInstance.insertFromItemUpdateFields(dbKey);
                                        dataInstance.dataTable.replace(updateFieldName, newGroupKey,0);
                                    }
                                } else
                                {
                                        byte[] checkKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                                        newGroupKey = dataInstance.insertFromItemUpdateFields(checkKey);
                                        dataInstance.dataTable.replace(updateFieldName, newGroupKey,0);
                                }
                                
                            }
                            //change this if more than 1 exam type per session required
                            if (examinationType != null)
                            {
                                okToUpdate =  updateSynopsisList(examinationType);
                            }
                            if (okToUpdate = true)
                            {
                                dataInstance.insertBulkData();
                            }
                        }
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
                            DBAccess wrapDataInstance = (DBAccess)wrapUpCollection.get((Object)wrapTableName);
                            if (okToUpdate == true)
                            {
                                wrapDataInstance.insertBulkData();
                            }
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
        String MTUserName = null;
        char[] MTPassword = null;
        
        public MTTableDataModel(String modelName,String userName, char[] password) throws MTException
        {
           super(modelName, InfoManager.OS_VERSION, userName, password);
           MTUserName =  userName;
           MTPassword = password;
        }
        
        public MTTableDataModel(String modelName, String keyValue,String userName, char[] password) throws MTException
        {
            super(modelName, keyValue, InfoManager.OS_VERSION, userName, password);
           MTUserName =  userName;
           MTPassword = password;
        }
        
        public MTTableDataModel(String modelName,  String keyValue, String subSelectionCode,String userName, char[] password) throws MTException
        {
            super(modelName, keyValue, subSelectionCode, InfoManager.OS_VERSION, userName, password);
           MTUserName =  userName;
           MTPassword = password;
        }
        
        
        public String getUserName()
        {
            return MTUserName;
        }
        
        public char[] getPassword()
        {
            return MTPassword;
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
        
        public DataSelectView(String viewType, int tabPlacement,  String tabIconName, MTTableDataModel _theModel, int _height, int  _width) throws MTException
        {
            theTabIconName = tabIconName;
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
            int componentWidth = 250;
            int componentHeight = 25;
            
            String[] theKeyValues = null;
            String[] theDescValues = null;
            JLabel label1;
            JLabel label2;
            Border raisedBevel, loweredBevel;
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
            if (rowCount < 20)
            {
                rowCount = 20;
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
            boolean[] theLabelDisplays = dataInstance.dataTable.getLabelDisplays();
            String thePanelHeading = (String)_Model.getTableAliases().get((Object)_TableName);
            JLabel panelHeadingLabel = new JLabel(thePanelHeading);
            panelHeadingLabel.setForeground(new Color(255,255,255));
            panelHeadingLabel.setPreferredSize(new Dimension(200,25));
            panelHeadingLabel.setFont(new Font("Serif",Font.BOLD,14));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setBackground(new Color(0,50,150));
            headingPanel.add(panelHeadingLabel);
            //remove later
            //dataPanel.add(headingPanel, BorderLayout.NORTH);
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
                    boolean theLabelDisplay = theLabelDisplays[colIndex];
                    
                    JLabel fieldLabel = new JLabel(theFieldLabel);
                    fieldLabel.setPreferredSize(new Dimension(componentWidth,componentHeight));
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
                            cb.setPreferredSize(new Dimension(componentWidth,componentHeight));
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
        public JButton dataUpdateButton;
        public JButton otherButton;
        //public JButton dataUpdateEndButton;
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
        
        public DataView(String viewType, int tabPlacement, String iconName, MTTableDataModel _theModel, int _height, int  _width) throws MTException
        {
            if(viewType.equalsIgnoreCase(SIMPLE_VIEW))
            {
                try
                {
                    //continue here hein!!
                    this.setPreferredSize(new Dimension(_width+50,_height+50));
                    initSimpleDataView(tabPlacement, iconName, _theModel, _height, _width);
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

        private void swapElements(java.lang.String stringArray[], int i, int j)
        {
            java.lang.String temp;
            
            // Swap the elements
            temp = stringArray[i];
            stringArray[i] = stringArray[j];
            stringArray[j] = temp;
            
        }
        
        private void quickSort(java.lang.String stringArray[],
        int lo0,
        int hi0)
        {
            
            int lo = lo0;
            int hi = hi0;
            java.lang.String mid = null;
            
            
            // See if the high index is more than the low index
            if (hi0 > lo0)
            {
                // Arbitrarily establishing partition element as the midpoint of the array
                mid = stringArray[(lo0 + hi0) / 2];
                
                // Loop through the array until indices cross
                while (lo <= hi)
                {
                    // Find the first element that is greater than or equal to
                    // the partition element starting from the left Index.
                    while ((lo < hi0) &&
                    (stringArray[lo].compareTo(mid) < 0))
                        // Increment the lower index
                        ++lo;
                    
                    // Find an element that is smaller than or equal to
                    // the partition element starting from the right Index.
                    while ((hi > lo0) &&
                    (stringArray[hi].compareTo(mid) > 0))
                        // Decrement the high index
                        --hi;
                    
                    // If the indexes have not crossed, swap
                    if (lo <= hi)
                    {
                        // Swap the elements
                        swapElements(stringArray, lo, hi);
                        ++lo;
                        --hi;
                    }
                }
                
                // If the right index has not reached the left side of array
                // must now sort the left partition.
                if (lo0 < hi)
                    quickSort(stringArray, lo0, hi);
                
                // If the left index has not reached the right side of array
                // must now sort the right partition.
                if (lo < hi0)
                    quickSort(stringArray, lo, hi0 );
                
            }
        }
        public void initSimpleDataView(int _tabPlacement, MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                Border raisedBevel, loweredBevel;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
                
                tabbedPane = new JTabbedPane(_tabPlacement);
                //tabbedPane.setSize(new Dimension(FRAME_WIDTH+250, FRAME_HEIGHT)); //FRAME_HEIGHT
                //tabbedPane.setMaximumSize(new Dimension(FRAME_WIDTH+250, FRAME_HEIGHT));
                //tabbedPane.setPreferredSize(new Dimension(FRAME_WIDTH+250, FRAME_HEIGHT));
                tabbedPane.setSize(new Dimension(width, height)); //FRAME_HEIGHT
                tabbedPane.setMaximumSize(new Dimension(width, height));
                tabbedPane.setPreferredSize(new Dimension(width, height));
                JPanel controlPane = new JPanel();
                String theTableName = null;
                String theGroupLabel = null;
                theGroupLabel = mtModel.getGroupName();
                setLayout(new BorderLayout());
                JLabel mainHeaderLabel = new JLabel(theGroupLabel);
                mainHeaderLabel.setBorder(BorderFactory.createEtchedBorder());
                //this.add(mainHeaderLabel,  BorderLayout.NORTH);
                tablesTextList = new HashMap(mtModel.getTableCount());
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    // this was added to the tab previously in place of 'add'. (String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop))
                    String addObjectDescription = (String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop));
                    tabbedPane.addTab("Add", new ImageIcon(imagePath + "tfoldp16.gif"), createPane(mtModel, theTableName, FRAME_HEIGHT, FRAME_WIDTH,addObjectDescription));
                    tablesTextList.put((Object)theTableName,(Object)fieldTextList.clone());
                }
                this.add(tabbedPane,  BorderLayout.CENTER);
                
                dataUpdateButton = new JButton("Update");
                dataUpdateButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
                dataUpdateButton.setBackground(new Color(255,255,0));
                
                //dataUpdateEndButton = new JButton("Update and Exit");
                controlPane.setLayout(new BorderLayout());
                controlPane.setBorder(compoundBorder);
                //controlPane.add(dataUpdateEndButton, BorderLayout.EAST);
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
        
        public void initSimpleDataView(int _tabPlacement, String iconName, MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                Border raisedBevel, loweredBevel;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
                
                tabbedPane = new JTabbedPane(_tabPlacement);
                //tabbedPane.setSize(new Dimension(FRAME_WIDTH+250, FRAME_HEIGHT)); //FRAME_HEIGHT
                //tabbedPane.setMaximumSize(new Dimension(FRAME_WIDTH+250, FRAME_HEIGHT));
                //tabbedPane.setPreferredSize(new Dimension(FRAME_WIDTH+250, FRAME_HEIGHT));
                tabbedPane.setSize(new Dimension(width, height)); //FRAME_HEIGHT
                tabbedPane.setMaximumSize(new Dimension(width, height));
                tabbedPane.setPreferredSize(new Dimension(width, height));
                JPanel controlPane = new JPanel();
                theTableName = null;
                String theGroupLabel = null;
                theGroupLabel = mtModel.getGroupName();
                setLayout(new BorderLayout());
                JLabel mainHeaderLabel = new JLabel(theGroupLabel);
                mainHeaderLabel.setBorder(BorderFactory.createEtchedBorder());
                //this.add(mainHeaderLabel,  BorderLayout.NORTH);
                tablesTextList = new HashMap(mtModel.getTableCount());
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    // this was added to the tab previously in place of 'add'. (String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop))
                    String addObjectDescription = (String)mtModel.getTabSymbols().get((Object)mtModel.getTableNames().get(loop));
                    tabbedPane.addTab(null, new ImageIcon(imagePath + iconName), createPane(mtModel, theTableName, FRAME_HEIGHT, FRAME_WIDTH,addObjectDescription));
                    tablesTextList.put((Object)theTableName,(Object)fieldTextList.clone());
                }
                JScrollPane tabPanelScrollPane = new JScrollPane(tabbedPane);
                
                if ((height > 500) || (width > 750))
                {
                    tabPanelScrollPane.setPreferredSize(new Dimension(700,500));
                    tabPanelScrollPane.setMaximumSize(new Dimension(700,500));
                }
                this.add(tabPanelScrollPane,  BorderLayout.CENTER);
                dataUpdateButton = new JButton("Update");
                dataUpdateButton.setFont(new Font("Arial Bold",Font.PLAIN,16));
                dataUpdateButton.setBackground(new Color(255,255,0));

                //dataUpdateEndButton = new JButton("Update and Exit");
                controlPane.setLayout(new BorderLayout());
                controlPane.setBorder(compoundBorder);
                //controlPane.add(dataUpdateEndButton, BorderLayout.EAST);
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

        public JPanel createPane(MTTableDataModel _Model, String _TableName, int _height, int _width, String objectDescription) throws MTException
        {
            
            int rowCount  =  0;
            int colCount = 0;
            int componentWidth = 250;
            int componentHeight = 25;
            int componentRows = 10;
            int componentCols = 4;
            int panelWidth = _width;
            int panelHeight = _height;
            
            
            Border raisedBevel, loweredBevel;
            SpringUtilities springGrid = new SpringUtilities();
            HashMap dbAccessCollection = (HashMap)_Model.getdbAccessCollection();
            lookupTableDataCollection = (HashMap)_Model.getLookupTableDataCollection();
            xMLTableCollection = (HashMap)_Model.getXMLTableCollection();
            comboBoxDefaultCollection = (HashMap)_Model.getComboBoxDefaultCollection();
            updateSynopsisCollection = (HashMap)_Model.getUpdateSynopsisCollection();
            autoSelectFieldCollection = (HashMap)_Model.getAutoSelectFieldCollection();
            groupLookupTableCollection = (HashMap)_Model.getGroupLookupTableDataCollection();
            groupedTableDataCollection = (HashMap)_Model.getGroupedTableDataCollection();
            dependanceTableDataCollection = (HashMap)_Model.getDependanceTableLinks();
            dependanceItemTypeCollection = (HashMap)_Model.getDependanceItemTypes();
            comboBoxInactiveStateCollection = (HashMap)_Model.getcomboBoxInactiveStateCollection();
            comboBoxActiveStateCollection = (HashMap)_Model.getcomboBoxActiveStateCollection();
            dependantItemActivityMap = (HashMap)_Model.getDependantItemActivityMap();
            synonymTableInfoCollection = (HashMap)_Model.getSynonymTableInfoCollection();
            fieldFilterListCollection = (HashMap)_Model.getFieldFilterList();
            
            if (_Model.getComponentWidth() != 0)
            {
                componentWidth =  _Model.getComponentWidth();
                componentHeight = _Model.getComponentHeight();
            }
            
            if (_Model.getComponentPanelWidth() != 0)
            {
                panelWidth =  _Model.getComponentPanelWidth();
                panelHeight = _Model.getComponentPanelHeight();
            }
            
            componentRows = _Model.getComponentRows();
            componentCols = _Model.getComponentCols();
            
            DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)_TableName);
            raisedBevel = BorderFactory.createRaisedBevelBorder();
            loweredBevel = BorderFactory.createLoweredBevelBorder();
            compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
            
            JPanel dataPanel = new JPanel();
            //JPanel labelPanel = new JPanel();
            JPanel fieldPanel = new JPanel();
            JPanel southPanel = new JPanel();
            JPanel buttonPanel = new JPanel();
            JPanel headingPanel = new JPanel();
            
            rowCount = dataInstance.dataTable.getColumnCount();
            if (rowCount < 18)
            {
                rowCount = 18;
            }
            dataPanel.setLayout(new BoxLayout(dataPanel,BoxLayout.Y_AXIS));
            //labelPanel.setLayout(new GridLayout(rowCount, 0));
            
            
            //fieldPanel.setLayout(new SpringLayout());
            
            //fieldPanel.setLayout(new GridLayout(rowCount, 0));
            
            buttonPanel.setLayout(new GridLayout(1,0,5,5));
            buttonPanel.setBorder(compoundBorder);
            southPanel.setLayout(new BorderLayout());
            //JButton clearButton = new JButton("Clear");
            //JButton searchButton = new JButton("Search");
            //buttonPanel.add(clearButton);
            //buttonPanel.add(searchButton);
            dataPanel.setBorder(BorderFactory.createEtchedBorder());
            //dataPanel.setSize(new Dimension(panelWidth,panelHeight));
            //dataPanel.setMaximumSize(new Dimension(panelWidth,panelHeight));
            dataPanel.setPreferredSize(new Dimension(panelWidth,panelHeight));
            fieldPanel.setPreferredSize(new Dimension(panelWidth,panelHeight));
            theColumnNames = dataInstance.dataTable.getColumnNames();
            theColumnAliases = dataInstance.dataTable.getColumnAliases();
            theColumnTypes = dataInstance.dataTable.getColumnKeyTypes();
            theColumnLocks = dataInstance.dataTable.getColumnLocks();
            theColumnDisplays = dataInstance.dataTable.getColumnDisplays();
            theLabelDisplays = dataInstance.dataTable.getLabelDisplays();
            //String thePanelHeading = (String)_Model.getTableAliases().get((Object)_TableName);
            //thePanelHeading = thePanelHeading + ": " + keyDescription;
            String  thePanelHeading = "<html> <h2><font color=\"yellow\">" + objectDescription + " </font></h2> </html>";
            JLabel panelHeadingLabel = new JLabel(thePanelHeading);
            panelHeadingLabel.setForeground(new Color(255,255,255));
            panelHeadingLabel.setPreferredSize(new Dimension(400,25));
            panelHeadingLabel.setMaximumSize(new Dimension(400,25));
            panelHeadingLabel.setFont(new Font("Serif",Font.BOLD,14));
            headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
            headingPanel.setBackground(new Color(0,50,150));
            headingPanel.add(panelHeadingLabel);
            //add this for larger screen
            dataPanel.add(headingPanel, BorderLayout.NORTH);
            colCount = dataInstance.dataTable.getColumnCount();
            fieldTextList = new HashMap(colCount);
            otherButtonList = new HashMap(2);
            otherButtonLabelCollection = new HashMap(2);
            MaskFormatter formatter;
            JFormattedTextField ftf;
            String selectedValue = "";
            ArrayList fieldTextItems = null;
            boolean firstPass = false;
            String firstGroupKey = null;
            String fieldFilter = null;
            JLabel fieldLabel  = null;
            JPanel XMLPanel = null;
            try
            {
                for (int fieldIndex = 0; fieldIndex < colCount; fieldIndex++)
                {
                    String theFieldLabel = theColumnAliases[fieldIndex] + ":";
                    String theFieldName = theColumnNames[fieldIndex];
                    String theFieldType = theColumnTypes[fieldIndex];
                    if (fieldFilterListCollection != null)
                    {
                        fieldFilter = (String)fieldFilterListCollection.get((Object)theFieldName);
                        if (fieldFilter != null)
                        {
                            if (fieldFilter.equalsIgnoreCase("0"))
                            {
                                //continue;
                            }
                        }
                    }
                    String theFieldValue = dataInstance.dataTable.getDataAt(0, fieldIndex);
                    int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                    boolean theFieldLock = theColumnLocks[fieldIndex];
                    boolean theFieldDisplay = theColumnDisplays[fieldIndex];
                    boolean theLabelDisplay = theLabelDisplays[fieldIndex];
                    fieldLabel = new JLabel(theFieldLabel);
                    fieldLabel.setPreferredSize(new Dimension(componentWidth,componentHeight));
                    fieldLabel.setMaximumSize(new Dimension(componentWidth,componentHeight));
                    if (theFieldType.equalsIgnoreCase("PRIMARY"))
                    {
                        String zeroPadding = "";
                        for (int loop = 0; loop < fieldLength - theFieldValue.length(); loop++)
                        {
                            zeroPadding = zeroPadding + "0";
                        }
                        theFieldValue = zeroPadding + theFieldValue;
                        JTextField fieldText = new JTextField(theFieldValue,10);
                        if (theFieldLock)
                        {
                            fieldText.setEnabled(false);
                        } else
                        {
                            fieldText.setEnabled(true);
                        }
                        fieldText.setPreferredSize(new Dimension(componentWidth,componentHeight));
                        fieldText.setMaximumSize(new Dimension(componentWidth,componentHeight));
                        
                        if (theLabelDisplay == true)
                        {
                            fieldPanel.add(fieldLabel);
                        }
                        if (theFieldDisplay == true)
                        {
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
                            String defaultValue = null;
                            String[] comboBoxData = new String[lookupTableData.size()];
                            int comboBoxDataIndex = 0;
                            if (comboBoxDefaultCollection != null)
                            {
                                defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                            }
                            while (iter.hasNext())
                            {
                                String theKey = (String)iter.next();
                                String theValue = (String)lookupTableData.get((Object)theKey);
                                if (defaultValue != null)
                                {
                                    if (theValue.equalsIgnoreCase(defaultValue))
                                    {
                                        selectedValue = theValue;
                                    }
                                };
                                //selectedValue = theValue;
                                comboBoxData[comboBoxDataIndex] = theValue;
                                comboBoxDataIndex++;
                            }
                            quickSort(comboBoxData,0,comboBoxData.length - 1);
                            if (defaultValue == null)
                            {
                                selectedValue = comboBoxData[0];
                            }
                            JComboBox cb = new JComboBox(comboBoxData);
                            cb.setPreferredSize(new Dimension(componentWidth,componentHeight));
                            cb.setMaximumSize(new Dimension(componentWidth,componentHeight));
                            cb.setBackground(new Color(255,255,255));
                            cb.setSelectedItem(selectedValue);
                            if (autoSelectFieldCollection != null)
                            {
                                if (autoSelectFieldCollection.get((Object)theFieldName) != null)
                                {
                                    cb.setEditable(true);
                                    cb.addItemListener(new CBItemListener());
                                    cb.getEditor().getEditorComponent().addKeyListener(new CBKeyListener());
                                    cb.addKeyListener(new CBKeyListener());
                                }
                            }
                            cb.addActionListener(new CBActionListener());

                            if (comboItemCollection == null)
                            {
                                comboLabelCollection = new HashMap(5);
                                comboItemCollection = new HashMap(5);
                                comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                comboItemCollection.put((Object)theFieldName,(Object)cb);
                            } else
                            {
                                comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                comboItemCollection.put((Object)theFieldName,(Object)cb);
                            }
                            if (theLabelDisplay == true)
                            {
                                fieldPanel.add(fieldLabel);
                            }
                            if (theFieldDisplay == true)
                            {
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
                            if (theFieldLock)
                            {
                                //dependanceTextField.setValue("");
                                cb.setEnabled(false);
                                cb.setVisible(false);
                                fieldLabel.setVisible(false);
                                
                            } else
                            {
                                cb.setVisible(true);
                                cb.setEnabled(true);
                                fieldLabel.setVisible(true);
                            }
                            
                            //if (theFieldDisplay == true)
                            //{
                            //    fieldPanel.add(fieldLabel);
                            //    fieldPanel.add(cb);
                            //    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                            //}
                        } else
                        if (theFieldType.equalsIgnoreCase("XMLTREE"))
                        {
                            //lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theFieldName);
                            String XMLFileMap = null;
                            String defaultValue = null;
                            if (xMLTableCollection != null)
                            {
                                XMLFileMap = (String)xMLTableCollection.get((Object)theFieldName);
                            }
                            //XMLNavigator xmlNavigator = new XMLNavigator(XMLFileMap); 
                            if (XMLFileMap != null)
                            {
                               xmlNavigator = new XMLNavigator(XMLFileMap); 
                            }
                            if (comboBoxDefaultCollection != null)
                            {
                                defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                            }
                            if (defaultValue != null)
                            {
                                xmlNavigator.findItemInXML(defaultValue);
                            }
                            JPanel tmpPanel = xmlNavigator.getFramePanel();
                                if (theLabelDisplay == true)
                                {
                                    fieldPanel.add(fieldLabel);
                                }
                            if (theFieldDisplay == true)
                            {
                                XMLPanel = tmpPanel;
                            }
                            ftf = new JFormattedTextField();
                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);

                                /*if (fieldTextList.containsKey(theColumnNames[fieldIndex]))
                                {
                                    fieldTextItems = (ArrayList)fieldTextList.get(theColumnNames[fieldIndex]);
                                    fieldTextItems.add((Object)xmlNavigator);
                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                                } else
                                {
                                    fieldTextItems = new  ArrayList();
                                    fieldTextItems.add((Object)xmlNavigator);
                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)fieldTextItems);
                                }*/
                            
                        } else
                            if (theFieldType.equalsIgnoreCase("GROUPKEY"))
                            {
                                groupLookupData = (HashMap)groupLookupTableCollection.get((Object)theFieldName);
                                //~
                                Set keySet = groupLookupData.keySet();
                                Iterator iter = keySet.iterator();
                                String[] comboBoxData = new String[groupLookupData.size()];
                                int comboBoxDataIndex = 0;
                                firstPass = true;
                                while (iter.hasNext())
                                {
                                    String theKey = (String)iter.next();
                                    //if (firstPass)
                                    //{
                                    //    firstGroupKey = theKey;
                                    //}
                                    //firstPass = false;
                                    String theValue = (String)groupLookupData.get((Object)theKey);
                                    //if (theFieldValue.equalsIgnoreCase(theKey))
                                    //{
                                    //    selectedValue = theValue;
                                    //}
                                    comboBoxData[comboBoxDataIndex] = theValue;
                                    comboBoxDataIndex++;
                                }
                                quickSort(comboBoxData,0,comboBoxData.length - 1);
                                selectedValue = comboBoxData[0];
                                Iterator iterSorted = keySet.iterator();
                                while (iterSorted.hasNext())
                                {
                                    String theSKey = (String)iterSorted.next();
                                    String theSValue = (String)groupLookupData.get((Object)theSKey);
                                    if (theSValue.equalsIgnoreCase(selectedValue))
                                    {
                                        firstGroupKey = theSKey;
                                    }
                                }
                                
                                JComboBox cb = new JComboBox(comboBoxData);
                                cb.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                cb.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                cb.setBackground(new Color(255,255,255));
                                cb.setSelectedItem(selectedValue);
                                if (autoSelectFieldCollection != null)
                                {
                                    if (autoSelectFieldCollection.get((Object)theFieldName) != null)
                                    {
                                        cb.setEditable(true);
                                        cb.addItemListener(new CBItemListener());
                                        cb.getEditor().getEditorComponent().addKeyListener(new CBKeyListener());
                                        cb.addKeyListener(new CBKeyListener());
                                    }
                                }
                                cb.addActionListener(new CBActionListener());
                                
                                if (theLabelDisplay == true)
                                {
                                    fieldPanel.add(fieldLabel);
                                }
                                if (theFieldDisplay == true)
                                {
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
                                if (comboItemCollection == null)
                                {
                                    comboLabelCollection = new HashMap(5);
                                    comboItemCollection = new HashMap(5);
                                    comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                    comboItemCollection.put((Object)theFieldName,(Object)cb);
                                } else
                                {
                                    comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                    comboItemCollection.put((Object)theFieldName,(Object)cb);
                                }
                                
                                
                                //if (theFieldDisplay == true)
                                //{
                                //    fieldPanel.add(fieldLabel);
                                //    fieldPanel.add(cb);
                                //    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                                //}
                            }  else
                                if (theFieldType.equalsIgnoreCase("GROUP"))
                                {
                                    groupData = (HashMap)groupedTableDataCollection.get((Object)theFieldName);
                                    groupDataSubset = (HashMap)groupData.get((Object)firstGroupKey);
                                    Set keySet = groupDataSubset.keySet();
                                    Iterator iter = keySet.iterator();
                                    String[] comboBoxData = new String[groupDataSubset.size()];
                                    String defaultValue = null;
                                    int comboBoxDataIndex = 0;
                                    if (comboBoxDefaultCollection != null)
                                    {
                                        defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                    }
                                    while (iter.hasNext())
                                    {
                                        String theKey = (String)iter.next();
                                        String theValue = (String)groupDataSubset.get((Object)theKey);
                                        if (defaultValue != null)
                                        {
                                            if (theValue.equalsIgnoreCase(defaultValue))
                                            {
                                                selectedValue = theValue;
                                            }
                                        }
                                        comboBoxData[comboBoxDataIndex] = theValue;
                                        comboBoxDataIndex++;
                                    }
                                    quickSort(comboBoxData,0,comboBoxData.length - 1);
                                    JComboBox cb = new JComboBox(comboBoxData);
                                    cb.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                    cb.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                    cb.setBackground(new Color(255,255,255));
                                    cb.setSelectedItem(selectedValue);
                                    cb.addActionListener(new CBActionListener());
                                    //cb.setLightWeightPopupEnabled(true);
                                    if (autoSelectFieldCollection != null)
                                    {
                                        if (autoSelectFieldCollection.get((Object)theFieldName) != null)
                                        {
                                            cb.setEditable(true);
                                            cb.addItemListener(new CBItemListener());
                                            cb.getEditor().getEditorComponent().addKeyListener(new CBKeyListener());
                                            cb.getEditor().getEditorComponent().addMouseListener(new MouseClickListener());
                                            cb.addKeyListener(new CBKeyListener());
                                            //cb.setToolTipText("Right-Clicking on this item will bring up a synonym list if any exists.");

                                            
                                        }
                                    }
                                    if (theLabelDisplay == true)
                                    {
                                        fieldPanel.add(fieldLabel);
                                    }
                                    if (theFieldDisplay == true)
                                    {
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
                                    if (comboItemCollection == null)
                                    {
                                        comboLabelCollection = new HashMap(5);
                                        comboItemCollection = new HashMap(5);
                                        comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                        comboItemCollection.put((Object)theFieldName,(Object)cb);
                                    } else
                                    {
                                        comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                        comboItemCollection.put((Object)theFieldName,(Object)cb);
                                    }
                                    if (theFieldLock)
                                    {
                                        //dependanceTextField.setValue("");
                                        cb.setEnabled(false);
                                        cb.setVisible(false);
                                        fieldLabel.setVisible(false);
                                        
                                    } else
                                    {
                                        cb.setVisible(true);
                                        cb.setEnabled(true);
                                        fieldLabel.setVisible(true);
                                    }
                                    
                                    
                                    //if (theFieldDisplay == true)
                                    //{
                                    //    fieldPanel.add(fieldLabel);
                                    //    fieldPanel.add(cb);
                                    //    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                                    //}
                                } else
                                    if (theFieldType.equalsIgnoreCase("PHONE_FIELD"))
                                    {
                                        formatter = new  MaskFormatter("(###) ###-####");
                                        ftf = new JFormattedTextField(formatter);
                                        ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                        ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                        ftf.setValue(theFieldValue);
                                        //cb.setBackground(new Color(255,255,255));
                                        if (theFieldDisplay == true)
                                        {
                                            fieldPanel.add(fieldLabel);
                                            fieldPanel.add(ftf);
                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                        }
                                    }  else
                                        if (theFieldType.equalsIgnoreCase("CELL_FIELD"))
                                        {
                                            formatter = new  MaskFormatter("(###) ###-####");
                                            ftf = new JFormattedTextField(formatter);
                                            ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                            ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                            ftf.setValue(theFieldValue);
                                            //cb.setBackground(new Color(255,255,255));
                                            if (theLabelDisplay == true)
                                            {
                                                fieldPanel.add(fieldLabel);
                                            }
                                            if (theFieldDisplay == true)
                                            {
                                                fieldPanel.add(ftf);
                                                fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                            }
                                        } else
                                            if (theFieldType.equalsIgnoreCase("FILLER_FIELD"))
                                            {
                                                if (theLabelDisplay == true)
                                                {
                                                    if (theFieldLabel.equalsIgnoreCase("FILLER:") == true)
                                                    {
                                                        fieldPanel.add(new JLabel());
                                                    } else
                                                    {
                                                        if (theFieldLabel.endsWith(":") == true)
                                                        {
                                                            theFieldLabel = theFieldLabel.substring(0,theFieldLabel.indexOf(":"));
                                                        }
                                                        fieldLabel = new JLabel(theFieldLabel);
                                                        fieldPanel.add(fieldLabel);
                                                    }
                                                } else
                                                {
                                                    fieldPanel.add(new JLabel());
                                                }
                                            }  else
                                                if (theFieldType.equalsIgnoreCase("AREA_FIELD"))
                                                {
                                                    formatter = new  MaskFormatter("####");
                                                    ftf = new JFormattedTextField(formatter);
                                                    ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                    ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                    ftf.setValue(theFieldValue);
                                                    //cb.setBackground(new Color(255,255,255));
                                                    if (theLabelDisplay == true)
                                                    {
                                                        fieldPanel.add(fieldLabel);
                                                    }
                                                    if (theFieldDisplay == true)
                                                    {
                                                        fieldPanel.add(ftf);
                                                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                    }
                                                }  else
                                                    if (theFieldType.equalsIgnoreCase("ID_FIELD"))
                                                    {
                                                        formatter = new  MaskFormatter("#############");
                                                        ftf = new JFormattedTextField(formatter);
                                                        ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                        ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                        ftf.setValue(theFieldValue);
                                                        //cb.setBackground(new Color(255,255,255));
                                                        if (theLabelDisplay == true)
                                                        {
                                                            fieldPanel.add(fieldLabel);
                                                        }
                                                        if (theFieldDisplay == true)
                                                        {
                                                            fieldPanel.add(ftf);
                                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                        }
                                                    }  else
                                                        if (theFieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                                                        {
                                                            formatter = new  MaskFormatter("##/##");
                                                            ftf = new JFormattedTextField(formatter);
                                                            ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                            ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                            ftf.setValue(theFieldValue);
                                                            //cb.setBackground(new Color(255,255,255));
                                                            if (theLabelDisplay == true)
                                                            {
                                                                fieldPanel.add(fieldLabel);
                                                            }
                                                            if (theFieldDisplay == true)
                                                            {
                                                                fieldPanel.add(ftf);
                                                                fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                            }
                                                        }  else
                                                            if (theFieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
                                                            {
                                                                //textField = new JTextField((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                                                                //textField.addMouseListener(new TheMouseListener());
                                                                //textField.setBackground(new Color(componentWidth,componentHeight0,200));
                                                                //textField.setForeground(new Color(componentWidth,componentHeight0,200));
                                                                ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                                                                openTextAreaButton = new JButton("Push to open",openIcon);
                                                                openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                                                                openTextAreaButton.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                openTextAreaButton.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                openTextAreaButton.addMouseListener(new TheMouseListener());
                                                                //openTextAreaButton.addActionListener(this);
                                                                if (theLabelDisplay == true)
                                                                {
                                                                    fieldPanel.add(fieldLabel);
                                                                }
                                                                if (theFieldDisplay == true)
                                                                {
                                                                    textArea = new JTextArea(80,25);
                                                                    textArea.setText(theFieldValue);
                                                                    fieldPanel.add(openTextAreaButton);
                                                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)textArea);
                                                                    otherButtonList.put((Object)theColumnNames[fieldIndex],(Object)openTextAreaButton);
                                                                    otherButtonLabelCollection.put((Object)theColumnNames[fieldIndex],(Object)fieldLabel);
                                                                }
                                                                if (theFieldLock)
                                                                {
                                                                    openTextAreaButton.setVisible(false);
                                                                    fieldLabel.setVisible(false);
                                                                    
                                                                } else
                                                                {
                                                                    openTextAreaButton.setVisible(true);
                                                                    fieldLabel.setVisible(true);
                                                                }
                                                                
                                                            }  else
                                                                if (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                                                                {
                                                                    ImageIcon openIcon = new ImageIcon(imagePath + "Open16.gif");
                                                                    openTextAreaButton = new JButton("Push to open",openIcon);
                                                                    //addFileButton = new JButton("Add");
                                                                    openTextAreaButton.setToolTipText((String)_TableName + "," + (String)theColumnNames[fieldIndex]);
                                                                    openTextAreaButton.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                    openTextAreaButton.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                    openTextAreaButton.addMouseListener(new TheMouseListener());
                                                                    //openTextAreaButton.add(addFileButton);
                                                                    if (theLabelDisplay == true)
                                                                    {
                                                                        fieldPanel.add(fieldLabel);
                                                                    }
                                                                    if (theFieldDisplay == true)
                                                                    {
                                                                        textArea = new JTextArea(80,25);
                                                                        textArea.setText(theFieldValue);
                                                                        fieldPanel.add(openTextAreaButton);
                                                                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)textArea);
                                                                    }
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
                                                                            //if (theFieldValue.equalsIgnoreCase("0"))
                                                                            //    selectedValue = "No";
                                                                            //else
                                                                            //    selectedValue = "Yes";
                                                                            comboBoxData[comboBoxDataIndex] = theValue;
                                                                        }
                                                                        JComboBox cb = new JComboBox(comboBoxData);
                                                                        cb.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                        cb.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                        cb.setBackground(new Color(255,255,255));
                                                                        selectedValue = "No";
                                                                        cb.setSelectedItem(selectedValue);
                                                                        if (theLabelDisplay == true)
                                                                        {
                                                                            fieldPanel.add(fieldLabel);
                                                                        }
                                                                        if (theFieldDisplay == true)
                                                                        {
                                                                            fieldPanel.add(cb);
                                                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                                                                        }
                                                                        if (comboItemCollection == null)
                                                                        {
                                                                            comboLabelCollection = new HashMap(5);
                                                                            comboItemCollection = new HashMap(5);
                                                                            comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                            comboItemCollection.put((Object)theFieldName,(Object)cb);
                                                                        } else
                                                                        {
                                                                            comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                            comboItemCollection.put((Object)theFieldName,(Object)cb);
                                                                        }
                                                                        if (theFieldLock)
                                                                        {
                                                                            //dependanceTextField.setValue("");
                                                                            cb.setEnabled(false);
                                                                            cb.setVisible(false);
                                                                            fieldLabel.setVisible(false);
                                                                            
                                                                        } else
                                                                        {
                                                                            cb.setVisible(true);
                                                                            cb.setEnabled(true);
                                                                            fieldLabel.setVisible(true);
                                                                        }
                                                                    }   else
                                                                        if (theFieldType.equalsIgnoreCase("BOOLEAN_GROUP"))
                                                                        {
                                                                            //build combobox data array
                                                                            String[] comboBoxData = new String[2];
                                                                            String defaultValue = null;
                                                                            
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
                                                                                comboBoxData[comboBoxDataIndex] = theValue;
                                                                            }
                                                                            //int comboBoxDataIndex = 0;
                                                                            if (comboBoxDefaultCollection != null)
                                                                            {
                                                                                defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                                                            }
                                                                            
                                                                            for (int loop = 0; loop < 2; loop++)
                                                                            {
                                                                                if (comboBoxData[loop].equalsIgnoreCase(defaultValue))
                                                                                {
                                                                                    selectedValue = defaultValue;
                                                                                    break;
                                                                                }
                                                                            }
                                                                            JComboBox cb = new JComboBox(comboBoxData);
                                                                            cb.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                            cb.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                            cb.setBackground(new Color(255,255,255));
                                                                            cb.setSelectedItem(selectedValue);
                                                                            cb.addActionListener(new CBActionListener());
                                                                            if (theLabelDisplay == true)
                                                                            {
                                                                                fieldPanel.add(fieldLabel);
                                                                            }
                                                                            if (theFieldDisplay == true)
                                                                            {
                                                                                fieldPanel.add(cb);
                                                                                fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                                                                            }
                                                                            if (comboItemCollection == null)
                                                                            {
                                                                                comboLabelCollection = new HashMap(5);
                                                                                comboItemCollection = new HashMap(5);
                                                                                comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                comboItemCollection.put((Object)theFieldName,(Object)cb);
                                                                            } else
                                                                            {
                                                                                comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                comboItemCollection.put((Object)theFieldName,(Object)cb);
                                                                            }
                                                                            if (theFieldLock)
                                                                            {
                                                                                //dependanceTextField.setValue("");
                                                                                cb.setEnabled(false);
                                                                                cb.setVisible(false);
                                                                                fieldLabel.setVisible(false);
                                                                                
                                                                            } else
                                                                            {
                                                                                cb.setVisible(true);
                                                                                cb.setEnabled(true);
                                                                                fieldLabel.setVisible(true);
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
                                                                                cb.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                cb.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                cb.setBackground(new Color(255,255,255));
                                                                                cb.setSelectedItem(selectedValue);
                                                                                if (theLabelDisplay == true)
                                                                                {
                                                                                    fieldPanel.add(fieldLabel);
                                                                                }
                                                                                if (theFieldDisplay == true)
                                                                                {
                                                                                    fieldPanel.add(cb);
                                                                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)cb);
                                                                                }
                                                                                if (comboItemCollection == null)
                                                                                {
                                                                                    comboLabelCollection = new HashMap(5);
                                                                                    comboItemCollection = new HashMap(5);
                                                                                    comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                    comboItemCollection.put((Object)theFieldName,(Object)cb);
                                                                                } else
                                                                                {
                                                                                    comboLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                    comboItemCollection.put((Object)theFieldName,(Object)cb);
                                                                                }
                                                                                if (theFieldLock)
                                                                                {
                                                                                    //dependanceTextField.setValue("");
                                                                                    cb.setEnabled(false);
                                                                                    cb.setVisible(false);
                                                                                    fieldLabel.setVisible(false);
                                                                                    
                                                                                } else
                                                                                {
                                                                                    cb.setVisible(true);
                                                                                    cb.setEnabled(true);
                                                                                    fieldLabel.setVisible(true);
                                                                                }
                                                                            } else
                                                                                if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                                                                                {
                                                                                    formatter = new  MaskFormatter("U U U");
                                                                                    ftf = new JFormattedTextField(formatter);
                                                                                    ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                    ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                    ftf.setValue(theFieldValue);
                                                                                    //cb.setBackground(new Color(255,255,255));
                                                                                    if (theLabelDisplay == true)
                                                                                    {
                                                                                        fieldPanel.add(fieldLabel);
                                                                                    }
                                                                                    if (theFieldDisplay == true)
                                                                                    {
                                                                                        fieldPanel.add(ftf);
                                                                                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                                                    }
                                                                                } else
                                                                                    if (theFieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                                                                                    {
                                                                                        formatter = new  MaskFormatter("####-##-## ##:##:##");
                                                                                        ftf = new JFormattedTextField(formatter);
                                                                                        ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                        ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                        ftf.setValue(theFieldValue);
                                                                                        //cb.setBackground(new Color(255,255,255));
                                                                                        if (theLabelDisplay == true)
                                                                                        {
                                                                                            fieldPanel.add(fieldLabel);
                                                                                        }
                                                                                        if (theFieldDisplay == true)
                                                                                        {
                                                                                            fieldPanel.add(ftf);
                                                                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                                                        }
                                                                                    }  else
                                                                                        if (theFieldType.equalsIgnoreCase("DATE_FIELD"))
                                                                                        {
                                                                                            formatter = new  MaskFormatter("####-##-##");
                                                                                            ftf = new JFormattedTextField(formatter);
                                                                                            ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                            ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                            ftf.setValue(theFieldValue);
                                                                                            //cb.setBackground(new Color(255,255,255));
                                                                                            if (theLabelDisplay == true)
                                                                                            {
                                                                                                fieldPanel.add(fieldLabel);
                                                                                            }
                                                                                            if (theFieldDisplay == true)
                                                                                            {
                                                                                                fieldPanel.add(ftf);
                                                                                                fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                                                            }
                                                                                        }   else
                                                                                            if (theFieldType.equalsIgnoreCase("SHORT_DATE"))
                                                                                            {
                                                                                                formatter = new  MaskFormatter("##-##");
                                                                                                ftf = new JFormattedTextField(formatter);
                                                                                                ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                ftf.setValue(theFieldValue);
                                                                                                if (theFieldLock)
                                                                                                {
                                                                                                    ftf.setEnabled(false);
                                                                                                } else
                                                                                                {
                                                                                                    ftf.setEnabled(true);
                                                                                                }
                                                                                                if (theLabelDisplay == true)
                                                                                                {
                                                                                                    fieldPanel.add(fieldLabel);
                                                                                                }
                                                                                                if (theFieldDisplay == true)
                                                                                                {
                                                                                                    fieldPanel.add(ftf);
                                                                                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                                                                }
                                                                                            } else
                                                                                                if (theFieldType.equalsIgnoreCase("BOOLEAN_GROUPDEPENDANT"))
                                                                                                {
                                                                                                    String[] intStrings = new String[200];
                                                                                                    String defaultValue = null;
                                                                                                    for (int loop = 0; loop < 200; loop++)
                                                                                                    {
                                                                                                         intStrings[loop] = String.valueOf(loop);
                                                                                                    }
                                                                                                    SpinnerListModel intModel = new  CyclingSpinnerListModel(intStrings);
                                                                                                    JSpinner spinner = new JSpinner(intModel);
                                                                                                    spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                    spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                    if (comboBoxDefaultCollection != null) {
                                                                                                        defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                                                                                    }
                                                                                                    if (defaultValue != null) {
                                                                                                        spinner.setValue(defaultValue);
                                                                                                        spinner.setVisible(false);
                                                                                                        fieldLabel.setVisible(false);
                                                                                                    }
                                                                                                    if (theFieldLock) {
                                                                                                        //ftf.setEnabled(false);
                                                                                                    } else {
                                                                                                        //ftf.setEnabled(true);
                                                                                                    }
                                                                                                    if (theLabelDisplay == true) {
                                                                                                        fieldPanel.add(fieldLabel);
                                                                                                    }
                                                                                                    if (theFieldDisplay == true) {
                                                                                                        fieldPanel.add(spinner);
                                                                                                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                    }
                                                                                                    if (textFieldLabelCollection == null) {
                                                                                                        textFieldLabelCollection = new HashMap(5);
                                                                                                        textFieldLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                                    } else {
                                                                                                        textFieldLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                                    }
                                                                                                } else
                                                                                                    if (theFieldType.equalsIgnoreCase("GROUPDEPENDANT") == true)
                                                                                                    {
                                                                                                        String mask = "";
                                                                                                        for (int loop = 0; loop < fieldLength; loop++)
                                                                                                        {
                                                                                                            mask = mask + "*";
                                                                                                        }
                                                                                                        formatter = new  MaskFormatter(mask);
                                                                                                        finalFtf = new JFormattedTextField(formatter);
                                                                                                        finalFtf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                        finalFtf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                        finalFtf.setValue(theFieldValue);
                                                                                                        finalFtf.setVisible(true);
                                                                                                        otherButton = new JButton("Unlisted Item", new ImageIcon(imagePath + "add16.gif"));
                                                                                                        otherButton.addActionListener(new ActionListener()
                                                                                                        {
                                                                                                            public void actionPerformed(ActionEvent e)
                                                                                                            {
                                                                                                                if (finalFtf.isVisible() == false)
                                                                                                                {
                                                                                                                    finalFtf.setValue("");
                                                                                                                    finalFtf.setVisible(true);
                                                                                                                    finalFtf.setEnabled(true);
                                                                                                                } else
                                                                                                                {
                                                                                                                    finalFtf.setVisible(false);
                                                                                                                    finalFtf.setEnabled(false);
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                        otherButton.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                        otherButton.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                        
                                                                                                        if (theLabelDisplay == true)
                                                                                                        {
                                                                                                            fieldPanel.add(otherButton);
                                                                                                        }
                                                                                                        if (theFieldDisplay == true)
                                                                                                        {
                                                                                                            fieldPanel.add(finalFtf);
                                                                                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)finalFtf);
                                                                                                        }
                                                                                                    } else
                                                                                                        if (theFieldType.equalsIgnoreCase("FIELD_FOREIGN"))
                                                                                                        {
                                                                                                            String mask = "";
                                                                                                            for (int loop = 0; loop < fieldLength; loop++)
                                                                                                            {
                                                                                                                mask = mask + "*";
                                                                                                            }
                                                                                                            formatter = new  MaskFormatter(mask);
                                                                                                            ftf = new JFormattedTextField(formatter);
                                                                                                            ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                            ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                            ftf.setValue(theFieldValue);
                                                                                                            if (textFieldLabelCollection == null)
                                                                                                            {
                                                                                                                textFieldLabelCollection = new HashMap(5);
                                                                                                                textFieldLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                                            } else
                                                                                                            {
                                                                                                                textFieldLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                                            }
                                                                                                            if (theFieldLock)
                                                                                                            {
                                                                                                                ftf.setEnabled(false);
                                                                                                                fieldLabel.setVisible(false);
                                                                                                            } else
                                                                                                            {
                                                                                                                fieldLabel.setVisible(true);
                                                                                                                ftf.setEnabled(true);
                                                                                                            }
                                                                                                            if (theLabelDisplay == true)
                                                                                                            {
                                                                                                                fieldPanel.add(fieldLabel);
                                                                                                            }
                                                                                                            if (theFieldDisplay == true)
                                                                                                            {
                                                                                                                fieldPanel.add(ftf);
                                                                                                                fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)ftf);
                                                                                                            }
                                                                                                        }  else
                                                                                                            if (theFieldType.equalsIgnoreCase("SPININTFIELD"))
                                                                                                            {
                                                                                                                String[] intStrings = new String[32];
                                                                                                                for (int loop = 0; loop < 32; loop++)
                                                                                                                {
                                                                                                                    intStrings[loop] = String.valueOf(loop);
                                                                                                                }
                                                                                                                SpinnerListModel intModel = new  CyclingSpinnerListModel(intStrings);
                                                                                                                JSpinner spinner = new JSpinner(intModel);
                                                                                                                spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                if (theFieldLock)
                                                                                                                {
                                                                                                                    //ftf.setEnabled(false);
                                                                                                                } else
                                                                                                                {
                                                                                                                    //ftf.setEnabled(true);
                                                                                                                }
                                                                                                                if (theLabelDisplay == true)
                                                                                                                {
                                                                                                                    fieldPanel.add(fieldLabel);
                                                                                                                }
                                                                                                                if (theFieldDisplay == true)
                                                                                                                {
                                                                                                                    fieldPanel.add(spinner);
                                                                                                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                }
                                                                                                            }  else
                                                                                                                if (theFieldType.equalsIgnoreCase("SPINTEMP"))
                                                                                                                {
                                                                                                                    String[] floatStrings = new String[100];
                                                                                                                    String defaultValue = null;
                                                                                                                    double floatPoint = 0.1f;
                                                                                                                    double tempIncrement = 30.0f;
                                                                                                                    for (int loop = 0; loop < 100; loop++)
                                                                                                                    {
                                                                                                                        
                                                                                                                        String tempString = String.valueOf((double)tempIncrement);
                                                                                                                        tempIncrement = tempIncrement + floatPoint;
                                                                                                                        tempString = tempString.substring(0,tempString.indexOf(".")+2);
                                                                                                                        floatStrings[loop] = tempString;
                                                                                                                    }
                                                                                                                    SpinnerListModel intModel = new  CyclingSpinnerListModel(floatStrings);
                                                                                                                    JSpinner spinner = new JSpinner(intModel);
                                                                                                                    spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                    spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                    if (comboBoxDefaultCollection != null)
                                                                                                                    {
                                                                                                                        defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                                                                                                    }
                                                                                                                    if (defaultValue != null)
                                                                                                                    {
                                                                                                                        spinner.setValue(defaultValue);
                                                                                                                    }
                                                                                                                    if (theFieldLock)
                                                                                                                    {
                                                                                                                        //ftf.setEnabled(false);
                                                                                                                    } else
                                                                                                                    {
                                                                                                                        //ftf.setEnabled(true);
                                                                                                                    }
                                                                                                                    if (theLabelDisplay == true)
                                                                                                                    {
                                                                                                                        fieldPanel.add(fieldLabel);
                                                                                                                    }
                                                                                                                    if (theFieldDisplay == true)
                                                                                                                    {
                                                                                                                        fieldPanel.add(spinner);
                                                                                                                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                    }
                                                                                                                }  else
                                                                                                                    if (theFieldType.equalsIgnoreCase("SPINPULSE"))
                                                                                                                    {
                                                                                                                        String defaultValue = null;
                                                                                                                        String[] intStrings = new String[170];
                                                                                                                        for (int loop = 0; loop < 170; loop++)
                                                                                                                        {
                                                                                                                            intStrings[loop] = String.valueOf(loop+30);
                                                                                                                        }
                                                                                                                        SpinnerListModel intModel = new  CyclingSpinnerListModel(intStrings);
                                                                                                                        JSpinner spinner = new JSpinner(intModel);
                                                                                                                        spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                        spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                        if (comboBoxDefaultCollection != null)
                                                                                                                        {
                                                                                                                            defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                                                                                                        }
                                                                                                                        if (defaultValue != null)
                                                                                                                        {
                                                                                                                            spinner.setValue(defaultValue);
                                                                                                                        }
                                                                                                                        
                                                                                                                        if (theFieldLock)
                                                                                                                        {
                                                                                                                            //ftf.setEnabled(false);
                                                                                                                        } else
                                                                                                                        {
                                                                                                                            //ftf.setEnabled(true);
                                                                                                                        }
                                                                                                                        if (theLabelDisplay == true)
                                                                                                                        {
                                                                                                                            fieldPanel.add(fieldLabel);
                                                                                                                        }
                                                                                                                        if (theFieldDisplay == true)
                                                                                                                        {
                                                                                                                            fieldPanel.add(spinner);
                                                                                                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                        }
                                                                                                                    }   else
                                                                                                                        if (theFieldType.equalsIgnoreCase("SPINBREATH"))
                                                                                                                        {
                                                                                                                            String defaultValue = null;
                                                                                                                            String[] intStrings = new String[100];
                                                                                                                            for (int loop = 0; loop < 100; loop++)
                                                                                                                            {
                                                                                                                                intStrings[loop] = String.valueOf(loop+5);
                                                                                                                            }
                                                                                                                            SpinnerListModel intModel = new  CyclingSpinnerListModel(intStrings);
                                                                                                                            JSpinner spinner = new JSpinner(intModel);
                                                                                                                            spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                            spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                            if (comboBoxDefaultCollection != null)
                                                                                                                            {
                                                                                                                                defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                                                                                                            }
                                                                                                                            if (defaultValue != null)
                                                                                                                            {
                                                                                                                                spinner.setValue(defaultValue);
                                                                                                                            }
                                                                                                                            
                                                                                                                            if (theFieldLock)
                                                                                                                            {
                                                                                                                                //ftf.setEnabled(false);
                                                                                                                            } else
                                                                                                                            {
                                                                                                                                //ftf.setEnabled(true);
                                                                                                                            }
                                                                                                                            if (theLabelDisplay == true)
                                                                                                                            {
                                                                                                                                fieldPanel.add(fieldLabel);
                                                                                                                            }
                                                                                                                            if (theFieldDisplay == true)
                                                                                                                            {
                                                                                                                                fieldPanel.add(spinner);
                                                                                                                                fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                            }
                                                                                                                        }  else
                                                                                                                            if (theFieldType.equalsIgnoreCase("SPINBPS"))
                                                                                                                            {
                                                                                                                                String[] intStrings = new String[190];
                                                                                                                                String defaultValue = null;
                                                                                                                                for (int loop = 0; loop < 190; loop++)
                                                                                                                                {
                                                                                                                                    intStrings[loop] = String.valueOf(loop+60);
                                                                                                                                }
                                                                                                                                SpinnerListModel intModel = new  CyclingSpinnerListModel(intStrings);
                                                                                                                                JSpinner spinner = new JSpinner(intModel);
                                                                                                                                spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                if (comboBoxDefaultCollection != null)
                                                                                                                                {
                                                                                                                                    defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                                                                                                                }
                                                                                                                                if (defaultValue != null)
                                                                                                                                {
                                                                                                                                    spinner.setValue(defaultValue);
                                                                                                                                }
                                                                                                                                if (theFieldLock)
                                                                                                                                {
                                                                                                                                    //ftf.setEnabled(false);
                                                                                                                                } else
                                                                                                                                {
                                                                                                                                    //ftf.setEnabled(true);
                                                                                                                                }
                                                                                                                                if (theLabelDisplay == true)
                                                                                                                                {
                                                                                                                                    fieldPanel.add(fieldLabel);
                                                                                                                                }
                                                                                                                                if (theFieldDisplay == true)
                                                                                                                                {
                                                                                                                                    fieldPanel.add(spinner);
                                                                                                                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                                }
                                                                                                                            }   else
                                                                                                                                if (theFieldType.equalsIgnoreCase("SPINBPD"))
                                                                                                                                {
                                                                                                                                    String defaultValue = null;
                                                                                                                                    String[] intStrings = new String[150];
                                                                                                                                    for (int loop = 0; loop < 150; loop++)
                                                                                                                                    {
                                                                                                                                        intStrings[loop] = String.valueOf(loop+20);
                                                                                                                                    }
                                                                                                                                    SpinnerListModel intModel = new  CyclingSpinnerListModel(intStrings);
                                                                                                                                    JSpinner spinner = new JSpinner(intModel);
                                                                                                                                    spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                    spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                    if (comboBoxDefaultCollection != null)
                                                                                                                                    {
                                                                                                                                        defaultValue = (String)comboBoxDefaultCollection.get((Object)theFieldName);
                                                                                                                                    }
                                                                                                                                    if (defaultValue != null)
                                                                                                                                    {
                                                                                                                                        spinner.setValue(defaultValue);
                                                                                                                                    }
                                                                                                                                    if (theFieldLock)
                                                                                                                                    {
                                                                                                                                        //ftf.setEnabled(false);
                                                                                                                                    } else
                                                                                                                                    {
                                                                                                                                        //ftf.setEnabled(true);
                                                                                                                                    }
                                                                                                                                    if (theLabelDisplay == true)
                                                                                                                                    {
                                                                                                                                        fieldPanel.add(fieldLabel);
                                                                                                                                    }
                                                                                                                                    if (theFieldDisplay == true)
                                                                                                                                    {
                                                                                                                                        fieldPanel.add(spinner);
                                                                                                                                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                                    }
                                                                                                                                }  else
                                                                                                                                    if (theFieldType.equalsIgnoreCase("SPINMONTHYEAR"))
                                                                                                                                    {
                                                                                                                                        Calendar calendar = Calendar.getInstance();
                                                                                                                                        Date initDate = calendar.getTime();
                                                                                                                                        calendar.add(Calendar.DAY_OF_MONTH , -90);
                                                                                                                                        Date earliestDate = calendar.getTime();
                                                                                                                                        calendar.add(Calendar.DAY_OF_MONTH, 90);
                                                                                                                                        Date latestDate = calendar.getTime();
                                                                                                                                        SpinnerDateModel dateModel = new SpinnerDateModel(initDate,
                                                                                                                                        null,
                                                                                                                                        null,
                                                                                                                                        Calendar.DAY_OF_MONTH);//ignored for user input
                                                                                                                                        JSpinner  spinner = new JSpinner(dateModel);
                                                                                                                                        spinner.setEditor(new JSpinner.DateEditor(spinner, "MM/yy"));
                                                                                                                                        //Tweak the spinner's formatted text field.
                                                                                                                                        //ftf = getTextField(spinner);
                                                                                                                                        //if (ftf != null ) {
                                                                                                                                        //    ftf.setHorizontalAlignment(JTextField.RIGHT);
                                                                                                                                        //    ftf.setBorder(BorderFactory.createEmptyBorder(1,1,1,3));
                                                                                                                                        //}
                                                                                                                                        spinner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                                                                                                                                        spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                        spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                        //XXX: No easy way to get to the buttons and change their border.
                                                                                                                                        //setSeasonalColor(dateModel.getDate()); //initialize color
                                                                                                                                        if (theFieldLock)
                                                                                                                                        {
                                                                                                                                            //ftf.setEnabled(false);
                                                                                                                                        } else
                                                                                                                                        {
                                                                                                                                            //ftf.setEnabled(true);
                                                                                                                                        }
                                                                                                                                        if (theLabelDisplay == true)
                                                                                                                                        {
                                                                                                                                            fieldPanel.add(fieldLabel);
                                                                                                                                        }
                                                                                                                                        if (theFieldDisplay == true)
                                                                                                                                        {
                                                                                                                                            fieldPanel.add(spinner);
                                                                                                                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                                        }
                                                                                                                                    }    else
                                                                                                                                        if (theFieldType.equalsIgnoreCase("SPINLITMONTHYEAR"))
                                                                                                                                        {
                                                                                                                                            Calendar calendar = Calendar.getInstance();
                                                                                                                                            Date initDate = calendar.getTime();
                                                                                                                                            calendar.add(Calendar.DAY_OF_MONTH , -90);
                                                                                                                                            Date earliestDate = calendar.getTime();
                                                                                                                                            calendar.add(Calendar.DAY_OF_MONTH, 90);
                                                                                                                                            Date latestDate = calendar.getTime();
                                                                                                                                            SpinnerDateModel dateModel = new SpinnerDateModel(initDate,
                                                                                                                                            null,
                                                                                                                                            null,
                                                                                                                                            Calendar.DAY_OF_MONTH);//ignored for user input
                                                                                                                                            JSpinner  spinner = new JSpinner(dateModel);
                                                                                                                                            spinner.setEditor(new JSpinner.DateEditor(spinner, "MMMMM yyyy"));
                                                                                                                                            //Tweak the spinner's formatted text field.
                                                                                                                                            //ftf = getTextField(spinner);
                                                                                                                                            //if (ftf != null ) {
                                                                                                                                            //    ftf.setHorizontalAlignment(JTextField.RIGHT);
                                                                                                                                            //    ftf.setBorder(BorderFactory.createEmptyBorder(1,1,1,3));
                                                                                                                                            //}
                                                                                                                                            spinner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                                                                                                                                            spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                            spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                            //XXX: No easy way to get to the buttons and change their border.
                                                                                                                                            //setSeasonalColor(dateModel.getDate()); //initialize color
                                                                                                                                            if (theFieldLock)
                                                                                                                                            {
                                                                                                                                                //ftf.setEnabled(false);
                                                                                                                                            } else
                                                                                                                                            {
                                                                                                                                                //ftf.setEnabled(true);
                                                                                                                                            }
                                                                                                                                            if (theLabelDisplay == true)
                                                                                                                                            {
                                                                                                                                                fieldPanel.add(fieldLabel);
                                                                                                                                            }
                                                                                                                                            if (theFieldDisplay == true)
                                                                                                                                            {
                                                                                                                                                fieldPanel.add(spinner);
                                                                                                                                                fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                                            }
                                                                                                                                        }     else
                                                                                                                                            if (theFieldType.equalsIgnoreCase("SPINYEARFIELD"))
                                                                                                                                            {
                                                                                                                                                Calendar calendar = Calendar.getInstance();
                                                                                                                                                Date initDate = calendar.getTime();
                                                                                                                                                calendar.add(Calendar.YEAR , -90);
                                                                                                                                                Date earliestDate = calendar.getTime();
                                                                                                                                                calendar.add(Calendar.YEAR, 90);
                                                                                                                                                Date latestDate = calendar.getTime();
                                                                                                                                                SpinnerDateModel dateModel = new SpinnerDateModel(initDate,
                                                                                                                                                null,
                                                                                                                                                null,
                                                                                                                                                Calendar.YEAR);//ignored for user input
                                                                                                                                                JSpinner  spinner = new JSpinner(dateModel);
                                                                                                                                                spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy"));
                                                                                                                                                //Tweak the spinner's formatted text field.
                                                                                                                                                //ftf = getTextField(spinner);
                                                                                                                                                //if (ftf != null ) {
                                                                                                                                                //    ftf.setHorizontalAlignment(JTextField.RIGHT);
                                                                                                                                                //    ftf.setBorder(BorderFactory.createEmptyBorder(1,1,1,3));
                                                                                                                                                //}
                                                                                                                                                spinner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                                                                                                                                                spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                
                                                                                                                                                //XXX: No easy way to get to the buttons and change their border.
                                                                                                                                                //setSeasonalColor(dateModel.getDate()); //initialize color
                                                                                                                                                if (theFieldLock)
                                                                                                                                                {
                                                                                                                                                    //ftf.setEnabled(false);
                                                                                                                                                } else
                                                                                                                                                {
                                                                                                                                                    //ftf.setEnabled(true);
                                                                                                                                                }
                                                                                                                                                if (theLabelDisplay == true)
                                                                                                                                                {
                                                                                                                                                    fieldPanel.add(fieldLabel);
                                                                                                                                                }
                                                                                                                                                if (theFieldDisplay == true)
                                                                                                                                                {
                                                                                                                                                    fieldPanel.add(spinner);
                                                                                                                                                    fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                                                }
                                                                                                                                            }  else
                                                                                                                                                if (theFieldType.equalsIgnoreCase("SPIN_DATE_FIELD"))
                                                                                                                                                {
                                                                                                                                                    Calendar calendar = Calendar.getInstance();
                                                                                                                                                    Date initDate = calendar.getTime();
                                                                                                                                                    calendar.add(Calendar.DAY_OF_MONTH , -90);
                                                                                                                                                    Date earliestDate = calendar.getTime();
                                                                                                                                                    calendar.add(Calendar.DAY_OF_MONTH, 90);
                                                                                                                                                    Date latestDate = calendar.getTime();
                                                                                                                                                    SpinnerDateModel dateModel = new SpinnerDateModel(initDate,
                                                                                                                                                    null,
                                                                                                                                                    null,
                                                                                                                                                    Calendar.DAY_OF_MONTH);//ignored for user input
                                                                                                                                                    JSpinner  spinner = new JSpinner(dateModel);
                                                                                                                                                    spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
                                                                                                                                                    //Tweak the spinner's formatted text field.
                                                                                                                                                    //ftf = getTextField(spinner);
                                                                                                                                                    //if (ftf != null ) {
                                                                                                                                                    //    ftf.setHorizontalAlignment(JTextField.RIGHT);
                                                                                                                                                    //    ftf.setBorder(BorderFactory.createEmptyBorder(1,1,1,3));
                                                                                                                                                    //}
                                                                                                                                                    spinner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                                                                                                                                                    spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                    spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                    
                                                                                                                                                    //XXX: No easy way to get to the buttons and change their border.
                                                                                                                                                    //setSeasonalColor(dateModel.getDate()); //initialize color
                                                                                                                                                    if (theFieldLock)
                                                                                                                                                    {
                                                                                                                                                        //ftf.setEnabled(false);
                                                                                                                                                    } else
                                                                                                                                                    {
                                                                                                                                                        //ftf.setEnabled(true);
                                                                                                                                                    }
                                                                                                                                                    if (theLabelDisplay == true)
                                                                                                                                                    {
                                                                                                                                                        fieldPanel.add(fieldLabel);
                                                                                                                                                    }
                                                                                                                                                    if (theFieldDisplay == true)
                                                                                                                                                    {
                                                                                                                                                        fieldPanel.add(spinner);
                                                                                                                                                        fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                                                    }
                                                                                                                                                }  else
                                                                                                                                                    if (theFieldType.equalsIgnoreCase("SPINDOSAGE"))
                                                                                                                                                    {
                                                                                                                                                        String[] dosageStrings = new String[24];
                                                                                                                                                        dosageStrings[0] = "100";
                                                                                                                                                        dosageStrings[1] = "1";
                                                                                                                                                        dosageStrings[2] = "2";
                                                                                                                                                        dosageStrings[3] = "3";
                                                                                                                                                        dosageStrings[4] = "4";
                                                                                                                                                        dosageStrings[5] = "5";
                                                                                                                                                        dosageStrings[6] = "6";
                                                                                                                                                        dosageStrings[7] = "7";
                                                                                                                                                        dosageStrings[8] = "8";
                                                                                                                                                        dosageStrings[9] = "9";
                                                                                                                                                        dosageStrings[10] = "10";
                                                                                                                                                        dosageStrings[11] = "20";
                                                                                                                                                        dosageStrings[12] = "25";
                                                                                                                                                        dosageStrings[13] = "50";
                                                                                                                                                        dosageStrings[14] = "75";
                                                                                                                                                        dosageStrings[15] = "100";
                                                                                                                                                        dosageStrings[16] = "200";
                                                                                                                                                        dosageStrings[17] = "250";
                                                                                                                                                        dosageStrings[18] = "500";
                                                                                                                                                        dosageStrings[19] = "750";
                                                                                                                                                        dosageStrings[20] = "1000";
                                                                                                                                                        dosageStrings[21] = "2000";
                                                                                                                                                        dosageStrings[22] = "5000";
                                                                                                                                                        dosageStrings[23] = "10000";
                                                                                                                                                        SpinnerListModel intModel = new  CyclingSpinnerListModel(dosageStrings);
                                                                                                                                                        JSpinner spinner = new JSpinner(intModel);
                                                                                                                                                        spinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                        spinner.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                        if (theFieldLock)
                                                                                                                                                        {
                                                                                                                                                            //ftf.setEnabled(false);
                                                                                                                                                        } else
                                                                                                                                                        {
                                                                                                                                                            //ftf.setEnabled(true);
                                                                                                                                                        }
                                                                                                                                                        if (theLabelDisplay == true)
                                                                                                                                                        {
                                                                                                                                                            fieldPanel.add(fieldLabel);
                                                                                                                                                        }
                                                                                                                                                        if (theFieldDisplay == true)
                                                                                                                                                        {
                                                                                                                                                            fieldPanel.add(spinner);
                                                                                                                                                            fieldTextList.put((Object)theColumnNames[fieldIndex],(Object)spinner);
                                                                                                                                                        }
                                                                                                                                                    }    else
                                                                                                                                                    {
                                                                                                                                                        String mask = "";
                                                                                                                                                        for (int loop = 0; loop < fieldLength; loop++)
                                                                                                                                                        {
                                                                                                                                                            mask = mask + "*";
                                                                                                                                                        }
                                                                                                                                                        formatter = new  MaskFormatter(mask);
                                                                                                                                                        ftf = new JFormattedTextField(formatter);
                                                                                                                                                        ftf.setPreferredSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                        ftf.setMaximumSize(new Dimension(componentWidth,componentHeight));
                                                                                                                                                        ftf.setValue(theFieldValue);
                                                                                                                                                        if (textFieldLabelCollection == null)
                                                                                                                                                        {
                                                                                                                                                            textFieldLabelCollection = new HashMap(5);
                                                                                                                                                            textFieldLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                                                                                        } else
                                                                                                                                                        {
                                                                                                                                                            textFieldLabelCollection.put((Object)theFieldName,(Object)fieldLabel);
                                                                                                                                                        }
                                                                                                                                                        if (theFieldLock)
                                                                                                                                                        {
                                                                                                                                                            //dependanceTextField.setValue("");
                                                                                                                                                            //ftf.setEnabled(false);
                                                                                                                                                            ftf.setVisible(false);
                                                                                                                                                            fieldLabel.setVisible(false);
                                                                                                                                                            
                                                                                                                                                        } else
                                                                                                                                                        {
                                                                                                                                                            ftf.setVisible(true);
                                                                                                                                                            //ftf.setEnabled(true);
                                                                                                                                                            fieldLabel.setVisible(true);
                                                                                                                                                        }
                                                                                                                                                        if (theLabelDisplay == true)
                                                                                                                                                        {
                                                                                                                                                            fieldPanel.add(fieldLabel);
                                                                                                                                                        }
                                                                                                                                                        if (theFieldDisplay == true)
                                                                                                                                                        {
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
            int dummyComponentAmount = (int)(componentRows * componentCols) - colCount;
            for (int x = 0 ; x < dummyComponentAmount; x++)
            {
                fieldPanel.add(new JLabel());
            }
            fieldPanel.setBorder(BorderFactory.createEtchedBorder());
            
            //springGrid.makeCompactGrid(fieldPanel,componentRows,componentCols,0,0,2,2);
            
            
            SGLayout contentLayout  = new SGLayout(componentRows,componentCols,SGLayout.FILL,SGLayout.FILL,5,5);
            //contentLayout.setRowScale(1, 0.1);
            
            if ((theTableName.equalsIgnoreCase("PATIENT_COMPLAINTS") == true) || (theTableName.equalsIgnoreCase("PREVIOUS_SURGICAL_HISTORY") == true) || (theTableName.equalsIgnoreCase("PATIENT_DIAGNOSES") == true) || (theTableName.equalsIgnoreCase("PREVIOUS_MEDICAL_HISTORY") == true)  || (theTableName.equalsIgnoreCase("FAMILY_HISTORY") == true))
            {    
                contentLayout.setColumnScale(1, 1.6);
            }
                
            //contentLayout.setRowAlignment(1, SGLayout.FILL,SGLayout.TOP);
            fieldPanel.setLayout(contentLayout);
            
            
            
            dataPanel.add(fieldPanel);
            if (XMLPanel != null)
            {
                 dataPanel.add(XMLPanel);
            }
            
            //dataPanel.add(fieldPanel, BorderLayout.EAST);
            //dataPanel.add(southPanel, BorderLayout.SOUTH);
            
            return dataPanel;
        }
        public JTabbedPane getTabbedPane()
        {
            return tabbedPane;
        }
    }
    
    class ComboItemListener implements ItemListener
    {
        
        public void itemStateChanged(ItemEvent e) 
        {
            JComboBox newComboBox = (JComboBox)e.getItem();
            int x = 1;
        }
        
    }
    
    
    class MouseClickListener implements MouseListener
    {

        public void mouseClicked(MouseEvent mEvent)
        {
        }
        public void mouseEntered(MouseEvent mEvent)
        {
        }
        public void mouseExited(MouseEvent mEvent)
        {
        }
        public void mousePressed(MouseEvent mEvent)
        {
        }
        public void mouseReleased(MouseEvent mEvent)
        {
            if ( SwingUtilities.isRightMouseButton(mEvent) == true) 
            {
                try 
                {
                   
                    ArrayList synonymTableInfoArrayList = null;
                    String tableName = null;
                    JTextField textField = (JTextField)mEvent.getSource();
                    SwingUtilities.getRoot(mEvent.getComponent()).repaint();
                    ArrayList synonymList = new ArrayList(5);
                    String tfText = textField.getText();
                    if (synonymTableInfoCollection != null) {
                        Set synonymTableInfoKeySet = synonymTableInfoCollection.keySet();
                        Iterator synonymTableInfoIter = synonymTableInfoKeySet.iterator();
                        while (synonymTableInfoIter.hasNext()) {
                            tableName = (String)synonymTableInfoIter.next();
                            synonymTableInfoArrayList = (ArrayList)synonymTableInfoCollection.get((Object)tableName);
                            break;
                        }
                        MTBusinessModel synonymModel = new MTBusinessModel(tfText,synonymTableInfoArrayList,InfoManager.OS_VERSION,theDataModel.getUserName(),theDataModel.getPassword());
                        synonymList = synonymModel.getSynonymNameList();
                        
                        //inner class to build up popup-menu with jlist item of synonyms.
                        class PopUpSynonymChooser extends JPanel
                        implements ActionListener {
                            
                            public PopUpSynonymChooser(ArrayList synonymArrrayList) 
                            {
                                this.add(makeSynonymList(synonymArrrayList.toArray()));
                                setVisible(true);
                            }
                            
                            public void actionPerformed(ActionEvent e) 
                            {
                                String color = e.getActionCommand(  );

                            }
                            
                            class SynonymListListener implements ListSelectionListener {
                                public void valueChanged(ListSelectionEvent e) {
                                    try {
                                        if (e.getValueIsAdjusting()) return;
                                        JList tsl = (JList)e.getSource();
                                        if (tsl.isSelectionEmpty()) {
                                        } else {
                                            String selectedSynValue = (String)tsl.getModel().getElementAt(tsl.getMinSelectionIndex());
                                            synonymSourceComboBox.setSelectedItem((Object)selectedSynValue);
                                            theDataView.getTabbedPane().remove(synonymListFrame);
                                        }
                                    }
                                    catch (Exception exc) {
                                        
                                    }
                                    
                                }
                            }

                            
                            
                            private JList makeSynonymList(Object[] synonymStringArray)
                            {
                                
                                Object[] tmpList  = {"None Found."};
                                JList synonymJList;
                                if (synonymStringArray.length == 0)
                                {
                                    synonymJList = new JList(tmpList);
                                    synonymJList.setEnabled(false);
                                } else
                                {
                                     synonymJList = new JList(synonymStringArray);
                                     synonymJList.addListSelectionListener(new SynonymListListener());
                                }
                                synonymJList.setBorder(compoundBorder);
                                synonymJList.setBackground(new Color(180,180,250));
                                synonymJList.setToolTipText("Click on any item in the list to replace item selected in combobox with clicked item.");
                                return synonymJList;
                            }
                            
                        }
                        if (synonymList.size() > 0)
                        {    
                            synonymListFrame = new JInternalFrame(tfText,
                            false, //resizable
                            true, //closable
                            false, //maximizable
                            false); //iconifiable
                            synonymListFrame.setSize(new Dimension(300,300));
                            //synonymListFrame.setLocation(200, 200);
                            synonymListFrame.setVisible(true);
                            //synonymListFrame.addInternalFrameListener(this);
                            JPanel synonymPanel = new PopUpSynonymChooser(synonymList);
                            //synonymPanel.setBorder(BorderFactory.createEtchedBorder());
                            JScrollPane synonymListScrollPane = new JScrollPane(synonymPanel);
                            //synonymListScrollPane.setBorder(compoundBorder);
                            synonymListScrollPane.setWheelScrollingEnabled(true);
                            //synonymListScrollPane.setLayout(new BorderLayout());
                            synonymListFrame.getContentPane().add(new JLabel("<html><h2><font color=\"blue\">Synonym List</font></h2></html>",JLabel.CENTER),BorderLayout.NORTH);
                            synonymListFrame.getContentPane().add(synonymListScrollPane,BorderLayout.CENTER);
                            //tabbedPane.add(tfText, new PopUpSynonymChooser(synonymList));
                            theDataView.getTabbedPane().add(synonymListFrame,0);
                            theDataView.getTabbedPane().setTitleAt(0,tfText);
                            theDataView.getTabbedPane().setToolTipTextAt(0,"Press this tab to view the synonym list.");
                        } else
                            JOptionPane.showMessageDialog(theDataView, "No synonyms for this selection." , "Synonym list for " + tfText, JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                catch (Exception e) {
                    //throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
                }
                
            }
            
            
        }//endif right mouse button
            
   }
    
    class TheMouseListener extends MouseInputAdapter implements InternalFrameListener
    {
       String cellReference = "";
        public void mouseReleased(MouseEvent mEvent)
        {
            if ( (SwingUtilities.isLeftMouseButton(mEvent)) && (textFrame == false))
            {
                String tableName = "";
                String fieldName = "";
                String theToken = "";
                String fieldIndex = "";
                cellReference = ((JButton)mEvent.getComponent()).getToolTipText();
                int x = theDataView.getComponentCount();
                JInternalFrame notesFrame = new MyInternalFrame(x, cellReference);
                notesFrame.addInternalFrameListener(new NotesFrameListener());
                theDataView.getTabbedPane().add(notesFrame,0);
                theDataView.getTabbedPane().setTitleAt(0,"Notes");
                theDataView.getTabbedPane().setToolTipTextAt(0,"This is the Notes selection Tab");
                textFrame = true;
                changeFocus(textArea,textArea);
                theDataView.getTabbedPane().setSelectedIndex(0);
            } else
            if ( SwingUtilities.isRightMouseButton(mEvent)  == true)
            {
                cellReference = ((JButton)mEvent.getComponent()).getToolTipText();
            }
        }
        
        
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameClosed(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
            textFrame = false;
        }
        
        public void internalFrameClosing(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
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
    
   class NotesFrameListener implements InternalFrameListener
   {
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
        }
        
        public void internalFrameClosed(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
            textFrame = false;
        }
        
        public void internalFrameClosing(javax.swing.event.InternalFrameEvent internalFrameEvent)
        {
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

   private void changeFocus(final Component source,
        final Component target) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          target.dispatchEvent(
            new FocusEvent(source, FocusEvent.FOCUS_GAINED));
          textArea.requestFocus();
        }
      });
    }
    
   private class MyInternalFrame extends JInternalFrame {
       public MyInternalFrame(int x, String cReference) {
           
           String tableName = "";
           String fieldName = "";
           String theToken = "";
           String fieldIndex = "";
           StringTokenizer fieldTokens = new StringTokenizer(cReference,",");
           int tokenCount = fieldTokens.countTokens();
           for (int cellReferenceIndex = 0; cellReferenceIndex <  tokenCount; cellReferenceIndex++) {
               theToken = (String)fieldTokens.nextToken();
               if (cellReferenceIndex == 0) {
                   tableName = theToken;
               } else
                   if (cellReferenceIndex == 1) {
                       fieldName = theToken;
                   } else {
                       fieldIndex = theToken;
                   }
           }
           String frameHeading = fieldName.replace('_',' ');
           
           setSize(new Dimension(300,300));
           setLocation(200, 200);
           setBounds(x * 25, x * 25, getWidth(), getHeight());
           setVisible(true);
           setIconifiable(false);
           setMaximizable(false);
           setResizable(false);
           setClosable(true);
           setTitle(frameHeading);
           HashMap fieldMap = (HashMap)(theDataView.tablesTextList.get((Object)tableName));
           textArea = (JTextArea)fieldMap.get((Object)fieldName);
           areaScrollPane = new JScrollPane(textArea);
           getContentPane().add(areaScrollPane);

           //frameHeading = "-DOCS"; //used for test of docscan
           
           
           if ((frameHeading.indexOf("DOCS") > 0) || (frameHeading.indexOf("REFERENCE") > 0)) {
               addFileButton = new JButton("Add File");
               scanDocButton = new JButton("Scan Document");
               JPanel buttonPane = new JPanel();
               buttonPane.add(addFileButton);
               if (InfoManager.OS_VERSION.equalsIgnoreCase("WINXP")) {
                   buttonPane.add(scanDocButton);
               }
               addFileButton.addActionListener(new ActionListener() {
                   public void actionPerformed(ActionEvent e) {
                       JFileChooser fileChooser = new JFileChooser(docScanPath);
                       FileExtensionFilter filter = new FileExtensionFilter();
                       filter.add("gif");
                       filter.add("jpg");
                       filter.setDescription("GIF & JPG Images");
                       fileChooser.setFileFilter(filter);
                       int returnOption = fileChooser.showOpenDialog(theDataView);
                       if (returnOption == JFileChooser.APPROVE_OPTION) {
                           String fileName = fileChooser.getSelectedFile().getName();
                           textArea.append("\n" + fileName);
                       }
                       textArea.repaint();
                   }
               });
               
               if (InfoManager.OS_VERSION.equalsIgnoreCase("WINXP")) {
                   scanDocButton.addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) {
                           String fileIndex = "";
                           iniFilePresent = config.readIni(docScanPath + "docscan.ini");
                           if (iniFilePresent) {
                               JOptionPane optionPane = new JOptionPane();
                               int result = optionPane.showConfirmDialog(theDataView,"Put document in scanner and press\nOK when ready to scan else press Cancel.","Scanner Confirmation.",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                               if (result == JOptionPane.OK_OPTION) {
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
                               
                           } else {
                               JOptionPane optionPane = new JOptionPane();
                               optionPane.showMessageDialog(theDataView,"Required docscan.ini file is missing!");
                           }
                       }
                   });
               }
               getContentPane().add(buttonPane,BorderLayout.SOUTH); 
           }
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
                    if (theTabIconName != null)
                    {
                       theDataView = new DataView(editViewType, editTabPlacement,theTabIconName, theDataModel, editHeight, editWidth);
                    } else
                    {
                        theDataView = new DataView(editViewType, editTabPlacement, theDataModel, editHeight, editWidth);
                    }
                        
                    removeAll();
                    add(theDataView);
                    CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
                    AddInfoMVCApp.updateUI();
                }
            }
            catch (MTException exc)
            {
                JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
    
    class CBKeyListener implements KeyListener
    {
        public JComboBox getActiveComboBox(Object eventSource)
        {
            JComboBox cbBox = null;
            String fieldName = null;
            String autoSelectField = null;
            boolean comboBoxFound = false;
            
            Set autoSelectFieldKeySet = autoSelectFieldCollection.keySet();
            Iterator autoSelectFieldIter = autoSelectFieldKeySet.iterator();
            while (autoSelectFieldIter.hasNext())
            {
                autoSelectField = (String)autoSelectFieldIter.next();
                autoSelectFieldType = (String)autoSelectFieldCollection.get((Object)autoSelectField);
                
                
                Set dataViewKeySet = theDataView.tablesTextList.keySet();
                Iterator tablesViewIter = dataViewKeySet.iterator();
                while (tablesViewIter.hasNext())
                {
                    Set autoSelectComboFieldKeySet = null;
                    String tableNameOfView = (String)tablesViewIter.next();
                    if (autoSelectFieldType.equalsIgnoreCase("GROUP") == true)
                    {
                        autoSelectComboFieldKeySet = groupedTableDataCollection.keySet();
                    } else
                    {
                        autoSelectComboFieldKeySet = lookupTableDataCollection.keySet();
                    }
                    
                    Iterator autoSelectComboFieldIter = autoSelectComboFieldKeySet.iterator();
                    while (autoSelectComboFieldIter.hasNext())
                    {
                        fieldName = (String)autoSelectComboFieldIter.next();
                        if (fieldName.equalsIgnoreCase(autoSelectField) == true)
                        {
                            HashMap fieldList = (HashMap)theDataView.tablesTextList.get(tableNameOfView);
                            ArrayList fieldArrayList = null;
                            fieldArrayList = (ArrayList)fieldList.get((Object)fieldName);
                            if (fieldArrayList != null)
                            {
                                cbBox = (JComboBox)fieldArrayList.get(0);
                            } else
                            {
                                cbBox = (JComboBox)fieldList.get((Object)fieldName);
                            }
                            if (cbBox != null)
                            {
                                if (eventSource == cbBox.getEditor().getEditorComponent())
                                {
                                    comboBoxFound =  true;
                                    break;
                                }
                            }
                        }
                    }
                    if (comboBoxFound ==  true) break;
                }
                if (comboBoxFound ==  true) break;
            }
            
            if (comboBoxFound == true)
            {
                return cbBox;
            } else
            {
                return null;
            }
        }
        
        public void keyPressed(KeyEvent event)
        {
            JComboBox sourceComboBox = getActiveComboBox(event.getSource());
            if (autoSelectFieldType.equalsIgnoreCase("GROUP") == true)
            {
                if (event.getSource() == sourceComboBox.getEditor().getEditorComponent())
                {
                    if (event.getKeyText(event.getKeyCode()).compareTo("Backspace") == 0)
                    {
                        if (groupKeyPressBufferLength > 0)
                        {
                            groupKeyPressBufferLength = groupKeyPressBufferLength - 1;
                            groupKeyPressBuffer.deleteCharAt(groupKeyPressBuffer.length()-1);
                            sourceComboBox.removeAllItems();
                            for (int itemListLoop = 0; itemListLoop < groupTempCBItemList.size(); itemListLoop++)
                            {
                                String itemName = (String)groupTempCBItemList.get(itemListLoop);
                                if (groupKeyPressBufferLength > 0)
                                {
                                    if(itemName.length() >= groupKeyPressBufferLength)
                                    {
                                        if (groupKeyPressBuffer.substring(0,groupKeyPressBufferLength).equalsIgnoreCase(itemName.substring(0,groupKeyPressBufferLength)))
                                        {
                                            sourceComboBox.addItem(itemName);
                                        }
                                    }
                                } else
                                {
                                    sourceComboBox.addItem(itemName);
                                }
                            }
                            if (sourceComboBox.getItemCount() > 0)
                            {
                                sourceComboBox.setPopupVisible(true);
                                sourceComboBox.setSelectedIndex(0);
                            } else
                            {
                                sourceComboBox.setPopupVisible(true);
                            }
                        } else
                        {
                            if (groupFirstKeyPressPass == false)
                            {
                                sourceComboBox.removeAllItems();
                                for (int itemListLoop = 0; itemListLoop < groupTempCBItemList.size(); itemListLoop++)
                                {
                                    String itemName = (String)groupTempCBItemList.get(itemListLoop);
                                    sourceComboBox.addItem(itemName);
                                }
                            }
                            groupKeyPressBuffer.setLength(0);
                            groupFirstKeyPressPass = true;
                            
                        }
                    }  else//endif backspace
                        if (event.getKeyText(event.getKeyCode()).compareTo("Home") == 0)
                        {
                            sourceComboBox.removeAllItems();
                            for (int itemListLoop = 0; itemListLoop < groupTempCBItemList.size(); itemListLoop++)
                            {
                                String itemName = (String)groupTempCBItemList.get(itemListLoop);
                                sourceComboBox.addItem(itemName);
                            }
                            int itemCount = sourceComboBox.getItemCount();
                            if (itemCount > 0)
                            {
                                sourceComboBox.setSelectedIndex(0);
                                sourceComboBox.setPopupVisible(true);
                            }
                            else
                            {
                                sourceComboBox.setPopupVisible(false);
                            }
                            
                            groupKeyPressBuffer.delete(0,groupKeyPressBuffer.length());
                            groupKeyPressBuffer.setLength(0);
                            groupKeyPressBufferLength = 0;
                            groupFirstKeyPressPass = true;
                            
                        }
                        else
                        {
                            if (groupFirstKeyPressPass == true)
                            {
                                //make a copy of  the combobox data into groupTempCBItemList
                                groupTempCBItemList.clear();
                                for (int loop = 0; loop < sourceComboBox.getItemCount(); loop++)
                                {
                                    groupTempCBItemList.add((Object)sourceComboBox.getItemAt(loop));
                                }
                                groupFirstKeyPressPass = false;
                            }
                            groupKeyPressBufferLength++;
                            groupKeyPressBuffer.append(event.getKeyChar());
                            sourceComboBox.removeAllItems();
                            for (int itemListLoop = 0; itemListLoop < groupTempCBItemList.size(); itemListLoop++)
                            {
                                String itemName = (String)groupTempCBItemList.get(itemListLoop);
                                if(itemName.length() >= groupKeyPressBufferLength)
                                {
                                    if (groupKeyPressBuffer.substring(0,groupKeyPressBufferLength).equalsIgnoreCase(itemName.substring(0,groupKeyPressBufferLength)))
                                    {
                                        sourceComboBox.addItem(itemName);
                                    }
                                }
                            }
                            int itemCount = sourceComboBox.getItemCount();
                            if (itemCount > 0)
                            {
                                sourceComboBox.setSelectedIndex(0);
                                sourceComboBox.setPopupVisible(true);
                            }
                            else
                            {
                                sourceComboBox.setPopupVisible(false);
                            }
                        }
                }
            } else
            {
                //Execute this if not a group combobox. Change later as this code can only handle 1 additional combo-box.
                if (event.getSource() == sourceComboBox.getEditor().getEditorComponent())
                {
                    if (event.getKeyText(event.getKeyCode()).compareTo("Backspace") == 0)
                    {
                        if (keyPressBufferLength > 0)
                        {
                            keyPressBufferLength = keyPressBufferLength - 1;
                            keyPressBuffer.deleteCharAt(keyPressBuffer.length()-1);
                            sourceComboBox.removeAllItems();
                            for (int itemListLoop = 0; itemListLoop < tempCBItemList.size(); itemListLoop++)
                            {
                                String itemName = (String)tempCBItemList.get(itemListLoop);
                                if (keyPressBufferLength > 0)
                                {
                                    if(itemName.length() >= keyPressBufferLength)
                                    {
                                        if (keyPressBuffer.substring(0,keyPressBufferLength).equalsIgnoreCase(itemName.substring(0,keyPressBufferLength)))
                                        {
                                            sourceComboBox.addItem(itemName);
                                        }
                                    }
                                } else
                                {
                                    sourceComboBox.addItem(itemName);
                                }
                            }
                            if (sourceComboBox.getItemCount() > 0)
                            {
                                sourceComboBox.setPopupVisible(true);
                                sourceComboBox.setSelectedIndex(0);
                            } else
                            {
                                sourceComboBox.setPopupVisible(true);
                            }
                        } else
                        {
                            if (firstKeyPressPass == false)
                            {
                                sourceComboBox.removeAllItems();
                                for (int itemListLoop = 0; itemListLoop < tempCBItemList.size(); itemListLoop++)
                                {
                                    String itemName = (String)tempCBItemList.get(itemListLoop);
                                    sourceComboBox.addItem(itemName);
                                }
                            }
                            keyPressBuffer.setLength(0);
                            firstKeyPressPass = true;
                            
                        }
                    }  else//endif backspace
                        if (event.getKeyText(event.getKeyCode()).compareTo("Home") == 0)
                        {
                            sourceComboBox.removeAllItems();
                            for (int itemListLoop = 0; itemListLoop < tempCBItemList.size(); itemListLoop++)
                            {
                                String itemName = (String)tempCBItemList.get(itemListLoop);
                                sourceComboBox.addItem(itemName);
                            }
                            int itemCount = sourceComboBox.getItemCount();
                            if (itemCount > 0)
                            {
                                sourceComboBox.setSelectedIndex(0);
                                sourceComboBox.setPopupVisible(true);
                            }
                            else
                            {
                                sourceComboBox.setPopupVisible(false);
                            }
                            
                            keyPressBuffer.delete(0,keyPressBuffer.length());
                            keyPressBuffer.setLength(0);
                            keyPressBufferLength = 0;
                            firstKeyPressPass = true;
                            
                        }
                        else
                        {
                            if (firstKeyPressPass == true)
                            {
                                //make a copy of  the combobox data into groupTempCBItemList
                                tempCBItemList.clear();
                                for (int loop = 0; loop < sourceComboBox.getItemCount(); loop++)
                                {
                                    tempCBItemList.add((Object)sourceComboBox.getItemAt(loop));
                                }
                                firstKeyPressPass = false;
                            }
                            keyPressBufferLength++;
                            keyPressBuffer.append(event.getKeyChar());
                            sourceComboBox.removeAllItems();
                            for (int itemListLoop = 0; itemListLoop < tempCBItemList.size(); itemListLoop++)
                            {
                                String itemName = (String)tempCBItemList.get(itemListLoop);
                                if(itemName.length() >= keyPressBufferLength)
                                {
                                    if (keyPressBuffer.substring(0,keyPressBufferLength).equalsIgnoreCase(itemName.substring(0,keyPressBufferLength)))
                                    {
                                        sourceComboBox.addItem(itemName);
                                    }
                                }
                            }
                            int itemCount = sourceComboBox.getItemCount();
                            if (itemCount > 0)
                            {
                                sourceComboBox.setSelectedIndex(0);
                                sourceComboBox.setPopupVisible(true);
                            }
                            else
                            {
                                sourceComboBox.setPopupVisible(false);
                            }
                        }
                }
            }// end autoselect field type
        }
        public void keyReleased(java.awt.event.KeyEvent keyEvent)
        {
        }
        
        public void keyTyped(java.awt.event.KeyEvent keyEvent)
        {
        }
        
    }
    
    class CBItemListener implements ItemListener
    {
        
        
        public String getActiveBoxFieldName(ItemEvent event)
        {
            JComboBox sourceComboBox = (JComboBox)event.getSource();
            synonymSourceComboBox = (JComboBox)event.getSource();
            JComboBox cbBox = null;
            String fieldName = null;
            boolean comboBoxFound = false;
            Set dataViewKeySet = theDataView.tablesTextList.keySet();
            Iterator tablesViewIter = dataViewKeySet.iterator();
            while (tablesViewIter.hasNext())
            {
                String tableNameOfView = (String)tablesViewIter.next();
                Set autoSelectComboFieldKeySet = null;
                if (groupedTableDataCollection != null)
                {
                    autoSelectComboFieldKeySet = groupedTableDataCollection.keySet();
                    if (autoSelectComboFieldKeySet.isEmpty())
                    {
                        autoSelectComboFieldKeySet = lookupTableDataCollection.keySet();
                    }
                } else
                {
                    autoSelectComboFieldKeySet = lookupTableDataCollection.keySet();
                }
                
                Iterator autoSelectComboFieldIter = autoSelectComboFieldKeySet.iterator();
                while (autoSelectComboFieldIter.hasNext())
                {
                    fieldName = (String)autoSelectComboFieldIter.next();
                    //dependantItem = (String)dependanceTableDataCollection.get(fieldName);
                    
                    HashMap fieldList = (HashMap)theDataView.tablesTextList.get(tableNameOfView);
                    ArrayList fieldArrayList = null;
                    fieldArrayList = (ArrayList)fieldList.get((Object)fieldName);
                    if (fieldArrayList != null)
                    {
                        cbBox = (JComboBox)fieldArrayList.get(0);
                    } else
                    {
                        cbBox = (JComboBox)fieldList.get((Object)fieldName);
                    }
                    if (cbBox != null)
                    {
                        if (sourceComboBox == cbBox)
                        {
                            comboBoxFound = true;
                            break;
                        }
                    }
                }
                if (comboBoxFound ==  true) break;
            }
            if (comboBoxFound == true)
            {
                return fieldName;
            } else
            {
                return null;
            }
        }
        
        
        public void itemStateChanged(ItemEvent event)
        {
            String fieldItem = getActiveBoxFieldName(event);
            if (fieldItem ==  null) return;
            JComboBox sourceComboBox = (JComboBox)event.getSource();
            if ((event.getStateChange() == ItemEvent.SELECTED) && (autoSelectFieldCollection.get((Object)fieldItem) != null))
            {
                String cboBoxVal = (String)sourceComboBox.getSelectedItem();
                cboBoxVal = cboBoxVal.toUpperCase();
                
               /*if (perNameTree.contains(cboBoxVal) == true)
               {
                   personId.removeAllItems();
                   docList.removeAllItems();
                   propertyDesc.removeAllItems();
                   getIdsFromPerName();
                
                   per1   = (Owners)persons.get((Object)personId.getSelectedItem());
                   getPropertiesFromPersonId();
                   getDataFromProperty() ;
                   if (personName.getSelectedItem() == null)
                   {
                       personName.addItem(per1.getPerName());
                   }
               }
               else if (perNameTree.contains(personName.getSelectedItem()) == false)
               {
                   docList.removeAllItems();
                   propertyDesc.removeAllItems();
               }*/
            }
            
        }
    }
    
    class CBActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            boolean keyFound = false;
            boolean groupKeyFound = false;
            String selectedKey = null;
            String selectedGroupKey = null;
            String theCollectionKey = null;
            String comboCollectionKey = null;
            String theLookupCollectionKey = null;
            int arrayIndex = 0;
            String theGroupKey = null;
            String groupLinkTo = null;
            String dependantItem = null;
            ArrayList dependantItems = null;
            boolean groupFieldFound = false;
            String theTableName = null;
            HashMap theFieldList = null;
            ArrayList theFieldArrayList = null;
            boolean selectedFieldFound = false;
            String selectedFieldName = null;
            String inTableName = null;
            HashMap inFieldList = null;
            String selectedColumnType = null;
            String[] tmpStringArray = null;
            String inactiveCBBoxState = null;
            String activeCBBoxState = null;
            JFormattedTextField dependanceTextField = null;
            JComboBox collectionComboItem = null;
            JLabel collectionComboLabelItem = null;
            
            
            String selectedItem = (String)((JComboBox)e.getSource()).getSelectedItem();
            JComboBox sourceComboBox = (JComboBox)e.getSource();
            selectedFieldFound = false;
            keyFound = false;
            groupKeyFound = false;
            String thePrevKey = null;
            String theKey = null;
            String thePrevVal = null;
            String theValue = null;
            String theNextKey = null;
            String theNextValue = null;
            String activeMapValue = null;
            String activeState = null;
            String stateAndMap = null;
            //Find fieldname for this combobox item.
            try
            {
            if (comboItemCollection != null)
            {
                Set comboItemCollectionKeySet = comboItemCollection.keySet();
                Iterator comboItemCollectionIter = comboItemCollectionKeySet.iterator();
                while (comboItemCollectionIter.hasNext())
                {
                    comboCollectionKey = (String)comboItemCollectionIter.next();
                    collectionComboItem = (JComboBox)comboItemCollection.get((Object)comboCollectionKey);
                    if (sourceComboBox == collectionComboItem)
                    {
                        selectedKey = comboCollectionKey;
                        keyFound = true;
                        selectedFieldName = comboCollectionKey;
                        selectedFieldFound = true;
                        collectionComboLabelItem = (JLabel)comboLabelCollection.get((Object)comboCollectionKey);
                        //utilsInstance.logSQLData("3861 Seleted Combo key = " + comboCollectionKey,"WINXP");
                        break;
                    }
                }
            }
            
            // Find field Name if not a standard combobox
            if (selectedFieldFound == false)
            {
                Set tableSet = theDataView.tablesTextList.keySet();
                Iterator tabIter = tableSet.iterator();
                while (tabIter.hasNext())
                {
                    inTableName = (String)tabIter.next();
                    inFieldList = (HashMap)theDataView.tablesTextList.get(inTableName);
                    
                    Set fieldKeySet = inFieldList.keySet();
                    Iterator fieldIter = fieldKeySet.iterator();
                    while (fieldIter.hasNext())
                    {
                        String theFieldName = (String)fieldIter.next();
                        
                        for (int fieldIndex = 0; fieldIndex < theColumnNames.length; fieldIndex++)
                        {
                            String storedFieldName = theColumnNames[fieldIndex];
                            if (theFieldName.equalsIgnoreCase(storedFieldName) == true)
                            {
                                selectedColumnType = theColumnTypes[fieldIndex];
                        //utilsInstance.logSQLData("3892 Selected Column Type = " + selectedColumnType,"WINXP");
                                break;
                            }
                        }
                        if ((selectedColumnType.equalsIgnoreCase("GROUP") == true) || (selectedColumnType.equalsIgnoreCase("GROUPKEY") == true) || (selectedColumnType.equalsIgnoreCase("FOREIGN") == true))
                        {
                            theFieldArrayList = (ArrayList)inFieldList.get((Object)theFieldName);
                            if (theFieldArrayList != null)
                            {
                                JComboBox cbBox = (JComboBox)theFieldArrayList.get(0);
                                if (cbBox == sourceComboBox)
                                {
                                    selectedFieldName = theFieldName;
                                    selectedFieldFound = true;
                        //utilsInstance.logSQLData("3906 selectedFieldName = " + selectedFieldName,"WINXP");
                                    break;
                                }
                            }
                        } else
                            if ((selectedColumnType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD") == true) || (selectedColumnType.equalsIgnoreCase("BOOLEAN_FIELD") == true) || (selectedColumnType.equalsIgnoreCase("BOOLEAN_GROUP") == true))
                            {
                                JComboBox cbBox = (JComboBox)inFieldList.get((Object)theFieldName);
                                if (cbBox == sourceComboBox)
                                {
                                    selectedFieldName = theFieldName;
                                    selectedFieldFound = true;
                                    break;
                                }
                                
                            }
                    }
                    if (selectedFieldFound == true) break;
                }
            } //end if selectedfieldfound = false
            
            
            //Check if group combobox selected item changed
            if (groupLookupTableCollection != null)
            {
                Set groupLookupKeySetCollection = groupLookupTableCollection.keySet();
                Iterator groupLookupIter = groupLookupKeySetCollection.iterator();
                groupKeyFound = false;
                while (groupLookupIter.hasNext())
                {
                    theGroupKey = (String)groupLookupIter.next();
                    HashMap groupLookupTableData = (HashMap)groupLookupTableCollection.get((Object)theGroupKey);
                    Set groupLookupKeySet = groupLookupTableData.keySet();
                    Iterator lookupListIter = groupLookupKeySet.iterator();
                    while (lookupListIter.hasNext())
                    {
                        theKey = (String)lookupListIter.next();
                        theValue = (String)groupLookupTableData.get((Object)theKey);
                        if (theValue.equalsIgnoreCase(selectedItem))
                        {
                            selectedGroupKey = theKey;
                            HashMap groupCrossLinkCollection = (HashMap)theDataModel.getGroupTableCrossLinks();
                            groupLinkTo = (String)groupCrossLinkCollection.get((Object)theGroupKey);
                        //utilsInstance.logSQLData("3944 theKey = " + theKey,"WINXP");
                        //utilsInstance.logSQLData("3945 theValue = " + theValue,"WINXP");
                        //utilsInstance.logSQLData("3952 groupLinkTo = " + groupLinkTo,"WINXP");
                            theCollectionKey = groupLinkTo;
                            groupKeyFound = true;
                            break;
                        }
                    }
                    if (groupKeyFound == true) break;
                }
            }
            if (groupKeyFound == true)
            {
                //HashMap groupCrossLinkCollection = (HashMap)theDataModel.getGroupTableCrossLinks();
                //String groupKey = (String)groupCrossLinkCollection.get((Object)theGroupKey);
                //HashMap tempgroupData = (HashMap)groupedTableDataCollection.get((Object)groupKey);
                HashMap myGroupDataSubset = (HashMap)groupData.get((Object)selectedGroupKey);
                
                groupFieldFound = false;
                String tableNameForGroup = null;
                HashMap groupFieldList = null;
                Set groupTableKeySet = theDataView.tablesTextList.keySet();
                Iterator groupTableIter = groupTableKeySet.iterator();
                while (groupTableIter.hasNext())
                {
                    tableNameForGroup = (String)groupTableIter.next();
                        //utilsInstance.logSQLData("3975 tableNameForGroup = " + tableNameForGroup,"WINXP");
                    groupFieldList = (HashMap)theDataView.tablesTextList.get(tableNameForGroup);
                    ArrayList groupFieldArrayList = null;
                    groupFieldArrayList = (ArrayList)groupFieldList.get((Object)groupLinkTo);
                    if (groupFieldArrayList != null)
                    {
                        JComboBox cbBox = (JComboBox)groupFieldArrayList.get(0);
                        //cbBox.removeActionListener(this);
                        cbBox.removeAllItems();
                        groupFirstKeyPressPass = true;
                        groupKeyPressBufferLength = 0;
                        groupKeyPressBuffer.setLength(0);
                        groupTempCBItemList.clear();
                        if (myGroupDataSubset != null)
                        {
                            Set comboBoxDataKeySet = myGroupDataSubset.keySet();
                            Iterator comboBoxIter = comboBoxDataKeySet.iterator();
                            int subsetArrayIndex = 0;
                            tmpStringArray = new String[comboBoxDataKeySet.size()];
                            while (comboBoxIter.hasNext())
                            {
                                String comboBoxKey = (String)comboBoxIter.next();
                                String comboBoxValue = (String)myGroupDataSubset.get((Object)comboBoxKey);
                                tmpStringArray[subsetArrayIndex] = comboBoxValue;
                                subsetArrayIndex++;
                            }
                            theMTUtils.quickSort(tmpStringArray,0,tmpStringArray.length - 1);
                            for (int loop = 0; loop < tmpStringArray.length; loop++)
                            {
                                cbBox.addItem((Object)tmpStringArray[loop]);
                            }
                        }
                        
                        groupFieldFound = true;
                        //cbBox.addActionListener(new CBActionListener());
                        
                        cbBox.repaint();
                    }
                    if (groupFieldFound == true) break;
                }
            }
            
            
            
            //selectedFieldName
            if ((comboBoxInactiveStateCollection != null) && (selectedFieldName != null))
            {
                inactiveCBBoxState = (String)comboBoxInactiveStateCollection.get((Object)selectedFieldName);
            }
            if ((comboBoxActiveStateCollection != null) && (selectedFieldName != null))
            {
                activeCBBoxState = (String)comboBoxActiveStateCollection.get((Object)selectedFieldName);
            }
            if ((comboBoxInactiveStateCollection == null)  && (comboBoxActiveStateCollection == null))
            {
                activeCBBoxState = "Other";
                inactiveCBBoxState = "No";
            }
            
            //find location and index of group combobox in dataview and update state of dependancies if group value selected is equal to "Other".
            String selectedGroupItem = (String)((JComboBox)e.getSource()).getSelectedItem();
                        //utilsInstance.logSQLData("4037 selectedGroupItem = " + selectedGroupItem,"WINXP");
            if (inactiveCBBoxState == null)
            {
                inactiveCBBoxState = selectedGroupItem;
            }
            if (activeCBBoxState == null)
            {
                activeCBBoxState = selectedGroupItem + "aa";
            }
            if ((dependanceTableDataCollection != null ) && (selectedGroupItem != null) &&   ((selectedGroupItem.equalsIgnoreCase(inactiveCBBoxState) == false) ||  (selectedGroupItem.equalsIgnoreCase(activeCBBoxState) == true)))
            {
                //find group dependancies
                if (theCollectionKey != null)
                {
                    dependantItems = (ArrayList)dependanceTableDataCollection.get(theCollectionKey);
                } else
                {
                    //This was added to get the dependant column item.
                    boolean comboBoxFound = false;
                    JComboBox cbBox = null;
                    JComboBox dependanceComboBox = null;
                    String dependanceItemType = null;
                    Set dataViewKeySet = theDataView.tablesTextList.keySet();
                    Iterator tablesViewIter = dataViewKeySet.iterator();
                    while (tablesViewIter.hasNext())
                    {
                        String tableNameOfView = (String)tablesViewIter.next();
                        if (selectedFieldFound == true)
                        {
                            Set dependantFieldKeySet = dependanceTableDataCollection.keySet();
                            Iterator dependantFieldIter = dependantFieldKeySet.iterator();
                            while (dependantFieldIter.hasNext())
                            {
                                String dependantFieldName = (String)dependantFieldIter.next();
                        //utilsInstance.logSQLData("4072 dependantFieldName = " + dependantFieldName,"WINXP");
                                dependantItems = (ArrayList)dependanceTableDataCollection.get(dependantFieldName);
                                if (dependantFieldName.equalsIgnoreCase(selectedFieldName) == true)
                                {
                                    for (int dependantItemLoop = 0; dependantItemLoop < dependantItems.size(); dependantItemLoop++)
                                    {
                                        
                                        dependantItem =  (String)dependantItems.get(dependantItemLoop);
                        //utilsInstance.logSQLData("4080 dependantItem = " + dependantItem,"WINXP");
                                        if (dependantItemActivityMap != null)
                                        {
                                            stateAndMap = (String)dependantItemActivityMap.get((Object)dependantItem);
                                            if (stateAndMap != null)
                                            {
                                               StringTokenizer mapTokens = new StringTokenizer(stateAndMap,",");
                                               activeState = (String)mapTokens.nextToken();
                                               activeMapValue = (String)mapTokens.nextToken();
                        //utilsInstance.logSQLData("4080 activeState = " + activeState,"WINXP");
                        //utilsInstance.logSQLData("4080 activeMapValue = " + activeMapValue,"WINXP");
                                            } else
                                            {
                                               activeState = "Active";
                                               activeMapValue = selectedGroupItem;
                        //utilsInstance.logSQLData("4094 activeMapValue = " + activeMapValue,"WINXP");
                                            }
 
                                        } else
                                        {
                                            activeState = "Active";
                                            activeMapValue = selectedGroupItem;
                        //utilsInstance.logSQLData("4102 activeMapValue = " + activeMapValue,"WINXP");
                                        }
                                        //this is to try and switch off 'other' when  more than one visible
                                        if ((activeState.equalsIgnoreCase("Active") == true) && (activeMapValue.equalsIgnoreCase("Other") == true) && (dependantItem.equalsIgnoreCase("Other") == false))
                                        {
                                            if (dependanceItemTypeCollection != null)
                                            {
                                                dependanceItemType = (String)dependanceItemTypeCollection.get(dependantItem);
                       //utilsInstance.logSQLData("4110 dependanceItemType = " + dependanceItemType,"WINXP");
                                             }
                                            if ((dependanceItemType != null) && (dependanceItemType.equalsIgnoreCase("TEXTFIELD")))
                                            {
                                                //cbBox = (JComboBox)fieldList.get((Object)dependantItem);
                                                HashMap fieldList = (HashMap)theDataView.tablesTextList.get(tableNameOfView);
                                                dependanceTextField = (JFormattedTextField)fieldList.get((Object)dependantItem);
                      //utilsInstance.logSQLData("4110 dependanceTextField = " + dependanceTextField,"WINXP");
                                                 if (dependanceTextField != null)
                                                {
                                                    comboBoxFound = true;
                                                    dependanceTextField.setValue("");
                                                    dependanceTextField.setEnabled(false);
                                                    dependanceTextField.setVisible(false);
                                                    JLabel comboLabelItem = (JLabel)textFieldLabelCollection.get((Object)dependantItem);
                                                    comboLabelItem.setVisible(false);
                                                }
                                                
                                            }
                                            if ((dependanceItemType != null) && (dependanceItemType.equalsIgnoreCase("SPINFIELD")))
                                            {
                                                //cbBox = (JComboBox)fieldList.get((Object)dependantItem);
                                                HashMap fieldList = (HashMap)theDataView.tablesTextList.get(tableNameOfView);
                                                JSpinner theSpinner =  ((JSpinner)fieldList.get((Object)dependantItem));
                                                theSpinner.setVisible(false);
                                                JLabel comboLabelItem = (JLabel)textFieldLabelCollection.get((Object)dependantItem);
                                                comboLabelItem.setVisible(false);
                                            }
                                        }// end of try
                                        
                                        if (((activeState.equalsIgnoreCase("Active") == true) && (activeMapValue.equalsIgnoreCase(selectedGroupItem) == true)) || ((activeState.equalsIgnoreCase("Inactive") == true) && (activeMapValue.equalsIgnoreCase(selectedGroupItem) == false)))
                                        {
                                        HashMap fieldList = (HashMap)theDataView.tablesTextList.get(tableNameOfView);
                                        if (dependanceItemTypeCollection != null)
                                        {
                                            dependanceItemType = (String)dependanceItemTypeCollection.get(dependantItem);
                                        }
                                        if (dependanceItemType != null)
                                        {
                                            if (dependanceItemType.equalsIgnoreCase("ARRAYCOMBO"))
                                            {
                                                ArrayList tempArrayList = (ArrayList)fieldList.get((Object)dependantItem);
                                                if (tempArrayList != null)
                                                {
                                                    cbBox = (JComboBox)tempArrayList.get(0);
                                                    if (cbBox != null)
                                                    {
                                                        comboBoxFound = true;
                                                        cbBox.setEnabled(true);
                                                        cbBox.setVisible(true);
                                                        JLabel comboLabelItem = (JLabel)comboLabelCollection.get((Object)dependantItem);
                                                        comboLabelItem.setVisible(true);
                                                    }
                                                }
                                            } else
                                                if (dependanceItemType.equalsIgnoreCase("COMBO"))
                                                {
                                                    cbBox = (JComboBox)fieldList.get((Object)dependantItem);
                                                    if (cbBox != null)
                                                    {
                                                        comboBoxFound = true;
                                                        cbBox.setEnabled(true);
                                                        cbBox.setVisible(true);
                                                        JLabel comboLabelItem = (JLabel)comboLabelCollection.get((Object)dependantItem);
                                                        comboLabelItem.setVisible(true);
                                                    }
                                                } else
                                                    if (dependanceItemType.equalsIgnoreCase("TEXTFIELD"))
                                                    {
                                                        //cbBox = (JComboBox)fieldList.get((Object)dependantItem);
                                                        dependanceTextField = (JFormattedTextField)fieldList.get((Object)dependantItem);
                                                        if (dependanceTextField != null)
                                                        {
                                                            comboBoxFound = true;
                                                            dependanceTextField.setValue("");
                                                            dependanceTextField.setEnabled(true);
                                                            dependanceTextField.setVisible(true);
                                                            JLabel comboLabelItem = (JLabel)textFieldLabelCollection.get((Object)dependantItem);
                                                            comboLabelItem.setVisible(true);
                                                        }
                                                        
                                                    } else
                                                    if (dependanceItemType.equalsIgnoreCase("SPINFIELD"))
                                                    {
                                                          JSpinner theSpinner =  ((JSpinner)fieldList.get((Object)dependantItem));
                                                          theSpinner.setVisible(true);
                                                          JLabel comboLabelItem = (JLabel)textFieldLabelCollection.get((Object)dependantItem);
                                                          comboLabelItem.setVisible(true);
                                                    } else
                                                        if (dependanceItemType.equalsIgnoreCase("OTHERBUTTON"))
                                                        {
                                                            JButton otherButton = (JButton)otherButtonList.get((Object)dependantItem);
                                                            if (otherButton != null)
                                                            {
                                                                comboBoxFound = true;
                                                                otherButton.setVisible(true);
                                                                JLabel comboLabelItem = (JLabel)otherButtonLabelCollection.get((Object)dependantItem);
                                                                comboLabelItem.setVisible(true);
                                                            }
                                                            
                                                        }  else
                                                            if (dependanceItemType.equalsIgnoreCase("OTHER"))
                                                            {
                                                                dependanceTextField = (JFormattedTextField)fieldList.get((Object)dependantItem);
                                                                if (dependanceTextField != null)
                                                                {
                                                                    comboBoxFound = true;
                                                                    dependanceTextField.setValue("");
                                                                    dependanceTextField.setEnabled(false);
                                                                    dependanceTextField.setVisible(false);
                                                                }
                                                                
                                                            }
                                        }
                                        }
                                    } // iterate through dependantitems
                                } //endif
                            }
                            if (comboBoxFound ==  true) break;
                        }
                    }
                }
            }    else
            //if ((selectedGroupItem.equalsIgnoreCase(inactiveCBBoxState) == true) ||  (selectedGroupItem.equalsIgnoreCase(activeCBBoxState) == false))
                
            {
                if ( dependanceTableDataCollection != null)
                {
                    //find group dependancies
                    if (theCollectionKey != null)
                    {
                        dependantItems = (ArrayList)dependanceTableDataCollection.get(theCollectionKey);
                    } else
                    {
                        if (selectedFieldFound == true)
                        {
                            //This was added to get the dependant table item.
                            Set dependantTableKeySet = dependanceTableDataCollection.keySet();
                            Iterator dependantTablesIter = dependantTableKeySet.iterator();
                            while (dependantTablesIter.hasNext())
                            {
                                String dependantTableName = (String)dependantTablesIter.next();
                      //utilsInstance.logSQLData("4253 dependantTableName = " + dependantTableName,"WINXP");
                                if (dependantTableName.equalsIgnoreCase(selectedFieldName) == true)
                                {
                                    dependantItems = (ArrayList)dependanceTableDataCollection.get(dependantTableName);
                                    break;
                                }
                                
                            }
                        }
                    }
                    //dependantItem = (String)dependanceTableDataCollection.get(theCollectionKey);
                    if (dependantItems != null)
                    {
                        if ((selectedItem != null) && (selectedItem.equalsIgnoreCase(inactiveCBBoxState) ==  true))
                        {
                            groupFieldFound = true;
                        }
                    }
                    if (dependantItems != null)
                    {
                        if ((selectedItem != null) && (selectedItem.equalsIgnoreCase(activeCBBoxState) ==  false))
                        {
                            groupFieldFound = true;
                        }
                    }
                    
                    if (groupLookupTableCollection != null)
                    {
                        Set groupLookupKeySetCollection = groupLookupTableCollection.keySet();
                        Iterator groupLookupIter = groupLookupKeySetCollection.iterator();
                        HashMap groupCrossLinkCollection = (HashMap)theDataModel.getGroupTableCrossLinks();
                        while (groupLookupIter.hasNext())
                        {
                            theGroupKey = (String)groupLookupIter.next();
                            groupLinkTo = (String)groupCrossLinkCollection.get((Object)theGroupKey);
                      //utilsInstance.logSQLData("4287 theGroupKey = " + theGroupKey,"WINXP");
                      //utilsInstance.logSQLData("4288 groupLinkTo = " + groupLinkTo,"WINXP");
                            
                            Set groupTableKeySet = theDataView.tablesTextList.keySet();
                            Iterator groupTableIter = groupTableKeySet.iterator();
                            while (groupTableIter.hasNext())
                            {
                                theTableName = (String)groupTableIter.next();
                      //utilsInstance.logSQLData("4295 theTableName = " + theTableName,"WINXP");
                                theFieldList = (HashMap)theDataView.tablesTextList.get(theTableName);
                                theFieldArrayList = (ArrayList)theFieldList.get((Object)groupLinkTo);
                                if (theFieldArrayList != null)
                                {
                                    JComboBox cbBox = (JComboBox)theFieldArrayList.get(0);
                                    if (cbBox == sourceComboBox)
                                    {
                                        if ((selectedGroupItem != null) && (selectedGroupItem.equalsIgnoreCase("Other") == false))
                                        {
                                            groupFieldFound = true;
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    JComboBox dependanceComboBox = null;
                    if ((dependantItems != null) && (groupFieldFound == true))
                    {
                        
                        
                        for (int dependantItemLoop = 0; dependantItemLoop < dependantItems.size(); dependantItemLoop++)
                        {
                            dependantItem =  (String)dependantItems.get(dependantItemLoop);
                       //utilsInstance.logSQLData("4320 dependantItem = " + dependantItem,"WINXP");
                                       if (dependantItemActivityMap != null)
                                        {
                                            stateAndMap = (String)dependantItemActivityMap.get((Object)dependantItem); 
                                            if (stateAndMap != null)
                                            {
                                                StringTokenizer mapTokens = new StringTokenizer(stateAndMap,",");
                                                activeState = (String)mapTokens.nextToken();
                                                activeMapValue = (String)mapTokens.nextToken();
                                            } else
                                            {
                                                activeState = "Inactive";
                                                activeMapValue = selectedGroupItem;
                                            }
 
                                        } else
                                        {
                                            activeState = "Inactive";
                                            activeMapValue = selectedGroupItem;
                                        }
                                        if (((activeState.equalsIgnoreCase("Inactive") == true) && (activeMapValue.equalsIgnoreCase(selectedGroupItem) == true)) || ((activeState.equalsIgnoreCase("Active") == true) && (activeMapValue.equalsIgnoreCase(selectedGroupItem) == false)))
                                        {
                            
                            String tableNameOfGroup = null;
                            String dependanceItemType = null;
                            HashMap tableFieldList = null;
                            
                            if (selectedFieldFound == true)
                            {
                                
                                Set tablesKeySet = theDataView.tablesTextList.keySet();
                                Iterator tablesIter = tablesKeySet.iterator();
                                while (tablesIter.hasNext())
                                {
                                    tableNameOfGroup = (String)tablesIter.next();
                                    tableFieldList = (HashMap)theDataView.tablesTextList.get(tableNameOfGroup);
                                    if (dependanceItemTypeCollection != null)
                                    {
                                        dependanceItemType = (String)dependanceItemTypeCollection.get(dependantItem);
                                    }
                                    if (dependanceItemType != null)
                                    {
                       //utilsInstance.logSQLData("4363 dependanceItemType = " + dependanceItemType,"WINXP");
                                        if (dependanceItemType.equalsIgnoreCase("ARRAYCOMBO"))
                                        {
                                            ArrayList tempArrayList = (ArrayList)tableFieldList.get((Object)dependantItem);
                                            if (tempArrayList != null)
                                            {
                                                dependanceComboBox = (JComboBox)tempArrayList.get(0);
                                                if (dependanceComboBox != null)
                                                {
                                                    dependanceComboBox.setEnabled(false);
                                                    dependanceComboBox.setVisible(false);
                                                    JLabel comboLabelItem = (JLabel)comboLabelCollection.get((Object)dependantItem);
                                                    comboLabelItem.setVisible(false);
                                                }
                                            }
                                            
                                        } else
                                            if (dependanceItemType.equalsIgnoreCase("COMBO"))
                                            {
                                                dependanceComboBox = (JComboBox)tableFieldList.get((Object)dependantItem);
                                                if (dependanceComboBox != null)
                                                {
                                                    //comboBoxFound = true;
                                                    dependanceComboBox.setEnabled(false);
                                                    dependanceComboBox.setVisible(false);
                                                    JLabel comboLabelItem = (JLabel)comboLabelCollection.get((Object)dependantItem);
                                                    comboLabelItem.setVisible(false);
                                                }
                                            } else
                                                if (dependanceItemType.equalsIgnoreCase("TEXTFIELD"))
                                                {
                                                    //cbBox = (JComboBox)fieldList.get((Object)dependantItem);
                                                    dependanceTextField = (JFormattedTextField)tableFieldList.get((Object)dependantItem);
                                                    if (dependanceTextField != null)
                                                    {
                                                        //comboBoxFound = true;
                                                        dependanceTextField.setValue("");
                                                        dependanceTextField.setEnabled(false);
                                                        dependanceTextField.setVisible(false);
                                                        JLabel comboLabelItem = (JLabel)textFieldLabelCollection.get((Object)dependantItem);
                                                        comboLabelItem.setVisible(false);
                                                    }
                                                    
                                                } else
                                                    if (dependanceItemType.equalsIgnoreCase("SPINFIELD"))
                                                    {
                                                          JSpinner theSpinner =  ((JSpinner)tableFieldList.get((Object)dependantItem));
                                                          theSpinner.setVisible(false);
                                                          JLabel comboLabelItem = (JLabel)textFieldLabelCollection.get((Object)dependantItem);
                                                          comboLabelItem.setVisible(false);
                                                    }  else
                                                    if (dependanceItemType.equalsIgnoreCase("MEDIUMTEXT"))
                                                    {
                                                        //FIX UP LATER
                                                        //cbBox = (JComboBox)fieldList.get((Object)dependantItem);
                                                        dependanceTextField = (JFormattedTextField)tableFieldList.get((Object)dependantItem);
                                                        if (dependanceTextField != null)
                                                        {
                                                            //comboBoxFound = true;
                                                            dependanceTextField.setValue("");
                                                            dependanceTextField.setEnabled(false);
                                                            dependanceTextField.setVisible(false);
                                                            JLabel comboLabelItem = (JLabel)textFieldLabelCollection.get((Object)dependantItem);
                                                            comboLabelItem.setVisible(false);
                                                        }
                                                        
                                                    }  else
                                                        if (dependanceItemType.equalsIgnoreCase("OTHERBUTTON"))
                                                        {
                                                            JButton otherButton = (JButton)otherButtonList.get((Object)dependantItem);
                                                            if (otherButton != null)
                                                            {
                                                                //comboBoxFound = true;
                                                                otherButton.setVisible(false);
                                                                JLabel comboLabelItem = (JLabel)otherButtonLabelCollection.get((Object)dependantItem);
                                                                comboLabelItem.setVisible(false);
                                                            }
                                                            
                                                        } else
                                                            if (dependanceItemType.equalsIgnoreCase("OTHER"))
                                                            {
                                                                dependanceTextField = (JFormattedTextField)tableFieldList.get((Object)dependantItem);
                                                                if (dependanceTextField != null)
                                                                {
                                                                    //comboBoxFound = true;
                                                                    dependanceTextField.setValue("");
                                                                    dependanceTextField.setEnabled(false);
                                                                    dependanceTextField.setVisible(false);
                                                                }
                                                                
                                                            }
                                        
                                    } else
                                    {
                                        dependanceTextField = (JFormattedTextField)tableFieldList.get((Object)dependantItem);
                                        if (dependanceTextField != null)
                                        {
                                            dependanceTextField.setValue("");
                                            dependanceTextField.setEnabled(false);
                                            dependanceTextField.setVisible(false);
                                        }
                                    }
                                }
                            }
                        }
                        }//loop by items in dependantitemlist
                    }
                }
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
                       //utilsInstance.logSQLData("4482 tableName = " + tableName,"WINXP");
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

            //this is old code that maybe must be deleted. will keep a while longer 
           /*String selectedGroupItem = (String)((JComboBox)e.getSource()).getSelectedItem();
           if (selectedGroupItem.equalsIgnoreCase("Other") == true)
           {
               //find group dependancies
               String dependantItem = (String)dependanceTableDataCollection.get(theCollectionKey);
               if (dependantItem != null)
               {
                   String tableNameOfGroup = null;
                   HashMap tableFieldList = null;
                   Set tablesKeySet = theDataView.tablesTextList.keySet();
                   Iterator tablesIter = tablesKeySet.iterator();
                   while (tablesIter.hasNext())
                   {
                       tableNameOfGroup = (String)tablesIter.next();
                       tableFieldList = (HashMap)theDataView.tablesTextList.get(tableNameOfGroup);
                       JFormattedTextField dependanceTextField = (JFormattedTextField)tableFieldList.get((Object)dependantItem);
                       if (dependanceTextField != null)
                       {
                           dependanceTextField.setEnabled(true);
                           break;
                       }
                   }
               }
           }*/
            
            
            //check which fields are crosslinked and update them
           
           boolean crossKeyFound = false;
           String selectedValue = null;
           String theSourceKey = null;
           String theSourceValue = null;
           HashMap crossLinkCollection = (HashMap)theDataModel.getLookupTableCrossLinks();
           
           if (crossLinkCollection != null)
           {
           ArrayList crossLinkList = (ArrayList)crossLinkCollection.get(selectedFieldName);
           //selectedItem
           if ((crossLinkList != null) && (selectedItem != null))
           {
           HashMap sourceLookupTableData = (HashMap)lookupTableDataCollection.get((Object)selectedFieldName); 
           selectedFieldName = null;
           Set sourceLookupTableKeySet = sourceLookupTableData.keySet();
           Iterator sourceLookupTableIter = sourceLookupTableKeySet.iterator();
           while (sourceLookupTableIter.hasNext())
           {
                theSourceKey = (String)sourceLookupTableIter.next();
                theSourceValue = (String)sourceLookupTableData.get((Object)theSourceKey);
                if (selectedItem.equalsIgnoreCase(theSourceValue) == true)
                {
                       //utilsInstance.logSQLData("**************4552 selectedItem = " + selectedItem,"WINXP");
                       //utilsInstance.logSQLData("**************activeMapValue = " + activeMapValue,"WINXP");
                       //utilsInstance.logSQLData("4552 theSourceKey = " + theSourceKey,"WINXP");
                       //utilsInstance.logSQLData("4553 theSourceValue = " + theSourceValue,"WINXP");
                    break;
                }
           }
           
           for (int loop = 0; loop < crossLinkList.size(); loop++)
           {
               String crossLinkField = (String)crossLinkList.get(loop);
                       //utilsInstance.logSQLData("4553 crossLinkField = " + crossLinkField,"WINXP");
               HashMap crossLookupTableData = (HashMap)lookupTableDataCollection.get((Object)crossLinkField);
            
               Set crossLookupTableKeySet = crossLookupTableData.keySet();
               Iterator crossLookupIter = crossLookupTableKeySet.iterator();
               while (crossLookupIter.hasNext())
               {
                   String theCrossKey = (String)crossLookupIter.next();
                   String theCrossValue = (String)crossLookupTableData.get((Object)theCrossKey);
                   if (theCrossKey.equalsIgnoreCase(theSourceKey))
                   {
                       //selectedValue = theCrossValue;
                       ArrayList fieldsArray = (ArrayList)fieldList.get((Object)crossLinkField);
                       JComboBox theCB = (JComboBox)fieldsArray.get(arrayIndex);
                       theCB.setSelectedItem(theCrossValue);
                       crossKeyFound = true;
                       selectedFieldName = null;
                       //utilsInstance.logSQLData("4572 theCrossKey = " + theCrossKey,"WINXP");
                       //utilsInstance.logSQLData("4573 theCrossValue = " + theCrossValue,"WINXP");
                       break;
                   }
               }//end while
           }//end for loop < crossLinkList.size()
           }
           }
        }//end try
            catch(Exception exc)
            {
                System.out.println(exc.getMessage());
            }
        }// end actionperformed
    }// end class.
  //patientKey, FH_FATHER, "ADDFAMILYHISTORY", JTabbedPane.TOP,tabIconName,"SIMPLE",userName,passWord)  
    public AddInfoMVC(String selectionModelName, String modelName, String viewType, String userName, char[] password)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }
    
    
    public AddInfoMVC(String keyValue, String modelName, int tabPlacement , String viewType, String userName, char[] password)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, HEIGHT, WIDTH, userName, password);
        theScrollPane1 = typePanel;
    }

    
    //latest addition to handle tab icons
    public AddInfoMVC(String keyValue, String modelName, int tabPlacement , String tabIconName, String viewType, String userName, char[] password)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, modelName, viewType, tabPlacement, tabIconName, InfoManager.OS_VERSION, HEIGHT, WIDTH, userName, password);
        theScrollPane1 = typePanel;
    }

    public AddInfoMVC(String keyValue, String modelName, JPanel capturedPanel, HashMap capturedList, int tabPlacement , String tabIconName, String viewType, String userName, char[] password)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, modelName, viewType,capturedPanel ,capturedList,tabPlacement, tabIconName, InfoManager.OS_VERSION, HEIGHT, WIDTH, userName, password);
        theScrollPane1 = typePanel;
    }

    public AddInfoMVC(String keyValue, String modelName, int tabPlacement , String viewType, String userName, char[] password, int height, int width)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }
    
    public AddInfoMVC(String keyValue, String modelName, JPanel capturedPanel, HashMap capturedList, int tabPlacement , String tabIconName,String viewType, String userName, char[] password, int height, int width)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, modelName, viewType,capturedPanel,capturedList,tabPlacement, tabIconName,InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }

    public AddInfoMVC(String selectionModelName, String modelName, String viewType, int height, int width, String userName, char[] password)
    {
        AddInfoMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }
    
    public AddInfoMVC(String selectionModelName, String modelName, String viewType, int tabPlacement, int height, int width, String userName, char[] password)
    {
        AddInfoMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }
    //new one to handle tab icons
    public AddInfoMVC(String selectionModelName, String modelName, String viewType, int tabPlacement, String tabIconName,int height, int width, String userName, char[] password)
    {
        AddInfoMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabPlacement, tabIconName, InfoManager.OS_VERSION, height, width, userName, password);
        theScrollPane1 = typePanel;
    }
    
    public AddInfoMVC(String keyValue, String subSelectionCode, String modelName, int tabPlacement , String viewType, String userName, char[] password)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, subSelectionCode, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }

public AddInfoMVC(String keyValue, String subSelectionCode, String modelName, int tabPlacement , String tabIconName, String viewType, String userName, char[] password)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, subSelectionCode, modelName, viewType, tabPlacement, tabIconName, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }
    
    public AddInfoMVC(String keyValue, String subSelectionCode, String modelName, JPanel capturedPanel, HashMap capturedList,int tabPlacement , String tabIconName, String viewType, String userName, char[] password)
    {
        
        /** Creates new  Main Dialog Type Panel */
        AddInfoMVCApp = this;
        initMainComponent(keyValue, subSelectionCode, modelName, viewType,capturedPanel,capturedList,tabPlacement, tabIconName, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
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
            theMTUtils = new MTUtils();
            theUsername = userName;
            selectionDataModel = new  MTTableDataModel(selectionModelName, userName, password);
            theDataSelectView = new DataSelectView(viewType, tabPlacement, selectionDataModel, _height, _width);
            //SwingUtilities.getRoot(theDataSelectView).repaint();
            add(theDataSelectView);
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public void initComponents(String selectionModelName, String modelName, String viewType, int tabPlacement, String tabIcon, String _theOS, int _height, int _width, String userName, char[] password)
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
            theMTUtils = new MTUtils();
            theUsername = userName;
            selectionDataModel = new  MTTableDataModel(selectionModelName, userName, password);
            theDataSelectView = new DataSelectView(viewType, tabPlacement, tabIcon, selectionDataModel, _height, _width);
            add(theDataSelectView);
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void initMainComponent(String keyValue, String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
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
            theUsername = userName;
            theMTUtils = new MTUtils();
            theDataModel = new MTTableDataModel(editModelName, keyValue, userName, password);
            theDataView = new DataView(editViewType, editTabPlacement, theDataModel, editHeight, editWidth);
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
            add(theDataView);
            //SwingUtilities.getRoot(theDataView).repaint();
            AddInfoMVCApp.updateUI();
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void initMainComponent(String keyValue, String modelName, String viewType, int tabPlacement, String iconName, String _theOS, int _height, int _width, String userName, char[] password)
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
            theMTUtils = new MTUtils();
            theUsername = userName;
            theDataModel = new MTTableDataModel(editModelName, keyValue, userName, password);
            theDataView = new DataView(editViewType, editTabPlacement, iconName, theDataModel, editHeight, editWidth);
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
            add(theDataView);
            //SwingUtilities.getRoot(theDataView).repaint();
            AddInfoMVCApp.updateUI();
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public void initMainComponent(String keyValue, String modelName, String viewType, JPanel capturedPanel, HashMap capturedList, int tabPlacement, String iconName, String _theOS, int _height, int _width, String userName, char[] password)
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
            theMTUtils = new MTUtils();
            theUsername = userName;
            theDataModel = new MTTableDataModel(editModelName, keyValue, userName, password);
            theDataView = new DataView(editViewType, editTabPlacement, iconName, theDataModel, editHeight, editWidth);
            //CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel,capturedPanel,capturedList);
            add(theDataView);
            //SwingUtilities.getRoot(theDataView).repaint();
            AddInfoMVCApp.updateUI();
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public void initMainComponent(String keyValue, String subSelectionCode, String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
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
            theMTUtils = new MTUtils();
            theUsername = userName;
            theDataModel = new MTTableDataModel(editModelName, keyValue, subSelectionCode, userName, password);
            theDataView = new DataView(editViewType, editTabPlacement, theDataModel, editHeight, editWidth);
            //theDataView.setLayout(new BorderLayout());
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
            add(theDataView);
            //SwingUtilities.getRoot(theDataView).repaint();
            AddInfoMVCApp.updateUI();
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }
    public void initMainComponent(String keyValue, String subSelectionCode, String modelName, String viewType, int tabPlacement, String tabIconName, String _theOS, int _height, int _width, String userName, char[] password)
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
            theUsername = userName;
            theMTUtils = new MTUtils();
            theDataModel = new MTTableDataModel(editModelName, keyValue, subSelectionCode, userName, password);
            theDataView = new DataView(editViewType, editTabPlacement, tabIconName,theDataModel, editHeight, editWidth);
            //theDataView.setLayout(new BorderLayout());
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
            add(theDataView);
            //SwingUtilities.getRoot(theDataView).repaint();
            AddInfoMVCApp.updateUI();
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }
    public void initMainComponent(String keyValue, String subSelectionCode, String modelName, String viewType, JPanel capturedPanel, HashMap capturedList, int tabPlacement, String tabIconName, String _theOS, int _height, int _width, String userName, char[] password)
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
            theUsername = userName;
            theMTUtils = new MTUtils();
            theDataModel = new MTTableDataModel(editModelName, keyValue, subSelectionCode, userName, password);
            theDataView = new DataView(editViewType, editTabPlacement, tabIconName,theDataModel, editHeight, editWidth);
            //theDataView.setLayout(new BorderLayout());
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel,capturedPanel,capturedList);
            add(theDataView);
            //SwingUtilities.getRoot(theDataView).repaint();
            AddInfoMVCApp.updateUI();
        }
        catch (MTException e)
        {
            JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }
        
    }
}

