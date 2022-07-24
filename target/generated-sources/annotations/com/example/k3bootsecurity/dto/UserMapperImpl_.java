package com.example.k3bootsecurity.dto;

import com.example.k3bootsecurity.entity.RoleEntity;
import com.example.k3bootsecurity.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-20T09:11:01+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class UserMapperImpl_ implements UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDto toUserDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( userEntity.getId() );
        userDto.setName( userEntity.getName() );
        userDto.setSurname( userEntity.getSurname() );
        userDto.setAge( userEntity.getAge() );
        userDto.setEmail( userEntity.getEmail() );
        userDto.setRoles( roleEntityListToRoleDtoList( userEntity.getRoles() ) );

        return userDto;
    }

    @Override
    public UserEntity toUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( userDto.getId() );
        userEntity.setName( userDto.getName() );
        userEntity.setSurname( userDto.getSurname() );
        userEntity.setAge( userDto.getAge() );
        userEntity.setEmail( userDto.getEmail() );
        userEntity.setRoles( roleDtoListToRoleEntityList( userDto.getRoles() ) );

        return userEntity;
    }

    protected List<RoleDto> roleEntityListToRoleDtoList(List<RoleEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleDto> list1 = new ArrayList<RoleDto>( list.size() );
        for ( RoleEntity roleEntity : list ) {
            list1.add( roleMapper.toRoleDto( roleEntity ) );
        }

        return list1;
    }

    protected List<RoleEntity> roleDtoListToRoleEntityList(List<RoleDto> list) {
        if ( list == null ) {
            return null;
        }

        List<RoleEntity> list1 = new ArrayList<RoleEntity>( list.size() );
        for ( RoleDto roleDto : list ) {
            list1.add( roleMapper.toRoleEntity( roleDto ) );
        }

        return list1;
    }
}
