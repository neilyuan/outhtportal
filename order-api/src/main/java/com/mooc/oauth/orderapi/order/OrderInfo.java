package com.mooc.oauth.orderapi.order;

import lombok.Data;

@Data
public class OrderInfo {
	private Long orderId;
	private Long userId;
	private Long productId;
}
