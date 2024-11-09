package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Enseignant {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategorieEnseignant categorie;

    private int maxHeuresService;

    private int heuresAssignees;

    // Relations
    @OneToMany
    @JoinColumn(name="Affectation_ID")
    private List<Affectation> affectations;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Enseignant() {
    }

    public Enseignant(Long id, CategorieEnseignant categorie, int maxHeuresService, int heuresAssignees, List<Affectation> affectations, User user) {
        this.id = id;
        this.categorie = categorie;
        this.maxHeuresService = maxHeuresService;
        this.heuresAssignees = heuresAssignees;
        this.affectations = affectations;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return user.getId();
    }
}
