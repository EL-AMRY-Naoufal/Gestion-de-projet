package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.enumurators.TypeHeure;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Formation;

import java.util.List;
import java.util.Map;

public class ModuleDto {
    private Long id;

    private String nom;

    private int totalHeuresRequises;

    private int groupes;

    private Map<TypeHeure, Integer> heuresParType;

    private Formation formation;

    private List<Affectation> affectations;

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getTotalHeuresRequises() {
        return totalHeuresRequises;
    }

    public int getGroupes() {
        return groupes;
    }

    public Map<TypeHeure, Integer> getHeuresParType() {
        return heuresParType;
    }

    public Formation getFormation() {
        return formation;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }
}
