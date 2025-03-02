package com.fst.il.m2.Projet.models;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Builder
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

    @OneToMany(mappedBy = "formation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Niveau> niveaux;

    public Formation() {
    }

    public Formation(Long id, String nom, int totalHeures, ResponsableFormation responsableFormation, List<Niveau> niveaux) {
        this.id = id;
        this.nom = nom;
        this.totalHeures = totalHeures;
        this.responsableFormation = responsableFormation;
        this.niveaux = niveaux;
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

    public List<Niveau> getNiveaux() {
        return niveaux;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }
}