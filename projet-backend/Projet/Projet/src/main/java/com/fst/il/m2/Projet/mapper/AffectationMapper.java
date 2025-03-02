package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.AffectationDto;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.Groupe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AffectationMapper {
    public static Affectation toEntity(AffectationDto affectationDto) {
        return Affectation.builder()
                .id(affectationDto.getId())
                .heuresAssignees(affectationDto.getHeuresAssignees())
                .commentaire(affectationDto.getCommentaire())
                .dateAffectation(LocalDate.parse(affectationDto.getDateAffectation()
                        , DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(Locale.FRANCE)))
                .enseignant(Enseignant.builder().id(affectationDto.getEnseignantId()).build())
                .groupe(Groupe.builder().id(affectationDto.getGroupeId()).build())
                .build();
    }

    public static AffectationDto toDto(Affectation affectation) {
        return AffectationDto.builder()
                .id(affectation.getId())
                .heuresAssignees(affectation.getHeuresAssignees())
                .dateAffectation(affectation.getDateAffectation().toString())
                .commentaire(affectation.getCommentaire())
                .enseignantId(affectation.getEnseignant().getId())
                .groupeId(affectation.getGroupe().getId())
                .moduleId(affectation.getGroupe().getModule().getId())
                .build();
    }
}
