package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "Formations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private int totalHeures;

    // Relations
    @ManyToOne
    @JoinColumn(name = "responsable_formation_id")
    private ResponsableFormation responsableFormation;

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Niveau> niveaux;

    @ManyToOne
    private Departement departement;
}