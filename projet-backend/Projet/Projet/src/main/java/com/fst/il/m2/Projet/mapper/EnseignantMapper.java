package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.models.Enseignant;

public class EnseignantMapper {
    public static EnseignantDto enseignantToEnseignantDto(Enseignant enseignant) {
        CategorieEnseignant categorie = enseignant.getCategorieEnseignant().keySet().stream().findFirst().orElse(CategorieEnseignant.PROFESSEUR);
        return EnseignantDto.builder()
                .id(enseignant.getId())
                .categorieEnseignant(categorie)
                .nbHeureCategorie(enseignant.getNbHeureCategorie(categorie))
                .maxHeuresService(enseignant.getMaxHeuresService())
                .heuresAssignees(enseignant.getHeuresAssignees())
                .user(enseignant.getUser())
                .build();
    }
}
