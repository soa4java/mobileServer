package com.yuchengtech.mobile.server.web.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;







import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES256Encryption {

	/**
	 * 加密/解密算法/工作模式/填充方式
	 * 
	 * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
	 * */
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

	/**
	 * 密钥算法 java6支持56位密钥，bouncycastle支持64位
	 * */
	private static final String KEY_ALGORITHM = "AES";

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 * */
	public static String decrypt(String secret, byte[] key) throws Exception {
		// 欢迎密钥
		Key k = toKey(key);
		byte[] data = parseStr2Byte(secret);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
//		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return new String(cipher.doFinal(data));
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密后的数据
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * */
	public static String encrypt(String str, byte[] key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
		// 还原密钥
		Key k = toKey(key);
		/**
		 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
		 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
		 */

		byte[] data = str.getBytes();
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return parseByte2Str(cipher.doFinal(data));

	}

	/**
	 * 
	 * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
	 * 
	 * @return byte[] 二进制密钥
	 * */
	private static byte[] initkey() throws Exception {
		//
		// //实例化密钥生成器
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, "BC");
		// 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
		kg.init(256);
		// kg.init(128);
		// 生成密钥
		SecretKey secretKey = kg.generateKey();
		// 获取二进制密钥编码形式
		return secretKey.getEncoded();

//		 为了便于测试，这里我把key写死了，如果大家需要自动生成，可用上面注释掉的代码
//		 return new byte[] { 0x08, 0x08, 0x04, 0x0b, 0x02, 0x0f, 0x0b, 0x0c,
//		 0x01, 0x03, 0x09, 0x07, 0x0c, 0x03, 0x07, 0x0a, 0x04, 0x0f,
//		 0x06, 0x0f, 0x0e, 0x09, 0x05, 0x01, 0x0a, 0x0a, 0x01, 0x09,
//		 0x06, 0x07, 0x09, 0x0d };
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {

		String str = "AES";
		System.out.println("原文：" + str);

		// 初始化密钥
		byte[] key;
		try {

			key = AES256Encryption.initkey();
			System.out.print("密钥16：");

			for (int i = 0; i < key.length; i++) {
				System.out.printf("%x", key[i]);
			}
			System.out.print("\n");
			System.out.print("密钥64：");
			System.out.println(parseByte2Str(key));

			// 加密数据
			String data = AES256Encryption.encrypt(str, key);
			System.out.print("加密后：");
			System.out.println(data);
			System.out.print("\n");
			for (int i = 0; i < parseStr2Byte(data).length; i++) {
				System.out.printf("%x", parseStr2Byte(data)[i]);
			}
			System.out.print("\n");
			
			data = AES256Encryption.decrypt(data, key);
			System.out.println("解密后：" + data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 将二进制转换成字符串
	 * 
	 * @param buf
	 * @return
	 */
	private static String parseByte2Str(byte buf[]) {
		BASE64Encoder base64encoder = new BASE64Encoder();
		String encode = base64encoder.encode(buf);

		return encode;
	}

	private static byte[] parseStr2Byte(String hexStr) throws AESException {

		BASE64Decoder base64decoder = new BASE64Decoder();
		byte[] encodeByte = null;
		try {
			encodeByte = base64decoder.decodeBuffer(hexStr);
			return encodeByte;
		} catch (IOException e) {
			throw new AESException(e);
		}
	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * */
	private static Key toKey(byte[] key){
		// 实例化DES密钥
		// 生成密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}
}