package com.yuchengtech.mobile.server.security.keys;


 
public interface PassKeySequencer {
	
	/*
	 * global statics 
	 */
	public static final int DEFUALT_MAX_VALUES = 25; 
	public static final String  DEFAULT_SEQUENCE_TYPE = "hash";
	
	
	/*
	 * Public Interface 
	 */
	public  String nextValue(); // release next pass key (predecessor of last value) 
	public  String encode(String value); // apply the operation that generates the successor value for the provided value
	
    public void genValues(int n); // create a new sequence of N key elements 
    public void genValues();   // create a new sequence of key elements 
	
    
}
