package com.royal.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.royal.core.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	//
}
