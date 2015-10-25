package com.yuchengtech.mobile.server.web.service;

public class BaseService implements Service {

	/**
	 * Service名称
	 */
	private String name;
	
	/**
	 * Service别名
	 */
	private String alias;
	
	/**
	 * 中文名称
	 */
	private String label;
	
	/**
	 * 描述
	 */
	private String desc; 
	
	 

	/**
	 * 终止Service，释放资源。
	 */
	public void terminate() {

	}

	/**
	 * 获得Service的名称。
	 * 
	 * @return Service名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获得Service的别名。
	 * 
	 * @return Service别名
	 */
	public String getAlias() {
		return alias;
	}
	
	/**
	 * 判断是否唯一实例化。
	 * 
	 * @return 是否唯一实例化
	 */
	public boolean isSingleton() {
		return false;
	}

	/**
	 * 设置Service的别名。
	 * 
	 * @param alias Service别名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 设置Service的名称。
	 * 
	 * @param name Service名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获得Service的名称。
	 * 
	 * @return Service名称
	 */
	public String getId() {
		
		return this.name;
	}
	
	/**
	 * 设置Service的名称。setName的等价方法。
	 * 
	 * @param value Service名称
	 */
	public void setId(String value) {
		name = value;
	}

	/**
	 * 获得中文名称。
	 * 
	 * @return 中文名称
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 设置中文名称。
	 * 
	 * @param label 中文名称
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 获得描述。
	 * 
	 * @return 描述
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置描述。
	 * 
	 * @param desc 描述
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}