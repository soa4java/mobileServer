package com.yuchengtech.mobile.server.adapter.provider;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.yuchengtech.mobile.server.adapter.common.annotation.demowebservice.DemoWebServiceArea;
import com.yuchengtech.mobile.server.adapter.common.annotation.demowebservice.DemoWebServiceDTOAnnotation;
import com.yuchengtech.mobile.server.adapter.common.annotation.demowebservice.DemoWebServiceRequestAnnotation;
import com.yuchengtech.mobile.server.adapter.dto.DTO;
import com.yuchengtech.mobile.server.adapter.exception.AccessDemoWebServiceException;
import com.yuchengtech.mobile.server.adapter.exception.CreateDemoWebServiceRequestException;
import com.yuchengtech.mobile.server.adapter.exception.DemoWebServiceReturnException;
import com.yuchengtech.mobile.server.adapter.exception.ParseDemoWebServiceResponseException;
import com.yuchengtech.mobile.server.adapter.interfaces.demowebservice.DemoWebService;
import com.yuchengtech.mobile.server.adapter.request.Request;
import com.yuchengtech.mobile.server.adapter.service.SpringContextHolder;

/**
 * Demo WebSerice 提供者模板
 *
 * @version 1.0
 *
 * @author sunliang
 */
public abstract class DemoWebServiceProviderTemplate {

	private Logger logger = Logger.getLogger(this.getClass());
	
//	abstract public String key();
	
	/*** 
     * 调用WebService接口
     * 
     */  
	public <X extends Request,T extends DTO> List<T> request(X request,Class<T> dtoClazz,String cmd) throws AccessDemoWebServiceException,CreateDemoWebServiceRequestException,ParseDemoWebServiceResponseException,DemoWebServiceReturnException {
		return request(request,dtoClazz,cmd,false);
	}
	
    /*** 
     * 调用WebService接口
     * 
     */  
	public <X extends Request,T extends DTO> List<T> request(X request,Class<T> dtoClazz,String cmd,boolean singleResult) throws AccessDemoWebServiceException,CreateDemoWebServiceRequestException,ParseDemoWebServiceResponseException,DemoWebServiceReturnException {

		logger.debug("-----------开始时间：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(new Date()));
		String requestXml=loadRequestXml(request,cmd);
		
		logger.debug("请求数据：" + requestXml);
		
		String response="";
		DemoWebService demoWebService=(DemoWebService)SpringContextHolder.getBean("demoWebService");

		logger.debug("demoWebService：" + demoWebService);
		try {
			logger.debug("-----------请求时间：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(new Date()));
			response=demoWebService.execute(requestXml);
			logger.debug("-----------请求结束时间：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(new Date()));
			logger.debug("执行返回：" + response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccessDemoWebServiceException(e);
		}
		
		List<T> result=parseResponse(dtoClazz,response,singleResult);
		logger.debug("-----------解析时间：" + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(new Date()));
		
		return result;
	}
	
	/*** 
     * 创建请求对象
     * 
     * <input>                                  
     *    <key>xxxxxx</key>                    
     *    <cmd>sendSysMail</cmd>                   
     *    <domain>0</domain>                       
     *    <mailsubject>...</mailsubject>
     *    ...
     *</input>
     */  
	public <X extends Request> String loadRequestXml(X request,String cmd) throws CreateDemoWebServiceRequestException{
		
		try {

			StringWriter requestStringWriter=new StringWriter();
			XMLOutputter out = new XMLOutputter();
			
			//创建文档
		    Document document = new Document();
		    //创建根元素
		    Element input = new Element("input");
		    //把根元素加入到document中
		    document.addContent(input); 
		    
		    Element key = new Element("key");
		    key.setText("xxxxxx");
		    input.addContent(key); 
		    
		    Element domain = new Element("domain");
		    domain.setText("0");
		    input.addContent(domain); 

		    Element command = new Element("cmd");
		    command.setText(cmd);
		    input.addContent(command);
			
			//得到request类
			Class<?> requestClazz=request.getClass();

			logger.debug("request类："+requestClazz.getName());
			
			//得到全部属性
			java.lang.reflect.Field[] pubProps = requestClazz.getSuperclass().getDeclaredFields();
			java.lang.reflect.Field[] thisProps = requestClazz.getDeclaredFields();
			
			//合并两个属性数组
			java.lang.reflect.Field[] props =new java.lang.reflect.Field[pubProps.length+thisProps.length];
			System.arraycopy(pubProps, 0, props, 0, pubProps.length);
			System.arraycopy(thisProps, 0, props, pubProps.length, thisProps.length);
			
			logger.debug("request类属性："+Arrays.asList(props));
			logger.debug("request类属性数量："+props!=null?props.length:0);
			
			//得到全部方法（注意：包含了Object方法）
			Method[] methods=requestClazz.getMethods();

			logger.debug("request类方法："+Arrays.toString(methods));
			
			//如果没有属性则返回
			if (props==null||props.length<1) {
				out.output(document,requestStringWriter);
		        return requestStringWriter.toString();
			}
			
			//如果没有方法则返回
			if (methods==null||methods.length<1) {
				out.output(document,requestStringWriter);
				return requestStringWriter.toString();
			}

			//遍历属性，将属性装入body中
			for(java.lang.reflect.Field prop:props) {
				logger.debug("属性名称："+prop.getName());
				
				//默认xml标签为属性名
				String fieldName=prop.getName();
				if (prop.isAnnotationPresent(DemoWebServiceRequestAnnotation.class)) {
					DemoWebServiceRequestAnnotation anno = (DemoWebServiceRequestAnnotation)prop.getAnnotation(DemoWebServiceRequestAnnotation.class); 
					fieldName=anno.value();
				}
				
				logger.debug("字段名称："+fieldName);
				
				//得到属性值
				Object value=null;
				for (Method currentMethod:methods) {
					if (currentMethod.getName().equals("get"+prop.getName().substring(0,1).toUpperCase()+prop.getName().substring(1))) {
						value=currentMethod.invoke(request);
						break;
					}
				}
				if (value==null) {
					value="";
				}
				
				logger.debug("值："+value);
				
			    Element element = new Element(fieldName);
			    element.setText(value.toString());
			    input.addContent(element);
			}

			//去除<?xml version="1.0" encoding="UTF-8"?>
			Format format=Format.getRawFormat();
			format.setOmitDeclaration(true);
			
			out.setFormat(format);
			out.output(document,requestStringWriter);
			
			String result=requestStringWriter.toString();
	        return result;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new CreateDemoWebServiceRequestException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new CreateDemoWebServiceRequestException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new CreateDemoWebServiceRequestException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CreateDemoWebServiceRequestException(e);
		}
	}
	
	/*** 
     * 解析响应对象
     * 
     * <output>
     *    <message>
     *        <result>1</result>
     *        <description>收件箱信息获取成功</description>
     *    </message>
     *	  <data>
     *     <recordCount>4</recordCount>
     *     <pageCount>2</pageCount>
     *     <list>
     *     ...
     *     </list>
     *     <list>
     *     ...
     *     </list>
     *     ...
     *	  </data>
     *</output>
     */  
	public <T extends DTO> List<T> parseResponse(Class<T> dtoClazz,String response,boolean singleResult) throws ParseDemoWebServiceResponseException,DemoWebServiceReturnException{
				
		List<T> result=new ArrayList<T>();
				
		try {
	        // 解析XML文档
	        SAXBuilder builder = new SAXBuilder();
	        
	        // 将xmlFile转化为Document
	        Document doc = builder.build(new StringReader(response));
	        
	        // 获得根元素
	        Element root = doc.getRootElement();
	        
	        Element message = root.getChild("message");
	        String flag=message.getChildText("result");
	        String description=message.getChildText("description");
	        logger.debug("flag:"+flag);
	        logger.debug("description:"+description);
	        if (flag==null||!flag.equals("0")) {
	        	throw new DemoWebServiceReturnException(description);
	        }
			
	        Element data = root.getChild("data");
	        List<Element> dataList=data.getChildren();
	        Map<String,String> dataMap=new HashMap<String,String>();
	        if (dataList==null) {
	        	//无返回内容的情况（增删改操作等）
	        	return result;
	        }
	        for (Element dataElement:dataList) {
	        	if (!dataElement.getName().equals("list")) {
	        		dataMap.put(dataElement.getName(), dataElement.getText());
	        	}
	        }

	        List<Element> list=data.getChildren("list");
	        
			//得到全部属性
			java.lang.reflect.Field[] props = dtoClazz.getDeclaredFields();
			
			//如果没有属性则返回
			if (props==null||props.length<1) {
				return result;
			}
			
			//得到全部方法
			Method[] methods=dtoClazz.getDeclaredMethods();
			
			//如果没有方法则返回
			if (methods==null||methods.length<1) {
				return result;
			}

			//返回为某个值，而不是数组的情况
			if (singleResult) {
				T instance=(T)dtoClazz.newInstance();
				
				//遍历属性，将Field装入属性
				for(java.lang.reflect.Field prop:props) {

					//默认xml标签为属性名
					String fieldName=prop.getName();
					Class<?>  cls=String.class;
					if (prop.isAnnotationPresent(DemoWebServiceDTOAnnotation.class)) {
						DemoWebServiceDTOAnnotation anno = (DemoWebServiceDTOAnnotation)prop.getAnnotation(DemoWebServiceDTOAnnotation.class); 
						fieldName=anno.value();
						cls=anno.fieldClass();
					}
					
					if (cls==String.class) {
						//字段为字符串类型
						String value=dataMap.get(fieldName);
						
						for (Method currentMethod:methods) {
							if (currentMethod.getName().equals("set"+prop.getName().substring(0,1).toUpperCase()+prop.getName().substring(1))) {							
								currentMethod.invoke(instance,value);
								break;
							}
						}
					}else{
						//字段为子类别
						List<Object> subList=new ArrayList<Object>(); 
						
						Element subContentElement=data.getChild(fieldName);
						List<Element> subElementlist=subContentElement.getChildren();
						
						//得到子类别全部属性
						java.lang.reflect.Field[] subProps = cls.getDeclaredFields();
						
						//如果没有属性则返回
						if (subProps==null) {
							break;
						}
						
						//得到全部方法
						Method[] subMethods=cls.getDeclaredMethods();
						
						//如果没有方法则返回
						if (subMethods==null) {
							break;
						}
						
						for (Element subElement:subElementlist) {
							
							Object subInstance=cls.newInstance();

							//遍历属性，将Field装入属性
							for(java.lang.reflect.Field subProp:subProps) {
								//默认xml标签为属性名
								String subFieldName=subProp.getName();
								if (subProp.isAnnotationPresent(DemoWebServiceDTOAnnotation.class)) {
									DemoWebServiceDTOAnnotation anno = (DemoWebServiceDTOAnnotation)subProp.getAnnotation(DemoWebServiceDTOAnnotation.class); 
									subFieldName=anno.value();
								}
								
								String subValue=subElement.getChildText(subFieldName);
								
								for (Method currentMethod:subMethods) {
									if (currentMethod.getName().equals("set"+subProp.getName().substring(0,1).toUpperCase()+subProp.getName().substring(1))) {							
										currentMethod.invoke(subInstance,subValue);
										break;
									}
								}
							}
							
							subList.add(subInstance);
						}
						
						for (Method currentMethod:methods) {
							if (currentMethod.getName().equals("set"+prop.getName().substring(0,1).toUpperCase()+prop.getName().substring(1))) {							
								currentMethod.invoke(instance,subList);
								break;
							}
						}
					}
				}
				
				result.add(instance);
			}else{
				//遍历返回结果
				if (list!=null){
					for (int i=0;i<list.size();i++) {
						Element listElement=list.get(i);
						
						T instance=(T)dtoClazz.newInstance();
						
						//遍历属性，将Field装入属性
						for(java.lang.reflect.Field prop:props) {

							//默认xml标签为属性名
							String fieldName=prop.getName();
							DemoWebServiceArea aera=DemoWebServiceArea.LIST;
							if (prop.isAnnotationPresent(DemoWebServiceDTOAnnotation.class)) {
								DemoWebServiceDTOAnnotation anno = (DemoWebServiceDTOAnnotation)prop.getAnnotation(DemoWebServiceDTOAnnotation.class); 
								fieldName=anno.value();
								aera=anno.area();
							}
							
							String value="";
							if (aera==DemoWebServiceArea.LIST) {
								value=listElement.getChildText(fieldName);
							}else{
								value=dataMap.get(fieldName);
							}
							
							for (Method currentMethod:methods) {
								if (currentMethod.getName().equals("set"+prop.getName().substring(0,1).toUpperCase()+prop.getName().substring(1))) {							
									currentMethod.invoke(instance,value);
									break;
								}
							}
						}
						
						result.add(instance);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ParseDemoWebServiceResponseException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ParseDemoWebServiceResponseException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ParseDemoWebServiceResponseException(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new ParseDemoWebServiceResponseException(e);
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new ParseDemoWebServiceResponseException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseDemoWebServiceResponseException(e);
		}
		return result;
	}
}
