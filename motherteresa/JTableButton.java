package motherteresa;
/*Using the Swing JTable class can quickly become a sticky business when you want to customize it to your specific needs. First you must become familiar with how the JTable class is organized. Individual cells are rendered by TableCellRenderer implementations. The table contents are represented by an implementation of the TableModel interface. By default, JTable uses DefaultTableCellRenderer to draw its cells. DefaultTableCellRenderer recognizes a few primitive types, rendering them as strings, and can even display Boolean types as checkboxes. But it defaults to displaying the value returned by toString() for types it does not specifically handle.
You have to provide your own TableCellRenderer implementation if you want to display buttons in a JTable. The TableCellRenderer interface contains only one method, getTableCellRendererComponent(...), which returns a java.awt.Component that knows how to draw the contents of a specific cell. Usually, getTableCellRendererComponent() will return the same component for every cell of a column, to avoid the unnecessary use of extra memory. But when the contents of a cell is itself a component, it is all right to return that component as the renderer. Therefore, the first step towards having JButtons display correctly in a JTable is to create a TableCellRenderer implementation that returns the JButton contained in the cell being rendered. In the accompanying code listing, JTableButtonRenderer demonstrates how to do this.
Even after creating a custom TableCellRenderer, you're still not done. The TableModel associated with a given JTable does not only keep track of the contents of each cell, but it also keeps track of the class of data stored in each column. DefaultTableModel is designed to work with DefaultTableCellRenderer and will return java.lang.String.class for columns containing data types that it does not specifically handle. The exact method that does this is getColumnClass(int column). Your second step is to create a TableModel implementation that returns JButton.class for cells that contain JButtons. JTableButtonModel shows one way to do this. It just returns the result of getClass() for each piece of cell data.
At this point, you're almost done, but not quite. What's the use of putting a JButton in a JTable if you can't press the darn thing? By default, JTable will not forward mouse events to components contained in its cells. If you want to be able to press the buttons you add to JTable, you have to create your own MouseListener that forwards events to the JButton cells. JTableButtonMouseListener demonstrates how you could do this.
Yep, you're done now. The code listing shows how to tie all these pieces together. */
//import com.sun.java.swing.*;
//import com.sun.java.swing.plaf.windows.table.*;
//import java.awt.*;
//import java.awt.event.*;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel; //AbstractTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellRenderer;

import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;


public final class JTableButton extends JFrame {
  private JTable __table;
  private JScrollPane __scrollPane;
  public JButton tmpButton = null;

  public JTableButton() {
    super("JTable Button Demo");
    TableCellRenderer defaultRenderer;
    tmpButton = new JButton("Test-Button");
    tmpButton.addActionListener(new ButtonActionListener());
   JTableButtonModel myModel = new JTableButtonModel(tmpButton);
    __table = new JTable(myModel);
    defaultRenderer = __table.getDefaultRenderer(JButton.class);
    __table.setDefaultRenderer(JButton.class,
			       new JTableButtonRenderer(defaultRenderer));
    __table.setPreferredScrollableViewportSize(new Dimension(400, 200));
    __table.addMouseListener(new JTableButtonMouseListener(__table));
    //myModel.regActionListener();

    __scrollPane = new JScrollPane(__table);
    setContentPane(__scrollPane);
 }

 class ButtonActionListener  implements ActionListener
 {
    public void actionPerformed(ActionEvent e) 
    {
        System.out.println("Inside button ActionListener");
    }    
 } 

  public static void main(String[] args) {
    Frame frame;
    WindowListener exitListener;
    exitListener = new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
	Window window = e.getWindow();
	window.setVisible(false);
	window.dispose();
	System.exit(0);
      }
    };

    frame = new JTableButton();
    frame.addWindowListener(exitListener);
    frame.pack();
    frame.setVisible(true);
  }
  
}




