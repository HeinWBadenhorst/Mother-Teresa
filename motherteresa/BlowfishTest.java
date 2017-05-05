package motherteresa;

//package pracman;

/**
  * testing the Blowfish encryption algorithm classes
  * @author Markus Hahn <hahn@flix.de>
  * @version 12 Nov 98
  */

import java.io.*;
import java.util.*;


public class BlowfishTest {
 
     // max. size of message to encrypt
     final static int MAX_MESS_SIZE = 256;

     // benchmark settings
     final static int TESTBUFSIZE = 100000;
     final static int TESTLOOPS   = 40;
     
     // startup CBC IV
     final static long CBCIV_START = 0xfedcba9876543210L;
     
     /**
       * the application entry point
       */
     public static void main(String args[])
     {

         String result = null;  
         System.out.print("\nstarting selftest...");
           if (!BlowfishECB.selfTest()) {
             System.out.println(",selftest failed");
             return;
    	   };
           System.out.println(", passed.");

           // create our test key (simulate an SHA-1 digest)
           Random rand = new Random();
           byte[] testkey = new byte[20];
           for (int nI = 0; nI < 20; nI++)
             testkey[nI] = (byte) (rand.nextInt() & 0x0ff);
                 
           // do the key-setups
           System.out.print("setting up Blowfish keys...");
           BlowfishECB bfecb = new BlowfishECB(testkey);
           BlowfishCBC bfcbc = new BlowfishCBC(testkey, CBCIV_START);
           System.out.println(", done.\n");
       
           // get a message
           System.out.print("something to encrypt please >");
           System.out.flush();
           byte[] tempbuf = new byte[MAX_MESS_SIZE];
           String testString = "Of all the fishes in the sea the mermaid is the one for me.\n1 2 3 4 5 6 7 8 9 0\n!@#$%^&*()";
           tempbuf = testString.getBytes();
           int nMessSize = testString.length();
           //try { 
           //  nMessSize = System.in.read(tempbuf) - 2; 
           //}
           //catch (java.io.IOException ioe) { 
           //  return; 
           //};

           // align to the next 8 byte border
           byte[] messbuf;
           int nRest = nMessSize & 7;
           if (nRest != 0) {
             messbuf = new byte[(nMessSize & (~7)) + 8];
             System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);

             for (int nI = nMessSize; nI < messbuf.length ; nI++) 
               messbuf[nI] = 0x20;
             System.out.println("message with " + nMessSize + " bytes aligned to " + messbuf.length + " bytes");
           }
           else {
             messbuf = new byte[nMessSize];
             System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);
           };

           // ECB encryption/decryption test...
           bfecb.encrypt(messbuf);
           System.out.println("ECB encrypted message: " + BinConverter.bytesToBinHex(messbuf));
           String encBinHexString = BinConverter.bytesToBinHex(messbuf);
           try
           {
               DBAccess dbAccessInstance = new DBAccess("localhost", "medibase", "ENCRYPT_TEST", "root", "volkskas", "WINDOWS");
               String keyValue = dbAccessInstance.insertKrData("ENCRYPT_TEST","TEST_CODE","CRYP_FIELD",encBinHexString);
               result = dbAccessInstance.getKrDataItem("ENCRYPT_TEST","TEST_CODE", keyValue,"CRYP_FIELD");
           }
           catch (MTException e)
           {
//              throw new MTException(e.getExceptionType(), e.getErrorMessage());
           }
           catch (Exception e) 
           {
              String errorString = e.getMessage(); 
//              throw new MTException(InfoManager.DBACCESS_ERROR, e.getMessage());
           }

           
           // show the result
           int buffSize = 0;
           int intResult = BinConverter.binHexToBytes(result,messbuf,0,0,buffSize);//.getBytes();

           bfecb.decrypt(messbuf);
           System.out.println("ECB decrypted message: >>>" + new String(messbuf) + "<<<");

           
           /*// CBC encryption/decryption test...
           byte[] showIV = new byte[BlowfishCBC.BLOCKSIZE];
           bfcbc.getCBCIV(showIV);
           System.out.println("CBC IV : " + BinConverter.bytesToBinHex(showIV));
           bfcbc.encrypt(messbuf);
           
           // show the result
           System.out.println("CBC encrypted message: " + BinConverter.bytesToBinHex(messbuf));
           System.out.println("Messagebuffer=:" + messbuf);
           bfcbc.setCBCIV(CBCIV_START);
           bfcbc.decrypt(messbuf);
           System.out.println("CBC decrypted message: >>>" + new String(messbuf) + "<<<");
           */
           // benchmark...
           System.out.println("\nbenchmark is running, encrypting " + TESTBUFSIZE * TESTLOOPS  * 8 + 
                              " bytes\nplease wait...");  
           Date startTime = new Date();
           long[] testbuf = new long[TESTBUFSIZE];
           for (int nI = 0; nI < TESTLOOPS; nI++) {
             bfecb.encrypt(testbuf);
             System.out.print("#");
             System.out.flush();
           };
           System.out.println();
           Date stopTime = new Date();
           long lTime = stopTime.getTime() - startTime.getTime();
           float fAmount = TESTBUFSIZE * TESTLOOPS * 8;
           float fTime = lTime;
           float fRate = (fAmount * 1000) / fTime;
           System.out.println("rate: " + fRate + " bytes/sec");
       
           // don't leave any traces in the memory
           bfecb.cleanUp();
           bfcbc.cleanUp();
     }
}

