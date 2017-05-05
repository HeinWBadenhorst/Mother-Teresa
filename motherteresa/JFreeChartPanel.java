/*
 * JFreeChartPanel.java
 *
 * Created on 02 June 2005, 09:37
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
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.data.xy.*;

/**
 *
 * @author  HWBadenhorst
 */
public class JFreeChartPanel  {
    
    /** Creates a new instance of JFreeChartPanel */
    public JFreeChartPanel(String sourceFile) 
    {
       StockHistoryChart shc = new StockHistoryChart(sourceFile);
       JFrame frame = new JFrame( "Frequency Spectrum Chart for " + shc.getSymbol() );
       frame.getContentPane().add( shc, BorderLayout.CENTER );
       frame.setSize( 640, 480 );
       frame.setVisible( true );
       frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }
    

public class StockHistoryChart extends JPanel
{
  // Holds the data
  private TimeSeriesCollection datasetHighLow = new TimeSeriesCollection();
  private XYSeriesCollection datasetCollection = new XYSeriesCollection();

  // Create a chart
  private JFreeChart chart;

  // Create a panels that can show our chart
  private ChartPanel panel;

  private String stockSymbol;

  public StockHistoryChart( String filename )
  {
    try
    {
      // Get Stock Symbol
      this.stockSymbol = filename.substring( 0, filename.indexOf( '.' ) );

      // Create time series
      ///TimeSeries high = new TimeSeries( "High", Day.class );
      ///TimeSeries low = new TimeSeries( "Low", Day.class );
      TimeSeries high = new TimeSeries( "High", Day.class );
      TimeSeries low = new TimeSeries( "Low", Day.class );
      XYSeries highFxSeries = new XYSeries("High");
      highFxSeries.add(10.0,10.0);
      highFxSeries.add(11.0,20.0);
      highFxSeries.add(12.0,30.0);
      highFxSeries.add(13.0,40.0);
      highFxSeries.add(14.0,50.0);
      highFxSeries.add(15.0,60.0);
      highFxSeries.add(16.0,70.0);
      highFxSeries.add(17.0,80.0);
      
      XYSeries lowFxSeries = new XYSeries("Low");
      lowFxSeries.add(10.0,5.0);
      lowFxSeries.add(11.0,15.0);
      lowFxSeries.add(12.0,25.0);
      lowFxSeries.add(13.0,35.0);
      lowFxSeries.add(14.0,45.0);
      lowFxSeries.add(15.0,55.0);
      lowFxSeries.add(16.0,65.0);
      lowFxSeries.add(17.0,75.0);
      datasetCollection.addSeries(lowFxSeries);
      datasetCollection.addSeries(highFxSeries);

      BufferedReader br = new BufferedReader( new FileReader( filename ) );
      String key = br.readLine();
      System.out.println( "Key: " + key );
      ArrayList dates = new ArrayList();
      String line = br.readLine();
      
      
      int x = 1;
      while( line != null &&
          !line.startsWith( "<!--" ) )
      {
        StringTokenizer st = new StringTokenizer( line, ",", false );
        Day day = getDay( st.nextToken() );
        double openValue = Double.parseDouble( st.nextToken() );
        double highValue = Double.parseDouble( st.nextToken() );
        double lowValue = Double.parseDouble( st.nextToken() );
        double closeValue = Double.parseDouble( st.nextToken() );
        double volumeValue = Double.parseDouble( st.nextToken() );

        // Add this value to our series'
        
        //Integer.toString(x+100)); 

        high.add(day, highValue );
        low.add( day, lowValue );

        // Copy Day to Date
        java.util.Date date = new java.util.Date( day.getFirstMillisecond() );
        
        dates.add( (Object)Integer.toString(x+100)); //date

        // Read the next day
        line = br.readLine();
      }
      
      // Build the datasets
      datasetHighLow.addSeries( high );
      datasetHighLow.addSeries( low );

      JFreeChart highLowDifChart = buildDifferenceChart( datasetCollection, "High/Low Difference Chart" );

      // Create this panel
      this.setLayout( new GridLayout( 2, 3 ) );
      this.add( new ChartPanel( highLowDifChart ) );
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
  }

  private void copyTimeSeriesToArray( double[] arr, TimeSeries series )
  {
    for( int i=0; i<series.getItemCount(); i++ ) 
    {
      Number num = series.getValue( i );
      arr[ i ] = num.doubleValue();
    }
  }


  private JFreeChart buildDifferenceChart( XYSeriesCollection dataset, String title )
  {
    // Create the chart
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
      title,
      "Frequency", "Level",
      dataset,
      true, // legend
      true, // tool tips
      false // URLs
    );
    chart.setBackgroundPaint(Color.white);
    
    org.jfree.chart.plot.XYPlot plot = chart.getXYPlot();
    plot.setRenderer(new XYDifferenceRenderer( new Color( 0, 255, 0 ), new Color( 0, 255, 0 ), false));
    plot.setBackgroundPaint(Color.black);
    plot.setDomainGridlinePaint(Color.green);
    plot.setRangeGridlinePaint(Color.green);
    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
    
    //ValueAxis domainAxis = new DateAxis("Frequency");
    ValueAxis domainAxis = new NumberAxis("Frequency");
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);
    plot.setDomainAxis(domainAxis);
    plot.setForegroundAlpha(0.9f);

    return chart;

  }
  
  private JFreeChart buildStackedAreaChart( DefaultTableXYDataset dataset, String title )
  {
    // Create the chart
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
      title,
      "Frequency", "Level",
      dataset,
      true, // legend
      true, // tool tips
      false // URLs
    );
    chart.setBackgroundPaint(Color.white);
    
    XYPlot plot = chart.getXYPlot();
    plot.setRenderer(new XYDifferenceRenderer( new Color( 0, 255, 0 ), new Color( 0, 255, 0 ), false));
    plot.setBackgroundPaint(Color.black);
    plot.setDomainGridlinePaint(Color.green);
    plot.setRangeGridlinePaint(Color.green);
    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
    
    ValueAxis domainAxis = new DateAxis("Frequency");
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);
    plot.setDomainAxis(domainAxis);
    plot.setForegroundAlpha(0.9f);

    return chart;

  }

  protected Day getDay( String date )
  {
    try
    {
      StringTokenizer st = new StringTokenizer( date, "-", false );

      // Get the day
      int day = Integer.parseInt( st.nextToken() );

      // Get the month
      String monthStr = st.nextToken();
      int month = -1;
      if( monthStr.equalsIgnoreCase( "Jan" ) ) month = 1;
      else if( monthStr.equalsIgnoreCase( "Feb" ) ) month = 2;
      else if( monthStr.equalsIgnoreCase( "Mar" ) ) month = 3;
      else if( monthStr.equalsIgnoreCase( "Apr" ) ) month = 4;
      else if( monthStr.equalsIgnoreCase( "May" ) ) month = 5;
      else if( monthStr.equalsIgnoreCase( "Jun" ) ) month = 6;
      else if( monthStr.equalsIgnoreCase( "Jul" ) ) month = 7;
      else if( monthStr.equalsIgnoreCase( "Aug" ) ) month = 8; 
      else if( monthStr.equalsIgnoreCase( "Sep" ) ) month = 9; 
      else if( monthStr.equalsIgnoreCase( "Oct" ) ) month = 10; 
      else if( monthStr.equalsIgnoreCase( "Nov" ) ) month = 11; 
      else if( monthStr.equalsIgnoreCase( "Dec" ) ) month = 12; 

      // Get the year
      int year = Integer.parseInt( st.nextToken() );
      if( year >=0 && year <= 10 )
      {
        year += 2000;
      }
      else
      {
        year += 1900;
      }

      // Build a new Day
      return new Day( day, month, year );
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }
    return null;
  }

  public String getSymbol()
  {
    return this.stockSymbol;
  }

}



  public static void main( String[] args )
  {
    //if( args.length < 1 )
    //{
    //  System.out.println( "Usage: StockHistoryChart filename.csv" );
    //  System.exit( 0 );
    //}
    new JFreeChartPanel(InfoManager.TARGET_VOLUME + "\\test.csv");

  }


}
