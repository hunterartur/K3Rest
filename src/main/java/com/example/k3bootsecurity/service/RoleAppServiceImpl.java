package com.example.k3bootsecurity.service;

import com.example.k3bootsecurity.entity.Role;
import com.example.k3bootsecurity.exception.RoleNotFoundException;
import com.example.k3bootsecurity.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAppServiceImpl implements AppService<Role> {

    private final static String ROLE_NOT_FOUND_BY_NAME_MSG = "Role with name %s not found";
    private final static String ROLE_NOT_FOUND_BY_ID_MSG = "Role with name %d not found";

    private RoleRepository repository;

    public RoleAppServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND_BY_NAME_MSG, name)));
    }

    @Override
    public Role getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND_BY_ID_MSG, id)));
    }

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Override
    public void saveOrUpdate(Role object) {
        repository.save(object);
    }

    @Override
    public void remove(Role object) {
        repository.delete(object);
    }
}
