package com.ensah.core.services.impl;

import com.ensah.core.dao.AdministrateurRepository;
import com.ensah.core.dao.PersonnelRepository;
import com.ensah.core.dao.RoleRepository;
import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Personnel;
import com.ensah.core.entities.Role;
import com.ensah.core.services.IAdministrateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdministrateurServiceImpl implements IAdministrateurService {

    private final RoleRepository roleRepository;
    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Administrateur registerAdministrateur(Administrateur administrateur) throws RuntimeException {

        if(administrateurRepository.existsByUsername(administrateur.getPhone())){
            throw new RuntimeException("Phone : "+ administrateur.getPhone()+ " already exists");
        }

        if(administrateurRepository.existsByUsername(administrateur.getEmail())){
            throw new RuntimeException("Email : "+administrateur.getEmail()+ " already exists");
        }

        if(administrateurRepository.existsByEmail(administrateur.getUsername())){
            throw new RuntimeException("Username : "+administrateur.getUsername()+ " already exists");
        }

        administrateur.setPassword(passwordEncoder.encode(administrateur.getPassword()));
        Role adminRole = roleRepository.findByName("Administrateur")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        administrateur.setRole(adminRole);
        return administrateurRepository.save(administrateur);
    }

    @Override
    @Transactional
    public List<Administrateur> getAdministrateurs() {
        return administrateurRepository.findAll();
    }

    @Override
    public Administrateur getAdministrateurByUsername(String username) {
        return administrateurRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    public Administrateur getAdministrateurById(Long id) {
        return administrateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    @Override
    @Transactional
    public Administrateur modifierAdministrateur(Long id, Administrateur administrateur) {
        Administrateur foundedAdmin = administrateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        foundedAdmin.setNom(administrateur.getNom());
        foundedAdmin.setPrenom(administrateur.getPrenom());
        foundedAdmin.setEmail(administrateur.getEmail());
        foundedAdmin.setUsername(administrateur.getUsername());
        foundedAdmin.setPhone(administrateur.getPhone());
        foundedAdmin.setPassword(passwordEncoder.encode(administrateur.getPassword()));
        return administrateurRepository.save(foundedAdmin);
    }

    @Transactional
    @Override
    public Administrateur deleteAdministrateurById(Long id) {
        Administrateur theAdmin = administrateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Administrateur not found!"));
        if(theAdmin != null) administrateurRepository.deleteById(id);
        return theAdmin;
    }

}
