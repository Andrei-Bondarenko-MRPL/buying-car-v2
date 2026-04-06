package com.example.buyingcarv2.service.interfaces;

import com.example.buyingcarv2.dto.OrderDto;
import com.example.buyingcarv2.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto saveOrder(Order order);
    OrderDto getOrderById(UUID id);
    List<OrderDto> getOrderList();
    OrderDto updateOrder(Order order, UUID id);
    void deleteOrderById(UUID id);
}
