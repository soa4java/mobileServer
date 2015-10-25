package com.yuchengtech.mobile.server.adapter.interfaces.demowebservice;

import javax.jws.WebService;

/**
 * 
 * @author SunLiang
 * @version 1.0
 */
@WebService(targetNamespace = "http://server.ws.interfaces.grcb.yuchengtech.com/")
public interface DemoWebService {
	
	/*** 
     * 
     * 请求报文
     * <input>                                  
     *    <key>xxxxxx</key>                    
     *    <cmd>productTypeCmd</cmd>                   
     *    <domain>0</domain>                       
     *    <productName>...</productName>
     *    ...
     *</input>
     *
     *
     * 响应报文
     * <output>
     *    <message>
     *        <result>1</result>
     *        <description>success</description>
     *    </message>
     *	  <data>
     *     <recordCount>4</recordCount>
     *     <pageCount>2</pageCount>
     *     <list>
     *     ...
     *     </list>
     *     <list>
     *     ...
     *     </list>
     *     ...
     *	  </data>
     *</output>
     */  
    public String execute(String req);
    
}

