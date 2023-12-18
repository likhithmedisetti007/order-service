package com.likhith.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.likhith.order.document.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByUserId(String userId);

	Optional<Order> findByUserIdAndLatest(String userId, boolean latest);

}