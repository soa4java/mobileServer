package com.yuchengtech.mobile.server.security.auth;


public class SimpleOtpAuthority extends  BaseOtpAuthority implements OtpAuthority  {
	
	public SimpleOtpAuthority() {
		
	}

	protected boolean validRequester(String id) {
		if(id == null || id.length() == 0)
			return false; 
		
		return true; 
	}


}
