/*
 * MTRifeFunctionGenerator.java
 *
 * Created on 07 June 2005, 12:31
 */

package motherteresa;

/**
 *
 * @author  HWBadenhorst
 */
import java.util.*;
import java.text.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.event.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.FloatControl;


public class MTRifeFunctionGenerator extends JPanel implements ActionListener
{
    /** Creates a new instance of MTRifeFunctionGenerator */
    public MTRifeFunctionGenerator(ArrayList frequencySetData)
    {
        super();
        if (frequencySetData.isEmpty())
        {
            fxSetData = new ArrayList(5);
            HashMap tmpMap = new HashMap(1);
            tmpMap.put("20","180"); 
            fxSetData.add(tmpMap.clone());  
            tmpMap.clear(); 
            tmpMap.put("727","180"); 
            fxSetData.add(tmpMap.clone());  
            tmpMap.clear(); 
            tmpMap.put("880","180"); 
            fxSetData.add(tmpMap.clone());  
            tmpMap.clear(); 
            tmpMap.put("10000","180"); 
            fxSetData.add(tmpMap.clone());  
        } else
        {
           fxSetData = frequencySetData;
        }
        initFxTable(fxSetData);
        initFxChart(fxSetData);
        buildPanels();
    }
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border compoundBorder = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
    JPanel innerPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel functionButtonPanel = new JPanel();
    JPanel forwardBackwardButtonPanel = new JPanel();
    JPanel volumePanel = new JPanel();
    JPanel attribPanel = new JPanel();
    
    JPanel waveAttribPanel = new JPanel();
    JPanel fxAttribPanel = new JPanel();
    
    JPanel waveTypeHeadingPanel = new JPanel();
    JPanel waveTypePanel = new JPanel();
    JPanel outPanPanel = new JPanel();
    JPanel waveTypeAndPanPanel = new JPanel();
    
    JPanel fxDurationControlHeadingPanel = new JPanel();
    JPanel fxDurationControlPanel = new JPanel();
    JPanel fxRepeatSetPanel = new JPanel();
    JPanel fxRepeatPanel = new JPanel();
    JPanel fxRepeatAllPanel = new JPanel();
    JPanel manualFxPanel = new JPanel();
    JPanel attribFillerPanel = new JPanel();
    JPanel manualSweepPanel = new JPanel();
    JPanel manualButtonPanel = new JPanel();
    JPanel chartPanel = new JPanel();
    JPanel tableAndStatusPanel = new JPanel();
    JPanel tablePanel = new JPanel();
    JPanel statusPanel = new JPanel();
    JPanel fxViewPanel = new JPanel();
    JPanel setTimeLeftPanel = new JPanel();
    JPanel fxTimeLeftPanel = new JPanel();
    JPanel frequenciesLeftPanel = new JPanel();
    JPanel generatorStatusPanel = new JPanel();
    JPanel tableEditButtonPanel = new JPanel();
    JPanel loadSavePanel = new JPanel();
    
    static String imagePath = InfoManager.TARGET_VOLUME + "\\Mother Teresa\\images\\";
    
    JButton startButton = new JButton("Run", new ImageIcon(imagePath + "Play24.gif"));
    JButton pauseButton = new JButton("Pause", new ImageIcon(imagePath + "Pause24.gif"));
    JButton stopButton = new JButton("Stop", new ImageIcon(imagePath + "Stop24.gif"));
    JButton applyChangesButton = new JButton("Add Fx");
    JButton applySweepButton = new JButton("Add Sweep");
    
    JButton incrementButton = new JButton(new ImageIcon(imagePath + "Up24.gif"));
    JButton decrementButton = new JButton(new ImageIcon(imagePath + "Down24.gif"));
    JButton deleteRowButton = new JButton(new ImageIcon(imagePath + "RowDelete24.gif"));
    JButton sortSetDescButton = new JButton(new ImageIcon(imagePath + "SortDesc.gif"));
    JButton sortSetAscButton = new JButton(new ImageIcon(imagePath + "SortAsc.gif"));
    JButton removeDupsButton = new JButton(new ImageIcon(imagePath + "remdup.gif"));
    JButton adjustTimeButton = new JButton(new ImageIcon(imagePath + "t60.gif"));
    
    JButton saveSetButton = new JButton(new ImageIcon(imagePath + "Save24.gif"));
    JButton loadSetButton = new JButton(new ImageIcon(imagePath + "Open24.gif"));
    
    JFileChooser freqFileChooser =  null;
    
    
    ImageIcon rightIcon = new ImageIcon(imagePath + "arrowright.gif");
    ImageIcon leftIcon = new ImageIcon(imagePath + "arrowleft.gif");
    JButton nextFxButton = new JButton(rightIcon);
    JButton previousFxButton = new JButton(leftIcon);
    JSlider volumeSlider = new JSlider(JSlider.VERTICAL,0,100,70);
    JRadioButton sineButton = new JRadioButton("Sinesoidial Wave");
    JRadioButton squareButton = new JRadioButton("Square Wave");
    JRadioButton triangleButton = new JRadioButton("Triangular Wave");
    JRadioButton sawToothButton = new JRadioButton("Sawtooth Wave");
    ButtonGroup waveGroup = new ButtonGroup();
    
    JRadioButton leftChannelButton = new JRadioButton("Left Channel");
    JRadioButton rightChannelButton = new JRadioButton("Right Channel");
    JRadioButton bothChannelButton = new JRadioButton("Both Channels");
    ButtonGroup channelGroup = new ButtonGroup();

    JLabel waveTypeLabel = new JLabel("<html><h2><font color=\"blue\">Waveform Type</font></h2></htlm>");
    JLabel durationControlLabel = new JLabel("<html><h2><font color=\"blue\">Frequency Repeats</font></h2></htlm>");
    JLabel manualControlLabel = new JLabel("<html><h2><font color=\"blue\">Manual Frequency Entry</font></h2></htlm>");
    
    //JLabel setRepeatLabel = new JLabel("<html><h3><font color=\"green\">Set Repeat Count</font></h3></htlm>");
    //JLabel currentRepeatLabel = new JLabel("<html><h3><font color=\"green\">Current Fx Repeat</font></h3></htlm>");
    //JLabel manualTimeLabel = new JLabel("<html><h3><font color=\"green\">Manual Time</font></h3></htlm>");
    //JLabel manualFxLabel = new JLabel("<html><h3><font color=\"green\">Manual Fx</font></h3></htlm>");
    
    JLabel setRepeatLabel = new JLabel("Set Repeat Count");
    JLabel currentRepeatLabel = new JLabel("Current Fx Repeat");
    JLabel manualTimeLabel = new JLabel("Time (Secs)");
    JLabel manualFxLabel = new JLabel("Single Fx (Hz)");
    JLabel fxAllLabel = new JLabel("All Fx Repeat Count");
    
    JLabel startFXLabel = new JLabel("Start Fx");
    JLabel endFXLabel = new JLabel("End Fx");
    JLabel incrementFxLabel = new JLabel("Increment");
    JLabel sweepDurationLabel = new JLabel("Time");
    JTextField manualFxField = new JTextField("1000");
    
    
    JLabel timeLeftHeading = new JLabel("<html><h2><font color=\"blue\">Set Progress</font></h2></htlm>");
    JLabel setTimeLeftLabel = new JLabel("Set Time (Mins)");
    JLabel fxTimeLeftLabel = new JLabel("Current Fx Time (Secs)");
    JLabel fxFrequenciesLeftLabel = new JLabel("Frequencies Left");
    JLabel fxFrequenciesLeft = new JLabel("---");
    JLabel setTimeLeft = new JLabel("---");
    JLabel fxTimeLeft = new JLabel("---");
    String runningString = "<html><h3><font color=\"red\">RUNNING...</font></h3></htlm>";
    String pausedString = "<html><h3><font color=\"blue\">PAUSED...</font></h3></htlm>";
    String stoppedString = "<html><h3><font color=\"green\">STOPPED...</font></h3></htlm>";
    JLabel fxGenStatusLabel = new JLabel(stoppedString);
    
    JTextField startFxField = new JTextField("1");
    JTextField endFxField = new JTextField("1000");
    JTextField icrementFxField = new JTextField("1.0");
    
    JTable frequencyListTable =  null;
    //new JTable(tableData,tableHeading);
    JScrollPane frequencyScrollPane =  null;
    
    String[][] frequencyListData = null;
    String[] freqListHeading = new String[2];
    
    
    JSpinner fxTimeSpinner = null;
    JSpinner fxSetSpinner = null;
    JSpinner fxSpinner = null;
    
    int	nWaveformType = Oscillator.WAVEFORM_SQUARE;
    int timePeriod = 0;
    int freqTableCount = 0;
    float	fSampleRate = 44100.0F;
    float	fSignalFrequency = 3000.0F;
    float	fAmplitude = 0.7F;
    AudioFormat	audioFormat;
    static final int BUFFER_SIZE = 128000;
    SourceDataLine	line = null;
    AudioInputStream	oscillator = null;
    byte[]		abData = null;
    Thread oscillatorThread = null;
    int runTimeInSeconds = 5;
    ArrayList fxSetData = null;
    MTFrequencyPanel freqPanel = null;
    float  floatSetTime = 0;
    boolean oscThreadSuspendedState = false;
    DefaultTableModel dm = null;
    FrequencyChart freqChart = null;
    int fxLoop = 0;
    int startFreqPos = 0;
    int rowCount = 0;
    File freqFile = null;
    
    public void initFxTable(ArrayList frequencyList)
    {
        freqTableCount = frequencyList.size();
        frequencyListData = new String[frequencyList.size()][2];
        freqListHeading[0] = "Frequency";
        freqListHeading[1] = "Time";
        String timeIn = null;
        String frequencyIn = null;
        
        final String headers[] = new String[frequencyList.size()];
        for (int popLoop = 0; popLoop < frequencyList.size(); popLoop++)
        {
            headers[popLoop] = String.valueOf(popLoop+1);
        }
        
        
        
        ListModel lm = new AbstractListModel()
        {
            
            public int getSize()
            {
                return headers.length;
            }
            
            public Object getElementAt(int index)
            {
                return headers[index];
            }
        };
        
        
        for (int fxLoop = 0; fxLoop < frequencyList.size(); fxLoop++)
        {
            HashMap freqMap = (HashMap)frequencyList.get(fxLoop);
            
            Set freqMapKeySet = freqMap.keySet();
            Iterator freqMapIter = freqMapKeySet.iterator();
            while (freqMapIter.hasNext())
            {
                frequencyIn = (String)freqMapIter.next();
                timeIn = (String)freqMap.get((Object)frequencyIn);
            }
            frequencyListData[fxLoop][0] = frequencyIn;
            frequencyListData[fxLoop][1] = timeIn;
        }
        
        dm = new DefaultTableModel(frequencyListData,freqListHeading);
        frequencyListTable = new JTable(dm);
        frequencyListTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        JList rowHeader = new JList(lm);
        rowHeader.setFixedCellWidth(30);
        
        rowHeader.setFixedCellHeight(frequencyListTable.getRowHeight()-1
        + frequencyListTable.getRowMargin());
        //                           + table.getIntercellSpacing().height);
        rowHeader.setCellRenderer(new RowHeaderRenderer(frequencyListTable));
        
        frequencyScrollPane = new JScrollPane(frequencyListTable);
        frequencyScrollPane.setRowHeaderView(rowHeader);
    }
    
    class RowHeaderRenderer extends JLabel implements ListCellRenderer
    {
        RowHeaderRenderer(JTable table)
        {
            JTableHeader header = table.getTableHeader();
            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(CENTER);
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont());
        }
        
        public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus)
        {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    
    public void initFxChart(ArrayList frequencyList)
    {
        freqChart = new FrequencyChart(frequencyList);
        chartPanel.add(freqChart);
    }
    
    private void buildPanels()
    {
        
        String[] intTimeStrings = new String[1000];
        for (int loop = 0; loop < 1000; loop++)
        {
            intTimeStrings[loop] = String.valueOf(loop);
        }
        
        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        stopButton.addActionListener(this);
        sineButton.addActionListener(this);
        squareButton.addActionListener(this);
        triangleButton.addActionListener(this);
        sawToothButton.addActionListener(this);
        leftChannelButton.addActionListener(this);
        rightChannelButton.addActionListener(this);
        bothChannelButton.addActionListener(this);
        applyChangesButton.addActionListener(this);
        nextFxButton.addActionListener(this);
        previousFxButton.addActionListener(this);
        
        incrementButton.setToolTipText("Press to increment the selected Frequency or Time in the Frequency Table.");
        decrementButton.setToolTipText("Press to decrement the selected Frequency or Time in the Frequency Table.");
        deleteRowButton.setToolTipText("Press to delete the selected row in the Frequency Table.");
        sortSetDescButton.setToolTipText("Press to sort the Frequency Table set in Descending order.");
        sortSetAscButton.setToolTipText("Press to sort the Frequency Table set in Ascending order.");
        removeDupsButton.setToolTipText("Press to remove all duplicates from table.");
        adjustTimeButton.setToolTipText("Press to adjust time to 60 Minutes total for set.");
        SpinnerListModel intSweepTimeModel = new  CyclingSpinnerListModel(intTimeStrings);
        JSpinner intSweepTimeSpinner = new JSpinner(intSweepTimeModel);
        String defSweepTimeValue = "180";
        intSweepTimeSpinner.setValue(defSweepTimeValue);
        
        SCLayout mainPanelLayout  = new SCLayout(3,SCLayout.FILL,SCLayout.FILL,3);
        freqPanel = new MTFrequencyPanel("0.0");//9999999.0
        PointLayout freqPanelLayout = new PointLayout();
        freqPanel.setLayout(freqPanelLayout);
        
        mainPanelLayout.setScale(0, 0.12);
        mainPanelLayout.setScale(2, 0.08);
        setLayout(mainPanelLayout);
        setBorder(raisedbevel);
        
        SRLayout attribPanelLayout  = new SRLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        attribPanelLayout.setScale(2, 0.2);
        attribPanel.setLayout(attribPanelLayout);
        
        SRLayout waveTypeAndPanPanelLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        waveTypeAndPanPanel.setLayout(waveTypeAndPanPanelLayout);

        
        
        
        SRLayout setTimeLeftPanelLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        setTimeLeftPanelLayout.setScale(1, 0.3);
        setTimeLeftPanel.setLayout(setTimeLeftPanelLayout);
        
        SRLayout fxTimeLeftPanelLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        fxTimeLeftPanelLayout.setScale(1, 0.3);
        fxTimeLeftPanel.setLayout(fxTimeLeftPanelLayout);
        
        SRLayout frequenciesLeftPanelLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        frequenciesLeftPanelLayout.setScale(1, 0.3);
        frequenciesLeftPanel.setLayout(frequenciesLeftPanelLayout);
        
        //add components to above panels
        setTimeLeftPanel.add(setTimeLeftLabel);;
        setTimeLeftPanel.add(setTimeLeft);;
        
        fxTimeLeftPanel.add(fxTimeLeftLabel);;
        fxTimeLeftPanel.add(fxTimeLeft);;
        
        frequenciesLeftPanel.add(fxFrequenciesLeftLabel);
        frequenciesLeftPanel.add(fxFrequenciesLeft);
        
        SCLayout waveAttribPanelLayout  = new SCLayout(9,SCLayout.FILL,SCLayout.FILL,20);
        waveAttribPanelLayout.setScale(0, 0.3);
        waveAttribPanelLayout.setScale(2, 0.3);
        waveAttribPanelLayout.setScale(4, 0.3);
        waveAttribPanelLayout.setScale(5, 0.3);
        waveAttribPanelLayout.setScale(6, 0.3);
        waveAttribPanelLayout.setScale(7, 0.4);
        waveAttribPanelLayout.setScale(8, 0.4);
        waveAttribPanel.setLayout(waveAttribPanelLayout);
        
        PointLayout waveTypeHeadingLayout = new PointLayout();
        waveTypeHeadingPanel.setLayout(waveTypeHeadingLayout);
        
        SCLayout waveTypeLayout  = new SCLayout(4,SCLayout.FILL,SCLayout.FILL,3);
        waveTypePanel.setLayout(waveTypeLayout);
        
        SCLayout outPanPanelLayout  = new SCLayout(3,SCLayout.FILL,SCLayout.FILL,3);
        outPanPanel.setLayout(outPanPanelLayout);
        
        
        outPanPanel.add(leftChannelButton);
        outPanPanel.add(bothChannelButton);
        outPanPanel.add(rightChannelButton);
        
        
        PointLayout fxDurationControlHeadingLayout = new PointLayout();
        fxDurationControlHeadingPanel.setLayout(fxDurationControlHeadingLayout);
        
        SCLayout fxDurationControlLayout  = new SCLayout(3,SCLayout.FILL,SCLayout.FILL,3);
        fxDurationControlPanel.setLayout(fxDurationControlLayout);
        
        SRLayout fxRepeatSetLayout  = new SRLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        fxRepeatSetLayout.setScale(1, 0.2);
        fxRepeatSetPanel.setLayout(fxRepeatSetLayout);
        
        SRLayout fxRepeatLayout  = new SRLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        fxRepeatLayout.setScale(1, 0.2);
        fxRepeatPanel.setLayout(fxRepeatLayout);
        
        SRLayout fxRepeatAllLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        fxRepeatAllLayout.setScale(1, 0.2);
        fxRepeatAllPanel.setLayout(fxRepeatAllLayout);
        
        SRLayout manualFxLayout  = new SRLayout(4,SRLayout.FILL,SRLayout.FILL,3);
        manualFxLayout.setScale(1, 0.5);
        manualFxLayout.setScale(3, 0.5);
        manualFxPanel.setLayout(manualFxLayout);
        
        SRLayout manualSweepLayout  = new SRLayout(8,SRLayout.FILL,SRLayout.FILL,3);
        manualSweepLayout.setScale(1, 0.8);
        manualSweepLayout.setScale(3, 0.8);
        manualSweepLayout.setScale(5, 0.5);
        manualSweepPanel.setLayout(manualSweepLayout);
        
        
        SRLayout tableEditButtonLayout  = new SRLayout(7,SRLayout.FILL,SRLayout.FILL,3);
        tableEditButtonPanel.setLayout(tableEditButtonLayout);
        
        tableEditButtonPanel.add(decrementButton);
        tableEditButtonPanel.add(incrementButton);
        tableEditButtonPanel.add(sortSetAscButton);
        tableEditButtonPanel.add(sortSetDescButton);
        tableEditButtonPanel.add(deleteRowButton);
        tableEditButtonPanel.add(removeDupsButton);
        tableEditButtonPanel.add(adjustTimeButton);
        
        decrementButton.addActionListener(this);
        incrementButton.addActionListener(this);
        sortSetAscButton.addActionListener(this);
        adjustTimeButton.addActionListener(this);
        removeDupsButton.addActionListener(this);
        sortSetDescButton.addActionListener(this);
        deleteRowButton.addActionListener(this);
        loadSetButton.addActionListener(this);
        saveSetButton.addActionListener(this);
        
        
        SCLayout fxViewPanelLayout  = new SCLayout(2,SCLayout.FILL,SCLayout.FILL,3);
        fxViewPanelLayout.setScale(1, 0.5);
        fxViewPanel.setLayout(fxViewPanelLayout);
        
        fxFrequenciesLeft.setForeground(new Color(255,0,0));
        setTimeLeft.setForeground(new Color(255,0,0));
        fxTimeLeft.setForeground(new Color(255,0,0));
        
        SRLayout tableAndSweepLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        tableAndStatusPanel.setLayout(tableAndSweepLayout);
        
        PointLayout chartLayout = new PointLayout();
        chartPanel.setLayout(chartLayout);
        
        PointLayout tableLayout = new PointLayout();
        tablePanel.setLayout(chartLayout);
        
        SCLayout statusLayout  = new SCLayout(6,SCLayout.FILL,SCLayout.FILL,3);
        //statusLayout.setScale(4,1.8);
        
        statusPanel.setLayout(statusLayout);
        
        PointLayout genStatLayout = new PointLayout();
        generatorStatusPanel.setLayout(genStatLayout);
        generatorStatusPanel.add(fxGenStatusLabel);
        //add Status Label and set time left and fx tiem left
        statusPanel.add(timeLeftHeading);
        statusPanel.add(generatorStatusPanel);
        statusPanel.add(setTimeLeftPanel);
        statusPanel.add(fxTimeLeftPanel);
        statusPanel.add(frequenciesLeftPanel);
        statusPanel.add(tableEditButtonPanel);
        
        manualSweepPanel.add(startFXLabel);
        manualSweepPanel.add(startFxField);
        manualSweepPanel.add(endFXLabel);
        manualSweepPanel.add(endFxField);
        manualSweepPanel.add(incrementFxLabel);
        manualSweepPanel.add(icrementFxField);
        manualSweepPanel.add(sweepDurationLabel);
        manualSweepPanel.add(intSweepTimeSpinner);
        //manualSweepPanel.add(applySweepButton);
        
        SRLayout manualButtonPanelLayout  = new SRLayout(4,SRLayout.FILL,SRLayout.FILL,3);
        manualButtonPanel.setLayout(manualButtonPanelLayout);
        manualButtonPanel.add(applyChangesButton);
        manualButtonPanel.add(applySweepButton);
        manualButtonPanel.add(saveSetButton);
        manualButtonPanel.add(loadSetButton);
        
        
        SRLayout loadSavePanelLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        loadSavePanel.setLayout(loadSavePanelLayout);
        
        //loadSavePanel.add(saveSetButton);
        //loadSavePanel.add(loadSetButton);
        
        waveTypeHeadingPanel.add(waveTypeLabel);
        fxDurationControlHeadingPanel.add(durationControlLabel);
        
        squareButton.setSelected(true);
        bothChannelButton.setSelected(true);
        
        sineButton.setActionCommand("Sine");
        squareButton.setActionCommand("Square");
        triangleButton.setActionCommand("Triangle");
        sawToothButton.setActionCommand("Sawtooth");

        leftChannelButton.setActionCommand("Left");
        rightChannelButton.setActionCommand("Right");
        bothChannelButton.setActionCommand("Both");
        
        
        waveGroup.add(sineButton);
        waveGroup.add(squareButton);
        waveGroup.add(triangleButton);
        waveGroup.add(sawToothButton);
        
        channelGroup.add(leftChannelButton);
        channelGroup.add(bothChannelButton);
        channelGroup.add(rightChannelButton);
        
        waveTypePanel.add(sineButton);
        waveTypePanel.add(squareButton);
        waveTypePanel.add(triangleButton);
        waveTypePanel.add(sawToothButton);
        
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(new SliderListener());
        
        //TitledBorder volumeBorder = BorderFactory.createTitledBorder("Volume");
        //volumeBorder.setTitleJustification(volumeBorder.CENTER);
        PointLayout volumeLayout = new PointLayout();
        //volumePanel.setBorder(volumeBorder);
        volumePanel.setLayout(volumeLayout);
        volumePanel.add(volumeSlider);
        
        String[] intStrings = new String[10];
        String defaultValue = null;
        for (int loop = 0; loop < 10; loop++)
        {
            intStrings[loop] = String.valueOf(loop);
        }
        SpinnerListModel intfxSetModel = new  CyclingSpinnerListModel(intStrings);
        SpinnerListModel intfxModel = new  CyclingSpinnerListModel(intStrings);
        SpinnerListModel intfxAllModel = new  CyclingSpinnerListModel(intStrings);
        
        fxSetSpinner = new JSpinner(intfxSetModel);
        String defValue = "1";
        fxSetSpinner.setValue(defValue);
        fxSpinner = new JSpinner(intfxModel);
        fxSpinner.setValue(defValue);
        JSpinner fxAllSpinner = new JSpinner(intfxAllModel);
        fxAllSpinner.setValue(defValue);
        
        SpinnerListModel intTimeModel = new  CyclingSpinnerListModel(intTimeStrings);
        fxTimeSpinner = new JSpinner(intTimeModel);
        String defTimeValue = "180";
        fxTimeSpinner.setValue(defTimeValue);
        
        fxRepeatSetPanel.add(setRepeatLabel);
        fxRepeatSetPanel.add(fxSetSpinner);
        
        fxRepeatPanel.add(currentRepeatLabel);
        fxRepeatPanel.add(fxSpinner);
        
        fxRepeatAllPanel.add(fxAllLabel);
        fxRepeatAllPanel.add(fxAllSpinner);
        
        manualFxPanel.add(manualFxLabel);
        manualFxPanel.add(manualFxField);
        manualFxPanel.add(manualTimeLabel);
        manualFxPanel.add(fxTimeSpinner);
        
        fxDurationControlPanel.add(fxRepeatSetPanel);
        fxDurationControlPanel.add(fxRepeatPanel);
        fxDurationControlPanel.add(fxRepeatAllPanel);

        waveTypeAndPanPanel.add(waveTypePanel);
        waveTypeAndPanPanel.add(outPanPanel);
        
        waveAttribPanel.add(waveTypeHeadingPanel);
        waveAttribPanel.add(waveTypeAndPanPanel);
        waveAttribPanel.add(fxDurationControlHeadingPanel);
        waveAttribPanel.add(fxDurationControlPanel);
        waveAttribPanel.add(manualControlLabel);
        waveAttribPanel.add(manualFxPanel);
        waveAttribPanel.add(manualSweepPanel);
        waveAttribPanel.add(manualButtonPanel);
        waveAttribPanel.add(loadSavePanel);
        
        tablePanel.add(frequencyScrollPane);
        tableAndStatusPanel.add(tablePanel);
        tableAndStatusPanel.add(statusPanel);
        fxViewPanel.add(tableAndStatusPanel);
        fxViewPanel.add(chartPanel);
        
        
        attribPanel.add(waveAttribPanel);
        attribPanel.add(fxViewPanel);
        attribPanel.add(volumePanel);
        
        SRLayout buttonPanelLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        buttonPanelLayout.setScale(0, 0.33);
        buttonPanel.setLayout(buttonPanelLayout);
        
        SRLayout functionButtonPanelLayout  = new SRLayout(3,SRLayout.FILL,SRLayout.FILL,3);
        functionButtonPanel.setLayout(functionButtonPanelLayout);
        functionButtonPanel.add(startButton);
        functionButtonPanel.add(pauseButton);
        functionButtonPanel.add(stopButton);
        
        SRLayout forwardBackwardButtonPanelLayout  = new SRLayout(2,SRLayout.FILL,SRLayout.FILL,3);
        forwardBackwardButtonPanel.setLayout(forwardBackwardButtonPanelLayout);
        forwardBackwardButtonPanel.add(previousFxButton);
        forwardBackwardButtonPanel.add(nextFxButton);
        
        buttonPanel.setBorder(raisedbevel);
        buttonPanel.add(forwardBackwardButtonPanel);
        buttonPanel.add(functionButtonPanel);
        
        add(freqPanel);
        add(attribPanel);
        add(buttonPanel);
        floatSetTime = calcSetTime(fxSetData);
        String strSetTime = String.valueOf(floatSetTime);
        strSetTime = strSetTime.substring(0,strSetTime.indexOf(".")+2);
        setTimeLeft.setText(strSetTime);
        int freqLeft = fxSetData.size();
        fxFrequenciesLeft.setText(String.valueOf(freqLeft));
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);
        sortSetDescButton.setEnabled(true);
        sortSetAscButton.setEnabled(true);
        removeDupsButton.setEnabled(true);
        adjustTimeButton.setEnabled(true);
        
        
    }
    
    public void initFrequencyGenerator(int waveformType, float sigFrequency, float sigAmplitude)
    {
        audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
        fSampleRate, 16, 2, 4, fSampleRate, false);
        oscillator = new Oscillator(
        waveformType,
        sigFrequency,
        sigAmplitude,
        audioFormat,
        AudioSystem.NOT_SPECIFIED);
        DataLine.Info	info = new DataLine.Info(
        SourceDataLine.class,
        audioFormat);
        try
        {
            line = (SourceDataLine) AudioSystem.getLine(info);
            
            line.open(audioFormat);
            FloatControl panControl = (FloatControl)line.getControl(FloatControl.Type.PAN);
            if (leftChannelButton.isSelected() ==  true)
            {
                panControl.setValue(-1);
            }
            if (bothChannelButton.isSelected() ==  true)
            {
                panControl.setValue(0);
            }
            if (rightChannelButton.isSelected() ==  true)
            {
                panControl.setValue(1);
            }
            line.start();
            abData = new byte[BUFFER_SIZE];
        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void runFrequency()
    {
        Calendar rightNow = Calendar.getInstance();
        java.util.Date dateTime = rightNow.getTime();
        long timeStart = dateTime.getTime();
        long timeToEnd = (runTimeInSeconds * 1000) + timeStart;
        while (true)
        {
            //Calendar timeNow = Calendar.getInstance();
            Calendar rightTimeNow = Calendar.getInstance();
            java.util.Date dateTimeNow = rightTimeNow.getTime();
            long timeNow = dateTimeNow.getTime();
            if (timeNow >= timeToEnd)
            {
                break;
            }
            try
            {
                int	nRead = oscillator.read(abData);
                int	nWritten = line.write(abData, 0, nRead);
            }
            catch (Exception exp)
            {
                
            }
        }
    }
    
    public float calcSetTime(ArrayList frequencySet)
    {
        float fullSetTime = 0;
        String timeIn = null;
        for (int fxLoop = 0; fxLoop < frequencySet.size(); fxLoop++)
        {
            HashMap freqMap = (HashMap)frequencySet.get(fxLoop);
            Set freqMapKeySet = freqMap.keySet();
            Iterator freqMapIter = freqMapKeySet.iterator();
            while (freqMapIter.hasNext())
            {
                String frequencyIn = (String)freqMapIter.next();
                timeIn = (String)freqMap.get((Object)frequencyIn);
            }
            fullSetTime = fullSetTime + Float.parseFloat(timeIn);
        }
        fullSetTime = fullSetTime / 60;
        return fullSetTime;
    }
    
    public float calcSetTime(int startPos)
    {
        float fullSetTime = 0;
        String timeIn = null;
        
        int rowCount = frequencyListTable.getRowCount();
        for (int fxLoop = startPos; fxLoop < rowCount; fxLoop++)
        {
            String frequencyIn = (String)frequencyListTable.getValueAt(fxLoop,0);
            timeIn = (String)frequencyListTable.getValueAt(fxLoop,1);
            //timeIn = (String)frequencySet[fxLoop][1];
            fullSetTime = fullSetTime + Float.parseFloat(timeIn);
        }
        fullSetTime = fullSetTime / 60;
        return fullSetTime;
    }
    
    
    public void recalcFreqDuration()
    {
        floatSetTime = calcSetTime(0);
        String strSetTime = String.valueOf(floatSetTime);
        strSetTime = strSetTime.substring(0,strSetTime.indexOf(".")+2);
        setTimeLeft.setText(strSetTime);
        int freqLeft = freqTableCount;
        fxFrequenciesLeft.setText(String.valueOf(freqLeft));
        fxTimeLeft.setText("---");
        
    }
    
    public void recalcFreqDuration(int startRowPos)
    {
        floatSetTime = calcSetTime(startRowPos);
        String strSetTime = String.valueOf(floatSetTime);
        strSetTime = strSetTime.substring(0,strSetTime.indexOf(".")+2);
        setTimeLeft.setText(strSetTime);
        int freqLeft = freqTableCount;
        fxFrequenciesLeft.setText(String.valueOf(freqLeft));
        fxTimeLeft.setText("---");
        
    }
    
    
    public void runFrequencySet(int startFreqPos)
    {
        int setLoopCount = Integer.parseInt((String)fxSetSpinner.getValue());
        for (int setLoop = 0; setLoop < setLoopCount; setLoop++)
        {
            float freqToGen = 0;
            int timeToGen = 0;
            recalcFreqDuration(startFreqPos);
            //String strSetTime = String.valueOf(floatSetTime);
            //strSetTime = strSetTime.substring(0,strSetTime.indexOf(".")+2);
            //setTimeLeft.setText(strSetTime);
            rowCount = frequencyListTable.getRowCount();
            
            for (fxLoop = startFreqPos; fxLoop < rowCount; fxLoop++)
            {
                int freqLeft = frequencyListTable.getRowCount() - fxLoop;
                frequencyListTable.changeSelection(fxLoop,0,false,false);
                fxFrequenciesLeft.setText(String.valueOf(freqLeft));
                String frequencyIn = (String)frequencyListTable.getValueAt(fxLoop,0);
                String timeIn = (String)frequencyListTable.getValueAt(fxLoop,1);
                freqToGen = Float.parseFloat(frequencyIn);
                timeToGen = Integer.parseInt(timeIn);
                if (line != null)
                {
                    line.stop();
                }
                
                initFrequencyGenerator(nWaveformType,freqToGen,fAmplitude);
                //freqPanel = new MTFrequencyPanel(freqToGen);
                freqPanel.changeFrequency(freqToGen);
                Calendar rightNow = Calendar.getInstance();
                java.util.Date dateTime = rightNow.getTime();
                long timeStart = dateTime.getTime();
                long timeToEnd = (timeToGen * 1000) + timeStart;
                while (true)
                {
                    Calendar rightTimeNow = Calendar.getInstance();
                    java.util.Date dateTimeNow = rightTimeNow.getTime();
                    long timeNow = dateTimeNow.getTime();
                    if (timeNow >= timeToEnd)
                    {
                        break;
                    }
                    float timeLeftInSecs = (timeToEnd - timeNow)/1000;
                    fxTimeLeft.setText(String.valueOf(timeLeftInSecs));
                    try
                    {
                        int	nRead = oscillator.read(abData);
                        int	nWritten = line.write(abData, 0, nRead);
                    }
                    catch (Exception exp)
                    {
                    }
                }
                float timeInMins = (float)timeToGen/(float)60;
                floatSetTime = floatSetTime  - timeInMins;
                String strSetTime = String.valueOf(floatSetTime);
                strSetTime = strSetTime.substring(0,strSetTime.indexOf(".")+2);
                setTimeLeft.setText(strSetTime);
                
                int currentFXRepeatCount = Integer.parseInt((String)fxSpinner.getValue());
                if (currentFXRepeatCount > 1)
                {
                    fxLoop--;
                    fxSpinner.setValue(String.valueOf(currentFXRepeatCount-1));
                }
                
            }
            this.startFreqPos = 0;
            fxFrequenciesLeft.setText("0");
            int setRemainderCount = Integer.parseInt((String)fxSetSpinner.getValue()) - 1;
            if (setRemainderCount == 0)
            {
                fxSetSpinner.setValue("1");
                
            } else
                fxSetSpinner.setValue(String.valueOf(setRemainderCount));
        }
        fxFrequenciesLeft.setText("0");
        fxLoop = 0;
        recalcFreqDuration(0);
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);
        nextFxButton.setEnabled(false);
        previousFxButton.setEnabled(false);
        sortSetDescButton.setEnabled(true);
        sortSetAscButton.setEnabled(true);
        removeDupsButton.setEnabled(true);
        adjustTimeButton.setEnabled(true);
        fxGenStatusLabel.setText(stoppedString);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == startButton)
        {
            if ((oscillatorThread != null) && (oscThreadSuspendedState == true))
            {
                oscillatorThread.resume();
                oscThreadSuspendedState = false;
            } else
            {
                Runnable oscRunThread = new MyRunnable();
                oscillatorThread = new Thread(oscRunThread);
                oscillatorThread.start();
            }
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            pauseButton.setEnabled(true);
            nextFxButton.setEnabled(true);
            previousFxButton.setEnabled(true);
            sortSetDescButton.setEnabled(false);
            sortSetAscButton.setEnabled(false);
            removeDupsButton.setEnabled(false);
            adjustTimeButton.setEnabled(false);
            
            fxGenStatusLabel.setText(runningString);
        } else
            if (e.getSource() == stopButton)
            {
                if ((oscillatorThread != null) && (oscThreadSuspendedState == true))
                {
                    oscillatorThread.resume();
                    oscThreadSuspendedState = false;
                }
                if (oscillatorThread != null)
                {
                    oscillatorThread.stop();
                    oscThreadSuspendedState = false;
                }
                freqPanel.changeFrequency("0.0");
                frequencyListTable.changeSelection(0,0,false,false);
                recalcFreqDuration(0);
                startFreqPos = 0;
                fxLoop = 0;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                pauseButton.setEnabled(false);
                nextFxButton.setEnabled(false);
                previousFxButton.setEnabled(false);
                sortSetDescButton.setEnabled(true);
                sortSetAscButton.setEnabled(true);
                removeDupsButton.setEnabled(true);
                adjustTimeButton.setEnabled(true);
                
                fxGenStatusLabel.setText(stoppedString);
            } else
                if (e.getSource() == pauseButton)
                {
                    if (oscThreadSuspendedState == false)
                    {
                        if (oscillatorThread != null)
                        {
                            oscillatorThread.suspend();
                            oscThreadSuspendedState = true;
                            pauseButton.setEnabled(false);
                            stopButton.setEnabled(false);
                            startButton.setEnabled(true);
                            fxGenStatusLabel.setText(pausedString);
                        }
                    }
                } else
                    if (e.getSource() == incrementButton)
                    {
                        int rowPos = frequencyListTable.getSelectedRow();
                        if (rowPos != -1)
                        {
                            int intValueAtCell = 0;
                            String valueAtCell = (String)dm.getValueAt(frequencyListTable.getSelectedRow(), frequencyListTable.getSelectedColumn());
                            if (valueAtCell.indexOf(".") > 0)
                            {
                                String intPart =  valueAtCell.substring(0,valueAtCell.indexOf("."));
                                String fracPart =  valueAtCell.substring(valueAtCell.indexOf(".")+1,valueAtCell.length());
                                intValueAtCell = Integer.parseInt(intPart);
                                intValueAtCell++;
                                dm.setValueAt(String.valueOf(intValueAtCell) + "." + fracPart, frequencyListTable.getSelectedRow(), frequencyListTable.getSelectedColumn());
                            } else
                            {
                                intValueAtCell = Integer.parseInt(valueAtCell);
                                intValueAtCell++;
                                dm.setValueAt(String.valueOf(intValueAtCell), frequencyListTable.getSelectedRow(), frequencyListTable.getSelectedColumn());
                            }
                            recalcFreqDuration(0);
                            freqChart.updateChart(frequencyListTable);
                        }
                        
                    } else
                        if (e.getSource() == decrementButton)
                        {
                            int rowPos = frequencyListTable.getSelectedRow();
                            if (rowPos != -1)
                            {
                                int intValueAtCell = 0;
                                String valueAtCell = (String)dm.getValueAt(frequencyListTable.getSelectedRow(), frequencyListTable.getSelectedColumn());
                                if (valueAtCell.indexOf(".") > 0)
                                {
                                    String intPart =  valueAtCell.substring(0,valueAtCell.indexOf("."));
                                    String fracPart =  valueAtCell.substring(valueAtCell.indexOf(".")+1,valueAtCell.length());
                                    intValueAtCell = Integer.parseInt(intPart);
                                    if (intValueAtCell > 1)
                                    {
                                        intValueAtCell--;
                                    }
                                    dm.setValueAt(String.valueOf(intValueAtCell) + "." + fracPart, frequencyListTable.getSelectedRow(), frequencyListTable.getSelectedColumn());
                                    
                                    
                                } else
                                {
                                    intValueAtCell = Integer.parseInt(valueAtCell);
                                    if (intValueAtCell > 1)
                                    {
                                        intValueAtCell--;
                                    }
                                    dm.setValueAt(String.valueOf(intValueAtCell), frequencyListTable.getSelectedRow(), frequencyListTable.getSelectedColumn());
                                }
                                recalcFreqDuration(0);
                                freqChart.updateChart(frequencyListTable);
                            }
                            
                        }  else
                            if (e.getSource() == deleteRowButton)
                            {
                                int rowPos = frequencyListTable.getSelectedRow();
                                if (rowPos != -1)
                                {
                                    dm.removeRow(rowPos);
                                    if (freqTableCount > 0)
                                    {
                                        freqTableCount--;
                                    }
                                    recalcFreqDuration(0);
                                    frequencyListTable.addNotify();
                                    freqChart.updateChart(frequencyListTable);
                                }
                            } else
                                if (e.getSource() == nextFxButton)
                                {
                                    if (fxLoop < rowCount-1)
                                    {
                                        startFreqPos =  fxLoop + 1;
                                        recalcFreqDuration(startFreqPos);
                                        if ((oscillatorThread != null) && (oscThreadSuspendedState == true))
                                        {
                                            oscillatorThread.resume();
                                            oscThreadSuspendedState = false;
                                        }
                                        if (oscillatorThread != null)
                                        {
                                            oscillatorThread.stop();
                                            oscThreadSuspendedState = false;
                                        }
                                        Runnable oscRunThread = new MyRunnable();
                                        oscillatorThread = new Thread(oscRunThread);
                                        oscillatorThread.start();
                                        startButton.setEnabled(false);
                                        stopButton.setEnabled(true);
                                        pauseButton.setEnabled(true);
                                        fxGenStatusLabel.setText(runningString);
                                    }
                                    
                                } else
                                    if (e.getSource() == previousFxButton)
                                    {
                                        
                                        if (fxLoop > 0)
                                        {
                                            startFreqPos =  fxLoop -1;
                                            recalcFreqDuration(startFreqPos);
                                            if ((oscillatorThread != null) && (oscThreadSuspendedState == true))
                                            {
                                                oscillatorThread.resume();
                                                oscThreadSuspendedState = false;
                                            }
                                            if (oscillatorThread != null)
                                            {
                                                oscillatorThread.stop();
                                                oscThreadSuspendedState = false;
                                            }
                                            Runnable oscRunThread = new MyRunnable();
                                            oscillatorThread = new Thread(oscRunThread);
                                            oscillatorThread.start();
                                            startButton.setEnabled(false);
                                            stopButton.setEnabled(true);
                                            pauseButton.setEnabled(true);
                                            fxGenStatusLabel.setText(runningString);
                                        }
                                    } else
                                        if (e.getSource() == applyChangesButton)
                                        {
                                            int rowPos = frequencyListTable.getSelectedRow();
                                            if (rowPos != -1)
                                            {
                                                Object[] newRow = new Object[2];
                                                String freqTime = (String)fxTimeSpinner.getValue();
                                                String freqIn  = manualFxField.getText();
                                                newRow[0] = freqIn;
                                                newRow[1] = freqTime;
                                                dm.insertRow(rowPos,newRow);
                                                freqTableCount++;
                                                recalcFreqDuration(0);
                                                freqChart.updateChart(frequencyListTable);
                                            }
                                        } else
                                            if (e.getSource() == sortSetAscButton)
                                            {
                                                ExpandedSet sortDataSet = new  ExpandedSet(dm);
                                                sortDataSet.sortSet();
                                                ArrayList testSet = sortDataSet.getFullSet();
                                                int rowLCount = 0;
                                                while(sortDataSet.hasNext)
                                                {
                                                    ArrayList rowData = sortDataSet.getNextItem();
                                                    String freqVal = (String)rowData.get(0);
                                                    String timeVal = (String)rowData.get(1);
                                                    dm.setValueAt(freqVal, rowLCount, 0);
                                                    dm.setValueAt(timeVal, rowLCount, 1);
                                                    rowLCount++;
                                                }
                                                frequencyListTable.addNotify();
                                                //TreeMap myTree = new TreeMap(freqSet);
                                                //Set freqTreeSet = myTree.keySet();
                                                //Iterator symptomTreeSetIterator = freqTreeSet.iterator();
                                                //while (symptomTreeSetIterator.hasNext())
                                                //{
                                                //   String freqVal = (String)symptomTreeSetIterator.next();
                                                //String timeVal =
                                                
                                                //}
                                                //SortedSet keySet = freqSet.keySet();
                                                //Iterator iter = keySet.iterator();
                                                //while (iter.hasNext())
                                                //{
                                                //     String theKey = (String)iter.next();
                                                //}
                                                
                                            } else
                                            if (e.getSource() == removeDupsButton)
                                            {
                                                ExpandedSet removeDupsDataSet = new  ExpandedSet(dm);
                                                removeDupsDataSet.removeDuplicates();
                                                ArrayList testSet = removeDupsDataSet.getFullSet();
                                                dm.setDataVector(removeDupsDataSet.getObjArray(),freqListHeading);
                                                freqChart.updateChart(frequencyListTable);
                                                frequencyListTable.addNotify();
                                                freqTableCount = dm.getRowCount(); 
                                                recalcFreqDuration(0);
                                                
                                            } else
                                            if (e.getSource() == adjustTimeButton)
                                            {
                                                ExpandedSet adjustTimeDataSet = new  ExpandedSet(dm);
                                                adjustTimeDataSet.adjustTime(3600);
                                                ArrayList testSet = adjustTimeDataSet.getFullSet();
                                                dm.setDataVector(adjustTimeDataSet.getObjArray(),freqListHeading);
                                                freqChart.updateChart(frequencyListTable);
                                                frequencyListTable.addNotify();
                                                freqTableCount = dm.getRowCount(); 
                                                recalcFreqDuration(0);
                                                
                                            } else
                                                if (e.getSource() == loadSetButton)
                                                {
                                                    freqFileChooser = new JFileChooser(InfoManager.TARGET_VOLUME + "\\Rife List\\");
                                                    freqFileChooser.addChoosableFileFilter(new FreqFileFilter());
                                                    
                                                    int returnVal = freqFileChooser.showDialog(this, "Load");
                                                    if (returnVal == JFileChooser.APPROVE_OPTION)
                                                    {
                                                        freqFile = freqFileChooser.getSelectedFile();
                                                        populateTable(freqFile);
                                                    }
                                                    freqTableCount = dm.getRowCount();
                                                    recalcFreqDuration(0);
                                                    freqChart.updateChart(frequencyListTable);
                                                    
                                                    
                                                } else
                                                if (e.getSource() == saveSetButton)
                                                {
                                                    freqFileChooser = new JFileChooser(InfoManager.TARGET_VOLUME + "\\Rife List\\");
                                                    freqFileChooser.addChoosableFileFilter(new FreqFileFilter());
                                                    
                                                    int returnVal = freqFileChooser.showSaveDialog(this);
                                                    if (returnVal == JFileChooser.APPROVE_OPTION)
                                                    {
                                                        File outFile = freqFileChooser.getSelectedFile();
                                                        saveFreqFile(outFile);
                                                    }
                                                }
                                                else
                                                    if (e.getSource() == sortSetDescButton)
                                                    {
                                                        ExpandedSet sortDataSet = new  ExpandedSet(dm);
                                                        sortDataSet.sortSetDesc();
                                                        ArrayList testSet = sortDataSet.getFullSet();
                                                        int rowLCount = 0;
                                                        while(sortDataSet.hasNext)
                                                        {
                                                            ArrayList rowData = sortDataSet.getNextItem();
                                                            String freqVal = (String)rowData.get(0);
                                                            String timeVal = (String)rowData.get(1);
                                                            dm.setValueAt(freqVal, rowLCount, 0);
                                                            dm.setValueAt(timeVal, rowLCount, 1);
                                                            rowLCount++;
                                                        }
                                                        frequencyListTable.addNotify();
                                                    } else
                                                        if (e.getSource() == sineButton)
                                                        {
                                                            nWaveformType = Oscillator.WAVEFORM_SINE;
                                                        } else
                                                            if (e.getSource() == squareButton)
                                                            {
                                                                nWaveformType = Oscillator.WAVEFORM_SQUARE;
                                                            } else
                                                                if (e.getSource() == triangleButton)
                                                                {
                                                                    nWaveformType = Oscillator.WAVEFORM_TRIANGLE;
                                                                } else
                                                                    if (e.getSource() == sawToothButton)
                                                                    {
                                                                        nWaveformType = Oscillator.WAVEFORM_SAWTOOTH;
                                                                    }
    }
    
    public void saveFreqFile(File freqFile)
    {
        try
        {
            String remString = "rem Rife Generator Script for Mother Teres@\r\n";
            String termString = " 0 0 100\r\n";
            String endString = "end\r\n";
            RandomAccessFile theFreqFile  = new RandomAccessFile(freqFile, "rw");
            theFreqFile.writeBytes(remString);
            for (int loop = 0; loop < dm.getRowCount(); loop++)
            {
                String freqStr = (String)dm.getValueAt(loop, 0); 
                String timeStr = (String)dm.getValueAt(loop, 1);
                String outString = "tone " + freqStr.trim() + " " + timeStr + termString;
                if ((freqStr != null) && (freqStr.length() > 0))
                {
                    theFreqFile.writeBytes(outString);
                }
            }
            theFreqFile.writeBytes(endString);
            theFreqFile.close();
            
        }
        catch (IOException e)
        {
            // if there is an error opening the file, return false
            System.out.println("IOException: " + e.getMessage() + ".!!");
        }
        
    }
    
    public void populateTable(File freqFile)
    {
        while (dm.getRowCount() > 0)
        {
            dm.removeRow(0);
        }
        String inFileString = null;
        try
        {
            RandomAccessFile theFreqFile  = new RandomAccessFile(freqFile, "r");
            while (true)
            {
                // try reading a line from the file
                inFileString = theFreqFile.readLine();
                if (inFileString == null)
                {
                    break;
                }
                StringTokenizer freqTokens = new StringTokenizer(inFileString," ");
                int tokenCount = freqTokens.countTokens();
                if (tokenCount > 2)
                {
                    String entryType = freqTokens.nextToken();
                    if (entryType.equalsIgnoreCase("TONE") == true)
                    {
                        String rtsFreq = freqTokens.nextToken();
                        String rtsTime = freqTokens.nextToken();
                        Object[] rowObject = new String[2];
                        rowObject[0] = rtsFreq;
                        rowObject[1] = rtsTime;
                        dm.addRow(rowObject);
                    }
                }
            }
        }
        catch (IOException e)
        {
            // if there is an error opening the file, return false
            System.out.println("IOException: " + e.getMessage() + ".!!");
        }
        //}
    }
    
    public class MyRunnable implements Runnable
    {
        public void run()
        {
            runFrequencySet(startFreqPos);
        }
    }
    
    public class SliderListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting())
            {
                int volumeUnadjusted = (int)source.getValue();
                float volumeAdjusted = (float)volumeUnadjusted / (float)100.0;
                fAmplitude = volumeAdjusted;
                if (fAmplitude > 0.99)
                {
                    fAmplitude = (float)0.99;
                }
            }
        }
    }
}
