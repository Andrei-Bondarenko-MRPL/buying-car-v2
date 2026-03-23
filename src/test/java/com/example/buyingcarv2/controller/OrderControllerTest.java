package com.example.buyingcarv2.controller;

import com.example.buyingcarv2.dto.CarDto;
import com.example.buyingcarv2.dto.ClientDto;
import com.example.buyingcarv2.dto.OrderDto;
import com.example.buyingcarv2.model.Order;
import com.example.buyingcarv2.service.interfaces.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    private Order order;

    private OrderDto orderDto;

    private List<OrderDto> ordersDto;

    @BeforeEach
    public void init() {
        order = new Order();
        order.setId(1L);

        ClientDto client = new ClientDto("John", "Doe", "+380501112233");

        CarDto car = new CarDto("Toyota", "Black", 30000L);

        orderDto = new OrderDto();
        orderDto.setClient(client);
        orderDto.setCars(List.of(car));

        ordersDto = List.of(orderDto);
    }

    @Test
    public void shouldCallGetOrderByIdTest() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(orderDto);

        mockMvc.perform(get("/order/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client.firstName").value("John"));

        verify(orderService).getOrderById(1L);
    }

    @Test
    public void shouldCallGetOrderList() throws Exception {
        when(orderService.getOrderList()).thenReturn(ordersDto);

        mockMvc.perform(get("/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].client.firstName").value("John"));

        verify(orderService).getOrderList();
    }

    @Test
    public void shouldCallSaveOrderTest() throws Exception {
        when(orderService.saveOrder(any(Order.class))).thenReturn(orderDto);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client.firstName").value("John"));

        verify(orderService).saveOrder(any(Order.class));
    }

    @Test
    public void shouldCallUpdateOrderByIdTest() throws Exception {
        when(orderService.updateOrder(any(Order.class), eq(1L)))
                .thenReturn(orderDto);

        mockMvc.perform(put("/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.client.firstName").value("John"));

        verify(orderService).updateOrder(any(Order.class), eq(1L));
    }

    @Test
    public void shouldCallDeleteOrderById() throws Exception {
        mockMvc.perform(delete("/order/1"))
                .andExpect(status().isOk());

        verify(orderService).deleteOrderById(1L);
    }
}
