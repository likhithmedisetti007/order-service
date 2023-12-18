package com.likhith.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.likhith.order.dto.ShoppingCartResponse;

@FeignClient(name = "shopping-cart-service", path = "/shopping-cart")
public interface ShoppingCartClient {

	@GetMapping("/getCart/{userId}")
	ShoppingCartResponse getCart(@PathVariable("userId") String userId);

	@DeleteMapping("/clearCart/{userId}")
	String clearCart(@PathVariable("userId") String userId);

}