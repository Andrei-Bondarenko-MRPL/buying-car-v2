package com.example.buyingcarv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
    private ClientDto client;
    private List<CarDto> cars;
}
