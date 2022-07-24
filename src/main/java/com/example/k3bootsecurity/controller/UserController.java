package com.example.k3bootsecurity.controller;

import com.example.k3bootsecurity.dto.UserDto;
import com.example.k3bootsecurity.dto.UserMapper;
import com.example.k3bootsecurity.entity.UserEntity;
import com.example.k3bootsecurity.service.AppService;
import com.example.k3bootsecurity.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final AuthService authService;
    private final AppService<UserEntity> entityAppService;
    private final UserMapper userMapper;

    public UserController(AuthService authService, AppService<UserEntity> entityAppService, UserMapper userMapper) {
        this.authService = authService;
        this.entityAppService = entityAppService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/info")
    public ResponseEntity<UserDto> getAuthentication() {
        UserDto userDto = userMapper.toUserDto((UserEntity) authService.getAuthInfo().getPrincipal());
        log.info(userDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
