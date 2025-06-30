package com.example.restaurant.mappers;

import com.example.restaurant.dto.MenuItemDto;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.enums.Category;
import com.example.restaurant.enums.SpiceLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    @Mapping(source = "spiceLevel", target = "chillies", qualifiedByName = "spiceLevelToChillies")
    @Mapping(source = "category", target = "category", qualifiedByName = "categoryToString")
    MenuItemDto toDto(MenuItem entity);

    @Mapping(source = "chillies", target = "spiceLevel", qualifiedByName = "chilliesToSpiceLevel")
    @Mapping(source = "category", target = "category", qualifiedByName = "stringToCategory")
    MenuItem toEntity(MenuItemDto dto);

    List<MenuItemDto> toDto(List<MenuItem> entities);

    @Named("spiceLevelToChillies")
    default int spiceLevelToChillies(SpiceLevel spiceLevel) {
        return spiceLevel != null ? spiceLevel.getChillies() : 0;
    }

    @Named("chilliesToSpiceLevel")
    default SpiceLevel chilliesToSpiceLevel(int chillies) {
        return SpiceLevel.fromChillies(chillies);
    }

    @Named("categoryToString")
    default String categoryToString(Category category) {
        return category != null ? category.name() : null;
    }

    @Named("stringToCategory")
    default Category stringToCategory(String categoryStr) {
        return categoryStr != null ? Category.valueOf(categoryStr) : null;
    }
}
