/* (swing1.1beta3)*/
package motherteresa;
//package tame.examples;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//import ButtonEditor;
//import ButtonRenderer;

/**
@author Nobuo Tamemasa
@version 1.0 11/09/98
*/
public class JButtonTableExample extends JFrame {

  public JButtonTableExample(){
    super( "Eureka, Solved at last!!" );

    //DefaultTableModel dm = new DefaultTableModel();
    MTCustomTableModel dm = new MTCustomTableModel(new Object[][]{{"Button 1","Text1"},
                                    {"Button 2","Text2"}},
                     new Object[]{"Button","String"});
                     
    //dm.setDataVector(new Object[][]{{"Button 1","Text1"},
    //                                {"Button 2","Text2"}},
    //                 new Object[]{"Button","String"});

    MTJTable table = new MTJTable(dm);
    table.getColumn("Button").setCellRenderer(new ButtonRenderer());
    table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));
    JScrollPane scroll = new JScrollPane(table);
    getContentPane().add( scroll );
    setSize( 400, 100 );
    setVisible(true);
  }

  public static void main(String[] args) {
    JButtonTableExample frame = new JButtonTableExample();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
