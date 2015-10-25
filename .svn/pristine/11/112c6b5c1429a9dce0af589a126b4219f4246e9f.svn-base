package com.yuchengtech.mobile.server.web;

public class MobileException extends Exception 
{
	
	/**
	 * 错误码, 10000 系统内部错误
	 */
	private String errorCode = "10000";
	
	
	/**
	 * 显示给用户的错误信息，可以是多语言资源中的资源ID
	 */
	private String showMessage;
	
	
   /**
    * 引起此异常的真正异常，通常在对系统运行中抛出异常进行二次封装时使用
    */
	private Throwable cause;
   
   /**
    *  构造器方法。
    */
   public MobileException() 
   {
    
   }
  
   public MobileException(String message )
   {
	   super( message );
   }
 
   public MobileException(String errorCode, String message )
   {
	   super( message );
	   this.errorCode = errorCode;
   }
    
   public MobileException(String message, Throwable cause) 
   {
	   super(message, cause);
	   this.cause = cause;
    
   }
 
   public MobileException(String errorCode, String message, Throwable cause) 
   {
	   super(message, cause);
	   this.errorCode = errorCode;
	   this.cause = cause;
    
   }
   
    
   public String getMessage()
   {
	   String msg = super.getMessage();
	   if(this.errorCode != null){
		   if(msg != null)
			   msg = "[error_code:"+this.errorCode+"] " + msg;
		   else
			   msg = "[error_code:"+this.errorCode+"]";
	   }
	   
	   String causeMsg = null;
	   if( cause != null )
		   causeMsg = cause.getMessage();
	   
	   if( msg != null )
	   {
		   if( causeMsg != null )
			   return msg + " caused by: " + causeMsg;
		   else
			   return msg;
	   }
	   else
	   {
		   return causeMsg;
	   }
   }
    
   public MobileException(Throwable cause) 
   {
	   super(cause);
	   this.cause = cause;
   }
    
   public String toString() 
   {
	   if( cause == null )
		   return super.toString();
	   else
		   return super.toString() + " cause: " + cause.toString(); 
   }
 
   public String getErrorCode() {
	   return errorCode;
   }
    
   public void setErrorCode(String errorCode) {
	   this.errorCode = errorCode;
   }
  
   public String getShowMessage() {
	   if( showMessage == null )
		   return super.getMessage();
	   return showMessage;
   }
    
   public void setShowMessage(String showMessage) {
	   this.showMessage = showMessage;
   }
}