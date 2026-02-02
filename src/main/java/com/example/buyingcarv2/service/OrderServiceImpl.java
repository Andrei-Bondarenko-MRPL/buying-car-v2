package com.example.buyingcarv2.service;

import com.example.buyingcarv2.exception.NoSuchOrderExistsException;
import com.example.buyingcarv2.exception.OrderAlreadyExistsException;
import com.example.buyingcarv2.model.Order;
import com.example.buyingcarv2.repository.OrderRepository;
import com.example.buyingcarv2.service.interfaces.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchOrderExistsException(String.format("Order was not found by this Id=%d", orderId)));
    }

    @Override
    public List<Order> getOrderList() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(orders::add);

        return orders;
    }

    @Override
    public Order saveOrder(Order order) {
        if (order.getId() != null && orderRepository.existsById(order.getId())) {
            throw new OrderAlreadyExistsException(String.format("Order with this Id=%d already exists", order.getId()));
        }

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Order order, Long orderId) {
        Order updatedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchOrderExistsException(String.format("Updated order was not found by Id=%d", orderId)));

        if (Objects.nonNull(order.getCars())) {
            updatedOrder.setCars(order.getCars());
        }

        if (Objects.nonNull(order.getClient())) {
            updatedOrder.setClient(order.getClient());
        }

        updatedOrder.getCars().forEach(car -> car.setOrder(updatedOrder));

        return orderRepository.save(updatedOrder);
    }

    @Override
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
