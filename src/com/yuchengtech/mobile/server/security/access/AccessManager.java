package com.yuchengtech.mobile.server.security.access;

import java.util.ArrayList;
import java.util.List;

import com.yuchengtech.mobile.server.web.MobileException;

public class AccessManager {

	
	/**
	 * 访问控制器集合
	 */
	private List accessControllers = new ArrayList();
	
	/**
	 * 访问控制器管理器标识
	 */
	private String id = null;
	
	public AccessManager() {
	}

	/**
	 * 依次调用所有访问控制器，检查是否可以访问accessItemId指定的业务。
	 * 并将访问控制器返回的访问控制对象，保存在映射表中。
	 * 
	 * @param context 被访问控制的资源结点对象
	 * @param requestObject 请求对象
	 * @param accessItemId 被访问控制的请求ID
	 * @return Object 根据实现情况返回的控制对象，在beginAccess和endAccess方法中作为参数传入。
	 * @throws EMPException
	 */
	public Object checkAccess(  Object requestObject, String accessItemId) throws MobileException {
		
		AccessControllerInfo info = new AccessControllerInfo();
		for( int i=0; i<accessControllers.size(); i++ )
		{
			AccessController controller = (AccessController)accessControllers.get( i );
			Object o = controller.checkAccess(  requestObject, accessItemId );
			if( o != null )
				info.addControllerInfo(controller, o );
		}
		if( info.hasObject() )
			return info;
		return null;
	}

	/**
	 * 依次调用所有访问控制器的开始访问方法。
	 * 
	 * @param accessInfo 该传入对象为checkAccess方法的返回对象
	 */
	public void beginAccess(Object accessInfo) {
		AccessControllerInfo ai = (AccessControllerInfo)accessInfo;
		Object ctrls[] = ai.controllers.keySet().toArray();
		for( int i=0; i<ctrls.length; i++)
		{
			AccessController controller = (AccessController)ctrls[i];
			controller.beginAccess( ai.controllers.get(controller));
		}
		
	}
	
	/**
	 * 依次调用所有访问控制器的结束访问方法。
	 * 
	 * @param accessInfo 该传入对象为checkAccess方法的返回对象
	 * @param beginTimeStamp 被控制对象的开始执行时间
	 */
	public void endAccess(Object accessInfo, long beginTimeStamp) {

		AccessControllerInfo ai = (AccessControllerInfo)accessInfo;
		Object ctrls[] = ai.controllers.keySet().toArray();
		for( int i=0; i<ctrls.length; i++)
		{
			AccessController controller = (AccessController)ctrls[i];
			controller.endAccess( ai.controllers.get(controller), beginTimeStamp );
		}
		
	}
	
	/**
	 * 增加一个访问控制器。
	 * 
	 * @param controller 访问控制器
	 */
	public void addAccessController(AccessController controller) {
		this.accessControllers.add( controller );
	}

	/**
	 * 获得访问控制器集合。
	 * 
	 * @return 访问控制器集合
	 */
	public List getAccessControllers() {
		return this.accessControllers;
	}
	
	/**
	 * 获得访问控制器管理器标识。
	 * 
	 * @return 访问控制器管理器标识
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置访问控制器管理器标识。
	 * 
	 * @param id 访问控制器管理器标识
	 */
	public void setId(String id) {
		this.id = id;
	}

}
