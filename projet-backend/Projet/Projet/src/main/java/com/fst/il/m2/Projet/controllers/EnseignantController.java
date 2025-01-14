package com.fst.il.m2.Projet.controllers;
import com.fst.il.m2.Projet.dto.AffectationDTO;
import com.fst.il.m2.Projet.dto.EnseignantDto;
import com.fst.il.m2.Projet.mapper.EnseignantMapper;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.business.EnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
public class EnseignantController {
    private final EnseignantService enseignantService;

    @GetMapping("/{id}/affectations")
    public ResponseEntity<List<AffectationDTO>> getAffectationsByEnseignantId(@PathVariable Long id) {

        List<AffectationDTO> affectations = enseignantService.getAffectationsByEnseignantIdFormated(id);

        if (affectations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(affectations, HttpStatus.OK);
    }
    
    @GetMapping("/enseignants-non-enregistres")
    public ResponseEntity<List<User>> getEnseignantsNotInEnseignantTable() {
        List<User> users = enseignantService.getUsersWithRoleEnseignantNotInEnseignant();
        return ResponseEntity.ok(users);
    }

    @GetMapping()
    public List<User> getEnseignants(){
        return enseignantService.getEnseignants();
    }

    @PostMapping()
    public EnseignantDto createEnseignant(@RequestBody EnseignantDto enseignant) {
        return EnseignantMapper.enseignantToEnseignantDto(this.enseignantService.createEnseignant(
                enseignant.getId(),
                enseignant.getMaxHeuresService(),
                enseignant.getHeuresAssignees(),
                enseignant.getCategorieEnseignant(),
                enseignant.getNbHeureCategorie(),
                1L)
        );
    }

    @GetMapping("{id}")
    public EnseignantDto getEnseignantById(@PathVariable Long id) {
        return EnseignantMapper.enseignantToEnseignantDto(enseignantService.getEnseignantById(id));
    }

    @PutMapping()
    public  EnseignantDto updateEnseignant(@RequestBody EnseignantDto enseignant) {
        if(enseignant.getId() == null) {
            return EnseignantMapper.enseignantToEnseignantDto(
                    this.enseignantService.createEnseignant(
                            enseignant.getUser().getId(),
                            enseignant.getMaxHeuresService(),
                            enseignant.getHeuresAssignees(),
                            enseignant.getCategorieEnseignant(),
                            enseignant.getNbHeureCategorie(),
                            1L

                    )
            );
        }
        return EnseignantMapper.enseignantToEnseignantDto(
                this.enseignantService.updateEnseignant(
                        enseignant.getId(),
                        enseignant.getMaxHeuresService(),
                        enseignant.getCategorieEnseignant(),
                        enseignant.getNbHeureCategorie()
                )
        );
    }
}
