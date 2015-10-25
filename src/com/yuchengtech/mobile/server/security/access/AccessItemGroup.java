package com.yuchengtech.mobile.server.security.access;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AccessItemGroup {

	private AccessInfo accessInfo = new AccessInfo();

	private String id;

	private String label;
	
	private String desc;
	
	private Map accessItems = new HashMap();

	/**
	 * ��󲢷���
	 */
	private int maxConcurrent = 10;

	/**
	 * ������������
	 */
	private int slope = 0;

	public AccessItemGroup() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addAccessItem(AccessItem item) {
		this.accessItems.put(item.getId(), item);
	}

	public AccessItem getAccessItem(String accessItemId) {
		return (AccessItem)this.accessItems.get(accessItemId);
	}

	public Map getAccessItems() {
		return accessItems;
	}

	public AccessInfo getAccessInfo() {
		return accessInfo;
	}

	public void setAccessInfo(AccessInfo accessInfo) {
		this.accessInfo = accessInfo;
	}

	public boolean contains(String accessItemId) {
		return this.accessItems.containsKey(accessItemId);
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<AccessItemGroup id="+this.id+" maxConcurrent="+this.maxConcurrent+" slope="+slope+" >");

		Iterator it = accessItems.keySet().iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			AccessItem item = (AccessItem)accessItems.get(key);
			sb.append(item);	
		}
		sb.append("</AccessItemGroup>");
		return sb.toString();
	}
}
