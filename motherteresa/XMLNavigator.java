/*
 * XMLNavigator.java
 *
 * Created on 19 July 2004, 11:37
 */
package motherteresa;


import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;



/**
 *
 * @author  HBadenhorst
 */
public class XMLNavigator {
    private  DOMBuilder builder = null;
    private  Document doc = null;
    private JTextField northXMLText = null;
    private JTextField eastXMLText = null;
    private JTextField westXMLText = null;
    private JTextField southXMLText = null;
    private JTextField currentXMLText = null;
    private JTextField dummyField1 = null;
    private JTextField dummyField2 = null;
    private JTextField dummyField3 = null;
    private JTextField dummyField4 = null;
    private JPanel navPanel = null;
    private JPanel findPanel = null;
    private Element leftElement,middleElement,rightElement,upElement,downElement = null;
    private Element leftSpareElement = null;
    private Element currentDisplayElement = null;
    private java.util.List siblingElementList = null;
    private java.util.List childElementList = null;
    private Element nullElement = new Element("EDGE");
    private Element bottomElement = new Element("BOTTOM");
    private Element topElement = new Element("TOP");
    private JTextField quickFindText = null;
    private JButton findButton = null;
    private JButton rootButton = null;
    private Element searchElement = null;
    private Element child = null;
    private String tmpItem = null;
    private JPanel navigatorPanel = null;
    private String imagePath  = InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\";
    private JButton upButton = null;
    private JButton downButton = null;
    private JButton leftButton = null;
    private JButton rightButton = null;
    private ArrayList foundElements = new ArrayList(5);
    private JPanel framePanel = null;
    
    
    /** Creates a new instance of XMLNavigator */
    public XMLNavigator() 
    {
        //no xml file supplied so use sample page to demo
        int result = initDOMTree(InfoManager.WINXP_XML_PATH + "sample.xml");
        navPanel = buildNavigatorPanel(doc);
        navPanel.setMaximumSize(new Dimension(700,100));
        navPanel.setPreferredSize(new Dimension(700,100));
        navPanel.setSize(new Dimension(700,100));
        findPanel = buildQuickFindPanel();
        findPanel.setMaximumSize(new Dimension(700,80));
        findPanel.setPreferredSize(new Dimension(700,80));
        findPanel.setSize(new Dimension(700,80));
        JFrame windowFrame = new JFrame("XML Navigator Demo");
        windowFrame.setSize(700,200);
        windowFrame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {System.exit(0);}});
        framePanel  = new JPanel();
        framePanel.setSize(new Dimension(700,200));
        framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.Y_AXIS));
        framePanel.add(navPanel);
        framePanel.add(findPanel);
        windowFrame.getContentPane().add(framePanel,BorderLayout.CENTER);
        windowFrame.setVisible(true);
        currentDisplayElement = getRootElement();
        if (downElement == null) {
            downElement = bottomElement;
        }

        getWidthNextElement(currentDisplayElement);
        childElementList = currentDisplayElement.getChildren();
        if ((childElementList != null) && (childElementList.size() > 0)) {
            upElement = (Element)childElementList.get(0);
        } else {
            upElement = topElement;
        }
        //getWidthPreviousElement(currentDisplayElement);
        northXMLText.setText(upElement.getName());
        southXMLText.setText(downElement.getName());
        westXMLText.setText(leftElement.getName());
        currentXMLText.setText(middleElement.getName());
        eastXMLText.setText(rightElement.getName());
        fixTextColors();
    }
    
    public XMLNavigator(String fileName) 
    {
        
        int result = initDOMTree(InfoManager.WINXP_XML_PATH + fileName);
        navPanel = buildNavigatorPanel(doc);
        navPanel.setMaximumSize(new Dimension(700,100));
        navPanel.setPreferredSize(new Dimension(700,100));
        navPanel.setSize(new Dimension(700,100));
        findPanel = buildQuickFindPanel();
        findPanel.setMaximumSize(new Dimension(700,70));
        findPanel.setPreferredSize(new Dimension(700,70));
        findPanel.setSize(new Dimension(700,70));
        //JFrame windowFrame = new JFrame("XML Navigator Demo");
        //windowFrame.setSize(700,200);
        //windowFrame.addWindowListener(new WindowAdapter() {
        //public void windowClosing(WindowEvent e) {System.exit(0);}});
        framePanel  = new JPanel();
        framePanel.setSize(new Dimension(700,200));
        framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.Y_AXIS));
        framePanel.add(navPanel);
        framePanel.add(findPanel);
        //windowFrame.getContentPane().add(framePanel,BorderLayout.CENTER);
        //windowFrame.setVisible(true);
        currentDisplayElement = getRootElement();
        if (downElement == null) {
            downElement = bottomElement;
        }

        getWidthNextElement(currentDisplayElement);
        childElementList = currentDisplayElement.getChildren();
        if ((childElementList != null) && (childElementList.size() > 0)) {
            upElement = (Element)childElementList.get(0);
        } else {
            upElement = topElement;
        }
        //getWidthPreviousElement(currentDisplayElement);
        northXMLText.setText(upElement.getName());
        southXMLText.setText(downElement.getName());
        westXMLText.setText(leftElement.getName());
        currentXMLText.setText(middleElement.getName());
        eastXMLText.setText(rightElement.getName());
        fixTextColors();
    }

    
    
    public int initDOMTree(String fileName)
    {
        try 
        {
            builder = new DOMBuilder();
            doc = builder.build(new File(fileName));
        } catch (JDOMException e) 
        {
            e.printStackTrace();
        }
        return 0;
    }

    private void prepareTextFields()
    {
        northXMLText = new JTextField(50);
        southXMLText = new JTextField(50);
        eastXMLText = new JTextField(50);
        westXMLText = new JTextField(50);
        dummyField1 = new JTextField(50);
        dummyField2 = new JTextField(50);
        dummyField3 = new JTextField(50);
        dummyField4 = new JTextField(50);
        currentXMLText = new JTextField(50);
        northXMLText.setPreferredSize(new Dimension(200,25));
        northXMLText.setMaximumSize(new Dimension(200,25));
        southXMLText.setPreferredSize(new Dimension(200,25));
        southXMLText.setMaximumSize(new Dimension(200,25));
        eastXMLText.setPreferredSize(new Dimension(200,25));
        eastXMLText.setMaximumSize(new Dimension(200,25));
        westXMLText.setPreferredSize(new Dimension(200,25));
        westXMLText.setMaximumSize(new Dimension(200,25));
        dummyField1.setPreferredSize(new Dimension(200,25));
        dummyField1.setMaximumSize(new Dimension(200,25));
        dummyField1.setVisible(false);   
        dummyField2.setPreferredSize(new Dimension(200,25));
        dummyField2.setMaximumSize(new Dimension(200,25));
        dummyField2.setVisible(false);   
        dummyField3.setPreferredSize(new Dimension(200,25));
        dummyField3.setMaximumSize(new Dimension(200,25));
        dummyField3.setVisible(false);   
        dummyField4.setPreferredSize(new Dimension(200,25));
        dummyField4.setMaximumSize(new Dimension(200,25));
        dummyField4.setVisible(false);   
        currentXMLText.setPreferredSize(new Dimension(200,25));
        currentXMLText.setBackground(new Color(50,150,50));
    }
    
    public JPanel buildNavigatorPanel(Document document)
    {
        navigatorPanel = new JPanel(new SpringLayout());
        navigatorPanel.setMaximumSize(new Dimension(600,200));
        navigatorPanel.setSize(new Dimension(600,200));
        SpringUtilities springGrid = new SpringUtilities();
        prepareTextFields();
        navigatorPanel.add(dummyField1);
        navigatorPanel.add(northXMLText);
        navigatorPanel.add(dummyField2);
        navigatorPanel.add(westXMLText);
        navigatorPanel.add(currentXMLText);
        navigatorPanel.add(eastXMLText);
        navigatorPanel.add(dummyField3);
        navigatorPanel.add(southXMLText);
        navigatorPanel.add(dummyField4);
        springGrid.makeGrid(navigatorPanel,3,3,0,0,2,2);
        navigatorPanel.addKeyListener(new CursorKeyListener());
        navigatorPanel.setFocusable(true); 
        navigatorPanel.requestFocus();
        navigatorPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
        return navigatorPanel;
    }

    public JPanel buildQuickFindPanel()
    {
        JPanel quickFindPanel = new JPanel();
        //quickFindPanel.setLayout(new BoxLayout(quickFindPanel, BoxLayout.X_AXIS));
        quickFindPanel.setMaximumSize(new Dimension(600,50));
        quickFindPanel.setPreferredSize(new Dimension(600,50));
        quickFindPanel.setSize(new Dimension(600,50));
        quickFindText =  new JTextField(30);
        quickFindText.setMaximumSize(new Dimension(50,50));
        quickFindText.setPreferredSize(new Dimension(50,50));
        quickFindText.setBackground(new Color(121,163,204));
        quickFindText.setForeground(new Color(255,255,0));
        quickFindText.setHorizontalAlignment(JTextField.CENTER);
        JPanel navPanel = buildNavigateButtonPanel();
        
        findButton = new JButton("Find");
        findButton.setMaximumSize(new Dimension(100,50));
        findButton.setPreferredSize(new Dimension(100,50));
        rootButton = new JButton("Root");
        rootButton.setMaximumSize(new Dimension(100,50));
        rootButton.setPreferredSize(new Dimension(100,50));
        quickFindPanel.add(quickFindText);
        quickFindPanel.add(navPanel);
        
        quickFindPanel.add(findButton);
        quickFindPanel.add(rootButton);
        findButton.addActionListener(new panelButtonListener());
        rootButton.addActionListener(new panelButtonListener());
        quickFindPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
        return quickFindPanel;
    }

    public JPanel buildNavigateButtonPanel()
    {
        JPanel navigatePanel = new JPanel();
        JButton dummyButton1 = new JButton();
        dummyButton1.setSize(new Dimension(18,18)); 
        dummyButton1.setPreferredSize(new Dimension(18,18)); 
        dummyButton1.setMaximumSize(new Dimension(18,18));
        dummyButton1.setBackground(new Color(180,180,180));
        JButton dummyButton2 = new JButton();
        dummyButton2.setSize(new Dimension(18,18)); 
        dummyButton2.setPreferredSize(new Dimension(18,18)); 
        dummyButton2.setMaximumSize(new Dimension(18,18)); 
        dummyButton2.setBackground(new Color(180,180,180));
        navigatePanel.setMaximumSize(new Dimension(60,42));
        navigatePanel.setMinimumSize(new Dimension(60,42));
        navigatePanel.setSize(new Dimension(60,42));
        navigatePanel.setLayout(new SpringLayout());
        SpringUtilities springGrid = new SpringUtilities();
        upButton = new JButton(new ImageIcon(imagePath + "arrow_u.gif"));
        upButton.setMaximumSize(new Dimension(18,18));
        upButton.setSize(new Dimension(18,18));
        upButton.setPreferredSize(new Dimension(18,18)); 
        upButton.addActionListener(new panelButtonListener());
        downButton = new JButton(new ImageIcon(imagePath + "arrow_d.gif"));
        downButton.setMaximumSize(new Dimension(18,18));
        downButton.setSize(new Dimension(18,18));
        downButton.setPreferredSize(new Dimension(18,18)); 
        downButton.addActionListener(new panelButtonListener());
        leftButton = new JButton(new ImageIcon(imagePath + "arrow_l.gif"));
        leftButton.setMaximumSize(new Dimension(18,18));
        leftButton.setSize(new Dimension(18,18));
        leftButton.setPreferredSize(new Dimension(18,18)); 
        leftButton.addActionListener(new panelButtonListener());
        rightButton = new JButton(new ImageIcon(imagePath + "arrow_r.gif"));
        rightButton.setMaximumSize(new Dimension(18,18));
        rightButton.setPreferredSize(new Dimension(18,18)); 
        rightButton.setSize(new Dimension(18,18));
        rightButton.addActionListener(new panelButtonListener());

        navigatePanel.add(leftButton);
        navigatePanel.add(upButton);
        navigatePanel.add(rightButton);
        navigatePanel.add(dummyButton1);
        navigatePanel.add(downButton);
        navigatePanel.add(dummyButton2);
        springGrid.makeGrid(navigatePanel,2,3,0,0,1,1);
        navigatePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
        
        return navigatePanel;
    }

    public Element getRootElement()
    {
        return doc.getRootElement();
    }

    
    public void getWidthNextElement(Element currentElement)
    {
        
        int itemCount = 0;
        Element parentElement = null;
        String elementName = null;
        boolean edgeReached = false;
        if (currentElement != doc.getRootElement())
        {
            parentElement = currentElement.getParent();
            siblingElementList = parentElement.getChildren();
            if (((Element)siblingElementList.get(siblingElementList.size()-1)) ==  currentElement) {
               edgeReached = true;
               rightElement = nullElement;
            }
        } else
        {
            parentElement = currentElement;
            siblingElementList = parentElement.getChildren();
        }
        
        //if (rightElement != null)
        //{
        //    elementName = rightElement.getName();
        //} else
        //{
        //   elementName = "null"; 
        //}
        
        if ((parentElement != null) && (edgeReached == false))
        {
           Iterator iterator = siblingElementList.iterator();
           while(iterator.hasNext())
           {
                Object obj = iterator.next();
                if (obj instanceof Element)
                {
                    leftElement = (Element)obj;
                    if ((currentElement == leftElement) || (parentElement == currentElement))
                    {
                        itemCount = 1;
                        while (iterator.hasNext() )
                        {
                            Object nextObj = iterator.next();
                            if (nextObj instanceof Element)
                            {
                                itemCount = 2;
                                middleElement = (Element)nextObj;
                                while (iterator.hasNext())
                                {
                                    nextObj = iterator.next();
                                    if (nextObj instanceof Element)
                                    {
                                        itemCount = 3;
                                        rightElement = (Element)nextObj;
                                        break;
                                    }
                                }
                            }
                            if (itemCount == 3) break;
                        }
                        
                    } 
                }
                if (itemCount == 3) break;
           } //end while iterator
        }
        if (itemCount == 1)
        {
            currentElement = leftElement;
            middleElement = leftElement;
            currentDisplayElement = middleElement;
            leftElement = nullElement;
            rightElement = nullElement;
        } else
        if (itemCount == 2)
        {
           currentElement = middleElement; 
           currentDisplayElement = middleElement; 
           rightElement  = nullElement; 
        } else
        if (itemCount == 3)
        {
           currentDisplayElement = middleElement; 
        }
    }

    public void getWidthPreviousElement(Element currentElement)
    {
        int itemCount = 0;
        Element parentElement = null;
        String elementName = null;
        Object obj = null;
        boolean edgeReached = false;
        boolean currentElementFound = false;
        if (currentElement.getName().equalsIgnoreCase(doc.getRootElement().getName()) == false)
        {
            parentElement = currentElement.getParent();
            siblingElementList = parentElement.getChildren();
            if ((Element)siblingElementList.get(0) == currentElement)
            {
               edgeReached = true;
               leftElement = nullElement;
            }
        } else
        {
            parentElement = currentElement;
            siblingElementList = parentElement.getChildren();
        }
        if (leftElement != null)
        {
            elementName = leftElement.getName();
        } else
        {
           elementName = "null"; 
        }
        if ((parentElement != null) && (edgeReached == false))
        {
           ListIterator iterator = siblingElementList.listIterator(siblingElementList.size());
           while(iterator.hasPrevious())
           {
                obj = iterator.previous();
                if (obj instanceof Element)
                {
                    rightElement = (Element)obj;
                    itemCount = 1;
                    if ((currentElement == rightElement) || (parentElement == currentElement))
                    {
                        currentElementFound = true;
                        while (iterator.hasPrevious())
                        {
                            Object nextObj = iterator.previous();
                            if (nextObj instanceof Element)
                            {
                                itemCount = 2;
                                middleElement = (Element)nextObj;
                                while (iterator.hasPrevious())
                                {
                                    nextObj = iterator.previous();
                                    if (nextObj instanceof Element)
                                    {
                                        itemCount = 3;
                                        leftElement = (Element)nextObj;
                                        break;
                                    }
                                }
                            }
                            if (itemCount == 3) break;
                        }
                }
           }
           if (itemCount == 3) break;
        } // next element loop..
        }
        if (itemCount == 1)
        {
            middleElement = rightElement;
            rightElement = nullElement;
            leftElement = nullElement;
            currentDisplayElement = middleElement; 
        } else
        if (itemCount == 2)
        {
            leftElement = nullElement;
            currentDisplayElement = middleElement; 
        } else
        if (itemCount > 2)
        {
           currentDisplayElement = middleElement;
        }
    }

    public void getDepthNextElement(Element currentElement)
    {
        
        int itemCount = 0;
        Element parentElement = null;
        String elementName = null;
        
        if (currentElement.getName().equalsIgnoreCase(doc.getRootElement().getName()) == false)
        {
            siblingElementList = currentElement.getChildren();
            if ((siblingElementList != null) && (siblingElementList.size() > 0))
            {    
               downElement = currentElement;
               Element tmpElement = (Element)siblingElementList.get(0);
               currentDisplayElement  = tmpElement;
               middleElement = currentDisplayElement;
               getWidthNextElement(currentDisplayElement);
               getWidthPreviousElement(currentDisplayElement);
               westXMLText.setText(leftElement.getName());
               currentXMLText.setText(middleElement.getName());
               eastXMLText.setText(rightElement.getName());

            } else
            {
                upElement = topElement;
            }
            //now find the first child and display northside
            childElementList = currentDisplayElement.getChildren();
            if ((childElementList != null) && (childElementList.size() > 0))
            {
                upElement = (Element)childElementList.get(0);
            } else
            {
                upElement = topElement;
            }
            
        }
    }
    
    public void getDepthPreviousElement(Element currentElement)
    {
        
        int itemCount = 0;
        Element parentElement = null;
        String elementName = null;
        
        if ((downElement != null) && (downElement.getName().equalsIgnoreCase("BOTTOM") == false) )
        {
            if (currentElement.getName().equalsIgnoreCase(doc.getRootElement().getName()) == false) {
                parentElement = currentElement.getParent();
                if (parentElement != null) {
                    upElement = currentElement;
                    currentDisplayElement = downElement;
                    middleElement = currentDisplayElement;
                    Element preElement = middleElement;
                    getWidthNextElement(currentDisplayElement);
                    if (preElement.getName().equalsIgnoreCase(currentDisplayElement.getName()) == false) {
                        getWidthPreviousElement(currentDisplayElement);
                    } else {
                        getWidthPreviousElement(currentDisplayElement);
                        if (preElement.getName().equalsIgnoreCase(currentDisplayElement.getName()) == false) {
                            getWidthNextElement(currentDisplayElement);
                        }
                    }
                    //now find the first child and display northside
                    childElementList = currentDisplayElement.getChildren();
                    if (childElementList != null) {
                        Iterator iterator = childElementList.iterator();
                        while(iterator.hasNext()) {
                            Object obj = iterator.next();
                            if (obj instanceof Element) {
                                upElement = (Element)obj;
                                break;
                            }
                        }
                    } else {
                        upElement = topElement;
                    }
                    westXMLText.setText(leftElement.getName());
                    currentXMLText.setText(middleElement.getName());
                    eastXMLText.setText(rightElement.getName());
                    parentElement = currentDisplayElement.getParent();
                    if (parentElement != null) {
                        downElement = parentElement;
                    } else {
                        downElement = bottomElement;
                    }
                    
                }
            }
        }
    }

    public void navigateTo(Element currentElement)
    {
        
        int itemCount = 0;
        Element parentElement = null;
        String elementName = null;
        boolean edgeReached = false;
        if (currentElement.getName().equalsIgnoreCase(doc.getRootElement().getName()) == false)
        {
            parentElement = currentElement.getParent();
            siblingElementList = parentElement.getChildren();
            if ((Element)siblingElementList.get(siblingElementList.size()-1) == currentElement) {
               edgeReached = true;
               rightElement = nullElement;
            }
        } else
        {
            parentElement = currentElement;
            siblingElementList = parentElement.getChildren();
        }
        if (parentElement != null)
        {
            downElement = parentElement;
            middleElement = currentElement;
            currentDisplayElement = currentElement;
        }
        
        if (parentElement != null)
        {
           Iterator iterator = siblingElementList.iterator();
           int elementIndex = 0;
           middleElement = null;
           while(iterator.hasNext())
           {
                Object obj = iterator.next();
                if (obj instanceof Element)
                {
                    if (middleElement != null)
                    { 
                        leftElement = middleElement;
                    } else
                    {
                        leftElement = nullElement;
                    }
                    middleElement = (Element)obj;
                    elementIndex++;
                    if (currentElement == middleElement)
                    {

                        childElementList = currentDisplayElement.getChildren();
                        if ((childElementList != null) && (childElementList.size() > 0)) {
                            Iterator childIterator = childElementList.iterator();
                            while(childIterator.hasNext()) {
                               obj = childIterator.next();
                               if (obj instanceof Element) {
                                   upElement = (Element)obj;
                                   break;
                               }
                            }
                        } else {
                            upElement = topElement;
                        }
                        if (elementIndex == 1) leftElement = nullElement;
                        itemCount = 1;
                        if(iterator.hasNext() )
                        {
                            Object nextObj = null;
                            while(iterator.hasNext())
                            {
                                 nextObj = iterator.next();
                                 if (nextObj instanceof Element)
                                 {
                                     break;
                                 }
                            }   
                            itemCount = 2;
                            rightElement = (Element)nextObj;
                        } else
                        {
                            rightElement = nullElement;
                        }
                        
                    } 
                }
                if (itemCount > 0) break;
           } //end while iterator
        }
    }
    
    
    public void findElement(Element current,int depth,String elementName)
    {
        //if (current.getName().equalsIgnoreCase(elementName) == true)
        //{
        //    return current;
        //} else
        //{
            if (current.getName().equalsIgnoreCase(elementName) == true)
            {
                searchElement = current;  
                return;
            }
            java.util.List children = current.getChildren();
            Iterator iterator = children.iterator();
            while (iterator.hasNext())
            {
                child = (Element)iterator.next();
                if (child.getName().equalsIgnoreCase(elementName) == true)
                {
                    searchElement = child;
                    return;
                } else
                {
                  findElement(child,depth+1,elementName);
                }
            }
        //}
       
    }
    
    public void findElements(Element current,int depth,String elementName)
    {
        //if (current.getName().equalsIgnoreCase(elementName) == true) {
        //    foundElements.add((Object)current);
            //return;
        //}
        java.util.List children = current.getChildren();
        Iterator iterator = children.iterator();
        while (iterator.hasNext()) {
            child = (Element)iterator.next();
            if (child.getName().equalsIgnoreCase(elementName) == true) {
                foundElements.add((Object)child);
            }
            findElements(child,depth+1,elementName);
            
        }
       
    }
    
    
    public void findItemInXML(String itemName)
    {
        Element root = doc.getRootElement();
        tmpItem = itemName;
        searchElement = null;
        findElement(root,0,itemName);
        if (searchElement != null)
        {
            navigateTo(searchElement);
            westXMLText.setText(leftElement.getName());
            currentXMLText.setText(middleElement.getName());
            eastXMLText.setText(rightElement.getName());
            southXMLText.setText(downElement.getName());
            northXMLText.setText(upElement.getName());
            fixTextColors();
        }
        
        
    }
    
    public void findItemsInXML(String itemName)
    {
        Element root = doc.getRootElement();
        tmpItem = itemName;
        foundElements.clear();
        findElements(root,0,itemName);
        if (foundElements.size() == 1)
        {
            searchElement = (Element)foundElements.get(0);
            navigateTo(searchElement);
            westXMLText.setText(leftElement.getName());
            currentXMLText.setText(middleElement.getName());
            eastXMLText.setText(rightElement.getName());
            southXMLText.setText(downElement.getName());
            northXMLText.setText(upElement.getName());
            fixTextColors();
        } else
        if (foundElements.size() > 1)
        {
            //JPanel choicePanel = buildChoiceList(foundElements);
            Object[] possibilities = new Object[foundElements.size()];
            
            for (int loop = 0; loop < foundElements.size(); loop++)
            {
                Element tmpElement = (Element)foundElements.get(loop);
                Element parentElement = tmpElement.getParent();
                if (parentElement != null)
                {
                    possibilities[loop] = String.valueOf(loop) + "-" + parentElement.getName() + "-" + tmpElement.getName();
                } else
                {
                    possibilities[loop] = String.valueOf(loop) + "-Root-" + tmpElement.getName();
                }
                
            }
            String s = (String)JOptionPane.showInputDialog(
                                        navigatorPanel,
                                        "Select one of the items below:\n",
                                        "Anatomical Item Selection",
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        possibilities,
                                        null);

            if ((s != null) && (s.length() > 0)) 
            {
                   String itemPos = null;
                   StringTokenizer itemTokens = new StringTokenizer(s,"-");	
                   int tokenCount = itemTokens.countTokens();
                   if(tokenCount > 0)
                   {
                        itemPos = (String)itemTokens.nextToken();
                   }
                   searchElement = (Element)foundElements.get(Integer.parseInt(itemPos));
                   navigateTo(searchElement);
            }
            westXMLText.setText(leftElement.getName());
            currentXMLText.setText(middleElement.getName());
            eastXMLText.setText(rightElement.getName());
            southXMLText.setText(downElement.getName());
            northXMLText.setText(upElement.getName());
            fixTextColors();
        }        
        
    }
    
    
    public void fixTextColors()
    {
        if (upElement.getName().equalsIgnoreCase("TOP") == true) {
            northXMLText.setBackground(new Color(255,0,0));
            northXMLText.setText("");
        } else {
            northXMLText.setBackground(new Color(200,200,200));
            Attribute nameAttrib = upElement.getAttribute("NAME");
            String nameAttribValue = nameAttrib.getValue();
            northXMLText.setText(nameAttribValue);
        }
        if ((downElement.getName().equalsIgnoreCase("BOTTOM") == true) || (downElement.getName().equalsIgnoreCase("ANATOMY") == true)) {
            southXMLText.setBackground(new Color(255,0,0));
            southXMLText.setText("");
        } else {
            southXMLText.setBackground(new Color(200,200,200));
            Attribute nameAttrib = downElement.getAttribute("NAME");
            String nameAttribValue = nameAttrib.getValue();
            southXMLText.setText(nameAttribValue);
        }
        if (leftElement.getName().equalsIgnoreCase("EDGE") == true) {
            westXMLText.setBackground(new Color(255,0,0));
            westXMLText.setText("");
        } else {
            westXMLText.setBackground(new Color(200,200,200));
            Attribute nameAttrib = leftElement.getAttribute("NAME");
            String nameAttribValue = nameAttrib.getValue();
            westXMLText.setText(nameAttribValue);
        }
        if (rightElement.getName().equalsIgnoreCase("EDGE") == true) {
            eastXMLText.setBackground(new Color(255,0,0));
            eastXMLText.setText("");
        } else {
            eastXMLText.setBackground(new Color(200,200,200));
            Attribute nameAttrib = rightElement.getAttribute("NAME");
            String nameAttribValue = nameAttrib.getValue();
            eastXMLText.setText(nameAttribValue);
        }
        Attribute nameAttrib = middleElement.getAttribute("NAME");
        String nameAttribValue = nameAttrib.getValue();
        currentXMLText.setText(nameAttribValue);

    }
    
   public class panelButtonListener implements ActionListener
   {
       
       public void actionPerformed(ActionEvent event) 
       {
            if (event.getSource() == findButton)
            {
                String findText = quickFindText.getText().trim();
                findItemsInXML(findText);
                navigatorPanel.setFocusable(true); 
                navigatorPanel.requestFocus();

            } else
            if (event.getSource() == rootButton)
            {
                findItemInXML(getRootElement().getName());
                navigateTo(middleElement);
                westXMLText.setText(leftElement.getName());
                currentXMLText.setText(middleElement.getName());
                eastXMLText.setText(rightElement.getName());
                southXMLText.setText(downElement.getName());
                northXMLText.setText(upElement.getName());
                fixTextColors();
                navigatorPanel.setFocusable(true); 
                navigatorPanel.requestFocus();

            } else
            if (event.getSource() == upButton)
            {
                getDepthNextElement(currentDisplayElement);
                northXMLText.setText(upElement.getName());
                southXMLText.setText(downElement.getName());
                fixTextColors();
                navigatorPanel.setFocusable(true); 
                navigatorPanel.requestFocus();
            } else
            if (event.getSource() == downButton)
            {
                getDepthPreviousElement(currentDisplayElement);
                northXMLText.setText(upElement.getName());
                southXMLText.setText(downElement.getName());
                fixTextColors();
                navigatorPanel.setFocusable(true); 
                navigatorPanel.requestFocus();
            } else
            if (event.getSource() == leftButton)
            {
               if (downElement == null)
               {
                   downElement = bottomElement;
                   southXMLText.setText(downElement.getName());
               }
               getWidthPreviousElement(currentDisplayElement);
               westXMLText.setText(leftElement.getName());
               currentXMLText.setText(middleElement.getName());
               eastXMLText.setText(rightElement.getName());
               childElementList = currentDisplayElement.getChildren();
               if ((childElementList != null) && (childElementList.size() > 0)) {
                   upElement = (Element)childElementList.get(0);
               } else {
                   upElement = topElement;
               }
               northXMLText.setText(upElement.getName());
               fixTextColors();
                navigatorPanel.setFocusable(true); 
                navigatorPanel.requestFocus();
            } else
            if (event.getSource() == rightButton)
            {
               if (downElement == null)
               {
                   downElement = bottomElement;
                   southXMLText.setText(downElement.getName());
               }
                getWidthNextElement(currentDisplayElement);
                westXMLText.setText(leftElement.getName());
                currentXMLText.setText(middleElement.getName());
                eastXMLText.setText(rightElement.getName());
               childElementList = currentDisplayElement.getChildren();
               if ((childElementList != null) && (childElementList.size() > 0)) {
                   upElement = (Element)childElementList.get(0);
               } else {
                   upElement = topElement;
               }
               northXMLText.setText(upElement.getName());
               fixTextColors();
                navigatorPanel.setFocusable(true); 
                navigatorPanel.requestFocus();
            }
       }
   }
    
    public class CursorKeyListener extends KeyAdapter
    {
        public CursorKeyListener()
        {
           super();    
        }
        
        public void keyPressed(KeyEvent ke)
        {
            int keyCode = ke.getKeyCode();
            
            if( keyCode == KeyEvent.VK_LEFT)
            {
               if (downElement == null)
               {
                   downElement = bottomElement;
                   southXMLText.setText(downElement.getName());
               }
               getWidthPreviousElement(currentDisplayElement);
               westXMLText.setText(leftElement.getName());
               currentXMLText.setText(middleElement.getName());
               eastXMLText.setText(rightElement.getName());
               childElementList = currentDisplayElement.getChildren();
               if ((childElementList != null) && (childElementList.size() > 0)) {
                   upElement = (Element)childElementList.get(0);
               } else {
                   upElement = topElement;
               }
               northXMLText.setText(upElement.getName());
               fixTextColors();
               
            }     
            if( keyCode == KeyEvent.VK_RIGHT)
            {
               if (downElement == null)
               {
                   downElement = bottomElement;
                   southXMLText.setText(downElement.getName());
               }
                getWidthNextElement(currentDisplayElement);
                westXMLText.setText(leftElement.getName());
                currentXMLText.setText(middleElement.getName());
                eastXMLText.setText(rightElement.getName());
               childElementList = currentDisplayElement.getChildren();
               if ((childElementList != null) && (childElementList.size() > 0)) {
                   upElement = (Element)childElementList.get(0);
               } else {
                   upElement = topElement;
               }
               northXMLText.setText(upElement.getName());
               fixTextColors();
            }     
            if( keyCode == KeyEvent.VK_UP)
            {
                getDepthNextElement(currentDisplayElement);
                northXMLText.setText(upElement.getName());
                southXMLText.setText(downElement.getName());
                fixTextColors();
            }     
            if( keyCode == KeyEvent.VK_DOWN)
            {
                getDepthPreviousElement(currentDisplayElement);
                northXMLText.setText(upElement.getName());
                southXMLText.setText(downElement.getName());
               fixTextColors();
            }     
            
        }
    }
    
    public String getCurrentSelection()
    {
        return currentXMLText.getText();
    }    
    
    public JPanel getFramePanel()
    {
        return framePanel;
    }

    public static void main(String[] args)
    {
        new XMLNavigator();
    }
}
