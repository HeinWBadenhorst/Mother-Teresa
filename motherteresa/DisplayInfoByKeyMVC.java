/*
 * DisplayInfoByKeyMVC.java
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


public class DisplayInfoByKeyMVC extends JPanel {
   
   // The initial width and height of the frame
  public static int WIDTH = 1000;
  public static int HEIGHT = 700;
  private JScrollPane typePanel;
  public JScrollPane theScrollPane1;
  public static JButton updateButton;
  public static JButton addrowButton;
  public static JButton removeRowButton;
  public static DBAccess typeDBAccess;
  public String[][] dataArray;
  private Object[][] tmpDataArray;
  private static final String SIMPLE_VIEW = "SIMPLE";
  private static final String OTHER_VIEW = "OTHER";
  private static final String INFO_VIEW = "INFO";
  private static final String INFO_BY_DATE = "INFODATE";
  private static final String INFO_BY_XML = "INFOBYXML";
  private static final String INFO_BY_DATE_XML = "BYXMLDATED";
  
  private static final int tabsTop = JTabbedPane.TOP;
  private static final int tabsBottom = JTabbedPane.BOTTOM;
  private static final int tabsLeft = JTabbedPane.LEFT;
  private static final int tabsRight = JTabbedPane.RIGHT;
  private DisplayInfoByKeyMVC DisplayInfoByKeyMVCApp;
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
  private String docScanPath = "";
  private int selectedRow = 0;
  public String selectedKeyValue = "";
  public ArrayList dateListArray = null;
  public JTable selectTable;
  private String displayModelName = "";
  private String displayViewType = "";
  private int displayTabPlacement = 0;
  private int displayHeight = 0;
  private int displayWidth = 0;
  private String keyDescription = "";
  private ArrayList fieldTextItems;
  public JPanel displayPanel = null;
  public JPanel labelPanel = null;
  public JPanel valuePanel = null;
  private String defUserName = InfoManager.DEF_USER_NAME;
  private char[] defPassword = InfoManager.DEF_PASSWORD;
  private String pageOrientation = null;
  boolean multiPage = false;
  boolean isEmpty = false;
  private ArrayList dateCheckArray = null;
  private String thisCaptureDate = null;
  private String dateCheckItemAllowed = null;
  private ArrayList captureDateList;
  private HashMap dateByTableMap; 

  
  
  public int m_maxNumPage = 1;
  public JComponentVista vista;
  public String[] theColumnNames;
  //public String fieldName = null;


  
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
        
        public CtoMAdaptor(DataView c) throws MTException //old class had datamodel as second parameter
        {
            //model = m;
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
                    //paper.setSize(1000,800);
                    format.setPaper(paper);
                    if ((pageOrientation != null) && (pageOrientation.equalsIgnoreCase("LANDSCAPE") == true))
                    {
                         format.setOrientation(format.LANDSCAPE);
                    } else
                    {
                         format.setOrientation(format.PORTRAIT);
                    }
                    format = pj.pageDialog(format);
                    vista = new JComponentVista(displayPanel, format);
                    if (multiPage)
                    {
                        vista.scaleToFitX();
                    } else
                    {
                       vista.scaleToFit(true); 
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

        
        public MTTableDataModel(String modelName, String keyValue, String username, char[] password) throws MTException
        {
            super(modelName, keyValue, InfoManager.OS_VERSION, username, password);
        }

        public MTTableDataModel(String modelName, String username, char[] password) throws MTException
        {
            super(modelName, InfoManager.OS_VERSION, username, password);
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
            if((viewType.equalsIgnoreCase(SIMPLE_VIEW)) || (viewType.equalsIgnoreCase(INFO_VIEW)))
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
            panelHeadingLabel.setFont(new Font("Serif",Font.BOLD,12));
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
                String fieldType = theColumnTypes[colIndex];
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
                fieldLabel.setPreferredSize(new Dimension(150,20));
                if (fieldType.equalsIgnoreCase("PRIMARY"))
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
                if (fieldType.equalsIgnoreCase("FOREIGN"))
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
              selectTable.setPreferredScrollableViewportSize(new Dimension(400,430));
              selectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
              dataPanel.add(scrollPane, BorderLayout.WEST);
              ListSelectionModel rowSM = selectTable.getSelectionModel();
              rowSM.addListSelectionListener(new tableListener());
              
              dataInstance.closeConnection();
              dbAccessCollection.remove((Object)_TableName);
              dataInstance = null;
              System.gc();
              
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
            if(viewType.equalsIgnoreCase(INFO_VIEW))
            {
                try
                {
                    initInfoDataView(_theModel, _height, _width);
                }
                catch (MTException ex)
                {
                    throw ex;
                }
            } else
            if(viewType.equalsIgnoreCase(INFO_BY_DATE))
            {
                try
                {
                    initDateTabbedDataView(_theModel, _height, _width);
                }
                catch (MTException ex)
                {
                    throw ex;
                }
            } else
            if(viewType.equalsIgnoreCase(INFO_BY_XML))
            {
                try
                {
                    initDataViewByXMLSpec(_theModel, _height, _width);
                }
                catch (MTException ex)
                {
                    throw ex;
                }
            }
        }

        public DataView(String viewType, ArrayList dateChosenList, MTTableDataModel _theModel, int _height, int  _width) throws MTException
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
            if(viewType.equalsIgnoreCase(INFO_VIEW))
            {
                try
                {
                    initInfoDataView(_theModel, _height, _width);
                }
                catch (MTException ex)
                {
                    throw ex;
                }
            } else
            if(viewType.equalsIgnoreCase(INFO_BY_DATE))
            {
                try
                {
                    initDateTabbedDataView(_theModel, _height, _width);
                }
                catch (MTException ex)
                {
                    throw ex;
                }
            } else
            if(viewType.equalsIgnoreCase(INFO_BY_XML))
            {
                try
                {
                    initDataViewByXMLSpec(_theModel, _height, _width);
                }
                catch (MTException ex)
                {
                    throw ex;
                }
            } else
            if(viewType.equalsIgnoreCase(INFO_BY_DATE_XML))
            {
                try
                {
                    initDataViewByDateXMLSpec(_theModel,dateChosenList, _height, _width);
                }
                catch (MTException ex)
                {
                    throw ex;
                }
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

        public void addItemToPanel(JPanel _panel, GridBagLayout _gridBag, GridBagConstraints _constraints, String XPos, String YPos, Component component)
        {
           int  rowCoord = 0;
           int  colCoord = 0;
           colCoord = Integer.parseInt(XPos);
           colCoord = Integer.parseInt(YPos);
           _constraints.gridx = colCoord;
           _constraints.gridy = rowCoord;
           _gridBag.setConstraints(component, _constraints);
           _panel.add(component);
        }
        
        public void addItemToPanel(JPanel _panel, GridBagLayout _gridBag, GridBagConstraints _constraints, Component component)
        {
           _gridBag.setConstraints(component, _constraints);
           _panel.add(component);
        }

        public  String[] getFileList(String docFiles)
        {
           String theToken = "";
           String[] returnList;
           StringTokenizer docTokens = new StringTokenizer(docFiles,"\n");	
           int tokenCount = docTokens.countTokens();
           returnList = new String[tokenCount];
           for (int listIndex = 0; listIndex <  tokenCount; listIndex++)
           {
              theToken = (String)docTokens.nextToken();
              returnList[listIndex] = theToken;
           }
           return returnList;
        }

        public String formatDate(String inputString)
        {
            String month = inputString.substring(2,4);
            String year = inputString.substring(0,2);
            int intYear = Integer.parseInt(year);
            int intMonth = Integer.parseInt(month);
            String textFullYear = null;
            String textMonth = null;
            
            switch (intMonth)
            {
                case 0:
                  return "None Entered";  
                case 1:
                  textMonth = "January";  
                break;
                case 2:
                  textMonth = "February";  
                break;
                case 3:
                  textMonth = "March";  
                break;
                case 4:
                  textMonth = "April";  
                break;
                case 5:
                  textMonth = "May";  
                break;
                case 6:
                  textMonth = "June";  
                break;
                case 7:
                  textMonth = "July";  
                break;
                case 8:
                  textMonth = "August";  
                break;
                case 9:
                  textMonth = "September";  
                break;
                case 10:
                  textMonth = "October";  
                break;
                case 11:
                  textMonth = "November";  
                break;
                case 12:
                  textMonth = "December";  
                break;
            }
            if (intYear > 60)
            {
                textFullYear = String.valueOf(intYear + 1900);
            } else
            {
               textFullYear = String.valueOf(intYear + 2000);
            }
            return textMonth + " " + textFullYear;
        }
        public void initSimpleDataView(MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                HashMap labelCoordCollection = mtModel.getLabelDispCoordinateCollection();
                HashMap fieldCoordCollection = mtModel.getFieldDispCoordinateCollection();
                HashMap objectCoordCollection = mtModel.getObjectDispCoordinateCollection();
                
                HashMap lowerBoundCollection = mtModel.getLowerBoundCollection();
                HashMap upperBoundCollection = mtModel.getUpperBoundCollection();
                
                HashMap dbAccessCollection = (HashMap)mtModel.getdbAccessCollection();
                HashMap tableDisplayHeightCollection = (HashMap)mtModel.gettableRowDisplayHeightCollection();
                lookupTableDataCollection = (HashMap)mtModel.getLookupTableDataCollection();
                int rowCount  =  0;
                int colCount = 0;
                int dateEntries = 0;
                int panelRows = 0;
                String labelFont = "Serif";
                String valueFont = "Bookman Antiqua";
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
                labelPanel = new JPanel();
                valuePanel = new JPanel();
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
                gridBag.setConstraints(groupLabel, constraints);
                displayPanel.add(groupLabel);
                tablesTextList = new HashMap(mtModel.getTableCount());
                int tableOffset = 0;
                int dateOffset = 0;
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
                    int dateLoop = 0;
                    if ((dateList == null) || (dateList.size() == 0))
                    { 
                        dateLoop = 1;
                    } else
                    {
                        dateLoop = dateList.size();
                    }
                    for(dateEntries = 0; dateEntries < dateLoop; dateEntries++)
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
                        panelHeadingLabel.setForeground(new Color(0,0,255));
                        panelHeadingLabel.setPreferredSize(new Dimension(200,20));
                        panelHeadingLabel.setFont(new Font(labelFont,Font.BOLD,12));
                        panelHeadingLabel.setBorder(BorderFactory.createEtchedBorder());
            
                        String objectCoords = (String)objectCoordCollection.get((Object)"KEY_NAME");
                        constraints.gridwidth = 1;
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
                              if (theFieldName.indexOf("FILLER") == 0)
                              {
                                  continue;
                              }
                              String fieldType = theColumnTypes[fieldIndex];
                              String theFieldValue = dataInstance.dataTable.getDataAt(dateEntries, fieldIndex);
                              
                              int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                              boolean theFieldLock = theColumnLocks[fieldIndex];
                              boolean theFieldDisplay = theColumnDisplays[fieldIndex];
                              JLabel fieldLabel = new JLabel(theFieldLabel);
                              fieldLabel.setBorder(loweredBevel);
                              //fieldLabel.setBackground(new Color(0,0,0));
                              fieldLabel.setForeground(new Color(50,50,150));
                              fieldLabel.setBackground(new Color(255,255,255));
                              fieldLabel.setPreferredSize(new Dimension(150,20));
                              if (fieldType.equalsIgnoreCase("PRIMARY"))
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
                               if (fieldType.equalsIgnoreCase("FOREIGN"))
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
                                 if (fieldType.equalsIgnoreCase("BOOLEAN_FIELD"))
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
                                 if (fieldType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
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
                                 if (fieldType.equalsIgnoreCase("PHONE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if (fieldType.equalsIgnoreCase("CELL_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if (fieldType.equalsIgnoreCase("AREA_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if (fieldType.equalsIgnoreCase("ID_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("#############");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if (fieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("##/##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if (fieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
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
                                 }   else
                                 if (fieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                                 {
                                     if (theFieldDisplay == true)
                                     {
                                        String[] docFileNames = getFileList(theFieldValue);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 2;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        for (int docIndex = 0; docIndex < docFileNames.length; docIndex++)
                                        {
                                            ImageIcon theImage = new ImageIcon(docScanPath + docFileNames[docIndex]);
                                            JLabel theLabel = new JLabel(theImage);
                                            JScrollPane scrollPane = new JScrollPane(theLabel);
                                            addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset + docIndex, scrollPane);
                                        }
                                     }
                                 } else
                                 if (fieldType.equalsIgnoreCase("INITIAL_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("U U U");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if (fieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-## ##:##:##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if (fieldType.equalsIgnoreCase("DATE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 }   else
                                 if ((fieldType.equalsIgnoreCase("SPINMONTHYEAR")) || (fieldType.equalsIgnoreCase("SPINLITMONTHYEAR")))
                                 {
                                     //formatter = new  MaskFormatter("####-##-##");
                                     ftf = new JFormattedTextField();
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     String tmpField = formatDate(theFieldValue);
                                     //String tmpField = "Month=" + theFieldValue.substring(2,4) +"/Year=" + theFieldValue.substring(0,2);
                                     ftf.setValue(tmpField);
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
                                 }    else
                                 if (fieldType.equalsIgnoreCase("SPINTEMP"))
                                 {
                                     ftf = new JFormattedTextField();
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     String tmpField = theFieldValue;
                                     //String tmpField = "Month=" + theFieldValue.substring(2,4) +"/Year=" + theFieldValue.substring(0,2);
                                     ftf.setValue(tmpField);
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
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     if (lowerBoundCollection != null)
                                     {
                                         if (lowerBoundCollection.containsKey((Object)theFieldName))
                                         {
                                             String lowerValue = (String)lowerBoundCollection.get((Object)theFieldName);
                                             int intLowerValue = Integer.parseInt(lowerValue);
                                             String upperValue = (String)upperBoundCollection.get((Object)theFieldName);
                                             int intUpperValue = Integer.parseInt(upperValue);
                                             int intFieldValue = Integer.parseInt(theFieldValue);
                                             if (intFieldValue < intLowerValue)
                                             {
                                                  ftf.setBackground(new Color(120,120,255));
                                                  ftf.setForeground(new Color(150,150,150));
                                             } else
                                             if (intFieldValue > intUpperValue)
                                             {
                                                  ftf.setBackground(new Color(255,120,120));
                                                  ftf.setForeground(new Color(150,150,0));
                                             }
                                         }
                                     }
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
                   //--ccc---
                    dataInstance.closeConnection();
                    dbAccessCollection.remove((Object)theTableName);
                    dataInstance = null;
                    System.gc();

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
        
       private String stripNulls(String textString)
       {
           String internalString = textString;
            while (internalString.indexOf("null") > -1)
            {
               int startIndex = internalString.indexOf("null");
               int endIndex = internalString.indexOf("null") + 4;
               internalString = internalString.substring(0, startIndex) + internalString.substring(endIndex, internalString.length());
            }
           return internalString;
       }

        
        
        public void initInfoDataView(MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                HashMap labelCoordCollection = mtModel.getLabelDispCoordinateCollection();
                HashMap fieldCoordCollection = mtModel.getFieldDispCoordinateCollection();
                HashMap lowerBoundCollection = mtModel.getLowerBoundCollection();
                HashMap upperBoundCollection = mtModel.getUpperBoundCollection();
                HashMap objectCoordCollection = mtModel.getObjectDispCoordinateCollection();
                HashMap dbAccessCollection = (HashMap)mtModel.getdbAccessCollection();
                HashMap tableDisplayHeightCollection = (HashMap)mtModel.gettableRowDisplayHeightCollection();
                HashMap labelByTableCollection = (HashMap)mtModel.getLabelByTableCollection();
                HashMap fieldByTableCollection = (HashMap)mtModel.getFieldByTableCollection();
                lookupTableDataCollection = (HashMap)mtModel.getLookupTableDataCollection();
                int rowCount  =  0;
                int colCount = 0;
                int dateEntries = 0;
                int panelRows = 0;
                String labelFont = "Serif";
                String valueFont = "Bookman Antiqua";
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
                gridBag.setConstraints(groupLabel, constraints);
                displayPanel.add(groupLabel);
                tablesTextList = new HashMap(mtModel.getTableCount());
                int tableOffset = 0;
                int dateOffset = 0;
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    if ((labelByTableCollection != null) && (labelByTableCollection.size() > 0))
                    {
                       labelCoordCollection =  (HashMap)labelByTableCollection.get((Object)theTableName);
                       fieldCoordCollection =  (HashMap)fieldByTableCollection.get((Object)theTableName);
                    }
                    
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
                    int dateLoop = 0;
                    if ((dateList == null) || (dateList.size() == 0))
                    { 
                        dateLoop = 1;
                    } else
                    {
                        dateLoop = dateList.size();
                    }
                    for(dateEntries = 0; dateEntries < rowCount; dateEntries++)//changed from dateloop to rowcount
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
                        boolean[] theColumnHighlights = dataInstance.dataTable.getColumnHighlights(); 
                        String thePanelHeading = (String)mtModel.getTableAliases().get((Object)theTableName);
                        thePanelHeading = mtModel.getKeyName();
                        JLabel panelHeadingLabel = new JLabel(thePanelHeading);
                        panelHeadingLabel.setForeground(new Color(0,0,255));
                        panelHeadingLabel.setPreferredSize(new Dimension(200,20));
                        panelHeadingLabel.setFont(new Font(labelFont,Font.BOLD,12));
                        panelHeadingLabel.setBorder(BorderFactory.createEtchedBorder());
            
                        String objectCoords = (String)objectCoordCollection.get((Object)"KEY_NAME");
                        constraints.gridwidth = 1;
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
                              if (theFieldName.indexOf("FILLER") == 0)
                              {
                                  continue;
                              }
                              String theFieldType = theColumnTypes[fieldIndex];
                              String theFieldValue = dataInstance.dataTable.getDataAt(dateEntries, fieldIndex);
                              theFieldValue = stripNulls(theFieldValue);
                              
                              int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                              boolean theFieldLock = theColumnLocks[fieldIndex];
                              boolean theFieldDisplay = theColumnDisplays[fieldIndex];
                              boolean theColumnHighlight = theColumnHighlights[fieldIndex];
                              JLabel fieldLabel = new JLabel(theFieldLabel);
                              fieldLabel.setBorder(loweredBevel);
                              //fieldLabel.setBackground(new Color(0,0,0));
                              if (theColumnHighlight ==  true)
                              {
                                  fieldLabel.setForeground(new Color(255,0,0));
                              } else
                              {
                                fieldLabel.setForeground(new Color(50,50,150));
                              }
                              fieldLabel.setBackground(new Color(255,255,255));
                              fieldLabel.setPreferredSize(new Dimension(150,20));
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
                               if ((theFieldType.equalsIgnoreCase("FOREIGN")) || (theFieldType.equalsIgnoreCase("GROUP")) || (theFieldType.equalsIgnoreCase("GROUPKEY")))
                               {
                                   lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theFieldName);
                                   Set keySet = lookupTableData.keySet();
                                   Iterator iter = keySet.iterator();
                                   boolean valueFound = false;
                                   while (iter.hasNext())
                                   {
                                       String theKey = (String)iter.next();
                                       String theValue = (String)lookupTableData.get((Object)theKey);
                                       if (theFieldValue.equalsIgnoreCase(theKey))
                                       {
                                           valueFound = true;
                                           selectedValue = theValue;
                                           break;
                                       }
                                    }
                                    if(valueFound == false)
                                    {
                                       selectedValue = "Not Applicable"; 
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
                               }  else
                               if (theFieldType.equalsIgnoreCase("FIELD_FOREIGN"))
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
                                    //if (theFieldDisplay == true) Always display this field
                                    //{
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
                                    //}
                                 } else
                                 if ((theFieldType.equalsIgnoreCase("BOOLEAN_FIELD")) || (theFieldType.equalsIgnoreCase("BOOLEAN_GROUP")))
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                        //do not display this field if it is empty as it uses up real estate
                                        if ((theFieldValue != null) && (theFieldValue.length() > 0))
                                        {
                                            addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                            addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, textArea);
                                        }
                                     }
                                 }   else
                                 if (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                                 {
                                     if (theFieldDisplay == true)
                                     {
                                        String[] docFileNames = getFileList(theFieldValue);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 2;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        for (int docIndex = 0; docIndex < docFileNames.length; docIndex++)
                                        {
                                            ImageIcon theImage = new ImageIcon(docScanPath + docFileNames[docIndex]);
                                            JLabel theLabel = new JLabel(theImage);
                                            JScrollPane scrollPane = new JScrollPane(theLabel);
                                            addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset + docIndex, scrollPane);
                                        }
                                     }
                                 } else
                                 if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                                 {
                                     String mask = "";
                                     for (int maskloop = 0; maskloop < fieldLength; maskloop++)
                                     {
                                         mask = mask + "*";
                                     }
                                     formatter = new  MaskFormatter(mask);

                                     //formatter = new  MaskFormatter("U U U");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if ((theFieldType.equalsIgnoreCase("SPINMONTHYEAR")) || (theFieldType.equalsIgnoreCase("SPINLITMONTHYEAR")))
                                 {
                                     //formatter = new  MaskFormatter("####-##-##");
                                     ftf = new JFormattedTextField();
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     String tmpField = formatDate(theFieldValue);
                                     //String tmpField = "Month=" + theFieldValue.substring(2,4) +"/Year" + theFieldValue.substring(0,2);
                                     ftf.setValue(tmpField);
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
                                 }     else
                                 if (theFieldType.equalsIgnoreCase("SPINTEMP"))
                                 {
                                     ftf = new JFormattedTextField();
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     String tmpField = theFieldValue;
                                     //String tmpField = "Month=" + theFieldValue.substring(2,4) +"/Year=" + theFieldValue.substring(0,2);
                                     ftf.setValue(tmpField);
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
                                 if (theFieldType.equalsIgnoreCase("GROUPDEPENDANT"))
                                 {
                                     String mask = "";
                                     for (int maskloop = 0; maskloop < fieldLength; maskloop++)
                                     {
                                         mask = mask + "*";
                                     }
                                     formatter = new  MaskFormatter(mask);
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if ((theFieldDisplay == true) && (theFieldValue.length() > 0))
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
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     if (lowerBoundCollection != null)
                                     {
                                         if (lowerBoundCollection.containsKey((Object)theFieldName))
                                         {
                                             String lowerValue = (String)lowerBoundCollection.get((Object)theFieldName);
                                             int intLowerValue = Integer.parseInt(lowerValue);
                                             String upperValue = (String)upperBoundCollection.get((Object)theFieldName);
                                             int intUpperValue = Integer.parseInt(upperValue);
                                             int intFieldValue = Integer.parseInt(theFieldValue);
                                             if (intFieldValue < intLowerValue)
                                             {
                                                  ftf.setBackground(new Color(120,120,255));
                                                  ftf.setForeground(new Color(150,150,150));
                                             } else
                                             if (intFieldValue > intUpperValue)
                                             {
                                                  ftf.setBackground(new Color(255,120,120));
                                                  ftf.setForeground(new Color(150,150,0));
                                             }
                                         }
                                     }
                                     
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
                    //--ccc---
                    dataInstance.closeConnection();
                    dbAccessCollection.remove((Object)theTableName);
                    dataInstance = null;
                    System.gc();

                    if (rowCount == 0)
                    {
                        isEmpty = true;
                    }
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
        
    
      public void initDateTabbedDataView(MTTableDataModel mtModel, int height, int width) throws MTException
      {
            try
            {
                HashMap labelCoordCollection = mtModel.getLabelDispCoordinateCollection();
                HashMap fieldCoordCollection = mtModel.getFieldDispCoordinateCollection();
                HashMap lowerBoundCollection = mtModel.getLowerBoundCollection();
                HashMap upperBoundCollection = mtModel.getUpperBoundCollection();
                HashMap objectCoordCollection = mtModel.getObjectDispCoordinateCollection();
                HashMap dbAccessCollection = (HashMap)mtModel.getdbAccessCollection();
                HashMap tableDisplayHeightCollection = (HashMap)mtModel.gettableRowDisplayHeightCollection();
                HashMap labelByTableCollection = (HashMap)mtModel.getLabelByTableCollection();
                HashMap fieldByTableCollection = (HashMap)mtModel.getFieldByTableCollection();
                lookupTableDataCollection = (HashMap)mtModel.getLookupTableDataCollection();
                int rowCount  =  0;
                int colCount = 0;
                int dateEntries = 0;
                int panelRows = 0;
                String labelFont = "Serif";
                String valueFont = "Bookman Antiqua";
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
                gridBag.setConstraints(groupLabel, constraints);
                displayPanel.add(groupLabel);
                tablesTextList = new HashMap(mtModel.getTableCount());
                int tableOffset = 0;
                int dateOffset = 0;
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    if ((labelByTableCollection != null) && (labelByTableCollection.size() > 0))
                    {
                       labelCoordCollection =  (HashMap)labelByTableCollection.get((Object)theTableName);
                       fieldCoordCollection =  (HashMap)fieldByTableCollection.get((Object)theTableName);
                    }
                    
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
                    int dateLoop = 0;
                    if ((dateList == null) || (dateList.size() == 0))
                    { 
                        dateLoop = 1;
                    } else
                    {
                        dateLoop = dateList.size();
                    }
                    for(dateEntries = 0; dateEntries < rowCount; dateEntries++)//changed from dateloop to rowcount
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
                        boolean[] theColumnHighlights = dataInstance.dataTable.getColumnHighlights(); 
                        String thePanelHeading = (String)mtModel.getTableAliases().get((Object)theTableName);
                        thePanelHeading = mtModel.getKeyName();
                        JLabel panelHeadingLabel = new JLabel(thePanelHeading);
                        panelHeadingLabel.setForeground(new Color(0,0,255));
                        panelHeadingLabel.setPreferredSize(new Dimension(200,20));
                        panelHeadingLabel.setFont(new Font(labelFont,Font.BOLD,12));
                        panelHeadingLabel.setBorder(BorderFactory.createEtchedBorder());
            
                        String objectCoords = (String)objectCoordCollection.get((Object)"KEY_NAME");
                        constraints.gridwidth = 1;
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
                              if (theFieldName.indexOf("FILLER") == 0)
                              {
                                  continue;
                              }
                                 
                              String theFieldType = theColumnTypes[fieldIndex];
                              String theFieldValue = dataInstance.dataTable.getDataAt(dateEntries, fieldIndex);
                              theFieldValue = stripNulls(theFieldValue);
                              
                              int fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(theFieldName));
                              boolean theFieldLock = theColumnLocks[fieldIndex];
                              boolean theFieldDisplay = theColumnDisplays[fieldIndex];
                              boolean theColumnHighlight = theColumnHighlights[fieldIndex];
                              JLabel fieldLabel = new JLabel(theFieldLabel);
                              fieldLabel.setBorder(loweredBevel);
                              //fieldLabel.setBackground(new Color(0,0,0));
                              if (theColumnHighlight ==  true)
                              {
                                  fieldLabel.setForeground(new Color(255,0,0));
                              } else
                              {
                                fieldLabel.setForeground(new Color(50,50,150));
                              }
                              fieldLabel.setBackground(new Color(255,255,255));
                              fieldLabel.setPreferredSize(new Dimension(150,20));
                              
                              if (fieldIndex == 0)
                              {
                                    if ((dateList != null)  && (dateList.containsKey((Object)theFieldValue)))
                                    {
                                         dateTabField = (String)dateList.get((Object)theFieldValue);
      
                                     ftf = new JFormattedTextField();
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     String tmpString = dateTabField.substring(0,16);
                                     ftf.setValue(tmpString);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     JLabel dateLabel = new JLabel("Capture Date:");
                                        String labelCoords = (String)labelCoordCollection.get((Object)"CAPTURE_DATE");
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)"CAPTURE_DATE");
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, dateLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
       
                                        
                                    } else
                                    {
                                        dateTabField = "Null"; 
                                    }
                                    //rightTabbedPane.addTab(dateTabField,null,dataPanel,"Capture Date");
                              }

                              
                              
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
                               if ((theFieldType.equalsIgnoreCase("FOREIGN")) || (theFieldType.equalsIgnoreCase("GROUP")) || (theFieldType.equalsIgnoreCase("GROUPKEY")))
                               {
                                   lookupTableData = (HashMap)lookupTableDataCollection.get((Object)theFieldName);
                                   Set keySet = lookupTableData.keySet();
                                   Iterator iter = keySet.iterator();
                                   boolean valueFound = false;
                                   while (iter.hasNext())
                                   {
                                       String theKey = (String)iter.next();
                                       String theValue = (String)lookupTableData.get((Object)theKey);
                                       if (theFieldValue.equalsIgnoreCase(theKey))
                                       {
                                           valueFound = true;
                                           selectedValue = theValue;
                                           break;
                                       }
                                    }
                                    if(valueFound == false)
                                    {
                                       selectedValue = "Not Applicable"; 
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
                               }  else
                               if (theFieldType.equalsIgnoreCase("FIELD_FOREIGN"))
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
                                    //if (theFieldDisplay == true) Always display this field
                                    //{
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
                                    //}
                                 } else
                                 if ((theFieldType.equalsIgnoreCase("BOOLEAN_FIELD")) || (theFieldType.equalsIgnoreCase("BOOLEAN_GROUP")))
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                        //do not display this field if it is empty as it uses up real estate
                                        if ((theFieldValue != null) && (theFieldValue.length() > 0))
                                        {
                                            addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                            addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, textArea);
                                        }
                                     }
                                 }   else
                                 if (theFieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                                 {
                                     if (theFieldDisplay == true)
                                     {
                                        String[] docFileNames = getFileList(theFieldValue);
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 2;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        for (int docIndex = 0; docIndex < docFileNames.length; docIndex++)
                                        {
                                            ImageIcon theImage = new ImageIcon(docScanPath + docFileNames[docIndex]);
                                            JLabel theLabel = new JLabel(theImage);
                                            JScrollPane scrollPane = new JScrollPane(theLabel);
                                            addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset + docIndex, scrollPane);
                                        }
                                     }
                                 } else
                                 if (theFieldType.equalsIgnoreCase("INITIAL_FIELD"))
                                 {
                                     String mask = "";
                                     for (int maskloop = 0; maskloop < fieldLength; maskloop++)
                                     {
                                         mask = mask + "*";
                                     }
                                     formatter = new  MaskFormatter(mask);

                                     //formatter = new  MaskFormatter("U U U");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                     ftf.setPreferredSize(new Dimension(250,20));
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
                                 if ((theFieldType.equalsIgnoreCase("SPINMONTHYEAR")) || (theFieldType.equalsIgnoreCase("SPINLITMONTHYEAR")))
                                 {
                                     //formatter = new  MaskFormatter("####-##-##");
                                     ftf = new JFormattedTextField();
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     String tmpField = formatDate(theFieldValue);
                                     ftf.setValue(tmpField);
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
                                 }   else
                                 if (theFieldType.equalsIgnoreCase("GROUPDEPENDANT"))
                                 {
                                     String mask = "";
                                     for (int maskloop = 0; maskloop < fieldLength; maskloop++)
                                     {
                                         mask = mask + "*";
                                     }
                                     formatter = new  MaskFormatter(mask);
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     ftf.setDisabledTextColor(new Color(0,0,50));
                                     ftf.setFont(new Font(valueFont,1,13));
                                     ftf.setBorder(loweredBevel);
                                     ftf.setEnabled(false);
                                     if ((theFieldDisplay == true) && (theFieldValue.length() > 0))
                                     {
                                        String labelCoords = (String)labelCoordCollection.get((Object)theFieldName);
                                        String fieldCoords = (String)fieldCoordCollection.get((Object)theFieldName);
                                        constraints.gridwidth = 1;
                                        addItemToPanel(displayPanel, gridBag, constraints, labelCoords, dateOffset + tableOffset, fieldLabel);
                                        addItemToPanel(displayPanel, gridBag, constraints, fieldCoords, dateOffset + tableOffset, ftf);
                                     }
                                 }     else
                                 if (theFieldType.equalsIgnoreCase("SPINTEMP"))
                                 {
                                     ftf = new JFormattedTextField();
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     String tmpField = theFieldValue;
                                     //String tmpField = "Month=" + theFieldValue.substring(2,4) +"/Year=" + theFieldValue.substring(0,2);
                                     ftf.setValue(tmpField);
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
                                     ftf.setPreferredSize(new Dimension(250,20));
                                     ftf.setValue(theFieldValue);
                                     ftf.setBackground(new Color(255,255,255));
                                     ftf.setForeground(new Color(0,0,50));
                                     if (lowerBoundCollection != null)
                                     {
                                         if (lowerBoundCollection.containsKey((Object)theFieldName))
                                         {
                                             String lowerValue = (String)lowerBoundCollection.get((Object)theFieldName);
                                             int intLowerValue = Integer.parseInt(lowerValue);
                                             String upperValue = (String)upperBoundCollection.get((Object)theFieldName);
                                             int intUpperValue = Integer.parseInt(upperValue);
                                             int intFieldValue = Integer.parseInt(theFieldValue);
                                             if (intFieldValue < intLowerValue)
                                             {
                                                  ftf.setBackground(new Color(120,120,255));
                                                  ftf.setForeground(new Color(150,150,150));
                                             } else
                                             if (intFieldValue > intUpperValue)
                                             {
                                                  ftf.setBackground(new Color(255,120,120));
                                                  ftf.setForeground(new Color(150,150,0));
                                             }
                                         }
                                     }
                                     
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

                                               

                    } //end of datelist entries
                    //--ccc---
                    dataInstance.closeConnection();
                    dbAccessCollection.remove((Object)theTableName);
                    dataInstance = null;
                    System.gc();

                    tableOffset += dateOffset;
                    if (rowCount == 0)
                    {
                        isEmpty = true;
                    }

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

   private Color getColour(String inColour)
   {
       
               if (inColour.equalsIgnoreCase("ROYALBLUE") == true)
               {
                   return new Color(65,105,225);
               } else
               if (inColour.equalsIgnoreCase("GRAY") == true)
               {
                   return new Color(224,224,224);
               } else
               if (inColour.equalsIgnoreCase("SILVER") == true)
               {
                   return new Color(192,192,192);
               } else
               if (inColour.equalsIgnoreCase("BLACK") == true)
               {
                   return new Color(0,0,0);
               } else
               if (inColour.equalsIgnoreCase("WHITE") == true)
               {
                   return new Color(255,255,255);
               } else
               if (inColour.equalsIgnoreCase("BLUE") == true)
               {
                   return new Color(0,0,255);
               } else
               if (inColour.equalsIgnoreCase("RED") == true)
               {
                   return new Color(255,0,0);
               } else
               if (inColour.equalsIgnoreCase("GREEN") == true)
               {
                   return new Color(0,255,0);
               } else
               if (inColour.equalsIgnoreCase("YELLOW") == true)
               {
                   return new Color(255,255,0);
               } else
               if (inColour.equalsIgnoreCase("LIGHTYELLOW") == true)
               {
                   return new Color(255,255,128);
               } else
               if (inColour.equalsIgnoreCase("MAGENTA") == true)
               {
                   return new Color(255,0,255);
               } else
               if (inColour.equalsIgnoreCase("CYAN") == true)
               {
                   return new Color(0,255,255);
               } else
                   return new Color(0,0,0);
   }
        
        
    
   public void buildDisplayPage(JPanel displayPanel, ArrayList pageItemList,HashMap pageItemToTypeMap, HashMap staticTextItemMap, HashMap textItemMap, HashMap tableItemMap, HashMap imageItemMap, HashMap tableVarList, HashMap looseTextItemList, int columnInCount, HashMap noDisplayItemList, HashMap textLabelComboItemMap, HashMap labelTextVarMap)
   {
       //groupLabel.setBorder(BorderFactory.createEtchedBorder()); add this later
       //note Column In Count is not used yet and comes from xml file pages attribute.
       GridBagLayout gridBag = new GridBagLayout();
       JTable  displayTable = null;
       GridBagConstraints constraints = new GridBagConstraints();
       constraints.fill = GridBagConstraints.HORIZONTAL;
       constraints.anchor = GridBagConstraints.CENTER;
       int fontStyleInt = 0;
       int labelFontStyleInt = 0;
       constraints.gridheight = 1;
       constraints.gridwidth = 1;
       constraints.weightx = 1.0;
       constraints.weighty = 1.0;
       constraints.gridx = 1;
       constraints.gridy = 1;
       HashMap colItemWidthMap = null;
       HashMap colHeadingWidthMap = null;
       HashMap fieldVarList = null;
       HashMap tableNameToHeadersLink = new HashMap(10);
       String theTableName = null;
       int rowCount = 1;
       
       Set tableVarKeySet = tableVarList.keySet();
       Iterator tableVarIter = tableVarKeySet.iterator();
       while (tableVarIter.hasNext())
       {
          theTableName = (String)tableVarIter.next();
          fieldVarList = (HashMap)tableVarList.get((Object)theTableName);
          break;
       }

       displayPanel.setLayout(gridBag);
       for (int itemLoop = 0; itemLoop < pageItemList.size(); itemLoop++)
       {
           String itemName = (String)pageItemList.get(itemLoop);
           String itemType = (String)pageItemToTypeMap.get((Object)itemName);
           if (itemType.equalsIgnoreCase("LITERALTEXTITEM") == true)
           {
               HashMap textItemDisplayMapData = (HashMap)textItemMap.get((Object)itemName);
               HashMap fieldVarItems = (HashMap)fieldVarList.get((Object)itemName);
               
               String YPos = (String)textItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)textItemDisplayMapData.get((Object)"COLPOS");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               String fontSize = (String)textItemDisplayMapData.get((Object)"FONTSIZE");
               String fontType = (String)textItemDisplayMapData.get((Object)"FONTTYPE");
               String fontStyle = (String)textItemDisplayMapData.get((Object)"FONTSTYLE");
               String foreColour = (String)textItemDisplayMapData.get((Object)"FORECOLOUR");
               String backColour = (String)textItemDisplayMapData.get((Object)"BACKCOLOUR");
               String fillDown = (String)textItemDisplayMapData.get((Object)"FILLDOWN");
               String maxRows = (String)textItemDisplayMapData.get((Object)"FILLMAXROWS");
               String editableField = (String)textItemDisplayMapData.get((Object)"EDITABLE");
               boolean isEditableField = Boolean.getBoolean(editableField);
               boolean isFillDown = Boolean.getBoolean(fillDown);
               int intMaxRows = Integer.parseInt(maxRows);
               if (isFillDown == false)
               {
                 intMaxRows = 1;  
               }
               String fieldValue = (String)looseTextItemList.get((Object)itemName);
               
               int intFontSize = Integer.parseInt(fontSize);
               Color intForeColour = getColour(foreColour);
               Color intBackColour = getColour(backColour);

               if (fontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  fontStyleInt = Font.PLAIN; 
               } else
               if (fontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  fontStyleInt = Font.BOLD; 
               } else
               if (fontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  fontStyleInt = Font.ITALIC; 
               }
               JTextField fieldText = new JTextField(fieldValue);
               fieldText.setPreferredSize(new Dimension(200,20));
               fieldText.setBackground(intBackColour);
               fieldText.setForeground(intForeColour);
               fieldText.setFont(new Font(fontType,fontStyleInt,intFontSize));
               //fieldText.setBorder(loweredBevel);
               if (isEditableField) 
               {
                   fieldText.setEnabled(true);
               } else {
                   fieldText.setEnabled(false);
               }
               fieldText.setEnabled(true);
               fieldText.setEditable(false);
               constraints.gridheight = intMaxRows;
               //check
               constraints.gridx = intXPos;
               constraints.gridy = intYPos;
               if (noDisplayItemList != null)
               {
                   String itemNoDisplayValue = (String)noDisplayItemList.get((Object)itemName); 
                   if (itemNoDisplayValue != null)
                   {
                        if (itemNoDisplayValue.equalsIgnoreCase(fieldValue) == false)
                        {
                            addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                        }
                   } else
                   {
                      addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                   }
               } else
               {
                   addItemToPanel(displayPanel, gridBag, constraints, fieldText);
               }
               
           } else
           if (itemType.equalsIgnoreCase("TEXTLABELCOMBOITEM") == true)
           {
               HashMap textLabelItemDisplayMapData = (HashMap)textLabelComboItemMap.get((Object)itemName);
               
               String YPos = (String)textLabelItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)textLabelItemDisplayMapData.get((Object)"COLPOS");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               String fontSize = (String)textLabelItemDisplayMapData.get((Object)"FONTSIZE");
               String fontType = (String)textLabelItemDisplayMapData.get((Object)"FONTTYPE");
               String fontStyle = (String)textLabelItemDisplayMapData.get((Object)"FONTSTYLE");
               String foreColour = (String)textLabelItemDisplayMapData.get((Object)"FORECOLOUR");
               String backColour = (String)textLabelItemDisplayMapData.get((Object)"BACKCOLOUR");
               String labelFontSize = (String)textLabelItemDisplayMapData.get((Object)"LABELFONTSIZE");
               String labelFontType = (String)textLabelItemDisplayMapData.get((Object)"LABELFONTTYPE");
               String labelFontStyle = (String)textLabelItemDisplayMapData.get((Object)"LABELFONTSTYLE");
               String labelForeColour = (String)textLabelItemDisplayMapData.get((Object)"LABELFORECOLOUR");
               String labelBackColour = (String)textLabelItemDisplayMapData.get((Object)"LABELBACKCOLOUR");
               String labelTextFlow = (String)textLabelItemDisplayMapData.get((Object)"LABELTEXTFLOW");
               String labelFirst = (String)textLabelItemDisplayMapData.get((Object)"LABELFIRST");
               String editableField = (String)textLabelItemDisplayMapData.get((Object)"EDITABLE");
               String tableLink = (String)textLabelItemDisplayMapData.get((Object)"TABLELINK");
               HashMap tableVarItems = (HashMap)(HashMap)tableVarList.get((Object)tableLink);
               
               boolean isEditableField;
               if (editableField.equalsIgnoreCase("Y") == true)
               {
                  isEditableField = true;
               } else
               {
                  isEditableField = false;
               }
               boolean isLabelFirst;
               if (labelFirst.equalsIgnoreCase("Y") == true)
               {
                  isLabelFirst = true;
               } else
               {
                  isLabelFirst = false;
               }

               ArrayList varList = (ArrayList)tableVarItems.get((Object)tableLink);
               ArrayList colValuesAndVars = (ArrayList)tableVarItems.get((Object)itemName);
               rowCount = colValuesAndVars.size();

               HashMap colVarMap =  (HashMap)colValuesAndVars.get(0);
               String fieldValue = (String)colVarMap.get((Object)"FIELDVALUE");
               String fieldLength = (String)colVarMap.get((Object)"FIELDLENGTH");
               String fieldType = (String)colVarMap.get((Object)"FIELDTYPE");
               String fieldLock = (String)colVarMap.get((Object)"FIELDLOCK");
               String fieldDisplay = (String)colVarMap.get((Object)"FIELDDISPLAY");
               String fieldHighlight = (String)colVarMap.get((Object)"FIELDHIGHLIGHT");
               String fieldLabelDisplay = (String)colVarMap.get((Object)"LABELDISPLAY");
               String labelValue = (String)colVarMap.get((Object)"LABELVALUE"); //this is the table heading

               
               int intFontSize = Integer.parseInt(fontSize);
               int intLabelFontSize = Integer.parseInt(labelFontSize);

               Color intForeColour = getColour(foreColour);
               Color intBackColour = getColour(backColour);

               Color intLabelForeColour = getColour(labelForeColour);
               Color intLabelBackColour = getColour(labelBackColour);

               if (fontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  fontStyleInt = Font.PLAIN; 
               } else
               if (fontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  fontStyleInt = Font.BOLD; 
               } else
               if (fontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  fontStyleInt = Font.ITALIC; 
               }
               
               if (labelFontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  labelFontStyleInt = Font.PLAIN; 
               } else
               if (labelFontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  labelFontStyleInt = Font.BOLD; 
               } else
               if (labelFontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  labelFontStyleInt = Font.ITALIC; 
               }

               JTextField fieldText = new JTextField(fieldValue);
               fieldText.setPreferredSize(new Dimension(200,20));
               fieldText.setBackground(intBackColour);
               fieldText.setForeground(intForeColour);
               fieldText.setFont(new Font(fontType,fontStyleInt,intFontSize));

               JTextField fieldLabel = new JTextField(labelValue);
               //JLabel  = new JLabel();
               fieldLabel.setPreferredSize(new Dimension(200,20));
               fieldLabel.setBackground(intLabelBackColour);
               fieldLabel.setForeground(intLabelForeColour);
               fieldLabel.setFont(new Font(labelFontType,labelFontStyleInt,intLabelFontSize));
               
               //fieldText.setBorder(loweredBevel);
               if (isEditableField) {
                   fieldText.setEnabled(true);
               } else {
                   fieldText.setEnabled(false);
               }
               fieldText.setEnabled(true);
               fieldText.setEditable(false);
               constraints.gridheight = 1;

               constraints.gridx = intXPos;
               constraints.gridy = intYPos;
               if ((fieldValue != null) && (fieldValue.length() > 0) && (fieldValue.trim().equalsIgnoreCase("null") == false))
               {
               if (noDisplayItemList != null)
               {
                   String itemNoDisplayValue = (String)noDisplayItemList.get((Object)itemName); 
                   if (itemNoDisplayValue != null)
                   {
                        if (itemNoDisplayValue.equalsIgnoreCase(fieldValue) == false)
                        {
                            if (isLabelFirst == true) {
                                if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                    constraints.gridx = intXPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                } else {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                    constraints.gridy = intYPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                }
                            } else {
                                if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                    constraints.gridx = intXPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                } else {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                    constraints.gridy = intYPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                }
                            }
                        }
                   } else
                   {
                       if (isLabelFirst == true) {
                           if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                               constraints.gridx = intXPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                           } else {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                               constraints.gridy = intYPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                           }
                       } else {
                           if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                               constraints.gridx = intXPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                           } else {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                               constraints.gridy = intYPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                           }
                       }
                   }
               } else
               {
                   if (isLabelFirst == true)
                   {
                       if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true)
                       {    
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                          constraints.gridx = intXPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                       } else
                       {
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                          constraints.gridy = intYPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                       }
                   } else
                   {
                       if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true)
                       {    
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                          constraints.gridx = intXPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                       } else
                       {
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                          constraints.gridy = intYPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                       }
                   }
               }
              }
               
           } else
           if (itemType.equalsIgnoreCase("STATICTEXTITEM") == true)
           {
               HashMap textItemDisplayMapData = (HashMap)staticTextItemMap.get((Object)itemName);
               HashMap fieldVarItems = (HashMap)fieldVarList.get((Object)itemName);
               
               String YPos = (String)textItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)textItemDisplayMapData.get((Object)"COLPOS");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               String fontSize = (String)textItemDisplayMapData.get((Object)"FONTSIZE");
               String fontType = (String)textItemDisplayMapData.get((Object)"FONTTYPE");
               String fontStyle = (String)textItemDisplayMapData.get((Object)"FONTSTYLE");
               String foreColour = (String)textItemDisplayMapData.get((Object)"FORECOLOUR");
               String backColour = (String)textItemDisplayMapData.get((Object)"BACKCOLOUR");
               String fillDown = (String)textItemDisplayMapData.get((Object)"FILLDOWN");
               String maxRows = (String)textItemDisplayMapData.get((Object)"FILLMAXROWS");
               String editableField = (String)textItemDisplayMapData.get((Object)"EDITABLE");
               String fieldValue = (String)textItemDisplayMapData.get((Object)"ITEMVALUE");
               
               String tableLink  = (String)textItemDisplayMapData.get((Object)"TABLELINK");
               boolean isEditableField = Boolean.getBoolean(editableField);
               boolean isFillDown = Boolean.getBoolean(fillDown);
               int intMaxRows = Integer.parseInt(maxRows);
               if (isFillDown == false)
               {
                 intMaxRows = 1;  
               }
               int intFontSize = Integer.parseInt(fontSize);
               Color intForeColour = getColour(foreColour);
               Color intBackColour = getColour(backColour);

               if (fontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  fontStyleInt = Font.PLAIN; 
               } else
               if (fontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  fontStyleInt = Font.BOLD; 
               } else
               if (fontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  fontStyleInt = Font.ITALIC; 
               }
               JTextField fieldText = new JTextField(fieldValue);
               fieldText.setPreferredSize(new Dimension(200,20));
               fieldText.setBackground(intBackColour);
               fieldText.setForeground(intForeColour);
               fieldText.setFont(new Font(fontType,fontStyleInt,intFontSize));
               //fieldText.setBorder(loweredBevel);
               if (isEditableField) {
                   fieldText.setEnabled(true);
               } else 
               {
                   fieldText.setEnabled(false);
               }
               fieldText.setEnabled(true);
               fieldText.setEditable(false);
               constraints.gridheight = intMaxRows;
               constraints.gridwidth = 1;
               
               constraints.gridx = intXPos;
               constraints.gridy = intYPos;
               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
               tableNameToHeadersLink.put((Object)tableLink,(Object)fieldText);
               
               //displayPanel.remove(fieldText);
               
               
           } else           
           if (itemType.equalsIgnoreCase("TABLEITEM") == true)
           {

               HashMap tableItemDisplayMapData = (HashMap)tableItemMap.get((Object)itemName);
               fieldVarList = (HashMap)tableVarList.get((Object)itemName);
               if ((fieldVarList != null) && (fieldVarList.size() > 0))
               {
                   
               String YPos = (String)tableItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)tableItemDisplayMapData.get((Object)"COLPOS");
               String gridWidth = (String)tableItemDisplayMapData.get((Object)"GRIDWIDTH");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               //int rowCount = 0;
               int colCount = Integer.parseInt((String)tableItemDisplayMapData.get((Object)"COLCOUNT"));
               ArrayList colNames = (ArrayList)tableItemDisplayMapData.get((Object)"COLNAMELIST");
               ArrayList colForegrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLFOREGROUNDLIST");
               ArrayList colBackgrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLBACKGROUNDLIST");
               ArrayList colHeadingForegrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLHEADINGFOREGROUNDLIST");
               ArrayList colHeadingBackgrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLHEADINGBACKGROUNDLIST");
               Object[][] tableData = null;
               Object[] tableHeadingData = null;
               int[] tableHeadingLengths = null;
               int[] colItemWidthList = null;
               ArrayList columnsToBeDisplayed = null;
               int colNameDispIndex = 0;
               int dispColCount = 0;
               boolean allDefaults = true;
               
                   
               //loop through all the columns for this table to check if this column should be displayed
               for (int colNameLoop = 0; colNameLoop < colCount; colNameLoop++)
               {
                   String colName = (String)colNames.get(colNameLoop);
                   ArrayList colValuesAndVars = (ArrayList)fieldVarList.get((Object)colName);
                   rowCount = colValuesAndVars.size();
                   //now check if there is a list of items that contains the values that must not be displayed
                   if (noDisplayItemList != null) 
                   {
                        String itemNoDisplayValue = (String)noDisplayItemList.get((Object)colName);
                        //check if the list contains the column data that must be checked
                        if ((itemNoDisplayValue != null) && (itemNoDisplayValue.length() > 0))
                        {
                            //now loop through the row data to check the actual values. 
                            for (int valueLoop1 = 0; valueLoop1 < rowCount; valueLoop1++) 
                            {
                                HashMap colVarMap =  (HashMap)colValuesAndVars.get(valueLoop1);
                                String fieldValue = (String)colVarMap.get((Object)"FIELDVALUE");
                                //if this column contains values that must be displayed then this column must be added to table 
                                if ((fieldValue != null)  && (fieldValue.equalsIgnoreCase(itemNoDisplayValue) == false) && (fieldValue.trim().equalsIgnoreCase("null") == false) )
                                {
                                    allDefaults = false;
                                    //add this column to the column array that must be displayed
                                    if (columnsToBeDisplayed != null)
                                    {
                                        columnsToBeDisplayed.add((Object)colName);
                                    } else
                                    {
                                        columnsToBeDisplayed = new ArrayList(colCount);
                                        columnsToBeDisplayed.add((Object)colName);
                                    }
                                    break;
                                }
                            }
                        } else
                        {
                             if (columnsToBeDisplayed != null)
                             {
                                 columnsToBeDisplayed.add((Object)colName);
                             } else
                             {
                                 columnsToBeDisplayed = new ArrayList(colCount);
                                 columnsToBeDisplayed.add((Object)colName);
                             }
                        }
                   }
               }
               if (columnsToBeDisplayed != null)
               {
                   //this will use columnsToBeDisplayed to determine which colums must be displayed
                   dispColCount = columnsToBeDisplayed.size();
                   tableHeadingData = new String[dispColCount];
                   colItemWidthList = new int[dispColCount];
                   tableHeadingLengths = new int[dispColCount];
                   int checkloop = 0;
                   
                   for (int colNameLoop = 0; colNameLoop < colCount; colNameLoop++) {
                       String colName = (String)colNames.get(colNameLoop);
                       boolean containsName = false;
                       for (checkloop = 0; checkloop < columnsToBeDisplayed.size(); checkloop++)
                       {
                           if (((String)columnsToBeDisplayed.get(checkloop)).equalsIgnoreCase(colName) == true)
                           {
                              containsName = true;
                              break;
                           }
                       }

                       if (containsName == true)
                       {
                           String colForeground = (String)colForegrounds.get(colNameLoop);
                           String colbackground = (String)colBackgrounds.get(colNameLoop);
                           String colHeadingForeground = (String)colHeadingForegrounds.get(colNameLoop);
                           String colHeadingBackground = (String)colHeadingBackgrounds.get(colNameLoop);
                           ArrayList colValuesAndVars = (ArrayList)fieldVarList.get((Object)colName);
                           rowCount = colValuesAndVars.size();
                           if (tableData == null)
                           {
                               tableData = new String[rowCount][dispColCount];
                           }
                           for (int valueLoop = 0; valueLoop < rowCount; valueLoop++) {
                               HashMap colVarMap =  (HashMap)colValuesAndVars.get(valueLoop);
                               String fieldValue = (String)colVarMap.get((Object)"FIELDVALUE");
                               String fieldLength = (String)colVarMap.get((Object)"FIELDLENGTH");
                               String fieldType = (String)colVarMap.get((Object)"FIELDTYPE");
                               String fieldLock = (String)colVarMap.get((Object)"FIELDLOCK");
                               String fieldDisplay = (String)colVarMap.get((Object)"FIELDDISPLAY");
                               String fieldHighlight = (String)colVarMap.get((Object)"FIELDHIGHLIGHT");
                               String fieldLabelDisplay = (String)colVarMap.get((Object)"LABELDISPLAY");
                               String fieldLabelValue = (String)colVarMap.get((Object)"LABELVALUE"); //this is the table heading
                               //check if label must be displayed and put blank or data in next code
                               tableHeadingData[colNameDispIndex] = fieldLabelValue;
                               tableHeadingLengths[colNameDispIndex] = fieldLabelValue.length();
                               tableData[valueLoop][colNameDispIndex] = fieldValue;
                               int itemLength = fieldValue.length();
                               int itemWidth = colItemWidthList[colNameDispIndex];
                               if (itemLength > itemWidth) {
                                   colItemWidthList[colNameDispIndex] = itemLength;
                               }
                           }
                           colNameDispIndex++;
                       }
                   } //end colname loop
                   if (rowCount > 0)
                   {
                       CustomTableModel customModel = new CustomTableModel((Object[][])tableData.clone(), (Object[])tableHeadingData.clone());
                       //TableSorter sorter = new TableSorter(customModel);
                       //tableData = null;
                       //tableHeadingData = null;
                       displayTable = new JTable(customModel);
                       displayTable.addMouseListener(new JTableButtonMouseListener(displayTable));
                       int tableHeight = rowCount * 40;
                       displayTable.setSize(new Dimension(1000, tableHeight));
                       displayTable.setPreferredSize(new Dimension(1000, tableHeight));
                       displayTable.setMinimumSize(new Dimension(1000, tableHeight));
                       displayTable.setAutoResizeMode(displayTable.AUTO_RESIZE_OFF);
                       displayTable.setBackground(new Color(150,200,255));
                       displayTable.setPreferredScrollableViewportSize(new Dimension(1000, tableHeight));
                       displayTable.setShowHorizontalLines(false);
                       for (int colSetLoop = 0; colSetLoop < dispColCount; colSetLoop++) {
                           boolean useHeadingWidth = false;
                           if (tableHeadingLengths[colSetLoop] > colItemWidthList[colSetLoop]) {
                               useHeadingWidth = true;
                           }
                           TableColumn column = displayTable.getColumnModel().getColumn(colSetLoop);
                           if(useHeadingWidth) {
                               column.setPreferredWidth(tableHeadingLengths[colSetLoop]*10);
                           } else {
                               column.setPreferredWidth(colItemWidthList[colSetLoop]*10);
                           }
                           column.setMaxWidth(400);
                           //column;
                           //column.setAutoResizeMode(displayTable.AUTO_RESIZE_OFF);
                           //column.sizeWidthToFit();
                           //column.setResizable(true);
                           
                       }
                       JScrollPane scrollPane =  new JScrollPane(displayTable);
                       scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                       scrollPane.setMinimumSize(new Dimension(1000, tableHeight));
                       scrollPane.setPreferredSize(new Dimension(1000, tableHeight));
                       constraints.gridwidth = colCount*2;
                       constraints.gridheight = rowCount + 1;
                       constraints.gridx = intXPos;
                       constraints.gridy = intYPos;
                       addItemToPanel(displayPanel, gridBag, constraints, scrollPane);
                   } // end if rowcount > 0
               //===================
               } else //columnsToBeDisplayed != null
               {
                  //table wont be displayed so  remove table heading
                  JTextField hfText = (JTextField)tableNameToHeadersLink.get((Object)itemName);
                  if (hfText != null)
                  {
                      displayPanel.remove(hfText);
                  }
               } //columnsToBeDisplayed == null
               } else   // end fieldvarlist
               {
                  //table wont be displayed so  remove table heading
                  JTextField hfText = (JTextField)tableNameToHeadersLink.get((Object)itemName);
                  if (hfText != null)
                  {
                      displayPanel.remove(hfText);
                  }
                   
               }
           } else           
           if (itemType.equalsIgnoreCase("IMAGEITEM") == true)
           {
               
           }           
       }
       //addItemToPanel(JPanel _panel, GridBagLayout _gridBag, GridBagConstraints _constraints, String XPos, String YPos, Component component)
    }

    public int getPreferredRowHeight(JTable table, int rowIndex, int margin) {
        // Get the current default height for all rows
        int height = table.getRowHeight();
    
        // Determine highest cell in the row
        for (int c=0; c<table.getColumnCount(); c++) {
            TableCellRenderer renderer = table.getCellRenderer(rowIndex, c);
            Component comp = table.prepareRenderer(renderer, rowIndex, c);
            int h = comp.getPreferredSize().height + 2*margin;
            height = Math.max(height, h);
        }
        return height;
    }

    public void buildDisplayPage(JPanel displayPanel, ArrayList pageItemList,HashMap pageItemToTypeMap, HashMap staticTextItemMap, HashMap textItemMap, HashMap tableItemMap, HashMap imageItemMap, HashMap tableVarList, HashMap looseTextItemList, int columnInCount, HashMap noDisplayItemList, HashMap textLabelComboItemMap, HashMap labelTextVarMap, HashMap dateByTableMap)
   {
       //groupLabel.setBorder(BorderFactory.createEtchedBorder()); add this later
       //note Column In Count is not used yet and comes from xml file pages attribute.
       GridBagLayout gridBag = new GridBagLayout();
       JTable  displayTable = null;
       GridBagConstraints constraints = new GridBagConstraints();
       constraints.fill = GridBagConstraints.HORIZONTAL;
       constraints.anchor = GridBagConstraints.CENTER;
       int fontStyleInt = 0;
       int labelFontStyleInt = 0;
       constraints.gridheight = 1;
       constraints.gridwidth = 1;
       constraints.weightx = 1.0;
       constraints.weighty = 1.0;
       constraints.gridx = 1;
       constraints.gridy = 1;
       HashMap colItemWidthMap = null;
       HashMap colHeadingWidthMap = null;
       HashMap fieldVarList = null;
       HashMap tableNameToHeadersLink = new HashMap(10);
       String theTableName = null;
       int rowCount = 1;
       
       
       
       Set tableVarKeySet = tableVarList.keySet();
       Iterator tableVarIter = tableVarKeySet.iterator();
       while (tableVarIter.hasNext())
       {
          theTableName = (String)tableVarIter.next();
          fieldVarList = (HashMap)tableVarList.get((Object)theTableName);
          break;
       }

       displayPanel.setLayout(gridBag);
       for (int itemLoop = 0; itemLoop < pageItemList.size(); itemLoop++)
       {
           String itemName = (String)pageItemList.get(itemLoop);
           String itemType = (String)pageItemToTypeMap.get((Object)itemName);
           if (itemType.equalsIgnoreCase("LITERALTEXTITEM") == true)
           {
               HashMap textItemDisplayMapData = (HashMap)textItemMap.get((Object)itemName);
               HashMap fieldVarItems = (HashMap)fieldVarList.get((Object)itemName);
               
               String YPos = (String)textItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)textItemDisplayMapData.get((Object)"COLPOS");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               String fontSize = (String)textItemDisplayMapData.get((Object)"FONTSIZE");
               String fontType = (String)textItemDisplayMapData.get((Object)"FONTTYPE");
               String fontStyle = (String)textItemDisplayMapData.get((Object)"FONTSTYLE");
               String foreColour = (String)textItemDisplayMapData.get((Object)"FORECOLOUR");
               String backColour = (String)textItemDisplayMapData.get((Object)"BACKCOLOUR");
               String fillDown = (String)textItemDisplayMapData.get((Object)"FILLDOWN");
               String maxRows = (String)textItemDisplayMapData.get((Object)"FILLMAXROWS");
               String editableField = (String)textItemDisplayMapData.get((Object)"EDITABLE");
               boolean isEditableField = Boolean.getBoolean(editableField);
               boolean isFillDown = Boolean.getBoolean(fillDown);
               int intMaxRows = Integer.parseInt(maxRows);
               if (isFillDown == false)
               {
                 intMaxRows = 1;  
               }
               String fieldValue = (String)looseTextItemList.get((Object)itemName);
               
               int intFontSize = Integer.parseInt(fontSize);
               Color intForeColour = getColour(foreColour);
               Color intBackColour = getColour(backColour);

               if (fontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  fontStyleInt = Font.PLAIN; 
               } else
               if (fontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  fontStyleInt = Font.BOLD; 
               } else
               if (fontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  fontStyleInt = Font.ITALIC; 
               }
               JTextField fieldText = new JTextField(fieldValue);
               fieldText.setEditable(false);
               fieldText.setPreferredSize(new Dimension(200,20));
               fieldText.setBackground(intBackColour);
               fieldText.setForeground(intForeColour);
               fieldText.setFont(new Font(fontType,fontStyleInt,intFontSize));
               //fieldText.setBorder(loweredBevel);
               if (isEditableField) {
                   fieldText.setEnabled(true);
               } else {
                   fieldText.setEnabled(false);
               }
               fieldText.setEnabled(true);
               constraints.gridheight = intMaxRows;
               //check
               constraints.gridx = intXPos;
               constraints.gridy = intYPos;
               if (noDisplayItemList != null)
               {
                   String itemNoDisplayValue = (String)noDisplayItemList.get((Object)itemName); 
                   if (itemNoDisplayValue != null)
                   {
                        if (itemNoDisplayValue.equalsIgnoreCase(fieldValue) == false)
                        {
                            addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                        }
                   } else
                   {
                      addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                   }
               } else
               {
                   addItemToPanel(displayPanel, gridBag, constraints, fieldText);
               }
               
           } else
           if (itemType.equalsIgnoreCase("TEXTLABELCOMBOITEM") == true)
           {
               HashMap textLabelItemDisplayMapData = (HashMap)textLabelComboItemMap.get((Object)itemName);
               
               String YPos = (String)textLabelItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)textLabelItemDisplayMapData.get((Object)"COLPOS");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               String fontSize = (String)textLabelItemDisplayMapData.get((Object)"FONTSIZE");
               String fontType = (String)textLabelItemDisplayMapData.get((Object)"FONTTYPE");
               String fontStyle = (String)textLabelItemDisplayMapData.get((Object)"FONTSTYLE");
               String foreColour = (String)textLabelItemDisplayMapData.get((Object)"FORECOLOUR");
               String backColour = (String)textLabelItemDisplayMapData.get((Object)"BACKCOLOUR");
               String labelFontSize = (String)textLabelItemDisplayMapData.get((Object)"LABELFONTSIZE");
               String labelFontType = (String)textLabelItemDisplayMapData.get((Object)"LABELFONTTYPE");
               String labelFontStyle = (String)textLabelItemDisplayMapData.get((Object)"LABELFONTSTYLE");
               String labelForeColour = (String)textLabelItemDisplayMapData.get((Object)"LABELFORECOLOUR");
               String labelBackColour = (String)textLabelItemDisplayMapData.get((Object)"LABELBACKCOLOUR");
               String labelTextFlow = (String)textLabelItemDisplayMapData.get((Object)"LABELTEXTFLOW");
               String labelFirst = (String)textLabelItemDisplayMapData.get((Object)"LABELFIRST");
               String editableField = (String)textLabelItemDisplayMapData.get((Object)"EDITABLE");
               String tableLink = (String)textLabelItemDisplayMapData.get((Object)"TABLELINK");
               HashMap tableVarItems = (HashMap)(HashMap)tableVarList.get((Object)tableLink);
               
               boolean isEditableField;
               if (editableField.equalsIgnoreCase("Y") == true)
               {
                  isEditableField = true;
               } else
               {
                  isEditableField = false;
               }
               boolean isLabelFirst;
               if (labelFirst.equalsIgnoreCase("Y") == true)
               {
                  isLabelFirst = true;
               } else
               {
                  isLabelFirst = false;
               }

               ArrayList varList = (ArrayList)tableVarItems.get((Object)tableLink);
               ArrayList colValuesAndVars = (ArrayList)tableVarItems.get((Object)itemName);
               rowCount = colValuesAndVars.size();

               HashMap colVarMap =  (HashMap)colValuesAndVars.get(0);
               String fieldValue = (String)colVarMap.get((Object)"FIELDVALUE");
               String fieldLength = (String)colVarMap.get((Object)"FIELDLENGTH");
               String fieldType = (String)colVarMap.get((Object)"FIELDTYPE");
               String fieldLock = (String)colVarMap.get((Object)"FIELDLOCK");
               String fieldDisplay = (String)colVarMap.get((Object)"FIELDDISPLAY");
               String fieldHighlight = (String)colVarMap.get((Object)"FIELDHIGHLIGHT");
               String fieldLabelDisplay = (String)colVarMap.get((Object)"LABELDISPLAY");
               String labelValue = (String)colVarMap.get((Object)"LABELVALUE"); //this is the table heading

               
               int intFontSize = Integer.parseInt(fontSize);
               int intLabelFontSize = Integer.parseInt(labelFontSize);

               Color intForeColour = getColour(foreColour);
               Color intBackColour = getColour(backColour);

               Color intLabelForeColour = getColour(labelForeColour);
               Color intLabelBackColour = getColour(labelBackColour);

               if (fontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  fontStyleInt = Font.PLAIN; 
               } else
               if (fontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  fontStyleInt = Font.BOLD; 
               } else
               if (fontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  fontStyleInt = Font.ITALIC; 
               }
               
               if (labelFontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  labelFontStyleInt = Font.PLAIN; 
               } else
               if (labelFontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  labelFontStyleInt = Font.BOLD; 
               } else
               if (labelFontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  labelFontStyleInt = Font.ITALIC; 
               }

               JTextField fieldText = new JTextField(fieldValue);
               fieldText.setPreferredSize(new Dimension(200,20));
               fieldText.setBackground(intBackColour);
               fieldText.setForeground(intForeColour);
               fieldText.setFont(new Font(fontType,fontStyleInt,intFontSize));

               JTextField fieldLabel = new JTextField(labelValue);
               //JLabel  = new JLabel();
               fieldLabel.setPreferredSize(new Dimension(200,20));
               fieldLabel.setBackground(intLabelBackColour);
               fieldLabel.setForeground(intLabelForeColour);
               fieldLabel.setFont(new Font(labelFontType,labelFontStyleInt,intLabelFontSize));
               
               //fieldText.setBorder(loweredBevel);
               if (isEditableField) {
                   fieldText.setEnabled(true);
               } else {
                   fieldText.setEnabled(false);
               }
               fieldText.setEnabled(true);
               fieldText.setEditable(false);
               constraints.gridheight = 1;

               constraints.gridx = intXPos;
               constraints.gridy = intYPos;
               if ((fieldValue != null) && (fieldValue.length() > 0) && (fieldValue.trim().equalsIgnoreCase("null") == false))
               {
               if (noDisplayItemList != null)
               {
                   String itemNoDisplayValue = (String)noDisplayItemList.get((Object)itemName); 
                   if (itemNoDisplayValue != null)
                   {
                        if (itemNoDisplayValue.equalsIgnoreCase(fieldValue) == false)
                        {
                            if (isLabelFirst == true) {
                                if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                    constraints.gridx = intXPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                } else {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                    constraints.gridy = intYPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                }
                            } else {
                                if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                    constraints.gridx = intXPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                } else {
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                                    constraints.gridy = intYPos + 1;
                                    addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                                }
                            }
                        }
                   } else
                   {
                       if (isLabelFirst == true) {
                           if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                               constraints.gridx = intXPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                           } else {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                               constraints.gridy = intYPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                           }
                       } else {
                           if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true) {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                               constraints.gridx = intXPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                           } else {
                               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                               constraints.gridy = intYPos + 1;
                               addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                           }
                       }
                   }
               } else
               {
                   if (isLabelFirst == true)
                   {
                       if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true)
                       {    
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                          constraints.gridx = intXPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                       } else
                       {
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                          constraints.gridy = intYPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                       }
                   } else
                   {
                       if (labelTextFlow.equalsIgnoreCase("HORIZONTAL") == true)
                       {    
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                          constraints.gridx = intXPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                       } else
                       {
                          addItemToPanel(displayPanel, gridBag, constraints, fieldText);
                          constraints.gridy = intYPos + 1;
                          addItemToPanel(displayPanel, gridBag, constraints, fieldLabel);
                       }
                   }
               }
              }
               
           } else
           if (itemType.equalsIgnoreCase("STATICTEXTITEM") == true)
           {
               HashMap textItemDisplayMapData = (HashMap)staticTextItemMap.get((Object)itemName);
               HashMap fieldVarItems = (HashMap)fieldVarList.get((Object)itemName);
               
               String YPos = (String)textItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)textItemDisplayMapData.get((Object)"COLPOS");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               String fontSize = (String)textItemDisplayMapData.get((Object)"FONTSIZE");
               String fontType = (String)textItemDisplayMapData.get((Object)"FONTTYPE");
               String fontStyle = (String)textItemDisplayMapData.get((Object)"FONTSTYLE");
               String foreColour = (String)textItemDisplayMapData.get((Object)"FORECOLOUR");
               String backColour = (String)textItemDisplayMapData.get((Object)"BACKCOLOUR");
               String fillDown = (String)textItemDisplayMapData.get((Object)"FILLDOWN");
               String maxRows = (String)textItemDisplayMapData.get((Object)"FILLMAXROWS");
               String editableField = (String)textItemDisplayMapData.get((Object)"EDITABLE");
               String fieldValue = (String)textItemDisplayMapData.get((Object)"ITEMVALUE");
               
               String tableLink  = (String)textItemDisplayMapData.get((Object)"TABLELINK");
               boolean isEditableField = Boolean.getBoolean(editableField);
               boolean isFillDown = Boolean.getBoolean(fillDown);
               int intMaxRows = Integer.parseInt(maxRows);
               if (isFillDown == false)
               {
                 intMaxRows = 1;  
               }
               int intFontSize = Integer.parseInt(fontSize);
               Color intForeColour = getColour(foreColour);
               Color intBackColour = getColour(backColour);

               if (fontStyle.equalsIgnoreCase("NORMAL") == true)
               {
                  fontStyleInt = Font.PLAIN; 
               } else
               if (fontStyle.equalsIgnoreCase("BOLD") == true)
               {
                  fontStyleInt = Font.BOLD; 
               } else
               if (fontStyle.equalsIgnoreCase("ITALIC") == true)
               {
                  fontStyleInt = Font.ITALIC; 
               }
               JTextField fieldText = new JTextField(fieldValue);
               fieldText.setPreferredSize(new Dimension(200,20));
               fieldText.setBackground(intBackColour);
               fieldText.setForeground(intForeColour);
               fieldText.setFont(new Font(fontType,fontStyleInt,intFontSize));
               //fieldText.setBorder(loweredBevel);
               if (isEditableField) {
                   fieldText.setEnabled(true);
               } else 
               {
                   fieldText.setEnabled(false);
               }
               fieldText.setEnabled(true);
               fieldText.setEditable(false);
               constraints.gridheight = intMaxRows;
               constraints.gridwidth = 1;
               
               constraints.gridx = intXPos;
               constraints.gridy = intYPos;
               addItemToPanel(displayPanel, gridBag, constraints, fieldText);
               tableNameToHeadersLink.put((Object)tableLink,(Object)fieldText);
               
               //displayPanel.remove(fieldText);
               
               
           } else           
           if (itemType.equalsIgnoreCase("TABLEITEM") == true)
           {
       HashMap notesCollection = new HashMap(5);
       ArrayList tableNotesHeaderNameList = new ArrayList(1);
       int notesIndex = 0;

               HashMap tableItemDisplayMapData = (HashMap)tableItemMap.get((Object)itemName);
               ArrayList dateSelectionList = (ArrayList)dateByTableMap.get((Object)itemName);
               fieldVarList = (HashMap)tableVarList.get((Object)itemName);
               if ((fieldVarList != null) && (fieldVarList.size() > 0))
               {
                   
               String YPos = (String)tableItemDisplayMapData.get((Object)"ROWPOS");
               String XPos = (String)tableItemDisplayMapData.get((Object)"COLPOS");
               String gridWidth = (String)tableItemDisplayMapData.get((Object)"GRIDWIDTH");
               int intXPos = Integer.parseInt(XPos);
               int intYPos = Integer.parseInt(YPos);
               //int rowCount = 0;
               int colCount = Integer.parseInt((String)tableItemDisplayMapData.get((Object)"COLCOUNT"));
               ArrayList colNames = (ArrayList)tableItemDisplayMapData.get((Object)"COLNAMELIST");
               ArrayList colForegrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLFOREGROUNDLIST");
               ArrayList colBackgrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLBACKGROUNDLIST");
               ArrayList colHeadingForegrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLHEADINGFOREGROUNDLIST");
               ArrayList colHeadingBackgrounds = (ArrayList)tableItemDisplayMapData.get((Object)"COLHEADINGBACKGROUNDLIST");
               String[][] tableData = null;
               String[] tableHeadingData = null;
               int[] tableHeadingLengths = null;
               int[] colItemWidthList = null;
               ArrayList columnsToBeDisplayed = null;
               int colNameDispIndex = 0;
               int dispColCount = 0;
               boolean allDefaults = true;
               
                   
               //loop through all the columns for this table to check if this column should be displayed
               for (int colNameLoop = 0; colNameLoop < colCount; colNameLoop++)
               {
                   String colName = (String)colNames.get(colNameLoop);
                   ArrayList colValuesAndVars = (ArrayList)fieldVarList.get((Object)colName);
                   rowCount = colValuesAndVars.size();
                   //now check if there is a list of items that contains the values that must not be displayed
                   if (noDisplayItemList != null) 
                   {
                        String itemNoDisplayValue = (String)noDisplayItemList.get((Object)colName);
                        //check if the list contains the column data that must be checked
                        if ((itemNoDisplayValue != null) && (itemNoDisplayValue.length() > 0))
                        {
                            //now loop through the row data to check the actual values. 
                            for (int valueLoop1 = 0; valueLoop1 < rowCount; valueLoop1++) 
                            {
                                HashMap colVarMap =  (HashMap)colValuesAndVars.get(valueLoop1);
                                String fieldValue = (String)colVarMap.get((Object)"FIELDVALUE");
                                //if this column contains values that must be displayed then this column must be added to table 
                                if ((fieldValue != null)  && (fieldValue.equalsIgnoreCase(itemNoDisplayValue) == false) && (fieldValue.trim().equalsIgnoreCase("null") == false) )
                                {
                                    allDefaults = false;
                                    //add this column to the column array that must be displayed
                                    if (columnsToBeDisplayed != null)
                                    {
                                        columnsToBeDisplayed.add((Object)colName);
                                    } else
                                    {
                                        columnsToBeDisplayed = new ArrayList(colCount);
                                        columnsToBeDisplayed.add((Object)colName);
                                    }
                                    break;
                                }
                            }
                        } else
                        {
                             if (columnsToBeDisplayed != null)
                             {
                                 columnsToBeDisplayed.add((Object)colName);
                             } else
                             {
                                 columnsToBeDisplayed = new ArrayList(colCount);
                                 columnsToBeDisplayed.add((Object)colName);
                             }
                        }
                   }
               }
               if (columnsToBeDisplayed != null)
               {
                   //this will use columnsToBeDisplayed to determine which colums must be displayed
                   dispColCount = columnsToBeDisplayed.size()+1;
                   tableHeadingData = new String[dispColCount];
                   colItemWidthList = new int[dispColCount+1];
                   tableHeadingLengths = new int[dispColCount+1];
                   int checkloop = 0;
                   
                   for (int colNameLoop = 0; colNameLoop < colCount; colNameLoop++) {
                       String colName = (String)colNames.get(colNameLoop);
                       boolean containsName = false;
                       for (checkloop = 0; checkloop < columnsToBeDisplayed.size(); checkloop++)
                       {
                           if (((String)columnsToBeDisplayed.get(checkloop)).equalsIgnoreCase(colName) == true)
                           {
                              containsName = true;
                              break;
                           }
                       }
                               if (colNameLoop == 0)
                               {
                                  tableHeadingData[colNameDispIndex] = "Date Captured";
                                  tableHeadingLengths[colNameDispIndex] = tableHeadingData[colNameDispIndex].length();
                                  int labelWidth =  tableHeadingData[colNameDispIndex].length();
                                  colItemWidthList[colNameDispIndex] = labelWidth;
                                  colNameDispIndex++;
                               }

                       if (containsName == true)
                       {
                           String colForeground = (String)colForegrounds.get(colNameLoop);
                           String colbackground = (String)colBackgrounds.get(colNameLoop);
                           String colHeadingForeground = (String)colHeadingForegrounds.get(colNameLoop);
                           String colHeadingBackground = (String)colHeadingBackgrounds.get(colNameLoop);
                           ArrayList colValuesAndVars = (ArrayList)fieldVarList.get((Object)colName);
                           rowCount = colValuesAndVars.size();
                           if (tableData == null)
                           {
                               tableData = new String[rowCount][dispColCount];
                           }
                           for (int valueLoop = 0; valueLoop < rowCount; valueLoop++) 
                           {
                               String trimmedDate = ((String)dateSelectionList.get(valueLoop)).substring(0,10);
                               //change this dateFormat to '10 October 2004' format later
                               tableData[valueLoop][0] = trimmedDate;
                               HashMap colVarMap =  (HashMap)colValuesAndVars.get(valueLoop);
                               String fieldValue = (String)colVarMap.get((Object)"FIELDVALUE");
                               String fieldLength = (String)colVarMap.get((Object)"FIELDLENGTH");
                               String fieldType = (String)colVarMap.get((Object)"FIELDTYPE");

                               String fieldLock = (String)colVarMap.get((Object)"FIELDLOCK");
                               String fieldDisplay = (String)colVarMap.get((Object)"FIELDDISPLAY");
                               String fieldHighlight = (String)colVarMap.get((Object)"FIELDHIGHLIGHT");
                               String fieldLabelDisplay = (String)colVarMap.get((Object)"LABELDISPLAY");
                               String fieldLabelValue = (String)colVarMap.get((Object)"LABELVALUE"); //this is the table heading
                               if (fieldType.equalsIgnoreCase("TEXT_AREA_FIELD") == true)
                               {
                                   if (fieldValue.equalsIgnoreCase("null") == true)
                                   {
                                      fieldValue = "--";
                                   } else
                                   {
                                      notesIndex++;
                                      notesCollection.put((Object)"Notes_" + Integer.toString(notesIndex),(Object)fieldValue);
                                      fieldValue = "Notes_" + Integer.toString(notesIndex);
                                   }
                                   tableNotesHeaderNameList.add((Object)fieldLabelValue);
                               }
                               //check if label must be displayed and put blank or data in next code

                                  tableHeadingData[colNameDispIndex] = fieldLabelValue;
                               
                               tableHeadingLengths[colNameDispIndex] = fieldLabelValue.length();
                               tableData[valueLoop][colNameDispIndex] = fieldValue;
                               int itemLength = fieldValue.length();
                               int itemWidth = colItemWidthList[colNameDispIndex];
                               if (itemLength > itemWidth) 
                               {
                                   colItemWidthList[colNameDispIndex] = itemLength;
                               }
                           }
                           colNameDispIndex++;
                       }
                   } //end colname loop
                   if (rowCount > 0)
                   {
                       
                       MTCustomTableModel customModel = new MTCustomTableModel((Object[][])tableData.clone(), (Object[])tableHeadingData.clone());
                      
                       
                       //CustomTableModel customModel = new CustomTableModel((Object[][])tableData.clone(), (Object[])tableHeadingData.clone());
                       
                       //TableSorter sorter = new TableSorter(customModel);
                       //tableData = null;
                       //tableHeadingData = null;
                       displayTable = new MTJTable(customModel);
                        
                       for (int tLoop = 0; tLoop < tableNotesHeaderNameList.size(); tLoop++)
                       {       
                           String notesItemIndexName = (String)tableNotesHeaderNameList.get(tLoop);
                           displayTable.getColumn(notesItemIndexName).setCellRenderer(new ButtonRenderer());
                           displayTable.getColumn(notesItemIndexName).setCellEditor(new ButtonEditor(new JCheckBox(),notesCollection));
                       }
                       
                       //TableCellRenderer defaultRenderer = displayTable.getDefaultRenderer(JButton.class);
                       //displayTable.setDefaultRenderer(JButton.class, new JTableButtonRenderer(defaultRenderer));
                       //displayTable.addMouseListener(new JTableButtonMouseListener(displayTable));
                       
                       int tableHeight = 32;
                       if (rowCount > 1)
                       {
                           tableHeight = getPreferredRowHeight(displayTable,1,0) * (rowCount + 1);
                       } else
                       {
                           tableHeight = getPreferredRowHeight(displayTable,0,0) * (rowCount + 1);
                       }
                       //int tableHeight = 40 * rowCount;
                       displayTable.setSize(new Dimension(1000, tableHeight));
                       displayTable.setPreferredSize(new Dimension(1000, tableHeight));
                       displayTable.setMinimumSize(new Dimension(1000, tableHeight));
                       displayTable.setAutoResizeMode(displayTable.AUTO_RESIZE_OFF);
                       displayTable.setBackground(new Color(150,200,255));
                       displayTable.setPreferredScrollableViewportSize(new Dimension(1000, tableHeight));
                       displayTable.setShowHorizontalLines(false);
                       for (int colSetLoop = 0; colSetLoop < dispColCount; colSetLoop++) {
                           boolean useHeadingWidth = false;
                           if (tableHeadingLengths[colSetLoop] > colItemWidthList[colSetLoop]) {
                               useHeadingWidth = true;
                           }
                           TableColumn column = displayTable.getColumnModel().getColumn(colSetLoop);
                           if(useHeadingWidth) {
                               column.setPreferredWidth(tableHeadingLengths[colSetLoop]*10);
                           } else {
                               column.setPreferredWidth(colItemWidthList[colSetLoop]*10);
                           }
                           column.setMaxWidth(400);

                       //Add button in table column to test    
                       //TableColumn buttonColumn = displayTable.getColumnModel().getColumn(2);
                       //JButton myButton = new JButton("Test");

                       //buttonColumn.setCellEditor(new DefaultCellEditor(myButton));


                           
                           
                           
                           
                           //column;
                           //column.setAutoResizeMode(displayTable.AUTO_RESIZE_OFF);
                           //column.sizeWidthToFit();
                           //column.setResizable(true);
                           
                       }
                       JScrollPane scrollPane =  new JScrollPane(displayTable);
                       scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                       scrollPane.setMinimumSize(new Dimension(1000, tableHeight));
                       scrollPane.setPreferredSize(new Dimension(1000, tableHeight));
                       constraints.gridwidth = colCount*2;
                       constraints.gridheight = rowCount + 1;
                       constraints.gridx = intXPos;
                       constraints.gridy = intYPos;
                       addItemToPanel(displayPanel, gridBag, constraints, scrollPane);
                   } // end if rowcount > 0
               //===================
               } else //columnsToBeDisplayed != null
               {
                  //table wont be displayed so  remove table heading
                  JTextField hfText = (JTextField)tableNameToHeadersLink.get((Object)itemName);
                  if (hfText != null)
                  {
                      displayPanel.remove(hfText);
                  }
               } //columnsToBeDisplayed == null
               } else   // end fieldvarlist
               {
                  //table wont be displayed so  remove table heading
                  JTextField hfText = (JTextField)tableNameToHeadersLink.get((Object)itemName);
                  if (hfText != null)
                  {
                      displayPanel.remove(hfText);
                  }
                   
               }
           } else           
           if (itemType.equalsIgnoreCase("IMAGEITEM") == true)
           {
               
           }           
       }
       //addItemToPanel(JPanel _panel, GridBagLayout _gridBag, GridBagConstraints _constraints, String XPos, String YPos, Component component)
    }
       
    public void initDataViewByXMLSpec(MTTableDataModel mtModel, int height, int width) throws MTException
    {
            try
            {
                String xmlFileName = mtModel.getXMLFileName();
                XMLPageFormatterParser pageParser = new XMLPageFormatterParser(xmlFileName);
                String pageName = pageParser.getPageName();
                String pageVersion  = pageParser.getPageVersion();
                int columnInCount  = pageParser.getPageGridCount();
                pageOrientation = pageParser.getPageOrientation();
                multiPage = pageParser.isMultiPage();
                String theExtKey = null;
                String theExtValue = null;
                ArrayList pageItemList = pageParser.getPageItemList();
                HashMap pageItemToTypeMap = pageParser.getPageItemToTypeMap();
                HashMap staticTextItemMap = pageParser.getStaticTextItemMap();
                HashMap textItemMap = pageParser.getTextItemMap();
                HashMap tableItemMap = pageParser.getTableItemMap();
                HashMap imageItemMap = pageParser.getImageItemMap();
                HashMap labelCoordCollection = mtModel.getLabelDispCoordinateCollection();
                HashMap fieldCoordCollection = mtModel.getFieldDispCoordinateCollection();
                HashMap lowerBoundCollection = mtModel.getLowerBoundCollection();
                HashMap upperBoundCollection = mtModel.getUpperBoundCollection();
                HashMap objectCoordCollection = mtModel.getObjectDispCoordinateCollection();
                HashMap dbAccessCollection = (HashMap)mtModel.getdbAccessCollection();
                HashMap tableDisplayHeightCollection = (HashMap)mtModel.gettableRowDisplayHeightCollection();
                HashMap labelByTableCollection = (HashMap)mtModel.getLabelByTableCollection();
                HashMap fieldByTableCollection = (HashMap)mtModel.getFieldByTableCollection();
                lookupTableDataCollection = (HashMap)mtModel.getLookupTableDataCollection();
                HashMap looseTextItemList = (HashMap)mtModel.getLooseTextItemMapList();
                HashMap textLabelComboItemMap = pageParser.getTextLabelComboItemMap();
                HashMap labelTextVarMap = (HashMap)mtModel.getLabelTextVarMap();
                HashMap noDisplayItemList = (HashMap)mtModel.getComboBoxDefaultCollection();
                ArrayList imageList = new ArrayList(10);
                HashMap fieldVarList  = new HashMap(10);
                HashMap fieldVarItemsMap;
                int fieldLength = 0;
                String fieldName = null;
                String fieldLabelValue = null;
                boolean fieldLock = false;
                boolean labelDisplay = false;
                boolean fieldDisplay = true;
                boolean fieldHighLight = false;
                String fieldValue = null;
                String fieldType = null;
                String captureDate = null;
                int rowCount  =  0;
                int colCount = 0;
                int dateEntries = 0;
                int panelRows = 0;
                String labelFont = "Serif";
                String valueFont = "Bookman Antiqua";
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
                HashMap tableVarList = new HashMap(1);
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
                tablesTextList = new HashMap(mtModel.getTableCount());
                int tableOffset = 0;
                int dateOffset = 0;
                isEmpty = true;
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    fieldVarList.clear();
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    if ((labelByTableCollection != null) && (labelByTableCollection.size() > 0))
                    {
                       labelCoordCollection =  (HashMap)labelByTableCollection.get((Object)theTableName);
                       fieldCoordCollection =  (HashMap)fieldByTableCollection.get((Object)theTableName);
                    }
                    
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
                    int dateLoop = 0;
                    if ((dateList == null) || (dateList.size() == 0))
                    { 
                        dateLoop = 1;
                    } else
                    {
                        dateLoop = dateList.size();
                    }
                    String[] theColumnNames = dataInstance.dataTable.getColumnNames();
                    String[] theColumnAliases = dataInstance.dataTable.getColumnAliases();
                    String[] theColumnTypes = dataInstance.dataTable.getColumnKeyTypes();
                    boolean[] theColumnLocks = dataInstance.dataTable.getColumnLocks();
                    boolean[] theColumnDisplays = dataInstance.dataTable.getColumnDisplays();
                    boolean[] theColumnHighlights = dataInstance.dataTable.getColumnHighlights();
                    boolean[] theLabelDisplays = dataInstance.dataTable.getLabelDisplays();
                    String thePanelHeading = (String)mtModel.getTableAliases().get((Object)theTableName);
                    thePanelHeading = mtModel.getKeyName();
                    //next field is the patient name
                    JLabel panelHeadingLabel = new JLabel(thePanelHeading);
                    panelHeadingLabel.setForeground(new Color(0,0,255));
                    panelHeadingLabel.setPreferredSize(new Dimension(200,20));
                    panelHeadingLabel.setFont(new Font(labelFont,Font.BOLD,12));
                    panelHeadingLabel.setBorder(BorderFactory.createEtchedBorder());
                    String objectCoords = (String)objectCoordCollection.get((Object)"KEY_NAME");
                    //addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, panelHeadingLabel);
                    //headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
                    //headingPanel.setBackground(new Color(0,50,150));
                    //headingPanel.add(panelHeadingLabel);
                    //dataPanel.add(headingPanel, BorderLayout.NORTH);
                    ArrayList primaryItems = new ArrayList(1);
                    ArrayList foreignItems = new ArrayList(1);
                    ArrayList fieldForeignItems = new ArrayList(1);
                    ArrayList booleanFieldItems = new ArrayList(1);
                    ArrayList toByFieldItems = new ArrayList(1);
                    ArrayList phoneFieldItems = new ArrayList(1);
                    ArrayList cellFieldItems = new ArrayList(1);
                    ArrayList areaFieldItems = new ArrayList(1);
                    ArrayList idFieldItems = new ArrayList(1);
                    ArrayList birthDayFieldItems = new ArrayList(1);
                    ArrayList textAreaFieldItems = new ArrayList(1);
                    ArrayList documentAreaFieldItems = new ArrayList(1);
                    ArrayList initialFieldItems = new ArrayList(1);
                    ArrayList dateTimeFieldItems = new ArrayList(1);
                    ArrayList dateFieldItems = new ArrayList(1);
                    ArrayList spinMYFieldItems = new ArrayList(1);
                    ArrayList groupDepFieldItems = new ArrayList(1);
                    ArrayList spinTempFieldItems = new ArrayList(1);
                    ArrayList generalFieldItems = new ArrayList(1);
                    MaskFormatter formatter;
                    JFormattedTextField ftf;
                    JTextField fieldText;
                    String selectedValue = "";
                    String keyFieldValue = null;
                    String dateTabField = "";
                   
                    for(dateEntries = 0; dateEntries < rowCount; dateEntries++)//changed from dateloop to rowcount
                    {
                        dateOffset = dateEntries * tableHeight;
                        try
                        {
                           for (int fieldIndex = 0; fieldIndex < colCount; fieldIndex++)
                           {
                              fieldLabelValue = theColumnAliases[fieldIndex];
                              fieldName = theColumnNames[fieldIndex].trim();
                              if (fieldName.indexOf("FILLER") == 0)
                              {
                                  continue;
                              }
                                 
                              fieldType = theColumnTypes[fieldIndex];
                              String theFieldValue = dataInstance.dataTable.getDataAt(dateEntries, fieldIndex);
                              fieldValue = stripNulls(theFieldValue);
                              
                              fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(fieldName));
                              fieldLock = theColumnLocks[fieldIndex];
                              fieldDisplay = theColumnDisplays[fieldIndex];
                              fieldHighLight = theColumnHighlights[fieldIndex];
                              labelDisplay = theLabelDisplays[fieldIndex];
                              
                              
                              //this is for displaying capture date item
                              if (fieldIndex == 0)
                              {
                                    if ((dateList != null)  && (dateList.containsKey((Object)fieldValue)))
                                    {
                                         captureDate = (String)dateList.get((Object)fieldValue);
                                    } else
                                    {
                                        captureDate = "Null"; 
                                    }
                              }

                              
                              if (fieldType.equalsIgnoreCase("PRIMARY"))
                              {
                                   String zeroPadding = "";
                                   for (int keyloop = 0; keyloop < fieldLength - fieldValue.length(); keyloop++)
                                   {
                                      zeroPadding = zeroPadding + "0";
                                   }
                                   fieldValue = zeroPadding + fieldValue;
                                   if (fieldDisplay == true)
                                   {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)fieldValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                   } 
                                   isEmpty = false;
                               } else
                               if ((fieldType.equalsIgnoreCase("FOREIGN")) || (fieldType.equalsIgnoreCase("GROUP")) || (fieldType.equalsIgnoreCase("GROUPKEY")))
                               {
                                   lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                                   Set keySet = lookupTableData.keySet();
                                   Iterator iter = keySet.iterator();
                                   boolean valueFound = false;
                                   while (iter.hasNext())
                                   {
                                       //String theKey = (String)iter.next();
                                       //String theValue = (String)lookupTableData.get((Object)theKey);
                                       theExtKey = (String)iter.next();
                                       theExtValue = (String)lookupTableData.get((Object)theExtKey);
                                       if (theFieldValue.equalsIgnoreCase(theExtKey))
                                       {
                                           valueFound = true;
                                           selectedValue = theExtValue;
                                           break;
                                       }
                                    }
                                    if(valueFound == false)
                                    {
                                       selectedValue = "Not Applicable"; 
                                    }
                                    if (fieldDisplay == true)
                                    {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                    }
                                    isEmpty = false;
                               }  else
                               if (fieldType.equalsIgnoreCase("FIELD_FOREIGN"))
                               {
                                   lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
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
                                    //if (fieldDisplay == true) 
                                    //{
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                    //}
                                    isEmpty = false;
                                } else
                                 if ((fieldType.equalsIgnoreCase("BOOLEAN_FIELD")) || (fieldType.equalsIgnoreCase("BOOLEAN_GROUP")))
                                 {
                                     if (theFieldValue.equalsIgnoreCase("0"))
                                        selectedValue = "No";
                                     else
                                        selectedValue = "Yes";
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 } else
                                 if (fieldType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                                 {
                                    if (theFieldValue.equalsIgnoreCase("0"))
                                        selectedValue = "To"; 
                                    else
                                        selectedValue = "By";
                                    if (fieldDisplay == true)
                                    {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                    }
                                   isEmpty = false;
                                 } else
                                 if (fieldType.equalsIgnoreCase("PHONE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("CELL_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("AREA_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("ID_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("#############");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("##/##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
                                 {
                                        if ((theFieldValue != null) && (theFieldValue.length() > 0) && (fieldDisplay == true))
                                        {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }   else
                                 if (fieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                                 {
                                     if (fieldDisplay == true)
                                     {
                                        imageList.clear();
                                        String[] docFileNames = getFileList(theFieldValue);
                                        for (int docIndex = 0; docIndex < docFileNames.length; docIndex++)
                                        {
                                            ImageIcon theImage = new ImageIcon(docScanPath + docFileNames[docIndex]);
                                            imageList.add((Object)theImage);
                                        }
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)imageList.clone()); //this adds an array of images!!
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());

                                     }
                                   isEmpty = false;
                                 } else
                                 if (fieldType.equalsIgnoreCase("INITIAL_FIELD"))
                                 {
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap.clone());
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList);
                                     }
                                 } else
                                 if (fieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-## ##:##:##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("DATE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if ((fieldType.equalsIgnoreCase("SPINMONTHYEAR")) || (fieldType.equalsIgnoreCase("SPINLITMONTHYEAR")))
                                 {
                                     String tmpField = formatDate(theFieldValue);

                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)tmpField);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }   else
                                 if (fieldType.equalsIgnoreCase("GROUPDEPENDANT"))
                                 {
                                     String mask = "";
                                     for (int maskloop = 0; maskloop < fieldLength; maskloop++)
                                     {
                                         mask = mask + "*";
                                     }
                                     formatter = new  MaskFormatter(mask);
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if ((fieldDisplay == true) && (theFieldValue.length() > 0))
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }     else
                                 if (fieldType.equalsIgnoreCase("SPINTEMP"))
                                 {
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 } else
                                 {
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 } //end else
                            } //end for fieldindex
                        } //end try
                        catch (Exception ex) 
                        {
                            throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
                        }
                    } //end of datelist entries
                    //if (rowCount == 0)
                    //{
                    //    isEmpty = true;
                    //}
                    //fieldVarList.clear();
                    tableOffset += dateOffset;
                    HashMap tempHashList = (HashMap)fieldVarList.clone();
                    tableVarList.put((Object)theTableName,(Object)tempHashList);
                    
                    
                    //--ccc---
                    dataInstance.closeConnection();
                    dbAccessCollection.remove((Object)theTableName);
                    dataInstance = null;
                    System.gc();
                    
                    
                } //end for tablelist loop
                if (isEmpty == false)
                {
                    buildDisplayPage(displayPanel, pageItemList, pageItemToTypeMap, staticTextItemMap, textItemMap, tableItemMap, imageItemMap, tableVarList, looseTextItemList,columnInCount, noDisplayItemList, textLabelComboItemMap, labelTextVarMap);
                   JScrollPane displayScrollPane = new JScrollPane(displayPanel);
                   //displayScrollPane.add();
                   this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                   this.add(displayScrollPane);
                   printReportButton = new JButton("Print Report");
                   controlPane.setLayout(new BorderLayout());
                   controlPane.setBorder(compoundBorder);
                   controlPane.add(printReportButton, BorderLayout.CENTER);
                   this.add(controlPane);
                }
                               
            } 
            catch (MTException e)
            {
                throw new MTException(e.getExceptionType(), e.getErrorMessage());
            }
            catch (Exception e)
            {
                throw new MTException(InfoManager.VIEW_BUILDER_ERROR, e.getMessage());
            }
            
        } //end initSimpleDataView

    public void initDataViewByDateXMLSpec(MTTableDataModel mtModel, ArrayList dateArrayList, int height, int width) throws MTException
    {
            try
            {
                String xmlFileName = mtModel.getXMLFileName();
                XMLPageFormatterParser pageParser = new XMLPageFormatterParser(xmlFileName);
                String pageName = pageParser.getPageName();
                String pageVersion  = pageParser.getPageVersion();
                int columnInCount  = pageParser.getPageGridCount();
                pageOrientation = pageParser.getPageOrientation();
                multiPage = pageParser.isMultiPage();
                String theExtKey = null;
                String theExtValue = null;
                ArrayList pageItemList = pageParser.getPageItemList();
                HashMap pageItemToTypeMap = pageParser.getPageItemToTypeMap();
                HashMap staticTextItemMap = pageParser.getStaticTextItemMap();
                HashMap textItemMap = pageParser.getTextItemMap();
                HashMap tableItemMap = pageParser.getTableItemMap();
                HashMap imageItemMap = pageParser.getImageItemMap();
                HashMap labelCoordCollection = mtModel.getLabelDispCoordinateCollection();
                HashMap fieldCoordCollection = mtModel.getFieldDispCoordinateCollection();
                HashMap lowerBoundCollection = mtModel.getLowerBoundCollection();
                HashMap upperBoundCollection = mtModel.getUpperBoundCollection();
                HashMap objectCoordCollection = mtModel.getObjectDispCoordinateCollection();
                HashMap dbAccessCollection = (HashMap)mtModel.getdbAccessCollection();
                HashMap tableDisplayHeightCollection = (HashMap)mtModel.gettableRowDisplayHeightCollection();
                HashMap labelByTableCollection = (HashMap)mtModel.getLabelByTableCollection();
                HashMap fieldByTableCollection = (HashMap)mtModel.getFieldByTableCollection();
                lookupTableDataCollection = (HashMap)mtModel.getLookupTableDataCollection();
                HashMap looseTextItemList = (HashMap)mtModel.getLooseTextItemMapList();
                HashMap textLabelComboItemMap = pageParser.getTextLabelComboItemMap();
                HashMap labelTextVarMap = (HashMap)mtModel.getLabelTextVarMap();
                HashMap noDisplayItemList = (HashMap)mtModel.getComboBoxDefaultCollection();
                ArrayList imageList = new ArrayList(10);
                HashMap fieldVarList  = new HashMap(10);
                HashMap fieldVarItemsMap;
                int fieldLength = 0;
                //String fieldName = null;
                String fieldLabelValue = null;
                boolean fieldLock = false;
                boolean labelDisplay = false;
                boolean fieldDisplay = true;
                boolean fieldHighLight = false;
                String fieldValue = null;
                String fieldType = null;
                String captureDate = null;
                int rowCount  =  0;
                int colCount = 0;
                int dateEntries = 0;
                int panelRows = 0;
                String labelFont = "Serif";
                String valueFont = "Bookman Antiqua";
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
                HashMap tableVarList = new HashMap(1);
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
                tablesTextList = new HashMap(mtModel.getTableCount());
                dateByTableMap = new HashMap(mtModel.getTableCount());
                int tableOffset = 0;
                int dateOffset = 0;
                isEmpty = true;
                for (int loop = 0; loop < mtModel.getTableCount(); loop++)
                {
                    fieldVarList.clear();
                    theTableName = (String)mtModel.getTableNames().get(loop);
                    if ((labelByTableCollection != null) && (labelByTableCollection.size() > 0))
                    {
                       labelCoordCollection =  (HashMap)labelByTableCollection.get((Object)theTableName);
                       fieldCoordCollection =  (HashMap)fieldByTableCollection.get((Object)theTableName);
                    }
                    
                    int tableHeight = Integer.parseInt((String)tableDisplayHeightCollection.get((Object)theTableName));
                    
                    DBAccess dataInstance = (DBAccess)dbAccessCollection.get((Object)theTableName);
                    dateList = dataInstance.getDateFieldList();
                    rowCount = dataInstance.dataTable.getRowCount();
                    colCount = dataInstance.dataTable.getColumnCount();//colums added
                    fieldTextList = new HashMap(colCount);
                    fieldTextItems = new ArrayList(dateList.size());
                    if (colCount < 21)
                    {
                        panelRows = 21; 
                    } else
                    {
                        panelRows = colCount;
                    }
                    int dateLoop = 0;
                    if ((dateList == null) || (dateList.size() == 0))
                    { 
                        dateLoop = 1;
                    } else
                    {
                        dateLoop = dateList.size();
                    }
                    theColumnNames = dataInstance.dataTable.getColumnNames();
                    String[] theColumnAliases = dataInstance.dataTable.getColumnAliases();
                    String[] theColumnTypes = dataInstance.dataTable.getColumnKeyTypes();
                    boolean[] theColumnLocks = dataInstance.dataTable.getColumnLocks();
                    boolean[] theColumnDisplays = dataInstance.dataTable.getColumnDisplays();
                    boolean[] theColumnHighlights = dataInstance.dataTable.getColumnHighlights();
                    boolean[] theLabelDisplays = dataInstance.dataTable.getLabelDisplays();
                    String thePanelHeading = (String)mtModel.getTableAliases().get((Object)theTableName);
                    thePanelHeading = mtModel.getKeyName();
                    //next field is the patient name
                    JLabel panelHeadingLabel = new JLabel(thePanelHeading);
                    panelHeadingLabel.setForeground(new Color(0,0,255));
                    panelHeadingLabel.setPreferredSize(new Dimension(200,20));
                    panelHeadingLabel.setFont(new Font(labelFont,Font.BOLD,12));
                    panelHeadingLabel.setBorder(BorderFactory.createEtchedBorder());
                    String objectCoords = (String)objectCoordCollection.get((Object)"KEY_NAME");
                    //addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, panelHeadingLabel);
                    //headingPanel.setBorder(BorderFactory.createRaisedBevelBorder());
                    //headingPanel.setBackground(new Color(0,50,150));
                    //headingPanel.add(panelHeadingLabel);
                    //dataPanel.add(headingPanel, BorderLayout.NORTH);
                    ArrayList primaryItems = new ArrayList(1);
                    ArrayList foreignItems = new ArrayList(1);
                    ArrayList fieldForeignItems = new ArrayList(1);
                    ArrayList booleanFieldItems = new ArrayList(1);
                    ArrayList toByFieldItems = new ArrayList(1);
                    ArrayList phoneFieldItems = new ArrayList(1);
                    ArrayList cellFieldItems = new ArrayList(1);
                    ArrayList areaFieldItems = new ArrayList(1);
                    ArrayList idFieldItems = new ArrayList(1);
                    ArrayList birthDayFieldItems = new ArrayList(1);
                    ArrayList textAreaFieldItems = new ArrayList(1);
                    ArrayList documentAreaFieldItems = new ArrayList(1);
                    ArrayList initialFieldItems = new ArrayList(1);
                    ArrayList dateTimeFieldItems = new ArrayList(1);
                    ArrayList dateFieldItems = new ArrayList(1);
                    ArrayList spinMYFieldItems = new ArrayList(1);
                    ArrayList groupDepFieldItems = new ArrayList(1);
                    ArrayList spinTempFieldItems = new ArrayList(1);
                    ArrayList generalFieldItems = new ArrayList(1);
                    MaskFormatter formatter;
                    JFormattedTextField ftf;
                    JTextField fieldText;
                    String selectedValue = "";
                    String keyFieldValue = null;
                    String dateTabField = "";
                   
                    captureDateList = new ArrayList(rowCount);
                    for(dateEntries = 0; dateEntries < rowCount; dateEntries++)//changed from dateloop to rowcount
                    {
                        dateOffset = dateEntries * tableHeight;
                        try
                        {
                            
                              //dateList checking                            
                              String tmpKeyValue = dataInstance.dataTable.getDataAt(dateEntries, 0);
                              String trimmedKeyValue = stripNulls(tmpKeyValue);
                              
                              thisCaptureDate = (String)dateList.get((Object)trimmedKeyValue);
                            
                              //now check date compared to date selection array
                              boolean dateIncluded = false;
                              for (int dateCheckLoop = 0; dateCheckLoop < dateArrayList.size(); dateCheckLoop++)
                              {
                                  dateCheckItemAllowed = (String)dateArrayList.get(dateCheckLoop);
                                  
                                  if (dateCheckItemAllowed.equalsIgnoreCase(thisCaptureDate.substring(0,dateCheckItemAllowed.length())) == true)
                                  {
                                      dateIncluded = true;
                                      break;
                                  }
                                  
                              }
                              if (dateIncluded == false)
                              {
                                  continue;
                              }
                           //add this capture date 
                           captureDateList.add((String)thisCaptureDate);
                            
                           for (int fieldIndex = 0; fieldIndex < colCount; fieldIndex++)
                           {
                              fieldLabelValue = theColumnAliases[fieldIndex];
                              fieldName = theColumnNames[fieldIndex].trim();
                              if (fieldName.indexOf("FILLER") == 0)
                              {
                                  continue;
                              }
                                 
                              fieldType = theColumnTypes[fieldIndex];
                              String theFieldValue = dataInstance.dataTable.getDataAt(dateEntries, fieldIndex);
                              fieldValue = stripNulls(theFieldValue);
                              
                              fieldLength = Integer.parseInt(dataInstance.dataTable.getColumnLength(fieldName));
                              fieldLock = theColumnLocks[fieldIndex];
                              fieldDisplay = theColumnDisplays[fieldIndex];
                              fieldHighLight = theColumnHighlights[fieldIndex];
                              labelDisplay = theLabelDisplays[fieldIndex];
                              
                              
                              //this is for displaying capture date item
                              if (fieldIndex == 0)
                              {
                                    if ((dateList != null)  && (dateList.containsKey((Object)fieldValue)))
                                    {
                                         captureDate = (String)dateList.get((Object)fieldValue);
                                    } else
                                    {
                                        captureDate = "Null"; 
                                    }
                              }

                              
                              
                              if (fieldType.equalsIgnoreCase("PRIMARY"))
                              {
                                   String zeroPadding = "";
                                   for (int keyloop = 0; keyloop < fieldLength - fieldValue.length(); keyloop++)
                                   {
                                      zeroPadding = zeroPadding + "0";
                                   }
                                   fieldValue = zeroPadding + fieldValue;
                                   if (fieldDisplay == true)
                                   {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)fieldValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                   } 
                                   isEmpty = false;
                               } else
                               if ((fieldType.equalsIgnoreCase("FOREIGN")) || (fieldType.equalsIgnoreCase("GROUP")) || (fieldType.equalsIgnoreCase("GROUPKEY")))
                               {
                                   lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
                                   if (lookupTableData == null)
                                   {
                                       System.out.println("No Lookup found for:" + fieldName);
                                   }
                                   Set keySet = lookupTableData.keySet();
                                   Iterator iter = keySet.iterator();
                                   boolean valueFound = false;
                                   while (iter.hasNext())
                                   {
                                       //String theKey = (String)iter.next();
                                       //String theValue = (String)lookupTableData.get((Object)theKey);
                                       theExtKey = (String)iter.next();
                                       theExtValue = (String)lookupTableData.get((Object)theExtKey);
                                       if (theFieldValue.equalsIgnoreCase(theExtKey))
                                       {
                                           valueFound = true;
                                           selectedValue = theExtValue;
                                           break;
                                       }
                                    }
                                    if(valueFound == false)
                                    {
                                       selectedValue = "Not Applicable"; 
                                    }
                                    if (fieldDisplay == true)
                                    {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                    }
                                    isEmpty = false;
                               }  else
                               if (fieldType.equalsIgnoreCase("FIELD_FOREIGN"))
                               {
                                   lookupTableData = (HashMap)lookupTableDataCollection.get((Object)fieldName);
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
                                    //if (fieldDisplay == true) 
                                    //{
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                    //}
                                    isEmpty = false;
                                } else
                                 if ((fieldType.equalsIgnoreCase("BOOLEAN_FIELD")) || (fieldType.equalsIgnoreCase("BOOLEAN_GROUP")))
                                 {
                                     if (theFieldValue.equalsIgnoreCase("0"))
                                        selectedValue = "No";
                                     else
                                        selectedValue = "Yes";
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 } else
                                 if (fieldType.equalsIgnoreCase("TO_BY_BOOLEAN_FIELD"))
                                 {
                                    if (theFieldValue.equalsIgnoreCase("0"))
                                        selectedValue = "To"; 
                                    else
                                        selectedValue = "By";
                                    if (fieldDisplay == true)
                                    {
                                      fieldVarItemsMap = new HashMap(5);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                    }
                                   isEmpty = false;
                                 } else
                                 if (fieldType.equalsIgnoreCase("PHONE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("CELL_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("(###) ###-####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("AREA_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("ID_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("#############");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("BIRTH_DAY_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("##/##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                      fieldVarItemsMap = new HashMap(1);       
                                      fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                      fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                      fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                      fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                      fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                      fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                      fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                      fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("TEXT_AREA_FIELD"))
                                 {
                                        if ((theFieldValue != null) && (theFieldValue.length() > 0) && (fieldDisplay == true))
                                        {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }   else
                                 if (fieldType.equalsIgnoreCase("DOCUMENT_AREA_FIELD"))
                                 {
                                     if (fieldDisplay == true)
                                     {
                                        imageList.clear();
                                        String[] docFileNames = getFileList(theFieldValue);
                                        for (int docIndex = 0; docIndex < docFileNames.length; docIndex++)
                                        {
                                            ImageIcon theImage = new ImageIcon(docScanPath + docFileNames[docIndex]);
                                            imageList.add((Object)theImage);
                                        }
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)imageList.clone()); //this adds an array of images!!
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());

                                     }
                                   isEmpty = false;
                                 } else
                                 if (fieldType.equalsIgnoreCase("INITIAL_FIELD"))
                                 {
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap.clone());
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList);
                                     }
                                 } else
                                 if (fieldType.equalsIgnoreCase("DATE_TIME_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-## ##:##:##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if (fieldType.equalsIgnoreCase("DATE_FIELD"))
                                 {
                                     formatter = new  MaskFormatter("####-##-##");
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }  else
                                 if ((fieldType.equalsIgnoreCase("SPINMONTHYEAR")) || (fieldType.equalsIgnoreCase("SPINLITMONTHYEAR")))
                                 {
                                     String tmpField = formatDate(theFieldValue);

                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)tmpField);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }   else
                                 if (fieldType.equalsIgnoreCase("GROUPDEPENDANT"))
                                 {
                                     String mask = "";
                                     for (int maskloop = 0; maskloop < fieldLength; maskloop++)
                                     {
                                         mask = mask + "*";
                                     }
                                     formatter = new  MaskFormatter(mask);
                                     ftf = new JFormattedTextField(formatter);
                                     ftf.setValue(theFieldValue);
                                     selectedValue = ftf.getText();
                                     if ((fieldDisplay == true) && (theFieldValue.length() > 0))
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)selectedValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 }     else
                                 if (fieldType.equalsIgnoreCase("SPINTEMP"))
                                 {
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 } else
                                 {
                                     if (fieldDisplay == true)
                                     {
                                            fieldVarItemsMap = new HashMap(1);       
                                            fieldVarItemsMap.put((Object)"FIELDVALUE",(Object)theFieldValue);
                                            fieldVarItemsMap.put((Object)"FIELDLENGTH",(Object)String.valueOf(fieldLength));
                                            fieldVarItemsMap.put((Object)"FIELDTYPE",(Object)fieldType);
                                            fieldVarItemsMap.put((Object)"FIELDLOCK",(Object)String.valueOf(fieldLock));
                                            fieldVarItemsMap.put((Object)"FIELDDISPLAY",(Object)String.valueOf(fieldDisplay));
                                            fieldVarItemsMap.put((Object)"FIELDHIGHLIGHT",(Object)String.valueOf(fieldHighLight));
                                            fieldVarItemsMap.put((Object)"LABELDISPLAY",(Object)String.valueOf(labelDisplay));
                                            fieldVarItemsMap.put((Object)"LABELVALUE",(Object)fieldLabelValue);
                                      ArrayList  itemList = (ArrayList)fieldVarList.get((Object)fieldName);
                                      if (itemList == null)
                                      {
                                          itemList = new ArrayList(1);
                                      }
                                      itemList.add(fieldVarItemsMap);
                                      if (fieldVarList.get((Object)fieldName) != null)
                                      {    
                                         fieldVarList.remove((Object)fieldName);
                                      }
                                      fieldVarList.put((Object)fieldName,(Object)itemList.clone());
                                     }
                                   isEmpty = false;
                                 } //end else
                            } //end for fieldindex
                        } //end try
                        catch (Exception ex) 
                        {
                            throw new MTException(InfoManager.PANE_CREATION_ERROR, ex.getMessage());
                        }
                    } //end of datelist entries
                    //if (rowCount == 0)
                    //{
                    //    isEmpty = true;
                    //}
                    //fieldVarList.clear();
                    tableOffset += dateOffset;
                    HashMap tempHashList = (HashMap)fieldVarList.clone();
                    tableVarList.put((Object)theTableName,(Object)tempHashList);
                    //now add the date by table hashmap
                    dateByTableMap.put((Object)theTableName,(Object)captureDateList.clone()); 
                    //--ccc---
                    dataInstance.closeConnection();
                    dbAccessCollection.remove((Object)theTableName);
                    dataInstance = null;
                    System.gc();

                    
                } //end for tablelist loop
                if (isEmpty == false)
                {
                   buildDisplayPage(displayPanel, pageItemList, pageItemToTypeMap, staticTextItemMap, textItemMap, tableItemMap, imageItemMap, tableVarList, looseTextItemList,columnInCount, noDisplayItemList, textLabelComboItemMap, labelTextVarMap, dateByTableMap);
                   JScrollPane displayScrollPane = new JScrollPane(displayPanel);
                   //displayScrollPane.add();
                   this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                   this.add(displayScrollPane);
                   printReportButton = new JButton("Print Report");
                   controlPane.setLayout(new BorderLayout());
                   controlPane.setBorder(compoundBorder);
                   controlPane.add(printReportButton, BorderLayout.CENTER);
                   this.add(controlPane);
                }
                               
            } 
            catch (MTException e)
            {
                throw new MTException(e.getExceptionType(), e.getErrorMessage());
            }
            catch (Exception e)
            {
                throw new MTException(InfoManager.VIEW_BUILDER_ERROR, e.getMessage());
            }
        } //end initSimpleDataView
    }   //end view class
    
    
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
                  dateListArray = theDataModel.getDateListArray();
                  
                  theDataView = new DataView("INFOBYXML", theDataModel, displayHeight, displayWidth);//displayViewType
                  removeAll();
                  add(theDataView);
                  CtoMAdaptor CtoM = new CtoMAdaptor(theDataView);
                  DisplayInfoByKeyMVCApp.updateUI();
                  theDataModel = null;
               }
            }
            catch (MTException exc)
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            
         }
   }
   

    public DisplayInfoByKeyMVC(String selectionModelName, String modelName, String viewType, String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        DisplayInfoByKeyMVCApp = this;
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }

    public DisplayInfoByKeyMVC(String keyValue, String modelName, int tabPlacement , String viewType, String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        DisplayInfoByKeyMVCApp = this;
        initMainComponent(keyValue, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }


    public DisplayInfoByKeyMVC(String selectionModelName, String modelName, String viewType, int height, int width, String userName, char[] password) 
    {
        DisplayInfoByKeyMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabsTop, InfoManager.OS_VERSION, height, width,userName,password);
        theScrollPane1 = typePanel;
    }

    public DisplayInfoByKeyMVC(String selectionModelName, String modelName, String viewType, int tabPlacement, int height, int width, String userName, char[] password) 
    {
        DisplayInfoByKeyMVCApp = this;
        /** Creates new Main Dialog Type Panel */
        initComponents(selectionModelName, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, height, width,userName,password);
        theScrollPane1 = typePanel;
    }

    public DisplayInfoByKeyMVC(String keyValue, String modelName, ArrayList dateList,int tabPlacement , String viewType, String userName, char[] password) 
    {
        
        /** Creates new  Main Dialog Type Panel */
        DisplayInfoByKeyMVCApp = this;
        initMainComponent(keyValue, modelName, viewType, dateList, tabPlacement, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }
   
    
    public void initComponents(String selectionModelName, String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
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
            displayModelName  = modelName;
            displayViewType = viewType;
            displayTabPlacement = tabPlacement;
            displayHeight = _height;
            displayWidth = _width;
            selectionDataModel = new  MTTableDataModel(selectionModelName, userName, password);
            theDataSelectView = new DataSelectView(viewType, tabPlacement, selectionDataModel, _height, _width);
            super.add(theDataSelectView);
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
            
            theDataModel = new MTTableDataModel(modelName, keyValue, userName, password);

            theDataView = new DataView(viewType,  theDataModel, _height, _width);
            if (isEmpty == false)
            {
               CtoMAdaptor CtoM = new CtoMAdaptor(theDataView);
               add(theDataView);
            }
            DisplayInfoByKeyMVCApp.updateUI();
            theDataModel = null;
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }

    public void initMainComponent(String keyValue, String modelName, String viewType, ArrayList dateSelectedList, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
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
            
            theDataModel = new MTTableDataModel(modelName, keyValue, userName, password);

            //theDataView = new DataView(viewType,  theDataModel, _height, _width);
            theDataView = new DataView(viewType,  dateSelectedList, theDataModel, _height, _width);
            
            if (isEmpty == false)
            {
               CtoMAdaptor CtoM = new CtoMAdaptor(theDataView);
               add(theDataView);
            }
            DisplayInfoByKeyMVCApp.updateUI();
            theDataModel = null;
           
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }
    
    public void closeConnection()
    {
        if (theDataModel != null)
        {
            if (theDataModel.dbAccessInstance != null)
            {
                theDataModel.dbAccessInstance.closeConnection();
            }
        }
    }

    public void closeDBInstance()
    {
        if (theDataModel != null)
        {
            if (theDataModel.dbAccessInstance != null)
            {
                theDataModel.dbAccessInstance = null;
            }
        }
    }

}
