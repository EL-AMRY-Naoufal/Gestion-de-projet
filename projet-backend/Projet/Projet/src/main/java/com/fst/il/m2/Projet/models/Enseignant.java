package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Enseignant {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* pour utiliser l'id d'user comme l'id d'enseignant 
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    */

    @ElementCollection
    @CollectionTable(name = "categorie_enseignant_map", joinColumns = @JoinColumn(name = "enseignant_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "heures")
    private Map<CategorieEnseignant, Integer> categorieEnseignant;

    private int maxHeuresService;

    private int heuresAssignees;

    // Relations
    @OneToMany(mappedBy = "enseignant")
    private List<Affectation> affectations;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public int getNbHeureCategorie(CategorieEnseignant categorie) {
        return this.categorieEnseignant.getOrDefault(categorie, 0);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<CategorieEnseignant, Integer> getCategorieEnseignant() {
        return categorieEnseignant;
    }

    public void setCategorieEnseignant(Map<CategorieEnseignant, Integer> categorieEnseignant) {
        this.categorieEnseignant = categorieEnseignant;
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
