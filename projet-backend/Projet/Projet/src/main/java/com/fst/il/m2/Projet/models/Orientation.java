package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="Orientation")
public class Orientation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;

    @OneToMany(mappedBy = "orientation", cascade = CascadeType.ALL)
    private List<Semestre> semestres;

    public Orientation() {
    }

    public Orientation(Long id, String nom, Niveau niveau, List<Semestre> semestres) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
        this.semestres = semestres;
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

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public List<Semestre> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<Semestre> semestres) {
        this.semestres = semestres;
    }
}
