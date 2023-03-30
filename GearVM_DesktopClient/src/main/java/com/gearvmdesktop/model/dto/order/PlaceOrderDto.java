package com.gearvmdesktop.model.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderDto {
    private String phoneNumber;
    private double totalPrice;
    private List<OrderItemDto> orderItems;
}