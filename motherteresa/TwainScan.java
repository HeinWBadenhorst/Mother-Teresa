/*
 * TwainScan.java
 *
 * Created on March 20, 2003, 12:24 PM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst.
 */

import java.awt.*;
import java.io.*;
import java.awt.image.*;
import SK.gnome.twain.*;
import com.sun.image.codec.jpeg.*;

public class TwainScan
{

    java.awt.Image image;
    
    /** Creates a new instance of TwainScan */
    public TwainScan()
    {
    }


  
    public void scan() 
    {
        try
        {
            TwainSource source=TwainManager.getDefaultSource();
            source.setPixelType(TwainSource.TWPT_BW);
            source.setVisible(false);
            source.setFrame(0, 0, 10, 12);
            //Resolution is set in pixels per unit.
            source.setXResolution(72);
            source.setYResolution(72);
    
            image=Toolkit.getDefaultToolkit().createImage(source);
            MediaTracker tracker=new MediaTracker(new Frame());
            tracker.addImage(image, 0);
            try
            { 
                tracker.waitForAll();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            tracker.removeImage(image);
            System.err.println("getPixelType2="+source.getPixelType());
            System.err.println("getBitDepth2="+source.getBitDepth());
            TwainManager.close();
        }
        catch (TwainException e)
        {
            e.printStackTrace();
        }
    
    }
  
    /** 
        This method saves an image data to the jpeg file format. It uses 
        com.sun.image.codec.jpeg package.
    */
    public void save(String imagePath, String fileName) 
    { 
        try
        {
            File imageFile = new File(imagePath + fileName);
            BufferedImage bimg=new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            bimg.createGraphics().drawImage(image, 0, 0, null);
            FileOutputStream out=new FileOutputStream(imageFile);
            JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param=encoder.getDefaultJPEGEncodeParam(bimg);
            param.setQuality(1.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bimg);
            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
