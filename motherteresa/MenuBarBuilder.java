package motherteresa;
/*
 * MenuBarBuilder.java
 *
 * Created on 09 June 2003, 12:25
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.BorderLayout;


/**
 *
 * @author  Hein Badenhorst
*/

public class MenuBarBuilder extends DynamicMenuBuilder {
    
    private HashMap methodList = new HashMap(5);
    private HashMap updateNumberList = new HashMap(5);
    private HashMap mitSkipButtonMap = new HashMap(5);
    
    private ArrayList menuArrayList = new ArrayList(5);
    //private ArrayList skipButtonArrayList = new ArrayList(5);
    
    private String imagePath = null;
    private int arrrayIndex = 0;
    private int itemCount = 0;
    private int fillerCount = 13;
    
    private boolean firstTime = true; 

    /** Creates a new instance of MenuBarBuilder */
    public MenuBarBuilder()
    {
        super();
        this.imagePath = InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\";
    }

    public MenuBarBuilder(String xmlFilePath, String imagePath)
    {
        super(xmlFilePath);
        this.imagePath = imagePath;
    }

    public MenuBarBuilder(String xmlFilePath, String imagePath, int ItemCount)
    {
        super(xmlFilePath);
        this.imagePath = imagePath;
        this.itemCount = ItemCount;
    }

    public MenuBarBuilder(String xmlFilePath, String imagePath, int ItemCount, int fillerCount)
    {
        super(xmlFilePath);
        this.imagePath = imagePath;
        this.itemCount = ItemCount;
        this.fillerCount = fillerCount;
    }

    public HashMap getMethodList()
    {
       return methodList; 
    }
    
    public HashMap getUpdateStateNumberList()
    {
       return updateNumberList; 
    }

    public HashMap getMitSkipButtonMap()
    {
       return mitSkipButtonMap; 
    }

    public ArrayList getMenuArrayList()
    {
       return menuArrayList; 
    }
    
    //public ArrayList getSkipButtonArrayList()
    //{
    //   return skipButtonArrayList; 
    //}

    
    public JMenuBar createMenuBar() 
    {
        String menuItemName =  "";
        String menuItemIcon = "";
        String menuItemType =  "";
        String menuItemPos =  "";
        String menuUpdateEventNumber =  "";
        JPanel menuPanel;
        boolean firstPass = true;
        Border raisedBevel, loweredBevel, compoundBorder;
        raisedBevel = BorderFactory.createRaisedBevelBorder();
        loweredBevel = BorderFactory.createLoweredBevelBorder();
        compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
        JMenuBar menuBar = new JMenuBar();
        if (itemCount > 0)
        {
            menuBar.setLayout(new SpringLayout());
        } else
        {
          menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        }
        Element theRootElement = super.getRootElement();
        ArrayList menuList = super.getMenuAttribs(theRootElement);
        Iterator menuIterator = menuList.iterator();
        while(menuIterator.hasNext())
        {
            ArrayList attribArray = (ArrayList)menuIterator.next();
            menuItemName =  (String)attribArray.get(0);
            menuItemIcon =  (String)attribArray.get(1);
            menuItemType =  (String)attribArray.get(2);
            menuItemPos =  (String)attribArray.get(3);
            menuUpdateEventNumber =  (String)attribArray.get(4);
            if (menuItemType.equalsIgnoreCase("METHOD") == false)
            {
                menuBar.add(createMenus(menuItemName, menuItemIcon), new BoxLayout(menuBar, BoxLayout.Y_AXIS));
            } else
            {
                getMethodName(super.getRootElement(), menuItemName);
                String methodName = getMethodValue();
                //JMenuItem mit = new JMenuItem(menuItemName, new ImageIcon(imagePath + menuItemIcon));
                menuPanel = new JPanel();
                menuPanel.setLayout(new BorderLayout());
                JMenuItem mit = new JMenuItem(new ImageIcon(imagePath + menuItemIcon));
                JButton mitSkipButton = new JButton(new ImageIcon(imagePath + "arrowdown.gif"));
                JButton mitNoSkipButton = new JButton(new ImageIcon(imagePath + "arrownodown.gif"));
                mitSkipButton.setEnabled(false);
                mitSkipButton.setMaximumSize(new Dimension(40,60));
                mitSkipButton.setPreferredSize(new Dimension(40,60));
                mitSkipButton.setBackground(new Color(150,150,200));

                mitNoSkipButton.setEnabled(false);
                mitNoSkipButton.setMaximumSize(new Dimension(40,60));
                mitNoSkipButton.setPreferredSize(new Dimension(40,60));
                mitNoSkipButton.setBackground(new Color(150,150,200));
                menuPanel.add(mit,BorderLayout.WEST);
                if (menuUpdateEventNumber.equalsIgnoreCase("-1") == false)
                {
                    menuPanel.add(mitSkipButton, BorderLayout.EAST);
                    mitSkipButton.setToolTipText("Press to bypass " + menuItemName);
                    mitSkipButton.setBorder(compoundBorder);
                } else
                {
                    menuPanel.add(mitNoSkipButton, BorderLayout.EAST);
                    mitNoSkipButton.setBorder(compoundBorder);
                }

                mit.setToolTipText(menuItemName);
                mit.setBorder(compoundBorder);
                if (firstPass == true)
                {
                    //mit.setBackground(new Color(0,200,0));
                    mit.setBackground(new Color(120,120,120));
                    firstPass = false;
                } else
                {
                    mit.setBackground(new Color(120,120,120));
                }
                menuBar.add(menuPanel);
                // Store objects in array to have handle to objects.
                methodList.put((Object)mit,(Object)methodName);
                menuArrayList.add(arrrayIndex,(Object)mit);
                //add map of skipbutton to menubutton if skipbutton present for this menu button.
                if (menuUpdateEventNumber.equalsIgnoreCase("-1") == false)
                {
                    updateNumberList.put((Object)mitSkipButton,(Object)menuUpdateEventNumber);
                    mitSkipButtonMap.put((Object)mitSkipButton,(Object)mit);
                }
                arrrayIndex++;
            }
        }        
        //this is to provide for filler buttons.
        
        for (int menuItemLoop = 0; menuItemLoop < (fillerCount - itemCount); menuItemLoop++)
        {
            menuPanel = new JPanel();
            menuPanel.setLayout(new BorderLayout());
            JButton myButton = new JButton("");
            myButton.setEnabled(false);
            menuPanel.add(myButton);
            menuBar.add(menuPanel);
        }
        if (itemCount > 0)
        {
            SpringUtilities springGrid = new SpringUtilities();
            
            springGrid.makeGrid(menuBar,fillerCount,1,0,0,2,2);
        }
        
        return menuBar;
    }

    // used by createMenuBar
    public JMenu createMenus(String title, String icon) {
        Border raisedBevel, loweredBevel, compoundBorder;
        raisedBevel = BorderFactory.createRaisedBevelBorder();
        loweredBevel = BorderFactory.createLoweredBevelBorder();
        compoundBorder = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
        String menuUpdateEventNumber =  "";
        String menuItemName = null;

        JMenu m = new VerticalMenu("<html><h2><font color=\"darkgreen\">>>></font></h2></html>");
        m.setBorder(compoundBorder);
        Element theRootElement = super.getRootElement();
        ArrayList menuList = super.getMenuAttribs(title);
        Iterator menuIterator = menuList.iterator();
        while(menuIterator.hasNext())
        {
            ArrayList attribArray = (ArrayList)menuIterator.next();
            menuItemName =  (String)attribArray.get(0);
            String menuItemIcon =  (String)attribArray.get(1);
            String menuItemType =  (String)attribArray.get(2);
            String menuItemPos =  (String)attribArray.get(3);
            menuUpdateEventNumber =  (String)attribArray.get(4);
            if (menuItemType.equalsIgnoreCase("METHOD"))
            {
                //JPanel menuPanel = new JPanel();
                //menuPanel.setLayout(new BorderLayout());
                getMethodName(super.getRootElement(), menuItemName);
                String methodName = getMethodValue();
                JMenuItem tmpMenuItem =  new JMenuItem(menuItemName, new ImageIcon(imagePath + menuItemIcon));
                tmpMenuItem.setToolTipText(menuItemName);
                //JButton mitSkipButton = new JButton(new ImageIcon(imagePath + "arrowright.gif"));
                //menuPanel.add(tmpMenuItem,BorderLayout.WEST);
                //if (menuUpdateEventNumber.equalsIgnoreCase("-1") == false)
                //{
                //    menuPanel.add(mitSkipButton, BorderLayout.EAST);
                //}
                m.add(tmpMenuItem);
                //dont know why so?
                //methodList.put((Object)m.add(tmpMenuItem),(Object)methodName);
                methodList.put((Object)tmpMenuItem,(Object)methodName);
                menuArrayList.add(arrrayIndex,(Object)tmpMenuItem);
                //updateNumberList.put((Object)mitSkipButton,(Object)menuUpdateEventNumber);
                //skipButtonArrayList.add(arrrayIndex,(Object)mitSkipButton);
                //JMenuItem tmpMenuItem =  new JMenuItem(new ImageIcon(imagePath + menuItemIcon));
                //tmpMenuItem.setBorder(compoundBorder);//Add this line if submenus must also have thick borders
                //methodList.put((Object)tmpMenuItem,(Object)methodName);
                arrrayIndex++;

            } else
            if (menuItemType.equalsIgnoreCase("LINK"))
            {
                m.add(createMenus(menuItemName, menuItemIcon));
                m.setToolTipText(menuItemName);
            }
        }
        //JPanel menuPanel = new JPanel();
        //menuPanel.setLayout(new BorderLayout());
        //JButton mitSkipButton = new JButton(new ImageIcon(imagePath + "arrowright.gif"));
        //m.add(menuPanel,BorderLayout.WEST);

        m.setIcon(new ImageIcon(imagePath + icon));
        //m.add(mitSkipButton);
        m.setBackground(new Color(120,120,120));
        m.setPreferredSize(new Dimension(140,70));
        m.setToolTipText(title);
        return m;
    }

    // used by createMenuBar
    public JMenu createMenu(String title) {
        JMenu m = new VerticalMenu(title);
        return m;
    }

    class VerticalMenu extends JMenu {
        VerticalMenu(String label) 
        {
            super(label);
            JPopupMenu pm = getPopupMenu();
            pm.setLayout(new BoxLayout(pm, BoxLayout.Y_AXIS));
        }

        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        public Dimension getMaximumSize() {
            return getPreferredSize();
        }

        public void setPopupMenuVisible(boolean b) {
            boolean isVisible = isPopupMenuVisible();
            if (b != isVisible) {
                if ((b==true) && isShowing()) {
                    //Set location of popupMenu (pulldown or pullright).
                    //Perhaps this should be dictated by L&F.
                    int x = 0;
                    int y = 0;
                    Container parent = getParent();
                    if (parent instanceof JPopupMenu) {
                        x = getWidth();
                        y = 0; //getHeight();
                    } else {
                        x = getWidth();
                        y = 0;
                    }
                    getPopupMenu().show(this, x, y);
                } else {
                    getPopupMenu().setVisible(false);
                }
            }
        } 
    }
    
}
