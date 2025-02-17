package com.fst.il.m2.Projet.mapper;

import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.models.Semestre;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ModuleMapper {
    public static Module toEntity(ModuleDto moduleDto) {
        return Module.builder()
                .id(moduleDto.getId())
                .nom(moduleDto.getNom())
                .heuresParType(moduleDto.getHeuresParType())
                .semestre(Semestre.builder().id(moduleDto.getSemestreId()).build())
                .build();
    }

    public static ModuleDto toDto(Module module) {
        return ModuleDto.builder()
                .id(module.getId())
                .nom(module.getNom())
                .heuresParType(module.getHeuresParType())
                .semestreId(module.getSemestre().getId())
                .build();
    }
}
