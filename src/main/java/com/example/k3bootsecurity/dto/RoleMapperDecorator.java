package com.example.k3bootsecurity.dto;

import com.example.k3bootsecurity.entity.RoleEntity;
import com.example.k3bootsecurity.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class RoleMapperDecorator implements RoleMapper {

    @Autowired
    @Qualifier("roleAppServiceImpl")
    private AppService<RoleEntity> service;

    @Override
    public RoleEntity toRoleEntity(RoleDto roleDto) {
        RoleEntity roleEntity = service.getById(roleDto.getId());
        roleEntity.setName(roleDto.getName());
        return roleEntity;
    }
}
