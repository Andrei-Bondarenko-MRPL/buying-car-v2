package com.example.buyingcarv2.repository;

import com.example.buyingcarv2.model.Car;
import com.example.buyingcarv2.model.Client;
import com.example.buyingcarv2.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    public void init() {
        Client client = new Client();
        client.setFirstName("Bob");
        client.setLastName("Dilan");

        Car car = new Car();
        car.setModel("Mercedes");
        car.setColor("Black");

        List<Car> cars = new ArrayList<>();
        cars.add(car);

        order = new Order();
        order.setCars(cars);
        order.setClient(client);
    }

    @Test
    public void shouldFindOrderByIdTest() {
        Order savedOrder = orderRepository.save(order);
        Order orderById = orderRepository.findById(savedOrder.getId()).orElseThrow();

        assertEquals(savedOrder.getId(), orderById.getId());
        assertNotNull(orderById);
    }

    @Test
    public void shouldFindAllOrdersTest() {
        List<Order> orders = new ArrayList<>();

        orderRepository.save(order);
        orderRepository.findAll().forEach(orders::add);

        String model = orders.get(0).getCars().get(0).getModel();

        assertEquals(1, orders.size());
        assertEquals("Mercedes", model);
    }

    @Test
    public void shouldSaveOrderTest() {
        Order savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder);

        String carModel = savedOrder.getCars().get(0).getModel();
        assertEquals("Mercedes", carModel);
    }

    @Test
    public void shouldUpdateOrderTest() {
        Order orderToUpdate = orderRepository.save(order);
        orderToUpdate.getClient().setLastName("Jonson");

        Order savedOrder = orderRepository.save(orderToUpdate);
        String clientLastName = orderRepository.findById(savedOrder.getId()).orElseThrow()
                .getClient()
                .getLastName();

        assertEquals("Jonson", clientLastName);
    }

    @Test
    public void shouldDeleteOrderByIdTest() {
        Order savedOrder = orderRepository.save(order);

        orderRepository.deleteById(savedOrder.getId());
        Order deletedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        assertNull(deletedOrder);
    }
}
