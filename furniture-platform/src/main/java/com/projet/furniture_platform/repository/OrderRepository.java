package com.projet.furniture_platform.repository;

import com.projet.furniture_platform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
