package com.example.buyingcarv2.service;

import com.example.buyingcarv2.exception.NoSuchOrderExistsException;
import com.example.buyingcarv2.exception.OrderAlreadyExistsException;
import com.example.buyingcarv2.model.Car;
import com.example.buyingcarv2.model.Client;
import com.example.buyingcarv2.model.Order;
import com.example.buyingcarv2.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private List<Order> orders;

    @BeforeEach
    public void init() {
        Order orderFirst = new Order();
        orderFirst.setId(1L);

        Order orderSecond = new Order();
        orderSecond.setId(2L);

        orders = List.of(orderFirst, orderSecond);
    }

    @Test
    public void shouldGetOrderByIdTest() {
        Order order = orders.get(0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order orderById = orderService.getOrderById(1L);

        assertEquals(order, orderById);
    }

    @Test
    public void shouldThrowExceptionWhenGetOrderByIdTest() {
        when(orderRepository.findById(1L))
                .thenThrow(NoSuchOrderExistsException.class);

        assertThrows(NoSuchOrderExistsException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    public void shouldReturnAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> orderList = orderService.getOrderList();

        assertEquals(orders, orderList);
    }

    @Test
    public void shouldReturnEmptyListTest() {
        List<Order> emptyOrderList = List.of();

        when(orderRepository.findAll()).thenReturn(emptyOrderList);

        List<Order> orderList = orderService.getOrderList();

        assertTrue(orderList.isEmpty());
    }

    @Test
    public void shouldSaveNewOrderTest() {
        Order savedOrder = new Order();
        savedOrder.setId(1L);

        when(orderRepository.save(savedOrder)).thenReturn(savedOrder);

        Order orderResult = orderService.saveOrder(savedOrder);

        assertEquals(1L, orderResult.getId());
        verify(orderRepository).save(savedOrder);
    }

    @Test
    public void shouldThrowExceptionSaveExistsOrder() {
        when(orderRepository.existsById(1L)).thenReturn(true);

        assertThrows(OrderAlreadyExistsException.class, () -> orderService.saveOrder(orders.get(0)));
        verify(orderRepository).existsById(1L);
    }

    @Test
    public void shouldUpdateOrderTest() {
        Car car = new Car(1L, "Mercedes", "Black", 20000L, orders.get(1));

        Client client = new Client();
        client.setId(1L);

        Order order = new Order();
        order.setCars(List.of(car));
        order.setClient(client);

        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(orders.get(0)));
        when(orderRepository.save(orders.get(0))).thenReturn(orders.get(0));

        Order updatedOrder = orderService.updateOrder(order, 1L);

        Long carId = updatedOrder.getCars().get(0).getId();
        assertEquals(carId, car.getId());
    }

    @Test
    public void shouldThrowExceptionUpdateOrderTest() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenThrow(NoSuchOrderExistsException.class);

        assertThrows(NoSuchOrderExistsException.class, () -> orderService.updateOrder(order, 1L));
    }

    @Test
    public void shouldDeleteOrderByIdTest() {
        orderService.deleteOrderById(1L);

        verify(orderRepository).deleteById(1L);
    }
}
