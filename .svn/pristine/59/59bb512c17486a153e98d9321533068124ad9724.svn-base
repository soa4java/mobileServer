package com.yuchengtech.mobile.server.web.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonView extends AbstractView {
	private static final Logger log = LoggerFactory
			.getLogger(JsonView.class);
	private JsonGenerator jsonGenerator;
	public JsonView(HttpServletRequest request,HttpServletResponse response, Object reultData) {
		super(request,response, reultData);
		try {
			jsonGenerator= new ObjectMapper().getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		} catch (Exception e) {
			log.error("",e);
		}
	}

	public void doView() {
		try {
			
			jsonGenerator.writeObject(reultData);
			if(reultData!=null)
			log.debug("Result:"+reultData.toString());
		} catch (Exception e) {
			log.error("",e);
		}  
	}
}
