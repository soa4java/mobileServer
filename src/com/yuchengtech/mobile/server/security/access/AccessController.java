package com.yuchengtech.mobile.server.security.access;

import com.yuchengtech.mobile.server.web.MobileException;

public interface AccessController {

	/**
	 * 检查是否可以访问accessItemId指定的业务。
	 * 如果可以访问，则返回对应的访问控制对象，
	 * 如果没有定义访问控制，则直接返回null，否则抛出相关异常。
	 * 
	 * @param context 被访问控制的资源结点对象
	 * @param requestObj 请求对象
	 * @param actionId 被访问控制的请求ID
	 * @return Object 根据实现情况返回的控制对象，在beginAccess和endAccess方法中作为参数传入。
	 * @throws EMPException
	 */
	public Object checkAccess( Object requestObj, String actionId) throws MobileException;
	
	/**
	 * 开始访问actionId指定的业务。
	 * 
	 * @param accessInfo 该传入对象为checkAccess方法的返回对象
	 */
	public void beginAccess(Object accessInfo );
	
	/**
	 * 结束访问actionId指定的业务
	 * 
	 * @param accessInfo 该传入对象为checkAccess方法的返回对象
	 * @param beginTimeStamp 被控制对象的开始执行时间
	 */
	public void endAccess(Object accessInfo, long beginTimeStamp);
}
