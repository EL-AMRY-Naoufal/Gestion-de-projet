package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
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
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;

    public Affectation() {
    }

    public Affectation(Long id, int heuresAssignees, LocalDate dateAffectation, Enseignant enseignant, Groupe groupe) {
        this.id = id;
        this.heuresAssignees = heuresAssignees;
        this.dateAffectation = dateAffectation;
        this.enseignant = enseignant;
        this.groupe = groupe;
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

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
}