package motherteresa;
/***********************************************************************
 * JazzMachine.java - Source Code (requires java 1.1)                  *
 * Version release date : June 24, 2003                                *
 * Copyright (C) 2000-2003  Neural Semantics sprl, Belgium             *
 * Author : Michel Petre mpetre@neuralsemantics.com                    *
 *                                                                     *
 *     http://www.neuralsemantics.com/                                 *
 *                                                                     *
 * This code is released under GNU GPL license, version 2 or later,    *
 * with the following modifications :                                  *
 *                                                                     *
 *   Commercial use - you are free to use any part of this source      *
 *   code in any commercial software, provided that :                  *
 *      1. If you distribute the source code, this copyright notice    *
 *         must remain unchanged.                                      *
 *      2. A credit must be added to your software and documentation,  *
 *         on every place where your own copyright notices appear.     *
 *      3. A free licensed copy of your software must be provided      *
 *         to Neural Semantics.                                        *
 *      More questions ? report@neuralsemantics.com                    *
 *                                                                     *
 *   Non commercial software - A credit must be added to your software *
 *   and documentation. For applets, a link to our web page is enough. *
 *                                                                     *
 *   Use and modifications of the software : parts of this code can    *
 *   only be used in software that are - conceptually - clearly        *
 *   different from this one. This means that just making cosmetic     *
 *   changes to the code in order to remove/change the displayed       *
 *   copyright notice or the link to our web page is expressly         *
 *   forbidden.                                                        *
 *                                                                     *
 *   This notice must remain intact in all copies of this code.        *
 *   This code is distributed WITHOUT ANY WARRANTY OF ANY KIND.        *
 *   The GNU GPL license can be found at :                             *
 *           http://www.gnu.org/copyleft/gpl.html                      *
 *                                                                     *
 * Please feel free to use any portion of this code for your best      *
 * purposes !                                                          *
 ***********************************************************************/

 /* History & changes **************************************************
 *                                                                     *
 ******** August 18, 2000 **********************************************
 *   - First release                                                   *
 ******** November 14, 2000 ********************************************
 *   - Added setForeground(Color c) to the labels, to overcome a       *
 *     minor color inheritance bug in some IE JVM's                    *
 ******** May 21, 2000 *************************************************
 *   - Added standard hyperlink panel                                  *
 *   - Changed static variable names to uppercase                      *
 *   - Display output sound frequency instead of requested frequency   *
 ******** January 30, 2002 *********************************************
 *   - Lowered volume range and default volume level                   *
 *   - Enabled applet color control through the <APPLET> tag           *
 ******** December 7, 2002 *********************************************
 *   - Enabled font size control through the <APPLET> tag              *
 ******** June 24, 2003 ************************************************
 *   - Changed hyperlink panel                                         *
 ***********************************************************************/


import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class JazzMachine extends JFrame 
             implements Runnable, AdjustmentListener, MouseListener {

  // program name
  final static String TITLE = "The jazz machine";

  // Line separator char
  final static String LSEP = System.getProperty("line.separator");

  // Value range
  final static int MIN_FREQ = 1;   // min value for barFreq 
  final static int MAX_FREQ = 200; // max value for barFreq 
  final static int MIN_AMPL = 0;   // min value for barVolume 
  final static int MAX_AMPL = 100; // max value for barVolume

  // Sun's mu-law audio rate = 8KHz
  private double rate = 8000d;       

  private boolean audioOn = false;     // state of audio switch (on/off)
  private boolean changed = true;      // change flag
  private int freqValue = 1;           // def value of frequency scrollbar
  private int amplValue = 70;          // def value of volume scrollbar
  private int amplMultiplier = 100;    // volume multiplier coeff 
  private int amplitude;    // the requested values
  private double frequency;
  private double soundFrequency;          // the actual output frequency

  // the mu-law encoded sound samples
  private byte[] mu;
  // the audio stream
  private java.io.InputStream soundStream;

  // graphic components
  private Scrollbar barVolume, barFreq;
  private Label labelValueFreq;
  private Canvas canvas;    

  // flag for frequency value display
  private boolean showFreq = true;

  // width and height of canvas area
  private int cw, ch;
  // offscreen Image and Graphics objects
  private Image img;
  private Graphics graph;
  

  // default font size
  private int fontSize = 12;

  // hyperlink objects
  private Panel linkPanel;
  
  private Label labelNS;
  private Color inactiveLinkColor = new Color(255,255,128);
  private Color activeLinkColor = Color.white; 
  private Font linkFont = new Font("Dialog", Font.PLAIN, fontSize);

  private JTextField frequencyField;

  // standard font for the labels
  private Font ctrlFont;

  // standard foreground color for the labels
  private Color fgColor = Color.white; 
  // standard background color for the control area
  private Color ctrlColor = Color.darkGray; 
  // standard background color for the graphic ball area
  private Color bgColor = Color.black; 

  // start value for the time counter
  private long startTime;
  // maximum life time for an unchanged sound (10 seconds)
  private long fixedTime = 180000;

  // this applet's context 
  private AppletContext appletContext;

  // animation thread
  Thread runner;

//**********************************************************************
//                             Constructors
//**********************************************************************
  public JazzMachine() 
  {
      init();
      start();
  }

//**********************************************************************
//                                Methods
//**********************************************************************
  public void init() {
    // read applet <PARAM> tags

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent event)
            {
                System.exit(0);
            }
        });
        this.setTitle("Java Frequency Generator");
      

    // font for the labels
    ctrlFont = new Font("Dialog", Font.PLAIN, fontSize);

    // convert scrollbar values to real values (see below for details)
    amplitude = (MAX_AMPL - amplValue) * amplMultiplier;
    frequency = (double)Math.pow(1.2d, (double)(freqValue + 250) / 10.0);

    this.getContentPane().setLayout(new BorderLayout());
    this.setSize(600,600);
    setBackground(ctrlColor);
    setForeground(fgColor);

    Label labelVolume = new Label(" Volume ");
    labelVolume.setForeground(fgColor);
    labelVolume.setAlignment(Label.CENTER);
    labelVolume.setFont(ctrlFont);

    barVolume = new Scrollbar(Scrollbar.VERTICAL, amplValue, 1, 
                     MIN_AMPL, MAX_AMPL + 1);
    barVolume.addAdjustmentListener(this);
    // assign fixed size to the scrollbar
    Panel pVolume = new Panel();
    pVolume.setLayout(null);
    pVolume.add(barVolume);
    barVolume.setSize(16, 90);
    pVolume.setSize(16, 90);

    Label labelFreq = new Label("Frequency");
    labelFreq.setForeground(fgColor);
    labelFreq.setAlignment(Label.RIGHT);
    labelFreq.setFont(ctrlFont);

    barFreq = new Scrollbar(Scrollbar.HORIZONTAL, freqValue, 1, 
                  MIN_FREQ, MAX_FREQ);
    barFreq.addAdjustmentListener(this);
    // assign fixed size to the scrollbar
    Panel pFreq = new Panel();
    pFreq.setLayout(null);
    pFreq.add(barFreq);
    barFreq.setSize(140, 18);
    pFreq.setSize(140, 18);

    // show initial frequency value
    labelValueFreq = new Label();
    if (showFreq) {
      labelValueFreq.setText("0000000 Hz");
      labelValueFreq.setForeground(fgColor);
      labelValueFreq.setAlignment(Label.LEFT);
      labelValueFreq.setFont(ctrlFont);
    }
    
    
    frequencyField = new JTextField("1000");    
    

    Panel east = new Panel();
    east.setLayout(new BorderLayout(10, 10));
    east.add("North", labelVolume);
    Panel pEast = new Panel();
    pEast.add(pVolume);
    east.add("Center", pEast);

    Panel south = new Panel();
    Panel pSouth = new Panel();
    pSouth.setLayout(new FlowLayout(FlowLayout.CENTER));
    pSouth.add(labelFreq);
    pSouth.add(frequencyField);
    pSouth.add(pFreq);
    pSouth.add(labelValueFreq);
    south.add("South", pSouth);

    
    Panel west = new Panel();
    // dummy label to enlarge the panel
    west.add(new Label("      "));

    getContentPane().add("South", south);
    getContentPane().add("East", east);
    getContentPane().add("West", west);
    getContentPane().add("Center", canvas = new Canvas());
    this.show();
  }
//------------------------------------------------------------------------
  private void switchAudio(boolean b) {
    // switch audio to ON if b=true and audio is OFF
    if ((b) && (! audioOn)) {
      try {
        sun.audio.AudioPlayer.player.start(soundStream);
      }
      catch(Exception e) { }
      audioOn = true;
    }
    // switch audio to OFF if b=false and audio is ON
    if ((! b) && (audioOn)) {
      try {
        sun.audio.AudioPlayer.player.stop(soundStream);
      }
      catch(Exception e) { }
      audioOn = false;
    }
  }
//------------------------------------------------------------------------
  private void getChanges() {
    // create new sound wave
    mu = getWave(frequency, amplitude);

    // show new frequency value
    if (showFreq)
      labelValueFreq.setText((new Double(soundFrequency)).toString() + " Hz");
    // shut up !

    switchAudio(false);

    // switch audio stream to new sound sample
    try {
      soundStream = new sun.audio.ContinuousAudioDataStream(new 
                        sun.audio.AudioData(mu));
    }
    catch(Exception e) { }

    // listen
    switchAudio(true);

    // Adapt animation settings
    double prop = (double)freqValue / (double)MAX_FREQ;
    int r = (int)(255 * prop);
    int b = (int)(255 * (1.0 - prop));
    int g = (int)(511 * (.5d - Math.abs(.5d - prop)));

    // start the timer
    startTime = System.currentTimeMillis();

    // things are fixed
    changed = false;
  }

//**********************************************************************
//                               Thread
//**********************************************************************
  public void start() {
    // create thread
    if (runner == null) {
      runner = new Thread(this);
      runner.start();
    }
  }
//------------------------------------------------------------------------
  public void run() {
    // infinite loop
    while (true) {
      // Volume or Frequency has changed ?
      if (changed)
        this.getChanges();

      // a touch of hallucination
      repaint();

      // let the children sleep. Shut up if inactive during more
      // than the fixed time.
      if (System.currentTimeMillis() - startTime > fixedTime)
        switchAudio(false);

      // let the computer breath
      try { Thread.sleep(100); }
      catch (InterruptedException e) { }
    }
  }
//------------------------------------------------------------------------
  public void stop() {
    this.cleanup();
  }
//------------------------------------------------------------------------
  public void destroy() {
    this.cleanup();
  }
//------------------------------------------------------------------------
  private synchronized void cleanup() {
    // shut up !
    switchAudio(false);
    // kill the runner thread
    if (runner != null) {
      try {
        runner.stop();
        runner.join();
        runner = null;
      }
      catch(Exception e) { }
    }
  }

//**********************************************************************
//                     AdjustmentListener Interface
//**********************************************************************
  public void adjustmentValueChanged(AdjustmentEvent e) {
    Object source = e.getSource();

    // Volume range : 0 - 10000
    // ! Scrollbar value range is inverted.
    // ! 100 = multiplier coefficient.
    if (source == barVolume) {
      amplitude = (MAX_AMPL - barVolume.getValue()) * amplMultiplier;
      changed = true;
    }
    // Frequency range : 97 - 3591 Hz
    // ! Scrollbar value range represents a logarithmic function.
    //   The purpose is to assign more room for low frequency values.
    else if (source == barFreq) {
      freqValue = barFreq.getValue();
      frequency = (double)Math.pow(1.2d, (double)(freqValue + 250) / 10.0);
      changed = true;
    }
  }

//**********************************************************************
//                     MouseListener Interface
//**********************************************************************
  public void mouseClicked(MouseEvent e) {
  }
//-------------------------------------------
  public void mouseEntered(MouseEvent e) {
    // text color rollover
    labelNS.setForeground(activeLinkColor);
  }
//-------------------------------------------
  public void mouseExited(MouseEvent e) {
    // text color rollover
    labelNS.setForeground(inactiveLinkColor);
  }
//-------------------------------------------
  public void mousePressed(MouseEvent e) {
    // link to Neural Semantics website
  }
//-------------------------------------------
  public void mouseReleased(MouseEvent e) {
  }


//**********************************************************************
//                          Sound processing
//**********************************************************************
  // Creates a sound wave from scratch, using predefined frequency
  // and amplitude.
  private byte[] getWave(double freq, int ampl) {
    int lin;

    // calculate the number of samples in one sinewave period
    // !! change this to multiple periods if you need more precision !!
    int nSample = (int)(rate / freq);

    // calculate output wave frequency 
    soundFrequency = (double)(rate / nSample);
 
    // create array of samples
    byte[] wave = new byte[nSample];

    // pre-calculate time interval & constant stuff
    double timebase = 2.0 * Math.PI * freq / rate;

    // Calculate samples for a single period of the sinewave.
    // Using a single period is no big precision, but enough 
    // for this applet anyway !
    for (int i=0; i<nSample; i++) {
      // calculate PCM sample value
      lin = (int)(Math.sin(timebase * i) * ampl);
      // convert it to mu-law
      wave[i] = linToMu(lin);
    }
    return wave;
  }

/************************************************************************/
/* linToMu : performs PCM to mu-law conversion                          */
/*           originally for Sparcstation 1.                             */
/*                                                                      */
/*   Original C code :                                                  */
/*                                                                      */
/*      Copyright 1989 by Rich Gopstein and Harris Corporation          */
/*                                                                      */
/*      Permission to use, copy, modify, and distribute this software   */
/*      and its documentation for any purpose and without fee is        */
/*      hereby granted, provided that the above copyright notice        */
/*      appears in all copies and that both that copyright notice and   */
/*      this permission notice appear in supporting documentation, and  */
/*      that the name of Rich Gopstein and Harris Corporation not be    */
/*      used in advertising or publicity pertaining to distribution     */
/*      of the software without specific, written prior permission.     */
/*      Rich Gopstein and Harris Corporation make no representations    */
/*      about the suitability of this software for any purpose.  It     */
/*      provided "as is" without express or implied warranty.           */
/*                                                                      */
/*   Translated to Java by Neural Semantics sprl                      */
/*      http://www.neuralsemantics.com/                                 */
/************************************************************************/

  private static byte linToMu(int lin) {
    int mask;
    if (lin < 0) {
      lin = -lin;
      mask = 0x7F;
    }
    else  {
      mask = 0xFF;
    }
    if (lin < 32) 
      lin = 0xF0 | 15 - (lin / 2);
    else if (lin < 96) 
      lin = 0xE0 | 15 - (lin-32) / 4;
    else if (lin < 224) 
      lin = 0xD0 | 15 - (lin-96) / 8;
    else if (lin < 480) 
      lin = 0xC0 | 15 - (lin-224) / 16;
    else if (lin < 992) 
      lin = 0xB0 | 15 - (lin-480) / 32;
    else if (lin < 2016) 
      lin = 0xA0 | 15 - (lin-992) / 64;
    else if (lin < 4064) 
      lin = 0x90 | 15 - (lin-2016) / 128;
    else if (lin < 8160) 
      lin = 0x80 | 15 - (lin-4064) / 256;
    else 
      lin = 0x80;
    return (byte)(mask & lin);
  }

//**********************************************************************
//                             Applet info
//**********************************************************************
  public String getAppletInfo() {
    String s = "The jazz machine" + LSEP + LSEP +
               "A music synthetizer applet" + LSEP +
               "Copyright (c) Neural Semantics, 2000-2003" + LSEP + LSEP +
               "Home page : http://www.neuralsemantics.com/";
    return s;
  }

  //**********************************************************************
//                                 Interface
//**********************************************************************
  public Dimension getPreferredSize() {
    return new Dimension(getSize().width, getSize().height);
  }

  public static void main(String[] args)
  {
        new JazzMachine();
  }

}
