package com.yuchengtech.mobile.server.adapter.service.demowebservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchengtech.mobile.server.adapter.dto.demowebservice.DemoProductDetailDTO;
import com.yuchengtech.mobile.server.adapter.dto.demowebservice.DemoProductListDTO;
import com.yuchengtech.mobile.server.adapter.dto.demowebservice.DemoProductTypeDTO;
import com.yuchengtech.mobile.server.adapter.provider.demowebservice.DemoProductProvider;
import com.yuchengtech.mobile.server.adapter.request.demowebservice.DemoProductDetailRequest;
import com.yuchengtech.mobile.server.adapter.request.demowebservice.DemoProductListRequest;
import com.yuchengtech.mobile.server.adapter.request.demowebservice.DemoProductTypeRequest;
import com.yuchengtech.mobile.server.adapter.service.BaseManager;
/**
 * Demo 产品 Manager
 *
 *
 * @version 1.0
 *
 * @author sunliang
 */
@Service
public class DemoProductManager extends BaseManager {
	@Autowired
	private DemoProductProvider demoProductProvider;


	public List<DemoProductListDTO> productList(DemoProductListRequest request) throws Exception {
		return demoProductProvider.productList(request);
	}


	public List<DemoProductTypeDTO> productType(DemoProductTypeRequest request) throws Exception {
		return demoProductProvider.productType(request);
	}


	public List<DemoProductDetailDTO> productDetail(
			DemoProductDetailRequest request) throws Exception  {
		return demoProductProvider.productDetail(request);
	}
}
