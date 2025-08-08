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
public class TypeElementPedagogique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "typeElement")
    @JsonIgnore
    private List<ElementPedagogique> elementsPedagogiques;

    @Override
    public String toString() {
        return "TypeElementPedagogique{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
