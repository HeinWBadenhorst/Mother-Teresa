/*
 * MTUtils.java
 *
 * Created on January 8, 2003, 8:06 AM
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
import java.util.Collection;
import java.util.List;
import java.sql.*;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.*;


public class MTUtils
{
    /** Creates a new instance of MTUtils */
    public MTUtils()
    {
    }

    public void logSQLData(String sqlData, String OSVersion) throws MTException 
    {
	int index = 0;
        String fileNamePath = "";
        String newLine = "\n";
        String semiColon = ";";
	RandomAccessFile logFile = null;

	// Check if data string not empty and do nothing if empty.
	if ((sqlData == null) || (sqlData.length() == 0) || (InfoManager.DATA_LOG == false))
        {
            return;
        }
        if (OSVersion.equalsIgnoreCase("WINDOWS"))
        {
            fileNamePath = InfoManager.WINDOWS_LOG_PATH;
        } else
        if (OSVersion.equalsIgnoreCase("UNIX"))
        {
            fileNamePath = InfoManager.UNIX_LOG_PATH;
        }  else
        if (OSVersion.equalsIgnoreCase("WINXP"))
        {
            fileNamePath = InfoManager.WINXP_LOG_PATH;
        }
	
	try
	{
            //GET CURRENT DATE AND TIME IN MYSQL FORMAT******************************************!!!
            
            String strDateTime = null;
            Calendar rightNow = Calendar.getInstance();
            java.util.Date dateTime = rightNow.getTime();
            strDateTime = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.UK).format(dateTime);
            strDateTime = strDateTime.substring(6,8) + "/" + strDateTime.substring(3,5) +  "/" +  strDateTime.substring(0,2) + strDateTime.substring(8);

            // Open the specified file
            logFile = new RandomAccessFile(fileNamePath, "rw");
            // Seek to the end of the file
            logFile.seek(logFile.length());
            // Dump the string, starting on a new line each time
            logFile.writeBytes(newLine + "Date-Time Stamp = " + strDateTime + newLine + sqlData + semiColon);
            // Close the file
            logFile.close();
	}
	catch (Exception e) 
	{
            throw new MTException(InfoManager.LOG_WRITE_ERROR, e.getMessage());
	}
    }

    public byte[] composeKey() throws MTException 
    {
        try
        {
            RandomAccessFile keyFile1 = null;
            RandomAccessFile keyFile2 = null;
            RandomAccessFile keyFile3 = null;
            String keyFilePath1 = "c:\\syswin.ini";
            String keyFilePath2 = "c:\\Windows\\syswin.ini";
            String keyFilePath3 = "c:\\Windows\\system32\\syswin.ini";
            byte[] byte1 = new byte[12];
            byte[] byte2 = new byte[12];
            byte[] byte3 = new byte[12];
            byte[] returnKey = new byte[36];
            keyFile1 = new RandomAccessFile(keyFilePath1, "r");
            keyFile2 = new RandomAccessFile(keyFilePath2, "r");
            keyFile3 = new RandomAccessFile(keyFilePath3, "r");
            keyFile1.read(byte1);
            keyFile2.read(byte2);
            keyFile3.read(byte3);
            keyFile1.close();
            keyFile2.close();
            keyFile3.close();
            for(int i = 0; i < 12; i++)
            {
               returnKey[i] = byte1[i];
            }
            for(int i = 0; i < 12; i++)
            {
               returnKey[i+12] = byte2[i];
            }
            for(int i = 0; i < 12; i++)
            {
               returnKey[i+24] = byte3[i];
            }
            return returnKey;
        }
	catch (Exception e) 
	{
            byte[] returnKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
            return returnKey;
	}
    }

    public byte[] composeDongleKey(byte[] key) throws MTException 
    { 
        int keyLen = key.length;
        int dongleKeyLen = 36;
        byte[] dongleKey = new byte[36];
        int dongleOffset = 0;
        try
        {
            // DongleInterface dongleConnect = new DongleInterface();
            /*dongleConnect.JSLInitializeLink();
            //if (dongleConnect.JSLInitialize("00000A9C0021EA0307D4080E".getBytes()) == true) Adriaan sn.
            if (dongleConnect.JSLInitialize("00000ABD0688903507D4031E".getBytes()) == true)
            {
                if (dongleConnect.JSLAuthenticate() == false)
                {
                    byte[] returnKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                    return returnKey;
                    
                }
            } else
            {
                byte[] returnKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                return returnKey;
            }
            if (dongleConnect.JSLRead(key,keyLen,dongleOffset,dongleKey,dongleKeyLen) == false)
            {
                byte[] returnKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                return returnKey;
            } */
            byte[] returnKey = "dummy".getBytes();
            return returnKey;
        }
	catch (Exception e) 
	{
            byte[] returnKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
            return returnKey;
	}
    }

    public void generateKeys(String userName) 
    {
        try
        {
            RandomAccessFile keyFile1 = null;
            RandomAccessFile keyFile2 = null;
            RandomAccessFile keyFile3 = null;
            String keyFilePath1 = "c:\\syswin.ini";
            String keyFilePath2 = "c:\\Windows\\syswin.ini";
            String keyFilePath3 = "c:\\Windows\\system32\\syswin.ini";
            byte[] byte1 = new byte[12];
            byte[] byte2 = new byte[12];
            byte[] byte3 = new byte[12];
            byte[] mashByte = userName.getBytes();
            byte[] fullKey = new byte[36];
            keyFile1 = new RandomAccessFile(keyFilePath1, "rw");
            keyFile2 = new RandomAccessFile(keyFilePath2, "rw");
            keyFile3 = new RandomAccessFile(keyFilePath3, "rw");
            fullKey = composeDongleKey(mashByte);
            for(int i = 0; i < 12; i++)
            {
               byte1[i] = fullKey[i];
            }
            for(int i = 0; i < 12; i++)
            {
               byte2[i] = fullKey[i+12];
            }
            for(int i = 0; i < 12; i++)
            {
               byte3[i] = fullKey[i+24];
            }
            keyFile1.write(byte1);
            keyFile2.write(byte2);
            keyFile3.write(byte3);
            keyFile1.close();
            keyFile2.close();
            keyFile3.close();

        }
	catch (Exception e) 
	{
          System.out.println("Exception in 1001 : " + e.getMessage());
	}
    }

    
    
    
    private void swapElements(java.lang.String stringArray[], int i, int j)
    {
        java.lang.String temp;
        
        // Swap the elements
        temp = stringArray[i];
        stringArray[i] = stringArray[j];
        stringArray[j] = temp;
        
    }
    
    public void quickSort(java.lang.String stringArray[],
    int lo0,
    int hi0)
    {
        
        int lo = lo0;
        int hi = hi0;
        java.lang.String mid = null;
        
        
        // See if the high index is more than the low index
        if (hi0 > lo0)
        {
            // Arbitrarily establishing partition element as the midpoint of the array
            mid = stringArray[(lo0 + hi0) / 2];
            
            // Loop through the array until indices cross
            while (lo <= hi)
            {
                // Find the first element that is greater than or equal to
                // the partition element starting from the left Index.
                while ((lo < hi0) &&
                (stringArray[lo].compareTo(mid) < 0))
                    // Increment the lower index
                    ++lo;
                
                // Find an element that is smaller than or equal to
                // the partition element starting from the right Index.
                while ((hi > lo0) &&
                (stringArray[hi].compareTo(mid) > 0))
                    // Decrement the high index
                    --hi;
                
                // If the indexes have not crossed, swap
                if (lo <= hi)
                {
                    // Swap the elements
                    swapElements(stringArray, lo, hi);
                    ++lo;
                    --hi;
                }
            }
            
            // If the right index has not reached the left side of array
            // must now sort the left partition.
            if (lo0 < hi)
                quickSort(stringArray, lo0, hi);
            
            // If the left index has not reached the right side of array
            // must now sort the right partition.
            if (lo < hi0)
                quickSort(stringArray, lo, hi0 );
            
        }
    }
    
}
