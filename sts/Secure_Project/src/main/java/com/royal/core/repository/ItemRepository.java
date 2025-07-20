package com.royal.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.royal.core.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	//
}