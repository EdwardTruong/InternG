package com.example.cache_example_with_ehcache.appilication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.cache_example_with_ehcache.appilication.dto.UserDto;
import com.example.cache_example_with_ehcache.domain.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(UserEntity entity);
}
