package com.ensah.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Nom obligatoire")
    private String nom;

    @NotEmpty(message = "Prénom Obligatoire")
    private String prenom;

    @NotEmpty(message = "Email Obligatoire")
    @Email(message = "Email n'est pas valid")
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @NotEmpty(message = "Phone obligatoire")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "phone invalid")
    private String phone;

    @Column(unique = true)
    @NotEmpty(message = "Username obligatoire")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "Username obligatoire")
    @Size(min = 6, message = "Password doit être au moins 6 caractères")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public String toString() {
        return "Personnel{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
