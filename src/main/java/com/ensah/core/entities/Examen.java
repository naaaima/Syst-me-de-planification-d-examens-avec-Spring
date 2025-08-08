package com.ensah.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Date heureDebut;

    private Integer dureePrevue;
    private Integer dureeReelle;
    private String anneeUniversitaire;
    private String epreuve;
    private String pv;
    private String rapportTextuel;

    @ManyToOne
    private TypeExamen typeExamen;

    @ManyToOne
    private Semestre semestre;

    @ManyToOne
    private Session session;

    @ManyToOne
    private Enseignant coordonnateur;

    @ManyToMany
    @JsonIgnore
    private List<Salle> salles;

    @OneToMany(mappedBy = "examen")
    @JsonIgnore
    private List<Surveillance> surveillances;
}
