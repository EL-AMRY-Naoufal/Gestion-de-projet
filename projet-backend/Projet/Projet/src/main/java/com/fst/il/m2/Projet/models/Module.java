package com.fst.il.m2.Projet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fst.il.m2.Projet.enumurators.TypeHeure;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private int totalHeuresRequises;

    private int groupes;

    @ElementCollection
    private Map<TypeHeure, Integer> heuresParType;

    // Relations
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @JsonIgnore
    @OneToMany(mappedBy = "module")
    private List<Affectation> affectations;

    public Module() {
    }

    public Module(Long id, String nom, int totalHeuresRequises, int groupes, Map<TypeHeure, Integer> heuresParType, Formation formation, List<Affectation> affectations) {
        this.id = id;
        this.nom = nom;
        this.totalHeuresRequises = totalHeuresRequises;
        this.groupes = groupes;
        this.heuresParType = heuresParType;
        this.formation = formation;
        this.affectations = affectations;
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

    public int getTotalHeuresRequises() {
        return totalHeuresRequises;
    }

    public void setTotalHeuresRequises(int totalHeuresRequises) {
        this.totalHeuresRequises = totalHeuresRequises;
    }

    public int getGroupes() {
        return groupes;
    }

    public void setGroupes(int groupes) {
        this.groupes = groupes;
    }

    public Map<TypeHeure, Integer> getHeuresParType() {
        return heuresParType;
    }

    public void setHeuresParType(Map<TypeHeure, Integer> heuresParType) {
        this.heuresParType = heuresParType;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }
}
