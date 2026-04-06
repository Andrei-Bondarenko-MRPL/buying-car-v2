package com.example.buyingcarv2.repository;

import com.example.buyingcarv2.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<Order, UUID> {
}
