package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
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

    @ManyToOne
    @JoinColumn(name = "annee_id")
    private Annee annee;

    public Departement() {
    }

    public Departement(Long id, String nom, List<Formation> formations, ResponsableDepartement responsableDepartement, Annee annee) {
        this.id = id;
        this.nom = nom;
        this.formations = formations;
        this.responsableDepartement = responsableDepartement;
        this.annee = annee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }

    public ResponsableDepartement getResponsableDepartement() {
        return responsableDepartement;
    }

    public void setResponsableDepartement(ResponsableDepartement responsableDepartement) {
        this.responsableDepartement = responsableDepartement;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }
}
