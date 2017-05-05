// DataKey.java

package motherteresa; 


// DataKey.java

/** Represents a key in the form (row, column)
 */
public class DataKey
	   implements Cloneable
{
  private int row;
  private int column;

  public DataKey (int row, int column)
  {
	this.row = row;
	this.column = column;
  }            

  public int getRow ()
  {  return row;  }            

  public int getColumn ()
  {  return column;  }            

  public boolean equals (Object p)
  {
	boolean result = false;
	if (p instanceof DataKey)
	  result = (row == ((DataKey)p).row)
			 && (column == ((DataKey)p).column);
	return result;
  }            

  public int hashCode ()
  {  return (row*2000)+column;  }            

  public String toString ()
  {
	String s = "( " + row + ", " + column + " )";
	return s;
  }              

  public Object clone ()
				throws CloneNotSupportedException
  {
	Object copy = super.clone();
	((DataKey)copy).row = this.row;
	((DataKey)copy).column = this.column;
	return copy;
  }            

}