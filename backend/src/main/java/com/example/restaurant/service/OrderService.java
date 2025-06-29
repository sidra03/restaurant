package com.example.restaurant.service;

import com.example.restaurant.dto.OrderRequestDto;
import com.example.restaurant.dto.OrderResponseDto;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.entity.Order;
import com.example.restaurant.mappers.OrderMapper;
import com.example.restaurant.repository.MenuItemRepository;
import com.example.restaurant.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        // Fetch menu items by IDs
        List<MenuItem> items = menuItemRepository.findAllById(requestDto.getItemIds());

        // Calculate total price
        double total = items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();

        // Calculate total chillies and check if all hot
        int totalChillies = items.stream()
                .mapToInt(MenuItem::getChillies)
                .sum();
        boolean allHot = items.stream()
                .allMatch(item -> item.getChillies() > 0);

        // Apply 10% discount if all dishes hot
        double discount = 0.0;
        if (allHot) {
            discount = total * 0.10;
            total = total * 0.9;
        }

        // Create order entity
        Order order = Order.builder()
                .items(items)
                .total(total)
                .status("pending")
                .hotOrder(totalChillies >= 5)
                .chillies(totalChillies)
                .customerName(requestDto.getCustomerName())
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderResponseDto responseDto = orderMapper.toDto(savedOrder);
        responseDto.setDiscount(discount);

        return responseDto;
    }

    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(order);
    }
}
