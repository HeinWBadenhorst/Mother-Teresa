/*
 * MTRifeTableEdit.java
 *
 * Created on 07 July 2005, 10:55
 */

package motherteresa;
import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.*;


/**
 *
 * @author  HWBadenhorst
 */

public class MTRifeTableEdit extends JPanel implements ActionListener
{
    
    /** Creates a new instance of MTRifeTableEdit */
    public MTRifeTableEdit()
    {
        initDatabase();
        buildPanels();
    }
    
    public MTRifeTableEdit(String rifeItem, String commentItem, String frequencyItem)
    {
        setFrequencies(frequencyItem);
        setOldFrequencies(frequencyItem);
        descriptionField.setText(rifeItem);
        commentArea.setText(commentItem);
        oldDescription = rifeItem;
        initDatabase();
        buildPanels();
    }
    
    //Panels
    JPanel leftPanel = new JPanel();
    JPanel middlePanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel descriptionPanel = new JPanel();
    JPanel commentPanel = new JPanel();
    JPanel freqPanel = new JPanel();
    JPanel oldFreqPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel topPanel = new JPanel();
    
    //JTextField
    JTextField freq1Field = new JTextField("0");
    JTextField freq2Field = new JTextField("0");
    JTextField freq3Field = new JTextField("0");
    JTextField freq4Field = new JTextField("0");
    JTextField freq5Field = new JTextField("0");
    JTextField freq6Field = new JTextField("0");
    JTextField freq7Field = new JTextField("0");
    JTextField freq8Field = new JTextField("0");
    JTextField freq9Field = new JTextField("0");
    JTextField freq10Field = new JTextField("0");
    JTextField freq11Field = new JTextField("0");
    JTextField freq12Field = new JTextField("0");
    JTextField freq13Field = new JTextField("0");
    JTextField freq14Field = new JTextField("0");
    JTextField freq15Field = new JTextField("0");
    JTextField freq16Field = new JTextField("0");
    JTextField freq17Field = new JTextField("0");
    JTextField freq18Field = new JTextField("0");
    JTextField freq19Field = new JTextField("0");
    JTextField freq20Field = new JTextField("0");
    JTextField freq21Field = new JTextField("0");
    JTextField freq22Field = new JTextField("0");
    JTextField freq23Field = new JTextField("0");
    JTextField freq24Field = new JTextField("0");
    JTextField freq25Field = new JTextField("0");
    JTextField freq26Field = new JTextField("0");
    JTextField freq27Field = new JTextField("0");
    JTextField freq28Field = new JTextField("0");
    JTextField freq29Field = new JTextField("0");
    JTextField freq30Field = new JTextField("0");
    JTextField freq31Field = new JTextField("0");
    JTextField freq32Field = new JTextField("0");
    JTextField freq33Field = new JTextField("0");
    JTextField freq34Field = new JTextField("0");
    JTextField freq35Field = new JTextField("0");
    JTextField freq36Field = new JTextField("0");
    JTextField freq37Field = new JTextField("0");
    JTextField freq38Field = new JTextField("0");
    JTextField freq39Field = new JTextField("0");
    JTextField freq40Field = new JTextField("0");
    
    JTextField descriptionField = new JTextField();
    
    
    //JLabel
    JLabel descriptionLabel = new JLabel("<html><h1><font color=\"blue\">Description</font></h1></htlm>");
    JLabel commentLabel = new JLabel("<html><h1><font color=\"blue\">Comments</font></h1></htlm>");
    JLabel oldFreqLabel = new JLabel("<html><h1><font color=\"blue\">Original Frequencies</font></h1></htlm>");
    JLabel freqGridLabel = new JLabel("<html><h1><font color=\"red\">Frequency Grid</font></h1></htlm>");
    
    //JButton
    JButton storeButton = new JButton("Store");
    JButton clearButton = new JButton("Clear");
    
    //JScrollPane
    JScrollPane commentScrollPane = null;
    JScrollPane oldFxScrollPane = null;
    
    //JTextArea
    JTextArea commentArea = new JTextArea(10,45);
    JTextArea oldFrequencyArea = new JTextArea(10,45);
    
    //JString
    String oldDescription = null;
    
    //Connection
    Connection conn = null;
    
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == storeButton)
        {
            if (oldDescription != null)
            {
                updateTableItem();
            } else
            {
                insertTableItem();
            }
            
        } else
            if (e.getSource() == clearButton)
            {
                freq1Field.setText("0");
                freq2Field.setText("0");
                freq3Field.setText("0");
                freq4Field.setText("0");
                freq5Field.setText("0");
                freq6Field.setText("0");
                freq7Field.setText("0");
                freq8Field.setText("0");
                freq9Field.setText("0");
                freq10Field.setText("0");
                freq11Field.setText("0");
                freq12Field.setText("0");
                freq13Field.setText("0");
                freq14Field.setText("0");
                freq15Field.setText("0");
                freq16Field.setText("0");
                freq17Field.setText("0");
                freq18Field.setText("0");
                freq19Field.setText("0");
                freq20Field.setText("0");
                freq21Field.setText("0");
                freq22Field.setText("0");
                freq23Field.setText("0");
                freq24Field.setText("0");
                freq25Field.setText("0");
                freq26Field.setText("0");
                freq27Field.setText("0");
                freq28Field.setText("0");
                freq29Field.setText("0");
                freq30Field.setText("0");
                freq31Field.setText("0");
                freq32Field.setText("0");
                freq33Field.setText("0");
                freq34Field.setText("0");
                freq35Field.setText("0");
                freq36Field.setText("0");
                freq37Field.setText("0");
                freq38Field.setText("0");
                freq39Field.setText("0");
                freq40Field.setText("0");
            }
    }
    
    public void setOldFrequencies(String frequencyItem)
    {
        oldFrequencyArea.setText(frequencyItem);
    }
    
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
    
    public void updateTableItem()
    {
        String outDesc = descriptionField.getText().trim();
        String outComment = commentArea.getText().trim();
        String sql = "update Main set Description = '" + outDesc + "', Comments = '" + outComment + "', F1 = " + freq1Field.getText().trim() + ", F2 = " + freq2Field.getText().trim() + ", F3 = " + freq3Field.getText().trim() + ", F4 = " + freq4Field.getText().trim() + ", F5 = " + freq5Field.getText().trim() + ", F6 = " + freq6Field.getText().trim() + ", F7 = " + freq7Field.getText().trim() + ", F8 = " + freq8Field.getText().trim() + ", F9 = " + freq9Field.getText().trim() + ", F10 = " + freq10Field.getText().trim() + ", F11 = " + freq11Field.getText().trim() + ", F12 = " + freq12Field.getText().trim() + ", F13 = " + freq13Field.getText().trim() + ", F14 = " + freq14Field.getText().trim() + ", F15 = " + freq15Field.getText().trim() + ", F16 = " + freq16Field.getText().trim() + ", F17 = " + freq17Field.getText().trim() + ", F18 = " + freq18Field.getText().trim() + ", F19 = " + freq19Field.getText().trim() + ", F20 = " + freq20Field.getText().trim() + ", F21 = " + freq21Field.getText().trim() + ", F22 = " + freq22Field.getText().trim() + ", F23 = " + freq23Field.getText().trim() + ", F24 = " + freq24Field.getText().trim() + ", F25 = " + freq25Field.getText().trim() + ", F26 = " + freq26Field.getText().trim() + ", F27 = " + freq27Field.getText().trim() + ", F28 = " + freq28Field.getText().trim() + ", F29 = " + freq29Field.getText().trim() + ", F30 = " + freq30Field.getText().trim() + ", F31 = " + freq31Field.getText().trim() + ", F32 = " + freq32Field.getText().trim() + ", F33 = " + freq33Field.getText().trim() + ", F34 = " + freq34Field.getText().trim() + ", F35 = " + freq35Field.getText().trim() + ", F36 = " + freq36Field.getText().trim() + ", F37 = " + freq37Field.getText().trim() + ", F38 = " + freq38Field.getText().trim() + ", F39 = " + freq39Field.getText().trim() + ", F40 = " + freq40Field.getText().trim() +  " where Description = '" + oldDescription + "';";
        PreparedStatement pst = null;
        try
        {
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        catch (Exception exc)
        {
            System.out.println("Could not update database!!");
            
        }
    }
    
    public void insertTableItem()
    {
        try
        {
            Statement stmt = conn.createStatement();
            String sqlIn = "select max(COUNTER) from Main";
            int counterNext = 0;
            ResultSet resultIn = stmt.executeQuery(sqlIn);
            while (resultIn.next())
            {
                counterNext = resultIn.getInt(1) + 1;
            }
            String outDesc = descriptionField.getText().trim();
            String outComment = commentArea.getText().trim();
            String sql = "insert into  Main values(" + counterNext + ", '" + outDesc + "','" + outComment + "', " + freq1Field.getText().trim() + ", " + freq2Field.getText().trim() + ", " + freq3Field.getText().trim() + ", " + freq4Field.getText().trim() + ", " + freq5Field.getText().trim() + ", " + freq6Field.getText().trim() + ", " + freq7Field.getText().trim() + ", " + freq8Field.getText().trim() + ", " + freq9Field.getText().trim() + ", " + freq10Field.getText().trim() + ", " + freq11Field.getText().trim() + ", " + freq12Field.getText().trim() + ", " + freq13Field.getText().trim() + ", " + freq14Field.getText().trim() + ", " + freq15Field.getText().trim() + ", " + freq16Field.getText().trim() + ", " + freq17Field.getText().trim() + ", " + freq18Field.getText().trim() + ", " + freq19Field.getText().trim() + ", " + freq20Field.getText().trim() + ", " + freq21Field.getText().trim() + ", " + freq22Field.getText().trim() + ", " + freq23Field.getText().trim() + ", " + freq24Field.getText().trim() + ", " + freq25Field.getText().trim() + ", " + freq26Field.getText().trim() + ", " + freq27Field.getText().trim() + ", " + freq28Field.getText().trim() + ", " + freq29Field.getText().trim() + ", " + freq30Field.getText().trim() + ", " + freq31Field.getText().trim() + ", " + freq32Field.getText().trim() + ", " + freq33Field.getText().trim() + ", " + freq34Field.getText().trim() + ", " + freq35Field.getText().trim() + ", " + freq36Field.getText().trim() + ", " + freq37Field.getText().trim() + ", " + freq38Field.getText().trim() + ", " + freq39Field.getText().trim() + ", " + freq40Field.getText().trim() +  ");";
            PreparedStatement pst = null;
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        catch (Exception exc)
        {
            System.out.println("Could not insert into database!!");
            
        }
    }
    
    
    public void setFrequencies(String frequencyList)
    {
        StringTokenizer freqTokens = new StringTokenizer(frequencyList,",");
        int tokenCount = freqTokens.countTokens();
        if (tokenCount > 0)
        {
            for(int loop = 1; loop <= tokenCount; loop++)
            {
                String entryType = freqTokens.nextToken().trim();
                switch(loop)
                {
                    case 1:
                        freq1Field.setText(entryType);
                        break;
                    case 2:
                        freq2Field.setText(entryType);
                        break;
                    case 3:
                        freq3Field.setText(entryType);
                        break;
                    case 4:
                        freq4Field.setText(entryType);
                        break;
                    case 5:
                        freq5Field.setText(entryType);
                        break;
                    case 6:
                        freq6Field.setText(entryType);
                        break;
                    case 7:
                        freq7Field.setText(entryType);
                        break;
                    case 8:
                        freq8Field.setText(entryType);
                        break;
                    case 9:
                        freq9Field.setText(entryType);
                        break;
                    case 10:
                        freq10Field.setText(entryType);
                        break;
                    case 11:
                        freq11Field.setText(entryType);
                        break;
                    case 12:
                        freq12Field.setText(entryType);
                        break;
                    case 13:
                        freq13Field.setText(entryType);
                        break;
                    case 14:
                        freq14Field.setText(entryType);
                        break;
                    case 15:
                        freq15Field.setText(entryType);
                        break;
                    case 16:
                        freq16Field.setText(entryType);
                        break;
                    case 17:
                        freq17Field.setText(entryType);
                        break;
                    case 18:
                        freq18Field.setText(entryType);
                        break;
                    case 19:
                        freq19Field.setText(entryType);
                        break;
                    case 20:
                        freq20Field.setText(entryType);
                        break;
                    case 21:
                        freq21Field.setText(entryType);
                        break;
                    case 22:
                        freq22Field.setText(entryType);
                        break;
                    case 23:
                        freq23Field.setText(entryType);
                        break;
                    case 24:
                        freq24Field.setText(entryType);
                        break;
                    case 25:
                        freq25Field.setText(entryType);
                        break;
                    case 26:
                        freq26Field.setText(entryType);
                        break;
                    case 27:
                        freq27Field.setText(entryType);
                        break;
                    case 28:
                        freq28Field.setText(entryType);
                        break;
                    case 29:
                        freq29Field.setText(entryType);
                        break;
                    case 30:
                        freq30Field.setText(entryType);
                        break;
                    case 31:
                        freq31Field.setText(entryType);
                        break;
                    case 32:
                        freq32Field.setText(entryType);
                        break;
                    case 33:
                        freq33Field.setText(entryType);
                        break;
                    case 34:
                        freq34Field.setText(entryType);
                        break;
                    case 35:
                        freq35Field.setText(entryType);
                        break;
                    case 36:
                        freq36Field.setText(entryType);
                        break;
                    case 37:
                        freq37Field.setText(entryType);
                        break;
                    case 38:
                        freq38Field.setText(entryType);
                        break;
                    case 39:
                        freq39Field.setText(entryType);
                        break;
                    case 40:
                        freq40Field.setText(entryType);
                        break;
                }
                
            }
        }
    }
    
    public void buildPanels()
    {
        SGLayout freqPanelLayout  = new SGLayout(4,10,SGLayout.FILL,SGLayout.FILL,1,3);
        freqPanel.setLayout(freqPanelLayout);
        freqPanel.add(freq1Field);
        freqPanel.add(freq2Field);
        freqPanel.add(freq3Field);
        freqPanel.add(freq4Field);
        freqPanel.add(freq5Field);
        freqPanel.add(freq6Field);
        freqPanel.add(freq7Field);
        freqPanel.add(freq8Field);
        freqPanel.add(freq9Field);
        freqPanel.add(freq10Field);
        freqPanel.add(freq11Field);
        freqPanel.add(freq12Field);
        freqPanel.add(freq13Field);
        freqPanel.add(freq14Field);
        freqPanel.add(freq15Field);
        freqPanel.add(freq16Field);
        freqPanel.add(freq17Field);
        freqPanel.add(freq18Field);
        freqPanel.add(freq19Field);
        freqPanel.add(freq20Field);
        freqPanel.add(freq21Field);
        freqPanel.add(freq22Field);
        freqPanel.add(freq23Field);
        freqPanel.add(freq24Field);
        freqPanel.add(freq25Field);
        freqPanel.add(freq26Field);
        freqPanel.add(freq27Field);
        freqPanel.add(freq28Field);
        freqPanel.add(freq29Field);
        freqPanel.add(freq30Field);
        freqPanel.add(freq31Field);
        freqPanel.add(freq32Field);
        freqPanel.add(freq33Field);
        freqPanel.add(freq34Field);
        freqPanel.add(freq35Field);
        freqPanel.add(freq36Field);
        freqPanel.add(freq37Field);
        freqPanel.add(freq38Field);
        freqPanel.add(freq39Field);
        freqPanel.add(freq40Field);
        
        storeButton.addActionListener(this);
        clearButton.addActionListener(this);
        
        SCLayout leftPanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        leftPanel.setLayout(leftPanelLayout);
        
        SCLayout middlePanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        middlePanel.setLayout(middlePanelLayout);
        
        SCLayout rightPanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        rightPanel.setLayout(rightPanelLayout);
        
        SRLayout topPanelLayout  = new SRLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        topPanel.setLayout(topPanelLayout);
        
        SCLayout descriptionPanelLayout = new SCLayout(3,SCLayout.FILL,SCLayout.FILL,1);
        descriptionPanelLayout.setScale(1,0.5);
        descriptionPanelLayout.setScale(2,0.5);
        descriptionPanel.setLayout(descriptionPanelLayout);
        
        descriptionPanel.add(descriptionLabel);
        
        descriptionField.setBackground(new Color(255,255,151));
        
        descriptionPanel.add(descriptionField);
        descriptionPanel.add(buttonPanel);
        
        SRLayout buttonPanelLayout = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,1);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanel.add(clearButton);
        buttonPanel.add(storeButton);
        
        leftPanel.add(descriptionPanel);
        leftPanel.add(freqGridLabel);
        
        SCLayout commentPanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        commentPanel.setLayout(commentPanelLayout);
        
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setBackground(new Color(2,171,253));
        
        oldFrequencyArea.setLineWrap(true);
        oldFrequencyArea.setWrapStyleWord(true);
        oldFrequencyArea.setBackground(new Color(150,150,150));
        oldFrequencyArea.setEditable(false);
        
        SCLayout oldFrequencyPanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        oldFreqPanel.setLayout(oldFrequencyPanelLayout);
        oldFreqPanel.add(oldFreqLabel);
        
        oldFxScrollPane = new JScrollPane(oldFrequencyArea);
        
        oldFreqPanel.add(oldFxScrollPane);
        
        commentPanel.add(commentLabel);
        commentScrollPane = new JScrollPane(commentArea);
        commentPanel.add(commentScrollPane);
        
        middlePanel.add(commentPanel);
        rightPanel.add(oldFreqPanel);
        
        topPanel.add(leftPanel);
        topPanel.add(middlePanel);
        topPanel.add(rightPanel);
        
        SCLayout mainPanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        
        mainPanelLayout.setScale(1, 0.2);
        setLayout(mainPanelLayout);
        
        add(topPanel);
        add(freqPanel);
    }
}
