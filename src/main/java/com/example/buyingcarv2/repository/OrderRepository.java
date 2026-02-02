package com.example.buyingcarv2.repository;

import com.example.buyingcarv2.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
