// DataColumnIterator.java

package motherteresa; 

import java.util.Iterator;

public class DataColumnIterator
	   implements Iterator
{
  private int columnCount;
  private int nextColumn = 0;

  private DataTable dataTable;

  public DataColumnIterator (DataTable dt)
  {
	this.dataTable = dt;
	this.columnCount = dt.getColumnCount();
  }            


  private boolean verifyColumn (int index)
  {  return ( (index >= 0) && (nextColumn < columnCount) );  }            

  public boolean hasNext ()
  {
	return verifyColumn(nextColumn);
  }            

  public Object next ()
  {
	String[] result = dataTable.getColumn(nextColumn++);
	return result;
  }            
	 
  public void remove ()
  {
	throw new UnsupportedOperationException("remove() not implemented.");
  }             
}