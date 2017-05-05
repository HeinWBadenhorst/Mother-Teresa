package motherteresa;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author ges
 * @author kwalrath
 */
/* MenuLayoutDemo.java is a 1.4 application that requires no other files. */

public class MenuLayoutDemo extends DynamicMenuBuilder{

    
    public MenuLayoutDemo()
    {
        super(); //Add file and path for constructor later
    }
    public JMenuBar createMenuBar() 
    {
        String menuItemName =  "";
        String menuItemType =  "";
        String menuItemPos =  "";
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        //DynamicMenuBuilder menuBuilder = new DynamicMenuBuilder();
        Element theRootElement = super.getRootElement();
        ArrayList menuList = super.getMenuAttribs(theRootElement);
        Iterator menuIterator = menuList.iterator();
        while(menuIterator.hasNext())
        {
            ArrayList attribArray = (ArrayList)menuIterator.next();
            menuItemName =  (String)attribArray.get(0);
            menuItemType =  (String)attribArray.get(1);
            menuItemPos =  (String)attribArray.get(2);
            menuBar.add(createMenus(menuItemName));
        }        
        menuBar.setBorder(BorderFactory.createEtchedBorder());//.createMatteBorder(0,0,0,1,Color.BLACK));
        return menuBar;
    }

    // used by createMenuBar
    public JMenu createMenus(String title) {
        JMenu m = new VerticalMenu(title);
        Element theRootElement = super.getRootElement();
        ArrayList menuList = super.getMenuAttribs(title);
        Iterator menuIterator = menuList.iterator();
        while(menuIterator.hasNext())
        {
            ArrayList attribArray = (ArrayList)menuIterator.next();
            String menuItemName =  (String)attribArray.get(0);
            String menuItemType =  (String)attribArray.get(1);
            String menuItemPos =  (String)attribArray.get(2);
            if (menuItemType.equalsIgnoreCase("METHOD"))
            {
                m.add(menuItemName);
            } else
            if (menuItemType.equalsIgnoreCase("LINK"))
            {
                //JMenu submenu = new VerticalMenu(menuItemName);
                m.add(createMenus(menuItemName));
            }
        }
        return m;
    }

    // used by createMenuBar
    public JMenu createMenu(String title) {
        JMenu m = new VerticalMenu(title);
        m.add("Menu item #1 in " + title);
        m.add("Menu item #2 in " + title);
        m.add("Menu item #3 in " + title);

        JMenu submenu = new VerticalMenu("Submenu");
        submenu.add("Submenu item #1");
        submenu.add("Submenu item #2");
        m.add(submenu);

        return m;
    }

    public static void main(String[] args) {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("MenuLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create/set menu bar and content pane.
        MenuLayoutDemo demo = new MenuLayoutDemo();
        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.WHITE); //contrasting bg
        contentPane.add(demo.createMenuBar(),
                        BorderLayout.LINE_START);

        //Display the window.
        frame.setSize(300, 150);
        frame.setVisible(true);
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
