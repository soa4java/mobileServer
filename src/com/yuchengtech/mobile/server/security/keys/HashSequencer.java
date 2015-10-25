package com.yuchengtech.mobile.server.security.keys;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


 
public class HashSequencer extends BaseSequencer implements PassKeySequencer {


	protected  String genSeed() {
		return this.getClass().getName() + new Date().getTime(); 
	}
	
	

	public String encode(String value) {

		String  rval = ""; 

        try {
                rval = genMD5(value);
        }
        catch (NoSuchAlgorithmException ex) {
                System.err.println("encode():" + ex);
                return rval;
        }
        catch (UnsupportedEncodingException ex) {
            System.err.println("encode():" + ex);
            return rval;
        }
        return rval; 

	}
	

	protected String genMD5(String value) 
	  		throws NoSuchAlgorithmException, UnsupportedEncodingException  {
	      
		   MessageDigest md = MessageDigest.getInstance("MD5");
	       md.update(value.getBytes(), 0, value.length());
	       return      byteArrayToHexString( md.digest());
	       //return "" + new BigInteger(1, md.digest()).toString(16);
	}
	private static String byteArrayToHexString(byte[] b){  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++){  
            resultSb.append(byteToHexString(b[i]));  
        }  
        return resultSb.toString();  
    }  
      
    /** 将一个字节转化成十六进制形式的字符串     */  
    private static String byteToHexString(byte b){  
        int n = b;  
        if (n < 0)  
            n = 256 + n;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",  
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};  
	public static void main(String args[]) throws Exception{
		HashSequencer h=new HashSequencer();
		String s=h.genMD5("9972bc88918691e8500a5fec0998974c");
		System.out.println(s);
		
	}
 

}
