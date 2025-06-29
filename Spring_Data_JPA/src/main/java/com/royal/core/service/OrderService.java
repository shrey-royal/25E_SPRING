package com.royal.core.service;

import java.util.List;
import java.util.Optional;

import com.royal.core.entity.Order;

public interface OrderService {
	Order saveOrder(Order order);
	List<Order> getAllOrders();
	Optional<Order> getOrderById(Long id);
	void deleteOrder(Long id);
}
