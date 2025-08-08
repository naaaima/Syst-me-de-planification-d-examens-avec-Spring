package com.ensah;


import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Enseignant;
import com.ensah.core.entities.Personnel;
import com.ensah.core.entities.Role;
import com.ensah.core.services.IAdministrateurService;
import com.ensah.core.services.IEnseignantService;
import com.ensah.core.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ExamPlanAppApplication implements CommandLineRunner {

	private final IRoleService roleService;
	private final IAdministrateurService administrateurService;
	private final IEnseignantService enseignantService;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ExamPlanAppApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
/*
		List<Personnel> administrateurList = administrateurService.getAdministrateurs();
		System.out.println(administrateurList);
*//*
		if (!roleService.existsByName("Administrateur")) {
			roleService.addRole(
					Role.builder().name("Administrateur").build()
			);
		}
		if (!roleService.existsByName("Enseignant")) {
			roleService.addRole(
					Role.builder().name("Enseignant").build()
			);
		}

		// Créer un administrateur
		Administrateur admin = (Administrateur) Administrateur.builder()
				.nom("Admin_2")
				.prenom("Super_2")
				.email("admin_2@example.com")
				.phone("1234567333")
				.username("admin_2")
				.password(passwordEncoder.encode("password"))
				.build();
		administrateurService.addAdministrateur(admin);

		// Créer un enseignant
		Enseignant enseignant = (Enseignant) Enseignant.builder()
				.nom("Enseignant_2")
				.prenom("Prof_2")
				.email("enseignant_2@example.com")
				.phone("0987654222")
				.username("enseignant_2")
				.password(passwordEncoder.encode("password"))
				.build();
		enseignantService.addEnseignant(enseignant);

*/
	}
}
