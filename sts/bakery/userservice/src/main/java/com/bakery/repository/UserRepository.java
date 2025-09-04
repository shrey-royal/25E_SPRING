package com.bakery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bakery.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	//
}
