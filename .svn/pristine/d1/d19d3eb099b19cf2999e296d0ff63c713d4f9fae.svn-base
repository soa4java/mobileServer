package com.yuchengtech.mobile.server.adapter.common.annotation.demowebservice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.FIELD)
public @interface DemoWebServiceDTOAnnotation {  
    
    /**
	 * 字段名
	 */
	public String value();

    /**
	 * 字段名
	 */
	public Class<?> fieldClass() default String.class;

	/**
	 * 区域
	 */
	public DemoWebServiceArea area() default DemoWebServiceArea.LIST;
}  
