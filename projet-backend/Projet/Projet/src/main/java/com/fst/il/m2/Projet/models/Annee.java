package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;

@Entity
@Table(name="Annee")
public class Annee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int debut;
    private int fin;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Annee() {
    }

    public Annee(Long id, int debut, int fin, Departement departement) {
        this.id = id;
        this.debut = debut;
        this.fin = fin;
        this.departement = departement;
    }

    public int getDebut() {
        return debut;
    }

    public void setDebut(int debut) {
        this.debut = debut;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
