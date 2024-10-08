package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Affectaions")
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heuresAssignees;

    private LocalDate dateAffectation;

    // Relations
    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}