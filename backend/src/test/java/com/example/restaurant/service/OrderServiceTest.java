package com.example.restaurant.service;

import com.example.restaurant.dto.OrderRequestDto;
import com.example.restaurant.dto.OrderResponseDto;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.entity.Order;
import com.example.restaurant.mappers.OrderMapper;
import com.example.restaurant.repository.MenuItemRepository;
import com.example.restaurant.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder_AllHotItems_DiscountApplied() {
        // Prepare mock menu items
        MenuItem item1 = MenuItem.builder().id(1L).price(10.0).chillies(2).build();
        MenuItem item2 = MenuItem.builder().id(2L).price(20.0).chillies(1).build();

        List<MenuItem> items = List.of(item1, item2);

        // Mock repository to return these menu items
        when(menuItemRepository.findAllById(List.of(1L, 2L))).thenReturn(items);

        // Prepare the order entity to be saved (simulate DB save)
        Order savedOrder = Order.builder()
                .id(100L)
                .items(items)
                .total(27.0) // 10 + 20 = 30 with 10% discount = 27
                .status("pending")
                .hotOrder(true)
                .chillies(3)
                .customerName("John Doe")
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Prepare the DTO returned by mapper
        OrderResponseDto responseDto = OrderResponseDto.builder()
                .id(100L)
                .customerName("John Doe")
                .total(27)
                .status("pending")
                .hot(true)
                .totalChillies(3)
                .discount(3) // 10% of 30 = 3
                .build();

        when(orderMapper.toDto(savedOrder)).thenReturn(responseDto);

        // Create request DTO
        OrderRequestDto requestDto = OrderRequestDto.builder()
                .customerName("John Doe")
                .itemIds(List.of(1L, 2L))
                .build();

        // Call service method
        OrderResponseDto result = orderService.createOrder(requestDto);

        // Verify repository interaction
        verify(menuItemRepository, times(1)).findAllById(List.of(1L, 2L));
        verify(orderRepository, times(1)).save(any(Order.class));

        // Assert results
        assertEquals("John Doe", result.getCustomerName());
        assertEquals(27, result.getTotal());
        assertTrue(result.isHot());
        assertEquals(3, result.getTotalChillies());
        assertEquals(3, result.getDiscount());
    }
}
