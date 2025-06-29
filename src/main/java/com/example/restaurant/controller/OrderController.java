package com.example.restaurant.controller;

import com.example.restaurant.dto.OrderRequestDto;
import com.example.restaurant.dto.OrderResponseDto;
import com.example.restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
