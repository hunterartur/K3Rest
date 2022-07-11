package com.example.k3bootsecurity.controller;

import com.example.k3bootsecurity.entity.UserEntity;
import com.example.k3bootsecurity.service.AppService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@PreAuthorize("hasAnyRole('USER, ADMIN')")
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final AppService<UserEntity> entityAppService;

    public UserController(AppService<UserEntity> service) {
        this.entityAppService = service;
    }
}
