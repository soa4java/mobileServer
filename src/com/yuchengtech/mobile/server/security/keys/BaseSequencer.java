package com.yuchengtech.mobile.server.security.keys;

import java.util.Stack;


 
public abstract class BaseSequencer {
	  
	protected int maxValues = PassKeySequencer.DEFUALT_MAX_VALUES;
	protected Stack<String> passwordLifo; 
	
	
	protected abstract String genSeed(); 
	public abstract String encode (String value);
	
	
	protected  String nextValue(String value) {
		return encode(value);
	}
	
	
	public BaseSequencer() {
		passwordLifo = new Stack<String>(); 
	}
	
	public BaseSequencer(int n) { 
		maxValues = n;
		passwordLifo = new Stack<String>(); 
	}
	
 
	public String nextValue() {
		return passwordLifo.pop();
	}
		 
    public void genValues(int n) {
    	passwordLifo.removeAllElements();
		String value = genSeed(); 
		for (int i=0; i < n ; i++) {
			value = nextValue(value);
			passwordLifo.add(value);
		}
		
    }
		
    public void genValues() {
    	genValues(maxValues);
    }
	
	
}
