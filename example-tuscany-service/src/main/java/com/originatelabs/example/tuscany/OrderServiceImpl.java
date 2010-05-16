package com.originatelabs.example.tuscany;

import org.osoa.sca.annotations.Service;

import com.originatelabs.example.service.CreateOrderRequest;
import com.originatelabs.example.service.CreateOrderResponse;
import com.originatelabs.example.service.ServiceFactory;

@Service(OrderService.class)
public class OrderServiceImpl implements OrderService {

	public String ping(String request) {
		return request;
	}
	
	public CreateOrderResponse create(CreateOrderRequest request) {
		CreateOrderResponse response = ServiceFactory.INSTANCE.createCreateOrderResponse();
		return response;
	}
	
}
