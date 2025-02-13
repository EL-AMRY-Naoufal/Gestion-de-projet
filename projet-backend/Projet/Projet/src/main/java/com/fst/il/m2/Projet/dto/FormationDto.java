package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class FormationDto {
    private Long id;

    private String nom;

    private int totalHeures;

    private Long responsableFormationId;

    private Long departementId;
}
