package com.yuchengtech.mobile.server.security.access;

import java.util.HashMap;
import java.util.Map;

public class AccessControllerInfo {

	Map controllers = new HashMap();
	
	public AccessControllerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addControllerInfo(AccessController controller, Object accessInfo )
	{
		controllers.put( controller, accessInfo );
	}
	
	public boolean hasObject()
	{
		return !controllers.isEmpty();
	}
}
