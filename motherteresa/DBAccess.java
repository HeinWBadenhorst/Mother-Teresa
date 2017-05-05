package motherteresa;
/** Java class "dbAccess.java" 
 *This class allows a generic interface for database access.
 *Most methods in this class throws an exception of type MTException
*/

import java.util.*;
import java.util.Collection;
import java.util.List;
import java.sql.*;

public class DBAccess {

// attributes

    private HashMap inputKeyList;
    private HashMap inputFieldList;
    private HashMap whereFieldList;
    private HashMap dateFieldList;
    private String  operator; 
    private String tableName = ""; 
    private String userName = "";
    private String databaseName = "";
    private String databaseURL = "";
    private String password = null; 
    private boolean autoCommitFlag = true;
    private String errorString = "";
    public DataTable dataTable = null;
    protected HashMap elementMap = new HashMap(20);
    protected SimpleForm simpleForm = new SimpleForm ();
    protected SystemTableLib tableLib = null;
    protected ArrayList tableDetails = new ArrayList();
    protected ArrayList fieldDetails = new ArrayList();
    protected String theOS = null;
    protected Connection conn = null;
            
    protected MTUtils mtUtils = new MTUtils();
    final static long CBCIV_START = 0xfedcba9876543210L;
    private HashMap updateItemList = null;
    final static int MAX_MESS_SIZE = 256;


    //Constructor
    public DBAccess(java.lang.String hostName, java.lang.String systemName, java.lang.String tableName, java.lang.String _userName, java.lang.String _password, String _myOS) throws MTException 
    {
        String fieldActive = "";
        String keyType = "";
        String tempFieldName = "";
        String tempAliasName = "";
        String fieldLength = "";
        boolean fieldLock = false;
        boolean fieldDisplay = true;
        boolean fieldHighlight = true;
        boolean labelDisplay = true;
        theOS = _myOS;
        setDatabaseName(systemName);
        setTableName(tableName);
        setUserName(_userName);
        setPassword(_password);
        databaseURL = "jdbc:mysql://" + hostName + "/" + systemName;
        try
        {
            tableLib = new  SystemTableLib(systemName, theOS);
            tableDetails = tableLib.getTableDetails(this.tableName);
            dataTable = new DataTable();
            dataTable.setRowLimit(9999);
            dataTable.setSize(1000, this.tableDetails.size());
            inputFieldList = new HashMap(20);
            whereFieldList = new HashMap(20);
            dateFieldList = new HashMap(20);
            inputKeyList = new HashMap(20);
            int tableDetailIndex = 0;
            int columnIterator = 0;
            String fieldType = "";
            for (tableDetailIndex = 0; tableDetailIndex < this.tableDetails.size(); tableDetailIndex++)
            {
                fieldDetails = (ArrayList)tableDetails.get(tableDetailIndex);
                tempFieldName = (String)fieldDetails.get(0);
                tempAliasName = (String)fieldDetails.get(1);
                fieldActive = (String)fieldDetails.get(5);
                fieldLength = (String)fieldDetails.get(4);
                if (((String)fieldDetails.get(6)).equalsIgnoreCase("1") == true)
                {
                    fieldLock = true;
                } else fieldLock = false;
                if (((String)fieldDetails.get(7)).equalsIgnoreCase("1") == true)
                {
                    fieldDisplay = true;
                } else fieldDisplay = false;
                if (((String)fieldDetails.get(8)).equalsIgnoreCase("1") == true)
                {
                    fieldHighlight = true;
                } else fieldHighlight = false;
                if (((String)fieldDetails.get(9)).equalsIgnoreCase("1") == true)
                {
                    labelDisplay = true;
                } else labelDisplay = false;
                keyType = (String)fieldDetails.get(2);
                fieldType = (String)fieldDetails.get(3);
                if (fieldActive.equalsIgnoreCase("1"))
                {
                    dataTable.addNewColumn(tempFieldName, tempAliasName , fieldType, fieldLength, fieldLock, fieldDisplay, keyType, fieldHighlight,labelDisplay);
                    if (keyType.equalsIgnoreCase("PRIMARY"))
                    {
                        dataTable.setKeyColumn(tempFieldName);
                    }
                }
            }
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if ((theOS.equalsIgnoreCase("WINDOWS")) || (theOS.equalsIgnoreCase("WINXP")))
            {
                if (userName.equalsIgnoreCase("root") == false)
                {    
                      conn = DriverManager.getConnection(databaseURL,"root",password);
                } else
                {
                      conn = DriverManager.getConnection(databaseURL,userName,password);
                }
            } else
            {
                 conn = DriverManager.getConnection(databaseURL, userName, password);
            }

        }
        catch (Exception e)
        {
            this.errorString = e.getMessage();  
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
    }    

    public DBAccess(java.lang.String hostName, java.lang.String systemName, java.lang.String tableName, java.lang.String _userName, char[] _password, String _myOS) throws MTException 
    {
        String fieldActive = "";
        String keyType = "";
        String tempFieldName = "";
        String tempAliasName = "";
        String fieldLength = "";
        boolean fieldLock = false;
        boolean fieldDisplay = true;
        boolean fieldHighlight = true;
        boolean labelDisplay = true;
        theOS = _myOS;
        setDatabaseName(systemName);
        setTableName(tableName);
  
        StringBuffer tmpBuffer = new StringBuffer(8);
        tmpBuffer.append(_password);

        String strPassword = tmpBuffer.toString();
        setUserName(_userName);
        setPassword(strPassword);
        databaseURL = "jdbc:mysql://" + hostName + "/" + systemName;
        try
        {
            tableLib = new  SystemTableLib(systemName, theOS);
            tableDetails = tableLib.getTableDetails(this.tableName);
            dataTable = new DataTable();
            dataTable.setRowLimit(9999);
            dataTable.setSize(1000, this.tableDetails.size());
            inputFieldList = new HashMap(20);
            whereFieldList = new HashMap(20);
            dateFieldList = new HashMap(20);
            inputKeyList = new HashMap(20);
            int tableDetailIndex = 0;
            int columnIterator = 0;
            String fieldType = "";
            for (tableDetailIndex = 0; tableDetailIndex < this.tableDetails.size(); tableDetailIndex++)
            {
                fieldDetails = (ArrayList)tableDetails.get(tableDetailIndex);
                tempFieldName = (String)fieldDetails.get(0);
                tempAliasName = (String)fieldDetails.get(1);
                fieldActive = (String)fieldDetails.get(5);
                fieldLength = (String)fieldDetails.get(4);
                if (((String)fieldDetails.get(6)).equalsIgnoreCase("1") == true)
                {
                    fieldLock = true;
                } else fieldLock = false;
                if (((String)fieldDetails.get(7)).equalsIgnoreCase("1") == true)
                {
                    fieldDisplay = true;
                } else fieldDisplay = false;
                if (((String)fieldDetails.get(8)).equalsIgnoreCase("1") == true)
                {
                    fieldHighlight = true;
                } else fieldHighlight = false;
                if (((String)fieldDetails.get(9)).equalsIgnoreCase("1") == true)
                {
                    labelDisplay = true;
                } else labelDisplay = false;
                keyType = (String)fieldDetails.get(2);
                fieldType = (String)fieldDetails.get(3);
                if (fieldActive.equalsIgnoreCase("1"))
                {
                    dataTable.addNewColumn(tempFieldName, tempAliasName , fieldType, fieldLength, fieldLock, fieldDisplay, keyType, fieldHighlight,labelDisplay);
                    if (keyType.equalsIgnoreCase("PRIMARY"))
                    {
                        dataTable.setKeyColumn(tempFieldName);
                    }
                }
            }
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            if ((theOS.equalsIgnoreCase("WINDOWS")) || (theOS.equalsIgnoreCase("WINXP")))
            {
                //remove username and password for an anonymous logon
                if (userName.equalsIgnoreCase("root") == false)
                {    
                      conn = DriverManager.getConnection(databaseURL,"root",password);
                } else
                {
                      conn = DriverManager.getConnection(databaseURL,userName,password);
                }
            } else
            {
                 conn = DriverManager.getConnection(databaseURL, userName, password);
            }
        }
        catch (Exception e)
        {
            this.errorString = e.getMessage();  
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
    }    
  // operations

    public void setTableName(String _tableName) {        
        tableName = _tableName;
    } // end setTableName        

    public void setDatabaseName(String _databaseName) {        
        databaseName = _databaseName;
    } // end setDatabaseName        

    public void addInputFieldList(String name, String value) {        
        inputFieldList.put((Object)name,(Object)value);
    } // end setDatabaseName        

    public String[] getInputFieldList() 
    {
        String[] fieldList = new String[inputFieldList.size()];
        Set keySet = inputFieldList.keySet();
        Iterator iter = keySet.iterator();
        int loop = 0;
        while (iter.hasNext())
        {
            String theKey = (String)iter.next();
            fieldList[loop] = theKey;
            loop++;
        }
        return fieldList;
    } // end getInputFieldList 
    
    
    public boolean addItemToUpdateFields(String tableName, String updateFieldName, String updateTableFieldName, String updateFieldValue, String categoryFieldName, String categoryFieldValue)
    {
       if (updateItemList == null)
       {
//updateTableName,updateFieldName, updateTableFieldName, updateFieldValue,categoryFieldName,categoryFieldValue);
           
           updateItemList = new HashMap(5);
           ArrayList keyAndValList = new ArrayList(5);
           keyAndValList.add(0,(Object)updateFieldName);
           keyAndValList.add(1,(Object)updateTableFieldName);
           keyAndValList.add(2,(Object)updateFieldValue);
           keyAndValList.add(3,(Object)categoryFieldName);
           keyAndValList.add(4,(Object)categoryFieldValue);
           updateItemList.put((Object)tableName ,(Object)keyAndValList.clone());
       } else
       {
           ArrayList keyAndValList = new ArrayList(5);
           keyAndValList.add(0,(Object)updateFieldName);
           keyAndValList.add(1,(Object)updateTableFieldName);
           keyAndValList.add(2,(Object)updateFieldValue);
           keyAndValList.add(3,(Object)categoryFieldName);
           keyAndValList.add(4,(Object)categoryFieldValue);
           updateItemList.put((Object)tableName ,(Object)keyAndValList.clone());
       }
       return true;
    }

    public String decrypCBC(String binHexMessage, byte[] dbKey, int binHexMessageSize)
    {
           BlowfishCBC bfcbc = new BlowfishCBC(dbKey, CBCIV_START);
           byte[] messbuf = new byte[binHexMessageSize];
           int buffSize = 50;
           
           int intResult = BinConverter.binHexToBytes(binHexMessage,messbuf,0,0,binHexMessageSize);
           bfcbc.setCBCIV(CBCIV_START);
           bfcbc.decrypt(messbuf);
           bfcbc.cleanUp();
           String result =  new String(messbuf).trim();
           return result;
    }
    
    
     public String encrypCBC(String inMessage, byte[] dbKey)
     {
           BlowfishCBC bfcbc = new BlowfishCBC(dbKey, CBCIV_START);
           dbKey = null;
           byte[] tempbuf = new byte[MAX_MESS_SIZE];
           tempbuf = inMessage.getBytes();
           int nMessSize = inMessage.length();
           byte[] messbuf;
           int nRest = nMessSize & 7;
           if (nRest != 0) {
             messbuf = new byte[(nMessSize & (~7)) + 8];
             System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);

             for (int nI = nMessSize; nI < messbuf.length ; nI++) 
               messbuf[nI] = 0x20;
               //System.out.println("message with " + nMessSize + " bytes aligned to " + messbuf.length + " bytes");
           }
           else {
             messbuf = new byte[nMessSize];
             System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);
           };
           byte[] showIV = new byte[BlowfishCBC.BLOCKSIZE];
           bfcbc.getCBCIV(showIV);
           //System.out.println("CBC IV : " + BinConverter.bytesToBinHex(showIV));
           bfcbc.encrypt(messbuf);
           bfcbc.cleanUp();
           // return the result
           String result = BinConverter.bytesToBinHex(messbuf);
           return result;
     }

     public String insertFromItemUpdateFields(byte[] dbKey) throws MTException 
     {
         //HashMap fieldListItems = (HashMap)controller.tablesTextList.get(tableName);
         String newKey = null;
         try
         {
             Set updateItemListKeySet = updateItemList.keySet();
             Iterator updateItemListIterator = updateItemListKeySet.iterator();
             while (updateItemListIterator.hasNext()) {
                 String theTableName = (String)updateItemListIterator.next();
                 ArrayList keyAndValList = (ArrayList)updateItemList.get((Object)theTableName);
                 String updateKeyName = (String)keyAndValList.get(0);
                 String updateFieldName = (String)keyAndValList.get(1);
                 String updateFieldValue = (String)keyAndValList.get(2);
                 String categoryFieldName = (String)keyAndValList.get(3);
                 String categoryFieldValue = (String)keyAndValList.get(4);
                 String theEnValue = "";
                 if (InfoManager.ENCRYPTED_DATABASE == true)
                 {
                    theEnValue = encrypCBC(updateFieldValue,dbKey);
                 } else
                 {
                     theEnValue = updateFieldValue;
                 }
                 newKey = insertKrData(theTableName,updateKeyName, updateFieldName,theEnValue,categoryFieldName,categoryFieldValue);
             }
         }
         catch (Exception e)
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return newKey;
         
     }

    /*public String[] getDateFieldList() 
    {
        
        Set keySet = dateFieldList.keySet();
        Iterator iter = keySet.iterator();
        int loop = 0;
        ArrayList tempList = new ArrayList(5);
        while (iter.hasNext())
        {
            String theKey = (String)iter.next();
            tempList = (ArrayList)dateFieldList.get(theKey);
        }
        String[] fieldList = new String[tempList.size()];
        for (loop = 0; loop < fieldList.length; loop++)
        {
           fieldList[loop] = (String)tempList.get(loop);
        }

        return fieldList;
    } // end getInputFieldList        
    */
    public HashMap getDateFieldList() 
    {
        return dateFieldList;
    } // end getInputFieldList        

    public void addWhereFieldList(String name, String value) 
    {
        ArrayList tempList;
        if (whereFieldList.containsKey(name))
        {
            tempList = (ArrayList)whereFieldList.get(name);
            tempList.add((Object)value);
            whereFieldList.put((Object)name,(Object)tempList);
        } else
        {
            tempList = new  ArrayList();
            tempList.add((Object)value);
            whereFieldList.put((Object)name,(Object)tempList);
        }
    } // end setDatabaseName        

    public void addWhereFieldList(String name, String[] values) 
    {
        ArrayList tempList = new ArrayList(values.length);
        for(int loop = 0; loop < values.length; loop++)
        {
           tempList.add(loop,(Object)values[loop]); 
        }
        clearWhereFieldList(name);
        whereFieldList.put((Object)name,(Object)tempList);
    } // end addWhereFieldList        

    public void clearWhereFieldList(String name)
    {
        if (whereFieldList.containsKey(name))
        {
           whereFieldList.remove(name);
        }
    }
    
    public void addDateFieldList(String name, String value) 
    {
        //ArrayList tempList;
        //if (dateFieldList.containsKey(name))
        //{
        //    tempList = (ArrayList)dateFieldList.get(name);
        //    tempList.add((Object)value);
        //    dateFieldList.put((Object)name,(Object)tempList);
        //} else
        //{
        //    tempList = new  ArrayList();
        //    tempList.add((Object)value);
        //    dateFieldList.put((Object)name,(Object)tempList);
        //}
        dateFieldList.put((Object)name,(Object)value);
    } // end addDateFieldList        
    
    public int clearTableData()  throws MTException
    {
        try
        {
            String sql = "delete from " + tableName;
            mtUtils.logSQLData(sql, theOS);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end delete all Data
  
    public void setPassword(String _password) {        
        password = _password;
    } // end setPassword        

    public void setUserName(String _userName) {        
        userName = _userName;
    } // end setUserName        

    public int insertData() throws Exception {        
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";

        try {
            
            String sql = "insert into " + tableName + " (";
            String sqlValPart =  "values (";
            Set keySet = inputFieldList.keySet();
            Iterator iter = keySet.iterator();
            while (iter.hasNext())
            {
                String theKey = (String)iter.next();
                String theValue = (String)inputFieldList.get((Object)theKey);
                colType = dataTable.getColumnType(theKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                   theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                   theValue = "\"" +  theValue + "\"";
                }  else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                   theValue = "\"" +  theValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                   theValue = "\"" +  theValue + "\"";
                }

                if(iter.hasNext() == false)
                {
                  sql = sql + theKey + ") ";
                  sqlValPart = sqlValPart +  theValue; 
                } else
                {
                    sql = sql + dataTable.getColumnName(loop) + ",";
                    sqlValPart = sqlValPart +  theValue + ","; 
                }
            }
            sql = sql + sqlValPart + ")";
            mtUtils.logSQLData(sql, theOS);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end insertData        

    public String insertKrData(String tableName, String keyName, String fieldName, String inputData) throws Exception {        
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String theKeyValue = null;
        String tmpColumnName = "";
        Statement stmt = null;
        ResultSet RS = null;


        try {
            //
            String sqlQuery = "SELECT MAX(" + keyName + ") FROM " + tableName + ";";
            String maxKeyName = "MAX(" + keyName + ")";
            stmt = conn.createStatement();
            RS = stmt.executeQuery(sqlQuery);
            while (RS.next())
            {
               theKeyValue = String.valueOf(RS.getInt(maxKeyName)); 
            }
            if ((theKeyValue == null) || (theKeyValue.length() == 0))
            {
               theKeyValue = "0";  
            }
            //increment key value;
            theKeyValue = String.valueOf(Integer.parseInt(theKeyValue) + 1);
            RS.close();
            stmt.close();
            String sql = "insert into " + tableName + " (" + fieldName + "," + keyName + ") values ('" + inputData + "'," + theKeyValue + ");";
            //mtUtils.logSQLData(sql, theOS);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        //catch (MTException e)
        //{
        //    throw new MTException(e.getExceptionType(), e.getErrorMessage());
        //}
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return theKeyValue;
    } // end insertKRData        

    public String insertKrData(String tableName, String updateKeyName, String fieldName, String inputData, String categoryName, String categoryValue) throws Exception {        
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String theKeyValue = null;
        String tmpColumnName = "";
        Statement stmt = null;
        ResultSet RS = null;


        try {
            //
            
            String sqlQuery = "SELECT MAX(" + updateKeyName + ") FROM " + tableName + ";";
            String maxKeyName = "MAX(" + updateKeyName + ")";
            String sql = null;
            stmt = conn.createStatement();
            RS = stmt.executeQuery(sqlQuery);
            while (RS.next())
            {
               theKeyValue = String.valueOf(RS.getInt(maxKeyName)); 
            }
            if ((theKeyValue == null) || (theKeyValue.length() == 0))
            {
               theKeyValue = "0";  
            }
            //increment key value;
            theKeyValue = String.valueOf(Integer.parseInt(theKeyValue) + 1);
            RS.close();
            stmt.close();
            if (tableName.equalsIgnoreCase("SYMPTOMS") == true)
            {
              sql = "insert into " + tableName + " (" + updateKeyName + "," + fieldName + ", SYSTEM_CATEGORY_MAP" + " , DATA_SOURCE_CODE) values (" + theKeyValue + ", '" + inputData.trim() + "','["  + categoryValue + "]' , 2 );";
                
            } else
            {
              sql = "insert into " + tableName + " (" + updateKeyName + "," + fieldName + ","  + categoryName + " , DATA_SOURCE_CODE) values (" + theKeyValue + ", '" + inputData.trim() + "',"  + categoryValue + " , 2 );";
            }
            //mtUtils.logSQLData(sql, theOS);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        //catch (MTException e)
        //{
        //    throw new MTException(e.getExceptionType(), e.getErrorMessage());
        //}
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return theKeyValue;
    } // end insertKRData        

    
    public int updateKrSingleField(String tableName, String keyName, String keyValue, String fieldName, String inputData) throws Exception {        
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String theKeyValue = null;
        String tmpColumnName = "";
        Statement stmt = null;
        ResultSet RS = null;


        try {
            
            String sqlQuery = "UPDATE " + tableName + " SET " + fieldName + " = '" + inputData + "' where " + keyName + " = " + keyValue + ";";
            //mtUtils.logSQLData(sqlQuery, theOS);
            PreparedStatement pst = conn.prepareStatement(sqlQuery);
            pst.executeUpdate();
            //pst = conn.prepareStatement("FLUSH TABLES;");
            //pst.executeUpdate();
            pst.close();
        }
        //catch (MTException e)
        //{
        //    throw new MTException(e.getExceptionType(), e.getErrorMessage());
        //}
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end updateKrData        

    public void updateUsingSQL(String inputSQL) throws Exception {        
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String theKeyValue = null;
        String tmpColumnName = "";
        Statement stmt = null;
        ResultSet RS = null;


        try {
            
            //mtUtils.logSQLData(sqlQuery, theOS);
            PreparedStatement pst = conn.prepareStatement(inputSQL);
            pst.executeUpdate();
            //pst = conn.prepareStatement("FLUSH TABLES;");
            //pst.executeUpdate();
            pst.close();
        }
        //catch (MTException e)
        //{
        //    throw new MTException(e.getExceptionType(), e.getErrorMessage());
        //}
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
    } // end updateKrData        
    
    // this method locks the max indexes table and increments the account number to allow for separate accesses
    public String getNextAccountNum() throws Exception {        
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String theKeyValue = null;
        String tmpColumnName = "";
        Statement stmt = null;
        ResultSet RS = null;


        try {
            Statement statmt = conn.createStatement();
            RS = statmt.executeQuery("SELECT ACCOUNT_NUMBER FROM MAX_INDEXES FOR UPDATE");
            while (RS.next())
            {
               theKeyValue = String.valueOf(RS.getInt("ACCOUNT_NUMBER")); 
            }
            RS.close();
            String strNextNumber = String.valueOf(Integer.parseInt(theKeyValue) + 1);
            String sqlUpdateQuery = "UPDATE MAX_INDEXES SET ACCOUNT_NUMBER = " + strNextNumber + ";";
            statmt.execute(sqlUpdateQuery);
            statmt.close();
            return strNextNumber;
        }
        //catch (MTException e)
        //{
        //    throw new MTException(e.getExceptionType(), e.getErrorMessage());
        //}
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
    } // end updateKrData        

    public String getKrDataItem(String tableName, String keyName, String keyValue, String fieldName) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         String theColValue = null;
         String sqlString = null;

         try
         {
             //
            sqlString = "select " + fieldName + " from " + tableName + " where " +  keyName + " = " + keyValue + ";";
            stmt = conn.createStatement();
            //mtUtils.logSQLData(sqlString, theOS);
            RS = stmt.executeQuery(sqlString);
            while (RS.next())
            {
               theColValue = String.valueOf(RS.getString(fieldName)); 
            }
            RS.close();
            stmt.close();
            if ((theColValue == null) || (theColValue.length() == 0))
            {
              return null;  
            }
         }
         //catch (MTException e)
         //{
         //   throw new MTException(e.getExceptionType(), e.getErrorMessage());
         //}
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return theColValue;
    } // end getKrDataItem        

    public ArrayList getKrDataItems(String tableName, String fieldName) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         String theColValue = null;
         String sqlString = null;
         ArrayList resultSet = new ArrayList(10);

         try
         {
            //
            sqlString = "select " + fieldName + " from " + tableName + ";";
            stmt = conn.createStatement();
            //mtUtils.logSQLData(sqlString, theOS);
            RS = stmt.executeQuery(sqlString);
            while (RS.next())
            {
               theColValue = String.valueOf(RS.getString(fieldName));
               resultSet.add((Object)theColValue.trim());
            }
            RS.close();
            stmt.close();
            if ((theColValue == null) || (theColValue.length() == 0))
            {
              return null;  
            }
         }
         //catch (MTException e)
         //{
         //   throw new MTException(e.getExceptionType(), e.getErrorMessage());
         //}
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return resultSet;
    } // end getKrDataItem        

    public int updateData() throws MTException 
    {
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";
       	String[] theColumnNames = dataTable.getColumnNames();
        String colName = "";
        String whereSQL = "";
        String theValue =  "";
        boolean keyFound = false;
        ArrayList valueList;
        boolean firstPass = true;
        int keyPos = -1;
        Set keySet = whereFieldList.keySet();
	Iterator iter = keySet.iterator();
	while (iter.hasNext())
	{
            String theKey = (String)iter.next();
            valueList = (ArrayList)whereFieldList.get((Object)theKey); 
            for (loop = 0; loop < valueList.size(); loop++)
            {
                theValue = (String)valueList.get(loop);
                colType = dataTable.getColumnType(theKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'where clause' column " + theKey + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                   theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                    theValue = "\"" +  theValue + "\"";
                }  else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                   theValue = "\"" +  theValue + "\"";
                }
                if(firstPass == true)
                {
                    whereSQL = " where " + theKey + " = " + theValue;
                    firstPass = false;
                } else
                {
                    if (loop == 0)
                    {
                        whereSQL = whereSQL +  " and " + theKey + " = " + theValue;
                    } else
                    {
                        whereSQL = whereSQL +  " or " + theKey + " = " + theValue;
                        
                    }
                }
            }//end for
        } //end while
        try {
            
            String sql = "update " + tableName + " set ";
            Set inputFieldKeySet = inputFieldList.keySet();
            Iterator inputFieldKeySetIterator = inputFieldKeySet.iterator();
            while (inputFieldKeySetIterator.hasNext())
            {
                String inputFieldKey = (String)inputFieldKeySetIterator.next();
                String inputFieldValue = (String)inputFieldList.get((Object)inputFieldKey);
                colType = dataTable.getColumnType(inputFieldKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'Input field' column " + inputFieldKey + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                }  else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                }


                if(inputFieldKeySetIterator.hasNext() == false)
                {
                  sql = sql + inputFieldKey + " = " + inputFieldValue; 
                } else
                {
                    sql = sql + inputFieldKey + " = " + inputFieldValue + ", "; 
                }
            }
            sql = sql + whereSQL;
            mtUtils.logSQLData(sql, theOS);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end updateData        

     public int insertBulkData() throws MTException 
     {
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";

        try {
            
            String sql = "insert into " + tableName + " (";
            for (int colLoop = 0; colLoop < dataTable.getColumnCount(); colLoop++)
            {
                //skip if a filler
                if (dataTable.getColumnName(colLoop).indexOf("FILLER") == 0)
                {
                       continue;
                }
                if (colLoop < dataTable.getColumnCount() - 1)
                {
                    sql = sql + dataTable.getColumnName(colLoop) + ",";
                } else
                {
                    sql = sql + dataTable.getColumnName(colLoop) + ") ";
                }
            }
            String sqlValPart =  " values (";
            for (int rowLoop = 0; rowLoop < dataTable.getRowCount(); rowLoop++)
            {
               String sqlDataValPart = "";
               String sqlDataValue = "";
               for (int colLoop = 0; colLoop < dataTable.getColumnCount(); colLoop++)
               {
                   colType = dataTable.getColumnType(colLoop);
                   sqlDataValue = dataTable.getDataAt(rowLoop,colLoop);
                   if (colType.indexOf("FILLER") == 0)
                   {
                       continue;
                   }
                   if ((sqlDataValue == null) || (sqlDataValue.length() == 0))
                   {
                     sqlDataValue = "null";  
                   }
                   if (colType.equalsIgnoreCase("VARCHAR"))
                   {
                      sqlDataValPart = sqlDataValPart + "\"" +  sqlDataValue + "\"";
                   } else
                   if (colType.equalsIgnoreCase("TEXT"))
                   {
                      sqlDataValPart = sqlDataValPart + "\"" +  sqlDataValue + "\"";
                   } else
                   if (colType.equalsIgnoreCase("MEDIUMTEXT"))
                   {
                      sqlDataValPart = sqlDataValPart + "\"" +  sqlDataValue + "\"";
                   } else
                   if (colType.equalsIgnoreCase("BINARY"))
                   {
                       sqlDataValPart =  sqlDataValPart + "\"" +  sqlDataValue + "\"";
                   } else
                   if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                   {
                      sqlDataValPart = sqlDataValPart + "\"" +  sqlDataValue + "\"";
                   } else
                   if (colType.equalsIgnoreCase("FLOAT"))
                   {
                      sqlDataValPart = sqlDataValPart +  sqlDataValue;
                   } else
                   {
                      sqlDataValPart = sqlDataValPart +  sqlDataValue; 
                   }
                   if (colLoop < dataTable.getColumnCount() - 1)
                   {
                     sqlDataValPart = sqlDataValPart + ", ";  
                   }
                }//end for colloop
                String fullSQL = sql + sqlValPart + sqlDataValPart + ")";
                mtUtils.logSQLData(fullSQL, theOS);
                PreparedStatement pst = conn.prepareStatement(fullSQL);
                pst.executeUpdate();
                pst.close();
            }//end for rowloop
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end insertData

     public int updateBulkData() throws MTException 
     {
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";
        String colName = "";
        String whereSQL = "";
        String theValue =  "";
        boolean keyFound = false;
        ArrayList valueList;
        boolean firstPass = true;
 

        try {
            
            String sql = "update " + tableName + " set ";
            firstPass = true;
            Set keySet = whereFieldList.keySet();
            Iterator iter = keySet.iterator();
            while (iter.hasNext())
            {
                String theKey = (String)iter.next();
                valueList = (ArrayList)whereFieldList.get((Object)theKey); 
                for (loop = 0; loop < valueList.size(); loop++)
                {
                    theValue = (String)valueList.get(loop);
                    colType = dataTable.getColumnType(theKey);
                    if (colType.equalsIgnoreCase("null"))
                    {
                        errorString = "Supplied 'where clause' column " + theKey + " does not exist in table";
                        return -1;
                    }
                    if (colType.equalsIgnoreCase("VARCHAR"))
                    {
                        theValue = "\"" +  theValue + "\"";
                    } else
                    if (colType.equalsIgnoreCase("BINARY"))
                    {
                       theValue = "\"" +  theValue + "\"";
                    } else
                    if ((colType.equalsIgnoreCase("TEXT")) || (colType.equalsIgnoreCase("MEDIUMTEXT")))
                    {
                        theValue = "\"" +  theValue + "\"";
                    }  else
                   if (colType.equalsIgnoreCase("FLOAT"))
                   {
                      //theValue = theValue;
                   } else
                    if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                    {
                        theValue = "\"" +  theValue + "\"";
                    }
                    if (firstPass == true)
                    {
                        whereSQL = " where " + theKey + " = " + theValue;
                        firstPass = false;
                    } else
                    {
                        if (loop == 0)
                        {
                            whereSQL = whereSQL +  " and " + theKey + " = " + theValue;
                        } else
                        {
                            whereSQL = whereSQL +  " or " + theKey + " = " + theValue;
                        
                        }
                    }
                }//end for
            } //end while
           
            for (int colLoop = 0; colLoop < dataTable.getColumnCount(); colLoop++)
            {
                colType = dataTable.getColumnType(colLoop);
                String inputFieldKey = dataTable.getColumnName(colLoop);
                String inputFieldValue = dataTable.getDataAt(0,colLoop);

                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'Input field' column " + inputFieldKey + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                }  else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("TEXT")) || (colType.equalsIgnoreCase("MEDIUMTEXT")))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if (colType.equalsIgnoreCase("FLOAT"))
                {
                   //inputFieldValue = inputFieldValue;
                }
                if((colLoop + 1) == dataTable.getColumnCount())
                {
                  sql = sql + inputFieldKey + " = " + inputFieldValue; 
                } else
                {
                    sql = sql + inputFieldKey + " = " + inputFieldValue + ", "; 
                }
            }
            sql = sql + whereSQL;
            mtUtils.logSQLData(sql, theOS);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end updateBulkData
     
     public int updateMultiData() throws MTException 
     {
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";
        String colName = "";
        String whereSQL = "";
        String theValue =  "";
        boolean keyFound = false;
        ArrayList valueList;
        boolean firstPass = true;
 

        try {
            
            for (int updateLoop = 0; updateLoop < dataTable.getRowCount(); updateLoop++)
            {
                String sql = "update " + tableName + " set ";
                Set keySet = whereFieldList.keySet();
                Iterator iter = keySet.iterator();
                firstPass = true;
                while (iter.hasNext())
                {
                    String theKey = (String)iter.next();
                    valueList = (ArrayList)whereFieldList.get((Object)theKey); 
                    theValue = (String)valueList.get(updateLoop);
                    colType = dataTable.getColumnType(theKey);
                    if (colType.equalsIgnoreCase("null"))
                    {
                        errorString = "Supplied 'where clause' column " + theKey + " does not exist in table";
                        return -1;
                    }
                    if (colType.equalsIgnoreCase("VARCHAR"))
                    {
                        theValue = "\"" +  theValue + "\"";
                    } else
                    if ((colType.equalsIgnoreCase("TEXT")) || (colType.equalsIgnoreCase("MEDIUMTEXT")))
                    {
                        theValue = "\"" +  theValue + "\"";
                    } else
                    if (colType.equalsIgnoreCase("BINARY"))
                    {
                        theValue = "\"" +  theValue + "\"";
                    } else
                    if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                    {
                        theValue = "\"" +  theValue + "\"";
                    }
                    if (firstPass == true)
                    {
                        whereSQL = " where " + theKey + " = " + theValue;
                        firstPass = false;
                    } else
                    {
                        if (loop == 0)
                        {
                            whereSQL = whereSQL +  " and " + theKey + " = " + theValue;
                        } else
                        {
                            whereSQL = whereSQL +  " or " + theKey + " = " + theValue;
                        }
                    }
                } //end while
           
                for (int colLoop = 0; colLoop < dataTable.getColumnCount(); colLoop++)
                {
                    colType = dataTable.getColumnType(colLoop);
                    String inputFieldKey = dataTable.getColumnName(colLoop);
                    String inputFieldValue = dataTable.getDataAt(updateLoop,colLoop);

                    if (colType.equalsIgnoreCase("null"))
                    {
                        errorString = "Supplied 'Input field' column " + inputFieldKey + " does not exist in table";
                        return -1;
                    }
                    if (colType.equalsIgnoreCase("VARCHAR"))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    } else
                    if ((colType.equalsIgnoreCase("TEXT")) || (colType.equalsIgnoreCase("MEDIUMTEXT")))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    } else
                    if (colType.equalsIgnoreCase("BINARY"))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    } else
                    if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    }
                    if((colLoop + 1) == dataTable.getColumnCount())
                    {
                        sql = sql + inputFieldKey + " = " + inputFieldValue; 
                    } else
                    {
                        sql = sql + inputFieldKey + " = " + inputFieldValue + ", "; 
                    }
                }
                sql = sql + whereSQL;
                mtUtils.logSQLData(sql, theOS);
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.executeUpdate();
                pst.close();
            } //end updateloop
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end updateMultiData

     
     public int updateDataFromTable() throws MTException 
     {
        // your code here
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";
        String colName = "";
        String whereSQL = "";
        String theValue =  "";
        boolean keyFound = false;
        ArrayList valueList;
        boolean firstPass = true;
 

        try {
            
            for (int updateLoop = 0; updateLoop < dataTable.getRowCount(); updateLoop++)
            {
                String keyColumn = dataTable.getKeyColumnName();
                int keyColPos = dataTable.getColumnIndex(keyColumn);
                String keyVal = dataTable.getDataAt(updateLoop,keyColPos); 
                
                String sql = "update " + tableName + " set ";
                colType = dataTable.getColumnType(keyColumn);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'where clause' column " + keyColumn + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                    keyVal = "\"" +  keyVal + "\"";
                } else
                    if ((colType.equalsIgnoreCase("TEXT")) || (colType.equalsIgnoreCase("MEDIUMTEXT")))
                    {
                        keyVal = "\"" +  keyVal + "\"";
                    } else
                        if (colType.equalsIgnoreCase("BINARY"))
                        {
                            keyVal = "\"" +  keyVal + "\"";
                        } else
                            if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                            {
                                keyVal = "\"" +  keyVal + "\"";
                            }
                whereSQL = " where " + keyColumn + " = " + keyVal;
           
                for (int colLoop = 0; colLoop < dataTable.getColumnCount(); colLoop++)
                {
                    colType = dataTable.getColumnType(colLoop);
                    String inputFieldKey = dataTable.getColumnName(colLoop);
                    String inputFieldValue = dataTable.getDataAt(updateLoop,colLoop);

                    if (colType.equalsIgnoreCase("null"))
                    {
                        errorString = "Supplied 'Input field' column " + inputFieldKey + " does not exist in table";
                        return -1;
                    }
                    if (colType.equalsIgnoreCase("VARCHAR"))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    } else
                    if ((colType.equalsIgnoreCase("TEXT")) || (colType.equalsIgnoreCase("MEDIUMTEXT")))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    } else
                    if (colType.equalsIgnoreCase("BINARY"))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    } else
                    if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                    {
                        inputFieldValue = "\"" +  inputFieldValue + "\"";
                    }
                    if((colLoop + 1) == dataTable.getColumnCount())
                    {
                        sql = sql + inputFieldKey + " = " + inputFieldValue; 
                    } else
                    {
                        sql = sql + inputFieldKey + " = " + inputFieldValue + ", "; 
                    }
                }
                sql = sql + whereSQL;
                mtUtils.logSQLData(sql, theOS);
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.executeUpdate();
                pst.close();
            } //end updateloop
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end updateMultiData

     public int deleteData()  throws MTException 
    {
        // your code here
        int loop = 0;
        int theRow = 0;
        String colType = "";
       	String[] theColumnNames = dataTable.getColumnNames();
        String whereSQL = "";
        String theValue =  "";
        boolean keyFound = false;
        boolean firstPass = true;
        int keyPos = -1;
        ArrayList valueList;
        Set keySet = whereFieldList.keySet();
	Iterator iter = keySet.iterator();
	while (iter.hasNext())
	{
            String theKey = (String)iter.next();
            valueList = (ArrayList)whereFieldList.get((Object)theKey); 
            for (loop = 0; loop < valueList.size(); loop++)
            {
                theValue = (String)valueList.get(loop);
                colType = dataTable.getColumnType(theKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'where clause' column " + theKey + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                    theValue = "\"" +  theValue + "\"";
                }
                if(firstPass == true)
                {
                    whereSQL = " where " + theKey + " = " + theValue;
                    firstPass = false;
                } else
                {
                    if (loop == 0)
                    {
                        whereSQL = whereSQL +  " and " + theKey + " = " + theValue;
                    } else
                    {
                        whereSQL = whereSQL +  " or " + theKey + " = " + theValue;
                        
                    }
                }
            }//end for
        } //end while
        try 
        {
            
            String sql = "delete from  " + tableName;
            sql = sql + whereSQL;
            mtUtils.logSQLData(sql, theOS);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
       return 0;
    } // end updateData        
    
    public int selectData() throws MTException
    {        
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";
        String theColName = "";
       	String[] theColumnNames = dataTable.getColumnNames();
        ArrayList colTypes = new ArrayList(20);
        String colName = "";
        String whereSQL = "";
        String theValue =  "";
        String theColType = "";
        String theColValue = "";
        ResultSet RS = null;
        Statement stmt = null;
        int colIndex = 0;
        ArrayList valueList;
        boolean keyFound = false;
        boolean firstPass = true;
        int keyPos = -1;
        Set keySet = whereFieldList.keySet();
	Iterator iter = keySet.iterator();
	while (iter.hasNext())
	{
            String theKey = (String)iter.next();
            valueList = (ArrayList)whereFieldList.get((Object)theKey); 
            for (loop = 0; loop < valueList.size(); loop++)
            {
                theValue = (String)valueList.get(loop);
                colType = dataTable.getColumnType(theKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'where clause' column " + theKey + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                    theValue = "\"" +  theValue + "\"";
                }

                if(firstPass == true)
                {
                    whereSQL = " where " + theKey + " = " + theValue;
                    firstPass = false;
                } else
                {
                    if (loop == 0)
                    {
                        whereSQL = whereSQL +  " and " + theKey + " = " + theValue;
                    } else
                    {
                        whereSQL = whereSQL +  " or " + theKey + " = " + theValue;
                        
                    }
                }
            }//end for
        } //end while
        try 
        {
            
            String sql = "select " ;
            
            Set inputFieldKeySet = inputFieldList.keySet();
            Iterator inputFieldKeySetIterator = inputFieldKeySet.iterator();
            while (inputFieldKeySetIterator.hasNext())
            {
                String inputFieldKey = (String)inputFieldKeySetIterator.next();
                String inputFieldValue = (String)inputFieldList.get((Object)inputFieldKey);
                colType = dataTable.getColumnType(inputFieldKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'Input field' column " + inputFieldKey + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                    inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                   inputFieldValue = "\"" +  inputFieldValue + "\"";
                }

                if(inputFieldKeySetIterator.hasNext() == false)
                {
                  sql = sql + inputFieldKey; 
                } else
                {
                    sql = sql + inputFieldKey + ", "; 
                }
            }
            sql = sql + " from " + tableName + whereSQL;
            mtUtils.logSQLData(sql, theOS);
            stmt = conn.createStatement();
            RS = stmt.executeQuery(sql);
            while (RS.next())
            {
                theRow = dataTable.getRowCount();
                dataTable.addRow(theRow);
                inputFieldKeySet = inputFieldList.keySet();
                inputFieldKeySetIterator = inputFieldKeySet.iterator();
                colIndex = 0;
                while (inputFieldKeySetIterator.hasNext())
                {
                    String inputFieldKey = (String)inputFieldKeySetIterator.next();
                    theColType = (String)dataTable.getColumnType(inputFieldKey);
                    if (theColType.equalsIgnoreCase("INT"))
                    {
                       theColValue = String.valueOf(RS.getInt(colIndex + 1)); 
                    } else
                    if (theColType.equalsIgnoreCase("VARCHAR"))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if (theColType.equalsIgnoreCase("BINARY"))
                    {
                       theColValue = "\"" +  theColValue + "\"";
                    } else
                    if (theColType.equalsIgnoreCase("TEXT"))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if ((theColType.equalsIgnoreCase("DATETIME")) || (theColType.equalsIgnoreCase("DATE")))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if (theColType.equalsIgnoreCase("MEDIUMTEXT"))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if (theColType.equalsIgnoreCase("FLOAT"))
                    {
                       theColValue = String.valueOf(RS.getFloat(colIndex + 1)); 
                    } else
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    }
                    //theColName = dataTable.getColumnName(colIndex);
                    dataTable.addElement(inputFieldKey,theColValue);
                    colIndex++;
                }
            }
            RS.close();
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return 0;
    } // end getData        

    public int selectDatabyKey() throws MTException
    {        
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";
        String theColName = "";
       	String[] theColumnNames = dataTable.getColumnNames();
        ArrayList colTypes = new ArrayList(20);
        String colName = "";
        String whereSQL = "";
        String theValue =  "";
        String theColType = "";
        String theColValue = "";
        ResultSet RS = null;
        Statement stmt = null;
        int colIndex = 0;
        ArrayList valueList;
        boolean keyFound = false;
        boolean firstPass = true;
        int keyPos = -1;
        int colLoop = 0;
        
        Set keySet = whereFieldList.keySet();
	Iterator iter = keySet.iterator();
        if (iter.hasNext() == false) return 0;
	while (iter.hasNext())
	{
            String theKey = (String)iter.next();
            valueList = (ArrayList)whereFieldList.get((Object)theKey); 
            for (loop = 0; loop < valueList.size(); loop++)
            {
                theValue = (String)valueList.get(loop);
                colType = dataTable.getColumnType(theKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'where clause' column " + theKey + " does not exist in table";
                    return -1;
                }
                if (colType.indexOf("FILLER") == 0)
                {
                    continue;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                   theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                    theValue = "\"" +  theValue + "\"";
                }

                if(firstPass == true)
                {
                    whereSQL = " where " + theKey + " = " + theValue;
                    firstPass = false;
                } else
                {
                    if (loop == 0)
                    {
                        whereSQL = whereSQL +  " and " + theKey + " = " + theValue;
                    } else
                    {
                        whereSQL = whereSQL +  " or " + theKey + " = " + theValue;
                        
                    }
                }
            }//end for
        } //end while
        try 
        {
            
            String sql = "select " ;
            for (colLoop = 0; colLoop < theColumnNames.length; colLoop++)
            {
                String inputFieldKey = (String)theColumnNames[colLoop];
                if (inputFieldKey.indexOf("FILLER") == 0)
                {
                    continue;
                }

                colType = dataTable.getColumnType(inputFieldKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'Input field' column " + inputFieldKey + " does not exist in table";
                    return -1;
                }
                if((colLoop + 1) == theColumnNames.length)
                {
                  sql = sql + inputFieldKey; 
                } else
                {
                    sql = sql + inputFieldKey + ", "; 
                }
            }
            sql = sql + " from " + tableName + whereSQL;
            mtUtils.logSQLData(sql, theOS);
            stmt = conn.createStatement();
            RS = stmt.executeQuery(sql);
            while (RS.next())
            {
                theRow = dataTable.getRowCount();
                dataTable.addRow(theRow);
                colIndex = 0;
                for (colLoop = 0; colLoop < theColumnNames.length; colLoop++)
                {
                    String inputFieldKey = (String)theColumnNames[colLoop];
                    if (inputFieldKey.indexOf("FILLER") == 0)
                    {
                       continue;
                    }
                    theColType = (String)dataTable.getColumnType(inputFieldKey);
                    if (theColType.equalsIgnoreCase("INT"))
                    {
                       theColValue = String.valueOf(RS.getInt(colIndex + 1)); 
                    } else
                    if (theColType.equalsIgnoreCase("VARCHAR"))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if ((theColType.equalsIgnoreCase("TEXT")) || (theColType.equalsIgnoreCase("MEDIUMTEXT")) || (theColType.equalsIgnoreCase("BINARY")))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if ((theColType.equalsIgnoreCase("DATETIME")) || (theColType.equalsIgnoreCase("DATE")))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if (theColType.equalsIgnoreCase("FLOAT"))
                    {
                       theColValue = String.valueOf(RS.getFloat(colIndex + 1)); 
                    }
                    //theColName = dataTable.getColumnName(colIndex);
                    dataTable.addElement(inputFieldKey,theColValue);
                    colIndex++;
                }
            }
            RS.close();
            stmt.close();
            
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return 0;
    } // end getData        

    public int selectDatabyKeys() throws MTException
    {        
        int loop = 0;
        int theRow = 0;
        String dataValue = "";
        String colType = "";
        String tmpColumnName = "";
        String theColName = "";
       	String[] theColumnNames = dataTable.getColumnNames();
        ArrayList colTypes = new ArrayList(20);
        String colName = "";
        String whereSQL = "";
        String theValue =  "";
        String theColType = "";
        String theColValue = "";
        ResultSet RS = null;
        Statement stmt = null;
        int colIndex = 0;
        ArrayList valueList;
        boolean keyFound = false;
        boolean firstPass = true;
        int keyPos = -1;
        int colLoop = 0;
        
        Set keySet = whereFieldList.keySet();
	Iterator iter = keySet.iterator();
	while (iter.hasNext())
	{
            String theKey = (String)iter.next();
            valueList = (ArrayList)whereFieldList.get((Object)theKey); 
            for (loop = 0; loop < valueList.size(); loop++)
            {
                theValue = (String)valueList.get(loop);
                colType = dataTable.getColumnType(theKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'where clause' column " + theKey + " does not exist in table";
                    return -1;
                }
                if (colType.equalsIgnoreCase("VARCHAR"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("BINARY"))
                {
                   theValue = "\"" +  theValue + "\"";
                } else
                if (colType.equalsIgnoreCase("TEXT"))
                {
                    theValue = "\"" +  theValue + "\"";
                } else
                if ((colType.equalsIgnoreCase("DATETIME")) || (colType.equalsIgnoreCase("DATE")))
                {
                    theValue = "\"" +  theValue + "\"";
                }

                if(firstPass == true)
                {
                    whereSQL = " where " + theKey + " = " + theValue;
                    firstPass = false;
                } else
                {
                    if (loop == 0)
                    {
                        whereSQL = whereSQL +  " and " + theKey + " = " + theValue;
                    } else
                    {
                        whereSQL = whereSQL +  " or " + theKey + " = " + theValue;
                        
                    }
                }
            }//end for
        } //end while
        try 
        {
            
            String sql = "select " ;
            for (colLoop = 0; colLoop < theColumnNames.length; colLoop++)
            {
                String inputFieldKey = (String)theColumnNames[colLoop];
                colType = dataTable.getColumnType(inputFieldKey);
                if (colType.equalsIgnoreCase("null"))
                {
                    errorString = "Supplied 'Input field' column " + inputFieldKey + " does not exist in table";
                    return -1;
                }
                if((colLoop + 1) == theColumnNames.length)
                {
                  sql = sql + inputFieldKey; 
                } else
                {
                    sql = sql + inputFieldKey + ", "; 
                }
            }
            sql = sql + " from " + tableName + whereSQL;
            mtUtils.logSQLData(sql, theOS);
            stmt = conn.createStatement();
            RS = stmt.executeQuery(sql);
            while (RS.next())
            {
                theRow = dataTable.getRowCount();
                dataTable.addRow(theRow);
                colIndex = 0;
                for (colLoop = 0; colLoop < theColumnNames.length; colLoop++)
                {
                    String inputFieldKey = (String)theColumnNames[colLoop];
                    theColType = (String)dataTable.getColumnType(inputFieldKey);
                    if (theColType.equalsIgnoreCase("INT"))
                    {
                       theColValue = String.valueOf(RS.getInt(colIndex + 1)); 
                    } else
                    if (colType.equalsIgnoreCase("BINARY"))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1));
                    } else
                    if (theColType.equalsIgnoreCase("VARCHAR"))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if ((theColType.equalsIgnoreCase("TEXT")) || (theColType.equalsIgnoreCase("MEDIUMTEXT")))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if ((theColType.equalsIgnoreCase("DATETIME")) || (theColType.equalsIgnoreCase("DATE")))
                    {
                       theColValue = String.valueOf(RS.getString(colIndex + 1)); 
                    } else
                    if (theColType.equalsIgnoreCase("FLOAT"))
                    {
                       theColValue = String.valueOf(RS.getFloat(colIndex + 1)); 
                    }
                    //theColName = dataTable.getColumnName(colIndex);
                    dataTable.addElement(inputFieldKey,theColValue);
                    colIndex++;
                }
            }
            RS.close();
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return 0;
    } // end getData        

    public HashMap getLookupData(String SQLString) throws MTException
    {
       ResultSet RS = null;
       Statement stmt = null;
       String fieldKey = null;
       String fieldValue = null;
       HashMap lookupTableData = new HashMap(20);
       try 
       {
            
            stmt = conn.createStatement();
            mtUtils.logSQLData(SQLString, theOS);
            RS = stmt.executeQuery(SQLString);
            while (RS.next())
            {
                fieldKey = String.valueOf(RS.getInt(1)); 
                fieldValue = String.valueOf(RS.getString(2));
                lookupTableData.put((Object)fieldKey, (Object)fieldValue);
            }
            RS.close();
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return lookupTableData;
    } // end getlookupData        

    public HashMap getExamLookupData() throws MTException
    {
       ResultSet RS = null;
       Statement stmt = null;
       String fieldKey = null;
       String fieldKeyValue = null;
       String fieldKeyDescription = null;
       String fieldValue = null;
       String mainSQLString = null;
       HashMap lookupTableData = new HashMap(20);
       HashMap mainLookUpData = new HashMap(150);
       HashMap lookupTableData1 = new HashMap(20);
       
       try 
       {
            
            stmt = conn.createStatement();
            String typeSQLString = "select EXAM_SUB_TYPE_CODE, EXAM_TYPE_DESCRIPTION FROM EXAM_TYPE";
            RS = stmt.executeQuery(typeSQLString);
            while (RS.next())
            {
                fieldKey = String.valueOf(RS.getInt(1)); 
                fieldValue = String.valueOf(RS.getString(2));
                lookupTableData.put((Object)fieldKey, (Object)fieldValue);
            }
            
            Set examTypeSet = lookupTableData.keySet();
            Iterator examTypeSetIter = examTypeSet.iterator();
            while (examTypeSetIter.hasNext())
            {
               fieldKeyValue = (String)examTypeSetIter.next();
               fieldKeyDescription = (String)lookupTableData.get((Object)fieldKeyValue);
               //ok now use fieldkeyValue to extract the exam types for that value.
               mainSQLString = "select EXAM_SUB_TYPE_SEQ_NUM, EXAM_SUB_TYPE_DESCRIPTION from EXAMINATION_TYPES where EXAM_SUB_TYPE_CODE = " + fieldKeyValue + ";";
               RS = stmt.executeQuery(mainSQLString);
               lookupTableData1.clear();
               while (RS.next())
               {
                   fieldKey = String.valueOf(RS.getInt(1)); 
                   fieldValue = String.valueOf(RS.getString(2));
                   lookupTableData1.put((Object)fieldKey, (Object)fieldValue);
               }
               // ok we now have the exam data for one type, so put it in main container
               mainLookUpData.put((Object)fieldKeyDescription + "_CODE",(Object)lookupTableData1.clone());
            }
            RS.close();
            stmt.close();
            return mainLookUpData;
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
    } // end getlookupData        
    
    public HashMap getGroupedLookupData(String SQLString, String groupField, String TableName) throws MTException
    {
       ResultSet RS = null;
       Statement stmt = null;
       String fieldKey = null;
       String fieldValue = null;
       String fullSQLString = null;
       int groupLoop = 0;
       HashMap groupedData = null;
       HashMap groupedCollection = new HashMap(10);
       ArrayList groupList = new ArrayList(10);
       try 
       {
            
            stmt = conn.createStatement();
            fullSQLString = "select " + groupField + " from " + TableName;
            RS = stmt.executeQuery(fullSQLString);
            while (RS.next())
            {
                fieldKey = String.valueOf(RS.getInt(1)); 
                groupList.add((Object)fieldKey);
            }
            for (groupLoop = 0; groupLoop < groupList.size(); groupLoop++)
            {
                String groupByVal = (String)groupList.get(groupLoop);
                fullSQLString = SQLString + " where " + groupField + " = " + groupByVal;
                mtUtils.logSQLData(fullSQLString, theOS);
                RS = stmt.executeQuery(fullSQLString);
                groupedData = new HashMap(20);
                while (RS.next())
                {
                    fieldKey = String.valueOf(RS.getInt(1)); 
                    fieldValue = String.valueOf(RS.getString(2));
                    groupedData.put((Object)fieldKey, (Object)fieldValue);
                }
                //insert decrypt stuff here
                if (InfoManager.ENCRYPTED_DATABASE == true)
                {
                    byte[] dbKey =   mtUtils.composeKey();
                    byte[] checkKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                    boolean dummyFlag = true;
                    for (int k = 0; k < dbKey.length; k++)
                    {
                        if (dbKey[k] !=  checkKey[k])
                        {
                            dummyFlag = false;
                            break;
                        }
                    }
                    if (dummyFlag == false)
                    {
                        int userNameSize = userName.length();
                        int amtNames = (int)Math.ceil((double)36 /(double)userNameSize);
                        String concatName = "";
                        for (int cLoop = 0; cLoop < amtNames; cLoop++)
                        {
                            concatName = concatName + userName;
                        }
                        String strMashKey = concatName.substring(0,36);
                        byte[] mashKey =   strMashKey.getBytes();
                        for (int x = 0; x < mashKey.length; x++)
                        {
                            dbKey[x] = (byte)((int)dbKey[x] ^ (int)mashKey[x]);
                        }
                        Set crFieldSet = groupedData.keySet();
                        Iterator crFieldIter = crFieldSet.iterator();
                        while (crFieldIter.hasNext())
                        {
                            String keyName = (String)crFieldIter.next();
                            String theValue = (String)groupedData.get((Object)keyName);
                            int fullSize = theValue.length();
                            int encStringSize = fullSize >> 1;
                            String decryptString  = decrypCBC(theValue, dbKey, encStringSize);
                            groupedData.put((Object)keyName,(Object)decryptString);
                        }
                    }
                }
                groupedCollection.put((Object)groupByVal,(Object)groupedData);
            }
            RS.close();
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return groupedCollection;
    } // end getGroupedLookupData        

    public HashMap getGroupedLookupData(String SQLString, String groupField, String TableName, String groupTableName) throws MTException
    {
       ResultSet RS = null;
       Statement stmt = null;
       String fieldKey = null;
       String fieldValue = null;
       String fullSQLString = null;
       int groupLoop = 0;
       HashMap groupedData = null;
       HashMap groupedCollection = new HashMap(10);
       ArrayList groupList = new ArrayList(10);
       try 
       {
            
            stmt = conn.createStatement();
            fullSQLString = "select " + groupField + " from " + groupTableName;
            RS = stmt.executeQuery(fullSQLString);
            while (RS.next())
            {
                fieldKey = String.valueOf(RS.getInt(1)); 
                groupList.add((Object)fieldKey);
            }
            for (groupLoop = 0; groupLoop < groupList.size(); groupLoop++)
            {
                String groupByVal = (String)groupList.get(groupLoop);
                fullSQLString = SQLString + " where " + groupField + " = " + groupByVal;
                mtUtils.logSQLData(fullSQLString, theOS);
                RS = stmt.executeQuery(fullSQLString);
                groupedData = new HashMap(20);
                while (RS.next())
                {
                    fieldKey = String.valueOf(RS.getInt(1)); 
                    fieldValue = String.valueOf(RS.getString(2));
                    //groupedData.put((Object)fieldKey, (Object)fieldValue);
                if (InfoManager.ENCRYPTED_DATABASE == true)
                {
                    byte[] dbKey =   mtUtils.composeKey();
                    byte[] checkKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                    boolean dummyFlag = true;
                    for (int k = 0; k < dbKey.length; k++)
                    {
                        if (dbKey[k] !=  checkKey[k])
                        {
                            dummyFlag = false;
                            break;
                        }
                    }
                    if (dummyFlag == false)
                    {
                        int userNameSize = userName.length();
                        int amtNames = (int)Math.ceil((double)36 /(double)userNameSize);
                        String concatName = "";
                        for (int cLoop = 0; cLoop < amtNames; cLoop++)
                        {
                            concatName = concatName + userName;
                        }
                        String strMashKey = concatName.substring(0,36);
                        byte[] mashKey =   strMashKey.getBytes();
                        for (int x = 0; x < mashKey.length; x++)
                        {
                            dbKey[x] = (byte)((int)dbKey[x] ^ (int)mashKey[x]);
                        }
                        //Set crFieldSet = groupedData.keySet();
                        //Iterator crFieldIter = crFieldSet.iterator();
                        //while (crFieldIter.hasNext())
                        //{
                        //    String keyName = (String)crFieldIter.next();
                        //    String theValue = (String)groupedData.get((Object)keyName);
                            int fullSize = fieldValue.length();
                            int encStringSize = fullSize >> 1;
                            String decryptString  = decrypCBC(fieldValue, dbKey, encStringSize);
                            groupedData.put((Object)fieldKey,(Object)decryptString);
                        //}
                    } else
                    {
                      groupedData.put((Object)fieldKey, (Object)fieldValue);
                    }
                } else
                {
                      groupedData.put((Object)fieldKey, (Object)fieldValue);
                }
                }
                groupedCollection.put((Object)groupByVal,(Object)groupedData.clone());
                groupedData.clear();
            }
            RS.close();
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return groupedCollection;
    } // end getGroupedLookupData        

    public HashMap getGroupedLookupDataByMap(String SQLString1, String SQLString2, HashMap groupList, String mapTableName) throws MTException
    {
       ResultSet RS = null;
       Statement stmt = null;
       String fieldKey = null;
       String fieldValue = null;
       String fullSQLString = null;
       String itemListSQL = null;
       int groupLoop = 0;
       HashMap groupedData = new HashMap(5000);
       HashMap groupedCollection = new HashMap(10000);
       HashMap keyAndNameCollection = new HashMap(10000);
       HashMap keyAndCategoryCollection = new HashMap(10000);
       ArrayList keyList = new ArrayList(3000);
       try 
       {
            
            stmt = conn.createStatement();
            
            keyAndNameCollection = getLookupData(SQLString1);
            keyAndCategoryCollection = getLookupData(SQLString2); 
            
            if (InfoManager.ENCRYPTED_DATABASE == true) {
                byte[] dbKey =   mtUtils.composeKey();
                byte[] checkKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
                boolean dummyFlag = true;
                for (int k = 0; k < dbKey.length; k++) {
                    if (dbKey[k] !=  checkKey[k]) {
                        dummyFlag = false;
                        break;
                    }
                }
                if (dummyFlag == false) {
                    int userNameSize = userName.length();
                    int amtNames = (int)Math.ceil((double)36 /(double)userNameSize);
                    String concatName = "";
                    for (int cLoop = 0; cLoop < amtNames; cLoop++) {
                        concatName = concatName + userName;
                    }
                    String strMashKey = concatName.substring(0,36);
                    byte[] mashKey =   strMashKey.getBytes();
                    for (int x = 0; x < mashKey.length; x++) {
                        dbKey[x] = (byte)((int)dbKey[x] ^ (int)mashKey[x]);
                    }
                    Set crFieldSet = keyAndNameCollection.keySet();
                    Iterator crFieldIter = crFieldSet.iterator();
                    while (crFieldIter.hasNext())
                    {
                        String keyName = (String)crFieldIter.next();
                        String theValue = (String)keyAndNameCollection.get((Object)keyName);
                        int fullSize = theValue.length();
                        int encStringSize = fullSize >> 1;
                        String decryptString  = decrypCBC(theValue, dbKey, encStringSize);
                        keyAndNameCollection.put((Object)keyName,(Object)decryptString);
                    }
                }
            }

            Set groupFieldSet = groupList.keySet();
            Iterator groupFieldIter = groupFieldSet.iterator();
            while (groupFieldIter.hasNext()) 
            {
                String groupCode = ((String)groupFieldIter.next());
                groupedData.clear();
                Set keyAndCategorySet = keyAndCategoryCollection.keySet();
                Iterator keyAndCategoryIter = keyAndCategorySet.iterator();
                while (keyAndCategoryIter.hasNext()) {
                    String keyName = (String)keyAndCategoryIter.next();
                    String theValue = (String)keyAndCategoryCollection.get((Object)keyName);
                    //do the TokenIterator thing
                    StringTokenizer categoryTokens = new StringTokenizer(theValue,"[],");
                    while (categoryTokens.hasMoreTokens()) {
                        String tmpCatCode = ((String)categoryTokens.nextToken()).trim();
                        if (tmpCatCode.equalsIgnoreCase(groupCode) == true) {
                            groupedData.put((Object)keyName,(Object)keyAndNameCollection.get((Object)keyName));
                            break;
                        }
                        
                    }
                }
                // ok now build up the group data
                groupedCollection.put((Object)groupCode,(Object)groupedData.clone());
                groupedData.clear();
                
            }
            //mtUtils.logSQLData(fullSQLString, theOS);
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return groupedCollection;
    } // end getGroupedLookupData        

    public HashMap getLookupDataCombine(String SQLString, String[] combineList) throws MTException
    {
       ResultSet RS = null;
       Statement stmt = null;
       String fieldKey = null;
       String fieldValue = "";
       HashMap lookupTableData = new HashMap(20);
       try 
       {
            
            stmt = conn.createStatement();
            mtUtils.logSQLData(SQLString, theOS);
            RS = stmt.executeQuery(SQLString);
            while (RS.next())
            {
                fieldKey = String.valueOf(RS.getInt(1));
                fieldValue = "";
                for (int combineLoop = 0; combineLoop < combineList.length; combineLoop++)
                {    
                    //fieldValue = fieldValue + String.valueOf(RS.getString(combineLoop+2)) + " ";
                    fieldValue = fieldValue + String.valueOf(RS.getString(combineList[combineLoop])) + " ";
                    
                }
                lookupTableData.put((Object)fieldKey, (Object)fieldValue);
            }
            RS.close();
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return lookupTableData;
    } // end getData        

    public String getSingleLookupDataCombine(String SQLString, String[] combineList) throws MTException
    {
       ResultSet RS = null;
       Statement stmt = null;
       String fieldKey = null;
       String fieldValue = "";
       String returnString = null;
       try 
       {
            
            stmt = conn.createStatement();
            mtUtils.logSQLData(SQLString, theOS);
            RS = stmt.executeQuery(SQLString);
            while (RS.next())
            {
                //fieldKey = String.valueOf(RS.getInt(1));
                //fieldValue = "";
                for (int combineLoop = 0; combineLoop < combineList.length; combineLoop++)
                {    
                    fieldValue = fieldValue + String.valueOf(RS.getString(combineList[combineLoop])) + " ";
                }
                returnString = fieldValue;
                break;
            }
            RS.close();
            stmt.close();
        }
        catch (MTException e)
        {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
        }
        catch (Exception e) 
        {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
        }
        return returnString;
    } // end getData        

    public ResultSet instSQLFetch(String instSQLString) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;

         try
         {
            
            stmt = conn.createStatement();
            mtUtils.logSQLData(instSQLString, theOS);
            RS = stmt.executeQuery(instSQLString);
         }
         catch (MTException e)
         {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
         }
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return RS;
    } // end instSQLFetch        

    public String instSQLSingleItemFetch(String instSQLString, String fieldName, String fieldType) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         String theColValue = null;

         try
         {
             
            stmt = conn.createStatement();
            mtUtils.logSQLData(instSQLString, theOS);
            RS = stmt.executeQuery(instSQLString);
            while (RS.next())
            {
                  if (fieldType.equalsIgnoreCase("INT"))
                  {
                      theColValue = String.valueOf(RS.getInt(fieldName)); 
                  } else
                  if ( (fieldType.equalsIgnoreCase("VARCHAR")) || (fieldType.equalsIgnoreCase("BINARY")))
                  {
                     theColValue = String.valueOf(RS.getString(fieldName)); 
                  } else
                  if (fieldType.equalsIgnoreCase("TEXT"))
                  {
                     theColValue = String.valueOf(RS.getString(fieldName)); 
                  } else
                  if ((fieldType.equalsIgnoreCase("DATETIME")) || (fieldType.equalsIgnoreCase("DATE")))
                  {
                     theColValue = String.valueOf(RS.getTimestamp(fieldName)); 
                  } else
                  if (fieldType.equalsIgnoreCase("FLOAT"))
                  {
                     theColValue = String.valueOf(RS.getFloat(fieldName)); 
                  }
             }
            RS.close();
            stmt.close();
            if ((theColValue == null) || (theColValue.length() == 0))
            {
              theColValue = "0";  
            }
         }
         catch (MTException e)
         {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
         }
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return theColValue;
    } // end instSQLSingleItemFetch        
    
    public ArrayList instSQLSingleItemListFetch(String instSQLString, String fieldName, String fieldType) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         ArrayList theColValue = new ArrayList(10);

         try
         {
             
            stmt = conn.createStatement();
            mtUtils.logSQLData(instSQLString, theOS);
            RS = stmt.executeQuery(instSQLString);
            
            while (RS.next())
            {
                  if (fieldType.equalsIgnoreCase("INT"))
                  {
                      theColValue.add((Object)String.valueOf(RS.getInt(fieldName))); 
                  } else
                  if (fieldType.equalsIgnoreCase("VARCHAR"))
                  {
                     theColValue.add((Object)String.valueOf(RS.getString(fieldName))); 
                  } else
                  if ((fieldType.equalsIgnoreCase("TEXT")) || (fieldType.equalsIgnoreCase("BINARY")))
                  {
                     theColValue.add((Object)String.valueOf(RS.getString(fieldName))); 
                  } else
                  if ((fieldType.equalsIgnoreCase("DATETIME")) || (fieldType.equalsIgnoreCase("DATE")))
                  {
                     theColValue.add((Object)String.valueOf(RS.getTimestamp(fieldName))); 
                  } else
                  if (fieldType.equalsIgnoreCase("FLOAT"))
                  {
                     theColValue.add((Object)String.valueOf(RS.getFloat(fieldName))); 
                  }
                  
             }
            RS.close();
            stmt.close();
         }
         catch (MTException e)
         {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
         }
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return theColValue;
    } // end instSQLSingleItemListFetch        

    public String instSQLSingleItemFetchDecr(String instSQLString, String fieldName, String fieldType) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         String theColValue = null;

         try
         {
             
            stmt = conn.createStatement();
            mtUtils.logSQLData(instSQLString, theOS);
            RS = stmt.executeQuery(instSQLString);
            while (RS.next())
            {
                  if (fieldType.equalsIgnoreCase("INT"))
                  {
                      theColValue = String.valueOf(RS.getInt(fieldName)); 
                  } else
                  if ( (fieldType.equalsIgnoreCase("VARCHAR")) || (fieldType.equalsIgnoreCase("BINARY")))
                  {
                     theColValue = String.valueOf(RS.getString(fieldName)); 
                  } else
                  if (fieldType.equalsIgnoreCase("TEXT"))
                  {
                     theColValue = String.valueOf(RS.getString(fieldName)); 
                  } else
                  if ((fieldType.equalsIgnoreCase("DATETIME")) || (fieldType.equalsIgnoreCase("DATE")))
                  {
                     theColValue = String.valueOf(RS.getTimestamp(fieldName)); 
                  } else
                  if (fieldType.equalsIgnoreCase("FLOAT"))
                  {
                     theColValue = String.valueOf(RS.getFloat(fieldName)); 
                  }
             }
            RS.close();
            stmt.close();
            if ((theColValue == null) || (theColValue.length() == 0))
            {
              theColValue = "0";  
            }
         }
         catch (MTException e)
         {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
         }
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         if (InfoManager.ENCRYPTED_DATABASE == true)
         {
             byte[] dbKey =   mtUtils.composeKey();
             byte[] checkKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
             boolean dummyFlag = true;
             for (int k = 0; k < dbKey.length; k++)
             {
                 if (dbKey[k] !=  checkKey[k])
                 {
                     dummyFlag = false;
                     break;
                 }
             }
             if (dummyFlag == false)
             {
                 
                 int userNameSize = userName.length();
                 int amtNames = (int)Math.ceil((double)36 /(double)userNameSize);
                 String concatName = "";
                 for (int cLoop = 0; cLoop < amtNames; cLoop++)
                 {
                     concatName = concatName + userName;
                 }
                 String strMashKey = concatName.substring(0,36);
                 byte[] mashKey =   strMashKey.getBytes();
                 for (int x = 0; x < mashKey.length; x++)
                 {
                     dbKey[x] = (byte)((int)dbKey[x] ^ (int)mashKey[x]);
                 }
                 String theValue = theColValue;
                 int fullSize = theValue.length();
                 int encStringSize = fullSize >> 1;
                 String decryptString  = decrypCBC(theValue, dbKey, encStringSize);
                 theColValue = decryptString;
             }
         }//end if enc db
         return theColValue;
    } // end instSQLSingleItemFetch        

    public ArrayList instSQLSingleItemListFetchDecr(String instSQLString, String fieldName, String fieldType) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         ArrayList theColValue = new ArrayList(10);

         try
         {
             
            stmt = conn.createStatement();
            mtUtils.logSQLData(instSQLString, theOS);
            RS = stmt.executeQuery(instSQLString);
            
            while (RS.next())
            {
                  if (fieldType.equalsIgnoreCase("INT"))
                  {
                      theColValue.add((Object)String.valueOf(RS.getInt(fieldName))); 
                  } else
                  if (fieldType.equalsIgnoreCase("VARCHAR"))
                  {
                     theColValue.add((Object)String.valueOf(RS.getString(fieldName))); 
                  } else
                  if ((fieldType.equalsIgnoreCase("TEXT")) || (fieldType.equalsIgnoreCase("BINARY")))
                  {
                     theColValue.add((Object)String.valueOf(RS.getString(fieldName))); 
                  } else
                  if ((fieldType.equalsIgnoreCase("DATETIME")) || (fieldType.equalsIgnoreCase("DATE")))
                  {
                     theColValue.add((Object)String.valueOf(RS.getTimestamp(fieldName))); 
                  } else
                  if (fieldType.equalsIgnoreCase("FLOAT"))
                  {
                     theColValue.add((Object)String.valueOf(RS.getFloat(fieldName))); 
                  }
                  
             }
            RS.close();
            stmt.close();
         }
         catch (MTException e)
         {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
         }
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         //now decrypt the data
         if (InfoManager.ENCRYPTED_DATABASE == true)
         {
             byte[] dbKey =   mtUtils.composeKey();
             byte[] checkKey = "abcdefghijklmnopqrstuvwxyz1234567890".getBytes();
             boolean dummyFlag = true;
             for (int k = 0; k < dbKey.length; k++)
             {
                 if (dbKey[k] !=  checkKey[k])
                 {
                     dummyFlag = false;
                     break;
                 }
             }
             if (dummyFlag == false)
             {
                 
                 int userNameSize = userName.length();
                 int amtNames = (int)Math.ceil((double)36 /(double)userNameSize);
                 String concatName = "";
                 for (int cLoop = 0; cLoop < amtNames; cLoop++)
                 {
                     concatName = concatName + userName;
                 }
                 String strMashKey = concatName.substring(0,36);
                 byte[] mashKey =   strMashKey.getBytes();
                 for (int x = 0; x < mashKey.length; x++)
                 {
                     dbKey[x] = (byte)((int)dbKey[x] ^ (int)mashKey[x]);
                 }
                 for (int loop = 0; loop < theColValue.size(); loop++)
                 {
                     String theValue = (String)theColValue.get(loop);
                     int fullSize = theValue.length();
                     int encStringSize = fullSize >> 1;
                     String decryptString  = decrypCBC(theValue, dbKey, encStringSize);
                     theColValue.add(loop,(Object)decryptString);
                 }
             }
         }//end if enc db

         
         
         return theColValue;
    } // end instSQLSingleItemListFetch        

    
    public HashMap instSQLDoubleItemListFetch(String instSQLString, String fieldName1, String fieldType1, String fieldName2, String fieldType2) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         HashMap itemList = new HashMap(10);
         String hashKey, hashVal = "";

         try
         {
             
            stmt = conn.createStatement();
            mtUtils.logSQLData(instSQLString, theOS);
            RS = stmt.executeQuery(instSQLString);
            
            while (RS.next())
            {
                 if (fieldType1.equalsIgnoreCase("INT"))
                 {
                      hashKey = String.valueOf(RS.getInt(fieldName1)); 
                      if (fieldType2.equalsIgnoreCase("INT"))
                      {
                          hashVal = String.valueOf(RS.getInt(fieldName2)); 
                      } else
                      if ((fieldType2.equalsIgnoreCase("VARCHAR")) || (fieldType2.equalsIgnoreCase("BINARY")))
                      {
                         hashVal = String.valueOf(RS.getString(fieldName2)); 
                      } else
                      if ((fieldType2.equalsIgnoreCase("TEXT")) || (fieldType2.equalsIgnoreCase("MEDIUMTEXT")))
                      {
                         hashVal = String.valueOf(RS.getString(fieldName2)); 
                      } else
                      if ((fieldType2.equalsIgnoreCase("DATETIME")) || (fieldType2.equalsIgnoreCase("DATE")))
                      {
                          hashVal = String.valueOf(RS.getTimestamp(fieldName2)); 
                      } else
                      if (fieldType2.equalsIgnoreCase("FLOAT"))
                      {
                        hashVal = String.valueOf(RS.getFloat(fieldName2)); 
                      }
                      itemList.put((Object)hashKey,(Object)hashVal);
                 }
             }
            RS.close();
            stmt.close();
         }
         catch (MTException e)
         {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
         }
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return itemList;
    } // end instSQLSingleItemListFetch        
    
    public HashMap instSQLTripleItemListFetch(String instSQLString, String fieldName1, String fieldType1, String fieldName2, String fieldType2, String fieldName3, String fieldType3) throws MTException  
    {       
         ResultSet RS = null;
         Statement stmt = null;
         HashMap itemList = new HashMap(10);
         ArrayList valueList = null;
         String hashKey, hashVal = "";

         try
         {
             
            stmt = conn.createStatement();
            mtUtils.logSQLData(instSQLString, theOS);
            RS = stmt.executeQuery(instSQLString);
            
            while (RS.next())
            {
                 valueList = new ArrayList(10);
                 if (fieldType1.equalsIgnoreCase("INT"))
                 {
                      hashKey = String.valueOf(RS.getInt(fieldName1)); 
                      if (fieldType2.equalsIgnoreCase("INT"))
                      {
                          hashVal = String.valueOf(RS.getInt(fieldName2));
                          valueList.add(hashVal);
                      } else
                      if ((fieldType2.equalsIgnoreCase("VARCHAR")) || (fieldType2.equalsIgnoreCase("BINARY")))
                      {
                         hashVal = String.valueOf(RS.getString(fieldName2)); 
                         valueList.add(hashVal);
                      } else
                      if ((fieldType2.equalsIgnoreCase("TEXT")) || (fieldType2.equalsIgnoreCase("MEDIUMTEXT")))
                      {
                         hashVal = String.valueOf(RS.getString(fieldName2));
                         valueList.add(hashVal);
                     } else
                      if ((fieldType2.equalsIgnoreCase("DATETIME")) || (fieldType2.equalsIgnoreCase("DATE")))
                      {
                          hashVal = String.valueOf(RS.getTimestamp(fieldName2)); 
                          valueList.add(hashVal);
                      }
                      if (fieldType3.equalsIgnoreCase("INT"))
                      {
                          hashVal = String.valueOf(RS.getInt(fieldName3));
                          valueList.add(hashVal);
                      } else
                      if ((fieldType3.equalsIgnoreCase("VARCHAR")) || (fieldType3.equalsIgnoreCase("BINARY")))
                      {
                         hashVal = String.valueOf(RS.getString(fieldName3)); 
                         valueList.add(hashVal);
                      } else
                      if ((fieldType3.equalsIgnoreCase("TEXT")) || (fieldType3.equalsIgnoreCase("MEDIUMTEXT")))
                      {
                         hashVal = String.valueOf(RS.getString(fieldName3));
                         valueList.add(hashVal);
                      } else
                      if ((fieldType3.equalsIgnoreCase("DATETIME")) || (fieldType3.equalsIgnoreCase("DATE")))
                      {
                          hashVal = String.valueOf(RS.getTimestamp(fieldName3)); 
                          valueList.add(hashVal);
                      } else
                      if (fieldType3.equalsIgnoreCase("FLOAT"))
                      {
                          hashVal = String.valueOf(RS.getFloat(fieldName3)); 
                          valueList.add(hashVal);
                      }
                      itemList.put((Object)hashKey,(Object)valueList);
                 }
             }
            RS.close();
            stmt.close();
         }
         catch (MTException e)
         {
            throw new MTException(e.getExceptionType(), e.getErrorMessage());
         }
         catch (Exception e) 
         {
            this.errorString = e.getMessage(); 
            throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
         }
         return itemList;
    } // end instSQLSingleItemListFetch        

    public void closeConnection()
    {
        try
        {
            if (conn != null)
            {
               conn.close();
               conn = null;
            }
        }
        catch (Exception exc)
        {
           
        }
    }
    
    public String getErrorString() {        
        // your code here
        return errorString;
    } // end getErrorString        

    public boolean isAutoCommitFlag() {        
        return autoCommitFlag;
    } // end isAutoCommitFlag        

    public void setAutoCommitFlag(boolean _autoCommitFlag) {        
        autoCommitFlag = _autoCommitFlag;
    } // end setAutoCommitFlag        

} // end dbAccess



