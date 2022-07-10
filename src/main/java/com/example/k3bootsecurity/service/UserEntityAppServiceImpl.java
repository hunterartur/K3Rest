package com.example.k3bootsecurity.service;

import com.example.k3bootsecurity.entity.UserEntity;
import com.example.k3bootsecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserEntityAppServiceImpl implements AppService<UserEntity>, UserDetailsService {

    private final static String USER_NOT_FOUND_BY_NAME_MSG = "User with email %s not found";
    private final static String USER_NOT_FOUND_BY_ID_MSG = "User with id %d not found";

    private UserRepository repository;

    public UserEntityAppServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public UserEntity getByName(String name) {
        return repository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_NAME_MSG, name)));
    }

    @Override
    public UserEntity getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public void saveOrUpdate(UserEntity object) {
        repository.save(object);
    }

    @Override
    public void remove(UserEntity object) {
        repository.delete(object);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_NAME_MSG, username)));
    }
}
