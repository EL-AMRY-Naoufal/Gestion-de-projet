package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.business.ResponsableFormationService;
import com.fst.il.m2.Projet.dto.DepartementDto;
import com.fst.il.m2.Projet.dto.FormationDto;
import com.fst.il.m2.Projet.models.Departement;
import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.models.ResponsableFormation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FormationMapper {

    public ResponsableFormationService responsableDepartementService;

    public Formation toEntity(FormationDto formationDto) {
        return Formation.builder()
                .id(formationDto.getId())
                .nom(formationDto.getNom())
                .departement(Departement.builder().id(formationDto.getDepartementId()).build())
                .responsableFormation(responsableDepartementService.getResponsableFormationById(formationDto.getId()))
                .build();
    }

    public FormationDto toDto(Formation formation) {
        return FormationDto.builder()
                .id(formation.getId())
                .nom(formation.getNom())
                .totalHeures(formation.getTotalHeures())
                .responsableFormationId(formation.getResponsableFormation().getId())
                .departementId(formation.getDepartement().getId())
                .build();
    }
}
