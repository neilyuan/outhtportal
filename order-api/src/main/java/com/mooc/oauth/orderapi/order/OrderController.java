package com.mooc.oauth.orderapi.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

	@PostMapping
	public OrderInfo create(@RequestBody OrderInfo orderInfo) {
		return orderInfo;
	}

	@GetMapping("/{id}")
	public Long getOrder(@PathVariable Long id) {
		return id;
	}

}
