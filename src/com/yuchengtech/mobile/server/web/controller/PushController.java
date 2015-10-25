package com.yuchengtech.mobile.server.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.web.view.JsonView;
import com.yuchengtech.mobile.server.web.view.View;

public class PushController {
	private static final Logger log = LoggerFactory
			.getLogger(ClientInitController.class);
	public View execute(HttpServletRequest request, HttpServletResponse response) {
		Object reultData = null;		
		return new JsonView(request, response, reultData);
	}
}
