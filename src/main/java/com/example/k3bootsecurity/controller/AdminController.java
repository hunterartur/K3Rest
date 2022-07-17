package com.example.k3bootsecurity.controller;

import com.example.k3bootsecurity.dto.RoleDto;
import com.example.k3bootsecurity.dto.RoleMapper;
import com.example.k3bootsecurity.dto.UserMapper;
import com.example.k3bootsecurity.dto.UserDto;
import com.example.k3bootsecurity.entity.RoleEntity;
import com.example.k3bootsecurity.entity.UserEntity;
import com.example.k3bootsecurity.service.AppService;
import com.example.k3bootsecurity.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {

    private final AuthService authService;
    private final AppService<UserEntity> userAppService;
    private final AppService<RoleEntity> roleAppService;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final HttpHeaders headers;

    {
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        headers.add("Content-Language", "ru-RU");
    }

    public AdminController(AppService<UserEntity> service, AppService<RoleEntity> roleAppService,
                           UserMapper userMapper, RoleMapper roleMapper, AuthService authService) {
        this.userAppService = service;
        this.roleAppService = roleAppService;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.authService = authService;
    }

    @GetMapping(path = "/users/")
    public ResponseEntity<List<UserDto>> index() {
        List<UserDto> userDtos = userAppService.getAll().stream().map(userMapper::toUserDto).collect(Collectors.toList());
        log.info("Get all users!");
        return !userDtos.isEmpty() ? new ResponseEntity<>(userDtos, headers, HttpStatus.OK)
                : new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userMapper.toUserDto(userAppService.getById(id));
        log.info(String.format("Get user by id = %d", userDto.getId()));
        return (userDto != null) ? new ResponseEntity<>(userDto, headers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/users/")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserEntity userEntity) {
        try {
            if (!userAppService.exists(userEntity.getEmail())) {
                UserDto userDto = userMapper.toUserDto(userAppService.saveOrUpdate(userEntity));
                log.info(String.format("Save user id = %d", userDto.getId()));
                return new ResponseEntity<>(userDto, headers, HttpStatus.CREATED);
            } else {
                log.warn(String.format("A user with such an %s exists", userEntity.getEmail()));
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            log.error("Failed to save user");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/users/")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        try {
            UserEntity userEntity = userAppService.saveOrUpdate(userMapper.toUserEntity(userDto));
            UserDto userUpdatedDto = userMapper.toUserDto(userEntity);
            log.info(String.format("Updating user id = %d", userUpdatedDto.getId()));
            return new ResponseEntity<>(userUpdatedDto, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Error updating user id = %d", userDto.getId()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        try {
            userAppService.remove(id);
            log.info(String.format("Deleting a user id = %d", id));
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("Error when deleting user id = %d", id));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/roles/")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleEntity> roleEntities = roleAppService.getAll();
        List<RoleDto> roleDtos = roleEntities.stream().map(roleMapper::toRoleDto).collect(Collectors.toList());
        return !roleDtos.isEmpty() ? new ResponseEntity<>(roleDtos, headers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/info")
    public ResponseEntity<UserDto> getAuthentication() {
        UserDto userDto = userMapper.toUserDto((UserEntity) authService.getAuthInfo().getPrincipal());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
