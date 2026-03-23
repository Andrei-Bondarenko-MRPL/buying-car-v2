package com.example.buyingcarv2.service;

import com.example.buyingcarv2.config.OrderMapper;
import com.example.buyingcarv2.dto.OrderDto;
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
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDto getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchOrderExistsException(String.format("Order was not found by this Id=%d", orderId)));

        return orderMapper.toOrderDto(order);
    }

    @Override
    public List<OrderDto> getOrderList() {
        List<OrderDto> orders = new ArrayList<>();
        orderRepository.findAll()
                .forEach(order -> orders.add(orderMapper.toOrderDto(order)));

        return orders;
    }

    @Override
    public OrderDto saveOrder(Order order) {
        if (order.getId() != null && orderRepository.existsById(order.getId())) {
            throw new OrderAlreadyExistsException(String.format("Order with this Id=%d already exists", order.getId()));
        }

        return orderMapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto updateOrder(Order order, Long orderId) {
        Order updatedOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchOrderExistsException(String.format("Updated order was not found by Id=%d", orderId)));

        if (Objects.nonNull(order.getCars())) {
            updatedOrder.getCars().clear();
            updatedOrder.getCars().addAll(order.getCars());
        }

        if (Objects.nonNull(order.getClient())) {
            updatedOrder.setClient(order.getClient());
        }

        Order savedOrder = orderRepository.save(updatedOrder);

        return orderMapper.toOrderDto(savedOrder);
    }

    @Override
    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
