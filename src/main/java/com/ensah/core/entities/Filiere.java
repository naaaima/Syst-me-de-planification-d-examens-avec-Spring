package com.ensah.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nom obligatoire")
    private String nom;

    @ManyToOne
    @NotNull
    private Departement departement;

    @OneToMany(mappedBy = "filiere")
    @JsonIgnore
    private List<Enseignant> enseignants;

    @OneToMany(mappedBy = "filiere")
    @JsonIgnore
    private List<ElementPedagogique> elementsPedagogiques;

    public Filiere() {
        this.elementsPedagogiques = new ArrayList<ElementPedagogique>();
        this.enseignants = new ArrayList<Enseignant>();
    }

    public void addEnseignant(Enseignant enseignant) {
        this.enseignants.add(enseignant);
    }

    public void removeEnseignant(Enseignant enseignant) {
        this.enseignants.remove(enseignant);
    }

    public void addElementPedagogique(ElementPedagogique elementPedagogique) {
        this.elementsPedagogiques.add(elementPedagogique);
    }

    public void removeElementPedagogique(ElementPedagogique elementPedagogique) {
        this.elementsPedagogiques.remove(elementPedagogique);
    }

    @Override
    public String toString() {
        return "Filiere{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
