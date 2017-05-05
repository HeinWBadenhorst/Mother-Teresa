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


public class DiagnosticAidMVC extends JPanel {
   
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
  private DiagnosticAidMVC DiagnosticAidMVCApp;
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
  public ArrayList inputList = new ArrayList(5);
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

  public void populateDiseaseList(int percentageSet, HashMap inputByMapCollection, ArrayList inputList, HashMap MapByInputNameCollection)
  {
      int symptomMatchCount = 0;
      int diseaseSymptomCount = 0;
      int diseaseIndex = 0;
      String selectedListItem = null;
      diseaseActiveList.clear();
      if (symptomNameList != null)
      {    
        symptomVecList.removeAllElements();  
        symptomNameList.setListData(symptomVecList);
      }
      ArrayList tmpSymptomList = new ArrayList(5);
      Set inputByMapKeySet = inputByMapCollection.keySet();
      Iterator inputByMapKeySetIter = inputByMapKeySet.iterator();
      while (inputByMapKeySetIter.hasNext())
      {
          symptomMatchCount = 0;
          String tempDiseaseKey = (String)inputByMapKeySetIter.next();
          tmpSymptomList = (ArrayList)inputByMapCollection.get((Object)tempDiseaseKey);
          diseaseSymptomCount = tmpSymptomList.size();
          for (int loop2 = 0; loop2 < tmpSymptomList.size(); loop2++)
          {
              String symptomCode = (String)tmpSymptomList.get(loop2);
              for (int index = 0; index < inputList.size(); index ++)
              {
                  String tmpInputString = (String)inputList.get(index);
                  if (tmpInputString.equalsIgnoreCase(symptomCode) == true)
                  {
                      symptomMatchCount++;
                  }
              }
          }
          double symCount = (double)symptomMatchCount;
          double disCount = (double)diseaseSymptomCount;
          
          double percentMatch = ((symCount / disCount)*100);
          int percentInt = (int)percentMatch;
          
          if (percentMatch >= percentageSet)
          {
              String diseaseName = (String)MapByInputNameCollection.get((Object)tempDiseaseKey);
              diseaseActiveList.put((Object)diseaseName,(Object)String.valueOf(percentInt));
          }
      }
      diseaseVecList.clear();
      Set diseaseActiveKeySet = diseaseActiveList.keySet();
      Iterator diseaseActiveKeySetIter = diseaseActiveKeySet.iterator();
      while (diseaseActiveKeySetIter.hasNext())
      {
          String theDiseaseName = (String)diseaseActiveKeySetIter.next();
          String percentMatch =  (String)diseaseActiveList.get((Object)theDiseaseName);
          diseaseVecList.addElement(theDiseaseName + ", " + percentMatch + "%");
      }
      
      //outputDiseaseList = new JList(diseaseVecList);
      outputDiseaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      outputDiseaseList.removeAll();
      outputDiseaseList.setListData(diseaseVecList);
      //outputDiseaseList.setListData(diseaseVecList);

      
      int percTemp = 0;
      for (int loop = 0; loop < diseaseVecList.size(); loop++)
      {
          String tmpString = (String)diseaseVecList.get(loop);
          String tmpPercSymp = tmpString.substring(tmpString.indexOf(',')+2,tmpString.indexOf('%'));
          int percentageOfSymptoms = Integer.parseInt(tmpPercSymp);
          if (percentageOfSymptoms > percTemp)
          {
              selectedListItem = tmpString.substring(0,tmpString.indexOf(','));
              percTemp = percentageOfSymptoms;
              diseaseIndex = loop;
          }
      }
      if (selectedListItem != null)
      {
          outputDiseaseList.setSelectedIndex(diseaseIndex);
          Set MapByInputNameKeySet = MapByInputNameCollection.keySet();
          Iterator MapByInputNameIter = MapByInputNameKeySet.iterator();
          symptomVecList.removeAllElements();
          while (MapByInputNameIter.hasNext())
          {
              String tempDiseaseKey = (String)MapByInputNameIter.next();
              String diseaseName = (String)MapByInputNameCollection.get((Object)tempDiseaseKey);
              if (diseaseName.equalsIgnoreCase(selectedListItem) == true)
              {
                  ArrayList symptomKeyList = (ArrayList)inputByMapCollection.get((Object)tempDiseaseKey);
                  for (int loop = 0; loop < symptomKeyList.size(); loop++)
                  {
                      String symptomKey = (String)symptomKeyList.get(loop);
                      String symptomName = (String)inputByMapNameCollection.get((Object)symptomKey);
                      symptomVecList.add((Object)symptomName);
                  }
                  if (symptomNameList == null)
                  {
                      symptomNameList = new JList(symptomVecList);
                  } else
                  symptomNameList.setListData(symptomVecList);
                  //displayPanel.repaint();
                  //displayPanel.updateUI();
                  break;
              }
          }
      }
      //this.repaint();
      //this.updateUI();
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
                controller.printReportButton.addActionListener(this);
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
                if (e.getSource() == controller.printReportButton)
                {
                    PrinterJob pj = PrinterJob.getPrinterJob(); 
                    PageFormat format = new PageFormat();
                    Paper paper = new Paper();
                    paper.setSize(596,842);
                    format.setPaper(paper);
                    format = pj.pageDialog(format);
                    vista = new JComponentVista(displayPanel, format);
                    pj.setPageable(vista);
                    if (pj.printDialog()) 
                    { 
                        pj.print();
                    }
                }/* else
                if (e.getSource() == controller.updateExitButton)
                {
                     this..remove(this);
                     super.updateUI();
                }*/
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
        public JButton printReportButton;
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
        
        public void initSimpleDataView(MTTableDataModel mtModel, int height, int width) throws MTException
        {
            try
            {
                int symptomMatchCount = 0;
                int percentSet = 50;
                int diseaseSymptomCount = 0;
                
                Vector inputVecList = null;
                //Vector diseaseVecList = null;
                Border raisedBevel, loweredBevel, compoundBorder;
                raisedBevel = BorderFactory.createRaisedBevelBorder();
                loweredBevel = BorderFactory.createLoweredBevelBorder();
                compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
                inputList = mtModel.getInputList();
                ArrayList tmpSymptomList = null;
                inputByMapCollection = mtModel.getInputByMapCollection();
                // check if this not right
                HashMap mapByInputCollection = mtModel.getMapByInputCollection();
                inputByMapNameCollection = mtModel.getInputByMapNameCollection();
                MapByInputNameCollection = mtModel.getMapByInputNameCollection();
                ListModel InputListModel = new DefaultListModel();
                ListModel mapByInputModel = new DefaultListModel();
                ListModel InputByMapModel = new DefaultListModel();
                ListModel inputByMapNameModel = new DefaultListModel();
                ListModel mapByInputNameModel = new DefaultListModel();
                inputVecList = new Vector(5,5);
                //diseaseVecList = new Vector(5,5);
                for (int loop = 0; loop < inputList.size(); loop++)
                {
                   String inputCode =  (String)inputList.get(loop);
                   String inputName = (String)inputByMapNameCollection.get((Object)inputCode);
                   inputVecList.addElement(inputName); 
                }
                JList inputKeyList = new JList(inputVecList);
                inputKeyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                
                JSlider percentSlider = new JSlider(JSlider.HORIZONTAL,0,100,50);
                percentSlider.addChangeListener(new PercentSliderListener());
                percentSlider.setMajorTickSpacing(10);
                percentSlider.setMinorTickSpacing(5);
                percentSlider.setPaintTicks(true);
                percentSlider.setPaintLabels(true);
                percentSlider.setBorder(compoundBorder);
                populateDiseaseList(percentSet,inputByMapCollection,inputList,MapByInputNameCollection);
                
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
                gridBag.setConstraints(groupLabel, constraints);
                displayPanel.add(groupLabel);
                
                objectCoords = "2,0";
                constraints.gridwidth = 1;
                JPanel inputKeyPanel = new JPanel();
                JLabel inputKeyLabel = new JLabel("Patient Symptoms");
                JScrollPane inputKeyListScrollPane = new JScrollPane(inputKeyList);
                inputKeyListScrollPane.setBorder(compoundBorder);
                inputKeyPanel.setLayout(new BorderLayout());
                inputKeyPanel.add(inputKeyLabel,BorderLayout.NORTH);
                inputKeyPanel.add(inputKeyListScrollPane,BorderLayout.SOUTH);
                gridBag.setConstraints(inputKeyPanel, constraints);
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, inputKeyPanel);
                
                objectCoords = "2,1";
                constraints.gridwidth = 1;
                JPanel outputDiseasePanel = new JPanel();
                JLabel outputDiseaseLabel = new JLabel("Diseases");
                JScrollPane outputDiseaseListScrollPane = new JScrollPane(outputDiseaseList);
                outputDiseaseListScrollPane.setBorder(compoundBorder);
                outputDiseasePanel.setLayout(new BorderLayout());
                outputDiseasePanel.add(outputDiseaseLabel,BorderLayout.NORTH);
                outputDiseasePanel.add(outputDiseaseListScrollPane,BorderLayout.SOUTH);
                gridBag.setConstraints(outputDiseasePanel, constraints);
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, outputDiseasePanel);
                outputDiseaseList.addListSelectionListener(new diseaseListListener());
                int percTemp = 0;
                for (int loop = 0; loop < diseaseVecList.size(); loop++)
                {
                    String tmpString = (String)diseaseVecList.get(loop);
                    String tmpPercSymp = tmpString.substring(tmpString.indexOf(',')+2,tmpString.indexOf('%'));
                    percentageOfSymptoms = Integer.parseInt(tmpPercSymp);
                    if (percentageOfSymptoms > percTemp)
                    {    
                        selectedListItem = tmpString.substring(0,tmpString.indexOf(','));
                        percTemp = percentageOfSymptoms;
                    }

                }
                Set MapByInputNameKeySet = MapByInputNameCollection.keySet();
                Iterator MapByInputNameIter = MapByInputNameKeySet.iterator();
                boolean diseaseFound = false;
                while (MapByInputNameIter.hasNext())
                {
                    tempDiseaseKey = (String)MapByInputNameIter.next();
                    String diseaseName = (String)MapByInputNameCollection.get((Object)tempDiseaseKey);
                    if (diseaseName.equalsIgnoreCase(selectedListItem) == true)
                    {
                        symptomVecList.clear();
                        ArrayList symptomKeyList = (ArrayList)inputByMapCollection.get((Object)tempDiseaseKey);
                        for (int loop = 0; loop < symptomKeyList.size(); loop++)
                        {
                            String symptomKey = (String)symptomKeyList.get(loop);
                            String symptomName = (String)inputByMapNameCollection.get((Object)symptomKey);
                            symptomVecList.add((Object)symptomName);
                        }
                        String theObjectCoords = "2,2";
                        constraints.gridwidth = 1;
                        //symptomNameList = new JList(symptomVecList);
                        symptomNameList = new JList();
                        symptomNameList.setListData(symptomVecList);
                        JLabel symptomNameLabel = new JLabel("Disease Symptoms");
                        symptomNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        symptomNameScrollPane = new JScrollPane(symptomNameList);
                        symptomNameScrollPane.setBorder(compoundBorder);
                        symptomNamePanel.setLayout(new BorderLayout());
                        symptomNamePanel.add(symptomNameLabel,BorderLayout.NORTH);
                        symptomNamePanel.add(symptomNameScrollPane,BorderLayout.SOUTH);
                        gridBag.setConstraints(symptomNamePanel, constraints);
                        
                        addItemToPanel(displayPanel, gridBag, constraints, theObjectCoords, 0, symptomNamePanel);
                        diseaseFound = true;
                        break;
                    }
                }
                if (diseaseFound == false)
                {
                    String theObjectCoords = "2,2";
                    symptomNameList = new JList();
                    symptomNameList.setListData(symptomVecList);
                    JLabel symptomNameLabel = new JLabel("Disease Symptoms");
                    symptomNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    symptomNameScrollPane = new JScrollPane(symptomNameList);
                    symptomNameScrollPane.setBorder(compoundBorder);
                    symptomNamePanel.setLayout(new BorderLayout());
                    symptomNamePanel.add(symptomNameLabel,BorderLayout.NORTH);
                    symptomNamePanel.add(symptomNameScrollPane,BorderLayout.SOUTH);
                    gridBag.setConstraints(symptomNamePanel, constraints);
                    addItemToPanel(displayPanel, gridBag, constraints, theObjectCoords, 0, symptomNamePanel);
                }
                /*objectCoords = "2,5";
                constraints.gridwidth = 1;
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, panelHeadingLabel);
*/
                objectCoords = "4,0";
                constraints.gridwidth = 3;
                JPanel sliderPanel = new JPanel();
                JLabel sliderLabel = new JLabel("Percentage Symptoms Required");
                JScrollPane percentSliderScrollPane = new JScrollPane(percentSlider);
                sliderPanel.setLayout(new BorderLayout());
                sliderPanel.add(sliderLabel,BorderLayout.NORTH);
                sliderPanel.add(percentSliderScrollPane,BorderLayout.SOUTH);
                //percentSliderScrollPane.setBorder(compoundBorder);
                addItemToPanel(displayPanel, gridBag, constraints, objectCoords, 0, sliderPanel);
                
                JScrollPane displayScrollPane = new JScrollPane(displayPanel);
                //displayScrollPane.add();
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                this.add(displayScrollPane);
                printReportButton = new JButton("Print Info");
                controlPane.setLayout(new BorderLayout());
                controlPane.setBorder(compoundBorder);
                controlPane.add(printReportButton, BorderLayout.CENTER);
                //updateExitButton = new JButton("Exit", new ImageIcon(imagePath + "updateexit24.gif"));
                //controlPane.add(updateExitButton, BorderLayout.EAST);
                this.add(controlPane);
                
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
                {
                    selectedListItem = (String)theList.getSelectedValue();
                    selectedListItem = selectedListItem.substring(0,selectedListItem.indexOf(','));
                    Set MapByInputNameKeySet = MapByInputNameCollection.keySet();
                    Iterator MapByInputNameIter = MapByInputNameKeySet.iterator();
                    symptomVecList.removeAllElements();
                    while (MapByInputNameIter.hasNext())
                    {
                        tempDiseaseKey = (String)MapByInputNameIter.next();
                        String diseaseName = (String)MapByInputNameCollection.get((Object)tempDiseaseKey);
                        if (diseaseName.equalsIgnoreCase(selectedListItem) == true)
                        {
                            ArrayList symptomKeyList = (ArrayList)inputByMapCollection.get((Object)tempDiseaseKey);
                            for (int loop = 0; loop < symptomKeyList.size(); loop++)
                            {
                                String symptomKey = (String)symptomKeyList.get(loop);
                                String symptomName = (String)inputByMapNameCollection.get((Object)symptomKey);
                                symptomVecList.add((Object)symptomName);
                            }
                            symptomNameList.setListData(symptomVecList);
                            displayPanel.repaint();
                            displayPanel.updateUI();
                            break;
                        }
                    }

                }
            }
        }
        
    } //end class

    class PercentSliderListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting())
            {
                int percent = (int)source.getValue();
                populateDiseaseList(percent,inputByMapCollection,inputList,MapByInputNameCollection);
                
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

                  //theDataSelectView.removeAll();//show(false);
                  theDataModel = new MTTableDataModel(displayModelName, selectedKeyValue,defUserName,defPassword);
                  theDataView = new DataView(displayViewType, theDataModel, displayHeight, displayWidth);
                  removeAll();
                  add(theDataView);
                  CtoMAdaptor CtoM = new CtoMAdaptor(theDataView, theDataModel);
                  DiagnosticAidMVCApp.updateUI();
               }
            }
            catch (MTException exc)
            {
               JOptionPane.showMessageDialog(theDataView, exc.getErrorMessage(), exc.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
            }
            
         }
   }

    public DiagnosticAidMVC(String keyValue, String modelName, int tabPlacement , String viewType, String userName, char[] password) 
    {
        /** Creates new  Main Dialog Type Panel */
        DiagnosticAidMVCApp = this;
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
            DiagnosticAidMVCApp.updateUI();
        }
        catch (MTException e)
        {
           JOptionPane.showMessageDialog(theDataView, e.getErrorMessage(), e.getMessageHeading(), JOptionPane.ERROR_MESSAGE);
        }

     }

}
