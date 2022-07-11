package com.example.k3bootsecurity.service;

import com.example.k3bootsecurity.entity.RoleEntity;
import com.example.k3bootsecurity.exception.RoleNotFoundException;
import com.example.k3bootsecurity.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleAppServiceImpl implements AppService<RoleEntity> {

    private final static String ROLE_NOT_FOUND_BY_NAME_MSG = "Role with name %s not found";
    private final static String ROLE_NOT_FOUND_BY_ID_MSG = "Role with name %d not found";

    private RoleRepository repository;

    public RoleAppServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public RoleEntity getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND_BY_NAME_MSG, name)));
    }

    @Transactional
    @Override
    public RoleEntity getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Transactional
    @Override
    public List<RoleEntity> getAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public RoleEntity saveOrUpdate(RoleEntity object) {
        return repository.save(object);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean exists(String name) {
        return repository.existsByName(name);
    }
}
