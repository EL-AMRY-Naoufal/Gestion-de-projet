package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.Groupe;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class AffectationDto {
    private Long id;

    private int heuresAssignees;

    private Long enseignantId;

    private Long groupeId;

    private String commentaire;

    private String dateAffectation;
}
