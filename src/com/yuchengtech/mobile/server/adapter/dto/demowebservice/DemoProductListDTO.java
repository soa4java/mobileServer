package com.yuchengtech.mobile.server.adapter.dto.demowebservice;

import com.yuchengtech.mobile.server.adapter.common.annotation.demowebservice.DemoWebServiceDTOAnnotation;
import com.yuchengtech.mobile.server.adapter.dto.DTO;

/**
 * 产品列表DTO
 *
 * @version 1.0
 *
 * @author sunliang
 */
public class DemoProductListDTO implements DTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 产品名称
	 */
	@DemoWebServiceDTOAnnotation(value="productName")
	private String productName;
	
	/**
	 * 投资方向
	 */
	@DemoWebServiceDTOAnnotation(value="investmentType")
	private String investmentType;

	/**
	 * 预计利率
	 */
	@DemoWebServiceDTOAnnotation(value="rate")
	private String rate;
    
	/**
	 * 产品代码
	 */
	@DemoWebServiceDTOAnnotation(value="productCode")
	private String productCode;

	/**
	 * 发售时间
	 */
	private String openDate;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
}
