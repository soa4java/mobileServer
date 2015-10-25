package com.yuchengtech.mobile.server.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuchengtech.mobile.server.web.view.View;

public interface Controller {
    
    public View execute(HttpServletRequest request,HttpServletResponse response);
}
