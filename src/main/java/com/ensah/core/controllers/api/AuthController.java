package com.ensah.core.controllers.api;

import com.ensah.core.dto.LoginRequestDto;
import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Personnel;
import com.ensah.core.responses.JwtResponse;
import com.ensah.core.security.jwt.JwtUtils;
import com.ensah.core.security.user.CustomUserDetails;
import com.ensah.core.services.IAdministrateurService;
import com.ensah.core.services.IEnseignantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vi/auth")
public class AuthController {
/*
    private final IAdministrateurService administrateurService;
    private final IEnseignantService enseignantService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @PostMapping("/register-admin")
    public ResponseEntity<Personnel> registerAdministrateur(@RequestBody Administrateur administrateur){
        System.out.println(administrateur);
        return ResponseEntity.ok(administrateurService.registerAdministrateur(administrateur));
    }

    @PostMapping("/register-enseignant")
    public ResponseEntity<Personnel> registerEnseignant(@RequestBody Enseignant enseignant){
        System.out.println(enseignant);
        return ResponseEntity.ok(enseignantService.registerEnseignant(enseignant));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest){
        System.out.println(loginRequest);
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return ResponseEntity.ok(new JwtResponse(
                userDetails.getUsername(),
                jwt,
                roles
        ));
    }


 */
}
