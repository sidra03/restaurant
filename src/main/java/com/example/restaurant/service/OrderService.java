package com.example.restaurant.service;

import com.example.restaurant.dto.MenuItemDto;
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

        // Calculate total price
        double total = items.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();

        // Calculate total chillies and check if all dishes are hot
        int totalChillies = items.stream()
                .mapToInt(item -> item.getSpiceLevel() != null ? item.getSpiceLevel().getChillies() : 0)
                .sum();

        boolean allHot = items.stream()
                .allMatch(item -> item.getSpiceLevel() != null && item.getSpiceLevel().getChillies() >= 2);

        boolean anyHot = totalChillies >= 1;

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

        // Add discount, total chillies, hot flags
        responseDto.setDiscount(discount);
        responseDto.setChillies(totalChillies);
        responseDto.setHotOrder(anyHot);
        responseDto.setCustomerName(order.getCustomerName());

        List<MenuItemDto> itemDtos = items.stream()
                .map(item -> MenuItemDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .chillies(item.getSpiceLevel() != null ? item.getSpiceLevel().getChillies() : 0)
                        .category(item.getCategory().name())
                        .build())
                .toList();

        responseDto.setOrderedItems(itemDtos);

        // Set message depending on order content
        if (allHot && anyHot) {
            responseDto.setMessage("All dishes are hot! You got a 10% discount.");
        } else if (anyHot) {
            responseDto.setMessage("Your order is hot with a total of " + totalChillies + " chillies.");
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
