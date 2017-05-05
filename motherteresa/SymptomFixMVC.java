/*
 * DiagnosticAidMVC.java
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
import java.io.*;



public class SymptomFixMVC extends JPanel {
   
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
  private static final String INFO_VIEW = "INFO";
  private static final int tabsTop = JTabbedPane.TOP;
  private static final int tabsBottom = JTabbedPane.BOTTOM;
  private static final int tabsLeft = JTabbedPane.LEFT;
  private static final int tabsRight = JTabbedPane.RIGHT;
  private SymptomFixMVC SymptomFixMVCApp;
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
  private JLabel itemSavedLabel;
  public  String fieldName = "";
  private String imagePath = "";
  private RandomAccessFile symptomOutFile;
  private RandomAccessFile symptomInFile;
  private RandomAccessFile symptomMapFile;
  private RandomAccessFile lastItemSavedFile;
  private String docScanPath = "";
  private int selectedRow = 0;
  public JTextField editBox;
  public JLabel techSymptomName = new JLabel("None Selected");
  public JLabel popSymptomName = new JLabel("None Selected");
  
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
  private Vector inputVecList,mapVecList = null;
  private DefaultListModel techNameListModel = new DefaultListModel();
  private DefaultListModel inputNameListModel = new DefaultListModel();
  private DefaultListModel similarSymptomListModel = new DefaultListModel();
  JList inputKeyList;
  JList inputMapList;
  JList similarMapList;
  public String lastItemSaved = null;
  public int m_maxNumPage = 1;
  public JComponentVista vista; 
  public ArrayList inputList = new ArrayList(5);
  public HashMap techItemMapList = new HashMap(100);
  public HashMap popItemMapList = new HashMap(100);
  public HashMap inputByMapCollection = new HashMap(5);
  public HashMap MapByInputNameCollection = new HashMap(5);
  public HashMap inputByMapNameCollection = new HashMap(5);
  public HashMap diseaseActiveList = new HashMap(5);
  //public ListModel outputDiseaseListModel = new DefaultListModel();
  public JList outputDiseaseList = new JList();
  public JScrollPane symptomNameScrollPane = null;
  public JPanel symptomNamePanel = new JPanel();
  public GridBagLayout gridBag = new GridBagLayout();
  public GridBagConstraints constraints = new GridBagConstraints();
  public Vector diseaseVecList = new Vector(5,5);
  public Vector symptomVecList = new Vector(5,5);
  public JList symptomNameList = null;
  private String defUserName = null;
  private char[] defPassword = null;

          public  HashMap getSymptomTechMapList()
        {
           String theToken = "";
           String inString;
           HashMap returnList = new HashMap(1000);
           int listIndex = 0;
            while (true)
            {
                // try reading a line from the file
                try
                {
                    inString = symptomMapFile.readLine() ;
                    listIndex++;
                    if (inString == null)
                    {
                        break;
                    }
                    StringTokenizer symptomTokens = new StringTokenizer(inString,"=|#");
                    String popItem  = ((String)symptomTokens.nextToken()).trim();
                    String techItem = ((String)symptomTokens.nextToken()).trim();
                    if (popItem.length() > 1)
                    {
                        popItem = popItem.substring(0,1).toUpperCase() + popItem.substring(1,popItem.length()).toLowerCase();
                    }
                    if (techItem.length() > 1)
                    {
                        techItem = techItem.substring(0,1).toUpperCase() + techItem.substring(1,techItem.length()).toLowerCase();
                    }
                    returnList.put((Object)popItem.trim(),(Object)techItem.trim());
                    System.out.println("ListIndex=" + listIndex);
                }
                catch (IOException e)
                {
                    System.out.println("Exception:" + e.getMessage());
                }
            }

           return returnList;
        }
          public  HashMap getSymptomPopMapList()
        {
           String theToken = "";
           String inString;
           HashMap returnList = new HashMap(1000);
           int listIndex = 0;
           try {
               symptomMapFile.seek(0);
           }
           catch (IOException e) {
               System.out.println("Exception:" + e.getMessage());
           }
            while (true)
            {
                // try reading a line from the file
                try
                {
                    inString = symptomMapFile.readLine() ;
                    listIndex++;
                    if (inString == null)
                    {
                        break;
                    }
                    StringTokenizer symptomTokens = new StringTokenizer(inString,"=|#");
                    String popItem  = ((String)symptomTokens.nextToken()).trim();
                    String techItem = ((String)symptomTokens.nextToken()).trim();
                    if (popItem.length() > 1)
                    {
                        popItem = popItem.substring(0,1).toUpperCase() + popItem.substring(1,popItem.length()).toLowerCase();
                    }
                    if (techItem.length() > 1)
                    {
                        techItem = techItem.substring(0,1).toUpperCase() + techItem.substring(1,techItem.length()).toLowerCase();
                    }
                    returnList.put((Object)techItem.trim(),(Object)popItem.trim());
                    //System.out.println("listIndex=" + listIndex);
                }
                catch (IOException e)
                {
                    System.out.println("Exception:" + e.getMessage());
                }
            }

           return returnList;
        }

          public boolean writeSymptomMapFile(String data) {
      
      // try to write all the data to the file in one shot
      try {
          symptomMapFile.write(data.getBytes());
      }
      catch (IOException e) {
          // if we can't write to the file, return false
          return false;
      }
      // operation successful; return true
      return true;
  }

     public boolean openSymptomMapFileForUpdate(String filename) {
      
      String  filePath = InfoManager.TARGET_VOLUME + "\\Development\\Medical Data\\SymptomFix\\";
      try {
          symptomMapFile = new RandomAccessFile(filePath + filename, "rw");
          symptomMapFile.seek(symptomMapFile.length());
      }
      catch (IOException e) {
          // if there was an error opening or writing to the file, return false
          return false;
      }
      return true;
      
  }

  public boolean openSymptomMapFile(String filename) {
      
      String  filePath = InfoManager.TARGET_VOLUME + "\\Development\\Medical Data\\SymptomFix\\";
      try {
          symptomMapFile = new RandomAccessFile(filePath + filename, "r");
      }
      catch (IOException e) {
          // if there was an error opening or writing to the file, return false
          return false;
      }
      return true;
      
  }
  
  public boolean openLastItemSavedFile(String filename) {
      
      String  filePath = InfoManager.TARGET_VOLUME + "\\Development\\Medical Data\\SymptomFix\\";
      try {
          lastItemSavedFile = new RandomAccessFile(filePath + filename, "rw");
      }
      catch (IOException e) {
          // if there was an error opening or writing to the file, return false
          return false;
      }
      return true;
      
  }

    public boolean closeLastItemSavedFile()
    {
        try
        {
            lastItemSavedFile.close();
        }
        catch (IOException e)
        {
            // if we can't write to the file, return false
            return false;
        }
        return true;
    }

    public boolean writeLastItemSavedFile(String data) 
    {
      
      // try to write all the data to the file in one shot
      try {
          lastItemSavedFile.setLength(0);
          lastItemSavedFile.write(data.getBytes());
      }
      catch (IOException e) {
          // if we can't write to the file, return false
          return false;
      }
      // operation successful; return true
      return true;
    }

    public boolean closeSymptomInFile()
    {
        try
        {
            symptomInFile.close();
        }
        catch (IOException e)
        {
            // if we can't write to the file, return false
            return false;
        }
        return true;
    }

    public boolean closeSymptomOutFile()
    {
        try
        {
            symptomOutFile.close();
        }
        catch (IOException e)
        {
            // if we can't write to the file, return false
            return false;
        }
        return true;
    }

    public boolean closeSymptomMapFile()
    {
        try
        {
            symptomMapFile.close();
        }
        catch (IOException e)
        {
            // if we can't write to the file, return false
            return false;
        }
        return true;
    }
  
    public boolean writeSymptomOutFile(String data) 
    {
      
      // try to write all the data to the file in one shot
      try {
          symptomOutFile.write(data.getBytes());
      }
      catch (IOException e) {
          // if we can't write to the file, return false
          return false;
      }
      // operation successful; return true
      return true;
    }

  
  // the Models
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
                controller.updateTextButton.addActionListener(this);
                controller.updateTechButton.addActionListener(this);
                controller.doCorrectionButton.addActionListener(this);
                controller.insertTechListButton.addActionListener(this);
                
                //controller.updateExitButton.addActionListener(this);
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
                if (e.getSource() == controller.updateTextButton)
                {
                    String oldItem = ((String)inputKeyList.getSelectedValue()).trim();
                    String replaceMentItem = editBox.getText().trim();
                    replaceMentItem = replaceMentItem.substring(0,1).toUpperCase() + replaceMentItem.substring(1,replaceMentItem.length()).toLowerCase();
                    String updateString = oldItem + " | " + replaceMentItem + "\n";
                    boolean x = writeSymptomOutFile(updateString);
                    itemSavedLabel.setText("<html> <font color = \"blue\">" + oldItem + "-changed to-" + replaceMentItem + "</font></html>");
                    boolean res = openLastItemSavedFile("LastSave.txt");
                    boolean res2 = writeLastItemSavedFile(oldItem);
                    closeLastItemSavedFile();

                    
                    
                } else
                if (e.getSource() == controller.updateTechButton)
                {
                    String oldItem = ((String)inputKeyList.getSelectedValue()).trim();
                    String replaceMentItem = techSymptomName.getText().trim();
                    replaceMentItem = replaceMentItem.substring(0,1).toUpperCase() + replaceMentItem.substring(1,replaceMentItem.length()).toLowerCase();
                    String updateString = oldItem + " | " + replaceMentItem + "\n";
                    boolean x = writeSymptomOutFile(updateString);
                    itemSavedLabel.setText("<html> <font color = \"blue\">" + oldItem + "-changed to-" + replaceMentItem + "</font></html>");
                    boolean res = openLastItemSavedFile("LastSave.txt");
                    boolean res2 = writeLastItemSavedFile(oldItem);
                    closeLastItemSavedFile();

                } else
                if (e.getSource() == controller.doCorrectionButton)
                {
                    closeSymptomOutFile();
                } else
                if (e.getSource() == controller.insertTechListButton)
                {
                    openSymptomMapFileForUpdate("PopSymptoms.txt");
                    String oldItem = ((String)inputKeyList.getSelectedValue()).trim();
                    String replaceMentItem = editBox.getText().trim();
                    oldItem = oldItem.substring(0,1).toUpperCase() + oldItem.substring(1,oldItem.length()).toLowerCase();
                    replaceMentItem = replaceMentItem.substring(0,1).toUpperCase() + replaceMentItem.substring(1,replaceMentItem.length()).toLowerCase();
                    String updateString = oldItem + " # " + replaceMentItem + "\n";
                    boolean x = writeSymptomMapFile(updateString);
                    closeSymptomMapFile();
                    
                    //now get the technical to popular word map.
                    boolean result = openSymptomMapFile("PopSymptoms.txt");
                    techItemMapList = getSymptomTechMapList();
                    closeSymptomMapFile();
                    //mapVecList = new Vector(1000,5);
                    if (techItemMapList != null) {
                        techNameListModel.clear();
                        TreeMap myTree = new TreeMap(techItemMapList);
                        Set synonymMapKeySet = myTree.keySet();
                        Iterator synonymMapIter = synonymMapKeySet.iterator();
                        while (synonymMapIter.hasNext()) {
                            String thePopName = (String)synonymMapIter.next();
                            String theTechName = (String)techItemMapList.get((Object)thePopName);
                            techNameListModel.addElement(thePopName);
                        }
                        
                    }
                    int item = inputMapList.getNextMatch(oldItem,0,javax.swing.text.Position.Bias.Forward);
                    if (item != -1) {
                        inputMapList.setSelectedIndex(item);
                        inputMapList.ensureIndexIsVisible(item);
                    }
                }
               
            }
            //catch (MTException exc) 
            //{
            //   JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            //}
            catch (Exception ex) 
            {
               JOptionPane.showMessageDialog(theDataView, ex.getMessage(), "Unspecified Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } 

    class MTTableDataModel extends MTBusinessModel 
    {
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

    public class DataView extends JPanel
    {
        private JTabbedPane tabbedPane;
        public JButton updateTextButton;
        public JButton insertTechListButton;
        public JButton updateTechButton;
        public JButton doCorrectionButton;
        
        public JButton updateExitButton;
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
        
        public  ArrayList getSymptomInFileList()
        {
           String theToken = "";
           String inString;
           ArrayList returnList = new ArrayList(8000);
           int listIndex = 0;
            while (true)
            {
                // try reading a line from the file
                try
                {
                    inString = symptomInFile.readLine() ;
                    if (inString == null)
                    {
                        break;
                    }
                    StringTokenizer symptomTokens = new StringTokenizer(inString,"[]");
                    //theToken = (String)symptomTokens.nextToken();
                    theToken = (String)symptomTokens.nextToken();
                    returnList.add(theToken.trim());
                }
                catch (IOException e)
                {
                    System.out.println("Exception:" + e.getMessage());
                }
            }

           return returnList;
        }

        public boolean openSymptomInFile(String filename) {
            
            String  filePath = InfoManager.TARGET_VOLUME + "\\Development\\Medical Data\\SymptomFix\\";
            try {
                symptomInFile = new RandomAccessFile(filePath + filename, "r");
            }
            catch (IOException e) {
                // if there was an error opening or writing to the file, return false
                return false;
            }
            return true;
            
        }
        

        public boolean openSymptomOutFile(String filename) {
            
            String  filePath = InfoManager.TARGET_VOLUME + "\\Development\\Medical Data\\SymptomFix\\";
            try {
                symptomOutFile = new RandomAccessFile(filePath + filename, "rw");
                symptomOutFile.seek(symptomOutFile.length());
            }
            catch (IOException e) {
                // if there was an error opening or writing to the file, return false
                return false;
            }
            return true;
            
        }

        
        
      
        public void initSimpleDataView(MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                int symptomMatchCount = 0;
                int percentSet = 50;
                int diseaseSymptomCount = 0;
                
                //Vector diseaseVecList = null;
                Border raisedBevel, loweredBevel, compoundBorder;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
                inputList = mtModel.getInputList();
                ArrayList tmpSymptomList = null;
                inputByMapCollection = mtModel.getInputByMapCollection();
                // check if this not right
                editBox = new JTextField("No Item");
                
                boolean result1 = openSymptomOutFile("CorrectionList.txt");
                boolean result2 = openLastItemSavedFile("LastSave.txt");
                try
                {
                    lastItemSaved = lastItemSavedFile.readLine() ;
                    if (lastItemSaved == null)
                    {
                        itemSavedLabel = new JLabel("<html> <font color = \"blue\"> None Saved Yet! </font></html>");
                    } else
                    {
                        itemSavedLabel = new JLabel("<html> <font color = \"blue\">" + lastItemSaved + "</font></html>"); 
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Exception:" + e.getMessage());
                }
                //first get the symptom list that must be corrected
                boolean result = openSymptomInFile("SymptomList.cnt");
                inputList = getSymptomInFileList();
                closeSymptomInFile();
                //inputVecList = new Vector(1000,5);
                for (int loop = 0; loop < inputList.size(); loop++)
                {
                   String inputItem =  (String)inputList.get(loop);
                   //String inputName = (String)inputByMapNameCollection.get((Object)inputCode);
                   inputNameListModel.addElement(inputItem); 
                }
                //inputKeyList.setModel(InputListModel);
                inputKeyList = new JList(inputNameListModel);
                inputKeyList.setVisibleRowCount(20);
                //inputKeyList.setAutoscrolls(true);
                inputKeyList.setMaximumSize(new Dimension(400, 135000));
                inputKeyList.setPreferredSize(new Dimension(400, 135000));
                inputKeyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                
                
                //now get the technical to popular word map.
                result = openSymptomMapFile("PopSymptoms.txt");
                techItemMapList = getSymptomTechMapList();
                popItemMapList = getSymptomPopMapList();
                closeSymptomMapFile();
                //mapVecList = new Vector(1000,5);
                if (techItemMapList != null) {
                    
                    //first popular items
                    TreeMap myTree = new TreeMap(techItemMapList);
                    Set synonymMapKeySet = myTree.keySet();
                    Iterator synonymMapIter = synonymMapKeySet.iterator();
                    while (synonymMapIter.hasNext()) 
                    {
                        String thePopName = (String)synonymMapIter.next();
                        String theTechName = (String)techItemMapList.get((Object)thePopName);
                        techNameListModel.addElement(thePopName);
                    }
                    
                    //now reverse for technical items 
                    TreeMap myPopTree = new TreeMap(popItemMapList);
                    Set synonymPopMapKeySet = myPopTree.keySet();
                    Iterator synonymPopMapIter = synonymPopMapKeySet.iterator();
                    while (synonymPopMapIter.hasNext()) {
                        String thePopName = (String)synonymPopMapIter.next();
                        String theTechName = (String)popItemMapList.get((Object)thePopName);
                        similarSymptomListModel.addElement(thePopName);
                    }
                    
                }
                //this is the list of popular names!!!
                inputMapList = new JList(techNameListModel);
                inputMapList.setVisibleRowCount(20);
                inputMapList.setMaximumSize(new Dimension(300, 120000));
                inputMapList.setPreferredSize(new Dimension(300, 120000));
                inputMapList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                //this has changed to a list of technical names
                similarMapList = new JList(similarSymptomListModel);
                similarMapList.setVisibleRowCount(200);
                similarMapList.setMaximumSize(new Dimension(300,120000));
                similarMapList.setPreferredSize(new Dimension(300,120000));
                similarMapList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                //populateDiseaseList(percentSet,inputByMapCollection,inputList,MapByInputNameCollection);
                
                int rowCount  =  0;
                int colCount = 0;
                int dateEntries = 0;
                int percentageOfSymptoms = 0;
                int panelRows = 0;
                String labelFont = "Serif";
                String valueFont = "Bookman Antiqua";
                String objectCoords = null;
                ArrayList tempList;
                HashMap dateList = null;
                int rowCoord = 0;
                int colCoord = 0;
                String theToken = "";
                displayPanel = new JPanel();
                JPanel controlPane = new JPanel();
                String theTableName = null;
                String selectedListItem = null;
                String tempDiseaseKey = null;
                
                String theGroupLabel = null;
                theGroupLabel = mtModel.getGroupName();
                JLabel groupLabel = new JLabel(theGroupLabel);

                //GridBagLayout gridBag = new GridBagLayout();
                //GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = colCoord;
                constraints.gridy = rowCoord;
                constraints.gridwidth = 3;
                constraints.gridheight = 1;
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.anchor = GridBagConstraints.CENTER;
                displayPanel.setLayout(gridBag);
                groupLabel.setBorder(BorderFactory.createEtchedBorder());
                groupLabel.setForeground(new Color(0,0,255));
                JLabel savedLabel = new  JLabel("Last Item saved were:-");
                gridBag.setConstraints(savedLabel, constraints);
                displayPanel.add(savedLabel);
                constraints.gridx = colCoord + 1;
                gridBag.setConstraints(itemSavedLabel, constraints);
                displayPanel.add(itemSavedLabel);
                
                //now build the symptom panel and list
                objectCoords = "2,0";
                constraints.gridwidth = 1;
                JPanel inputSymptomPanel = new JPanel();
                JLabel inputKeyLabel = new JLabel("Old Symptoms");
                JScrollPane inputKeyListScrollPane = new JScrollPane(inputKeyList);
                inputKeyListScrollPane.setBorder(compoundBorder);
                inputKeyListScrollPane.setWheelScrollingEnabled(true);
                inputSymptomPanel.setLayout(new BorderLayout());
                //inputSymptomPanel.setPreferredSize(new Dimension(300, 400));
                //inputSymptomPanel.setMaximumSize(new Dimension(300, 400));
                inputSymptomPanel.add(inputKeyLabel,BorderLayout.NORTH);
                inputSymptomPanel.add(inputKeyListScrollPane,BorderLayout.SOUTH);
                gridBag.setConstraints(inputSymptomPanel, constraints);
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, inputSymptomPanel);
                inputKeyList.addListSelectionListener(new diseaseListListener());
                

                //now build the  technical symptom panel.
                objectCoords = "2,1";
                constraints.gridwidth = 1;
                JPanel similarSymptomPanel = new JPanel();
                JLabel similarSymptomLabel = new JLabel("Technical Symptoms");
                JScrollPane similarSymptomScrollPane = new JScrollPane(similarMapList);
                similarSymptomScrollPane.setBorder(compoundBorder);
                similarSymptomPanel.setLayout(new BorderLayout());
                similarSymptomPanel.add(similarSymptomLabel,BorderLayout.NORTH);
                similarSymptomPanel.add(similarSymptomScrollPane,BorderLayout.SOUTH);
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, similarSymptomPanel);
                similarMapList.addListSelectionListener(new diseaseListListener());
                
                
                //now build the  popular symptom panel.
                objectCoords = "2,2";
                constraints.gridwidth = 1;
                JPanel outputDiseasePanel = new JPanel();
                JLabel outputDiseaseLabel = new JLabel("Popular Symptoms");
                JScrollPane outputDiseaseListScrollPane = new JScrollPane(inputMapList);
                outputDiseaseListScrollPane.setBorder(compoundBorder);
                outputDiseasePanel.setLayout(new BorderLayout());
                outputDiseasePanel.add(outputDiseaseLabel,BorderLayout.NORTH);
                outputDiseasePanel.add(outputDiseaseListScrollPane,BorderLayout.SOUTH);
                gridBag.setConstraints(outputDiseasePanel, constraints);
                gridBag.setConstraints(outputDiseasePanel, constraints);
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, outputDiseasePanel);
                inputMapList.addListSelectionListener(new diseaseListListener());



                objectCoords = "4,0";
                constraints.gridwidth = 3;
                JPanel sliderPanel = new JPanel();
                JLabel sliderLabel = new JLabel("Edit Item                                                -                                                                   Technical Description");
                sliderPanel.setLayout(new BorderLayout());
                sliderPanel.add(sliderLabel,BorderLayout.NORTH);
                editBox.setPreferredSize(new Dimension(500, 50));
                editBox.setMaximumSize(new Dimension(500, 50));
                sliderPanel.add(editBox,BorderLayout.WEST);
                techSymptomName.setHorizontalTextPosition(JLabel.LEFT);
                popSymptomName.setHorizontalTextPosition(JLabel.LEFT);
                //techSymptomName.setBackground(new Color(0,0,250));
                //techSymptomName.setForeground(new Color(0,255,0));
                sliderPanel.add(techSymptomName,BorderLayout.CENTER);
                sliderPanel.add(popSymptomName,BorderLayout.EAST);
                //Add buttons here !!!sliderPanel.add(percentSliderScrollPane,BorderLayout.SOUTH);
                //percentSliderScrollPane.setBorder(compoundBorder);
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, sliderPanel);
                
                JScrollPane displayScrollPane = new JScrollPane(displayPanel);
                //displayScrollPane.add();
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(displayScrollPane);
                
               updateTextButton = new JButton("Free Text Update");
               updateTechButton = new JButton("Update With Technical Item");
               doCorrectionButton = new JButton("Close All Files");
               insertTechListButton = new JButton("Insert Into TechList");
                //controlPane.setLayout(new BorderLayout());
                controlPane.setBorder(compoundBorder);
                controlPane.add(updateTextButton);
                controlPane.add(updateTechButton);
                controlPane.add(insertTechListButton);
                controlPane.add(doCorrectionButton);
                //updateExitButton = new JButton("Exit", new ImageIcon(imagePath + "updateexit24.gif"));
                //controlPane.add(updateExitButton, BorderLayout.EAST);
                this.add(controlPane);
                if (lastItemSaved != null)
                {
                int lastItemIndex = inputKeyList.getNextMatch(lastItemSaved,0,javax.swing.text.Position.Bias.Forward);
                if (lastItemIndex != -1) {
                    inputKeyList.setSelectedIndex(lastItemIndex);
                    inputKeyList.ensureIndexIsVisible(lastItemIndex);

                }
                }


                
            } // end of try
            /*catch (MTException e)
            {
                throw new MTException(e.getExceptionType(), e.getErrorMessage());
            }*/
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
    
        class diseaseListListener implements ListSelectionListener
        {
            public void valueChanged(ListSelectionEvent e)
            {
                String tempDiseaseKey = null;
                String selectedListItem = null;
                Vector diseaseVecList = new Vector(5,5);
                Border raisedBevel, loweredBevel, compoundBorder;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
                if (e.getValueIsAdjusting())
                {
                    return;
                }
                JList theList = (JList)e.getSource();
                if (theList.isSelectionEmpty())
                {
                    return;
                } else
                if (theList == inputKeyList)
                {
                    selectedListItem = (String)theList.getSelectedValue();
                    selectedListItem = selectedListItem.substring(0,1).toUpperCase() + selectedListItem.substring(1,selectedListItem.length()).toLowerCase();
                    int item = inputMapList.getNextMatch(selectedListItem,0,javax.swing.text.Position.Bias.Forward);
                    if (item != -1)
                    {
                        inputMapList.setSelectedIndex(item);
                        inputMapList.ensureIndexIsVisible(item);
                    }
                    editBox.setText(selectedListItem);
                    
                    /*similarSymptomListModel.clear();
                    for (int loop = 0; loop < inputNameListModel.size(); loop++)
                    {
                        String symptomItem = (String)inputNameListModel.get(loop);
                        StringTokenizer wordTokens = new StringTokenizer(symptomItem," ");	
                        int wordCount = wordTokens.countTokens();
                        for (int wordIndex = 0; wordIndex <  wordCount; wordIndex++)
                        {
                            String theToken = (String)wordTokens.nextToken();
                            
                            StringTokenizer selectedListItemTokens = new StringTokenizer(selectedListItem," ");
                            int selectedListItemCount = selectedListItemTokens.countTokens();
                            for (int selectedListItemIndex = 0; selectedListItemIndex <  selectedListItemCount; selectedListItemIndex++) {
                                String selectedListItemToken = (String)selectedListItemTokens.nextToken();
                                if ((selectedListItemToken.equalsIgnoreCase(theToken) == true) && (selectedListItemToken.length() > 2) && (selectedListItemToken.equalsIgnoreCase("the") == false) && (selectedListItemToken.equalsIgnoreCase("and") == false) && (selectedListItemToken.equalsIgnoreCase("over") == false) && (selectedListItemToken.equalsIgnoreCase("may") == false)){
                                    if (similarSymptomListModel.contains((Object)symptomItem) == false )
                                    {
                                       similarSymptomListModel.addElement(symptomItem);

                                    }
                                    
                                }
                                
                                
                            }
                        }
                    }
                    int simItem = similarSymptomListModel.indexOf(selectedListItem);
                    if (simItem != -1) {
                        similarMapList.setSelectedIndex(simItem);
                        similarMapList.ensureIndexIsVisible(simItem);
                    }*/
                    
                } else
                if (theList == inputMapList)
                {
                    selectedListItem = (String)theList.getSelectedValue();
                    String techString = (String)techItemMapList.get((Object)selectedListItem);
                    techString = techString.substring(0,1).toUpperCase() + techString.substring(1,techString.length()).toLowerCase();
                    techSymptomName.setText(techString);
                    int simItem = similarSymptomListModel.indexOf(techString);
                    if (simItem != -1) {
                        similarMapList.setSelectedIndex(simItem);
                        similarMapList.ensureIndexIsVisible(simItem);
                    }
                    displayPanel.repaint();
                    displayPanel.updateUI();

                } else
                if (theList == similarMapList)
                {
                    selectedListItem = (String)theList.getSelectedValue();
                    
                    String techString = selectedListItem.substring(0,1).toUpperCase() + selectedListItem.substring(1,selectedListItem.length()).toLowerCase();
                    String popString = (String)popItemMapList.get((Object)techString);
                    popSymptomName.setText(popString);
                    techSymptomName.setText(techString);
                }
                
            }
        }
        
    } //end class



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

                  //theDataSelectView.removeAll();//show(false);
                  theDataModel = new MTTableDataModel(displayModelName, selectedKeyValue,defUserName,defPassword);
                  theDataView = new DataView(displayViewType, theDataModel, displayHeight, displayWidth);
                  removeAll();
                  add(theDataView);
                  CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
                  SymptomFixMVCApp.updateUI();
               }
            }
            catch (MTException exc)
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            
         }
   }

    public SymptomFixMVC(String keyValue, String modelName, int tabPlacement , String viewType, String userName, char[] password) 
    {
        /** Creates new  Main Dialog Type Panel */
        SymptomFixMVCApp = this;
        initMainComponent(keyValue, modelName, viewType, tabPlacement, InfoManager.OS_VERSION, HEIGHT, WIDTH,userName,password);
        theScrollPane1 = typePanel;
    }


    public void initMainComponent(String keyValue, String modelName, String viewType, int tabPlacement, String _theOS, int _height, int _width, String userName, char[] password)
     {
        try
        {
            defUserName = userName;
            defPassword = password;
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
            CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
            add(theDataView);
            SymptomFixMVCApp.updateUI();
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }

}
