package com.likhith.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.likhith.order.document.Order;
import com.likhith.order.dto.OrderResponse;

@Service
public interface OrderService {

	List<OrderResponse> getAllOrders(String userId);

	OrderResponse getOrder(String id);

	String createOrder(String userId);

	String updateOrder(Order order);

	String submitOrder(String userId);

	String cancelOrder(String id);

}