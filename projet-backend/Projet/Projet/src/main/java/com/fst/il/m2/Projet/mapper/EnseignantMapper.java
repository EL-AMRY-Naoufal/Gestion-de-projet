package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.models.Enseignant;

import java.util.Map;
import java.util.stream.Collectors;

public class EnseignantMapper {
    public static EnseignantDto enseignantToEnseignantDto(Enseignant enseignant) {
        CategorieEnseignant categorie = enseignant.getCategorieEnseignant().keySet().stream().findFirst().orElse(CategorieEnseignant.ENSEIGNANT_CHERCHEUR);
        Map<Long, Double> heuresAssigneesParAnnee = enseignant.getHeuresParAnnee()
                .stream()
                .collect(Collectors.toMap(
                        h -> h.getAnnee().getId(), // Clé : année (ex. 2023)
                        h -> h.getHeures(),           // Valeur : heures assignées
                        (existing, replacement) -> existing // En cas de doublon, on garde la première valeur
                ));
        return EnseignantDto.builder()
                .id(enseignant.getId())
                .name(enseignant.getName())
                .firstname(enseignant.getFirstname())
                .hasAccount(enseignant.isHasAccount())
                .categorieEnseignant(categorie)
                .nbHeureCategorie(enseignant.getNbHeureCategorie(categorie))
                .maxHeuresService(enseignant.getMaxHeuresService())
                .heuresAssignees(heuresAssigneesParAnnee)
                .user(enseignant.getUser())
                .build();
    }
}
