package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.models.Enseignant;

public class EnseignantMapper {
    public static EnseignantDto enseignantToEnseignantDto(Enseignant enseignant) {
        return EnseignantDto.builder()
                .id(enseignant.getUser().getId())
                .categorie(enseignant.getCategorie())
                .maxHeuresService(enseignant.getMaxHeuresService())
                .heuresAssignees(enseignant.getHeuresAssignees())
                .build();
    }
}
