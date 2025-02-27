package com.fst.il.m2.Projet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AffectationDTO {
    private Long id;
    private String description;
    private int heuresAssignees;
    private String nomModule;
    private LocalDate dateAffectation;
    private String commentaire;
    private String nomGroupe;


    public AffectationDTO(Long id, int heuresAssignees, LocalDate dateAffectation, String nomModule, String commentaire, String nomGroupe) {
        this.id = id;
        this.heuresAssignees = heuresAssignees;
        this.nomModule = nomModule;
        this.dateAffectation = dateAffectation;
        this.commentaire = commentaire;
        this.nomGroupe = nomGroupe;
    }
}
