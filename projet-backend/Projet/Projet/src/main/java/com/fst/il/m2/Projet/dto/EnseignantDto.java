package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnseignantDto {
    private Long id;
    private CategorieEnseignant categorie;
    private int maxHeuresService;
    private int heuresAssignees;
}
