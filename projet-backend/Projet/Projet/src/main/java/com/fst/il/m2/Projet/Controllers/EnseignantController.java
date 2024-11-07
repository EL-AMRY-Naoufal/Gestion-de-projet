package com.fst.il.m2.Projet.Controllers;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.business.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    private final EnseignantService enseignantService;

    @Autowired
    public EnseignantController(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }

    @GetMapping("/{id}/affectations")
    public ResponseEntity<List<Affectation>> getAffectationsByEnseignantId(@PathVariable Long id) {

        List<Affectation> affectations = enseignantService.getAffectationsByEnseignantId(id);

        if (affectations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(affectations, HttpStatus.OK);
    }
}
