package com.likhith.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.likhith.order.client.ShoppingCartClient;
import com.likhith.order.document.Order;
import com.likhith.order.dto.OrderResponse;
import com.likhith.order.dto.ShoppingCartResponse;
import com.likhith.order.repository.OrderRepository;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ShoppingCartClient shoppingCartClient;

	@Override
	public List<OrderResponse> getAllOrders(String userId) {

		List<OrderResponse> orderServiceResponseList = new ArrayList<>();
		List<Order> orders = orderRepository.findByUserId(userId);

		if (!CollectionUtils.isEmpty(orders)) {
			orderServiceResponseList = orders.stream().map(order -> {
				OrderResponse orderServiceResponse = new OrderResponse();
				BeanUtils.copyProperties(order, orderServiceResponse);
				return orderServiceResponse;
			}).collect(Collectors.toList());
		}

		return orderServiceResponseList;
	}

	@Override
	public OrderResponse getOrder(String id) {

		OrderResponse orderResponse = new OrderResponse();
		Optional<Order> order = orderRepository.findById(id);

		if (order.isPresent()) {
			BeanUtils.copyProperties(order.get(), orderResponse);
			return orderResponse;
		} else {
			return null;
		}
	}

	@Override
	public String createOrder(String userId) {

		String message = null;
		Optional<Order> orderFromDB = orderRepository.findByUserIdAndLatest(userId, true);

		if (orderFromDB.isEmpty()) {
			Order order = new Order();
			order.setUserId(userId);
			order.setLatest(true);
			orderRepository.save(order);
			message = "Order created successfully";
		} else {
			message = "There is a pending order for the user. Please cancel it first before creating a new Order";
		}

		return message;
	}

	@Override
	public String updateOrder(Order order) {

		String message = null;
		Optional<Order> orderFromDB = orderRepository.findByUserIdAndLatest(order.getUserId(), true);

		if (orderFromDB.isPresent()) {
			orderFromDB.get().setAddress(order.getAddress());
			orderFromDB.get().setContact(order.getContact());
			orderFromDB.get().setEmail(order.getEmail());

			orderRepository.save(orderFromDB.get());

			message = "Order updated successfully";
		} else {
			message = "No order found that can be updated";
		}

		return message;
	}

	@Override
	public String submitOrder(String userId) {

		String message = null;
		Optional<Order> orderFromDB = orderRepository.findByUserIdAndLatest(userId, true);

		if (orderFromDB.isPresent()) {
			ShoppingCartResponse cartResponse = shoppingCartClient.getCart(userId);

			orderFromDB.get().setProducts(cartResponse.getProducts());
			orderFromDB.get().setSubTotal(cartResponse.getSubTotal());
			orderFromDB.get().setLatest(false);

			orderRepository.save(orderFromDB.get());

			shoppingCartClient.clearCart(userId);

			message = "Order submitted successfully";
		} else {
			message = "No order found that can be submitted";
		}

		return message;
	}

	@Override
	public String cancelOrder(String id) {

		String message = null;
		Optional<Order> orderFromDB = orderRepository.findById(id);

		if (orderFromDB.isPresent()) {
			orderRepository.delete(orderFromDB.get());
			message = "Order cancelled successfully";
		} else {
			message = "No order found that can be cancelled";
		}

		return message;
	}

}