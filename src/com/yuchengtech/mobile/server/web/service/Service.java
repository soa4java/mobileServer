package com.yuchengtech.mobile.server.web.service;

public interface Service {

	/**
	 * 终止Service，释放资源。
	 */
	public void terminate();

	/**
	 * 获得Service的名称。
	 * 
	 * @return Service名称
	 */
	public String getName();

	/**
	 * 获得Service的别名。
	 * 
	 * @return Service别名
	 */
	public String getAlias();

	/**
	 * 判断是否唯一实例化。
	 * 
	 * @return 是否唯一实例化
	 */
	public boolean isSingleton();

}
