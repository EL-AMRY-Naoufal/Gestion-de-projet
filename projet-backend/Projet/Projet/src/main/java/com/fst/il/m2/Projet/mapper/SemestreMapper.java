package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.SemestreDto;
import com.fst.il.m2.Projet.models.Niveau;
import com.fst.il.m2.Projet.models.Semestre;

public class SemestreMapper {
    public static Semestre toEntity(SemestreDto semestreDto) {
        return Semestre.builder()
                .id(semestreDto.getId())
                .nom(semestreDto.getNom())
                .niveau(Niveau.builder().id(semestreDto.getNiveauId()).build())
                .build();
    }

    public static SemestreDto toDto(Semestre semestre) {
        return SemestreDto.builder()
                .id(semestre.getId())
                .nom(semestre.getNom())
                .niveauId(semestre.getNiveau().getId())
                .build();
    }
}
