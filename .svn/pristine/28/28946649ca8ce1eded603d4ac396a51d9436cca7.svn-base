package com.yuchengtech.mobile.server.web.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.common.Constants;
import com.yuchengtech.mobile.server.security.auth.AuthManager;
import com.yuchengtech.mobile.server.web.MobileException;
import com.yuchengtech.mobile.server.web.service.ServiceManage;
import com.yuchengtech.mobile.server.web.view.JsonView;
import com.yuchengtech.mobile.server.web.view.LianaView;
import com.yuchengtech.mobile.server.web.view.View;
/**
 * 
 *Liana后端业务调用Controller
 * @author Administrator
 *
 */
public class MobileCommonController extends AbstractController {
	private static final Logger log = LoggerFactory
			.getLogger(MobileCommonController.class);

	 
	public View execute(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		Enumeration<String> enums= request.getHeaderNames();
		StringBuffer sb=new StringBuffer();
		/*while(enums.hasMoreElements()){
		    if(sb.length()>0)sb.append("|");
			String n=enums.nextElement();
			String v=request.getHeader(n);
			sb.append(n).append(":").append(v);
		}*/
		sb.append("Cookie:").append(request.getHeader("Cookie") );
		log.debug("header:"+sb.toString());
		this.response = response;
		Object reultData;
		
		
		Map<String, String> values= getReqValues();
		String did=values.get("d");
		String encode_msg=values.get("e");
		String channel=values.get("channel");
		if(channel==null){
			channel=values.get("channel");
		}
		
//		int check_result=AuthManager.basicCheck(did, encode_msg,values.get("action"));
		
		int check_result=AuthManager.basicCheck(did,values.get("action"));
		
		if(channel==null||(Constants.NoCheck_channel.indexOf(channel)==-1&&check_result!=0)){
			log.warn("Client Check Fail:"+check_result);
			reultData=getError("1005","无效请求");
			return new JsonView(request, response, reultData);
		}
		//todo:日志记录
		String msg=prepareParam(values);
		
		System.out.println("!!!!!!!!!!msg:"+msg);
		
		try {
			List list = (List) ServiceManage.getHttpResource().sendAndWait(values.get("action"),msg,sb.toString());
			reultData=list.get(0);
			if(list.size()>1){
				String setCookie=list.get(1).toString();
				setCookie(setCookie);
			}
		} catch (Exception e) {
			log.error("",e);
			reultData=getError("1001","通信异常");
			return new JsonView(request, response, reultData);
		}
		return new LianaView(request, response, reultData);
	}
}
