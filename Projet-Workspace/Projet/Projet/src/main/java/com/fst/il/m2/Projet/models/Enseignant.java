package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Enseignant extends User {

    @Enumerated(EnumType.STRING)
    private CategorieEnseignant categorie;

    private int maxHeuresService;

    private int heuresAssignees;

    // Relations
    @OneToMany
    @JoinColumn(name="Affectation_ID")
    private List<Affectation> affectations;

    public Enseignant() {
    }

    public Enseignant(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }

    public Enseignant(String username, String password, String email, Role role, CategorieEnseignant categorie, int maxHeuresService, int heuresAssignees, List<Affectation> affectations) {
        super(username, password, email, role);
        this.categorie = categorie;
        this.maxHeuresService = maxHeuresService;
        this.heuresAssignees = heuresAssignees;
        this.affectations = affectations;
    }

    public CategorieEnseignant getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieEnseignant categorie) {
        this.categorie = categorie;
    }

    public int getMaxHeuresService() {
        return maxHeuresService;
    }

    public void setMaxHeuresService(int maxHeuresService) {
        this.maxHeuresService = maxHeuresService;
    }

    public int getHeuresAssignees() {
        return heuresAssignees;
    }

    public void setHeuresAssignees(int heuresAssignees) {
        this.heuresAssignees = heuresAssignees;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }
}
