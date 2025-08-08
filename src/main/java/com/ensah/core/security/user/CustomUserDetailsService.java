package com.ensah.core.security.user;

import com.ensah.core.dao.PersonnelRepository;
import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Personnel;
import com.ensah.core.services.IPersonnelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final IPersonnelService personnelService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Personnel personnel = personnelService.getPersonnelByUsername(username);
        //return new CustomUserDetails(personnel.getUsername(), personnel.getPassword(), personnel.getRole());


        log.info("Username : {} ", username);
        Personnel personnel = personnelService.getPersonnelByUsername(username);
        log.info("Personnel : {}", personnel);

        String password = personnel.getPassword();
        log.info("Password : {} ", password);
        String role = "ROLE_"+personnel.getRole().getName();
        log.info("Role : {} ", role);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));
        log.info("Roles : {} ", roles);

        return new CustomUserDetails(username, password, roles);
    }

    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personnel personnel = personnelRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new CustomUserDetails(personnel);
    }*/
}
