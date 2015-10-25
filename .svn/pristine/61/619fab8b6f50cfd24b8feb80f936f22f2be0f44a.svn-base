package com.yuchengtech.mobile.server.security.keys;



public class StringSequencer extends BaseSequencer implements PassKeySequencer  {

	public  String encode (String value) {
		StringBuffer buffer = new StringBuffer(value.length());
		buffer.append(value);
		return buffer.reverse().toString();
	}

	
	protected  String genSeed() {
		return "token" + Math.random();
	}
	

}
