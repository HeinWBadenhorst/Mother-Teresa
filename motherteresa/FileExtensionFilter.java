/*
 * FileExtensionFilter.java
 *
 * Created on March 19, 2003, 10:28 AM
 */
package motherteresa;

import java.io.*;
import java.util.*;


/**
 *
 * @author  administrator
 */
public class FileExtensionFilter extends javax.swing.filechooser.FileFilter
{
    private LinkedList extensions = new LinkedList();
    private String description;
    
    /** Creates a new instance of FileExtensionFilter */
    public FileExtensionFilter()
    {
    }
    
    public FileExtensionFilter(String extension)
    {
        add(extension);
    }
    
    public void add(String extension)
    {
        extensions.add(extension);
    }
    
    public boolean check(String extension, String name)
    {
        int length = name.length();
        if (length < extension.length())
        {
            return false;
        }
        String ext = name.substring(length - extension.length(), length);
        return ext.equals(extension);
    }
    
    public boolean accept(File file)
    {
        Iterator iter = extensions.iterator();
        boolean accept = false;
        while (iter.hasNext())
        {
            accept = accept | check((String)iter.next(),file.getName());
        }
        return accept;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
}
