package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.AnneeDto;
import com.fst.il.m2.Projet.models.Annee;

public class AnneeMapper {
    public static Annee toEntity(AnneeDto anneeDto) {
        return Annee.builder()
                .id(anneeDto.getId())
                .debut(anneeDto.getDebut())
                .build();
    }

    public static AnneeDto toDto(Annee annee) {
        return AnneeDto.builder()
                .id(annee.getId())
                .debut(annee.getDebut())
                .build();
    }
}
