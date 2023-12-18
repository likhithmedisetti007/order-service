package com.likhith.order.dto;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class ShoppingCartResponse {

	private String id;
	private String userId;
	private Set<String> productIds;
	private List<Product> products;
	private String subTotal;

}