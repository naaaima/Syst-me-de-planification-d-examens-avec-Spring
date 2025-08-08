package com.ensah.core.services.impl;

import com.ensah.core.dao.AdministrateurRepository;
import com.ensah.core.dao.PersonnelRepository;
import com.ensah.core.dao.RoleRepository;
import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Personnel;
import com.ensah.core.entities.Role;
import com.ensah.core.services.IAdministrateurService;
import com.ensah.core.services.IPersonnelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonnelServiceImpl implements IPersonnelService {

    private final PersonnelRepository personnelRepository;


    @Override
    public Personnel getPersonnelByUsername(String username) {
        return personnelRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Personnel not found"));
    }

}
