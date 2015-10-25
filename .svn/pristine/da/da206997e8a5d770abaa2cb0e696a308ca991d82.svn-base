package com.yuchengtech.mobile.server.security.access;
public class AccessItem {

	private AccessInfo accessInfo = new AccessInfo();
	
	private String id;
	
	/**
	 * 最大并发数
	 */
	private int maxConcurrent = 10;

	/**
	 * 并发浮动因子
	 */
	private int slope = 5;

	/**
	 * 额外并发数(用于分组并发)
	 */
	private int extra = 0;

	public AccessItem() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AccessInfo getAccessInfo() {
		return accessInfo;
	}

	public void setAccessInfo(AccessInfo accessInfo) {
		this.accessInfo = accessInfo;
	}

	public int getMaxConcurrent() {
		return maxConcurrent;
	}

	public void setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}

	public int getSlope() {
		return slope;
	}

	public void setSlope(int slope) {
		this.slope = slope;
	}

	public int getExtra() {
		return extra;
	}

	public void setExtra(int extra) {
		this.extra = extra;
	}

	public String toString() {
		return "<AccessItem id="+this.id+" maxConcurrent="+this.maxConcurrent+" slope="+slope+" extra="+extra+" />";
	}
}
