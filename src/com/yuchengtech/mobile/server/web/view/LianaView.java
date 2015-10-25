package com.yuchengtech.mobile.server.web.view;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LianaView extends AbstractView {
	private static final Logger log = LoggerFactory
			.getLogger(LianaView.class);
	public LianaView(HttpServletRequest request,HttpServletResponse response, Object reultData) {
		super(request,response, reultData);
	 
	}

	public void doView() {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(reultData.toString());
			log.debug("Result:"+reultData.toString());
		} catch (Exception e) {
			log.error("error:",e);
		}
	}
}

