package com.fst.il.m2.Projet.Controllers;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieEnseignantController {
    @GetMapping
    public List<CategorieEnseignant> getCategories() {
        return Arrays.asList(CategorieEnseignant.values());
    }
}
