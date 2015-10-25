package com.yuchengtech.mobile.server.web.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractView implements View {

	protected HttpServletRequest request;
	protected Object reultData;
	protected HttpServletResponse response;

	public AbstractView(HttpServletRequest request,HttpServletResponse response,Object data) {
		this.request = request;
		this.response=response;
		this.reultData=data;
	}
}
