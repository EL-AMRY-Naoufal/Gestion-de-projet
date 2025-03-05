package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.dto.DepartementDto;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Departement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DepartementMapper {

    public ResponsableDepartementService responsableDepartementService;

    public Departement toEntity(DepartementDto departementDto) {
        return Departement.builder()
            .id(departementDto.getId())
            .nom(departementDto.getNom())
            .annee(Annee.builder().id(departementDto.getAnneeId()).build())
            .responsableDepartement(responsableDepartementService.getResponsableDepartementById(departementDto.getResponsableDepartementId()))
            .build();
    }

    public DepartementDto toDto(Departement departement) {
        return DepartementDto.builder()
            .id(departement.getId())
            .nom(departement.getNom())
            .anneeId(departement.getAnnee().getId())
            .responsableDepartementId(departement.getResponsableDepartement().getId())
            .build();
    }
}