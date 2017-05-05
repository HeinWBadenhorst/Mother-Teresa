/*
 * patientInfoContainer.java
 *
 * Created on 13 June 2003, 09:09
 */

package motherteresa;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
 
/**
 *
 * @author  Hein
 */
public class PatientInfoContainer implements ListSelectionListener, ActionListener  
{

    private Vector infoCategories;
    private JList categorylist;
    private JSplitPane infoSplitPane;
    private JScrollPane categoryListPane;
    private JScrollPane infoDisplayPane;
    private HashMap infoPanelCollection = new HashMap(10);
    private HashMap jButtonMap = new HashMap(10);
    private Object[] infoObjectCategories = new JLabel[10];
    private JPanel categoryPanel = new JPanel();
    private JButton perInfoButton;
    private JButton examinationButton;
    private JButton currComplaintButton;
    private JButton medHistoryButton;
    private JButton assessmentButton;
    private JButton treatmentPlanButton;
    private JButton surgicalHistoryButton;
    private JButton systemicButton;
    private JButton familyHistoryButton;
    private JButton socialHistoryButton;
    private JButton workHistoryButton;
    private JButton treatmentHistoryButton;
    private JButton allergyHistoryButton;
    private JButton travelHistoryButton;
    
    /** Creates a new instance of patientInfoContainer */
    public PatientInfoContainer() 
    {
       infoCategories = new Vector(14);
    }
    
    public PatientInfoContainer(String imagePath) 
    {
       infoCategories = new Vector(10);
       perInfoButton = new JButton(new ImageIcon(imagePath + "personal.gif"));
       perInfoButton.setToolTipText("Personal Info");
       jButtonMap.put((Object)"Personal Info", (Object)perInfoButton);
       perInfoButton.addActionListener(this);

       currComplaintButton = new JButton(new ImageIcon(imagePath + "maincomplaint_m.gif"));
       currComplaintButton.setToolTipText("Current Complaints");
       jButtonMap.put((Object)"Current Complaints", (Object)currComplaintButton);
       currComplaintButton.addActionListener(this);
       
       medHistoryButton = new JButton(new ImageIcon(imagePath + "medicalhistory_m.gif"));
       medHistoryButton.setToolTipText("Previous Medical History");
       jButtonMap.put((Object)"Previous Medical History", (Object)medHistoryButton);
       medHistoryButton.addActionListener(this);
       
       assessmentButton = new JButton(new ImageIcon(imagePath + "assessment_m.gif"));
       assessmentButton.setToolTipText("Patient Assesment");
       jButtonMap.put((Object)"Patient Assesment", (Object)assessmentButton);
       assessmentButton.addActionListener(this);
       
       treatmentPlanButton = new JButton(new ImageIcon(imagePath + "plan_m.gif"));
       treatmentPlanButton.setToolTipText("Treatment Plan");
       jButtonMap.put((Object)"Treatment Plan", (Object)treatmentPlanButton);
       treatmentPlanButton.addActionListener(this);
       
       surgicalHistoryButton = new JButton(new ImageIcon(imagePath + "surgicalhistory_m.gif"));
       surgicalHistoryButton.setToolTipText("Previous Surgical History");
       jButtonMap.put((Object)"Previous Surgical History", (Object)surgicalHistoryButton);
       surgicalHistoryButton.addActionListener(this);

       systemicButton = new JButton(new ImageIcon(imagePath + "systemic_m.gif"));
       systemicButton.setToolTipText("Systemic Info");
       jButtonMap.put((Object)"Systemic Info", (Object)systemicButton);
       systemicButton.addActionListener(this);

       familyHistoryButton = new JButton(new ImageIcon(imagePath + "familyhistory_m.gif"));
       familyHistoryButton.setToolTipText("Family History");
       jButtonMap.put((Object)"Family History", (Object)familyHistoryButton);
       familyHistoryButton.addActionListener(this);

       socialHistoryButton = new JButton(new ImageIcon(imagePath + "socialhistory_m.gif"));
       socialHistoryButton.setToolTipText("Social History");
       jButtonMap.put((Object)"Social History", (Object)socialHistoryButton);
       socialHistoryButton.addActionListener(this);

       workHistoryButton = new JButton(new ImageIcon(imagePath + "workhistory_m.gif"));
       workHistoryButton.setToolTipText("Occcupation History");
       jButtonMap.put((Object)"Occcupation History", (Object)workHistoryButton);
       workHistoryButton.addActionListener(this);

       treatmentHistoryButton = new JButton(new ImageIcon(imagePath + "treatment_m.gif"));
       treatmentHistoryButton.setToolTipText("Treatment History");
       jButtonMap.put((Object)"Treatment History", (Object)treatmentHistoryButton);
       treatmentHistoryButton.addActionListener(this);

       allergyHistoryButton = new JButton(new ImageIcon(imagePath + "allergy_m.gif"));
       allergyHistoryButton.setToolTipText("Allergy History");
       jButtonMap.put((Object)"Allergy History", (Object)allergyHistoryButton);
       allergyHistoryButton.addActionListener(this);

       travelHistoryButton = new JButton(new ImageIcon(imagePath + "travelhistory_m.gif"));
       travelHistoryButton.setToolTipText("Travel History");
       jButtonMap.put((Object)"Travel History", (Object)travelHistoryButton);
       travelHistoryButton.addActionListener(this);

       examinationButton = new JButton(new ImageIcon(imagePath + "examination_m.gif"));
       examinationButton.setToolTipText("Examination Info");
       jButtonMap.put((Object)"Examination Info", (Object)examinationButton);
       examinationButton.addActionListener(this);

       categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
    }

    public void addCategory(String categoryName, JPanel categoryObject)
    {
       //infoCategories.addElement((JButton)jButtonMap.get((Object)categoryName));
       //infoObjectCategories[0] = (JLabel)jButtonMap.get((Object)categoryName);
       //categorylist = new JList(infoObjectCategories);
       //categorylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       //categorylist.setSelectedIndex(0);
       //categorylist.addListSelectionListener(this);
       categoryPanel.add((JButton)jButtonMap.get(categoryName));
       Set buttonKeySet = jButtonMap.keySet();
       Iterator buttonIter = buttonKeySet.iterator();
       while (buttonIter.hasNext())
       {
         String buttonName = (String)buttonIter.next();
         JButton theButton = (JButton)jButtonMap.get(buttonName);
         theButton.setBackground(new Color(100,100,100));
       }
       ((JButton)jButtonMap.get(categoryName)).setBackground(new Color(255,70,70));
       categoryListPane = new JScrollPane(categoryPanel);
       infoPanelCollection.put((Object)categoryName,(Object)categoryObject);
       infoDisplayPane = new JScrollPane(categoryObject);

       //Create a split pane with the two scroll panes in it
        infoSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   categoryListPane, infoDisplayPane);
        infoSplitPane.setOneTouchExpandable(true);
        infoSplitPane.setDividerLocation(100);

        //Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(100, 50);
        Dimension infoPanelSize = new Dimension(500, 50);
        categoryListPane.setMinimumSize(minimumSize);
        infoDisplayPane.setMinimumSize(infoPanelSize);

        //Provide a preferred size for the split pane
        infoSplitPane.setPreferredSize(new Dimension(600, 200)); 
        
        
        JPanel categoryItem = (JPanel)infoPanelCollection.get((Object)categoryName);
        infoDisplayPane = new JScrollPane(categoryItem);
        infoSplitPane.setBottomComponent(infoDisplayPane);

       
    }
    
    public void removeCategory(String categoryName)
    {
        categoryPanel.remove((JButton)jButtonMap.get(categoryName));
        JButton tempButton = (JButton)jButtonMap.get((Object)categoryName);
        //infoCategories.removeElement((Object)tempButton);
        infoPanelCollection.remove((Object)categoryName);
    }
 
    public JSplitPane getInfoSplitPane() 
    {
        return infoSplitPane;
    }
 
    public void valueChanged(ListSelectionEvent e) 
    {
        if (e.getValueIsAdjusting())
            return;
        JList theList = (JList)e.getSource();
        if (theList.isSelectionEmpty()) 
        {
            infoDisplayPane = null;
        } else 
        {
            String categoryName = (String)theList.getSelectedValue();
            JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)categoryName);
            infoDisplayPane = new JScrollPane(categoryObject);
            infoSplitPane.setBottomComponent(infoDisplayPane);
            //infoDisplayPane.revalidate();
            //infoSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,categoryListPane, infoDisplayPane);
            //infoSplitPane.revalidate();

        }
    }
    public void actionPerformed(ActionEvent event)
    {
        Set buttonKeySet = jButtonMap.keySet();
        Iterator buttonIter = buttonKeySet.iterator();
        while (buttonIter.hasNext()) {
            String buttonName = (String)buttonIter.next();
            JButton theButton = (JButton)jButtonMap.get(buttonName);
            theButton.setBackground(new Color(100,100,100));
        }
        
        
          if (event.getSource() == perInfoButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Personal Info");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Personal Info")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == examinationButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Examination Info");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Examination Info")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == currComplaintButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Current Complaints");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Current Complaints")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == medHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Previous Medical History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Previous Medical History")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == assessmentButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Patient Assesment");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Patient Assesment")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == treatmentPlanButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Treatment Plan");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Treatment Plan")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == surgicalHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Previous Surgical History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Previous Surgical History")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == systemicButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Systemic Info");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Systemic Info")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == familyHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Family History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Family History")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == socialHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Social History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Social History")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == workHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Occcupation History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Occcupation History")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == treatmentHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Treatment History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Treatment History")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == allergyHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Allergy History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Allergy History")).setBackground(new Color(255,70,70));
          } else
          if (event.getSource() == travelHistoryButton) 
          {
               JPanel categoryObject = (JPanel)infoPanelCollection.get((Object)"Travel History");
               infoDisplayPane = new JScrollPane(categoryObject);
             infoSplitPane.setBottomComponent(infoDisplayPane);
             ((JButton)jButtonMap.get("Travel History")).setBackground(new Color(255,70,70));
          }
    }
     
}
