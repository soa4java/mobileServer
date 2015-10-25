package com.yuchengtech.mobile.server.web.service.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.web.MobileException;

public class HttpCommProcessThread implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(HttpCommProcessThread.class);
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
	public HttpCommProcessThread(HttpCommService service, HttpURLConnection reqConnection, String msg, String reqMethod ) {
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
			//reqConnection.connect();
			output = reqConnection.getOutputStream();
			if ("post".equalsIgnoreCase(reqMethod) && msg != null && msg.length() >0) 
			{
				output.write(msg.getBytes());
				output.close();
				output = null;
			}

			// 处理HTTP响应的返回状态信息
			int responseCode = reqConnection.getResponseCode();// 响应的代码

			service.setAlive( true );
			
			if( responseCode != 200 )
				throw new MobileException("HttpCommService failed! responseCode = " + responseCode + " msg=" + reqConnection.getResponseMessage());
			
			input = reqConnection.getInputStream();

			int len;
			byte[] buf = new byte[2048];
			readLen = 0;

			while (!isStop) 
			{
				len = input.read(buf);
				if (len <= 0) 
				{
					this.readOK = true;
					input.close();
					break;
				}
				if (len + readLen >= buffer.length) // enlarge the buffer
				{
					byte[] tmp = new byte[buffer.length + len + 1024];
					System.arraycopy(buffer, 0, tmp, 0, readLen);
					buffer = tmp;
				}

				System.arraycopy(buf, 0, buffer, readLen, len);
				readLen += len;

			}
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
	public String getMessage(String encoding) throws MobileException {

		if( exception != null )
			throw exception;
		
		if (!readOK) // time Out
		{
			throw new MobileException("HttpCommService failed dur to time out!");
		}
		
		if (readLen <= 0) {
			return "";
		}

		if (encoding != null) {
			try {
				return new String(buffer, 0, readLen, encoding);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return new String(buffer, 0, readLen);
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
