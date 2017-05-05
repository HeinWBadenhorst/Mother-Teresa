package motherteresa;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

public class SimpleForm

{
  protected String formName = "";
  protected HashMap formMap = null;
  protected DataTable dataTable = null;

  
  public SimpleForm () {}      

  public void setFormName (String name)
  {  this.formName = new String(name.toCharArray());  }      


  public void setFormMap (HashMap map)
  {  this.formMap = map;  }      

  public HashMap getFormMapInstance ()
  {  return formMap;  }      

  public String getFormName ()
  {  return formName;  }      

  public void setDataTable (DataTable dt)
  {  this.dataTable = dt;  }      

  public DataTable getDataTableInstance ()
  {  return dataTable;  }      

  public String[] getElementNames ()
  {
	Set theNames = formMap.keySet();
	Iterator iter = theNames.iterator();
	String[] result = new String[formMap.size()];
	int idx = 0;
	while(iter.hasNext())
	{
	  result[idx++] = (String)iter.next();
	}
	return result;
  }      

  public String getElementValue (String name)
  {
	if (formMap.containsKey(name))
	  return (String) formMap.get(name);
	else
	  return "-";
  }      


}