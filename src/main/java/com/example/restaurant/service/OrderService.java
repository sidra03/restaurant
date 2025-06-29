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

        if (items.isEmpty()) {
            throw new IllegalArgumentException("No valid menu items found for the order.");
        }

        // Calculate total price (consider quantity if available in requestDto, if not, assumed 1 per item)
        double total = items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();

        // Calculate total chillies and check if all dishes are hot
        int totalChillies = items.stream()
                .mapToInt(MenuItem::getChillies)
                .sum();

        boolean allHot = items.stream()
                .allMatch(item -> item.getChillies() > 0);

        boolean anyHot = totalChillies >= 5;

        // Apply 10% discount if all dishes are hot
        double discount = 0.0;
        if (allHot && anyHot) {
            discount = total * 0.10;
            total = total * 0.90;
        }

        // Create order entity
        Order order = Order.builder()
                .items(items)
                .total(total)
                .status("pending")
                .hotOrder(anyHot)
                .chillies(totalChillies)
                .customerName(requestDto.getCustomerName())
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderResponseDto responseDto = orderMapper.toDto(savedOrder);
        responseDto.setDiscount(discount);
        responseDto.setTotalChillies(totalChillies);
        responseDto.setHot(anyHot);

        // Set message depending on order content
        if (allHot && anyHot) {
            responseDto.setMessage("🔥 All dishes are hot! You got a 10% discount.");
        } else if (anyHot) {
            responseDto.setMessage("🌶 Your order is hot with a total of " + totalChillies + " chillies.");
        } else {
            responseDto.setMessage("Thank you for your order!");
        }

        return responseDto;
    }

    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return orderMapper.toDto(order);
    }
}
