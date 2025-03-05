package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.GroupeDto;
import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.models.Module;

public class GroupeMapper {
    public static Groupe toEntity(GroupeDto groupeDto) {
        return Groupe.builder()
                .id(groupeDto.getId())
                .nom(groupeDto.getNom())
                .type(groupeDto.getType())
                .totalHeuresDuGroupe(groupeDto.getTotalHeuresDuGroupe())
                .heuresAffectees(groupeDto.getHeuresAffectees())
                .module(Module.builder().id(groupeDto.getModuleId()).build())
                .build();
    }

    public static GroupeDto toDto(Groupe groupe) {
        return GroupeDto.builder()
                .id(groupe.getId())
                .nom(groupe.getNom())
                .type(groupe.getType())
                .heuresAffectees(groupe.getHeuresAffectees())
                .totalHeuresDuGroupe(groupe.getTotalHeuresDuGroupe())
                .moduleId(groupe.getModule().getId())
                .build();
    }
}
