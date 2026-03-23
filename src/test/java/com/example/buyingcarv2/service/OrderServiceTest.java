package com.example.buyingcarv2.service;

import com.example.buyingcarv2.config.OrderMapper;
import com.example.buyingcarv2.dto.CarDto;
import com.example.buyingcarv2.dto.OrderDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private List<Order> orders;

    @BeforeEach
    public void init() {
        Order orderFirst = new Order();
        orderFirst.setId(1L);
        orderFirst.setCars(new ArrayList<>());

        Order orderSecond = new Order();
        orderSecond.setId(2L);
        orderSecond.setCars(new ArrayList<>());

        orders = List.of(orderFirst, orderSecond);
    }

    @Test
    public void shouldGetOrderByIdTest() {
        OrderDto order = orderMapper.toOrderDto(orders.get(0));

        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(orders.get(0)));

        OrderDto orderById = orderService.getOrderById(1L);

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

        List<OrderDto> orderDtos = orders.stream()
                .map(order -> orderMapper.toOrderDto(order))
                .toList();

        List<OrderDto> orderList = orderService.getOrderList();

        assertEquals(orderDtos, orderList);
    }

    @Test
    public void shouldReturnEmptyListTest() {
        List<Order> emptyOrderList = List.of();

        when(orderRepository.findAll()).thenReturn(emptyOrderList);

        List<OrderDto> orderList = orderService.getOrderList();

        assertTrue(orderList.isEmpty());
    }

    @Test
    public void shouldSaveNewOrderTest() {
        Order savedOrder = new Order();
        savedOrder.setId(1L);

        OrderDto dto = new OrderDto();

        when(orderRepository.save(savedOrder)).thenReturn(savedOrder);
        when(orderMapper.toOrderDto(savedOrder)).thenReturn(dto);

        OrderDto orderResult = orderService.saveOrder(savedOrder);

        assertNotNull(orderResult);
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
        Car car = new Car(1L, "Mercedes", "Black", 20000L);

        Client client = new Client();
        client.setId(1L);

        Order order = new Order();
        order.setCars(List.of(car));
        order.setClient(client);

        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(orders.get(0)));
        when(orderRepository.save(orders.get(0))).thenReturn(orders.get(0));

        OrderDto orderDto = new OrderDto();
        orderDto.setCars(List.of(new CarDto("Mercedes", "Black", 20000L)));

        when(orderMapper.toOrderDto(any(Order.class))).thenReturn(orderDto);

        OrderDto updatedOrder = orderService.updateOrder(order, 1L);

        String model = updatedOrder.getCars().get(0).getModel();
        assertEquals(model, car.getModel());
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
