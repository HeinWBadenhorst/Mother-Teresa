/*
 * FrequencyChart.java
 *
 * Created on 13 June 2005, 12:13
 */

package motherteresa;

// Import the Java classes
import java.util.*;
import java.text.*;
import java.io.*;

// Import the Swing classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Import the JFreeChart classes
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.*;
import org.jfree.data.general.*;
import org.jfree.data.time.*;
import org.jfree.ui.*;
//import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.data.xy.*;

/**
 *
 * @author  HWBadenhorst
 */
public class FrequencyChart extends JPanel{
    
    /** Creates a new instance of FrequencyChart */
    public FrequencyChart() 
    {
        super();
    }
    
    public FrequencyChart(ArrayList chartData) 
    {
        super();
        buildDataCollection(chartData);
        initChart();
    }
    
    private void buildDataCollection(ArrayList chartData)
    {
         XYSeries highFxSeries = new XYSeries("High");
         XYSeries lowFxSeries = new XYSeries("Low");
         String frequencyIn = null;
         String timeIn = null;
         
         for (int fxLoop = 0; fxLoop < chartData.size(); fxLoop++) 
         {
            HashMap freqMap = (HashMap)chartData.get(fxLoop);
            
            Set freqMapKeySet = freqMap.keySet();
            Iterator freqMapIter = freqMapKeySet.iterator();
            while (freqMapIter.hasNext()) 
            {
                frequencyIn = (String)freqMapIter.next();
                timeIn = (String)freqMap.get((Object)frequencyIn);
            }
            lowFxSeries.add(Double.parseDouble(frequencyIn) - 1.0,0.0);
            highFxSeries.add(Double.parseDouble(frequencyIn) - 1.0,0.0);
            lowFxSeries.add(Double.parseDouble(frequencyIn),0.0);
            highFxSeries.add(Double.parseDouble(frequencyIn),Double.parseDouble(timeIn));
            lowFxSeries.add(Double.parseDouble(frequencyIn) + 1.0,0.0);
            highFxSeries.add(Double.parseDouble(frequencyIn) + 1.0,0.0);
        }

        frequencySetCollection.addSeries(lowFxSeries);
        frequencySetCollection.addSeries(highFxSeries);

    }
    

   public void updateChart(JTable frequencyListTable)
   {
        int rowCount = frequencyListTable.getRowCount();
        String frequencyIn = null;
        String timeIn = null;
        XYSeries highFxSeries =  new XYSeries("High");
        XYSeries lowFxSeries =  new XYSeries("Low");
        
        for (int fxLoop = 0; fxLoop < rowCount; fxLoop++)
        {
            frequencyIn = (String)frequencyListTable.getValueAt(fxLoop,0);
            timeIn = (String)frequencyListTable.getValueAt(fxLoop,1);
            lowFxSeries.add(Double.parseDouble(frequencyIn) - 1.0,0.0);
            highFxSeries.add(Double.parseDouble(frequencyIn) - 1.0,0.0);
            lowFxSeries.add(Double.parseDouble(frequencyIn),0.0);
            highFxSeries.add(Double.parseDouble(frequencyIn),Double.parseDouble(timeIn));
            lowFxSeries.add(Double.parseDouble(frequencyIn) + 1.0,0.0);
            highFxSeries.add(Double.parseDouble(frequencyIn) + 1.0,0.0);
        }
        frequencySetCollection.removeAllSeries();
        frequencySetCollection.removeAllSeries();
        frequencySetCollection.addSeries(lowFxSeries);
        frequencySetCollection.addSeries(highFxSeries);
        removeAll();
        initChart();
   }
        
    
    public void initChart()
    {
      JFreeChart chart = ChartFactory.createTimeSeriesChart(
      "Frequency Chart",
      "Frequency", "Duration (Seconds)",
      frequencySetCollection,
      true, // legend
      true, // tool tips
      false // URLs
    );
    chart.setBackgroundPaint(Color.LIGHT_GRAY);
    chart.setBorderVisible(true);
    XYPlot plot = chart.getXYPlot();
    plot.setRenderer(new XYDifferenceRenderer( new Color( 0, 255, 0 ), new Color( 0, 255, 0 ), false));
    plot.setBackgroundPaint(Color.black);
    plot.setDomainGridlinePaint(Color.green);
    plot.setRangeGridlinePaint(Color.green);
    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
    ValueAxis domainAxis = new NumberAxis("Frequency");
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);
    plot.setDomainAxis(domainAxis);
    plot.setForegroundAlpha(0.9f);

    PointLayout chartLayout = new PointLayout();
    setLayout(chartLayout);

    add( new ChartPanel( chart ) );
    }
    
     private XYSeriesCollection frequencySetCollection = new XYSeriesCollection();
}
