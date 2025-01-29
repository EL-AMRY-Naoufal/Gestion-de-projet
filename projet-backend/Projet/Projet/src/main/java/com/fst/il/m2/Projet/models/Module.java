package com.fst.il.m2.Projet.models;

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


    @ElementCollection
    private Map<TypeHeure, Integer> heuresParType;

    // Relations
    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToMany(mappedBy = "module")
    private List<Affectation> affectations;

    @OneToMany(mappedBy = "module")
    private  List<Groupe> groupes;

    public Module() {
    }

    public Module(Long id, String nom, int totalHeuresRequises, int groupes, Map<TypeHeure, Integer> heuresParType, Formation formation, List<Affectation> affectations) {
        this.id = id;
        this.nom = nom;
        this.totalHeuresRequises = totalHeuresRequises;
        this.heuresParType = heuresParType;
        this.formation = formation;
        this.affectations = affectations;
    }

    public Module(Long id, String nom, int totalHeuresRequises, Map<TypeHeure, Integer> heuresParType, Formation formation, List<Affectation> affectations, List<Groupe> groupes) {
        this.id = id;
        this.nom = nom;
        this.totalHeuresRequises = totalHeuresRequises;
        this.heuresParType = heuresParType;
        this.formation = formation;
        this.affectations = affectations;
        this.groupes = groupes;
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

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void setGroupes(List<Groupe> groupes) {
        this.groupes = groupes;
    }
}
