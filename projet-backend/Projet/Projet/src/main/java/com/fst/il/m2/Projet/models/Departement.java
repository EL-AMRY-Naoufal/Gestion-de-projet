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

    public Departement() {
    }

    public Departement(Long id, String nom, List<Formation> formations, ResponsableDepartement responsableDepartement) {
        this.id = id;
        this.nom = nom;
        this.formations = formations;
        this.responsableDepartement = responsableDepartement;
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
}
