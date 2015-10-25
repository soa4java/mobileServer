package com.yuchengtech.mobile.server.security.auth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yuchengtech.mobile.server.common.Constants;
import com.yuchengtech.mobile.server.web.utils.CryptUtils;

public class AuthManager {
	public static boolean debug_mode = Constants.Client_debug_mode;

	public final static int CLIENT_OK = 0;//
	public final static int UNKNOWN_CLIENT = 1;
	public final static int INVALID_PASSWORD = 2;

	public final static int INVALID_REQUEST = 3;
	public final static int DECRYPT_FAIL = 4;

	private static Map<String, ClientInfo> clientMap = Collections
			.synchronizedMap(new HashMap<String, ClientInfo>());
	private static OtpAuthority otpAuthority = new SimpleOtpAuthority();

	// 客户端请求接入
	public static void initClient(String did, String random) {
		if (!((SimpleOtpAuthority) otpAuthority).hello(did, random)) {
			return;
		}
		ClientInfo c = new ClientInfo();
		c.setDid(did);
		c.setClientInitKey(random);
		clientMap.put(did, c);
	}

	// 客户端身份校验
	public static int validClient(String did, String random) {
		return otpAuthority.authorizeRequest(did, random);
	}

//	// 客户端请求基本检测
//	// 交易请求的基本校验，校验URL是否合法
//	// 完成，未联调
//	public static int basicCheck(String did, String encodeMsg,String url) {
//		if (debug_mode)
//			return CLIENT_OK;
//		// encodeMsg:did:random:
//		if (did == null || "".equals(did) || (Constants.safeCheckList.indexOf(url)!=-1&&( encodeMsg == null
//				|| "".equals(encodeMsg))) )
//			return INVALID_REQUEST;
//		ClientInfo c = clientMap.get(did);
//		if (c == null || c.getToken() == null) {
//			return UNKNOWN_CLIENT;
//		}
//       
//		if (Constants.safeCheckList.indexOf(url) != -1) {
//			byte[] srcBytes = CryptUtils.decrypt(c.getToken().getBytes(),
//					encodeMsg.getBytes());
//			if (srcBytes == null
//					|| (srcBytes != null && !(new String(srcBytes)
//							.startsWith(did)))) {
//				return DECRYPT_FAIL;
//			}
//			if (!c.request(encodeMsg)) {
//				return INVALID_REQUEST;
//			}
//		}
//		
//		return CLIENT_OK;
//	}
	
	// 方法已修改，改为监测访问的liana借口地址是否合法，暂时使用property中的safeCheckList参数，之后要改为从数据库表 TBL_LIANAINTERFACE 中读取
	public static int basicCheck(String did, String url) {
		if (debug_mode)
			return CLIENT_OK;
		if (did == null || "".equals(did) || Constants.safeCheckList.indexOf(url)==-1)
			return INVALID_REQUEST;
		ClientInfo c = clientMap.get(did);
		if (c == null || c.getToken() == null) {
			return UNKNOWN_CLIENT;
		}
		
		return CLIENT_OK;
	}

	public static ClientInfo getClientInfo(String did) {
		return clientMap.get(did);
	}

	public static String genToken() {

		try {
			String value = "t" + new Date().getTime();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(value.getBytes(), 0, value.length());
			return "" + new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(new Date().getTime());
	}
	
	public static String getTokenFromDid(String did) {

		ClientInfo c = clientMap.get(did);
		if (c == null) {
			return null;
		}else{
			return c.getToken();
		}
	}
	
}
