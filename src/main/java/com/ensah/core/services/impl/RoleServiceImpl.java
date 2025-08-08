package com.ensah.core.services.impl;

import com.ensah.core.dao.RoleRepository;
import com.ensah.core.entities.Role;
import com.ensah.core.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;



    @Override
    public boolean existsByName(String name) {
        return roleRepository.findByName(name).isPresent();
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}
