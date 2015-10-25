package com.yuchengtech.mobile.server.security.auth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ClientTest {

	protected int maxValues = 2;
	protected Stack<String> passwordLifo;

	protected String genSeed() {
		return this.getClass().getName() + new Date().getTime();
	}

	public String encode(String value) {

		String rval = "";

		try {
			rval = genMD5(value);
		} catch (NoSuchAlgorithmException ex) {
			System.err.println("encode():" + ex);
			return rval;
		} catch (UnsupportedEncodingException ex) {
			System.err.println("encode():" + ex);
			return rval;
		}
		return rval;

	}

	protected String genMD5(String value) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(value.getBytes(), 0, value.length());
		return "" + new BigInteger(1, md.digest()).toString(16);
	}

	protected String nextValue(String value) {
		return encode(value);
	}

	public ClientTest() {
		passwordLifo = new Stack<String>();
	}

	public ClientTest(int n) {
		maxValues = n;
		passwordLifo = new Stack<String>();
	}

	public String nextValue() {
		return passwordLifo.pop();
	}

	public void genValues(int n) {
		passwordLifo.removeAllElements();
		String value = genSeed();
		for (int i = 0; i < n; i++) {
			value = nextValue(value);
			passwordLifo.add(value);
		}

	}

	public void genValues() {
		genValues(maxValues);
	}

	public static void main(String args[]) throws Exception {
		ClientTest test = new ClientTest();
		test.genValues();
		String did="abcdef";
		String password1 = test.nextValue();
		String password2 = test.nextValue();
		//f941c8739dabbfcecb36cfe05b70f69d:97742f9fda223b9fe3b0851edf56c487

		System.out.println(password1+":"+password2);
		String random="97742f9fda223b9fe3b0851edf56c487";
		
		DefaultHttpClient httpclient = new DefaultHttpClient(); // 实例化一个HttpClient

		HttpResponse response = null;

		HttpEntity entity = null;


		HttpPost httpost = new HttpPost("http://127.0.0.1:8081/mobileServer/mb.do"); // 引号中的参数是：servlet的地址

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair("d",did));
		nvps.add(new BasicNameValuePair("r", random));//第二回合修改
	 	nvps.add(new BasicNameValuePair("c", "android"));//第一回合先注释掉
	 	//ClientInfo c= AuthManager.getClientInfo(did);
	 	//byte[] msg = CryptUtils.encrypt(c.getToken().getBytes(), "fb459fa9a4dd803ebcc788ef236c33b5+com.citic.iphone+84:38:35:4A:3A:A8+1".getBytes());
	 	nvps.add(new BasicNameValuePair("e", "fb459fa9a4dd803ebcc788ef236c33b5+com.citic.iphone+84:38:35:4A:3A:A8+1"));//第一回合先注释掉
		
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8)); // 将参数传入post方法中

		response = httpclient.execute(httpost); // 执行

		entity = response.getEntity(); // 返回服务器响应

		System.out.println("----------------------------------------");
		String responseString = null;
		if (response.getEntity() != null) {
			responseString = EntityUtils.toString(response.getEntity());
			System.out.println(responseString); // 打印出服务器响应的HTML代码
		}
		System.out.println("Login form get: " + response.getStatusLine());

	}
}
