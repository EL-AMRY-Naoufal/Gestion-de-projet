package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.models.User;
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
    private String name;
    private String firstname;
    private boolean hasAccount;
    private CategorieEnseignant categorieEnseignant;
    private int nbHeureCategorie;
    private int maxHeuresService;
    private int heuresAssignees;
    private User user;
}
