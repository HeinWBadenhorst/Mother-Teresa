// DataTable.java

package motherteresa; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

public class DataTable

{
  public static final int DEFAULT_ROW_LIMIT = 150;
  public static final int DEFAULT_COLUMN_LIMIT = 40;

  protected String[] keyColumn = null;
  protected String keyColumnName = null;
  
  protected int rowLimit = DEFAULT_ROW_LIMIT;
  protected int columnLimit = DEFAULT_COLUMN_LIMIT;


  protected int rowCount = 0;
  protected int columnCount = 0;
  protected int cellCount = 0;
  protected int nameCount = 0;

  protected int dataRowCount = -1;
  protected int dataColumnCount = -1;
  protected int allocatedRows = -1;
  protected int allocatedColumns = -1;
  protected int allocatedCells = -1;
 
  protected HashMap dataCells = null;
  protected HashSet rowSet = null;
  protected HashSet columnSet = null;
  protected ArrayList columnNames = null;
  protected HashMap columnTypes = null;
  protected HashMap columnKeyTypes = null;
  protected HashMap columnAliases = null;
  protected HashMap columnLengths = null;
  protected HashMap columnLock = null;
  protected HashMap columnDisplay = null;
  protected HashMap labelDisplay = null;
  protected HashMap columnHighlight = null;
 
  protected boolean rowLimitExceeded = false;


  public DataTable () {}                    

  public boolean containsName (String columnName)
  {
	return columnNames.contains(columnName);
  }                

  public boolean setKeyColumn(String name)
  {
	if (name == null) return false;
	if (name.equals("")) return false;
	if (keyColumn != null) return false;
	keyColumnName = new String(name.toCharArray());
  if (isEmpty())
  {
	keyColumn = new String[0];
	return true;
  }
	int idx = getColumnIndex(name);
	if (idx >= 0) 
  {
  	keyColumn = getColumn(name);
  	return true;
  }
  else
  {
	keyColumn = new String[0];
	return false;
  }

  }          

  public String getKeyColumnName ()
  {  return keyColumnName;  }        

  public String[] getKeyColumn ()
  {
	if (keyColumn == null)
	  throw new IllegalStateException("DataTable.getKeyColumn(): "
				+ " The key column for this tabel has not been set.");
	return (String[])keyColumn.clone();
  }        


  public void setSize (int rows, int columns)
  {
	if (verifyAllocation()) return;
	if ( (rows >= 0) && (columns >= 0) )
	{
	  dataRowCount = rows;
	  dataColumnCount = columns;
	  if (rows <= rowLimit)
		allocatedRows = rows;
	  else
		allocatedRows = rowLimit;
	  if (columns <= columnLimit)
		allocatedColumns = columns;
	  else
		allocatedColumns = columnLimit;
	  dataCells  = new HashMap (allocatedColumns*allocatedRows);
	  rowSet = new HashSet(allocatedRows);
	  columnSet = new HashSet(allocatedColumns);
	  columnNames = new ArrayList(allocatedColumns);
	  columnTypes = new HashMap(allocatedColumns);
          columnKeyTypes = new HashMap(allocatedColumns);
	  columnAliases = new HashMap(allocatedColumns);
          columnLengths = new HashMap(allocatedColumns);
	  columnDisplay = new HashMap(allocatedColumns);
          columnLock = new HashMap(allocatedColumns);
          columnHighlight = new HashMap(allocatedColumns);
          labelDisplay = new HashMap(allocatedColumns);
          
	}
	else
	{
	  dataCells  = new HashMap (columnLimit*rowLimit);
	  rowSet = new HashSet(rowLimit);
	  columnSet = new HashSet(columnLimit);
	  columnNames = new ArrayList(columnLimit);
	  columnTypes = new HashMap(columnLimit);
          columnKeyTypes = new HashMap(columnLimit);
	  columnAliases = new HashMap(columnLimit);
          columnLengths = new HashMap(columnLimit);
	  columnDisplay = new HashMap(columnLimit);
          columnLock = new HashMap(columnLimit);
          columnHighlight = new HashMap(columnLimit);
          labelDisplay = new HashMap(columnLimit);
	}     

//    System.out.println("Allocation: rows = " + allocatedRows
//               + " cols= " + allocatedColumns);
  }                    


  private boolean verifyAllocation ()
  {
	return ( ((columnSet != null) && (rowSet != null) && (dataCells != null)) );
  }                    

  private void allocateByDefault ()
  {
	allocatedRows = rowLimit / 2;
	allocatedColumns = columnLimit / 2;  
	allocatedCells = allocatedRows * allocatedColumns; 
	dataCells  = new HashMap (allocatedCells);
	rowSet = new HashSet(allocatedRows);
	columnSet = new HashSet(allocatedColumns);
	columnNames = new ArrayList(allocatedColumns);
        columnTypes = new HashMap(allocatedColumns);
        columnKeyTypes = new HashMap(allocatedColumns);
        columnAliases = new HashMap(allocatedColumns);
        columnLengths = new HashMap(allocatedColumns);
        columnLock = new HashMap(allocatedColumns);
        columnDisplay = new HashMap(allocatedColumns);
        columnHighlight = new HashMap(allocatedColumns);
        labelDisplay = new HashMap(allocatedColumns);


//    System.out.println("Allocation: rows= " + allocatedRows
//               + " cols= " + allocatedColumns);
  }                    

  private boolean addColumn (int colIndex)
  {
	boolean result = false;
	if (columnSet.add(new Integer(colIndex)))
	{
	  result = true;
	  columnCount++;
//      System.out.println("Column added: " + colIndex);
	}
	return result;
  }                    

private int addNewColumn(String newName)
  {
	int theIndex = columnCount;
	columnNames.add(theIndex, newName);
//	columnAliases.add(theIndex, newName);
	columnSet.add(new Integer(theIndex));
	nameCount++;
	columnCount++; 
	return theIndex;
  }                  

  public int addNewColumn(String newName, String aliasName, String colType)
  {
	int theIndex = columnCount;
	columnNames.add(theIndex, newName);
	columnAliases.put( (Object)newName, (Object)aliasName);
	columnTypes.put( (Object)newName, (Object)colType);
	columnSet.add(new Integer(theIndex));
	nameCount++;
	columnCount++; 
	return theIndex;
  }                  

  public int addNewColumn(String newName, String aliasName, String colType, String colLength)
  {
	int theIndex = columnCount;
	columnNames.add(theIndex, newName);
	columnAliases.put( (Object)newName, (Object)aliasName);
	columnTypes.put( (Object)newName, (Object)colType);
	columnLengths.put( (Object)newName, (Object)colLength);
	columnSet.add(new Integer(theIndex));
	nameCount++;
	columnCount++; 
	return theIndex;
  }                  

 public int addNewColumn(String newName, String aliasName, String colType, String colLength, boolean colLock, boolean colDisplay)
  {
	int theIndex = columnCount;
	columnNames.add(theIndex, newName);
	columnAliases.put( (Object)newName, (Object)aliasName);
	columnTypes.put( (Object)newName, (Object)colType);
	columnLengths.put( (Object)newName, (Object)colLength);
	columnLock.put( (Object)newName, (Object)String.valueOf(colLock));
	columnDisplay.put( (Object)newName, (Object)String.valueOf(colDisplay));
	columnSet.add(new Integer(theIndex));
	nameCount++;
	columnCount++; 
	return theIndex;
  }                  

 public int addNewColumn(String newName, String aliasName, String colType, String colLength, boolean colLock, boolean colDisplay, String colKeyType)
  {
	int theIndex = columnCount;
	columnNames.add(theIndex, newName);
	columnAliases.put( (Object)newName, (Object)aliasName);
	columnTypes.put( (Object)newName, (Object)colType);
	columnLengths.put( (Object)newName, (Object)colLength);
	columnLock.put( (Object)newName, (Object)String.valueOf(colLock));
	columnDisplay.put( (Object)newName, (Object)String.valueOf(colDisplay));
        columnKeyTypes.put((Object)newName, (Object)String.valueOf(colKeyType));
	columnSet.add(new Integer(theIndex));
	nameCount++;
	columnCount++; 
	return theIndex;
  }                  

 public int addNewColumn(String newName, String aliasName, String colType, String colLength, boolean colLock, boolean colDisplay, String colKeyType, boolean colHighlight)
  {
	int theIndex = columnCount;
	columnNames.add(theIndex, newName);
	columnAliases.put( (Object)newName, (Object)aliasName);
	columnTypes.put( (Object)newName, (Object)colType);
	columnLengths.put( (Object)newName, (Object)colLength);
	columnLock.put( (Object)newName, (Object)String.valueOf(colLock));
	columnHighlight.put( (Object)newName, (Object)String.valueOf(colHighlight));
	columnDisplay.put( (Object)newName, (Object)String.valueOf(colDisplay));
        columnKeyTypes.put((Object)newName, (Object)String.valueOf(colKeyType));
	columnSet.add(new Integer(theIndex));
	nameCount++;
	columnCount++; 
	return theIndex;
  }                  

 public int addNewColumn(String newName, String aliasName, String colType, String colLength, boolean colLock, boolean colDisplay, String colKeyType, boolean colHighlight, boolean labelDisp)
  {
	int theIndex = columnCount;
	columnNames.add(theIndex, newName);
	columnAliases.put( (Object)newName, (Object)aliasName);
	columnTypes.put( (Object)newName, (Object)colType);
	columnLengths.put( (Object)newName, (Object)colLength);
	columnLock.put( (Object)newName, (Object)String.valueOf(colLock));
	columnHighlight.put( (Object)newName, (Object)String.valueOf(colHighlight));
	columnDisplay.put( (Object)newName, (Object)String.valueOf(colDisplay));
 	labelDisplay.put( (Object)newName, (Object)String.valueOf(labelDisp));
        columnKeyTypes.put((Object)newName, (Object)String.valueOf(colKeyType));
	columnSet.add(new Integer(theIndex));
	nameCount++;
	columnCount++; 
	return theIndex;
  }                  

 protected boolean addRow (int rowIndex)
  {
	boolean result = false;
	if (rowSet.add(new Integer(rowIndex)))
	{
	  result = true;
	  rowCount++;
//      System.out.println("Row added: " + rowIndex);
	}
	return result;
  }                    

  public boolean addElement (int row, int col, String value)
  {
//    System.out.println("Processing - " + row + " " + col + " " + value);
	if (row >= rowLimit) return false;
	if (col >= columnLimit ) return false;
	if ( (row < 0) || (col < 0)) return false;
	if (! verifyAllocation())
	  allocateByDefault(); 
	String theValue = "";
  if (value == null)
	theValue = new String("");
  else if (value.equals(""))
	theValue = "";
  else
	theValue = new String(value.trim().toCharArray());
	boolean result = false;
	DataKey key = new DataKey(row, col);
	if (! dataCells.containsKey(key))
	{
	  result = true;
	  dataCells.put(key, theValue);
          //System.out.println("Cell added " + row + " " + col + " " + theValue);
	  cellCount++;
	  addRow(row);
	  addColumn(col);
	}  
	return result;
  }                        

  public boolean addElement (String columnName, String value)
  {
   int theRow = 0;
   int theCol = 0;
   String theName;
   boolean result = false;
   
        theRow = rowCount -1;
        theName = new String(columnName.trim().toCharArray());
 	if (! columnNames.contains(theName))
	{
            return result;
        }
        theCol = columnNames.indexOf(theName);
        DataKey theKey = new DataKey(theRow, theCol);
        dataCells.put(theKey, clean(value));
        cellCount++;
        result = true;
	return result;
  }                        

  public boolean addOrReplaceElement (int row, int col, String value)
  {
//    System.out.println("Processing - " + row + " " + col + " " + value);
	if (row >= rowLimit) return false;
	if (col >= columnLimit ) return false;
	if ( (row < 0) || (col < 0)) return false;
	if (! verifyAllocation())
	  allocateByDefault(); 
	String theValue = "";
  if (value == null)
	theValue = new String("");
  else if (value.equals(""))
	theValue = "";
  else
	theValue = new String(value.trim().toCharArray());
	boolean result = false;
	DataKey key = new DataKey(row, col);
	if (! dataCells.containsKey(key))
	{
	  result = true;
	  dataCells.put(key, theValue);
          //System.out.println("Cell added " + row + " " + col + " " + theValue);
	  cellCount++;
	  addRow(row);
	  addColumn(col);
	} else
        {
           result = replace(row, col, theValue); 
        }
	return result;
  }                        

  
  public boolean addColumnName (String name, int ID)
  {
	if (ID < 0) return false;
	if (ID >= columnLimit) return false;
	if (! verifyAllocation())
	  allocateByDefault();
	String theName = "";
	if (! ((name == null) && (name.equals(""))) )
	  theName = new String(name.trim().toCharArray());
	boolean result = false;
	if (! columnNames.contains(theName))
	{
	  result = true;
	  columnNames.add(ID, theName);
	  //columnAliases.add(ID, theName);
//      System.out.println("Name added " + ID + " " + theName);
	  nameCount++;
      	  addColumn(ID); //check later if ok
	}  
	return result;
  }                    

  public boolean addColumnType (String name, int ID)
  {
	if (ID < 0) return false;
	if (ID >= columnLimit) return false;
	if (! verifyAllocation())
	  allocateByDefault();
	String theName = "";
	if (! ((name == null) && (name.equals(""))) )
	  theName = new String(name.trim().toCharArray());
	boolean result = false;
        String colName = getColumnName(ID);
	columnTypes.put((Object)colName, (Object)theName);
        result = true;
	return result;
  }                    

  public boolean addColumnLength (String length, int ID)
  {
	if (ID < 0) return false;
	if (ID >= columnLimit) return false;
	if (! verifyAllocation())
	  allocateByDefault();
	String theLength = "";
	if (! ((length == null) && (length.equals(""))) )
	  theLength = new String(length.trim().toCharArray());
	boolean result = false;
        String colName = getColumnName(ID);
	columnLengths.put((Object)colName, (Object)theLength);
        result = true;
	return result;
  }                    

  public int getColumnIndex (String colName)
  {
	int result = -1;
	String key = "";
	if ( (colName != null) && (!colName.equals("")))
	  key = new String(colName.trim().toCharArray());
	if (columnNames.contains(key))
	{
	  result = columnNames.indexOf(key);
	}
	return result;
  }                    

  public String getColumnName (int index)
  {
	String result = null;
	if ( (index >= 0) && (index < columnCount))
	{
	  result = (String) columnNames.get(index);
	}
	return result;
  }                    

  public String getColumnType (int index)
  {
        String result = "null";
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
	  result = (String) columnTypes.get((Object)colName);
          return result;
	} else
	return result;
  }                    

  public String getColumnType (String columnName)
  {
          String theValue = (String)columnTypes.get((Object)columnName);
          return theValue;
  }

  public String getColumnKeyType (int index)
  {
        String result = "null";
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
	  result = (String) columnKeyTypes.get((Object)colName);
          return result;
	} else
	return result;
  }                    

  public String getColumnKeyType (String columnName)
  {
          String theValue = (String)columnKeyTypes.get((Object)columnName);
          return theValue;
  }

  public String[] getColumnKeyTypes ()
  {
	String[] result = new String[columnNames.size()];
	for (int j = 0; j < columnKeyTypes.size(); j++)
	{  result[j] = getColumnKeyType(j);  }
	return result;
  }                    

  public String getColumnLength (int index)
  {
        String result = "null";
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
	  result = (String) columnLengths.get((Object)colName);
          return result;
	} else
	return result;
  }                    

  public String getColumnLength (String columnName)
  {
          String theValue = (String)columnLengths.get((Object)columnName);
          return theValue;
  }
  
  public String getColumnAlias (int index)
  {
	String result = "null";
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
	  result = (String) columnAliases.get((Object)colName);
	}
	return result;
  }                    

  public String getColumnAlias (String columnName)
  {
          String theValue = (String)columnAliases.get((Object)columnName);
          return theValue;
  }

  public String[] getColumnNames ()
  {
	String[] result = new String[columnNames.size()];
	for (int j = 0; j < columnNames.size(); j++)
	{  result[j] = getColumnName(j);  }
	return result;
  }                    

  public String[] getColumnAliases ()
  {
	String[] result = new String[columnNames.size()];
	for (int j = 0; j < columnNames.size(); j++)
	{  result[j] = getColumnAlias(j);  }
	return result;
  }                    

  public void setColumnAlias (String colName, String aliasName)
  {
	int colIndex = getColumnIndex(colName);
	columnAliases.put((Object)colName, (Object)aliasName);
  }                    

  public boolean getColumnLock (int index)
  {
	boolean result = false;
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
          if (((String)columnLock.get((Object)colName)).equalsIgnoreCase("true") == true) 
          { 
              result = true;
          } else
          {
              result = false;
          }
	}
	return result;
  }                    

  public boolean getColumnHighlight (int index)
  {
	boolean result = false;
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
          if (((String)columnHighlight.get((Object)colName)).equalsIgnoreCase("true") == true) 
          { 
              result = true;
          } else
          {
              result = false;
          }
	}
	return result;
  }                    

  public boolean getColumnLock (String columnName)
  {
      boolean result = false;  
      if (((String)columnLock.get((Object)columnName)).equalsIgnoreCase("true") == true) 
          { 
              result = true;
          } else
          {
              result = false;
          }
          return result;
  }

  public boolean getColumnHighlight (String columnName)
  {
      boolean result = false;  
      if (((String)columnHighlight.get((Object)columnName)).equalsIgnoreCase("true") == true) 
          { 
              result = true;
          } else
          {
              result = false;
          }
          return result;
  }

  public boolean[] getColumnLocks ()
  {
	boolean[] result = new boolean[columnNames.size()];
	for (int j = 0; j < columnLock.size(); j++)
	{  result[j] = getColumnLock(j);  }
	return result;
  }                    

  public boolean[] getColumnHighlights ()
  {
	boolean[] result = new boolean[columnNames.size()];
	for (int j = 0; j < columnHighlight.size(); j++)
	{  result[j] = getColumnHighlight(j);  }
	return result;
  }                    

  public void setColumnLock (String colName, boolean colLock)
  {
	int colIndex = getColumnIndex(colName);
	columnLock.put((Object)colName, (Object)String.valueOf(colLock));
  }                    

  public void setColumnHighlight (String colName, boolean colHighlight)
  {
	int colIndex = getColumnIndex(colName);
	columnHighlight.put((Object)colName, (Object)String.valueOf(colHighlight));
  }                    

  public void setLabelDisplay (String colName, boolean labelDisp)
  {
	//int colIndex = getColumnIndex(colName);
	this.labelDisplay.put((Object)colName, (Object)String.valueOf(labelDisp));
  }                    

  public boolean getColumnDisplay (int index)
  {
	boolean result = false;
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
          if (((String)columnDisplay.get((Object)colName)).equalsIgnoreCase("true") == true) 
          { 
              result = true;
          } else
          {
              result = false;
          }
	}
	return result;
  }                    

  public boolean getLabelDisplay (int index)
  {
	boolean result = false;
	if ( (index >= 0) && (index < columnCount))
	{
          String colName = (String)columnNames.get(index);
          if (((String)labelDisplay.get((Object)colName)).equalsIgnoreCase("true") == true) 
          { 
              result = true;
          } else
          {
              result = false;
          }
	}
	return result;
  }                    

  public boolean getColumnDisplay (String columnName)
  {
      boolean result = false;  
      if (((String)columnDisplay.get((Object)columnName)).equalsIgnoreCase("true") == true) 
      { 
          result = true;
      } else
      {
          result = false;
      }
      return result;
  }

  public boolean[] getColumnDisplays ()
  {
	boolean[] result = new boolean[columnNames.size()];
	for (int j = 0; j < columnDisplay.size(); j++)
	{  result[j] = getColumnDisplay(j);  }
	return result;
  }                    

  public boolean getLabelDisplay (String columnName)
  {
      boolean result = false;  
      if (((String)labelDisplay.get((Object)columnName)).equalsIgnoreCase("true") == true) 
      { 
          result = true;
      } else
      {
          result = false;
      }
      return result;
  }

  public boolean[] getLabelDisplays ()
  {
	boolean[] result = new boolean[columnNames.size()];
	for (int j = 0; j < labelDisplay.size(); j++)
	{  result[j] = getLabelDisplay(j);  }
	return result;
  }                    

  public void setColumnDisplay (String colName, boolean colDisplay)
  {
	int colIndex = getColumnIndex(colName);
	columnDisplay.put((Object)colName, (Object)String.valueOf(colDisplay));
  }                    

  public boolean replace (int rowIndex, int colIndex, String newValue)
  {
	boolean result = false;
	if (verifyRowIndex(rowIndex) && verifyColumnIndex(colIndex))
	{
	  result = true;
	  String theValue = "";
	  if (! ((newValue == null) && newValue.equals("")))
		theValue = new String(newValue.trim().toCharArray());
	  DataKey key = new DataKey(rowIndex, colIndex);
	  dataCells.put(key, theValue);
//      System.out.println("Cell replaced: " + rowIndex + " " + colIndex + " " + theValue);
	}  
	return result;
  }                    



  public boolean replace (String columnName, String newValue, int rowIndex)
  {
	return replace(rowIndex, getColumnIndex(columnName), newValue);
  }                    


  public boolean replace (String columnName, String[] newValues)
  {
	boolean result = false;
	boolean temp = false;
	int controlCount = 0;
	if (newValues.length == rowCount)
	{
	  int columnIndex = getColumnIndex(columnName);
	  if (columnIndex >= 0)
	  {
		for (int i = 0; i < rowCount; i++)
		{  

		  temp = replace(i, columnIndex, newValues[i]);
		  if (temp) controlCount++;
		}
	  }
	  if (controlCount == rowCount) result = true;
	}
	return result;
  }                    

  public boolean setRowLimit (int newLimit)
  {
	boolean result = false;
	if (newLimit > 0)
	{
	  this.rowLimit = newLimit;
	  result = true;
	}
	return result;
  }                    

  public int getRowLimit ()
  {  return rowLimit;  }                    




  public int getDataRowCount ()
  {  return dataRowCount;  }                    



  public int getDataColumnCount ()
  {  return dataColumnCount;  }                    

  public int getRowCount ()
  {  return rowCount;  }                    

  public int getColumnCount ()
  {  return columnCount;  }                    


  public String getDataAt(int row, int col)
  {
	String result = "";
	if ( (row >= 0) && (row < rowCount))
	{
	  DataKey dk = new DataKey(row, col);
	  if (dataCells.containsKey(dk))
		result = (String) dataCells.get(dk);
	}
	return result;
  }                    

  public void replaceDataAt(int row, int col,String newValue)
  {
	if ( (row >= 0) && (row < rowCount))
	{
	  DataKey dk = new DataKey(row, col);
	  if (dataCells.containsKey(dk))
          {
        	dataCells.remove((Object)dk);
                dataCells.put((Object)dk, (Object)newValue);
          }
	}
  }                    

  public void replaceDataAt(String columnName,String[] newValues)
  {
        int columnIndex = getColumnIndex(columnName);
	for (int loop = 0; loop < rowCount; loop++)
        {
	  DataKey dk = new DataKey(loop, columnIndex);
	  if (dataCells.containsKey(dk))
          {
        	dataCells.remove((Object)dk);
                dataCells.put((Object)dk, (Object)newValues[loop]);
          }
	}
  }                    

  public boolean verifyColumnIndex (int colIndex)
  {  return ( (colIndex >= 0) && (colIndex < columnCount) );  }                    

  public boolean verifyRowIndex (int rowIndex)
  {  return ( (rowIndex >= 0) && (rowIndex < rowCount) );  }                    


  public boolean isEmpty ()
  {  return (cellCount == 0);  }                    

  public String[] getColumn (int colIndex)
  {
//	if (isEmpty()) return null;
	if (!verifyColumnIndex(colIndex))
	throw new IllegalArgumentException("index out of bounds.");
	String[] result = new String[rowCount];
	for (int i = 0; i < rowCount; i++)
	{  result[i] = new String(getDataAt(i, colIndex));  }
	return result;
  }                      


  public String[] getColumn (String columnName)
  {
	return getColumn(getColumnIndex(columnName));
  }                    

  public String[] getRow (int rowIndex)
  {
	if (isEmpty()) return null;
	if (!verifyRowIndex(rowIndex)) return null;
	String[] result = new String[columnCount];
	for (int j = 0; j < columnCount; j++)
	{  result[j] = new String(getDataAt(rowIndex, j));  }
	return result;
  }                    

  public void removeLastRow ()
  {
	int lastRow = rowCount -1;
	for (int j = 0; j < columnCount; j++)
	{
	  DataKey key = new DataKey(lastRow, j);
	  dataCells.remove(key);
	}
	rowCount--;
  }                    


  public void addNewColumn (String newName, String[] values)
  {
	int newCol = columnCount;
//	columnCount++;
	addColumnName(clean(newName), newCol);
	for (int i = 0; i < values.length; i++)
	{
	  addElement(i, newCol, clean(values[i]));
	}
  }                      

  public boolean addNewColumn (String newName, StringBuffer[] values)
  {
	if ((newName == null) && newName.equals(""))
	  return false;
	if (values.length != rowCount)
	  return false;
  	boolean result = false;
	int newIndex = addNewColumn(newName);
	for (int i = 0; i < rowCount; i++)
	{
		DataKey key = new DataKey(i, newIndex);
		if (! dataCells.containsKey(key))
		{
		  result = true;
		  dataCells.put(key, clean(values[i].toString()));
		  cellCount++;
		}  
	}
	return result;
  }                        



  public Iterator rowIterator ()
  {
	return new DataRowIterator(this);
  }                    

  public Iterator columnIterator ()
  {
	return new DataColumnIterator(this);
  }                    

  private String clean (String s)
  {
	if (! ((s == null) || (s.equals("")) ) )
	  return new String(s.trim().toCharArray());
	else
	  return new String("");
  }                    

  private int find (String arg, String[] target)
  {
	for (int i = 0; i < target.length; i++)
	{
	  if (arg.equals(target[i]))
		return i;
	}
	return -1;
  }              

  private int[] accessVector(String[] sorted, String[] unsorted)
  {
	if (sorted.length != unsorted.length)
	  throw new IllegalArgumentException("array lengths are not equal.");
	if (sorted.length != rowCount)
	  throw new IllegalArgumentException("array length not the required length.");
	int[] av = new int[sorted.length];
	for (int i = 0; i < sorted.length; i++)
	{
	  av[i] = find(sorted[i], unsorted);
	}
	return av; 
  }              

  public void sort (String colName)
  {
  if (isEmpty()) return;
  if (rowCount == 1) return; 
	int keyIndex = getColumnIndex (colName);
	if (keyIndex < 0)
	  throw new IllegalArgumentException("column name not defined."); 
	String[] keyColumn = getColumn(keyIndex);
	String [] copy = (String[])keyColumn.clone();
	Arrays.sort(copy);
  boolean[] hitFlags = new boolean[keyColumn.length];
  for (int i = 0; i < keyColumn.length; i++)
  {  hitFlags[i] = false;  }

	int[] accessVector = accessVector(copy, keyColumn, hitFlags);
	for (int j = 0; j < columnCount; j++)
	{
	  String[] theColumn = getColumn(j);
	  for (int i = 0; i < rowCount; i++)
	  {
		replace (i, j, theColumn[accessVector[i]]);
	  }
	}

  }                  



  private int[] accessVector(String[] sorted, String[] unsorted, boolean[] hits)
  {
	if (sorted.length != unsorted.length)
	  throw new IllegalArgumentException("array lengths are not equal.");
	if (sorted.length != rowCount)
	  throw new IllegalArgumentException("array length not the required length.");
	int[] av = new int[sorted.length];
	for (int i = 0; i < sorted.length; i++)
	{
 	  av[i] = find(sorted[i], unsorted, hits);
	}
	return av; 
  }                    private int find (String arg, String[] target, boolean[] hits)
  {
	for (int i = 0; i < target.length; i++)
	{
	  if (arg.equals(target[i]))
	{
	  if (!hits[i])
	  {
		hits[i] = true;
			return i;
	  }
	}
	}
	return -1;
  }                
}