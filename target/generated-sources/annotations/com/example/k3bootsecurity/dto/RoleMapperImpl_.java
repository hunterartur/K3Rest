package com.example.k3bootsecurity.dto;

import com.example.k3bootsecurity.entity.RoleEntity;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-11T21:31:19+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class RoleMapperImpl_ implements RoleMapper {

    @Override
    public RoleDto toRoleDto(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( roleEntity.getId() );
        roleDto.setName( roleEntity.getName() );

        return roleDto;
    }

    @Override
    public RoleEntity toRoleEntity(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( roleDto.getId() );
        roleEntity.setName( roleDto.getName() );

        return roleEntity;
    }
}
