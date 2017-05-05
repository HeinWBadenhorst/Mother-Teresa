/*
 * ExpandedSet.java
 *
 * Created on 21 June 2005, 01:58
 */

package motherteresa;

import java.util.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author  HWBadenhorst
 */
public class ExpandedSet
{
    private ArrayList objectSet = null;
    private ArrayList itemComponents = null;
    private int sizeOfSet = 0;
    private int setInitSize = 0;
    private int nextItem = 0;
    public boolean hasNext = false;
    
    /** Creates a new instance of ExpandedSet */
    public ExpandedSet(int initialSize)
    {
        setInitSize = initialSize;
        objectSet = new ArrayList(initialSize);
        
    }
    
    public ExpandedSet(ArrayList setData)
    {
        objectSet = setData;
        sizeOfSet =  objectSet.size();
    }
    
    public ExpandedSet(DefaultTableModel tableDataModel)
    {
        int rowCount = tableDataModel.getRowCount();
        objectSet =  new ArrayList(rowCount);
        for (int tableDataLoop = 0; tableDataLoop < rowCount; tableDataLoop++)
        {
            String firstValue = (String)tableDataModel.getValueAt(tableDataLoop, 0);
            String secondValue = (String)tableDataModel.getValueAt(tableDataLoop, 1);
            ArrayList tempList = new ArrayList(2);
            tempList.add(firstValue);
            tempList.add(secondValue);
            objectSet.add(tempList.clone());
        }
        sizeOfSet =  objectSet.size();
        if (sizeOfSet > 0)
        {
            hasNext = true;
        }
    }
    
    
    
    public boolean put(ArrayList itemComponent)
    {
        
        objectSet.add(itemComponent);
        sizeOfSet++;
        return true;
    }
    
    public boolean remove(String item)
    {
        for (int loop = 0; loop < objectSet.size(); loop++)
        {
            ArrayList itemComp = (ArrayList)objectSet.get(loop);
            String itemVal = (String)itemComp.get(0);
            if (item.equalsIgnoreCase(itemVal) == true)
            {
                objectSet.remove(loop);
                sizeOfSet--;
                break;
            }
        }
        return true;
    }
    
    public boolean removeAllOf(String item)
    {
        for (int loop = 0; loop < objectSet.size(); loop++)
        {
            ArrayList itemComp = (ArrayList)objectSet.get(loop);
            String itemVal = (String)itemComp.get(0);
            if (item.equalsIgnoreCase(itemVal) == true)
            {
                objectSet.remove(loop);
                sizeOfSet--;
                
            }
        }
        return true;
    }

    
    public boolean removeAllFrom(String item, int index)
    {
        for (int loop = index+1; loop < objectSet.size(); loop++)
        {
            if (loop == objectSet.size())
            {
                break;
            }
            ArrayList itemComp = (ArrayList)objectSet.get(loop);
            String itemVal = (String)itemComp.get(0);
            if (item.equalsIgnoreCase(itemVal) == true)
            {
                objectSet.remove(loop);
                loop--;
                sizeOfSet--;
                
            }
        }
        return true;
    }
    
    
    public boolean removeDuplicates()
    {
        for (int loop = 0; loop < objectSet.size(); loop++)
        {
            if (loop == objectSet.size())
            {
                break;
            }
            ArrayList itemComp = (ArrayList)objectSet.get(loop);
            String itemVal = (String)itemComp.get(0);
            removeAllFrom(itemVal,loop);
        }
        return true;
    }
    
    public boolean adjustTime(int newTime)
    {
        int itemCount = objectSet.size();
        double timePerItem = newTime / itemCount;
        double roundUp = Math.ceil(timePerItem);
        int timePerItemInt = (int)roundUp;
        
        ArrayList newSet = new ArrayList(itemCount);
        for (int loop = 0; loop < objectSet.size(); loop++)
        {
            if (loop == objectSet.size())
            {
                break;
            }
            ArrayList itemComp = (ArrayList)objectSet.get(loop);
            String itemVal = (String)itemComp.get(0);
            String timeVal = String.valueOf(timePerItemInt);
            itemComp.set(1, timeVal);
            newSet.add(itemComp.clone());
        }
        objectSet = newSet;
        return true;
    }

    public boolean clear()
    {
        objectSet = new ArrayList(setInitSize);
        sizeOfSet = 0;
        return true;
    }
    
    public boolean containsItem(String item)
    {
        boolean itemFound = false;
        int loop = 0;
        for (loop = 0; loop < objectSet.size(); loop++)
        {
            ArrayList itemComp = (ArrayList)objectSet.get(loop);
            String itemVal = (String)itemComp.get(0);
            if (item.equalsIgnoreCase(itemVal) == true)
            {
                itemFound = true;
                break;
            }
        }
        return itemFound;
    }
    
    public int size()
    {
        return sizeOfSet;
    }
    
    public ArrayList  get(String item)
    {
        for (int loop = 0; loop < objectSet.size(); loop++)
        {
            ArrayList itemComp = (ArrayList)objectSet.get(loop);
            String itemVal = (String)itemComp.get(0);
            if (item.equalsIgnoreCase(itemVal) == true)
            {
                return itemComp;
            }
        }
        return null;
    }
    
    public boolean sortSet()
    {
        quickArraySort(objectSet,0,sizeOfSet-1);
        return true;
    }
    
    public boolean sortSetDesc()
    {
        quickDescArraySort(objectSet,0,sizeOfSet-1);
        return true;
    }

    public ArrayList  getNextItem()
    {
        if (hasNext == true)
        {
            if (nextItem < sizeOfSet)
            {
                if (nextItem == sizeOfSet-1 )
                {
                    hasNext = false;
                }
                ArrayList itemComp = (ArrayList)objectSet.get(nextItem);
                nextItem++;
                return itemComp;
            } else
            {
                hasNext = false;
                return null;
            }
            
        } else
            return null;
    }
    
    
    public void resetNext()
    {
        if (sizeOfSet > 0)
        {
            hasNext = true;
            nextItem = 0;
        }
        
        
    }
    
    
    
    public ArrayList getFullSet()
    {
        return objectSet;
        
    }
    public Object[][] getObjArray()
    {
        Object[][] dataArray = new Object[objectSet.size()][2];
        for (int loop = 0; loop < objectSet.size(); loop++)
        {
           ArrayList rowData = (ArrayList)objectSet.get(loop);
           String freqVal = (String)rowData.get(0);
           String timeVal = (String)rowData.get(1);
           dataArray[loop][0] = freqVal;
           dataArray[loop][1] = timeVal;
        }
        return dataArray;
    }
    
    private void swapElements(ArrayList objSetIn,int i, int j)
    {
        ArrayList tempI;
        ArrayList tempJ;
        
        // Swap the elements
        tempI = (ArrayList)objSetIn.get(i);
        tempJ = (ArrayList)objSetIn.get(j);
        objSetIn.remove(i);
        objSetIn.add(i,tempJ);
        objSetIn.remove(j);
        objSetIn.add(j,tempI);
    }
    
    
    private void quickArraySort(ArrayList objSetIn,int lo0,
    int hi0)
    {
        int lo = lo0;
        int hi = hi0;
        java.lang.String midStr = null;
        double midInt = -1;
        // See if the high index is more than the low index
        if (hi0 > lo0)
        {
            // Arbitrarily establishing partition element as the midpoint of the array
            ArrayList itemComp = (ArrayList)objSetIn.get((lo0 + hi0) / 2);
            midStr = (String)itemComp.get(0);
            midInt = Double.parseDouble(midStr.trim());
            
            // Loop through the array until indices cross
            while (lo <= hi)
            {
                // Find the first element that is greater than or equal to
                // the partition element starting from the left Index.
                while (lo < hi0)
                {
                    ArrayList theItem = (ArrayList)objSetIn.get(lo);
                    
                    String theStringItem = (String)theItem.get(0);
                    double lowInt = Double.parseDouble(theStringItem.trim());
                    if (lowInt < midInt)
                    {
                        // Increment the lower index
                        ++lo;
                    } else break;
                }
                // Find an element that is smaller than or equal to
                // the partition element starting from the right Index.
                while (hi > lo0)
                {
                    ArrayList theHiItem = (ArrayList)objSetIn.get(hi);
                    String theHighStringItem = (String)theHiItem.get(0);
                    double highInt = Double.parseDouble(theHighStringItem.trim());
                    if (highInt > midInt)
                    {
                        // Decrement the high index
                        --hi;
                    } else break;
                }
                // If the indexes have not crossed, swap
                if (lo <= hi)
                {
                    // Swap the elements
                    swapElements(objSetIn,lo, hi);
                    ++lo;
                    --hi;
                }
            }
            
            // If the right index has not reached the left side of array
            // must now sort the left partition.
            if (lo0 < hi)
                quickArraySort(objSetIn,lo0, hi);
            
            // If the left index has not reached the right side of array
            // must now sort the right partition.
            if (lo < hi0)
                quickArraySort(objSetIn,lo, hi0 );
        }
    }

    private void quickDescArraySort(ArrayList objSetIn,int lo0,
    int hi0)
    {
        int lo = lo0;
        int hi = hi0;
        java.lang.String midStr = null;
        double midInt = -1;
        // See if the high index is more than the low index
        if (hi0 > lo0)
        {
            // Arbitrarily establishing partition element as the midpoint of the array
            ArrayList itemComp = (ArrayList)objSetIn.get((lo0 + hi0) / 2);
            midStr = (String)itemComp.get(0);
            midInt = Double.parseDouble(midStr.trim());
            
            // Loop through the array until indices cross
            while (lo <= hi)
            {
                // Find the first element that is greater than or equal to
                // the partition element starting from the left Index.
                while (lo < hi0)
                {
                    ArrayList theItem = (ArrayList)objSetIn.get(lo);
                    
                    String theStringItem = (String)theItem.get(0);
                    double lowInt = Double.parseDouble(theStringItem.trim());
                    if (lowInt > midInt)
                    {
                        // Increment the lower index
                        ++lo;
                    } else break;
                }
                // Find an element that is smaller than or equal to
                // the partition element starting from the right Index.
                while (hi > lo0)
                {
                    ArrayList theHiItem = (ArrayList)objSetIn.get(hi);
                    String theHighStringItem = (String)theHiItem.get(0);
                    double highInt = Double.parseDouble(theHighStringItem.trim());
                    if (highInt < midInt)
                    {
                        // Decrement the high index
                        --hi;
                    } else break;
                }
                // If the indexes have not crossed, swap
                if (lo <= hi)
                {
                    // Swap the elements
                    swapElements(objSetIn,lo, hi);
                    ++lo;
                    --hi;
                }
            }
            
            // If the right index has not reached the left side of array
            // must now sort the left partition.
            if (lo0 < hi)
                quickDescArraySort(objSetIn,lo0, hi);
            
            // If the left index has not reached the right side of array
            // must now sort the right partition.
            if (lo < hi0)
                quickDescArraySort(objSetIn,lo, hi0 );
        }
    }
}
