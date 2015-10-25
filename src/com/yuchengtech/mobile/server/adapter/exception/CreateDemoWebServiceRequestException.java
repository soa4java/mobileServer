package com.yuchengtech.mobile.server.adapter.exception;

/**
 * 创建demo webservice请求对象异常
 * 
 * @author SunLiang
 * @version 1.0
 * @since 2012-12-24
 */
public class CreateDemoWebServiceRequestException extends Exception
{
    static final long serialVersionUID = 5850243503337783048L;
   
    public CreateDemoWebServiceRequestException(String msg)
    {
        super(msg);
    }
    
    public CreateDemoWebServiceRequestException(Throwable t)
    {
        super(t);
    }
    
    public CreateDemoWebServiceRequestException(String msg, Throwable t) {
        super(msg, t);
    }
}