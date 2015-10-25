package com.yuchengtech.mobile.server.security.keys;

public class PassKeySequencerFactory {
	
 
	static public PassKeySequencer createSequence(String classPath) {

		PassKeySequencer passKeySequence = null;  
	
        try{
            Class theClass = Class.forName( classPath );
            passKeySequence = (PassKeySequencer)theClass.newInstance();
	    }
	    catch( Exception e ){
	    	 
	    }
	    if( passKeySequence == null ) {
	    	passKeySequence = new HashSequencer();  
	    }
		
		return passKeySequence; 
	}
	
 
	static public PassKeySequencer createSequence() { 
		
		String seqType; 
		ObjectAttributes attributes; 
		attributes = ObjectAttributes.createObjectAttributes("PassKeySequencerFactory");
		attributes.loadAttributes();
		seqType = attributes.getString("sequenceType");
		//System.out.println("client attributes: " + attributes.toString());
		if(seqType == null) {
			seqType = PassKeySequencer.DEFAULT_SEQUENCE_TYPE;
		}
		
		return createSequence(seqType); 
	}
	
	

}
