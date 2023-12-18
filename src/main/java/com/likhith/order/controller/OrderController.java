package com.likhith.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likhith.order.document.Order;
import com.likhith.order.dto.OrderResponse;
import com.likhith.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService service;

	@GetMapping("/getAllOrders/{userId}")
	public ResponseEntity<List<OrderResponse>> getAllOrders(@PathVariable("userId") String userId) {
		List<OrderResponse> responseList = service.getAllOrders(userId);
		if (CollectionUtils.isEmpty(responseList)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
		} else {
			return ResponseEntity.ok().body(responseList);
		}
	}

	@GetMapping("/getOrder/{id}")
	public ResponseEntity<OrderResponse> getOrder(@PathVariable("id") String id) {
		OrderResponse response = service.getOrder(id);
		if (ObjectUtils.isEmpty(response)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).build();
		} else {
			return ResponseEntity.ok().body(response);
		}
	}

	@GetMapping("/createOrder/{userId}")
	public ResponseEntity<String> createOrder(@PathVariable("userId") String userId) {
		return ResponseEntity.ok().body(service.createOrder(userId));
	}

	@PutMapping("/updateOrder")
	public ResponseEntity<String> updateOrder(@RequestBody Order order) {
		return ResponseEntity.ok().body(service.updateOrder(order));
	}

	@PostMapping("/submitOrder/{userId}")
	public ResponseEntity<String> submitOrder(@PathVariable("userId") String userId) {
		return ResponseEntity.ok().body(service.submitOrder(userId));
	}

	@DeleteMapping("/cancelOrder/{id}")
	public ResponseEntity<String> cancelOrder(@PathVariable("id") String id) {
		return ResponseEntity.ok().body(service.cancelOrder(id));
	}

}