package evoparsons.rmishared;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Auth implements Serializable {
    private static final long serialVersionUID = 1L;
    //public final int id;
    private String sid; 
    private String ssig; 
    private String skey;
    public String getSid() {
        return sid; 
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getSsig() {
        return ssig;
    }
    public void setSsig(String ssig) {
        this.ssig = ssig;
    }
    public String getSkey() {
        return skey;
    }
    public void setSkey(String skey) {
        if (skey != null && !skey.isEmpty())
            this.skey = skey;
    }
    public Auth() {}
    public Auth(String sid, String ssig, String skey) {
        //this.id = id; 
        this.sid = sid; 
        this.ssig = ssig; 
        this.skey = skey;
    }

    private static String byteToHex(final byte[] hash)
    {
        try (Formatter formatter = new Formatter())
        {
            for (byte b : hash)
            {
                formatter.format("%02x", b);
            }
            String result = formatter.toString();
            formatter.close();
            return result;
        }
    }    

    public static String sha1(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }    
}