package com.example.k3bootsecurity.dto;

import com.example.k3bootsecurity.entity.UserEntity;
import com.example.k3bootsecurity.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    @Qualifier("userEntityAppServiceImpl")
    private AppService<UserEntity> service;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity toUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = service.getById(userDto.getId());
        UserEntity user = userMapper.toUserEntity(userDto);
        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setAge(user.getAge());
        userEntity.setEmail(user.getEmail());
        userEntity.setRoles(user.getRoles());
        return userEntity;
    }
}
