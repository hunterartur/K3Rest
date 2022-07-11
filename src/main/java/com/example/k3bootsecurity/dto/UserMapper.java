package com.example.k3bootsecurity.dto;

import com.example.k3bootsecurity.entity.UserEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    UserDto toUserDto(UserEntity userEntity);

    UserEntity toUserEntity(UserDto userDto);
}
