package com.yuchengtech.mobile.server.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.security.auth.AuthManager;
import com.yuchengtech.mobile.server.web.MobileException;
import com.yuchengtech.mobile.server.web.service.ServiceManage;
import com.yuchengtech.mobile.server.web.view.ImageView;
import com.yuchengtech.mobile.server.web.view.JsonView;
import com.yuchengtech.mobile.server.web.view.LianaView;
import com.yuchengtech.mobile.server.web.view.View;
/**
 * 
 *Liana后端业务调用Controller
 * @author Administrator
 *
 */
public class ImageController extends AbstractController {
	private static final Logger log = LoggerFactory
			.getLogger(ImageController.class);

	 
	public View execute(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		StringBuffer sb=new StringBuffer();
		sb.append("Cookie:").append(request.getHeader("Cookie") );
		log.debug("header:"+sb.toString());
		this.response = response;
		Object reultData;
		 
		Map<String, String> values= getReqValues();
		try {
			List list = (List)ServiceManage.getHttpResource().sendAndWait_ForStream(values.get("action"),"",sb.toString());
			reultData=list.get(0);
			if(list.size()>1){
				String setCookie=list.get(1).toString();
				setCookie(setCookie);
			}
		} catch (Exception e) {
			log.error("",e);
			reultData=getError("1001","通信异常");
			return new JsonView(request, response, null);
		}
		return new ImageView(request, response, reultData);
	}
}
