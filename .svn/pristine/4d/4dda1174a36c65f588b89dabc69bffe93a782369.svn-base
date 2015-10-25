package com.yuchengtech.mobile.server.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.console.entity.account.ActionsDef;
import com.yuchengtech.mobile.server.common.Constants;

public class ControllerFactory {
	private static final Logger log = LoggerFactory.getLogger(ControllerFactory.class);
    private static Map<String,String> controllerMap=new HashMap<String,String>();
    private static Object lock=new Object();
  
    

	private static Map<String,Controller> controllerCache=new HashMap<String,Controller>();
    
	static{
		controllerMap.put("default", "com.yuchengtech.mobile.server.web.controller.MobileCommonController");
		controllerMap.put("signIn.do", "com.yuchengtech.mobile.server.web.controller.MobileCommonController");
		controllerMap.put("getSessionData.do", "com.yuchengtech.mobile.server.web.controller.MobileCommonController");
		controllerMap.put("mb.do", "com.yuchengtech.mobile.server.web.controller.ClientInitController");
		controllerMap.put("VerifyImage", "com.yuchengtech.mobile.server.web.controller.ImageController");
		controllerMap.put("generateImage", "com.yuchengtech.mobile.server.web.controller.ImageController");
	}

	public static void initControllerMap(List<ActionsDef> list) {
		for(ActionsDef d:list){
			controllerMap.put(d.getActionurl(), d.getActioncontroller());
		}
	}
    public static final Controller getControllerByClass(Class actionClass) {
        try {
            //Controller controller = (Controller) actionClass.newInstance();
            return (Controller) actionClass.newInstance();
        } catch (Exception e) {
        	log.error("",e);
        }  
        return null;
    }

    public static final Controller getController(String url) {
    	//todo 判断有效的url才处理请求！
    	  String url_ =url;
          /*if(url.indexOf(".")!=-1){
              controller =url.substring(0, url.indexOf("."));
          }
          else*/
    	  if(url.indexOf(".")==-1){
    		  url_=url.substring(url.lastIndexOf("/")+1);
          	/* if(!"VerifyImage".equals(controller)){
          		 return null;
          	 }*/
          }
    	  if(!controllerMap.containsKey(url_)&& Constants.Client_debug_mode==true){
      		url_="default";
      	}
          
          
        try {
            String name = controllerMap.get(url_);
            if(name==null){
            	log.debug("Invalid request:"+url_);
            	return null;
            }
/*			if (controllerCache.get(name) == null) {
				synchronized (lock) {
					if (controllerCache.get(name) == null) {
						Class actionClass = Class.forName(name);
						Controller c = getControllerByClass(actionClass);
						controllerCache.put(name, c);
					}
				}
			}
            
            return controllerCache.get(name);*/
            Class actionClass = Class.forName(name);
			return getControllerByClass(actionClass);
			
        } catch (ClassNotFoundException e) {
           log.error("",e);
        }
        return null;
    }
}
