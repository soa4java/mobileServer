package com.yuchengtech.mobile.server.web.utils;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptUtils {
	public static final byte[] Key = "abcdefgh".getBytes();
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
													// DES,DESede,Blowfish


	public static byte[] encrypt(byte[] keybyte, byte[] src) {
		try { 
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		return null;
	}

	// 解密字符串

	public static byte[] decrypt(byte[] keybyte, byte[] src) {
		try {  
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);

		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		return null;
	}

	public static void main(String[] args) { // 添加新安全算法,如果用JCE就要把它添加进去
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final byte[] keyBytes = Key; // 8字节的密钥
		String szSrc = "这是一个测试abcd";
		System.out.println("加密前的字符串:" + szSrc);
		byte[] encoded = encrypt(keyBytes, szSrc.getBytes());
		System.out.println("加密后的字符串:" + new String(encoded));
		byte[] srcBytes = decrypt(keyBytes, encoded);
		System.out.println("解密后的字符串:" + (new String(srcBytes)));

	}
}
