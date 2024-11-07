package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Enseignant  {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private CategorieEnseignant categorie;

    private int maxHeuresService;

    private int heuresAssignees;

    // Relations
    @OneToMany
    @JoinColumn(name="Affectation_ID")
    private List<Affectation> affectations;

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
