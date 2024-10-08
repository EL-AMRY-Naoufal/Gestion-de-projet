package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Formations")
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

    @OneToMany(mappedBy = "formation")
    private List<Module> modules;

}