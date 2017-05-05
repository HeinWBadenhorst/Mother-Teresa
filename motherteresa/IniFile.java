package motherteresa;
/*
Research Project:   Graphical Database for Category Theory
                    J. Bradbury, Dr. R. Rosebrugh, I. Rutherford
                    Mount Allison University 2001
File:               IniFile.java
                    (written by: Ian Rutherford)
Description:
*/

import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.awt.*;

public class IniFile
{
	protected int numnodes = 0, maxnodes = 50, cursor = 0;
	protected IniFileNode[] nodes = new IniFileNode[maxnodes];

	public IniFile()
	{
		int i;
		for (i=0; i<maxnodes; i++)
			nodes[i] = new IniFileNode();
	}

	public boolean addHeader(String name)
    {
		int i;

		// check for bad input
        if (name.length() == 0)
        	return false;

        // allocate a larger array if needed
    	if (numnodes >= maxnodes)
		{
			IniFileNode[] temp = new IniFileNode[maxnodes+50];
			for (i=0; i<maxnodes; i++)
				temp[i] = nodes[i];
			for (i=maxnodes; i<maxnodes+50; i++)
				temp[i] = new IniFileNode();
			nodes = temp;
			maxnodes += 50;
		}

		// make sure the name is alphanumeric
        for (i=0; i<name.length(); i++)
        {
        	if (!((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') || (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') || (name.charAt(i) >= '0' && name.charAt(i) <= '9') || name.charAt(i) == ' '))
            	i = name.length() + 1;
        }
        if (i == name.length() + 1)
        	return false;

        // now add the header to the list
        if (numnodes == 0)
        {
			nodes[numnodes].title = name;
        }
        else
        {
        	// make sure it doesn't already exist
	    	for (i=0; i<numnodes; i++)
			if (nodes[i].title.compareTo(name) == 0)
            {
            	cursor = i;
				return false;
            }

			nodes[numnodes].title = name;
        }

		cursor = numnodes;
		numnodes++;
        return true;
    }

    public boolean removeHeader(String name)
    {
    	int i;

        // search for the header
        for (i=0; i<numnodes; i++)
        {
        	// if found, replace it with the last header in the list, then erase the last header
            // this means that the list of headers is no longer in the order that the user placed
            // it in originally, but for an .ini file this is irrelevent
        	if (nodes[i].title.compareTo(name) == 0)
            {
            	numnodes--;
                nodes[i] = nodes[numnodes];
                nodes[numnodes].erase();
            	return true;
            }
        }
        // the header was not found so return false
        return false;
    }

    public boolean removeHeader(int pos)
    {
    	// check for invalid input
    	if (pos < 0 || pos >= numnodes)
        	return false;

        // remove the item
        if (pos == numnodes-1)
        {
            numnodes--;
        	nodes[numnodes].erase();
        }
        else
        {
        	numnodes--;
        	nodes[pos] = nodes[numnodes];
            nodes[numnodes].erase();
		}
		return true;
	}

	public void eraseAll()
    {
		int i;

        // erase all the headers and reset the cursor
		for (i=0; i<numnodes; i++)
			nodes[i].erase();
		numnodes = 0;
        cursor = 0;
	}

	public boolean setHeader(String name)
	{
		int i;

        // set the cursor position to the requested header
        for (i=0; i<numnodes; i++)
        {
        	if (nodes[i].title.compareTo(name) == 0)
            {
            	cursor = i;
                return true;
            }
        }
        return false;
	}

	public boolean setHeader(int pos)
	{
    	// check for invalid input
	   	if (pos < 0 || pos >= numnodes)
			return false;

        // set the cursor appripriately
		cursor = pos;
        return true;
	}

	public String getHeader()
	{
    	return(nodes[cursor].title);
	}

	public boolean addItem(String name, String value)
    {
    	// check for invalid input
    	if (numnodes == 0)
        	return false;

        // return the value given by the addItem() function in the IniFileNode class
		return (nodes[cursor].addItem(name, value));
	}

	public boolean changeItem(String name, String value)
	{
		// same as above
		if (numnodes == 0)
			return false;

		//If node can not be changed add the item
		if (!nodes[cursor].changeItem(name, value))
			return(addItem(name, value));
		return(false);
	}

	public boolean changeItem(int pos, String data)
	{
		// same as above
		if (numnodes == 0)
			return false;

		//If node can not be changed add the item
		return(nodes[cursor].changeItem(pos, data));
	}

	public String getItem(int pos)
	{
    	// same as above
    	if (numnodes == 0)
        	return new String();
        return (nodes[cursor].getItem(pos));
	}

	public String getItem(String value)
	{
    	// same as above
    	if (numnodes == 0)
        	return new String();

        return (nodes[cursor].getItem(value));
	}

	public boolean removeItem(String value)
	{
    	// same as above
    	if (numnodes == 0)
        	return false;
        return (nodes[cursor].removeItem(value));
	}

	public boolean removeItem(int pos)
	{
    	// same as above
    	if (numnodes == 0)
        	return false;
        return (nodes[cursor].removeItem(pos));
	}

    public boolean writeIni(String filename)
    {
		int i, j;
		String data = new String();
		RandomAccessFile file;

		// delete the old file by opening it and writing a single byte to it
        try
        {
	        FileOutputStream deletefile = new FileOutputStream(filename);
	        deletefile.write(0);
	        deletefile.close();
        }
        catch (IOException e)
        {
        	// if there was an error opening or writing to the file, return false
        	return false;
        }

        // try to open the file
		try
		{
			file = new RandomAccessFile(filename, "rw");
		}
		catch (IOException e)
		{
        	// if we can't open the file, return false
			return false;
		}

        // make a large string containing all the .ini file data in printable format
		for (i=0; i<numnodes; i++)
		{
			if (nodes[i].numItems() != 0)
			{
            	// add the header with square brackets around it
				data += "[" + nodes[i].title + "]\n";

                // add all the items to the list
				for (j=0; j<nodes[i].numItems(); j++)
				{
					data += nodes[i].getItemName(j) + " = " + nodes[i].getItem(j) + "\n";
				}
                // put a blank line before the next header
				data += "\n";
			}
		}

		// try to write all the data to the file in one shot
		try
		{
			file.write(data.getBytes());
			file.close();
		}
		catch (IOException e)
		{
        	// if we can't write to the file, return false
			return false;
		}
        // operation successful; return true
        return true;
    }

	public boolean readIni(String filename)
	{
		String temp;
		int j = 0, m;
		RandomAccessFile file;
        // erase all data in memory before loading the new file
		eraseAll();

		// try to open the file for input
		try
		{
			file = new RandomAccessFile(filename, "r");
		}
		catch (IOException e)
		{
        	// if there is an error opening the file, return false
			return false;
		}

	    while (true)
		{
        	// try reading a line from the file
			try
			{
				temp = file.readLine();
			}
			catch (IOException e)
			{
            	// if there is an error reading any more data, then we're done
				return false;
			}
            // if we're at the end of the file (when temp is null), then we're done
			if (temp == null)
				return true;

      		// if the line is blank, do nothing. The statement "j = j" is just a dummy placeholder
   			if (temp.length() == 1 || temp.length() == 0)
				j = j;
            // if the line is a header surrounded by square brackets, then add it to the list
			else if (temp.charAt(0) == '[' && temp.charAt(temp.length()-1) == ']')
			{
				addHeader(temp.substring(1, temp.length()-1));
			}

            // if there is one square bracket but not another, then the line is invalid and
            // therefore the file itself is an invalid .ini file
			else if (temp.charAt(0) == '[')
			{
				eraseAll();
				return false;
			}

            // otherwise, check to see if the line is an item to be added to the list
			else
			{
            	// find the equals character ('='), at position j
				j = 0;
				while (j < temp.length() && temp.charAt(j) != '=')
					j++;
                // if the first or last character is a '=' then the line is invalid
				if (j == 0 || j >= temp.length())
                {
                	eraseAll();
					return false;
                }
				m = j;

				// the next section removes whitespace between the '=' and the data
				j++;
				m--;

                // find the beginning of the text to the right of the '='
				while (j < temp.length() && temp.charAt(j) == ' ')
					j++;

                // find the end of the text to the left of the '='
				while (m >= 0 && temp.charAt(m) == ' ')
					m--;

                // if the left side is all whitespace, then the line is invalid
				if (m < 0)
			    {
			    	eraseAll();
					return false;
				}

		        String rhs = temp.substring(j, temp.length());
		        //Remove carriage returns and line feeds from the ini data
		        for (int i=0; i < rhs.length(); i++)
		        {
		          if ((rhs.charAt(i) == '\n') || (rhs.charAt(i) == '\r'))
		          {
		            if (i+1 >= rhs.length())
		              rhs = rhs.substring(0, i);
		            else
		              rhs = rhs.substring(0, i) + rhs.substring(i+1);
		          }
        		}

                // now we have a valid item, so add it to the list
                String lhs = temp.substring(0,m+1);
				addItem(lhs, rhs);
	  		}
	    }
	}

    public String getData()
    {
    	// identical to the writeIni() function, but returns a string containing the
        // formatted .ini file data
		int i, j;
		String data = new String();
		for (i=0; i<numnodes; i++)
		{
			if (nodes[i].numItems() != 0)
			{
				data += "[" + nodes[i].title + "]\n";
				for (j=0; j<nodes[i].numItems(); j++)
				{
					data += nodes[i].getItemName(j) + " = " + nodes[i].getItem(j) + "\n";
				}
				data += "\n";
			}
		}
        return data;
    }
}
