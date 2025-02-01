package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
@Entity
@Table(name="Semestre")
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau niveau;

    @OneToMany
    @JoinColumn(name = "module_id")
    private List<Module> modules;

    public Semestre() {
    }

    public Semestre(Long id, String nom, Niveau niveau, List<Module> modules) {
        this.id = id;
        this.nom = nom;
        this.niveau = niveau;
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

    public Niveau getNiveau() {return niveau;}

    public void setNiveau(Niveau niveau) {this.niveau = niveau;}

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
