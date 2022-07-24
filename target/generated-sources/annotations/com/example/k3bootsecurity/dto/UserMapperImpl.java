package com.example.k3bootsecurity.dto;

import com.example.k3bootsecurity.entity.UserEntity;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-20T09:11:01+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 1.8.0_202 (Oracle Corporation)"
)
@Component
@Primary
public class UserMapperImpl extends UserMapperDecorator {

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public UserDto toUserDto(UserEntity userEntity)  {
        return delegate.toUserDto( userEntity );
    }
}
