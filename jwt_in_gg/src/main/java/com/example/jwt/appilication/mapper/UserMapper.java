package com.example.jwt.appilication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.jwt.appilication.dto.UserDto;
import com.example.jwt.domain.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(UserEntity entity);
}
