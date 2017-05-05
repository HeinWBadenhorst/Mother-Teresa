/*
 * MTFrequencyPanel.java
 *
 * Created on 06 June 2005, 02:13
 */
package motherteresa;

import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;


public class MTFrequencyPanel extends JPanel {
    
    /** Creates a new instance of MTFrequencyPanel */
    public MTFrequencyPanel() {
        //super();
        loadFreqDigits();
        displayDefaultDigits();
    }
    
    public MTFrequencyPanel(int frequency) {
        //super();
        loadFreqDigits();
        displayIntFrequency(frequency);
    }
    
    public MTFrequencyPanel(double frequency) {
        //super();
        loadFreqDigits();
        displayDoubleFrequency(frequency);
    }
    
    public MTFrequencyPanel(float frequency) {
        //super();
        loadFreqDigits();
        displayFloatFrequency(frequency);
    }
    
    public MTFrequencyPanel(String frequency) {
        //super();
        loadFreqDigits();
        displayStringFrequency(frequency);
    }
    
    final static int NUM_IMAGES = 13;
    final static int START_INDEX = 0;
    ImageIcon[] images = new ImageIcon[NUM_IMAGES];
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border compoundBorder = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    JPanel innerPanel = new JPanel();
    
    
    public void changeFrequency(int frequency) {
        try {
            
            if (getComponentCount() > 0) {
                innerPanel.removeAll();
            }
            displayIntFrequency(frequency);
            innerPanel.updateUI();
        }
        catch (Exception exp) {
            return;
        }
    }
    
    public void changeFrequency(String frequency) {
        try {
            
            if (getComponentCount() > 0) {
                innerPanel.removeAll();
            }
            displayStringFrequency(frequency);
            innerPanel.updateUI();
        }
        catch (Exception exp) {
            return;
        }
    }
    
    public void changeFrequency(double frequency) {
        try {
            
            if (getComponentCount() > 0) {
                innerPanel.removeAll();
            }
            displayDoubleFrequency(frequency);
            innerPanel.updateUI();
        }
        catch (Exception exp) {
            return;
        }
    }
    
    public void changeFrequency(float frequency) {
        try {
            
            if (getComponentCount() > 0) {
                innerPanel.removeAll();
            }
            displayFloatFrequency(frequency);
            innerPanel.updateUI();
        }
        catch (Exception exp) {
            return;
        }
    }
    
    
    
    
    private void loadFreqDigits() {
        // Get the images and put them into an array of ImageIcon.
        for (int i = 0; i < NUM_IMAGES; i++) {
            ImageIcon icon = new ImageIcon(InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\" + i + "crt.gif");
            images[i] = icon;
        }
        ImageIcon icon = new ImageIcon(InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\pointCRT.gif");
        images[10] = icon;
        icon = new ImageIcon(InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\commaCRT.gif");
        images[11] = icon;
        icon = new ImageIcon(InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\blankCRT.gif");
        images[12] = icon;
        PointLayout mainLayout = new PointLayout();
        setLayout(mainLayout);
        //setBorder(compoundBorder);
        //innerPanel.setLayout(ipLayout);
        //setSize(270,70);
    }
    
    private void displayIntFrequency(int frequency) {
        if (frequency > 9999999) {
            displayError();
            return;
        }
        String strFreq = String.valueOf(frequency);
        int strFreqLen = strFreq.length();
        int zeroPadAmt = NUM_IMAGES - strFreqLen;
        
        for (int loop = 0; loop < zeroPadAmt; loop++) {
            innerPanel.add(new JLabel(images[12]));
        }
        
        for (int loop1 = 0; loop1 < strFreqLen; loop1++) {
            int imageIndex = Integer.parseInt(strFreq.substring(loop1,loop1+1));
            innerPanel.add(new JLabel(images[imageIndex]));
        }
        innerPanel.setBackground(new Color(0,0,0));
        add(innerPanel);
    }
    
    private void displayFloatFrequency(float frequency) {
        if (frequency > 9999999.0) {
            displayError();
            return;
        }
        String strFreq = String.valueOf(frequency);
        if (strFreq.length() > 11) {
            displayError();
            return;
        }
        
        StringTokenizer freqTokens = new StringTokenizer(strFreq,".");
        
        String intPart = (String)freqTokens.nextToken();
        String floatPart = (String)freqTokens.nextToken();
        
        int addCommas = 0;
        int fullIntLen = 0;
        if (intPart.length() < 4) {
            fullIntLen = intPart.length();
            addCommas = 0;
            
        } else
            if ((intPart.length() > 3) && (intPart.length() < 7)) {
                fullIntLen = intPart.length() + 1;
                addCommas = 1;
                
            } else
                if (intPart.length() > 6) {
                    fullIntLen = intPart.length() + 2;
                    addCommas = 2;
                    
                }
        int zeroPadAmt = NUM_IMAGES - (fullIntLen + 2);
        // fill in blanks
        for (int loop = 0; loop < zeroPadAmt; loop++) {
            innerPanel.add(new JLabel(images[12]));
        }
        int strFreqLen = intPart.length();
        
        
        
        for (int loop1 = 0; loop1 < strFreqLen; loop1++) {
            if ((addCommas == 2) && (loop1 == 1)) {
                innerPanel.add(new JLabel(images[11]));
            } else
                if ((addCommas == 2) && (loop1 == 5)) {
                    innerPanel.add(new JLabel(images[11]));
                } else
                    if ((addCommas == 1) && (strFreqLen == 6) && (loop1 == 3)) {
                        innerPanel.add(new JLabel(images[11]));
                    } else
                        if ((addCommas == 1) && (strFreqLen == 5) && (loop1 == 2)) {
                            innerPanel.add(new JLabel(images[11]));
                        } else
                            if ((addCommas == 1) && (strFreqLen == 4) && (loop1 == 1)) {
                                innerPanel.add(new JLabel(images[11]));
                            }
            int imageIndex = Integer.parseInt(strFreq.substring(loop1,loop1+1));
            innerPanel.add(new JLabel(images[imageIndex]));
        }
        
        innerPanel.add(new JLabel(images[10]));
        
        int imageIndex = Integer.parseInt(floatPart.substring(0,1));
        innerPanel.add(new JLabel(images[imageIndex]));
        
        setBackground(new Color(0,0,0));
        //add(innerPanel);
    }
    
    private void displayDoubleFrequency(double frequency) {
        if (frequency > 9999999.0) {
            displayError();
            return;
        }
        String strFreq = String.valueOf(frequency);
        if (strFreq.length() > 11) {
            displayError();
            return;
        }
        
        StringTokenizer freqTokens = new StringTokenizer(strFreq,".");
        
        String intPart = (String)freqTokens.nextToken();
        String floatPart = (String)freqTokens.nextToken();
        
        int strFreqLen = intPart.length();
        int zeroPadAmt = NUM_IMAGES - (strFreqLen + 2);
        
        for (int loop = 0; loop < zeroPadAmt; loop++) {
            innerPanel.add(new JLabel(images[12]));
        }
        for (int loop1 = 0; loop1 < strFreqLen; loop1++) {
            int imageIndex = Integer.parseInt(strFreq.substring(loop1,loop1+1));
            innerPanel.add(new JLabel(images[imageIndex]));
        }
        
        innerPanel.add(new JLabel(images[10]));
        
        int imageIndex = Integer.parseInt(floatPart.substring(0,1));
        innerPanel.add(new JLabel(images[imageIndex]));
        
        setBackground(new Color(0,0,0));
        add(innerPanel);
    }
    
    private void displayStringFrequency(String frequency) {
        String strFreq = frequency.trim();
        StringTokenizer freqTokens = new StringTokenizer(strFreq,".");
        
        if (freqTokens.countTokens() == 2) {
            String intPart = (String)freqTokens.nextToken();
            String floatPart = (String)freqTokens.nextToken();
            try {
                if (Integer.parseInt(intPart) == -1) {
                    displayError();
                    return;
                }
                if (Integer.parseInt(floatPart) == -1) {
                    displayError();
                    return;
                }
            }
            catch (Exception exp) {
                displayError();
                return;
                
            }
            
            int strFreqLen = intPart.length();
            int zeroPadAmt = NUM_IMAGES - (strFreqLen + 2);
            
            for (int loop = 0; loop < zeroPadAmt; loop++) {
                innerPanel.add(new JLabel(images[0]));
            }
            for (int loop1 = 0; loop1 < strFreqLen; loop1++) {
                int imageIndex = Integer.parseInt(strFreq.substring(loop1,loop1+1));
                innerPanel.add(new JLabel(images[imageIndex]));
            }
            
            innerPanel.add(new JLabel(images[10]));
            
            int imageIndex = Integer.parseInt(floatPart.substring(0,1));
            innerPanel.add(new JLabel(images[imageIndex]));
        } else {
            int  strFreqLen = strFreq.length();
            int zeroPadAmt = NUM_IMAGES - (strFreqLen);
            
            for (int loop = 0; loop < zeroPadAmt; loop++) {
                innerPanel.add(new JLabel(images[0]));
            }
            for (int loop1 = 0; loop1 < strFreqLen; loop1++) {
                int imageIndex = Integer.parseInt(strFreq.substring(loop1,loop1+1));
                innerPanel.add(new JLabel(images[imageIndex]));
            }
            
        }
        // innerPanel.setBackground(new Color(50,50,50));
        add(innerPanel);
    }
    
    private void displayError() {
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[0]));
        innerPanel.add(new JLabel(images[0]));
        innerPanel.add(new JLabel(images[0]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.setBackground(new Color(0,0,0));
        add(innerPanel);
    }
    
    private void displayDefaultDigits() {
        innerPanel.add(new JLabel(images[0]));
        innerPanel.add(new JLabel(images[1]));
        innerPanel.add(new JLabel(images[2]));
        innerPanel.add(new JLabel(images[3]));
        innerPanel.add(new JLabel(images[4]));
        innerPanel.add(new JLabel(images[5]));
        innerPanel.add(new JLabel(images[6]));
        innerPanel.add(new JLabel(images[7]));
        innerPanel.add(new JLabel(images[8]));
        innerPanel.add(new JLabel(images[10]));
        innerPanel.add(new JLabel(images[9]));
        innerPanel.setBackground(new Color(0,0,0));
        add(innerPanel);
    }
}
