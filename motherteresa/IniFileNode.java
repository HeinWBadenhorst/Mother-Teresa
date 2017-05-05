package motherteresa;
/*
Research Project:   Graphical Database for Category Theory
                    J. Bradbury, Dr. R. Rosebrugh, I. Rutherford
                    Mount Allison University 2001
File:               IniFileNode.java
                    (written by: Ian Rutherford)
Description:
*/

public class IniFileNode
{
    protected int numitems = 0, maxitems = 50;
	public String title = new String("");
	protected String names[] = new String[maxitems];
	protected String values[] = new String[maxitems];

        
        
        
    public int numItems()
    {
    	return numitems;
    }

	public boolean addItem(String name, String value)
    {
    	int i;
        if (name.length() == 0)
        	return false;

        // allocate some more memory if needed
		if (numitems >= maxitems)
		{
			String temp1[] = new String[maxitems+50];
			String temp2[] = new String[maxitems+50];
			for (i=0; i<maxitems; i++)
			{
				temp1[i] = names[i];
				temp2[i] = values[i];
			}
			for (i=maxitems; i<maxitems+50; i++)
			{
				temp1[i] = new String();
				temp2[i] = new String();
			}
			names = temp1;
			values = temp2;
		}

		// make sure the item doesn't already exist
        for (i=0; i<numitems; i++)
        	if (names[i].compareTo(name) == 0)
            	return changeItem(name, value);

		// add the item at the end of the array
		names[numitems] = name;
        values[numitems] = value;
        numitems++;
        return true;
    }

    public String getItem(String name)
    {
    	int i = 0;

        // find the position of this item
		while (i < numitems && names[i].compareTo(name) != 0)
        	i++;

        // if the item doesn't exist, return an empty string
        if (i == numitems)
        	return new String();
        return values[i];
    }

    public String getItem(int pos)
    {
    	// if input is invalid, return an empty string
    	if (pos < 0 || pos >= numitems)
            return new String();
        return values[pos];
    }

    public boolean changeItem(String name, String value)
    {
    	int i = 0;

        // find the item position
		while (i < numitems && names[i].compareTo(name) != 0)
        	i++;

        if (i == numitems)
			return false;
		values[i] = value;
		return true;
    }

    public boolean changeItem(int pos, String value)
    {
    	if (pos < 0 || pos >= numitems)
			return false;
		values[pos] = value;
		return true;
    }

	public String getItemName(int pos)
    {
    	if (pos < 0 || pos >= numitems)
			return new String();
        return names[pos];
    }

    public boolean removeItem(String name)
    {
    	int i = 0;

        // fine the item position
		while (i < numitems && names[i].compareTo(name) != 0)
        	i++;

        // if the item doesn't exist, return false
        if (i == numitems)
			return false;

        // take the last item from the list and copy it overtop of the
        // item to be removed, then delete the last item from the list
        if (i < numitems-1)
        {
        	numitems--;
	        values[i] = values[numitems];
    	    names[i] = names[numitems];
            values[numitems] = "";
            names[numitems] = "";
        }
        // if the item is the last one, then simply remove it
        else
        {
        	numitems--;
            values[numitems] = "";
            names[numitems] = "";
        }
        return true;
    }

    public boolean removeItem(int pos)
    {
    	//same as above
    	if (pos < 0 || pos >= numitems)
        	return false;
	    if (pos < numitems-1)
        {
        	numitems--;
	        values[pos] = values[numitems];
    	    names[pos] = names[numitems];
            values[numitems] = "";
            names[numitems] = "";
        }
        else
        {
        	numitems--;
            values[numitems] = "";
            names[numitems] = "";
        }
        return true;
    }

    void erase()
    {
    	// erase all items
    	int i;
    	for (i=0; i<numitems; i++)
        {
        	names[i] = "";
            values[i] = "";
        }
        title = "";
        numitems = 0;
    }
}
