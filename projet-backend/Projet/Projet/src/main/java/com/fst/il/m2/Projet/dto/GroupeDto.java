package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.enumurators.TypeHeure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupeDto {
    private Long id;
    private String nom;
    private TypeHeure type;
    private int heuresAffectees;
    private int totalHeuresDuGroupe;
    private Long moduleId;
}
