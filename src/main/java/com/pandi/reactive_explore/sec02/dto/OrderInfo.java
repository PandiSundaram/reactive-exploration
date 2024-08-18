package com.pandi.reactive_explore.sec02.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderInfo(UUID orderId, String customerName, String productName,
                        Integer amount,
                        Instant orderDate) {
}
