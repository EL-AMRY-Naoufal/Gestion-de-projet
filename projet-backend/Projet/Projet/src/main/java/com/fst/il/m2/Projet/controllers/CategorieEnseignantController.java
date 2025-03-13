package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Catégories Enseignant", description = "Endpoints pour récupérer les catégories des enseignants")
public class CategorieEnseignantController {

    @Operation(summary = "Récupérer les catégories des enseignants", description = "Cette méthode permet de récupérer la liste des catégories des enseignants.")
    @GetMapping
    public List<CategorieEnseignant> getCategories() {
        return Arrays.asList(CategorieEnseignant.values());
    }
}