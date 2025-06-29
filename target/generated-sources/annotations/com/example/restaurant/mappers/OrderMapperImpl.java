package com.example.restaurant.mappers;

import com.example.restaurant.dto.OrderResponseDto;
import com.example.restaurant.entity.Order;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-29T15:43:32+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.15 (Homebrew)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Override
    public OrderResponseDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponseDto.OrderResponseDtoBuilder orderResponseDto = OrderResponseDto.builder();

        orderResponseDto.customerName( order.getCustomerName() );
        orderResponseDto.id( order.getId() );
        orderResponseDto.items( menuItemMapper.toDto( order.getItems() ) );
        orderResponseDto.total( order.getTotal() );
        orderResponseDto.status( order.getStatus() );

        return orderResponseDto.build();
    }
}
