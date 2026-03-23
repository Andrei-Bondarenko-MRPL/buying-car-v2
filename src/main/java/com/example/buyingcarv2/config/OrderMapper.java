package com.example.buyingcarv2.config;

import com.example.buyingcarv2.dto.OrderDto;
import com.example.buyingcarv2.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDto toOrderDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
