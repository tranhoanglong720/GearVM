package com.gearvmstore.GearVM.model.dto.order;

import lombok.Data;

@Data
public class UpdateOrderItem {
    private Long productId;
    private String customerName;
    private String customerPhone;
    private int amount;
}
