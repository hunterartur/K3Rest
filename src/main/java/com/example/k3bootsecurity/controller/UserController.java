package com.example.k3bootsecurity.controller;

import com.example.k3bootsecurity.entity.UserEntity;
import com.example.k3bootsecurity.service.AppService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@PreAuthorize("hasAnyRole('USER, ADMIN')")
@Controller
@RequestMapping(path = "/user")
public class UserController {

    private AppService<UserEntity> entityAppService;

    public UserController(AppService<UserEntity> service) {
        this.entityAppService = service;
    }

    @GetMapping(path = "")
    public String userPage(ModelMap model, Principal principal) {
        UserEntity userEntity = entityAppService.getByName(principal.getName());
        model.addAttribute("principal", userEntity);
        return "index";
    }
}
