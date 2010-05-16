package com.originatelabs.example.tuscany;

import org.osoa.sca.annotations.Remotable;

import com.originatelabs.example.service.CreateOrderRequest;
import com.originatelabs.example.service.CreateOrderResponse;

@Remotable
public interface OrderService {
	
	String ping(String request);
	
	CreateOrderResponse create(CreateOrderRequest request);

}
