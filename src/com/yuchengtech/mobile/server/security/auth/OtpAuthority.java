package com.yuchengtech.mobile.server.security.auth;


public interface OtpAuthority  {

	public int authorizeRequest(
			String id, String password );
	
}
