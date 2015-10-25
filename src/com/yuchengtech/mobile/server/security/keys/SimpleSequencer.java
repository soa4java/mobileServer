package com.yuchengtech.mobile.server.security.keys;


public class SimpleSequencer 
		extends BaseSequencer implements PassKeySequencer {
		
 
	public String encode(String value) {
		int ival = this.seqToInt(value);
		ival = ival += 3; 
		String sval = "" + ival; 
		
		return sval.replaceAll("3", "4");
	}
	
 
	protected String genSeed() {
		return "18"; 
	}
	
 
	private int seqToInt(String seq) {
		int ival;
		
		try {
			ival = Integer.parseInt(seq);
		}
		catch(NumberFormatException ex) {
			ival = 90890707; 
		}
		
		return ival; 
	}

}
