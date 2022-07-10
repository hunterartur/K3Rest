package com.example.k3bootsecurity.controller;

import com.example.k3bootsecurity.entity.Role;
import com.example.k3bootsecurity.entity.UserEntity;
import com.example.k3bootsecurity.dto.UserDto;
import com.example.k3bootsecurity.service.AppService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasAnyRole('ADMIN')")
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final AppService<UserEntity> entityAppService;
    private final AppService<Role> roleAppService;

    public AdminController(AppService<UserEntity> service, AppService<Role> roleAppService, PasswordEncoder passwordEncoder) {
        this.entityAppService = service;
        this.roleAppService = roleAppService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(path = "")
    public String allUsers(ModelMap model, Principal principal) {
        List<UserEntity> entities = entityAppService.getAll();
        List<UserDto> users = entities.stream().map(UserEntity::toUser).collect(Collectors.toList());
        List<Role> roleList = roleAppService.getAll();
        UserEntity userEntity = entityAppService.getByName(principal.getName());
        model.addAttribute("users", users);
        model.addAttribute("newUser", new UserEntity());
        model.addAttribute("roleList", roleList);
        model.addAttribute("principal", userEntity);
        return "index";
    }

    @PutMapping(path = "/update")
    public String update(@Valid @ModelAttribute("user") UserDto user, @RequestParam("roles") List<Role> roles) {
            System.out.println(user);
            UserEntity userEntity = entityAppService.getById(user.getId());
            userEntity.fromUser(user);
            if (!roles.isEmpty()) {
                userEntity.setRoles(roles);
            }
            entityAppService.saveOrUpdate(userEntity);
            return "redirect:/admin";
    }

    @DeleteMapping(path = "/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        UserEntity entity = entityAppService.getById(id);
        entityAppService.remove(entity);
        return "redirect:/admin";
    }

    @PostMapping(path = "/create")
    public String create(@Valid @ModelAttribute("user") UserEntity userEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/createUser";
        } else {
            //сделать проверку на null boolean полей
            userEntity.setEnabled(userEntity.getEnabled() != null);
            userEntity.setExpired(userEntity.getExpired() != null);
            userEntity.setLocked(userEntity.getLocked() != null);
            String password = passwordEncoder.encode(userEntity.getPassword());
            userEntity.setPassword(password);
            entityAppService.saveOrUpdate(userEntity);
            return "redirect:/admin";
        }
    }

}
