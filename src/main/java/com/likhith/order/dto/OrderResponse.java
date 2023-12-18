package com.likhith.order.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

	private String id;
	private String userId;
	private String address;
	private String contact;
	private String email;
	private List<Product> products;
	private String subTotal;
	private boolean latest;
	private String orderStatus;

}