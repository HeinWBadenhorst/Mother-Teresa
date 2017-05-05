/*
 * FreqTester.java
 *
 * Created on 06 June 2005, 02:46
 */
package motherteresa;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.util.*;


/**
 *
 * @author  HWBadenhorst
 */
public class FreqTester extends JFrame
{
    
    /** Creates a new instance of FreqTester */
    public FreqTester() 
    {
        super( "Rife Function Generator" );
        //MTFrequencyPanel freqPanel = new MTFrequencyPanel("5234513.3");
        
        ArrayList tempFXList = new ArrayList(13);
        HashMap fxItems = new HashMap(20);
        
        fxItems.put((Object)"2000",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"3000",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"900.8",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"95.5",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"128",(Object)"3");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"129",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"130",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"167",(Object)"3");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"1140",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"3333",(Object)"3");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"2228",(Object)"3");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"128",(Object)"99");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          fxItems.put((Object)"727",(Object)"3");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();

          fxItems.put((Object)"787",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();

          fxItems.put((Object)"800",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();

          fxItems.put((Object)"880",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();

          fxItems.put((Object)"1550",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();

          fxItems.put((Object)"5000",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();

          fxItems.put((Object)"10000",(Object)"1");  
          tempFXList.add(fxItems.clone());
          fxItems.clear();
          
          
        MTRifeFunctionGenerator functionGen = new MTRifeFunctionGenerator(tempFXList);
        setSize(800,600);
        setVisible(true);
        //SGLayout contentLayout  = new SGLayout(2,1,SGLayout.FILL,SGLayout.FILL,5,5);
        //getContentPane().setLayout(contentLayout);
        getContentPane().add( functionGen);
        //getContentPane().add( new JButton("Run"));
        getContentPane().validate();
        //for (int loop = 0; loop < 1000000; loop++)
        //{
        //    freqPanel.changeFrequency(String.valueOf(loop));
        //}
    }
    
    public static void main(String[] args) {
        FreqTester frame = new FreqTester();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
