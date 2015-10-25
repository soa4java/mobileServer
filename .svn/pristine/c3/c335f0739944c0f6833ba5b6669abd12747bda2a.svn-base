package com.yuchengtech.mobile.server.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.web.MobileBaseServlet;

public class CheckOnlineThread implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(MobileBaseServlet.class);
	public void run() {
		
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				log.error("error", e);
			}
		}


	}
}
