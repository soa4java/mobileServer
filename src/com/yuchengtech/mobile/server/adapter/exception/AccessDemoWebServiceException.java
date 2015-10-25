package com.yuchengtech.mobile.server.adapter.exception;

/**
 * 访问demo webservice异常
 * @author SunLiang
 * @version 1.0
 */
public class AccessDemoWebServiceException extends Exception
{
    static final long serialVersionUID = 5850243503337783048L;
   
    public AccessDemoWebServiceException(String msg)
    {
        super(msg);
    }
    
    public AccessDemoWebServiceException(Throwable t)
    {
        super(t);
    }
    
    public AccessDemoWebServiceException(String msg, Throwable t) {
        super(msg, t);
    }
}