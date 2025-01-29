package com.fst.il.m2.Projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.Length;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "Affectations")
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heuresAssignees;

    private LocalDate dateAffectation;

    @Column
    private String commentaire = "";

    // Relations
    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    public Affectation() {
    }

    public Affectation(Long id, int heuresAssignees, LocalDate dateAffectation, Enseignant enseignant, Module module) {
        this.id = id;
        this.heuresAssignees = heuresAssignees;
        this.dateAffectation = dateAffectation;
        this.enseignant = enseignant;
        this.module = module;
    }
}