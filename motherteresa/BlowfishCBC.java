package motherteresa;

/**
  * implementation of the Blowfish encryption algorithm in CBC mode
  * @author Markus Hahn <hahn@flix.de>
  * @version 10 Nov 98
  */

public class BlowfishCBC extends BlowfishECB {


  // here we hold the CBC IV
  long m_lCBCIV;


  /**
    * get the current CBC IV (for cipher resets)
    * @return current CBC IV
    */
  public long getCBCIV() {
    return m_lCBCIV;
  };

  /**
    * get the current CBC IV (for cipher resets)
    * @param dest wher eto put current CBC IV in network byte ordered array
    */
  public void getCBCIV(byte[] dest) {
    BinConverter.longToByteArray(m_lCBCIV, dest, 0);
  };

  /**
    * set the current CBC IV (for cipher resets)
    * @param lNewCBCIV the new CBC IV
    */
  public void setCBCIV(long lNewCBCIV) {
    m_lCBCIV = lNewCBCIV;
  };

  /**
    * set the current CBC IV (for cipher resets)
    * @param newCBCIV the new CBC IV  in network byte ordered array
    */
  public void setCBCIV(byte[] newCBCIV) {
    m_lCBCIV = BinConverter.byteArrayToLong(newCBCIV, 0);
  };




  /**
    * constructor, stores a zero CBC IV
    * @param bfkey key material, up to MAXKEYLENGTH bytes
    */
  public BlowfishCBC(byte[] bfkey) {

    super(bfkey);
    // store zero CBCB IV
    setCBCIV(0);
  };


  /**
    * constructor
    * @param bfkey key material, up to MAXKEYLENGTH bytes
    * @param lInitCBCIV the CBC IV
    */
  public BlowfishCBC(byte[] bfkey, 
                     long lInitCBCIV) {

    super(bfkey);
    // store the CBCB IV
    setCBCIV(lInitCBCIV);
  };


  /**
    * constructor
    * @param bfkey key material, up to MAXKEYLENGTH bytes
    * @param lInitCBCIV the CBC IV (array with min. BLOCKSIZE bytes)
    */
  public BlowfishCBC(byte[] bfkey, 
                     byte[] initCBCIV) {

    super(bfkey);
    // store the CBCB IV
    setCBCIV(initCBCIV);
  };


  /**
    * cleans up all critical internals,
    * call this if you don't need an instance anymore
    */ 
  public void cleanUp() {

    m_lCBCIV = 0;
    super.cleanUp();
  };


  // internal routine to encrypt a block in CBC mode
  protected long encryptBlock(long lPlainblock) {

    // chain with the CBC IV
    lPlainblock ^= m_lCBCIV;
    // encrypt the block
    lPlainblock = super.encryptBlock(lPlainblock);
    // the encrypted block is the new CBC IV
    return (m_lCBCIV = lPlainblock);
  };


  // internal routine to decrypt a block in CBC mode
  protected long decryptBlock(long lCipherblock) {

    // save the current block
    long lTemp = lCipherblock;
    // decrypt the block
    lCipherblock = super.decryptBlock(lCipherblock);
    // dechain the block
    lCipherblock ^= m_lCBCIV;
    // set the new CBC IV
    m_lCBCIV = lTemp;
    // return the decrypted block
    return lCipherblock;
  };



  /**
    * encrypts a byte buffer (should be aligned to an 8 byte border) 
    * to another buffer (of the same size or bigger)
    * @param inbuffer buffer with plaintext data
    * @param outbuffer buffer to get the ciphertext data
    */
  public void encrypt(byte[] inbuffer, 
                      byte[] outbuffer) {

    int nLen = inbuffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=8) {

      // encrypt a temporary 64bit block
      lTemp = BinConverter.byteArrayToLong(inbuffer, nI);
      lTemp = encryptBlock(lTemp);
      BinConverter.longToByteArray(lTemp, outbuffer, nI);
    };
  };



  /**
    * encrypts a byte buffer (should be aligned to an 8 byte border) to itself
    * @param buffer buffer to encrypt
    */
  public void encrypt(byte[] buffer) {

    int nLen = buffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=8) {

      // encrypt a temporary 64bit block
      lTemp = BinConverter.byteArrayToLong(buffer, nI);
      lTemp = encryptBlock(lTemp);
      BinConverter.longToByteArray(lTemp, buffer, nI);
    };
  };




  /**
    * encrypts an int buffer (should be aligned to an
    * two integer border) to another int buffer (of the same 
    * size or bigger)
    * @param inbuffer buffer with plaintext data
    * @param outBuffer buffer to get the ciphertext data
    */
  public void encrypt(int[] inbuffer, 
                      int[] outbuffer) {

    int nLen = inbuffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=2) {

      // encrypt a temporary 64bit block
      lTemp = BinConverter.intArrayToLong(inbuffer, nI);
      lTemp = encryptBlock(lTemp);
      BinConverter.longToIntArray(lTemp, outbuffer, nI);
    };
  };


  /**
    * encrypts an integer buffer (should be aligned to an
    * @param buffer buffer to encrypt
    */
  public void encrypt(int[] buffer) {

    int nLen = buffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=2) {

      // encrypt a temporary 64bit block
      lTemp = BinConverter.intArrayToLong(buffer, nI);
      lTemp = encryptBlock(lTemp);
      BinConverter.longToIntArray(lTemp, buffer, nI);
    };
  };



  /**
    * encrypts a long buffer to another long buffer (of the same size or bigger)
    * @param inbuffer buffer with plaintext data
    * @param outbuffer buffer to get the ciphertext data
    */
  public void encrypt(long[] inbuffer, 
                      long[] outbuffer) {

    int nLen = inbuffer.length;
    for (int nI = 0; nI < nLen; nI++)
      outbuffer[nI] = encryptBlock(inbuffer[nI]);
  };



  /**
    * encrypts a long buffer to itself
    * @param buffer buffer to encrypt
    */
  public void encrypt(long[] buffer) {

    int nLen = buffer.length;
    for (int nI = 0; nI < nLen; nI++) {
      buffer[nI] = encryptBlock(buffer[nI]);
    };
  };



  /**
    * decrypts a byte buffer (should be aligned to an 8 byte border) 
    * to another buffer (of the same size or bigger)
    * @param inbuffer buffer with ciphertext data
    * @param outBuffer buffer to get the plaintext data
    */
  public void decrypt(byte[] inbuffer, 
                      byte[] outbuffer) {

    int nLen = inbuffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=8) {

      // decrypt a temporary 64bit block
      lTemp = BinConverter.byteArrayToLong(inbuffer, nI);
      lTemp = decryptBlock(lTemp);
      BinConverter.longToByteArray(lTemp, outbuffer, nI);
    };
  };



  /**
    * decrypts a byte buffer (should be aligned to an 8 byte border) to itself
    * @param buffer buffer to decrypt
    */
  public void  decrypt(byte[] buffer) {

    int nLen = buffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=8) {

      // decrypt over a temporary 64bit block
      lTemp = BinConverter.byteArrayToLong(buffer, nI);
      lTemp = decryptBlock(lTemp);
      BinConverter.longToByteArray(lTemp, buffer, nI);
    };
  };




  /**
    * decrypts an integer buffer (should be aligned to an
    * two integer border) to another int buffer (of the same size or bigger)
    * @param inbuffer buffer with ciphertext data
    * @param outbuffer buffer to get the plaintext data
    */
  public void decrypt(int[] inbuffer, 
                      int[] outbuffer) {

    int nLen = inbuffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=2) {

      // decrypt a temporary 64bit block
      lTemp = BinConverter.intArrayToLong(inbuffer, nI);
      lTemp = decryptBlock(lTemp);
      BinConverter.longToIntArray(lTemp, outbuffer, nI);
    };
  };


  /**
    * decrypts an int buffer (should be aligned to a
    * two integer border) 
    * @param buffer buffer to decrypt
    */
  public void decrypt(int[] buffer) {

    int nLen = buffer.length;
    long lTemp;
    for (int nI = 0; nI < nLen; nI +=2) {

      // decrypt a temporary 64bit block
      lTemp = BinConverter.intArrayToLong(buffer, nI);
      lTemp = decryptBlock(lTemp);
      BinConverter.longToIntArray(lTemp, buffer, nI);
    };
  };



  /**
    * decrypts a long buffer to another long buffer (of the same size or bigger)
    * @param inbuffer buffer with ciphertext data
    * @param outbuffer buffer to get the plaintext data
    */
  public void decrypt(long[] inbuffer, 
                      long[] outbuffer) {

    int nLen = inbuffer.length;
    for (int nI = 0; nI < nLen; nI++)
      outbuffer[nI] = decryptBlock(inbuffer[nI]);
  };



  /**
    * decrypts a long buffer to itself
    * @param buffer buffer to decrypt
    */
  public void decrypt(long[] buffer) {

    int nLen = buffer.length;
    for (int nI = 0; nI < nLen; nI++)
      buffer[nI] = decryptBlock(buffer[nI]);
  };


};
