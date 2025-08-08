package com.ensah.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private Integer capacite;

    @OneToMany(mappedBy = "salle")
    @JsonIgnore
    private List<Surveillance> surveillances;

    @ManyToMany(mappedBy = "salles")
    @JsonIgnore
    private List<Examen> examens;
}
