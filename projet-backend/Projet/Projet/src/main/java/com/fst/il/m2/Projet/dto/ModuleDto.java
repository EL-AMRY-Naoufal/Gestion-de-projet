package com.fst.il.m2.Projet.dto;


import com.fst.il.m2.Projet.enumurators.TypeHeure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModuleDto {
    private Long id;
    private String nom;
    private Map<TypeHeure, Integer> heuresParType;
    private Long semestreId;
}
