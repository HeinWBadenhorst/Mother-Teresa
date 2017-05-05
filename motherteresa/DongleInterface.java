
/*
 * DongleInterface.java
 *
 * Created on 23 March 2004, 10:54
 */

package motherteresa;

/**
 *
 * @author  Hein Badenhorst
 */
public class DongleInterface
{
    
    /** Creates a new instance of DongleInterface */
    public DongleInterface()
    {
    }
    
    
    public native void JSLInitializeLink();
    
    public native boolean JSLInitialize(byte[] HSKey);
    
    public native boolean JSLAuthenticate();
    
    public native boolean JSLWrite(byte[] key, int keyLength, int offset, byte[] buffer, int bufferLength);
    
    public native boolean JSLRead(byte[] key, int keyLength, int offset, byte[] buffer, int bufferLength);
    
    public native boolean JSLEncrypt(byte[] key, int keyLength, int offset, byte[] buffer, int bufferLength);
    
    public native boolean JSLDecrypt(byte[] key, int keyLength, int offset, byte[] buffer, int bufferLength);
    
    static {
    System.loadLibrary("DongleInterface");
    }
}
