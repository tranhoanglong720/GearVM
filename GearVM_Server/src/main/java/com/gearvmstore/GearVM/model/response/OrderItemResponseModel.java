package com.gearvmstore.GearVM.model.response;

import lombok.Data;

@Data
public class OrderItemResponseModel {
    private Long id;
    private ProductResponseModel product;
    private int quantity;
    private double price;
}
