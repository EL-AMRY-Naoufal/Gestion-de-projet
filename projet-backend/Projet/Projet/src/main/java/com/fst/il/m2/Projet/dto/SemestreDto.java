package com.fst.il.m2.Projet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class SemestreDto {
    private Long id;

    private String nom;

    private Long niveauId;
}
