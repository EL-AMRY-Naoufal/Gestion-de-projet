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

    public Formation() {
    }

    public Formation(Long id, String nom, int totalHeures, ResponsableFormation responsableFormation, List<Module> modules) {
        this.id = id;
        this.nom = nom;
        this.totalHeures = totalHeures;
        this.responsableFormation = responsableFormation;
        this.modules = modules;
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

    public int getTotalHeures() {
        return totalHeures;
    }

    public void setTotalHeures(int totalHeures) {
        this.totalHeures = totalHeures;
    }

    public ResponsableFormation getResponsableFormation() {
        return responsableFormation;
    }

    public void setResponsableFormation(ResponsableFormation responsableFormation) {
        this.responsableFormation = responsableFormation;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}