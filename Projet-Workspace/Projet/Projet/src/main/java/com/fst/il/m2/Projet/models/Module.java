package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.TypeHeure;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private int totalHeuresRequises;

    private int groupes;

    @ElementCollection
    private Map<TypeHeure, Integer> heuresParType;

    // Relations
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToMany(mappedBy = "module")
    private List<Affectation> affectations;
}
