package com.yuchengtech.mobile.server.security.access;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
 
public class AccessInfo implements Serializable{

	/**
	 * 访问总计
	 */
	int accessCount;
	
	/**
	 * 当前的并发访问
	 */
	int concurrentCount;
	
	/**
	 * 最大并发访问
	 */
	int maxConcurrentCount;
	
    /**
	 * 最大并发访问发生时间
	 */
	long maxConcurrentTimeStamp;
	
	/**
	 * 最近的访问响应时间
	 */
	int  latestResponseTime;
	
	/**
	 * 最大响应时间
	 */
	int maxResponseTime;
	
	/**
	 * 最大响应时间的发生时间
	 */
	long maxResponseTimeStamp;
	
	/**
	 * 最后一次访问时间
	 */
	long latestAccessTimeStamp;	
	
	/**
	 * 总访问时间
	 */
	long totalAccessTime;
	
	/**
	 * 平均响应时间
	 */
	long averageResponseTime;
	
	/**
	 * 访问控制对象标识
	 */
	String accessObjName = null;

	/**
	 * 返回值
	 */
	String retValue = null;
	
	/**
	 * 访问控制中的异常对象
	 */
	Throwable exception = null; 
	
	/**
	 * 构造器方法
	 */
	public AccessInfo() {
		super();
	}

	/**
	 * 带参数的构造器方法
	 * @param accessObjName
	 */
	public AccessInfo(String accessObjName) {
		super();
		this.accessObjName = accessObjName; 
	}
	
	
	/**
	 *启动新的访问，开始进行访问控制统计。
	 */
	public synchronized void newAccess()
	{
		long timeStamp = System.currentTimeMillis();
		this.accessCount++;
		this.concurrentCount++;
		this.latestAccessTimeStamp = timeStamp;
		
		if( concurrentCount > this.maxConcurrentCount )
		{
			maxConcurrentCount = concurrentCount;
			this.maxConcurrentTimeStamp = timeStamp;
		}
		
		this.retValue = "";
		this.exception = null;
	}
	
	/**
	 * 结束访问，进行访问控制统计计算。
	 * @param timeUsed 总耗时时间
	 * 
	 */
	public synchronized void endAccess(long timeUsed )
	{
		this.concurrentCount--;
		this.latestResponseTime = (int)timeUsed;
		long timeStamp = System.currentTimeMillis();

		if( this.latestResponseTime > this.maxResponseTime )
		{
			maxResponseTime = latestResponseTime;
			this.maxResponseTimeStamp = timeStamp;
		}
		
		totalAccessTime+= timeUsed;
		if( accessCount > 0 )
			this.averageResponseTime = totalAccessTime/this.accessCount;
	}
	
	
	
	/**
	 * 根据类型值获得访问信息中的某类型数据值。
	 * @param type 
	 *	0: 访问总计
	 *	1:当前的并发访问
	 *	2:最大并发访问
	 *	3:最近的访问响应时间
	 *	4:最大响应时间
	 *	5:平均响应时间
	 *  6：最后访问时间
	 * @return int 记录的数据值
	 */
	public int getAccessValueOf( int type )
	{
		switch(type )
		{
			//访问总计
		case 0:	//ACCESS_COUNT:
			return this.accessCount;
			
			//当前的并发访问
		case 1: //CONCURENT_COUNT:
			return this.concurrentCount;
			
			//最大并发访问
		case 2://MAX_CONCURRENT_COUNT:
			return this.maxConcurrentCount;

			//最近的访问响应时间
		case 3://LATEST_RESPONSE_TIME:
			return this.latestResponseTime;
			
			//最大响应时间
		case 4://MAX_RESPONSE_TIME:
			return this.maxResponseTime;
			
			//平均响应时间
		case 5://AVR_RESPONSE_TIME:
			if( accessCount > 0 )
				return (int)(this.totalAccessTime/this.accessCount);
			else
				return 0;
		case 6:
			return (int)latestAccessTimeStamp;
		}
		return 0;
	}
	
	/**
	 * 字符串方法
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		buf.append("\r\nOBJName:" + accessObjName );
		
		buf.append("\tAccessCount:" + accessCount );
		buf.append("\tconcurrentCount:" + concurrentCount );
		
		buf.append("\tAverageResponseTime:" + this.averageResponseTime );
		
		buf.append("\tmaxConcurrentCount:" + maxConcurrentCount );
		buf.append("\tmaxmaxConcurrentTimeStamp:");
		buf.append(this.getMaxConcurrentTimeStamp());
		
		buf.append("\tlatestResponseTime:" + latestResponseTime );
	
		buf.append("\tmaxResponseTime:" + maxResponseTime );
		
		buf.append("\tmaxResponseTimeStamp:");
			buf.append(this.getMaxResponseTimeStamp());
		
		buf.append("\tlatestAccessTimeStamp:");
		buf.append(this.getLatestAccessTimeStamp());

		buf.append("\tretValue:" + retValue );
		
		if( exception != null )
			buf.append("\tException:" + exception );
		return buf.toString();
	}

	public String getAccessObjName() {
		return accessObjName;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public long getAverageResponseTime() {
		return averageResponseTime;
	}

	public int getConcurrentCount() {
		return concurrentCount;
	}

	public String getLatestAccessTimeStamp() 
	{
		return formatTimeStr(latestAccessTimeStamp );
	}

	public int getLatestResponseTime() {
		return latestResponseTime;
	}

	public int getMaxConcurrentCount() {
		return maxConcurrentCount;
	}

	public String getMaxConcurrentTimeStamp() {

		return formatTimeStr(maxConcurrentTimeStamp );
	}

	public int getMaxResponseTime() {
		return maxResponseTime;
	}

	public String getMaxResponseTimeStamp() {
		return formatTimeStr(maxResponseTimeStamp );
	}

	public long getTotalAccessTime() {
		return totalAccessTime;
	}
	
	private String formatTimeStr( long timeStamp )
	{
		if( timeStamp == 0 )
			return "-";
		
		java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format( new java.util.Date( timeStamp ));
		
	}

	public void setRetValue(String value )
	{
		retValue = value;
	}
	
	public void setThrowable(Throwable te )
	{
		this.exception = te;
	}

	public Throwable getException() {
		return exception;
	}

	public String getRetValue() {
		return retValue;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
	public Map getMap() {

		Map infoMap = new HashMap();

		infoMap.put("accessObjName", getAccessObjName());	//访问控制对象标识
		
		infoMap.put("accessCount", ""+getAccessCount());	//访问总计
		infoMap.put("concurrentCount", ""+getConcurrentCount());	//当前的并发访问
		infoMap.put("maxConcurrentCount", ""+getMaxConcurrentCount());	//最大并发访问
		infoMap.put("maxConcurrentTimeStamp", getMaxConcurrentTimeStamp());	//最大并发访问发生时间
		infoMap.put("latestResponseTime", ""+getLatestResponseTime());	//最近的访问响应时间
		infoMap.put("maxResponseTime", ""+getMaxResponseTime());	//最大响应时间
		infoMap.put("maxResponseTimeStamp", getMaxResponseTimeStamp());	//最大响应时间的发生时间
		infoMap.put("latestAccessTimeStamp", getLatestAccessTimeStamp());	//最后一次访问时间
		infoMap.put("totalAccessTime", ""+getTotalAccessTime());	//总访问时间
		infoMap.put("averageResponseTime", ""+getAverageResponseTime());	//平均响应时间
		
		return infoMap;
	}
}
