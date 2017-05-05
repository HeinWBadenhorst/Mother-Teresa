/*
 * DisplayInfoByDateMVC.java
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
import java.awt.print.*;
import java.awt.print.Paper.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Dimension;
import javax.swing.event.*;
import java.util.*;
import java.awt.image.*;


public class DisplayInfoByDateMVC extends JPanel {
   
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
  private DisplayInfoByDateMVC DisplayInfoByDateMVCApp;
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
  private String displayModelName = "";
  private String displayViewType = "";
  private int displayTabPlacement = 0;
  private int displayHeight = 0;
  private int displayWidth = 0;
  private String keyDescription = "";
  private ArrayList fieldTextItems;
  public JPanel displayPanel = null;
  public int m_maxNumPage = 1;
  public JComponentVista vista; 
  public String defUserName = null;
  public char[] defPassword = null;
  
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
                controller.printReportButton.addActionListener(this);
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
                if (e.getSource() == controller.printReportButton)
                {
                    PrinterJob pj = PrinterJob.getPrinterJob(); 
                    PageFormat format = new PageFormat();
                    Paper paper = new Paper();
                    paper.setSize(596,842);
                    format.setPaper(paper);
                    format = pj.pageDialog(format);
                    vista = new JComponentVista(displayPanel, format);
                    if (format.getOrientation() == format.LANDSCAPE)
                    {
                        vista.scaleToFit(false);
                        vista.scaleToFitX();
                    }
                    pj.setPageable(vista);
                    if (pj.printDialog()) 
                    { 
                        pj.print();
                    }
                }
                    
            }
            //catch (MTException exc) 
            //{
            //   JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            //}
            catch (PrinterException exc) 
            {
                exc.printStackTrace();
                System.err.println("Printing error: "+exc.toString());
            }
            catch (Exception ex) 
            {
               JOptionPane.showMessageDialog(theDataView, ex.getMessage(), "Unspecified Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } 

    class MTTableDataModel extends MTBusinessModel 
    {
        public MTTableDataModel(String modelName,String userName, char[] password) throws MTException
        {
            super(modelName, InfoManager.OS_VERSION,userName,password);
        }

        public MTTableDataModel(String modelName, String keyValue,String userName, char[] password) throws MTException
        {
            super(modelName, keyValue, InfoManager.OS_VERSION,userName,password);
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
        public JButton printReportButton;
        public HashMap fieldTextList;
        public HashMap tablesTextList;
        
        public DataView(String viewType, MTTableDataModel _theModel, int _height, int  _width) throws MTException
        {
            if(viewType.equalsIgnoreCase(SIMPLE_VIEW))
            {
                try
                {
                    initSimpleDataView(_theModel, _height, _width);
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

        
        public void addItemToPanel(JPanel _panel, GridBagLayout _gridBag, GridBagConstraints _constraints, String coords, int offset, Component label)
        {
           int  rowCoord = 0;
           int  colCoord = 0;
           String theToken = "";
           StringTokenizer coordTokens = new StringTokenizer(coords,",");	
           int tokenCount = coordTokens.countTokens();
           for (int coordIndex = 0; coordIndex <  tokenCount; coordIndex++)
           {
              theToken = (String)coordTokens.nextToken();
              if (coordIndex == 0)
              {
                 rowCoord = Integer.parseInt(theToken) + offset;
              } else
              {
                 colCoord = Integer.parseInt(theToken);
              }
           }
           _constraints.gridx = colCoord;
           _constraints.gridy = rowCoord;
           _gridBag.setConstraints(label, _constraints);
           _panel.add(label);
        }
        
        public void initSimpleDataView(MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                HashMap labelCoordCollection = mtModel.getLabelDispCoordinateCollection();
                HashMap fieldCoordCollection = mtModel.getFieldDispCoordinateCollection();
                HashMap objectCoordCollection = mtModel.getObjectDispCoordinateCollection();
                HashMap dbAccessCollection = (HashMap)mtModel.getdbAccessCollection();
                HashMap tableDisplayHeightCollection = (HashMap)mtModel.gettableRowDisplayHeightCollection();
                lookupTableDataCollection = (HashMap)mtModel.getLookupTableDataCollection();
                int rowCount  =  0;
                int colCount = 0;
                int dateEntries = 0;
                int panelRows = 0;
                ArrayList tempList;
                HashMap dateList = null;
                Border raisedBevel, loweredBevel, compoundBorder;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
                int rowCoord = 0;
                int colCoord = 0;
                String theToken = "";
                displayPanel = new JPanel();
                JPanel controlPane = new JPanel();
                String theTableName = null;
                String theGroupLabel = null;
                theGroupLabel = mtModel.getGroupName();
                JLabel groupLabel = new JLabel(theGroupLabel);
                String groupLabelCoords = (String)objectCoordCollection.get("GROUP_NAME");
                StringTokenizer coordTokens = new StringTokenizer(groupLabelCoords,",");	
                int tokenCount = coordTokens.countTokens();
                for (int coordIndex = 0; coordIndex <  tokenCount; coordIndex++)
                {
                   theToken = (String)coordTokens.nextToken();
                   if (coordIndex == 0)
                   {
                       rowCoord = Integer.parseInt(theToken);
                   } else
                   {
                       colCoord = Integer.parseInt(theToken);
                   }
                }
                GridBagLayout gridBag = new GridBagLayout();
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = colCoord;
                constraints.gridy = rowCoord;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.anchor = GridBagConstraints.CENTER;
                displayPanel.setLayout(gridBag);
                groupLabel.setBorder(BorderFactory.createEtchedBorder());
                groupLabel.setForeground(new Color(0,0,255));
                //groupLabel.setBackground(new Color(0,0,255));
                gridBag.setConstraints(groupLabel, constraints);
                displayPanel.add(groupLabel);
                tablesTextList = new HashMap(mtModel.getTableCount());
                int tableOffset = 0;
                int dateOffset = 0;
                String labelFont = "Serif";
                String valueFont = "Bookman Antiqua";
                
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    int tableHeight = Integer.parseInt((String)tableDisplayHeightCollection.get((Object)theTableName));
                    
                    DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)theTableName);
                    dateList = dataInstance.getDateFieldList();
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
                    for(dateEntries = 0; dateEntries < dateList.size(); dateEntries++)
                    {
                        dateOffset = dateEntries * tableHeight;
                        /*dataPanel.setLayout(new BorderLayout());
                        labelPanel.setLayout(new GridLayout(panelRows, 0));
                        fieldPanel.setLayout(new GridLayout(panelRows, 0));
                        buttonPanel.setLayout(new GridLayout(1,0,5,5));
                        buttonPanel.setBorder(compoundBorder);
                        southPanel.setLayout(new BorderLayout());
                        dataPanel.setBorder(BorderFactory.createEtchedBorder());*/

                        String[] theColumnNames = dataInstance.dataTable.getColumnNames();
                        String[] theColumnAliases = dataInstance.dataTable.getColumnAliases();
                        String[] theColumnTypes = dataInstance.dataTable.getColumnKeyTypes();
                        boolean[] theColumnLocks = dataInstance.dataTable.getColumnLocks();
                        boolean[] theColumnDisplays = dataInstance.dataTable.getColumnDisplays();
                        String thePanelHeading = (String)mtModel.getTableAliases().get((Object)theTableName);
                        thePanelHeading = keyDescription;
                        JLabel panelHeadingLabel = new JLabel(thePanelHeading);
                        panelHeadingLabel.setBackground(new Color(0,0,255));
                        panelHeadingLabel.setForeground(new Color(255,255,255));
                        panelHeadingLabel.setPreferredSize(new Dimension(200,20));
                        panelHeadingLabel.setFont(new Font(labelFont,Font.BOLD,12));
                        panelHeadingLabel.setBorder(BorderFactory.createRaisedBevelBorder());
                        String objectCoords = (String)objectCoordCollection.get((Object)"KEY_NAME");
                        addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, panelHeadingLabel);
                        //headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
                        //headingPanel.setBackground(new Color(0,50,150));
                        //headingPanel.add(panelHeadingLabel);
                        //dataPanel.add(headingPanel, BorderLayout.NORTH);

                        MaskFormatter formatter;
                        JFormattedTextField ftf;
                        JTextField fieldText;
                        String selectedValue = "";
                        String keyFieldValue = null;
                        String dateTabField = "";
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
                              fieldLabel.setBorder(loweredBevel);
                              //fieldLabel.setBackground(new Color(0,0,0));
                              fieldLabel.setForeground(new Color(50,50,150));
                              fieldLabel.setBackground(new Color(255,255,255));
                              fieldLabel.setPreferredSize(new Dimension(200,20));
                              if (theFieldType.equalsIgnoreCase("PRIMARY"))
                              {
                                   keyFieldValue = theFieldValue;
                                   String zeroPadding = "";
                                   for (int keyloop = 0; keyloop < fieldLength - theFieldValue.length(); keyloop++)
                                   {
                                      zeroPadding = zeroPadding + "0";
                                   }
                                   theFieldValue = zeroPadding + theFieldValue;
                                   fieldText = new JTextField(theFieldValue);
                                   fieldText.setPreferredSize(new Dimension(200,20));
                                   fieldText.setBackground(new Color(255,255,255));
                                   fieldText.setForeground(new Color(0,0,50));
                                   fieldText.setDisabledTextColor(new Color(0,0,50));
                                   fieldText.setFont(new Font(valueFont,1,13));
                                   fieldText.setBorder(loweredBevel);
                                   fieldText.setEnabled(false);
                                   if (theFieldDisplay == true)
                                   {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, fieldText);
                                   } //end else fieldisplay = true;
                               } else
                               if (theFieldType.equalsIgnoreCase("FOREIGN"))
                               {
                                   lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theFieldName);
                                   Set keySet = lookupTableData.keySet();
                                   Iterator iter = keySet.iterator();
                                   while (iter.hasNext())
                                   {
                                       String theKey = (String)iter.next();
                                       String theValue = (String)lookupTableData.get((Object)theKey);
                                       if (theFieldValue.equalsIgnoreCase(theKey))
                                       {
                                           selectedValue = theValue;
                                           break;
                                       }
                                    }
                                    if (theFieldDisplay == true)
                                    {
                                        fieldText = new JTextField(selectedValue);
                                        fieldText.setPreferredSize(new Dimension(200,20));
                                        fieldText.setBackground(new Color(255,255,255));
                                        fieldText.setForeground(new Color(0,0,50));
                                        fieldText.setDisabledTextColor(new Color(0,0,50));
                                        fieldText.setFont(new Font(valueFont,1,13));
                                        fieldText.setBorder(loweredBevel);
                                        fieldText.setEnabled(false);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, fieldText);
                                    }
                                 } else
                                 if (theFieldType.equalsIgnoreCase("BOOLEAN_FIELD"))
                                 {
                                     if (theFieldValue.equalsIgnoreCase("0"))
                                        selectedValue = "No";
                                     else
                                        selectedValue = "Yes";
                                     if (theFieldDisplay == true)
                                     {
                                        fieldText = new JTextField(selectedValue);
                                        fieldText.setPreferredSize(new Dimension(200,20));
                                        fieldText.setBackground(new Color(255,255,255));
                                        fieldText.setForeground(new Color(0,0,50));
                                        fieldText.setDisabledTextColor(new Color(0,0,50));
                                        fieldText.setFont(new Font(valueFont,1,13));
                                        fieldText.setBorder(loweredBevel);
                                        fieldText.setEnabled(false);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, fieldText);
                                     }
                                 } else
                                 if (theFieldType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                                 {
                                    if (theFieldValue.equalsIgnoreCase("0"))
                                        selectedValue = "To"; 
                                    else
                                        selectedValue = "By";
                                    if (theFieldDisplay == true)
                                    {
                                        fieldText = new JTextField(selectedValue);
                                        fieldText.setPreferredSize(new Dimension(200,20));
                                        fieldText.setBackground(new Color(255,255,255));
                                        fieldText.setForeground(new Color(0,0,50));
                                        fieldText.setDisabledTextColor(new Color(0,0,50));
                                        fieldText.setFont(new Font(valueFont,1,13));
                                        fieldText.setBorder(loweredBevel);
                                        fieldText.setEnabled(false);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, fieldText);
                                    }
                                 } else
                                 if (theFieldType.equalsIgnoreCase("PHONE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 }  else
                                 if (theFieldType.equalsIgnoreCase("CELL_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 }  else
                                 if (theFieldType.equalsIgnoreCase("AREA_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 }  else
                                 if (theFieldType.equalsIgnoreCase("ID_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("#############");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 }  else
                                 if (theFieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("##/##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 }  else
                                 if (theFieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
                                 {
                                     if (theFieldDisplay == true)
                                     {
                                        textArea = new JTextArea(10,50);
                                        textArea.setText(theFieldValue);
                                        textArea.setBackground(new Color(255,255,255));
                                        textArea.setForeground(new Color(0,0,50));
                                        textArea.setDisabledTextColor(new Color(0,0,50));
                                        textArea.setFont(new Font(valueFont,1,13));
                                        textArea.setBorder(loweredBevel);
                                        
                                        textArea.setEnabled(false);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 2;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, textArea);
                                     }
                                 }  else
                                 if (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                                 {
                                     if (theFieldDisplay == true)
                                     {
                                        textArea = new JTextArea(10,50);
                                        textArea.setText(theFieldValue);
                                        textArea.setBackground(new Color(255,255,255));
                                        textArea.setForeground(new Color(0,0,50));
                                        textArea.setDisabledTextColor(new Color(0,0,50));
                                        textArea.setFont(new Font(valueFont,1,13));
                                        textArea.setBorder(loweredBevel);
                                        
                                        textArea.setEnabled(false);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 2;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, textArea);
                                     }
                                 } else
                                 if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("U U U");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 } else
                                 if (theFieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-## ##:##:##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(200,200,200));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 }  else
                                 if (theFieldType.equalsIgnoreCase("DATE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(200,200,200));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 } else
                                 {
                                     String mask = "";
                                     for (int maskloop = 0; maskloop < fieldLength; maskloop++)
                                     {
                                         mask = mask + "*";
                                     }
                                     formatter = new  MaskFormatter(mask);
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(200,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if (theFieldDisplay == true)
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 } //end else
                            } //end for fieldindex
                        } //end try
                        catch (Exception ex) 
                        {
                            throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
                        }

                        /* use this to add date
                         if ((dateList != null)  && (dateList.containsKey((Object)keyFieldValue)))
                        {
                            dateTabField = (String)dateList.get((Object)keyFieldValue);
                        } else
                        {
                            dateTabField = "Null"; 
                        }
                        rightTabbedPane.addTab(dateTabField,null,dataPanel,"Capture Date");
                        */
                    } //end of datelist entries
                    tableOffset += dateOffset; 
                } //end for tablelist loop
                
                JScrollPane displayScrollPane = new JScrollPane(displayPanel);
                //displayScrollPane.add();
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(displayScrollPane);
                printReportButton = new JButton("Print Report");
                controlPane.setLayout(new BorderLayout());
                controlPane.setBorder(compoundBorder);
                controlPane.add(printReportButton, BorderLayout.CENTER);
                this.add(controlPane);
            } // end of try
            catch (MTException e)
            {
                throw new MTException(e.getExceptionType(), e.getErrorMessage());
            }
            catch (Exception e)
            {
                throw new MTException(InfoManager.VIEW_BUILDER_ERROR, e.getMessage());
            }
            
        } //end initSimpleDataView
        
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
                  theDataModel = new MTTableDataModel(displayModelName, selectedKeyValue,defUserName,defPassword);
                  theDataView = new DataView(displayViewType, theDataModel, displayHeight, displayWidth);
                  removeAll();
                  add(theDataView);
                  CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
                  DisplayInfoByDateMVCApp.updateUI();
               }
            }
            catch (MTException exc)
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            
         }
   }
   

    public DisplayInfoByDateMVC(String selectionModelName, String modelName, String viewType, String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        DisplayInfoByDateMVCApp = this;
        defUserName = userName;
        defPassword = password;
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }

    public DisplayInfoByDateMVC(String selectionModelName, String modelName, String viewType, int height, int width, String userName, char[] password) 
    {
        DisplayInfoByDateMVCApp = this;
        defUserName = userName;
        defPassword = password;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, height, width,userName,password);
        theScrollPane1 = typePanel;
    }

    public DisplayInfoByDateMVC(String selectionModelName, String modelName, String viewType, int tabPlacement, int height, int width, String userName, char[] password) 
    {
        DisplayInfoByDateMVCApp = this;
        defUserName = userName;
        defPassword = password;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, height, width,userName,password);
        theScrollPane1 = typePanel;
    }

    public void initComponents(String selectionModelName, String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
    {
        try
        {
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINDOWS"))
            {
               imagePath = InfoManager.WINDOWS_IMAGE_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("UNIX"))
            {
               imagePath = InfoManager.UNIX_IMAGE_PATH;
            } else
            if (InfoManager.OS_VERSION.equalsIgnoreCase("WINXP"))
            {
               imagePath = InfoManager.WINXP_IMAGE_PATH;
            }
            // Set Up DataModel by instantiating MTBusinessModel
            displayModelName  = modelName;
            displayViewType = viewType;
            displayTabPlacement = tabPlacement;
            displayHeight = _height;
            displayWidth = _width;
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
