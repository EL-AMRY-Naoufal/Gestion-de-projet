package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.NiveauDto;
import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.models.Niveau;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NiveauMapper {

    public static Niveau toEntity(NiveauDto niveauDto) {
        return Niveau.builder()
                .id(niveauDto.getId())
                .nom(niveauDto.getNom())
                .formation(Formation.builder().id(niveauDto.getFormationId()).build())
                .build();
    }

    public static NiveauDto toDto(Niveau niveau) {
        return NiveauDto.builder()
                .id(niveau.getId())
                .nom(niveau.getNom())
                .formationId(niveau.getFormation().getId())
                .build();
    }
}

