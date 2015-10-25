package com.yuchengtech.mobile.server.web.service.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.web.MobileException;
import com.yuchengtech.mobile.server.web.service.BaseService;
import com.yuchengtech.mobile.server.web.utils.Base64;

public class HttpCommService extends BaseService {
	private static final Logger log = LoggerFactory
			.getLogger(HttpCommService.class);
	private String reqProxyIP;

	private int reqProxyPort = -1;

	private String reqMethod = "POST";

	private String useProxyAuthor;

	private String proxyUserName;

	private String proxyUserPass;

	private String httpURL;

	private String reqHead;
	
	private int maxConnection = 50;

	public int curConnection = 0;
	
	//服务器是否可用
	private boolean isAlive = true;
	
	
	//default timeOut
	private int timeOut = 2000;
	
	private String encoding = null;
	
	/**
	 * constructor comment.
	 */
	public HttpCommService() {
		super();
	}

	public HttpCommService(String arg1)  {
		super();
		super.setName(arg1);
	}


	
	public void terminate() 
	{
		log.debug( "Required to terminate the HTTPCommService: " + this.toString());
	}
	
	
	
	public List sendAndWait( String msg )throws MobileException
	{
		return sendAndWait(null,msg, timeOut ,null);
	}
	public List sendAndWait( String action,String msg )throws MobileException
	{
		return sendAndWait(action,msg, timeOut ,null);
	}
	
	/**
	 * 发送请求并等待结果返回
	 * 
	 * @param reourceCfg
	 * @param msg
	 * @param connectTimeout
	 * @param dataColl
	 * @return String返回结果
	 * @throws MobileException
	 */
	public List sendAndWait(String action,String msg,int timeOut,String header ) throws MobileException {
		
		HttpURLConnection reqConnection = null;

		try {
			
			String httpURL = this.httpURL;
			if(action!=null){
				httpURL=httpURL+action;
			}
			
			// 获得URL
			if (!"post".equalsIgnoreCase(reqMethod) && msg != null && msg.length() >0){
				httpURL = httpURL + "?" + msg;
			}
			
			URL reqUrl = new URL(httpURL);
//			log.debug("httpURL:"+httpURL);
			// 建立URLConnection连接
			reqConnection = (HttpURLConnection) reqUrl.openConnection();

			// 如果使用了代理的话,则对代理信息进行设置
			if (useProxyAuthor != null
					&& useProxyAuthor.trim().equalsIgnoreCase("true")) {

				if (this.reqProxyIP != null
						&& this.reqProxyIP.trim().length() != 0) {// 在HttpCommService中对代理进行了设置
					System.getProperties().put("proxySet", "true");
					System.getProperties().put("proxyHost", this.reqProxyIP);
					System.getProperties().put("proxyPort",	"" + this.reqProxyPort);
				} 

				if (this.proxyUserName != null
						&& this.proxyUserName.trim().length() != 0) {// 在HttpCommService中对代理进行了设置
					reqConnection.setRequestProperty("Proxy-Authorization", new String(
											Base64.encode((this.proxyUserName
													+ ":" + this.proxyUserPass)
													.getBytes())));
					reqConnection.setRequestProperty("Proxy-Connection", "Keep-Alive");
				} 
			}

			if (header != null) 
			{
				StringTokenizer stName = new StringTokenizer(header, "|");
				while (stName.hasMoreTokens()) {
					String reqMessage = (String) stName.nextElement();
					int idx = reqMessage.indexOf(":");
					String reqName = reqMessage.substring(0, idx);
					String reqValue = reqMessage.substring(idx + 1, reqMessage
							.length());
					reqConnection.setRequestProperty(reqName, reqValue);
				}
			}

			reqConnection.setRequestMethod(reqMethod.toUpperCase());
			
		/*	if("post".equalsIgnoreCase(reqMethod)){
				int contentLength = 0;
				if (msg != null && msg.length() >0)
					contentLength = msg.getBytes().length;
				reqConnection.addRequestProperty("Content-Length", String.valueOf(contentLength));
			}
			*/
			reqConnection.setDoInput(true);
			reqConnection.setDoOutput(true);


			HttpCommProcessThread rec = new HttpCommProcessThread(this, reqConnection, msg, reqMethod );
			rec.startUp();
			rec.waitForData( timeOut);
			
			String retMessage = rec.getMessage(encoding);
				java.util.List ret = new ArrayList();
				ret.add(retMessage);
				Map headers = reqConnection.getHeaderFields();
				if(headers.get("Set-Cookie")!=null){
					String setCookie=((java.util.List)headers.get("Set-Cookie")).get(0).toString();
					ret.add(setCookie);
//					log.debug("..setCookie:"+ret.get(1));
				}
				
			return ret;
		} catch (IOException ee) {
			log.error( "HttpCommService sendAndWait failed!", ee);
			throw new MobileException(ee);
		}
		catch(MobileException empe)
		{
			throw empe;
		}
		finally
		{	
			synchronized (this) {
				this.curConnection--;
			}
			if( resource != null )
				resource.releaseResource( this );
		}			
	}
public Object sendAndWait_ForStream(String action,String msg,int timeOut,String header ) throws MobileException {
		
		HttpURLConnection reqConnection = null;

		try {
			
			String httpURL = this.httpURL;
			if(action!=null){
				httpURL=httpURL+action;
			}
//			log.debug("httpURL:"+httpURL);
			// 获得URL
			if (!"post".equalsIgnoreCase(reqMethod) && msg != null && msg.length() >0){
				httpURL = httpURL + "?" + msg;
			}
			
			URL reqUrl = new URL(httpURL);

			// 建立URLConnection连接
			reqConnection = (HttpURLConnection) reqUrl.openConnection();

			// 如果使用了代理的话,则对代理信息进行设置
			if (useProxyAuthor != null
					&& useProxyAuthor.trim().equalsIgnoreCase("true")) {

				if (this.reqProxyIP != null
						&& this.reqProxyIP.trim().length() != 0) {// 在HttpCommService中对代理进行了设置
					System.getProperties().put("proxySet", "true");
					System.getProperties().put("proxyHost", this.reqProxyIP);
					System.getProperties().put("proxyPort",	"" + this.reqProxyPort);
				} 

			  
			}

			if (header != null) 
			{
				StringTokenizer stName = new StringTokenizer(header, "|");
				while (stName.hasMoreTokens()) {
					String reqMessage = (String) stName.nextElement();
					int idx = reqMessage.indexOf(":");
					String reqName = reqMessage.substring(0, idx);
					String reqValue = reqMessage.substring(idx + 1, reqMessage
							.length());
					reqConnection.setRequestProperty(reqName, reqValue);
				}
			}

			 		reqConnection.setRequestMethod("GET");
		
		 
			
			reqConnection.setDoInput(true);
			reqConnection.setDoOutput(true); 

			HttpCommStreamProcessThread rec = new HttpCommStreamProcessThread(this, reqConnection, msg, reqMethod );
			rec.startUp();
			rec.waitForData( timeOut);
			
			
			java.util.List ret = new ArrayList();
            ret.add(rec.getBytes());
			
			Map headers = reqConnection.getHeaderFields();
			if(headers.get("Set-Cookie")!=null){
				String setCookie=((java.util.List)headers.get("Set-Cookie")).get(0).toString();
				ret.add(setCookie);
//				log.debug("..setCookie:"+ret.get(1));
			}
	       

	        
			return ret;
		} catch (IOException ee) {
			log.error( "HttpCommService sendAndWait failed!", ee);
			throw new MobileException(ee);
		}
		catch(MobileException empe)
		{
			throw empe;
		}
		finally
		{	
			synchronized (this) {
				this.curConnection--;
			}
			if( resource != null )
				resource.releaseResource( this );
		}			
	}
	/*
	 * 获得各个参数的值
	 */
	public void setReqMethod(String value) {
		this.reqMethod = value;
	}

	public void setReqProxyIP(String value) {
		this.reqProxyIP = value;
	}

	public void setReqProxyPort(int value) {
		this.reqProxyPort = value;
	}

	public void setUseProxyAuthor(String value) {
		this.useProxyAuthor = value;
	}

	public void setProxyUserName(String value) {
		this.proxyUserName = value;
	}

	public void setProxyUserPass(String value) {
		this.proxyUserPass = value;
	}

	public void setHttpURL(String value) {
		this.httpURL = value;
	}

	public void setReqHead(String head) {
		this.reqHead = head;
	}

	public int getMaxConnection() {
		return maxConnection;
	}

	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	public String getHttpURL() {
		return httpURL;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public String getProxyUserPass() {
		return proxyUserPass;
	}

	public String getReqHead() {
		return reqHead;
	}

	public String getReqMethod() {
		return reqMethod;
	}
 

	public String getReqProxyIP() {
		return reqProxyIP;
	}

	public int getReqProxyPort() {
		return reqProxyPort;
	}

	public String getUseProxyAuthor() {
		return useProxyAuthor;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	 

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getCurConnection() {
		return curConnection;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public void setHttpResource( HttpResource resource )
	{
		this.resource = resource;
	}
	private HttpResource resource = null;

}
