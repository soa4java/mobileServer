package com.yuchengtech.mobile.server.adapter.request.demowebservice;

import com.yuchengtech.mobile.server.adapter.common.annotation.demowebservice.DemoWebServiceRequestAnnotation;
import com.yuchengtech.mobile.server.adapter.request.Request;

public class DemoProductListRequest implements Request{

	/**
	 * 产品类型编号
	 */
	@DemoWebServiceRequestAnnotation("productTypeCode")
	private String productTypeCode;

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
}
