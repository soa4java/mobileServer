package com.yuchengtech.mobile.server.security.auth;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {
	private String did;
	private String clientInitKey;
	private String channel;
	private String token;
	private String username;
	private String ip;
	private long loginTime;
	private long lastVistTime;
	
	private static long visitInterval=10*1000;//2次访问时间差
	private List<String> URLList = new ArrayList<String>();

	public long getLastVistTime() {
		return lastVistTime;
	}

	public void setLastVistTime(long lastVistTime) {
		this.lastVistTime = lastVistTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public String getClientInitKey() {
		return clientInitKey;
	}

	public void setClientInitKey(String clientInitKey) {
		this.clientInitKey = clientInitKey;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getURLList() {
		return URLList;
	}
	public boolean request(String hashURL){
		if(URLList.contains(hashURL)||token==null){
			return false;
		}
		
		if(URLList.size()>20){
			URLList.remove(0);
		}
		URLList.add(hashURL);
		//lastVisitTime=System.currentTimeMillis();
		return true;
	}
}
