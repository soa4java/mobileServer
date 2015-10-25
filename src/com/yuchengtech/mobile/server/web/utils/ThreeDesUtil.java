package com.yuchengtech.mobile.server.web.utils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;



/**y
 * 
 *    
 * 类名称：ThreeDesUtil  
 * 类描述：  3des的加密及解密
 * 创建人：zhangmin  
 * 创建时间：2014-2-26 下午2:23:53  
 * 修改人：  
 * 修改时间：2014-2-26 下午2:23:53  
 * 修改备注：  
 * @version 1.0.0  
 *
 */
public class ThreeDesUtil {
	private static final String Algorithm = "DESede"; 
	//private static final String key  =  "ece3fe290144a2cb8ee400c5";
	private final static String iv = "01234567"; 
	 
	/**
	 * 
	 * @author zhangmin   
	 * encryptThreeDESECB  加密方法,将所有异常向业务层抛出
	 * @param src   需要加密的数据
	 * @return	加密以后的数据
	 * @throws Exception  String  
	 * @exception    
	 * @since  1.0.0
	 */
	public static String encryptThreeDESECB(String key,String src) throws Exception{  
		
	    DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	      
	    Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");  
	    IvParameterSpec ips = new IvParameterSpec(iv.getBytes()); 
	    cipher.init(Cipher.ENCRYPT_MODE, securekey,ips);  
	    //为保证将明文字符串转换为byte[]后和ios转换的结果一致，采用该方法进行转换
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    DataOutputStream outputstream = new DataOutputStream(baos); 
	    outputstream.writeChars(src);
 	    byte[] contents = baos.toByteArray();  
 	   
 	    byte[] b=cipher.doFinal(contents); 
	    
 	    
	    return new String(Base64.encodeBase64(b)).replaceAll("\r", "").replaceAll("\n", ""); 
	}  
	  
	/**
	 * 
	 * @author zhangmin   
	 * decryptThreeDESECB 解密方法，异常抛向业务层 
	 * @param src   需要解密的密文字符串
	 * @return  String  解密以后的明文
	 * @throws Exception  String
	 * @exception    
	 * @since  1.0.0
	 */
	public static String decryptThreeDESECB(String key,String src) throws Exception  
	{  
	    //--通过base64,将字符串转成byte数组  
	  //  BASE64Decoder decoder = new BASE64Decoder();  
	  //  byte[] bytesrc = decoder.decodeBuffer(src);  
	    byte[] bytesrc=src.getBytes();  
        //Base64 base64=new Base64();  
	    bytesrc=Base64.decodeBase64(bytesrc);  
	    
	    
	    //--解密的key  
	    DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));  
	    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Algorithm);  
	    SecretKey securekey = keyFactory.generateSecret(dks);  
	      
	    //--Chipher对象解密  
	    Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");  
	    IvParameterSpec ips = new IvParameterSpec(iv.getBytes()); 
	    cipher.init(Cipher.DECRYPT_MODE, securekey,ips);  
	    byte[] retByte = cipher.doFinal(bytesrc);  
	    //加密时，采用的是writeChars()，该方法默认的编码方式是UTF-16,因此在将解密后的byte[]转换为字符串时需要设置编码方式为UTF-16
	    return new String(retByte,"UTF-16");
 	}

	public static void main(String[] args) {
	 String key  =  "ece3fe290144a2cb8ee400c5";
		 
		String test="我爱北京天安门。====woaibeijingtiananmen.....";
		try {
			String desStr = encryptThreeDESECB(key,test);
			
			System.out.println("encryptThreeDESECB====="+desStr);
			System.out.println("decryptThreeDESECB====="+decryptThreeDESECB(key,desStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}