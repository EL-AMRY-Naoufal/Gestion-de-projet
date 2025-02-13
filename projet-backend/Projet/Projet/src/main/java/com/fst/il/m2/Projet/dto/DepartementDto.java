package com.fst.il.m2.Projet.dto;

import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.models.ResponsableDepartement;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class DepartementDto {
    private Long id;

    private String nom;

    private Long responsableDepartementId;

    private Long anneeId;
}
