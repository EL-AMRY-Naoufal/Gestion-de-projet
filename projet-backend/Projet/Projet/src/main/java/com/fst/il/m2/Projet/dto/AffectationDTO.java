package com.fst.il.m2.Projet.dto;

import java.time.LocalDate;

public class AffectationDTO {
    private Long id;
    private String description;
    private int heuresAssignees;
    private String nomModule;
    private LocalDate dateAffectation;

    public AffectationDTO(Long id, int heuresAssignees, LocalDate dateAffectation, String nomModule) {
        this.id = id;
        this.heuresAssignees = heuresAssignees;
        this.nomModule = nomModule;
        this.dateAffectation = dateAffectation;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(LocalDate dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public String getDescription() {
        return description;
    }


    public int getHeuresAssignees() {
        return heuresAssignees;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getNomModule() {
        return nomModule;
    }

    public void setNomModule(String nomModule) {
        this.nomModule = nomModule;
    }


}
