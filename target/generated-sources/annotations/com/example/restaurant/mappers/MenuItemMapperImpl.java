package com.example.restaurant.mappers;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.entity.MenuItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-30T22:32:35+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class MenuItemMapperImpl implements MenuItemMapper {

    @Override
    public MenuItemDto toDto(MenuItem entity) {
        if ( entity == null ) {
            return null;
        }

        MenuItemDto.MenuItemDtoBuilder menuItemDto = MenuItemDto.builder();

        menuItemDto.chillies( spiceLevelToChillies( entity.getSpiceLevel() ) );
        menuItemDto.category( categoryToString( entity.getCategory() ) );
        menuItemDto.id( entity.getId() );
        menuItemDto.name( entity.getName() );
        menuItemDto.description( entity.getDescription() );
        menuItemDto.price( entity.getPrice() );
        menuItemDto.available( entity.isAvailable() );

        return menuItemDto.build();
    }

    @Override
    public MenuItem toEntity(MenuItemDto dto) {
        if ( dto == null ) {
            return null;
        }

        MenuItem.MenuItemBuilder menuItem = MenuItem.builder();

        menuItem.spiceLevel( chilliesToSpiceLevel( dto.getChillies() ) );
        menuItem.category( stringToCategory( dto.getCategory() ) );
        menuItem.id( dto.getId() );
        menuItem.name( dto.getName() );
        menuItem.description( dto.getDescription() );
        menuItem.price( dto.getPrice() );
        menuItem.available( dto.isAvailable() );

        return menuItem.build();
    }

    @Override
    public List<MenuItemDto> toDto(List<MenuItem> entities) {
        if ( entities == null ) {
            return null;
        }

        List<MenuItemDto> list = new ArrayList<MenuItemDto>( entities.size() );
        for ( MenuItem menuItem : entities ) {
            list.add( toDto( menuItem ) );
        }

        return list;
    }
}
