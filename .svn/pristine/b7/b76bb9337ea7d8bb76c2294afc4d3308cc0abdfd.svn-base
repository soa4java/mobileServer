package com.yuchengtech.mobile.server.web.view;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageView extends AbstractView {
	private static final Logger log = LoggerFactory
			.getLogger(ImageView.class);
	public ImageView(HttpServletRequest request,HttpServletResponse response, Object reultData) {
		super(request,response, reultData);
	 
	}

	 
	public void doView() {
		try {
			response.setContentType("image/jpeg");
			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			if(reultData==null){
				
			}else
			response.getOutputStream().write((byte[])reultData);
		} catch (Exception e) {
			response.setStatus(500);
			log.error("error:",e);
		}
	}
}

