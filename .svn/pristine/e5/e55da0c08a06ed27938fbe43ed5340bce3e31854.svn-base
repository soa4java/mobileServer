package com.yuchengtech.mobile.server.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ImageProxyServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String filePath = request.getParameter("file");

		String s = "http://172.20.35.31:8080/innermanage/downloadCharityPic.do?picUrl="+filePath;
		URLConnection uc = null;
		InputStream in = null;
		OutputStream out = null;
		
		try {
			uc = new URL(s).openConnection();
			in = uc.getInputStream();  

			System.out.println("URL=="+s);
			
			response.reset();
			response.resetBuffer();
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
////			response.setHeader("Cache-Control", "public");
//			response.setContentType("application/pdf");
			
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int reader = 0;
			while ((reader = in.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, reader);
			}
			out.flush();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
