package com.yuchengtech.mobile.server.security.auth;

import java.util.Hashtable;
import java.util.Map;

import com.yuchengtech.mobile.server.security.keys.PassKeySequencer;
import com.yuchengtech.mobile.server.security.keys.PassKeySequencerFactory;
 
public abstract class BaseOtpAuthority implements  OtpAuthority{
	
	private Map<String,String>  requesterMap; 
	private PassKeySequencer passKeySequence; 

	
	public BaseOtpAuthority() {
		init(); 
	}
	
  
	protected void init() {
		
		requesterMap = new Hashtable<String,String>(83); 
		passKeySequence = PassKeySequencerFactory.createSequence();
		
		return; 
	}
	
	
	protected abstract boolean validRequester(String id) ;
	 
	protected void update(String id, String password) {
		requesterMap.put(id, password); 
	}
	
 
	protected void purgeId(String id) {
		requesterMap.remove(id); 
	}
	
	
	 
	protected boolean knownClient(String id) {
		if( requesterMap.containsKey(id) == false) {
			return false; 
		}
		return true; 
	}
	
	public boolean hello(String id, String info )  {

		if(this.validRequester(id) == true) {
			update(id, info); 
			return true;
		}
		return false;
		
	}
	protected boolean authenticate(String id, String password) {
 
		if( password == null || password.length() == 0 )
			return false; 
	 
		String real = passKeySequence.encode(password); 
		//String real = password; 
 		String found = requesterMap.get(id);
		 
		
		if(real.equals(found) == true ) {	
			update(id, password) ; 
			return true; 
		}
		
		return false; 
	}
	
 

 
	public int authorizeRequest(
			String id, String info ) {
		if( !knownClient(id)) {
			return AuthManager.UNKNOWN_CLIENT; 
		}
		if( !authenticate(id, info )) {
			return AuthManager.INVALID_PASSWORD; 
		}
		return AuthManager.CLIENT_OK; 
	}
 
	
}
