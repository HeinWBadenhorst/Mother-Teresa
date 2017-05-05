/*
 * RifeTable.java
 *
 * Created on 24 June 2005, 09:50
 */

package motherteresa;

import java.sql.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.Dimension;
import java.awt.Color;

/**
 *
 * @author  HWBadenhorst
 */
public class RifeTablePanel extends JPanel implements ActionListener
{
    static final int xSize = 600;
    static final int ySize = 600;
    
    /** Creates a new instance of RifeTable */
    public RifeTablePanel()
    {
        //super( "Rife Disease List Navigator" );
        buildPanels(this.xSize, this.ySize);
        initDatabase();
    }
    public RifeTablePanel(int xSize, int ySize)
    {
        //super( "Rife Disease List Navigator" );
        buildPanels(xSize, ySize);
        initDatabase();
    }
    
    //Panels
    JPanel dbPanel = new JPanel();
    JPanel selectPanel = new JPanel();
    JPanel selectPanel1 = new JPanel();
    JPanel selectPanel2 = new JPanel();
    JPanel searchCommentFxPanel = new JPanel();
    JPanel searchPanel = new JPanel();
    JPanel commentAndFxPanel = new JPanel();
    JPanel commentPanel = new JPanel();
    JPanel fxPanel = new JPanel();
    JPanel setEditPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel viewPanel = new JPanel();
    JPanel tablePanel = new JPanel();
    JPanel tableTextAreaPanel = new JPanel();
    JPanel tableButtonsPanel = new JPanel();
    
    JPanel bufferPanel = new JPanel();
    JPanel diseaseSelectPanel = new JPanel();
    JPanel disCommentFxPanel = new JPanel();
    JPanel disCommentPanel = new JPanel();
    JPanel disFxPanel = new JPanel();
    JPanel disSelectButtonPanel = new JPanel();
    
    static final String imagePath = InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\";
    
    JButton getAButton = new JButton("A");
    JButton getBButton = new JButton("B");
    JButton getCButton = new JButton("C");
    JButton getDButton = new JButton("D");
    JButton getEButton = new JButton("E");
    JButton getFButton = new JButton("F");
    JButton getGButton = new JButton("G");
    JButton getHButton = new JButton("H");
    JButton getIButton = new JButton("I");
    JButton getJButton = new JButton("J");
    JButton getKButton = new JButton("K");
    JButton getLButton = new JButton("L");
    JButton getMButton = new JButton("M");
    JButton getNButton = new JButton("N");
    JButton getOButton = new JButton("O");
    JButton getPButton = new JButton("P");
    JButton getQButton = new JButton("Q");
    JButton getRButton = new JButton("R");
    JButton getSButton = new JButton("S");
    JButton getTButton = new JButton("T");
    JButton getUButton = new JButton("U");
    JButton getVButton = new JButton("V");
    JButton getWButton = new JButton("W");
    JButton getXButton = new JButton("X");
    JButton getYButton = new JButton("Y");
    JButton getZButton = new JButton("Z");
    
    JButton addToBufferButton = new JButton("Add To WorkSet");
    JButton searchButton = new JButton("Search");
    
    JButton clearSetButton = new JButton("Clear All");
    JButton deleteSetItemButton = new JButton("Delete Item");
    JButton runSetButton = new JButton("To Generator");
    
    JButton addToTableButton = new JButton("Add New");
    JButton deleteFromTableButton = new JButton("Delete");
    JButton editInTableButton = new JButton("Edit");
    
    //JTextArea
    JTextArea commentArea = new JTextArea(10,45);
    JTextArea disCommentArea = new JTextArea();
    JTextArea frequencyArea = new JTextArea(10,45);
    JTextArea disFrequencyArea = new JTextArea();
    JTextArea tableArea = new JTextArea();
    
    //JLabels
    JLabel searchForLabel = new JLabel("<html><h3><font color=\"blue\">Search For</font></h3></htlm>");
    JLabel diseaseNameLabel = new JLabel("<html><h3><font color=\"blue\">Disease Comments</font></h3></htlm>");
    JLabel frequencyLabel = new JLabel("<html><h3><font color=\"blue\">Disease Frequencies</font></h3></htlm>");
    
    JLabel bufferListLabel = new JLabel("<html><h3><font color=\"blue\">WorkSet List</font></h3></htlm>");
    JLabel bufferCommentLabel = new JLabel("<html><h3><font color=\"blue\">WorkSet Item Comments</font></h3></htlm>");
    JLabel bufferFrequencyLabel = new JLabel("<html><h3><font color=\"blue\">WorkSet Item Frequencies</font></h3></htlm>");
    
    //JCheckBox
    JCheckBox descriptionBox = new JCheckBox("Description");
    JCheckBox commentBox = new JCheckBox("Comments");
    JCheckBox frequencyBox = new JCheckBox("Frequencies");
    
    //JtextField
    JTextField searchField = new JTextField("");
    
    //Connection
    Connection conn = null;
    
    //JList
    JList tableListArea = null;
    JList bufferListArea = null;
    
    //ScrollPane
    JScrollPane bufferListScrollPane = null;
    JScrollPane listScrollPane = null;
    JScrollPane commentScrollPane = null;
    JScrollPane frequencyScrollPane = null;
    JScrollPane disCommentScrollPane = null;
    JScrollPane disFrequencyScrollPane = null;
    
    //ListModel
    DefaultListModel listModel = null;
    DefaultListModel bufferListModel = null;
    
    //HashMap
    HashMap tmpDiseaseList = new HashMap(5);
    HashMap freqTimeMap = new HashMap(1);
    
    //ArrayList
    ArrayList frequencyArray = new ArrayList(10);
    
    //Statics
    static final int DEF_TIME = 180;
    
    public  void initDatabase()
    {
        try
        {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String dbURL = "jdbc:odbc:Rifesource";
            conn = DriverManager.getConnection(dbURL);
        }
        catch (Exception exc)
        {
            
        }
        
    }
    
    public boolean getDiseaseListByAlpha(String alphaItem)
    {
        try
        {
            addToBufferButton.setEnabled(false);
            deleteFromTableButton.setEnabled(false);
            editInTableButton.setEnabled(false);
            Statement stmt = conn.createStatement();
            String sql = "select Description from Main where Description LIKE '" + alphaItem + "%'";
            ResultSet results = stmt.executeQuery(sql);
            listModel.clear();
            HashMap diseaseListMap = new HashMap(30);
            while (results.next())
            {
                String description = results.getString(1);
                //add to tableArea
                diseaseListMap.put(description,"0");
            }
            TreeMap myTree = new TreeMap(diseaseListMap);
            Set diseaseTreeSet = myTree.keySet();
            Iterator diseaseTreeSetIterator = diseaseTreeSet.iterator();
            while (diseaseTreeSetIterator.hasNext())
            {
                String diseaseString = (String)diseaseTreeSetIterator.next();
                listModel.addElement(diseaseString);
            }
            stmt.close();
            commentArea.setText("");
            frequencyArea.setText("");
            return true;
        }
        catch (Exception exp)
        {
            
        }
        return false;
    }
    
    public boolean getDiseaseListByUserDescription(String userItem)
    {
        try
        {
            addToBufferButton.setEnabled(false);
            Statement stmt = conn.createStatement();
            String sql = "select Description from Main where Description LIKE '%" + userItem + "%'";
            ResultSet results = stmt.executeQuery(sql);
            while (results.next())
            {
                String description = results.getString(1);
                //add to tableArea
                tmpDiseaseList.put(description,"0");
            }
            stmt.close();
            return true;
        }
        catch (Exception exp)
        {
            
        }
        return false;
        
    }
    
    public boolean getDiseaseListByUserComment(String userItem)
    {
        try
        {
            addToBufferButton.setEnabled(false);
            Statement stmt = conn.createStatement();
            String sql = "select Description from Main where comments LIKE '%" + userItem + "%'";
            ResultSet results = stmt.executeQuery(sql);
            while (results.next())
            {
                String description = results.getString(1);
                tmpDiseaseList.put(description,"0");
            }
            stmt.close();
            return true;
        }
        catch (Exception exp)
        {
            
        }
        return false;
    }
    
    public boolean getDiseaseListByUserFrequency(String userItem)
    {
        try
        {
            addToBufferButton.setEnabled(false);
            Statement stmt = conn.createStatement();
            String sql = "select Description from Main where F1 = " + userItem + " or  F2 = " + userItem + " or  F3 = " + userItem + " or  F4 = " + userItem + " or  F5 = " + userItem + " or  F6 = " + userItem + " or  F7 = " + userItem + " or  F8 = " + userItem + " or  F9 = " + userItem + " or  F10 = " + userItem + " or  F11 = " + userItem + " or  F12 = " + userItem + " or  F13 = " + userItem + " or  F14 = " + userItem + " or  F15 = " + userItem + " or  F16 = " + userItem + " or  F17 = " + userItem + " or  F18 = " + userItem + " or  F19 = " + userItem + " or  F20 = " + userItem + " or  F21 = " + userItem + " or  F22 = " + userItem + " or  F23 = " + userItem + " or  F24 = " + userItem + " or  F25 = " + userItem + " or  F26 = " + userItem + " or  F27 = " + userItem + " or  F28 = " + userItem + " or  F29 = " + userItem + " or  F30 = " + userItem + " or  F31 = " + userItem + " or  F32 = " + userItem + " or  F33 = " + userItem + " or  F34 = " + userItem + " or  F35 = " + userItem + " or  F36 = " + userItem + " or  F37 = " + userItem + " or  F38 = " + userItem + " or  F39 = " + userItem + " or  F40 = " + userItem;
            ResultSet results = stmt.executeQuery(sql);
            while (results.next())
            {
                String description = results.getString(1);
                //add to tableArea
                tmpDiseaseList.put(description,"0");
            }
            stmt.close();
            return true;
        }
        catch (Exception exp)
        {
            
        }
        return false;
        
    }
    
    public void buildPanels(int xSize, int ySize)
    {
        listModel = new DefaultListModel();
        tableListArea = new JList(listModel);
        listScrollPane = new JScrollPane(tableListArea);
        tableListArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableListArea.addListSelectionListener(new SharedListSelectionHandler());
        
        tableListArea.setBackground(new Color(0,255,187));
        
        
        bufferListModel = new DefaultListModel();
        bufferListArea = new JList(bufferListModel);
        bufferListScrollPane = new JScrollPane(bufferListArea);
        bufferListArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bufferListArea.addListSelectionListener(new BufferListSelectionHandler());
        bufferListArea.setBackground(new Color(255,255,151));
        
        clearSetButton.setEnabled(false);
        deleteSetItemButton.setEnabled(false);
        runSetButton.setEnabled(true);
        
        addToBufferButton.setEnabled(false);
        deleteFromTableButton.setEnabled(false);
        editInTableButton.setEnabled(false);
        
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBackground(new Color(2,171,253));
        commentArea.setEditable(false);
        
        frequencyArea.setLineWrap(true);
        frequencyArea.setWrapStyleWord(true);
        frequencyArea.setBackground(new Color(2,171,253));
        frequencyArea.setEditable(false);
        
        disCommentArea.setLineWrap(true);
        disCommentArea.setWrapStyleWord(true);
        disCommentArea.setBackground(new Color(255,255,151));
        disCommentArea.setEditable(false);
        
        disFrequencyArea.setLineWrap(true);
        disFrequencyArea.setWrapStyleWord(true);
        disFrequencyArea.setBackground(new Color(255,255,151));
        disFrequencyArea.setEditable(false);
        
        SCLayout dbPanelLayout  = new SCLayout(3,SCLayout.FILL,SCLayout.FILL,3);
        dbPanelLayout.setScale(0, 0.1);
        dbPanelLayout.setScale(2, 0.05);
        dbPanel.setLayout(dbPanelLayout);
        
        SCLayout selectPanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        
        SRLayout selectPanel1Layout  = new SRLayout(13,SRLayout.FILL,SRLayout.FILL,3);
        SRLayout selectPanel2Layout  = new SRLayout(13,SRLayout.FILL,SRLayout.FILL,3);
        
        selectPanel.setLayout(selectPanelLayout);
        selectPanel1.setLayout(selectPanel1Layout);
        selectPanel2.setLayout(selectPanel2Layout);
        
        SRLayout viewPanelLayout  = new SRLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        viewPanel.setLayout(viewPanelLayout);
        
        SCLayout tablePanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        tablePanelLayout.setScale(1, 0.05);
        tablePanel.setLayout(tablePanelLayout);
        
        PointLayout tableTextAreaPanelLayout = new PointLayout();
        tableTextAreaPanel.setLayout(tableTextAreaPanelLayout);
        
        tableTextAreaPanel.add(listScrollPane);
        
        SRLayout tableButtonsPanelLayout  = new SRLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        tableButtonsPanel.setLayout(tableButtonsPanelLayout);
        
        tableButtonsPanel.add(addToTableButton);
        tableButtonsPanel.add(deleteFromTableButton);
        tableButtonsPanel.add(editInTableButton);
        
        
        tablePanel.add(tableTextAreaPanel);
        tablePanel.add(tableButtonsPanel);
        
        
        SCLayout searchCommentFxPanelLayout = new SCLayout(3,SCLayout.FILL,SCLayout.FILL,3);
        searchCommentFxPanelLayout.setScale(0, 0.5);
        searchCommentFxPanelLayout.setScale(2, 0.2);
        searchCommentFxPanel.setLayout(searchCommentFxPanelLayout);
        
        SGLayout searchPanelLayout = new SGLayout(3,2,SGLayout.FILL,SGLayout.FILL,3,3);
        searchPanel.setLayout(searchPanelLayout);
        
        //add items to searchPanel
        descriptionBox.setSelected(true);
        searchField.setBackground(new Color(252,3,66));
        
        searchPanel.add(searchForLabel);
        searchPanel.add(descriptionBox);
        searchPanel.add(searchField);
        searchPanel.add(commentBox);
        searchPanel.add(searchButton);
        searchPanel.add(frequencyBox);
        
        SCLayout commentAndFxPanelLayout = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        commentAndFxPanel.setLayout(commentAndFxPanelLayout);
        
        SCLayout commentPanelLayout = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        commentPanelLayout.setScale(0, 0.3);
        commentPanel.setLayout(commentPanelLayout);
        //add to comment Panel
        commentPanel.add(diseaseNameLabel);
        commentScrollPane = new JScrollPane(commentArea);
        //commentScrollPane.add(commentArea);
        
        commentPanel.add(commentScrollPane);
        
        
        SCLayout fxPanelLayout = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        fxPanelLayout.setScale(0, 0.3);
        fxPanel.setLayout(commentPanelLayout);
        
        frequencyScrollPane = new JScrollPane(frequencyArea);
        
        fxPanel.add(frequencyLabel);
        fxPanel.add(frequencyScrollPane);
        
        PointLayout setEditPanelLayout = new PointLayout();
        setEditPanel.setLayout(setEditPanelLayout);
        setEditPanel.add(addToBufferButton);
        
        commentAndFxPanel.add(commentPanel);
        commentAndFxPanel.add(fxPanel);
        
        searchCommentFxPanel.add(searchPanel);
        searchCommentFxPanel.add(commentAndFxPanel);
        searchCommentFxPanel.add(setEditPanel);
        
        SCLayout bufferPanelLayout = new SCLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        bufferPanelLayout.setScale(0, 0.4);
        bufferPanelLayout.setScale(2, 0.1);
        bufferPanel.setLayout(bufferPanelLayout);
        
        SCLayout diseaseSelectPanelLayout = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        diseaseSelectPanelLayout.setScale(0, 0.3);
        diseaseSelectPanel.setLayout(diseaseSelectPanelLayout);
        diseaseSelectPanel.add(bufferListLabel);
        diseaseSelectPanel.add(bufferListScrollPane);
        
        
        SCLayout disCommentFxPanelLayout = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        disCommentFxPanel.setLayout(disCommentFxPanelLayout);
        
        SCLayout disCommentPanelLayout = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        disCommentPanelLayout.setScale(0, 0.3);
        disCommentPanel.setLayout(disCommentPanelLayout);
        disCommentPanel.add(bufferCommentLabel);
        disCommentScrollPane = new JScrollPane(disCommentArea);
        
        disCommentPanel.add(disCommentScrollPane);
        
        SCLayout disFxPanelLayout = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        disFxPanelLayout.setScale(0, 0.3);
        disFxPanel.setLayout(disFxPanelLayout);
        disFxPanel.add(bufferFrequencyLabel);
        disFrequencyScrollPane = new JScrollPane(disFrequencyArea);
        
        disFxPanel.add(disFrequencyScrollPane);
        
        SRLayout disSelectButtonPanelLayout = new SRLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        disSelectButtonPanel.setLayout(disSelectButtonPanelLayout);
        disSelectButtonPanel.add(deleteSetItemButton);
        disSelectButtonPanel.add(clearSetButton);
        disSelectButtonPanel.add(runSetButton);
        
        disCommentFxPanel.add(disCommentPanel);
        disCommentFxPanel.add(disFxPanel);
        
        bufferPanel.add(diseaseSelectPanel);
        bufferPanel.add(disCommentFxPanel);
        bufferPanel.add(disSelectButtonPanel);
        
        getAButton.addActionListener(this);
        getBButton.addActionListener(this);
        getCButton.addActionListener(this);
        getDButton.addActionListener(this);
        getEButton.addActionListener(this);
        getFButton.addActionListener(this);
        getGButton.addActionListener(this);
        getHButton.addActionListener(this);
        getIButton.addActionListener(this);
        getJButton.addActionListener(this);
        getKButton.addActionListener(this);
        getLButton.addActionListener(this);
        getMButton.addActionListener(this);
        getNButton.addActionListener(this);
        getOButton.addActionListener(this);
        getPButton.addActionListener(this);
        getQButton.addActionListener(this);
        getRButton.addActionListener(this);
        getSButton.addActionListener(this);
        getTButton.addActionListener(this);
        getUButton.addActionListener(this);
        getVButton.addActionListener(this);
        getWButton.addActionListener(this);
        getXButton.addActionListener(this);
        getYButton.addActionListener(this);
        getZButton.addActionListener(this);
        
        addToBufferButton.addActionListener(this);
        searchButton.addActionListener(this);
        clearSetButton.addActionListener(this);
        deleteSetItemButton.addActionListener(this);
        runSetButton.addActionListener(this);
        addToTableButton.addActionListener(this);
        deleteFromTableButton.addActionListener(this);
        editInTableButton.addActionListener(this);
        
        selectPanel1.add(getAButton);
        selectPanel1.add(getBButton);
        selectPanel1.add(getCButton);
        selectPanel1.add(getDButton);
        selectPanel1.add(getEButton);
        selectPanel1.add(getFButton);
        selectPanel1.add(getGButton);
        selectPanel1.add(getHButton);
        selectPanel1.add(getIButton);
        selectPanel1.add(getJButton);
        selectPanel1.add(getKButton);
        selectPanel1.add(getLButton);
        selectPanel1.add(getMButton);
        selectPanel2.add(getNButton);
        selectPanel2.add(getOButton);
        selectPanel2.add(getPButton);
        selectPanel2.add(getQButton);
        selectPanel2.add(getRButton);
        selectPanel2.add(getSButton);
        selectPanel2.add(getTButton);
        selectPanel2.add(getUButton);
        selectPanel2.add(getVButton);
        selectPanel2.add(getWButton);
        selectPanel2.add(getXButton);
        selectPanel2.add(getYButton);
        selectPanel2.add(getZButton);
        selectPanel.add(selectPanel1);
        selectPanel.add(selectPanel2);
        
        viewPanel.add(tablePanel);
        viewPanel.add(searchCommentFxPanel);
        viewPanel.add(bufferPanel);
        
        SRLayout buttonPanelLayout = new SRLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        buttonPanel.setLayout(buttonPanelLayout);
        
        dbPanel.add(selectPanel);
        dbPanel.add(viewPanel);
        dbPanel.add(buttonPanel);
        
        PointLayout mainPanelLayout = new PointLayout();
        setLayout(mainPanelLayout);
        setSize(xSize,ySize);
        add( dbPanel);
        setVisible(true);
    }
    
    
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Rife Disease List Navigator");
	frame.getContentPane().add(new RifeTablePanel());
        frame.setSize(800,600);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == getAButton)
        {
            getDiseaseListByAlpha("A");
        } else
            if (e.getSource() == getBButton)
            {
                getDiseaseListByAlpha("B");
            } else
                if (e.getSource() == getBButton)
                {
                    getDiseaseListByAlpha("B");
                } else
                    if (e.getSource() == getCButton)
                    {
                        getDiseaseListByAlpha("C");
                    } else
                        if (e.getSource() == getDButton)
                        {
                            getDiseaseListByAlpha("D");
                        } else
                            if (e.getSource() == getEButton)
                            {
                                getDiseaseListByAlpha("E");
                            } else
                                if (e.getSource() == getFButton)
                                {
                                    getDiseaseListByAlpha("F");
                                } else
                                    if (e.getSource() == getGButton)
                                    {
                                        getDiseaseListByAlpha("G");
                                    } else
                                        if (e.getSource() == getHButton)
                                        {
                                            getDiseaseListByAlpha("H");
                                        } else
                                            if (e.getSource() == getIButton)
                                            {
                                                getDiseaseListByAlpha("I");
                                            } else
                                                if (e.getSource() == getJButton)
                                                {
                                                    getDiseaseListByAlpha("J");
                                                } else
                                                    if (e.getSource() == getKButton)
                                                    {
                                                        getDiseaseListByAlpha("K");
                                                    } else
                                                        if (e.getSource() == getLButton)
                                                        {
                                                            getDiseaseListByAlpha("L");
                                                        } else
                                                            if (e.getSource() == getMButton)
                                                            {
                                                                getDiseaseListByAlpha("M");
                                                            } else
                                                                if (e.getSource() == getNButton)
                                                                {
                                                                    getDiseaseListByAlpha("N");
                                                                } else
                                                                    if (e.getSource() == getOButton)
                                                                    {
                                                                        getDiseaseListByAlpha("O");
                                                                    } else
                                                                        if (e.getSource() == getPButton)
                                                                        {
                                                                            getDiseaseListByAlpha("P");
                                                                        } else
                                                                            if (e.getSource() == getQButton)
                                                                            {
                                                                                getDiseaseListByAlpha("Q");
                                                                            } else
                                                                                if (e.getSource() == getRButton)
                                                                                {
                                                                                    getDiseaseListByAlpha("R");
                                                                                } else
                                                                                    if (e.getSource() == getSButton)
                                                                                    {
                                                                                        getDiseaseListByAlpha("S");
                                                                                    } else
                                                                                        if (e.getSource() == getTButton)
                                                                                        {
                                                                                            getDiseaseListByAlpha("T");
                                                                                        } else
                                                                                            if (e.getSource() == getUButton)
                                                                                            {
                                                                                                getDiseaseListByAlpha("U");
                                                                                            } else
                                                                                                if (e.getSource() == getVButton)
                                                                                                {
                                                                                                    getDiseaseListByAlpha("V");
                                                                                                } else
                                                                                                    if (e.getSource() == getWButton)
                                                                                                    {
                                                                                                        getDiseaseListByAlpha("W");
                                                                                                    } else
                                                                                                        if (e.getSource() == getXButton)
                                                                                                        {
                                                                                                            getDiseaseListByAlpha("X");
                                                                                                        } else
                                                                                                            if (e.getSource() == getYButton)
                                                                                                            {
                                                                                                                getDiseaseListByAlpha("Y");
                                                                                                            } else
                                                                                                                if (e.getSource() == getZButton)
                                                                                                                {
                                                                                                                    getDiseaseListByAlpha("Z");
                                                                                                                } else
                                                                                                                    
                                                                                                                    if (e.getSource() == searchButton)
                                                                                                                    {
                                                                                                                        String searchCriteria = searchField.getText();
                                                                                                                        if (searchCriteria != null)
                                                                                                                        {
                                                                                                                            listModel.clear();
                                                                                                                            tmpDiseaseList.clear();
                                                                                                                            deleteFromTableButton.setEnabled(false);
                                                                                                                            editInTableButton.setEnabled(false);

                                                                                                                            if (descriptionBox.isSelected() == true)
                                                                                                                            {
                                                                                                                                getDiseaseListByUserDescription(searchCriteria);
                                                                                                                            }
                                                                                                                            
                                                                                                                            if (commentBox.isSelected() == true)
                                                                                                                            {
                                                                                                                                getDiseaseListByUserComment(searchCriteria);
                                                                                                                            }
                                                                                                                            if (frequencyBox.isSelected() == true)
                                                                                                                            {
                                                                                                                                getDiseaseListByUserFrequency(searchCriteria);
                                                                                                                            }
                                                                                                                            commentArea.setText("");
                                                                                                                            frequencyArea.setText("");
                                                                                                                            
                                                                                                                            TreeMap myTree = new TreeMap(tmpDiseaseList);
                                                                                                                            Set diseaseTreeSet = myTree.keySet();
                                                                                                                            Iterator diseaseTreeSetIterator = diseaseTreeSet.iterator();
                                                                                                                            while (diseaseTreeSetIterator.hasNext())
                                                                                                                            {
                                                                                                                                String diseaseString = (String)diseaseTreeSetIterator.next();
                                                                                                                                listModel.addElement(diseaseString);
                                                                                                                            }
                                                                                                                            
                                                                                                                        }
                                                                                                                    } else
                                                                                                                        if (e.getSource() == addToBufferButton)
                                                                                                                        {
                                                                                                                            if (tableListArea.isSelectionEmpty())
                                                                                                                            {
                                                                                                                                return;
                                                                                                                            } else
                                                                                                                            {
                                                                                                                                String listItemSelected = (String)tableListArea.getSelectedValue();
                                                                                                                                if (bufferListModel.contains(listItemSelected) == false)
                                                                                                                                {
                                                                                                                                    bufferListModel.addElement(listItemSelected);
                                                                                                                                }
                                                                                                                                clearSetButton.setEnabled(true);
                                                                                                                                //deleteSetItemButton.setEnabled(true);
                                                                                                                                //runSetButton.setEnabled(true);
                                                                                                                            }
                                                                                                                        } else
                                                                                                                            if (e.getSource() == clearSetButton)
                                                                                                                            {
                                                                                                                                disCommentArea.setText("");
                                                                                                                                disFrequencyArea.setText("");
                                                                                                                                bufferListModel.clear();
                                                                                                                                clearSetButton.setEnabled(false);
                                                                                                                                deleteSetItemButton.setEnabled(false);
                                                                                                                                //runSetButton.setEnabled(false);
                                                                                                                                
                                                                                                                            } else
                                                                                                                                if (e.getSource() == deleteSetItemButton)
                                                                                                                                {
                                                                                                                                    disCommentArea.setText("");
                                                                                                                                    disFrequencyArea.setText("");
                                                                                                                                    int index = bufferListArea.getSelectedIndex();
                                                                                                                                    if (index > -1)
                                                                                                                                    {
                                                                                                                                        bufferListModel.removeElementAt(index);
                                                                                                                                        deleteSetItemButton.setEnabled(false);
                                                                                                                                        if (bufferListModel.getSize() == 0)
                                                                                                                                        {
                                                                                                                                            clearSetButton.setEnabled(false);
                                                                                                                                            deleteSetItemButton.setEnabled(false);
                                                                                                                                            //runSetButton.setEnabled(false);
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                } else
                                                                                                                                    if (e.getSource() == runSetButton)
                                                                                                                                    {
                                                                                                                                        ArrayList bufferFreqs = getBufferFrequencies(DEF_TIME);
                                                                                                                                        MTRifeFunctionGenerator functionGen = new MTRifeFunctionGenerator(bufferFreqs);
                                                                                                                                        JFrame freqGenFrame = new JFrame("Rife Function Generator");
                                                                                                                                        freqGenFrame.setSize(800,600);
                                                                                                                                        freqGenFrame.getContentPane().add(functionGen);
                                                                                                                                        freqGenFrame.setVisible(true);
                                                                                                                                        freqGenFrame.show();
                                                                                                                                    } else
                                                                                                                                    if (e.getSource() == editInTableButton)
                                                                                                                                    {
                                                                                                                                        int index = tableListArea.getSelectedIndex();
                                                                                                                                        String rifeListItem = (String)tableListArea.getSelectedValue();
                                                                                                                                        String commentItem = commentArea.getText();
                                                                                                                                        String frequencyItem = frequencyArea.getText();
                                                                                                                                        MTRifeTableEdit tableEdit = new MTRifeTableEdit(rifeListItem,commentItem,frequencyItem);
                                                                                                                                        JFrame tableEditFrame = new JFrame("Rife Table Editor");
                                                                                                                                        tableEditFrame.setSize(800,600);
                                                                                                                                        tableEditFrame.getContentPane().add(tableEdit);
                                                                                                                                        tableEditFrame.setVisible(true);
                                                                                                                                        tableEditFrame.show();
                                                                                                                                    } else
                                                                                                                                    if (e.getSource() == deleteFromTableButton)
                                                                                                                                    {
                                                                                                                                          JOptionPane optionPane = new JOptionPane();
                                                                                                                                          int result = optionPane.showConfirmDialog(this,"Are you sure you want to delete the item [" + (String)tableListArea.getSelectedValue() + "]?","Confirm Delete",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
                                                                                                                                          if (result == JOptionPane.OK_OPTION) 
                                                                                                                                          {
                                                                                                                                            deleteItem((String)tableListArea.getSelectedValue());
                                                                                                                                          }
                                                                                                                                    } else
                                                                                                                                    if (e.getSource() == addToTableButton)
                                                                                                                                    {
                                                                                                                                        int index = tableListArea.getSelectedIndex();
                                                                                                                                        String rifeListItem = (String)tableListArea.getSelectedValue();
                                                                                                                                        String commentItem = commentArea.getText();
                                                                                                                                        String frequencyItem = frequencyArea.getText();
                                                                                                                                        MTRifeTableEdit tableEdit = new MTRifeTableEdit();
                                                                                                                                        JFrame tableEditFrame = new JFrame("Rife Table Editor");
                                                                                                                                        tableEditFrame.setSize(800,600);
                                                                                                                                        tableEditFrame.getContentPane().add(tableEdit);
                                                                                                                                        tableEditFrame.setVisible(true);
                                                                                                                                        tableEditFrame.show();
                                                                                                                                    } 
    }
    
   
    public void deleteItem(String itemDescription)
   {
        String sql = "delete from  Main where Description = '" + itemDescription.trim() + "';"; 
        PreparedStatement pst = null;
        try
        {
           pst = conn.prepareStatement(sql);
           pst.executeUpdate();
           pst.close();
        }
        catch (Exception exc)
        {
            System.out.println("Could not delete item in database!!");
            
        }

    }
    
    
    
    public void getCommentAndFrequencies(String diseaseName)
    {
        try
        {
            commentArea.setText("");
            frequencyArea.setText("");
            Statement stmt = conn.createStatement();
            String comments = null;
            String sql = "select comments from Main where Description = '" + diseaseName + "'";
            ResultSet results = stmt.executeQuery(sql);
            HashMap diseaseListMap = new HashMap(30);
            while (results.next())
            {
                comments = results.getString(1);
                break;
            }
            results.close();
            commentArea.setText(comments);
            sql = "select F1,F2,F3,F4,F5,F6,F7,F8,F9,F10,F11,F12,F13,F14,F15,F16,F17,F18,F19,F20,F21,F22,F23,F24,F25,F26,F27,F28,F29,F30,F31,F32,F33,F34,F35,F36,F37,F38,F39,F40 from main where description = '" + diseaseName + "'";
            results = stmt.executeQuery(sql);
            StringBuffer freqList = new StringBuffer();
            while (results.next())
            {
                for (int loop = 1; loop < 41; loop++)
                {
                    String freq = results.getString(loop);
                    if (freq.equalsIgnoreCase("0.0") == false)
                    {
                        freqList.append(freq + ", ");
                    }
                }
                if (freqList.charAt(freqList.length()-2) == ',')
                {
                    freqList.deleteCharAt(freqList.length()-2);
                }
                frequencyArea.setText(freqList.toString());
                results.close();
                break;
            }
            stmt.close();
        }
        catch (Exception exp)
        {
            
        }
    }
    
    public void getBufferCommentsAndFrequencies(String diseaseName)
    {
        try
        {
            disCommentArea.setText("");
            disFrequencyArea.setText("");
            Statement stmt = conn.createStatement();
            String comments = null;
            String sql = "select comments from Main where Description = '" + diseaseName + "'";
            ResultSet results = stmt.executeQuery(sql);
            HashMap diseaseListMap = new HashMap(30);
            while (results.next())
            {
                comments = results.getString(1);
                break;
            }
            results.close();
            disCommentArea.setText(comments);
            sql = "select F1,F2,F3,F4,F5,F6,F7,F8,F9,F10,F11,F12,F13,F14,F15,F16,F17,F18,F19,F20,F21,F22,F23,F24,F25,F26,F27,F28,F29,F30,F31,F32,F33,F34,F35,F36,F37,F38,F39,F40 from main where description = '" + diseaseName + "'";
            results = stmt.executeQuery(sql);
            StringBuffer freqList = new StringBuffer();
            while (results.next())
            {
                for (int loop = 1; loop < 41; loop++)
                {
                    String freq = results.getString(loop);
                    if (freq.equalsIgnoreCase("0.0") == false)
                    {
                        freqList.append(freq + ", ");
                    }
                }
                if (freqList.charAt(freqList.length()-2) == ',')
                {
                    freqList.deleteCharAt(freqList.length()-2);
                }
                disFrequencyArea.setText(freqList.toString());
                results.close();
                break;
            }
            stmt.close();
        }
        catch (Exception exp)
        {
            
        }
    }
    
    
    public ArrayList getBufferFrequencies(int defaultTimeInSeconds)
    {
        try
        {
            Statement stmt = conn.createStatement();
            frequencyArray.clear();
            for (int loop1 = 0; loop1 < bufferListModel.getSize(); loop1++)
            {
                
                String diseaseName = (String)bufferListModel.getElementAt(loop1);
                String sql = "select F1,F2,F3,F4,F5,F6,F7,F8,F9,F10,F11,F12,F13,F14,F15,F16,F17,F18,F19,F20,F21,F22,F23,F24,F25,F26,F27,F28,F29,F30,F31,F32,F33,F34,F35,F36,F37,F38,F39,F40 from main where description = '" + diseaseName + "'";
                ResultSet results = stmt.executeQuery(sql);
                StringBuffer freqList = new StringBuffer();
                while (results.next())
                {
                    for (int loop = 1; loop < 41; loop++)
                    {
                        String freq = results.getString(loop);
                        if (freq.equalsIgnoreCase("0.0") == false)
                        {
                            freqTimeMap.put((Object)freq,(Object)String.valueOf(defaultTimeInSeconds));
                            frequencyArray.add(freqTimeMap.clone());
                            freqTimeMap.clear();
                        }
                    }
                    results.close();
                    break;
                }
            }
            stmt.close();
            return frequencyArray;
        }
        catch (Exception exp)
        {
            
        }
        return frequencyArray;
    }
    
    class SharedListSelectionHandler implements ListSelectionListener
    {
        String listItem = null;
        public void valueChanged(ListSelectionEvent e)
        {
            if (e.getValueIsAdjusting())
                return;
            JList theList = (JList)e.getSource();
            if (theList.isSelectionEmpty())
            {
                return;
            } else
            {
                int index = theList.getSelectedIndex();
                listItem = (String)theList.getSelectedValue();
                getCommentAndFrequencies(listItem);
                addToBufferButton.setEnabled(true);
                deleteFromTableButton.setEnabled(true);
                editInTableButton.setEnabled(true);
            }
        }
    }
    
    class BufferListSelectionHandler implements ListSelectionListener
    {
        String listItem = null;
        public void valueChanged(ListSelectionEvent e)
        {
            if (e.getValueIsAdjusting())
                return;
            JList theList = (JList)e.getSource();
            if (theList.isSelectionEmpty())
            {
                return;
            } else
            {
                int index = theList.getSelectedIndex();
                listItem = (String)theList.getSelectedValue();
                deleteSetItemButton.setEnabled(true);
                getBufferCommentsAndFrequencies(listItem);
            }
        }
    }
}