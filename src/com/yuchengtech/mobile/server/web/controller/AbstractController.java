package com.yuchengtech.mobile.server.web.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController implements Controller {
	private static final Logger log = LoggerFactory
			.getLogger(AbstractController.class);
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected Map<String, Object> getError(String code, String msg) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ec", code);
		m.put("em", msg);
		return m;
	}

	protected Map<String, Object> getSuccess() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("ec", "0");
		m.put("em", " ");
		return m;
	}

	protected void setCookie(String setCookie) {
//		String setCookie=list.get(1).toString();
		log.debug("....Set-Cookie:"+setCookie);
		if(setCookie.indexOf(";")!=-1){
			String ck[]=setCookie.split(";");
			String cookie=ck[0];
			String cookiePath=ck[1];
			String cookiePath_=cookiePath.substring(0,cookiePath.indexOf("=")+1)+"/";
			setCookie=cookie+";"+cookiePath_;
		}
		log.debug("....Set-Cookie:"+setCookie);
		response.setHeader("Set-Cookie", setCookie);
	}
	protected Map<String, String> getReqValues() {
		Map<String, String> vmap = new HashMap<String, String>();
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			String value1 = request.getParameter(name);
			String value = value1;
			/*
			 * try { value = new String(value1.getBytes("ISO8859-1"), "UTF-8");
			 * } catch (UnsupportedEncodingException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); }
			 */
			vmap.put(name, value);
		}
		vmap.put("sourceip", request.getRemoteAddr());
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		vmap.put("action", uri);
		log.debug("Request Parameter ["+uri+"]:"+vmap.toString());
		return vmap;
	}

	protected String prepareParam(Map<String, String> paramMap) {
		StringBuffer sb = new StringBuffer();
		if (paramMap.isEmpty()) {
			return "";
		} else {
			for (String key : paramMap.keySet()) {
				String value = (String) paramMap.get(key);
				if (sb.length() < 1) {
					sb.append(key).append("=").append(value);
				} else {
					sb.append("&").append(key).append("=").append(value);
				}
			}
			return sb.toString();
		}
	}
	
	protected Map<String, String> parseJsonToMap(String json) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();  
		Map<String, String> map = mapper.readValue(json, Map.class);
		
		return map;
	}
}
