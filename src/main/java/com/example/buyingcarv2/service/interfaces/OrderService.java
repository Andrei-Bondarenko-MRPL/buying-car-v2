package com.example.buyingcarv2.service.interfaces;

import com.example.buyingcarv2.model.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getOrderList();
    Order updateOrder(Order order, Long id);
    void deleteOrderById(Long id);
}
