package com.ensah.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Enseignant extends Personnel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Departement departement;

    @ManyToOne
    private Filiere filiere;

    @ManyToMany
    @JsonIgnore
    private List<Groupe> groupes;

    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    private List<Surveillance> surveillances;

    @OneToMany(mappedBy = "coordonateur")
    @JsonIgnore
    private List<ElementPedagogique> elementsCoordonnes;
}
