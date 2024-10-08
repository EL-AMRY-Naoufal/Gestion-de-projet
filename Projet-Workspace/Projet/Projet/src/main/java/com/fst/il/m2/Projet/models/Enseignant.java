package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
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
}
