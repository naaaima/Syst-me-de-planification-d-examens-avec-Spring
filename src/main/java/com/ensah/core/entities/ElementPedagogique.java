package com.ensah.core.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElementPedagogique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Titre obligatoire")
    private String titre;

    @ManyToOne
    @NotNull(message = "Coordonateur obligatoire")
    private Enseignant coordonateur;

    @ManyToOne
    @NotNull(message = "Filière obligatoire")
    private Filiere filiere;

    @ManyToOne
    @NotNull(message = "Type élement pedagogique obligatoire")
    private TypeElementPedagogique typeElement;

    @ManyToOne
    @NotNull(message = "Niveau obligatoire")
    private Niveau niveau;

    @Override
    public String toString() {
        return "ElementPedagogique{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                '}';
    }
}
