package com.yuchengtech.mobile.server.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

public class Constants {

	public static String CONTEXT_PATH;
	public static String NoCheck_channel;
	
	
	public static String CONTEXT_REAL_PATH;
	
	public static String UPLOAD_PATH;
	
	public static String  BACKEND_HTTP_URL;
	public static String  safeCheckList;
	public static String  MOBILE_SERVER_URL;
	public static int  BACKEND_HTTP_MaxConnection;
	public static int  BACKEND_HTTP_Timeout;
	
	public static boolean   Client_debug_mode;
	public static String   actions_filter_type;
	public static int   direct_update_size;
	public static String appkey;
	public static String masterSecret;
	
	public static final int PAGE_SIZE = 10;
	static {
		Properties properties = new Properties();
		InputStream in = null;
		String value = null;
		String fieldName = null;
		String fieldType = null;
		Method method =null;
		try {
			in = Constants.class.getResourceAsStream("/constants.properties");
			properties.load(in);	
			Class<Constants> clazz = Constants.class;
			Constants obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields){
				fieldName = field.getName();
				if(properties.containsKey(fieldName)){
					method = Properties.class.getDeclaredMethod("getProperty", String.class);
					value = (String) method.invoke(properties, fieldName);
					if (null!=value) {
						fieldType = field.getType().getName();
					    if (String.class.getName().equals(fieldType)) {
							field.set(obj, value);
						}else if ("int".equals(fieldType)) {
							field.setInt(obj, Integer.parseInt(value));
						}else if ("boolean".equals(fieldType)) {
							field.setBoolean(obj, Boolean.parseBoolean(value));
						}else {
							//do nothing
						}
					}					
				}
			}
		} catch (Exception e) {
			System.err.println("载入配置文件/constants.properties时发生错误");
			e.printStackTrace();
		}finally{
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 私有构造函数,仅允许Constants在初始化成员变量时创建实例,不允许其他类调用
	 */
	private Constants(){}
	
}