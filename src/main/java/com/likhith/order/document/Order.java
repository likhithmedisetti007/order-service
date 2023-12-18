package com.likhith.order.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.likhith.order.dto.Product;

import lombok.Data;

@Document(collection = "order")
@Data
public class Order {

	@Id
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