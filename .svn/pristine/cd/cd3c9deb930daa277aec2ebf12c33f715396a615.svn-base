package com.yuchengtech.mobile.server.web.service;

import com.yuchengtech.mobile.server.common.Constants;
import com.yuchengtech.mobile.server.web.service.http.HttpCommService;
import com.yuchengtech.mobile.server.web.service.http.HttpResource;
/**
 * 后端服务管理
 * @author Administrator
 *
 */
public class ServiceManage {
	private static HttpResource httpResource;;
	static {
		//todo 服务配置
		httpResource = new HttpResource("default");
		httpResource.setMaxConnection(Constants.BACKEND_HTTP_MaxConnection);
		httpResource.setTimeOut(Constants.BACKEND_HTTP_Timeout);

		
		HttpCommService http = new HttpCommService("default_http");
		http.setHttpURL(Constants.BACKEND_HTTP_URL);
		http.setEncoding("UTF-8");
		
		httpResource.addHttpCommService(http);
		new Thread(httpResource).start();
	}

	public static HttpResource getHttpResource() {
		return httpResource;
	}
	
}
