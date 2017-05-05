// DataRowIterator.java

package motherteresa; 

import java.util.Iterator;

public class DataRowIterator
	   implements Iterator
{
  private int rowCount;
  private int nextRow = 0;

  private DataTable dataTable;

  public DataRowIterator (DataTable dt)
  {
	this.dataTable = dt;
	this.rowCount = dt.getRowCount();
  }            

  private boolean verifyRow (int index)
  {  return ( (index >= 0) && (nextRow < rowCount) );  }            


  public boolean hasNext ()
  {
	return verifyRow(nextRow); 
  }            

  public Object next ()
  {
	String[] result = dataTable.getRow(nextRow++);
	return result;
  }            
	 
  public void remove ()
  {
	throw new UnsupportedOperationException("remove() not implemented.");
  }             
}