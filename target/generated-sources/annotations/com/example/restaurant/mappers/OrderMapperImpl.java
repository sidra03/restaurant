package com.example.restaurant.mappers;

import com.example.restaurant.dto.OrderResponseDto;
import com.example.restaurant.entity.Order;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-30T22:32:35+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponseDto toDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponseDto.OrderResponseDtoBuilder orderResponseDto = OrderResponseDto.builder();

        orderResponseDto.customerName( order.getCustomerName() );
        orderResponseDto.id( order.getId() );
        orderResponseDto.total( order.getTotal() );
        orderResponseDto.status( order.getStatus() );
        orderResponseDto.hotOrder( order.isHotOrder() );
        orderResponseDto.chillies( order.getChillies() );

        return orderResponseDto.build();
    }
}
