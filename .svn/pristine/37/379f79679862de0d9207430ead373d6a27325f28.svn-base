package com.yuchengtech.mobile.server.adapter.provider.demowebservice;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.yuchengtech.mobile.server.adapter.dto.demowebservice.DemoProductDetailDTO;
import com.yuchengtech.mobile.server.adapter.dto.demowebservice.DemoProductListDTO;
import com.yuchengtech.mobile.server.adapter.dto.demowebservice.DemoProductTypeDTO;
import com.yuchengtech.mobile.server.adapter.provider.DemoWebServiceProviderTemplate;
import com.yuchengtech.mobile.server.adapter.request.demowebservice.DemoProductDetailRequest;
import com.yuchengtech.mobile.server.adapter.request.demowebservice.DemoProductListRequest;
import com.yuchengtech.mobile.server.adapter.request.demowebservice.DemoProductTypeRequest;

/**
 * demo Webservice 产品 Provider
 *
 * @version 1.0
 *
 * @author sunliang
 */
@Component
public class DemoProductProvider extends DemoWebServiceProviderTemplate{

	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 *  产品类型方法
	 */
	public static String PRODUCT_TYPE_CMD;
	
	/**
	 *  产品列表方法
	 */
	public static String PRODUCT_LIST_CMD;
	
	/**
	 *  产品详情方法
	 */
	public static String PRODUCT_DETAIL_CMD;
		
	static {
		Properties properties = new Properties();
		InputStream in = null;
		String propertiesFileName = "/"+DemoProductProvider.class.getSimpleName()+".properties";
		try {
			in = DemoProductProvider.class.getResourceAsStream(propertiesFileName);
			properties.load(in);	
			Class<DemoProductProvider> clazz = DemoProductProvider.class;
			DemoProductProvider obj = clazz.newInstance();
			java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
			for(java.lang.reflect.Field field:fields){
				String fieldName = field.getName();
				if(properties.containsKey(fieldName)){
					String value=properties.getProperty(fieldName);
					field.set(obj, value);			
				}
			}
		} catch (Exception e) {
			System.err.println("载入配置文件“"+DemoProductProvider.class.getSimpleName()+".properties”时发生错误");
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
		
	public List<DemoProductTypeDTO> productType(DemoProductTypeRequest request) throws Exception{
		return request(request,DemoProductTypeDTO.class,PRODUCT_TYPE_CMD);
	}

	public List<DemoProductListDTO> productList(DemoProductListRequest request) throws Exception {
		return request(request,DemoProductListDTO.class,PRODUCT_LIST_CMD);
	}
	
	public List<DemoProductDetailDTO> productDetail(DemoProductDetailRequest request) throws Exception {
		return request(request,DemoProductDetailDTO.class,PRODUCT_DETAIL_CMD,true);
	}
}
