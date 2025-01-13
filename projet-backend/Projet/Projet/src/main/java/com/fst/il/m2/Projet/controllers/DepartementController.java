package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.DepartementService;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Departement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementService departementService;

    @GetMapping("/year/{anneeId}")
    public List<Departement> getDepartementsByYear(@PathVariable String anneeId) {
        List<Departement> departements = departementService.getDepartementsByAnnee(Annee.builder().id(Long.valueOf(anneeId)).build());
        departements.forEach((d) -> System.out.println(d.getNom()));
        return departements;
    }

    @GetMapping("/{id}")
    public Departement getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }


}
