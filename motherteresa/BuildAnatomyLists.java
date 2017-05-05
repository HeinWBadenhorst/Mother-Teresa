/*
 * BuildAnatomyLists.java
 *
 * Created on 26 July 2004, 12:23
 */

package motherteresa;

import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import java.awt.event.*;
import java.util.Set;
import java.util.Iterator;
import java.text.Format;

/**
 *
 * @author  HBadenhorst
 */
public class BuildAnatomyLists {
    RandomAccessFile anatomyFile;
    String subString = null;
    StringBuffer inStringBuffer = null;  
    
    
    /** Creates a new instance of BuildAnatomyLists */
    public BuildAnatomyLists() 
    {
        buildAnatomyFiles();
    }


    public boolean closeAnatomyFile()
    {
        try
        {
            anatomyFile.close();
        }
        catch (IOException e)
        {
            // if we can't write to the file, return false
            return false;
        }
        return true;
    }
    
    
    public boolean openAnatomyFile(String filename)
    {
        
        String  filePath = InfoManager.TARGET_VOLUME + "\\tmp\\";
        try
        {
            anatomyFile = new RandomAccessFile(filePath + filename, "rw");
        }
        catch (IOException e)
        {
            // if there was an error opening or writing to the file, return false
            return false;
        }
        return true;
        
    }

    public class ExtensionFilter implements FilenameFilter
    {
        private final String ext;
        
        public ExtensionFilter(String extension)
        {
            ext = extension;
        }
        
        public boolean accept(File dir, String name)
        {
            return name.endsWith("."+ext);
        }
    }
    
    public boolean buildAnatomyFiles()
    {
        File anatomyDir = new File(InfoManager.TARGET_VOLUME + "\\tmp\\");
        File anatomyFiles[] = anatomyDir.listFiles(new ExtensionFilter("html"));
        RandomAccessFile file = null;
        String fileString = null;
        
        int numFiles = anatomyFiles.length;
        inStringBuffer = null;
        ArrayList wordList = new ArrayList(100);
        for(int loop = 0; loop < numFiles; loop++)
        {
            //anatomyNumber++;
            System.out.println("Starting with file: " + anatomyFiles[loop].getName() + "!!");
            if (anatomyFiles[loop].getName().equalsIgnoreCase("49.html") == true)
            {
                int r = 1;
            }
            try
            {
                file = new RandomAccessFile(anatomyFiles[loop], "r");
                wordList.clear();
                while (true)
                {
                    // try reading a line from the file
                    fileString = file.readLine();
                    if (fileString == null)
                    {
                        break;
                    }
                    inStringBuffer = new StringBuffer(fileString);
                    while (inStringBuffer.indexOf("<B>") > -1)
                    {
                        int start = inStringBuffer.indexOf("<B>") + 3;
                        int end = inStringBuffer.indexOf("</B>");
                        if (end > start)
                        {
                            subString = inStringBuffer.substring(start,end);
                            inStringBuffer.delete(inStringBuffer.indexOf("<B>"),inStringBuffer.indexOf("</B>")+4);
                            if ((subString.indexOf("&nbsp;&nbsp;&nbsp;") == -1) && (subString.trim().length() > 2))
                            {   
                                if (subString.indexOf("<A") > -1)
                                {
                                    StringBuffer theBuffer = new StringBuffer(subString);
                                    if (subString.indexOf("</A>") > -1)
                                    {
                                        theBuffer.delete(theBuffer.indexOf("<A"),theBuffer.indexOf("</A>")+4);
                                    } else
                                    {
                                        theBuffer.delete(theBuffer.indexOf("<A"),theBuffer.length());
                                    }
                                    subString = theBuffer.toString();
                                }
                                subString = subString.trim() + "\n"; 
                                if ((subString != null) && (subString.length() > 1))
                                {
                                    subString = subString.substring(0,1).toUpperCase() + subString.substring(1,subString.length()).toLowerCase();
                                    wordList.add((Object)subString);
                                }
                            }
                        } else
                        {
                           if (end != -1)
                           {
                              inStringBuffer.delete(inStringBuffer.indexOf("</B>"),inStringBuffer.indexOf("</B>")+4);
                           }
                        }
                            
                    }
                    while (inStringBuffer.indexOf("<I>") > -1)
                    {
                        subString = inStringBuffer.substring(inStringBuffer.indexOf("<I>") + 3,inStringBuffer.indexOf("</I>"));
                        inStringBuffer.delete(inStringBuffer.indexOf("<I>"),inStringBuffer.indexOf("</I>")+4);
                            if ((subString.indexOf("&nbsp;&nbsp;&nbsp;") == -1) && (subString.trim().length() > 2))
                            {   
                                if (subString.indexOf("<A") > -1)
                                {
                                    StringBuffer theBuffer = new StringBuffer(subString);
                                    if (subString.indexOf("</A>") > -1)
                                    {
                                        theBuffer.delete(theBuffer.indexOf("<A"),theBuffer.indexOf("</A>")+4);
                                    } else
                                    {
                                        theBuffer.delete(theBuffer.indexOf("<A"),theBuffer.length());
                                    }
                                    subString = theBuffer.toString();
                                }
                                subString = subString.trim() + "\n"; 
                                if ((subString != null) && (subString.length() > 1))
                                {
                                    subString = subString.substring(0,1).toUpperCase() + subString.substring(1,subString.length()).toLowerCase();
                                    wordList.add((Object)subString);
                                }
                            }
                    }
                }     
                String fileName = anatomyFiles[loop].getName();
                fileName = fileName.substring(0,fileName.indexOf(".")) + ".txt";
                openAnatomyFile(fileName);
                for (int lpIndex = 0; lpIndex < wordList.size(); lpIndex++)
                {
                    String tmpData = (String)wordList.get(lpIndex);
                    anatomyFile.write(tmpData.getBytes());
                }
                closeAnatomyFile();
            }
            catch (IOException e)
            {
                // if there is an error opening the file, return false
                System.out.println("IOException: " + e.getMessage() + ".!!");
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args)
    {
        new BuildAnatomyLists();
    }

    
}
