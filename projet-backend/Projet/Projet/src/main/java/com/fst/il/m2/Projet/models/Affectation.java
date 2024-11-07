package com.fst.il.m2.Projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Affectations")
public class Affectation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int heuresAssignees;

    private LocalDate dateAffectation;

    // Relations
    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    @JsonIgnore  
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHeuresAssignees() {
        return heuresAssignees;
    }

    public void setHeuresAssignees(int heuresAssignees) {
        this.heuresAssignees = heuresAssignees;
    }

    public LocalDate getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(LocalDate dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}