package com.ensah.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nom obligatoire")
    private String nom;

    @OneToMany(mappedBy = "departement")
    @JsonIgnore
    private List<Enseignant> enseignants;

    @OneToMany(mappedBy = "departement")
    @JsonIgnore
    private List<Filiere> filieres;

    public Departement() {
        this.enseignants = new ArrayList<Enseignant>();
        this.filieres = new ArrayList<Filiere>();
    }

    public void addEnseignant(Enseignant enseignant) {
        this.enseignants.add(enseignant);
    }

    public void removeEnseignant(Enseignant enseignant) {
        this.enseignants.remove(enseignant);
    }

    public void addFiliere(Filiere filiere) {
        this.filieres.add(filiere);
    }

    public void removeFiliere(Filiere filiere) {
        this.filieres.remove(filiere);
    }

    @Override
    public String toString() {
        return "Departement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
