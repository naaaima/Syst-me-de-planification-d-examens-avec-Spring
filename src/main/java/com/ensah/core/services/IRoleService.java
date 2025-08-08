package com.ensah.core.services;

import com.ensah.core.entities.Role;

public interface IRoleService {
    public boolean existsByName(String name);

    public Role addRole(Role administrateur);
}
