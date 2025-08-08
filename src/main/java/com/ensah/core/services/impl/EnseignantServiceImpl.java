package com.ensah.core.services.impl;

import com.ensah.core.dao.EnseignantRepository;
import com.ensah.core.dao.EnseignantRepository;
import com.ensah.core.dao.PersonnelRepository;
import com.ensah.core.dao.RoleRepository;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Role;
import com.ensah.core.services.IEnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnseignantServiceImpl implements IEnseignantService {

    private final RoleRepository roleRepository;
    private final EnseignantRepository enseignantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Enseignant addEnseignant(Enseignant enseignant) {

        if(enseignantRepository.existsByUsername(enseignant.getEmail())){
            throw new RuntimeException(enseignant.getEmail()+ " already exists");
        }

        if(enseignantRepository.existsByEmail(enseignant.getEmail())){
            throw new RuntimeException(enseignant.getEmail()+ " already exists");
        }

        Role adminRole = roleRepository.findByName("Enseignant")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        enseignant.setRole(adminRole);
        return enseignantRepository.save(enseignant);
    }

    @Override
    @Transactional
    public Enseignant registerEnseignant(Enseignant enseignant) {

        if(enseignantRepository.existsByUsername(enseignant.getEmail())){
            throw new RuntimeException(enseignant.getEmail()+ " already exists");
        }

        if(enseignantRepository.existsByEmail(enseignant.getEmail())){
            throw new RuntimeException(enseignant.getEmail()+ " already exists");
        }

        enseignant.setPassword(passwordEncoder.encode(enseignant.getPassword()));
        Role adminRole = roleRepository.findByName("Enseignant")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        enseignant.setRole(adminRole);
        return enseignantRepository.save(enseignant);
    }
    
    @Override
    public List<Enseignant> getEnseignants() {
        return enseignantRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteEnseignant(String email) {
        Enseignant theAdmin = getEnseignant(email);
        if(theAdmin != null) enseignantRepository.deleteByEmail(email);
    }

    @Override
    public Enseignant getEnseignantByUsername(String username) {
        return enseignantRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Enseignant not found"));
    }

    @Override
    public Enseignant getEnseignant(String email) {
        return (Enseignant) enseignantRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }




    @Override
    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    @Transactional
    public Enseignant modifierEnseignant(Long id, Enseignant enseignant) {
        Enseignant foundedAdmin = enseignantRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        foundedAdmin.setNom(enseignant.getNom());
        foundedAdmin.setPrenom(enseignant.getPrenom());
        foundedAdmin.setEmail(enseignant.getEmail());
        foundedAdmin.setUsername(enseignant.getUsername());
        foundedAdmin.setPhone(enseignant.getPhone());
        foundedAdmin.setPassword(passwordEncoder.encode(enseignant.getPassword()));
        return enseignantRepository.save(foundedAdmin);
    }

    @Transactional
    @Override
    public Enseignant deleteEnseignantById(Long id) {
        Enseignant theAdmin = enseignantRepository.findById(id).orElseThrow(() -> new RuntimeException("Enseignant not found!"));
        if(theAdmin != null) enseignantRepository.deleteById(id);
        return theAdmin;
    }

    @Override
    public Enseignant findById(Long id) {
        return enseignantRepository.findById(id).orElseThrow(()-> new RuntimeException("Enseignant not found!"));
    }
}
