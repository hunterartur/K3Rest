package com.example.k3bootsecurity.dto;

import com.example.k3bootsecurity.entity.RoleEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
@DecoratedWith(RoleMapperDecorator.class)
public interface RoleMapper {

    RoleDto toRoleDto(RoleEntity roleEntity);

    RoleEntity toRoleEntity(RoleDto roleDto);
}
