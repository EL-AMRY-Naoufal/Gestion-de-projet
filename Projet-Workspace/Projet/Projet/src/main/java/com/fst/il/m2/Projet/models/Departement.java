package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Departements")
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;


    @OneToMany
    @JoinColumn(name = "Formation_ID")
    private List<Formation> formations;

    @ManyToOne
    @JoinColumn(name = "responsable_departement_id")
    private ResponsableDepartement responsableDepartement;
}
