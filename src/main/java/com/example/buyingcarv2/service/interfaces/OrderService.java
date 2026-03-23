package com.example.buyingcarv2.service.interfaces;

import com.example.buyingcarv2.dto.OrderDto;
import com.example.buyingcarv2.model.Order;

import java.util.List;

public interface OrderService {
    OrderDto saveOrder(Order order);
    OrderDto getOrderById(Long id);
    List<OrderDto> getOrderList();
    OrderDto updateOrder(Order order, Long id);
    void deleteOrderById(Long id);
}
