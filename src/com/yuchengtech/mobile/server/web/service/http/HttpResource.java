package com.yuchengtech.mobile.server.web.service.http;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.mobile.server.web.MobileException;
import com.yuchengtech.mobile.server.web.service.BaseService;

public class HttpResource  extends BaseService implements Runnable {

	private static final Logger log = LoggerFactory
			.getLogger(HttpResource.class);
	
	private List httpServices = new ArrayList();
	private int curIdx = 0;

	//申请连接资源的等待时间
	private long waitTime = -1;	//default no wait;

	//当前的并发量
	private int curConnection  = 0;
	//目前为止发生的最大并发量
	private int maxConnection = 0;
	//最大并发量发生时间
	private long maxConnectionTimeStamp = 0;
	//连接次数统计
	private long connectionCounts = 0;
	
	
	//检测间隔，默认值为一分钟一次
	private int aliveCheckInterval = 60000;
	//服务器是否有效的检测数据包
	private String aliveCheckData = "#";
	
	//default timeOut 
	private int timeOut = 2000;
	
	
	/**
	 *  
	 */
	public HttpResource() {
		super();
	}

	/**
	 * @param name
	 */
	public HttpResource(String name) {
		super();
		super.setName(name);
	}

	public List sendAndWait(String msg ) throws MobileException
	{
		return sendAndWait(null,msg,  timeOut,null );
	}
	public List sendAndWait(String action,String msg ,String reqHead) throws MobileException
	{
		return sendAndWait(action,msg,  timeOut,reqHead );
	}
	public Object sendAndWait_ForStream(String action,String msg,String reqHead ) throws MobileException
	{
		return sendAndWait_ForStream(action,msg,  timeOut ,reqHead);
	}
	
	/**
	 * 获得HttpCommService服务，然后发送并等待
	 * @param msg
	 * @param connectTimeout1
	 * @param dataColl
	 * @return
	 * @throws MobileException
	 */
	public List sendAndWait(String action,String msg, int timeOut,String reqHeader)	throws MobileException 
	{		
		try {
			return this.getResouce().sendAndWait(action,msg, timeOut,  reqHeader);
		} catch (Exception e) {
			throw new MobileException( e);
		}
	}
	public Object sendAndWait_ForStream(String action,String msg, int timeOut,String reqHeader)	throws MobileException 
	{		
		try {
			return this.getResouce().sendAndWait_ForStream(action,msg, timeOut,  reqHeader);
		} catch (Exception e) {
			throw new MobileException( e);
		}
	}
	private synchronized HttpCommService getResouce() throws MobileException{
		
		int count = 0;
		long begin = System.currentTimeMillis();
		
		while(true)
		{
			HttpCommService srv = (HttpCommService) this.httpServices.get(curIdx);
			curIdx++;
			count ++ ;
			if (curIdx >= this.httpServices.size())
				curIdx = 0;
			
			if(!srv.isAlive()){
				log.warn("HttpCommService "+srv.getName()+" ["+srv.getHttpURL()+"] can not be connect!");
			}
			else if( srv.curConnection < srv.getMaxConnection())
			{
				srv.curConnection ++;

				curConnection++;
				if ( maxConnection < curConnection )
				{
					maxConnection = curConnection;
					maxConnectionTimeStamp = System.currentTimeMillis();
				}
				connectionCounts++;
				
				return srv;
			}
			else
			{
				log.warn("All connections in HttpCommService "+srv.getName()+" ["+srv.getHttpURL()+"] are in used!");
			}
			
			if(count >= this.httpServices.size())
			{
				long useTime = System.currentTimeMillis() - begin;
				if( this.waitTime > 0  && useTime < waitTime )//waiting for free connection
				{
					try{
						wait( waitTime - useTime);
					}catch(Exception e)
					{
						
					}
					continue;
				}
				
				break;
			}
		}
		//log.warn("All connection in HttpResource ["+this.getName()+"] are in used, you can increase the maxConnection of HttpCommService or waitTime of HttpResource to solve this problem!");
		throw new MobileException("All connection in HttpResource ["+this.getName()+"] are in used, you can increase the maxConnection of HttpCommService or waitTime of HttpResource to solve this problem!");
	}
	
	
	public synchronized void releaseResource(HttpCommService service )
	{
		curConnection--;
		notifyAll();
	}
	
	
	public void addHttpCommService(HttpCommService service) {
		this.httpServices.add(service);
		service.setHttpResource( this );
	}

	public List getHttpCommServices() {
		return httpServices;
	}

	public long getConnectionCounts() {
		return connectionCounts;
	}

	public void setConnectionCounts(long connectionCounts) {
		this.connectionCounts = connectionCounts;
	}

	public int getCurConnection() {
		return curConnection;
	}

	public void setCurConnection(int curConnection) {
		this.curConnection = curConnection;
	}

	public int getMaxConnection() {
		return maxConnection;
	}

	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	public long getMaxConnectionTimeStamp() {
		return maxConnectionTimeStamp;
	}

	public void setMaxConnectionTimeStamp(long maxConnectionTimeStamp) {
		this.maxConnectionTimeStamp = maxConnectionTimeStamp;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	
	
	Thread checkThread = null;
	boolean isStop = false;
	
	/**
	 * 初始化
	 *
	 */
	public void initialize()throws MobileException
	{
		checkThread = new Thread( this );
		checkThread.start();
		
	}
	
	
	/**
	 * 检查HTTP连接的健康状态
	 */
	public void run()
	{
		
		while(!isStop )
		{
			try{
				Thread.sleep( this.aliveCheckInterval );
				
			}catch(Exception e)
			{
				
			}
			
			//log.debug("Begin to Check the HTTPResource alive of: " + this.getName());
			for( int i=0; i<httpServices.size(); i++)
			{
				HttpCommService srv = (HttpCommService) this.httpServices.get(i);
				if( !srv.isAlive() )
				{
					log.warn( "The HTTPCommService: " + srv.getName() + " was down!");
					try{
						srv.sendAndWait(null,aliveCheckData, 10000 ,null);
					}catch(Exception e)
					{
					}
				}
			}
		}
	}
	
	
	public void terminate() 
	{
		log.debug("Required to terminate the HTTPResource: " + this.toString());
		
		for( int i=0; i<httpServices.size(); i++)
		{
			HttpCommService srv = (HttpCommService) this.httpServices.get(i);
			srv.terminate();
		}
		
		isStop = true;
		checkThread.interrupt();
	}

	public String getAliveCheckData() {
		return aliveCheckData;
	}

	public void setAliveCheckData(String aliveCheckData) {
		this.aliveCheckData = aliveCheckData;
	}

	public int getAliveCheckInterval() {
		return aliveCheckInterval;
	}

	public void setAliveCheckInterval(int aliveCheckInterval) {
		this.aliveCheckInterval = aliveCheckInterval;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	

}
