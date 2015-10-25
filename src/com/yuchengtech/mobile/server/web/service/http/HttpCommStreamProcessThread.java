package com.yuchengtech.mobile.server.web.service.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.web.MobileException;

public class HttpCommStreamProcessThread implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(HttpCommStreamProcessThread.class);
	public boolean isStop = false;

	public boolean readOK = false;

	private HttpURLConnection reqConnection = null;
	
	public Thread readingThread;

	private byte[] buffer = new byte[2048];

	private int readLen;

	private String msg = null;
	
	private MobileException exception = null;
	private HttpCommService service = null;
	
	private String reqMethod;
	
	/**
	 * ReadThread constructor comment.
	 */
	public HttpCommStreamProcessThread(HttpCommService service, HttpURLConnection reqConnection, String msg, String reqMethod ) {
		super();
		this.reqConnection = reqConnection;
		this.msg = msg;
		this.service = service;
		this.reqMethod = reqMethod;
	}

	public void run() {

		InputStream input = null;
		OutputStream output = null;

		try{
			 

			// 处理HTTP响应的返回状态信息
			int responseCode = reqConnection.getResponseCode();// 响应的代码

			service.setAlive( true );
			
			if( responseCode != 200 )
				throw new MobileException("HttpCommService failed! responseCode = " + responseCode + " msg=" + reqConnection.getResponseMessage());
			
			input = reqConnection.getInputStream();

			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
			   int ch;  
			   while ((ch = input.read()) != -1) {  
			    bytestream.write(ch);  
			   }  
			   buffer = bytestream.toByteArray();  
			   bytestream.close();  
		}
		 
		catch(MobileException ee)
		{
			exception = ee;
		}
		catch( IOException ie)
		{
			exception = new MobileException("Http Comm failed in httpCommService", ie);
			service.setAlive( false );
			
		}
		catch(Exception e)
		{
			exception = new MobileException("Http Comm failed in httpCommService", e);
		}
		finally
		{
			try{
				reqConnection.disconnect();
				if( input != null )
					input.close();
				if( output != null )
					output.close();

				wakeUp();
			}catch(Exception e)
			{
				
			}
		}
	}

	public void startUp() {
		this.readingThread = new Thread(this);
		readingThread.setName("HttpCommService reading thread!");
		readingThread.start();
	}
	public byte[] getBytes() throws MobileException {
		return buffer;
	}

	private synchronized void wakeUp() {
		notifyAll();
	}

	public synchronized void waitForData(int timeout) 
	{
		try {
			wait(timeout);
		} 
		catch (Exception e) 
		{
		}
			
		if (!readOK)
		{
			isStop = true;
			try{
//				System.out.println("Stop the reading thread!");
				if( readingThread.isAlive() )
					readingThread.interrupt();
			}catch(Exception e)
			{
				
			}
		}
	}
}
