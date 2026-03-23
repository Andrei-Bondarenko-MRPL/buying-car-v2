package com.example.buyingcarv2.controller;

import com.example.buyingcarv2.dto.OrderDto;
import com.example.buyingcarv2.model.Order;
import com.example.buyingcarv2.service.interfaces.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping()
    public List<OrderDto> getOrderList() {
        return orderService.getOrderList();
    }

    @PostMapping()
    public OrderDto saveOrder(@Valid @RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("{orderId}")
    public OrderDto updateOrder(@RequestBody Order updatedOrder,
                             @PathVariable Long orderId) {
        return orderService.updateOrder(updatedOrder, orderId);
    }

    @DeleteMapping("{orderId}")
    public void deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
    }
}
